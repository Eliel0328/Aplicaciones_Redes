package  Pruebas;
import java.net.*;
import java.io.*;
    
public class Test{
    public static void main(String args[]) {
        int c;
        try{
            InputStream in = new URL("http://148.204.58.221/axel/aplicaciones/sockets/java/").openStream();
            while((c=in.read())!=-1) {
                System.out.print((char)c);
            }
            in.close();
        }
        catch(Throwable err){
            err.printStackTrace();
        }
    }
}