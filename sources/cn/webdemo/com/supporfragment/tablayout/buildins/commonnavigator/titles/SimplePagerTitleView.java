package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.widget.TextView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes.dex */
public class SimplePagerTitleView extends TextView implements IMeasurablePagerTitleView {
    protected int mNormalColor;
    protected int mSelectedColor;

    public SimplePagerTitleView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        setGravity(17);
        int iDip2px = UIUtil.dip2px(context, 10.0d);
        setPadding(iDip2px, 0, iDip2px, 0);
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

    public void onDeselected(int i2, int i3) {
        setTextColor(this.mNormalColor);
    }

    public void onEnter(int i2, int i3, float f2, boolean z2) {
    }

    public void onLeave(int i2, int i3, float f2, boolean z2) {
    }

    public void onSelected(int i2, int i3) {
        setTextColor(this.mSelectedColor);
    }

    public void setNormalColor(int i2) {
        this.mNormalColor = i2;
    }

    public void setSelectedColor(int i2) {
        this.mSelectedColor = i2;
    }
}
