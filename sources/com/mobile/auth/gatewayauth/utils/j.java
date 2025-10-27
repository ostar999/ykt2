package com.mobile.auth.gatewayauth.utils;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private static Pattern f10310a = Pattern.compile("^[-\\+]?[\\d]*$");

    public static String a(long j2) {
        try {
            try {
                return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss.SSS").format(new Date(j2));
            } catch (Exception unused) {
                return String.valueOf(j2);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
