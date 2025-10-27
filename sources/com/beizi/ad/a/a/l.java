package com.beizi.ad.a.a;

import java.util.Random;

/* loaded from: classes2.dex */
public class l {
    public static boolean a(int i2) {
        float fNextInt = new Random().nextInt(100);
        i.c("BeiZisAd", "ratio = " + fNextInt + ",ratioCheckNum = " + i2);
        return fNextInt < ((float) i2);
    }
}
