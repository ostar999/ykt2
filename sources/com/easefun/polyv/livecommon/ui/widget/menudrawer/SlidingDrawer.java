package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class SlidingDrawer extends DraggableDrawer {
    private static final String TAG = "OverlayDrawer";

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.menudrawer.SlidingDrawer$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position;

        static {
            int[] iArr = new int[Position.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position = iArr;
            try {
                iArr[Position.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public SlidingDrawer(Activity activity, int dragMode) {
        super(activity, dragMode);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isContentTouch(int r5, int r6) {
        /*
            r4 = this;
            int[] r0 = com.easefun.polyv.livecommon.ui.widget.menudrawer.SlidingDrawer.AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position
            com.easefun.polyv.livecommon.ui.widget.menudrawer.Position r1 = r4.getPosition()
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 1
            r2 = 0
            if (r0 == r1) goto L35
            r3 = 2
            if (r0 == r3) goto L2c
            r3 = 3
            if (r0 == r3) goto L23
            r5 = 4
            if (r0 == r5) goto L1a
            goto L40
        L1a:
            com.easefun.polyv.livecommon.ui.widget.menudrawer.BuildLayerFrameLayout r5 = r4.mContentContainer
            int r5 = com.easefun.polyv.livecommon.ui.widget.menudrawer.ViewHelper.getBottom(r5)
            if (r5 <= r6) goto L3e
            goto L3f
        L23:
            com.easefun.polyv.livecommon.ui.widget.menudrawer.BuildLayerFrameLayout r6 = r4.mContentContainer
            int r6 = com.easefun.polyv.livecommon.ui.widget.menudrawer.ViewHelper.getRight(r6)
            if (r6 <= r5) goto L3e
            goto L3f
        L2c:
            com.easefun.polyv.livecommon.ui.widget.menudrawer.BuildLayerFrameLayout r5 = r4.mContentContainer
            int r5 = com.easefun.polyv.livecommon.ui.widget.menudrawer.ViewHelper.getTop(r5)
            if (r5 >= r6) goto L3e
            goto L3f
        L35:
            com.easefun.polyv.livecommon.ui.widget.menudrawer.BuildLayerFrameLayout r6 = r4.mContentContainer
            int r6 = com.easefun.polyv.livecommon.ui.widget.menudrawer.ViewHelper.getLeft(r6)
            if (r6 >= r5) goto L3e
            goto L3f
        L3e:
            r1 = r2
        L3f:
            r2 = r1
        L40:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.menudrawer.SlidingDrawer.isContentTouch(int, int):boolean");
    }

    private void offsetMenu(int offsetPixels) {
        if (!this.mOffsetMenu || this.mMenuSize == 0) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int i2 = this.mMenuSize;
        float f2 = this.mOffsetPixels;
        float f3 = i2;
        int iAbs = (int) ((1.0f - (Math.abs(this.mOffsetPixels) / f3)) * f3 * (-0.25f) * ((int) (f2 / Math.abs(f2))));
        int i3 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i3 == 1) {
            if (!PLVMenuDrawer.USE_TRANSLATIONS) {
                BuildLayerFrameLayout buildLayerFrameLayout = this.mMenuContainer;
                buildLayerFrameLayout.offsetLeftAndRight(iAbs - buildLayerFrameLayout.getLeft());
                this.mMenuContainer.setVisibility(offsetPixels == 0 ? 4 : 0);
                return;
            } else if (offsetPixels > 0) {
                this.mMenuContainer.setTranslationX(iAbs);
                return;
            } else {
                this.mMenuContainer.setTranslationX(-i2);
                return;
            }
        }
        if (i3 == 2) {
            if (!PLVMenuDrawer.USE_TRANSLATIONS) {
                BuildLayerFrameLayout buildLayerFrameLayout2 = this.mMenuContainer;
                buildLayerFrameLayout2.offsetTopAndBottom(iAbs - buildLayerFrameLayout2.getTop());
                this.mMenuContainer.setVisibility(offsetPixels == 0 ? 4 : 0);
                return;
            } else if (offsetPixels > 0) {
                this.mMenuContainer.setTranslationY(iAbs);
                return;
            } else {
                this.mMenuContainer.setTranslationY(-i2);
                return;
            }
        }
        if (i3 == 3) {
            if (!PLVMenuDrawer.USE_TRANSLATIONS) {
                this.mMenuContainer.offsetLeftAndRight(iAbs - (this.mMenuContainer.getRight() - width));
                this.mMenuContainer.setVisibility(offsetPixels == 0 ? 4 : 0);
                return;
            } else if (offsetPixels != 0) {
                this.mMenuContainer.setTranslationX(iAbs);
                return;
            } else {
                this.mMenuContainer.setTranslationX(f3);
                return;
            }
        }
        if (i3 != 4) {
            return;
        }
        if (!PLVMenuDrawer.USE_TRANSLATIONS) {
            this.mMenuContainer.offsetTopAndBottom(iAbs - (this.mMenuContainer.getBottom() - height));
            this.mMenuContainer.setVisibility(offsetPixels == 0 ? 4 : 0);
        } else if (offsetPixels != 0) {
            this.mMenuContainer.setTranslationY(iAbs);
        } else {
            this.mMenuContainer.setTranslationY(f3);
        }
    }

    private void onPointerUp(MotionEvent ev) {
        int actionIndex = ev.getActionIndex();
        if (ev.getPointerId(actionIndex) == this.mActivePointerId) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = ev.getX(i2);
            this.mActivePointerId = ev.getPointerId(i2);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    public boolean checkTouchSlop(float dx, float dy) {
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        return (i2 == 2 || i2 == 4) ? Math.abs(dy) > ((float) this.mTouchSlop) && Math.abs(dy) > Math.abs(dx) : Math.abs(dx) > ((float) this.mTouchSlop) && Math.abs(dx) > Math.abs(dy);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void closeMenu(boolean animate) {
        animateOffsetTo(0, 0, animate);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void drawOverlay(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float f2 = this.mOffsetPixels;
        int i2 = (int) f2;
        float fAbs = Math.abs(f2) / this.mMenuSize;
        int i3 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i3 == 1) {
            this.mMenuOverlay.setBounds(0, 0, i2, height);
        } else if (i3 == 2) {
            this.mMenuOverlay.setBounds(0, 0, width, i2);
        } else if (i3 == 3) {
            this.mMenuOverlay.setBounds(i2 + width, 0, width, height);
        } else if (i3 == 4) {
            this.mMenuOverlay.setBounds(0, i2 + height, width, height);
        }
        this.mMenuOverlay.setAlpha((int) ((1.0f - fAbs) * 185.0f));
        this.mMenuOverlay.draw(canvas);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer, com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void initDrawer(Context context, AttributeSet attrs, int defStyle) {
        super.initDrawer(context, attrs, defStyle);
        super.addView(this.mMenuContainer, -1, new ViewGroup.LayoutParams(-1, -1));
        super.addView(this.mContentContainer, -1, new ViewGroup.LayoutParams(-1, -1));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer
    public void initPeekScroller() {
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 3 || i2 == 4) {
            this.mPeekScroller.startScroll(0, 0, (-this.mMenuSize) / 3, 0, 5000);
        } else {
            this.mPeekScroller.startScroll(0, 0, this.mMenuSize / 3, 0, 5000);
        }
    }

    public boolean onDownAllowDrag(int x2, int y2) {
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 1) {
            boolean z2 = this.mMenuVisible;
            return (!z2 && this.mInitialMotionX <= ((float) this.mTouchSize)) || (z2 && this.mInitialMotionX >= this.mOffsetPixels);
        }
        if (i2 == 2) {
            boolean z3 = this.mMenuVisible;
            return (!z3 && this.mInitialMotionY <= ((float) this.mTouchSize)) || (z3 && this.mInitialMotionY >= this.mOffsetPixels);
        }
        if (i2 == 3) {
            int width = getWidth();
            int i3 = (int) this.mInitialMotionX;
            boolean z4 = this.mMenuVisible;
            return (!z4 && i3 >= width - this.mTouchSize) || (z4 && ((float) i3) <= ((float) width) + this.mOffsetPixels);
        }
        if (i2 != 4) {
            return false;
        }
        int height = getHeight();
        boolean z5 = this.mMenuVisible;
        return (!z5 && this.mInitialMotionY >= ((float) (height - this.mTouchSize))) || (z5 && this.mInitialMotionY <= ((float) height) + this.mOffsetPixels);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int iFindPointerIndex;
        int action = ev.getAction() & 255;
        if (action == 1 || action == 3) {
            this.mActivePointerId = -1;
            this.mIsDragging = false;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            if (Math.abs(this.mOffsetPixels) > this.mMenuSize / 2) {
                openMenu();
            } else {
                closeMenu();
            }
            return false;
        }
        if (action == 0 && this.mMenuVisible && isCloseEnough()) {
            setOffsetPixels(0.0f);
            stopAnimation();
            endPeek();
            setDrawerState(0);
            this.mIsDragging = false;
        }
        if (this.mMenuVisible) {
            int i2 = this.mActivePointerId;
            if (i2 == -1 || (iFindPointerIndex = ev.findPointerIndex(i2)) == -1) {
                iFindPointerIndex = 0;
            }
            if (isContentTouch((int) ev.getX(iFindPointerIndex), (int) ev.getY(iFindPointerIndex))) {
                return true;
            }
        }
        if (!this.mMenuVisible && !this.mIsDragging && this.mTouchMode == 0) {
            return false;
        }
        if (action != 0 && this.mIsDragging) {
            return true;
        }
        if (action == 0) {
            float x2 = ev.getX();
            this.mInitialMotionX = x2;
            this.mLastMotionX = x2;
            float y2 = ev.getY();
            this.mInitialMotionY = y2;
            this.mLastMotionY = y2;
            boolean zOnDownAllowDrag = onDownAllowDrag((int) this.mLastMotionX, (int) y2);
            this.mActivePointerId = ev.getPointerId(0);
            if (zOnDownAllowDrag) {
                setDrawerState(this.mMenuVisible ? 8 : 0);
                stopAnimation();
                endPeek();
                this.mIsDragging = false;
            }
        } else if (action == 2) {
            int i3 = this.mActivePointerId;
            if (i3 != -1) {
                int iFindPointerIndex2 = ev.findPointerIndex(i3);
                if (iFindPointerIndex2 == -1) {
                    this.mIsDragging = false;
                    this.mActivePointerId = -1;
                    endDrag();
                    closeMenu(true);
                    return false;
                }
                float x3 = ev.getX(iFindPointerIndex2);
                float f2 = x3 - this.mLastMotionX;
                float y3 = ev.getY(iFindPointerIndex2);
                float f3 = y3 - this.mLastMotionY;
                if (checkTouchSlop(f2, f3)) {
                    if (this.mOnInterceptMoveEventListener != null && ((this.mTouchMode == 2 || this.mMenuVisible) && canChildrenScroll((int) f2, (int) f3, (int) x3, (int) y3))) {
                        endDrag();
                        requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                    if (onMoveAllowDrag((int) x3, (int) y3, f2, f3)) {
                        setDrawerState(2);
                        this.mIsDragging = true;
                        this.mLastMotionX = x3;
                        this.mLastMotionY = y3;
                    }
                }
            }
        } else if (action == 6) {
            onPointerUp(ev);
            this.mLastMotionX = ev.getX(ev.findPointerIndex(this.mActivePointerId));
            this.mLastMotionY = ev.getY(ev.findPointerIndex(this.mActivePointerId));
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        return this.mIsDragging;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        int i2 = r2 - l2;
        int i3 = b3 - t2;
        if (PLVMenuDrawer.USE_TRANSLATIONS) {
            this.mContentContainer.layout(0, 0, i2, i3);
        } else {
            int i4 = (int) this.mOffsetPixels;
            if (getPosition() == Position.LEFT || getPosition() == Position.RIGHT) {
                this.mContentContainer.layout(i4, 0, i2 + i4, i3);
            } else {
                this.mContentContainer.layout(0, i4, i2, i3 + i4);
            }
        }
        int i5 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i5 == 1) {
            this.mMenuContainer.layout(0, 0, this.mMenuSize, i3);
            return;
        }
        if (i5 == 2) {
            this.mMenuContainer.layout(0, 0, i2, this.mMenuSize);
        } else if (i5 == 3) {
            this.mMenuContainer.layout(i2 - this.mMenuSize, 0, i2, i3);
        } else {
            if (i5 != 4) {
                return;
            }
            this.mMenuContainer.layout(0, i3 - this.mMenuSize, i2, i3);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childMeasureSpec;
        int childMeasureSpec2;
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        if (mode == 0 || mode2 == 0) {
            throw new IllegalStateException("Must measure with an exact size");
        }
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (this.mOffsetPixels == -1.0f) {
            openMenu(false);
        }
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 2 || i2 == 4) {
            int childMeasureSpec3 = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, size);
            childMeasureSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, 0, this.mMenuSize);
            childMeasureSpec2 = childMeasureSpec3;
        } else {
            childMeasureSpec2 = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, this.mMenuSize);
            childMeasureSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, size2);
        }
        this.mMenuContainer.measure(childMeasureSpec2, childMeasureSpec);
        this.mContentContainer.measure(ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, size), ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, size2));
        setMeasuredDimension(size, size2);
        updateTouchAreaSize();
    }

    public boolean onMoveAllowDrag(int x2, int y2, float dx, float dy) {
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 1) {
            boolean z2 = this.mMenuVisible;
            return (!z2 && this.mInitialMotionX <= ((float) this.mTouchSize) && dx > 0.0f) || (z2 && ((float) x2) >= this.mOffsetPixels);
        }
        if (i2 == 2) {
            boolean z3 = this.mMenuVisible;
            return (!z3 && this.mInitialMotionY <= ((float) this.mTouchSize) && dy > 0.0f) || (z3 && ((float) y2) >= this.mOffsetPixels);
        }
        if (i2 == 3) {
            int width = getWidth();
            boolean z4 = this.mMenuVisible;
            return (!z4 && this.mInitialMotionX >= ((float) (width - this.mTouchSize)) && dx < 0.0f) || (z4 && ((float) x2) <= ((float) width) + this.mOffsetPixels);
        }
        if (i2 != 4) {
            return false;
        }
        int height = getHeight();
        boolean z5 = this.mMenuVisible;
        return (!z5 && this.mInitialMotionY >= ((float) (height - this.mTouchSize)) && dy < 0.0f) || (z5 && ((float) y2) <= ((float) height) + this.mOffsetPixels);
    }

    public void onMoveEvent(float dx, float dy) {
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 1) {
            setOffsetPixels(Math.min(Math.max(this.mOffsetPixels + dx, 0.0f), this.mMenuSize));
            return;
        }
        if (i2 == 2) {
            setOffsetPixels(Math.min(Math.max(this.mOffsetPixels + dy, 0.0f), this.mMenuSize));
        } else if (i2 == 3) {
            setOffsetPixels(Math.max(Math.min(this.mOffsetPixels + dx, 0.0f), -this.mMenuSize));
        } else {
            if (i2 != 4) {
                return;
            }
            setOffsetPixels(Math.max(Math.min(this.mOffsetPixels + dy, 0.0f), -this.mMenuSize));
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void onOffsetPixelsChanged(int offsetPixels) {
        if (PLVMenuDrawer.USE_TRANSLATIONS) {
            int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
            if (i2 == 2 || i2 == 4) {
                this.mContentContainer.setTranslationY(offsetPixels);
            } else {
                this.mContentContainer.setTranslationX(offsetPixels);
            }
        } else {
            int i3 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
            if (i3 == 2 || i3 == 4) {
                BuildLayerFrameLayout buildLayerFrameLayout = this.mContentContainer;
                buildLayerFrameLayout.offsetTopAndBottom(offsetPixels - buildLayerFrameLayout.getTop());
            } else {
                BuildLayerFrameLayout buildLayerFrameLayout2 = this.mContentContainer;
                buildLayerFrameLayout2.offsetLeftAndRight(offsetPixels - buildLayerFrameLayout2.getLeft());
            }
        }
        offsetMenu(offsetPixels);
        invalidate();
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        onOffsetPixelsChanged((int) this.mOffsetPixels);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00d4  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.ui.widget.menudrawer.SlidingDrawer.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void onUpEvent(int x2, int y2) {
        int i2 = (int) this.mOffsetPixels;
        int i3 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i3 == 1) {
            if (this.mIsDragging) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
                int xVelocity = (int) getXVelocity(this.mVelocityTracker);
                this.mLastMotionX = x2;
                animateOffsetTo(xVelocity > 0 ? this.mMenuSize : 0, xVelocity, true);
                return;
            }
            if (!this.mMenuVisible || x2 <= i2) {
                return;
            }
            closeMenu();
            return;
        }
        if (i3 == 2) {
            if (this.mIsDragging) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
                int yVelocity = (int) getYVelocity(this.mVelocityTracker);
                this.mLastMotionY = y2;
                animateOffsetTo(yVelocity > 0 ? this.mMenuSize : 0, yVelocity, true);
                return;
            }
            if (!this.mMenuVisible || y2 <= i2) {
                return;
            }
            closeMenu();
            return;
        }
        if (i3 == 3) {
            int width = getWidth();
            if (this.mIsDragging) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
                int xVelocity2 = (int) getXVelocity(this.mVelocityTracker);
                this.mLastMotionX = x2;
                animateOffsetTo(xVelocity2 <= 0 ? -this.mMenuSize : 0, xVelocity2, true);
                return;
            }
            if (!this.mMenuVisible || x2 >= width + i2) {
                return;
            }
            closeMenu();
            return;
        }
        if (i3 != 4) {
            return;
        }
        if (this.mIsDragging) {
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
            int yVelocity2 = (int) getYVelocity(this.mVelocityTracker);
            this.mLastMotionY = y2;
            animateOffsetTo(yVelocity2 < 0 ? -this.mMenuSize : 0, yVelocity2, true);
            return;
        }
        if (!this.mMenuVisible || y2 >= getHeight() + i2) {
            return;
        }
        closeMenu();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void openMenu(boolean animate) {
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        animateOffsetTo((i2 == 1 || i2 == 2) ? this.mMenuSize : (i2 == 3 || i2 == 4) ? -this.mMenuSize : 0, 0, animate);
    }

    public SlidingDrawer(Context context) {
        super(context);
    }

    public SlidingDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
