package com.example.acer.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.myapplication.R;
import com.example.acer.myapplication.activity.service.ColService;
import com.example.acer.myapplication.activity.service.resultEnd;
import com.example.acer.myapplication.java_class.Item;
import com.example.acer.myapplication.java_class.ItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import android.support.v7.app.ActionBarActivity;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ColService.GETFOLDERNAME_RESULT_OK:
                    getRealData();
                    break;
                case ColService.GETCOLLECTIONINFO_RESULT_OK:
                    getRealCollectionData();
                    break;
            }
        }
    };
    /**
     * 后端的返回结果
     */
    private resultEnd result = new resultEnd();//侧滑栏返回
    private resultEnd resultCollectiion = new resultEnd();//主界面返回
    //声明相关变量
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private ListView lvRightMenu;
    //    private AlertDialog alert_btn;
    private View view;
    //右侧收藏夹栏内容 Arraylist类型
    private ArrayList<Item> lvr = new ArrayList<Item>();
    //数组容器
    private Menu menu;
    private ArrayAdapter array_left;
    private ItemAdapter array_right;
    private AlertDialog alert_btn;
    private ImageView ivRunningMan;
    private AnimationDrawable mAnimationDrawable;

    //其他变量
    private String dirname = "Java";
    List<String> dirList;
    List<Item> collectionList;

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

        //设置收藏夹列表栏
        this.setupForColList();

        //设置主页面列表
        this.setCollectionList();
//      alert_dialog();

        // 检测剪切板是否变化
        detectClipBoard();

        // 注册剪切板监听
        setClipBoard();

        // 如果是分享链接到收藏加
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                // handleSendText(intent); // Handle text being sent
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                String link = null;
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if ((link = getLink(sharedText)) != null)
                {
                    // 是链接
                    editor.putBoolean("isLink", true);
                } else {
                    editor.putBoolean("isLink", false);
                }
                showbottom(sharedText);
            }
        }

// TODO 覆盖
    }

    @Override
    protected void onResume() {
        super.onResume();

        detectClipBoard();
    }


    // 检测剪切板是否变化
    private void detectClipBoard() {
        // 剪切板是否变化
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Boolean isClipchange = sharedPref.getBoolean(getString(R.string.isClipChange), false);

        if (isClipchange) {
            String newCopy = sharedPref.getString(getString(R.string.newCopy), null);
            String link = null;
            SharedPreferences.Editor editor = sharedPref.edit();

            if ((link = getLink(newCopy)) != null)
            {
                // 是链接
                editor.putBoolean("isLink", true);
            } else {
                editor.putBoolean("isLink", false);
            }
            showbottom(newCopy);
        }
    }

    // 从分享的文字或复制的文字中获取链接
    private String getLink(String origin) {
        Pattern pattern = Pattern.compile(".*((https|http)://[a-z.]*).*");
        Matcher matcher = pattern.matcher(origin);

        // 如果有match
        if (matcher.matches()) {
            String link = matcher.group(1);
            return link;
        } else {
            return null;
        }
    }

    // 注册剪切板监听
    private void setClipBoard() {
        String oldCopy = "";
        final ClipboardManager clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipBoard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {

            // 剪切板发生变化的事件监听
            @Override
            public void onPrimaryClipChanged() {
                ClipData clipData = clipBoard.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                String newCopy = item.getText().toString();

                // 设置剪切板已更新的标志位，并将新复制的内容也存入sharedPreferences
                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putBoolean(getString(R.string.isClipChange), true);
                editor.putString(getString(R.string.newCopy), newCopy);

                editor.commit();

                // Access your context here using YourActivityName.this
//                textView.setText(text);
            }
        });
    }

    private String getNewCopy() {
        // TODO 新获取的剪切板与之前存储的剪切板内容比较
        return null;
    }


    //显示底部悬浮窗
    private void showbottom(String text) {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.bottomdialog, null);

        TextView textView = contentView.findViewById(R.id.newCopy);
        textView.setText(text);

        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        // ok的监听事件
        Button okBtn = contentView.findViewById(R.id.save);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "save", Toast.LENGTH_SHORT).show();
            }
        });
        bottomDialog.show();
    }

//获取所有组件


    @SuppressLint("ResourceType")
    private void findViews() {
        menu = (Menu) findViewById(R.menu.col_tab);
        view = (View) findViewById(R.id.bottom_navigation);
        lvRightMenu = (ListView) findViewById(R.id.right_menu);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.left_menu);
    }//findview


    //收藏夹界面

    /**
     * 从后台请求数据，并给收藏夹列表的adapter设置数据
     */
    private void setupForColList() {
        if (result.getData() == null) {
            List<String> list = new ArrayList<String>();
            result.setData(list);
        }

        Map<String, String> map = new HashMap<String, String>();
        SharedPreferences sh = getSharedPreferences("User_info", Context.MODE_PRIVATE);
        String nameFromMemory = sh.getString("userName", null);
        map.put("username", nameFromMemory);
        //请求数据
        new ColService().getFolderName(this, map, this.handler, result);
        dirList = (List<String>) result.getData();
        array_left = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dirList);
        lvLeftMenu.setAdapter(array_left);
        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dirname = dirList.get(i);
                ((List<Item>) resultCollectiion.getData()).clear();
                Toast.makeText(MainActivity.this, dirname, Toast.LENGTH_SHORT).show();
                setCollectionList();
            }
        });
    }

    /**
     * 当子线程返回数据时，在handler调用，更新收藏夹列表界面
     */
    private void getRealData() {
        array_left.notifyDataSetChanged();
        if (!result.isAvaibleNet()) {//网络访问出错
            Toast.makeText(MainActivity.this, "服务器登录接口调用失败", Toast.LENGTH_SHORT).show();
        } else if (result.isSuccess()) {
            Toast.makeText(MainActivity.this, result.getInfo(), Toast.LENGTH_SHORT).show();
        } else {
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

    //收藏信息界面

    /**
     * 从     *后台请求数据，并给收藏夹主界面列表的adapter设置数据
     */
    private void setCollectionList() {
        if (resultCollectiion.getData() == null) {
            List<Item> itemList = new ArrayList<Item>();
            resultCollectiion.setData(itemList);
        }
        Map<String, String> mapCollection = new HashMap<String, String>();
        SharedPreferences sh = getSharedPreferences("User_info", Context.MODE_PRIVATE);
        String nameFromMemory = sh.getString("userName", null);
        mapCollection.put("username", nameFromMemory);
        mapCollection.put("dirname", dirname);

        //请求数据
        new ColService().getCollectionInfo(this, mapCollection, this.handler, resultCollectiion);
        collectionList = (List<Item>) resultCollectiion.getData();
        array_right = new ItemAdapter(this, R.layout.item, collectionList);
        lvRightMenu.setAdapter(array_right);

        //响应事件，打开相应的网页
        lvRightMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(collectionList.get(i).getValue()));
                startActivity(intent);
                Toast.makeText(MainActivity.this, collectionList.get(i).getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        array_right.notifyDataSetChanged();
    }

    /**
     * 当子线程返回数据时，在handler调用，更新收藏信息主界面
     */
    private void getRealCollectionData() {
        if (!resultCollectiion.isAvaibleNet()) {//网络访问出错
            Toast.makeText(MainActivity.this, "服务器登录接口调用失败", Toast.LENGTH_SHORT).show();
        } else if (resultCollectiion.isSuccess()) {
            Toast.makeText(MainActivity.this, resultCollectiion.getInfo(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, resultCollectiion.getInfo(), Toast.LENGTH_SHORT).show();
        }
    }

}







