package com.tencent.smtt.sdk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.tencent.smtt.export.external.DexLoader;
import dalvik.system.DexClassLoader;

/* loaded from: classes6.dex */
class v {

    /* renamed from: a, reason: collision with root package name */
    protected DexLoader f21390a;

    public v(DexLoader dexLoader) {
        this.f21390a = dexLoader;
    }

    public Object a(Context context) {
        DexLoader dexLoader = this.f21390a;
        return dexLoader.newInstance("com.tencent.tbs.player.TbsPlayerProxy", new Class[]{Context.class, DexClassLoader.class}, context, dexLoader.getClassLoader());
    }

    public void a(Object obj) {
        this.f21390a.invokeMethod(obj, "com.tencent.tbs.player.TbsPlayerProxy", "onUserStateChanged", new Class[0], new Object[0]);
    }

    public void a(Object obj, Activity activity, int i2) {
        this.f21390a.invokeMethod(obj, "com.tencent.tbs.player.TbsPlayerProxy", "onActivity", new Class[]{Activity.class, Integer.TYPE}, activity, Integer.valueOf(i2));
    }

    public boolean a(Object obj, Bundle bundle, FrameLayout frameLayout, Object obj2) {
        DexLoader dexLoader = this.f21390a;
        Object objInvokeMethod = obj2 != null ? dexLoader.invokeMethod(obj, "com.tencent.tbs.player.TbsPlayerProxy", "play", new Class[]{Bundle.class, FrameLayout.class, Object.class}, bundle, frameLayout, obj2) : dexLoader.invokeMethod(obj, "com.tencent.tbs.player.TbsPlayerProxy", "play", new Class[]{Bundle.class, FrameLayout.class}, bundle, frameLayout);
        if (objInvokeMethod instanceof Boolean) {
            return ((Boolean) objInvokeMethod).booleanValue();
        }
        return false;
    }
}
