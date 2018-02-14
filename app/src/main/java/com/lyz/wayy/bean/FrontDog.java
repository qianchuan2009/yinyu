package com.lyz.wayy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/14.
 */
/*
 *更换宠物到前台的bean
 */

public class FrontDog {

    /**
     * code : 1
     * id : 8
     * userDog : {"dogId":8,"dogValidTime":0,"dogFeedTime":172800,"dogUnWorkTime":0,"petExp":24,"step":-2,"uId":5}
     */

    private int code;
    private int id;
    private UserDogBean userDog;

    public static FrontDog objectFromData(String str) {

        try {
//            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(str, FrontDog.class);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDogBean getUserDog() {
        return userDog;
    }

    public void setUserDog(UserDogBean userDog) {
        this.userDog = userDog;
    }

    public static class UserDogBean {
        /**
         * dogId : 8
         * dogValidTime : 0
         * dogFeedTime : 172800
         * dogUnWorkTime : 0
         * petExp : 24
         * step : -2
         * uId : 5
         */

        private int dogId;
        private int dogValidTime;
        private int dogFeedTime;
        private int dogUnWorkTime;
        private int petExp;
        private int step;
        private int uId;

        public static UserDogBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(key), UserDogBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        public int getDogId() {
            return dogId;
        }

        public void setDogId(int dogId) {
            this.dogId = dogId;
        }

        public int getDogValidTime() {
            return dogValidTime;
        }

        public void setDogValidTime(int dogValidTime) {
            this.dogValidTime = dogValidTime;
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

        public int getPetExp() {
            return petExp;
        }

        public void setPetExp(int petExp) {
            this.petExp = petExp;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public int getUId() {
            return uId;
        }

        public void setUId(int uId) {
            this.uId = uId;
        }
    }
}
