const express = require('express');
const router = express.Router();
const bodyParser = require('body-parser');
const { check, validationResult } = require('express-validator/check');

//Importing the controller from controller.js
const controller = require('./controller');

//Catch /items route and use controller.list
router.get('/users', controller.list);
//Catch post request and validates and sanitizes the body.
router.post('/users/add', [check('username').isLength({ min: 5 }), check('email').isEmail(), check('password').isLength({ min: 5 }), controller.add_users]);

//Catching all other routes and sending 404 not found
router.get('*', function(req, res){
  res.writeHead(404, { 'Content-Type': 'text/plain' });
  res.end('Not found! Try /items');
});

module.exports = router;
