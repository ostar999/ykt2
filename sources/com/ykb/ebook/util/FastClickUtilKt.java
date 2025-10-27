package com.ykb.ebook.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u001a\u0006\u0010\b\u001a\u00020\t\u001a\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000\"\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"MIN_DELAY_TIME", "", "MIN_DELAY_TIME_ACTIVITY", "TAG", "", "sLastActivitySimpleName", "sLastClickTime", "", "isFastClick", "", "isFastClickActivity", "activitySimpleName", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class FastClickUtilKt {
    private static final int MIN_DELAY_TIME = 500;
    private static final int MIN_DELAY_TIME_ACTIVITY = 800;

    @NotNull
    private static final String TAG = "FastClickUtil";

    @Nullable
    private static String sLastActivitySimpleName;
    private static long sLastClickTime;

    public static final boolean isFastClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z2 = jCurrentTimeMillis - sLastClickTime <= ((long) MIN_DELAY_TIME);
        android.util.Log.e(TAG, "log_common_FastClickUtil : " + (jCurrentTimeMillis - sLastClickTime));
        sLastClickTime = jCurrentTimeMillis;
        return z2;
    }

    public static final boolean isFastClickActivity(@NotNull String activitySimpleName) {
        Intrinsics.checkNotNullParameter(activitySimpleName, "activitySimpleName");
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z2 = jCurrentTimeMillis - sLastClickTime <= ((long) MIN_DELAY_TIME_ACTIVITY);
        sLastClickTime = jCurrentTimeMillis;
        if (Intrinsics.areEqual(activitySimpleName, sLastActivitySimpleName)) {
            return z2;
        }
        sLastActivitySimpleName = activitySimpleName;
        return false;
    }
}
