package com.lyz.wayy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.lyz.wayy.msg.WebActivity;
import com.lyz.wayy.pub.Utils;

import org.json.JSONObject;


/**
 * @desc 启动屏
 * Created by devilwwj on 16/1/23.
 */
public class SplashActivity extends Activity {
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断是否是第一次开启应用
//        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
//        if (!isFirstOpen) {
//            Intent intent = new Intent(this, WelcomeGuideActivity.class);
//            startActivity(intent);
//            finish();
//            return;
//        }

        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.activity_splash);

//        JPushInterface.init(this);
//        if(Utils.isOpenNetwork(this)){
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    isNeedTurn();
//                }
//            }, 1000);
//        }else{
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                 public void run() {
//                    enterHomeActivity();
//                }
//            }, 2000);
//        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);


    }

    private  void isNeedTurn(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result=false;
                Utils.OkHttps example = new Utils.OkHttps();
                try{
//                    String pkgName=MainActivity.getCurrentPkgName(SplashActivity.this);
//                    String response = example.run("http://vipapp.01appddd.com/Lottery_server/get_init_data.php?type=android&appid="+pkgName);
                    String response = example.run("http://5597755.com/Lottery_server/get_init_data.php?type=android&appid=mz00001");
                    JSONObject jObj=new JSONObject(response);
                    String type=jObj.getString("rt_code");
                    if ("200".equalsIgnoreCase(type)){
                        String data=jObj.getString("data");
                        byte[] b= Base64.decode(data,0);
                        String str=new String(b);
                        Log.d("a", "run: "+str);
                        JSONObject jsonObj=new JSONObject(str);
                        String show_url=jsonObj.getString("show_url");
                        final String url2=jsonObj.getString("url");
                        if("1".equalsIgnoreCase(show_url)){
                            url=url2;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashActivity.this, WebActivity.class);
                                    intent.putExtra("dh", 99);
                                    intent.putExtra("title",url);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    enterHomeActivity();
                                }
                            }, 2000);
                                }
                            });
                        }

                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                enterHomeActivity();
                            }
                        }, 2000);
                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void enterHomeActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
