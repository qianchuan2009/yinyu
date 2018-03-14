package com.lyz.wayy.clothes;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.lyz.wayy.R;
import com.lyz.wayy.bean.ClothesInfoBean;
import com.lyz.wayy.pub.ConstFile;
import com.lyz.wayy.pub.Utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by helch on 2018/2/15.
 */

public class AdapterBuyClothes extends RecyclerView.Adapter<AdapterBuyClothes.ViewHolder> {

    ArrayList<ClothesInfoBean> list=new ArrayList<>();//图片的人名
    Context context;

    public AdapterBuyClothes(Context ct, ArrayList<ClothesInfoBean> _list){
        this.list=_list;
        this.context=ct;
    }

    public void reSetDatalist(ArrayList<ClothesInfoBean> _list) {
         this.list=_list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyclothes_grid_item, parent, false);
        return new ViewHolder(view);
    }

    private AdapterBuyClothes.OnMyItemClickListener listener;
    public void setOnMyItemClickListener(AdapterBuyClothes.OnMyItemClickListener listener){
        this.listener = listener;

    }

    public interface OnMyItemClickListener{
        void myClick(ClothesInfoBean boyDog, int pos);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ClothesInfoBean buyDog=list.get(position);
        final String url = ConstFile.serverUrl + "images/virtualimage/" + buyDog.getSort() + "/" + buyDog.getXgraphic();
        Glide.with(context).load(url)
                .placeholder(R.mipmap.init)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
//        String url="https://img.gcall.com/dca5/M00/10/8E/wKhoNlggetaENWylAAAAAAAAAAA457.jpg";
//        String url2="http://www.5ieng.cn/images/virtualimage/10/002x.gif";
//        Glide.with(context)
//                .load(url)
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        //原始图片宽高
//                        int imageWidth = resource.getWidth();
//                        int imageHeight = resource.getHeight();
//                        //按比例收缩图片
//                        float ratio=(float) ((imageWidth*1.0)/(110*1.0));
//                        int height=(int) (imageHeight*1.0/ratio);
//                        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
//                        params.width=110;
//                        params.height=height;
//                        holder.imageView.setImageBitmap(resource);
//                    }
//                });


        String price=buyDog.getCost()+"";
        ((ViewHolder) holder).price.setText(price);

        if (listener!=null){
            final AdapterBuyClothes self=this;
            ((ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView price = null;
        public pl.droidsonroids.gif.GifImageView imageView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            imageView= (pl.droidsonroids.gif.GifImageView) itemView.findViewById(R.id.buydog_item_imageview2);
        }
    }
}
