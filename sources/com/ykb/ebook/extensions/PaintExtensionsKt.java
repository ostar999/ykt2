package com.ykb.ebook.extensions;

import android.graphics.Paint;
import android.text.TextPaint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"textHeight", "", "Landroid/text/TextPaint;", "getTextHeight", "(Landroid/text/TextPaint;)F", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPaintExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PaintExtensions.kt\ncom/ykb/ebook/extensions/PaintExtensionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,11:1\n1#2:12\n*E\n"})
/* loaded from: classes7.dex */
public final class PaintExtensionsKt {
    public static final float getTextHeight(@NotNull TextPaint textPaint) {
        Intrinsics.checkNotNullParameter(textPaint, "<this>");
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        return (fontMetrics.descent - fontMetrics.ascent) + fontMetrics.leading;
    }
}
