package com.sunsun.discovery;

import com.sunsun.mylibrary.IBaseCore;

/**
 * Created by sunsun on 2015/11/18.
 */
public interface IDiscoveryCore extends IBaseCore {

    public void getDiscoveryInfo();

    public void sendDiscoveryInfoSuccess(Object args);

    public void sendDiscoveryInfoError();

}
