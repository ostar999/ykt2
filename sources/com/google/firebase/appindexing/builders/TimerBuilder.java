package com.google.firebase.appindexing.builders;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.firebase.appindexing.internal.zzad;
import java.util.Calendar;

/* loaded from: classes4.dex */
public final class TimerBuilder extends IndexableBuilder<TimerBuilder> {
    public static final String EXPIRED = "Expired";
    public static final String MISSED = "Missed";
    public static final String PAUSED = "Paused";
    public static final String RESET = "Reset";
    public static final String STARTED = "Started";
    public static final String UNKNOWN = "Unknown";

    public TimerBuilder() {
        super("Timer");
    }

    public final TimerBuilder setExpireTime(Calendar calendar) {
        return put("expireTime", zzad.zza(calendar));
    }

    public final TimerBuilder setIdentifier(String str) {
        return put("identifier", str);
    }

    public final TimerBuilder setLength(long j2) {
        return put(SessionDescription.ATTR_LENGTH, j2);
    }

    public final TimerBuilder setMessage(String str) {
        return put("message", str);
    }

    public final TimerBuilder setRemainingTime(long j2) {
        return put("remainingTime", j2);
    }

    public final TimerBuilder setRingtone(String str) {
        return put("ringtone", str);
    }

    public final TimerBuilder setTimerStatus(String str) {
        if ("Started".equals(str) || "Paused".equals(str) || EXPIRED.equals(str) || "Missed".equals(str) || RESET.equals(str) || "Unknown".equals(str)) {
            return put("timerStatus", str);
        }
        String strValueOf = String.valueOf(str);
        throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid timer status ".concat(strValueOf) : new String("Invalid timer status "));
    }

    public final TimerBuilder setVibrate(boolean z2) {
        return put("vibrate", z2);
    }
}
