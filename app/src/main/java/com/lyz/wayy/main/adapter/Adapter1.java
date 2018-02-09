package com.lyz.wayy.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.lyz.wayy.R;

import java.util.List;

/**
 * 第一个tab页碎片的RecyclerView的适配器
 * Created by chenxx on 2018/1/31.
 */

public class Adapter1 extends RecyclerView.Adapter {

    private Context context;

    private List<Integer> list; //图片的路径id

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public ViewHolder(View view){
            super(view);
            imageView=(ImageView) view.findViewById(R.id.adapter1_img);
        }
    }
    public Adapter1(List<Integer> list){
        this.list=list;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder)holder;
        viewHolder.imageView.setImageResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder=new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter1,parent,false));
        return viewHolder;
    }


    //    private Context context;
//
//    private List<Integer> pictures=new ArrayList<>();
//
//    public Adapter1(List<Integer> images, Context context) {
//        super();
//        this.context = context;
//        this.pictures=images;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return pictures.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder=null;
//        if(convertView==null){
//            viewHolder = new ViewHolder();
//            // 获得容器
//            convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter1,null);
//
//            // 初始化组件
//            viewHolder.image = (ImageView) convertView.findViewById(R.id.adapter1_img);
//            // 给converHolder附加一个对象
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        // 给组件设置资源
//        viewHolder.image.setImageResource(pictures.get(position));
//        return convertView;
//    }
//
//    @Override
//    public int getCount() {
//        if (null != pictures) {
//            return pictures.size();
//        } else {
//            return 0;
//        }
//    }
//
//    class ViewHolder {
//        public ImageView image;
//    }
}