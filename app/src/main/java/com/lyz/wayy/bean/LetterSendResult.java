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

public class LetterSendResult {
    /**
     * code : 1
     * msg : ss
     */

    private int code;
    private String msg;

    public static LetterSendResult objectFromData(String str) {

        try {
            return new Gson().fromJson(str, LetterSendResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
