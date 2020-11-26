package webserver;

import java.net.*;
import java.io.*;
import org.junit.*;
import java.nio.file.*;

public class ServerTesting {

    public int serverPort;
    public String serverFolder = "D:/An IV/VVS/Proiect_VVS_WebServer/HTML";
    public String serverMaintenanceFolder = "D:/An IV/VVS/Proiect_VVS_WebServer/HTML/ServerMaintenance";
    public String serverStatus;
    public String handledRequest;

    @Test
    public void modifyServerPort(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerPort(6531);
        Assert.assertEquals(6531,serverHTTP.serverPort);
    }

    @Test
    public void modifyServerStatus()
    {
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerStatus("STOP");
        Assert.assertEquals("STOP", serverHTTP.serverStatus);
    }

    @Test
    public void change_serverFolder_with_a_correct_folder(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerFolder("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test\\HTML");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test\\HTML",serverHTTP.serverFolder);
    }

    @Test
    public void change_serve_folder_with_an_incorrect_folder(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerFolder("WrongPath");
        Assert.assertNotEquals("WrongPath",serverHTTP.serverFolder);
    }

    @Test
    public void change_serverFolder_with_incorrect_folder_with_no_firstpage_folder(){
        //using HTML_test_1 folder
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerFolder("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_1\\HTML");
        Assert.assertNotEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_1\\HTML",serverHTTP.serverFolder);
    }

    @Test
    public void change_serverFolder_with_incorrect_folder_with_no_404notfound_folder(){
        //using HTML_test_2 folder
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerFolder("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_2\\HTML");
        Assert.assertNotEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_2\\HTML",serverHTTP.serverFolder);
    }

    @Test
    public void change_serverFolder_with_incorrect_folder_with_no_serverISdown_folder(){
        //using HTML_test_3 folder
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerFolder("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_3\\HTML");
        Assert.assertNotEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_3\\HTML",serverHTTP.serverFolder);
    }

    @Test
    public void change_serverFolder_with_incorrect_folder_with_no_ServerMaintenance_folder(){
        //using HTML_test_4 folder
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerFolder("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_4\\HTML");
        Assert.assertNotEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML_test_4\\HTML",serverHTTP.serverFolder);
    }

    @Test
    public void change_serverMaintenanceFolder_with_correct_folder(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerMaintenanceFolder("D:/An IV/VVS/Proiect_VVS_WebServer/HTML_test/HTML/ServerMaintenance");
        Assert.assertEquals("D:/An IV/VVS/Proiect_VVS_WebServer/HTML_test/HTML/ServerMaintenance",serverHTTP.serverMaintenanceFolder);
    }

    @Test
    public void change_serverMaintenanceFolder_with_incorrect_folder(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        serverHTTP.modifyServerMaintenanceFolder("D:/An IV/VVS/Proiect_VVS_WebServer/HTML_test_4/HTML/ServerMaintenance1231");
        Assert.assertNotEquals("D:/An IV/VVS/Proiect_VVS_WebServer/HTML_test_4/HTML/ServerMaintenance1231",serverHTTP.serverMaintenanceFolder);
    }

    @Test
    public void get_file_path_method_test_with_two_ok_parameters(){
        Path path = Paths.get("D:/An IV/VVS/Proiect_VVS_WebServer/HTML","/404notfound");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML\\404notfound",path.toString());
    }

    @Test
    public void get_file_path_method_on_an_existent_path_requested(){
        Path path = Paths.get("D:/An IV/VVS/Proiect_VVS_WebServer/HTML","/404notfound");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML\\404notfound",path.toString());
    }

    @Test
    public void get_file_path_method_test_with_first_parameter_and_second_none(){
        Path path = Paths.get("D:/An IV/VVS/Proiect_VVS_WebServer/HTML","");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML",path.toString());
    }

    @Test
    public void get_file_path_method_test_with_first_parameter_empty_and_second_ok(){
        Path path = Paths.get("","D:/An IV/VVS/Proiect_VVS_WebServer/HTML");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML",path.toString());
    }

    @Test
    public void get_file_path_method_test_with_first_parameter_null_and_second_ok(){
        Path path = Paths.get(null,"/VVS/Proiect_VVS_WebServer/HTML");
        Assert.assertEquals("null\\VVS\\Proiect_VVS_WebServer\\HTML",path.toString());
    }

    @Test(expected = NullPointerException.class)
    public void get_file_path_method_test_with_first_parameter_ok_and_second_null(){
        Path path = Paths.get("/VVS/Proiect_VVS_WebServer/HTML",null);
        Assert.assertEquals("\\VVS\\Proiect_VVS_WebServer\\HTML\\null",path.toString());
    }

    @Test
    public void get_file_path_first_page_html(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        Path path = serverHTTP.getFilePath("/");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML\\firstpage\\first_page.html",path.toString());
    }

    @Test
    public void get_file_path_custom_page_html(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        Path path = serverHTTP.getFilePath("/pages/content.html");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML\\pages\\content.html",path.toString());
    }

    @Test
    public void get_file_path_maintenance_page_html(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        Path path = serverHTTP.getFilePath("/maintenance");
        Assert.assertEquals("D:\\An IV\\VVS\\Proiect_VVS_WebServer\\HTML\\ServerMaintenance\\server_maintenance.html",path.toString());
    }

    @Test
    public void get_file_path_favicon_icon_file(){
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        Path path = serverHTTP.getFilePath("/favicon");
        Assert.assertEquals("\\favicon",path.toString());
    }

    @Test
    public void get_file_path_method_on_random_path(){
        Path path = Paths.get("macaroane/dulci");
        Assert.assertFalse(Files.exists(path));
    }

    @Test
    public void get_file_path_method_on_existing_path(){
        Path path = Paths.get("D:/An IV/VVS/Proiect_VVS_WebServer/src/webserver/Main.java");
        Assert.assertTrue(Files.exists(path));
    }

    @Test(expected = NullPointerException.class)
    public void get_path_with_null_parameter()
    {
        Path path = Paths.get(null);
        Assert.assertEquals(null,path);
    }

    @Test(expected =  IOException.class)
    public void send_response_method_client_disconected_and_exception_thrown() throws IOException {
        ServerHTTP serverHTTP = new ServerHTTP(serverPort,serverFolder,serverStatus);
        ServerSocket serverSocket = new ServerSocket(4000);
        Socket clientSocket = serverSocket.accept();
        Path path = Paths.get("D:/An IV/VVS/Proiect_VVS_WebServer/HTML/firstpage/first_page.html");

        OutputStream outputStream = clientSocket.getOutputStream();
        outputStream.close();
        serverHTTP.sendResponse(outputStream,"200 OK","text/html",Files.readAllBytes(path));

    }

    @Test
    public void handle_request_on_a_used_port(){
        ServerHTTP serverHTTP = new ServerHTTP(56256, serverFolder,"RUN");
        serverHTTP.handleRequest();
        serverHTTP.handleRequest();
        serverHTTP.handleRequest();
    }

}
