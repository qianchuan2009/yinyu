package com.lyz.wayy.main.frame;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lyz.wayy.R;
import com.lyz.wayy.main.adapter.Adapter1;

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
//        gridView.setAdapter(adapter1);
        return view;
    }
}
