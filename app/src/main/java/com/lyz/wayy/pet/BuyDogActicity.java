package com.lyz.wayy.pet;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lyz.wayy.ConstFile;
import com.lyz.wayy.MainActivity;
import com.lyz.wayy.R;
import com.lyz.wayy.Utils;
import com.lyz.wayy.bean.BuyDog;
import com.lyz.wayy.bean.PkgInfo;
import com.lyz.wayy.lucky.AdapterLucky;
import com.lyz.wayy.lucky.LuckyBean;
import com.lyz.wayy.lucky.LuckyUtil;
import com.lyz.wayy.main.frame.FragmentPkg;

import org.json.JSONException;
import org.json.JSONObject;

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
    TextView fbView;
    private FragmentManager fragmentManager = getSupportFragmentManager();


    private AdapterBuyDog adapterBuyDog; //recyclerView的适配器
    private RecyclerView recyclerView; //显示图片的布局
//    private Handler handler;//传过来的handler
    ArrayList<BuyDog> dataList=new ArrayList<>();

    int fb;//fb
    int star;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buydog);
        ButterKnife.bind(this);
//        radioGroup.setOnCheckedChangeListener(checkedchangelistner);
        Intent intent = getIntent();
        String fbStr=intent.getStringExtra("fb");
        fb=Integer.parseInt(fbStr);
        star=intent.getIntExtra("star",0);
        mystar.setText(star+"");
        fbView.setText(fb+"");

        change2();
        initDogShop();
    }


    private void initDogShop(){

        getDogList();
        adapterBuyDog=new AdapterBuyDog(this,dataList);
        buydogGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BuyDog buyDog=dataList.get(i);
                showBuyDogDlg(buyDog);
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

    /////////
    private void showBuyDogDlg(final BuyDog buyDog){
        AlertDialog.Builder builder = new AlertDialog.Builder(BuyDogActicity.this,R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_buydog, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();

        ImageView  touxiang=dlgView.findViewById(R.id.bugdog_touxiang);
        String tid=buyDog.getTId();
//        int id = getResources().getIdentifier("animal"+tid+"_04_0001", "drawable", BuyDogActicity.this.getPackageName());
//        touxiang.setImageResource(id);

        int id = BuyDogActicity.this.getResources().getIdentifier("animal"+tid+"_04", "drawable", getPackageName());
//            Drawable drawable = getResources().getDrawable(id);
        touxiang.setImageResource(id);
        final AnimationDrawable animationDrawable = (AnimationDrawable) touxiang.getDrawable();
        animationDrawable.start();

        TextView title=dlgView.findViewById(R.id.title);
        title.setText(buyDog.getTName());

        TextView desc=dlgView.findViewById(R.id.desc);
        desc.setText(buyDog.getDepict());

        TextView price=dlgView.findViewById(R.id.price);
        String priceStr=buyDog.getList().get_$1().getFBPrice()+"";
        price.setText(priceStr);

        TextView price2=dlgView.findViewById(R.id.price2);
        price2.setText(priceStr);

        //设置背景透明
//        WindowManager m = getWindowManager();
//        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = Utils.dp2px(300,MainActivity.this);
//        p.width = Utils.dp2px(260,MainActivity.this);
//        dialog.getWindow().setAttributes(p);//设置生效

        ImageView closeBtn= (ImageView) dialog.findViewById(R.id.buydog_cancel);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                animationDrawable.stop();
            }
        });

        ImageView okBtn= (ImageView) dialog.findViewById(R.id.buydog_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyDogFunc(buyDog);
                animationDrawable.stop();
                dialog.dismiss();
            }
        });

    }

    private  void buyDogFunc(final BuyDog buyDog){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=shop&act=buy&type=4&id="+buyDog.getTId()+"&web_uid=" +ConstFile.uId;
                    final String response = example.run(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.trim().length()==0){
                                Toast.makeText(BuyDogActicity.this, "购买失败", Toast.LENGTH_SHORT).show();
                            }else{
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getInt("code")==1){
                                        Toast.makeText(BuyDogActicity.this, jsonObject.getString("direction"), Toast.LENGTH_SHORT).show();
                                        change2();
                                        int fbs= Integer.parseInt(fbView.getText()+"");
                                        fbs+=jsonObject.getInt("FB");
                                        fbView.setText(fb+"");
                                    }else{
                                        Toast.makeText(BuyDogActicity.this, "购买失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
