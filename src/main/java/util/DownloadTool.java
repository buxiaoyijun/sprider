package util;

import bean.BaiduImage;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bxyjisL on 2018/5/23.
 */
public class DownloadTool {
    private ExecutorService pool;
    private String outputPath;
    private int count;
    public DownloadTool(String outputPath,int threadNum){
        pool= Executors.newFixedThreadPool(threadNum);
        this.outputPath=outputPath;
        count=1;
    }
    public void shutdown(){
        pool.shutdown();
    }
    public void download(List<BaiduImage> list) {
        URL url = null;
        DataInputStream dataInputStream = null;
        DownloadThread downloadThread=null;
        FileOutputStream fileOutputStream = null;
        BaiduImage image = null;
        for (int i = 0; i < list.size(); i++) {
            try {
                image = list.get(i);
                //先判断文件是否存在
                File file = new File(outputPath + image.getFilename());
                if (file.exists()) {
                    //因为一个页面会包3个页面的内容
                    System.out.println(outputPath + image.getFilename() + "已存在!");
                    continue;
                }
                downloadThread=new DownloadThread(image,outputPath,count++);
                pool.execute(downloadThread);

            } catch (Exception e) {
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
}
