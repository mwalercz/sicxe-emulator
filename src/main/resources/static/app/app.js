var app = angular
    .module('sicxe-sim',['ui.router','sicxe-admin'])
    .config(function($stateProvider,$urlRouterProvider, $locationProvider) {
        $urlRouterProvider.otherwise("/simulator");
        $stateProvider
            .state('common', {
                templateUrl: './templates/common.html',
                abstract: true
            })
            .state('simulator', {
                url: '/simulator',
                parent: 'common',
                templateUrl: './partials/simulator.html',
                controller: 'SimulatorController'
            })
            .state('about', {
                url: '/about',
                parent: 'common',
                templateUrl: './partials/about.html',
                controller: 'AboutController'
            })
            .state('tutorials', {
                url: '/tutorials',
                parent: 'common',
                templateUrl: './partials/tutorials.html',
                controller: 'TutorialsController'
            })
            .state('login', {
                url: '/login',
                parent: 'common',
                templateUrl: './partials/login.html',
                controller: 'LoginController'
            })
            .state('signup', {
                url: '/signup',
                parent: 'common',
                templateUrl: './partials/signup.html',
                controller: 'SignupController'
            })
        $locationProvider.html5Mode(true);

    });

