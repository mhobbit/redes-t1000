package com.tarea1;

import java.io.*;
import java.net.*;
import java.util.*;

class ConnectionThread {
    Socket connection;

    public ConnectionThread(Socket connection) {
        this.connection = connection;
    }

    public void runThread(){
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            OutputStream output = new BufferedOutputStream(connection.getOutputStream());
            PrintStream printOutput = new PrintStream(output);

            //leyendo el request
            String[] request = input.readLine().split(" ");

            if(request.length != 3) {
                errorReport(printOutput, connection, "400", "Bad Request", "Your browser sent a request that ");
            }
            //HTTP HANDLERS!
            errorReport(printOutput, connection, "200", "TEST", "test completo!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void errorReport(PrintStream pout, Socket connection,
                                    String code, String title, String msg) {
        pout.print("HTTP/1.0 " + code + " " + title + "\r\n" +
                "\r\n" +
                "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\r\n" +
                "<TITLE>" + code + " " + title + "</TITLE>\r\n" +
                "</HEAD><BODY>\r\n" +
                "<H1>" + title + "</H1>\r\n" + msg + "<P>\r\n" +
                "<HR><ADDRESS>FileServer 1.0 at " +
                connection.getLocalAddress().getHostName() +
                " Port " + connection.getLocalPort() + "</ADDRESS>\r\n" +
                "</BODY></HTML>\r\n");
    }
}
