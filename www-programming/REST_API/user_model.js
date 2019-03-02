const mongoose = require('mongoose');

var user_schema = new mongoose.Schema({
  username: {type: String, required: true},
  password: {type: String, required: true}
});

exports.User = mongoose.model('user', user_schema);
