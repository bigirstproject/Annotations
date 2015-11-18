package com.sunsun.mylibrary;

import android.content.Context;

/**
 * Created by sunsun on 15/11/17.
 */
public abstract class AbstractBaseCore implements IBaseCore {

    protected Context getContext() {
        return CoreManager.getContext();
    }

    protected void notifyClients(Class<? extends ICoreClient> clientClass, String methodName, Object... args) {
        CoreManager.notifyClientsCoreEvents(clientClass, methodName, args);
    }

}
