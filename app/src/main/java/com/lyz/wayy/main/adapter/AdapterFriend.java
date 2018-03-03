package com.lyz.wayy.main.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyz.wayy.CharmUtil;
import com.lyz.wayy.R;
import com.lyz.wayy.bean.Friend;
import com.lyz.wayy.bean.PkgInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 第二个tab页碎片的RecyclerView的适配器
 * Created by chenxx on 2018/2/7.
 */

public class AdapterFriend extends RecyclerView.Adapter {

    List<Friend> list=new ArrayList<>();//图片的人名
    Context context;
    public AdapterFriend(List<Friend> _list, Context ct){
        this.list=_list;
        this.context=ct;
    }

    public  void reSetDatalist(List<Friend> _list){
        list=_list;
    }

    private OnMyItemClickListener listener;
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(Friend frd, int pos);
    }

    private class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private TextView ordertv;
        private TextView levertv;
        private TextView dogtv;
        private RelativeLayout selectbfrl;
        public ViewHolder(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.adapter2_img);
            textView=(TextView)view.findViewById(R.id.adapter2_txt);
            ordertv=(TextView)view.findViewById(R.id.order);
            levertv=(TextView)view.findViewById(R.id.level);
            dogtv=(TextView)view.findViewById(R.id.dog_level);
            selectbfrl=(RelativeLayout)view.findViewById(R.id.select_bg);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Friend frd=list.get(position);
        ViewHolder viewHolder=(ViewHolder)holder;
        viewHolder.textView.setText(frd.getUserName());
        viewHolder.ordertv.setText(position+1+"");
        int level = CharmUtil.expToGrade4Man(frd.getExp());
        viewHolder.levertv.setText(level+"");
        int dogLevel = CharmUtil.toLevel(frd.getCharm());
        viewHolder.dogtv.setText(dogLevel+"");
        if(position==0){
            viewHolder.ordertv.setTextColor(context.getResources().getColor(R.color.frd_1));
        }else if(position==1){
            viewHolder.ordertv.setTextColor(context.getResources().getColor(R.color.frd_2));
        }else if(position==2){
            viewHolder.ordertv.setTextColor(context.getResources().getColor(R.color.frd_3));
        }else{
            viewHolder.ordertv.setTextColor(context.getResources().getColor(R.color.white));
        }

        if (frd.isSelected()){
            viewHolder.selectbfrl.setBackground(context.getResources().getDrawable(R.mipmap.headpic_red));
        }else{
            viewHolder.selectbfrl.setBackground(context.getResources().getDrawable(R.mipmap.headpic3));
        }

        Glide.with(context).load(frd.getHeadPic()).into(viewHolder.imageView);
        final int num=position;
        final TextView textViewTemp=viewHolder.textView;
        if (listener!=null){
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    steFrdSelect(frd);
                    listener.myClick(frd,num);
                }
            });
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    steFrdSelect(frd);
                    listener.myClick(frd,num);
                }
            });
        }
    }

    private  void steFrdSelect(Friend frd){
        for (Friend ff:list) {
            if (ff.isSelected())
            ff.setSelected(false);
        }
        frd.setSelected(true);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder=new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter2,parent,false));
        return viewHolder;
    }

}