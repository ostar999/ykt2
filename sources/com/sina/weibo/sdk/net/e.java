package com.sina.weibo.sdk.net;

import android.os.Bundle;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class e implements d {

    /* renamed from: j, reason: collision with root package name */
    private String f17178j;

    /* renamed from: k, reason: collision with root package name */
    private Bundle f17179k = new Bundle();

    /* renamed from: l, reason: collision with root package name */
    private Bundle f17180l = new Bundle();

    /* renamed from: m, reason: collision with root package name */
    private Map<String, Object<File>> f17181m = new HashMap();

    /* renamed from: n, reason: collision with root package name */
    private Map<String, byte[]> f17182n = new HashMap();

    /* renamed from: o, reason: collision with root package name */
    private int f17183o;

    /* renamed from: p, reason: collision with root package name */
    private int f17184p;

    public static final class a {

        /* renamed from: j, reason: collision with root package name */
        public String f17185j;

        /* renamed from: k, reason: collision with root package name */
        Bundle f17186k = new Bundle();

        /* renamed from: l, reason: collision with root package name */
        Bundle f17187l = new Bundle();

        /* renamed from: m, reason: collision with root package name */
        Map<String, Object<File>> f17188m = new HashMap();

        /* renamed from: n, reason: collision with root package name */
        Map<String, byte[]> f17189n = new HashMap();

        /* renamed from: o, reason: collision with root package name */
        int f17190o = 30000;

        /* renamed from: p, reason: collision with root package name */
        int f17191p = 60000;

        public final a a(String str, Object obj) {
            a(this.f17186k, str, obj);
            return this;
        }

        public final a b(String str, Object obj) {
            a(this.f17187l, str, obj);
            return this;
        }

        public final e g() {
            return new e(this);
        }

        private void a(Bundle bundle, String str, Object obj) {
            if (obj != null) {
                if (obj instanceof String) {
                    bundle.putString(str, String.valueOf(obj));
                    return;
                }
                if (obj instanceof Integer) {
                    bundle.putInt(str, ((Integer) obj).intValue());
                    return;
                }
                if (obj instanceof Short) {
                    bundle.putShort(str, ((Short) obj).shortValue());
                    return;
                }
                if (obj instanceof Character) {
                    bundle.putChar(str, ((Character) obj).charValue());
                    return;
                }
                if (obj instanceof Byte) {
                    bundle.putByte(str, ((Byte) obj).byteValue());
                    return;
                }
                if (obj instanceof Long) {
                    bundle.putLong(str, ((Long) obj).longValue());
                    return;
                }
                if (obj instanceof Float) {
                    bundle.putFloat(str, ((Float) obj).floatValue());
                    return;
                }
                if (obj instanceof Double) {
                    bundle.putDouble(str, ((Double) obj).doubleValue());
                    return;
                }
                if (obj instanceof Boolean) {
                    bundle.putBoolean(str, ((Boolean) obj).booleanValue());
                } else if (obj instanceof byte[]) {
                    this.f17189n.put(str, (byte[]) obj);
                } else {
                    if (!(obj instanceof Serializable)) {
                        throw new IllegalArgumentException("Unsupported params type!");
                    }
                    bundle.putSerializable(str, (Serializable) obj);
                }
            }
        }
    }

    public e(a aVar) {
        this.f17178j = aVar.f17185j;
        this.f17179k.putAll(aVar.f17186k);
        this.f17180l.putAll(aVar.f17187l);
        this.f17181m.putAll(aVar.f17188m);
        this.f17182n.putAll(aVar.f17189n);
        this.f17183o = aVar.f17190o;
        this.f17184p = aVar.f17191p;
    }

    @Override // com.sina.weibo.sdk.net.d
    public final Bundle f() {
        return this.f17180l;
    }

    @Override // com.sina.weibo.sdk.net.d
    public final int getConnectTimeout() {
        return this.f17183o;
    }

    @Override // com.sina.weibo.sdk.net.d
    public final Bundle getParams() {
        return this.f17179k;
    }

    @Override // com.sina.weibo.sdk.net.d
    public final int getReadTimeout() {
        return this.f17184p;
    }

    @Override // com.sina.weibo.sdk.net.d
    public final String getUrl() {
        return this.f17178j;
    }
}
