package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import webserver.WebServerContext;
import webserver.WebServerResponse;

public class ScriptsController {
    public static void initialScripts(WebServerContext context){
        StringBuilder output = new StringBuilder();
        WebServerResponse response = context.getResponse();
        try {
            String scriptPath = "/home/azymut/Desktop/4A/PolyThreat/Scripts/NetworkInformations.sh";
            
            @SuppressWarnings("deprecation")
            Process process = Runtime.getRuntime().exec(scriptPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            response.json(output);        
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
