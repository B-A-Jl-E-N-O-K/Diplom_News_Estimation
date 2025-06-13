var fs = require('fs');
var http = require('http');
var https = require('https');
// var privateKey  = fs.readFileSync('sslcert/server.key', 'utf8');
// var certificate = fs.readFileSync('sslcert/server.crt', 'utf8');

// var credentials = {key: privateKey, cert: certificate};

const express = require('express');

const path = require('path');

const app = express();



app.use(express.static(path.join(__dirname, 'build')));

app.get(/(.*)/, (req, res) => {

  res.sendFile(path.join(__dirname, 'build', 'index.html'));

});

var httpServer = http.createServer(app);
// var httpsServer = https.createServer(credentials, app);

httpServer.listen(8080);
// httpsServer.listen(8443);

// const PORT = process.env.PORT || 3000;

// app.listen(PORT, () => {

//   console.log(`Server is running on port ${PORT}`);

// });