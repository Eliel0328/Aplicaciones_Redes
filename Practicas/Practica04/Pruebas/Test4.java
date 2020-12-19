package  Pruebas;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class Test4 {
   public static void main(String[] args) throws Exception  {
      Reader r = null;

      try   {
         URL u = new URL("http://148.204.58.221/axel/aplicaciones/sockets/java/");
         InputStream in = u.openStream();
         r = new InputStreamReader(in);

         ParserDelegator hp = new ParserDelegator();
         hp.parse(r, new HTMLEditorKit.ParserCallback() {
            public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
               System.out.println(t);
               if(t == HTML.Tag.A)  {
                  Enumeration attrNames = a.getAttributeNames();
                  StringBuilder b = new StringBuilder();
                  while(attrNames.hasMoreElements())    {
                      Object key = attrNames.nextElement();
                      if("href".equals(key.toString())) {
                          System.out.println(a.getAttribute(key));
                      }
                  }
               }
            }
         }, true);
      }finally {
         if(r != null)  {
            r.close();
         }
      }
   }
}