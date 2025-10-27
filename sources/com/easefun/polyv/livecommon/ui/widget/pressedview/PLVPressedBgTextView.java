package com.easefun.polyv.livecommon.ui.widget.pressedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes3.dex */
public class PLVPressedBgTextView extends AppCompatTextView {
    public PLVPressedBgTextView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        invalidate();
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int solidColor = getSolidColor();
        int iArgb = Color.argb(99, Color.red(solidColor), Color.green(solidColor), Color.blue(solidColor));
        if (isPressed()) {
            canvas.drawColor(iArgb);
        }
    }

    public PLVPressedBgTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PLVPressedBgTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
