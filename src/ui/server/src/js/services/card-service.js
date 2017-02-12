function Cards($rootScope) {
    var cards;

    function createDeck() {
        var cs = [];
        for(var i = 0; i < 52; i++) {
            cs.push(i);
        }
        return cs;
    }

    /* http://stackoverflow.com/questions/2450954/how-to-randomize-shuffle-a-javascript-array */
    function shuffle(array) {
        for (var i = array.length - 1; i > 0; i--) {
            var j = Math.floor(Math.random() * (i + 1));
            var temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }

    function getCardValue(cardInt) {
        var cardSuit = cardInt % 4;
        var cardNum = cardInt % 13;
        var cardObj = {suit: '', value: ''};
        switch(cardSuit) {
            case 0:
                cardObj.suit = 'hearts';
                break;
            case 1:
                cardObj.suit = 'clubs';
                break;
            case 2:
                cardObj.suit = 'diamonds';
                break;
            case 3:
                cardObj.suit = 'spades';
                break;
        }

        switch(cardNum) {
            case 0:
                cardObj.value = 'ace'
                break;
            case 10:
                cardObj.value = 'jack';
                break;
            case 11:
                cardObj.value = 'queen';
                break;
            case 12:
                cardObj.value = 'king';
                break;
            default:
                cardNum++;
                cardObj.value = '' + cardNum;
        }
        return cardObj;
    }

    return {
        createDeck: createDeck,
        shuffle: shuffle,
        getCardValue: getCardValue
    };
};

angular.module('cardsService', []).factory('cards', Cards);