const express = require('express')
const router = express.Router()
const db = require('../db')
const utils = require('../utils')
const cryptoJs = require('crypto-js')
const jwt = require('jsonwebtoken')
const config = require('../config')
const { request, response } = require('express')

router.get('/', (request, response) => {
  response.send(hello)
})
router.post('/signup', (request, response) => {
  const { firstName, lastName, email, password } = request.body
  db.execute(
    `insert into user (firstName, lastName, email, password) values (?, ?, ?, ?)`,
    [firstName, lastName, email, '' + cryptoJs.MD5(password)],
    (error, result) => {
      response.send(utils.createResult(error, result))
    }
  )
})

router.post('/signin', (request, response) => {
  const { email, password } = request.body
  db.execute(
    `select id, firstName, lastName, email, password from user where email = ? and password = ?`,
    [email, '' + cryptoJs.MD5(password)],
    (error, users) => {
      if (error) {
        response.send(utils.createError(error))
      } else if (users.length == 0) {
        response.send(utils.createError('user does not exist'))
      } else {
        const user = users[0]
        const token = jwt.sign(
          {
            id: user.id,
            name: user.firstName + ' ' + user.lastName,
            email: user.email,
          },
          config.secret
        )

        response.send(
          utils.createSuccess({
            token,
            name: user.firstName + ' ' + user.lastName,
          })
        )
      }
    }
  )
})

router.get('/profile', (request, response) => {
  db.execute(
    `select * from user where id = ${request.userId}`,
    (error, data) => {
      response.send(utils.createResult(error, data))
    }
  )
})

module.exports = router
