function AmountService($uibModal) {

    var path = 'js/services/modals/';

    function checkBetRaiseAmount(amountStr, stackSize, minBet) {
        var amount = parseInt(amountStr);
        if (!(typeof amount === 'number' && (amount%1) === 0)) {
            console.log(typeof amount);
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

    return {
        checkBetRaiseAmount : checkBetRaiseAmount
    }
}


angular.module('amountService', ['ui.bootstrap']).factory('amountService', AmountService);