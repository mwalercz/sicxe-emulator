/**
 * Created by maciek on 12.11.15.
 */
var sicxe = angular.module("sicxe-sim");
sicxe.service('MachineModel', function () {

    this.createMachine = function () {
        var memory = new Memory();
        return new Machine(memory);
    };
    function Machine(memory) {
        var handler = this;
        handler.ints = new Array();
        handler.memory = memory;
        handler.viewMemory = new Array();
        handler.CC = "";
        handler.F = 0;
        handler.ints.push(new Register("A"));
        handler.ints.push(new Register("B"));
        handler.ints.push(new Register("S"));
        handler.ints.push(new Register("SW"));
        handler.ints.push(new Register("L"));
        handler.ints.push(new Register("T"));
        handler.ints.push(new Register("PC"));
        handler.ints.push(new Register("X"));


    };

    function Register(key) {
        this.key = key;
        this.value = "0x000000";
    };

    function Memory() {
        var handler = this;
        handler.array = new Array();
        handler.get = function (key) {
            for (var i = 0; i < handler.array.length; i++) {
                if (key == handler.array[i].address) return handler.array[i];
            }
            return {
                address: key,
                value: 0
            };
        }
        this.put = function (key, value) {
            for (var i = 0; i < handler.array.length; i++) {
                if (key == handler.array[i].address) {
                    handler.array.splice(i, 1);
                    break;
                }
            }
            handler.array.push({
                address: key,
                value: value
            });
        }
    }
});