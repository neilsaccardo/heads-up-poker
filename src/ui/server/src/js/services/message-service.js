function Message(actions) {

    function getAIHasCheckedMessage() {
        return 'The AI has checked.';
    }

    function getAIHasCalledMessage() {
        return 'The AI has called.';
    }

    function getAIHasRaisedMessage(amount) {
        return 'The AI has raised ' + amount + ' chips.';
    }

    function getAIHasBetMessage(amount) {
        return 'The AI has bet ' + amount + ' chips.';
    }

    function getAIHasFoldedMessage() {
        return 'The AI has folded.';
    }

    var messageMap = {
        'call' : 'The AI has called.',
        'check' : 'The AI has checked.',
        'raise' : 'The AI has raised.' ,
        'bet' : 'The AI has bet.' ,
        'fold' : 'The AI has folded'
    }

    function getMessageOnAction(action) {
        return messageMap[action];
    }

    function getWinnerMessage(playerStack, aiStackSize) {
        if (aiStackSize > playerStack) {
            return "Unlucky... You've lost!";
        }
        else {
            return "Congratulations, you've won! "
        }
    }
    return {
        getAIHasCheckedMessage : getAIHasCheckedMessage,
        getAIHasCalledMessage : getAIHasCalledMessage,
        getAIHasRaisedMessage : getAIHasRaisedMessage,
        getAIHasBetMessage : getAIHasBetMessage,
        getAIHasFoldedMessage : getAIHasFoldedMessage,
        getMessageOnAction : getMessageOnAction,
        getWinnerMessage : getWinnerMessage
    }
}


angular.module('messageService', ['actionsService']).factory('message', Message);