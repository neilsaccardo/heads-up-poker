function TableController($scope, cards, socket, $timeout) {
    var ctrl = this;
    ctrl.player = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    ctrl.aiplayer = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    ctrl.hideAICards = true;
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
        console.log('NEW GAME');
        ctrl.isPlayerDealer = !ctrl.isPlayerDealer;
        ctrl.isPlayerTurn = !ctrl.isPlayerDealer;
        ctrl.checkBetOptions = !ctrl.isPlayerTurn;
        ctrl.hideAICards = true;
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
            ctrl.checkBetOptions = true;
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
//            ctrl.isPlayerTurn = true;
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
    }

    ctrl.raise = function() {
        console.log('I HAVE RAISED');
        socket.emit('action', {action: 'raise', amount: 20});
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
        ctrl.isPlayerTurn = !ctrl.isPlayerDealer;
        socket.emit();//inform server that the player has called.
        ctrl.checkBetOptions = true;
        if(ctrl.isPlayerDealer) {
            socket.emit('call', {action: 'call', amount: 0});
        }
    }
    ctrl.continueGame = function() {
        if(ctrl.isPlayerDealer) {
            console.log('Player is small blind.');
            ctrl.isPlayerTurn = true;
            ctrl.checkBetOptions = false;
            //wait for them to complete an action.
        } else {
            console.log('AI is small blind.');
            socket.emit('startgame', {data: 'sample'});
            //send an event for ai to start the hand.
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
            ctrl.addToPotPlayer(ctrl.bigBlindAmount);  //player puts small blind
        } else {
            ctrl.addToPotPlayer(ctrl.bigBlindAmount); //player puts big blind
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
        } else if (pokerStage === 2){
            console.log('RIVER')
            ctrl.river();
        } else if (pokerStage === 3) {
            //showdown (show ai hand). need to evaluate hands.
            console.log('SHOWDOWN');
            ctrl.hideAICards = false;
            ctrl.isPlayerTurn = false;
            var communityCardsEvalValues = [];
            for (var i = 0; i < ctrl.communityCards.length; i++) {
                communityCardsEvalValues.push(ctrl.communityCards[i].evalValue);
            }
            socket.emit('evaluate hands', { playerCards: [ctrl.player.cardOne.evalValue, ctrl.player.cardTwo.evalValue],
                                            aiCards: [ctrl.aiplayer.cardOne.evalValue, ctrl.aiplayer.cardTwo.evalValue],
                                            communityCards: communityCardsEvalValues});
        }
//        ctrl.checkBetOptions = !ctrl.isPlayerDealer;
        pokerStage++;
    }

    socket.on('playerwin', function(data) {
        console.log('The player has won the hand!! ', data.handName);
        $timeout(function () {
                    ctrl.newGame();
                }, 2000);
    });

    socket.on('aiwin', function(data) {
        console.log('The AI has won the hand!! ', data.handName);
        $timeout(function () {
                    ctrl.newGame();
                }, 2000);
    });

    socket.on('playeraidraw', function(data) {
        console.log('There was a draw this hand ', data.handName);
        $timeout(function () {
            ctrl.newGame();
        }, 2000);
    });

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