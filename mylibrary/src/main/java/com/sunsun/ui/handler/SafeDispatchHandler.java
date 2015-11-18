package com.sunsun.ui.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sunsun.util.MLog;

/**
 * Created by sunsun on 2015/11/18.
 */
public class SafeDispatchHandler extends Handler {

    private static final String TAG = SafeDispatchHandler.class.getSimpleName();

    public SafeDispatchHandler(Looper looper) {
        super(looper);
    }

    public SafeDispatchHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public SafeDispatchHandler() {
        super();
    }

    public SafeDispatchHandler(Callback callback) {
        super(callback);
    }

    @Override
    public void dispatchMessage(Message msg) {
        try {
            super.dispatchMessage(msg);
        } catch (Exception e) {
            MLog.error(TAG, e.getMessage(), e);
        } catch (Error error){
            MLog.error(TAG, error.getMessage(), error);
        }
    }

}
