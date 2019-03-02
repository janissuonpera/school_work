const bodyParser = require('body-parser');
const { check, validationResult } = require('express-validator/check');
const mongoose = require('mongoose');
const model = require('./model');

mongoose.connect('mongodb://localhost/tests');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
var User = mongoose.model('user', model.user_schema);

const bcrypt = require('bcryptjs');
const saltRounds = 10;

exports.list = function(req, res, next) {
  User.find(function (err, users) {
    if (err) return console.error(err);
      //console.log(users);
      res.render('view', {item: users});
  });

}

exports.add_users = function(req, res, next){
  var username = req.body.username;
  var email = req.body.email;
  var password = req.body.password;

  //Validate and sanitize parameters from body
  check('username').isLength({ min: 5 });
  check('password').isLength({ min: 5 });
  check('email').isEmail();
  const errors = validationResult(req);

  //If parameters are ok, continue with database operations. Otherwise send 400 error and end
  if (errors.isEmpty()){
    bcrypt.hash(password, saltRounds, function(err, hash) {
      //Generated hash is saved to the database instead of the actual password
      var new_user = new User({ username: username , email: email, password: password});
      new_user.save(function (err) {
        if (err) return console.error(err);
        res.end("Data saved to db\n");
      });
    });
  }else{
    res.statusCode(400);
    res.render("validation_error_view");
  }

}
