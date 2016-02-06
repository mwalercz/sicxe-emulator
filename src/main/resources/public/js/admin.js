var app = angular
    .module('sicxe-admin', ['ui.router'])
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/admin/users");
        $stateProvider
            .state('admin', {
                templateUrl: './templates/admin.html',
                abstract: true
            })
            .state('admin/users', {
                parent: 'admin',
                url: '/admin/users',
                views: {

                    'content': {
                        templateUrl: './partials/admin/users.html',
                        controller: 'UsersController'
                    }
                }
            })
            .state('admin/files', {
                parent: 'admin',
                url: '/admin/files',
                views: {
                    'content': {
                        templateUrl: './partials/admin/files.html',
                        controller: 'FilesController'
                    }
                }
            })
            .state('admin/articles', {
                parent: 'admin',
                url: '/admin/articles',
                views: {
                    'content':{
                        templateUrl: './partials/admin/articles.html',
                        controller: 'ArticlesController'
                    }
                }
            });

    });