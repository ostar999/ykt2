package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.commonsdk.config.FieldManager;

/* loaded from: classes6.dex */
public class h extends a {

    /* renamed from: a, reason: collision with root package name */
    public static final String f23379a = "umeng_sp_oaid";

    /* renamed from: b, reason: collision with root package name */
    public static final String f23380b = "key_umeng_sp_oaid";

    /* renamed from: c, reason: collision with root package name */
    public static final String f23381c = "key_umeng_sp_oaid_required_time";

    /* renamed from: d, reason: collision with root package name */
    private static final String f23382d = "oaid";

    /* renamed from: e, reason: collision with root package name */
    private Context f23383e;

    public h(Context context) {
        super(f23382d);
        this.f23383e = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        if (!FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = this.f23383e.getSharedPreferences(f23379a, 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getString(f23380b, "");
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
