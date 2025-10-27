package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView;
import com.psychiatrygarden.utils.ScreenUtil;
import com.yikaobang.yixue.R;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes6.dex */
public class CustomPagerTitlerView extends TextView implements IMeasurablePagerTitleView {
    protected int mNormalColor;
    protected int mSelectedColor;

    public CustomPagerTitlerView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        setGravity(17);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentBottom() {
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        return (int) ((getHeight() / 2) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentLeft() {
        String string;
        Rect rect = new Rect();
        if (getText().toString().contains("\n")) {
            string = "";
            for (String str : getText().toString().split("\\n")) {
                if (str.length() > string.length()) {
                    string = str;
                }
            }
        } else {
            string = getText().toString();
        }
        getPaint().getTextBounds(string, 0, string.length(), rect);
        return (getLeft() + (getWidth() / 2)) - (rect.width() / 2);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentRight() {
        String string;
        Rect rect = new Rect();
        if (getText().toString().contains("\n")) {
            string = "";
            for (String str : getText().toString().split("\\n")) {
                if (str.length() > string.length()) {
                    string = str;
                }
            }
        } else {
            string = getText().toString();
        }
        getPaint().getTextBounds(string, 0, string.length(), rect);
        return getLeft() + (getWidth() / 2) + (rect.width() / 2);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentTop() {
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        return (int) ((getHeight() / 2) - ((fontMetrics.bottom - fontMetrics.top) / 2.0f));
    }

    public int getNormalColor() {
        return this.mNormalColor;
    }

    public int getSelectedColor() {
        return this.mSelectedColor;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int index, int totalCount) {
        setTextColor(this.mNormalColor);
        if (index == 0) {
            setBackgroundResource(R.drawable.shape_round_discuss_default);
            ((LinearLayout.LayoutParams) getLayoutParams()).setMargins(ScreenUtil.getPxByDpF(getContext(), 0.5f), ScreenUtil.getPxByDpF(getContext(), 0.5f), 0, ScreenUtil.getPxByDpF(getContext(), 0.5f));
        } else if (index == totalCount - 1) {
            setBackgroundResource(R.drawable.shape_discuss_right_default);
            ((LinearLayout.LayoutParams) getLayoutParams()).setMargins(0, ScreenUtil.getPxByDpF(getContext(), 0.5f), ScreenUtil.getPxByDpF(getContext(), 0.5f), ScreenUtil.getPxByDpF(getContext(), 0.5f));
        } else {
            setBackgroundResource(R.drawable.shape_discuss_default);
            ((LinearLayout.LayoutParams) getLayoutParams()).setMargins(ScreenUtil.getPxByDpF(getContext(), 0.5f), ScreenUtil.getPxByDpF(getContext(), 0.5f), ScreenUtil.getPxByDpF(getContext(), 0.5f), ScreenUtil.getPxByDpF(getContext(), 0.5f));
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int index, int totalCount) {
        setTextColor(this.mSelectedColor);
        if (index == 0) {
            setBackgroundResource(R.drawable.shape_round_discuss_selected);
        } else if (index == totalCount - 1) {
            setBackgroundResource(R.drawable.shape_discuss_right_press);
        } else {
            setBackgroundResource(R.drawable.shape_discuss_press);
        }
        ((LinearLayout.LayoutParams) getLayoutParams()).setMargins(0, 0, 0, 0);
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
    }
}
