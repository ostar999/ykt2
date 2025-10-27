package com.google.android.material.timepicker;

import androidx.annotation.IntRange;
import androidx.annotation.StringRes;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
interface TimePickerControls {

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActiveSelection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClockPeriod {
    }

    void setActiveSelection(int i2);

    void setHandRotation(float f2);

    void setValues(String[] strArr, @StringRes int i2);

    void updateTime(int i2, int i3, @IntRange(from = 0) int i4);
}
