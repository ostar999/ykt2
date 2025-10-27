package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

/* loaded from: classes6.dex */
public class NestedRecyclerView extends RecyclerView {
    private ViewPager2 mViewPager;

    public NestedRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    private void init() {
        addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() { // from class: com.psychiatrygarden.widget.NestedRecyclerView.1
            private float initialX;
            private float initialY;

            /* JADX WARN: Removed duplicated region for block: B:27:0x0070  */
            @Override // androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener, androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView r6, android.view.MotionEvent r7) {
                /*
                    r5 = this;
                    com.psychiatrygarden.widget.NestedRecyclerView r0 = com.psychiatrygarden.widget.NestedRecyclerView.this
                    androidx.viewpager2.widget.ViewPager2 r0 = com.psychiatrygarden.widget.NestedRecyclerView.access$000(r0)
                    r1 = 0
                    if (r0 != 0) goto La
                    return r1
                La:
                    int r0 = r7.getAction()
                    r2 = 1
                    if (r0 == 0) goto L78
                    if (r0 == r2) goto L70
                    r3 = 2
                    if (r0 == r3) goto L1b
                    r7 = 3
                    if (r0 == r7) goto L70
                    goto L8b
                L1b:
                    float r0 = r7.getX()
                    float r3 = r5.initialX
                    float r0 = r0 - r3
                    float r7 = r7.getY()
                    float r3 = r5.initialY
                    float r7 = r7 - r3
                    float r3 = java.lang.Math.abs(r0)
                    float r7 = java.lang.Math.abs(r7)
                    int r7 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
                    if (r7 <= 0) goto L8b
                    com.psychiatrygarden.widget.NestedRecyclerView r7 = com.psychiatrygarden.widget.NestedRecyclerView.this
                    androidx.viewpager2.widget.ViewPager2 r7 = com.psychiatrygarden.widget.NestedRecyclerView.access$000(r7)
                    int r7 = r7.getCurrentItem()
                    com.psychiatrygarden.widget.NestedRecyclerView r3 = com.psychiatrygarden.widget.NestedRecyclerView.this
                    androidx.viewpager2.widget.ViewPager2 r3 = com.psychiatrygarden.widget.NestedRecyclerView.access$000(r3)
                    androidx.recyclerview.widget.RecyclerView$Adapter r3 = r3.getAdapter()
                    java.util.Objects.requireNonNull(r3)
                    int r3 = r3.getItemCount()
                    int r3 = r3 - r2
                    r4 = 0
                    if (r7 != r3) goto L60
                    int r3 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                    if (r3 >= 0) goto L60
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r1)
                    goto L8b
                L60:
                    android.view.ViewParent r6 = r6.getParent()
                    if (r7 != 0) goto L6c
                    int r7 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                    if (r7 > 0) goto L6b
                    goto L6c
                L6b:
                    r2 = r1
                L6c:
                    r6.requestDisallowInterceptTouchEvent(r2)
                    goto L8b
                L70:
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r1)
                    goto L8b
                L78:
                    float r0 = r7.getX()
                    r5.initialX = r0
                    float r7 = r7.getY()
                    r5.initialY = r7
                    android.view.ViewParent r6 = r6.getParent()
                    r6.requestDisallowInterceptTouchEvent(r2)
                L8b:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.NestedRecyclerView.AnonymousClass1.onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView, android.view.MotionEvent):boolean");
            }
        });
    }

    public void bindViewPager2(ViewPager2 viewPager) {
        this.mViewPager = viewPager;
    }

    public NestedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
