import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class Test7 {
    public static void main(String[] args) throws Exception  {
        int c;
        String html = "";
        try{
            InputStream in = new URL("http://148.204.58.221/axel/aplicaciones/sockets/java/udp/").openStream();
            while((c = in.read())!=-1) {
                html += (char)c;
                //System.out.print((char)c);
            }
            in.close();
            System.out.println(html);

            Pattern p = Pattern.compile("<img [^>]*src=[\\\"']([^\\\"^']*)");
            Matcher m = p.matcher(html);
            while (m.find()) {
                String src = m.group();
                int startIndex = src.indexOf("src=") + 5;
                String srcTag = src.substring(startIndex, src.length());
                System.out.println( srcTag );
            }
        }
        catch(Throwable err){
            err.printStackTrace();
        }
    }
}