package com.sunsun.annotations;

import android.app.Application;

import com.sunsun.mylibrary.CoreManager;

/**
 * Created by Administrator on 2015/11/18.
 */
public class AnnotationsApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CoreManager.init(this);
    }
}
