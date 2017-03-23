function LoginController($scope, socket) {
    var ctrl = this;

    ctrl.submitUsername = function() {
        console.log('Log in requested...');
        socket.emit('loginRequest', {id: ctrl.username});
    }
};

angular.module('login', ['socketService']).component('login', {
    templateUrl: 'js/login.html',
    controller: LoginController,
    bindings: {
        username: '<'
    }
});