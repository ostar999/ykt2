package com.tencent.open.web.security;

import com.tencent.open.a;
import com.tencent.open.log.SLog;

/* loaded from: classes6.dex */
public class SecureJsInterface extends a.b {
    public static boolean isPWDEdit = false;

    /* renamed from: a, reason: collision with root package name */
    private String f20696a;

    public void clearAllEdit() {
        SLog.i("openSDK_LOG.SecureJsInterface", "-->clear all edit.");
        try {
            JniInterface.clearAllPWD();
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.SecureJsInterface", "-->clear all edit exception: " + e2.getMessage());
            throw new RuntimeException(e2);
        }
    }

    public void curPosFromJS(String str) throws NumberFormatException {
        int i2;
        SLog.d("openSDK_LOG.SecureJsInterface", "-->curPosFromJS: " + str);
        try {
            i2 = Integer.parseInt(str);
        } catch (NumberFormatException e2) {
            SLog.e("openSDK_LOG.SecureJsInterface", "-->curPosFromJS number format exception.", e2);
            i2 = -1;
        }
        if (i2 < 0) {
            throw new RuntimeException("position is illegal.");
        }
        boolean z2 = a.f20699c;
        boolean z3 = a.f20698b;
        if (z3) {
            if (Boolean.valueOf(JniInterface.BackSpaceChar(z3, i2)).booleanValue()) {
                a.f20698b = false;
                return;
            }
            return;
        }
        String str2 = a.f20697a;
        this.f20696a = str2;
        JniInterface.insetTextToArray(i2, str2, str2.length());
        SLog.v("openSDK_LOG.SecureJsInterface", "curPosFromJS mKey: " + this.f20696a);
    }

    @Override // com.tencent.open.a.b
    public boolean customCallback() {
        return true;
    }

    public String getMD5FromNative() {
        SLog.i("openSDK_LOG.SecureJsInterface", "-->get md5 form native");
        try {
            String pWDKeyToMD5 = JniInterface.getPWDKeyToMD5(null);
            SLog.v("openSDK_LOG.SecureJsInterface", "-->getMD5FromNative, MD5= " + pWDKeyToMD5);
            return pWDKeyToMD5;
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.SecureJsInterface", "-->get md5 form native exception: " + e2.getMessage());
            throw new RuntimeException(e2);
        }
    }

    public void isPasswordEdit(String str) throws NumberFormatException {
        int i2;
        SLog.i("openSDK_LOG.SecureJsInterface", "-->is pswd edit, flag: " + str);
        try {
            i2 = Integer.parseInt(str);
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.SecureJsInterface", "-->is pswd edit exception: " + e2.getMessage());
            i2 = -1;
        }
        if (i2 != 0 && i2 != 1) {
            throw new RuntimeException("is pswd edit flag is illegal.");
        }
        if (i2 == 0) {
            isPWDEdit = false;
        } else if (i2 == 1) {
            isPWDEdit = true;
        }
    }
}
