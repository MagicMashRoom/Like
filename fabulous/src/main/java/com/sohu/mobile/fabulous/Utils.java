package com.sohu.mobile.fabulous;

/**
 * Created by dafengluo on 2017/12/21.
 */

public class Utils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        if (0 < timeD && timeD < 300) {
            return true;
        }
        return false;
    }
}
