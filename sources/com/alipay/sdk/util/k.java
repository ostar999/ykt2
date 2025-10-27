package com.alipay.sdk.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.sdk.app.EnvUtils;

/* loaded from: classes2.dex */
public final class k {

    /* renamed from: a, reason: collision with root package name */
    private static final String f3386a = "content://com.alipay.android.app.settings.data.ServerProvider/current_server";

    public static String a(Context context) {
        if (EnvUtils.isSandBox()) {
            return com.alipay.sdk.cons.a.f3196b;
        }
        if (context == null) {
            return com.alipay.sdk.cons.a.f3195a;
        }
        String str = com.alipay.sdk.cons.a.f3195a;
        return TextUtils.isEmpty(str) ? com.alipay.sdk.cons.a.f3195a : str;
    }

    private static String b(Context context) {
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(f3386a), null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            string = cursorQuery.moveToFirst() ? cursorQuery.getString(cursorQuery.getColumnIndex("url")) : null;
            cursorQuery.close();
        }
        return string;
    }
}
