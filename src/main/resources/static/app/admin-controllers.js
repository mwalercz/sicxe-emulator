'use strict';

angular.module('sicxe-admin')
    .controller('UsersController', function ($scope,UserService) {
        var handler = this;
        handler.selectedUsers = [];
        $scope.users = UserService.getUsers();
        $scope.changeSelection = function(user) {
            for (var i = 0; i < handler.selectedUsers.length; i++) {
                if (user.id == handler.selectedUsers[i].id) {
                    handler.selectedUsers.splice(i, 1);
                    return;
                }
            }
            handler.selectedUsers.push(user);
        }
    })
    .controller('ArticlesController', function () {
        console.log('articles');
    })
    .controller('FilesController', function () {
        console.log('files');
    });

