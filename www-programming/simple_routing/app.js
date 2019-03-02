const http = require('http');
const fs = require('fs');
const url = require('url');
const xssFilters = require('xss-filters');

const hostname = '0.0.0.0';
const port = 3000;

const server = http.createServer((req, res) =>{
    var fullUrl = 'http://' +'none' + req.url;
    var url = new URL(fullUrl);
    var method = req.method;
    var params = url.search;

    if(method=='POST'){
      if(url.pathname==="/save"){
        var body = "";
        req.on('data', chunk => {
          body += chunk.toString(); // convert Buffer to string
          writer('data.txt', body + "\n");
        });
        res.statusCode = 201;
        res.end("File saved\n");
      }
      else{
        res.statusCode = 400;
        res.setHeader('Content-type', 'text/plain');
        res.end("Error! If you want to use POST and save something use URL: \"/save\"\n");
      }
    }


    else if(method=='GET'){
      if(url.pathname==="/print"){
        res.statusCode = 200;
        res.setHeader('Content-type', 'text/plain');
        fs.readFile('data.txt', 'utf8', function (err, data) {
          if (err){
            res.end("Invalid file");
          }
          res.end(xssFilters.inHTMLData(data));
        });
      }
      else if(url.pathname==="/read"){
        var test = url.search;
        var query = url.searchParams;
        var file = query.get("file");
        var type = query.get("type");

        if(file!=null && type!=null){
          res.statusCode = 200;
          res.setHeader('Content-type', type);
          fs.readFile(file, 'utf8', function (err, data) {
            if (err){
              res.end("Invalid parameters");
            }
            res.end(xssFilters.inHTMLData(data));
          });
        }
        else{
          res.statusCode = 400;
          res.setHeader('Content-type', 'text/plain');
          res.end("Invalid parameters");
        }
      }
      else{
        res.statusCode = 400;
        res.setHeader('Content-type', 'text/plain');
        res.end("If you want to use GET go to URL: \"/print\"");
      }
    }
});

server.listen(port, hostname, ()=>{
  console.log(`Server running at http://${hostname}:${port}/`);
});

function writer(path, content){
  fs.writeFile(path, content, function (err) {
    if (err) throw err;
    console.log('File saved.');
  });
}
