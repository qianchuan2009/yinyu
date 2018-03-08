package com.lyz.wayy.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.pub.Utils;
import com.lyz.wayy.main.WebActivity2;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/2/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SettingsHtmlListViewActivity extends Activity {

    private ListView listView;

    private ArrayList<String> list=new ArrayList<String>();///111111
//    com.lyz.xy28.main.ListViewAdapter adapter;//11111

    private List<Object> listitems1 = new ArrayList<Object>();// 第一栏三个选项
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_settingshtml_listview);

        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        String title = intent.getStringExtra("title");
        TextView t= (TextView) findViewById(R.id.title);
        t.setText(title);

        ImageView imgView= (ImageView) findViewById(R.id.back);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView)findViewById(R.id.listView);
//        adapter = new ListViewAdapter(this,R.layout.acticity_personalsetting_html_item, list,dh);



        listitems1.clear();
        for (int i = 0; i < Utils.getStrData().length; i++) {
            SettingBanner sb = new SettingBanner();
            sb.setIcon(R.mipmap.src_img_ssq);
            sb.setCaption(Utils.getStrData()[i][0]);
            listitems1.add(sb);
        }
        com.lyz.wayy.setting.ListViewAdapter la1 = new com.lyz.wayy.setting.ListViewAdapter(SettingsHtmlListViewActivity.this, listitems1);
        la1.setListItemView(new com.lyz.wayy.setting.ListViewAdapter.ListItemView() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent,
                                List<Object> list, LayoutInflater layoutInflater) {
                View view = null;
                if (layoutInflater != null) {
                    view = layoutInflater.inflate(R.layout.acticity_personalsetting_html_item, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.app_icon);
                    setImage(imageView,position);
                    TextView tv_title = (TextView) view.findViewById(R.id.app_title);
                    TextView tv_content = (TextView) view.findViewById(R.id.app_tips);
                    SettingBanner sb = (SettingBanner) list.get(position);
                    tv_title.setText(sb.getCaption());
//                    tv_content.setPadding(0, 0, 0, (int) (parent.getHeight() * 0.015));
//                    imageView.setImageResource(sb.getIcon());
                }
                return view;
            }
        });
        //实现列表的显示
        listView.setAdapter(la1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SettingsHtmlListViewActivity.this, WebActivity2.class);
                intent.putExtra("dh", Utils.getStrData()[i][1]);
                intent.putExtra("title",Utils.getStrData()[i][0]);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });

    }


    private  void setImage(ImageView imgView,int index){
        switch (index){
            case 0:
                imgView.setImageResource(R.mipmap.src_img_ssq);
                break;
            case 1:imgView.setImageResource(R.mipmap.src_img_3d);
                break;
            case 2:imgView.setImageResource(R.mipmap.src_img_qlc);
                break;
            case 3:imgView.setImageResource(R.mipmap.src_img_qxc);
                break;
            case 4:imgView.setImageResource(R.mipmap.src_img_pls);
                break;
            case 5:imgView.setImageResource(R.mipmap.src_img_plw);
                break;
            case 6:imgView.setImageResource(R.mipmap.src_img_11x5);
                break;
            case 7:imgView.setImageResource(R.mipmap.src_img_ahk3);
                break;
            case 8:imgView.setImageResource(R.mipmap.src_img_dlt);
                break;
        }

    }
}
