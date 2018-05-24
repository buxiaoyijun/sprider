import bean.BaiduImage;
import util.DownloadTool;
import util.ImageTool;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
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

    public void checkoutpath(String outpath){
        File file=new File(outpath);
        if(!file.exists()){
           file.mkdir();
        }
    }
    public static void main(String args[]) {
        Main main = new Main();
        ImageTool imageTool=new ImageTool();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入百度图片关键字(如 saber)：");
        String word = scanner.nextLine();
        String url = "https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=" + word + "&pn=0&gsm=64&ct=&ic=0&lm=-1&width=0&height=0";
        System.out.println("输入线程数量(如 8):");
        int threadNum=scanner.nextInt();
        if(threadNum<1){
            threadNum=4;
        }
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=0&gsm=64&ct=&ic=0&lm=-1&width=0&height=0
        //https://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8&word=saber&pn=20&gsm=3c&ct=&ic=0&lm=-1&width=0&height=0
        //网页编码
        String encoding = "utf-8";
        //设置路径,默认为当前路径下的badiuimage
        String outpath = "badiuimage";
        main.checkoutpath(outpath);
        outpath=outpath+"\\";
        imageTool.getImags(url, encoding,outpath,threadNum);
        System.out.println("本次爬虫结束");
        //downloadTool.download(list,path);
    }
}
