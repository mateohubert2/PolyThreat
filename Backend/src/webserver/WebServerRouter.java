package webserver;

import java.io.*;
import java.util.ArrayList;

public class WebServerRouter {
    private ArrayList<WebServerRoute> routes;
    private static String currentDirectory = System.getProperty("user.home");

    public WebServerRouter() {
        this.routes = new ArrayList<>();
    }

    public void get(String path, WebServerRouteHandler handler) {
        this.addRoute("GET", path, handler);
    }

    public void post(String path, WebServerRouteHandler handler) {
        this.addRoute("POST", path, handler);
    }

    public void put(String path, WebServerRouteHandler handler) {
        this.addRoute("PUT", path, handler);
    }

    public void delete(String path, WebServerRouteHandler handler) {
        this.addRoute("DELETE", path, handler);
    }

    private void addRoute(String method, String path, WebServerRouteHandler handler) {
        this.routes.add(new WebServerRoute(method, path, handler));
    }

    public WebServerRoute findRoute(WebServerRequest request) throws WebServerRouteNotFoundException {
        String method = request.getMethod();
        String path = request.getPath();

        for (WebServerRoute route : this.routes) {
            if (route.match(method, path))
                return route;
        }

        throw new WebServerRouteNotFoundException(method, path);
    }

    public void addExecuteCommandRoute() {
        this.post("/execute", (WebServerContext context) -> {
            WebServerRequest request = context.getRequest();
            CommandRequest commandRequest = request.extractBody(CommandRequest.class);
            String response = executeCommand(commandRequest.getCommand(), commandRequest.getInput());
            context.getResponse().ok(response);
        });
    }

    private String executeCommand(String command, String input) {
        StringBuilder output = new StringBuilder();
        try {
            if (command.startsWith("sudo")) {
                output.append("Error: 'sudo' commands are not supported.\n");
            } else if (command.startsWith("cd ")) {
                String[] parts = command.split(" ", 2);
                String newDir = parts.length > 1 ? parts[1] : System.getProperty("user.home");
                File newDirFile = new File(newDir);
    
                if (!newDirFile.isAbsolute()) {
                    newDirFile = new File(currentDirectory, newDir);
                }
    
                if (newDirFile.isDirectory()) {
                    currentDirectory = newDirFile.getAbsolutePath();
                    output.append("Changed directory to ").append(currentDirectory).append("\n");
                } else {
                    output.append("Error: Not a valid directory.\n");
                }
            } else if (command.equals("cd") || command.equals("cd ~")) {
                currentDirectory = System.getProperty("user.home");
                output.append("Changed directory to ").append(currentDirectory).append("\n");
            } else {
                ProcessBuilder builder = new ProcessBuilder("bash", "-c", command);
                builder.directory(new File(currentDirectory));
                Process process = builder.start();
    
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
    
                while ((line = errorReader.readLine()) != null) {
                    output.append(line).append("\n");
                }
    
                writer.close();
                reader.close();
                errorReader.close();
                process.waitFor();
            }
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage()).append("\n");
        }
        return output.toString();
    }
       

    static class CommandRequest {
        private String command;
        private String input;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }
    }
}