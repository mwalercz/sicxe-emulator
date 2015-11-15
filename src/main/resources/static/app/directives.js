/**
 * Created by maciek on 15.11.15.
 */
var sicxe = angular.module("sicxe-sim");
sicxe.directive("match", function ($parse) {
    return {
        require: 'ngModel',
        link: function (scope, elem, attr, ctrl) {
            scope.$watch(function () {
                return $parse(attr.match)(scope) === ctrl.$modelValue;

            }, function (currentValue) {
                ctrl.$setValidity('mismatch', currentValue)

            });

        }

    };

});