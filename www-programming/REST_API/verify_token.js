const jwt = require('jsonwebtoken');

module.exports = function(req, res, next){
  try{
    var verified = jwt.verify(req.body.token, "verySecretKey");
    console.log("Authentication passed");
    next();
  }catch(err){
    return res.status(401).json({
      message: "Authentication failed",
      error: err.message
    });
  }
}
