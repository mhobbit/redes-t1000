package com.tarea1;

import java.net.*;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // creacion del socket
        int puerto = 3005;
        ServerSocket serverSocket = new ServerSocket(puerto);
        System.err.println("Servidor que se ejecuta en el puerto : " + puerto);

        // esperar repetidamente establecer conexiones
        while (true) {
            // Esperando una petición de un  cliente
            Socket clientSocket = serverSocket.accept();
            System.err.println("Nuevo cliente conectado");

            // la apertura de un flujo converation
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream())),true);

            // Cada uno de los datos de tiempo que se lee desde la red es la referencia al flujo de escritura.
            // Los datos de lectura se volvió a exactamente el mismo cliente.

            String s = new String();
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }

            out.write("HTTP/1.1 200 OK\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("\r\n");
            out.write("<TITLE>Ejemplo</TITLE>");
            out.write("<P>Hola Mundo</P>");

            // cerrar flujo de datos
            System.err.println("La conexion con el cliente a terminado");
            out.flush();
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}