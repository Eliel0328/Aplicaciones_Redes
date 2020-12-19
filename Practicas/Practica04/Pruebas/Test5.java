package  Pruebas;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Test5 {
    public static void main(String[] args) {
        try (
                InputStream openStream = new URL("http://148.204.58.221/axel/aplicaciones/sockets/java/canales/Serializacion/").openStream();
                Scanner scanner = new Scanner(openStream, "UTF-8");) {
            String out = scanner.useDelimiter("\\A").next();
            System.out.println(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}