function TableController($scope, cards, socket, $timeout, message, amountService, actions) {
    var ctrl = this;

    ctrl.player = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}};
    ctrl.aiplayer = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}};
    ctrl.hideAICards = true;
    ctrl.communityCards = []; //empty

    var bigBlindCalled = false;
    var deckPointer = 0;
    var deck = cards.createDeck();
    var pokerStage = 0;

    //method to initiate new game. called with the button.
    ctrl.newGame = function() {
        ctrl.showNewGameButton = false;
        ctrl.isPlayerDealer = false;
        ctrl.showNewHandButton = false;
        ctrl.goToShowDown = false;
        ctrl.playerStackSize = ctrl.stackSize | 5000;
        ctrl.aiStackSize = ctrl.stackSize | 5000;
        ctrl.potSize = 0;
        ctrl.aiToPot = 0;
        ctrl.playerToPot = 0;
        ctrl.betIncreaseEveryNumHands = ctrl.handsIncrease | 10;
        ctrl.handsLeftTillBetIncrease = ctrl.betIncreaseEveryNumHands;
        ctrl.bigBlindAmount = ctrl.minimumBet | 100;
        ctrl.isPlayerTurn = ctrl.isPlayerDealer;
        ctrl.newHand();
    }
    //method to start a new hand
    ctrl.newHand = function() {
        ctrl.goToShowDown = false;
        ctrl.handsLeftTillBetIncrease = ctrl.handsLeftTillBetIncrease - 1;
        if (ctrl.handsLeftTillBetIncrease === 0) {
            ctrl.bigBlindAmount = ctrl.bigBlindAmount + ctrl.bigBlindAmount;
            ctrl.handsLeftTillBetIncrease = ctrl.betIncreaseEveryNumHands;
        }
        ctrl.showNewHandButton = false;
        console.log('NEW GAME');
        ctrl.isPlayerDealer = !ctrl.isPlayerDealer;
        ctrl.isPlayerTurn = false;
        ctrl.checkBetOptions = !ctrl.isPlayerTurn;
        ctrl.hideAICards = false;
        pokerStage = 0;
        ctrl.potSize = 0;
        ctrl.aiToPot = 0;
        ctrl.playerToPot = 0;
        ctrl.communityCards = [];
        ctrl.dealOutCards();
        ctrl.blinds();
        bigBlindCalled = false;
        ctrl.message = 'New hand!';
        $timeout(function () {
            ctrl.startHand();
        }, 2000);
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
        if (!amountService.checkBetRaiseAmount(parseInt(ctrl.raiseBetAmount), ctrl.playerStackSize, ctrl.bigBlindAmount)) {
            return;
        };
        if (!amountService.checkRaiseOverOtherPlayerStack(parseInt(ctrl.raiseBetAmount), ctrl.aiStackSize)) {
            return;
        }
        console.log('betting');
        ctrl.addToPotPlayer(parseInt(ctrl.raiseBetAmount));
        var obj =  {action: actions.getBetString(), amount: ctrl.raiseBetAmount, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        ctrl.isPlayerTurn = false;
        if (ctrl.playerStackSize < ctrl.bigBlindAmount || (ctrl.aiStackSize - parseInt(ctrl.raiseBetAmount)) < ctrl.bigBlindAmount) {
            ctrl.goToShowDown = true;
        }
        socket.emit('action', obj);
        //send event to server saying opponent has bet.
    }

    ctrl.fold = function() {
        //send event to server saying opponent lost.
        ctrl.addPotToStackAI(ctrl.potSize);
        console.log('I HAVE FOLDED');
        var obj = {id: ctrl.username, round: pokerStage}
        socket.emit('playerFold', obj);
        ctrl.newHand();
    }

    ctrl.raise = function() {
        console.log(amountToCall() + '@@@');
        var amount = amountToCall();
        if (!amountService.checkBetRaiseAmount(parseInt(ctrl.raiseBetAmount) + amount, ctrl.playerStackSize, ctrl.bigBlindAmount)) {
            return;
        };
        if (!amountService.checkRaiseOverOtherPlayerStack(parseInt(ctrl.raiseBetAmount), ctrl.aiStackSize)) {
            return;
        }
        console.log('I HAVE RAISED');
        console.log('By ' + ctrl.raiseBetAmount);
        var obj =  {action: actions.getRaiseString(), amount: ctrl.raiseBetAmount, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        ctrl.addToPotPlayer(parseInt(ctrl.raiseBetAmount) + amount);
        ctrl.isPlayerTurn = false;
        console.log('DDD ' + ((ctrl.aiStackSize - parseInt(ctrl.raiseBetAmount))));
        console.log('ddddddd1 ' + ((ctrl.aiStackSize - parseInt(ctrl.raiseBetAmount)) < ctrl.bigBlindAmount));
        console.log('ddddddd2 ' + (ctrl.playerStackSize < ctrl.bigBlindAmount));
        if (ctrl.playerStackSize < ctrl.bigBlindAmount || (ctrl.aiStackSize - parseInt(ctrl.raiseBetAmount)) < ctrl.bigBlindAmount) {
            console.log('ALL INS');
            ctrl.goToShowDown = true;
        }
        socket.emit('action', obj);
    }

    ctrl.check = function() {
        console.log('I HAVE CHECKED');
        var obj =  {action: actions.getCheckString(), amount: 0, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        if (pokerStage === 0 && !(ctrl.isPlayerDealer)) { //pre flop, the player is not the dealer (player plays second)
            incrementStage();
            ctrl.continueGame();
        }
        else if (pokerStage > 0 && !(ctrl.isPlayerDealer)) { // post flop, if the player plays first
            ctrl.isPlayerTurn = false;
            socket.emit('action', obj);
        }
        else if (pokerStage > 0 && ctrl.isPlayerDealer) { //  post flop, if the player plays second
            ctrl.isPlayerTurn = false;
            incrementStage();
            ctrl.continueGame();
        }
    }

    ctrl.call = function() {
        console.log('I HAVE CALLED');
        var obj =  {action: actions.getCallString(), amount: 0, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        var amount = amountToCall();
        ctrl.addToPotPlayer(amount);        //TODO: put the real number here
        if (pokerStage === 0 && ctrl.isPlayerDealer && !(bigBlindCalled)) { // player calls the big blind amount
            ctrl.isPlayerTurn = false;
            bigBlindCalled = true;
            socket.emit('action', obj);
        }
        else if (pokerStage === 0 && !ctrl.isPlayerDealer && !bigBlindCalled) { //player has raised on the blind.
            bigBlindAmount = true;
            ctrl.isPlayerTurn = false;
            incrementStage();
            ctrl.continueGame();
        }
        else if ((pokerStage > 0) || bigBlindCalled) {   //if the big blind has been called already.
            ctrl.isPlayerTurn = false;
            incrementStage();
            ctrl.continueGame();
        }
    }

    ctrl.startHand = function() {
        console.log('Starting Hand. ');
        if (checkWinner()) {
            console.log('CHECK WINNER SUCCEDED');
            ctrl.message = message.getWinnerMessage(ctrl.playerStackSize, ctrl.aiStackSize);
            ctrl.showNewGameButton = true;
            return;
        }

        if (pokerStage === 0 && ctrl.isPlayerDealer) { //if the player is the dealer at the start of a game
            ctrl.isPlayerTurn = true;             // then they must start - its their turn
            ctrl.checkBetOptions = false;
        }
        else if (pokerStage === 0 && !(ctrl.isPlayerDealer)) { //else if AI is dealer
            ctrl.isPlayerTurn = false; //than AI must start
            var obj =  {action: actions.getCheckString(), amount: 0, round: pokerStage,
                            cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                            boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                            potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
            socket.emit('action', obj);
        }
    }

    ctrl.continueGame = function() { // Only called when stage is flop, turn, river
//        if (checkWinner() && pokerStage !== 4) {
//            console.log('CHECK WINNER SUCCEDED ');
//            ctrl.message = message.getWinnerMessage(ctrl.playerStackSize, ctrl.aiStackSize);
//            ctrl.showNewGameButton = true;
//            return;
//        }
        if (pokerStage === 4) { //SHOWDOWN. do nothing.
            return;
        }
        if (ctrl.isPlayerDealer) { // if player is dealer, they will act second. AI will act first
            ctrl.isPlayerTurn = false;
            var obj =  {action: actions.getCallString(), amount: 0, round: pokerStage,
                            cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                            boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                            potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
            socket.emit('action', obj);
        }
        else { // else they will act first - they will have option to check/bet
            ctrl.isPlayerTurn = true;
            ctrl.checkBetOptions = true;
        }
    }

    // methods relating to adding/removing from pot and stacks.
    ctrl.addToPotAI = function(betAmount) {
        if(ctrl.aiStackSize - betAmount < 0) {
            return false;
        } else {
            ctrl.aiToPot += betAmount;
            ctrl.aiStackSize -= betAmount;
            ctrl.potSize += betAmount;
            return true;
        }
    }

    ctrl.addToPotPlayer = function(betAmount) {
        if(ctrl.playerStackSize - betAmount < 0) {
            return false;
        }
        else {
            ctrl.playerToPot += betAmount;
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
        if(ctrl.isPlayerDealer) { // the dealer should post the small blind
            ctrl.addToPotAI(ctrl.bigBlindAmount);         // AI is not the dealer, they post the big blind
            ctrl.addToPotPlayer(ctrl.bigBlindAmount / 2); // player is the dealer, they post the small blind
        } else {
            ctrl.addToPotAI(ctrl.bigBlindAmount / 2);  // AI is the dealer, they post the small blind
            ctrl.addToPotPlayer(ctrl.bigBlindAmount);  // player is not the dealer, they post the big blind
        }
    }

    function amountToCall() {
        if (ctrl.aiToPot > ctrl.playerToPot) { // get the difference between two pots
            return ctrl.aiToPot - ctrl.playerToPot;
        }
        else {
            return ctrl.playerToPot - ctrl.aiToPot;
        }

    }

    function bringPokerStageToShowDown() {
        if (pokerStage === 1) {
            ctrl.flop();
            pokerStage++;
        }
        if (pokerStage === 2) {
            ctrl.turn();
            pokerStage++;
        }
        if (pokerStage === 3) {
            ctrl.river();
            pokerStage++;
        }
    }
    function incrementStage() {
        pokerStage++;

        if (ctrl.goToShowDown) {
            console.log('GO TO SHOWDOWN');
            bringPokerStageToShowDown();
        }
        if (pokerStage === 1) { // flop
            console.log('FLOP');
            ctrl.flop();
        } else if (pokerStage === 2) {  //turn
            console.log('TURN')
            ctrl.turn();
        } else if (pokerStage === 3){ //river
            console.log('RIVER')
            ctrl.river();
        } else if (pokerStage === 4) {  //showdown
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
                                            communityCards: communityCardsEvalValues, id: ctrl.username});
        }
    }

    function checkWinner() {
        console.log((ctrl.playerStackSize < ctrl.bigBlindAmount));
        console.log((ctrl.aiStackSize < ctrl.bigBlindAmount));
        return (ctrl.playerStackSize < ctrl.bigBlindAmount) || (ctrl.aiStackSize < ctrl.bigBlindAmount);
    }

    //socket on events relating to player win, loss or draw of hand
    socket.on('playerwin', function(data) {
        console.log('The player has won the hand!! ', data.handName);
        ctrl.message = "You've won the hand. : " + data.handName;
        ctrl.showNewHandButton = true;
        ctrl.addPotToStackPlayer(ctrl.potSize);
        ctrl.isPlayerTurn = false;
    });

    socket.on('aiwin', function(data) {
        console.log('The AI has won the hand!! ', data.handName);
        ctrl.message = "The AI has won the hand. : " + data.handName;
        ctrl.showNewHandButton = true;
        ctrl.isPlayerTurn = false;
        ctrl.addPotToStackAI(ctrl.potSize);

    });

    socket.on('playeraidraw', function(data) {
        console.log('There was a draw this hand ', data.handName);
        ctrl.message = "Draw this hand : " + data.handName;
//        $timeout(function () {
        ctrl.showNewHandButton = true;
        ctrl.addPotToStackAI(ctrl.potSize/2);
        ctrl.addPotToStackPlayer(ctrl.potSize/2);
        ctrl.isPlayerTurn = false;
  //          ctrl.newHand();
  //    }, 5000);
    });

    socket.on('PrintToConsole', function(data) {
        console.log(data);
    });

    socket.on('AIAction', function(data) {
        var minBet = ctrl.bigBlindAmount;
        console.log('Ai action is ' + data.action);
        if (data.action.toLowerCase() === actions.getFoldString()) {
            ctrl.aiFold();
        }
        else if (ctrl.goToShowDown) {
            console.log('CALL ALL IN');
            ctrl.aiCall();
        }
        else if (data.action.toLowerCase() === actions.getBet1String()) {
            var numChips = amountService.getBetRaiseAmount(ctrl.potSize, 1);
            numChips = (ctrl.aiStackSize < numChips) ? minBet : numChips;
            ctrl.aiBet(numChips);
        }
        else if (data.action.toLowerCase() === actions.getBet2String()) {
            var numChips = amountService.getBetRaiseAmount(ctrl.potSize, 2);
            numChips = (ctrl.aiStackSize < numChips) ? minBet : numChips;
            ctrl.aiBet(numChips);
        }
        else if (data.action.toLowerCase() === actions.getBet3String()) {
            var numChips = amountService.getBetRaiseAmount(ctrl.potSize, 3);
            numChips = (ctrl.aiStackSize < numChips) ? minBet : numChips;
            ctrl.aiBet(numChips);
        }
        else if (data.action.toLowerCase() === actions.getRaise1String()) {
            var numChips = amountService.getBetRaiseAmount(ctrl.potSize, 1);
            numChips = (ctrl.aiStackSize < numChips) ? minBet : numChips;
            ctrl.aiRaise(numChips);
        }
        else if (data.action.toLowerCase() === actions.getRaise2String()) {
            var numChips = amountService.getBetRaiseAmount(ctrl.potSize, 2);
            numChips = (ctrl.aiStackSize < numChips) ? minBet : numChips;
            ctrl.aiRaise(numChips);
        }
        else if (data.action.toLowerCase() === actions.getRaise3String()) {
            var numChips = amountService.getBetRaiseAmount(ctrl.potSize, 3);
            numChips = (ctrl.aiStackSize < numChips) ? minBet : numChips;
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
            ctrl.goToShowDown = true;
        }
    });

    ctrl.aiFold = function() {
        ctrl.message = message.getAIHasFoldedMessage();
        console.log('The AI has folded. PLayer wins the Hand')
        $timeout(function () {
                    ctrl.addPotToStackPlayer(ctrl.potSize);
                    ctrl.newHand();
                }, 3000);
    }

    ctrl.aiCall = function() {
        console.log('AI has called');
        ctrl.message = message.getAIHasCalledMessage();
        var amount = amountToCall();
        ctrl.addToPotAI(amount);
        if (pokerStage === 0 && !(ctrl.isPlayerDealer) && !(bigBlindCalled)) { // AI calls the big blind amount. !(ctrl.isPlayerDealer) is equivalent to ctrl.isAIDealer.
            bigBlindCalled = true;
            ctrl.isPlayerTurn = true;
            ctrl.checkBetOptions = true;
        }
        else if (pokerStage === 0 && ctrl.isPlayerDealer && !bigBlindCalled) { //player has raised on the blind.
            bigBlindAmount = true;
            ctrl.isPlayerTurn = false;
            incrementStage();
            ctrl.continueGame();
        }
        else if ((pokerStage > 0) || bigBlindCalled) {   //if the big blind has been called already.
            console.log('Calling. Go to next stage' );
            ctrl.isPlayerTurn = false;
            incrementStage();
            ctrl.continueGame();
        }
    }

    ctrl.aiCheck = function() {
        console.log('AI has checked');
        ctrl.message = message.getAIHasCheckedMessage();
        var obj =  {action: actions.getCheckString(), amount: 0, round: pokerStage,
                    cardOne: ctrl.aiplayer.cardOne, cardTwo: ctrl.aiplayer.cardTwo,
                    boardCards: ctrl.communityCards, minBet: ctrl.bigBlindAmount,
                    potSize: ctrl.potSize, stackSize: ctrl.aiStackSize, id: ctrl.username};
        if (pokerStage === 0 && (ctrl.isPlayerDealer)) { //pre flop, the AI is not the dealer (AI plays second)
            incrementStage();
            ctrl.continueGame();
        }
        else if (pokerStage > 0 && (ctrl.isPlayerDealer)) { // post flop, if the AI plays first
            ctrl.isPlayerTurn = true;
            ctrl.checkBetOptions = true;
        }
        else if (pokerStage > 0 && ctrl.isPlayerDealer) { //  post flop, if the AI plays second
            ctrl.isPlayerTurn = false;
            incrementStage();
            ctrl.continueGame();
        }
        else if (pokerStage > 0 && (!ctrl.isPlayerDealer)) {
            ctrl.isPlayerTurn = false;
            incrementStage();
            ctrl.continueGame();
        }
    }

    ctrl.aiBet = function(numChips) {
        if (numChips > ctrl.playerStackSize) {
            numChips = ctrl.playerStackSize;
            ctrl.goToShowDown = true;
        }
        ctrl.message = message.getAIHasBetMessage(numChips);
        console.log('AI has bet ' + numChips);
        ctrl.addToPotAI(numChips);
        ctrl.checkBetOptions = false;
        ctrl.isPlayerTurn = true;
    }

    ctrl.aiRaise = function(numChips) {
        if (numChips > ctrl.playerStackSize) {
            numChips = ctrl.playerStackSize;
            ctrl.goToShowDown = true;
        }
        ctrl.message = message.getAIHasRaisedMessage(numChips);
        console.log('Amount to Call : ' + amountToCall());
        console.log('AI has raised ' + numChips);
        ctrl.addToPotAI(numChips + amountToCall());
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

    ctrl.newGame(); // initiate new game
};

angular.module('poker-table', ['player', 'ai-player', 'community-cards', 'pot', 'min-bet','cardsService', 'socketService'
                                , 'messageService', 'amountService', 'actionsService', 'ai-action-message'])
                                .component('pokerTable', {
    templateUrl: 'js/table.html',
    controller: TableController,
    bindings: {
        username: '@',
        stackSize: '<',
        minimumBet: '<',
        handsIncrease: '<'
    }
});