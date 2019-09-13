const express = require('express')
const server = require('./server')
const router = express.Router()

router.post('/setQuizCompleted', (req, res) => {
  
    const userId = req.body.userId
    const quizId = req.body.quizId
    const quizScore = req.body.quizScore

    const queryString = "INSERT INTO quizzes (studentId, quizId, quizScore) VALUES((SELECT studentId FROM student WHERE student.userId = ?), ?, ?) ON DUPLICATE KEY UPDATE quizScore=?;"
    server.connection().query(queryString, [userId, quizId, quizScore, quizScore], (err, results, fields) => {
      if (err) {
        console.log(err)
        res.send({
            "code":500,
            "success":"Setting quiz failed"
            });        
      } else {
        res.send({
            "code":200,
            "success":"Adding quiz sucessfull",
          });
      }
    });
});

router.get('/getQuizzesCompleted/:id', (req, res) => {
    console.log("Fetching user with id: " + req.params.id)

    const studentId = req.params.id
    const queryString = "SELECT * FROM quizzes WHERE studentId = ?"
    server.connection().query(queryString, [studentId], (err, results, fields) => {
      if (err) {
        console.log("Failed to get quizzes: " + err)
        res.sendStatus(500)
        return
      }
      res.send({
        "code":200,
        "success":"Quizzes retrieved successfully",
        "completedQuizzes":results
        });
        console.log(results[0])
    })
})

module.exports = router