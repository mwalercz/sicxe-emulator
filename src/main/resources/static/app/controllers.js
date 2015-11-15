'use strict';

angular.module('sicxe-sim')
    .controller('SimulatorController', ['$scope', 'MachineService', 'MachineModel', 'UserService', function ($scope, MachineService, MachineModel, UserService) {
        var init = function () {
            if ($scope.machine == undefined) {
                $scope.machine = MachineModel.createMachine();
                MachineService.convertMemory($scope.machine);
            }
        };
        $scope.step = function () {
            MachineService.stepMock($scope.machine);
        };
        $scope.nextMemory = function () {
            MachineService.nextMemory($scope.machine);
        };
        $scope.prevMemory = function () {
            MachineService.prevMemory($scope.machine);
        };
        init();
        console.log('simcontroller');
    }])

    .controller('AboutController', function () {
        console.log('about');
    })
    .controller('TutorialsController', function () {
        console.log('tutorials');
    })
    .controller('ProfileController', function ($scope, UserService) {
        $scope.user = UserService.getUser();
        console.log('profile');
    })
    .controller('LoginController', function ($scope, $state, UserService) {
        $scope.login = function(){
            $scope.user.logged = true;
            if($scope.user.username == "mwal"){
                $scope.user.admin = true;
            } else {
                $scope.user.admin = false;
            }
            UserService.setUser($scope.user);
            $state.go('simulator', {}, {reload: true});
        }
    })
    .controller('HeaderController', function ($scope, $state, UserService) {
        $scope.user = UserService.getUser();
    })
    .controller('SignupController', function ($scope, $state) {
        $scope.signup = function(user) {
            $state.go('simulator', {}, {reload: true});
        }
    });


