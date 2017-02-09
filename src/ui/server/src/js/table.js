function TableController($scope) {
    var ctrl = this;
    ctrl.cardOne = {suit: 'hearts', value: '2'};
    ctrl.cardTwo = {suit: 'hearts', value: '3'};
};

angular.module('poker-table', ['player', 'ai-player', 'community-cards']).component('pokerTable', {
    templateUrl: 'js/table.html',
    controller: TableController,
    bindings: {

    }
});