package com.lyz.wayy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/13.
 */

public class FriendInfo {

    /**
     * money : 60753
     * exp : 242456
     * charm : 96
     * dog : {"dogId":27,"dogFeedTime":172800,"dogUnWorkTime":0,"step":-2}
     * top :
     * yellowstatus : 0
     * yellowlevel : 0
     */

    private int money;
    private int exp;
    private int charm;
    private DogBean dog;
    private String top;
    private int yellowstatus;
    private int yellowlevel;

    public static FriendInfo objectFromData(String str) {

        try {
//            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(str, FriendInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
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

    public DogBean getDog() {
        return dog;
    }

    public void setDog(DogBean dog) {
        this.dog = dog;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public int getYellowstatus() {
        return yellowstatus;
    }

    public void setYellowstatus(int yellowstatus) {
        this.yellowstatus = yellowstatus;
    }

    public int getYellowlevel() {
        return yellowlevel;
    }

    public void setYellowlevel(int yellowlevel) {
        this.yellowlevel = yellowlevel;
    }

    public static class DogBean {
        /**
         * dogId : 27
         * dogFeedTime : 172800
         * dogUnWorkTime : 0
         * step : -2
         */

        private int dogId;
        private int dogFeedTime;
        private int dogUnWorkTime;
        private int step;

        public static DogBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DogBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DogBean> arrayDogBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DogBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public int getDogId() {
            return dogId;
        }

        public void setDogId(int dogId) {
            this.dogId = dogId;
        }

        public int getDogFeedTime() {
            return dogFeedTime;
        }

        public void setDogFeedTime(int dogFeedTime) {
            this.dogFeedTime = dogFeedTime;
        }

        public int getDogUnWorkTime() {
            return dogUnWorkTime;
        }

        public void setDogUnWorkTime(int dogUnWorkTime) {
            this.dogUnWorkTime = dogUnWorkTime;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }
    }
}
