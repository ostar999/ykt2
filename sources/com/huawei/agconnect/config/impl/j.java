package com.huawei.agconnect.config.impl;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
class j implements d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f7315a;

    /* renamed from: b, reason: collision with root package name */
    private final String f7316b;

    public j(Context context, String str) {
        this.f7315a = context;
        this.f7316b = str;
    }

    private static String a(String str) {
        try {
            return "agc_" + Hex.encodeHexString(a(str.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            return "";
        }
    }

    private static byte[] a(byte[] bArr) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(bArr);
    }

    @Override // com.huawei.agconnect.config.impl.d
    public String a(String str, String str2) {
        int identifier;
        String strA = a(str);
        if (TextUtils.isEmpty(strA) || (identifier = this.f7315a.getResources().getIdentifier(strA, TypedValues.Custom.S_STRING, this.f7316b)) == 0) {
            return str2;
        }
        try {
            return this.f7315a.getResources().getString(identifier);
        } catch (Resources.NotFoundException unused) {
            return str2;
        }
    }
}
