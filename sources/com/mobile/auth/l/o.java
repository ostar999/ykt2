package com.mobile.auth.l;

import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes4.dex */
public class o {
    public static String a() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
    }
}
