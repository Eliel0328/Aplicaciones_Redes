package UDP;

import java.net.*;
import java.io.*;


public class Cliente {
    public static void main(String[] args) {
        final int puerto_Servidor = 3000;
        
        try {
            byte[] buffer = new byte[1024];
            InetAddress direccion_Servidor = InetAddress.getByName("localhost");

            DatagramSocket socket = new DatagramSocket();
            String mensaje = "Hola mundo desde el cliente";
            buffer = mensaje.getBytes();

            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccion_Servidor, puerto_Servidor);
            System.out.println("Mensaje enviado");
            socket.send(pregunta);

            buffer = new byte[1024];
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
            System.out.println("Mensaje Recibido");
            socket.receive(peticion);

            mensaje = new String(peticion.getData());
            System.out.println(mensaje);

            socket.close();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } 

    }    
}
