package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.viewpager.widget.ViewPager;
import com.lxj.xpopup.enums.LayoutStatus;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class PopupDrawerLayout extends FrameLayout {
    ViewDragHelper.Callback callback;
    boolean canChildScrollLeft;
    float downX;
    float downY;
    ViewDragHelper dragHelper;
    public boolean enableDrag;
    float fraction;
    boolean hasLayout;
    public boolean isDismissOnTouchOutside;
    public boolean isDrawStatusBarShadow;
    boolean isIntercept;
    boolean isToLeft;
    private OnCloseListener listener;
    View mChild;
    View placeHolder;
    public PopupPosition position;
    LayoutStatus status;
    float ty;

    /* renamed from: x, reason: collision with root package name */
    float f8906x;

    /* renamed from: y, reason: collision with root package name */
    float f8907y;

    public interface OnCloseListener {
        void onClose();

        void onDrag(int i2, float f2, boolean z2);

        void onOpen();
    }

    public PopupDrawerLayout(Context context) {
        this(context, null);
    }

    private boolean canScroll(ViewGroup viewGroup, float f2, float f3, int i2) {
        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
            View childAt = viewGroup.getChildAt(i3);
            int[] iArr = new int[2];
            childAt.getLocationInWindow(iArr);
            int i4 = iArr[0];
            if (XPopupUtils.isInRect(f2, f3, new Rect(i4, iArr[1], childAt.getWidth() + i4, iArr[1] + childAt.getHeight())) && (childAt instanceof ViewGroup)) {
                if (childAt instanceof ViewPager) {
                    ViewPager viewPager = (ViewPager) childAt;
                    return i2 == 0 ? viewPager.canScrollHorizontally(-1) || viewPager.canScrollHorizontally(1) : viewPager.canScrollHorizontally(i2);
                }
                if (!(childAt instanceof HorizontalScrollView)) {
                    return canScroll((ViewGroup) childAt, f2, f3, i2);
                }
                HorizontalScrollView horizontalScrollView = (HorizontalScrollView) childAt;
                return i2 == 0 ? horizontalScrollView.canScrollHorizontally(-1) || horizontalScrollView.canScrollHorizontally(1) : horizontalScrollView.canScrollHorizontally(i2);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int fixLeft(int i2) {
        PopupPosition popupPosition = this.position;
        if (popupPosition == PopupPosition.Left) {
            if (i2 < (-this.mChild.getMeasuredWidth())) {
                i2 = -this.mChild.getMeasuredWidth();
            }
            if (i2 > 0) {
                return 0;
            }
            return i2;
        }
        if (popupPosition != PopupPosition.Right) {
            return i2;
        }
        if (i2 < getMeasuredWidth() - this.mChild.getMeasuredWidth()) {
            i2 = getMeasuredWidth() - this.mChild.getMeasuredWidth();
        }
        return i2 > getMeasuredWidth() ? getMeasuredWidth() : i2;
    }

    public void close() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.3
            @Override // java.lang.Runnable
            public void run() {
                PopupDrawerLayout.this.dragHelper.abort();
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                ViewDragHelper viewDragHelper = popupDrawerLayout.dragHelper;
                View view = popupDrawerLayout.mChild;
                viewDragHelper.smoothSlideViewTo(view, popupDrawerLayout.position == PopupPosition.Left ? -view.getMeasuredWidth() : popupDrawerLayout.getMeasuredWidth(), 0);
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        });
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.ty = getTranslationY();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.placeHolder = getChildAt(0);
        this.mChild = getChildAt(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005c  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            boolean r0 = r5.enableDrag
            if (r0 != 0) goto L9
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        L9:
            androidx.customview.widget.ViewDragHelper r0 = r5.dragHelper
            r1 = 1
            boolean r0 = r0.continueSettling(r1)
            if (r0 != 0) goto La3
            com.lxj.xpopup.enums.LayoutStatus r0 = r5.status
            com.lxj.xpopup.enums.LayoutStatus r2 = com.lxj.xpopup.enums.LayoutStatus.Close
            if (r0 != r2) goto L1a
            goto La3
        L1a:
            float r0 = r6.getX()
            float r2 = r5.f8906x
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r2 = 0
            if (r0 >= 0) goto L27
            r0 = r1
            goto L28
        L27:
            r0 = r2
        L28:
            r5.isToLeft = r0
            float r0 = r6.getX()
            r5.f8906x = r0
            float r0 = r6.getY()
            r5.f8907y = r0
            int r0 = r6.getAction()
            if (r0 == 0) goto L62
            if (r0 == r1) goto L5c
            r3 = 2
            if (r0 == r3) goto L45
            r2 = 3
            if (r0 == r2) goto L5c
            goto L6e
        L45:
            float r0 = r5.f8906x
            float r3 = r5.downX
            float r0 = r0 - r3
            float r0 = java.lang.Math.abs(r0)
            float r3 = r5.f8907y
            float r4 = r5.downY
            float r3 = r3 - r4
            float r3 = java.lang.Math.abs(r3)
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L6e
            return r2
        L5c:
            r0 = 0
            r5.f8906x = r0
            r5.f8907y = r0
            goto L6e
        L62:
            float r0 = r6.getX()
            r5.downX = r0
            float r0 = r6.getY()
            r5.downY = r0
        L6e:
            float r0 = r6.getX()
            float r2 = r6.getY()
            boolean r0 = r5.canScroll(r5, r0, r2, r1)
            r5.canChildScrollLeft = r0
            androidx.customview.widget.ViewDragHelper r0 = r5.dragHelper
            boolean r0 = r0.shouldInterceptTouchEvent(r6)
            r5.isIntercept = r0
            boolean r1 = r5.isToLeft
            if (r1 == 0) goto L8d
            boolean r1 = r5.canChildScrollLeft
            if (r1 != 0) goto L8d
            return r0
        L8d:
            float r0 = r6.getX()
            float r1 = r6.getY()
            boolean r0 = r5.canScroll(r5, r0, r1)
            if (r0 != 0) goto L9e
            boolean r6 = r5.isIntercept
            return r6
        L9e:
            boolean r6 = super.onInterceptTouchEvent(r6)
            return r6
        La3:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.PopupDrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        this.placeHolder.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        if (this.hasLayout) {
            View view = this.mChild;
            view.layout(view.getLeft(), this.mChild.getTop(), this.mChild.getRight(), this.mChild.getMeasuredHeight());
            return;
        }
        if (this.position == PopupPosition.Left) {
            View view2 = this.mChild;
            view2.layout(-view2.getMeasuredWidth(), 0, 0, getMeasuredHeight());
        } else {
            this.mChild.layout(getMeasuredWidth(), 0, getMeasuredWidth() + this.mChild.getMeasuredWidth(), getMeasuredHeight());
        }
        this.hasLayout = true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.enableDrag) {
            return super.onTouchEvent(motionEvent);
        }
        if (this.dragHelper.continueSettling(true)) {
            return true;
        }
        this.dragHelper.processTouchEvent(motionEvent);
        return true;
    }

    public void open() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.2
            @Override // java.lang.Runnable
            public void run() {
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                ViewDragHelper viewDragHelper = popupDrawerLayout.dragHelper;
                View view = popupDrawerLayout.mChild;
                viewDragHelper.smoothSlideViewTo(view, popupDrawerLayout.position == PopupPosition.Left ? 0 : view.getLeft() - PopupDrawerLayout.this.mChild.getMeasuredWidth(), 0);
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }
        });
    }

    public void setDrawerPosition(PopupPosition popupPosition) {
        this.position = popupPosition;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.listener = onCloseListener;
    }

    public PopupDrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PopupDrawerLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.status = null;
        this.position = PopupPosition.Left;
        this.isDrawStatusBarShadow = false;
        this.fraction = 0.0f;
        this.enableDrag = true;
        this.hasLayout = false;
        this.isIntercept = false;
        ViewDragHelper.Callback callback = new ViewDragHelper.Callback() { // from class: com.lxj.xpopup.widget.PopupDrawerLayout.1
            private void calcFraction(int i3, int i4) {
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                PopupPosition popupPosition = popupDrawerLayout.position;
                if (popupPosition == PopupPosition.Left) {
                    popupDrawerLayout.fraction = ((popupDrawerLayout.mChild.getMeasuredWidth() + i3) * 1.0f) / PopupDrawerLayout.this.mChild.getMeasuredWidth();
                    if (i3 == (-PopupDrawerLayout.this.mChild.getMeasuredWidth()) && PopupDrawerLayout.this.listener != null) {
                        PopupDrawerLayout popupDrawerLayout2 = PopupDrawerLayout.this;
                        LayoutStatus layoutStatus = popupDrawerLayout2.status;
                        LayoutStatus layoutStatus2 = LayoutStatus.Close;
                        if (layoutStatus != layoutStatus2) {
                            popupDrawerLayout2.status = layoutStatus2;
                            popupDrawerLayout2.listener.onClose();
                        }
                    }
                } else if (popupPosition == PopupPosition.Right) {
                    popupDrawerLayout.fraction = ((popupDrawerLayout.getMeasuredWidth() - i3) * 1.0f) / PopupDrawerLayout.this.mChild.getMeasuredWidth();
                    if (i3 == PopupDrawerLayout.this.getMeasuredWidth() && PopupDrawerLayout.this.listener != null) {
                        PopupDrawerLayout popupDrawerLayout3 = PopupDrawerLayout.this;
                        LayoutStatus layoutStatus3 = popupDrawerLayout3.status;
                        LayoutStatus layoutStatus4 = LayoutStatus.Close;
                        if (layoutStatus3 != layoutStatus4) {
                            popupDrawerLayout3.status = layoutStatus4;
                            popupDrawerLayout3.listener.onClose();
                        }
                    }
                }
                if (PopupDrawerLayout.this.listener != null) {
                    PopupDrawerLayout.this.listener.onDrag(i3, PopupDrawerLayout.this.fraction, i4 < 0);
                    PopupDrawerLayout popupDrawerLayout4 = PopupDrawerLayout.this;
                    if (popupDrawerLayout4.fraction == 1.0f) {
                        LayoutStatus layoutStatus5 = popupDrawerLayout4.status;
                        LayoutStatus layoutStatus6 = LayoutStatus.Open;
                        if (layoutStatus5 != layoutStatus6) {
                            popupDrawerLayout4.status = layoutStatus6;
                            popupDrawerLayout4.listener.onOpen();
                        }
                    }
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i3, int i4) {
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                return view == popupDrawerLayout.placeHolder ? i3 : popupDrawerLayout.fixLeft(i3);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(@NonNull View view) {
                return 1;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i3, int i4, int i5, int i6) {
                super.onViewPositionChanged(view, i3, i4, i5, i6);
                View view2 = PopupDrawerLayout.this.placeHolder;
                if (view != view2) {
                    calcFraction(i3, i5);
                    return;
                }
                view2.layout(0, 0, view2.getMeasuredWidth(), PopupDrawerLayout.this.placeHolder.getMeasuredHeight());
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                int iFixLeft = popupDrawerLayout.fixLeft(popupDrawerLayout.mChild.getLeft() + i5);
                View view3 = PopupDrawerLayout.this.mChild;
                view3.layout(iFixLeft, view3.getTop(), PopupDrawerLayout.this.mChild.getMeasuredWidth() + iFixLeft, PopupDrawerLayout.this.mChild.getBottom());
                calcFraction(iFixLeft, i5);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f2, float f3) {
                int measuredWidth;
                int measuredWidth2;
                super.onViewReleased(view, f2, f3);
                PopupDrawerLayout popupDrawerLayout = PopupDrawerLayout.this;
                if (view == popupDrawerLayout.placeHolder && f2 == 0.0f) {
                    if (popupDrawerLayout.isDismissOnTouchOutside) {
                        popupDrawerLayout.close();
                        return;
                    }
                    return;
                }
                View view2 = popupDrawerLayout.mChild;
                if (view == view2 && popupDrawerLayout.isToLeft && !popupDrawerLayout.canChildScrollLeft && f2 < -500.0f) {
                    popupDrawerLayout.close();
                    return;
                }
                if (popupDrawerLayout.position == PopupPosition.Left) {
                    if (f2 < -1000.0f) {
                        measuredWidth2 = view2.getMeasuredWidth();
                    } else {
                        if (PopupDrawerLayout.this.mChild.getLeft() < (-view2.getMeasuredWidth()) / 2) {
                            measuredWidth2 = PopupDrawerLayout.this.mChild.getMeasuredWidth();
                        } else {
                            measuredWidth = 0;
                        }
                    }
                    measuredWidth = -measuredWidth2;
                } else if (f2 > 1000.0f) {
                    measuredWidth = popupDrawerLayout.getMeasuredWidth();
                } else {
                    measuredWidth = view.getLeft() < popupDrawerLayout.getMeasuredWidth() - (PopupDrawerLayout.this.mChild.getMeasuredWidth() / 2) ? PopupDrawerLayout.this.getMeasuredWidth() - PopupDrawerLayout.this.mChild.getMeasuredWidth() : PopupDrawerLayout.this.getMeasuredWidth();
                }
                PopupDrawerLayout popupDrawerLayout2 = PopupDrawerLayout.this;
                popupDrawerLayout2.dragHelper.smoothSlideViewTo(popupDrawerLayout2.mChild, measuredWidth, view.getTop());
                ViewCompat.postInvalidateOnAnimation(PopupDrawerLayout.this);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i3) {
                return (PopupDrawerLayout.this.dragHelper.continueSettling(true) || PopupDrawerLayout.this.status == LayoutStatus.Close) ? false : true;
            }
        };
        this.callback = callback;
        this.isDismissOnTouchOutside = true;
        this.dragHelper = ViewDragHelper.create(this, callback);
    }

    private boolean canScroll(ViewGroup viewGroup, float f2, float f3) {
        return canScroll(viewGroup, f2, f3, 0);
    }
}
