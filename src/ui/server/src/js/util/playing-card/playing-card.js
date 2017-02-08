function PlayingCardController() {

};

angular.module('playingCard', []).component('playingCard', {
    templateUrl: 'js/util/playing-card/playingCard.html',
    controller: PlayingCardController,
    bindings: {
        suit: '=', //need to pass these in as sttrings
        value: '=', // using the single quotes ''
        isHidden: '='
    }
});