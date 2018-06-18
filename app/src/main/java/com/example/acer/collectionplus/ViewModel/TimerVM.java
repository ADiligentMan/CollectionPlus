package com.example.acer.collectionplus.ViewModel;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.acer.collectionplus.Helper.AlarmReciver;

import java.util.Calendar;

import static com.mob.MobSDK.getContext;


public class TimerVM {
    private int year,month,day,hour,minute;
    Context context;
    public TimerVM( Context context){
        this.context=context;
    }
    public Context getContext(){
        return context;
    }



    //选择时间的dialog并设置提醒
    public void showDialogPick() {
        //获取Calendar对象，用于获取当前时间(dialog的原始状态)
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                //monthOfYear会比实际月份少一月 但不需要加一 就能按时收到提醒
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    //选择完时间后会调用该回调函数
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //time.append(" " + hourOfDay + ":" + minute);
                        //记录用户选择的时间
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND,0);
                        calendar.set(Calendar.MILLISECOND,0);
                        startRemind(calendar);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        }, year,month,day);
        datePickerDialog.show();
    }

    //开启提醒
    private void startRemind(Calendar uCalendar){
        AlarmManager am= (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);

        //AlarmReceiver.class为广播接受者
        Intent intent = new Intent(getContext(),AlarmReciver.class);
        PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        Toast.makeText(getContext(), "设置提醒成功", Toast.LENGTH_LONG).show();
        //得到AlarmManager实例

        /**
         * 单次提醒
         * calendar.getTimeInMillis() 上面获得时间点毫秒值
         */
        Log.e("amUc", String.valueOf(uCalendar.getTimeInMillis()));
        am.set(AlarmManager.RTC_WAKEUP, uCalendar.getTimeInMillis(), pi);
        //am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000, pi);
    }


    /**
     * 关闭提醒
     */
    public void stopRemind(){
        Intent intent = new Intent(getContext(), AlarmReciver.class);
        PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0,
                intent, 0);
        AlarmManager am= (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
        //取消警报
        am.cancel(pi);
        Toast.makeText(getContext(), "关闭提醒", Toast.LENGTH_SHORT).show();
    }

}
