package com.xiaomi.push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.xiaomi.push.fm;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
class fn implements fm.a {

    /* renamed from: a, reason: collision with other field name */
    protected Context f425a;

    /* renamed from: a, reason: collision with other field name */
    private PendingIntent f424a = null;

    /* renamed from: a, reason: collision with root package name */
    private volatile long f24854a = 0;

    public fn(Context context) {
        this.f425a = null;
        this.f425a = context;
    }

    private void a(AlarmManager alarmManager, long j2, PendingIntent pendingIntent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            AlarmManager.class.getMethod("setExact", Integer.TYPE, Long.TYPE, PendingIntent.class).invoke(alarmManager, 0, Long.valueOf(j2), pendingIntent);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    public long a() {
        return gi.b();
    }

    @Override // com.xiaomi.push.fm.a
    /* renamed from: a, reason: collision with other method in class */
    public void mo423a() {
        if (this.f424a != null) {
            try {
                ((AlarmManager) this.f425a.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(this.f424a);
            } catch (Exception unused) {
            } catch (Throwable th) {
                this.f424a = null;
                com.xiaomi.channel.commonutils.logger.b.c("unregister timer");
                this.f24854a = 0L;
                throw th;
            }
            this.f424a = null;
            com.xiaomi.channel.commonutils.logger.b.c("unregister timer");
            this.f24854a = 0L;
        }
        this.f24854a = 0L;
    }

    public void a(Intent intent, long j2) {
        AlarmManager alarmManager = (AlarmManager) this.f425a.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.f424a = PendingIntent.getBroadcast(this.f425a, 0, intent, 0);
        at.a(alarmManager, "setExactAndAllowWhileIdle", 0, Long.valueOf(j2), this.f424a);
        com.xiaomi.channel.commonutils.logger.b.c("register timer " + j2);
    }

    @Override // com.xiaomi.push.fm.a
    public void a(boolean z2) {
        long jA = a();
        if (z2 || this.f24854a != 0) {
            if (z2) {
                mo423a();
            }
            if (!z2 && this.f24854a != 0) {
                this.f24854a += jA;
                if (this.f24854a < System.currentTimeMillis()) {
                }
                Intent intent = new Intent(com.xiaomi.push.service.ax.f25625o);
                intent.setPackage(this.f425a.getPackageName());
                a(intent, this.f24854a);
            }
            jA -= SystemClock.elapsedRealtime() % jA;
            this.f24854a = System.currentTimeMillis() + jA;
            Intent intent2 = new Intent(com.xiaomi.push.service.ax.f25625o);
            intent2.setPackage(this.f425a.getPackageName());
            a(intent2, this.f24854a);
        }
    }

    @Override // com.xiaomi.push.fm.a
    /* renamed from: a */
    public boolean mo422a() {
        return this.f24854a != 0;
    }
}
