package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.export.external.DexLoader;

/* loaded from: classes6.dex */
class s {

    /* renamed from: a, reason: collision with root package name */
    private DexLoader f21270a;

    public s(DexLoader dexLoader) {
        this.f21270a = dexLoader;
    }

    public String a(Context context) {
        Object objNewInstance;
        Object objInvokeMethod;
        DexLoader dexLoader = this.f21270a;
        return (dexLoader == null || (objNewInstance = dexLoader.newInstance("com.tencent.tbs.utils.TbsVideoUtilsProxy", new Class[0], new Object[0])) == null || (objInvokeMethod = this.f21270a.invokeMethod(objNewInstance, "com.tencent.tbs.utils.TbsVideoUtilsProxy", "getCurWDPDecodeType", new Class[]{Context.class}, context)) == null) ? "" : String.valueOf(objInvokeMethod);
    }

    public void a(Context context, String str) {
        Object objNewInstance;
        DexLoader dexLoader = this.f21270a;
        if (dexLoader == null || (objNewInstance = dexLoader.newInstance("com.tencent.tbs.utils.TbsVideoUtilsProxy", new Class[0], new Object[0])) == null) {
            return;
        }
        this.f21270a.invokeMethod(objNewInstance, "com.tencent.tbs.utils.TbsVideoUtilsProxy", "deleteVideoCache", new Class[]{Context.class, String.class}, context, str);
    }
}
