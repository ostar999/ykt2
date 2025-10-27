package com.psychiatrygarden.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/* loaded from: classes6.dex */
public class CustomTextView extends TextView {
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void insertDrawable(int id) throws Resources.NotFoundException {
        SpannableString spannableString = new SpannableString("image");
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, 40, 40);
        spannableString.setSpan(new ImageSpan(drawable, 1), 0, 5, 17);
        append(spannableString);
    }
}
