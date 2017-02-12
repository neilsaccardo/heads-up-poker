angular.module('testDemo', [
    'poker-table'
]);

angular.module('testDemo').controller('DemoCtrl', function($scope) {
    var suits = ['hearts', 'diamonds', 'spades', 'clubs'];
    var ranks = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', 'jack', 'queen', 'king'];
    $scope.Cards = [];
    $scope.Cards.push({suit: 'heart', rank: 'back'});

    angular.forEach(suits, function(suit) {
        angular.forEach(ranks, function(rank) {
            $scope.Cards.push(
                    {
                        suit: suit,
                        rank: rank
                    });
        });
    });

    $scope.cardOne = {suit: 'hearts', value: '2'};
    $scope.cardTwo = {suit: 'hearts', value: '3'};
});
