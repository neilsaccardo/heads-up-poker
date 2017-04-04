function PotController($scope) {
    var ctrl = this;
    ctrl.getBorderColour = function() {
        if (ctrl.total > 5000) {
            return "blue-border";
        }
        else if (ctrl.total > 2500) {
            return "red-border";
        }
        else if (ctrl.total > 1000) {
            return "purple-border";
        }
        else if (ctrl.total > 500) {
            return "green-border";
        }
        else {
            return "black-border";
        }
    }
};

angular.module('pot', []).component('pot', {
    templateUrl: 'js/util/pot/pot.html',
    controller: PotController,
    bindings: {
        total: '<'
    }
});