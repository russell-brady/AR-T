const express = require('express')
const server = require('./server')
const router = express.Router()

router.post("/createClass", (req, res) => {
  console.log("Creating class")

  const className = req.body.className
  const classId = req.body.classId
  const userId = req.body.userId

  console.log(className, classId, userId)

  const queryString = "INSERT INTO class (className, classId, teacherId) VALUES (?, ?, (SELECT teacherId FROM teacher WHERE teacher.userId = ?))"
  server.connection().query(queryString, [className, classId, userId], (err, results, fields) => {
    if (err) {
      console.log(err)

      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      res.send({
        "code":200,
        "success":"Adding Class sucessfull",
        "classes":results
      }); 
    }

  })
})

router.post("/getClasses", (req, res) => {
  console.log("Getting classes")

  const id = req.body.id
  console.log(id)

  const queryString = "SELECT * FROM class WHERE teacherId = (SELECT teacherId FROM teacher where teacher.userId = ?)"
  server.connection().query(queryString, [id], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      console.log(results)
      res.send({
        "code":200,
        "success":"Getting Classes sucessfull",
        "classes":results
      }); 
    }
  })
})

router.post("/getClassStudents", (req, res) => {
  console.log("Getting students for class")

  const classId = req.body.classId

  const queryString = "SELECT * FROM users JOIN student ON student.userId = users.id WHERE classId = ?"
  server.connection().query(queryString, [classId], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      res.send({
        "code":200,
        "success":"Getting students sucessfull",
        "students":results
      });
    }
  })
})


router.post("/getClassmates", (req, res) => {
  console.log("Getting students for class")

  const id = req.body.id

  const queryString = "SELECT * FROM users JOIN student ON student.userId = users.id WHERE classId = (SELECT classId FROM student WHERE student.userId = ?) AND users.id != ?"
  server.connection().query(queryString, [id, id], (err, results, fields) => {
    if (err) {
      res.send({
        "code":500,
        "success":"Failed"
      });
    } else {
      res.send({
        "code":200,
        "success":"Getting students sucessfull",
        "students":results
      });
    }
  })
})

module.exports = router