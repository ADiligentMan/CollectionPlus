package com.example.acer.collectionplus.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {


    /**
     * 格式转换：yyyy-MM-dd HH:mm:ss -> MM-dd
     * @param time 需要格式化的时间。
     * @return
     */
    public static String getFormatedTime(String time){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat target = new SimpleDateFormat("MM-dd");
            String strTarget="";

            try {
                Date date = sdf.parse(time);
                strTarget = target.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        return  strTarget;
    }

    /**
     * 把Date转化为 yyyy-MM-dd HH:mm:ss的格式
     * @param date
     * @return
     */
    public static String getFormatedTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(date);
    }


}