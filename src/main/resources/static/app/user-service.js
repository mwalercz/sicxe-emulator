/**
 * Created by maciek on 15.11.15.
 */
"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service('UserService', function () {

    var handler = this;
    handler.user = {
        logged : false
    };
    handler.setUser = function(user){
        handler.user = user;
    };
    handler.getUser = function(){
        return handler.user;
    };
});
