function AIActionMessageController($scope) {

};

angular.module('ai-action-message', []).component('aiActionMessage', {
    templateUrl: 'js/util/ai-action-message/ai-action-message.html',
    controller: AIActionMessageController,
    bindings: {
        message: '@'
    }
});