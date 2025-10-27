package com.easefun.polyv.livecommon.ui.widget.gif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class GifEditText extends EditText {
    private static final String TAG = "GifEditText";
    private GifSpanChangeWatcher mGifSpanChangeWatcher;

    public GifEditText(Context context) {
        super(context);
        initGifSpanChangeWatcher();
    }

    private GifImageSpan getImageSpan(Drawable drawable) {
        Editable text = getText();
        GifImageSpan gifImageSpan = null;
        if (!TextUtils.isEmpty(text) && (text instanceof Spanned)) {
            GifImageSpan[] gifImageSpanArr = (GifImageSpan[]) text.getSpans(0, text.length(), GifImageSpan.class);
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

    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        GifImageSpan imageSpan = getImageSpan(drawable);
        if (imageSpan == null) {
            super.invalidateDrawable(drawable);
            return;
        }
        Editable text = getText();
        if (TextUtils.isEmpty(text) || !(text instanceof Editable)) {
            return;
        }
        text.setSpan(imageSpan, text.getSpanStart(imageSpan), text.getSpanEnd(imageSpan), text.getSpanFlags(imageSpan));
    }

    @Override // android.widget.EditText, android.widget.TextView
    public void setText(CharSequence text, TextView.BufferType type) {
        try {
            Editable text2 = getText();
            if (!TextUtils.isEmpty(text2) && (text2 instanceof Spannable)) {
                for (GifImageSpan gifImageSpan : (GifImageSpan[]) text2.getSpans(0, text2.length(), GifImageSpan.class)) {
                    gifImageSpan.getDrawable().setCallback(null);
                }
                for (GifSpanChangeWatcher gifSpanChangeWatcher : (GifSpanChangeWatcher[]) text2.getSpans(0, text2.length(), GifSpanChangeWatcher.class)) {
                    text2.removeSpan(gifSpanChangeWatcher);
                }
            }
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
        }
        if (!TextUtils.isEmpty(text) && !(text instanceof Editable)) {
            text = new SpannableStringBuilder(text);
        }
        if (!TextUtils.isEmpty(text) && (text instanceof Spannable)) {
            Spannable spannable = (Spannable) text;
            for (GifImageSpan gifImageSpan2 : (GifImageSpan[]) spannable.getSpans(0, spannable.length(), GifImageSpan.class)) {
                gifImageSpan2.getDrawable().setCallback(this);
            }
            for (GifSpanChangeWatcher gifSpanChangeWatcher2 : (GifSpanChangeWatcher[]) spannable.getSpans(0, spannable.length(), GifSpanChangeWatcher.class)) {
                spannable.removeSpan(gifSpanChangeWatcher2);
            }
            if (this.mGifSpanChangeWatcher == null) {
                this.mGifSpanChangeWatcher = new GifSpanChangeWatcher(this);
            }
            spannable.setSpan(this.mGifSpanChangeWatcher, 0, text.length(), 6553618);
        }
        super.setText(text, type);
    }

    public GifEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGifSpanChangeWatcher();
    }

    public GifEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGifSpanChangeWatcher();
    }
}
