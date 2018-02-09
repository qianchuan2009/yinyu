package com.lyz.wayy.main.adapter;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lyz.wayy.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 第二个tab页碎片的RecyclerView的适配器
 * Created by chenxx on 2018/2/7.
 */

public class Adapter2 extends RecyclerView.Adapter {

    List<String> listStr=new ArrayList<>();//图片的人名

    List<Integer> listImg=new ArrayList<>();//图片的路径id

    public Adapter2(List<String> listStr, List<Integer> listImg){
        this.listStr=listStr;
        this.listImg=listImg;
    }

    private OnMyItemClickListener listener;
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(View v, int pos);
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
        ViewHolder viewHolder=(ViewHolder)holder;
        viewHolder.textView.setText(listStr.get(position));
        viewHolder.imageView.setImageResource(listImg.get(position));
        final int num=position;
        final TextView textViewTemp=viewHolder.textView;
        if (listener!=null){
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(v,num);
                }
            });
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(textViewTemp,num);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listStr.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder=new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter2,parent,false));
        return viewHolder;
    }
//    private Context context;
//
//    private List<Picture> pictures=new ArrayList<>();
//
//    public Adapter2(List<String> titles, List<Integer> images, Context context) {
//        super();
//        this.context = context;
//        for (int i = 0; i < images.size(); i++) {
//            Picture picture = new Picture(titles.get(i), images.get(i));
//            pictures.add(picture);
//        }
//
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
//            convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter2, null);
//
//            // 初始化组件
//            viewHolder.image = (ImageView) convertView.findViewById(R.id.adapter2_img);
//            viewHolder.title = (TextView) convertView.findViewById(R.id.adapter2_txt);
//            // 给converHolder附加一个对象
//            convertView.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        // 给组件设置资源
//        Picture picture = pictures.get(position);
//        viewHolder.image.setImageResource(picture.getImgId());
//        viewHolder.title.setText(picture.getImgName());
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
//        public TextView title;
//        public ImageView image;
//    }
//
//    class Picture{
//
//        private String imgName;
//
//        private int imgId;
//
//        public Picture(String imgName,int imgId){
//            this.imgId=imgId;
//            this.imgName=imgName;
//        }
//
//        public String getImgName(){
//            return imgName;
//        }
//
//        public int getImgId(){
//            return imgId;
//        }
//    }
}