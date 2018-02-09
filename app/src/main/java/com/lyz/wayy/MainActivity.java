package com.lyz.wayy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lyz.wayy.main.frame.Fragment1;
import com.lyz.wayy.main.frame.Fragment2;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    @BindView(R.id.img_myImg)
    ImageView imgMyImg;
    @BindView(R.id.text1)
    TextView userName;
    @BindView(R.id.btnchoujiang)
    ImageView btnchoujiang;
    @BindView(R.id.duihua)
    ImageView duihua;
    @BindView(R.id.btnvip)
    ImageView btnvip;
    @BindView(R.id.mystar)
    TextView mystar;
    @BindView(R.id.fb)
    TextView fb;


    public static final int CHANGE_NAME=1;
    public static Context context;
    private ImageView imageView;//点击出现下方区域的图片
    private RadioGroup radioGroup;//下方tab页
    private RadioButton radioButton1;//tab页第一个按钮
    private RadioButton radioButton2;//tab页第二个按钮
    private TextView textView;//第一个进度条上面的文字
    private RelativeLayout onBottom;//下方tab页整体布局
    private static boolean isShow=false;//是否显示下方tab页
    private TextView name;//右方图片下面的名字
    private ProgressBar progressBar;//第一个进度条
    private LinearLayout picOut;//可以移动的图片的外部布局
    private ImageView moveImg;//可以移动的图片
    private FragmentManager fragmentManager=getSupportFragmentManager();


    String userJsonStr;
    UserInfo.UserBean userBean;//用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onCreate2();
        getUserInfo();
    }


    private void getUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=user&act=run&web_uid=" +ConstFile.uId;
                    String response = example.run(url);
                    userJsonStr=response;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            inituserInfo();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private  void inituserInfo(){
        userBean= UserInfo.UserBean.objectFromData(userJsonStr,"user");
        userName.setText(userBean.getUserName());
        Boolean isVip=userBean.getYellowstatus()==1;
        if(isVip){
            userName.setTextColor(Color.RED);
        }else{
            userName.setTextColor(Color.BLACK);
        }

        Glide.with(this).load(userBean.getHeadPic()).into(imgMyImg);
        fb.setText(userBean.getFB());
        mystar.setText(userBean.getMoney()+"");
    }

    @OnClick({R.id.btnchoujiang, R.id.duihua})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnchoujiang:
                break;
            case R.id.duihua:
                break;
        }
    }


    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CHANGE_NAME://下方点击改变右边名字
                    Bundle bundle=msg.getData();
                    String str=bundle.getString("name");
                    setName(str);
                    break;
                default:
            }
        }
    };

    private void setName(String str){
        name.setText(str);
    }

    RadioGroup.OnCheckedChangeListener checkedchangelistner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {//tab页切换
            switch (checkedId) {
                case R.id.tab1:
                    change1();
                    break;
                case R.id.tab2:
                    change2();
                    break;
                default:
                    break;
            }
        }
    };
    private void change1(){
        radioButton1.setPressed(true);
        radioButton1.setChecked(true);
        radioButton1.setBackgroundResource(R.drawable.button_back1);
        radioButton2.setBackgroundResource(R.drawable.button_back3);
        FragmentTransaction ft=fragmentManager.beginTransaction();
        Fragment1 fragment1=new Fragment1();
        fragment1.setContext(this);
        ft.replace(R.id.frame,fragment1);
        ft.commit();
    }
    private void change2(){
        radioButton2.setPressed(true);
        radioButton2.setChecked(true);
        radioButton1.setBackgroundResource(R.drawable.button_back3);
        radioButton2.setBackgroundResource(R.drawable.button_back2);
        FragmentTransaction ft=fragmentManager.beginTransaction();
        Fragment2 fragment2=new Fragment2();
        fragment2.setHandle(handler);
        fragment2.setContext(this);
        ft.replace(R.id.frame,fragment2);
        ft.commit();
    }

    private void onCreate2() {
        context=MainActivity.this;
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.show);
        onBottom=(RelativeLayout)findViewById(R.id.bottom);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        radioButton1=(RadioButton)findViewById(R.id.tab1);
        radioButton2=(RadioButton)findViewById(R.id.tab2);
        textView=(TextView)findViewById(R.id.text3);
        name=(TextView)findViewById(R.id.name);
        picOut=(LinearLayout)findViewById(R.id.l8);
        progressBar=(ProgressBar)findViewById(R.id.progress1);
        radioGroup.setOnCheckedChangeListener(checkedchangelistner);
        moveImg=(ImageView)findViewById(R.id.move_img);
        moveImg.setOnTouchListener(this);
        change1();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow){
                    onBottom.setVisibility(View.GONE);
                    isShow=false;
                }else {
                    onBottom.setVisibility(View.VISIBLE);
                    isShow=true;
                }
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str1="100";
//                String str2=edit2.getText().toString();
//                int length1=Integer.parseInt(str1);
//                int length2=Integer.parseInt(str2);
//                progressBar.setProgress(100*length1/length2);
//                textView.setText(length1+"/"+length2);
//            }
//        });

    }
    private int maxRight;
    private int maxBottom;
    private int lastX;
    private int lastY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //得到事件的坐标
        int eventX = (int) event.getRawX();
        int eventY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //得到父视图的right/bottom
                if(maxRight==0) {//保证只赋一次值
                    maxRight = picOut.getWidth();
                    maxBottom = picOut.getHeight();
                }
                //第一次记录lastX/lastY
                lastX =eventX;
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算事件的偏移
                int dx = eventX-lastX;
                int dy = eventY-lastY;
                //根据事件的偏移来移动imageView
                int left = moveImg.getLeft()+dx;
                int top = moveImg.getTop()+dy;
                int right = moveImg.getRight()+dx;
                int bottom = moveImg.getBottom()+dy;
                //限制left >=0
                if(left<0) {
                    right += -left;
                    left = 0;
                }
                //限制top
                if(top<0) {
                    bottom += -top;
                    top = 0;
                }
                //限制right <=maxRight
                if(right>maxRight) {
                    right = maxRight;
                    left=right-moveImg.getWidth();
                }
                //限制bottom <=maxBottom
                if(bottom>maxBottom) {
                    bottom = maxBottom;
                    top=bottom-moveImg.getHeight();
                }
                moveImg.layout(left, top, right, bottom);
                //再次记录lastX/lastY
                lastX = eventX;
                lastY = eventY;
                break;
            default:
                break;
        }
        return true;//所有的motionEvent都交给imageView处理
    }
}
