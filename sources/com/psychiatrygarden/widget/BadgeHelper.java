package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/* loaded from: classes6.dex */
public class BadgeHelper extends View {
    private static final String TAG = "BadgeHelper";
    private int badgeColor;
    private int bottomMargin;

    /* renamed from: h, reason: collision with root package name */
    private int f16258h;
    private boolean isCenterVertical;
    private boolean isOverlap;
    private boolean isSetup;
    private int leftMargin;
    private Paint mBackgroundPaint;
    private boolean mIgnoreTargetPadding;
    private Paint mTextPaint;
    private int number;
    private final RectF rect;
    private int rightMargin;
    private String text;
    private int textColor;
    private float textSize;
    private int topMargin;
    private int type;

    /* renamed from: w, reason: collision with root package name */
    private int f16259w;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        public static final int TYPE_POINT = 0;
        public static final int TYPE_TEXT = 1;
    }

    public BadgeHelper(Context context) {
        super(context);
        this.text = "0";
        this.type = 0;
        this.rect = new RectF();
        this.badgeColor = -2936293;
        this.textColor = -1;
    }

    private float getTextHeight(String text, Paint p2) {
        p2.getTextBounds(text, 0, text.length(), new Rect());
        return r0.height();
    }

    private float getTextWidth(String text, Paint p2) {
        return p2.measureText(text, 0, text.length());
    }

    private void init(int type, boolean isOverlap) {
        this.type = type;
        this.isOverlap = isOverlap;
        float f2 = getResources().getDisplayMetrics().density;
        if (type == 0) {
            Paint paint = new Paint();
            this.mBackgroundPaint = paint;
            paint.setStyle(Paint.Style.FILL);
            this.mBackgroundPaint.setFlags(1);
            this.mBackgroundPaint.setColor(this.badgeColor);
            int iRound = Math.round(f2 * 7.0f);
            this.f16258h = iRound;
            this.f16259w = iRound;
            return;
        }
        if (type != 1) {
            return;
        }
        Paint paint2 = new Paint();
        this.mBackgroundPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mBackgroundPaint.setFlags(1);
        this.mBackgroundPaint.setColor(this.badgeColor);
        Paint paint3 = new Paint();
        this.mTextPaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        this.mTextPaint.setFlags(1);
        this.mTextPaint.setColor(this.textColor);
        float f3 = this.textSize;
        if (f3 == 0.0f) {
            this.mTextPaint.setTextSize(f2 * 10.0f);
        } else {
            this.mTextPaint.setTextSize(f3);
        }
        int iRound2 = Math.round(getTextWidth("99", this.mTextPaint) * 1.4f);
        this.f16258h = iRound2;
        this.f16259w = iRound2;
    }

    public void bindToTargetView(TabLayout target, int tabIndex) throws NoSuchFieldException, SecurityException {
        View view;
        TabLayout.Tab tabAt = target.getTabAt(tabIndex);
        try {
            Field declaredField = TabLayout.Tab.class.getDeclaredField("mView");
            declaredField.setAccessible(true);
            view = (View) declaredField.get(tabAt);
        } catch (Exception e2) {
            e2.printStackTrace();
            view = null;
        }
        View view2 = view;
        if (view != null) {
            try {
                Field declaredField2 = view.getClass().getDeclaredField("mTextView");
                declaredField2.setAccessible(true);
                view2 = (View) declaredField2.get(view);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (view2 != null) {
            bindToTargetView(view2);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.rect;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = getWidth();
        this.rect.bottom = getHeight();
        canvas.drawRoundRect(this.rect, getWidth() / 2.0f, getWidth() / 2.0f, this.mBackgroundPaint);
        if (this.type == 1) {
            canvas.drawText(this.text, (getWidth() / 2.0f) - (getTextWidth(this.text, this.mTextPaint) / 2.0f), (getHeight() / 2.0f) + (getTextHeight(this.text, this.mTextPaint) / 2.0f), this.mTextPaint);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i2;
        int i3 = this.f16259w;
        if (i3 <= 0 || (i2 = this.f16258h) <= 0) {
            throw new IllegalStateException("如果你自定义了小红点的宽高,就不能设置其宽高小于0 ,否则请不要设置!");
        }
        setMeasuredDimension(i3, i2);
    }

    public BadgeHelper setBadgeCenterVertical() {
        this.isCenterVertical = true;
        return this;
    }

    public BadgeHelper setBadgeColor(int mBadgeColor) {
        this.badgeColor = mBadgeColor;
        return this;
    }

    public void setBadgeEnable(boolean enable) {
        setVisibility(enable ? 0 : 4);
    }

    public BadgeHelper setBadgeMargins(int left, int top2, int right, int bottom) {
        this.leftMargin = left;
        this.topMargin = top2;
        this.rightMargin = right;
        this.bottomMargin = bottom;
        return this;
    }

    public void setBadgeNumber(int number) {
        this.number = number;
        this.text = String.valueOf(number);
        if (this.isSetup) {
            if (number == 0) {
                setVisibility(4);
            } else {
                setVisibility(0);
            }
            invalidate();
        }
    }

    public BadgeHelper setBadgeOverlap(boolean isOverlap) {
        return setBadgeOverlap(isOverlap, false);
    }

    public BadgeHelper setBadgeSize(int w2, int h2) {
        this.f16259w = w2;
        this.f16258h = h2;
        return this;
    }

    public BadgeHelper setBadgeTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public BadgeHelper setBadgeTextSize(int textSize) {
        Context context = getContext();
        this.textSize = TypedValue.applyDimension(2, textSize, (context == null ? Resources.getSystem() : context.getResources()).getDisplayMetrics());
        return this;
    }

    public BadgeHelper setBadgeType(int type) {
        this.type = type;
        return this;
    }

    public BadgeHelper setBadgeOverlap(boolean isOverlap, boolean isIgnoreTargetPadding) {
        this.isOverlap = isOverlap;
        this.mIgnoreTargetPadding = isIgnoreTargetPadding;
        if (!isOverlap && isIgnoreTargetPadding) {
            Log.w(TAG, "警告:只有重叠模式isOverlap=true 设置mIgnoreTargetPadding才有意义");
        }
        return this;
    }

    public void bindToTargetView(View target) {
        init(this.type, this.isOverlap);
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (target == null) {
            return;
        }
        if (target.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) target.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(target);
            viewGroup.removeView(target);
            if (this.isOverlap) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
                frameLayout.setLayoutParams(layoutParams);
                target.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                viewGroup.addView(frameLayout, iIndexOfChild, layoutParams);
                frameLayout.addView(target);
                frameLayout.addView(this);
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getLayoutParams();
                if (this.isCenterVertical) {
                    layoutParams2.gravity = 16;
                } else {
                    layoutParams2.gravity = BadgeDrawable.TOP_END;
                }
                if (this.mIgnoreTargetPadding) {
                    layoutParams2.rightMargin = target.getPaddingRight() - this.f16259w;
                    layoutParams2.topMargin = target.getPaddingTop() - (this.f16258h / 2);
                }
                setLayoutParams(layoutParams2);
            } else {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(0);
                ViewGroup.LayoutParams layoutParams3 = target.getLayoutParams();
                linearLayout.setLayoutParams(layoutParams3);
                target.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                viewGroup.addView(linearLayout, iIndexOfChild, layoutParams3);
                linearLayout.addView(target);
                linearLayout.addView(this);
                if (this.isCenterVertical) {
                    linearLayout.setGravity(16);
                }
            }
            if ((this.leftMargin > 0 || this.topMargin > 0 || this.rightMargin > 0 || this.bottomMargin > 0) && (getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                marginLayoutParams.setMargins(this.leftMargin, this.topMargin, this.rightMargin, this.bottomMargin);
                setLayoutParams(marginLayoutParams);
            }
            this.isSetup = true;
        } else if (target.getParent() == null) {
            throw new IllegalStateException("目标View不能没有父布局!");
        }
        if (this.number == 0) {
            setVisibility(4);
        } else {
            setVisibility(0);
        }
    }
}
