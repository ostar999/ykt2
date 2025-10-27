package com.hyphenate.easeui.widget.chatrow;

import android.text.style.URLSpan;
import android.view.View;
import com.hyphenate.easeui.R;

/* loaded from: classes4.dex */
public class AutolinkSpan extends URLSpan {
    public AutolinkSpan(String str) {
        super(str);
    }

    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
    public void onClick(View view) {
        int i2 = R.id.action_chat_long_click;
        if (view.getTag(i2) != null) {
            view.setTag(i2, null);
        } else {
            super.onClick(view);
        }
    }
}
