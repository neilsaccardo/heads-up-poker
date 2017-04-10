var express = require('express');
var app = express();
var server = require('http').Server(app)
var io = require('socket.io')(server);
var PokerEvaluator = require('poker-evaluator');
var db = require('./db.js');
var net = require('net');
var obj = PokerEvaluator.evalHand(["Th", "Kh", "Qh", "Jh", "9h", "3s", "5h"]);
console.log(obj);

var JAVA_PORT = 3500;
var HOST = 'localhost';


app.use(express.static(__dirname + '/src'));

var javaServerSocket = new net.Socket();
javaServerSocket.connect (JAVA_PORT, HOST, function() {
    console.log('HERE');
});

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

var idSocketMap = [];
var ids = [];
var sockets = [];
io.on('connection', function (socket) {

    socket.on('disconnect', function() {
        var i = sockets.indexOf(socket);
        console.log(idSocketMap.length);
        console.log(i);
        sockets.splice(i, 1);
        var id = ids.splice(i, 1);
        console.log(id + ' is being removed from the list.');
        idSocketMap[id] = undefined;
    });

    socket.on('loginRequest', function(data) {
        var s = idSocketMap[data.id]; // used to see if this id is in use by a socket.
        console.log(idSocketMap.length);

        if (s === undefined) { //if the id is not in use
            sockets.push(socket); //add the socket using the id to a list
            ids.push(data.id);  // add the id (same index as socket)
            idSocketMap[data.id] = socket;  //  map the socket to the id
            console.log('Log in accepted: ' + data.id);
            socket.emit('loginAccepted', data);
        }
        else {
            console.log('Log in rejected: ' + data.id);
            socket.emit('loginRejected', data);
        }
    });

    socket.on('msg', function (data) { //testing socket communication
        console.log(data);
    });

    socket.on('testmessage', function (data) {
        console.log(data);
        idSocketMap[data.id] = socket;
        db.createRecordIfNoneExistent(data.id);
        //javaServerSocket.write('Testing, attention please ' + data.id.toString() + '\n');
    });

    socket.on('action', function (data) {
        console.log('Action received');
        if (data.action === 'bet') {
            //javaServerSocket.write(data.stage, data.cardOne, data.cardTwo, data.boardCards, data.minBet, data.stackSize)
//            socket.emit('cfr', {action: 'call', amount: data.amount}); //call/raise/fold on a bet
        }
        if (data.action === 'check') {
  //          socket.emit('fcb', { action: 'bet', amount: 0 }); //call/bet/fold on a check
        }
        if (data.action === 'raise') {
    //        socket.emit('cfr', { action: 'call', amount: 0 }); //call/bet/fold on a check
        }
        if (data.action === 'call') {
            console.log('log : ' + JSON.stringify(data));
        }
        sendActionToAIServer(data);
    });
    socket.on('playerFold', function(data) {
        db.saveIntoDBPlayerFold(data.id, data.round);
        console.log('PLAYER HAS FOLDED');
    });

    socket.on('call', function (data) {
        socket.emit('fcb', {action: 'check', amount: 0});
    });
    socket.on('newaction', function (data) {
        if (data.action === 'check') {
            socket.emit('fcb', {action: 'check', amount: data.amount});
        }
    });

    socket.on('startgame', function (data) {
        //receive the card info.
        socket.emit('cfr', {action: 'call', amount: 0}); //start game will be call raise or fold on the - big blind
    });

    socket.on('evaluate hands', function (data) {
        var playerHandRank = PokerEvaluator.evalHand(data.communityCards.concat(data.playerCards));
        var aiHandRank = PokerEvaluator.evalHand(data.communityCards.concat(data.aiCards));
        console.log('community cards: ', data.communityCards);
        console.log('player cards: ', data.playerCards);
        console.log('AI cards: ', data.aiCards);
        console.log(aiHandRank);
        console.log(playerHandRank);
        var returnData = {};
        if (playerHandRank.handType > aiHandRank.handType ||
           (playerHandRank.handType === aiHandRank.handType
            && playerHandRank.handRank > aiHandRank.handRank)) {
            console.log('Win for the Player'); //inform AI about this.
            db.saveIntoDBPlayerHandWin(data.id);
            socket.emit('playerwin', playerHandRank);
        }
        else if (playerHandRank.handType < aiHandRank.handType ||
                (playerHandRank.handType === aiHandRank.handType
                 && playerHandRank.handRank < aiHandRank.handRank)) {
            console.log('Win for the AI!!!!');
            socket.emit('aiwin', aiHandRank);
            db.saveIntoDBPlayerLossAtShowdown(data.id);
        } else { //its a draw
            console.log('Its A draw. What are the odds?');
            socket.emit('playeraidraw', playerHandRank);
            db.saveIntoDBPlayerLossAtShowdown(data.id);
        }
    });
});

var i = 0;


javaServerSocket.on('data', function(data) {
    console.log('Data: ' + data.toString());
    var socketID = data.toString().trim().split(' ')[0].trim();
    var keyToSocketList = socketID.toString().substring(2, socketID.toString().length).toString();
    console.log(keyToSocketList);
    var action = data.toString().trim().split(' ')[1].trim();
    idSocketMap[keyToSocketList].emit('AIAction', {action: action});
});


function sendActionToAIServer(data) {
    var modelString = db.retrieveModelFromDB(data.id);
    var action = data.action;
    var amount = data.action | 0;
    var round = data.round;
    var cardOne = data.cardOne.evalValue;
    var cardTwo = data.cardTwo.evalValue;
    var minBet = data.minBet;
    var stackSize = data.stackSize;
    var potSize = data.potSize;
    var boardCards = '';
    for (var i  = 0; i < data.boardCards.length; i++) {
        boardCards += (data.boardCards[i].evalValue + ' ');
    }
    var totalString = action + ' ' +  amount + ' ' + round + ' ' + cardOne + ' ' +
                        cardTwo + ' ' + minBet + ' ' + stackSize + ' ' + potSize +  ' ' + data.id +  ' ' + boardCards
                        + ': ' +  modelString + '\n';
    console.log(totalString);
    javaServerSocket.write(totalString);
}


server.listen(3000);
console.log('Listening on port 3000:');
