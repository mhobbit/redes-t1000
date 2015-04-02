package com.tarea1;

import java.net.*;
import java.io.*;
import java.util.*;

public class Main {
    static List<String> authClients = new ArrayList<String>();
    static boolean writingLog = false;
    public static boolean LogUser(String logLine){
        PrintWriter pw = null;
        try {
            if (!writingLog){
                writingLog = true;
                pw = new PrintWriter(new FileOutputStream(new File("log.txt"), true));
                pw.append(logLine);
                pw.append("\n");
                pw.close();
                writingLog = false;
                return true;
            }
            else {
                pw.close();
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            pw.close();
            return true;
        }
    }

    public static void main(String[] args) throws Exception {
        PoolThread Servidor = new PoolThread(3);

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
            System.out.println("Servidor esperando en el puerto " + port + "...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket clientSocket = socket.accept();
            Servidor.Asignar(clientSocket, route);
        }
    }
}