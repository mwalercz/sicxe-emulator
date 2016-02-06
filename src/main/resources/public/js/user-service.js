/**
 * Created by maciek on 15.11.15.
 */
"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service('UserService', function ($http) {

    var handler = this;
    handler.user = {
        logged: false
    };
    handler.login = function (user, successCallback) {
        $http.post('/login',user).then(function success(response) {
            var data = response.data;
            if(data.username){
                handler.user.username = data.username;
                handler.user.logged = true;
                successCallback();
            } else{
                console.log(response);
                handler.user.logged = false;
                alert("No such username/password combination")
            }

        }, function error(response) {
            handler.user = false;
            handler.user.logged = false;
            alert(response)
        });
    };

    handler.register = function (user, successCallback) {
        $http.post('/register', user).then(function success(response) {
            alert("now u can login using your credentials");
            successCallback(response);
        }, function error(response) {
            alert(response);
        });
    };

    handler.logout = function(callback){
        $http.get('/logout').then(function success(){
            handler.user = {
                logged: false
            };
            callback(handler.user)
        }, function error(){
            handler.user = {
                logged: false
            };
            callback(handler.user)
        })
    };
    handler.getProfile = function(callback){
        $http.get("/profile/".concat(handler.user.username)).then(function success(response){
            var data = response.data;
            if(data.username){
                handler.user.email = data.email;
                handler.user.username = data.username;
                handler.user.admin = data.admin;
                handler.user.assemblyFiles = data.assemblyFiles;
                handler.user.tutorials = data.tutorials;
                callback(handler.user);
            }
        })
    }
    handler.getUser = function () {
        return handler.user;
    };

    handler.getUsers = function () {
        return handler.users;
    };





});
