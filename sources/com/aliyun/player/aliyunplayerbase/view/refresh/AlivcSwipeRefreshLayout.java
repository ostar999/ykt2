package com.aliyun.player.aliyunplayerbase.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.psychiatrygarden.utils.Constants;

/* loaded from: classes2.dex */
public class AlivcSwipeRefreshLayout extends SwipeRefreshLayout {
    private boolean mIsDragger;
    private float startX;
    private float startY;

    public AlivcSwipeRefreshLayout(Context context) {
        super(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003f  */
    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout, android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            super.onInterceptTouchEvent(r5)
            boolean r0 = r4.canChildScrollUp()
            r1 = 0
            if (r0 != 0) goto L53
            int r0 = r5.getAction()
            if (r0 == 0) goto L42
            r2 = 1
            if (r0 == r2) goto L3f
            r3 = 2
            if (r0 == r3) goto L1a
            r5 = 3
            if (r0 == r5) goto L3f
            goto L50
        L1a:
            float r5 = r5.getY()
            float r0 = r4.startY
            float r5 = r5 - r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "distanceY"
            r0.append(r1)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "test"
            android.util.Log.e(r1, r0)
            r0 = 0
            int r5 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r5 <= 0) goto L50
            r4.mIsDragger = r2
            goto L50
        L3f:
            r4.mIsDragger = r1
            goto L50
        L42:
            float r0 = r5.getY()
            r4.startY = r0
            float r5 = r5.getX()
            r4.startX = r5
            r4.mIsDragger = r1
        L50:
            boolean r5 = r4.mIsDragger
            return r5
        L53:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.player.aliyunplayerbase.view.refresh.AlivcSwipeRefreshLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
        Log.e(Constants.ANSWER_MODE.TEST_MODE, "onTouchEvent" + zOnTouchEvent);
        return zOnTouchEvent;
    }

    public AlivcSwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
