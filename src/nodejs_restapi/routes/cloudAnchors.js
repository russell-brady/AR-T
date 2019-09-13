const express = require('express')
const server = require('./server')
const router = express.Router()

router.post('/addCloudAnchorId', (req, res) => {
  console.log("Trying to add cloud anchor id...")

  const anchor_id = req.body.anchor_id
  const model_id = req.body.model_id

  const queryString = "INSERT INTO cloud_anchor (anchor_id, modelId) VALUES (?, ?); SELECT LAST_INSERT_ID();"
  server.connection().query(queryString, [anchor_id, model_id], (err, results, fields) => {
    if (err) {
      console.log("Failed to insert new cloud anchor: " + err)
      res.send({
        "code":500,
        "success":"Adding Id failed"
      });
      return
    }
    res.send({
      "code":200,
      "success":"Adding Id sucessfull",
      "anchorKey":results[0].insertId
    }); 
  })
})

router.post('/getCloudAnchorId', (req, res) => {
  console.log("Trying to get cloud anchor id...")

  const key = req.body.anchor_key 

  const queryString = "SELECT * FROM cloud_anchor JOIN ar_models ON cloud_anchor.modelId = ar_models.arModelId WHERE anchor_key = ?"
  server.connection().query(queryString, [key], (err, results, fields) => {
    if (err) {
      console.log("Failed to get anchorId: " + err)
      res.send({
        "code":500,
        "success":"Failed"
      });
      return
    } else {
      if(results.length > 0){
        res.send({
          "code":200,
          "success":"Sucessfull",
          "anchorId":results[0].anchor_id,
          "modelLocation":results[0].modelLocation
        });
      } else {
        res.send({
          "code":204,
          "success":"Anchor does not exist"
          })
      }
    }
    res.end()
  })  
})

module.exports = router