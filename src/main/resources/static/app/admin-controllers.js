'use strict';

angular.module('sicxe-admin')
    .controller('UsersController', function ($scope, UserService, AdminService) {
        AdminService.init();
        $scope.users = UserService.getUsers();
        $scope.changeSelection = function (user) {
            AdminService.changeSelection(user);
        };
        $scope.remove = function () {
            UserService.remove(AdminService.getSelected());
            $scope.users = UserService.getUsers();
        }
    })
    .controller('ArticlesController', function ($scope, TutorialsService, AdminService) {
        AdminService.init();
        $scope.articles = TutorialsService.getTutorials();
        $scope.changeSelection = function (article) {
            AdminService.changeSelection(article);
        };
        $scope.remove = function () {
            TutorialsService.remove(AdminService.getSelected());
            $scope.articles = TutorialsService.getTutorials();
        }
    })
    .controller('FilesController', function ($scope, FileService, AdminService) {
        AdminService.init();
        $scope.files = FileService.getFiles();
        $scope.changeSelection = function (file) {
            AdminService.changeSelection(file);
        };
        $scope.remove = function () {
            FileService.remove(AdminService.getSelected());
            $scope.files = FileService.getFiles();
        }
    });

