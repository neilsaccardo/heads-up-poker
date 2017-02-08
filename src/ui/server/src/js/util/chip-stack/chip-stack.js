function ChipStackController($scope) {

};

angular.module('chip-stack', []).component('chipStack', {
    templateUrl: 'js/util/chip-stack/chip-stack.html',
    controller: ChipStackController,
    bindings: {
        total: '<'
    }
});