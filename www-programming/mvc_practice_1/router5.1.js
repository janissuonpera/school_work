const express = require('express');
const router = express.Router();
//Importing the controller from controller.js
const controller = require('./controller');

//Catch /items route and use controller.list
router.get('/items', controller.list);

//Catching all other routes and sending 404 not found
router.get('*', function(req, res){
  res.writeHead(404, { 'Content-Type': 'text/plain' });
  res.end('Not found! Try /items');
});

module.exports = router;
