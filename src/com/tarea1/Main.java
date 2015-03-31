package com.tarea1;

import java.io.IOException;
import java.net.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        //Inicializando socket en puerto 8080
        int port = 8080;
        int route = "www"
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
                    //crear nuevo thread con la conexión

                    connection = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
