package com.makemoji.mojilib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.makemoji.mojilib.MojiInputLayout;

/**
 * Created by s_baa on 8/23/2016.
 */
public class MyMojiInputLayout extends MojiInputLayout{

    public MyMojiInputLayout(Context context) {
        super(context);
    }

    public MyMojiInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMojiInputLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void setRnUpdateListener(RNUpdateListener listener){
        super.setRnUpdateListener(listener);
    }



}
