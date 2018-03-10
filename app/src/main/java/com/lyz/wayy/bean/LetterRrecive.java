package com.lyz.wayy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/3/10.
 */

public class LetterRrecive {

    /**
     * fromId : 1
     * sendTime : 1505313504
     * eDesc : 各位老师，欢迎使用我爱英语网。我们一起为英语教学努力。
     * status : 1
     * id : 1505313504
     * name : 管理员
     */

    private String fromId;
    private int sendTime;
    private String eDesc;
    private int status;
    private int id;
    private String name;

    public static LetterRrecive objectFromData(String str) {

        try {
//            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(str, LetterRrecive.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public String getEDesc() {
        return eDesc;
    }

    public void setEDesc(String eDesc) {
        this.eDesc = eDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
