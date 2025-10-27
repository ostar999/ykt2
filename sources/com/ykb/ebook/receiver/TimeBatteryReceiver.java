package com.ykb.ebook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.alipay.sdk.authjs.a;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.umeng.analytics.pro.d;
import com.ykb.ebook.common_interface.TimeBatteryCallback;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/ykb/ebook/receiver/TimeBatteryReceiver;", "Landroid/content/BroadcastReceiver;", a.f3170c, "Lcom/ykb/ebook/common_interface/TimeBatteryCallback;", "(Lcom/ykb/ebook/common_interface/TimeBatteryCallback;)V", "filter", "Landroid/content/IntentFilter;", "getFilter", "()Landroid/content/IntentFilter;", "onReceive", "", d.R, "Landroid/content/Context;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class TimeBatteryReceiver extends BroadcastReceiver {

    @NotNull
    private final TimeBatteryCallback callback;

    @NotNull
    private final IntentFilter filter;

    public TimeBatteryReceiver(@NotNull TimeBatteryCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        this.filter = intentFilter;
    }

    @NotNull
    public final IntentFilter getFilter() {
        return this.filter;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(@NotNull Context context, @NotNull Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        String action = intent.getAction();
        if (action != null) {
            int iHashCode = action.hashCode();
            if (iHashCode != -1538406691) {
                if (iHashCode == -1513032534 && action.equals("android.intent.action.TIME_TICK")) {
                    this.callback.timeBack();
                    return;
                }
                return;
            }
            if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                this.callback.batterBack(intent.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, -1));
            }
        }
    }
}
