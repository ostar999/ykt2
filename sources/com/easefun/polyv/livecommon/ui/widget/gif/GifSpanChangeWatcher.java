package com.easefun.polyv.livecommon.ui.widget.gif;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.TextWatcher;

/* loaded from: classes3.dex */
public class GifSpanChangeWatcher implements SpanWatcher, TextWatcher {
    private Drawable.Callback mCallback;

    public GifSpanChangeWatcher(Drawable.Callback callback) {
        this.mCallback = callback;
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s2) {
        if (s2 != null) {
            s2.setSpan(this, 0, s2.length(), 6553618);
        }
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanAdded(Spannable buf, Object what, int s2, int e2) {
        if (what instanceof GifImageSpan) {
            ((GifImageSpan) what).getDrawable().setCallback(this.mCallback);
        }
    }

    @Override // android.text.SpanWatcher
    public void onSpanChanged(Spannable buf, Object what, int s2, int e2, int st, int en) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanRemoved(Spannable buf, Object what, int s2, int e2) {
        if (what instanceof GifImageSpan) {
            ((GifImageSpan) what).getDrawable().setCallback(null);
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s2, int start, int before, int count) {
    }
}
