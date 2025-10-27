package com.huawei.hms.hatool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.UserManager;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;

/* loaded from: classes4.dex */
public class w0 {

    /* renamed from: c, reason: collision with root package name */
    public static w0 f7874c = new w0();

    /* renamed from: a, reason: collision with root package name */
    public boolean f7875a = false;

    /* renamed from: b, reason: collision with root package name */
    public Context f7876b = b.i();

    public static w0 b() {
        return f7874c;
    }

    @TargetApi(24)
    public boolean a() {
        boolean zIsUserUnlocked;
        if (!this.f7875a) {
            Context context = this.f7876b;
            if (context == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                UserManager userManager = (UserManager) context.getSystemService(PLVLinkMicManager.USER);
                if (userManager != null) {
                    zIsUserUnlocked = userManager.isUserUnlocked();
                } else {
                    this.f7875a = false;
                }
            } else {
                zIsUserUnlocked = true;
            }
            this.f7875a = zIsUserUnlocked;
        }
        return this.f7875a;
    }
}
