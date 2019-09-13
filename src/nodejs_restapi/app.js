// load our app server using express....
const express = require('express')
const app = express()
const morgan = require('morgan')
const bodyParser = require('body-parser')
var session = require('express-session');

app.engine('html', require('ejs').renderFile);
app.set('view engine', 'html');
app.set('views', __dirname + '/public');

app.use(bodyParser.urlencoded({extended:true, limit:'50mb'}))
app.use(session({
    secret: "Shh, its a secret!",
    resave: false,
    saveUninitialized: true
}));
app.use(morgan('short'))

const authentication = require('./routes/authentication.js')
const classes = require('./routes/class.js')
const cloudAnchors = require('./routes/cloudAnchors.js')
const assignments = require('./routes/assignments.js')
const assignmentSubmission = require('./routes/assignmentSubmission.js')
const models = require('./routes/models.js')
const webAppAuth = require('./routes/webAppAuth.js')
const lessons = require('./routes/lessons')
const quizzes = require('./routes/quizzes')

app.use(authentication)
app.use(classes)
app.use(cloudAnchors)
app.use(assignments)
app.use(assignmentSubmission)
app.use(models)
app.use(webAppAuth)
app.use(lessons)
app.use(quizzes)

app.use(express.static('./public'))
app.use('/uploads', express.static('./uploads'))
app.use('/assignmentSubmissions', express.static('./assignmentSubmissions'))

app.get("/", (req, res) => {
    console.log("Responding to root route")
    res.send("This is the root route...")
})

const PORT = process.env.PORT || 3003
app.listen(PORT, () => {
    console.log("Server is up and running...")
})

module.exports = app;