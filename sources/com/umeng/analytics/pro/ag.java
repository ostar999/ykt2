package com.umeng.analytics.pro;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/* loaded from: classes6.dex */
class ag implements z {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22432a = "content://cn.nubia.provider.deviceid.dataid/oaid";

    @Override // com.umeng.analytics.pro.z
    public String a(Context context) {
        if (context == null) {
            return null;
        }
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse(f22432a), null, null, null, null);
        if (cursorQuery != null) {
            string = cursorQuery.moveToNext() ? cursorQuery.getString(cursorQuery.getColumnIndex("device_ids_grndid")) : null;
            cursorQuery.close();
        }
        return string;
    }
}
