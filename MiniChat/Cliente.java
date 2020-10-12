package MiniChat;

import java.util.*;
import java.io.*;
import java.net.*;

public class Cliente implements Runnable{
    private int puerto;
    private String mensaje;
    
    public Cliente(int puerto, String mensaje){
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    public void run(){
        final String Host = "127.0.0.1";
        DataOutputStream out;

        try {
            Socket sc = new Socket(Host, puerto);
            out = new DataOutputStream(sc.getOutputStream());

            out.writeUTF(mensaje);

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
     
}
