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

import com.lyz.wayy.pub.ConstFile;
import com.lyz.wayy.MainActivity;
import com.lyz.wayy.R;
import com.lyz.wayy.pub.Utils;
import com.lyz.wayy.bean.PkgInfo;
import com.lyz.wayy.main.adapter.AdapterPkg;

import java.util.ArrayList;
import java.util.List;

/**
 *第二个tab页的碎片
 * Created by chenxx on 2018/2/7.
 */

public class FragmentPkg extends Fragment {

    //    private GridView gridView;
    Context context;
    private AdapterPkg adapterPkg; //recyclerView的适配器
    private RecyclerView recyclerView; //显示图片的布局
    private Handler handler;//传过来的handler
    List<PkgInfo> dataList=new ArrayList<>();
    public void setHandle(Handler handle){
        this.handler=handle;
    }
    public void setContext(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentpkg,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_pkg);
//        if(getContext()!=null){
//            LinearLayoutManager ms= new LinearLayoutManager(getContext());
//            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
//            recyclerView.setLayoutManager(ms);
//        }else {
        LinearLayoutManager ms= new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(ms);
        getPkg();
        adapterPkg=new AdapterPkg(dataList,context);
        adapterPkg.setOnMyItemClickListener(new AdapterPkg.OnMyItemClickListener() {
            @Override
            public void myClick( PkgInfo pkgInfo , int pos) {
//                String str=textView.getText().toString();
                Message message=new Message();
                message.what= MainActivity.CHANGE_PKG;
                Bundle bundle=new Bundle();
//                bundle.putString("name",str);
                bundle.putSerializable("pkg",pkgInfo);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
        recyclerView.setAdapter(adapterPkg);
        return view;
    }

    //朋友
    private void getPkg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=Package&act=getPackageInfo&web_uid=" +ConstFile.uId;
                    String response = example.run(url);
//                    JSONArray arr=new JSONArray(response);
                    dataList= PkgInfo.arrayPkgInfoFromData(response,"4");
                    if(dataList==null){
                        dataList=new ArrayList<>();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterPkg.reSetDatalist(dataList);
                            adapterPkg.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
