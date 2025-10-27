package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.config.FieldManager;
import com.ut.device.UTDevice;

/* loaded from: classes6.dex */
public class j extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23385a = "utdid";

    /* renamed from: b, reason: collision with root package name */
    private Context f23386b;

    public j(Context context) {
        super("utdid");
        this.f23386b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        try {
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.f23538u)) {
                return (String) UTDevice.class.getMethod("getUtdid", Context.class).invoke(null, this.f23386b);
            }
        } catch (Exception unused) {
        }
        return null;
    }
}
