package com.example.acer.myapplication.utility;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2018/3/21.
 * this class used to manage activities in app, we can exit program anywhere and anytime.
 */

public class ActivityCollector {
    public static List<Activity>  activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishALL(){
        for(Activity  activity: activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
