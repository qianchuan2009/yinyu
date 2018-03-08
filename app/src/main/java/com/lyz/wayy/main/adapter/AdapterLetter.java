package com.lyz.wayy.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.bean.Letter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by helch on 2018/2/15.
 */

public class AdapterLetter extends BaseAdapter {

    ArrayList<Letter> list=new ArrayList<>();//图片的人名
    Context context;

    public AdapterLetter(Context ct, ArrayList<Letter> _list){
        this.list=_list;
        this.context=ct;
    }

    public void reSetDatalist(ArrayList<Letter> _list) {
         this.list=_list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Letter getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//
//        }

        ViewHolder viewHolder;//存储列表视图中的组件，避免重复查找
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.letter_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.price = (TextView) view.findViewById(R.id.time_txt);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.buydog_item_imageview);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        Letter letter=getItem(position);
        int sendTime=letter.getSendTime();
        Date  date=new Date(sendTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        viewHolder.price.setText(dateString);

        int state=letter.getStatus();
        String name=(0==state)?"sitemebutton":"openmes";
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        viewHolder.imageView.setImageResource(id);

        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView price;//价格
    }
}
