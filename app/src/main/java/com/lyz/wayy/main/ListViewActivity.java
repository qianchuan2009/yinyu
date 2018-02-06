package com.lyz.wayy.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lyz.wayy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/2/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ListViewActivity extends Activity {

    private ListView listView;

    private ArrayList<String> list=new ArrayList<String>();///111111
    ListViewAdapter adapter;//11111


    private String dh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_listview);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index",0);
        dh = intent.getStringExtra("dh");
        String title = intent.getStringExtra("title");
        TextView t= (TextView) findViewById(R.id.title);
        t.setText(title+"开奖结果");

        ImageView imgView= (ImageView) findViewById(R.id.back);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        listView = (ListView)findViewById(R.id.listView);
        init1stPage();

//        Button wfsm= (Button) findViewById(R.id.wfsm);
//        Button skill= (Button) findViewById(R.id.wfjq);
//        wfsm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ListViewActivity.this, WebActivity2.class);
//                intent.putExtra("dh", dh);
//                intent.putExtra("title","玩法说明");
//                intent.putExtra("type",1);
//                startActivity(intent);
//            }
//        });
//
//        skill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ListViewActivity.this, WebActivity2.class);
//                intent.putExtra("dh", dh);
//                intent.putExtra("title","玩法技巧");
//                intent.putExtra("type",2);
//                startActivity(intent);
//            }
//        });

    }
    private void init1stPage(){
        String url="http://f.apiplus.net/"+dh+"-20.json";
        dowloadData(url);
//        ListView listView= (ListView) ((ListViewFragment)(fragments.get(0))).getScrollableView();

        SharedPreferences sp =this.getSharedPreferences("mydata", MODE_PRIVATE);
        String dataStr=sp.getString(dh,null);
        if (dataStr!=null){
            try{
                JSONObject jObj=new JSONObject(dataStr);
                final JSONArray arr=jObj.getJSONArray("data");

                if (arr!=null){

                    for (int i=0;i<arr.length();i++){
                        try {
                            list.add(arr.getString(i));
                        } catch (JSONException e) {
                            continue;
                        }
                    }
                }
            }catch (Exception e){
                list.clear();
            }

        }

        adapter = new ListViewAdapter(this,R.layout.listview_item, list,dh);
        //实现列表的显示
        listView.setAdapter(adapter);
    }

    private  void dowloadData(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result=false;
                ListViewActivity.OkHttps example = new ListViewActivity.OkHttps();
                try{
                    String response = example.run(url);
                    JSONObject jObj=new JSONObject(response);
                    final JSONArray arr=jObj.getJSONArray("data");


                    if (arr!=null){

                        for (int i=0;i<arr.length();i++){
                            try {
                                list.add(arr.getString(i));


                            } catch (JSONException e) {
                                continue;
                            }
                        }


                        // 需要做的事:发送消息
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);

                        Context ctx =ListViewActivity.this;
                        SharedPreferences sp =ctx.getSharedPreferences("mydata", MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString(dh, response);
                        editor.commit();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.setArrList(list);
                adapter.notifyDataSetChanged();
            }
            super.handleMessage(msg);
        };
    };

    private class OkHttps{
        OkHttpClient client = new OkHttpClient();
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
}
