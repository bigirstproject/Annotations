package com.sunsun.mylibrary;

import com.sunsun.util.MLog;

import java.util.HashMap;

/**
 * Created by sunsun on 15/11/17.
 */
public class CoreFactory {

    private static final HashMap<Class<? extends IBaseCore>,IBaseCore> cores;
    private static final HashMap<Class<? extends IBaseCore>,Class<? extends AbstractBaseCore>> coreClasses;

    static {
        /**
         * Class<? extends IBaseCore>：继承IBaseCore的接口，非实例（对象）；IBaseCore：实现IBaseCore接口的实例类（或对象）。
         */
        cores =new HashMap<Class<? extends IBaseCore>,IBaseCore>();
        /**
         * Class<? extends IBaseCore>：继承IBaseCore的接口，非实例（对象）；Class<? extends AbstractBaseCore>：继承AbstractBaseCore的类，Class<? extends IBaseCore>中的方法都已实现。
         */
        coreClasses =new HashMap<Class<? extends IBaseCore>,Class<? extends AbstractBaseCore>>();
    }

    /**
     * 从工厂获取实现cls接口的对象实例
     * 该实例是使用registerCoreClass注册的实现类的对象
     *
     * @param cls 必须是core接口类，不能是core实现类，否则会抛出异常
     * @return 如果生成对象失败，返回null
     */
    public static <T extends IBaseCore> T getCore(Class<T> cls) {

        if (cls == null) {
            return null;
        }
        try {
            IBaseCore core = cores.get(cls);
            if (core == null) {
                Class<? extends AbstractBaseCore> implClass = coreClasses.get(cls);
                if (implClass == null) {
                    if (cls.isInterface()) {
                        MLog.error("CoreFactory", "No registered core class for: " + cls.getName());
                        throw new IllegalArgumentException("No registered core class for: " + cls.getName());
                    } else {
                        MLog.error("CoreFactory", "Not interface core class for: " + cls.getName());
                        throw new IllegalArgumentException("Not interface core class for: " + cls.getName());
                    }
                } else {
                    core = implClass.newInstance();
                }

                if (core != null) {
                    cores.put(cls, core);
                    //MLog.debug("CoreFactory", cls.getName() + " created: "
                    //        + ((implClass != null) ? implClass.getName() : cls.getName()));
                }
            }
            return (T)core;
        } catch (Throwable e) {
            MLog.error("CoreFactory", "getCore() failed for: " + cls.getName(), e);
        }
        return null;
    }

    /**
     * 注册某个接口实现类
     * @param coreInterface
     * @param coreClass
     */
    public static void registerCoreClass(Class<? extends IBaseCore> coreInterface, Class<? extends AbstractBaseCore> coreClass) {

        if (coreInterface == null || coreClass == null) {
            return;
        }

        coreClasses.put(coreInterface, coreClass);
        //MLog.debug("CoreFactory", "registered class " + coreClass.getName() + " for core: " + coreInterface.getName());
    }

    /**
     * 返回某个接口是否有注册实现类
     * @param coreInterface
     * @return
     */
    protected static boolean hasRegisteredCoreClass(Class<? extends IBaseCore> coreInterface) {
        if (coreInterface == null) {
            return false;
        } else {
            return coreClasses.containsKey(coreInterface);
        }
    }

}
