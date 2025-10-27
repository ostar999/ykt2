package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.widget.NestedScrollView;

/* loaded from: classes6.dex */
public class DisInterceptNestedScrollView extends NestedScrollView {
    public DisInterceptNestedScrollView(Context context) {
        super(context);
        requestDisallowInterceptTouchEvent(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0012  */
    @Override // androidx.core.widget.NestedScrollView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r4) {
        /*
            r3 = this;
            int r0 = r4.getAction()
            r1 = 1
            if (r0 == r1) goto L12
            r2 = 2
            if (r0 == r2) goto Le
            r1 = 3
            if (r0 == r1) goto L12
            goto L16
        Le:
            r3.requestDisallowInterceptTouchEvent(r1)
            goto L16
        L12:
            r0 = 0
            r3.requestDisallowInterceptTouchEvent(r0)
        L16:
            boolean r4 = super.onTouchEvent(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.DisInterceptNestedScrollView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestDisallowInterceptTouchEvent(true);
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        requestDisallowInterceptTouchEvent(true);
    }
}
