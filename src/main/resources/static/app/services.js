"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service('MachineService', function ($http) {
    this.step = function () {
        var promise = $http.get('/machine/step').then(function (response) {
            console.log(response);
            return response.data;
        });
        return promise;
    }
    this.mockStep = function () {
        var promise = $http.get('/machine/step').then(function (response) {
            console.log(response);
            return {
                "memory": [
                    {
                        "key": 0,
                        "value": 5,
                    },
                    {
                        "key": 0,
                        "value": 2,
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
            }
        });
        return promise;
    }


});