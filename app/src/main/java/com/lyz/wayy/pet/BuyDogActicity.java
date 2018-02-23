package com.lyz.wayy.pet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lyz.wayy.ConstFile;
import com.lyz.wayy.R;
import com.lyz.wayy.Utils;
import com.lyz.wayy.bean.BuyDog;
import com.lyz.wayy.bean.PkgInfo;
import com.lyz.wayy.main.frame.FragmentPkg;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by helch on 2018/2/23.
 */

public class BuyDogActicity extends AppCompatActivity {
    @BindView(R.id.buydog_gridView)
    GridView buydogGridView;
    @BindView(R.id.tab2)
    RadioButton radioButton2;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.frame)
    FrameLayout frame;

    public static final int CHANGE_PKG = 2;
    @BindView(R.id.buydog_back)
    ImageView buydogBack;
    @BindView(R.id.mystar)
    TextView mystar;
    @BindView(R.id.fb)
    TextView fb;
    private FragmentManager fragmentManager = getSupportFragmentManager();


    private AdapterBuyDog adapterBuyDog; //recyclerView的适配器
    private RecyclerView recyclerView; //显示图片的布局
//    private Handler handler;//传过来的handler
    ArrayList<BuyDog> dataList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buydog);
        ButterKnife.bind(this);
//        radioGroup.setOnCheckedChangeListener(checkedchangelistner);

        change2();
        initDogShop();
    }


    private void initDogShop(){

        getDogList();
        adapterBuyDog=new AdapterBuyDog(this,dataList);
        buydogGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        buydogGridView.setAdapter(adapterBuyDog);
    }


    private void getDogList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=shop&act=getShopInfo&type=3,4&web_uid=" +ConstFile.uId;
                    String response = example.run(url);
//                    JSONArray arr=new JSONArray(response);
                    dataList= BuyDog.arrayBuyDogFromData(response,"4");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterBuyDog.reSetDatalist(dataList);
                            adapterBuyDog.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void change2() {
        radioButton2.setPressed(true);
        radioButton2.setChecked(true);
        radioButton2.setBackgroundResource(R.drawable.i_tool);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FragmentPkg fragment2 = new FragmentPkg();
        fragment2.setHandle(handler);
        fragment2.setContext(this);
        ft.replace(R.id.frame, fragment2);
        ft.commit();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_PKG://背包
                    Bundle bundle2 = msg.getData();
                    PkgInfo pkg = (PkgInfo) bundle2.getSerializable("pkg");
                    break;
                default:
                    break;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener checkedchangelistner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {//tab页切换
            switch (checkedId) {
                case R.id.tab2:
                    change2();
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.buydog_back)
    public void onViewClicked() {
        finish();
    }
}
