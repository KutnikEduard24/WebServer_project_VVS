package webserver;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {
        ServerHTTP server = new ServerHTTP(4000, "D:/An IV/VVS/Proiect_VVS_WebServer/HTML", "RUN");
        while (true) {
            server.handleRequest();
        }
    }
}

