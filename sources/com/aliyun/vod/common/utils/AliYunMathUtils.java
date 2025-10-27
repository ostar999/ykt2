package com.aliyun.vod.common.utils;

import android.util.Log;

/* loaded from: classes2.dex */
public class AliYunMathUtils {
    public static int convertFun(int i2) {
        if (fun(i2)) {
            return i2;
        }
        String binaryString = Integer.toBinaryString(i2);
        Log.d("Math", "the result is : " + binaryString);
        StringBuilder sb = new StringBuilder("1");
        sb.append(String.format("%0" + binaryString.length() + "d", 0));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("the fun is : ");
        sb2.append(sb.toString());
        Log.d("Math", sb2.toString());
        return Integer.parseInt(sb.toString(), 2);
    }

    public static boolean fun(int i2) {
        return i2 > 0 && (i2 & (i2 + (-1))) == 0;
    }
}
