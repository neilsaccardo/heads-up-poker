angular.module('testDemo', [
    'poker-table', 'login', 'header', 'socketService']
);

angular.module('testDemo').controller('DemoCtrl', function($scope, socket) {

    var ctrl = $scope;
    ctrl.showGame = false;// {show: false};
    ctrl.username = '';
    socket.on('loginAccepted', function(data) {
        console.log('Log in accepted....');
        ctrl.showGame = true;
        ctrl.username = data.id;
    });
});
