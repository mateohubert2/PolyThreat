package webserver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class WebServerResponse {
    private HttpExchange exchange;

    public WebServerResponse(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public void ok(String message) {
        this.send(200, message);
    }

    public void notFound(String message) {
        this.send(404, message);
    }

    public void serverError(String message) {
        this.send(500, message);
    }

    public void json(Object object) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        this.send(200, gson.toJson(object));
    }

    public void send(int statusCode, String message) {
        this.initCors();

        byte[] bytes = message.getBytes();

        try {
            exchange.sendResponseHeaders(statusCode, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (IOException e) {

        }
    }

    public void sendFile(int statusCode, String fileName) {
        this.initCors();
        InputStream inputStream = null;
        byte[] bytes = null;
        int indexPoint = fileName.lastIndexOf('.');

        String extension = "";
        if (indexPoint > 0) {
            extension = fileName.substring(indexPoint + 1);
        }
        System.out.println(fileName);
        try {
            inputStream = new FileInputStream("../Frontend/" + fileName);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        if(extension.equals("js")){
            exchange.getResponseHeaders().set("Content-Type", "application/javascript");
            bytes = fileToBytes(inputStream);
        }
        if(extension.equals("ico")){
            exchange.getResponseHeaders().set("Content-Type", "image/x-icon");
            try {
                bytes = inputStream.readAllBytes();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        if(extension.equals("png")){
            exchange.getResponseHeaders().set("Content-Type", "image/png");
            try {
                bytes = inputStream.readAllBytes();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        if(extension.equals("css")){
            exchange.getResponseHeaders().set("Content-Type", "text/css" );
            try {
                bytes = inputStream.readAllBytes();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        if(extension.equals("html")){
            bytes = fileToBytes(inputStream);
        }
        try {
            exchange.sendResponseHeaders(statusCode, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
         } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static byte[] fileToBytes(InputStream inputStream){
        byte[] buffer = new byte[1024];
        int bytesRead;
        StringBuilder content = new StringBuilder();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                content.append(new String(buffer, 0, bytesRead));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return content.toString().getBytes();
    }

    private void initCors() {
        Headers headers = this.exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", "*");
        headers.add("Access-Control-Allow-Methods", "*");
    }

    public OutputStream openSSEStream() {

        try {
            this.initCors();
            
            Headers responseHeaders = this.exchange.getResponseHeaders();
            responseHeaders.add("Content-Type", "text/event-stream");
            responseHeaders.add("Connection", "keep-alive");
            responseHeaders.add("Transfer-Encoding", "chunked");
            responseHeaders.add("X-Powered-By", "Native Application Server");

            this.exchange.sendResponseHeaders(200, 0);
        }
        catch(Exception e)
        {

        }

        return this.exchange.getResponseBody();
    }
}
