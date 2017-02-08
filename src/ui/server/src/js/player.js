function PlayerController($scope) {

};

angular.module('player', ['playingCard', 'dealerButton', 'chip-stack']).component('player', {
    templateUrl: 'js/player.html',
    controller: PlayerController,
    bindings: {
        cardOne: '<',
        cardTwo: '<',
        hideCard: '<',
        isDealer: '<',
        isOwnPlayer: '<',
        isTurn: '<'
    }
});