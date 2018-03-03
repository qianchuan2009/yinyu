package com.lyz.wayy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/25.
 */

public class UpdateBean {

    /**
     * ver : 1.12
     * url : http://eee
     * enforce : 1
     * update : 2017-12-15
     */

    private String ver;
    private String url;
    private int enforce;
    private String update;

    public static UpdateBean objectFromData(String str) {

        try {
            return new Gson().fromJson(str, UpdateBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getEnforce() {
        return enforce;
    }

    public void setEnforce(int enforce) {
        this.enforce = enforce;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
