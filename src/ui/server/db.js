var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/testwebapp');

var NUM_HANDS_BEFORE_NOT_DEFAULT = 25;
var playerSchema = mongoose.Schema({
                                        name: String,
                                        numHandsPlayed: Number,
                                        numFoldsPreFlop: Number,
                                        numFoldsFlop: Number,
                                        numFoldsTurn: Number,
                                        numFoldsRiver: Number,
                                        numGamesPlayed: Number,
                                        numHandsWon : Number,
                                        numGamesWon: Number,
                                        totalFolds: Number
                                    });

var Player = mongoose.model('Player', playerSchema);

function createPlayer(playerName) {
    return Player ({
                name: playerName,
                numHandsPlayed: 0,
                numFoldsPreFlop: 0,
                numFoldsFlop: 0,
                numFoldsTurn: 0,
                numFoldsRiver: 0,
                numGamesPlayed: 0,
                numHandsWon : 0,
                numGamesWon: 0,
                totalFolds: 0
            });
}

var db = {
    createRecordIfNoneExistent: function(playerName) {
        Player.findOne({'name': playerName}, function(err, player) {
            if (err) {
                throw err;
            }
            if (player == null) {
                console.log('is this always null?');
                player = createPlayer(playerName);
            }
            player.save(function(err) {
                                       if (err) throw err;
                                       console.log('Player successfully updated!');
                                   });
            console.log(player);
        });
    },
    saveIntoDBPlayerFold: function (playerName, round) {
        Player.findOne({'name': playerName}, function(err, player) {
            if (err) {
                throw err;
            }
            console.log(player);
            if (round === 0) {
                player.numFoldsPreFlop = player.numFoldsPreFlop + 1;
            }
            else if (round === 1) {
                player.numFoldsFlop = player.numFoldsFlop +1;
            }
            else if (round === 2) {
                player.numFoldsTurn = player.numFoldsTurn + 1;
            }
            else if (round === 3) {
                player.numFoldsRiver = player.numFoldsRiver + 1;
            }
            else {
                console.log('ROUND INPUT ERROR');
            }
            console.log(player.totalFolds);
            player.totalFolds = player.totalFolds + 1;
            player.numHandsPlayed = player.numHandsPlayed + 1;
            player.save(function(err) {
                if (err) throw err;
                console.log('Player successfully updated!');
            });
        });
        console.log('CALLED');
    },
    saveIntoDBPlayerHandWin: function (playerName) {
        Player.findOne({'name': playerName}, function(err, player) {
            console.log(playerName);
            if (err) {
                throw err;
            }
            player.numHandsPlayed = player.numHandsPlayed + 1;
            player.numHandsWon = player.numHandsWon + 1;
            player.save(function(err) {
                if (err) throw err;
                console.log('Player successfully updated!');
            });
        });
    },
    saveIntoDBPlayerLossAtShowdown: function(playerName) {
        Player.findOne({'name': playerName}, function(err, player) {
            if (err) {
                throw err;
            }
            player.numHandsPlayed = player.numHandsPlayed + 1;
            player.save(function(err) {
                if (err) throw err;
                console.log('Player successfully updated!');
            });
        });
    },
    retrieveModelFromDB: function(playerName, cb) {
        console.log('CALLING LoadIntoDB');
        Player.findOne({'name': playerName}, function(err, player) {
            if (err) throw err;
            // will need to create a json object with relevant info;
            if (player.numHandsPlayed < NUM_HANDS_BEFORE_NOT_DEFAULT) {
                console.log('default');
                modelString = 'default';
            }
            else {
                var modelString = player.numFoldsPreFlop + ' ' + player.numFoldsFlop + ' ' + player.numFoldsTurn
                                    + ' ' + player.numFoldsRiver + ' ' + player.numHandsPlayed;
                console.log(modelString);
            }
            cb(modelString);
        });
    }
}

module.exports = db;