package controllers;

import webserver.WebServerContext;
import webserver.WebServerRequest;
import webserver.WebServerResponse;

public class HomeController {

    public static void sendContent(WebServerContext context){
        WebServerResponse response = context.getResponse();
        WebServerRequest request = context.getRequest();
        String file = request.getParam("file");
        response.sendFile(200, file);
    }

    public static void sendSpecificContent(WebServerContext context){
        WebServerResponse response = context.getResponse();
        WebServerRequest request = context.getRequest();
        String file = request.getPath();
        response.sendFile(200, file);
    }
}