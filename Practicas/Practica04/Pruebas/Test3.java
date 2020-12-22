package  Pruebas;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Test3 {
    public static void main(String[] args) {
        try (BufferedInputStream in = new BufferedInputStream(new URL("http://148.204.58.221/icons/a.gif").openStream());
        FileOutputStream fileOutputStream = new FileOutputStream("Avance1")) {
        
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }
    }
}
