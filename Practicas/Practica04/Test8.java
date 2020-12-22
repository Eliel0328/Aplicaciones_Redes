import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test8 {

    static boolean linkViable(String s) { //  Verificar que se un link disponible para ir
        if(s.contains("//"))    return false;
        if(s.contains("@"))     return false;            
        if(s.contains("#"))     return false;
        if(s.contains("\\"))    return false;
        if(s.charAt(0) == '?')
            return false;
        return true;
    }

    static boolean isResource(String s) {           //  Verificar que el nombre corresponda a un recurso
        if (s.contains("."))
            return true;
        return false;
    }

    static int nivels(String s) {           
        String[] aux = s.split("/");
        int count = 0;
        for(String a: aux){
            if(!a.equals(""))
                count++;
        }
        return count;
    }

    static boolean back_ahead(String s, String a) {           
        if(a.contains(s) || a.contains("/" + s))
            return true;
        return false;
    }

    static String complementPath(int nivel){
        String replaced = "";
        for(int i = 0; i < nivel; ++i){
            replaced += "../";
        }
        return replaced;
    }

    static String path_downloadImg(String s, String img, int n){
        String[] aux = s.split("/");
        String[] aux1 = img.split("/");
        int count = nivels(s) - n;
        String filePath = "";
        
        for(String a: aux){
            if(!a.equals("") && count > 0){
                filePath += "/" + a;
                --count;
            }
        }

        for(String a: aux1){
            if(!a.equals(""))
                filePath += "/"+ a;
        }

        return filePath;
    }

    static void downloadImg(String s, String b){
        String[] aux = s.split("/");
        String path = "";
        int n = aux.length;

        for(int i = 0; i < n - 1; ++i){
            if(!aux[i].equals("")){
                File f = new File(path + "/" + aux[i]);
                path +=  aux[i] + "/";
                f.mkdirs();
            }
        }
        
        downloadResorce(b, path, aux[n - 1]);

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

    public static void main(String[] args) {
        int c;
        int nivel = 5;
        String urlOri = "http://148.204.58.221";
        String url = "http://148.204.58.221/axel/aplicaciones/sockets/java/udp/";
        Set<String> enlace = new HashSet<>();
        /*String aux = path_downloadImg("148.204.58.221/axel/aplicaciones/sockets/java/udp/", "/icons/text.gif", nivel);
        System.out.println(aux);
        downloadImg(aux, "http://148.204.58.221/icons/text.gif");*/

        
        String html = "";
        try{

            //  Descargar el contenido del index
            InputStream in = new URL(url).openStream();
            while((c = in.read())!=-1) {
                html += (char)c;
            }
            in.close();

            //  Reescribir las direcciones en los enlaces validos del servidor
            Pattern p = Pattern.compile("href=\"(.*?)\"");
            Matcher m = p.matcher(html);
            
            while (m.find()) {
                String src = m.group();
                int startIndex = src.indexOf("href=") + 6;
                String srcTag = src.substring(startIndex, src.length() - 1);
                
                if(linkViable(srcTag) && !isResource(srcTag) && !enlace.contains(srcTag)){
                    if(back_ahead(srcTag, url)){
                        int aux = nivel - nivels(srcTag);
                        String auxString = complementPath(aux);
                        html = html.replace(src, src.replace(srcTag, auxString+"index.html"));
                        //html = html.replace(srcTag, auxString + "index.html");
                    }else{
                        if(srcTag.charAt(srcTag.length() - 1) == '/')
                            html = html.replace(src, "href=" + srcTag.replace(srcTag, srcTag + "index.html"));
                            //html = html.replace(srcTag, srcTag + "index.html");
                        else
                            html = html.replace(src, "href=" + srcTag.replace(srcTag, srcTag + "/index.html"));
                            //html = html.replace(srcTag, srcTag + "/index.html");
                    }
                    enlace.add(srcTag);
                }

            }

            //  Reescribir las direcciones en las imagenes validos del servidor
            p = Pattern.compile("<img [^>]*src=[\\\"']([^\\\"^']*)");
            m = p.matcher(html);

            while (m.find()) {
                String src = m.group();
                int startIndex = src.indexOf("src=") + 5;
                String srcTag = src.substring(startIndex, src.length());
                System.out.println(srcTag);
                
                if(!enlace.contains(srcTag)){
                    String auxString = complementPath(nivel);
                    String pathImg = path_downloadImg("148.204.58.221/axel/aplicaciones/sockets/java/udp/", srcTag, nivel);
                    downloadImg(pathImg, urlOri + srcTag);
                    if(srcTag.charAt(0) == '/'){
                        //html = html.replace(src, srcTag.replace(srcTag, auxString.substring(0, (nivel * 3) - 1) + srcTag));
                        html = html.replace(srcTag, auxString.substring(0, (nivel * 3) - 1) + srcTag);
                    }
                    else
                        //html = html.replace(src, srcTag.replace(srcTag, auxString + srcTag));
                        html = html.replace(srcTag, auxString + srcTag);
                    enlace.add(srcTag);
                }
                
            }
            
            System.out.println(html);

            FileWriter myWriter = new FileWriter("index.html");
            myWriter.write(html);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          catch(Throwable err){
            err.printStackTrace();
        }

    }   
}
