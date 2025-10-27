package com.psychiatrygarden.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.core.internal.view.SupportMenu;

/* loaded from: classes6.dex */
public class CustomClickableSpan extends ClickableSpan {
    private String content;
    private int mEnd;
    private int mStart;
    private OnClickListener onClickListener;

    /* renamed from: x, reason: collision with root package name */
    private int f16260x;

    /* renamed from: y, reason: collision with root package name */
    private int f16261y;

    public interface OnClickListener {
        void onClick(View v2, String content, int x2, int y2);
    }

    public CustomClickableSpan() {
    }

    public int getEnd() {
        return this.mEnd;
    }

    public int getStart() {
        return this.mStart;
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View widget) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(widget, this.content, this.f16260x, this.f16261y);
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEnd(int mEnd) {
        this.mEnd = mEnd;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setStart(int mStart) {
        this.mStart = mStart;
    }

    public void setX(int x2) {
        this.f16260x = x2;
    }

    public void setY(int y2) {
        this.f16261y = y2;
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(SupportMenu.CATEGORY_MASK);
    }

    public CustomClickableSpan(int start, int end) {
        this(start, end, "");
    }

    public CustomClickableSpan(int mStart, int mEnd, String content) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.content = content;
    }
}
