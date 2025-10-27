package com.ykb.ebook.fragment;

import android.graphics.Rect;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\r\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J`\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/ykb/ebook/fragment/GapLineBackgroundSpan;", "Landroid/text/style/LineBackgroundSpan;", TtmlNode.ATTR_TTS_BACKGROUND_COLOR, "", "radius", "", "(IF)V", "isFirstLine", "", "rect", "Landroid/graphics/RectF;", "textBounds", "Landroid/graphics/Rect;", "drawBackground", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "left", "right", PLVDanmakuInfo.FONTMODE_TOP, "baseline", PLVDanmakuInfo.FONTMODE_BOTTOM, "text", "", "start", "end", "lineNumber", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class GapLineBackgroundSpan implements LineBackgroundSpan {
    private final int backgroundColor;
    private boolean isFirstLine;
    private final float radius;

    @NotNull
    private final RectF rect;

    @NotNull
    private final Rect textBounds;

    public GapLineBackgroundSpan(int i2, float f2) {
        this.backgroundColor = i2;
        this.radius = f2;
        this.rect = new RectF();
        this.isFirstLine = true;
        this.textBounds = new Rect();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003f  */
    @Override // android.text.style.LineBackgroundSpan
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawBackground(@org.jetbrains.annotations.NotNull android.graphics.Canvas r1, @org.jetbrains.annotations.NotNull android.graphics.Paint r2, int r3, int r4, int r5, int r6, int r7, @org.jetbrains.annotations.NotNull java.lang.CharSequence r8, int r9, int r10, int r11) {
        /*
            r0 = this;
            java.lang.String r4 = "canvas"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r4)
            java.lang.String r4 = "paint"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r4)
            java.lang.String r4 = "text"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r4)
            int r4 = r0.backgroundColor
            r2.setColor(r4)
            int r4 = r8.length()
            r6 = 0
            if (r10 != r4) goto L1d
            r4 = 1
            goto L1e
        L1d:
            r4 = r6
        L1e:
            java.lang.CharSequence r8 = r8.subSequence(r9, r10)
            java.lang.String r8 = r8.toString()
            int r9 = r8.length()
            android.graphics.Rect r10 = r0.textBounds
            r2.getTextBounds(r8, r6, r9, r10)
            android.graphics.Rect r9 = r0.textBounds
            int r9 = r9.height()
            int r10 = r7 - r5
            float r8 = r2.measureText(r8)
            if (r11 != 0) goto L42
            if (r4 == 0) goto L42
        L3f:
            int r5 = r5 + (-4)
            goto L51
        L42:
            if (r11 != 0) goto L4a
            int r10 = r10 - r9
            int r10 = r10 / 2
            int r7 = r10 + r9
            goto L51
        L4a:
            if (r4 == 0) goto L4d
            goto L3f
        L4d:
            int r5 = r5 + (-4)
            int r7 = r7 + (-25)
        L51:
            android.graphics.RectF r4 = r0.rect
            float r3 = (float) r3
            float r5 = (float) r5
            float r7 = (float) r7
            r4.set(r3, r5, r8, r7)
            android.graphics.RectF r3 = r0.rect
            float r4 = r0.radius
            r1.drawRoundRect(r3, r4, r4, r2)
            r0.isFirstLine = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.fragment.GapLineBackgroundSpan.drawBackground(android.graphics.Canvas, android.graphics.Paint, int, int, int, int, int, java.lang.CharSequence, int, int, int):void");
    }

    public /* synthetic */ GapLineBackgroundSpan(int i2, float f2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? 8.0f : f2);
    }
}
