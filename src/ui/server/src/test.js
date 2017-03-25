angular.module('testDemo', [
    'poker-table', 'login', 'header', 'socketService']
);

angular.module('testDemo').controller('DemoCtrl', function($scope, socket) {

    var ctrl = $scope;

    ctrl.test = 'testsssss';
    ctrl.showGame = false;// {show: false};
    ctrl.username = '';
    socket.on('loginAccepted', function(data) {
        console.log('Log in accepted....');
        console.log(data.id);
        ctrl.username = data.id;
        ctrl.showGame = true;
    });
});
