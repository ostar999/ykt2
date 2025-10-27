package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class StaticDrawer extends PLVMenuDrawer {

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.menudrawer.StaticDrawer$1, reason: invalid class name */
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
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public StaticDrawer(Context context) {
        super(context);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void closeMenu(boolean animate) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void drawOverlay(Canvas canvas) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public boolean getOffsetMenuEnabled() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public int getTouchBezelSize() {
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public int getTouchMode() {
        return 0;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void initDrawer(Context context, AttributeSet attrs, int defStyle) {
        super.initDrawer(context, attrs, defStyle);
        super.addView(this.mMenuContainer, -1, new ViewGroup.LayoutParams(-1, -1));
        super.addView(this.mContentContainer, -1, new ViewGroup.LayoutParams(-1, -1));
        this.mIsStatic = true;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public boolean isDrawerIndicatorEnabled() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public boolean isMenuVisible() {
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        int i2 = r2 - l2;
        int i3 = b3 - t2;
        int i4 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i4 == 1) {
            this.mMenuContainer.layout(0, 0, this.mMenuSize, i3);
            this.mContentContainer.layout(this.mMenuSize, 0, i2, i3);
            return;
        }
        if (i4 == 2) {
            this.mMenuContainer.layout(i2 - this.mMenuSize, 0, i2, i3);
            this.mContentContainer.layout(0, 0, i2 - this.mMenuSize, i3);
        } else if (i4 == 3) {
            this.mMenuContainer.layout(0, 0, i2, this.mMenuSize);
            this.mContentContainer.layout(0, this.mMenuSize, i2, i3);
        } else {
            if (i4 != 4) {
                return;
            }
            this.mMenuContainer.layout(0, i3 - this.mMenuSize, i2, i3);
            this.mContentContainer.layout(0, 0, i2, i3 - this.mMenuSize);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        if (mode == 0 || mode2 == 0) {
            throw new IllegalStateException("Must measure with an exact size");
        }
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int i2 = AnonymousClass1.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 1 || i2 == 2) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size2, 1073741824);
            int i3 = this.mMenuSize;
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
            this.mContentContainer.measure(View.MeasureSpec.makeMeasureSpec(size - i3, 1073741824), iMakeMeasureSpec);
            this.mMenuContainer.measure(iMakeMeasureSpec2, iMakeMeasureSpec);
        } else if (i2 == 3 || i2 == 4) {
            int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
            int i4 = this.mMenuSize;
            int iMakeMeasureSpec4 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
            this.mContentContainer.measure(iMakeMeasureSpec3, View.MeasureSpec.makeMeasureSpec(size2 - i4, 1073741824));
            this.mMenuContainer.measure(iMakeMeasureSpec3, iMakeMeasureSpec4);
        }
        setMeasuredDimension(size, size2);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void onOffsetPixelsChanged(int offsetPixels) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void openMenu(boolean animate) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void peekDrawer() {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void peekDrawer(long delay) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void peekDrawer(long startDelay, long delay) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setDragAreaMenuBottom(int bottom) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setDrawerIndicatorEnabled(boolean enabled) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setHardwareLayerEnabled(boolean enabled) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setMenuSize(int size) {
        this.mMenuSize = size;
        requestLayout();
        invalidate();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setOffsetMenuEnabled(boolean offsetMenu) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setSlideDrawable(int drawableRes) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setSlideDrawable(Drawable drawable) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setTouchBezelSize(int size) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setTouchMode(int mode) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setupUpIndicator(Activity activity) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void toggleMenu(boolean animate) {
    }

    public StaticDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
