function Rules() {
    function winningPlayer(p1Card1, p1Card2, p2card1, p2Card2, communityCards) {

    }

    return {
        winningPlayer : winningPlayer
    }
}


angular.module('rulesService', []).factory('rules', Rules);