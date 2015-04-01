package com.tarea1;

import java.io.*;
import java.net.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        //parseando argumentos:
        if (args.length!=2) {
            System.out.println("Usage: java FileServer <port> <wwwhome>");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0]);
        String route = args[1];

        //Inicializando socket en puerto dado
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            Socket connection = null;
            try {
                connection = socket.accept();
                if (connection != null) {
                    //enviar conección al thread pool
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    OutputStream output = new BufferedOutputStream(connection.getOutputStream());
                    PrintWriter printWriter = new PrintWriter(connection.getOutputStream(), true);

                    //leyendo el request
                    String request = input.readLine();
                    System.out.println(request);

                    while (true) {
                        String misc = input.readLine();
                        if (misc==null || misc.length()==0)
                            break;
                    }

                    String path = route + "/" + "main.html";
                    File f = new File(path);
                    InputStream file = new FileInputStream(f);
                    printWriter.write("HTTP/1.0 200 OK\r\n" +
                            "Date: " + new Date() + "\r\n" +
                            "Server: SuperServer 1.0\r\n\r\n");
                    sendFile(file, output);


                    System.out.println("La conexion con el cliente a terminado");

                    printWriter.flush();
                    output.close();
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void sendFile(InputStream file, OutputStream out)
    {
        try {
            byte[] buffer = new byte[1000];
            while (file.available()>0)
                out.write(buffer, 0, file.read(buffer));
        } catch (IOException e) {
            System.err.println(e); }
    }

}
