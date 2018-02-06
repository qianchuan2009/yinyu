package com.lyz.wayy.yuce;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyz.wayy.msg.News;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hhh on 2018/1/7.
 */

public class YuceUtil {

    private static SharedPreferences sp;

    public static void putNews(Context ctx, ArrayList<News> bookList) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookList);
        editor.putString("yuce", json);
        editor.commit();
    }

    public static ArrayList<News> getNews(Context ctx) {
        if (sp == null) {
        sp = ctx.getSharedPreferences("config", MODE_PRIVATE);
        }
        Gson gson = new Gson();
        String json = sp.getString("yuce", null);
        Type type = new TypeToken<List<News>>() {
        }.getType();
        ArrayList<News> arrayList = gson.fromJson(json, type);
        return arrayList;
    }


    public static void putIndex(Context ctx, int index) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("index4yuce",index);
        editor.commit();
    }

    public static int  getIndex(Context ctx) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", MODE_PRIVATE);
        }
        int json = sp.getInt("index4yuce",0);
        return json;
    }
}
