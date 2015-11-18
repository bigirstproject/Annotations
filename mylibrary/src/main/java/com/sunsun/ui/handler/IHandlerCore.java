package com.sunsun.ui.handler;

import com.sunsun.mylibrary.IBaseCore;
import com.sunsun.mylibrary.ICoreClient;

/**
 * Created by Administrator on 2015/11/18.
 */
public interface IHandlerCore  extends IBaseCore{


    /**
     * 在主线程里发送广播
     * @param clientClass
     * @param methodName
     * @param args
     */
    public void notifyClientsInMainThread(Class<? extends ICoreClient> clientClass, String methodName, Object... args);

}
