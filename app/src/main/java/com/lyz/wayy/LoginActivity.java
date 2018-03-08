package com.lyz.wayy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lyz.wayy.pub.ConstFile;
import com.lyz.wayy.pub.Utils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by helch on 2018/2/5.
 */

public class LoginActivity extends Activity {
    @BindView(R.id.activity_login_username_et)
    EditText userName;
    @BindView(R.id.activity_login_pw_et)
    EditText Pw;
    @BindView(R.id.activity_loginok_btn)
    Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_login);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = this.getSharedPreferences("mypw", MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        String pwStr=sharedPreferences.getString("pw","");
       if(name.length()>0){
           userName.setText(name);
           Pw.setText(pwStr);
           login();
       }
    }


    private void login() {
        final  String name=userName.getText().toString();
        final String pwStr=Pw.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                Utils.OkHttps example = new Utils.OkHttps();
                try {
                    String url= ConstFile.serverUrl+"index2.php?username="+name.trim()+"&password="+pwStr.trim();
                    String response = example.run(url);
                    JSONObject jObj = new JSONObject(response);
                    String type = jObj.getString("code");
                    final String msg=jObj.getString("msg");
                    final  String uid=jObj.getString("UID");
                    if ("1".equalsIgnoreCase(type)) {
                        ConstFile.uId=uid;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("uid", uid);
                                startActivity(intent);

                                SharedPreferences sharedPreferences = getSharedPreferences("mypw", MODE_PRIVATE);

                                //得到SharedPreferences.Editor对象，并保存数据到该对象中
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", name.trim());
                                editor.putString("pw", pwStr.trim());
                                //保存key-value对到文件中
                                editor.commit();

                                finish();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "无法连接服务器！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    @OnClick(R.id.activity_loginok_btn)
    public void onViewClicked() {
        login();
    }
}
