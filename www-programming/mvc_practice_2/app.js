//Create a few users using post-requests or just filling the form.
//The users will appear on the page. If you try to submit an invalid form, you
//will be redirected to another view. That view has a button to return to the original site.


const express = require('express');
const app = express();
const bodyParser = require('body-parser');
//Export middleware router from router.js
const router = require('./router');

const hbs = require('express-hbs');

//Setting up handlebars and views for mvc
app.engine('hbs', hbs.express4());
app.set('view engine', 'hbs');
app.set('views', __dirname);

//Setting up router middleware with the exported router
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))
app.use("/", router);


app.listen(3000, function(){
  console.log("Listening to port 3000");
});
