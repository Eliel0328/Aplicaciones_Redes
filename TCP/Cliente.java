package TCP;

import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        final String Host = "127.0.0.1";
        final int puerto = 3000;

        DataInputStream in;
        DataOutputStream out;

        try {
            Socket sc = new Socket(Host, puerto);

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            out.writeUTF("Hola, desde el cliente");
            String mensaje = in.readUTF();

            System.out.println(mensaje);

            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}