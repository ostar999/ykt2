package com.ykb.ebook.weight;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 82\u00020\u0001:\u000278B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t¢\u0006\u0002\u0010\rJ\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0007H\u0002J\b\u0010\"\u001a\u00020\u0007H\u0016J\u0016\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020\u0005J\u0010\u0010&\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u0010'\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u0010(\u001a\u00020)2\u0006\u0010!\u001a\u00020\u0007H\u0016J\b\u0010*\u001a\u00020\u0007H\u0016J\u0010\u0010+\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0012\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u0010\u0015\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u0010.\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u0010/\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u00100\u001a\u00020\f2\u0006\u00101\u001a\u00020\nH\u0002J\u0010\u00102\u001a\u00020\f2\u0006\u00101\u001a\u00020\nH\u0002J\b\u00103\u001a\u00020\u0007H\u0016J\u0010\u00104\u001a\u00020)2\u0006\u0010\u0006\u001a\u00020\fH\u0002J\u0010\u00105\u001a\u00020)2\u0006\u00101\u001a\u00020\nH\u0002J\u0010\u00106\u001a\u00020)2\u0006\u00101\u001a\u00020\nH\u0002R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u00069"}, d2 = {"Lcom/ykb/ebook/weight/ZhLayout;", "Landroid/text/Layout;", "text", "", "textPaint", "Landroid/text/TextPaint;", "width", "", "words", "", "", "widths", "", "(Ljava/lang/CharSequence;Landroid/text/TextPaint;ILjava/util/List;Ljava/util/List;)V", "cnCharWidth", "curPaint", "defaultCapacity", "gap", "lineCount", "lineStart", "", "getLineStart", "()[I", "setLineStart", "([I)V", "lineWidth", "", "getLineWidth", "()[F", "setLineWidth", "([F)V", "addLineArray", "", PLVDocumentMarkToolType.BRUSH, "getBottomPadding", "getDesiredWidth", "sting", "paint", "getEllipsisCount", "getEllipsisStart", "getLineContainsTab", "", "getLineCount", "getLineDescent", "getLineDirections", "Landroid/text/Layout$Directions;", "getLineTop", "getParagraphDirection", "getPostPancOffset", TypedValues.Custom.S_STRING, "getPrePancOffset", "getTopPadding", "inCompressible", "isPostPanc", "isPrePanc", "BreakMod", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nZhLayout.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ZhLayout.kt\ncom/ykb/ebook/weight/ZhLayout\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,268:1\n1864#2,3:269\n*S KotlinDebug\n*F\n+ 1 ZhLayout.kt\ncom/ykb/ebook/weight/ZhLayout\n*L\n57#1:269,3\n*E\n"})
/* loaded from: classes8.dex */
public final class ZhLayout extends Layout {
    private final float cnCharWidth;

    @NotNull
    private final TextPaint curPaint;
    private final int defaultCapacity;
    private final float gap;
    private int lineCount;

    @NotNull
    private int[] lineStart;

    @NotNull
    private float[] lineWidth;

    @NotNull
    private static final HashSet<String> postPanc = SetsKt__SetsKt.hashSetOf("，", "。", "：", "？", "！", "、", "”", "’", "）", "》", "}", "】", ")", ">", StrPool.BRACKET_END, "}", ",", StrPool.DOT, "?", "!", ":", "」", "；", com.alipay.sdk.util.h.f3376b);

    @NotNull
    private static final HashSet<String> prePanc = SetsKt__SetsKt.hashSetOf("“", "（", "《", "【", "‘", "‘", "(", "<", StrPool.BRACKET_START, StrPool.DELIM_START, "「");

    @NotNull
    private static final WeakHashMap<Paint, Float> cnCharWidthCache = new WeakHashMap<>();

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/weight/ZhLayout$BreakMod;", "", "(Ljava/lang/String;I)V", "NORMAL", "BREAK_ONE_CHAR", "BREAK_MORE_CHAR", "CPS_1", "CPS_2", "CPS_3", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum BreakMod {
        NORMAL,
        BREAK_ONE_CHAR,
        BREAK_MORE_CHAR,
        CPS_1,
        CPS_2,
        CPS_3
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BreakMod.values().length];
            try {
                iArr[BreakMod.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BreakMod.BREAK_ONE_CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BreakMod.BREAK_MORE_CHAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BreakMod.CPS_1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[BreakMod.CPS_2.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[BreakMod.CPS_3.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public ZhLayout(@NotNull CharSequence text, @NotNull TextPaint textPaint, int i2, @NotNull List<String> words, @NotNull List<Float> widths) {
        boolean z2;
        float f2;
        int i3;
        int i4;
        int length;
        super(text, textPaint, i2, Layout.Alignment.ALIGN_NORMAL, 0.0f, 0.0f);
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(textPaint, "textPaint");
        Intrinsics.checkNotNullParameter(words, "words");
        Intrinsics.checkNotNullParameter(widths, "widths");
        this.defaultCapacity = 10;
        this.lineStart = new int[10];
        this.lineWidth = new float[10];
        this.curPaint = textPaint;
        WeakHashMap<Paint, Float> weakHashMap = cnCharWidthCache;
        Float fValueOf = weakHashMap.get(textPaint);
        if (fValueOf == null) {
            fValueOf = Float.valueOf(getDesiredWidth("我", textPaint));
            weakHashMap.put(textPaint, Float.valueOf(fValueOf.floatValue()));
        }
        this.cnCharWidth = fValueOf.floatValue();
        int i5 = 0;
        int i6 = 0;
        float f3 = 0.0f;
        int length2 = 0;
        float fFloatValue = 0.0f;
        for (Object obj : words) {
            int i7 = i6 + 1;
            if (i6 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            String str = (String) obj;
            float fFloatValue2 = widths.get(i6).floatValue();
            f3 += fFloatValue2;
            if (f3 > i2) {
                BreakMod breakMod = (i6 < 1 || !isPrePanc(words.get(i6 + (-1)))) ? isPostPanc(words.get(i6)) ? (i6 < 1 || !isPostPanc(words.get(i6 + (-1)))) ? (i6 < 2 || !isPrePanc(words.get(i6 + (-2)))) ? BreakMod.BREAK_ONE_CHAR : BreakMod.CPS_3 : BreakMod.CPS_1 : BreakMod.NORMAL : (i6 < 2 || !isPrePanc(words.get(i6 + (-2)))) ? BreakMod.BREAK_ONE_CHAR : BreakMod.CPS_2;
                boolean z3 = breakMod == BreakMod.CPS_1 && (inCompressible(widths.get(i6).floatValue()) || inCompressible(widths.get(i6 + (-1)).floatValue()));
                if (breakMod == BreakMod.CPS_2 && (inCompressible(widths.get(i6 - 1).floatValue()) || inCompressible(widths.get(i6 - 2).floatValue()))) {
                    z3 = true;
                }
                if (breakMod == BreakMod.CPS_3 && (inCompressible(widths.get(i6).floatValue()) || inCompressible(widths.get(i6 - 2).floatValue()))) {
                    z3 = true;
                }
                if (breakMod.compareTo(BreakMod.BREAK_MORE_CHAR) > 0 && i6 < CollectionsKt__CollectionsKt.getLastIndex(words) && isPostPanc(words.get(i7))) {
                    z3 = true;
                }
                if (!z3 || i6 <= 2) {
                    i4 = 0;
                    length = 0;
                } else {
                    breakMod = BreakMod.NORMAL;
                    int i8 = i6;
                    i4 = 0;
                    length = 0;
                    while (true) {
                        if (i8 > 0) {
                            if (i8 == i6) {
                                fFloatValue = 0.0f;
                                i4 = 0;
                            } else {
                                i4++;
                                length += words.get(i8).length();
                                fFloatValue += widths.get(i8).floatValue();
                            }
                            if (isPostPanc(words.get(i8)) || isPrePanc(words.get(i8 - 1))) {
                                i8--;
                            } else {
                                breakMod = BreakMod.BREAK_MORE_CHAR;
                            }
                        }
                    }
                }
                switch (WhenMappings.$EnumSwitchMapping$0[breakMod.ordinal()]) {
                    case 1:
                        this.lineStart[i5 + 1] = length2;
                        f2 = fFloatValue2;
                        i3 = 1;
                        break;
                    case 2:
                        f2 = fFloatValue + fFloatValue2;
                        this.lineStart[i5 + 1] = length2 - words.get(i6 - 1).length();
                        i3 = 2;
                        break;
                    case 3:
                        f2 = fFloatValue + fFloatValue2;
                        this.lineStart[i5 + 1] = length2 - length;
                        i3 = i4 + 1;
                        break;
                    case 4:
                        this.lineStart[i5 + 1] = str.length() + length2;
                        f2 = 0.0f;
                        i3 = 0;
                        break;
                    case 5:
                        this.lineStart[i5 + 1] = str.length() + length2;
                        f2 = 0.0f;
                        i3 = 0;
                        break;
                    case 6:
                        this.lineStart[i5 + 1] = str.length() + length2;
                        f2 = 0.0f;
                        i3 = 0;
                        break;
                    default:
                        f2 = 0.0f;
                        i3 = 0;
                        break;
                }
                z2 = true;
            } else {
                z2 = false;
                f2 = 0.0f;
                i3 = 0;
            }
            if (z2) {
                this.lineWidth[i5] = f3 - f2;
                i5++;
                addLineArray(i5);
                f3 = f2;
            }
            if (CollectionsKt__CollectionsKt.getLastIndex(words) == i6) {
                if (!z2) {
                    int i9 = i5 + 1;
                    this.lineStart[i9] = str.length() + length2;
                    this.lineWidth[i5] = f3 - 0.0f;
                    addLineArray(i9);
                    i5 = i9;
                    f3 = 0.0f;
                } else if (i3 > 0) {
                    int[] iArr = this.lineStart;
                    int i10 = i5 + 1;
                    iArr[i10] = iArr[i5] + i3;
                    this.lineWidth[i5] = f3;
                    addLineArray(i10);
                    i5 = i10;
                }
            }
            length2 += str.length();
            i6 = i7;
            fFloatValue = fFloatValue2;
        }
        this.lineCount = i5;
        this.gap = (float) (this.cnCharWidth / 12.75d);
    }

    private final void addLineArray(int line) {
        int[] iArr = this.lineStart;
        if (iArr.length <= line + 1) {
            int[] iArrCopyOf = Arrays.copyOf(iArr, this.defaultCapacity + line);
            Intrinsics.checkNotNullExpressionValue(iArrCopyOf, "copyOf(this, newSize)");
            this.lineStart = iArrCopyOf;
            float[] fArrCopyOf = Arrays.copyOf(this.lineWidth, line + this.defaultCapacity);
            Intrinsics.checkNotNullExpressionValue(fArrCopyOf, "copyOf(this, newSize)");
            this.lineWidth = fArrCopyOf;
        }
    }

    private final float getPostPancOffset(String string) {
        this.curPaint.getTextBounds(string, 0, 1, new Rect());
        return Math.max(r0.left - this.gap, 0.0f);
    }

    private final float getPrePancOffset(String string) {
        this.curPaint.getTextBounds(string, 0, 1, new Rect());
        return (this.cnCharWidth / 2) - Math.max((this.cnCharWidth - r0.right) - this.gap, 0.0f);
    }

    private final boolean inCompressible(float width) {
        return width < this.cnCharWidth;
    }

    private final boolean isPostPanc(String string) {
        return postPanc.contains(string);
    }

    private final boolean isPrePanc(String string) {
        return prePanc.contains(string);
    }

    @Override // android.text.Layout
    public int getBottomPadding() {
        return 0;
    }

    public final float getDesiredWidth(@NotNull String sting, @NotNull TextPaint paint) {
        Intrinsics.checkNotNullParameter(sting, "sting");
        Intrinsics.checkNotNullParameter(paint, "paint");
        return paint.measureText(sting);
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int line) {
        return 0;
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int line) {
        return 0;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int line) {
        return true;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return this.lineCount;
    }

    @Override // android.text.Layout
    public int getLineDescent(int line) {
        return 0;
    }

    @Override // android.text.Layout
    @Nullable
    public Layout.Directions getLineDirections(int line) {
        return null;
    }

    @NotNull
    public final int[] getLineStart() {
        return this.lineStart;
    }

    @Override // android.text.Layout
    public int getLineTop(int line) {
        return 0;
    }

    @NotNull
    public final float[] getLineWidth() {
        return this.lineWidth;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int line) {
        return 0;
    }

    @Override // android.text.Layout
    public int getTopPadding() {
        return 0;
    }

    public final void setLineStart(@NotNull int[] iArr) {
        Intrinsics.checkNotNullParameter(iArr, "<set-?>");
        this.lineStart = iArr;
    }

    public final void setLineWidth(@NotNull float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.lineWidth = fArr;
    }

    @Override // android.text.Layout
    public int getLineStart(int line) {
        return this.lineStart[line];
    }

    @Override // android.text.Layout
    public float getLineWidth(int line) {
        return this.lineWidth[line];
    }
}
