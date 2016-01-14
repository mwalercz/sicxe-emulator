"use strict";
var sicxe = angular.module("sicxe-sim");

sicxe.service("FileService", function () {

    var handler = this;
    handler.files = [
        {
            id: 1,
            username: 'mwal',
            filename: 'lda',
            size: '1kb'

        },{
            id: 2,
            username: 'user',
            filename: 'add',
            size: '2kb'

        }
    ];
    handler.getFiles = function(){
        return handler.files;
    };
    handler.getUserFiles = function(user){
        var userFiles = [];
        for(var i = 0; i < handler.files.length; i++) {
            if(handler.files[i].username == user.username){
                userFiles.push(handler.files[i]);
            }

        }
        return userFiles;
    };
    handler.remove = function(selected){
        for(var i = 0; i < handler.files.length; i++){
            for(var j = 0; j < selected.length; j++){
                if(handler.files[i].id == selected[j].id){
                    handler.files.splice(i, 1);
                }

            }
        }
    }

});
