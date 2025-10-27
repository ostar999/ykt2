package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Legend extends ComponentBase {
    private List<Boolean> mCalculatedLabelBreakPoints;
    private List<FSize> mCalculatedLabelSizes;
    private List<FSize> mCalculatedLineSizes;
    private LegendDirection mDirection;
    private boolean mDrawInside;
    private LegendEntry[] mEntries;
    private LegendEntry[] mExtraEntries;
    private DashPathEffect mFormLineDashEffect;
    private float mFormLineWidth;
    private float mFormSize;
    private float mFormToTextSpace;
    private LegendHorizontalAlignment mHorizontalAlignment;
    private boolean mIsLegendCustom;
    private float mMaxSizePercent;
    public float mNeededHeight;
    public float mNeededWidth;
    private LegendOrientation mOrientation;
    private LegendForm mShape;
    private float mStackSpace;
    public float mTextHeightMax;
    public float mTextWidthMax;
    private LegendVerticalAlignment mVerticalAlignment;
    private boolean mWordWrapEnabled;
    private float mXEntrySpace;
    private float mYEntrySpace;

    /* renamed from: com.github.mikephil.charting.components.Legend$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation;

        static {
            int[] iArr = new int[LegendOrientation.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation = iArr;
            try {
                iArr[LegendOrientation.VERTICAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[LegendOrientation.HORIZONTAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum LegendDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public enum LegendForm {
        NONE,
        EMPTY,
        DEFAULT,
        SQUARE,
        CIRCLE,
        LINE
    }

    public enum LegendHorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum LegendOrientation {
        HORIZONTAL,
        VERTICAL
    }

    public enum LegendVerticalAlignment {
        TOP,
        CENTER,
        BOTTOM
    }

    public Legend() {
        this.mEntries = new LegendEntry[0];
        this.mIsLegendCustom = false;
        this.mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
        this.mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
        this.mOrientation = LegendOrientation.HORIZONTAL;
        this.mDrawInside = false;
        this.mDirection = LegendDirection.LEFT_TO_RIGHT;
        this.mShape = LegendForm.SQUARE;
        this.mFormSize = 8.0f;
        this.mFormLineWidth = 3.0f;
        this.mFormLineDashEffect = null;
        this.mXEntrySpace = 6.0f;
        this.mYEntrySpace = 0.0f;
        this.mFormToTextSpace = 5.0f;
        this.mStackSpace = 3.0f;
        this.mMaxSizePercent = 0.95f;
        this.mNeededWidth = 0.0f;
        this.mNeededHeight = 0.0f;
        this.mTextHeightMax = 0.0f;
        this.mTextWidthMax = 0.0f;
        this.mWordWrapEnabled = false;
        this.mCalculatedLabelSizes = new ArrayList(16);
        this.mCalculatedLabelBreakPoints = new ArrayList(16);
        this.mCalculatedLineSizes = new ArrayList(16);
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(3.0f);
    }

    public void calculateDimensions(Paint paint, ViewPortHandler viewPortHandler) {
        float f2;
        float f3;
        float f4;
        float fConvertDpToPixel = Utils.convertDpToPixel(this.mFormSize);
        float fConvertDpToPixel2 = Utils.convertDpToPixel(this.mStackSpace);
        float fConvertDpToPixel3 = Utils.convertDpToPixel(this.mFormToTextSpace);
        float fConvertDpToPixel4 = Utils.convertDpToPixel(this.mXEntrySpace);
        float fConvertDpToPixel5 = Utils.convertDpToPixel(this.mYEntrySpace);
        boolean z2 = this.mWordWrapEnabled;
        LegendEntry[] legendEntryArr = this.mEntries;
        int length = legendEntryArr.length;
        this.mTextWidthMax = getMaximumEntryWidth(paint);
        this.mTextHeightMax = getMaximumEntryHeight(paint);
        int i2 = AnonymousClass1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mOrientation.ordinal()];
        if (i2 == 1) {
            float lineHeight = Utils.getLineHeight(paint);
            float fMax = 0.0f;
            float f5 = 0.0f;
            float fCalcTextWidth = 0.0f;
            boolean z3 = false;
            for (int i3 = 0; i3 < length; i3++) {
                LegendEntry legendEntry = legendEntryArr[i3];
                boolean z4 = legendEntry.form != LegendForm.NONE;
                float fConvertDpToPixel6 = Float.isNaN(legendEntry.formSize) ? fConvertDpToPixel : Utils.convertDpToPixel(legendEntry.formSize);
                String str = legendEntry.label;
                if (!z3) {
                    fCalcTextWidth = 0.0f;
                }
                if (z4) {
                    if (z3) {
                        fCalcTextWidth += fConvertDpToPixel2;
                    }
                    fCalcTextWidth += fConvertDpToPixel6;
                }
                if (str != null) {
                    if (z4 && !z3) {
                        fCalcTextWidth += fConvertDpToPixel3;
                    } else if (z3) {
                        fMax = Math.max(fMax, fCalcTextWidth);
                        f5 += lineHeight + fConvertDpToPixel5;
                        fCalcTextWidth = 0.0f;
                        z3 = false;
                    }
                    fCalcTextWidth += Utils.calcTextWidth(paint, str);
                    if (i3 < length - 1) {
                        f5 += lineHeight + fConvertDpToPixel5;
                    }
                } else {
                    fCalcTextWidth += fConvertDpToPixel6;
                    if (i3 < length - 1) {
                        fCalcTextWidth += fConvertDpToPixel2;
                    }
                    z3 = true;
                }
                fMax = Math.max(fMax, fCalcTextWidth);
            }
            this.mNeededWidth = fMax;
            this.mNeededHeight = f5;
        } else if (i2 == 2) {
            float lineHeight2 = Utils.getLineHeight(paint);
            float lineSpacing = Utils.getLineSpacing(paint) + fConvertDpToPixel5;
            float fContentWidth = viewPortHandler.contentWidth() * this.mMaxSizePercent;
            this.mCalculatedLabelBreakPoints.clear();
            this.mCalculatedLabelSizes.clear();
            this.mCalculatedLineSizes.clear();
            int i4 = 0;
            float fMax2 = 0.0f;
            int i5 = -1;
            float f6 = 0.0f;
            float f7 = 0.0f;
            while (i4 < length) {
                LegendEntry legendEntry2 = legendEntryArr[i4];
                float f8 = fConvertDpToPixel;
                float f9 = fConvertDpToPixel4;
                boolean z5 = legendEntry2.form != LegendForm.NONE;
                float fConvertDpToPixel7 = Float.isNaN(legendEntry2.formSize) ? f8 : Utils.convertDpToPixel(legendEntry2.formSize);
                String str2 = legendEntry2.label;
                LegendEntry[] legendEntryArr2 = legendEntryArr;
                float f10 = lineSpacing;
                this.mCalculatedLabelBreakPoints.add(Boolean.FALSE);
                float f11 = i5 == -1 ? 0.0f : f6 + fConvertDpToPixel2;
                if (str2 != null) {
                    f2 = fConvertDpToPixel2;
                    this.mCalculatedLabelSizes.add(Utils.calcTextSize(paint, str2));
                    f3 = f11 + (z5 ? fConvertDpToPixel3 + fConvertDpToPixel7 : 0.0f) + this.mCalculatedLabelSizes.get(i4).width;
                } else {
                    f2 = fConvertDpToPixel2;
                    float f12 = fConvertDpToPixel7;
                    this.mCalculatedLabelSizes.add(FSize.getInstance(0.0f, 0.0f));
                    f3 = f11 + (z5 ? f12 : 0.0f);
                    if (i5 == -1) {
                        i5 = i4;
                    }
                }
                if (str2 != null || i4 == length - 1) {
                    float f13 = f7;
                    float f14 = f13 == 0.0f ? 0.0f : f9;
                    if (!z2 || f13 == 0.0f || fContentWidth - f13 >= f14 + f3) {
                        f4 = f13 + f14 + f3;
                    } else {
                        this.mCalculatedLineSizes.add(FSize.getInstance(f13, lineHeight2));
                        float fMax3 = Math.max(fMax2, f13);
                        this.mCalculatedLabelBreakPoints.set(i5 > -1 ? i5 : i4, Boolean.TRUE);
                        fMax2 = fMax3;
                        f4 = f3;
                    }
                    if (i4 == length - 1) {
                        this.mCalculatedLineSizes.add(FSize.getInstance(f4, lineHeight2));
                        fMax2 = Math.max(fMax2, f4);
                    }
                    f7 = f4;
                }
                if (str2 != null) {
                    i5 = -1;
                }
                i4++;
                fConvertDpToPixel2 = f2;
                fConvertDpToPixel = f8;
                fConvertDpToPixel4 = f9;
                lineSpacing = f10;
                f6 = f3;
                legendEntryArr = legendEntryArr2;
            }
            float f15 = lineSpacing;
            this.mNeededWidth = fMax2;
            this.mNeededHeight = (lineHeight2 * this.mCalculatedLineSizes.size()) + (f15 * (this.mCalculatedLineSizes.size() == 0 ? 0 : this.mCalculatedLineSizes.size() - 1));
        }
        this.mNeededHeight += this.mYOffset;
        this.mNeededWidth += this.mXOffset;
    }

    public List<Boolean> getCalculatedLabelBreakPoints() {
        return this.mCalculatedLabelBreakPoints;
    }

    public List<FSize> getCalculatedLabelSizes() {
        return this.mCalculatedLabelSizes;
    }

    public List<FSize> getCalculatedLineSizes() {
        return this.mCalculatedLineSizes;
    }

    public LegendDirection getDirection() {
        return this.mDirection;
    }

    public LegendEntry[] getEntries() {
        return this.mEntries;
    }

    public LegendEntry[] getExtraEntries() {
        return this.mExtraEntries;
    }

    public LegendForm getForm() {
        return this.mShape;
    }

    public DashPathEffect getFormLineDashEffect() {
        return this.mFormLineDashEffect;
    }

    public float getFormLineWidth() {
        return this.mFormLineWidth;
    }

    public float getFormSize() {
        return this.mFormSize;
    }

    public float getFormToTextSpace() {
        return this.mFormToTextSpace;
    }

    public LegendHorizontalAlignment getHorizontalAlignment() {
        return this.mHorizontalAlignment;
    }

    public float getMaxSizePercent() {
        return this.mMaxSizePercent;
    }

    public float getMaximumEntryHeight(Paint paint) {
        float f2 = 0.0f;
        for (LegendEntry legendEntry : this.mEntries) {
            String str = legendEntry.label;
            if (str != null) {
                float fCalcTextHeight = Utils.calcTextHeight(paint, str);
                if (fCalcTextHeight > f2) {
                    f2 = fCalcTextHeight;
                }
            }
        }
        return f2;
    }

    public float getMaximumEntryWidth(Paint paint) {
        float fConvertDpToPixel = Utils.convertDpToPixel(this.mFormToTextSpace);
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (LegendEntry legendEntry : this.mEntries) {
            float fConvertDpToPixel2 = Utils.convertDpToPixel(Float.isNaN(legendEntry.formSize) ? this.mFormSize : legendEntry.formSize);
            if (fConvertDpToPixel2 > f3) {
                f3 = fConvertDpToPixel2;
            }
            String str = legendEntry.label;
            if (str != null) {
                float fCalcTextWidth = Utils.calcTextWidth(paint, str);
                if (fCalcTextWidth > f2) {
                    f2 = fCalcTextWidth;
                }
            }
        }
        return f2 + f3 + fConvertDpToPixel;
    }

    public LegendOrientation getOrientation() {
        return this.mOrientation;
    }

    public float getStackSpace() {
        return this.mStackSpace;
    }

    public LegendVerticalAlignment getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    public float getXEntrySpace() {
        return this.mXEntrySpace;
    }

    public float getYEntrySpace() {
        return this.mYEntrySpace;
    }

    public boolean isDrawInsideEnabled() {
        return this.mDrawInside;
    }

    public boolean isLegendCustom() {
        return this.mIsLegendCustom;
    }

    public boolean isWordWrapEnabled() {
        return this.mWordWrapEnabled;
    }

    public void resetCustom() {
        this.mIsLegendCustom = false;
    }

    public void setCustom(LegendEntry[] legendEntryArr) {
        this.mEntries = legendEntryArr;
        this.mIsLegendCustom = true;
    }

    public void setDirection(LegendDirection legendDirection) {
        this.mDirection = legendDirection;
    }

    public void setDrawInside(boolean z2) {
        this.mDrawInside = z2;
    }

    public void setEntries(List<LegendEntry> list) {
        this.mEntries = (LegendEntry[]) list.toArray(new LegendEntry[list.size()]);
    }

    public void setExtra(List<LegendEntry> list) {
        this.mExtraEntries = (LegendEntry[]) list.toArray(new LegendEntry[list.size()]);
    }

    public void setForm(LegendForm legendForm) {
        this.mShape = legendForm;
    }

    public void setFormLineDashEffect(DashPathEffect dashPathEffect) {
        this.mFormLineDashEffect = dashPathEffect;
    }

    public void setFormLineWidth(float f2) {
        this.mFormLineWidth = f2;
    }

    public void setFormSize(float f2) {
        this.mFormSize = f2;
    }

    public void setFormToTextSpace(float f2) {
        this.mFormToTextSpace = f2;
    }

    public void setHorizontalAlignment(LegendHorizontalAlignment legendHorizontalAlignment) {
        this.mHorizontalAlignment = legendHorizontalAlignment;
    }

    public void setMaxSizePercent(float f2) {
        this.mMaxSizePercent = f2;
    }

    public void setOrientation(LegendOrientation legendOrientation) {
        this.mOrientation = legendOrientation;
    }

    public void setStackSpace(float f2) {
        this.mStackSpace = f2;
    }

    public void setVerticalAlignment(LegendVerticalAlignment legendVerticalAlignment) {
        this.mVerticalAlignment = legendVerticalAlignment;
    }

    public void setWordWrapEnabled(boolean z2) {
        this.mWordWrapEnabled = z2;
    }

    public void setXEntrySpace(float f2) {
        this.mXEntrySpace = f2;
    }

    public void setYEntrySpace(float f2) {
        this.mYEntrySpace = f2;
    }

    public void setExtra(LegendEntry[] legendEntryArr) {
        if (legendEntryArr == null) {
            legendEntryArr = new LegendEntry[0];
        }
        this.mExtraEntries = legendEntryArr;
    }

    public void setCustom(List<LegendEntry> list) {
        this.mEntries = (LegendEntry[]) list.toArray(new LegendEntry[list.size()]);
        this.mIsLegendCustom = true;
    }

    public void setExtra(int[] iArr, String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < Math.min(iArr.length, strArr.length); i2++) {
            LegendEntry legendEntry = new LegendEntry();
            int i3 = iArr[i2];
            legendEntry.formColor = i3;
            legendEntry.label = strArr[i2];
            if (i3 == 1122868 || i3 == 0) {
                legendEntry.form = LegendForm.NONE;
            } else if (i3 == 1122867) {
                legendEntry.form = LegendForm.EMPTY;
            }
            arrayList.add(legendEntry);
        }
        this.mExtraEntries = (LegendEntry[]) arrayList.toArray(new LegendEntry[arrayList.size()]);
    }

    public Legend(LegendEntry[] legendEntryArr) {
        this();
        if (legendEntryArr != null) {
            this.mEntries = legendEntryArr;
            return;
        }
        throw new IllegalArgumentException("entries array is NULL");
    }
}
