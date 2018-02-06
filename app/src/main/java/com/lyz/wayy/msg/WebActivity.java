package com.lyz.wayy.msg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyz.wayy.R;
import com.lyz.wayy.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;


public class WebActivity extends AppCompatActivity {
    String title="";
    boolean loaded=false;
    String data="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        TextView titleV= (TextView) findViewById(R.id.title);
        titleV.setText(title);
        ImageView back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String url=intent.getStringExtra("url");
        initData(url);

    }
    private void  initData(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.parse(new URL(url), 10000);
                    Elements titleLinks = doc.select("div.newsCenterA");

                    Elements imgs=titleLinks.first().select("img");
                    for (Element e : imgs) {
                        String width = e.attr("width");
                        if(null==width||"".equalsIgnoreCase(width.trim())){
                            e.attr("width", "100%");
                            continue;
                        }
                        int w=Integer.parseInt(width);
                        if(w>320){
                            e.attr("width", "100%");
                            e.attr("height", "inherit");
                            continue;
                        }
                    }


                    data= titleLinks.toString();
                    // 需要做的事:发送消息
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Utils.showOkAlertDialog(WebActivity.this, "提示", "向服务器请求数据失败", null);
                }
            }
        }).start();
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                init(data);
            }
            super.handleMessage(msg);
        };
    };

    private void  init(String str){
        final WebView webView= (WebView) findViewById(R.id.webview22);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.setVisibility(View.GONE);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        ImageView iv= (ImageView) findViewById(R.id.web_splash);
//                        iv.setVisibility(View.VISIBLE);
//                    }
//                }, 2000);
//              }});
            }
            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
//            String url=getUrl(index);
//            webView.loadUrl(url);


        webView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        webView.loadData(str, "text/html; charset=UTF-8", null);//这种写法可以正确解码
//        webView.loadData(str,"text/html", "utf-8");


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    webView.setVisibility(View.VISIBLE);
                }
            }, 2000);
    }

}
