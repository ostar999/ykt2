package com.psychiatrygarden.widget.english;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.psychiatrygarden.bean.WordSentenceBean;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StringUtil;
import com.umeng.analytics.pro.am;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class EnglishTextView extends View {
    private int colorClickText;
    private int colorClickTextBg;
    private int colorDashLine;
    private int colorGray;
    private int colorText;
    private boolean drawDashLineAble;
    private boolean mBold;
    private int mClickRectHeight;
    private Paint mClickTextPaint;
    private String mClickWord;
    private boolean mClickable;
    private Map<RectF, String> mClickedRectList;
    private float mCornersRadius;
    private Paint mDashLinePaint;
    private boolean mDrawLineAble;
    private Paint.FontMetrics mFontMetrics;
    private Map<String, Map<RectF, String>> mGoodSentenceLines;
    private float mItalicSize;
    private Paint mItalicTextPaint;
    private Paint mLinePaint;
    private float mLineSpace;
    private OnWordClickListener mListener;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private float[] mRawY;
    private Paint mRectPaint;
    private List<Entry> mRows;
    private float mStartX;
    private float mStartY;
    private String mText;
    private int mTextColor;
    private float mTextHeight;
    private Paint mTextPaint;
    private int mTextSize;
    private int mWidth;
    private List<WordSentenceBean> mWordColor;
    private List<WordSentenceBean> mWordSentenceBeans;
    private List<WordSentenceBean> mWordSentenceBeansItalic;

    public static class Entry {
        private String key;
        private RectF value;

        public Entry(String sentence, RectF rectF) {
            this.key = sentence;
            this.value = rectF;
        }

        public String getKey() {
            return this.key;
        }

        public RectF getValue() {
            return this.value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(RectF value) {
            this.value = value;
        }
    }

    public interface OnWordClickListener {
        void onDataInitListener(Map<String, Map<RectF, String>> sentenceLines);

        void onWordClickListener(Map<RectF, String> rectFStringMap, String word);
    }

    public EnglishTextView(Context context) {
        this(context, null);
    }

    private float appendWord(float usedWidth, StringBuilder sb, Object word, float wordWidth) {
        sb.append(word);
        return usedWidth + wordWidth;
    }

    private void clickOther(MotionEvent event) {
        for (Entry entry : this.mRows) {
            String key = entry.getKey();
            RectF value = entry.getValue();
            if (value.contains(this.mStartX, this.mStartY)) {
                searchWord(value, key, this.mStartX - this.mPaddingLeft, event);
                return;
            }
        }
    }

    private void drawClickedRect(Canvas canvas) {
        Map<RectF, String> map = this.mClickedRectList;
        if (map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<RectF, String> entry : this.mClickedRectList.entrySet()) {
            RectF key = entry.getKey();
            String value = entry.getValue();
            float f2 = this.mCornersRadius;
            canvas.drawRoundRect(key, f2, f2, this.mRectPaint);
            float f3 = key.left;
            float f4 = (key.top + key.bottom) / 2.0f;
            Paint.FontMetrics fontMetrics = this.mFontMetrics;
            float f5 = fontMetrics.descent;
            canvas.drawText(value, f3, (f4 + ((f5 - fontMetrics.ascent) / 2.0f)) - f5, this.mClickTextPaint);
            this.mClickTextPaint.setTextSkewX(0.0f);
        }
    }

    private void drawText(Canvas canvas) {
        this.mLinePaint.setStrokeWidth(ScreenUtil.getPxByDp(getContext(), 2));
        int i2 = 0;
        int length = 0;
        for (Entry entry : this.mRows) {
            String key = entry.getKey();
            RectF value = entry.getValue();
            this.mTextPaint.setTextSkewX(0.0f);
            float f2 = value.left;
            float f3 = (value.top + value.bottom) / 2.0f;
            Paint.FontMetrics fontMetrics = this.mFontMetrics;
            float f4 = fontMetrics.descent;
            canvas.drawText(key, f2, (f3 + ((f4 - fontMetrics.ascent) / 2.0f)) - f4, this.mTextPaint);
            try {
                if (this.mWordSentenceBeansItalic.size() > 0) {
                    this.mTextPaint.setTextSkewX(this.mItalicSize);
                    this.mTextPaint.setColor(getContext().getResources().getColor(this.colorGray));
                    for (int i3 = i2; i3 < this.mWordSentenceBeansItalic.size(); i3++) {
                        StringBuilder sb = new StringBuilder();
                        for (int i4 = i2; i4 < key.length(); i4++) {
                            int i5 = length + i4;
                            if (i5 < this.mWordSentenceBeansItalic.get(i3).getCharEndIndex() && this.mWordSentenceBeansItalic.get(i3).getCharStartIndex() <= i5) {
                                sb.append(key.charAt(i4));
                                if (i4 == key.length() - 1) {
                                    RectF rectF = new RectF(value.left + this.mTextPaint.measureText(key.substring(i2, (i4 - sb.length()) + 1)), value.top, value.left + this.mTextPaint.measureText(key.substring(i2, i4 + 1)), value.bottom);
                                    canvas.drawRect(rectF, this.mRectPaint);
                                    List<RectF> rectFS = this.mWordSentenceBeansItalic.get(i3).getRectFS();
                                    if (rectFS == null) {
                                        rectFS = new ArrayList<>();
                                    }
                                    rectFS.add(rectF);
                                    this.mWordSentenceBeansItalic.get(i3).setRectFS(rectFS);
                                    String string = sb.toString();
                                    float fMeasureText = value.left + this.mTextPaint.measureText(key.substring(i2, (i4 - sb.length()) + 1));
                                    float f5 = (value.top + value.bottom) / 2.0f;
                                    Paint.FontMetrics fontMetrics2 = this.mFontMetrics;
                                    float f6 = fontMetrics2.descent;
                                    canvas.drawText(string, fMeasureText, (f5 + ((f6 - fontMetrics2.ascent) / 2.0f)) - f6, this.mTextPaint);
                                    sb.delete(i2, sb.length());
                                }
                            } else if (sb.length() > 0) {
                                RectF rectF2 = new RectF(value.left + this.mTextPaint.measureText(key.substring(i2, i4 - sb.length())), value.top, value.left + this.mTextPaint.measureText(key.substring(i2, i4)), value.bottom);
                                canvas.drawRect(rectF2, this.mRectPaint);
                                List<RectF> rectFS2 = this.mWordSentenceBeansItalic.get(i3).getRectFS();
                                if (rectFS2 == null) {
                                    rectFS2 = new ArrayList<>();
                                }
                                rectFS2.add(rectF2);
                                this.mWordSentenceBeansItalic.get(i3).setRectFS(rectFS2);
                                String string2 = sb.toString();
                                float fMeasureText2 = value.left + this.mTextPaint.measureText(key.substring(i2, i4 - sb.length()));
                                float f7 = (value.top + value.bottom) / 2.0f;
                                Paint.FontMetrics fontMetrics3 = this.mFontMetrics;
                                float f8 = fontMetrics3.descent;
                                canvas.drawText(string2, fMeasureText2, (f7 + ((f8 - fontMetrics3.ascent) / 2.0f)) - f8, this.mTextPaint);
                                sb.delete(i2, sb.length());
                            }
                        }
                    }
                    this.mTextPaint.setTextSkewX(0.0f);
                    this.mTextPaint.setColor(this.mTextColor);
                }
            } catch (Exception unused) {
            }
            try {
                if (this.mWordSentenceBeans.size() > 0) {
                    int i6 = i2;
                    while (i6 < this.mWordSentenceBeans.size()) {
                        int i7 = i2;
                        while (i7 < key.length()) {
                            int i8 = length + i7;
                            if (i8 < this.mWordSentenceBeans.get(i6).getCharEndIndex() && this.mWordSentenceBeans.get(i6).getCharStartIndex() <= i8) {
                                char cCharAt = key.charAt(i7);
                                float fMeasureText3 = value.left + this.mTextPaint.measureText(key.substring(i2, i7));
                                float f9 = (value.top + value.bottom) / 2.0f;
                                Paint.FontMetrics fontMetrics4 = this.mFontMetrics;
                                float f10 = fontMetrics4.descent;
                                float strokeWidth = ((f9 + ((f10 - fontMetrics4.ascent) / 2.0f)) - f10) + fontMetrics4.bottom + (this.mLinePaint.getStrokeWidth() * 2.0f);
                                float fMeasureText4 = value.left + this.mTextPaint.measureText(key.substring(i2, i7) + cCharAt);
                                float f11 = (value.top + value.bottom) / 2.0f;
                                Paint.FontMetrics fontMetrics5 = this.mFontMetrics;
                                float f12 = fontMetrics5.descent;
                                canvas.drawLine(fMeasureText3, strokeWidth, fMeasureText4, (this.mLinePaint.getStrokeWidth() * 2.0f) + ((f11 + ((f12 - fontMetrics5.ascent) / 2.0f)) - f12) + fontMetrics5.bottom, this.mLinePaint);
                            }
                            i7++;
                            i2 = 0;
                        }
                        i6++;
                        i2 = 0;
                    }
                }
            } catch (Exception unused2) {
            }
            if (this.mWordColor.size() > 0) {
                for (int i9 = 0; i9 < this.mWordColor.size(); i9++) {
                    this.mTextPaint.setColor(Color.parseColor(this.mWordColor.get(i9).getWordColor()));
                    StringBuilder sb2 = new StringBuilder();
                    for (int i10 = 0; i10 < key.length(); i10++) {
                        int i11 = length + i10;
                        if (i11 < this.mWordColor.get(i9).getCharEndIndex() && this.mWordColor.get(i9).getCharStartIndex() <= i11) {
                            sb2.append(key.charAt(i10));
                            if (i10 == key.length() - 1) {
                                try {
                                    try {
                                        RectF rectF3 = new RectF(value.left + this.mTextPaint.measureText(key.substring(0, (i10 - sb2.length()) + 1)), value.top, value.left + this.mTextPaint.measureText(key.substring(0, i10 + 1)), value.bottom);
                                        canvas.drawRect(rectF3, this.mRectPaint);
                                        List<RectF> rectFS3 = this.mWordColor.get(i9).getRectFS();
                                        if (rectFS3 == null) {
                                            rectFS3 = new ArrayList<>();
                                        }
                                        rectFS3.add(rectF3);
                                        this.mWordColor.get(i9).setRectFS(rectFS3);
                                        String string3 = sb2.toString();
                                        float fMeasureText5 = value.left + this.mTextPaint.measureText(key.substring(0, (i10 - sb2.length()) + 1));
                                        float f13 = (value.top + value.bottom) / 2.0f;
                                        Paint.FontMetrics fontMetrics6 = this.mFontMetrics;
                                        float f14 = fontMetrics6.descent;
                                        canvas.drawText(string3, fMeasureText5, (f13 + ((f14 - fontMetrics6.ascent) / 2.0f)) - f14, this.mTextPaint);
                                        try {
                                            sb2.delete(0, sb2.length());
                                        } catch (Exception unused3) {
                                            i2 = 0;
                                        }
                                    } catch (Exception unused4) {
                                        i2 = 0;
                                    }
                                } catch (Exception unused5) {
                                    i2 = 0;
                                }
                            }
                            i2 = 0;
                        } else if (sb2.length() > 0) {
                            try {
                                RectF rectF4 = new RectF(value.left + this.mTextPaint.measureText(key.substring(0, i10 - sb2.length())), value.top, value.left + this.mTextPaint.measureText(key.substring(0, i10)), value.bottom);
                                canvas.drawRect(rectF4, this.mRectPaint);
                                List<RectF> rectFS4 = this.mWordColor.get(i9).getRectFS();
                                if (rectFS4 == null) {
                                    rectFS4 = new ArrayList<>();
                                }
                                rectFS4.add(rectF4);
                                this.mWordColor.get(i9).setRectFS(rectFS4);
                                String string4 = sb2.toString();
                                float fMeasureText6 = value.left + this.mTextPaint.measureText(key.substring(0, i10 - sb2.length()));
                                float f15 = (value.top + value.bottom) / 2.0f;
                                Paint.FontMetrics fontMetrics7 = this.mFontMetrics;
                                float f16 = fontMetrics7.descent;
                                canvas.drawText(string4, fMeasureText6, (f15 + ((f16 - fontMetrics7.ascent) / 2.0f)) - f16, this.mTextPaint);
                                i2 = 0;
                                try {
                                    sb2.delete(0, sb2.length());
                                } catch (Exception unused6) {
                                }
                            } catch (Exception unused7) {
                                i2 = 0;
                            }
                        } else {
                            i2 = 0;
                        }
                    }
                }
                i2 = 0;
                this.mTextPaint.setColor(this.mTextColor);
            } else {
                i2 = 0;
            }
            length += key.length();
            if (this.drawDashLineAble) {
                float f17 = value.left;
                float f18 = (value.top + value.bottom) / 2.0f;
                Paint.FontMetrics fontMetrics8 = this.mFontMetrics;
                float f19 = fontMetrics8.descent;
                float strokeWidth2 = (this.mDashLinePaint.getStrokeWidth() * 4.0f) + ((f18 + ((f19 - fontMetrics8.ascent) / 2.0f)) - f19) + fontMetrics8.bottom;
                float f20 = value.right;
                float f21 = (value.top + value.bottom) / 2.0f;
                Paint.FontMetrics fontMetrics9 = this.mFontMetrics;
                float f22 = fontMetrics9.descent;
                canvas.drawLine(f17, strokeWidth2, f20, (this.mDashLinePaint.getStrokeWidth() * 4.0f) + ((f21 + ((f22 - fontMetrics9.ascent) / 2.0f)) - f22) + fontMetrics9.bottom, this.mDashLinePaint);
            }
        }
    }

    private int getBeginIndex(String text, String wordSentence) {
        while (!text.endsWith(wordSentence)) {
            wordSentence = wordSentence.substring(0, wordSentence.length() - 1);
            if (wordSentence.length() == 0) {
                break;
            }
        }
        return text.length() - wordSentence.length();
    }

    private float getViewHeight() {
        float fMeasureText;
        if (this.mRows == null) {
            this.mRows = new ArrayList();
        }
        if (this.mRows.size() > 0) {
            this.mRows.clear();
        }
        String str = this.mText;
        if (str == null || str.length() <= 0) {
            return 0.0f;
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        float f2 = this.mPaddingTop;
        float f3 = this.mPaddingLeft + 1.0f;
        float fAppendWord = 0.0f;
        int i2 = 0;
        for (int i3 = 0; i3 < this.mText.length(); i3++) {
            char cCharAt = this.mText.charAt(i3);
            if (StringUtil.isLetter(cCharAt)) {
                sb.append(cCharAt);
            } else if (cCharAt == '\n') {
                LogUtils.e("换行符", sb.toString() + " " + sb2.toString());
                if (sb.length() > 0) {
                    sb2.append((CharSequence) sb);
                    sb.delete(0, sb.length());
                }
                trimString(sb2, false, f3, f2);
                f2 += this.mTextHeight + this.mLineSpace;
                sb2.delete(0, sb2.length());
                i2++;
                fAppendWord = 0.0f;
            } else {
                if (sb.length() > 0) {
                    sb.append(cCharAt);
                    fMeasureText = this.mTextPaint.measureText(sb.toString());
                } else {
                    fMeasureText = this.mTextPaint.measureText(String.valueOf(cCharAt));
                }
                if (fAppendWord + fMeasureText <= this.mWidth) {
                    fAppendWord = appendWord(fAppendWord, sb2, sb.length() > 0 ? sb : Character.valueOf(cCharAt), fMeasureText);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb.length() > 0 ? sb : Character.valueOf(cCharAt));
                    sb3.append("");
                    LogUtils.e("小于行宽", sb3.toString());
                    if (sb.length() > 0) {
                        sb.delete(0, sb.length());
                    }
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(sb.length() > 0 ? sb : Character.valueOf(cCharAt));
                    sb4.append("");
                    LogUtils.e("大于于行宽", sb4.toString());
                    trimString(sb2, true, f3, f2);
                    f2 += this.mTextHeight + this.mLineSpace;
                    sb2.delete(0, sb2.length());
                    fAppendWord = appendWord(0.0f, sb2, sb.length() > 0 ? sb : Character.valueOf(cCharAt), fMeasureText);
                    if (sb.length() > 0) {
                        sb.delete(0, sb.length());
                    }
                    i2++;
                    LogUtils.e("大于行宽", sb2.toString());
                }
            }
        }
        if (sb.length() > 0) {
            if (fAppendWord + this.mTextPaint.measureText(sb.toString()) <= this.mWidth) {
                sb2.append((CharSequence) sb);
            } else {
                trimString(sb2, false, f3, f2);
                sb2.delete(0, sb2.length());
                f2 += this.mTextHeight + this.mLineSpace;
                sb2.append((CharSequence) sb);
                i2++;
            }
        }
        if (sb2.length() > 0) {
            LogUtils.e("最后剩下的一行", sb2.toString());
            trimString(sb2, false, f3, f2);
            i2++;
        }
        OnWordClickListener onWordClickListener = this.mListener;
        if (onWordClickListener != null) {
            onWordClickListener.onDataInitListener(this.mGoodSentenceLines);
        }
        return (i2 * this.mTextHeight) + (this.mLineSpace * (i2 - 1)) + this.mPaddingBottom + this.mPaddingTop + 2.0f;
    }

    private void init() {
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setAntiAlias(true);
        this.mRectPaint.setStyle(Paint.Style.FILL);
        this.mRectPaint.setColor(this.colorClickTextBg);
        Paint paint2 = new Paint();
        this.mLinePaint = paint2;
        paint2.setAntiAlias(true);
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setColor(this.colorClickText);
        Paint paint3 = new Paint();
        this.mDashLinePaint = paint3;
        paint3.setAntiAlias(true);
        this.mDashLinePaint.setStyle(Paint.Style.STROKE);
        this.mDashLinePaint.setColor(getResources().getColor(this.colorDashLine));
        this.mDashLinePaint.setStrokeWidth(ScreenUtil.getPxByDp(getContext(), 1));
        this.mDashLinePaint.setPathEffect(new DashPathEffect(new float[]{15.0f, 10.0f}, 0.0f));
        Paint paint4 = new Paint();
        this.mTextPaint = paint4;
        paint4.setTextSize(this.mTextSize);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setColor(this.mTextColor);
        this.mTextPaint.setFakeBoldText(this.mBold);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setLetterSpacing(0.03f);
        Paint paint5 = new Paint();
        this.mClickTextPaint = paint5;
        paint5.setTextSize(this.mTextSize);
        this.mClickTextPaint.setAntiAlias(true);
        this.mClickTextPaint.setColor(this.colorClickText);
        this.mClickTextPaint.setFakeBoldText(this.mBold);
        this.mClickTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mClickTextPaint.setLetterSpacing(0.03f);
        Paint paint6 = new Paint();
        this.mItalicTextPaint = paint6;
        paint6.setTextSize(this.mTextSize);
        this.mItalicTextPaint.setAntiAlias(true);
        this.mItalicTextPaint.setColor(this.mTextColor);
        this.mItalicTextPaint.setFakeBoldText(this.mBold);
        this.mItalicTextPaint.setTextSkewX(this.mItalicSize);
        this.mItalicTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mItalicTextPaint.setLetterSpacing(0.03f);
        this.mCornersRadius = ScreenUtil.getPxByDp(getContext(), 0);
        Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
        this.mFontMetrics = fontMetrics;
        float fAbs = Math.abs(fontMetrics.top - fontMetrics.bottom);
        this.mTextHeight = fAbs;
        this.mClickRectHeight = (int) fAbs;
        if (this.mClickedRectList == null) {
            this.mClickedRectList = new HashMap();
        }
        if (this.mRawY == null) {
            this.mRawY = new float[2];
        }
        if (this.mClickable) {
            setFocusable(true);
        }
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        this.mDrawLineAble = typedArrayObtainStyledAttributes.getBoolean(6, true);
        this.drawDashLineAble = typedArrayObtainStyledAttributes.getBoolean(5, false);
        this.mClickable = typedArrayObtainStyledAttributes.getBoolean(2, false);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(3, getResources().getColor(this.colorText));
        this.colorClickText = typedArrayObtainStyledAttributes.getColor(1, getResources().getColor(R.color.FFF7D04A));
        this.colorClickTextBg = typedArrayObtainStyledAttributes.getColor(0, getResources().getColor(R.color.white));
        this.mTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(4, 50);
        this.mLineSpace = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mText = typedArrayObtainStyledAttributes.getString(8);
        typedArrayObtainStyledAttributes.recycle();
        init();
    }

    private void searchWord(RectF rectF, String row, float startX, MotionEvent event) {
        int length = row.length() - 1;
        if (startX < 0.0f || startX > this.mTextPaint.measureText(row) || length < 0) {
            return;
        }
        int i2 = 0;
        while (i2 <= length) {
            int i3 = (i2 + length) / 2;
            float fMeasureText = this.mTextPaint.measureText(row.substring(0, i3));
            int i4 = i3 + 1;
            float fMeasureText2 = this.mTextPaint.measureText(row.substring(0, i4));
            if (startX < fMeasureText) {
                length = i3 - 1;
            } else {
                if (startX <= fMeasureText2) {
                    if (StringUtil.isLetter(row.substring(i3, i4).charAt(0))) {
                        int i5 = i3 - 1;
                        if (i5 > 0) {
                            while (i5 >= 0 && StringUtil.isLetter(row.charAt(i5))) {
                                i5--;
                            }
                        }
                        if (i4 <= row.length() - 1) {
                            while (i4 <= row.length() - 1 && StringUtil.isLetter(row.charAt(i4))) {
                                i4++;
                            }
                        }
                        String strSubstring = row.substring(i5 == 0 ? 0 : i5 + 1, i4);
                        String strSubstring2 = row.substring(0, i5 == 0 ? 0 : i5 + 1);
                        float fMeasureText3 = this.mTextPaint.measureText(strSubstring);
                        float fMeasureText4 = this.mTextPaint.measureText(strSubstring2);
                        float f2 = this.mPaddingLeft + fMeasureText4;
                        float fHeight = rectF.top + ((rectF.height() - this.mClickRectHeight) / 2.0f);
                        float f3 = this.mPaddingLeft + fMeasureText4 + fMeasureText3;
                        float f4 = rectF.top;
                        float fHeight2 = rectF.height();
                        int i6 = this.mClickRectHeight;
                        RectF rectF2 = new RectF(f2, fHeight, f3, f4 + ((fHeight2 - i6) / 2.0f) + i6);
                        this.mClickedRectList.put(rectF2, strSubstring);
                        this.mRawY[0] = event.getRawY() - (this.mStartY - rectF.top);
                        this.mRawY[1] = event.getRawY() + (rectF.bottom - this.mStartY);
                        OnWordClickListener onWordClickListener = this.mListener;
                        if (onWordClickListener != null) {
                            this.mClickWord = strSubstring;
                            onWordClickListener.onWordClickListener(this.mClickedRectList, strSubstring);
                            for (int i7 = 0; i7 < this.mWordSentenceBeansItalic.size(); i7++) {
                                int i8 = 0;
                                while (true) {
                                    if (i8 >= this.mWordSentenceBeansItalic.get(i7).getRectFS().size()) {
                                        break;
                                    }
                                    if (this.mWordSentenceBeansItalic.get(i7).getRectFS().get(i8).contains(rectF2.centerX(), rectF2.centerY())) {
                                        this.mClickTextPaint.setTextSkewX(this.mItalicSize);
                                        break;
                                    }
                                    i8++;
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                i2 = i4;
            }
        }
    }

    private void trimString(StringBuilder sb, boolean full, float startL, float startT) {
        String string = sb.toString();
        this.mRows.add(new Entry(string, new RectF(startL - 2.0f, startT, (startL + this.mTextPaint.measureText(string)) - 2.0f, this.mTextHeight + startT)));
    }

    public float[] getRawY() {
        return this.mRawY;
    }

    public float getTextSize() {
        return this.mTextSize;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        drawText(canvas);
        if (this.mClickable) {
            drawClickedRect(canvas);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int viewHeight = (int) getViewHeight();
        this.mPaddingLeft = getPaddingLeft();
        this.mPaddingRight = getPaddingRight();
        this.mPaddingTop = getPaddingTop();
        this.mPaddingBottom = getPaddingBottom();
        this.mWidth = (measuredWidth - this.mPaddingLeft) - this.mPaddingRight;
        setMeasuredDimension(measuredWidth, viewHeight);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mClickable) {
            int action = event.getAction();
            if (action == 0) {
                this.mStartX = event.getX();
                this.mStartY = event.getY();
                return true;
            }
            if (action == 1) {
                float x2 = event.getX();
                float y2 = event.getY();
                if (Math.abs(x2 - this.mStartX) < 50.0f && Math.abs(y2 - this.mStartY) < 50.0f) {
                    clickOther(event);
                    invalidate();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setClickedRectList(Map<RectF, String> clickedRectList) {
        this.mClickedRectList = clickedRectList;
        invalidate();
    }

    public void setIsClick(boolean isClick) {
        this.mClickable = isClick;
        init();
        invalidate();
    }

    public void setItalicSize(float italic) {
        this.mItalicSize = italic;
        init();
        invalidate();
    }

    public void setOnWordClickListener(OnWordClickListener listener) {
        this.mListener = listener;
    }

    public void setText(String text) {
        String strReplaceAll = text.replaceAll("<br>", "\n").replaceAll("</br>", "\n");
        this.mWordSentenceBeansItalic.clear();
        this.mWordSentenceBeans.clear();
        this.mWordColor.clear();
        Matcher matcher = Pattern.compile("<span.*?>([\\s\\S]*?)</span>").matcher(strReplaceAll);
        while (matcher.find()) {
            strReplaceAll = matcher.group(1);
        }
        Matcher matcher2 = Pattern.compile("<(u|em|font).*?>([\\s\\S]*?)</(u|em|font)>").matcher(strReplaceAll.replaceAll("\n", ""));
        int length = 0;
        while (matcher2.find()) {
            String strGroup = matcher2.group(2);
            if ("em".equals(matcher2.group(1))) {
                strReplaceAll = strReplaceAll.replace(matcher2.group(0), matcher2.group(2));
                WordSentenceBean wordSentenceBean = new WordSentenceBean();
                wordSentenceBean.setOriginWord(strGroup);
                wordSentenceBean.setCharStartIndex(matcher2.start(0) - length);
                wordSentenceBean.setCharEndIndex((matcher2.start(0) - length) + strGroup.length());
                length += matcher2.group(0).length() - matcher2.group(2).length();
                this.mWordSentenceBeansItalic.add(wordSentenceBean);
            } else if (am.aG.equals(matcher2.group(1))) {
                strReplaceAll = strReplaceAll.replace(matcher2.group(0), matcher2.group(2));
                WordSentenceBean wordSentenceBean2 = new WordSentenceBean();
                wordSentenceBean2.setOriginWord(strGroup);
                wordSentenceBean2.setCharStartIndex(matcher2.start(0) - length);
                wordSentenceBean2.setCharEndIndex((matcher2.start(0) - length) + strGroup.length());
                length += matcher2.group(0).length() - matcher2.group(2).length();
                this.mWordSentenceBeans.add(wordSentenceBean2);
            } else if ("font".equals(matcher2.group(1))) {
                strReplaceAll = strReplaceAll.replace(matcher2.group(0), matcher2.group(2));
                WordSentenceBean wordSentenceBean3 = new WordSentenceBean();
                wordSentenceBean3.setOriginWord(strGroup);
                String[] strArrSplit = matcher2.group(0).split("\\'");
                if (strArrSplit.length > 1) {
                    wordSentenceBean3.setWordColor(strArrSplit[1]);
                }
                wordSentenceBean3.setCharStartIndex(matcher2.start(0) - length);
                wordSentenceBean3.setCharEndIndex((matcher2.start(0) - length) + strGroup.length());
                length += matcher2.group(0).length() - matcher2.group(2).length();
                this.mWordColor.add(wordSentenceBean3);
            }
        }
        this.mText = strReplaceAll;
        init();
        requestLayout();
    }

    public void setTextBold(boolean bold) {
        this.mBold = bold;
        init();
        invalidate();
    }

    public void setTextColor(int color) {
        this.mTextColor = getResources().getColor(color);
        init();
        invalidate();
    }

    public void setTextSize(int size) {
        this.mTextSize = ScreenUtil.sp2px(getContext(), size);
        init();
        requestLayout();
    }

    public EnglishTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnglishTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBold = false;
        this.mItalicSize = -0.25f;
        this.mText = "";
        this.mDrawLineAble = true;
        this.drawDashLineAble = false;
        this.mStartX = 0.0f;
        this.mStartY = 0.0f;
        this.mWordSentenceBeans = new ArrayList();
        this.mWordSentenceBeansItalic = new ArrayList();
        this.mWordColor = new ArrayList();
        this.colorText = R.color.FF333333;
        this.colorGray = R.color.ffb2b2b2;
        this.colorDashLine = R.color.ffb2b2b2;
        initAttr(context, attrs);
    }
}
