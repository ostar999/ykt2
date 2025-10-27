package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.widget.TextView;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView;

/* loaded from: classes3.dex */
public class PLVSimplePagerTitleView extends TextView implements IPLVMeasurablePagerTitleView {
    protected int mNormalColor;
    protected int mSelectedColor;

    public PLVSimplePagerTitleView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        setGravity(17);
        int iDip2px = PLVUIUtil.dip2px(context, 10.0d);
        setPadding(iDip2px, 0, iDip2px, 0);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentBottom() {
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        return (int) ((getHeight() / 2) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
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

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
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

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
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

    public void onDeselected(int index, int totalCount) {
        setTextColor(this.mNormalColor);
    }

    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    public void onSelected(int index, int totalCount) {
        setTextColor(this.mSelectedColor);
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
    }
}
