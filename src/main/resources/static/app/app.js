var app = angular
        .module('sicxe-sim', ['ngMessages', 'ui.router', 'sicxe-admin'])
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state('main', {
                    templateUrl: './templates/common.html',
                    abstract: true,
                })
                .state('common', {
                    parent: 'main',
                    abstract: true,
                    views: {
                        'header@main': {
                            templateUrl: './templates/header.html',
                            controller: 'HeaderController'
                        },
                        'footer@main': {
                            templateUrl: './templates/footer.html'
                        }
                    }

                })
                .state('simulator', {
                    url: '/',
                    parent: 'common',
                    views: {
                        'content@main': {
                            templateUrl: './partials/simulator.html',
                            controller: 'SimulatorController'
                        }
                    }

                })

                .state('about', {
                    url: '/about',
                    parent: 'common',
                    views: {
                        'content@main': {
                            templateUrl: './partials/about.html',
                            controller: 'AboutController'
                        }
                    }
                })
                .state('tutorials', {
                    url: '/tutorials',
                    parent: 'common',
                    views: {
                        'content@main': {
                            templateUrl: './partials/tutorials.html',
                            controller: 'TutorialsController'
                        }
                    }
                })
                .state('profile', {
                    url: '/profie',
                    parent: 'common',
                    views: {
                        'content@main': {
                            templateUrl: './partials/profile.html',
                            controller: 'ProfileController'
                        }
                    }
                })
                .state('login', {
                    url: '/login',
                    parent: 'common',
                    views: {
                        'content@main': {
                            templateUrl: './partials/login.html',
                            controller: 'LoginController'
                        }
                    }
                })
                .state('signup', {
                    url: '/signup',
                    parent: 'common',
                    views: {
                        'content@main': {
                            templateUrl: './partials/signup.html',
                            controller: 'SignupController'
                        }
                    }
                })

            $urlRouterProvider.otherwise('/');


        })
    ;

