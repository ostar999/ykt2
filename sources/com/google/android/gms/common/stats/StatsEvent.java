package com.google.android.gms.common.stats;

import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class StatsEvent extends AbstractSafeParcelable implements ReflectedParcelable {

    @KeepForSdk
    public interface Types {

        @KeepForSdk
        public static final int EVENT_TYPE_ACQUIRE_WAKE_LOCK = 7;

        @KeepForSdk
        public static final int EVENT_TYPE_RELEASE_WAKE_LOCK = 8;
    }

    public abstract int getEventType();

    public abstract long getTimeMillis();

    public String toString() {
        long timeMillis = getTimeMillis();
        int eventType = getEventType();
        long jZzu = zzu();
        String strZzv = zzv();
        StringBuilder sb = new StringBuilder(String.valueOf(strZzv).length() + 53);
        sb.append(timeMillis);
        sb.append(StrPool.TAB);
        sb.append(eventType);
        sb.append(StrPool.TAB);
        sb.append(jZzu);
        sb.append(strZzv);
        return sb.toString();
    }

    public abstract long zzu();

    public abstract String zzv();
}
