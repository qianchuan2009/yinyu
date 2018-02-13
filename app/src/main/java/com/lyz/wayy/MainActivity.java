package com.lyz.wayy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyz.wayy.bean.Friend;
import com.lyz.wayy.bean.FriendInfo;
import com.lyz.wayy.main.frame.Fragment2;
import com.lyz.wayy.main.frame.FragmentFriend;

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
    @BindView(R.id.gonggao)
    TextView gonggao;

    public static final int CHANGE_NAME = 1;
    public static Context context;
    @BindView(R.id.frd_img)
    ImageView frdImg;
    @BindView(R.id.frd_name)
    TextView frdName;
    @BindView(R.id.frd_msg)
    ImageView frdMsg;
    @BindView(R.id.frd_area)
    LinearLayout frdArea;
    @BindView(R.id.my_progress)
    ProgressBar myProgress;
    @BindView(R.id.my_progress_per)
    TextView myProgressPer;
    @BindView(R.id.my_dog_progress)
    ProgressBar myDogProgress;
    @BindView(R.id.my_dog_progress_per)
    TextView myDogProgressPer;
    @BindView(R.id.frd_progress_per)
    TextView frdProgressPer;
    @BindView(R.id.frd_progress)
    ProgressBar frdProgress;
    @BindView(R.id.frd_dog_progress_per)
    TextView frdDogProgressPer;
    @BindView(R.id.frd_dog_progress)
    ProgressBar frdDogProgress;
    @BindView(R.id.my_level)
    TextView myLevel;
    @BindView(R.id.my_dog_level)
    TextView myDogLevel;

    private ImageView imageView;//点击出现下方区域的图片
    private RadioGroup radioGroup;//下方tab页
    private RadioButton radioButton1;//tab页第一个按钮
    private RadioButton radioButton2;//tab页第二个按钮
    private RelativeLayout onBottom;//下方tab页整体布局
    private static boolean isShow = false;//是否显示下方tab页
    private RelativeLayout picOut;//可以移动的图片的外部布局
    private ImageView moveImg;//可以移动的图片
    private FragmentManager fragmentManager = getSupportFragmentManager();


    String userJsonStr;
    UserInfo.UserBean userBean;//用户信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onCreate2();
        getUserInfo();
        getGongGao();
    }


    private void getUserInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=user&act=run&web_uid=" + ConstFile.uId;
                    String response = example.run(url);
                    userJsonStr = response;
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


    //设置用户信息
    private void inituserInfo() {
        userBean = UserInfo.UserBean.objectFromData(userJsonStr, "user");
        userName.setText(userBean.getUserName());
        Boolean isVip = userBean.getYellowstatus() == 1;
        if (isVip) {
            userName.setTextColor(Color.RED);
        } else {
            userName.setTextColor(Color.BLACK);
        }

        Glide.with(this).load(userBean.getHeadPic()).into(imgMyImg);
        fb.setText(userBean.getFB());
        mystar.setText(userBean.getMoney() + "");

        int level = CharmUtil.expToGrade4Man(userBean.getExp());
        myLevel.setText(level+"");
        int loc2=CharmUtil.gradeToExp4Man(level);
        int loc1=CharmUtil.gradeToExp4Man(level + 1);
        int _nextExp = userBean.getExp() - loc2;
        float per=(float) _nextExp / (loc1 - loc2);
        int percent = (int)(per* 100);
        int levelExp = loc1 - loc2;
        myProgress.setProgress(percent);
        myProgressPer.setText(_nextExp+"/"+levelExp);


        int dogLevel=CharmUtil.toLevel(userBean.getCharm());
        myDogLevel.setText(dogLevel+"");
        int dogCur=CharmUtil.currentLevelValue(userBean.getCharm());
        int dogNext=CharmUtil.needLevelValue(userBean.getCharm());
        int has=userBean.getCharm()-dogCur;
        float p=has/(dogNext-dogCur);
        myDogProgress.setProgress((int)(p*100));
        myDogProgressPer.setText(has+"/"+dogNext);
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

    //公告
    private void getGongGao() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=sys&act=gonggao&type=mobile&web_uid=" + ConstFile.uId;
                    String response = example.run(url);
                    JSONObject json = new JSONObject(response);
                    final String notice = json.getString("notice");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gonggao.setText(notice);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_NAME://下方点击改变右边名字
                    Bundle bundle = msg.getData();
                    Friend frd = (Friend) bundle.getSerializable("friend");
                    getFrdInfo( frd);
                    break;
                default:
            }
        }
    };

    private void getFrdInfo(final Friend frd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=user&act=run&flag=1&web_uid=" + ConstFile.uId+"&ownerId="+frd.getUserId();
                    String response = example.run(url);
                    final FriendInfo friend= FriendInfo.objectFromData(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setFrdInfo(friend,frd);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //设置朋友信息
    private void setFrdInfo(FriendInfo friendInfo,Friend frd) {
        frdArea.setVisibility(View.VISIBLE);
        Boolean isVip = friendInfo.getYellowstatus() == 1;
        if (isVip) {
            frdName.setTextColor(Color.RED);
        } else {
            frdName.setTextColor(Color.BLACK);
        }
        frdName.setText(frd.getUserName());
        Glide.with(context).load(frd.getHeadPic()).into(frdImg);

        int level = CharmUtil.expToGrade4Man(frd.getExp());
//        frdLevel.setText(level+"");
        int loc2=CharmUtil.gradeToExp4Man(level);
        int loc1=CharmUtil.gradeToExp4Man(level + 1);
        int _nextExp = frd.getExp() - loc2;
        float per=(float) _nextExp / (loc1 - loc2);
        int percent = (int)(per* 100);
        int levelExp = loc1 - loc2;
        frdProgress.setProgress(percent);
        frdProgressPer.setText(_nextExp+"/"+levelExp);


        int dogLevel=CharmUtil.toLevel(frd.getCharm());
//        frdDogLevel.setText(dogLevel+"");
        int dogCur=CharmUtil.currentLevelValue(frd.getCharm());
        int dogNext=CharmUtil.needLevelValue(frd.getCharm());
        int has=frd.getCharm()-dogCur;
        float p=has/(dogNext-dogCur);
        frdDogProgress.setProgress((int)(p*100));
        frdDogProgressPer.setText(has+"/"+dogNext);
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

    private void change1() {
        radioButton1.setPressed(true);
        radioButton1.setChecked(true);
        radioButton1.setBackgroundResource(R.drawable.icon_list);
        radioButton2.setBackgroundResource(R.drawable.i_tool_p);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FragmentFriend fragmentFrd = new FragmentFriend();
        fragmentFrd.setContext(this);
        fragmentFrd.setHandle(handler);
        ft.replace(R.id.frame, fragmentFrd);
        ft.commit();
    }

    private void change2() {
        radioButton2.setPressed(true);
        radioButton2.setChecked(true);
        radioButton1.setBackgroundResource(R.drawable.icon_list_p);
        radioButton2.setBackgroundResource(R.drawable.i_tool);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment2 fragment2 = new Fragment2();
        fragment2.setHandle(handler);
        fragment2.setContext(this);
        ft.replace(R.id.frame, fragment2);
        ft.commit();
    }

    private void onCreate2() {
        context = MainActivity.this;
        imageView = (ImageView) findViewById(R.id.show);
        onBottom = (RelativeLayout) findViewById(R.id.bottom);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.bringToFront();
        radioButton1 = (RadioButton) findViewById(R.id.tab1);
        radioButton2 = (RadioButton) findViewById(R.id.tab2);
        picOut = (RelativeLayout) findViewById(R.id.move_area);
        radioGroup.setOnCheckedChangeListener(checkedchangelistner);
        moveImg = (ImageView) findViewById(R.id.move_img);
        moveImg.setOnTouchListener(this);
        change1();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    onBottom.setVisibility(View.GONE);
                    isShow = false;
                } else {
                    onBottom.setVisibility(View.VISIBLE);
                    isShow = true;
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
                if (maxRight == 0) {//保证只赋一次值
                    maxRight = picOut.getWidth();
                    maxBottom = picOut.getHeight();
                }
                //第一次记录lastX/lastY
                lastX = eventX;
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算事件的偏移
                int dx = eventX - lastX;
                int dy = eventY - lastY;
                //根据事件的偏移来移动imageView
                int left = moveImg.getLeft() + dx;
                int top = moveImg.getTop() + dy;
                int right = moveImg.getRight() + dx;
                int bottom = moveImg.getBottom() + dy;
                //限制left >=0
                if (left < 0) {
                    right += -left;
                    left = 0;
                }
                //限制top
                if (top < 0) {
                    bottom += -top;
                    top = 0;
                }
                //限制right <=maxRight
                if (right > maxRight) {
                    right = maxRight;
                    left = right - moveImg.getWidth();
                }
                //限制bottom <=maxBottom
                if (bottom > maxBottom) {
                    bottom = maxBottom;
                    top = bottom - moveImg.getHeight();
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

    @OnClick(R.id.frd_msg)
    public void onViewClicked() {
    }
}
