package com.lyz.wayy.lucky;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.lyz.wayy.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/15.
 */

public class AdapterLucky extends BaseAdapter {

    ArrayList<LuckyBean.ItemBean> list=new ArrayList<>();//图片的人名
    Context context;

    public AdapterLucky( Context ct,ArrayList<LuckyBean.ItemBean> _list){
        this.list=_list;
        this.context=ct;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LuckyBean.ItemBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lucky_grid_item, null);
        }
        ImageView imgView= (ImageView) convertView.findViewById(R.id.lucky_item_imageview);
//            imgView.setImageResource(R.drawable.lucky_item_bg);
//        setImage(imgView,position);
//        TextView textView= (TextView) convertView.findViewById(R.id.grid_item_text);
//        textView.setText(getItem(position)[0]);
        return convertView;
    }
}
