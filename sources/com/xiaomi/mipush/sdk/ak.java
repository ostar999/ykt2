package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.fk;
import com.xiaomi.push.ib;

/* loaded from: classes6.dex */
final class ak implements fk.a {
    @Override // com.xiaomi.push.fk.a
    public void a(Context context, ib ibVar) {
        MiTinyDataClient.upload(context, ibVar);
    }
}
