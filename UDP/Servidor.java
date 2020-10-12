package UDP;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        final int puerto = 3000;

        try {
            System.out.println("Servidor iniciado(UDP)");

            while(true){
                byte[] buffer = new byte[1024];
                DatagramSocket socket = new DatagramSocket(puerto);

                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socket.receive(peticion);

                System.out.println("Informacion recibida");
                String mensaje = new String(peticion.getData());
                System.out.println(mensaje);

                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();

                mensaje = "Hola mundo desde el servidor";
                buffer = new byte[1024];
                buffer = mensaje.getBytes();

                System.out.println("Informacion enviada");
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
                socket.send(respuesta);
                socket.close();
            }
            

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
