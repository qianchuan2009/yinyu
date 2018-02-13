package com.lyz.wayy.main.frame;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lyz.wayy.ConstFile;
import com.lyz.wayy.R;
import com.lyz.wayy.Utils;
import com.lyz.wayy.bean.Friend;
import com.lyz.wayy.main.adapter.Adapter1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 第一个tab页的碎片
 * Created by chenxx on 2018/2/7.
 */

public class Fragment1 extends Fragment {

//    private GridView gridView;
    private Context context;
    private RecyclerView recyclerView;//显示图片的布局
    private Adapter1 adapter1;//recyclerView的适配器
    List<Friend> dataList;
    public void setContext(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment1,container,false);
        recyclerView =(RecyclerView)view.findViewById(R.id.recycler1);
        if(getContext()!=null){
            LinearLayoutManager ms= new LinearLayoutManager(getContext());
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(ms);
        }else {
        LinearLayoutManager ms= new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(ms);
        }
//        gridView=view.findViewById(R.id.gridview1);
        List<Integer> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add(R.drawable.eb);
        }
        adapter1=new Adapter1(list);
        recyclerView.setAdapter(adapter1);
        getFriend();
        return view;
    }

    //朋友
    private void getFriend(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=friend&web_uid=" +ConstFile.uId;
                    String response = example.run(url);
//                    JSONArray arr=new JSONArray(response);
                    dataList=Friend.arrayFriendFromData(response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
