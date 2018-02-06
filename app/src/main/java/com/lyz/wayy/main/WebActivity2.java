package com.lyz.wayy.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyz.wayy.R;


public class WebActivity2 extends AppCompatActivity {
    String title="";
    boolean loaded=false;
    String dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        Intent intent = getIntent();
        dh = intent.getStringExtra("dh");
        title = intent.getStringExtra("title");
        int type=intent.getIntExtra("type",1);
        init(type);
        TextView t= (TextView) findViewById(R.id.title);
        t.setText(title);

        ImageView imgView= (ImageView) findViewById(R.id.back);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void  init(int   type){
        WebView webView= (WebView) findViewById(R.id.webview23);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);

                webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient());

        String url="file:///android_asset/gold/";
        if(type==1){
            url+=getPlaySm();
        }else{
            url+=getSkillUrl();
        }
        url+=".html";
        webView.loadUrl(url);
    }

    private String getPlaySm(){
        String url="";
        String url2="play_instruction_";
        if(dh.equalsIgnoreCase("ahk3")||dh.equalsIgnoreCase("bjk3")){
            url="JSK3";
        }else if(dh.equalsIgnoreCase("pl3")){
            url="PLS";
        }else if(dh.equalsIgnoreCase("fc3d")){
            url="FC3D";
        }else if(dh.equalsIgnoreCase("pl5")){
            url="PLW";
        }else if(dh.equalsIgnoreCase("ah11x5")){
            url="GX11X5";
        }else if(dh.equalsIgnoreCase("qlc")){
            url="QLC";
        }else if((dh.equalsIgnoreCase("qxc"))){
            url="QXC";
        }else if(dh.equalsIgnoreCase("ssq")){
            url="SSQ";
        }else  if(dh.equalsIgnoreCase("dlt")){
            url="DLT";
        }else  if(dh.equalsIgnoreCase("tjssc")){
            url="JXSSC";
        }
        return  url2+url;
    }

    private String getSkillUrl(){
        String url="";
        String url2="play_skill_";
        if(dh.equalsIgnoreCase("ahk3")||dh.equalsIgnoreCase("bjk3")){
            url="K3";
        }else if(dh.equalsIgnoreCase("pl3")){
            url="PLS";
        }else if(dh.equalsIgnoreCase("fc3d")){
            url="3D";
        }else if(dh.equalsIgnoreCase("pl5")){
            url="PLW";
        }else if(dh.equalsIgnoreCase("ah11x5")){
            url="SD11X5";
        }else if(dh.equalsIgnoreCase("qlc")){
            url="QLC";
        }else if((dh.equalsIgnoreCase("qxc"))){
            url="QXC";
        }else if(dh.equalsIgnoreCase("ssq")){
            url="SSQ";
        }else  if(dh.equalsIgnoreCase("dlt")){
            url="DLT";
        }else  if(dh.equalsIgnoreCase("tjssc")){
            url="JXSSC";
        }
        return  url2+url;
    }
}
