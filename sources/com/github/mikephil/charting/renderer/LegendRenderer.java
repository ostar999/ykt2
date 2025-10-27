package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LegendRenderer extends Renderer {
    protected List<LegendEntry> computedEntries;
    protected Paint.FontMetrics legendFontMetrics;
    protected Legend mLegend;
    protected Paint mLegendFormPaint;
    protected Paint mLegendLabelPaint;
    private Path mLineFormPath;

    /* renamed from: com.github.mikephil.charting.renderer.LegendRenderer$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation;
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment;

        static {
            int[] iArr = new int[Legend.LegendForm.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm = iArr;
            try {
                iArr[Legend.LegendForm.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[Legend.LegendForm.EMPTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[Legend.LegendForm.DEFAULT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[Legend.LegendForm.CIRCLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[Legend.LegendForm.SQUARE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[Legend.LegendForm.LINE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[Legend.LegendOrientation.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation = iArr2;
            try {
                iArr2[Legend.LegendOrientation.HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[Legend.LegendOrientation.VERTICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr3 = new int[Legend.LegendVerticalAlignment.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment = iArr3;
            try {
                iArr3[Legend.LegendVerticalAlignment.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[Legend.LegendVerticalAlignment.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[Legend.LegendVerticalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr4 = new int[Legend.LegendHorizontalAlignment.values().length];
            $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment = iArr4;
            try {
                iArr4[Legend.LegendHorizontalAlignment.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[Legend.LegendHorizontalAlignment.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[Legend.LegendHorizontalAlignment.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public LegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler);
        this.computedEntries = new ArrayList(16);
        this.legendFontMetrics = new Paint.FontMetrics();
        this.mLineFormPath = new Path();
        this.mLegend = legend;
        Paint paint = new Paint(1);
        this.mLegendLabelPaint = paint;
        paint.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mLegendLabelPaint.setTextAlign(Paint.Align.LEFT);
        Paint paint2 = new Paint(1);
        this.mLegendFormPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0153  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void computeLegend(com.github.mikephil.charting.data.ChartData<?> r19) {
        /*
            Method dump skipped, instructions count: 494
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LegendRenderer.computeLegend(com.github.mikephil.charting.data.ChartData):void");
    }

    public void drawForm(Canvas canvas, float f2, float f3, LegendEntry legendEntry, Legend legend) {
        int i2 = legendEntry.formColor;
        if (i2 == 1122868 || i2 == 1122867 || i2 == 0) {
            return;
        }
        int iSave = canvas.save();
        Legend.LegendForm form = legendEntry.form;
        if (form == Legend.LegendForm.DEFAULT) {
            form = legend.getForm();
        }
        this.mLegendFormPaint.setColor(legendEntry.formColor);
        float fConvertDpToPixel = Utils.convertDpToPixel(Float.isNaN(legendEntry.formSize) ? legend.getFormSize() : legendEntry.formSize);
        float f4 = fConvertDpToPixel / 2.0f;
        int i3 = AnonymousClass1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm[form.ordinal()];
        if (i3 == 3 || i3 == 4) {
            this.mLegendFormPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(f2 + f4, f3, f4, this.mLegendFormPaint);
        } else if (i3 == 5) {
            this.mLegendFormPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(f2, f3 - f4, f2 + fConvertDpToPixel, f3 + f4, this.mLegendFormPaint);
        } else if (i3 == 6) {
            float fConvertDpToPixel2 = Utils.convertDpToPixel(Float.isNaN(legendEntry.formLineWidth) ? legend.getFormLineWidth() : legendEntry.formLineWidth);
            DashPathEffect formLineDashEffect = legendEntry.formLineDashEffect;
            if (formLineDashEffect == null) {
                formLineDashEffect = legend.getFormLineDashEffect();
            }
            this.mLegendFormPaint.setStyle(Paint.Style.STROKE);
            this.mLegendFormPaint.setStrokeWidth(fConvertDpToPixel2);
            this.mLegendFormPaint.setPathEffect(formLineDashEffect);
            this.mLineFormPath.reset();
            this.mLineFormPath.moveTo(f2, f3);
            this.mLineFormPath.lineTo(f2 + fConvertDpToPixel, f3);
            canvas.drawPath(this.mLineFormPath, this.mLegendFormPaint);
        }
        canvas.restoreToCount(iSave);
    }

    public void drawLabel(Canvas canvas, float f2, float f3, String str) {
        canvas.drawText(str, f2, f3, this.mLegendLabelPaint);
    }

    public Paint getFormPaint() {
        return this.mLegendFormPaint;
    }

    public Paint getLabelPaint() {
        return this.mLegendLabelPaint;
    }

    public void renderLegend(Canvas canvas) {
        float f2;
        float f3;
        float chartWidth;
        float f4;
        float f5;
        List<Boolean> list;
        List<FSize> list2;
        int i2;
        float f6;
        float f7;
        float f8;
        float f9;
        float fContentTop;
        float f10;
        float f11;
        float f12;
        Legend.LegendDirection legendDirection;
        LegendEntry legendEntry;
        float fCalcTextWidth;
        double d3;
        if (this.mLegend.isEnabled()) {
            Typeface typeface = this.mLegend.getTypeface();
            if (typeface != null) {
                this.mLegendLabelPaint.setTypeface(typeface);
            }
            this.mLegendLabelPaint.setTextSize(this.mLegend.getTextSize());
            this.mLegendLabelPaint.setColor(this.mLegend.getTextColor());
            float lineHeight = Utils.getLineHeight(this.mLegendLabelPaint, this.legendFontMetrics);
            float lineSpacing = Utils.getLineSpacing(this.mLegendLabelPaint, this.legendFontMetrics) + Utils.convertDpToPixel(this.mLegend.getYEntrySpace());
            float fCalcTextHeight = lineHeight - (Utils.calcTextHeight(this.mLegendLabelPaint, "ABC") / 2.0f);
            LegendEntry[] entries = this.mLegend.getEntries();
            float fConvertDpToPixel = Utils.convertDpToPixel(this.mLegend.getFormToTextSpace());
            float fConvertDpToPixel2 = Utils.convertDpToPixel(this.mLegend.getXEntrySpace());
            Legend.LegendOrientation orientation = this.mLegend.getOrientation();
            Legend.LegendHorizontalAlignment horizontalAlignment = this.mLegend.getHorizontalAlignment();
            Legend.LegendVerticalAlignment verticalAlignment = this.mLegend.getVerticalAlignment();
            Legend.LegendDirection direction = this.mLegend.getDirection();
            float fConvertDpToPixel3 = Utils.convertDpToPixel(this.mLegend.getFormSize());
            float fConvertDpToPixel4 = Utils.convertDpToPixel(this.mLegend.getStackSpace());
            float yOffset = this.mLegend.getYOffset();
            float xOffset = this.mLegend.getXOffset();
            int i3 = AnonymousClass1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[horizontalAlignment.ordinal()];
            float f13 = fConvertDpToPixel4;
            float f14 = fConvertDpToPixel2;
            if (i3 == 1) {
                f2 = lineHeight;
                f3 = lineSpacing;
                if (orientation != Legend.LegendOrientation.VERTICAL) {
                    xOffset += this.mViewPortHandler.contentLeft();
                }
                chartWidth = direction == Legend.LegendDirection.RIGHT_TO_LEFT ? xOffset + this.mLegend.mNeededWidth : xOffset;
            } else if (i3 == 2) {
                f2 = lineHeight;
                f3 = lineSpacing;
                chartWidth = (orientation == Legend.LegendOrientation.VERTICAL ? this.mViewPortHandler.getChartWidth() : this.mViewPortHandler.contentRight()) - xOffset;
                if (direction == Legend.LegendDirection.LEFT_TO_RIGHT) {
                    chartWidth -= this.mLegend.mNeededWidth;
                }
            } else if (i3 != 3) {
                f2 = lineHeight;
                f3 = lineSpacing;
                chartWidth = 0.0f;
            } else {
                Legend.LegendOrientation legendOrientation = Legend.LegendOrientation.VERTICAL;
                float chartWidth2 = orientation == legendOrientation ? this.mViewPortHandler.getChartWidth() / 2.0f : this.mViewPortHandler.contentLeft() + (this.mViewPortHandler.contentWidth() / 2.0f);
                Legend.LegendDirection legendDirection2 = Legend.LegendDirection.LEFT_TO_RIGHT;
                f3 = lineSpacing;
                chartWidth = chartWidth2 + (direction == legendDirection2 ? xOffset : -xOffset);
                if (orientation == legendOrientation) {
                    double d4 = chartWidth;
                    if (direction == legendDirection2) {
                        f2 = lineHeight;
                        d3 = ((-this.mLegend.mNeededWidth) / 2.0d) + xOffset;
                    } else {
                        f2 = lineHeight;
                        d3 = (this.mLegend.mNeededWidth / 2.0d) - xOffset;
                    }
                    chartWidth = (float) (d4 + d3);
                } else {
                    f2 = lineHeight;
                }
            }
            int i4 = AnonymousClass1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[orientation.ordinal()];
            if (i4 != 1) {
                if (i4 != 2) {
                    return;
                }
                int i5 = AnonymousClass1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[verticalAlignment.ordinal()];
                if (i5 == 1) {
                    fContentTop = (horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER ? 0.0f : this.mViewPortHandler.contentTop()) + yOffset;
                } else if (i5 == 2) {
                    fContentTop = (horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER ? this.mViewPortHandler.getChartHeight() : this.mViewPortHandler.contentBottom()) - (this.mLegend.mNeededHeight + yOffset);
                } else if (i5 != 3) {
                    fContentTop = 0.0f;
                } else {
                    float chartHeight = this.mViewPortHandler.getChartHeight() / 2.0f;
                    Legend legend = this.mLegend;
                    fContentTop = (chartHeight - (legend.mNeededHeight / 2.0f)) + legend.getYOffset();
                }
                float f15 = fContentTop;
                float f16 = 0.0f;
                boolean z2 = false;
                int i6 = 0;
                while (i6 < entries.length) {
                    LegendEntry legendEntry2 = entries[i6];
                    boolean z3 = legendEntry2.form != Legend.LegendForm.NONE;
                    float fConvertDpToPixel5 = Float.isNaN(legendEntry2.formSize) ? fConvertDpToPixel3 : Utils.convertDpToPixel(legendEntry2.formSize);
                    if (z3) {
                        Legend.LegendDirection legendDirection3 = Legend.LegendDirection.LEFT_TO_RIGHT;
                        fCalcTextWidth = direction == legendDirection3 ? chartWidth + f16 : chartWidth - (fConvertDpToPixel5 - f16);
                        f11 = fCalcTextHeight;
                        f12 = f13;
                        f10 = chartWidth;
                        legendDirection = direction;
                        drawForm(canvas, fCalcTextWidth, f15 + fCalcTextHeight, legendEntry2, this.mLegend);
                        if (legendDirection == legendDirection3) {
                            fCalcTextWidth += fConvertDpToPixel5;
                        }
                        legendEntry = legendEntry2;
                    } else {
                        f10 = chartWidth;
                        f11 = fCalcTextHeight;
                        f12 = f13;
                        legendDirection = direction;
                        legendEntry = legendEntry2;
                        fCalcTextWidth = f10;
                    }
                    if (legendEntry.label != null) {
                        if (z3 && !z2) {
                            fCalcTextWidth += legendDirection == Legend.LegendDirection.LEFT_TO_RIGHT ? fConvertDpToPixel : -fConvertDpToPixel;
                        } else if (z2) {
                            fCalcTextWidth = f10;
                        }
                        if (legendDirection == Legend.LegendDirection.RIGHT_TO_LEFT) {
                            fCalcTextWidth -= Utils.calcTextWidth(this.mLegendLabelPaint, r1);
                        }
                        float f17 = fCalcTextWidth;
                        if (z2) {
                            f15 += f2 + f3;
                            drawLabel(canvas, f17, f15 + f2, legendEntry.label);
                        } else {
                            drawLabel(canvas, f17, f15 + f2, legendEntry.label);
                        }
                        f15 += f2 + f3;
                        f16 = 0.0f;
                    } else {
                        f16 += fConvertDpToPixel5 + f12;
                        z2 = true;
                    }
                    i6++;
                    direction = legendDirection;
                    f13 = f12;
                    fCalcTextHeight = f11;
                    chartWidth = f10;
                }
                return;
            }
            float f18 = chartWidth;
            float f19 = f13;
            List<FSize> calculatedLineSizes = this.mLegend.getCalculatedLineSizes();
            List<FSize> calculatedLabelSizes = this.mLegend.getCalculatedLabelSizes();
            List<Boolean> calculatedLabelBreakPoints = this.mLegend.getCalculatedLabelBreakPoints();
            int i7 = AnonymousClass1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[verticalAlignment.ordinal()];
            if (i7 != 1) {
                yOffset = i7 != 2 ? i7 != 3 ? 0.0f : yOffset + ((this.mViewPortHandler.getChartHeight() - this.mLegend.mNeededHeight) / 2.0f) : (this.mViewPortHandler.getChartHeight() - yOffset) - this.mLegend.mNeededHeight;
            }
            int length = entries.length;
            float f20 = f18;
            int i8 = 0;
            int i9 = 0;
            while (i8 < length) {
                float f21 = f19;
                LegendEntry legendEntry3 = entries[i8];
                float f22 = f20;
                int i10 = length;
                boolean z4 = legendEntry3.form != Legend.LegendForm.NONE;
                float fConvertDpToPixel6 = Float.isNaN(legendEntry3.formSize) ? fConvertDpToPixel3 : Utils.convertDpToPixel(legendEntry3.formSize);
                if (i8 >= calculatedLabelBreakPoints.size() || !calculatedLabelBreakPoints.get(i8).booleanValue()) {
                    f4 = f22;
                    f5 = yOffset;
                } else {
                    f5 = yOffset + f2 + f3;
                    f4 = f18;
                }
                if (f4 == f18 && horizontalAlignment == Legend.LegendHorizontalAlignment.CENTER && i9 < calculatedLineSizes.size()) {
                    f4 += (direction == Legend.LegendDirection.RIGHT_TO_LEFT ? calculatedLineSizes.get(i9).width : -calculatedLineSizes.get(i9).width) / 2.0f;
                    i9++;
                }
                int i11 = i9;
                boolean z5 = legendEntry3.label == null;
                if (z4) {
                    if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f4 -= fConvertDpToPixel6;
                    }
                    float f23 = f4;
                    list2 = calculatedLineSizes;
                    i2 = i8;
                    list = calculatedLabelBreakPoints;
                    drawForm(canvas, f23, f5 + fCalcTextHeight, legendEntry3, this.mLegend);
                    f4 = direction == Legend.LegendDirection.LEFT_TO_RIGHT ? f23 + fConvertDpToPixel6 : f23;
                } else {
                    list = calculatedLabelBreakPoints;
                    list2 = calculatedLineSizes;
                    i2 = i8;
                }
                if (z5) {
                    f6 = f14;
                    if (direction == Legend.LegendDirection.RIGHT_TO_LEFT) {
                        f7 = f21;
                        f8 = -f7;
                    } else {
                        f7 = f21;
                        f8 = f7;
                    }
                    f20 = f4 + f8;
                } else {
                    if (z4) {
                        f4 += direction == Legend.LegendDirection.RIGHT_TO_LEFT ? -fConvertDpToPixel : fConvertDpToPixel;
                    }
                    Legend.LegendDirection legendDirection4 = Legend.LegendDirection.RIGHT_TO_LEFT;
                    if (direction == legendDirection4) {
                        f4 -= calculatedLabelSizes.get(i2).width;
                    }
                    drawLabel(canvas, f4, f5 + f2, legendEntry3.label);
                    if (direction == Legend.LegendDirection.LEFT_TO_RIGHT) {
                        f4 += calculatedLabelSizes.get(i2).width;
                    }
                    if (direction == legendDirection4) {
                        f6 = f14;
                        f9 = -f6;
                    } else {
                        f6 = f14;
                        f9 = f6;
                    }
                    f20 = f4 + f9;
                    f7 = f21;
                }
                f14 = f6;
                f19 = f7;
                i8 = i2 + 1;
                yOffset = f5;
                length = i10;
                i9 = i11;
                calculatedLineSizes = list2;
                calculatedLabelBreakPoints = list;
            }
        }
    }
}
