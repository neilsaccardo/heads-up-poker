function AiPlayerController($scope) {

};

angular.module('ai-player', ['playingCard', 'dealerButton']).component('aiPlayer', {
    templateUrl: 'js/ai-player.html',
    controller: AiPlayerController,
    bindings: {
        cardOne: '<',
        cardTwo: '<',
        hideCard: '<',
        isDealer: '<',
        isTurn: '<'
    }
});