package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.Nullable;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class AttachButton extends View {
    private final String TAG;
    private boolean customIsAttach;
    private boolean customIsDrag;
    private boolean isDrug;
    private float mLastRawX;
    private float mLastRawY;
    private int mRootMeasuredHeight;
    private int mRootMeasuredWidth;
    private int mRootTopY;
    private OnClickAttachListener onClickAttachListener;

    public interface OnClickAttachListener {
        void onDeleteClick();

        void onViewClick();
    }

    public AttachButton(Context context) {
        this(context, null);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.AttachButton);
        this.customIsAttach = typedArrayObtainStyledAttributes.getBoolean(0, true);
        this.customIsDrag = typedArrayObtainStyledAttributes.getBoolean(1, true);
        this.mRootMeasuredWidth = ScreenUtil.getScreenWidth((Activity) context);
        this.mLastRawX = ScreenUtil.getScreenWidth(r0) - ScreenUtil.getPxByDp(context, 12);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.customIsDrag) {
            float rawX = ev.getRawX();
            float rawY = ev.getRawY();
            int action = ev.getAction();
            if (action == 0) {
                this.isDrug = false;
                this.mLastRawX = rawX;
                this.mLastRawY = rawY;
                ViewGroup viewGroup = (ViewGroup) getParent();
                if (viewGroup != null) {
                    int[] iArr = new int[2];
                    viewGroup.getLocationInWindow(iArr);
                    this.mRootMeasuredHeight = viewGroup.getMeasuredHeight();
                    this.mRootMeasuredWidth = viewGroup.getMeasuredWidth();
                    this.mRootTopY = iArr[1];
                }
            } else if (action != 1) {
                if (action == 2) {
                    if (rawX >= 0.0f && rawX <= this.mRootMeasuredWidth) {
                        if (rawY >= this.mRootTopY && rawY <= this.mRootMeasuredHeight + r4) {
                            float f2 = rawX - this.mLastRawX;
                            float f3 = rawY - this.mLastRawY;
                            if (!this.isDrug) {
                                if (Math.sqrt((f2 * f2) + (f3 * f3)) < ScreenUtil.getPxByDp(getContext(), 4)) {
                                    this.isDrug = false;
                                } else {
                                    this.isDrug = true;
                                }
                            }
                            float x2 = getX() + f2;
                            float y2 = getY() + f3;
                            float width = this.mRootMeasuredWidth - getWidth();
                            float height = this.mRootMeasuredHeight - getHeight();
                            if (x2 < 0.0f) {
                                x2 = 0.0f;
                            } else if (x2 > width) {
                                x2 = width;
                            }
                            float f4 = y2 >= 0.0f ? y2 > height ? height : y2 : 0.0f;
                            setX(x2);
                            setY(f4);
                            this.mLastRawX = rawX;
                            this.mLastRawY = rawY;
                        }
                    }
                }
            } else if (this.customIsAttach) {
                if (this.isDrug) {
                    if (this.mLastRawX <= this.mRootMeasuredWidth / 2.0f) {
                        animate().setInterpolator(new DecelerateInterpolator()).setDuration(300L).x(ScreenUtil.getPxByDp(getContext(), 12) * 1.0f).start();
                    } else {
                        animate().setInterpolator(new DecelerateInterpolator()).setDuration(300L).x((this.mRootMeasuredWidth - getWidth()) - ScreenUtil.getPxByDp(getContext(), 12)).start();
                    }
                } else if (this.onClickAttachListener != null) {
                    float x3 = ev.getX();
                    float y3 = ev.getY();
                    if (getMeasuredWidth() / 3.0f >= x3 || y3 >= getMeasuredHeight() / 3.0f) {
                        this.onClickAttachListener.onViewClick();
                    } else {
                        this.onClickAttachListener.onDeleteClick();
                    }
                }
            }
        }
        boolean z2 = this.isDrug;
        return z2 ? z2 : super.onTouchEvent(ev);
    }

    public void setClickListener(OnClickAttachListener onClickAttachListener) {
        this.onClickAttachListener = onClickAttachListener;
    }

    public void setIndextXY(float endX, float endY) {
        setY(endY);
        this.mLastRawX = endX;
        int pxByDp = ScreenUtil.getPxByDp(getContext(), 12);
        if (this.mLastRawX <= this.mRootMeasuredWidth / 2.0f) {
            setX(pxByDp);
        } else {
            setX((r4 - getWidth()) - pxByDp);
        }
        setClickListener(this.onClickAttachListener);
    }

    public void setScrollVisible(boolean isVisible) {
        int pxByDp;
        if (isVisible) {
            setAlpha(1.0f);
            pxByDp = ScreenUtil.getPxByDp(getContext(), 12);
        } else {
            setAlpha(0.5f);
            pxByDp = -ScreenUtil.getPxByDp(getContext(), 28);
        }
        if (this.mLastRawX <= this.mRootMeasuredWidth / 2.0f) {
            animate().setInterpolator(new DecelerateInterpolator()).setDuration(300L).x(pxByDp * 1.0f).start();
        } else {
            animate().setInterpolator(new DecelerateInterpolator()).setDuration(300L).x((this.mRootMeasuredWidth - getWidth()) - pxByDp).start();
        }
    }

    public AttachButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttachButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = "AttachButton";
        this.isDrug = false;
        this.mRootMeasuredWidth = 0;
        this.mRootMeasuredHeight = 0;
        this.mRootTopY = 0;
        initAttrs(context, attrs);
    }
}
