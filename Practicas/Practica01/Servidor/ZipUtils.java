package Servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    List <String> fileList;
    String SOURCE_FOLDER;

    public ZipUtils(String SOURCE_FOLDER) {
        fileList = new ArrayList < String > ();
        this.SOURCE_FOLDER = SOURCE_FOLDER;
    }

    public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.out.println("Zip: " + zipFile);
            FileInputStream in = null;

            for (String file: this.fileList) {
                System.out.println("Archivo agregado : " + file);
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
            System.out.println("Directorio comprimido corectamente");

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
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
            return;
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

    public String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }
}