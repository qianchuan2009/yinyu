
package com.lyz.wayy.setting;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.lyz.wayy.R;
import com.lyz.wayy.pub.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置页面
 *
 * @author zengxl 2015-9-22
 */
public class SettingFragment extends Fragment {

    private Context context;// 本类环境

    /* 存储个人设置列表里的内容列表 */
    private List<Object> listitems1 = new ArrayList<Object>();// 第一栏三个选项

    private ListView lv1;

    private int[] settingIconArr = new int[] {
            R.mipmap.gesture_password,
            R.mipmap.suggest,
            R.mipmap.about_us,
            R.mipmap.update
    };
    private String[][] strData={{"基本玩法",""},{"实用技巧","fc3d"},{"当前版本","qlc"},{"检查更新","qxc"}};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_personalsetting, container, false);
        context = this.getActivity();
//        getActivity().findViewById(R.id.layout_title).setVisibility(View.VISIBLE);
//        TextView titleView = (TextView) getActivity().findViewById(R.id.titlebar);
//        titleView.setText("设置");
        initView(view);
        initData();
        return view;
    }



    private void initView(View view) {
        lv1 = (ListView) view.findViewById(R.id.lv1);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                if (index==0){
                            Intent intent = new Intent(context, SettingsHtmlListViewActivity.class);
//                            intent.putExtra("dh", dh);
                            intent.putExtra("title","玩法说明");
                            intent.putExtra("type",1);
                            startActivity(intent);

                }else if(index==1){
                    Intent intent = new Intent(context, SettingsHtmlListViewActivity.class);
//                    intent.putExtra("dh", dh);
                    intent.putExtra("title","实用技巧");
                    intent.putExtra("type",2);
                    startActivity(intent);
                }else if(index==3){
                    Utils.showOkAlertDialog(context,"提示","当前已是最新版本！",null);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        /**
         * add by zengxl 20170616
         * 手势密码是否已设置刷新
         */
        listitems1.clear();
        for (int i = 0; i < 4; i++) {
            SettingBanner sb = new SettingBanner();
            sb.setIcon(settingIconArr[i]);
            sb.setCaption(strData[i][0]);
            if (i==2){
                sb.setTip("1.0.6");
            }
            listitems1.add(sb);
        }
        ListViewAdapter la1 = new ListViewAdapter(context, listitems1);
        la1.setListItemView(new ListViewAdapter.ListItemView() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent,
                                List<Object> list, LayoutInflater layoutInflater) {
                View view = null;
                if (layoutInflater != null) {
                    view = layoutInflater.inflate(R.layout.page_personalsetting_item, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.app_icon);
                    TextView tv_title = (TextView) view.findViewById(R.id.app_title);
                    TextView tv_content = (TextView) view.findViewById(R.id.app_tips);
                    SettingBanner sb = (SettingBanner) list.get(position);
                    tv_title.setText(sb.getCaption());
                    tv_content.setText(sb.getTip());
                    tv_content.setPadding(0, 0, 0, (int) (parent.getHeight() * 0.015));
                    imageView.setImageResource(sb.getIcon());
                    if(position==2){
                        ImageView appBtn = (ImageView) view.findViewById(R.id.app_btn);
                        appBtn.setVisibility(View.INVISIBLE);
                    }

                }
                return view;
            }
        });
        lv1.setAdapter(la1);
    }

    /**
     * 检查更新
     */
    protected void checkUpdate() {
    }


//    private void turnToLoginActivity() {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("server", server);
//        Util.startActivity(context, LoginActivity.class, bundle);
//    }




//    @Override
//    public void onDestroyView() {
//        System.gc();
//        if (receiverlist.size() > 0) {
//            for (int i = 0; i < receiverlist.size(); i++) {
//                if (receiverlist.get(i) != null) {
//                    context.unregisterReceiver((BroadcastReceiver) receiverlist
//                            .get(i));
//                }
//            }
//        }
//        LogUtil.addLogItemToTable(LogUtil.ES_PERSONALSETTING_IO);
//        super.onDestroyView();
//    }
}
