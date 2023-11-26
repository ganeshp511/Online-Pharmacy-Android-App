const express = require('express')
const cors = require('cors')
const morgan = require('morgan')
const jwt = require('jsonwebtoken')
const config = require('./config')
const utils = require('./utils')

// create a server
const app = express()
app.use(morgan('combined'))
app.use(cors('*'))
app.use(express.json())
app.use(express.urlencoded({extended:true}))

// adding middleware to extract the currently logged user
app.use((request, response, next) => {
  if (request.url == '/user/signup' ||
      request.url == '/user/signin') {
    console.log(`token not needed`)
    next()
  } else {
    const token = request.headers['token']
    console.log(token)
    try {
      // validating the token
      const data = jwt.verify(token, config.secret)

      // add the user details to the request so that every module can access the current user
      request.userId = data.id
      request.userName = data.name
      request.userEmail = data.email

      // call the main route
      next()
    } catch (ex) {
      response.send(utils.createError('invalid token'))
    }
  }
})

// add routes
const routerUser = require('./routes/user')
const routerMedicine = require('./routes/medicine')
const routerOrder = require('./routes/order')
const routerReminder = require('./routes/reminder')

app.use('/user', routerUser)
app.use('/medicine', routerMedicine)
app.use('/order', routerOrder)
app.use('/reminder', routerReminder)

// start the server
app.listen(4000, '0.0.0.0', () => {
  console.log(`server started on 4000`)
})
