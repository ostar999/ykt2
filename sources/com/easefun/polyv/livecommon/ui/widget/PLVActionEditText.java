package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.AppCompatEditText;

/* loaded from: classes3.dex */
public class PLVActionEditText extends AppCompatEditText {
    public PLVActionEditText(Context context) {
        super(context);
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(outAttrs);
        int i2 = outAttrs.imeOptions;
        int i3 = i2 & 255;
        if ((i3 & 6) != 0) {
            outAttrs.imeOptions = (i2 ^ i3) | 6;
        }
        int i4 = outAttrs.imeOptions;
        if ((1073741824 & i4) != 0) {
            outAttrs.imeOptions = i4 & (-1073741825);
        }
        return inputConnectionOnCreateInputConnection;
    }

    public PLVActionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PLVActionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
