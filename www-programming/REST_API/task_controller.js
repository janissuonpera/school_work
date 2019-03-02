const mongoose = require('mongoose');
const task_model = require('./task_model');


//Connect to a database
mongoose.connect('mongodb://localhost/taskdb');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

//Model of a task
var Task = task_model.Task;

//Lists all tasks
exports.list_tasks = function(req, res, next){

  Task.find({}, '-__v -_id', function(err, tasks){
    if(err){
      res.status(400).json({
        error: err.message
      })
    }
    else{
      //Adds link to self to json
      res.status(200).json({
        count: tasks.length,
        tasks: tasks.map(task=>{
          var spaceless_name = task.task_name.split(" ").join("%20");
          return {
            task_name: task.task_name,
            task_status: task.task_status,
            links: {
              self_url: 'http://localhost:3000/tasks/' + spaceless_name
            }
          }
        })
      });
    }
  });
}

//Get one task
exports.get_task = function(req, res, next){
  var name = req.params.name;
  Task.find({task_name: name}, '-__v -_id', function (err, task) {
    if (err){
      res.status(400).json({
        Error: err.message
      });
    }
    else{
      if(task.length>0){
        res.status(200).json({
          task: task,
          links:{
            url: 'http://localhost:3000/tasks'
          }
        });
      }else{
        res.status(404).json({
          Error: "Task not found"
        });
      }
    }
  });
}

//Adds a new tasks
exports.add_task = function (req, res, next){
  var new_task = new Task({
    task_name: req.body.name
  });
  new_task.save(function(err){
    if (err){
      res.status(400).json({
        Error: err.message
      })
    }
    else
      res.status(200).json(new_task);
  });
}

//Delete a tasks
exports.delete_task = function(req, res, next){
  Task.deleteOne({ task_name: req.params.name }, function(err, result){
    if(err){
      res.status(400).json({
        error: "Deletion failed"
      });
    }else{
      if(result.deletedCount != 0){
        res.status(200).json({
          message: "Deletion of task successful!"
        })
      }else{
        res.status(400).json({
          error: "Invalid task"
        })
      }
    }
  })
}

//Delete all task_status
exports.delete_all = function(req, res, next){
  Task.deleteMany({}, function(err, result){
    if(err){
      res.status(400).json({
        error: "Deletion failed"
      });
    }else{
      if(result.deletedCount != 0){
        res.status(200).json({
          message: "Deletion of all tasks successful!"
        })
      }else{
        res.status(400).json({
          error: "No tasks to be removed!"
        })
      }
    }
  })
}


//Update tasks
exports.update_task = function(req, res, next){
  var name = req.params.name;
  if(req.body.status==="done" || req.body.status==="not done"){
    Task.updateOne({task_name:name}, { $set: { task_status: req.body.status } }, function(err, result){
      if(err){
        res.status(400).json({
          error: "Update failed"
        });
      }else{
        res.status(200).json({
          message: "Update successful!"
        });
      }
    });
  }else{
    res.status(400).json({
      message: "Invalid status"}
    );
  }

}
