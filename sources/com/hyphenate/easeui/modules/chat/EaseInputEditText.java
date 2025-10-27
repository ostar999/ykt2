package com.hyphenate.easeui.modules.chat;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/* loaded from: classes4.dex */
public class EaseInputEditText extends EditText implements View.OnKeyListener, TextView.OnEditorActionListener {
    private boolean ctrlPress;
    private OnEditTextChangeListener listener;

    public interface OnEditTextChangeListener {
        void onClickKeyboardSendBtn(String str);

        void onEditTextHasFocus(boolean z2);
    }

    public EaseInputEditText(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 4 && (keyEvent == null || keyEvent.getKeyCode() != 66 || keyEvent.getAction() != 0 || !this.ctrlPress)) {
            return false;
        }
        String string = getText().toString();
        if (this.listener == null) {
            return true;
        }
        setText("");
        this.listener.onClickKeyboardSendBtn(string);
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    public void onFocusChanged(boolean z2, int i2, Rect rect) {
        super.onFocusChanged(z2, i2, rect);
        OnEditTextChangeListener onEditTextChangeListener = this.listener;
        if (onEditTextChangeListener != null) {
            onEditTextChangeListener.onEditTextHasFocus(z2);
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i2, KeyEvent keyEvent) {
        if (i2 == 0) {
            if (keyEvent.getAction() == 0) {
                this.ctrlPress = true;
            } else if (keyEvent.getAction() == 1) {
                this.ctrlPress = false;
            }
        }
        return false;
    }

    public void setOnEditTextChangeListener(OnEditTextChangeListener onEditTextChangeListener) {
        this.listener = onEditTextChangeListener;
    }

    public EaseInputEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EaseInputEditText(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.ctrlPress = false;
        setOnKeyListener(this);
        setOnEditorActionListener(this);
    }
}
