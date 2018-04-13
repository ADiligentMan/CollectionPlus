package com.example.acer.myapplication.activity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.myapplication.R;
import com.example.acer.myapplication.activity.service.ColService;
import com.example.acer.myapplication.activity.service.resultEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.support.v7.app.ActionBarActivity;

public class MainActivity extends AppCompatActivity{

    Handler handler = new Handler(){
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case ColService.GETFOLDERNAME_RESULT_OK:
                   getRealData();
                   break;
           }
        }
    };
    /**
     * 后端的返回结果
     */
    private resultEnd result = new resultEnd();
    //声明相关变量
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private ListView lvRightMenu;
    private AlertDialog alert_btn;
    //右侧收藏夹栏内容
    private String[] lvr = {"Item1","Item2","Item3","Item4","Item5"};
    //数组容器
    private ArrayAdapter array_left;
    private ArrayAdapter array_right;
    private ImageView ivRunningMan;
    private AnimationDrawable mAnimationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews(); //获取控件

        toolbar.setTitle("Toolbar");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
                @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        this.setupForColList();

        //设置主页面列表
       array_right = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lvr);
      lvRightMenu.setAdapter(array_right);
//      alert_dialog();


    }


    private void findViews() {
      lvRightMenu=(ListView) findViewById(R.id.right_menu);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.left_menu);
    }

    /**
     * 从后台请求数据，并给收藏夹列表的adapter设置数据
     */
    private void setupForColList(){

        if(result.getData()==null) {
            List<String> list = new ArrayList<String>();
            result.setData(list);
        }

        Map<String ,String > map = new HashMap<String ,String >();
        map.put("username","wangpeng");
        //请求数据
        new ColService().getFolderName(this,map,this.handler,result);

        array_left = new ArrayAdapter(this, android.R.layout.simple_list_item_1, (List<String>) result.getData());
        lvLeftMenu.setAdapter(array_left);
    }

    /**
     * 当子线程返回数据时，在handler调用，更新界面
     */
    private void getRealData(){

        array_left.notifyDataSetChanged();
        if(!result.isAvaibleNet()){//网络访问出错
            Toast.makeText(MainActivity.this, "服务器登录接口调用失败", Toast.LENGTH_SHORT).show();
        }else if(result.isSuccess()){
            Toast.makeText(MainActivity.this,result.getInfo(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, result.getInfo(), Toast.LENGTH_SHORT).show();
        }
    }

    //多选按钮弹出框
//  private void alert_dialog(){
//      AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//      builder.setTitle("收藏夹分类");
//      final String[] hobbies = {"分类一", "分类二", "分类三", "分类四"};
//      //    设置一个单项选择下拉框
//      /**
//       * 第一个参数指定我们要显示的一组下拉多选框的数据集合
//       * 第二个参数代表哪几个选项被选择，如果是null，则表示一个都不选择，如果希望指定哪一个多选选项框被选择，
//       * 需要传递一个boolean[]数组进去，其长度要和第一个参数的长度相同，例如 {true, false, false, true};
//       * 第三个参数给每一个多选项绑定一个监听器
//       */
//      builder.setMultiChoiceItems(hobbies, null, new DialogInterface.OnMultiChoiceClickListener()
//      {
//          StringBuffer sb = new StringBuffer(100);
//          @Override
//          public void onClick(DialogInterface dialog, int which, boolean isChecked)
//          {
//              if(isChecked)
//              {
//                  sb.append(hobbies[which] + ", ");
//              }
//              Toast.makeText(MainActivity.this, "爱好为：" + sb.toString(), Toast.LENGTH_SHORT).show();
//          }
//      });
//      builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
//      {
//          @Override
//          public void onClick(DialogInterface dialog, int which)
//          {
//
//          }
//      });
//      builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
//      {
//          @Override
//          public void onClick(DialogInterface dialog, int which)
//          {
//
//          }
//      });
//      builder.show();
//  }

}






