package com.ruffian.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.ruffian.library.widget.helper.RCheckHelper;
import com.ruffian.library.widget.iface.RHelper;

/* loaded from: classes6.dex */
public class RCheckBox extends AppCompatCheckBox implements RHelper<RCheckHelper> {
    private RCheckHelper mHelper;

    public RCheckBox(Context context) {
        this(context, null);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RCheckHelper rCheckHelper = this.mHelper;
        if (rCheckHelper != null) {
            rCheckHelper.drawIconWithText();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        RCheckHelper rCheckHelper = this.mHelper;
        if (rCheckHelper != null) {
            rCheckHelper.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z2) {
        RCheckHelper rCheckHelper = this.mHelper;
        if (rCheckHelper != null) {
            rCheckHelper.setChecked(z2);
        }
        super.setChecked(z2);
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        RCheckHelper rCheckHelper = this.mHelper;
        if (rCheckHelper != null) {
            rCheckHelper.setEnabled(z2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setSelected(boolean z2) {
        RCheckHelper rCheckHelper = this.mHelper;
        if (rCheckHelper != null) {
            rCheckHelper.setSelected(z2);
        }
        super.setSelected(z2);
    }

    public RCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHelper = new RCheckHelper(context, this, attributeSet);
    }

    @Override // com.ruffian.library.widget.iface.RHelper
    public RCheckHelper getHelper() {
        return this.mHelper;
    }
}
