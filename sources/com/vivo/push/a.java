package com.vivo.push;

import android.os.Bundle;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private Bundle f24226a;

    /* renamed from: b, reason: collision with root package name */
    private String f24227b;

    /* renamed from: c, reason: collision with root package name */
    private String f24228c;

    public a(String str, String str2, Bundle bundle) {
        this.f24227b = str;
        this.f24228c = str2;
        this.f24226a = bundle;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.vivo.push.a a(android.content.Intent r6) {
        /*
            java.lang.String r0 = "BundleWapper"
            r1 = 0
            if (r6 != 0) goto Lb
            java.lang.String r6 = "create error : intent is null"
            com.vivo.push.util.p.a(r0, r6)
            return r1
        Lb:
            android.os.Bundle r2 = r6.getExtras()
            if (r2 == 0) goto L1e
            java.lang.String r3 = "client_pkgname"
            java.lang.String r3 = r2.getString(r3)
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L1e
            goto L1f
        L1e:
            r3 = r1
        L1f:
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 == 0) goto L2a
            java.lang.String r4 = "create warning: pkgName is null"
            com.vivo.push.util.p.b(r0, r4)
        L2a:
            java.lang.String r4 = r6.getPackage()
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 == 0) goto L4f
            android.content.ComponentName r4 = r6.getComponent()
            if (r4 != 0) goto L3b
            goto L43
        L3b:
            android.content.ComponentName r6 = r6.getComponent()
            java.lang.String r1 = r6.getPackageName()
        L43:
            boolean r6 = android.text.TextUtils.isEmpty(r1)
            if (r6 == 0) goto L4e
            java.lang.String r6 = "create warning: targetPkgName is null"
            com.vivo.push.util.p.b(r0, r6)
        L4e:
            r4 = r1
        L4f:
            com.vivo.push.a r6 = new com.vivo.push.a
            r6.<init>(r3, r4, r2)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vivo.push.a.a(android.content.Intent):com.vivo.push.a");
    }

    public final int b(String str, int i2) {
        Bundle bundle = this.f24226a;
        return bundle == null ? i2 : bundle.getInt(str, i2);
    }

    public final ArrayList<String> c(String str) {
        Bundle bundle = this.f24226a;
        if (bundle == null) {
            return null;
        }
        return bundle.getStringArrayList(str);
    }

    public final Serializable d(String str) {
        Bundle bundle = this.f24226a;
        if (bundle == null) {
            return null;
        }
        return bundle.getSerializable(str);
    }

    public final boolean e(String str) {
        Bundle bundle = this.f24226a;
        if (bundle == null) {
            return false;
        }
        return bundle.getBoolean(str, false);
    }

    public final byte[] b(String str) {
        Bundle bundle = this.f24226a;
        if (bundle == null) {
            return null;
        }
        return bundle.getByteArray(str);
    }

    public final long b(String str, long j2) {
        Bundle bundle = this.f24226a;
        return bundle == null ? j2 : bundle.getLong(str, j2);
    }

    public final Bundle b() {
        return this.f24226a;
    }

    public final void a(String str, int i2) {
        if (this.f24226a == null) {
            this.f24226a = new Bundle();
        }
        this.f24226a.putInt(str, i2);
    }

    public final void a(String str, long j2) {
        if (this.f24226a == null) {
            this.f24226a = new Bundle();
        }
        this.f24226a.putLong(str, j2);
    }

    public final void a(String str, String str2) {
        if (this.f24226a == null) {
            this.f24226a = new Bundle();
        }
        this.f24226a.putString(str, str2);
    }

    public final void a(String str, byte[] bArr) {
        if (this.f24226a == null) {
            this.f24226a = new Bundle();
        }
        this.f24226a.putByteArray(str, bArr);
    }

    public final void a(String str, Serializable serializable) {
        if (this.f24226a == null) {
            this.f24226a = new Bundle();
        }
        this.f24226a.putSerializable(str, serializable);
    }

    public final void a(String str, boolean z2) {
        if (this.f24226a == null) {
            this.f24226a = new Bundle();
        }
        this.f24226a.putBoolean(str, z2);
    }

    public final void a(String str, ArrayList<String> arrayList) {
        if (this.f24226a == null) {
            this.f24226a = new Bundle();
        }
        this.f24226a.putStringArrayList(str, arrayList);
    }

    public final String a(String str) {
        Bundle bundle = this.f24226a;
        if (bundle == null) {
            return null;
        }
        return bundle.getString(str);
    }

    public final String a() {
        return this.f24227b;
    }
}
