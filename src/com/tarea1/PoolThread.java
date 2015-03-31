package com.tarea1;

import java.net.*;
import java.util.concurrent.Semaphore;

public class PoolThread extends Thread{
    public static Socket[] Pool;
    public static int Cantidad;
    public static int Maximo;
    private static Semaphore mutex = new Semaphore(1,true);

    public void PoolThread(int num){
        Pool = new Socket[num];
        Cantidad = 0;
        Maximo = Cantidad;
    }

    public int NewThread(){
        int estado = 0;
        try{
            mutex.acquire();
            if(Cantidad < Maximo){
                Cantidad++;
                start();
                estado = 1;
            }
            mutex.release();
        }
        catch(InterruptedException ex){
            return -1;
        }
        return estado;
    }

    @Override
    public void run() {
        try {
            System.out.println( "New Thread." );
            while(true){
                // codigo de socket
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
