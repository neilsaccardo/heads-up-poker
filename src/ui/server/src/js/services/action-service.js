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
        getRaise1String : getRaise1String,
        getRaise2String : getRaise2String,
        getRaise3String : getRaise3String,
        getBetString : getBetString,
        getBet1String : getBet1String,
        getBet2String : getBet2String,
        getBet3String : getBet3String,
        getCheckString : getCheckString,
        getCallString : getCallString,
        getFoldString : getFoldString,
        getAllInString : getAllInString
    }
}


angular.module('actionsService', []).factory('actions', Actions);