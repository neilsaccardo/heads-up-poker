function MinBetController($scope) {

};

angular.module('min-bet', []).component('minBet', {
    templateUrl: 'js/util/min-bet/min-bet.html',
    controller: MinBetController,
    bindings: {
        bet: '<',
        hands: '<'
    }
});