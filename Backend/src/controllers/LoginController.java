package controllers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonObject;

import webserver.WebServerContext;
import webserver.WebServerRequest;
import webserver.WebServerResponse;

public class LoginController {
    private static Map<String, String> validUsers = new HashMap<>();
    private static ArrayList<String> validConnectID = new ArrayList<>();
    private static boolean isMatchFound = false;
    private static final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int length = 128;
    private static final int INTERVAL_MINUTES = 2;
    private static boolean alreadyClear = false;

    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    static Runnable clearTask = () -> {
        validConnectID.clear();
        System.out.println("ArrayList vidée. Contenu actuel: " + validConnectID);
    };

    static{
        validUsers.put("admin", "admin");
    }

    public static void checkLogs(WebServerContext context){
        WebServerResponse response = context.getResponse();
        WebServerRequest request = context.getRequest();
        JsonObject jsonObject = request.extractBody(JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        for (Map.Entry<String, String> entry : validUsers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if(key.equals(username) && value.equals(password)){
                isMatchFound = true;
                break;
            }
        }
        if(isMatchFound){
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder(length);

            for (int i = 0; i < length; i++){
                int index = random.nextInt(alphanumeric.length());
                sb.append(alphanumeric.charAt(index));
            }
            validConnectID.add(sb.toString());
            response.json(sb.toString());
            isMatchFound = false;
        }
        else{
            response.notFound("Not a valid logs");
        }
    }

    public static void checkConnectID(WebServerContext context){
        WebServerResponse response = context.getResponse();
        WebServerRequest request = context.getRequest();
        JsonObject jsonObject = request.extractBody(JsonObject.class);
        String connectID = jsonObject.get("connectID").getAsString();
        boolean correctID = false;
        for (int i = 0; i < validConnectID.size(); i++){
            if(connectID.equals(validConnectID.get(i))){
                correctID = true;
                break;
            }
        }
        if(correctID){
            response.ok("correct ID");
            correctID = false;
        }
        else{
            response.notFound("Not a valid ID");
        }
        if(!alreadyClear){
            scheduler.scheduleAtFixedRate(clearTask, 0, INTERVAL_MINUTES, TimeUnit.MINUTES);
            alreadyClear = true;
        }
    }
}
