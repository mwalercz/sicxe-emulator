"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service('MachineService', function ($http) {
    var handler = this;
    handler.MEMORY_SIZE = 10*16;
    handler.MEMORY_START = 0;
    this.stepHttp = function (scopeMachine) {
        $http.get('/machine/step').success(function (data) {
             handler.convertToView(data, scopeMachine);
        });
    };
    this.stepMock = function (scopeMachine) {
        var data = {
            "memory": [
                {
                    "key": 0,
                    "value": 5
                }
            ],
            "registers": {
                "integers": [
                    {
                        "key": "PC",
                        "value": 3
                    }
                ],
                "f": null,
                "cc": null
            }
        };
        handler.convertToView(data, scopeMachine);
    };
    this.convertToView = function (data, scopeMachine) {
        var dataInts = data.registers.integers;
        var localInts = scopeMachine.ints;
        for (var i = 0; i < dataInts.length; i++) {
            for (var j = 0; j < localInts.length; j++) {
                var dataElement = dataInts[i];
                var localElement = localInts[j];
                if (dataElement.key == localElement.key) {
                    localElement.value = handler.toHex3Bytes(dataElement.value);
                    break;
                }
            }
        }
        if (data.registers.cc != null) {
            scopeMachine.CC = data.registers.cc
        }
        if (data.registers.f != null) {
            scopeMachine.F = data.registers.f;
        }
        angular.forEach(data.memory, function (element) {
            scopeMachine.memory.put(element.key, element.value);
        });
        handler.convertMemory(scopeMachine);
    };

    handler.convertMemory = function (scopeMachine) {
        scopeMachine.viewMemory = new Array();
        for (var i = handler.MEMORY_START; i < handler.MEMORY_START + handler.MEMORY_SIZE; i++) {
            var viewCell = {
                address : handler.toHexByte(scopeMachine.memory.get(i).address),
                value : handler.toHexByte(scopeMachine.memory.get(i).value)
            }
            scopeMachine.viewMemory.push(viewCell);
        }
    };
    handler.nextMemory = function(machine){
        handler.MEMORY_START += handler.MEMORY_SIZE;
        return handler.convertMemory(machine);
    };
    handler.prevMemory = function(machine){
        handler.MEMORY_START -= handler.MEMORY_SIZE;
        return handler.convertMemory(machine);
    };

    handler.toHexByte = function(value){
        var hex = value.toString(16,0,(1<<8)-1);
        if(hex.length < 2){
            hex = "0"+hex;
        }
        return hex;
    }

    handler.toHex3Bytes = function(value){
        var hex = value.toString(16,0, (1<<24)-1);
        while(hex.length < 6 ){
            hex = "0" + hex;
        }
        hex = "0x" + hex;
        return hex;

    }
});