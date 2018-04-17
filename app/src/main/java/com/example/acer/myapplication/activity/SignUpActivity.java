package com.example.acer.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.acer.myapplication.R;
import com.example.acer.myapplication.UI_utils.CountDownTimerButton;
import com.example.acer.myapplication.activity.service.UrlService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by admin on 2018/4/6.
 */

public class SignUpActivity extends AppCompatActivity {
    //variables defination
    private Button btnSignup,btnBack;
    private CountDownTimerButton btnEnsureWord;
    private EditText editTextName,editTextpw,editTextemail,editTextEnsureWord,editTextEnsurePw;
    private static final String TAG = "SignUpActivity";

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnEnsureWord=(CountDownTimerButton) findViewById(R.id.buttonEnsureWord);
        btnSignup=(Button)findViewById(R.id.buttonSignup);
        btnBack=(Button)findViewById(R.id.buttonBack);
        editTextName=(EditText) findViewById(R.id.editTextName);
        editTextemail=(EditText)findViewById(R.id.editTextemail);
        editTextEnsureWord=(EditText)findViewById(R.id.editTextEnsureWord);
        editTextpw=(EditText)findViewById(R.id.editTextpw);
        editTextEnsurePw=(EditText)findViewById(R.id.editTextEnsurePw);

//        setResult(RESULT_OK, null);
        btnEnsureWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "倒计时开始", Toast.LENGTH_SHORT).show();
                //发送邮箱并验证验证码
                ensureWord();
                //获取验证码
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//注册
                signUp();}
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回登录界面
                Intent intent = new Intent(SignUpActivity.this, LogActivity.class);
                startActivity(intent);
                finish();
                }
        });
    }

    //发送邮箱地址，服务器发送验证邮件，验证码检验
    private void ensureWord(){
        //获取，检验并发送邮箱地址
        String emailAddress=editTextemail.getText().toString();
        if(emailAddress.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            editTextemail.setError("邮箱地址不合法");
            return;
        }else {
            editTextemail.setError(null);
        }

        //创建网络访问对象
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        //创建参数请求
        RequestParams params = new RequestParams();
        params.put("email",emailAddress);

        String url = UrlService.getUrl(SignUpActivity.this, R.string.URL_SENDEMAIL);
        asyncHttpClient.post(url,params, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(String content) {
                JSONObject jsonObject = JSONObject.parseObject(content);

                boolean result=jsonObject.getBoolean("success");
                String info=jsonObject.getString("info");
                Log.d(TAG, info);
                Toast.makeText(SignUpActivity.this,info,Toast.LENGTH_SHORT).show();

                if(result){
                    //如果以及发送成功，则让按钮禁用并显示发送
                    //等到cookie过期以后才能重新显示
                    btnEnsureWord.setText("ENSURE WORD HAVE SENT");
                    btnEnsureWord.setEnabled(false);

                }
            }

            public void onFailure(Throwable error, String content) {
                Toast.makeText(SignUpActivity.this,"服务器注册接口调用失败,"+content,Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void signUp(){
        Log.d(TAG, "Signup");
        //进行简单验证
        if(!validate()){
            Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();
            btnSignup.setEnabled(true);
            return;
        }
        //获取内容
        String editTextNameS=editTextName.getText().toString();
        String editTextpwS=editTextpw.getText().toString();
        String editTextemailS=editTextemail.getText().toString();
        String editTextEnsureWordS=editTextEnsureWord.getText().toString();

        //创建网络访问对象
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        //创建参数请求
        RequestParams params = new RequestParams();
        params.put("username",editTextNameS);
        params.put("password",editTextpwS);
        params.put("email",editTextemailS);
        params.put("activeCode",editTextEnsureWordS);

        String url = UrlService.getUrl(SignUpActivity.this, R.string.URL_REG);
        asyncHttpClient.post(url,params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String content) {
                Log.d(TAG, "onSuccess: ");
                //解析返回数据
                JSONObject jsonObject = JSONObject.parseObject(content);
                boolean result = jsonObject.getBoolean("success");
                String info = jsonObject.getString("info");

                if(result){
                    //注册成功
                    Toast.makeText(SignUpActivity.this, info, Toast.LENGTH_SHORT).show();
                    //回到登录界面
                    Intent intent = new Intent(SignUpActivity.this, LogActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //注册失败
                    btnSignup.setEnabled(true);
                    btnEnsureWord.setEnabled(true);
                    Toast.makeText(SignUpActivity.this, info, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.d(TAG, "onFailure: "+content);
                Toast.makeText(SignUpActivity.this,"服务器注册接口调用失败,"+content,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate(){
        boolean validateResult=true;
        String editTextNameS=editTextName.getText().toString();
        String editTextpwS=editTextpw.getText().toString();
        String editTextemailS=editTextemail.getText().toString();
        String editTextEnsureWordS=editTextEnsureWord.getText().toString();
        String editTextEnsurePwS=editTextEnsurePw.getText().toString();

        //对于用户名
        if(editTextNameS.isEmpty()){
            validateResult=false;
            editTextName.setError("用户名不能为空");
        }else {
            editTextName.setError(null);
        }
        //对于邮箱
        if(editTextemailS.isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextemailS).matches()){
            validateResult=false;
            editTextemail.setError("邮箱地址不合法");
        }else {
            editTextemail.setError(null);
        }
        //对于邮箱验证码
        if(editTextEnsureWordS.isEmpty()){
            validateResult=false;
            editTextEnsureWord.setError("邮箱验证码不能为空");
        } {
            editTextEnsureWord.setError(null);
        }

        //对于密码
        if(editTextpwS.isEmpty()||editTextpwS.length()<4||editTextpwS.length()>16){
            validateResult=false;
            editTextpw.setError("密码应为4-16位");
        } else {
            editTextpw.setError(null);
        }

        //对于验证密码
        if(!editTextEnsurePwS.equals(editTextpwS)||editTextEnsurePwS.isEmpty()){
            validateResult=false;
            editTextEnsurePw.setError("两次输入密码不一致");
        } else {
            editTextEnsurePw.setError(null);
        }

        return validateResult;
    }
}


