package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Rect;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class UnderlineTextView extends AppCompatTextView {
    private Paint paint;
    private Path path;
    private List<UnderLineOptions> underLineOptionsList;

    public UnderlineTextView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLine$0(UnderLineOptions underLineOptions) {
        if (addOptions(underLineOptions)) {
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLines$1(List list) {
        Iterator it = list.iterator();
        while (it.hasNext() && addOptions((UnderLineOptions) it.next())) {
        }
        invalidate();
    }

    private float[] measureXY(int offset) {
        Layout layout = getLayout();
        layout.getLineBounds(layout.getLineForOffset(offset), new Rect());
        return new float[]{layout.getPrimaryHorizontal(offset) + getPaddingLeft(), r3.top + getPaddingTop(), layout.getSecondaryHorizontal(offset) + getPaddingRight(), r3.bottom + getPaddingBottom()};
    }

    public boolean addOptions(UnderLineOptions underLineOptions) {
        char c3;
        int lineStart = underLineOptions.getLineStart();
        int lineEnd = underLineOptions.getLineEnd();
        if (lineStart > getText().toString().length() || lineEnd < 0) {
            return false;
        }
        if (lineStart < 0) {
            lineStart = 0;
        }
        if (lineEnd > getText().toString().length()) {
            lineEnd = getText().toString().length();
        }
        underLineOptions.setContent(getText().toString().substring(lineStart, lineEnd));
        if (underLineOptions.getClickableSpan() != null) {
            underLineOptions.getClickableSpan().setStart(lineStart);
            underLineOptions.getClickableSpan().setEnd(lineEnd);
            underLineOptions.getClickableSpan().setContent(getText().toString().substring(lineStart, lineEnd));
        }
        float[] fArrMeasureXY = measureXY(lineStart);
        float[] fArrMeasureXY2 = measureXY(lineEnd);
        ArrayList arrayList = new ArrayList();
        float f2 = 2.0f;
        char c4 = 3;
        if (fArrMeasureXY[1] == fArrMeasureXY2[1]) {
            arrayList.add(fArrMeasureXY);
            arrayList.add(fArrMeasureXY2);
            if (underLineOptions.getClickableSpan() != null) {
                float f3 = fArrMeasureXY[0];
                underLineOptions.getClickableSpan().setX((int) (f3 + ((fArrMeasureXY2[0] - f3) / 2.0f)));
                underLineOptions.getClickableSpan().setY((int) fArrMeasureXY[3]);
            }
            underLineOptions.setLineXYs(arrayList);
        } else {
            int lineForOffset = getLayout().getLineForOffset(lineStart);
            int lineForOffset2 = getLayout().getLineForOffset(lineEnd);
            int i2 = lineForOffset;
            while (i2 <= lineForOffset2) {
                Rect rect = new Rect();
                getLayout().getLineBounds(i2, rect);
                if (i2 == lineForOffset) {
                    float[] fArr = new float[4];
                    fArr[0] = getLayout().getLineMax(i2) + measureXY(getLayout().getLineStart(i2))[0];
                    fArr[1] = fArrMeasureXY[1];
                    fArr[2] = getLayout().getLineMax(i2) + measureXY(getLayout().getLineStart(i2))[2];
                    fArr[c4] = fArrMeasureXY[c4];
                    arrayList.add(fArrMeasureXY);
                    arrayList.add(fArr);
                    if (underLineOptions.getClickableSpan() != null) {
                        float f4 = fArrMeasureXY[0];
                        underLineOptions.getClickableSpan().setX((int) (f4 + ((fArr[0] - f4) / f2)));
                        underLineOptions.getClickableSpan().setY((int) fArrMeasureXY[c4]);
                    }
                } else if (i2 == lineForOffset2) {
                    float[] fArr2 = new float[4];
                    fArr2[0] = measureXY(getLayout().getLineStart(i2))[0];
                    fArr2[1] = fArrMeasureXY2[1];
                    fArr2[2] = measureXY(getLayout().getLineStart(i2))[2];
                    fArr2[c4] = fArrMeasureXY2[c4];
                    arrayList.add(fArr2);
                    arrayList.add(fArrMeasureXY2);
                } else {
                    getLayout().getLineBounds(i2, new Rect());
                    float[] fArr3 = new float[4];
                    fArr3[0] = measureXY(getLayout().getLineStart(i2))[0];
                    fArr3[1] = rect.top + getPaddingTop();
                    fArr3[2] = measureXY(getLayout().getLineStart(i2))[2];
                    fArr3[c4] = rect.bottom + getPaddingTop();
                    float paddingTop = rect.bottom + getPaddingTop();
                    c3 = 3;
                    float[] fArr4 = {getLayout().getLineMax(i2) + measureXY(getLayout().getLineStart(i2))[0], rect.top + getPaddingTop(), getLayout().getLineMax(i2) + measureXY(getLayout().getLineStart(i2))[2], paddingTop};
                    arrayList.add(fArr3);
                    arrayList.add(fArr4);
                    i2++;
                    c4 = c3;
                    f2 = 2.0f;
                }
                c3 = c4;
                i2++;
                c4 = c3;
                f2 = 2.0f;
            }
            underLineOptions.setLineXYs(arrayList);
        }
        this.underLineOptionsList.add(underLineOptions);
        return true;
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        this.underLineOptionsList.clear();
        super.onDetachedFromWindow();
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (UnderLineOptions underLineOptions : this.underLineOptionsList) {
            if (underLineOptions.getLineXYs() != null) {
                if (underLineOptions.getLineStyle() == 1) {
                    Path path = new Path();
                    path.addCircle(0.0f, 0.0f, 2.0f, Path.Direction.CCW);
                    this.paint.setPathEffect(new PathDashPathEffect(path, 6.0f, 1.0f, PathDashPathEffect.Style.ROTATE));
                } else {
                    this.paint.setPathEffect(null);
                }
                this.paint.setColor(underLineOptions.getLineColor());
                for (int i2 = 0; i2 < underLineOptions.getLineXYs().size(); i2++) {
                    Log.d("lixx", i2 + " xy->  " + underLineOptions.getLineXYs().get(i2)[0] + "," + underLineOptions.getLineXYs().get(i2)[1] + "," + underLineOptions.getLineXYs().get(i2)[2] + "," + underLineOptions.getLineXYs().get(i2)[3]);
                    if (i2 % 2 == 0) {
                        this.path.moveTo(underLineOptions.getLineXYs().get(i2)[0], underLineOptions.getLineXYs().get(i2)[3]);
                    } else {
                        this.path.lineTo(underLineOptions.getLineXYs().get(i2)[0], underLineOptions.getLineXYs().get(i2)[3]);
                        canvas.drawPath(this.path, this.paint);
                        this.path.reset();
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        if (((ClickableSpan[]) ((Spannable) getText()).getSpans(getSelectionStart(), getSelectionEnd(), ClickableSpan.class)).length > 0) {
            return false;
        }
        return super.performClick();
    }

    public void setLine(@NonNull final UnderLineOptions options) {
        post(new Runnable() { // from class: com.psychiatrygarden.widget.wi
            @Override // java.lang.Runnable
            public final void run() {
                this.f17058c.lambda$setLine$0(options);
            }
        });
    }

    public void setLines(@NonNull final List<UnderLineOptions> optionsList) {
        this.underLineOptionsList.clear();
        post(new Runnable() { // from class: com.psychiatrygarden.widget.xi
            @Override // java.lang.Runnable
            public final void run() {
                this.f17107c.lambda$setLines$1(optionsList);
            }
        });
    }

    public UnderlineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.underLineOptionsList = new ArrayList();
        this.path = new Path();
        Paint paint = new Paint();
        this.paint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(6.0f);
        setHighlightColor(0);
    }
}
