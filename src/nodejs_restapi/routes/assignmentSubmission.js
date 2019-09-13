const express = require('express')
const server = require('./server')
const notifications = require('./notifications')
const router = express.Router()
var fs = require("fs");

router.post("/getAssignmentSubmissions", (req, res) => {
  console.log("Getting assignments submission")

  const assignmentId = req.body.assignmentId

  const queryString = "SELECT * FROM assignmentsubmission JOIN users ON assignmentsubmission.userId = users.id JOIN assignment on assignmentsubmission.assignmentId = assignment.idAssignment WHERE assignmentId = ?"
  server.connection().query(queryString, [assignmentId], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      res.send({
        "code":200,
        "success":"Getting assignments sucessfull",
        "assignmentSubmissions":results
      });
      console.log(results)
    }
  })
})

router.post("/assignmentSubmission", (req, res) => {

  const assignmentId = req.body.assignmentId
  const userId = req.body.userId
  const submission = req.body.submission

  const name = new Date().toISOString().replace(/:/g, '-') + ".jpg"
  const location = 'assignmentSubmissions/' + name
  fs.writeFile(location, new Buffer(submission, "base64"), function(err) {});

  const queryString = "INSERT INTO assignmentsubmission (assignmentId, userId, submission) VALUES (?, ?, ?)"
  server.connection().query(queryString, [assignmentId, userId, location], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      sendEmailAssignmentSubmission(userId)
      res.send({
        "code":200,
        "success":"Adding assignment submission sucessfull",
        "results": results
      });
    }
  })
})

router.post("/submitGrade", (req, res) => {

  const assignmentSubmissionId = req.body.assignmentSubmissionId
  const grade = req.body.grade

  const queryString = "UPDATE assignmentsubmission SET grade = ? WHERE assignmentSubmissionId = ?"
  server.connection().query(queryString, [grade, assignmentSubmissionId], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      sendEmailUpdatedGrade(assignmentSubmissionId)
      res.send({
        "code":200,
        "success":"Adding assignment submission sucessfull",
      });
    }
  })
})

router.get("/getStudentSubmissions/:id", (req, res) => {

  const studentId = req.params.id

  const queryString = "SELECT * FROM assignmentsubmission JOIN users ON assignmentsubmission.userId = users.id JOIN assignment on assignmentsubmission.assignmentId = assignment.idAssignment WHERE userId = ?;"
  server.connection().query(queryString, [studentId], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      res.send({
        "code":200,
        "success":"Adding assignment submission sucessfull",
        "assignmentSubmissions":results
      });
    }
  })
})

function sendEmailAssignmentSubmission(userId) {
  const queryString = "SELECT email FROM users WHERE id = ?;"
  server.connection().query(queryString, [userId], (err, results, fields) => {
    if (err) {
      console.log(err)
    } else {
      notifications.sendMail("Assignment submitted", "Your submission has been successful on the classroom and is ready for grading.", results[0].email)
    }
  })
}

function sendEmailUpdatedGrade(assignmentSubmissionId) {
  const queryString = "SELECT email FROM users WHERE id = (SELECT userId FROM assignmentsubmission WHERE assignmentSubmissionId = ?)"
  server.connection().query(queryString, [assignmentSubmissionId], (err, results, fields) => {
    if (err) {
      console.log(err)
    } else {
      notifications.sendMail("Assignment Graded", "Your assignment has been graded. Please check the classroom for your grade.", results[0].email)
    }
  })
}

module.exports = router