package com.sunsun.ui.handler;

import android.os.Handler;
import android.os.Looper;

import com.sunsun.mylibrary.AbstractBaseCore;
import com.sunsun.mylibrary.CoreManager;
import com.sunsun.mylibrary.ICoreClient;

/**
 * Created by sunsun on 2015/11/18.
 */
public class HandlerCoreImpl extends AbstractBaseCore implements IHandlerCore {



    protected final Handler mHandler = new SafeDispatchHandler(Looper.getMainLooper());

    protected static class HandlerCoreRunnable implements Runnable{
        Class<? extends ICoreClient> mClientClass;
        String mMethodName;
        Object[] mArgs;


        protected HandlerCoreRunnable(final Class<? extends ICoreClient> clientClass,
                                      final String methodName,
                                      final Object... args){
            mClientClass = clientClass;
            mMethodName = methodName;
            mArgs = args;
        }

        @Override
        public void run() {
            CoreManager.notifyClientsCoreEvents(mClientClass, mMethodName, mArgs);
        }
    }

    @Override
    public void notifyClientsInMainThread(Class<? extends ICoreClient> clientClass, String methodName, Object... args) {
        mHandler.post(new HandlerCoreRunnable(clientClass, methodName, args));
    }
}
