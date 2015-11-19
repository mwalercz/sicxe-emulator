'use strict';

angular.module('sicxe-sim')
    .controller('SimulatorController', ['$scope', 'MachineService', 'MachineModel', 'UserService', function ($scope, MachineService, MachineModel,UserService) {
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
    .controller('ProfileController', ['$scope','UserService', function ($scope, UserService) {
        $scope.user = UserService.getUser();
        console.log('profile');
    }])
    .controller('LoginController', ['$rootScope','$scope','$state','UserService', function ($rootScope, $scope, $state, UserService) {
        $scope.login = function(){
            $scope.user.logged = true;
            if($scope.user.username == "mwal"){
                $scope.user.admin = true;
            } else {
                $scope.user.admin = false;
            }
            UserService.setUser($scope.user);
            $rootScope.$broadcast('userLogged',"broadcast");
            $state.go('simulator', {}, {reload: true});
        }
    }])
    .controller('HeaderController', ['$scope','UserService', function ($scope, UserService) {
        $scope.user = UserService.getUser();
    }])
    .controller('OutputController', ['$rootScope','$scope', '$stomp', 'UserService', function($rootScope, $scope, $stomp, UserService){
        $scope.messages = [];
        $scope.mess = "";
        $scope.test = true;
        $scope.connect = function () {
            $stomp
                .connect('/update', {})
                .then(function (frame) {
                        console.log("connected" + frame);
                        var subscription =
                            $stomp.subscribe('/topic/greetings', function (payload, headers, res) {
                                $rootScope.$apply(function() {
                                    $rootScope.mess = payload.content;
                                });

                                console.log(payload.content);
                            }, {});
                        var message = {name: "Maciek"};
                        $stomp.send('/app/hello', message);

                        //subscription.unsubscribe();

                        //$stomp.disconnect(function () {
                        //    console.log("disconnected");
                        //});

                }, function (error) {
                    console.log(error);
                });

        };
        $rootScope.$on('userLogged', $scope.connect);

    }])
    .controller('SignupController', ['$scope','$state',function ($scope, $state) {
        $scope.signup = function(user) {
            $state.go('simulator', {}, {reload: true});
        }
    }]);


