package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private boolean hasFoucs;
    private Drawable mClearDrawable;
    private Context mContext;

    public ClearEditText(Context context) {
        this(context, null);
        this.mContext = context;
    }

    private void init() {
        Drawable drawable = getCompoundDrawables()[2];
        this.mClearDrawable = drawable;
        if (drawable == null) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                this.mClearDrawable = getResources().getDrawable(R.drawable.edit_delete);
            } else {
                this.mClearDrawable = getResources().getDrawable(R.drawable.edit_delete_night);
            }
        }
        Drawable drawable2 = this.mClearDrawable;
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s2) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View v2, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override // android.widget.TextView, android.text.TextWatcher
    public void onTextChanged(CharSequence s2, int start, int count, int after) {
        if (this.hasFoucs) {
            setClearIconVisible(s2.length() > 0);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1 && getCompoundDrawables()[2] != null) {
            if (event.getX() > ((float) (getWidth() - getTotalPaddingRight())) && event.getX() < ((float) (getWidth() - getPaddingRight()))) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    public void setClearIconVisible(boolean visible) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], visible ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        this.mContext = context;
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }
}
