package com.lyz.wayy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;

import java.io.IOException;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {



    public static String[][] getStrData(){
        String[][] strData={{"双色球","ssq"},{"福彩3D","fc3d"},{"七乐彩","qlc"},{"七星彩","qxc"},{"排列3","pl3"},{"排列5","pl5"},{
                "11选5","ah11x5"},{"快3","bjk3"},{"大乐透","dlt"}};
        return strData;
    }

    public static class OkHttps{
        OkHttpClient client = new OkHttpClient();
       public String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }

    /** 生成漂亮的颜色 */
    public static int generateBeautifulColor() {
        Random random = new Random();
        //为了让生成的颜色不至于太黑或者太白，所以对3个颜色的值进行限定
        int red = random.nextInt(150) + 50;//50-200
        int green = random.nextInt(150) + 50;//50-200
        int blue = random.nextInt(150) + 50;//50-200
        return Color.rgb(red, green, blue);//使用r,g,b混合生成一种新的颜色
    }

    /** 获得状态栏的高度 */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * dp转px
     * @param dp
     * @param context
     */
    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static boolean isOpenNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 显示弹出选择框
     *
     * @param context
     * @param title
     *            提示名
     * @param content
     *            提示内容
     * @param oklistener
     *            确定事件
     */
    public static void showAlertDialog(Context context, String title,
                                       String content, DialogInterface.OnClickListener oklistener) {
        /**
         * IRPT-24865 批量上报、更新时，安卓设备点击返回，会异常崩溃
         * Unable to add window -- token android.os.BinderProxy@1798ef0 is not valid; is your activity running?
         */
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(content);
            builder.setPositiveButton("是", oklistener);
            builder.setNegativeButton("否", null);
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示弹出选择框
     *
     * @param context
     * @param title 提示名
     * @param content 提示内容
     * @param oklistener 确定事件
     * @param cancelListener 取消事件
     */
    public static void showAlertDialog(Context context, String title, String content,
                                       DialogInterface.OnClickListener oklistener,
                                       DialogInterface.OnClickListener cancelListener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(content);
            builder.setPositiveButton("是", oklistener);
            builder.setNegativeButton("否", cancelListener);
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示弹出选择框
     *
     * @param context
     * @param title 提示名
     * @param content 提示内容
     * @param oklistener 确定事件
     * @param cancelListener 取消事件
     */
    public static void showAlertDialog(Context context, String title, String content,
                                       String positiveButtonTxt, String negativeButtonTxt,
                                       DialogInterface.OnClickListener oklistener,
                                       DialogInterface.OnClickListener cancelListener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(content);
            builder.setPositiveButton(positiveButtonTxt, oklistener);
            builder.setNegativeButton(negativeButtonTxt, cancelListener);
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示弹出选择框
     *
     * @param context
     * @param title 提示名
     * @param content 提示内容
     * @param oklistener 确定事件
     */
    public static void showOkAlertDialog(Context context, String title, String content,
                                         DialogInterface.OnClickListener oklistener) {
        showOkAlertDialog(context, title, "确定", content, oklistener);
    }

    /**
     * 显示弹出选择框
     *
     * @param context
     * @param title 提示名
     * @param content 提示内容
     * @param oklistener 确定事件
     */
    public static void showOkAlertDialog(Context context, String title, String positiveButtonTxt,
                                         String content, DialogInterface.OnClickListener oklistener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(content);
            builder.setCancelable(false);
            builder.setPositiveButton(positiveButtonTxt, oklistener);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}