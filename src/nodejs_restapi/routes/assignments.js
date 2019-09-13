const express = require('express')
const server = require('./server')
const notifications = require('./notifications')
const router = express.Router()

router.post("/getAssignments", (req, res) => {
  console.log("Getting assignments for class")

  const classId = req.body.classId

  const queryString = "SELECT * FROM assignment WHERE classId = ?"
  server.connection().query(queryString, [classId], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      res.send({
        "code":200,
        "success":"Getting assignments sucessfull",
        "assignments":results
      });
    }
    console.log(results)
  })
})

router.post("/getStudentAssignments", (req, res) => {
  console.log("Getting assignments for class")

  const id = req.body.id

  const queryString = "SELECT * FROM assignment a LEFT JOIN assignmentsubmission asu ON a.idAssignment = asu.assignmentId AND asu.userId = ? WHERE a.classId = (SELECT classId FROM student WHERE student.userId = ?) AND (asu.userId IS NULL OR asu.userId = ?);"
  server.connection().query(queryString, [id, id, id], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      res.send({
        "code":200,
        "success":"Getting assignments sucessfull",
        "assignments":results
      });
    }
    console.log(results)
  })
})

router.post("/createAssignment", (req, res) => {
  console.log("Getting assignments for class")

  const classId = req.body.classId
  const title = req.body.title
  const desc = req.body.desc
  const dateAssigned = req.body.dateAssigned
  const dateDue = req.body.dateDue

  const queryString = "INSERT INTO assignment (classId, title, assignmentDesc, dateAssigned, dateDue) VALUES (?, ?, ?, ?, ?)"
  server.connection().query(queryString, [classId, title, desc, dateAssigned, dateDue], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      sendClassEmail(classId)
      res.send({
        "code":200,
        "success":"Getting assignments sucessfull",
        "assignments":results
      });
    }
    console.log(results)
  })
})

function sendClassEmail(classId) {
  const queryString = "SELECT * FROM users JOIN student ON student.userId = users.id WHERE classId = ?"
  server.connection().query(queryString, [classId], (err, results, fields) => {
    if (err) {
      console.log(err)
    } else {
      var string = "";
      var arrayLength = results.length;
      for (var i = 0; i < arrayLength; i++) {
        string += results[i].email + ',';
      }
      notifications.sendMail("New Assignment", "A new assignment has been added to the classroom for you to complete. Please check it out.", string)
    }
  })
}

module.exports = router