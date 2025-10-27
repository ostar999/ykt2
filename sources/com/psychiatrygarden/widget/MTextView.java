package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes6.dex */
public class MTextView extends TextView {
    ArrayList<LINE> contentList;
    private Context context;
    private DisplayMetrics displayMetrics;
    private float lineSpacing;
    private int lineSpacingDP;
    private float lineWidthMax;
    private int maxWidth;
    private int minHeight;
    private ArrayList<Object> obList;
    private int oneLineWidth;
    private TextPaint paint;
    private CharSequence text;
    private Paint textBgColorPaint;
    private Rect textBgColorRect;
    private int textColor;
    private boolean useDefault;
    private static HashMap<String, SoftReference<MeasuredData>> measuredData = new HashMap<>();
    private static int hashIndex = 0;

    public class LINE {
        public int height;
        public ArrayList<Object> line = new ArrayList<>();
        public ArrayList<Integer> widthList = new ArrayList<>();

        public LINE() {
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("height:" + this.height + "   ");
            for (int i2 = 0; i2 < this.line.size(); i2++) {
                sb.append(this.line.get(i2) + ":" + this.widthList.get(i2));
            }
            return sb.toString();
        }
    }

    public class MeasuredData {
        ArrayList<LINE> contentList;
        public int hashIndex;
        public float lineWidthMax;
        public int measuredHeight;
        public int oneLineWidth;
        public float textSize;
        public int width;

        public MeasuredData() {
        }
    }

    public class SpanObject {
        public int end;
        public CharSequence source;
        public Object span;
        public int start;

        public SpanObject() {
        }
    }

    public class SpanObjectComparator implements Comparator<SpanObject> {
        public SpanObjectComparator() {
        }

        @Override // java.util.Comparator
        public int compare(SpanObject lhs, SpanObject rhs) {
            return lhs.start - rhs.start;
        }
    }

    public MTextView(Context context) {
        super(context);
        this.contentList = new ArrayList<>();
        this.paint = new TextPaint();
        this.textColor = -16777216;
        this.lineSpacingDP = 5;
        this.oneLineWidth = -1;
        this.lineWidthMax = -1.0f;
        this.obList = new ArrayList<>();
        this.useDefault = false;
        this.text = "";
        this.textBgColorPaint = new Paint();
        this.textBgColorRect = new Rect();
        this.context = context;
        this.paint.setAntiAlias(true);
        this.lineSpacing = dip2px(context, this.lineSpacingDP);
        this.minHeight = dip2px(context, 30.0f);
        this.displayMetrics = new DisplayMetrics();
    }

    private void cacheData(int width, int height) {
        MeasuredData measuredData2 = new MeasuredData();
        measuredData2.contentList = (ArrayList) this.contentList.clone();
        measuredData2.textSize = getTextSize();
        measuredData2.lineWidthMax = this.lineWidthMax;
        measuredData2.oneLineWidth = this.oneLineWidth;
        measuredData2.measuredHeight = height;
        measuredData2.width = width;
        int i2 = hashIndex + 1;
        hashIndex = i2;
        measuredData2.hashIndex = i2;
        for (int i3 = 0; i3 < this.contentList.size(); i3++) {
            this.contentList.get(i3).toString();
        }
        measuredData.put(this.text.toString(), new SoftReference<>(measuredData2));
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private int getCachedData(String text, int width) {
        MeasuredData measuredData2;
        SoftReference<MeasuredData> softReference = measuredData.get(text);
        if (softReference == null || (measuredData2 = softReference.get()) == null || measuredData2.textSize != getTextSize() || width != measuredData2.width) {
            return -1;
        }
        this.lineWidthMax = measuredData2.lineWidthMax;
        this.contentList = (ArrayList) measuredData2.contentList.clone();
        this.oneLineWidth = measuredData2.oneLineWidth;
        for (int i2 = 0; i2 < this.contentList.size(); i2++) {
            this.contentList.get(i2).toString();
        }
        return measuredData2.measuredHeight;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x019e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int measureContentHeight(int r21) {
        /*
            Method dump skipped, instructions count: 487
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.MTextView.measureContentHeight(int):int");
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public int getLineSpacingDP() {
        return this.lineSpacingDP;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        if (this.useDefault) {
            super.onDraw(canvas);
            return;
        }
        if (this.contentList.isEmpty()) {
            return;
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        float compoundPaddingTop = getCompoundPaddingTop() + 0 + this.lineSpacing;
        if (this.oneLineWidth != -1) {
            compoundPaddingTop = (getMeasuredHeight() / 2) - (this.contentList.get(0).height / 2);
        }
        Iterator<LINE> it = this.contentList.iterator();
        while (it.hasNext()) {
            LINE next = it.next();
            float f2 = compoundPaddingLeft;
            for (int i2 = 0; i2 < next.line.size(); i2++) {
                Object obj = next.line.get(i2);
                int iIntValue = next.widthList.get(i2).intValue();
                if (obj instanceof String) {
                    canvas.drawText((String) obj, f2, (next.height + compoundPaddingTop) - this.paint.getFontMetrics().descent, this.paint);
                } else {
                    if (obj instanceof SpanObject) {
                        SpanObject spanObject = (SpanObject) obj;
                        Object obj2 = spanObject.span;
                        if (obj2 instanceof ImageSpan) {
                            Drawable drawable = ((ImageSpan) obj2).getDrawable();
                            f2 += iIntValue;
                            drawable.setBounds((int) f2, (int) compoundPaddingTop, (int) f2, (int) (next.height + compoundPaddingTop));
                            drawable.draw(canvas);
                        } else if (obj2 instanceof BackgroundColorSpan) {
                            this.textBgColorPaint.setColor(((BackgroundColorSpan) obj2).getBackgroundColor());
                            this.textBgColorPaint.setStyle(Paint.Style.FILL);
                            this.textBgColorRect.left = (int) f2;
                            this.textBgColorRect.top = (int) (((next.height + compoundPaddingTop) - ((int) getTextSize())) - this.paint.getFontMetrics().descent);
                            Rect rect = this.textBgColorRect;
                            rect.right = rect.left + iIntValue;
                            rect.bottom = (int) (((next.height + compoundPaddingTop) + this.lineSpacing) - this.paint.getFontMetrics().descent);
                            canvas.drawRect(this.textBgColorRect, this.textBgColorPaint);
                            canvas.drawText(spanObject.source.toString(), f2, (next.height + compoundPaddingTop) - this.paint.getFontMetrics().descent, this.paint);
                        } else {
                            canvas.drawText(spanObject.source.toString(), f2, (next.height + compoundPaddingTop) - this.paint.getFontMetrics().descent, this.paint);
                        }
                    }
                }
                f2 += iIntValue;
            }
            compoundPaddingTop += next.height + this.lineSpacing;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.useDefault) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
                size = this.displayMetrics.widthPixels;
            } else if (mode != 1073741824) {
                size = 0;
            }
        }
        int i2 = this.maxWidth;
        if (i2 > 0) {
            size = Math.min(size, i2);
        }
        this.paint.setTextSize(getTextSize());
        this.paint.setColor(this.textColor);
        int iMeasureContentHeight = measureContentHeight(size);
        int iMin = Math.min(size, ((int) this.lineWidthMax) + getCompoundPaddingLeft() + getCompoundPaddingRight());
        int i3 = this.oneLineWidth;
        if (i3 > -1) {
            iMin = i3;
        }
        if (mode2 == Integer.MIN_VALUE || mode2 == 0) {
            size2 = iMeasureContentHeight;
        } else if (mode2 != 1073741824) {
            size2 = 0;
        }
        setMeasuredDimension(iMin, Math.max(size2 + getCompoundPaddingTop() + getCompoundPaddingBottom(), this.minHeight));
    }

    public void setLineSpacingDP(int lineSpacingDP) {
        this.lineSpacingDP = lineSpacingDP;
        this.lineSpacing = dip2px(this.context, lineSpacingDP);
    }

    public void setMText(CharSequence cs) {
        this.text = cs;
        this.obList.clear();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        this.useDefault = false;
        if (cs instanceof SpannableString) {
            SpannableString spannableString = (SpannableString) cs;
            CharacterStyle[] characterStyleArr = (CharacterStyle[]) spannableString.getSpans(0, spannableString.length(), CharacterStyle.class);
            for (int i3 = 0; i3 < characterStyleArr.length; i3++) {
                int spanStart = spannableString.getSpanStart(characterStyleArr[i3]);
                int spanEnd = spannableString.getSpanEnd(characterStyleArr[i3]);
                SpanObject spanObject = new SpanObject();
                spanObject.span = characterStyleArr[i3];
                spanObject.start = spanStart;
                spanObject.end = spanEnd;
                spanObject.source = spannableString.subSequence(spanStart, spanEnd);
                arrayList.add(spanObject);
            }
        }
        int size = arrayList.size();
        SpanObject[] spanObjectArr = new SpanObject[size];
        arrayList.toArray(spanObjectArr);
        Arrays.sort(spanObjectArr, 0, size, new SpanObjectComparator());
        arrayList.clear();
        for (int i4 = 0; i4 < size; i4++) {
            arrayList.add(spanObjectArr[i4]);
        }
        String string = cs.toString();
        int i5 = 0;
        while (i2 < cs.length()) {
            if (i5 < arrayList.size()) {
                SpanObject spanObject2 = (SpanObject) arrayList.get(i5);
                int i6 = spanObject2.start;
                if (i2 < i6) {
                    Integer numValueOf = Integer.valueOf(string.codePointAt(i2));
                    i2 = Character.isSupplementaryCodePoint(numValueOf.intValue()) ? i2 + 2 : i2 + 1;
                    this.obList.add(new String(Character.toChars(numValueOf.intValue())));
                } else if (i2 >= i6) {
                    this.obList.add(spanObject2);
                    i5++;
                    i2 = spanObject2.end;
                }
            } else {
                Integer numValueOf2 = Integer.valueOf(string.codePointAt(i2));
                i2 = Character.isSupplementaryCodePoint(numValueOf2.intValue()) ? i2 + 2 : i2 + 1;
                this.obList.add(new String(Character.toChars(numValueOf2.intValue())));
            }
        }
        requestLayout();
    }

    @Override // android.widget.TextView
    public void setMaxWidth(int maxpixels) {
        super.setMaxWidth(maxpixels);
        this.maxWidth = maxpixels;
    }

    @Override // android.widget.TextView
    public void setMinHeight(int minHeight) {
        super.setMinHeight(minHeight);
        this.minHeight = minHeight;
    }

    @Override // android.widget.TextView
    public void setTextColor(int color) {
        super.setTextColor(color);
        this.textColor = color;
    }

    public void setUseDefault(boolean useDefault) {
        this.useDefault = useDefault;
        if (useDefault) {
            setText(this.text);
            setTextColor(this.textColor);
        }
    }

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.contentList = new ArrayList<>();
        this.paint = new TextPaint();
        this.textColor = -16777216;
        this.lineSpacingDP = 5;
        this.oneLineWidth = -1;
        this.lineWidthMax = -1.0f;
        this.obList = new ArrayList<>();
        this.useDefault = false;
        this.text = "";
        this.textBgColorPaint = new Paint();
        this.textBgColorRect = new Rect();
        this.context = context;
        this.paint.setAntiAlias(true);
        this.lineSpacing = dip2px(context, this.lineSpacingDP);
        this.minHeight = dip2px(context, 30.0f);
        this.displayMetrics = new DisplayMetrics();
    }
}
