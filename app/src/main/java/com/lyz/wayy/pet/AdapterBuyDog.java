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
import com.lyz.wayy.bean.PkgInfo;
import com.lyz.wayy.lucky.LuckyBean;
import com.lyz.wayy.main.MainFragment;
import com.lyz.wayy.main.adapter.AdapterPkg;
import com.lyz.wayy.msg.MsgListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/15.
 */

public class AdapterBuyDog extends RecyclerView.Adapter<AdapterBuyDog.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buydog_grid_item, parent, false);
        return new ViewHolder(view);
    }

    private AdapterBuyDog.OnMyItemClickListener listener;
    public void setOnMyItemClickListener(AdapterBuyDog.OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(BuyDog boyDog, int pos);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BuyDog buyDog=list.get(position);
        String tid=buyDog.getTId();
        if ("1".equalsIgnoreCase(tid)){
            tid="";
        }
        int id =  context.getResources().getIdentifier("animal"+tid+"_04_0001", "drawable", context.getPackageName());
        ((ViewHolder) holder).imageView.setImageResource(id);
        String price=buyDog.getList().get_$1().getFBPrice()+"";
        ((ViewHolder) holder).price.setText(price);

//        ((ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        if (listener!=null){
            final AdapterBuyDog self=this;
            ((ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    for (BuyDog pp:list) {
//                        if (pp.getStatus()==1){
//                            pp.setStatus(0);
//                        }
//                    }
//                    pkg.setStatus(1);

                    listener.myClick(buyDog,position);
                    self.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return  list.size();
    }



//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
////        if (convertView == null) {
////
////        }
//
//        ViewHolder viewHolder;//存储列表视图中的组件，避免重复查找
//        View view;
//        if (convertView == null) {
//            view = View.inflate(context, R.layout.buydog_grid_item, null);
//            viewHolder = new ViewHolder();
//            viewHolder.price = (TextView) view.findViewById(R.id.price);
//            viewHolder.imageView= (ImageView) view.findViewById(R.id.buydog_item_imageview);
//            view.setTag(viewHolder); // 将ViewHolder存储在View中
//        } else {
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
//        }
//        BuyDog buyDog=getItem(position);
//        String tid=buyDog.getTId();
//        if ("1".equalsIgnoreCase(tid)){
//            tid="";
//        }
//        int id = context.getResources().getIdentifier("animal"+tid+"_04_0001", "drawable", context.getPackageName());
//
// String url = ConstFile.serverUrl + "images\\virtualimage\\" + i + "\\" + arr[i] + ".gif";
// viewHolder.imageView.setImageResource(id);
//        String price=buyDog.getList().get_$1().getFBPrice()+"";
//        viewHolder.price.setText(price);
//
////        ImageView imgView= (ImageView) convertView.findViewById(R.id.lucky_item_imageview);
////            imgView.setImageResource(R.drawable.lucky_item_bg);
////        setImage(imgView,position);
////        TextView textView= (TextView) convertView.findViewById(R.id.grid_item_text);
////        textView.setText(getItem(position)[0]);
//        return view;
//    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView price = null;
        public ImageView imageView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            imageView= (ImageView) itemView.findViewById(R.id.buydog_item_imageview);
        }
    }
}
