package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ReadMoreArrowIcon extends AppCompatTextView {
    private static final boolean DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true;
    private static final int DEFAULT_TRIM_LENGTH = 240;
    private static final int DEFAULT_TRIM_LINES = 3;
    private static final String ELLIPSIZE = "... ";
    private static final String ICON_PLACEHOLDER = "▶";
    private static final int INVALID_END_INDEX = -1;
    public static final int TRIM_MODE_LENGTH = 1;
    public static final int TRIM_MODE_LINES = 0;
    private TextView.BufferType bufferType;
    private int colorClickableText;
    private CharSequence expandText;
    private boolean isInit;
    private int lineCount;
    private int lineEndIndex;
    private OnStateChangeListener mListener;
    private boolean readMore;
    private final boolean showTrimExpandedText;
    private CharSequence text;
    private int trimCollapsedIconRes;
    private int trimLength;
    private int trimLines;
    private int trimMode;
    private final ReadMoreClickableSpan viewMoreSpan;

    public static class CenterAlignImageSpan extends ImageSpan {
        public CenterAlignImageSpan(Drawable drawable) {
            super(drawable);
        }

        @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x2, int top2, int y2, int bottom, Paint paint) {
            Drawable drawable = getDrawable();
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int i2 = fontMetricsInt.descent;
            int iHeight = ((y2 + i2) - ((i2 - fontMetricsInt.ascent) / 2)) - (drawable.getBounds().height() / 2);
            canvas.save();
            canvas.translate(x2, iHeight);
            drawable.draw(canvas);
            canvas.restore();
        }

        @Override // android.text.style.DynamicDrawableSpan, android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
            Drawable drawable = getDrawable();
            if (fontMetricsInt != null) {
                Paint.FontMetricsInt fontMetricsInt2 = paint.getFontMetricsInt();
                int iHeight = drawable.getBounds().height();
                int i2 = fontMetricsInt2.descent;
                int i3 = fontMetricsInt2.ascent;
                int i4 = i2 - i3;
                if (iHeight > i4) {
                    int i5 = (iHeight - i4) / 2;
                    fontMetricsInt.ascent = i3 - i5;
                    fontMetricsInt.descent = i2 + i5;
                    fontMetricsInt.top = fontMetricsInt2.top - i5;
                    fontMetricsInt.bottom = fontMetricsInt2.bottom + i5;
                } else {
                    fontMetricsInt.ascent = i3;
                    fontMetricsInt.descent = i2;
                    fontMetricsInt.top = fontMetricsInt2.top;
                    fontMetricsInt.bottom = fontMetricsInt2.bottom;
                }
            }
            return drawable.getBounds().width();
        }
    }

    public interface OnStateChangeListener {
        void onChange(boolean isExpand);

        void updateContent(CharSequence expandText, CharSequence collapseText);

        void updateLineCount(int count);

        void updateLineIndex(int index);
    }

    public class ReadMoreClickableSpan extends ClickableSpan {
        private ReadMoreClickableSpan() {
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(@NonNull View widget) {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    public ReadMoreArrowIcon(Context context) {
        this(context, null);
    }

    private CharSequence addClickableIcon(SpannableStringBuilder s2) {
        if (this.trimCollapsedIconRes == 0) {
            return s2;
        }
        s2.append(ICON_PLACEHOLDER);
        Drawable drawable = ContextCompat.getDrawable(getContext(), this.trimCollapsedIconRes);
        if (drawable != null) {
            int textSize = (int) (((int) getTextSize()) * 1.0f);
            drawable.setBounds(0, 0, textSize, textSize);
            CenterAlignImageSpan centerAlignImageSpan = new CenterAlignImageSpan(drawable);
            int length = s2.length() - 1;
            int length2 = s2.length();
            s2.setSpan(centerAlignImageSpan, length, length2, 33);
            s2.setSpan(this.viewMoreSpan, length, length2, 33);
        }
        return s2;
    }

    private CharSequence getDisplayableText() {
        return getTrimmedText(this.text);
    }

    private CharSequence getTrimmedText(CharSequence text) {
        int i2 = this.trimMode;
        return i2 == 1 ? (text == null || text.length() <= this.trimLength) ? text : !this.readMore ? updateCollapsedText() : updateExpandedText() : (i2 != 0 || text == null || this.lineEndIndex <= 0 || this.lineCount <= this.trimLines) ? text : this.readMore ? updateExpandedText() : updateCollapsedText();
    }

    private void onGlobalLayoutLineEndIndex() {
        if (this.trimMode == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.ReadMoreArrowIcon.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (ReadMoreArrowIcon.this.text == null || ReadMoreArrowIcon.this.text.length() <= 0) {
                        return;
                    }
                    ReadMoreArrowIcon.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    ReadMoreArrowIcon.this.refreshLineEndIndex();
                    ReadMoreArrowIcon.this.setText();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshLineEndIndex() {
        try {
            int i2 = this.trimLines;
            if (i2 == 0) {
                this.lineEndIndex = getLayout().getLineEnd(0);
            } else if (i2 <= 0 || getLineCount() < this.trimLines) {
                this.lineEndIndex = -1;
            } else {
                this.lineEndIndex = getLayout().getLineEnd(this.trimLines - 1) + 4;
            }
            if (this.lineCount == 0) {
                this.lineCount = getLineCount();
            }
            OnStateChangeListener onStateChangeListener = this.mListener;
            if (onStateChangeListener != null) {
                onStateChangeListener.updateLineIndex(this.lineEndIndex);
                this.mListener.updateLineCount(this.lineCount);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setText() {
        super.setText(getDisplayableText(), this.bufferType);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(0);
    }

    private CharSequence updateCollapsedText() {
        int i2;
        int length = this.text.length();
        int i3 = this.trimMode;
        if (i3 == 0) {
            length = this.lineEndIndex - 5;
            if (length < 0) {
                i2 = this.trimLength;
                length = i2 + 1;
            }
        } else if (i3 == 1) {
            i2 = this.trimLength;
            length = i2 + 1;
        }
        return this.text.length() < length ? this.text : addClickableIcon(new SpannableStringBuilder(this.text, 0, length - 1).append((CharSequence) ELLIPSIZE));
    }

    private CharSequence updateExpandedText() {
        return this.text;
    }

    public void restoreLineCount(int count) {
        this.lineCount = count;
    }

    public void restoreLineIndex(int index) {
        this.lineEndIndex = index;
    }

    public void restoreState(boolean readMore) {
        this.readMore = readMore;
    }

    public void setContentUpdateListener(OnStateChangeListener listener) {
        this.mListener = listener;
    }

    public void setOriginalText(String text) {
        this.text = text;
    }

    public void setTrimCollapsedIcon(int iconRes) {
        this.trimCollapsedIconRes = iconRes;
        setText();
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        setText();
    }

    public void setTrimLines(int trimLines) {
        this.trimLines = trimLines;
    }

    public void setTrimMode(int trimMode) {
        this.trimMode = trimMode;
    }

    public ReadMoreArrowIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ReadMoreArrowIcon);
        this.trimLength = typedArrayObtainStyledAttributes.getInt(3, 240);
        this.trimCollapsedIconRes = typedArrayObtainStyledAttributes.getResourceId(2, R.mipmap.ic_school_arrow);
        this.trimLines = typedArrayObtainStyledAttributes.getInt(4, 3);
        this.colorClickableText = typedArrayObtainStyledAttributes.getColor(0, ContextCompat.getColor(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.blue_more : R.color.blue_more_night));
        this.showTrimExpandedText = typedArrayObtainStyledAttributes.getBoolean(1, true);
        this.trimMode = typedArrayObtainStyledAttributes.getInt(5, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.viewMoreSpan = new ReadMoreClickableSpan();
        onGlobalLayoutLineEndIndex();
    }

    @Override // android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        this.text = text;
        this.bufferType = type;
        if (TextUtils.isEmpty(text)) {
            return;
        }
        setText();
    }
}
