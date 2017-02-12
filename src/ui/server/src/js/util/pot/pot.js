function PotController($scope) {
    var ctrl = this;
};

angular.module('pot', []).component('pot', {
    templateUrl: 'js/util/pot/pot.html',
    controller: PotController,
    bindings: {
        total: '<'
    }
});