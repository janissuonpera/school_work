const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const model = require('./model');
const { check, validationResult } = require('express-validator/check');

//Connect to a database
mongoose.connect('mongodb://localhost/test007');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

//Create a model from imported user_schema
var User = mongoose.model('user', model.user_schema);

//Import bcrypt
const bcrypt = require('bcryptjs');
const saltRounds = 10;

//Renders a view with a list of all users and their emails
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

  //Checking that middleware validators in router.js caught no errors.
  const errors = validationResult(req);

  //If parameters are ok, continue with database operations. Otherwise render a validation page.
  if (errors.isEmpty()){
    bcrypt.hash(password, saltRounds, function(err, hash) {
      //Generated hash is saved to the database instead of the actual password
      var new_user = new User({ username: username , email: email, password: hash});
      new_user.save(function (err) {
        if (err) return console.error(err);
        res.redirect('/users');
      });
    });
  }else{
    res.render("validation_error_view");
  }

}
