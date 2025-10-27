package com.psychiatrygarden.interfaceclass;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.psychiatrygarden.utils.SkinManager;

/* loaded from: classes4.dex */
public class TextClick extends ClickableSpan {
    public boolean isTrue;
    public Context mContext;
    public DomoIml mSpannableInterFace;

    public TextClick() {
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View widget) {
        this.mSpannableInterFace.clickToast();
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint ds) {
        if (this.isTrue) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
                ds.setColor(Color.parseColor("#6BB8CB"));
                return;
            } else {
                ds.setColor(Color.parseColor("#6BB8CB"));
                return;
            }
        }
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            ds.setColor(Color.parseColor("#dd594a"));
        } else {
            ds.setColor(Color.parseColor("#64729F"));
        }
    }

    public TextClick(DomoIml mSpannableInterFace) {
        this.mSpannableInterFace = mSpannableInterFace;
    }

    public TextClick(DomoIml mSpannableInterFace, boolean isTrue) {
        this.mSpannableInterFace = mSpannableInterFace;
        this.isTrue = isTrue;
    }
}
