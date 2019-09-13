const express = require('express')
const passwordHash = require('password-hash');
const server = require('./server')
const router = express.Router()

router.post('/registerTeacher', (req, res) => {
    console.log("Trying to register a new student...")
  
    const email = req.body.email
    const type = req.body.type
    const name = req.body.name
    const hashedPassword = passwordHash.generate(req.body.password)
    console.log(hashedPassword)
  
    const queryString = "INSERT INTO users (name, email, password, type) VALUES (?, ?, ?, ?); INSERT INTO teacher (userId) VALUES (last_insert_id());"
    server.connection().query(queryString, [name, email, hashedPassword, type], (err, results, fields) => {
      if (err) {
        console.log("Failed to insert new student: " + err)
        res.send({
          "code":500,
          "success":"registration failed"
            });
        return
      }
      res.send({
        "code":200,
        "success":"registration sucessfull",
          });
    })
})

router.post('/registerStudent', (req, res) => {
  console.log("Trying to register a new student...")

  const email = req.body.email
  const type = req.body.type
  const name = req.body.name
  const classId = req.body.classId
  const hashedPassword = passwordHash.generate(req.body.password)
  console.log(hashedPassword)

  const queryString = "INSERT INTO users (name, email, password, type) VALUES (?, ?, ?, ?); INSERT INTO student (userId, classId) VALUES (last_insert_id(), ?);"
  server.connection().query(queryString, [name, email, hashedPassword, type, classId], (err, results, fields) => {
    if (err) {
      console.log("Failed to insert new student: " + err)
      res.send({
        "code":500,
        "success":"registration failed"
          });
      return
    }
    res.send({
      "code":200,
      "success":"registration sucessfull",
      "results":results
        });
    
  })
})

router.post('/login', (req, res) => {
    console.log("Trying to login...")
  
    const email = req.body.email
    const password = req.body.password
  
    const queryString = "SELECT * FROM users WHERE email = ?"
    server.connection().query(queryString, [email], (err, results, fields) => {
      console.log(results)
      if (err) {
        console.log("Failed to log in: " + err)
        res.send({
          "code":500,
          "success":"Login failed"
            });
        return
      } else {
        console.log(results.length)
        if(results.length > 0){     
          if(passwordHash.verify(password, results[0].password)){
            res.send({
              "code":200,
              "success":"login successful",
              "user": results[0]
            });
          } else{
            res.send({
              "code":204,
              "success":"incorrect credentials"
              });
          }
        } else{
          res.send({
            "code":204,
            "success":"incorrect credentials"
          });
        }
      }
      res.end()
    })
})

module.exports = router