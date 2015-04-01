package com.tarea1;

import java.net.*;
import java.io.*;
import java.util.*;

public class Main {

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