package com.necer.listener;

import com.necer.enumeration.DateChangeBehavior;
import java.time.LocalDate;
import java.util.List;

/* loaded from: classes4.dex */
public interface OnCalendarMultipleChangedListener {
    void onCalendarChange(int i2, int i3, List<LocalDate> list, List<LocalDate> list2, DateChangeBehavior dateChangeBehavior);
}
