package com.sunsun.mylibrary;

import android.app.Application;
import android.content.Context;

import com.sunsun.discovery.DiscoveryCoreImpl;
import com.sunsun.discovery.IDiscoveryCore;
import com.sunsun.ui.handler.HandlerCoreImpl;
import com.sunsun.ui.handler.IHandlerCore;
import com.sunsun.util.MLog;
import com.sunsun.util.TimeLog;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sunsun on 15/11/17.
 */
public class CoreManager {

    public static final String TAG = CoreManager.class.getSimpleName();
    public static final String TAG_EVENT = "CoreManager_Event";
    /**
     * Class<?>：指IxxxClient注解的标示类（又称接口），存放的是类（接口）非实例对象；Set<Object>：注解标示类实例对象（new或intent跳转得到的实例对象），是set集合，有可能有注解标示的好几个类。
     */
    private static Map<Class<?>, Set<Object>> coreEvents = new HashMap<Class<?>, Set<Object>>();
    /**
     * Object：注解标示类实例对象（new或intent跳转得到的实例对象），是set集合，有可能有注解标示的好几个类；Map<String,Method>：当前类含有所有注解方法的集合。
     */
    private static Map<Object, Map<String, Method>> coreEventMethods = new HashMap<Object, Map<String, Method>>();

    private static Context context;

    /**
     * 程序启动时需要先调用此接口（如在Application的onCreate）
     *
     * @param app
     */
    public static void init(Application app) {
        context = app;
        if (!CoreFactory.hasRegisteredCoreClass(IHandlerCore.class)) {
            CoreFactory.registerCoreClass(IHandlerCore.class, HandlerCoreImpl.class);
        }
        if (!CoreFactory.hasRegisteredCoreClass(IDiscoveryCore.class)) {
            CoreFactory.registerCoreClass(IDiscoveryCore.class, DiscoveryCoreImpl.class);
        }
    }

    public static Context getContext() {
        return context;
    }


    /**
     * TODO 移除该对象所有监听接口，支持CoreEvent
     *
     * @param client
     */
    public static void removeClient(Object client) {
        if (client == null) {
            return;
        }
        try {
            Collection<Set<Object>> c = coreEvents.values();
            for (Set<Object> events : c) {
                events.remove(client);
            }
            coreEventMethods.remove(client);
        } catch (Throwable throwable) {
            MLog.error("CoreManager", "removeClient error! " + throwable);
        }

        //MLog.verbose(TAG_EVENT, "client(" + client + ") removed from all");
    }

    /**
     * TODO 增加Client，支持CoreEvent注解
     *
     * @param client
     */
    public static void addClient(Object client) {
        TimeLog.startTime(TAG_EVENT, "addClient", true);
        if (client == null) {
            MLog.warn(TAG_EVENT, "Don't give me a null client");
            return;
        }
        Class<?> originalClass = client.getClass();
        if (originalClass == null) {
            MLog.warn(TAG_EVENT, "Client.getClass() is null");
            return;
        }
        Method[] methods = originalClass.getMethods();
        MLog.info(TAG_EVENT, "methods.length =" + methods.length);
        int count = 0;
        for (Method method : methods) {
           // MLog.info(TAG_EVENT, "count = " + count++ + "    method.getName() " + method.getName());
            CoreEvent event = method.getAnnotation(CoreEvent.class);
            if (event != null) {
                Class<?> clientClass = event.coreClientClass();
                MLog.verbose(TAG_EVENT, "Client =" + client + ", event=" + event + ",method=" + method.getName());
                if (clientClass != null) {
                    addCoreEvents(client, clientClass);
                    addCoreEventMethodsIfNeeded(client, clientClass, method);
                }
            }
        }
        TimeLog.stopTime(TAG_EVENT, "addClient", true);
    }


    private static void addCoreEvents(Object client, Class<?> clientClass) {
        Set<Object> clients = coreEvents.get(clientClass);
        if (clients == null) {
            MLog.verbose(TAG_EVENT, "Clients is null, create new set :" + clientClass);
            clients = new HashSet<Object>();
            coreEvents.put(clientClass, clients);
        }
        clients.add(client);
        MLog.verbose(TAG_EVENT, "Clients add client " + client + ",size=" + clients.size());
    }


    private static void addCoreEventMethodsIfNeeded(Object client, Class<?> clientClass, /*Class<?> originalClass*/Method m) {
        Map<String, Method> methods = coreEventMethods.get(client);
        if (methods == null) {
            MLog.verbose(TAG_EVENT, "Client " + client + ",Class " + clientClass + " methods null, create new one");
            methods = new HashMap<String, Method>();
            coreEventMethods.put(client, methods);
        }
        MLog.verbose(TAG_EVENT, "Client=" + client + ",Class=" + clientClass + ",put method=" + m.getName());
        methods.put(m.getName(), m);
    }


    /**
     * TODO 广播CoreEvent注解事件
     *
     * @param clientClass
     * @param methodName
     * @param args
     */
    public static void notifyClientsCoreEvents(Class<? extends ICoreClient> clientClass, String methodName, Object... args) {

        if (clientClass == null || methodName == null || methodName.length() == 0) {
            return;
        }
        TimeLog.startTime(TAG_EVENT,"notifyClientsCoreEvents",true);
        Set<Object> clients = coreEvents.get(clientClass);

        if (clients != null) {
            //MLog.verbose(TAG_EVENT, "Notify core events client size=" + clients.size() + ",clientClass=" + clientClass);
            // 每次均构造一个新的对象返回，防止遍历中修改出问题
            clients = new HashSet<Object>(clients);
            //MLog.verbose(TAG_EVENT, "Notify core events AFTER size=" + clients.size() + ",clientClass=" + clientClass);
        } else {
            return;
        }
        try {
            for (Object c : clients) {
                Map<String, Method> methods = coreEventMethods.get(c);
                if (methods == null) {
                    /*if (isDebugSvc())
                        MLog.verbose(TAG_EVENT, "Notify core events methods is null client="
                            + c + ",method=" + methodName + ",args=" + args);*/
                    continue;
                }
                Method method = methods.get(methodName);

                if (method == null) {
                    /*if (isDebugSvc())
                        MLog.verbose(TAG_EVENT, "Can't find " + c + " has method " + methodName +
                            " for args[" + args.length + "]: " + args.toString());*/
                    continue;
                } else if (method.getParameterTypes() == null) {
                    MLog.error(TAG_EVENT, "Can't find " + c + " has method param:" + method.getParameterTypes() +
                            " for args[" + args.length + "]: " + args.toString());
                    continue;
                } else if (method.getParameterTypes().length != args.length) {
                    MLog.error(TAG_EVENT, "Can't find " + c + " has Method " + methodName +
                            " param number not matched: method(" + method.getParameterTypes().length +
                            "), args(" + args.length + ")");
                    continue;
                }
                /*if (isDebugSvc())
                    MLog.verbose(TAG_EVENT, "Notify core event target=" + c + ",method=" + methodName);*/
                try {
                    method.invoke(c, args);
                } catch (Throwable e) {
                    MLog.error(TAG_EVENT, "Notify core events method invoke error class=" + clientClass
                            + ",method=" + methodName
                            + ",args=" + args, e);
                }
            }

        } catch (Throwable e) {
            MLog.error(TAG_EVENT, "Notify core events error class=" + clientClass + ",method=" + methodName
                    + ",args=" + args, e);
        }
        TimeLog.stopTime(TAG_EVENT, "notifyClientsCoreEvents", true);
    }

}
