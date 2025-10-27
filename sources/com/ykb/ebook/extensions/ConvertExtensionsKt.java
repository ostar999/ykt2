package com.ykb.ebook.extensions;

import android.content.res.Resources;
import android.util.TypedValue;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0000\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0004"}, d2 = {"dpToPx", "", "", "spToPx", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ConvertExtensionsKt {
    public static final int dpToPx(int i2) {
        return (int) dpToPx(i2);
    }

    public static final float spToPx(float f2) {
        return TypedValue.applyDimension(2, f2, Resources.getSystem().getDisplayMetrics());
    }

    public static final float dpToPx(float f2) {
        return TypedValue.applyDimension(1, f2, Resources.getSystem().getDisplayMetrics());
    }
}
