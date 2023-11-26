const express = require('express')
const router = express.Router()
const db = require('../db')
const utils = require('../utils')
const multer = require('multer')
const upload = multer({ dest: 'images/' })

router.get('/', (request, response) => {
  db.execute(`select * from medicines`, (error, result) => {
    response.send(utils.createResult(error, result))
  })
})

router.post('/', upload.single('photo'), (request, response) => {
  const { title, company, price, mrp, unit, expiryDate } = request.body
  console.log(request.body)
  const thumbnail = request.file.filename
  const query = `insert into medicines (title, company, price, mrp, unit, thumbnail, expiryDate) values (?, ?, ?, ?, ?, ?, ?)`
  db.execute(
    query,
    [title, company, price, mrp, unit, thumbnail, expiryDate],
    (error, result) => {
      response.send(utils.createResult(error, result))
    }
  )
})

module.exports = router
