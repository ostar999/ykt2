package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lxj.xpopup.interfaces.OnClickOutsideListener;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class PartShadowContainer extends FrameLayout {
    public boolean isDismissOnTouchOutside;
    private OnClickOutsideListener listener;

    /* renamed from: x, reason: collision with root package name */
    private float f8904x;

    /* renamed from: y, reason: collision with root package name */
    private float f8905y;

    public PartShadowContainer(@NonNull Context context) {
        super(context);
        this.isDismissOnTouchOutside = true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        OnClickOutsideListener onClickOutsideListener;
        View childAt = getChildAt(0);
        int[] iArr = new int[2];
        childAt.getLocationInWindow(iArr);
        int i2 = iArr[0];
        if (!XPopupUtils.isInRect(motionEvent.getRawX(), motionEvent.getRawY(), new Rect(i2, iArr[1], childAt.getMeasuredWidth() + i2, iArr[1] + childAt.getMeasuredHeight()))) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.f8904x = motionEvent.getX();
                this.f8905y = motionEvent.getY();
            } else if (action == 1 || action == 2 || action == 3) {
                if (((float) Math.sqrt(Math.pow(motionEvent.getX() - this.f8904x, 2.0d) + Math.pow(motionEvent.getY() - this.f8905y, 2.0d))) < ViewConfiguration.get(getContext()).getScaledTouchSlop() && this.isDismissOnTouchOutside && (onClickOutsideListener = this.listener) != null) {
                    onClickOutsideListener.onClickOutside();
                }
                this.f8904x = 0.0f;
                this.f8905y = 0.0f;
            }
        }
        return true;
    }

    public void setOnClickOutsideListener(OnClickOutsideListener onClickOutsideListener) {
        this.listener = onClickOutsideListener;
    }

    public PartShadowContainer(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PartShadowContainer(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.isDismissOnTouchOutside = true;
    }
}
