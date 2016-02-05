"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service('MachineService', function ($rootScope, $http, Upload) {
    var handler = this;
    handler.MEMORY_SIZE = 16 * 16;
    handler.MEMORY_START = 0;
    handler.MEMORY_END = 16 * 16;
    this.getStart = function () {
        return handler.decimalToHex(handler.MEMORY_START);
    };
    this.getEnd = function () {
        return handler.decimalToHex(handler.MEMORY_END - 1);
    };
    this.assembly = function (scopeMachine, file) {
        if (file) {
            file.upload = Upload.upload({
                url: "/machine/assembly",
                data: {file: file}
            });

            file.upload.then(function (response) {
                if (response.data.errors == null) {
                    handler.convertDataToMachine(response.data, scopeMachine);
                    handler.convertMemoryToViewMemory(scopeMachine);
                    $rootScope.errors = ["Translating process was successful!"];
                } else {
                    $rootScope.errors = response.data.errors;
                    console.log(response);
                }
            }, function (response) {
                console.log(response);

            });
        }

    };
    this.load = function (id) {
        $http.get("machine/assembly/".concat(id)).then(function (response) {
            if (response.data.errors == null) {
                handler.convertDataToMachine(response.data, $rootScope.machine);
                handler.convertMemoryToViewMemory($rootScope.machine);
                $rootScope.errors = ["Translating process was successful!"];
            } else {
                $rootScope.errors = response.data.errors;
                console.log(response);
            }
        }, function (response) {
            console.log(response);

        });
    };


    this.step = function (scopeMachine) {
        $http.get('/machine/step').success(function (data) {
            if(data.errors == null) {
                handler.convertDataToMachine(data, scopeMachine);
                handler.convertMemoryToViewMemory(scopeMachine);
            } else {
                $rootScope.errors = data.errors;
                console.log(data);
            }
        });
    };
    this.convertDataToMachine = function (data, scopeMachine) {
        scopeMachine.nixbpe = data.machine.nixbpe;
        scopeMachine.instructions = data.instructions;
        var dataInts = data.machine.registers.integers;
        var localInts = scopeMachine.ints;
        for (var i = 0; i < dataInts.length; i++) {
            for (var j = 0; j < localInts.length; j++) {
                var dataElement = dataInts[i];
                var localElement = localInts[j];
                if (dataElement.key == localElement.key) {
                    localElement.value = dataElement.value;
                    break;
                }
            }
        }
        if (data.machine.registers.cc != null) {
            scopeMachine.CC = data.machine.registers.cc
        }
        if (data.machine.registers.f != null) {
            scopeMachine.F = data.machine.registers.f;
        }
        angular.forEach(data.machine.memory, function (element) {
            scopeMachine.memory.put(element.key, element.value);
        });
    };

    handler.convertMemoryToViewMemory = function (scopeMachine) {
        scopeMachine.viewMemory = new Array();
        handler.MEMORY_END = handler.MEMORY_START + handler.MEMORY_SIZE;
        for (var i = handler.MEMORY_START; i < handler.MEMORY_END; i++) {
            var viewCell = {
                address: scopeMachine.memory.get(i).address,
                value: scopeMachine.memory.get(i).value
            }
            scopeMachine.viewMemory.push(viewCell);
        }
    };
    handler.nextMemory = function (machine) {
        handler.MEMORY_START += handler.MEMORY_SIZE;
        return handler.convertMemoryToViewMemory(machine);
    };
    handler.prevMemory = function (machine) {
        handler.MEMORY_START -= handler.MEMORY_SIZE;
        return handler.convertMemoryToViewMemory(machine);
    };
    handler.decimalToHex = function(d) {
        var hex = Number(d).toString(16).toUpperCase();
        while (hex.length < 5) {
            hex = "0" + hex;
        }
        return hex;
    }

});