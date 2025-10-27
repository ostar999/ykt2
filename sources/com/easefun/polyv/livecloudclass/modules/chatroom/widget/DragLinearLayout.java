package com.easefun.polyv.livecloudclass.modules.chatroom.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/* loaded from: classes3.dex */
public class DragLinearLayout extends LinearLayout {
    private boolean isDragging;
    private float lastX;
    private float lastY;
    private int parentHeight;
    private int parentWidth;
    private final int touchSlop;

    public DragLinearLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.lastX = motionEvent.getRawX();
            this.lastY = motionEvent.getRawY();
        }
        return motionEvent.getAction() == 2 && this.isDragging;
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.DragLinearLayout.1
            @Override // java.lang.Runnable
            public void run() {
                DragLinearLayout dragLinearLayout = DragLinearLayout.this;
                dragLinearLayout.parentWidth = ((View) dragLinearLayout.getParent()).getWidth();
                DragLinearLayout dragLinearLayout2 = DragLinearLayout.this;
                dragLinearLayout2.parentHeight = ((View) dragLinearLayout2.getParent()).getHeight();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x008e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            float r0 = r8.getRawX()
            float r1 = r8.getRawY()
            float r2 = r7.lastX
            float r2 = r0 - r2
            float r3 = r7.lastY
            float r3 = r1 - r3
            int r4 = r8.getAction()
            r5 = 1
            if (r4 == r5) goto L8e
            r6 = 2
            if (r4 == r6) goto L1f
            r0 = 3
            if (r4 == r0) goto L8e
            goto L96
        L1f:
            boolean r4 = r7.isDragging
            if (r4 != 0) goto L42
            float r4 = java.lang.Math.abs(r2)
            int r6 = r7.touchSlop
            float r6 = (float) r6
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 > 0) goto L39
            float r4 = java.lang.Math.abs(r3)
            int r6 = r7.touchSlop
            float r6 = (float) r6
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L42
        L39:
            r7.isDragging = r5
            android.view.ViewParent r4 = r7.getParent()
            r4.requestDisallowInterceptTouchEvent(r5)
        L42:
            boolean r4 = r7.isDragging
            if (r4 == 0) goto L96
            float r8 = r7.getX()
            float r8 = r8 + r2
            float r2 = r7.getY()
            float r2 = r2 + r3
            r3 = 0
            int r4 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r4 >= 0) goto L56
            r8 = r3
        L56:
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 >= 0) goto L5b
            r2 = r3
        L5b:
            int r3 = r7.parentWidth
            int r4 = r7.getWidth()
            int r3 = r3 - r4
            float r3 = (float) r3
            int r3 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r3 <= 0) goto L6f
            int r8 = r7.parentWidth
            int r3 = r7.getWidth()
            int r8 = r8 - r3
            float r8 = (float) r8
        L6f:
            int r3 = r7.parentHeight
            int r4 = r7.getHeight()
            int r3 = r3 - r4
            float r3 = (float) r3
            int r3 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r3 <= 0) goto L83
            int r2 = r7.parentHeight
            int r3 = r7.getHeight()
            int r2 = r2 - r3
            float r2 = (float) r2
        L83:
            r7.setX(r8)
            r7.setY(r2)
            r7.lastX = r0
            r7.lastY = r1
            return r5
        L8e:
            boolean r0 = r7.isDragging
            if (r0 == 0) goto L96
            r8 = 0
            r7.isDragging = r8
            return r5
        L96:
            boolean r8 = super.onTouchEvent(r8)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecloudclass.modules.chatroom.widget.DragLinearLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public DragLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DragLinearLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isDragging = false;
        setOrientation(1);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
}
