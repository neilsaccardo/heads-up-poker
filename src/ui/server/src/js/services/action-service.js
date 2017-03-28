function Actions() {

    function getCallString() {
        return "call";
    }

    function getBetString() {
        return "bet";
    }

    function getBet1String() {
        return "bet1";
    }

    function getBet2String() {
        return "bet2";
    }

    function getBet3String() {
        return "bet3";
    }

    function getCheckString() {
        return "check";
    }

    function getRaiseString() {
        return "raise";
    }

    function getRaise1String() {
        return "raise";
    }

    function getRaise2String() {
        return "raise";
    }

    function getRaise3String() {
        return "raise";
    }

    function getFoldString() {
        return "fold";
    }

    function getAllInString() {
        return "allin";
    }

    return {
        getRaiseString : getRaiseString,
        getRaise1String : getRaiseString,
        getRaise2String : getRaiseString,
        getRaise3String : getRaiseString,
        getBetString : getBetString,
        getBet1String : getBetString,
        getBet2String : getBetString,
        getBet3String : getBetString,
        getCheckString : getCheckString,
        getCallString : getCallString,
        getFoldString : getFoldString
    }
}


angular.module('actionsService', []).factory('actions', Actions);