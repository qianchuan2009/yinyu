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
import com.lyz.wayy.bean.PkgInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 第二个tab页碎片的RecyclerView的适配器
 * Created by chenxx on 2018/2/7.
 */

public class AdapterPkg extends RecyclerView.Adapter {

    List<PkgInfo> list=new ArrayList<>();//图片的人名
    Context context;
    public AdapterPkg(List<PkgInfo> _list, Context ct){
        this.list=_list;
        this.context=ct;
    }

    public  void reSetDatalist(List<PkgInfo> _list){
        list=_list;
    }

    private AdapterPkg.OnMyItemClickListener listener;
    public void setOnMyItemClickListener(AdapterPkg.OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(PkgInfo frd, int pos);
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
//        private TextView textView;
        public ViewHolder(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.adapter_img);
//            textView=(TextView)view.findViewById(R.id.adapter2_txt);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PkgInfo pkg=list.get(position);
        AdapterPkg.ViewHolder viewHolder=(AdapterPkg.ViewHolder)holder;
        String jieDuanStr="Animal"+pkg.getDogId()+"_04";
//        String url="file:///android_asset/Animal/Animal"+pkg.getDogId()+"/"+jieDuanStr+"/"+"animal"+pkg.getDogId()+"_04_0001.png";
//        viewHolder.textView.setText(frd.getDogName());
//        Glide.with(context).load(url).into(viewHolder.imageView);
        int id = context.getResources().getIdentifier("animal"+pkg.getDogId()+"_04_0001", "drawable", context.getPackageName());
        viewHolder.imageView.setImageResource(id);

        final int num=position;
        if (listener!=null){
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(pkg,num);
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
        final AdapterPkg.ViewHolder viewHolder=new AdapterPkg.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pkg_item,parent,false));
        return viewHolder;
    }
}