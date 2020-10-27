import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private List <String> fileList;
    private static final String OUTPUT_ZIP_FILE = "Folder-" + LocalDate.now() + ".zip";
    private static final String SOURCE_FOLDER = "/home/emile239/Documentos/Materias/Redes2/Aplicaciones de comunicaciones de red/Practicas/Practica01/Drive/Muelas";

    public ZipUtils() {
        fileList = new ArrayList < String > ();
    }

    public static void main(String[] args) {
        ZipUtils appZip = new ZipUtils();
        appZip.generateFileList(new File(SOURCE_FOLDER));
        appZip.zipIt(OUTPUT_ZIP_FILE);
    }

    public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file: this.fileList) {
                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node) {
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    public void unzipFile(String destFolder){
        File dir = new File(destFolder);       
        
        if(!dir.exists()) dir.mkdirs();

        FileInputStream fis;
        byte[] buffer = new byte[1024];
        
        try {
            fis = new FileInputStream(SOURCE_FOLDER);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destFolder + File.separator + fileName);
                System.out.println("Descomprimir en "+ newFile.getAbsolutePath());
                
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
}