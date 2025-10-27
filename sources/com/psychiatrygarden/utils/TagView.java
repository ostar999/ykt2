package com.psychiatrygarden.utils;

import android.R;
import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/* loaded from: classes6.dex */
public class TagView extends FrameLayout implements Checkable {
    private static final int[] CHECK_STATE = {R.attr.state_checked};
    private boolean isChecked;

    public TagView(Context context) {
        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override // android.view.ViewGroup, android.view.View
    public int[] onCreateDrawableState(int extraSpace) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            View.mergeDrawableStates(iArrOnCreateDrawableState, CHECK_STATE);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            this.isChecked = checked;
            refreshDrawableState();
        }
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.isChecked);
    }
}
