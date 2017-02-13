var express = require('express');
var app = express();
var server = require('http').Server(app)
var io = require('socket.io')(server);

var net = require('net');

var PORT = 3500;
var HOST = 'localhost';

app.use(express.static(__dirname + '/src'));


app.get('/', function (req, res) {
    var options = {
        root: __dirname + '/src/',
        dotfiles: 'deny',
        headers: {
            'x-timestamp': Date.now(),
            'x-sent': true
        }
    };
    var fileName = "test.html";
    res.sendFile(fileName, options, function(err) {
        if (err) {
          next(err);
        } else {
          console.log('Sent:', fileName);
        };
    });
});

io.on('connection', function (socket) {
    socket.emit('news', { hello: 'world' });
    socket.on('msg', function (data) { //testing socket communication
        console.log(data);
    });

    socket.on('testmessage', function (data) {
        console.log(data);
    })
});


server.listen(3000)
