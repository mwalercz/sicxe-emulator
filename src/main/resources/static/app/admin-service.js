"use strict";
var admin = angular.module('sicxe-admin');
admin.service('AdminService', function () {
    var handler = this;
    handler.selected = [];
    handler.changeSelection = function(element) {
        for (var i = 0; i < handler.selected.length; i++) {
            if (element.id == handler.selected[i].id) {
                handler.selected.splice(i, 1);
                return;
            }
        }
        handler.selected.push(element);
    }
    handler.getSelected = function(){
        return handler.selected;
    }
    handler.remove = function(){
        handler.selected = [];
    }
    handler.init = function(){
        handler.selected = [];
    }

});

