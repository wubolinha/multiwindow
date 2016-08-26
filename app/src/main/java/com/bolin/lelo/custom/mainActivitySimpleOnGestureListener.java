package com.bolin.lelo.custom;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.bolin.lelo.event.fragEvent;
import com.bolin.lelo.event.slideEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/8/23.
 */
public class mainActivitySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

    private int mViewpageX,mViewpageY,mViewpageWidth,mViewpageHeight;

    public  void setViewPagePosition(int mViewpageWidth,int mViewpageHeight ){

        this.mViewpageWidth = mViewpageWidth*7/10;
        this.mViewpageHeight = mViewpageHeight*7/10;

        this.mViewpageX = (mViewpageWidth /2)-(mViewpageWidth*7/10/2 );
        this.mViewpageY = (mViewpageHeight /2)-(mViewpageHeight*7/10/2 );;

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

      //  Logger.v("onFling  "+ "x:  "+e1.getX() +"   y:  "+e1.getY() +"x:  "+e2.getX() +"   y:  "+e2.getY()   );

        JudgeSendEvent("onFling",e1,e2);
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onDown(MotionEvent e) {

      //  Logger.v("onDown   "+ "x:  "+e.getX() +"   y:  "+e.getY()   );
        JudgeSendEvent("onDown",e,null);
        return super.onDown(e);
    }

    //判断是否发出事件
    private void JudgeSendEvent(String action,MotionEvent e1, MotionEvent e2){
        switch (action ){
            case "onFling":

                if(e1.getX()<mViewpageX && e1.getY()>mViewpageY && e1.getY()<(mViewpageY+mViewpageHeight) ){
                    if( e2.getX()-e1.getX() >20 ){
                     //   Logger.v(" 向右滑动   ");
                        EventBus.getDefault().post(new slideEvent(MotionEvent.ACTION_MOVE, "right"));   //  发送消息
                    }else if(e1.getX()-e2.getX() >20){
                    //    Logger.v(" 向左滑动   ");
                        EventBus.getDefault().post(new slideEvent(MotionEvent.ACTION_MOVE,"left" ));
                    }

                }
                else if(e1.getX()>(mViewpageX+ mViewpageWidth)&& e1.getY()>mViewpageY && e1.getY()<(mViewpageY+mViewpageHeight) ){
                    if( e2.getX()-e1.getX() >20 ){
                        EventBus.getDefault().post(new slideEvent(MotionEvent.ACTION_MOVE, "right"));
                      //  Logger.v(" 向右滑动   ");
                    }else if(e1.getX()-e2.getX() >20){
                      //  Logger.v(" 向左滑动   ");
                        EventBus.getDefault().post(new slideEvent(MotionEvent.ACTION_MOVE, "left"));
                    }
                }


                break;


            case "onDown":
//                Logger.v( "  onDown   "+e1.getX()+"   "+e1.getY()
//                +"\n mViewpageX  "+mViewpageX +"  mViewpageWidth  "+mViewpageWidth
//                 +"\n mViewpageY  "+mViewpageY +"  mViewpageWidth  "+mViewpageHeight);


                if(e1.getX()<mViewpageX && e1.getY()>mViewpageY && e1.getY()<(mViewpageY+mViewpageHeight) ){

                 // Logger.v( "  onDown left "  );
                    EventBus.getDefault().post(new slideEvent(MotionEvent.ACTION_DOWN, "left"));

                }
                else if(e1.getX()>(mViewpageX+ mViewpageWidth)&& e1.getY()>mViewpageY && e1.getY()<(mViewpageY+mViewpageHeight) ){
                    EventBus.getDefault().post(new slideEvent(MotionEvent.ACTION_DOWN, "right"));
                    //Logger.v( "  onDown right "  );
                }

                break;
        }

    }


}
