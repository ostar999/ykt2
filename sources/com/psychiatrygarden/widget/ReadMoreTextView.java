package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
public class ReadMoreTextView extends AppCompatTextView {
    private static final boolean DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true;
    private static final int DEFAULT_TRIM_LENGTH = 240;
    private static final int DEFAULT_TRIM_LINES = 3;
    private static final String ELLIPSIZE = "... ";
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
    private CharSequence trimCollapsedText;
    private CharSequence trimExpandedText;
    private int trimLength;
    private int trimLines;
    private int trimMode;
    private final ReadMoreClickableSpan viewMoreSpan;

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
            ReadMoreTextView.this.readMore = !r3.readMore;
            if (ReadMoreTextView.this.mListener != null) {
                if (ReadMoreTextView.this.expandText == null) {
                    ReadMoreTextView readMoreTextView = ReadMoreTextView.this;
                    readMoreTextView.expandText = readMoreTextView.updateExpandedText();
                    ReadMoreTextView.this.mListener.updateContent(ReadMoreTextView.this.expandText, null);
                }
                ReadMoreTextView.this.mListener.onChange(ReadMoreTextView.this.readMore);
            }
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ReadMoreTextView.this.colorClickableText);
        }
    }

    public ReadMoreTextView(Context context) {
        this(context, null);
    }

    private CharSequence addClickableSpan(SpannableStringBuilder s2) {
        CharSequence charSequence = this.readMore ? this.trimExpandedText : this.trimCollapsedText;
        StringBuilder sb = new StringBuilder();
        String string = s2.toString();
        for (int length = charSequence.length(); length > 0; length--) {
            sb.append(string.charAt(string.length() - length));
        }
        if (!TextUtils.equals(sb.toString(), charSequence) && !this.readMore) {
            s2.append(charSequence);
        }
        if (!this.readMore) {
            s2.setSpan(this.viewMoreSpan, s2.length() - charSequence.length(), s2.length(), 33);
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
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.ReadMoreTextView.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (ReadMoreTextView.this.text == null || ReadMoreTextView.this.text.length() <= 0) {
                        return;
                    }
                    ReadMoreTextView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    ReadMoreTextView.this.refreshLineEndIndex();
                    ReadMoreTextView.this.setText();
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
        OnStateChangeListener onStateChangeListener = this.mListener;
        if (onStateChangeListener != null) {
            onStateChangeListener.updateContent(null, updateCollapsedText());
        }
    }

    private CharSequence updateCollapsedText() {
        int i2;
        int length = this.text.length();
        int i3 = this.trimMode;
        if (i3 == 0) {
            length = this.lineEndIndex - ((4 + this.trimCollapsedText.length()) + 1);
            if (length < 0) {
                i2 = this.trimLength;
                length = i2 + 1;
            }
        } else if (i3 == 1) {
            i2 = this.trimLength;
            length = i2 + 1;
        }
        return this.text.length() < length ? this.text : addClickableSpan(new SpannableStringBuilder(this.text, 0, length).append((CharSequence) ELLIPSIZE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence updateExpandedText() {
        if (!this.showTrimExpandedText) {
            return this.text;
        }
        CharSequence charSequence = this.text;
        return addClickableSpan(new SpannableStringBuilder(charSequence, 0, charSequence.length()));
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

    public void setColorClickableText(int colorClickableText) {
        this.colorClickableText = colorClickableText;
    }

    public void setContentUpdateListener(OnStateChangeListener listener) {
        this.mListener = listener;
    }

    public void setOriginalText(String text) {
        this.text = text;
    }

    public void setTrimCollapsedText(CharSequence trimCollapsedText) {
        this.trimCollapsedText = trimCollapsedText;
    }

    public void setTrimExpandedText(CharSequence trimExpandedText) {
        this.trimExpandedText = trimExpandedText;
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

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView);
        this.trimLength = typedArrayObtainStyledAttributes.getInt(4, 240);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(2, R.string.read_more);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(3, R.string.read_less);
        this.trimCollapsedText = getResources().getString(resourceId);
        this.trimExpandedText = getResources().getString(resourceId2);
        this.trimLines = typedArrayObtainStyledAttributes.getInt(5, 3);
        this.colorClickableText = typedArrayObtainStyledAttributes.getColor(0, ContextCompat.getColor(context, SkinManager.getCurrentSkinType(context) == 0 ? R.color.blue_more : R.color.blue_more_night));
        this.showTrimExpandedText = typedArrayObtainStyledAttributes.getBoolean(1, true);
        this.trimMode = typedArrayObtainStyledAttributes.getInt(6, 0);
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
