/**
 * Created by maciek on 16.11.15.
 */
angular.module('sicxe-sim')
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
                    'mobile-header@main':{
                        templateUrl: './templates/mobile-header.html',
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
                    },
                    'output@simulator': {
                        templateUrl: './partials/output.html',
                        controller: 'OutputController'
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
                        templateUrl: './templates/tutorials.html',
                        controller: 'TutorialsController'
                    }

                }
            })
            .state('tutorial', {
                url: '/tutorial?id',
                parent: 'common',
                views:{
                    'content@main': {
                        templateUrl: './partials/tutorial.html',
                        controller: 'TutorialController'
                    },
                    'simulator@tutorial': {
                        templateUrl: './partials/simulator.html',
                        controller: 'SimulatorController'
                    },
                    'output@simulator': {
                        templateUrl: './partials/output.html',
                        controller: 'OutputController'
                    }
                }
            })
            .state('new-tutorial',{
                url: '/new-tutorial',
                parent: 'common',
                views: {
                    'content@main': {
                        templateUrl: "./partials/new-tutorial.html",
                        controller: 'NewTutorialController'
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


    });