package com.sunsun.util;

import android.util.Log;

public class MLog {

    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;

    public static void verbose(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void verbose(String tag, String msg,Throwable e) {
        Log.v(tag, msg,e);
    }

    public static void debug(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void debug(String tag, String msg,Throwable e) {
        Log.d(tag, msg,e);
    }

    public static void info(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void info(String tag, String msg,Throwable e) {
        Log.i(tag, msg,e);
    }

    public static void warn(String tag, String msg) {
        Log.w(tag,msg);
    }

    public static void warn(String tag, String msg,Throwable e) {
        Log.w(tag,msg,e);
    }

    public static void error(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void error(String tag, String msg,Throwable e) {
        Log.e(tag,msg,e);
    }

}
