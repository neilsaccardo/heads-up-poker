function LoginController($scope, socket, $uibModal) {
    var ctrl = this;
    ctrl.showModal = false;
    ctrl.username = ctrl.username || '';
    ctrl.submitUsername = function() {
        console.log('Log in requested...');
        if (badUsername()) {
            console.log('Log in declined....');
            $uibModal.open({templateUrl: 'js/services/modals/modalContentBadUserName.html'});
        }
        else {
            socket.emit('loginRequest', {id: ctrl.username});
            console.log('Log in accepted');
        }
    }


    function badUsername() {
        console.log('ctrl.username ' + ctrl.username);
        if (ctrl.username === '') {
            return true;
        }
        if (ctrl.username.length > 100) {
            return true;
        }
        if (ctrl.username.includes(':')) {
            return true;
        }
        else {
            return false;
        }
    }

    ctrl.ok = function() {
        console.log('Closed modal');
    };

    socket.on('loginRejected', function(data) {
        console.log('Log in rejected');
        $uibModal.open({templateUrl: 'js/alreadyLoggedInModal.html'});
    });
};

angular.module('login', ['socketService', 'ui.bootstrap']).component('login', {
    templateUrl: 'js/login.html',
    controller: LoginController,
    bindings: {
        username: '<'
    }
});

