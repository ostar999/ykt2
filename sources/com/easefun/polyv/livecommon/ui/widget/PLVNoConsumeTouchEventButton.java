package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/* loaded from: classes3.dex */
public class PLVNoConsumeTouchEventButton extends AppCompatButton {
    private GestureDetector gestureDetector;
    private View.OnClickListener onClickListener;
    private View shareTouchEventView;

    public PLVNoConsumeTouchEventButton(Context context) {
        this(context, null);
    }

    private void initView() {
        this.gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVNoConsumeTouchEventButton.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent e2) {
                if (!PLVNoConsumeTouchEventButton.this.isEnabled()) {
                    return false;
                }
                if (PLVNoConsumeTouchEventButton.this.onClickListener == null) {
                    return true;
                }
                PLVNoConsumeTouchEventButton.this.onClickListener.onClick(PLVNoConsumeTouchEventButton.this);
                return true;
            }
        });
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        View view = this.shareTouchEventView;
        if (view == null) {
            return true;
        }
        view.onTouchEvent(event);
        return true;
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        this.onClickListener.onClick(this);
        return true;
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener l2) {
        this.onClickListener = l2;
    }

    public void setShareTouchEventView(View shareTouchEventView) {
        this.shareTouchEventView = shareTouchEventView;
    }

    public PLVNoConsumeTouchEventButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVNoConsumeTouchEventButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
