package com.bolin.lelo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bolin.lelo.R;
import com.bolin.lelo.event.baseEvent;
import com.bolin.lelo.event.fragEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Administrator on 2016/8/23.
 */
public class baseFrag extends Fragment implements View.OnTouchListener {

    protected float point_x, point_y; //手指按下的位置
    private boolean flag;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            point_x = event.getX();
            point_y = event.getY();
            flag = false;
        }

        return true;
    }



}
