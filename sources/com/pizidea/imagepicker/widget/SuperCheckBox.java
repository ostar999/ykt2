package com.pizidea.imagepicker.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/* loaded from: classes4.dex */
public class SuperCheckBox extends CompoundButton {
    private boolean canChecked;

    public SuperCheckBox(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.canChecked = true;
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return CheckBox.class.getName();
    }

    public boolean isCanChecked() {
        return this.canChecked;
    }

    @Override // android.widget.CompoundButton, android.view.View
    public boolean performClick() {
        boolean zPerformClick = super.performClick();
        if (!zPerformClick) {
            playSoundEffect(0);
        }
        return zPerformClick;
    }

    public void setCanChecked(boolean z2) {
        this.canChecked = z2;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void toggle() {
        if (this.canChecked) {
            super.toggle();
        }
    }

    public SuperCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.canChecked = true;
    }

    public SuperCheckBox(Context context) {
        super(context);
        this.canChecked = true;
    }
}
