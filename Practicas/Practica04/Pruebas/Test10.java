package Pruebas;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test10 {
    public static void main(String[] args) {       
        try {
            String dirname = "https://i2.wp.com/i.imgur.com/idgMMrI.jpg";
            //String dirname = "http://148.204.58.221/axel/aplicaciones/sockets/java/";
            //String dirname = "http://148.204.58.221/axel/aplicaciones/sockets/java/Cliente_O.java";
            URL url = new URL(dirname);
            // establecemos conexion
            URLConnection conexion = url.openConnection();
            int length = conexion.getContentLength();
            System.out.println(length);

                
            BufferedInputStream in = new BufferedInputStream(new URL(dirname).openStream());
            File f = new File("Prueba");
            FileOutputStream fos = new FileOutputStream(f);
    
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            int p = 0;
    
            while((bytesRead = in.read(dataBuffer, 0, 1024)) != -1){
                fos.write(dataBuffer, 0, bytesRead);
                p += bytesRead;
                double por = ((p * 100) / length);

                System.out.print("\r " + por + "% [");
                for (int i = 0; i <= (int)(por/2); i++) {
                  System.out.print(".");
                }
                for (int i = (int)(por/2); i < 50; i++) {
                  System.out.print(" ");
                }
                System.out.print("]");
            }
    
            fos.close();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
