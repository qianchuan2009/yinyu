package com.lyz.wayy.pub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.SurfaceView;


import com.lyz.wayy.SilkyAnimation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Utils {



    public static String[][] getStrData(){
        String[][] strData={{"双色球","ssq"},{"福彩3D","fc3d"},{"七乐彩","qlc"},{"七星彩","qxc"},{"排列3","pl3"},{"排列5","pl5"},{
                "11选5","ah11x5"},{"快3","bjk3"},{"大乐透","dlt"}};
        return strData;
    }

    public static class OkHttps{
        OkHttpClient client =new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

       public String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        public ResponseBody run2(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body();
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
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "9.9.9";
            }
        } catch (Exception e) {
            return "9.9.9";
        }
        return versionName;
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
    /**
     * 获取背包的宠物图片
     */
    public static void loadAmimalAnim(int dogId, int jieduan, SurfaceView mSurfaceView) {


//        AnimationDrawable animationDrawable = new AnimationDrawable();
        SilkyAnimation mAnimation=
                new SilkyAnimation.Builder(mSurfaceView)
                        //设置循环模式, 默认不循环
                        .setRepeatMode(SilkyAnimation.MODE_INFINITE)
                        .build();
        String jieDuanStr="Animal"+dogId+"_0"+jieduan;
//        String url="file:///android_asset/Animal/Animal"+dogId+"/"+jieDuanStr;
        String assetsPath="Animal/Animal"+dogId+"/"+jieDuanStr;

        mAnimation.start(assetsPath);
    }


//    /**
//     * 通过代码添加帧动画方法
//     */
//    private void setSrc2FrameAnim(int dogId,int jieduan) {
//
//
//        AnimationDrawable animationDrawable = new AnimationDrawable();
//        String jieDuanStr="Animal"+dogId+"_0"+jieduan;
//        String url="file:///android_asset/Animal/Animal"+dogId+"/"+jieDuanStr;
//        // 为AnimationDrawable添加动画帧
//        animationDrawable.addFrame(
//                getResources().getDrawable(R.drawable.img00), 50);
//        animationDrawable.addFrame(
//                getResources().getDrawable(R.drawable.img01), 50);
//        animationDrawable.addFrame(
//                getResources().getDrawable(R.drawable.img02), 50);
//        // 设置为循环播放
//        animationDrawable.setOneShot(false);
//        imageView.setBackground(animationDrawable);
//    }


//    虚拟形象算法
//    setQQShowData(data["virtualimage"])；
    public  static String[]  getQQShowData(String arg1) {
//        int i;
        String[] showArray;
        String showdata;

        showdata = arg1.toString();
        showArray = showdata.split("-");//将0-0-0-0-0-init-init-init-init-0-init-0-0-0-init-0-0-0-0-init-0-0-0-0-0-0去除-，变数组
//        showQQData = showArray;//给单例，进行监听
        return showQQData(showArray);
    }
//-------------------------

    private static String[] showQQData(String[] arg1)
    {
        String[] _showQQData = arg1;
        _showQQData[1]="0";
        if(!_showQQData[22].equalsIgnoreCase("0")) {
            _showQQData[1] = "0";
            _showQQData[2] = "0";
            _showQQData[3] = "0";
            _showQQData[4] = "0";
            _showQQData[5] = "0";
            _showQQData[6] = "0";
            _showQQData[7] = "0";
            _showQQData[8] = "0";
            _showQQData[9] = "0";
            _showQQData[10] = "0";
            _showQQData[11] = "0";
            _showQQData[12] = "0";
            _showQQData[13] = "0";
            _showQQData[14] = "0";
            _showQQData[15] = "0";
            _showQQData[16] = "0";
            _showQQData[17] = "0";
            _showQQData[18] = "0";
            _showQQData[19] = "0";
            _showQQData[20] = "0";
            _showQQData[21] = "0";
        }
        if(!_showQQData[9].equalsIgnoreCase("0")) {
            _showQQData[6] = "0";
            _showQQData[8] = "0";
            _showQQData[13] = "0";
            _showQQData[16] = "0";
            _showQQData[22] = "0";
        }
        if(!_showQQData[11].equalsIgnoreCase("0")) {
            _showQQData[10] = "0";
            _showQQData[7] = "0";
            _showQQData[13] = "0";
            _showQQData[16] = "0";
            _showQQData[22] = "0";
        }
        if(!_showQQData[13].equalsIgnoreCase("0")) {
            _showQQData[6] = "0";
            _showQQData[7] = "0";
            _showQQData[8] = "0";
            _showQQData[9] = "0";
            _showQQData[10] = "0";
            _showQQData[11] = "0";
            _showQQData[16] = "0";
            _showQQData[22] = "0";
        }
        if(!_showQQData[16].equalsIgnoreCase("0")) {
            _showQQData[8] = "0";
            _showQQData[9] = "0";
            _showQQData[10] = "0";
            _showQQData[11] = "0";
            _showQQData[13] = "0";
            _showQQData[22] = "0";
        }
        if(!_showQQData[19].equalsIgnoreCase("0")) {
            _showQQData[5] = _showQQData[19];
        }
        if(!_showQQData[12].equalsIgnoreCase("0")) {
            _showQQData[4] = _showQQData[12];
        }
        if(!_showQQData[25].equalsIgnoreCase("0")) {
            _showQQData[2] = _showQQData[25]="0";
        }
        if(_showQQData[16].equalsIgnoreCase("022")){
            _showQQData[19] = "0";
            _showQQData[5] = "0";
        }
        return _showQQData;
    }

    public static Drawable getImageFromAsserts(final Context ctx, String fileName) {
        try {
            InputStream is = ctx.getResources().getAssets().open(fileName);
            return Drawable.createFromStream(is, null);
        } catch (IOException e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (OutOfMemoryError e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * 返回 1表示version1>version2 ;返回0表示version1=version2 ;返回-1表示version1<version2
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        int[] arr1 = getVersionIntArr(version1);
        int[] arr2 = getVersionIntArr(version2);
        if (arr1 == null) {
            if (arr2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else if (arr2 == null) {
            return 1;
        } else {
            int maxLen = Math.max(arr1.length, arr2.length);
            for (int i = 0; i < maxLen; i++) {
                if (i >= arr1.length)
                    return -1;
                if (i >= arr2.length)
                    return 1;
                // 当前位都存在 比较大小
                if (arr1[i] > arr2[i]) {
                    return 1;
                } else if (arr1[i] < arr2[i]) {
                    return -1;
                } else {
                    // 相等
                }
            }
            // 比较完了函数没退出 那就是所有位都相等
            return 0;
        }
    }

    /**
     * 获得版本对应的数组，如10.2.3 返回 [10,2,3]
     *
     * @param version
     * @return
     */
    private static int[] getVersionIntArr(String version) {
        if ((version == null) || (version.length() == 0))
            return null;
        String[] arr = version.split("\\.");
        int[] intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = str2int(arr[i], 0);
        }
        return intArr;
    }

    /**
     * 如果非数字就返回 默认值
     *
     * @param str
     * @return
     */
    private static int str2int(String str, int def) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return def;
        }
    }

}