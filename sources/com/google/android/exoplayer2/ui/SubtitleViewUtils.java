package com.google.android.exoplayer2.ui;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.span.LanguageFeatureSpan;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Predicate;

/* loaded from: classes3.dex */
final class SubtitleViewUtils {
    private SubtitleViewUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeAllEmbeddedStyling$0(Object obj) {
        return !(obj instanceof LanguageFeatureSpan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeEmbeddedFontSizes$1(Object obj) {
        return (obj instanceof AbsoluteSizeSpan) || (obj instanceof RelativeSizeSpan);
    }

    public static void removeAllEmbeddedStyling(Cue.Builder builder) {
        builder.clearWindowColor();
        if (builder.getText() instanceof Spanned) {
            if (!(builder.getText() instanceof Spannable)) {
                builder.setText(SpannableString.valueOf(builder.getText()));
            }
            removeSpansIf((Spannable) Assertions.checkNotNull(builder.getText()), new Predicate() { // from class: com.google.android.exoplayer2.ui.h0
                @Override // com.google.common.base.Predicate
                public final boolean apply(Object obj) {
                    return SubtitleViewUtils.lambda$removeAllEmbeddedStyling$0(obj);
                }
            });
        }
        removeEmbeddedFontSizes(builder);
    }

    public static void removeEmbeddedFontSizes(Cue.Builder builder) {
        builder.setTextSize(-3.4028235E38f, Integer.MIN_VALUE);
        if (builder.getText() instanceof Spanned) {
            if (!(builder.getText() instanceof Spannable)) {
                builder.setText(SpannableString.valueOf(builder.getText()));
            }
            removeSpansIf((Spannable) Assertions.checkNotNull(builder.getText()), new Predicate() { // from class: com.google.android.exoplayer2.ui.g0
                @Override // com.google.common.base.Predicate
                public final boolean apply(Object obj) {
                    return SubtitleViewUtils.lambda$removeEmbeddedFontSizes$1(obj);
                }
            });
        }
    }

    private static void removeSpansIf(Spannable spannable, Predicate<Object> predicate) {
        for (Object obj : spannable.getSpans(0, spannable.length(), Object.class)) {
            if (predicate.apply(obj)) {
                spannable.removeSpan(obj);
            }
        }
    }

    public static float resolveTextSize(int i2, float f2, int i3, int i4) {
        float f3;
        if (f2 == -3.4028235E38f) {
            return -3.4028235E38f;
        }
        if (i2 == 0) {
            f3 = i4;
        } else {
            if (i2 != 1) {
                if (i2 != 2) {
                    return -3.4028235E38f;
                }
                return f2;
            }
            f3 = i3;
        }
        return f2 * f3;
    }
}
