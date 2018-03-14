package com.lyz.wayy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/3/1.
 */

public class ClothesInfoBean {

    /**
     "id": "46",
     "imagename": "1号 背景",  //图片名称
     "cost": "10",				//价格
     "description": "1号 背景",//说明、描述
     "endurance": "7",//持久
     "suitable": "t",//适用对象t为通用，f为女人，m为男人
     "graphic": "001.gif",//图片资源名称
     "xgraphic": "001x.gif",//商品资源名称
     "stock": "10000",//数量
     "open": "1",//开放
     "picid": "001"//图片id，等同于graphic序号
     * sort : //分类 1 sort（分类） 8：裤裙, 10：上衣, 14：脸型, 19：发型, 20：帽子, 17：饰品, 23：装饰，24：装饰2,
     */

    private String id;
    private String imagename;
    private String cost;
    private String description;
    private String endurance;
    private String suitable;
    private String graphic;
    private String xgraphic;
    private String stock;
    private String open;
    private String sort;
    private String picid;

    public static ClothesInfoBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), ClothesInfoBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<ClothesInfoBean> arrayClothesInfoBeanFromData(String str) {

        try {
//            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ClothesInfoBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndurance() {
        return endurance;
    }

    public void setEndurance(String endurance) {
        this.endurance = endurance;
    }

    public String getSuitable() {
        return suitable;
    }

    public void setSuitable(String suitable) {
        this.suitable = suitable;
    }

    public String getGraphic() {
        return graphic;
    }

    public void setGraphic(String graphic) {
        this.graphic = graphic;
    }

    public String getXgraphic() {
        return xgraphic;
    }

    public void setXgraphic(String xgraphic) {
        this.xgraphic = xgraphic;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPicid() {
        return picid;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }
}
