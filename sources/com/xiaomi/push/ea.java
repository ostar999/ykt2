package com.xiaomi.push;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class ea {

    /* renamed from: a, reason: collision with root package name */
    private static volatile ea f24751a;

    /* renamed from: a, reason: collision with other field name */
    private Context f338a;

    private ea(Context context) {
        this.f338a = context;
    }

    private int a(int i2) {
        return Math.max(60, i2);
    }

    public static ea a(Context context) {
        if (f24751a == null) {
            synchronized (ea.class) {
                if (f24751a == null) {
                    f24751a = new ea(context);
                }
            }
        }
        return f24751a;
    }

    private boolean a() {
        try {
            Context applicationContext = this.f338a;
            if (!(applicationContext instanceof Application)) {
                applicationContext = applicationContext.getApplicationContext();
            }
            ((Application) applicationContext).registerActivityLifecycleCallbacks(new dr(this.f338a, String.valueOf(System.currentTimeMillis() / 1000)));
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        ai aiVarA = ai.a(this.f338a);
        com.xiaomi.push.service.ao aoVarA = com.xiaomi.push.service.ao.a(this.f338a);
        SharedPreferences sharedPreferences = this.f338a.getSharedPreferences("mipush_extra", 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = sharedPreferences.getLong("first_try_ts", jCurrentTimeMillis);
        if (j2 == jCurrentTimeMillis) {
            sharedPreferences.edit().putLong("first_try_ts", jCurrentTimeMillis).commit();
        }
        if (Math.abs(jCurrentTimeMillis - j2) < 172800000) {
            return;
        }
        boolean zA = aoVarA.a(ic.ScreenSizeCollectionSwitch.a(), true);
        boolean zA2 = aoVarA.a(ic.AndroidVnCollectionSwitch.a(), true);
        boolean zA3 = aoVarA.a(ic.AndroidVcCollectionSwitch.a(), true);
        boolean zA4 = aoVarA.a(ic.AndroidIdCollectionSwitch.a(), true);
        boolean zA5 = aoVarA.a(ic.OperatorSwitch.a(), true);
        if (zA || zA2 || zA3 || zA4 || zA5) {
            int iA = a(aoVarA.a(ic.DeviceInfoCollectionFrequency.a(), 1209600));
            aiVarA.a(new ek(this.f338a, iA, zA, zA2, zA3, zA4, zA5), iA, 30);
        }
        boolean zA6 = aoVarA.a(ic.MacCollectionSwitch.a(), true);
        boolean zA7 = aoVarA.a(ic.IMSICollectionSwitch.a(), true);
        boolean zA8 = aoVarA.a(ic.IccidCollectionSwitch.a(), true);
        boolean zA9 = aoVarA.a(ic.DeviceIdSwitch.a(), true);
        if (zA6 || zA7 || zA8 || zA9) {
            int iA2 = a(aoVarA.a(ic.DeviceBaseInfoCollectionFrequency.a(), 1209600));
            aiVarA.a(new ej(this.f338a, iA2, zA6, zA7, zA8, zA9), iA2, 30);
        }
        if (aoVarA.a(ic.AppInstallListCollectionSwitch.a(), true)) {
            int iA3 = a(aoVarA.a(ic.AppInstallListCollectionFrequency.a(), 86400));
            aiVarA.a(new ee(this.f338a, iA3), iA3, 30);
        }
        if (aoVarA.a(ic.StorageCollectionSwitch.a(), true)) {
            int iA4 = a(aoVarA.a(ic.StorageCollectionFrequency.a(), 86400));
            aiVarA.a(new el(this.f338a, iA4), iA4, 30);
        }
        if (aoVarA.a(ic.BluetoothCollectionSwitch.a(), true)) {
            int iA5 = a(aoVarA.a(ic.BluetoothCollectionFrequency.a(), R2.drawable.icon_note));
            aiVarA.a(new eg(this.f338a, iA5), iA5, 30);
        }
        if (aoVarA.a(ic.AccountCollectionSwitch.a(), true)) {
            int iA6 = a(aoVarA.a(ic.AccountCollectionFrequency.a(), 604800));
            aiVarA.a(new ec(this.f338a, iA6), iA6, 30);
        }
        if (aoVarA.a(ic.WifiCollectionSwitch.a(), true)) {
            int iA7 = a(aoVarA.a(ic.WifiCollectionFrequency.a(), 900));
            aiVarA.a(new eo(this.f338a, iA7), iA7, 30);
        }
        if (aoVarA.a(ic.TopAppCollectionSwitch.a(), true)) {
            int iA8 = a(aoVarA.a(ic.TopAppCollectionFrequency.a(), 300));
            aiVarA.a(new em(this.f338a, iA8), iA8, 30);
        }
        if (aoVarA.a(ic.BroadcastActionCollectionSwitch.a(), true)) {
            int iA9 = a(aoVarA.a(ic.BroadcastActionCollectionFrequency.a(), 900));
            aiVarA.a(new eh(this.f338a, iA9), iA9, 30);
        }
        if (aoVarA.a(ic.WifiDevicesMacCollectionSwitch.a(), false)) {
            int iA10 = a(aoVarA.a(ic.WifiDevicesMacCollectionFrequency.a(), 900));
            aiVarA.a(new eq(this.f338a, iA10), iA10, 30);
        }
        if (aoVarA.a(ic.ActivityTSSwitch.a(), false)) {
            a();
        }
        if (aoVarA.a(ic.UploadSwitch.a(), true)) {
            aiVarA.a(new en(this.f338a), a(aoVarA.a(ic.UploadFrequency.a(), 86400)), 60);
        }
        if (aoVarA.a(ic.BatteryCollectionSwitch.a(), false)) {
            int iA11 = a(aoVarA.a(ic.BatteryCollectionFrequency.a(), 3600));
            aiVarA.a(new ef(this.f338a, iA11), iA11, 30);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m335a() {
        ai.a(this.f338a).a(new eb(this), 30);
    }
}
