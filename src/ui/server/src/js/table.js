function TableController($scope, cards) {
    var ctrl = this;
    ctrl.player = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    ctrl.aiplayer = {cardOne: {suit: 'hearts', value: '2'}, cardTwo: {suit: 'hearts', value: '2'}}
    var deckPointer = 0;
    var deck = cards.createDeck();
    var cardtest= cards.getCardValue(deck[0]);
    console.log(cardtest);

    ctrl.dealOutCards = function() {
        deckPointer = 0;
        cards.shuffle(deck);
        console.log(cards.getCardValue(deck[deckPointer]));
        ctrl.player.cardOne = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
        ctrl.player.cardTwo = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
        ctrl.aiplayer.cardOne = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
        ctrl.aiplayer.cardTwo = cards.getCardValue(deck[deckPointer]);
        deckPointer++;
    }


    ctrl.dealOutCards();
};

angular.module('poker-table', ['player', 'ai-player', 'community-cards', 'cardsService']).component('pokerTable', {
    templateUrl: 'js/table.html',
    controller: TableController,
    bindings: {

    }
});