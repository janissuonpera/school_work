const express = require('express');
const app = express();
//Export middleware router from router.js
const router = require('./router');

const hbs = require('express-hbs');

//Setting up handlebars and views for mvc
app.engine('hbs', hbs.express4());
app.set('view engine', 'hbs');
app.set('views', __dirname);

//Setting up router middleware with the exported router
app.use("/", router);


app.listen(3000, function(){
  console.log("Listening to port 3000");
});
