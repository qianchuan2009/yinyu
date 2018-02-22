package com.lyz.wayy.lucky;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helch on 2018/2/15.
 */

public class LuckyUtil {

    public static ArrayList<LuckyBean.ItemBean> getGiftArray() {
//        var luckDrawTipWindow:* = null;

//        var i:* = null;
        ArrayList<LuckyBean.ItemBean> listArr=new ArrayList<LuckyBean.ItemBean>();

        LuckyBean.ItemBean i = new LuckyBean.ItemBean();
        int j;
        int k;
        int l = 0;
        int m;
        int z;
        int[] testarray = new int[12];
        j = 0;
        while (j < 12) {
            testarray[j] = (j + 1);
            j++;
        }

        j = 0;
        while (j < 12) {
            z = (int) (Math.floor((Math.random() * 12)));
            if ((!(j == z))) {
                k = testarray[j];
                testarray[j] = testarray[z];
                testarray[z] = k;
            }
            ;
            j++;
        }
        //下面这些就是除去点击的那个，剩余的8个随机生成奖励，k代表类型，l用不到，m是数量
        j = 0;
        for (; j < 9; j++)//9个翻奖方块。
        {
//            if ((!(j == selectIndex)))//_item是你点击抽奖方块的序号，0-8
//            {
                z = testarray[j];
//            } else {
//                continue;
//            };
            k = 1;// 5是宠物经验，6是金币，7是人物经验，11是英语币
            m = 1;//这个代表数量
            switch (z) {
                case 1:
                    k = 6;
                    l = 4;
                    m = 3;
                    break;
                case 2:
                    k = 6;
                    l = 4;
                    m = 5;
                    break;
                case 3:
                    k = 6;
                    l = 4;
                    m = 4;
                    break;
                case 4:
                    k = 5;
                    l = 4;
                    m = 30;
                    break;
                case 5:
                    k = 5;
                    l = 4;
                    m = 50;
                    break;
                case 6:
                    k = 5;
                    l = 4;
                    m = 20;
                    break;
                case 7:
                    k = 7;
                    l = 4;
                    m = 20;
                    break;
                case 8:
                    k = 7;
                    l = 0;
                    m = 30;
                    break;
                case 9:
                    k = 7;
                    l = 4;
                    m = 25;
                    break;
                case 10:
                    k = 11;
                    l = 0;
                    m = 3;
                    break;
                case 11:
                    k = 11;
                    l = 4;
                    m = 5;
                    break;
                case 12:
                    k = 11;
                    l = 0;
                    m = 7;
                    break;

            }
            ;
            i.setENum(m + "");
            i.setEParam(l + "");
            i.setEType(k);
            i.setName("d");
            listArr.add(i);
        }
        return  listArr;
    }







}
