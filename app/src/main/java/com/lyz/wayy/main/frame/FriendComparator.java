package com.lyz.wayy.main.frame;

import com.lyz.wayy.bean.Friend;

import java.util.Comparator;

/**
 * Created by helch on 2018/3/8.
 */

public class FriendComparator implements Comparator<Friend> {
    private boolean asc;
    /**
     * 1降序
     * 其余升序
     * */
    public FriendComparator(boolean isAsc){
        this.asc=isAsc;
    }
    @Override
    public int compare(Friend lhs, Friend rhs) {
        // TODO 自动生成的方法存根
        if(!asc){
            return rhs.getExp()-lhs.getExp();
        }else{
            return -rhs.getExp()+lhs.getExp();
        }
    }

}