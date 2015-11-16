/**
 * Created by maciek on 16.11.15.
 */
"use strict"

var sicxe = angular.module("sicxe-sim");

sicxe.service("StompService", function ($stomp) {
    var handler = this;
    handler.message = {name: "Maciek"};
    handler.payload;
    handler.getPayload = function(){
        return handler.payload;
    };
    handler.connect = function (scopeMessage) {
        $stomp
            .connect('/update', {})
            .then(function (frame) {
                handler.log = +"connected" + frame;
                var subscription =
                    $stomp.subscribe('/topic/greetings', function (payload, headers, res) {
                        scopeMessage = payload;
                        console.log(payload + headers);
                    }, {});

                $stomp.send('/app/hello', handler.message);

                //subscription.unsubscribe();

                $stomp.disconnect(function () {
                    handler.error = "disconnected"
                });
            }, function (error) {
                handler.error = error;
            });

    };
    handler.testWebsocket = function(scopeMessage) {
        $stomp.setDebug(function (args) {
            handler.log =  args + '\n';

        });
        return handler.connect(scopeMessage);
    };

});

