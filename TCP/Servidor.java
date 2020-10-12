package TCP;

import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;
        Socket sc = null;

        DataInputStream in;
        DataOutputStream out;
        final int port = 3000;

        try {
            servidor = new ServerSocket(port);
            System.out.println("Servidor inciado");
            
            while(true){
                sc = servidor.accept();
            
                System.out.println("--Cliente Conectado");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();
                System.out.println(mensaje);

                out.writeUTF("Hola Mundo desde el Servidor");

                sc.close();
                System.out.println("Cliente Desconectado");
            }
        } catch (IOException e) {   
            e.printStackTrace();
        }
    }
}
