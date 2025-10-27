package com.easefun.polyv.livecommon.ui.widget.gif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.easefun.polyv.businesssdk.R;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.droidsonroids.gif.GifTextView;

/* loaded from: classes3.dex */
public class GifSpanTextView extends GifTextView {
    private static final String TAG = "GifSpanTextView";
    private static final String WEB_PATTERN = "((http[s]{0,1}://)?[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";
    private GifSpanChangeWatcher mGifSpanChangeWatcher;
    private WebLinkClickListener webLinkClickListener;

    public interface WebLinkClickListener {
        void webLinkOnClick(String url);
    }

    public GifSpanTextView(Context context) {
        super(context);
        initGifSpanChangeWatcher();
    }

    private GifImageSpan getImageSpan(Drawable drawable) {
        CharSequence text = getText();
        GifImageSpan gifImageSpan = null;
        if (!TextUtils.isEmpty(text) && (text instanceof Spanned)) {
            GifImageSpan[] gifImageSpanArr = (GifImageSpan[]) ((Spanned) text).getSpans(0, text.length(), GifImageSpan.class);
            if (gifImageSpanArr != null && gifImageSpanArr.length > 0) {
                for (GifImageSpan gifImageSpan2 : gifImageSpanArr) {
                    if (drawable == gifImageSpan2.getDrawable()) {
                        gifImageSpan = gifImageSpan2;
                    }
                }
            }
        }
        return gifImageSpan;
    }

    private void initGifSpanChangeWatcher() {
        GifSpanChangeWatcher gifSpanChangeWatcher = new GifSpanChangeWatcher(this);
        this.mGifSpanChangeWatcher = gifSpanChangeWatcher;
        addTextChangedListener(gifSpanChangeWatcher);
    }

    private void setContentHttpPattern(CharSequence string) {
        SpannableString spannableString = new SpannableString(string);
        Matcher matcher = Pattern.compile(WEB_PATTERN, 2).matcher(string);
        int iEnd = 0;
        boolean z2 = false;
        while (matcher.find(iEnd)) {
            iEnd = matcher.end();
            final String strGroup = matcher.group();
            spannableString.setSpan(new ClickableSpan() { // from class: com.easefun.polyv.livecommon.ui.widget.gif.GifSpanTextView.1
                @Override // android.text.style.ClickableSpan
                public void onClick(@NonNull View widget) {
                    PLVCommonLog.d(GifSpanTextView.TAG, "hit :" + strGroup);
                    if (GifSpanTextView.this.webLinkClickListener != null) {
                        GifSpanTextView.this.webLinkClickListener.webLinkOnClick(strGroup);
                    }
                }
            }, iEnd - strGroup.length(), iEnd, 33);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.link_color)), iEnd - strGroup.length(), iEnd, 33);
            z2 = true;
        }
        if (z2) {
            setText(spannableString);
            setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            setText(string);
            setMovementMethod(null);
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (getSelectionStart() != getSelectionEnd() && event.getActionMasked() == 0) {
            CharSequence text = getText();
            setText((CharSequence) null);
            setText(text);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        GifImageSpan imageSpan = getImageSpan(drawable);
        if (imageSpan == null) {
            super.invalidateDrawable(drawable);
            return;
        }
        CharSequence text = getText();
        if (TextUtils.isEmpty(text) || !(text instanceof Editable)) {
            return;
        }
        Editable editable = (Editable) text;
        int spanStart = editable.getSpanStart(imageSpan);
        int spanEnd = editable.getSpanEnd(imageSpan);
        int spanFlags = editable.getSpanFlags(imageSpan);
        editable.removeSpan(imageSpan);
        editable.setSpan(imageSpan, spanStart, spanEnd, spanFlags);
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() <= 0 || TextViewLinesUtils.getTextViewLines(this, getMeasuredWidth()) <= getMaxLines() || getEllipsize() != TextUtils.TruncateAt.END) {
            return;
        }
        super.setText(((Object) getText().subSequence(0, getLayout().getLineEnd(getMaxLines() - 1) - 1)) + "...");
    }

    @Override // android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        PLVCommonLog.d(TAG, "set text :" + ((Object) text));
        TextView.BufferType bufferType = TextView.BufferType.EDITABLE;
        CharSequence text2 = getText();
        if (!TextUtils.isEmpty(text) && text.length() >= 50) {
            PLVCommonLog.d(TAG, "set text :" + ((Object) text));
        }
        if (!TextUtils.isEmpty(text2) && (text2 instanceof Spannable)) {
            Spannable spannable = (Spannable) text2;
            for (GifImageSpan gifImageSpan : (GifImageSpan[]) spannable.getSpans(0, spannable.length(), GifImageSpan.class)) {
                gifImageSpan.getDrawable().setCallback(null);
            }
            for (GifSpanChangeWatcher gifSpanChangeWatcher : (GifSpanChangeWatcher[]) spannable.getSpans(0, spannable.length(), GifSpanChangeWatcher.class)) {
                spannable.removeSpan(gifSpanChangeWatcher);
            }
        }
        if (!TextUtils.isEmpty(text) && (text instanceof Spannable)) {
            Spannable spannable2 = (Spannable) text;
            for (GifImageSpan gifImageSpan2 : (GifImageSpan[]) spannable2.getSpans(0, spannable2.length(), GifImageSpan.class)) {
                gifImageSpan2.getDrawable().setCallback(this);
            }
            for (GifSpanChangeWatcher gifSpanChangeWatcher2 : (GifSpanChangeWatcher[]) spannable2.getSpans(0, spannable2.length(), GifSpanChangeWatcher.class)) {
                spannable2.removeSpan(gifSpanChangeWatcher2);
            }
            if (this.mGifSpanChangeWatcher == null) {
                this.mGifSpanChangeWatcher = new GifSpanChangeWatcher(this);
            }
            spannable2.setSpan(this.mGifSpanChangeWatcher, 0, text.length(), 6553618);
        }
        super.setText(text, bufferType);
    }

    public void setTextInner(CharSequence text, boolean specialType) {
        PLVCommonLog.d(TAG, "content :" + ((Object) text));
        if (specialType) {
            setContentHttpPattern(text);
        } else {
            setText(text);
            setMovementMethod(null);
        }
    }

    public void setWebLinkClickListener(WebLinkClickListener webLinkClickListener) {
        this.webLinkClickListener = webLinkClickListener;
    }

    public GifSpanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGifSpanChangeWatcher();
    }

    public GifSpanTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGifSpanChangeWatcher();
    }
}
