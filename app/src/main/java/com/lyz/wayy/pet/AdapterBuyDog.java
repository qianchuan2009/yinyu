package com.lyz.wayy.pet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.bean.BuyDog;
import com.lyz.wayy.lucky.LuckyBean;
import com.lyz.wayy.msg.MsgListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/15.
 */

public class AdapterBuyDog extends BaseAdapter {

    ArrayList<BuyDog> list=new ArrayList<>();//图片的人名
    Context context;

    public AdapterBuyDog(Context ct, ArrayList<BuyDog> _list){
        this.list=_list;
        this.context=ct;
    }

    public void reSetDatalist(ArrayList<BuyDog> _list) {
         this.list=_list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BuyDog getItem(int position) {
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
            view = View.inflate(context, R.layout.buydog_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.price = (TextView) view.findViewById(R.id.price);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.buydog_item_imageview);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        BuyDog buyDog=getItem(position);
        String tid=buyDog.getTId();
        if ("1".equalsIgnoreCase(tid)){
            tid="";
        }
        int id = context.getResources().getIdentifier("animal"+tid+"_04_0001", "drawable", context.getPackageName());
        viewHolder.imageView.setImageResource(id);
        String price=buyDog.getList().get_$1().getFBPrice()+"";
        viewHolder.price.setText(price);

//        ImageView imgView= (ImageView) convertView.findViewById(R.id.lucky_item_imageview);
//            imgView.setImageResource(R.drawable.lucky_item_bg);
//        setImage(imgView,position);
//        TextView textView= (TextView) convertView.findViewById(R.id.grid_item_text);
//        textView.setText(getItem(position)[0]);
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView price;//价格
    }
}
