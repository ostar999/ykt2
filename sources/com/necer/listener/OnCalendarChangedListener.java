package com.necer.listener;

import com.necer.enumeration.DateChangeBehavior;
import java.time.LocalDate;

/* loaded from: classes4.dex */
public interface OnCalendarChangedListener {
    void onCalendarChange(int i2, int i3, LocalDate localDate, DateChangeBehavior dateChangeBehavior);
}
