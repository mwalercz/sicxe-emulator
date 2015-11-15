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
                    'header': {
                        templateUrl: './partials/admin/users-header.html'
                    },
                    'content': {
                        templateUrl: './partials/admin/users-content.html',
                        controller: 'UsersController'
                    }
                }
            })
            .state('admin/files', {
                parent: 'admin',
                url: '/admin/files',
                views: {
                    'header': {
                        templateUrl: './partials/admin/files-header.html'
                    },
                    'content': {
                        templateUrl: './partials/admin/files-content.html',
                        controller: 'FilesController'
                    }
                }
            })
            .state('admin/articles', {
                parent: 'admin',
                url: '/admin/articles',
                views: {
                    'header':{
                        templateUrl: './partials/admin/articles-header.html'
                    },
                    'content':{
                        templateUrl: './partials/admin/articles-content.html',
                        controller: 'ArticlesController'
                    }
                }
            });

    });