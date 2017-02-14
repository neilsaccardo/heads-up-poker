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
        var cardSuit = Math.floor(cardInt / 13);
        var cardNum = cardInt % 13;
        var cardObj = {suit: '', value: '', evalValue: ''};
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

        switch(cardNum) {
            case 0:
                cardObj.value = 'ace';
                cardObj.evalValue = 'A';
                break;
            case 9:
                cardObj.value = '10';
                cardObj.evalValue = 'T';
            case 10:
                cardObj.value = 'jack';
                cardObj.evalValue = 'J';
                break;
            case 11:
                cardObj.value = 'queen';
                cardObj.evalValue = 'Q';
                break;
            case 12:
                cardObj.value = 'king';
                cardObj.evalValue = 'K';
                break;
            default:
                cardNum++;
                cardObj.value = '' + cardNum;
                cardObj.evalValue = '' + cardNum;
        }

        switch(cardSuit) {
            case 0:
                cardObj.suit = 'hearts';
                cardObj.evalValue = cardObj.evalValue + 'h';
                break;
            case 1:
                cardObj.suit = 'clubs';
                cardObj.evalValue = cardObj.evalValue + 'c';
                break;
            case 2:
                cardObj.suit = 'diamonds';
                cardObj.evalValue = cardObj.evalValue + 'd';
                break;
            case 3:
                cardObj.suit = 'spades';
                cardObj.evalValue = cardObj.evalValue + 's';
                break;
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