package com.example.acer.collectionplus.Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AnalyzeHtml {

    private static int count = 0;
    private Context context;

    public AnalyzeHtml(){

    }
    public AnalyzeHtml(Context context) {
        this.context = context;
    }

    public String GetTitle(String htmlUrl){
        // TODO Auto-generated method stub
        //System.out.println("/n------------开始读取网页(" + htmlUrl + ")-----------");
        String htmlSource = "";
        htmlSource = getHtmlSource(htmlUrl);//获取htmlUrl网址网页的源码
        //System.out.println("------------读取网页(" + htmlUrl + ")结束-----------/n");
        //System.out.println("------------分析(" + htmlUrl + ")结果如下-----------/n");
        String title = "null";
        title = getTitle(htmlSource);
        if (title.equals(""))
            title = "null";
        title = title.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", "");
        //System.out.println("网站标题： " + title.toString());
        return title;
    }

    /**
     * 根据网址返回网页的源码
     * @param htmlUrl
     * @return
     */
    private String getHtmlSource(String htmlUrl){
        URL url;
        StringBuffer sb = new StringBuffer();
        try{
            url = new URL(htmlUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));//读取网页全部内容
            String temp;
            while ((temp = in.readLine()) != null)  {
                sb.append(temp);
            }
            in.close();
        }catch (MalformedURLException e) {
            System.out.println("你输入的URL格式有问题！请仔细输入");
        }catch (IOException e) {
            //e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 从html源码(字符串)中去掉标题
     * @param htmlSource
     * @return
     */
    private String getTitle(String htmlSource){
        List<String> list = new ArrayList<String>();
        String title = "";
        //Pattern pa = Pattern.compile("<title>.*?</title>", Pattern.CANON_EQ);也可以
        Pattern pa = Pattern.compile("<title>.*?</title>");//源码中标题正则表达式
        Matcher ma = pa.matcher(htmlSource);
        while (ma.find()) {//寻找符合el的字串
            list.add(ma.group());//将符合el的字串加入到list中
        }
        for (int i = 0; i < list.size(); i++) {
            title = title + list.get(i);
        }
        return outTag(title);
    }

    /**
     * 去掉html源码中的标签
     * @param s
     * @return
     */
    private String outTag(String s) {
        return s.replaceAll("<.*?>", "");
    }


    private Bitmap getHttpBitmap(String url){
        Bitmap bitmap = null;
        try
        {
            URL pictureUrl = new URL(url);
            InputStream in = pictureUrl.openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    private String saveBitmap(Bitmap bitmap){
        String fileName = "null";
        File file ;
        fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/BSY"+count ;
        file = new File(fileName);

        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out))
            {
                out.flush();
                out.close();
// 插入图库
                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), "BSY"+count, null);

                count+=1;
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
        // 发送广播，通知刷新图库的显示
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));

        return fileName;
    }

    public String GetPath(String htmlUrl) {
        String res ="null";
        try {
            Document doc = Jsoup.connect(htmlUrl).get();
            Elements pngs1 = doc.select("img[src$=.jpg]");
            Elements pngs2 = doc.select("img[src$=.png]");
            if(pngs1.size()==0&&pngs2.size()==0)
                return res;
            int num = pngs1.size()!=0 ? pngs1.size():pngs2.size();
            Elements pngs =  pngs1.size()!=0 ? pngs1:pngs2;
            num = num/2;
            res = pngs.get(num).attr("src");

        } catch (IOException e) {

        }
        return res;
    }

}
