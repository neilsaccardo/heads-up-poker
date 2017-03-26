function Message() {

    function getAIHasCheckedMessage() {
        return 'The AI has checked.';
    }

    function getAIHasCalledMessage() {
        return 'The AI has called.';
    }

    function getAIHasRaisedMessage(amount) {
        return 'The AI has raised ' + amount + ' chips.';
    }

    function getAIHasBetMessage() {
        return 'The AI has bet ' + amount + ' chips.';
    }

    function getAIHasFoldedMessage() {
        return 'The AI has folded.';
    }

    return {
        getAIHasCheckedMessage : getAIHasCheckedMessage,
        getAIHasCalledMessage : getAIHasCalledMessage,
        getAIHasRaisedMessage : getAIHasRaisedMessage,
        getAIHasBetMessage : getAIHasBetMessage,
        getAIHasFoldedMessage : getAIHasFoldedMessage
    }
}


angular.module('messageService', []).factory('message', Message);