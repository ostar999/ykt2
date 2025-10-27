package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVOutsideTouchableLayout extends FrameLayout {
    private List<OnOutsideDismissListener> onDismissListeners;

    public static abstract class OnOutsideDismissListener {
        private View view;

        public OnOutsideDismissListener(View view) {
            this.view = view;
        }

        public abstract void onDismiss();
    }

    public PLVOutsideTouchableLayout(@NonNull Context context) {
        this(context, null);
    }

    public void addOnDismissListener(OnOutsideDismissListener listener) {
        if (this.onDismissListeners.contains(listener)) {
            return;
        }
        this.onDismissListeners.add(listener);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004b, code lost:
    
        if (r3 > (r2[0] + r1.getWidth())) goto L16;
     */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            int r0 = r8.getChildCount()
            if (r0 <= 0) goto L6d
            r0 = 0
            android.view.View r1 = r8.getChildAt(r0)
            r2 = 2
            int[] r2 = new int[r2]
            r1.getLocationInWindow(r2)
            float r3 = r9.getX()
            float r4 = r9.getY()
            r5 = r2[r0]
            float r6 = (float) r5
            int r6 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            r7 = 1
            if (r6 < 0) goto L4d
            float r6 = (float) r5
            int r6 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r6 <= 0) goto L41
            int r6 = r1.getWidth()
            int r5 = r5 + r6
            float r5 = (float) r5
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 >= 0) goto L41
            r5 = r2[r7]
            float r6 = (float) r5
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 < 0) goto L4d
            int r6 = r1.getHeight()
            int r5 = r5 + r6
            float r5 = (float) r5
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 > 0) goto L4d
        L41:
            r0 = r2[r0]
            int r2 = r1.getWidth()
            int r0 = r0 + r2
            float r0 = (float) r0
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L6d
        L4d:
            r8.removeAllViews()
            java.util.List<com.easefun.polyv.livecommon.ui.widget.PLVOutsideTouchableLayout$OnOutsideDismissListener> r9 = r8.onDismissListeners
            java.util.Iterator r9 = r9.iterator()
        L56:
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto L6c
            java.lang.Object r0 = r9.next()
            com.easefun.polyv.livecommon.ui.widget.PLVOutsideTouchableLayout$OnOutsideDismissListener r0 = (com.easefun.polyv.livecommon.ui.widget.PLVOutsideTouchableLayout.OnOutsideDismissListener) r0
            android.view.View r2 = com.easefun.polyv.livecommon.ui.widget.PLVOutsideTouchableLayout.OnOutsideDismissListener.access$000(r0)
            if (r2 != r1) goto L56
            r0.onDismiss()
            goto L56
        L6c:
            return r7
        L6d:
            boolean r9 = super.onInterceptTouchEvent(r9)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.PLVOutsideTouchableLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            setClickable(true);
        } else {
            setClickable(false);
        }
    }

    public PLVOutsideTouchableLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVOutsideTouchableLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.onDismissListeners = new ArrayList();
        setClickable(false);
    }
}
