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

    /*This function is used for components that want to display a card.  */
    function getCardValue(cardInt) {
        var cardSuit = Math.floor(cardInt / 13);
        var cardNum = cardInt % 13;
        console.log(cardInt);
        var cardObj = {suit: '', value: '', evalValue: ''};
        switch(cardNum) {
            case 0:
                cardObj.value = 'A';
                cardObj.evalValue = 'A';
                break;
            case 9:
                cardObj.value = '10';
                cardObj.evalValue = 'T';
                break;
            case 10:
                cardObj.value = 'J';
                cardObj.evalValue = 'J';
                break;
            case 11:
                cardObj.value = 'Q';
                cardObj.evalValue = 'Q';
                break;
            case 12:
                cardObj.value = 'K';
                cardObj.evalValue = 'K';
                break;
            default:
                cardObj.value = '' + (cardNum+1);
                cardObj.evalValue = '' + (cardNum+1);
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
                cardObj.suit = 'diams';
                cardObj.evalValue = cardObj.evalValue + 'd';
                break;
            case 3:
                cardObj.suit = 'spades';
                cardObj.evalValue = cardObj.evalValue + 's';
                break;
        }
        console.log(cardObj);
        return cardObj;
    }

    return {
        createDeck: createDeck,
        shuffle: shuffle,
        getCardValue: getCardValue
    };
};

angular.module('cardsService', []).factory('cards', Cards);