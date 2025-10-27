package com.ruffian.library.widget.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/* loaded from: classes6.dex */
public class RCheckHelper extends RTextViewHelper {
    public RCheckHelper(Context context, CompoundButton compoundButton, AttributeSet attributeSet) {
        super(context, compoundButton, attributeSet);
    }

    @Override // com.ruffian.library.widget.helper.RTextViewHelper
    public boolean isCompoundButtonChecked() {
        T t2 = this.mView;
        if (t2 != 0) {
            return ((CompoundButton) t2).isChecked();
        }
        return false;
    }
}
