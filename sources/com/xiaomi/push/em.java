package com.xiaomi.push;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.Calendar;
import java.util.List;

/* loaded from: classes6.dex */
public class em extends ei {

    /* renamed from: a, reason: collision with root package name */
    private SharedPreferences f24765a;

    public em(Context context, int i2) {
        super(context, i2);
        this.f24765a = context.getSharedPreferences("mipush_extra", 0);
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 9;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.TopApp;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        String packageName;
        List<UsageStats> listQueryUsageStats;
        try {
            UsageStatsManager usageStatsManager = (UsageStatsManager) ((ei) this).f339a.getSystemService("usagestats");
            Calendar calendar = Calendar.getInstance();
            calendar.add(5, -1);
            long timeInMillis = calendar.getTimeInMillis();
            calendar.add(5, 1);
            listQueryUsageStats = usageStatsManager.queryUsageStats(0, timeInMillis, calendar.getTimeInMillis());
        } catch (Throwable unused) {
            packageName = null;
        }
        if (ad.a(listQueryUsageStats)) {
            return null;
        }
        packageName = "";
        long j2 = 0;
        for (int i2 = 0; i2 < listQueryUsageStats.size(); i2++) {
            UsageStats usageStats = listQueryUsageStats.get(i2);
            if (usageStats.getLastTimeStamp() > j2) {
                long lastTimeStamp = usageStats.getLastTimeStamp();
                packageName = usageStats.getPackageName();
                j2 = lastTimeStamp;
            }
        }
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        if (TextUtils.equals(packageName, this.f24765a.getString("ltapn", null))) {
            return "^";
        }
        this.f24765a.edit().putString("ltapn", packageName).commit();
        return packageName;
    }
}
