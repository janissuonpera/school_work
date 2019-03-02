const express = require('express');
const bodyParser = require('body-parser');
const tasks_route = require('./router');

//Initialize app
const app = express();

//BodyParser middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

//Catch routes
app.use('/', tasks_route);

app.listen(3000, function(){
  console.log("Listening to port 3000");
});
