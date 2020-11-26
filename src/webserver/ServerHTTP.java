
package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public final class ServerHTTP extends Thread {

    /**
     * Declarations.
     */
    public int serverPort;
    public String serverFolder = "D:/An IV/VVS/Proiect_VVS_WebServer/HTML";
    public String serverMaintenanceFolder = "D:/An IV/VVS/Proiect_VVS_WebServer/HTML/ServerMaintenance";
    public String serverStatus;
    public String handledRequest="";

    public ServerHTTP(int serverPort, String serverFolder, String serverStatus) {
        this.serverPort = serverPort;
        this.serverFolder = serverFolder;
        this.serverStatus = serverStatus;
    }

    public void modifyServerPort(int newServerPort) {
        serverPort = newServerPort;
    }

    public void modifyServerStatus(String newServerStatus) {
        serverStatus = newServerStatus;
    }

    public void modifyServerFolder(String newServerFolder) {
        if (Files.exists(Paths.get(newServerFolder, "/firstpage/first_page.html"))
                        && Files.exists(Paths.get(newServerFolder, "/404notfound/404_page.html"))
                        && Files.exists(Paths.get(newServerFolder, "/serverISdown/server_down.html"))
                        && Files.exists(Paths.get(newServerFolder, "/ServerMaintenance/server_maintenance.html"))
                        && Files.exists(Paths.get(newServerFolder, "/favicon.ico"))) {
            serverFolder = newServerFolder;
        } else {
            System.err.println("Error while modifying server folder");
        }
    }

    public void modifyServerMaintenanceFolder(String newMaintenanceServerFolder) {
        if (Files.exists(Paths.get(newMaintenanceServerFolder, "/server_maintenance.html"))) {
            serverMaintenanceFolder = newMaintenanceServerFolder;
        } else {
            System.out.println("Error while modifying maintenance folder");
        }
    }

    public Path getFilePath(String path) {

        if ("/".equals(path)) {
            path = "/firstpage/first_page.html";
            return Paths.get(serverFolder, path);
        }
        else if("/pages/content.html".equals(path)) {
            path = "/pages/content.html";
            return Paths.get(serverFolder,path);
        }
        else if("/maintenance".equals(path)) {
            path = "/ServerMaintenance/server_maintenance.html";
            return Paths.get(serverFolder,path);
        }
        else if ("/favicon.ico".equals(path)) {
            return Paths.get(serverFolder, path);
        } else
            return Paths.get(path);
    }

    public void sendResponse(OutputStream outputStream, String status, String contentType, byte[] content) throws IOException {
        outputStream.write(("HTTP/1.1 \r\n" + status).getBytes());
        outputStream.write(("ContentType: " + contentType + "\r\n").getBytes());
        outputStream.write("\r\n".getBytes());
        outputStream.write(content);
        outputStream.write("\r\n\r\n".getBytes());
    }

    public void clientHandler(Socket clientSocket) throws IOException {

        System.out.println("New Communication Thread Started");
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
            String line;
            StringBuilder requestString = new StringBuilder();
            while (!(line = in.readLine()).isBlank()) {
                requestString.append(line + "\r\n");

                //outputStream.write(Integer.parseInt(line));
                if (line.trim().equals("")) {
                    break;
                }
            }
            String request = requestString.toString();
            String[] requestsLines = request.split("\r\n");
            String[] requestLine = requestsLines[0].split(" ");
            String path = requestLine[1];
            System.out.println("path is ----> : " + path);
            Path filePath = getFilePath(path);
            handledRequest = handledRequest + " " + filePath.toString();
            System.out.println("path is ----> : " + handledRequest);
            String contentType = Files.probeContentType(filePath);
            if (serverStatus.equals("RUN")) {
                if (Files.exists(filePath)) {
                    sendResponse(outputStream, "200 OK", contentType, Files.readAllBytes(filePath));
                } else {
                    // 404
                    byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
                    sendResponse(outputStream, "404 Not Found", "text/html", notFoundContent);
                }
            } else if (serverStatus.equals("Maintenance")) {
                if (contentType.contains("html")) {
                    sendResponse(outputStream, "Service is Unavailable 503", contentType,
                            Files.readAllBytes(Paths.get(serverFolder, "/ServerMaintenance/server_maintenance.html")));
                } else {
                    sendResponse(outputStream, "200 OK", contentType, Files.readAllBytes(filePath));
                }
            } else {
                if (contentType.contains("html")) {
                    sendResponse(outputStream, "Service is Unavailable 503", contentType,
                            Files.readAllBytes(Paths.get(serverFolder, "/serverISdown/server_down.html")));
                } else {
                    sendResponse(outputStream, "200 OK", contentType, Files.readAllBytes(filePath));
                }
            }
            outputStream.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }

    public void handleRequest() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket clientSocket = serverSocket.accept();
            clientHandler(clientSocket);
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Can't connect to port:" + serverPort);
        }
    }
}







