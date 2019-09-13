const express = require('express')
const passwordHash = require('password-hash');
const server = require('./server')
const router = express.Router()

router.get('/login', (req, res) => {
    console.log(req.session.user);
    res.render('login');
})

router.get('/dashboard', checkSignIn, function(req, res){
    console.log(req.session.user);
    res.render('dashboard', {email: req.session.user.email})
});

router.use('/dashboard', function(err, req, res, next){
    console.log(err);
    //User should be authenticated! Redirect him to log in.
    res.redirect('/login');
});

router.get('/logout', function(req, res){
    req.session.destroy(function(){
       console.log("user logged out.")
    });
    res.redirect('/login');
 });

router.post('/weblogin', (req, res) => {

    const email = req.body.email
    const password = req.body.password
    const queryString = "SELECT * FROM users WHERE email = ?"
    server.connection().query(queryString, [email], (err, results, fields) => {
      console.log(results)
      if (err) {
        res.render('login', {message: "Invalid credentials!"});
        return
      } else {
        console.log(results.length)
        if(results.length > 0){     
          if(passwordHash.verify(password, results[0].password)){
            req.session.user = results[0];
            res.redirect('/dashboard');
          } else{
            res.render('login', {message: "Invalid credentials!"});
          }
        } else{
            res.render('login', {message: "Invalid credentials!"});
        }
      }
      res.end()
    })
}) 

function checkSignIn(req, res, next){
    if(req.session.user){
       next();     //If session exists, proceed to page
    } else {
       var err = new Error("Not logged in!");
       console.log(req.session.user);
       next(err);  //Error, trying to access unauthorized page!
    }
}

module.exports = router