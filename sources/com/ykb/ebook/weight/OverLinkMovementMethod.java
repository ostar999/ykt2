package com.ykb.ebook.weight;

import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/* loaded from: classes8.dex */
public class OverLinkMovementMethod extends LinkMovementMethod {
    private static Object FROM_BELOW = new NoCopySpan.Concrete();
    public static boolean canScroll = false;
    private static OverLinkMovementMethod sInstance;

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new OverLinkMovementMethod();
        }
        return sInstance;
    }

    @Override // android.text.method.LinkMovementMethod, android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 2 || canScroll) {
            return super.onTouchEvent(textView, spannable, motionEvent);
        }
        return true;
    }
}
