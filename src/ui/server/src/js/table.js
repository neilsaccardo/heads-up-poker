function TableController($scope, cards, socket, $timeout, message, amountService, actions) {
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
    var pokerStage = 0;
    //method to start a new hand
    ctrl.newHand = function() {
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
        console.log('ctrl.username == ' + ctrl.username);
        socket.emit('testmessage', {id: ctrl.username});
    }
    //methods to receive actions from server
    socket.on('cfr', function(data) {
        console.log(data);
        if (data.action === actions.getCallString()) {
            console.log('AI HAS CALLED');
            ctrl.addToPotAI(data.amount);
            ctrl.checkBetOptions = true;
            incrementStage();
        }
        else if (data.action === actions.getFoldString()) {
            console.log('AI HAS FOLDED');
            ctrl.addPotToStackPlayer(ctrl.potSize);
            socket.emit('fold', 'dummy data for the moment');
            ctrl.newHand();
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
        if (data.action === actions.getCheckString()) {
            console.log('AI HAS CHECKED');
            ctrl.addToPotAI(data.amount);
            if (ctrl.isPlayerDealer) {
                ctrl.checkBetOptions = true;
            }
//            ctrl.checkBetOptions = true;
            ctrl.isPlayerTurn = true;
        }
        else if (data.action === actions.getBetString()) {
            console.log('AI HAS BET');
            ctrl.addToPotAI(data.amount);
            ctrl.checkBetOptions = false;
//            ctrl.isPlayerTurn = true;
        }
        else { //data.action === 'fold'
            console.log('AI HAS FOLDED');
            ctrl.addPotToStackPlayer(ctrl.potSize);
            socket.emit(actions.getFoldString(), 'dummy data for the moment');
            ctrl.newHand();
        }
    });

    //methods relating to the buttons on the UI
    ctrl.bet = function() {
        if (!amountService.checkBetRaiseAmount(ctrl.raiseBetAmount, ctrl.playerStackSize, ctrl.bigBlindAmount)) {
            return;
        };
        console.log('betting');
        var obj =  {action: actions.getBetString(), amount: ctrl.raiseBetAmount, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        socket.emit('action', obj);
        //send event to server saying opponent has bet.
    }

    ctrl.fold = function() {
        //send event to server saying opponent lost.
        ctrl.addPotToStackAI(ctrl.potSize);
        console.log('I HAVE FOLDED');
        ctrl.newHand();
    }

    ctrl.raise = function() {
        if (!amountService.checkBetRaiseAmount(ctrl.raiseBetAmount, ctrl.playerStackSize, ctrl.bigBlindAmount)) {
            return;
        };
        console.log('I HAVE RAISED');
        var obj =  {action: actions.getRaiseString(), amount: ctrl.raiseBetAmount, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        socket.emit('action', obj);
    }

    ctrl.check = function() {
        console.log('I HAVE CHECKED');
        var obj =  {action: actions.getCheckString(), amount: 0, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        if(ctrl.isPlayerDealer) {
            incrementStage();
            ctrl.isPlayerTurn = ctrl.isPlayerDealer;
            socket.emit('newaction', obj);
        } else {
            socket.emit('action', obj);
        }
    }

    ctrl.call = function() {
        console.log('I HAVE CALLED');
        ctrl.addToPotPlayer(10);
        incrementStage();
        ctrl.isPlayerTurn = !ctrl.isPlayerDealer;
        var obj =  {action: actions.getCallString(), amount: 0, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};

        socket.emit('action', obj);//inform server that the player has called.
        ctrl.checkBetOptions = true;
//        if(ctrl.isPlayerDealer) {
//            socket.emit('call', {action: 'call', amount: 0});
//        }
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

    // methods relating to adding/removing from pot and stacks.
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

    ctrl.addPotToStackPlayer = function(val) {
        ctrl.playerStackSize += val;
    }

    ctrl.addPotToStackAI = function(val) {
        ctrl.aiStackSize += val;
    }

    ctrl.blinds = function() {
        if(ctrl.isPlayerDealer) {
            ctrl.addToPotAI(ctrl.bigBlindAmount/2); //player puts small blind
            ctrl.addToPotPlayer(ctrl.bigBlindAmount);
        } else {
            ctrl.addToPotPlayer(ctrl.bigBlindAmount); //player puts big blind
            ctrl.addToPotAI(ctrl.bigBlindAmount/2);
        }
    }

    function incrementStage() {
        pokerStage++;
        if (pokerStage === 1) {
            console.log('FLOP');
            ctrl.flop();
        } else if (pokerStage === 2) {
            console.log('TURN')
            ctrl.turn();
        } else if (pokerStage === 3){
            console.log('RIVER')
            ctrl.river();
        } else if (pokerStage === 4) {
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
                                            communityCards: communityCardsEvalValues, id: ctrl.id});
        }
    }

    //socket on events relating to player win, loss or draw of hand
    socket.on('playerwin', function(data) {
        console.log('The player has won the hand!! ', data.handName);
        $timeout(function () {
                    ctrl.addPotToStackPlayer(ctrl.potSize);
                    ctrl.newHand();
                }, 2000);
    });

    socket.on('aiwin', function(data) {
        console.log('The AI has won the hand!! ', data.handName);
        $timeout(function () {
                    ctrl.addPotToStackAI();
                    ctrl.newHand();
                }, 2000);
    });

    socket.on('playeraidraw', function(data) {
        console.log('There was a draw this hand ', data.handName);
        $timeout(function () {
            ctrl.addPotToStackAI(ctrl.potSize/2);
            ctrl.addPotToStackPlayer(ctrl.potSize/2);
            ctrl.newHand();
        }, 2000);
    });

    socket.on('PrintToConsole', function(data) {
        console.log(data);
    });

    socket.on('AIAction', function(data) {
        console.log('Ai action is ' + data.action);

        if (data.action.toLowerCase() === actions.getFoldString()) {
            ctrl.aiFold();
        }
        else if (data.action.toLowerCase() === actions.getBet1String()) {
            var numChips = ((ctrl.potSize / 4) * 3) * 1;
            numChips = (numChips < minBet) ? minBet : numChips;
            ctrl.aiBet(10);
        }
        else if (data.action.toLowerCase() === actions.getBet2String()) {
            var numChips = ((ctrl.potSize / 4) * 3) * 2;
            numChips = (numChips < minBet) ? minBet : numChips;
            ctrl.aiBet(numChips);
        }
        else if (data.action.toLowerCase() === actions.getBet3String()) {
            var numChips = ((ctrl.potSize / 4) * 3) * 3;
            numChips = (numChips < minBet) ? minBet : numChips;
            ctrl.aiBet(30);
        }
        else if (data.action.toLowerCase() === actions.getRaise1String) {
            var numChips = ((ctrl.potSize / 4) * 3) * 1;
            numChips = (numChips < minBet) ? minBet : numChips;
            ctrl.aiRaise(10);
        }
        else if (data.action.toLowerCase() === actions.getRaise2String) {
            var numChips = ((ctrl.potSize / 4) * 3) * 2;
            numChips = (numChips < minBet) ? minBet : numChips;
            ctrl.aiRaise(20);
        }
        else if (data.action.toLowerCase() === actions.getRaise3String) {
            var numChips = ((ctrl.potSize / 4) * 3) * 3;
            numChips = (numChips < minBet) ? minBet : numChips;
            ctrl.aiRaise(numChips);
        }
        else if (data.action.toLowerCase() === actions.getCallString()) {
            ctrl.aiCall();
        }
        else if (data.action.toLowerCase() === actions.getCheckString()) {
            ctrl.aiCheck();
        }
        else if (data.action.toLowerCase() === actions.getAllInString()) {
            ctrl.aiBet(ctrl.aiStackSize);
        }
    });

    ctrl.aiFold = function() {
        console.log('The AI has folded. PLayer wins the Hand')
        $timeout(function () {
                    ctrl.addPotToStackPlayer(ctrl.potSize);
                    ctrl.newHand();
                }, 2000);
        ctrl.newHand();
    }

    ctrl.aiCall = function() {
        console.log('AI has checked');
        incrementStage();
    }

    ctrl.aiCheck = function() {
        console.log('AI has checked');
        ctrl.checkBetOptions = true;
        ctrl.isPlayerTurn = true;
        if(!ctrl.isPlayerDealer) {
            incrementStage();
            ctrl.isPlayerTurn = ctrl.isPlayerDealer;
        } else {
            socket.emit('action', obj);
        }
    }

    ctrl.aiBet = function(numChips) {
        console.log('AI has bet ' + numChips);
        ctrl.addToPotAI(numChips);
        ctrl.checkBetOptions = false;
        ctrl.isPlayerTurn = true;
    }

    ctrl.aiRaise = function(numChips) {
        console.log('AI has raised ' + numChips);
        ctrl.addToPotAI(ctrl.raiseBetAmount + numChips);
        ctrl.checkBetOptions = false;
        ctrl.isPlayerTurn = true;
    }

    // methods relating to distribution of cards
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

    ctrl.newHand(); //start a game.
};

angular.module('poker-table', ['player', 'ai-player', 'community-cards', 'pot', 'cardsService', 'socketService'
                                , 'messageService', 'amountService', 'actionsService'])
                                .component('pokerTable', {
    templateUrl: 'js/table.html',
    controller: TableController,
    bindings: {
        username: '@',
        stackSize: '<'
    }
});