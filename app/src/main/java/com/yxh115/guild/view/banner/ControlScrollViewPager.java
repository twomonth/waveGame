package com.yxh115.guild.view.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/8/3.
 */

public class ControlScrollViewPager extends ViewPager {

    private boolean isCanScroll = true;

    public ControlScrollViewPager(Context context) {
        super(context);
    }

    public ControlScrollViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    public void setScanScroll(boolean isCanScroll){

        this.isCanScroll = isCanScroll;

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return  false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
