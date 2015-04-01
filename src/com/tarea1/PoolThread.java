package com.tarea1;

import java.util.concurrent.Semaphore;
import java.net.*;

public class PoolThread {
    private HandlerThread[] Elementos;
    public static Boolean[] Disponibles;
    private Semaphore mutex = new Semaphore(1,true);
    private int Cantidad;
    private int Totales;

    //Constructor
    public PoolThread(int num) {
        Totales = num;
        Cantidad = 0;
        Elementos = new HandlerThread[num];
        Disponibles = new Boolean[num];
        for(int i = 0; i < num; i++){
            Elementos[i] = new HandlerThread(i);
            Disponibles[i] = true;
        }
    }

    public void Asignar(Socket clientSocket, String route){
        //Busca un Thread disponi
        for(int i = 0; i < Totales; i++){
            if(Disponibles[i]){
                Disponibles[i] = false;
                Elementos[i].AsignarTarea(clientSocket, route);
                break;
            }
        }
    }
}
