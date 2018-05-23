import bean.BaiduImage;
import util.DownloadTool;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bxyjisL on 2018/5/22.
 */
public class Main {
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
            index += 20;
            url = url.replaceFirst(s, "&pn=" + index);
            System.out.println("nextpage" + url);
        }
        return url;
    }

    //获取图片url列表
    public List<BaiduImage> getImags(String url, String coding) {
        List<BaiduImage> list = new ArrayList<BaiduImage>();
        DownloadTool downloadTool = new DownloadTool();
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
            downloadTool.download(list, "D:\\360Downloads\\badiu\\");
            url = nextPage(url);
        }
        return list;
    }

    public static void main(String args[]) {
        Main main = new Main();
        DownloadTool downloadTool = new DownloadTool();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入百度图片关键字(如 saber)：");
        String word = scanner.nextLine();
        String url = "https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=" + word + "&pn=0&gsm=64&ct=&ic=0&lm=-1&width=0&height=0";
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=0&gsm=64&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=20&gsm=3c&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=40&gsm=50&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=60&gsm=0&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=0&gsm=78&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=20&gsm=3c&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=40&gsm=50&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=60&gsm=64&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=80&gsm=78&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=100&gsm=8c&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=dog&pn=0&gsm=50&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=dog&pn=20&gsm=3c&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=dog&pn=40&gsm=0&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=SABER&pn=180&gsm=0&ct=&ic=0&lm=-1&width=0&height=0
        String encoding = "utf-8";
        //路径
        String path = "D:\\360Downloads\\badiu\\";
        List<BaiduImage> list = main.getImags(url, encoding);
        System.out.println(list.size());
        //downloadTool.download(list,path);
    }
}
