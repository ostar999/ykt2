package com.beizi.fusion.sm.a.a;

import android.annotation.SuppressLint;
import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: classes2.dex */
class k implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5300a;

    public k(Context context) {
        this.f5300a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    @SuppressLint({"AnnotateVersionCheck"})
    public boolean a() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) throws RemoteException {
        if (this.f5300a == null || cVar == null) {
            return;
        }
        if (!a()) {
            com.beizi.fusion.sm.a.f.a("Only supports Android 10.0 and above for Nubia");
            cVar.a(new com.beizi.fusion.sm.a.e("Only supports Android 10.0 and above for Nubia"));
            return;
        }
        try {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = this.f5300a.getContentResolver().acquireContentProviderClient(Uri.parse("content://cn.nubia.identity/identity"));
            if (contentProviderClientAcquireContentProviderClient == null) {
                return;
            }
            Bundle bundleCall = contentProviderClientAcquireContentProviderClient.call("getOAID", null, null);
            if (Build.VERSION.SDK_INT >= 24) {
                contentProviderClientAcquireContentProviderClient.close();
            } else {
                contentProviderClientAcquireContentProviderClient.release();
            }
            if (bundleCall == null) {
                throw new com.beizi.fusion.sm.a.e("OAID query failed: bundle is null");
            }
            String string = bundleCall.getInt("code", -1) == 0 ? bundleCall.getString("id") : null;
            if (string == null || string.length() == 0) {
                throw new com.beizi.fusion.sm.a.e("OAID query failed: " + bundleCall.getString("message"));
            }
            com.beizi.fusion.sm.a.f.a("OAID query success: " + string);
            cVar.a(string);
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            cVar.a(e2);
        }
    }
}
