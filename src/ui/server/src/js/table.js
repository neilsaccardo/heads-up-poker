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
            console.log('AI HAS CALLED');
            ctrl.addToPotAI(data.amount);
            incrementStage();
        }
        else if (data.action === 'fold') {
            console.log('AI HAS FOLDED');
            ctrl.addPotToStackPlayer();
            socket.emit('fold', 'dummy data for the moment');
            ctrl.newGame();
        }
        else { //data.action === 'raise'
            console.log('AI HAS RAISED');
            ctrl.addToPotAI(data.amount);
            ctrl.checkBetOptions = false;
            ctrl.isPlayerTurn = true;
        }
    });

    socket.on('fcb', function(data) {
        console.log(data);
        if (data.action === 'check') {
            console.log('AI HAS CHECKED');
            ctrl.addToPotAI(data.amount);
            if (ctrl.isPlayerDealer) {
                ctrl.checkBetOptions = true;
            }
//            ctrl.checkBetOptions = true;
            ctrl.isPlayerTurn = true;
        }
        else if (data.action === 'bet') {
            console.log('AI HAS BET');
            ctrl.addToPotAI(data.amount);
            ctrl.checkBetOptions = false;
            ctrl.isPlayerTurn = true;
        }
        else { //data.action === 'fold'
            console.log('AI HAS FOLDED');
            ctrl.addPotToStackPlayer();
            socket.emit('fold', 'dummy data for the moment');
            ctrl.newGame();
        }
    });

    ctrl.fold = function() {
        //send event to server saying opponent lost.
        ctrl.addPotToStackAI();
        console.log('I HAVE FOLDED');
        ctrl.newGame();
        console.log('NEW GAME');
    }

    ctrl.check = function() {
        console.log('I HAVE CHECKED');
        if(ctrl.isPlayerDealer) {
            incrementStage();
            ctrl.isPlayerTurn = ctrl.isPlayerDealer;
            socket.emit('newaction', {action: 'check', amount: 0});
        } else {
            socket.emit('action', {action: 'check', amount: 0});
        }
    }

    ctrl.call = function() {
        console.log('I HAVE CALLED');
        ctrl.addToPotPlayer(10);
        incrementStage();
        ctrl.isPlayerTurn = ctrl.isPlayerDealer;
    }
    ctrl.continueGame = function() {
        if(ctrl.isPlayerTurn) {
            //wait for them to complete an action.
        } else {
            console.log('WE are here');
            socket.emit('startgame', {data: 'sample'});
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
            console.log('FLOP');
            ctrl.flop();
        } else if (pokerStage === 1) {
            console.log('TURN')
            ctrl.turn();
        } else if(pokerStage === 2){
            console.log('RIVER')
            ctrl.river();
        }
//        ctrl.checkBetOptions = !ctrl.isPlayerDealer;
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