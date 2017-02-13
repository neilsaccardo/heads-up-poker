function TableController($scope, cards, socket) {
    var ctrl = this;
    ctrl.player = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    ctrl.aiplayer = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    ctrl.communityCards = []; //empty
    ctrl.isPlayerDealer = true;
    ctrl.playerStackSize = ctrl.stackSize | 4000;
    ctrl.aiStackSize = ctrl.stackSize | 4000;
    ctrl.potSize = 0;
    ctrl.bigBlindAmount = 100;
    ctrl.isPlayerTurn = ctrl.isPlayerDealer;
    var deckPointer = 0;
    var deck = cards.createDeck();
    var cardtest= cards.getCardValue(deck[0]);
    var pokerStage = 0;

    console.log(cardtest);

    ctrl.newGame = function() {
        ctrl.isPlayerDealer = !ctrl.isPlayerDealer;
        ctrl.isPlayerTurn = !ctrl.isPlayerDealer;
        ctrl.checkBetOptions = ctrl.isPlayerTurn;
        pokerStage = 0;
        ctrl.potSize = 0;
        ctrl.communityCards = [];
        ctrl.dealOutCards();
        ctrl.blinds();
        ctrl.continueGame();
    }

    ctrl.bet = function() {
        console.log('betting');
        var obj = {action: 'bet', amount: 100} ;
        socket.emit('action', obj);
        //send event to server saying opponent has bet.
    }

    socket.on('cfr', function(data) {
        console.log(data);
        if (data.action === 'call') {
            ctrl.addToPotAI(data.amount);
            incrementStage();
        }
        else if (data.action === 'fold') {
            ctrl.addPotToStackPlayer();
            socket.emit('fold', 'dummy data for the moment');
            ctrl.newGame();
        }
        else { //data.action === 'raise'
            ctrl.addToPotAI(data.amount);
            ctrl.checkBetOptions = false;
            ctrl.isPlayerTurn = true;
        }
    });

    socket.on('fcb', function(data) {
        console.log(data);
        if (data.action === 'check') {
            ctrl.addToPotAI(data.amount);
            ctrl.checkBetOptions = true;
            ctrl.isPlayerTurn = true;
            incrementStage();
        }
        else if (data.action === 'bet') {
            ctrl.addToPotAI(data.amount);
            ctrl.checkBetOptions = false;
            ctrl.isPlayerTurn = true;
        }
        else { //data.action === 'fold'
            ctrl.addPotToStackPlayer();
            socket.emit('fold', 'dummy data for the moment');
            ctrl.newGame();
        }
    });

    ctrl.fold = function() {
        //send event to server saying opponent lost.
        ctrl.addPotToStackAI();
        ctrl.newGame();
    }

    ctrl.continueGame = function() {
        if(ctrl.isPlayerTurn) {
            //wait for them to complete an action.
        } else {
            //send an event for ai to play.
        }
    }

    ctrl.dealOutCards = function() {
        deckPointer = 0;
        cards.shuffle(deck);
        ctrl.player.cardOne = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
        ctrl.player.cardTwo = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
        ctrl.aiplayer.cardOne = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
        ctrl.aiplayer.cardTwo = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
    }

    ctrl.flop = function() {
        ctrl.communityCards.push(cards.getCardValue(deck[deckPointer]));
        deckPointer++;
        ctrl.communityCards.push(cards.getCardValue(deck[deckPointer]));
        deckPointer++;
        ctrl.communityCards.push(cards.getCardValue(deck[deckPointer]));
        deckPointer++;
    }
    ctrl.turn = function() {
        ctrl.communityCards.push(cards.getCardValue(deck[deckPointer]));
        deckPointer++;
    }
    ctrl.river = function() {
        ctrl.communityCards.push(cards.getCardValue(deck[deckPointer]));
        deckPointer++;
    }

    ctrl.addToPotAI = function(betAmount) {
        if(ctrl.aiStackSize - betAmount < 0) {
            return false;
        } else {
            ctrl.aiStackSize -= betAmount;
            ctrl.potSize += betAmount;
            return true;
        }
    }

    ctrl.addToPotPlayer = function(betAmount) {
        if(ctrl.playerStackSize - betAmount < 0) {
            return false;
        } else {
            ctrl.playerStackSize -= betAmount;
            ctrl.potSize += betAmount;
            return true;
        }
    }

    ctrl.addPotToStackPlayer = function() {
        ctrl.playerStackSize += ctrl.potSize;
    }

    ctrl.addPotToStackAI = function() {
        ctrl.aiStackSize += ctrl.potSize;
    }
    ctrl.blinds = function() {
        if(ctrl.isPlayerDealer) {
            ctrl.addToPotAI(ctrl.bigBlindAmount/2);
            ctrl.addToPotPlayer(ctrl.bigBlindAmount);
        } else {
            ctrl.addToPotPlayer(ctrl.bigBlindAmount);
            ctrl.addToPotAI(ctrl.bigBlindAmount/2);
        }
    }


    function incrementStage() {
        if (pokerStage === 0) {
            ctrl.flop();
        } else if (pokerStage === 1) {
            ctrl.turn();
        } else if(pokerStage === 2){
            ctrl.river();
        }
        pokerStage++;
    }

    ctrl.newGame();
//    ctrl.flop();
//    ctrl.turn();
//    ctrl.river();
};

angular.module('poker-table', ['player', 'ai-player', 'community-cards', 'pot', 'cardsService', 'socketService']).component('pokerTable', {
    templateUrl: 'js/table.html',
    controller: TableController,
    bindings: {
        stackSize: '<'
    }
});