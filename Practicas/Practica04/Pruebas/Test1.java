package  Pruebas;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test1 {
  public static void main(String[] args) throws IOException {
    URL url = new URL("http://148.204.58.221/axel/aplicaciones/sockets/java/");

    URLConnection urlConnection = url.openConnection();
    Map<String, List<String>> headers = urlConnection.getHeaderFields();
    Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
    for (Map.Entry<String, List<String>> entry : entrySet) {
      String headerName = entry.getKey();
      System.out.println("Header Name:" + headerName);
      List<String> headerValues = entry.getValue();
      for (String value : headerValues) {
        System.out.print("Header value:" + value);
      }
      System.out.println();
      System.out.println();
    }
}
}