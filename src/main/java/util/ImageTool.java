package util;

import bean.BaiduImage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bxyjisL on 2018/5/24.
 */
public class ImageTool {
    private int model;
    public ImageTool(){
    }
    //获取网页文本
    public String getResource(String url, String encoding) {
        StringBuffer buffer = new StringBuffer();
        URL urlObject = null;
        URLConnection uc = null;
        InputStreamReader in = null;
        BufferedReader reader = null;
        try {
            urlObject = new URL(url);
            uc = urlObject.openConnection();
            in = new InputStreamReader(uc.getInputStream(), encoding);
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
        return buffer.toString();
    }

    public String nextPage(String url) {
        int index;
        Pattern pattern = Pattern.compile("&pn=[0-9]{0,}");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            String s = matcher.group();
            index = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.length()));
            //因为一个页面会包含3个页面的内容
            index += 60;
            url = url.replaceFirst(s, "&pn=" + index);
            System.out.println("nextpage" + url);
        }
        return url;
    }
    //获取图片url列表
    public void getImags(String url, String coding,String outpath,int threadNum) {
        List<BaiduImage> list = new ArrayList<BaiduImage>();
        DownloadTool downloadTool = new DownloadTool(outpath,threadNum);
        boolean flag = true;
        int i = 0;
        String resource = null;
        while (flag) {
            list.clear();
            flag = false;
            resource = getResource(url, coding);
            BaiduImage image = null;
            int last = 0;
            String imageInfo;
            String IMGURL = "\"objURL\":\"http://img5q.duitang.com/uploads/item/201501/02/20150102141135_r8zKf.jpeg\"";
            Pattern pattern = Pattern.compile("\"objURL\":\"http:\\\"?(.*?)(\\\"|>|\\\\s+)");
            Matcher matcher = pattern.matcher(resource);
            while (matcher.find()) {
                flag = true;
                image = new BaiduImage();
                imageInfo = matcher.group();
                last = imageInfo.lastIndexOf("/");
                image.setFilename(imageInfo.substring(last + 1, imageInfo.length() - 1));
                last = imageInfo.indexOf(":");
                image.setUrl(imageInfo.substring(last + 2, imageInfo.length() - 1));
                list.add(image);
                System.out.println(image.getUrl() + "\n" + image.getFilename());
            }
            downloadTool.download(list);
            url = nextPage(url);
        }
        downloadTool.shutdown();
    }
}
