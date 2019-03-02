const mongoose = require('mongoose');
exports.user_schema = new mongoose.Schema({
  username:  String,
  email: String,
  password:   String,
});
