package com.tarea1;

import javafx.collections.transformation.SortedList;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class HandlerThread extends Thread {
    public int ID;
    public Boolean Estado;
    public Socket clientSocket;
    String route;

    public HandlerThread(int num){
        ID = num;
        Estado = false;
        start();
    }

    public void AsignarTarea(Socket cliente, String route1){
        clientSocket = cliente;
        Estado = true;
        route = route1;
    }

    private void WriteHeader(PrintWriter out, int code, String status, String[] params){
        out.write("HTTP/1.0 " + code + " " + status + "\r\n");
        for(String param: params)
        {
            out.write(param);
        }
        out.write("\r\n");
    }

    private void ConnectionThread(Socket connection){
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            OutputStream output = new BufferedOutputStream(connection.getOutputStream());
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream(), true);

            //Extrayendo primera linea del request
            String request = input.readLine();
            int contentLength = 0;
            System.out.println(request);

            //Extraer datos del request
            while (true) {
                String line = input.readLine();
                //System.out.println(line);
                if (line.contains("Content-Length")){
                    contentLength = Integer.parseInt(line.split(":")[1].replace(" ", ""));
                }
                if (line==null || line.length()==0)
                    break;
            }
            StringBuilder requestContent = null;
            if (contentLength != 0){
                requestContent = new StringBuilder();
                for (int i = 0; i < contentLength; i++){
                    requestContent.append((char) input.read());
                }
            }
            String[] subdivitions =  request.split(" ");//du du du, du du du

            //AQUI HACER EL ARCHIVO DE LOG DE USUARIOS (NO CONFUNDIR CON LOGIN DE USUARIOS)
            String connectionIP = connection.getInetAddress().toString();
            String logLine = connectionIP.substring(1, connectionIP.length())+ " " +subdivitions[1];
            while(!Main.LogUser(logLine));

            //HTTP Handlers
            if(subdivitions[0].equals("GET")){
                //HTTP Handlers para GET
                if(subdivitions[1].equals("/")){
                    try{
                        String path = route + "/main.html";
                        File f = new File(path);
                        InputStream file = new FileInputStream(f);
                        WriteHeader(printWriter, 200, "OK", new String[] {});
                        sendFile(file, output);
                    }catch (IOException e) {
                        WriteHeader(printWriter, 404, "Not Found", new String[] {});
                    }
                }
                else if(subdivitions[1].equals("/login")){
                    try{
                        String path = route + "/login.html";
                        File f = new File(path);
                        InputStream file = new FileInputStream(f);
                        WriteHeader(printWriter, 200, "OK", new String[] {});
                        sendFile(file, output);
                    }catch (IOException e) {
                        WriteHeader(printWriter, 404, "Not Found", new String[] {});
                    }
                }
                else if(subdivitions[1].equals("/home_old")){
                    WriteHeader(printWriter, 301, "Moved Permanently", new String[]{"Location: /\r\n"});
                }
                else if(subdivitions[1].equals("/secret")){
                    try{
                        System.out.println("lista: "+Main.authClients.toString());
                        if (Main.authClients.contains(connectionIP)){
                            String path = route + "/secret.html";
                            File f = new File(path);
                            InputStream file = new FileInputStream(f);
                            WriteHeader(printWriter, 200, "OK", new String[] {});
                            sendFile(file, output);
                        }
                        else {
                            String path = route + "/forbidden.html";
                            File f = new File(path);
                            InputStream file = new FileInputStream(f);
                            WriteHeader(printWriter, 403, "Forbidden", new String[] {});
                            sendFile(file, output);
                        }
                    }catch (IOException e) {
                        WriteHeader(printWriter, 404, "Not Found", new String[] {});
                    }
                }
                else{
                    WriteHeader(printWriter, 404, "Not Found", new String[] {});
                }
            }
            else if(subdivitions[0].equals("POST")){
                //HTTPHandlers para POST
                if(subdivitions[1].equals("/secret")){
                    System.out.println(requestContent);
                    assert requestContent != null;
                    if (requestContent.toString().equals("user=root&pass=laboratorio1")) {
                        System.out.println("Usuario logueado exitosamente." + connectionIP);
                        Main.authClients.add(connectionIP);
                        System.out.println("lista1: "+Main.authClients.toString());
                    }
                    WriteHeader(printWriter, 301, "Moved Permanently", new String[]{"Location: /secret\r\n"});
                }
            }
            else{
                WriteHeader(printWriter, 404, "Not Found", new String[]{});
            }

            printWriter.flush();
            output.close();
            connection.close();

            System.out.println("La conexion con el cliente a terminado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(InputStream file, OutputStream out)
    {
        try {
            byte[] buffer = new byte[1000];
            while (file.available() > 0)
                out.write(buffer, 0, file.read(buffer));
        } catch (IOException e) {
            System.err.println(e); }
    }

    @Override
    public void run() {
        try {
            while(true){
                if(Estado){
                    ConnectionThread(clientSocket);
                    Estado = false;
                    PoolThread.Disponibles[ID] = true;
                }
                else{
                    //System.out.println(ID);
                }
                try {
                    //Forzar el uso de más threads
                    Thread.sleep(100);
                } catch(InterruptedException ex){}
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
