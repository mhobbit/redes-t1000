package com.tarea1;

import java.net.*;
import java.io.*;

public class Main {
    static boolean writingLog1 = false;
    static boolean writingLog2 = false;

    //Bonus: Creacion archivo log
    public static boolean LogUser(String logLine){
        PrintWriter pw = null;
        try {
            if (!writingLog1){
                writingLog1 = true;
                pw = new PrintWriter(new FileOutputStream(new File("log.txt"), true));
                pw.append(logLine+"\n");
                pw.close();
                writingLog1 = false;
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

    public static boolean LogAuthentication(String logLine){
        PrintWriter pw = null;
        try {
            if (!writingLog2){
                writingLog2 = true;
                pw = new PrintWriter(new FileOutputStream(new File("authentication.txt"), true));
                pw.append(logLine+"\n");
                pw.close();
                writingLog2 = false;
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

    public static boolean LogAuthentication_check(String logLine){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File ("authentication.txt");
            if(!archivo.exists())
                return false;
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            String linea;
            while((linea = br.readLine()) != null) {
                if (linea.contains(logLine))
                    return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        PoolThread Servidor = new PoolThread(10);

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