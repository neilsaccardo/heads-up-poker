function AmountService($uibModal) {

    var path = 'js/services/modals/';

    function checkBetRaiseAmount(amountStr, stackSize, minBet) {
        var amount = parseInt(amountStr);
        if (!(typeof amount === 'number' && (amount%1) === 0)) {
            $uibModal.open({templateUrl: path + 'modalContentNotAnInteger.html'});
            return false;
        }
        else if (amount < minBet) {
            $uibModal.open({templateUrl: path + 'modalContentMinBet.html'});
            return false;
        }
        else if (amount > stackSize) {
            $uibModal.open({templateUrl: path + 'modalContentBetTooBig.html'});
            return false;
        }
        else {
            return true;
        }
    }

    function getBetRaiseAmount(potSize, multiplier) {
        var mult = multiplier | 1;
        var numChips = ((potSize / 4) * 3) * mult;
        return Math.floor(numChips);
    }

    function checkRaiseOverOtherPlayerStack(amount, otherPlayerStack) {
        console.log('dsds');
        if (amount > otherPlayerStack) {
            $uibModal.open({templateUrl: path + 'modalContentBetTooBigForAI.html'});
            return false;
        } else {
            return true;
        }
    }
    return {
        checkBetRaiseAmount : checkBetRaiseAmount,
        getBetRaiseAmount : getBetRaiseAmount,
        checkRaiseOverOtherPlayerStack: checkRaiseOverOtherPlayerStack
    }
}

angular.module('amountService', ['ui.bootstrap']).factory('amountService', AmountService);