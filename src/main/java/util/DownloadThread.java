package util;

import bean.BaiduImage;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * Created by bxyjisL on 2018/5/24.
 */
public class DownloadThread extends Thread{
    private URL url;
    private BaiduImage image;
    private String outputPath;
    private int index;
    private DataInputStream dataInputStream;
    private FileOutputStream fileOutputStream;
    public DownloadThread(BaiduImage image,String outputPath,int index){
        this.image=image;
        this.outputPath=outputPath;
        this.index=index;
    }
    public void run(){
        try {
            URL url = new URL(image.getUrl());
            dataInputStream = new DataInputStream(url.openStream());
            fileOutputStream = new FileOutputStream(outputPath + image.getFilename());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context = output.toByteArray();
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            System.out.println("第" +index + "张图:" + image.getFilename() + "下载完毕");
            }
         catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataInputStream != null)
                    dataInputStream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
