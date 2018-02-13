package com.lyz.wayy.main.adapter;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.lyz.wayy.R;
import com.lyz.wayy.bean.Friend;

import java.util.ArrayList;
import java.util.List;


/**
 * 第二个tab页碎片的RecyclerView的适配器
 * Created by chenxx on 2018/2/7.
 */

public class AdapterPkg extends RecyclerView.Adapter {

    List<Friend> list=new ArrayList<>();//图片的人名
    Context context;
    public AdapterPkg(List<Friend> _list,Context ct){
        this.list=_list;
        this.context=ct;
    }

    public  void reSetDatalist(List<Friend> _list){
        list=_list;
    }

    private AdapterFriend.OnMyItemClickListener listener;
    public void setOnMyItemClickListener(AdapterFriend.OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(Friend frd, int pos);
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.adapter2_img);
            textView=(TextView)view.findViewById(R.id.adapter2_txt);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Friend frd=list.get(position);
        AdapterPkg.ViewHolder viewHolder=(AdapterPkg.ViewHolder)holder;
        viewHolder.textView.setText(frd.getUserName());
        Glide.with(context).load(frd.getHeadPic()).into(viewHolder.imageView);
        final int num=position;
        final TextView textViewTemp=viewHolder.textView;
        if (listener!=null){
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(frd,num);
                }
            });
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(frd,num);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final AdapterPkg.ViewHolder viewHolder=new AdapterPkg.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter2,parent,false));
        return viewHolder;
    }
}