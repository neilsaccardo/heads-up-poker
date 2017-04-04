function ChipStackController($scope) {
    var ctrl = this;
    ctrl.total = ctrl.total;

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

angular.module('chip-stack', []).component('chipStack', {
    templateUrl: 'js/util/chip-stack/chip-stack.html',
    controller: ChipStackController,
    bindings: {
        total: '<'
    }
});