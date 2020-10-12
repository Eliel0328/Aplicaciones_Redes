package MiniChat;

import java.util.*;
import java.io.*;
import java.net.*;

public class Servidor extends Observable implements Runnable{
    private int puerto;

    public Servidor(int puerto){
        this.puerto = puerto;
    }

    public void run(){
        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;

        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor inciado");
            
            while(true){
                sc = servidor.accept();
            
                System.out.println("Cliente Conectado");
                in = new DataInputStream(sc.getInputStream());

                String mensaje = in.readUTF();
                System.out.println(mensaje);

                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();

                sc.close();
                System.out.println("Cliente Desconectado");
            }
        } catch (IOException e) {   
            e.printStackTrace();
        }
    }
}
