package com.lyz.wayy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CharmUtil {

    //人物等级
    public static int expToGrade4Man(int arg1) {
        return (int) (Math.sqrt((arg1 + 25) / 100) - 0.5);
    }

    public static int gradeToExp4Man(int arg1) {
        return 100 * arg1 * (arg1 + 1);
    }
//    public void setExp(int arg1)
//    {
//        _exp = arg1;
//        level.level = expToGrade(arg1);
//        var loc2:*=gradeToExp(level.level);
//        var loc1:*=gradeToExp(level.level + 1);
//        _nextExp = arg1 - loc2;
//        expBar.percent = (_exp - loc2) / (loc1 - loc2) * 100;
//        expBar.exp = loc1 - loc2;
//        expBar.nextExp = _nextExp;
//        return;
//    }


    private static int[] _charmArr = new int[]{0, 180, 440, 800, 1300, 2000, 2800, 3750, 5000, 6500, 8300, 10400, 15100, 20500, 26500, 33200};


    public static int toLevel(int value) {
        int addLeve;
        int retvalue;
        int rtLeve = 1;
        int i = (_charmArr.length - 1);
        while (i >= 0) {
            if ((value >= _charmArr[i])) {
                rtLeve = i;
                break;
            }
            ;
            i--;
        }
        ;
        if (((rtLeve == (_charmArr.length - 1)) && (value > _charmArr[(_charmArr.length - 1)]))) {
            addLeve = 1;
            retvalue = getLevelValue(addLeve);
            while (value >= retvalue) {
                retvalue = getLevelValue(++addLeve);
            }
            ;
            rtLeve = (rtLeve + (addLeve - 1));
        }
        ;
        return (rtLeve);
    }

    public static int getLevelValue(int power) {
        int retLeve = 16;
        double multiple = 1.05;
        double ret = (_charmArr[(_charmArr.length - 1)] + (6700 * multiple));
        while (power > 1) {
            retLeve++;
            multiple = (multiple * 1.05);
            ret = (ret + (6700 * multiple));
            power--;
        }
        ;
        return (int) ret;
//        return ({
//                "levelValue":ret,
//            "level":retLeve
//			});
    }

    public static int currentLevelValue(int value) {
        int _level = toLevel(value);
        int _levelValue;
        if ((_level < _charmArr.length)) {
            _levelValue = _charmArr[_level];
        } else {
            _levelValue = getLevelValue(((_level - _charmArr.length) + 1));
        }
        ;
        int ret = (value - _levelValue);
        return (ret);
    }

    public static int needLevelValue(int value) {
        int _level = toLevel(value);
        if ((_level <= (_charmArr.length - 2))) {
            return (_charmArr[(_level + 1)] - _charmArr[_level]);
        }
        ;
        if (_level == (_charmArr.length - 1)) {
            return (getLevelValue(1) - _charmArr[(_charmArr.length - 1)]);
        }
        ;
        int addLevel = ((_level - _charmArr.length) + 1);
        return (getLevelValue((addLevel + 1)) - getLevelValue(addLevel));
    }

    public static String toMixCharm(int value) {
        int lv = toLevel(value);
        if ((lv <= 2)) {
            return ("0-2");
        }
        ;
        if (lv <= 4) {
            return ("3-4");
        }
        ;
        if (lv <= 6) {
            return ("5-6");
        }
        ;
        if (lv <= 8) {
            return ("7-8");
        }
        ;
        if (lv <= 10) {
            return ("9-10");
        }
        ;
        return ("10+");
    }
}

