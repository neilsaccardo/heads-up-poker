function PlayerController($scope) {
    var ctrl = this;

    ctrl.fold = function() {
        console.log('HERE');
    }
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
        isTurn: '<',
        stackSize: '<'
    }
});