package com.plv.foundationsdk.log.elog.logcode;

import androidx.annotation.IntRange;

/* loaded from: classes4.dex */
public interface PLVElogCodeModuleDes {
    String createEventName();

    String createModuleName();

    @IntRange(from = 0, to = 99)
    int firstTag();

    int moduleCode();
}
