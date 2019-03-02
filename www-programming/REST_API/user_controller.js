const mongoose = require('mongoose');
const user_model = require('./user_model');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const saltRounds = 10;

//Connect to a database
mongoose.connect('mongodb://localhost/taskdb');
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));

//Model of a user
var User = user_model.User;

//Create a new user
exports.add_user = function (req, res, next){
  var new_user;
  bcrypt.hash(req.body.password, saltRounds, function(err, hash) {
    if(err){
      res.status(400).json({
        Error: err.message
      });
    }
    else{
      new_user = new User({
        username: req.body.username,
        password: hash
      });
      new_user.save(function(err){
        if (err){
          res.status(400).json({
            Error: err.message
          })
        }
        else
          res.status(200).json(new_user);
      });
    }
  });
}

//Login and send a authentication token
exports.sign_in = function (req, res, next){
  User.find({username: req.body.username}, function(err, user){
    if (err){
      res.status(400).json({
        Error: err.message
      });
    }
    else{
      if(user.length>0){
        bcrypt.compare(req.body.password, user[0].password, function(err, result){
          if (err){
            return res.status(401).json({
              Error: "Authentication failed"
            });
          }else{
            if(result){
              const token = jwt.sign({ username: user[0].username},"verySecretKey",{expiresIn: "1h"});
              return res.status(200).json({
                message: "Authentication successful",
                token: token
              });
            }
            else{
              res.status(401).json({
                Error: "Authentication failed"
              });
            }
          }
        });
      }else{
        res.status(404).json({
          Error: "User not found"
        });
      }
    }
  });
}
