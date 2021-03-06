package com.lyz.wayy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lyz.wayy.bean.BuyDog;
import com.lyz.wayy.bean.Friend;
import com.lyz.wayy.bean.FriendInfo;
import com.lyz.wayy.bean.FrontDog;
import com.lyz.wayy.bean.Letter;
import com.lyz.wayy.bean.LetterRrecive;
import com.lyz.wayy.bean.LetterSendResult;
import com.lyz.wayy.bean.PkgInfo;
import com.lyz.wayy.bean.UpdateBean;
import com.lyz.wayy.bean.UserInfo;
import com.lyz.wayy.clothes.BuyClothesActicity;
import com.lyz.wayy.lucky.AdapterLucky;
import com.lyz.wayy.lucky.LuckyBean;
import com.lyz.wayy.lucky.LuckyUtil;
import com.lyz.wayy.main.adapter.AdapterLetter;
import com.lyz.wayy.main.frame.FragmentFriend;
import com.lyz.wayy.main.frame.FragmentPkg;
import com.lyz.wayy.main.frame.FriendComparator;
import com.lyz.wayy.pet.BuyDogActicity;
import com.lyz.wayy.pub.ConstFile;
import com.lyz.wayy.pub.TextViewWithFont;
import com.lyz.wayy.pub.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

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
    public static final int CHANGE_PKG = 2;
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

    @BindView(R.id.move_user_img)
    GifImageView moveUserImg;
    @BindView(R.id.frd_level)
    TextViewWithFont frdLevel;
    @BindView(R.id.frd_dog_level)
    TextViewWithFont frdDogLevel;
    @BindView(R.id.frdstar)
    TextViewWithFont frdstar;

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
    private ArrayList<Letter> letterDataList=new ArrayList<Letter>();
    private AdapterLetter adapterLetter;

    private   Friend currentFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onCreate2();
        getUserInfo();
        getGongGao();
        checkUpdate();
    }

    private void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=version&act=check&web_uid=" + ConstFile.uId;
                    String response = example.run(url);
                    final UpdateBean updateBean = UpdateBean.objectFromData(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String ver = Utils.getAppVersion(MainActivity.this);
                            int cp = Utils.compareVersion(ver, updateBean.getVer());
                            if (cp == -1) {
                                if (updateBean.getEnforce() == 1) {
                                    Utils.showOkAlertDialog(MainActivity.this, "提示", "检测到新版本请立刻更新！", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            doUpdate(updateBean.getUrl());
                                        }
                                    });
                                } else {
                                    Utils.showAlertDialog(MainActivity.this, "提示", "检测到新版本是否立刻更新？", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            doUpdate(updateBean.getUrl());
                                        }
                                    });
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

    private void doUpdate(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        finish();
        System.exit(0);
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
        change1();
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
        myLevel.setText(level + "");
        int loc2 = CharmUtil.gradeToExp4Man(level);
        int loc1 = CharmUtil.gradeToExp4Man(level + 1);
        int _nextExp = userBean.getExp() - loc2;
        float per = (float) _nextExp / (loc1 - loc2);
        int percent = (int) (per * 100);
        int levelExp = loc1 - loc2;
        myProgress.setProgress(percent);
        myProgressPer.setText(_nextExp + "/" + levelExp);


        int dogLevel = CharmUtil.toLevel(userBean.getCharm());
        myDogLevel.setText(dogLevel + "");
        int dogCur = CharmUtil.currentLevelValue(userBean.getCharm());
        int dogNext = CharmUtil.needLevelValue(userBean.getCharm());
//        int has = userBean.getCharm() - dogCur;
//        float p = has / (dogNext - dogCur);
        float p = (float) dogCur / (dogNext - dogCur);
        myDogProgress.setProgress((int) (p * 100));
//        myDogProgressPer.setText(has + "/" + dogNext);
        myDogProgressPer.setText(dogCur + "/" + dogNext);

        //设置宠物

        UserInfo.DogBean dog = UserInfo.DogBean.objectFromData(userJsonStr, "dog");
        int DogId = dog.getDogId();
        int step = dog.getStep();
        setDog2Front(step, DogId);

        //设置人物到前台
        try {
            JSONObject jsonObj = new JSONObject(userJsonStr);
            String vImgStr = jsonObj.getString("virtualimage");
            String[] vImgArr = Utils.getQQShowData(vImgStr);
            loadQQshowImg(vImgArr, moveUserImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //设置qq形象
    private void loadQQshowImg(final String[] arr, final ImageView image) throws IOException {
        final ArrayList<Drawable> drawableArr = new ArrayList<Drawable>();
//        Drawable[] array = new Drawable[26];
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr.length; i++) {
                    if (!arr[i].equalsIgnoreCase("0")) {
                        Utils.OkHttps example = new Utils.OkHttps();
//                 Drawable bitmap1 = Utils.getImageFromAsserts(this,"virtualimage/"+i+"/"+arr[i]+".gif");
                        String url = ConstFile.serverUrl + "images\\virtualimage\\" + i + "\\" + arr[i] + ".gif";
                        ResponseBody response = null;
                        try {
                            response = example.run2(url);
                            byte[] bytes = response.bytes();
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            Drawable drawable = new BitmapDrawable(bitmap);
//                            drawableArr.add(drawable);
                            GifDrawable gifFromBytes = new GifDrawable(bytes);
                            drawableArr.add(gifFromBytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //
//        Bitmap bitmap2 = ((BitmapDrawable) getResources().getDrawable(
//                R.drawable.go)).getBitmap();
                        Drawable[] array = drawableArr.toArray(new Drawable[drawableArr.size()]);
                        LayerDrawable la = new LayerDrawable(array);
//        // 其中第一个参数为层的索引号，后面的四个参数分别为left、top、right和bottom
                        la.setLayerInset(0, 0, 0, 0, 0);
////        la.setLayerInset(1, 20, 20, 20, 20);
                        image.setImageDrawable(la);
                    }
                });
            }
        }).start();

    }


    //设置宠物到前台
    private void setDog2Front(int step, int DogId) {
        ViewGroup.LayoutParams params = moveImg.getLayoutParams(); // 获取对话框当前的参数值
        if (step < 1) {
            params.width = Utils.dp2px(40, this);
            params.height = Utils.dp2px(50, this);
            moveImg.setLayoutParams(params);
            if (step == -2) {
                moveImg.setImageResource(R.drawable.egg0);
            } else if (step == -1) {
                moveImg.setImageResource(R.drawable.egg1);
            } else {
                moveImg.setImageResource(R.drawable.egg2);
            }
        } else {

            params.width = Utils.dp2px(60, this);
            params.height = Utils.dp2px(60, this);
            moveImg.setLayoutParams(params);

            int id = getResources().getIdentifier("animal" + DogId + "_0" + step, "drawable", getPackageName());
//            Drawable drawable = getResources().getDrawable(id);
            moveImg.setImageResource(id);
            AnimationDrawable animationDrawable = (AnimationDrawable) moveImg.getDrawable();
            animationDrawable.start();
        }
    }


    @OnClick({R.id.btnchoujiang, R.id.duihua,R.id.frd_msg,R.id.tab3,R.id.tab4,R.id.tab5,R.id.tab6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnchoujiang:
                showLuckyDlg();
                break;
            case R.id.duihua:
                showDuiHuanDlg();
                break;
            case R.id.frd_msg:
                sendFrdMsg();
                break;
            case R.id.tab4:
                //打开宠物购买
                Intent intent = new Intent(MainActivity.this, BuyDogActicity.class);
                intent.putExtra("star", userBean.getMoney());
                intent.putExtra("fb", userBean.getFB());
                startActivity(intent);
                break;
            case R.id.tab5:
                showLetterDlg();
                break;
            case R.id.tab6:
                //打开宠物购买
                Intent intent2 = new Intent(MainActivity.this, BuyClothesActicity.class);
                intent2.putExtra("star", userBean.getMoney());
                intent2.putExtra("fb", userBean.getFB());
                intent2.putExtra("userJsonStr", userJsonStr);
                startActivity(intent2);
                break;
        }
    }


    private void sendFrdMsg(){
        sendFrdLetterDlg();
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
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    gonggao.setVisibility(View.GONE);
                                }
                            }, 80000); //延时1s执行
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
                case CHANGE_NAME://朋友
                    Bundle bundle = msg.getData();
                    Friend frd = (Friend) bundle.getSerializable("friend");
                    getFrdInfo(frd);
                    break;
                case CHANGE_PKG://背包
                    Bundle bundle2 = msg.getData();
                    PkgInfo pkg = (PkgInfo) bundle2.getSerializable("pkg");
                    getDogAndSet2Front(pkg);
                    break;
                default:
                    break;
            }
        }
    };

    //设置宠物到主台
    private void getDogAndSet2Front(final PkgInfo pkgInfo) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=dog&act=changeDog&id=" + pkgInfo.getId() + "&web_uid=" + ConstFile.uId;
                    String response = example.run(url);
                    FrontDog dogs = FrontDog.objectFromData(response);
                    if (1 == dogs.getCode()) {
                        final FrontDog.UserDogBean dog = dogs.getUserDog();
                        //                    FrontDog.UserDogBean dog=FrontDog.UserDogBean.objectFromData(response)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setDog2Front(dog.getStep(), dog.getDogId());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    //设置用户形象到主台
    private void setUserImg2Front(String imgStr) {

    }


    //获取朋友信息
    private void getFrdInfo(final Friend frd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=user&act=run&flag=1&web_uid=" + ConstFile.uId + "&ownerId=" + frd.getUserId();
                    String response = example.run(url);
                    final FriendInfo friend = FriendInfo.objectFromData(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setFrdInfo(friend, frd);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //设置朋友信息
    private void setFrdInfo(FriendInfo friendInfo, Friend frd) {
        currentFriend=frd;
        if ((frd.getUserId() + "").equalsIgnoreCase(userBean.getUId())) {
            frdArea.setVisibility(View.GONE);
        } else {
            frdArea.setVisibility(View.VISIBLE);
        }

        Boolean isVip = friendInfo.getYellowstatus() == 1;
        if (isVip) {
            frdName.setTextColor(Color.RED);
        } else {
            frdName.setTextColor(Color.BLACK);
        }
        frdName.setText(frd.getUserName());
        Glide.with(context).load(frd.getHeadPic()).into(frdImg);

        frdstar.setText(friendInfo.getMoney()+"");

        int level = CharmUtil.expToGrade4Man(frd.getExp());
        frdLevel.setText(level + "");
        int loc2 = CharmUtil.gradeToExp4Man(level);
        int loc1 = CharmUtil.gradeToExp4Man(level + 1);
        int _nextExp = frd.getExp() - loc2;
        float per = (float) _nextExp / (loc1 - loc2);
        int percent = (int) (per * 100);
        int levelExp = loc1 - loc2;
        frdProgress.setProgress(percent);
        frdProgressPer.setText(_nextExp + "/" + levelExp);


        int dogLevel = CharmUtil.toLevel(frd.getCharm());
        frdDogLevel.setText(dogLevel + "");
        int dogCur = CharmUtil.currentLevelValue(frd.getCharm());
        int dogNext = CharmUtil.needLevelValue(frd.getCharm());
//        int has=frd.getCharm()-dogCur;
//        float p=has/(dogNext-dogCur);
        float p = (float) dogCur / dogNext;
//        frdDogProgress.setProgress((int)(p*100));
        frdDogProgress.setProgress((int) (p * 100));
        frdDogProgressPer.setText(dogCur + "/" + dogNext);

        //设置宠物
        FriendInfo.DogBean dog = friendInfo.getDog();
        int DogId = dog.getDogId();
        int step = dog.getStep();
        setDog2Front(step, DogId);

        //设置人物到前台
        try {
            String vImgStr = friendInfo.getVirtualimage();
            String[] vImgArr = Utils.getQQShowData(vImgStr);
            loadQQshowImg(vImgArr, moveUserImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                case R.id.tab3:
//                    fragmentFrd
                    //显示当前的人物状态
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
        fragmentFrd.setCurrentUserId(userBean.getUId());
        ft.replace(R.id.frame, fragmentFrd);
        ft.commit();
    }

    private void change2() {
        radioButton2.setPressed(true);
        radioButton2.setChecked(true);
        radioButton1.setBackgroundResource(R.drawable.icon_list_p);
        radioButton2.setBackgroundResource(R.drawable.i_tool);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FragmentPkg fragment2 = new FragmentPkg();
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


    //////////抽奖
    private void showLuckyDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_choujiang, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        TextView leftTime = (TextView) dlgView
                .findViewById(R.id.lucky_left_time);//设置标题
        int value = Integer.valueOf(userBean.getLuckDraw());
        leftTime.setText(userBean.getLuckDraw().equalsIgnoreCase("0") ? "0" : Math.abs(value) + "");
        GridView luckyGridView = (GridView) dlgView.findViewById(R.id.lucky_gridView);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();

        //设置背景透明
        WindowManager m = getWindowManager();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = Utils.dp2px(300, MainActivity.this);
        p.width = Utils.dp2px(260, MainActivity.this);
        dialog.getWindow().setAttributes(p);//设置生效

        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.lucky_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ArrayList<LuckyBean.ItemBean> luckyListRandom = LuckyUtil.getGiftArray();
        luckyGridView.setAdapter(new AdapterLucky(this, luckyListRandom));
        luckyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "点击了条目" + position, Toast.LENGTH_SHORT).show();
                getLuckyInfo(dlgView);
            }
        });

    }


    //获取奖励信息
    private void getLuckyInfo(final View dlgView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=task&act=luckDraw&luckNumber=1&web_uid=" + ConstFile.uId;
                    String response = example.run(url);
                    final LuckyBean luckyBean = LuckyBean.objectFromData(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final RelativeLayout rl = dlgView.findViewById(R.id.lucky_msg_root);
                            rl.setVisibility(View.VISIBLE);

                            ImageView imgView = dlgView.findViewById(R.id.lucky_msg_img);
                            ImageView imgOkBtn = dlgView.findViewById(R.id.lucky_msg_ok);
                            imgOkBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rl.setVisibility(View.GONE);
                                }
                            });
                            TextView textView = (TextView) dlgView.findViewById(R.id.lucky_msg_text);

                            if ((luckyBean == null) || (luckyBean.getCode() == 0)) {//无抽奖次数了
                                textView.setVisibility(View.GONE);
                                imgView.setImageResource(R.drawable.time_over);
                                AnimationDrawable animationDrawable = (AnimationDrawable) imgView.getDrawable();
                                animationDrawable.start();
                            } else {
                                imgView.setImageResource(R.drawable.jianglishuoming);
                                AnimationDrawable animationDrawable = (AnimationDrawable) imgView.getDrawable();
                                animationDrawable.start();
                                textView.setVisibility(View.VISIBLE);
                                String str = "恭喜你获得" + luckyBean.getItem().getENum() + "个" + luckyBean.getItem().getName();
                                textView.setText(str);

                                TextView leftTime = (TextView) dlgView
                                        .findViewById(R.id.lucky_left_time);//设置标题

                                String left = leftTime.getText().toString();
                                int lefttimes = Integer.parseInt(left) - 1;
                                leftTime.setText(lefttimes + "");
                                getUserInfo();
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //兑换
    private void showDuiHuanDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_jiangliduihuan, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();

//        //设置背景透明
//        WindowManager m = getWindowManager();
//        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = Utils.dp2px(300,MainActivity.this);
//        p.width = Utils.dp2px(260,MainActivity.this);
//        dialog.getWindow().setAttributes(p);//设置生效

        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.lucky_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText edt = (EditText) dialog.findViewById(R.id.edt);

        ImageView btn = (ImageView) dialog.findViewById(R.id.btn_duihuan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edt.getText().toString().trim();
                if (code.length() == 0) {
                    Toast.makeText(MainActivity.this, "请输入兑换码", Toast.LENGTH_SHORT).show();
                } else {
                    doDuiHuanExp(code, dialog);
                }
            }
        });

    }

    private void doDuiHuanExp(final String code, final AlertDialog dlg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/move_interactive.php?mod=activation&act=exchange&activation=" + code + "&web_uid=" + ConstFile.uId;
                    final String response = example.run(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ((response == null) || (response.length() == 0)) {
                                Toast.makeText(MainActivity.this, "兑换失败", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getInt("code") == 1) {
                                        String string = "";
                                        if (jsonObj.getInt("vip") == 1) {
                                            string = (("恭喜你成功兑换了" + jsonObj.getString("desc") + "×4") + "！\n" + "您的宠物经验和英文币都增加了。\n" + "由于您是会员，奖励已经加成！");
                                        } else {
                                            string = (("恭喜你成功兑换了" + jsonObj.getString("desc") + "×2") + "！\n您的宠物经验和英文币都增加了。");
                                        }
                                        dlg.dismiss();
                                        showDuiHuanOk(string);
                                        getUserInfo();
                                    } else {
                                        Toast.makeText(MainActivity.this, jsonObj.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, "兑换失败", Toast.LENGTH_SHORT).show();
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

    private void showDuiHuanOk(String desc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_duihuanok, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView = dlgView.findViewById(R.id.desc);
        textView.setText(desc);
        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.ok);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @OnClick(R.id.btnvip)
    public void onViewClicked() {
        showDuiHuanVIPDlg();
    }

    //兑换VIP
    private void showDuiHuanVIPDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_vipduihuan, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();

//        //设置背景透明
//        WindowManager m = getWindowManager();
//        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = Utils.dp2px(300,MainActivity.this);
//        p.width = Utils.dp2px(260,MainActivity.this);
//        dialog.getWindow().setAttributes(p);//设置生效

        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.lucky_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText edt = (EditText) dialog.findViewById(R.id.edt);

        ImageView btn = (ImageView) dialog.findViewById(R.id.btn_duihuan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edt.getText().toString().trim();
                if (code.length() == 0) {
                    Toast.makeText(MainActivity.this, "请输入会员序列号", Toast.LENGTH_SHORT).show();
                } else {
                    doDuiHuanVIP(code, dialog);
                }
            }
        });

    }

    private void doDuiHuanVIP(final String code, final AlertDialog dlg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/move_interactive.php?mod=activation&act=active_vip&activation=" + code + "&web_uid=" + ConstFile.uId;
                    final String response = example.run(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ((response == null) || (response.length() == 0)) {
                                Toast.makeText(MainActivity.this, "兑换失败", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getInt("code") == -1) {
                                        String string = jsonObj.getString("msg");
                                        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
                                    } else {
                                        String string = jsonObj.getString("desc");
                                        dlg.dismiss();
                                        showDuiHuanVIPOk(string);
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(MainActivity.this, "兑换失败", Toast.LENGTH_SHORT).show();
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

    private void showDuiHuanVIPOk(String desc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_duihuanok, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView = dlgView.findViewById(R.id.desc);
        textView.setText(desc);
        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.ok);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    //发送朋友邮件
    private void sendFrdLetterDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_letter, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();


        //设置背景透明
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = Utils.dp2px(260, MainActivity.this);
        p.width = Utils.dp2px(330, MainActivity.this);
        dialog.getWindow().setAttributes(p);//设置生效

        final RelativeLayout title1= (RelativeLayout) dialog.findViewById(R.id.top_nav); //111
        final RelativeLayout body1= (RelativeLayout) dialog.findViewById(R.id.luck_main_body);//111
        title1.setVisibility(View.GONE);
        body1.setVisibility(View.GONE);

        final RelativeLayout body2= (RelativeLayout) dialog.findViewById(R.id.msg_main_body);//222
        body2.setVisibility(View.VISIBLE);
        final TextView msgTv= (TextView) dialog.findViewById(R.id.edt_showmsg);

        final EditText msgTv2= (EditText) dialog.findViewById(R.id.edt);

        final RelativeLayout bottomRl= (RelativeLayout) dialog.findViewById(R.id.bottom_rl);
        bottomRl.setVisibility(View.GONE);

        final ImageView okBtn= (ImageView) dialog.findViewById(R.id.img_ok);
        final RelativeLayout del_rl = (RelativeLayout) dialog.findViewById(R.id.del_rl);
        final ImageView btnReplay= (ImageView) dialog.findViewById(R.id.replay);
        msgTv2.setText("");
        msgTv2.setVisibility(View.VISIBLE);
        msgTv.setVisibility(View.GONE);
        del_rl.setVisibility(View.GONE);
        okBtn.setVisibility(View.GONE);
        bottomRl.setVisibility(View.VISIBLE);
        btnReplay.setVisibility(View.GONE);
        ImageView imgView= (ImageView) dialog.findViewById(R.id.img_myImg);
        Glide.with(context).load(currentFriend.getHeadPic()).into(imgView);
        TextView tv= (TextView) dialog.findViewById(R.id.name);
        tv.setText(currentFriend.getUserName());

        ImageView cancel2= (ImageView) dialog.findViewById(R.id.img_cancel2);
        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        ImageView btnOk2= (ImageView) dialog.findViewById(R.id.img_ok2);
        btnOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=msgTv2.getText().toString().trim();
                if(str.length()==0){
                    Toast.makeText(MainActivity.this, "内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean result = false;
                        Utils.OkHttps example = new Utils.OkHttps();
                        try {
                            String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=message&act=sendMessage&type=3&toId="+currentFriend.getUserId()+"&web_uid=" +ConstFile.uId;
                            final String response = example.run(url);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LetterSendResult lsr=LetterSendResult.objectFromData(response);
                                    if (lsr.getCode()==1){
                                        dialog.dismiss();
                                    }
                                    Toast.makeText(MainActivity.this, lsr.getMsg(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    //////////////////////////////////

    //邮件
    private void showLetterDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
        final View dlgView = View
                .inflate(this, R.layout.dlg_letter, null);
        builder.setView(dlgView);
        builder.setCancelable(false);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        dialog.show();

        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.lucky_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //设置背景透明
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = Utils.dp2px(260, MainActivity.this);
        p.width = Utils.dp2px(330, MainActivity.this);
        dialog.getWindow().setAttributes(p);//设置生效

        final GridView gridView = (GridView) dialog.findViewById(R.id.letter_gridview);

        getLeterList();
        adapterLetter=new AdapterLetter(this,letterDataList);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Letter buyDog=letterDataList.get(i);
                showLetter(buyDog, dialog);
            }
        });
        gridView.setAdapter(adapterLetter);
    }

    private void getLeterList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=message&act=getList&web_uid=" +ConstFile.uId;
                    String response = example.run(url);
//                    JSONArray arr=new JSONArray(response);
                    letterDataList= Letter.arrayLetterFromData(response,"3");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapterLetter.reSetDatalist(letterDataList);
                            adapterLetter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showLetter(final Letter letter, final AlertDialog dialog){
        final RelativeLayout title1= (RelativeLayout) dialog.findViewById(R.id.top_nav); //111
        final RelativeLayout body1= (RelativeLayout) dialog.findViewById(R.id.luck_main_body);//111
        title1.setVisibility(View.GONE);
        body1.setVisibility(View.GONE);

        final RelativeLayout body2= (RelativeLayout) dialog.findViewById(R.id.msg_main_body);//222
        body2.setVisibility(View.VISIBLE);

        final TextView deltv= (TextView) dialog.findViewById(R.id.del);
        deltv.setVisibility(View.VISIBLE);

        final TextView msgTv= (TextView) dialog.findViewById(R.id.edt_showmsg);
        msgTv.setVisibility(View.VISIBLE);

        final EditText msgTv2= (EditText) dialog.findViewById(R.id.edt);
        msgTv2.setVisibility(View.GONE);

        final RelativeLayout bottomRl= (RelativeLayout) dialog.findViewById(R.id.bottom_rl);
        bottomRl.setVisibility(View.GONE);

        final ImageView okBtn= (ImageView) dialog.findViewById(R.id.img_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title1.setVisibility(View.VISIBLE);
                body1.setVisibility(View.VISIBLE);
                body2.setVisibility(View.GONE);
            }
        });
        final RelativeLayout del_rl = (RelativeLayout) dialog.findViewById(R.id.del_rl);
        final ImageView btnReplay= (ImageView) dialog.findViewById(R.id.replay);
        btnReplay.setVisibility(View.VISIBLE);
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgTv2.setText("");
                msgTv2.setVisibility(View.VISIBLE);
                msgTv.setVisibility(View.GONE);
                del_rl.setVisibility(View.GONE);
                okBtn.setVisibility(View.GONE);
                bottomRl.setVisibility(View.VISIBLE);
                btnReplay.setVisibility(View.GONE);
            }
        });


        ImageView cancel2= (ImageView) dialog.findViewById(R.id.img_cancel2);
        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgTv2.setVisibility(View.GONE);
                msgTv.setVisibility(View.VISIBLE);
                del_rl.setVisibility(View.VISIBLE);
                okBtn.setVisibility(View.VISIBLE);
                bottomRl.setVisibility(View.GONE);
                btnReplay.setVisibility(View.VISIBLE);
            }
        });

        final LetterRrecive[] lc = new LetterRrecive[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=message&act=openMessage&type=3&id="+letter.getId()+"&web_uid=" +ConstFile.uId;
                    final String response = example.run(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lc[0] =LetterRrecive.objectFromData(response);
                            TextView tv= (TextView) dialog.findViewById(R.id.name);
                            tv.setText(lc[0].getName());

                            msgTv.setText(lc[0].getEDesc());
                            setFriendhead(lc[0],dialog);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        ImageView btnOk2= (ImageView) dialog.findViewById(R.id.img_ok2);
        btnOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=msgTv2.getText().toString().trim();
                if(str.length()==0){
                    Toast.makeText(MainActivity.this, "内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean result = false;
                        Utils.OkHttps example = new Utils.OkHttps();
                        try {
                            String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=message&act=sendMessage&type=3&toId="+lc[0].getFromId()+"&web_uid=" +ConstFile.uId;
                            final String response = example.run(url);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LetterSendResult lsr=LetterSendResult.objectFromData(response);
                                    if (lsr.getCode()==1){
                                        msgTv2.setVisibility(View.GONE);
                                        msgTv.setVisibility(View.VISIBLE);
                                        del_rl.setVisibility(View.VISIBLE);
                                        okBtn.setVisibility(View.VISIBLE);
                                        bottomRl.setVisibility(View.GONE);
                                        btnReplay.setVisibility(View.VISIBLE);
                                    }
                                    Toast.makeText(MainActivity.this, lsr.getMsg(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }


    //朋友
    private void setFriendhead(final LetterRrecive lc, final AlertDialog dialog){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url = ConstFile.serverUrl + "myfarm/5ieng.php?mod=friend&web_uid=" +ConstFile.uId;
                    String response = example.run(url);
//                    JSONArray arr=new JSONArray(response);
                    final List<Friend> dataList= Friend.arrayFriendFromData(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i=0;i<dataList.size();i++){
                                Friend frd=dataList.get(i);
                                if ((frd.getUserId()+"").equalsIgnoreCase(lc.getFromId())){
                                    ImageView imgView= (ImageView) dialog.findViewById(R.id.img_myImg);
                                    Glide.with(context).load(frd.getHeadPic()).into(imgView);
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
