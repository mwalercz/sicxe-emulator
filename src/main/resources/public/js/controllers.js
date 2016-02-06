'use strict';

angular.module('sicxe-sim')
    .controller('SimulatorController', ['$rootScope', '$scope',
        'MachineService', 'MachineModel', 'UserService',
        function ($rootScope, $scope, MachineService, MachineModel) {

            var init = function () {
                $scope.memoryStart = MachineService.getStart();
                $scope.memoryEnd = MachineService.getEnd();
                $rootScope.errors = [];
                $rootScope.machine = MachineModel.createMachine();
                MachineService.convertMemoryToViewMemory($rootScope.machine);
            };
            $scope.step = function () {
                MachineService.step($rootScope.machine);

            };
            $scope.nextMemory = function () {
                MachineService.nextMemory($rootScope.machine);
                $scope.memoryStart = MachineService.getStart();
                $scope.memoryEnd = MachineService.getEnd();
            };
            $scope.prevMemory = function () {
                MachineService.prevMemory($rootScope.machine);
                $scope.memoryStart = MachineService.getStart();
                $scope.memoryEnd = MachineService.getEnd();
            };
            init();
            $scope.popoverVisible = false;
            console.log('simcontroller');
        }])

    .controller('AboutController', function () {
        console.log('about');
    })

    .controller('SimLoaderController', function ($rootScope, $scope, MachineService) {
        $scope.show = false;
        $scope.showOptions = function () {
            if ($scope.show == false) {
                $scope.show = true;
            } else {
                $scope.show = false;
            }
        }
        $scope.uploadFile = function (file) {
            MachineService.assembly($rootScope.machine, file);
        }
    })
    .controller('TutorialsController', function ($scope, TutorialsService) {
         TutorialsService.getTutorials(function(tutorials){
             $scope.tutorials = tutorials;
        });

    })
    .controller('TutorialController', function (MachineService, $scope, $stateParams, TutorialsService) {
        var id = $stateParams.id;
        TutorialsService.getTutorial(id, function(tutorial){
             $scope.tutorial = tutorial;
        });

        $scope.load = function(id){
            MachineService.load(id);
        }
    })
    .controller('ProfileController', function ($scope, UserService) {
        var init = function(user) {
            $scope.user = user;
        }
        UserService.getProfile(init);

    })
    .controller('NewTutorialController', function ($scope, TutorialsService, UserService, $state) {
        $scope.createTutorial = function (tutorial, file) {
            tutorial.author = UserService.getUser().username;
            TutorialsService.add(tutorial, file, function(tut){

                $state.go('tutorial', {'id': tut.id}, {reload: true})
            });

        }
    })
    .controller('LoginController', function ($scope, $state, UserService) {
        $scope.login = function (user) {
            UserService.login(user, function () {
                $state.go('simulator', {}, {reload: true});
            });


        }
    })
    .controller('HeaderController', function ($scope, $state, UserService) {
        $scope.user = UserService.getUser();
        $scope.show = false;
        $scope.switch = function () {
            if ($scope.show) {
                $scope.show = false;
            } else {
                $scope.show = true;
            }
        }
        $scope.logout = function () {
            UserService.logout(function () {
                $scope.switch();
                $state.go('simulator', {}, {reload: true});
            });
        }
    })
    //.controller('OutputController', function ($rootScope, $scope, $stomp, UserService) {
    //    $scope.messages = [];
    //    $scope.connect = function () {
    //        $stomp
    //            .connect('/update', {})
    //            .then(function (frame) {
    //                console.log("connected" + frame);
    //                var subscription =
    //                    $stomp.subscribe('/topic/greetings', function (payload, headers, res) {
    //                        $scope.$apply(function () {
    //                            $scope.messages.push(payload.content);
    //                        });
    //                        console.log(payload.content);
    //                    }, {});
    //                var message = {name: "Maciek"};
    //                $stomp.send('/app/hello', message);
    //
    //                //subscription.unsubscribe();
    //
    //                //$stomp.disconnect(function () {
    //                //    console.log("disconnected");
    //                //});
    //
    //            }, function (error) {
    //                console.log(error);
    //            });
    //
    //    };
    //    //$scope.connect();
    //
    //})
    .controller('SignupController', function ($scope, $state, UserService) {
        $scope.signup = function (user) {
            UserService.register(user, function (response) {
                $scope.user = response;
                $state.go('simulator', {}, {reload: true});
            });
        }
    });


