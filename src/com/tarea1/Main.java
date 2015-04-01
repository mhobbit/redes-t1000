package com.tarea1;

import java.net.*;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        PoolThread Servidor = new PoolThread(3);

        int Puerto = 8080;
        ServerSocket serverSocket = new ServerSocket(Puerto);
        System.err.println("Servidor esperando en el puerto " + Puerto + "...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Servidor.Asignar(clientSocket);
        }
    }
}