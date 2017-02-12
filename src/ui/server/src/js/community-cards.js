function CommunityCardsController($scope) {
    var ctrl = this;

};

angular.module('community-cards', ['playingCard']).component('communityCards', {
    templateUrl: 'js/community-cards.html',
    controller: CommunityCardsController,
    bindings: {
        cards: '<'
    }
});