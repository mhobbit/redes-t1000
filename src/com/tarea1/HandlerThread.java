package com.tarea1;

import java.io.*;
import java.net.*;

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

            //leyendo el request
            String request = input.readLine();
            String[] subdivitions =  request.split(" ");
            System.out.println(request);

            if(subdivitions[0].equals("GET")) {
                while (true) {
                    String lines = input.readLine();
                    System.out.println(lines);
                    if (lines == null || lines.length() == 0)
                        break;
                }
            }
            else{ //POST
                int contentLength = -1;
                while (true) {
                    String line = input.readLine();
                    System.out.println(line);

                    String contentLengthStr = "Content-Length: ";
                    if (line.startsWith(contentLengthStr)) {
                        contentLength = Integer.parseInt(line.substring(contentLengthStr.length()));
                    }

                    if (line.length() == 0) {
                        break;
                    }
                }

                char[] content = new char[contentLength];
                input.read(content);
                System.out.println(new String(content));
            }


            if(subdivitions[0].equals("GET")){
                //HTTPHandlers para GET
                if(subdivitions[1].equals("/")){
                    System.out.println("ruta: "+route);
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
                        String path = route + "/secret.html";
                        File f = new File(path);
                        InputStream file = new FileInputStream(f);
                        WriteHeader(printWriter, 403, "Forbidden", new String[] {});
                        sendFile(file, output);
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
            }
            else{
                WriteHeader(printWriter, 404, "Not Found", new String[] {});
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
