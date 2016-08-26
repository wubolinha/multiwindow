package com.bolin.lelo.fragment;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bolin.lelo.R;
import com.bolin.lelo.constance.fragConst;
import com.bolin.lelo.event.baseEvent;
import com.bolin.lelo.event.delThisFrag;
import com.bolin.lelo.event.deleteFragEvent;
import com.bolin.lelo.event.fragEvent;
import com.bolin.lelo.event.showDelImg;
import com.bolin.lelo.event.zoomEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Administrator on 2016/8/20.
 */
public class mainFrag extends baseFrag {


    private RelativeLayout mainrlt;
    private TextView showtitletv;
    private LinearLayout showtitlelt;
    private FrameLayout rootlt;
    private DisplayMetrics dm2;
    private String fragTag = "";
    private ImageView delthispage;
    private boolean isNewFragment = false;
    private int color;

    public mainFrag() {
        this.fragTag = fragConst.new_mainfrag_count + "";
        fragConst.new_mainfrag_count++;
        isNewFragment = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mainrlt = (RelativeLayout) view.findViewById(R.id.mainrlt);
        mainrlt.setOnTouchListener(this);
        rootlt = (FrameLayout) view.findViewById(R.id.rootlt);
        rootlt.setOnTouchListener(this);
        delthispage = (ImageView) view.findViewById(R.id.delthispage);
        showtitlelt = (LinearLayout) view.findViewById(R.id.showtitlelt);
        showtitletv = (TextView) view.findViewById(R.id.showtitletv);
        EventBus.getDefault().register(this);
        init(view);
        return view;

    }

    private void init(View view) {

        dm2 = getResources().getDisplayMetrics();
        TextView showtag = (TextView) view.findViewById(R.id.showtag);
        showtag.setText("当前fragment " + getFragTag());


        delthispage.setOnClickListener(View -> {
            delAnime();
            new Handler().postDelayed(() -> {
                EventBus.getDefault().post(new delThisFrag());   //  发送消息
            }, 300);
        });


        if ((fragConst.new_mainfrag_count > 1) && isNewFragment) {
            //缩小
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0.1f, 1f);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0.1f, 1f);
            ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(rootlt, pvhX, pvhY);
            scale.setDuration(50);
            scale.start();

            Random random = new Random();
            color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        }

        isNewFragment = false;
        if (color != 0) {
            mainrlt.setBackgroundColor(color);
        }
    }

    public String getFragTag() { // 被反射的方法
        return fragTag;
    }

    public void setFragTag(String fragTag) {
        this.fragTag = fragTag;
    }

    private float mov_x, mov_y; //相对于手指移动了的位置
    private int left, right, top, bottom;
    private List<int[]> positionlist = new ArrayList<>();

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        super.onTouch(v, event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            positionlist.clear();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //     Logger.v("x:  " + event.getX() + "   y:  " + event.getY());
            mov_x = event.getX() - super.point_x;
            mov_y = event.getY() - super.point_y;

            left = mainrlt.getLeft();
            right = mainrlt.getRight();
            top = mainrlt.getTop();
            bottom = mainrlt.getBottom();

            if (Math.abs(dm2.widthPixels - mainrlt.getWidth()) > 5) {
                mainrlt.layout(left, top + (int) mov_y, right, bottom + (int) mov_y);

                int[] position = {left, top + (int) mov_y, right, bottom + (int) mov_y};
                positionlist.add(position);
                // Logger.v("left " + position[0] + " top " + position[1] + " right " + position[2] + "  bottom " + position[3] );

                if (Math.abs(position[1]) > mainrlt.getWidth() / 2) {
                    //  Logger.v("-    显示  删除 按钮     -");
                    EventBus.getDefault().post(new showDelImg(true));   //  发送消息
                } else {
                    EventBus.getDefault().post(new showDelImg(false));   //  发送消息
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (positionlist.size() >= 2) {
                if (fragConst.fraglist.size()>1 &&  Math.abs(positionlist.get(positionlist.size() - 1)[1]) > mainrlt.getWidth() / 2) {
                    //  Logger.v("-      删除  fragment    -");
                    delAnime();
                    EventBus.getDefault().post(new showDelImg(false));   //  发送消息
                    new Handler().postDelayed(() -> {
                        EventBus.getDefault().post(new deleteFragEvent(getFragTag()));   //  发送消息
                    }, 200);
                    return true;
                }
            } else {

                //放大或者缩小fragment
                EventBus.getDefault().post(new fragEvent(getFragTag()));   //  发送消息
            }

            for (int i = positionlist.size() - 1; i >= 0; i--) {
                mainrlt.layout(positionlist.get(i)[0], positionlist.get(i)[1], positionlist.get(i)[2], positionlist.get(i)[3]);
            }
            mainrlt.layout(0, 0, mainrlt.getWidth(), mainrlt.getHeight());


        }

        return true;
    }


    //删除动画
    private void delAnime() {
        if (fragConst.fraglist.size() <= 1) {
            return;
        }
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.01f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.01f);
        ObjectAnimator scalexy = ObjectAnimator.ofPropertyValuesHolder(mainrlt, pvhX, pvhY);

        ObjectAnimator scale = ObjectAnimator.ofFloat(mainrlt, "translationY", 0, -2500);

        scale.setDuration(200);
        scalexy.setDuration(200);
        scale.start();
        scalexy.start();

    }

    @Subscribe
    public void onEventMainThread(baseEvent event) {
        // Toast.makeText(getActivity(), " 收到 event 数据  ", 0).show();

        if (event instanceof zoomEvent) {
            if (((zoomEvent) event).isMatchParent()) {
                showtitlelt.setVisibility(View.INVISIBLE);
            } else {
                showtitlelt.setVisibility(View.VISIBLE);
            }


        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


}
