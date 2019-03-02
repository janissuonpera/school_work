//Import the database, in this case just an array
var db = require('./model');

exports.list = function(req, res, next) {
  var items = db.items;
  //console.log(items);
  res.render('view', {item: items});

}
