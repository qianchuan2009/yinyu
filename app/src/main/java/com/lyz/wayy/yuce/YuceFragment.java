
package com.lyz.wayy.yuce;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.Utils;
import com.lyz.wayy.msg.MsgListViewAdapter;
import com.lyz.wayy.msg.News;
import com.lyz.wayy.msg.WebActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

/**
 * 设置页面
 *
 * @author zengxl 2015-9-22
 */
public class YuceFragment extends Fragment {

    private Context context;// 本类环境

    /* 存储个人设置列表里的内容列表 */
    private ArrayList<News> listArr = new ArrayList<News>();// 第一栏三个选项

    private ListView listView;
    private MsgListViewAdapter adapter;
    private int currentIndex;//当前的页码
    private RefreshLayout refreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_yuce, container, false);
        context = this.getActivity();
//        getActivity().findViewById(R.id.layout_title).setVisibility(View.VISIBLE);
//        TextView titleView = (TextView) getActivity().findViewById(R.id.titlebar);
//        titleView.setText("预测");
        initView(view);
        initData();
        return view;
    }


    private void initView(View view) {
        listView=view.findViewById(R.id.msg_listView);
        adapter = new MsgListViewAdapter(context,R.layout.page_yuce_item, listArr);
        //实现列表的显示
        listView.setAdapter(adapter);

        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                if (currentIndex<300){
//                    loadPage(currentIndex+1);
//                }else{
//                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//                }
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentIndex<300){
                    loadPage(currentIndex+1);
                }else{
                    refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示刷新失败
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(context,WebActivity.class);
                intent.putExtra("url",listArr.get(position).getNewsUrl());
                intent.putExtra("title",listArr.get(position).getNewsTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
    }

    private void initData() {
        int index=YuceUtil.getIndex(context);
        if (index>0){
            currentIndex=index;
        }
        ArrayList<News> listArr2=YuceUtil.getNews(context);
        if (listArr2!=null &&listArr2.size()>0){
            listArr=listArr2;
            adapter.setArrList(listArr);
            adapter.notifyDataSetChanged();
        }else{
            loadPage(0);
        }
    }


    private void loadPage(final int index) {
        currentIndex=index;
        YuceUtil.putIndex(context,index);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int a= (int) (Math.random()*2);
                    String rooturl="http://zx.aicai.com/cpzx/"+(index+1)+"_10_1691_ssq.html";
                    if(a==0){
                        rooturl="http://zx.aicai.com/cpzx/"+(index+1)+"_10_491_ssq.html";
//                        rooturl="http://zx.aicai.com/cpzx/"+(index+1)+"_10_1691_ssq.html";
                    }else{
                        rooturl="http://zx.aicai.com/cpzx/"+(index+1)+"_10_554_dlt.html";
//                        rooturl="http://zx.aicai.com/cpzx/"+(index+1)+"_10_1693_dlt.html";
                    }
                    Document doc = Jsoup.parse(new URL(rooturl), 10000);
//                    Document doc = Jsoup.parse(new URL("http://zx.aicai.com/cpzx/"+(index+1)+"_10_1693_dlt.html"), 10000);
                    Elements titleLinks = doc.select("div.newsjjcList").select("li");
                    for (Element e : titleLinks) {
                        Elements tt=e.select("span.newsShare.ml30");
                        String title = tt.last().dataset().get("title");
                        String url = tt.last().attr("data-link");
                        String desc = tt.last().attr("data-descr");
                        String time = e.select(".mr40").text();
                        News news=new News(title,url,desc,time);
                        listArr.add(news);
//                        listArr.add(0,news);
                    }

                    // 需要做的事:发送消息
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Utils.showOkAlertDialog(context, "提示", "向服务器请求数据失败", null);
                }
            }
        }).start();
    }


    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                adapter.setArrList(listArr);
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore(200/*,false*/);//传入false表示刷新失败
                YuceUtil.putNews(context,listArr);
            }
            super.handleMessage(msg);
        };
    };


}
