var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/test');

var playerSchema = mongoose.Schema({
                                    name: String,
                                    numHands: Number,
                                    numFoldsPreFlop: Number,
                                    numFoldsFlop: Number,
                                    numFoldsTurn: Number,
                                    numFoldsRiver: Number});

var player = mongoose.model('Player', playerSchema);

var db = {
    saveIntoDB: function (playerName ) {
        console.log('CALLING saveIntoDB');
        console.log(query);
    },
    retrieveFromDB: function(playerName) {
        console.log('CAllling LoadIntoDB');
        var query = player.findOne({'name': playerName});
    }
}

module.exports = db;