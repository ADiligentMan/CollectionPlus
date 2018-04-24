package com.example.acer.myapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.acer.myapplication.R;
import com.example.acer.myapplication.activity.service.UrlService;
import com.example.acer.myapplication.utility.AnalyzeHtml;
import com.example.acer.myapplication.utility.MyHttp;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddActivity extends AppCompatActivity {
    private Button btn_finish;//完成按钮
    private Button btn_directory; //分类按钮
    private ImageButton ib_add;//新加文件夹
    private ImageButton ib_cancel ;//退出
    private EditText et_title;
    private String title; //标题
    private String picPath; //图片路径
    private String text; //收藏的东西
    private boolean isLink; //是不是链接
    private static Dialog bottomDialog;

    Handler handler = new Handler(){
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
           if(msg.what ==1){
                et_title.setText(title);
           }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initVars();

        //点击完成按钮
        this.btn_finish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyHttp.setUrl(UrlService.getUrl(AddActivity.this,R.string.URL_SAVELINK));
                MyHttp.setParams(initParams());
                MyHttp.post(new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        Log.d("wangpeng", "onSuccess: "+content);
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        Log.d("wangpeng", "onFailure: "+content);
                    }
                });
                AddActivity.this.finish();
            }
        });

        //选择一个收藏夹
        this.btn_directory.setOnClickListener(new View.OnClickListener() {
            //在当前onClick方法中监听点击Button的动作
            public void onClick(View v) {
            }
        });

        //选择取消按钮
        this.ib_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyHttp.setUrl(UrlService.getUrl(AddActivity.this,R.string.URL_SAVELINK));
                MyHttp.setParams(initParams());
                MyHttp.post(new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String content) {
                        Log.d("wangpeng", "onSuccess: "+content);
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        Log.d("wangpeng", "onFailure: "+content);
                    }
                });
                AddActivity.this.finish();
            }
        });

        //新建一个收藏夹
        this.ib_add.setOnClickListener(new View.OnClickListener() {
            //在当前onClick方法中监听点击Button的动作
            public void onClick(View v) {
                    //显示输入界面
                    final EditText et = new EditText(AddActivity.this);
                    new AlertDialog.Builder(AddActivity.this)
                            .setTitle("请输入收藏夹名称 ：")
                            .setView(et)
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            Toast.makeText(getApplicationContext(),
                                                    "自定义分类成功 ！",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }).setNegativeButton("取消", null)
                            .setCancelable(false).show();


            }
        });
    }

    //初始化变量
    private void initVars(){
        Intent intent = this.getIntent();
        this.text = intent.getStringExtra("text");
        this.isLink = intent.getBooleanExtra("isLink",false);
        this.btn_finish = (Button) findViewById(R.id.btn_finish);
        this.btn_directory = (Button) findViewById(R.id.btn_directory);
        this.ib_add = (ImageButton) findViewById(R.id.ib_add);
        this.ib_cancel = (ImageButton) findViewById(R.id.ib_cancel);
        this.et_title = (EditText)findViewById(R.id.et_title);
        if(this.isLink == false) return;

        //如果是链接就解析一个标题和一个图片
        final AnalyzeHtml a = new AnalyzeHtml(AddActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
               title = a.GetTitle(text);
                picPath = a.GetPath(text);
                Message msg = new Message();
                msg.what =1;
                handler.sendMessage(msg);
            }
        }).start();
    }


    //初始化后台所需的参数
    private Map initParams(){
        Map<String,String> map = new HashMap<String,String>();
        //得到用户名
        SharedPreferences sh = getSharedPreferences("User_info", Context.MODE_PRIVATE);
        String username = sh.getString("userName", null);
        //得到日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        String date = sdf.format(new Date());

        map.put("username",username);
        map.put("dirname","Math");
        map.put("picPath",this.picPath);
        map.put("value",this.text);
        map.put("read","0");
        map.put("title",et_title.getText().toString());
        map.put("source","未知");
        map.put("time",date);
        map.put("type","未知");

        return map;
    }

    /**
     * @param context
     * @param text  链接或者文字
     * @param isLink 是不是链接
     */
    public static void actionStart(Context context, String text , boolean isLink , Dialog bottomDialog){
        Intent intent  = new Intent(context,AddActivity.class);
        intent.putExtra("text",text);
        intent.putExtra("isLink",isLink);
        AddActivity.bottomDialog = bottomDialog;
        context.startActivity(intent);
    }







    /**
     * Call this when your activity is done and should be closed.  The
     * ActivityResult is propagated back to whoever launched you via
     * onActivityResult().
     */
    @Override
    public void finish() {
        super.finish();
        if(AddActivity.bottomDialog!=null) {
            AddActivity.bottomDialog.dismiss();
        }
    }
}
