package com.ruffian.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatEditText;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.ruffian.library.widget.iface.RHelper;

/* loaded from: classes6.dex */
public class REditText extends AppCompatEditText implements RHelper<RTextViewHelper> {
    private RTextViewHelper mHelper;

    public REditText(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        RTextViewHelper rTextViewHelper = this.mHelper;
        if (rTextViewHelper != null) {
            rTextViewHelper.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        RTextViewHelper rTextViewHelper = this.mHelper;
        if (rTextViewHelper != null) {
            rTextViewHelper.setEnabled(z2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setSelected(boolean z2) {
        RTextViewHelper rTextViewHelper = this.mHelper;
        if (rTextViewHelper != null) {
            rTextViewHelper.setSelected(z2);
        }
        super.setSelected(z2);
    }

    public REditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHelper = new RTextViewHelper(context, this, attributeSet);
    }

    @Override // com.ruffian.library.widget.iface.RHelper
    public RTextViewHelper getHelper() {
        return this.mHelper;
    }
}
