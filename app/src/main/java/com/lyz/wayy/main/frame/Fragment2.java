package com.lyz.wayy.main.frame;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyz.wayy.MainActivity;
import com.lyz.wayy.R;
import com.lyz.wayy.main.adapter.Adapter2;

import java.util.ArrayList;
import java.util.List;

/**
 *第二个tab页的碎片
 * Created by chenxx on 2018/2/7.
 */

public class Fragment2 extends Fragment {

//    private GridView gridView;
    Context context;
    private Adapter2 adapter2; //recyclerView的适配器
    private RecyclerView recyclerView; //显示图片的布局
    private Handler handler;//传过来的handler

    public void setHandle(Handler handle){
        this.handler=handle;
    }
    public void setContext(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler2);
        if(getContext()!=null){
            LinearLayoutManager ms= new LinearLayoutManager(getContext());
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(ms);
        }else {
            LinearLayoutManager ms= new LinearLayoutManager(context);
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(ms);
        }
//        gridView=view.findViewById(R.id.gridview2);
        List<String> listStr=new ArrayList<>();
        List<Integer> listImg=new ArrayList<>();
        for (int i=0;i<10;i++){
            listImg.add(R.drawable.eb);
            if(i%2==0){
                listStr.add("赵六");
            }else {
                listStr.add("王五");
            }
        }
        adapter2=new Adapter2(listStr,listImg);
        adapter2.setOnMyItemClickListener(new Adapter2.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos) {
                TextView textView=(TextView)v;
                String str=textView.getText().toString();
                Message message=new Message();
                message.what= MainActivity.CHANGE_NAME;
                Bundle bundle=new Bundle();
                bundle.putString("name",str);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
        recyclerView.setAdapter(adapter2);
        return view;
    }
}
