package com.example.acer.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.acer.myapplication.R;
import com.example.acer.myapplication.activity.service.UrlService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by admin on 2018/4/6.
 */
public class LogActivity extends AppCompatActivity {
    //variables definition
    private Button loginButton,registerButton;
    private EditText editTextname,editTextpassword;
    private static final int REQUEST_SIGNUP = 1;//requestCode
    private static final String TA = "LogActivity";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        loginButton=(Button)findViewById(R.id.buttonLogin);
        registerButton=(Button)findViewById(R.id.buttonRegister);
        editTextname=(EditText)findViewById(R.id.editTextname);
        editTextpassword=(EditText)findViewById(R.id.editTextpassword);

        //处理已登录过用户的信息
//        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
//        asyncHttpClient.get("http://www.google.com", new AsyncHttpResponseHandler(){
//
//            @Override
//            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody) {
//                // Successfully got a response
//            }
//
//        });


        //login button activity
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //进入注册界面
                Intent intent = new Intent();
                intent.setClass(LogActivity.this, SignUpActivity.class);
                LogActivity.this.startActivity(intent);
                finish();

            }
        });
    }

    private void login(){
        //Service request
        final String username=editTextname.getText().toString();
        String password=editTextpassword.getText().toString();

        //窗口简单判断是否符合标准
        if (!validate()) {
            Toast.makeText(getBaseContext(),"Login failed", Toast.LENGTH_LONG).show();
            return;
        }

        //登录进度窗口（后续）？？？
//        final ProgressDialog progressDialog = new ProgressDialog(LogActivity.this,
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("验证登录中...");
//        progressDialog.show();

        //创建网络访问对象
        AsyncHttpClient client = new AsyncHttpClient();
        //创建参数请求
        RequestParams params = new RequestParams();
        params.put("username",username);
        params.put("password",password);


      String url = UrlService.getUrl(LogActivity.this, R.string.URL_LOGIN);
        Log.d("wangpeng", "login: "+url);

        //发送请求
        client.post(url, params, new AsyncHttpResponseHandler(){
            //回应服务器端发回的请求，返回是否登录成功，以及用户信息
            //服务器端返回的数据类型！！？？
            @Override
            public void onSuccess(String content){
                Log.d("wangpeng", "onSuccess: "+content);
                Toast.makeText(LogActivity.this, "服务器登录接口调用", Toast.LENGTH_SHORT).show();
                //json的反序列化
                JSONObject jsonObject = JSONObject.parseObject(content);
                Boolean result=jsonObject.getBoolean("success");
                String info=jsonObject.getString("info");

                //返回是否登录成功或者失败的类型
                if(result){
                    //“登录成功”
                    //弹窗显示，登录情况
                    Toast.makeText(LogActivity.this, info, Toast.LENGTH_SHORT).show();

                    //添加到内存中
                   SharedPreferences sh=getSharedPreferences("User_info", Context.MODE_PRIVATE);
                   sh.edit().putString("userName",username).commit();

                    //跳转到收藏夹主界面，并关闭登录界面
                    Intent intent = new Intent(LogActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //“邮箱错误”
                    //“用户名错误”
                    //“密码错误”
                    Toast.makeText(LogActivity.this, info, Toast.LENGTH_SHORT).show();
                }
            }

            //连接服务器失败
            @Override
            public void onFailure(Throwable error, String content) {
                Log.d("wangpeng", "onFailure: "+ content);
                Toast.makeText(LogActivity.this, "服务器登录接口调用失败", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void login2(){




        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String url = UrlService.getUrl(LogActivity.this, R.string.URL_LOGIN);
                    Log.d("wangpeng", "run: "+url);
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username","wangpeng").add("password","Wp336358").build();
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        String string = response.body().string();
                        Log.d("wangpeng", "login: "+string);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }
    //进行一些窗口信息的简单判断
    private boolean validate(){
//        用户名：不为空
//        邮箱名
//        密码：4-16位密码，应包含数字、字母等不同的字符
        String username=editTextname.getText().toString();
        String password=editTextpassword.getText().toString();

        //输入内容是否为空
        if(username.isEmpty()){
            editTextname.setError("用户名或邮箱名不能为空");
            return false;
        }
        else {
            editTextname.setError(null);
        }

        if(password.isEmpty()){
            editTextpassword.setError("密码不能为空");
            return false;
        }else {
            editTextpassword.setError(null);
        }

        if(password.length()<4||password.length()>16){
            editTextpassword.setError("密码长度应为4-16位");
            return false;
        }else {
            editTextpassword.setError(null);
        }
        return true;
    }


}
