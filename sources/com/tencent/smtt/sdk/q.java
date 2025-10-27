package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Bundle;
import com.tencent.smtt.export.external.DexLoader;

/* loaded from: classes6.dex */
class q {

    /* renamed from: a, reason: collision with root package name */
    private DexLoader f21262a;

    /* renamed from: b, reason: collision with root package name */
    private Object f21263b = null;

    public q(DexLoader dexLoader) {
        this.f21262a = dexLoader;
    }

    public Object a(Context context, Object obj, Bundle bundle) {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader != null) {
            this.f21263b = dexLoader.newInstance("com.tencent.tbs.cache.TbsVideoCacheTaskProxy", new Class[]{Context.class, Object.class, Bundle.class}, context, obj, bundle);
        }
        return this.f21263b;
    }

    public void a() {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader != null) {
            dexLoader.invokeMethod(this.f21263b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "pauseTask", new Class[0], new Object[0]);
        }
    }

    public void a(boolean z2) {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader != null) {
            dexLoader.invokeMethod(this.f21263b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "removeTask", new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public void b() {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader != null) {
            dexLoader.invokeMethod(this.f21263b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "resumeTask", new Class[0], new Object[0]);
        }
    }

    public void c() {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader != null) {
            dexLoader.invokeMethod(this.f21263b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "stopTask", new Class[0], new Object[0]);
        }
    }

    public long d() {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader == null) {
            return 0L;
        }
        Object objInvokeMethod = dexLoader.invokeMethod(this.f21263b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "getContentLength", new Class[0], new Object[0]);
        if (objInvokeMethod instanceof Long) {
            return ((Long) objInvokeMethod).longValue();
        }
        return 0L;
    }

    public int e() {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader != null) {
            Object objInvokeMethod = dexLoader.invokeMethod(this.f21263b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "getDownloadedSize", new Class[0], new Object[0]);
            if (objInvokeMethod instanceof Integer) {
                return ((Integer) objInvokeMethod).intValue();
            }
        }
        return 0;
    }

    public int f() {
        DexLoader dexLoader = this.f21262a;
        if (dexLoader != null) {
            Object objInvokeMethod = dexLoader.invokeMethod(this.f21263b, "com.tencent.tbs.cache.TbsVideoCacheTaskProxy", "getProgress", new Class[0], new Object[0]);
            if (objInvokeMethod instanceof Integer) {
                return ((Integer) objInvokeMethod).intValue();
            }
        }
        return 0;
    }
}
