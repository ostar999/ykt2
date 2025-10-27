package com.catchpig.mvvm.ext;

import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import com.catchpig.mvvm.utils.BetterHighlightSpan;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\r\n\u0002\b\b\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a6\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u001a\u001e\u0010\f\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a(\u0010\r\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a\u001e\u0010\u0010\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a\u0014\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u001a\u001e\u0010\u0012\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u001a\u0014\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u001a&\u0010\u0015\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a&\u0010\u0018\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a>\u0010\u0019\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u001a&\u0010\u001a\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a&\u0010\u001b\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a\u001c\u0010\u001c\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0017\u001a&\u0010\u001d\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u001a\u001c\u0010\u001e\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a\u001c\u0010 \u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a4\u0010!\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u001a\u001c\u0010\"\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u001a\u001c\u0010#\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u001a\u0012\u0010$\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u001c\u0010%\u001a\u00020\u001f*\u00020\u001f2\b\b\u0002\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0017\u001a\u0012\u0010&\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u0017¨\u0006'"}, d2 = {"appendBackgroundColorSpan", "Landroid/widget/TextView;", "str", "", "color", "", "appendClickSpan", "isUnderlineText", "", "clickAction", "Lkotlin/Function0;", "", "appendColorSpan", "appendSizeColorSpan", "scale", "", "appendSizeSpan", "appendStrikeThrougthSpan", "appendStyleSpan", TtmlNode.TAG_STYLE, "appendUnderlineSpan", "backgroundColorLineHeightSpan", SessionDescription.ATTR_RANGE, "Lkotlin/ranges/IntRange;", "backgroundColorSpan", "clickSpan", "colorSpan", "sizeSpan", "strikeThrougthSpan", "styleSpan", "toBackgroundColorLineHeightSpan", "", "toBackgroundColorSpan", "toClickSpan", "toColorSpan", "toSizeSpan", "toStrikeThrougthSpan", "toStyleSpan", "toUnderlineSpan", "mvvm_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SpanExtKt {
    @NotNull
    public static final TextView appendBackgroundColorSpan(@NotNull TextView textView, @NotNull String str, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        textView.append(toBackgroundColorSpan(str, new IntRange(0, str.length()), i2));
        return textView;
    }

    public static /* synthetic */ TextView appendBackgroundColorSpan$default(TextView textView, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 2) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return appendBackgroundColorSpan(textView, str, i2);
    }

    @NotNull
    public static final TextView appendClickSpan(@NotNull TextView textView, @NotNull String str, int i2, boolean z2, @NotNull Function0<Unit> clickAction) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(clickAction, "clickAction");
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(0);
        textView.append(toClickSpan(str, new IntRange(0, str.length()), i2, z2, clickAction));
        return textView;
    }

    public static /* synthetic */ TextView appendClickSpan$default(TextView textView, String str, int i2, boolean z2, Function0 function0, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 2) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        if ((i3 & 4) != 0) {
            z2 = false;
        }
        return appendClickSpan(textView, str, i2, z2, function0);
    }

    @NotNull
    public static final TextView appendColorSpan(@NotNull TextView textView, @NotNull String str, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        textView.append(toColorSpan(str, new IntRange(0, str.length()), i2));
        return textView;
    }

    public static /* synthetic */ TextView appendColorSpan$default(TextView textView, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 2) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return appendColorSpan(textView, str, i2);
    }

    @NotNull
    public static final TextView appendSizeColorSpan(@NotNull TextView textView, @NotNull String str, float f2, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        textView.append(toColorSpan(toSizeSpan(str, new IntRange(0, str.length()), f2), new IntRange(0, str.length()), i2));
        return textView;
    }

    public static /* synthetic */ TextView appendSizeColorSpan$default(TextView textView, String str, float f2, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 2) != 0) {
            f2 = 1.5f;
        }
        if ((i3 & 4) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return appendSizeColorSpan(textView, str, f2, i2);
    }

    @NotNull
    public static final TextView appendSizeSpan(@NotNull TextView textView, @NotNull String str, float f2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        textView.append(toSizeSpan(str, new IntRange(0, str.length()), f2));
        return textView;
    }

    public static /* synthetic */ TextView appendSizeSpan$default(TextView textView, String str, float f2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "";
        }
        if ((i2 & 2) != 0) {
            f2 = 1.5f;
        }
        return appendSizeSpan(textView, str, f2);
    }

    @NotNull
    public static final TextView appendStrikeThrougthSpan(@NotNull TextView textView, @NotNull String str) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        textView.append(toStrikeThrougthSpan(str, new IntRange(0, str.length())));
        return textView;
    }

    public static /* synthetic */ TextView appendStrikeThrougthSpan$default(TextView textView, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "";
        }
        return appendStrikeThrougthSpan(textView, str);
    }

    @NotNull
    public static final TextView appendStyleSpan(@NotNull TextView textView, @NotNull String str, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        textView.append(toStyleSpan(str, i2, new IntRange(0, str.length())));
        return textView;
    }

    public static /* synthetic */ TextView appendStyleSpan$default(TextView textView, String str, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        return appendStyleSpan(textView, str, i2);
    }

    @NotNull
    public static final TextView appendUnderlineSpan(@NotNull TextView textView, @NotNull String str) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        textView.append(toUnderlineSpan(str, new IntRange(0, str.length())));
        return textView;
    }

    public static /* synthetic */ TextView appendUnderlineSpan$default(TextView textView, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "";
        }
        return appendUnderlineSpan(textView, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.CharSequence, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    @NotNull
    public static final TextView backgroundColorLineHeightSpan(@NotNull TextView textView, @NotNull String str, @NotNull IntRange range, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        if (str.length() == 0) {
            str = textView.getText();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if(str.isEmpty())text else str");
        textView.setText(toBackgroundColorLineHeightSpan(str, range, i2));
        return textView;
    }

    public static /* synthetic */ TextView backgroundColorLineHeightSpan$default(TextView textView, String str, IntRange intRange, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 4) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return backgroundColorLineHeightSpan(textView, str, intRange, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.CharSequence, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    @NotNull
    public static final TextView backgroundColorSpan(@NotNull TextView textView, @NotNull String str, @NotNull IntRange range, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        if (str.length() == 0) {
            str = textView.getText();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if (str.isEmpty()) text else str");
        textView.setText(toBackgroundColorSpan(str, range, i2));
        return textView;
    }

    public static /* synthetic */ TextView backgroundColorSpan$default(TextView textView, String str, IntRange intRange, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 4) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return backgroundColorSpan(textView, str, intRange, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.CharSequence, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.lang.CharSequence] */
    @NotNull
    public static final TextView clickSpan(@NotNull TextView textView, @NotNull String str, @NotNull IntRange range, int i2, boolean z2, @NotNull Function0<Unit> clickAction) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(clickAction, "clickAction");
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(0);
        if (str.length() == 0) {
            str = textView.getText();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if (str.isEmpty()) text else str");
        textView.setText(toClickSpan(str, range, i2, z2, clickAction));
        return textView;
    }

    public static /* synthetic */ TextView clickSpan$default(TextView textView, String str, IntRange intRange, int i2, boolean z2, Function0 function0, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        String str2 = str;
        if ((i3 & 4) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        int i4 = i2;
        if ((i3 & 8) != 0) {
            z2 = false;
        }
        return clickSpan(textView, str2, intRange, i4, z2, function0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.CharSequence, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    @NotNull
    public static final TextView colorSpan(@NotNull TextView textView, @NotNull String str, @NotNull IntRange range, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        if (str.length() == 0) {
            str = textView.getText();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if (str.isEmpty()) text else str");
        textView.setText(toColorSpan(str, range, i2));
        return textView;
    }

    public static /* synthetic */ TextView colorSpan$default(TextView textView, String str, IntRange intRange, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 4) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return colorSpan(textView, str, intRange, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.CharSequence, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    @NotNull
    public static final TextView sizeSpan(@NotNull TextView textView, @NotNull String str, @NotNull IntRange range, float f2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        if (str.length() == 0) {
            str = textView.getText();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if (str.isEmpty()) text else str");
        textView.setText(toSizeSpan(str, range, f2));
        return textView;
    }

    public static /* synthetic */ TextView sizeSpan$default(TextView textView, String str, IntRange intRange, float f2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "";
        }
        if ((i2 & 4) != 0) {
            f2 = 1.5f;
        }
        return sizeSpan(textView, str, intRange, f2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.CharSequence, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    @NotNull
    public static final TextView strikeThrougthSpan(@NotNull TextView textView, @NotNull String str, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        if (str.length() == 0) {
            str = textView.getText();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if (str.isEmpty()) text else str");
        textView.setText(toStrikeThrougthSpan(str, range));
        return textView;
    }

    public static /* synthetic */ TextView strikeThrougthSpan$default(TextView textView, String str, IntRange intRange, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "";
        }
        return strikeThrougthSpan(textView, str, intRange);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.CharSequence, java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    @NotNull
    public static final TextView styleSpan(@NotNull TextView textView, @NotNull String str, @NotNull IntRange range, int i2) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(range, "range");
        if (str.length() == 0) {
            str = textView.getText();
        }
        Intrinsics.checkNotNullExpressionValue(str, "if (str.isEmpty()) text else str");
        textView.setText(toStyleSpan(str, i2, range));
        return textView;
    }

    public static /* synthetic */ TextView styleSpan$default(TextView textView, String str, IntRange intRange, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = "";
        }
        if ((i3 & 4) != 0) {
            i2 = 1;
        }
        return styleSpan(textView, str, intRange, i2);
    }

    @NotNull
    public static final CharSequence toBackgroundColorLineHeightSpan(@NotNull CharSequence charSequence, @NotNull IntRange range, int i2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new BetterHighlightSpan(i2), range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }

    public static /* synthetic */ CharSequence toBackgroundColorLineHeightSpan$default(CharSequence charSequence, IntRange intRange, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return toBackgroundColorLineHeightSpan(charSequence, intRange, i2);
    }

    @NotNull
    public static final CharSequence toBackgroundColorSpan(@NotNull CharSequence charSequence, @NotNull IntRange range, int i2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new BackgroundColorSpan(i2), range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }

    public static /* synthetic */ CharSequence toBackgroundColorSpan$default(CharSequence charSequence, IntRange intRange, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return toBackgroundColorSpan(charSequence, intRange, i2);
    }

    @NotNull
    public static final CharSequence toClickSpan(@NotNull CharSequence charSequence, @NotNull IntRange range, final int i2, final boolean z2, @NotNull final Function0<Unit> clickAction) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(clickAction, "clickAction");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new ClickableSpan() { // from class: com.catchpig.mvvm.ext.SpanExtKt$toClickSpan$1$clickableSpan$1
            @Override // android.text.style.ClickableSpan
            public void onClick(@NotNull View widget) {
                Intrinsics.checkNotNullParameter(widget, "widget");
                clickAction.invoke();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NotNull TextPaint ds) {
                Intrinsics.checkNotNullParameter(ds, "ds");
                ds.setColor(i2);
                ds.setUnderlineText(z2);
            }
        }, range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }

    public static /* synthetic */ CharSequence toClickSpan$default(CharSequence charSequence, IntRange intRange, int i2, boolean z2, Function0 function0, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        if ((i3 & 4) != 0) {
            z2 = false;
        }
        return toClickSpan(charSequence, intRange, i2, z2, function0);
    }

    @NotNull
    public static final CharSequence toColorSpan(@NotNull CharSequence charSequence, @NotNull IntRange range, int i2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new ForegroundColorSpan(i2), range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }

    public static /* synthetic */ CharSequence toColorSpan$default(CharSequence charSequence, IntRange intRange, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = SupportMenu.CATEGORY_MASK;
        }
        return toColorSpan(charSequence, intRange, i2);
    }

    @NotNull
    public static final CharSequence toSizeSpan(@NotNull CharSequence charSequence, @NotNull IntRange range, float f2) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new RelativeSizeSpan(f2), range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }

    public static /* synthetic */ CharSequence toSizeSpan$default(CharSequence charSequence, IntRange intRange, float f2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            f2 = 1.5f;
        }
        return toSizeSpan(charSequence, intRange, f2);
    }

    @NotNull
    public static final CharSequence toStrikeThrougthSpan(@NotNull CharSequence charSequence, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new StrikethroughSpan(), range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }

    @NotNull
    public static final CharSequence toStyleSpan(@NotNull CharSequence charSequence, int i2, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new StyleSpan(i2), range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }

    public static /* synthetic */ CharSequence toStyleSpan$default(CharSequence charSequence, int i2, IntRange intRange, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 1;
        }
        return toStyleSpan(charSequence, i2, intRange);
    }

    @NotNull
    public static final CharSequence toUnderlineSpan(@NotNull CharSequence charSequence, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new UnderlineSpan(), range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
        return spannableString;
    }
}
