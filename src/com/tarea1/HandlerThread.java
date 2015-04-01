package com.tarea1;

import java.io.*;
import java.net.*;

public class HandlerThread extends Thread {
    public int ID;
    public Boolean Estado;
    public Socket clientSocket;

    public HandlerThread(int num){
        ID = num;
        Estado = false;
        start();
    }

    public void AsignarTarea(Socket cliente){
        clientSocket = cliente;
        Estado = true;
    }

    @Override
    public void run() {
        try {
            while(true){
                if(Estado){
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream())
                    );
                    PrintWriter out = new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(clientSocket.getOutputStream())),true);

                    String cabecera = in.readLine();
                    String[] division = cabecera.split(" ");
                    System.out.println(ID + " - " + cabecera);

                    if(division[1].equals("/")){
                        out.write("HTTP/1.1 200 OK\r\n");
                        out.write("Content-Type: text/html\r\n");
                        out.write("\r\n");
                        out.write("<TITLE>Tarea Redes</TITLE>");
                        out.write("<P>Bienvenidos al Sitio</P>");
                    }
                    else if(division[1].equals("/home_old")){
                        out.write("HTTP/1.1 301 Moved Permanently\r\n");
                        out.write("Location: /\r\n");
                        out.write("\r\n");
                    }
                    else if(division[1].equals("/secret")){
                        out.write("HTTP/1.1 403 Forbidden\r\n");
                        out.write("Content-Type: text/html\r\n");
                        out.write("\r\n");
                        out.write("<TITLE>Tarea Redes</TITLE>");
                        out.write("<P>Pagin Secreta</P>");
                    }
                    else{
                        out.write("HTTP/1.1 404 OK\r\n");
                        out.write("Content-Type: text/html\r\n");
                        out.write("\r\n");
                    }

                    out.flush();
                    out.close();
                    in.close();
                    clientSocket.close();

                    Estado = false;
                    PoolThread.Disponibles[ID] = true;
                }
                else{
                    //System.out.println(ID);
                }
                try {
                    Thread.sleep(100);
                } catch(InterruptedException ex){}
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
