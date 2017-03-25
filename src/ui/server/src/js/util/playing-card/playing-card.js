function PlayingCardController() {
    var ctrl = this;

    ctrl.getRankValue = function() {
        if (ctrl.value === 'K' || ctrl.value === 'Q' || ctrl.value === 'J' || ctrl.value === 'A') {
            return ctrl.value.toLowerCase();
        }
        else {
            return ctrl.value;
        }
    }
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