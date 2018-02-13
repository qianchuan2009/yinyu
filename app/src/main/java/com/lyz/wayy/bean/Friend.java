package com.lyz.wayy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/10.
 */

public class Friend implements Serializable {
    /**
     * userId : 8
     * userName : 朋友丁
     * headPic : http://www.5ieng.cn/images/male/03.jpg
     * exp : 242328
     * charm : 242036
     */

    private int userId;
    private String userName;
    private String headPic;
    private int exp;
    private int charm;

    public static Friend objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), Friend.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Friend> arrayFriendFromData(String str) {

        try {
//            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Friend>>() {
            }.getType();

            return new Gson().fromJson(str, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCharm() {
        return charm;
    }

    public void setCharm(int charm) {
        this.charm = charm;
    }
}
