package com.google.android.exoplayer2.text.span;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class TextEmphasisSpan implements LanguageFeatureSpan {
    public static final int MARK_FILL_FILLED = 1;
    public static final int MARK_FILL_OPEN = 2;
    public static final int MARK_FILL_UNKNOWN = 0;
    public static final int MARK_SHAPE_CIRCLE = 1;
    public static final int MARK_SHAPE_DOT = 2;
    public static final int MARK_SHAPE_NONE = 0;
    public static final int MARK_SHAPE_SESAME = 3;
    public int markFill;
    public int markShape;
    public final int position;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface MarkFill {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface MarkShape {
    }

    public TextEmphasisSpan(int i2, int i3, int i4) {
        this.markShape = i2;
        this.markFill = i3;
        this.position = i4;
    }
}
