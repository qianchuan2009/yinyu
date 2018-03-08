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
import com.lyz.wayy.bean.Friend;
import com.lyz.wayy.main.adapter.AdapterFriend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *第二个tab页的碎片
 * Created by chenxx on 2018/2/7.
 */

public class FragmentFriend extends Fragment {

//    private GridView gridView;
    Context context;
    private AdapterFriend adapterFriend; //recyclerView的适配器
    private RecyclerView recyclerView; //显示图片的布局
    private Handler handler;//传过来的handler
    List<Friend> dataList=new ArrayList<>();
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
//        if(getContext()!=null){
//            LinearLayoutManager ms= new LinearLayoutManager(getContext());
//            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
//            recyclerView.setLayoutManager(ms);
//        }else {
            LinearLayoutManager ms= new LinearLayoutManager(context);
            ms.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(ms);
        getFriend();
        adapterFriend=new AdapterFriend(dataList,context);
        adapterFriend.setOnMyItemClickListener(new AdapterFriend.OnMyItemClickListener() {
            @Override
            public void myClick( Friend frd , int pos) {
//                String str=textView.getText().toString();
                Message message=new Message();
                message.what= MainActivity.CHANGE_NAME;
                Bundle bundle=new Bundle();
//                bundle.putString("name",str);
                bundle.putSerializable("friend",frd);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
        recyclerView.setAdapter(adapterFriend);
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
                    dataList= Friend.arrayFriendFromData(response);
                    Comparator<Friend> com=new FriendComparator(false);
                    Collections.sort(dataList, com);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterFriend.reSetDatalist(dataList);
                            adapterFriend.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
