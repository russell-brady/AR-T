const express = require('express')
const server = require('./server')
const multer = require('multer')
const router = express.Router()
//var upload = multer({ dest: 'uploads/' })

const storage = multer.diskStorage({
    destination: function(req, file, cb) {
      cb(null, 'uploads/');
    },
    filename: function(req, file, cb) {
      cb(null, new Date().toISOString().replace(/:/g, '-') + file.originalname);
    }
  });

const fileFilter = (req, file, cb) => {
    //reject a file
    // if (file.mimetype === 'model/gltf-binary') {
        cb(null, true);
    // } else {
        // cb(null, false);
    // }
};  

const upload = multer({
    storage: storage,
    fileFilter:fileFilter
  });

router.post('/uploadModel', upload.single('modelFile'),(req, res) => {
    console.log(req.file)
  
    const modelName = req.body.modelName
    const modelDesc = req.body.modelDesc
    const userId = req.session.user.id
    const modelLocation = req.file.path.replace(/\\/g, "/")
  
    const queryString = "INSERT INTO ar_models (userId, modelName, modelDesc, modelLocation) VALUES (?, ?, ?, ?)"
    server.connection().query(queryString, [userId, modelName, modelDesc, modelLocation], (err, results, fields) => {
      if (err) {
        console.log("Failed to add model: " + err)
        res.render('dashboard', {message: "Failed to add model"});
        return
      }
      res.render('dashboard', {message: "Model added successfully!"});
    })
})

router.get('/getModels/:id', (req, res) => {
    console.log("Fetching user with id: " + req.params.id)

    const userId = req.params.id
    const queryString = "SELECT * FROM ar_models WHERE userId = ?"
    server.connection().query(queryString, [userId], (err, results, fields) => {
      if (err) {
        console.log("Failed to get models: " + err)
        res.sendStatus(500)
        return
      }
      res.send({
        "code":200,
        "success":"Models retrieved successfully",
        "models":results
        });
        console.log(results)
    })
})

module.exports = router