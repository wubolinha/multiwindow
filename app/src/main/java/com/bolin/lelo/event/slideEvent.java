package com.bolin.lelo.event;

import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/8/23.
 */

//viewpager缩小后点击  viewpager控件的左边或者右边 发出的事件
public class slideEvent extends baseEvent {

    private int type;  //事件类型：点击 ACTION_DOWN ，滑动 ACTION_MOVE
    private String direction;//点击位置 或者 滑动方向

    public slideEvent(int type, String direction) {
        this.type = type;
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public String getDirection() {
        return direction;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
