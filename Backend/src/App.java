import controllers.HomeController;
import controllers.LoginController;
import controllers.ScriptsController;
import webserver.WebServer;
import webserver.WebServerContext;

public class App {
    public static void main(String[] args) throws Exception {
       WebServer webserver = new WebServer();
       webserver.listen(8080);
       webserver.getRouter().get("/:file", (WebServerContext context) -> { HomeController.sendContent(context); });
       webserver.getRouter().post("/verifylogs", (WebServerContext context) -> { LoginController.checkLogs(context); });
       webserver.getRouter().post("/verifyconnectid", (WebServerContext context) -> { LoginController.checkConnectID(context); });
       webserver.getRouter().post("/initialscripts", (WebServerContext context) -> { ScriptsController.initialScripts(context); });
       webserver.getRouter().addExecuteCommandRoute();
    }
}