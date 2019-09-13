const express = require('express')
const server = require('./server')
const router = express.Router()

router.post('/setLessonsCompleted', (req, res) => {
  
    const userId = req.body.userId
    const lessonId = req.body.lessonId

    const queryString = "INSERT INTO lessons (studentId, lessonId) VALUES((SELECT studentId FROM student WHERE student.userId = ?), ?) ON DUPLICATE KEY UPDATE lessonId=?;"
    server.connection().query(queryString, [userId, lessonId, lessonId], (err, results, fields) => {
      if (err) {
        console.log(err)
        res.send({
            "code":500,
            "success":"Setting lessons failed"
            });        
      } else {
        res.send({
            "code":200,
            "success":"Adding lessons sucessfull",
          });
      }
    });
});

router.get('/getLessonsCompleted/:id', (req, res) => {
    console.log("Fetching user with id: " + req.params.id)

    const studentId = req.params.id
    const queryString = "SELECT * FROM lessons WHERE studentId = ?"
    server.connection().query(queryString, [studentId], (err, results, fields) => {
      if (err) {
        console.log("Failed to get lessons: " + err)
        res.sendStatus(500)
        return
      }
      res.send({
        "code":200,
        "success":"lessons retrieved successfully",
        "completedLessons":results
        });
        console.log(results[0])
    })
})

module.exports = router