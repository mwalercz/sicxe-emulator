'use strict';

angular.module('sicxe-sim')
    .controller('SimulatorController', function ($scope,MachineService) {
        var init = function(){
          if ($scope.machine == undefined){
              $scope.machine
          }
        };
        console.log('simcontroller');
    })
    .controller('AboutController', function () {
        console.log('about');
    })
    .controller('TutorialsController', function () {
        console.log('tutorials');
    })
    .controller('LoginController', function () {
        console.log('login');
    })
    .controller('SignupController', function () {
        console.log('signup');
    });

