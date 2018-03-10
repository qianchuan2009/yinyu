
package com.lyz.wayy.main;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.pub.Utils;
import com.lzy.widget.tab.CircleIndicator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainFragment extends Fragment {

    private Context context;// 本类环境

    private ViewPager pagerHeader;
    private View titleBar_Bg;
    private TextView titleBar_title;
    private View status_bar_fix;
    private View titleBar;


    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_main, container, false);
        context = this.getActivity();
//        getActivity().findViewById(R.id.layout_title).setVisibility(View.VISIBLE);
//        TextView titleView = (TextView) getActivity().findViewById(R.id.titlebar);
//        titleView.setText("主页");
        initView(view);
//        initData();
        return view;
    }




    protected void initView(View view) {
        pagerHeader = (ViewPager) view.findViewById(R.id.pagerHeader);
        pagerHeader.setAdapter(new HeaderAdapter());
        CircleIndicator ci = (CircleIndicator) view.findViewById(R.id.ci);
        ci.setViewPager(pagerHeader);



        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "点击了条目" + position, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(context,ListViewActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("dh", Utils.getStrData()[position][1]);
                intent.putExtra("title",Utils.getStrData()[position][0]);
                startActivity(intent);
            }
        });
    }
    private   class OkHttps{
        OkHttpClient client = new OkHttpClient();
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//    }




    /**
     * 头布局的适配器
     */
    private class HeaderAdapter extends PagerAdapter {

        public int[] images = new int[]{//
                R.mipmap.image1, R.mipmap.image2, R.mipmap.image3};

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[position]);
            container.addView(imageView);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), "第" + position + "页", Toast.LENGTH_SHORT).show();
//                }
//            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public static String getCurrentPkgName(Context context) {
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int START_TASK_TO_FRONT = 2;
        String pkgName = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List appList = am.getRunningAppProcesses();
        List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo app : processes) {
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                Integer state = null;
                try {
                    state = field.getInt(app);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (state != null && state == START_TASK_TO_FRONT) {
                    currentInfo = app;
                    break;
                }
            }
        }
        if (currentInfo != null) {
            pkgName = currentInfo.processName;
        }
        return pkgName;
    }


    public class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return Utils.getStrData().length;
        }

        @Override
        public String[] getItem(int position) {
            return Utils.getStrData()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.main_grid_item, null);
            }
            ImageView imgView= (ImageView) convertView.findViewById(R.id.grid_item_imageview);
//            imgView.setImageResource(R.mipmap.cq_ssc);
            setImage(imgView,position);
            TextView textView= (TextView) convertView.findViewById(R.id.grid_item_text);
            textView.setText(getItem(position)[0]);
            return convertView;
        }
    }
    private  void setImage(ImageView imgView,int index){
        switch (index){
            case 0:
                imgView.setImageResource(R.mipmap.src_img_ssq);
                break;
            case 1:imgView.setImageResource(R.mipmap.src_img_3d);
                break;
            case 2:imgView.setImageResource(R.mipmap.src_img_qlc);
                break;
            case 3:imgView.setImageResource(R.mipmap.src_img_qxc);
                break;
            case 4:imgView.setImageResource(R.mipmap.src_img_pls);
                break;
            case 5:imgView.setImageResource(R.mipmap.src_img_plw);
                break;
            case 6:imgView.setImageResource(R.mipmap.src_img_11x5);
                break;
            case 7:imgView.setImageResource(R.mipmap.src_img_ahk3);
                break;
            case 8:imgView.setImageResource(R.mipmap.src_img_dlt);
                break;
        }

    }



}
