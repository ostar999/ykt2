package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

/* loaded from: classes6.dex */
public class EditTextCursorWatcher extends AppCompatEditText {
    private int mySelStart;

    public EditTextCursorWatcher(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mySelStart = 0;
    }

    @Override // android.widget.TextView
    public void onSelectionChanged(int selStart, int selEnd) {
        try {
            int i2 = this.mySelStart;
            if (selStart < i2) {
                setSelection(i2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setStartSelect(int selStart) {
        this.mySelStart = selStart;
    }

    public EditTextCursorWatcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mySelStart = 0;
    }

    public EditTextCursorWatcher(Context context) {
        super(context);
        this.mySelStart = 0;
        setStartSelect(5);
    }
}
