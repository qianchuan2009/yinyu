package com.lyz.wayy;

/**
 * Created by helch on 2018/2/8.
 */

public class UserInfo {

    /**
     * exp : 243300
     * charm : 0
     * dog : {"dogId":4,"dogFeedTime":172800,"dogUnWorkTime":0,"step":-2}
     * top :
     * weather : {"weatherId":1}
     * serverTime : {"time":1514101725}
     * user : {"uId":"5","userName":"朋友甲","money":61119,"FB":"35330","exp":243300,"charm":0,"headPic":"http://www.5ieng.cn/images/male/01.jpg","luckDraw":"-3","yellowstatus":1,"yellowlevel":0}
     * l : 0
     * a : 1
     * c : 0
     * b : 0
     * items : {"1":{"itemId":1},"2":{"itemId":2},"3":{"itemId":3},"4":{"itemId":4}}
     */

    private int exp;
    private int charm;
    private DogBean dog;
    private String top;
    private WeatherBean weather;
    private ServerTimeBean serverTime;
    private UserBean user;
    private int l;
    @com.google.gson.annotations.SerializedName("c")
    private int letters;

    public static class DogBean {
        /**
         * dogId : 4
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
                org.json.JSONObject jsonObject = new org.json.JSONObject(str);

                return new com.google.gson.Gson().fromJson(jsonObject.getString(key), DogBean.class);
            } catch (org.json.JSONException e) {
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

    public static class WeatherBean {
        /**
         * weatherId : 1
         */

        private int weatherId;

        public static WeatherBean objectFromData(String str, String key) {

            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(str);

                return new com.google.gson.Gson().fromJson(jsonObject.getString(key), WeatherBean.class);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public int getWeatherId() {
            return weatherId;
        }

        public void setWeatherId(int weatherId) {
            this.weatherId = weatherId;
        }
    }

    public static class ServerTimeBean {
        /**
         * time : 1514101725
         */

        private int time;

        public static ServerTimeBean objectFromData(String str, String key) {

            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(str);

                return new com.google.gson.Gson().fromJson(jsonObject.getString(key), ServerTimeBean.class);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public static class UserBean {
        /**
         * uId : 5
         * userName : 朋友甲
         * money : 61119
         * FB : 35330
         * exp : 243300
         * charm : 0
         * headPic : http://www.5ieng.cn/images/male/01.jpg
         * luckDraw : -3
         * yellowstatus : 1
         * yellowlevel : 0
         */

        private String uId;
        private String userName;
        private int money;
        private String FB;
        private int exp;
        private int charm;
        private String headPic;
        private String luckDraw;
        private int yellowstatus;
        private int yellowlevel;

        public static UserBean objectFromData(String str, String key) {

            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(str);

                return new com.google.gson.Gson().fromJson(jsonObject.getString(key), UserBean.class);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        public String getUId() {
            return uId;
        }

        public void setUId(String uId) {
            this.uId = uId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getFB() {
            return FB;
        }

        public void setFB(String FB) {
            this.FB = FB;
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

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getLuckDraw() {
            return luckDraw;
        }

        public void setLuckDraw(String luckDraw) {
            this.luckDraw = luckDraw;
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
    }
}
