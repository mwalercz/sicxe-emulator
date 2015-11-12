'use strict';

angular.module('sicxe-sim')
    .controller('SimulatorController', function ($scope, MachineService) {
        function Machine(memory) {
            var handler = this;
            handler.ints = new Array();
            handler.memory = memory;
            handler.CC ="";
            handler.F = 0;

            handler.ints.push(new Register("A"));
            handler.ints.push(new Register("B"));
            handler.ints.push(new Register("S"));
            handler.ints.push(new Register("SW"));
            handler.ints.push(new Register("L"));
            handler.ints.push(new Register("T"));
            handler.ints.push(new Register("PC"));
            handler.ints.push(new Register("X"));

        }

        function Register(key) {
            this.key = key;
            this.value = 0;
        }

        function Memory() {
            var handler = this;
            handler.array = new Array();
            handler.get = function (key) {
                for(var i = 0; i < handler.array.length; i++){
                    if(key == handler.array[i].address) return handler.array[i].value;
                }
                return 0;



            }
            this.put = function (key, value) {
                for(var i = 0; i < handler.array.length; i++){
                    if(key == handler.array[i].address){
                        handler.array.splice(i,1);
                        break;
                    }
                }
                handler.array.push({
                    address: key,
                    value: value
                });
            }
        }

            var init = function () {
                if ($scope.machine == undefined) {
                    $scope.memory = new Memory();
                    $scope.machine = new Machine($scope.memory);
                }
            };

            $scope.step = function () {
                MachineService.mockStep().then(function (data) {
                    var dataInts = data.registers.integers;
                    var localInts = $scope.machine.ints;
                    for(var i = 0; i < dataInts.length; i++){
                        for(var j = 0; j < localInts.length; j++){
                            var dataElement = dataInts[i];
                            var localElement = localInts[j];
                            if (dataElement.key == localElement.key){
                                localElement.value = dataElement.value;
                                break;
                            }
                        }
                    }
                    if (data.registers.cc != null) {
                        $scope.machine.CC = data.registers.cc
                    }
                    if (data.registers.f != null) {
                        $scope.machine.F = data.registers.f;
                    }
                    angular.forEach(data.memory, function (element) {
                        $scope.machine.memory.put(element.key, element.value);
                    });
                    $scope.testGet = $scope.machine.memory.get(0);

                });
            };
            init();
            $scope.step();
            console.log('simcontroller');
        }

        )

        .
        controller('AboutController', function () {
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


