/**
 * Created by maciek on 20.11.15.
 */
"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service('TutorialsService', function () {
    var handler = this;
    handler.tutorials = [
        {
            id: 1,
            title: "Lorem ipsum",
            author: "mwal",
            content: "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit, sed do eiusmod " +
            "tempor incididunt ut labore et dolore magna aliqua." +
            " Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
            " laboris nisi ut aliquip ex ea commodo consequat. " +
            "Duis aute irure dolor in reprehenderit in voluptate " +
            "velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, " +
            "sunt in culpa qui officia deserunt mollit anim id est laborum."

        },{
            id: 2,
            title: "Mea culpa",
            author: "admin",
            content: "Sed ut perspiciatis unde omnis iste natus " +
            "error sit voluptatem accusantium doloremque laudantium, " +
            "totam rem aperiam, eaque ipsa quae ab illo inventore veritatis " +
            "et quasi architecto beatae vitae dicta sunt explicabo. " +
            "Nemo enim ipsam voluptatem quia voluptas sit aspernatur " +
            "aut odit aut fugit, sed quia consequuntur magni dolores eos qui " +
            "ratione voluptatem sequi nesciunt. Neque porro quisquam est," +
            " qui dolorem ipsum quia dolor sit amet, consectetur, " +
            "adipisci velit, sed quia non numquam eius modi tempora " +
            "incidunt ut labore et dolore magnam aliquam quaerat voluptatem. " +
            "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit " +
            "laboriosam, nisi ut aliquid ex ea commodi consequatur? " +
            "Quis autem vel eum iure reprehenderit " +
            "qui in ea voluptate velit esse quam nihil molestiae consequatur" +
            ", vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"
        }
    ]

    this.getTutorials = function(){

        return handler.tutorials;
    }

    this.getTutorial = function(id){
        for(var i = 0; i < handler.tutorials.length; i++){
            if(id == handler.tutorials[i].id) return handler.tutorials[i];
        }
        return null;
    }



});