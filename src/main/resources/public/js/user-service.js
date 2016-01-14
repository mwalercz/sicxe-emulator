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
    handler.authenticate = function (credentials, successCallback) {
        var headers = credentials ? {authorization : "Basic "
        + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/user', {headers: headers}).then(function success(response) {
            var data = response.data;
            if(data.name){
                handler.user.username = data.name;
                handler.user.logged = true;
                successCallback();
            } else{
                console.log(response);
                handler.user.logged = false;
                alert("no such username/password combination")
            }

        }, function error(response) {
            handler.user = false;
            handler.user.logged = false;
            alert(response)
        });
    };

    handler.newUser = function (user, successCallback) {
        $http.post('/user/new', user).then(function success(response) {
            alert("now u can login using your credentials");
            successCallback(response);
        }, function error(response) {
            alert(response);
        });
    };

    handler.logout = function(successCallback){
        $http.post('/logout', {}).then(function success(){
            handler.user = {
                logged: false
            };
        }, function error(){
            handler.user = {
                logged: false
            };
        })
    };
    handler.getUser = function () {
        return handler.user;
    };

    handler.getUsers = function () {
        return handler.users;
    };
    handler.remove = function (selectedUsers) {
        for (var i = 0; i < handler.users.length; i++) {
            for (var j = 0; j < selectedUsers.length; j++) {
                if (handler.users[i].id == selectedUsers[j].id) {
                    handler.users.splice(i, 1);
                }

            }
        }
    };



});
