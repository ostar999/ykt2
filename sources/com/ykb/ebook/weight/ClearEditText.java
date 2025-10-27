package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;

/* loaded from: classes8.dex */
public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private boolean hasFoucs;
    private Drawable mClearDrawable;
    private Context mContext;
    private Drawable mSearchDrawable;

    public ClearEditText(Context context) {
        this(context, null);
        this.mContext = context;
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    private void init() {
        this.mClearDrawable = getCompoundDrawables()[2];
        this.mSearchDrawable = getCompoundDrawables()[0];
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 2) {
            this.mClearDrawable = getResources().getDrawable(R.drawable.icon_close_night_svg);
            this.mSearchDrawable = getResources().getDrawable(R.drawable.chapter_search_icon_svg);
            setBackground(getResources().getDrawable(R.drawable.blue_theme_search_bg));
            Resources resources = getResources();
            int i2 = R.color.color_7380a9;
            setTextColor(resources.getColor(i2));
            setHintTextColor(getResources().getColor(i2));
        } else if (readConfig.getColorMode() == 1) {
            this.mClearDrawable = getResources().getDrawable(R.drawable.icon_close_yellow_theme_svg);
            this.mSearchDrawable = getResources().getDrawable(R.drawable.chapter_search_icon_svg);
            setBackground(getResources().getDrawable(R.drawable.yellow_theme_search_bg));
            setTextColor(getResources().getColor(R.color.color_303030));
            setHintTextColor(getResources().getColor(R.color.color_bfbfbf));
        } else {
            setBackground(getResources().getDrawable(R.drawable.white_theme_search_bg));
            this.mClearDrawable = getResources().getDrawable(R.drawable.ic_close);
            this.mSearchDrawable = getResources().getDrawable(R.drawable.icon_search_blue_theme_svg);
            setTextColor(getResources().getColor(R.color.color_303030));
            setHintTextColor(getResources().getColor(R.color.color_bfbfbf));
        }
        Drawable drawable = this.mClearDrawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        Drawable drawable2 = this.mSearchDrawable;
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mSearchDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z2) {
        this.hasFoucs = z2;
        if (z2) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    @Override // android.widget.TextView, android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        if (this.hasFoucs) {
            setClearIconVisible(charSequence.length() > 0);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 && getCompoundDrawables()[2] != null) {
            if (motionEvent.getX() > ((float) (getWidth() - getTotalPaddingRight())) && motionEvent.getX() < ((float) (getWidth() - getPaddingRight()))) {
                setText("");
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setClearIconVisible(boolean z2) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], z2 ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }

    public ClearEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.editTextStyle);
        this.mContext = context;
    }

    public ClearEditText(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mContext = context;
        init();
    }
}
