const express = require('express');
const router = express.Router();
const task_controller = require('./task_controller');
const user_controller = require('./user_controller')
const checkToken = require('./verify_token');
const jwt = require('jsonwebtoken');


//Catch task routes
router.get('/tasks', task_controller.list_tasks);
router.get('/tasks/:name', task_controller.get_task);
router.post('/tasks', task_controller.add_task);
//These routes require token authentication
router.delete('/tasks/deleteall', checkToken, task_controller.delete_all);
router.delete('/tasks/:name', checkToken, task_controller.delete_task);
router.put('/tasks/:name', checkToken, task_controller.update_task);

//Catch user routes
router.post('/user/signup', user_controller.add_user);
router.post('/user/signin', user_controller.sign_in);


//Send error message in json format to better fit RESTful constraints
router.get('*', function(req, res, next){
  res.status(400).json(
    {
      error: "Invalid route! Try /tasks",
      links: "http://localhost:3000/tasks"
    }
  );
});

//Send error message in json format to better fit RESTful constraints
router.post('*', function(req, res, next){
  res.status(400).json(
    {
      error: "Invalid route! Try /tasks",
      links: "http://localhost:3000/tasks"
    }
  );
});

//Send error message in json format to better fit RESTful constraints
router.delete('*', function(req, res, next){
  res.status(400).json(
    {
      error: "Invalid route! Try /tasks/deleteall or /tasks/:task_name",
      links: "http://localhost:3000/tasks/deleteall"
    }
  );
});

//Send error message in json format to better fit RESTful constraints
router.put('*', function(req, res, next){
  res.status(400).json(
    {
      error: "Invalid route! Try /tasks/:task_name",
      links: "http://localhost:3000/tasks"
    }
  );
});



module.exports = router;
