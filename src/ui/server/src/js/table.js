function TableController($scope, cards) {
    var ctrl = this;
    ctrl.player = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    ctrl.aiplayer = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    ctrl.communityCards = []; //empty
    ctrl.isDealer = false;
    var deckPointer = 0;
    var deck = cards.createDeck();
    var cardtest= cards.getCardValue(deck[0]);
    console.log(cardtest);



    ctrl.newGame = function() {
        ctrl.isDealer = !ctrl.isDealer;
        ctrl.communityCards = [];
        ctrl.dealOutCards();
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

    ctrl.dealOutCards();
    ctrl.flop();
    ctrl.turn();
    ctrl.river();
};

angular.module('poker-table', ['player', 'ai-player', 'community-cards', 'pot', 'cardsService']).component('pokerTable', {
    templateUrl: 'js/table.html',
    controller: TableController,
    bindings: {

    }
});