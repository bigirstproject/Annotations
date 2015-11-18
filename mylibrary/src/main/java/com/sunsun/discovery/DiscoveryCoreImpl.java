package com.sunsun.discovery;

import android.os.Handler;

import com.sunsun.mylibrary.AbstractBaseCore;
import com.sunsun.mylibrary.CoreFactory;
import com.sunsun.mylibrary.CoreManager;
import com.sunsun.ui.handler.IHandlerCore;
import com.sunsun.util.MLog;

/**
 * Created by sunsun on 2015/11/18.
 */
public class DiscoveryCoreImpl extends AbstractBaseCore implements IDiscoveryCore {

    private static final String TAG = DiscoveryCoreImpl.class.getSimpleName();

    @Override
    public void getDiscoveryInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendDiscoveryInfoSuccess(1);
            }
        }, 1000);
    }

    @Override
    public void sendDiscoveryInfoSuccess(Object args) {
        long currentTime = System.currentTimeMillis();
        MLog.info(TAG, "dexian, sendDiscoveryInfoSuccess"+"   CurrentTime = " + currentTime);
        if (args != null) {
            CoreFactory.getCore(IHandlerCore.class).notifyClientsInMainThread(
                    IDiscoveryClient.class, "discoveryResult", args);
        }
    }

    @Override
    public void sendDiscoveryInfoError() {
        MLog.info(TAG, "dexian, sendDiscoveryInfoError");
        CoreFactory.getCore(IHandlerCore.class).notifyClientsInMainThread(
                IDiscoveryClient.class, "discoveryResult", 0);
    }

}
