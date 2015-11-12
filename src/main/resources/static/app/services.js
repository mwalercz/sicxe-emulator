"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.factory('MachineService', function () {
    $http({
        method: 'GET',
        url: '/machine/step'
    }).success(function (data) {
        var machine = data;
    }).error(function () {
        alert("failure");
    });
    return machine;


});