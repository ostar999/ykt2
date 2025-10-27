package com.psychiatrygarden.activity.purchase.util;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TabWidget;
import android.widget.TextView;

/* loaded from: classes5.dex */
public class BadgeView extends TextView {
    private static final int DEFAULT_BADGE_COLOR = Color.parseColor("#CCFF0000");
    private static final int DEFAULT_CORNER_RADIUS_DIP = 8;
    private static final int DEFAULT_LR_PADDING_DIP = 5;
    private static final int DEFAULT_MARGIN_DIP = 5;
    private static final int DEFAULT_POSITION = 2;
    private static final int DEFAULT_TEXT_COLOR = -1;
    public static final int POSITION_BOTTOM_LEFT = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_CENTER = 5;
    public static final int POSITION_TOP_LEFT = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    private static Animation fadeIn;
    private static Animation fadeOut;
    private ShapeDrawable badgeBg;
    private int badgeColor;
    private int badgeMarginH;
    private int badgeMarginV;
    private int badgePosition;
    private Context context;
    private boolean isShown;
    private View target;
    private int targetTabIndex;

    public BadgeView(Context context) {
        this(context, (AttributeSet) null, R.attr.textViewStyle);
    }

    private void applyLayoutParams() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        int i2 = this.badgePosition;
        if (i2 == 1) {
            layoutParams.gravity = 51;
            layoutParams.setMargins(this.badgeMarginH, this.badgeMarginV, 0, 0);
        } else if (i2 == 2) {
            layoutParams.gravity = 53;
            layoutParams.setMargins(0, this.badgeMarginV, this.badgeMarginH, 0);
        } else if (i2 == 3) {
            layoutParams.gravity = 83;
            layoutParams.setMargins(this.badgeMarginH, 0, 0, this.badgeMarginV);
        } else if (i2 == 4) {
            layoutParams.gravity = 85;
            layoutParams.setMargins(0, 0, this.badgeMarginH, this.badgeMarginV);
        } else if (i2 == 5) {
            layoutParams.gravity = 17;
            layoutParams.setMargins(0, 0, 0, 0);
        }
        setLayoutParams(layoutParams);
    }

    private void applyTo(View target) {
        ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
        ViewParent parent = target.getParent();
        FrameLayout frameLayout = new FrameLayout(this.context);
        if (target instanceof TabWidget) {
            View childTabViewAt = ((TabWidget) target).getChildTabViewAt(this.targetTabIndex);
            this.target = childTabViewAt;
            ((ViewGroup) childTabViewAt).addView(frameLayout, new ViewGroup.LayoutParams(-1, -1));
            setVisibility(8);
            frameLayout.addView(this);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        int iIndexOfChild = viewGroup.indexOfChild(target);
        viewGroup.removeView(target);
        viewGroup.addView(frameLayout, iIndexOfChild, layoutParams);
        frameLayout.addView(target);
        setVisibility(8);
        frameLayout.addView(this);
        viewGroup.invalidate();
    }

    private int dipToPixels(int dip) {
        return (int) TypedValue.applyDimension(1, dip, getResources().getDisplayMetrics());
    }

    private ShapeDrawable getDefaultBackground() {
        float fDipToPixels = dipToPixels(8);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{fDipToPixels, fDipToPixels, fDipToPixels, fDipToPixels, fDipToPixels, fDipToPixels, fDipToPixels, fDipToPixels}, null, null));
        shapeDrawable.getPaint().setColor(this.badgeColor);
        return shapeDrawable;
    }

    private void init(Context context, View target, int tabIndex) {
        this.context = context;
        this.target = target;
        this.targetTabIndex = tabIndex;
        this.badgePosition = 2;
        int iDipToPixels = dipToPixels(5);
        this.badgeMarginH = iDipToPixels;
        this.badgeMarginV = iDipToPixels;
        this.badgeColor = DEFAULT_BADGE_COLOR;
        setTypeface(Typeface.DEFAULT_BOLD);
        int iDipToPixels2 = dipToPixels(5);
        setPadding(iDipToPixels2, 0, iDipToPixels2, 0);
        setTextColor(-1);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeIn = alphaAnimation;
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200L);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        fadeOut = alphaAnimation2;
        alphaAnimation2.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200L);
        this.isShown = false;
        View view = this.target;
        if (view != null) {
            applyTo(view);
        } else {
            show();
        }
    }

    public int decrement(int offset) {
        return increment(-offset);
    }

    public int getBadgeBackgroundColor() {
        return this.badgeColor;
    }

    public int getBadgePosition() {
        return this.badgePosition;
    }

    public int getHorizontalBadgeMargin() {
        return this.badgeMarginH;
    }

    public View getTarget() {
        return this.target;
    }

    public int getVerticalBadgeMargin() {
        return this.badgeMarginV;
    }

    public void hide() {
        hide(false, null);
    }

    public int increment(int offset) throws NumberFormatException {
        CharSequence text = getText();
        int i2 = 0;
        if (text != null) {
            try {
                i2 = Integer.parseInt(text.toString());
            } catch (NumberFormatException unused) {
            }
        }
        int i3 = i2 + offset;
        setText(String.valueOf(i3));
        return i3;
    }

    @Override // android.view.View
    public boolean isShown() {
        return this.isShown;
    }

    public void setBadgeBackgroundColor(int badgeColor) {
        this.badgeColor = badgeColor;
        this.badgeBg = getDefaultBackground();
    }

    public void setBadgeMargin(int badgeMargin) {
        this.badgeMarginH = badgeMargin;
        this.badgeMarginV = badgeMargin;
    }

    public void setBadgePosition(int layoutPosition) {
        this.badgePosition = layoutPosition;
    }

    public void show() {
        show(false, null);
    }

    public void toggle() {
        toggle(false, null, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.textViewStyle);
    }

    public void hide(boolean animate) {
        hide(animate, fadeOut);
    }

    public void show(boolean animate) {
        show(animate, fadeIn);
    }

    public void toggle(boolean animate) {
        toggle(animate, fadeIn, fadeOut);
    }

    public BadgeView(Context context, View target) {
        this(context, null, R.attr.textViewStyle, target, 0);
    }

    public void hide(Animation anim) {
        hide(true, anim);
    }

    public void setBadgeMargin(int horizontal, int vertical) {
        this.badgeMarginH = horizontal;
        this.badgeMarginV = vertical;
    }

    public void show(Animation anim) {
        show(true, anim);
    }

    public void toggle(Animation animIn, Animation animOut) {
        toggle(true, animIn, animOut);
    }

    public BadgeView(Context context, TabWidget target, int index) {
        this(context, null, R.attr.textViewStyle, target, index);
    }

    private void hide(boolean animate, Animation anim) {
        setVisibility(8);
        if (animate) {
            startAnimation(anim);
        }
        this.isShown = false;
    }

    private void show(boolean animate, Animation anim) {
        if (getBackground() == null) {
            if (this.badgeBg == null) {
                this.badgeBg = getDefaultBackground();
            }
            setBackgroundDrawable(this.badgeBg);
        }
        applyLayoutParams();
        if (animate) {
            startAnimation(anim);
        }
        setVisibility(0);
        this.isShown = true;
    }

    private void toggle(boolean animate, Animation animIn, Animation animOut) {
        if (this.isShown) {
            hide(animate && animOut != null, animOut);
        } else {
            show(animate && animIn != null, animIn);
        }
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null, 0);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle, View target, int tabIndex) {
        super(context, attrs, defStyle);
        init(context, target, tabIndex);
    }
}
