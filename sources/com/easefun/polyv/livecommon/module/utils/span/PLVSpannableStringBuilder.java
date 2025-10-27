package com.easefun.polyv.livecommon.module.utils.span;

import android.text.SpannableStringBuilder;

/* loaded from: classes3.dex */
public class PLVSpannableStringBuilder extends SpannableStringBuilder {
    public PLVSpannableStringBuilder() {
    }

    @Override // android.text.SpannableStringBuilder
    public SpannableStringBuilder append(CharSequence text, Object what, int flags) {
        int length = length();
        append(text);
        setSpan(what, length, length(), flags);
        return this;
    }

    public SpannableStringBuilder appendExclude(CharSequence text, Object what) {
        return append(text, what, 33);
    }

    public PLVSpannableStringBuilder(CharSequence text) {
        super(text);
    }

    public PLVSpannableStringBuilder(CharSequence text, int start, int end) {
        super(text, start, end);
    }
}
