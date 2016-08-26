package com.bolin.lelo.event;

/**
 * Created by Administrator on 2016/8/25.
 */
//显示删除按钮
public class showDelImg extends baseEvent {


   private boolean  isShow;

    public showDelImg(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }
}
