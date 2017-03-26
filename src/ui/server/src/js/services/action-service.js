function Actions() {

    function getCallString() {
        return "call";
    }

    function getBetString() {
        return "bet";
    }

    function getCheckString() {
        return "check";
    }

    function getRaiseString() {
        return "raise";
    }

    function getFoldString() {
        return "fold";
    }

    return {
        getRaiseString : getRaiseString,
        getBetString : getBetString,
        getCheckString : getCheckString,
        getCallString : getCallString,
        getFoldString : getFoldString
    }
}


angular.module('actionsService', []).factory('actions', Actions);