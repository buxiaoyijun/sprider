package util;

import bean.BaiduImage;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * Created by bxyjisL on 2018/5/23.
 */
public class DownloadTool {
    public void download(List<BaiduImage> list, String outputPath) {
        URL url = null;
        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream = null;
        BaiduImage image = null;
        for (int i = 0; i < list.size(); i++) {
            try {
                image = list.get(i);
                //先判断文件是否存在
                File file = new File(outputPath + image.getFilename());
                if (file.exists()) {
                    System.out.println(outputPath + image.getFilename() + "存在!");
                    continue;
                }
                url = new URL(image.getUrl());
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
                System.out.println("第" + i + "张图:" + image.getFilename() + "下载完毕");
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
