package com.google.firebase.appindexing.builders;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public final class AlarmBuilder extends IndexableBuilder<AlarmBuilder> {
    public static final String FRIDAY = "Friday";
    public static final String MONDAY = "Monday";
    public static final String SATURDAY = "Saturday";
    public static final String SUNDAY = "Sunday";
    public static final String THURSDAY = "Thursday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";

    public AlarmBuilder() {
        super("Alarm");
    }

    public final AlarmBuilder setAlarmInstances(AlarmInstanceBuilder... alarmInstanceBuilderArr) {
        return put("alarmInstances", alarmInstanceBuilderArr);
    }

    public final AlarmBuilder setDayOfWeek(@NonNull String... strArr) {
        for (String str : strArr) {
            if (!SUNDAY.equals(str) && !MONDAY.equals(str) && !TUESDAY.equals(str) && !WEDNESDAY.equals(str) && !THURSDAY.equals(str) && !FRIDAY.equals(str) && !SATURDAY.equals(str)) {
                String strValueOf = String.valueOf(str);
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "Invalid weekday ".concat(strValueOf) : new String("Invalid weekday "));
            }
        }
        return put("dayOfWeek", strArr);
    }

    public final AlarmBuilder setEnabled(boolean z2) {
        return put("enabled", z2);
    }

    public final AlarmBuilder setHour(int i2) {
        if (i2 < 0 || i2 > 23) {
            throw new IllegalArgumentException("Invalid alarm hour");
        }
        return put("hour", i2);
    }

    public final AlarmBuilder setIdentifier(String str) {
        return put("identifier", str);
    }

    public final AlarmBuilder setMessage(String str) {
        return put("message", str);
    }

    public final AlarmBuilder setMinute(int i2) {
        if (i2 < 0 || i2 > 59) {
            throw new IllegalArgumentException("Invalid alarm minute");
        }
        return put("minute", i2);
    }

    public final AlarmBuilder setRingtone(String str) {
        return put("ringtone", str);
    }

    public final AlarmBuilder setVibrate(boolean z2) {
        return put("vibrate", z2);
    }
}
