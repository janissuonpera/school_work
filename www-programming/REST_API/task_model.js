const mongoose = require('mongoose');

var task_schema = new mongoose.Schema({
  task_name: {type: String, required: true},
  task_status: {type: String, default: 'not done'}
});

exports.Task = mongoose.model('task', task_schema);
