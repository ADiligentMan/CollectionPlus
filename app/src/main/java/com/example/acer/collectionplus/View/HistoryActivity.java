package com.example.acer.collectionplus.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.acer.collectionplus.Adapter.HistoryAdapter;
import com.example.acer.collectionplus.JavaBean.HistoryBean;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.SqlLiteUtils.History_DBUtils;
import com.example.acer.collectionplus.SqlLiteUtils.SQLiteDbHelper;

import org.apache.http.HeaderIterator;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
private List<HistoryBean> historyBeanList=new ArrayList<HistoryBean>();
private History_DBUtils historydao;
private SQLiteDbHelper sqLiteDbHelper;
private ImageView back_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        back_image=findViewById(R.id.imageView);
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HistoryActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
       //初始化数据
        sqLiteDbHelper=  new SQLiteDbHelper(this);
        historydao=new History_DBUtils(sqLiteDbHelper);
        historyBeanList=historydao.queryhistory();
        Log.d("historyListActivity",historyBeanList.toString());
        HistoryAdapter historyAdapter=new HistoryAdapter(HistoryActivity.this,R.layout.history_item_layout
        ,historyBeanList);
        ListView listView=(ListView)findViewById(R.id.historylist);
        listView.setAdapter(historyAdapter);


    }
}
