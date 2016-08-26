package com.bolin.lelo.constance;



import android.support.v4.app.Fragment;

import com.bolin.lelo.fragment.mainFrag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
public class fragConst {

   public static List<mainFrag>  fraglist=new ArrayList<>();  //fraglist和fraghashcode成对出现
   public static List<String>  fraghashcode=new ArrayList<>(); //存储对象的hashcode

   public static  int page_interval=40;  //界面之间的间隔

   public static  int init_page_count=1;  //初始化的界面个数

   public static  int new_mainfrag_count=0;  // mainFrag类构造函数被调用的次数

}
