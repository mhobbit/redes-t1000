package com.tarea1;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

public class Main {

    public static void main(String[] args) throws Exception {
        // creacion del socket
        int puerto = 3003;
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
                            new OutputStreamWriter(clientSocket.getOutputStream())),
                    true);

            // Cada uno de los datos de tiempo que se lee desde la red es la referencia al flujo de escritura.
            // Los datos de lectura se volvió a exactamente el mismo cliente.
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println(s);

                out.write("HTTP/1.0 200 OK\r\n");
                out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
                out.write("Server: Apache/0.8.4\r\n");
                out.write("Content-Type: text/html\r\n");
                out.write("Content-Length: 59\r\n");
                out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
                out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
                out.write("\r\n");
                out.write("<TITLE>Ejemplo</TITLE>");
                out.write("<P>Hola Mundo</P>");
            }

            // cerrar flujo de datos
            System.err.println("La conexion con el cliente a terminado");
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}