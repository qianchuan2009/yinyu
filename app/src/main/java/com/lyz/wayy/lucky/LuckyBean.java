package com.lyz.wayy.lucky;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/15.
 */

public class LuckyBean {

    /**
     * code : 1
     * item : {"eParam":"4","eNum":"20","name":"经验","eType":7}
     */

    private int code;
    private ItemBean item;

    public static LuckyBean objectFromData(String str) {

        try {
//            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(str, LuckyBean.class);
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

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * eParam : 4
         * eNum : 20
         * name : 经验
         * eType : 7
         */

        private String eParam;
        private String eNum;
        private String name;
        private int eType;

        public static ItemBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(key), ItemBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        public String getEParam() {
            return eParam;
        }

        public void setEParam(String eParam) {
            this.eParam = eParam;
        }

        public String getENum() {
            return eNum;
        }

        public void setENum(String eNum) {
            this.eNum = eNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getEType() {
            return eType;
        }

        public void setEType(int eType) {
            this.eType = eType;
        }
    }
}
