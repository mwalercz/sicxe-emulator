/**
 * Created by maciek on 20.11.15.
 */
"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service('TutorialsService', function ($http, Upload) {
    var handler = this;
    handler.tutorials = []

    handler.getTutorials = function (callback) {
        $http.get("/tutorials/all").then(function (response) {
            handler.tutorials = response.data.tutorials;
            callback(handler.tutorials);
        });
    }

    handler.getTutorial = function (id, callback) {
        $http.get("/tutorials/get/".concat(id)).then(function(response){
            callback(response.data);
        })
    }


    handler.add = function (tutorial, file, callback) {
        if (file) {
            tutorial.upload = Upload.upload({
                url: "/tutorials/save",
                fields: {
                    author: tutorial.author,
                    content: tutorial.content,
                    title: tutorial.title
                },
                file: file
            });

            tutorial.upload.then(function (response) {
                if (response.data) {
                    callback(response.data)
                } else {
                    $rootScope.errors = response.data.errors;
                    console.log(response);
                }
            }, function (response) {
                console.log(response);

            });
        }

    }


});