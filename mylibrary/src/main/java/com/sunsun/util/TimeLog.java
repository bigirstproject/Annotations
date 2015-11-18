package com.sunsun.util;

/**
 * Created by sunsun on 2015/11/18.
 */
public class TimeLog {

    private static long startTime = 0l;
    private static boolean hasStaticsTime = true;

    public static void startTime(String tag, String msg, boolean hasRecordTime) {
        if(!hasStaticsTime){
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (hasRecordTime) {
            startTime = currentTime;
        }
        MLog.info(tag, msg + " startTime = " + currentTime);
    }

    public static void stopTime(String tag, String msg, boolean hasTotal) {
        if(!hasStaticsTime){
            return;
        }
        long nowTime = System.currentTimeMillis();
        if (hasTotal) {
            MLog.info(tag, msg + " stopTime = " + nowTime + "    total time = " + (nowTime - startTime));
            startTime = 0l;
        } else {
            MLog.info(tag, msg + " stopTime = " + nowTime);
        }
    }

    public static void stopTime(String tag, String msg, long start) {
        if(!hasStaticsTime){
            return;
        }
        long nowTime = System.currentTimeMillis();
        MLog.info(tag, msg + " stopTime = " + nowTime + "    total time = " + (nowTime - start));
    }
}
