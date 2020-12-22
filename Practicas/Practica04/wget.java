import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class wget {

    static boolean isAddressResource(String s) {    //  Verificar link a descargar
        for(int i = s.length() - 1; i > 0; i--){
            if(s.charAt(i) == '/')
                return false;
            if(s.charAt(i) == '.')
                return true;
        }
        return false;
    }


    static String previousAddress(String s) {       //  Obtener direccion anterior
        for (int i = s.length() - 2; i > 0; i--)
            if (s.charAt(i) == '/')
                return s.substring(0, i + 1);
        return "";
    }

    static String newPath(String[] s) {             //  Construir nueva ruta para las descargas
        String aux = "";
        for (int i = 2; i < s.length; ++i) {
            aux += s[i] + "/";
            File file = new File(aux);
            file.mkdirs();
        }
        return aux;
    }

    static String makeLink(String s, String a){     //  Hacer un link nuevo auxiliar para ingresar
        if(s.contains(a) || s.contains("/" + a))    //  Posible error, complemento anterior
            return "";
        if(a.charAt(0) == '/')
            return s + a.substring(1);
        else
            return s + a;
    }

    static boolean linkViable(String s, String a) { //  Verificar que se un link disponible para ir
        if(s.contains("//"))    return false;
        if(s.contains("@"))     return false;            
        if(s.contains("#"))     return false;
        if(s.contains("\\"))    return false;
        if(a.contains(s) || a.contains("/" + s))
            return false;
        if(s.charAt(0) == '#' || s.charAt(0) == '?')
            return false;
        return true;
    }

    static boolean isResource(String s) {           //  Verificar que el nombre corresponda a un recurso
        if (s.contains("."))
            return true;
        return false;
    }

    static String onlyName(String s){               //  Obtener el nombre correctamente
        if(s.contains("/")){
            if(s.charAt(0) == '/' && s.charAt(s.length()-1) == '/')
                return s.substring(1, s.length()-1);
            if(s.charAt(0) == '/')
                return s.substring(1);
            if(s.charAt(s.length()-1) == '/')
                return s.substring(0, s.length()-1);
        }
        return s;
    }

    static void downloadResorce(String url, String path, String filename){
        System.out.println("Direccion: " + url);

        try {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            File f = new File(path + "/" + filename);
            FileOutputStream fos = new FileOutputStream(f);

            byte dataBuffer[] = new byte[1024];
            int bytesRead;

            while((bytesRead = in.read(dataBuffer, 0, 1024)) != -1){
                fos.write(dataBuffer, 0, bytesRead);
            }

            fos.close();
            System.out.println("Descargado: " + filename);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //static void downloadResorces(Set<String> links, String nameHMTL, String actualURL, String previousURL, String path, String space) {
    static void downloadResorces(Set<String> links, String actualURL, String previousURL, String path, String space) {

        try {
            //  Recursos necesario para recorrer el HTML
            Reader r = null;
            URL pageURL = new URL(actualURL);
            InputStream in = pageURL.openStream();

            //  Descargar el HTML del index
            //downloadResorce(actualURL, path, nameHMTL + ".html");
            downloadResorce(actualURL, path, "index.html");

            r = new InputStreamReader(in);
            ParserDelegator hp = new ParserDelegator();

            hp.parse(r, new HTMLEditorKit.ParserCallback(){
                public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos){
                    if(t == HTML.Tag.A){
                        Enumeration attrNames = a.getAttributeNames();
                        
                        //  Comprobar si quedan enlaces por recorrer
                        while(attrNames.hasMoreElements()){
                            Object key = attrNames.nextElement();
                            String actualName = (String)a.getAttribute(key);
                            String auxLink = makeLink(actualURL, actualName);
                            
                            //  Comprobar enlace
                            if("href".equals(key.toString()) &&  linkViable(actualName, previousURL) && !links.contains(auxLink)){
                                links.add(auxLink);
                                System.out.println(actualName);
                                System.out.println(space + auxLink);
                                
                                //  Descargar recurso o explorar un nuevo enlace
                                if(isResource(actualName)){
                                    downloadResorce(auxLink, path, onlyName(actualName));
                                }else{
                                    System.out.println("Carpeta: " + actualName);
                                    File f = new File(path, actualName);
                                    f.mkdir();
                                    //downloadResorces(links, actualName, makeLink(actualURL, actualName), actualURL, path + actualName, space + "----|");
                                    downloadResorces(links, makeLink(actualURL, actualName), actualURL, path + actualName, space + "----|");

                                }
                            }
                        }
                    }
                }
            }, true);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static void getData_Download(String s, String[] partsURL){
        String previousURL = previousAddress(s);        
        String server = partsURL[0] + "//" + partsURL[2] + "/";
        String path = newPath(partsURL);

        System.out.println("Enlace en donde se descargaran recursos: " + s);
        System.out.println("Dirección anterior: " + previousURL);
        System.out.println("Dirección del servidor: " + server);
        System.out.println("--  --  --  --  --  --  --  --  --  --  --  --  --");

        Set<String> links = new HashSet<>();
        links.add(s);
        links.add(previousURL);
        links.add(server);
        
        //downloadResorces(links, "index", s, previousURL, path, "----|");
        downloadResorces(links, s, previousURL, path, "----|");

    }

    public static void main(String[] args) {
        //  Dirección URL para descargar
        //String dirname = "https://i2.wp.com/i.imgur.com/idgMMrI.jpg";
        String dirname = "http://148.204.58.221/axel/aplicaciones/sockets/java/udp/";
        String[] partsURL = dirname.split("/");

        if(isAddressResource(dirname)){
            //  Descargar un solo recurso en la ubicación actual
            downloadResorce(dirname, System.getProperty("user.dir"), partsURL[partsURL.length - 1]);
        }
        else{
            //  Descargar todos los recursos de un enlace
            getData_Download(dirname, partsURL);
        }

    }
}