package com.beizi.fusion.e.a;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private Context f5016a;

    public f(Context context) {
        this.f5016a = context;
    }

    public String a() throws RemoteException {
        Uri uri = Uri.parse("content://cn.nubia.identity/identity");
        try {
            int i2 = Build.VERSION.SDK_INT;
            ContentProviderClient contentProviderClientAcquireContentProviderClient = this.f5016a.getContentResolver().acquireContentProviderClient(uri);
            Bundle bundleCall = contentProviderClientAcquireContentProviderClient.call("getOAID", null, null);
            if (i2 >= 24) {
                contentProviderClientAcquireContentProviderClient.close();
            } else {
                contentProviderClientAcquireContentProviderClient.release();
            }
            if (bundleCall.getInt("code", -1) == 0) {
                return bundleCall.getString("id");
            }
            bundleCall.getString("message");
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
