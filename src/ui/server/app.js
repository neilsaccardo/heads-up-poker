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
    });

    socket.on('action', function (data) {
        if (data.action === 'bet') {
            socket.emit('cfr', {action: 'call', amount: data.amount}); //call/raise/fold on a bet
        }
        if (data.action === 'check') {
            socket.emit('cfr', { action: 'call', amount: 0 }); //call/bet/fold on a check
        }
    });

    socket.on('newaction', function (data) {
        if (data.action === 'check') {
            socket.emit('fcb', {action: 'check', amount: data.amount});
        }
    });

    socket.on('startgame', function (data) {
        //receive the card info.
        socket.emit('fcb', {action: 'check', amount: 0});
    });
});


server.listen(3000)
