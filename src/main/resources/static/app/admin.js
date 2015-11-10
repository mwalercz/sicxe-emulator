var app = angular
    .module('sicxe-admin',['ui.router'])
    .config(function($stateProvider, $locationProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/admin");
        $stateProvider
            .state('admin', {
                url: '/admin',
                templateUrl: './templates/admin.html'
            })
        $locationProvider.html5Mode(true);

    });