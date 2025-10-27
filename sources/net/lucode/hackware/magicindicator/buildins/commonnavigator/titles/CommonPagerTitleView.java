package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

/* loaded from: classes9.dex */
public class CommonPagerTitleView extends FrameLayout implements IMeasurablePagerTitleView {
    private ContentPositionDataProvider mContentPositionDataProvider;
    private OnPagerTitleChangeListener mOnPagerTitleChangeListener;

    public interface ContentPositionDataProvider {
        int getContentBottom();

        int getContentLeft();

        int getContentRight();

        int getContentTop();
    }

    public interface OnPagerTitleChangeListener {
        void onDeselected(int i2, int i3);

        void onEnter(int i2, int i3, float f2, boolean z2);

        void onLeave(int i2, int i3, float f2, boolean z2);

        void onSelected(int i2, int i3);
    }

    public CommonPagerTitleView(Context context) {
        super(context);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentBottom() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentBottom() : getBottom();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentLeft() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentLeft() : getLeft();
    }

    public ContentPositionDataProvider getContentPositionDataProvider() {
        return this.mContentPositionDataProvider;
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentRight() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentRight() : getRight();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentTop() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentTop() : getTop();
    }

    public OnPagerTitleChangeListener getOnPagerTitleChangeListener() {
        return this.mOnPagerTitleChangeListener;
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i2, int i3) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onDeselected(i2, i3);
        }
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int i2, int i3, float f2, boolean z2) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onEnter(i2, i3, f2, z2);
        }
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int i2, int i3, float f2, boolean z2) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onLeave(i2, i3, f2, z2);
        }
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i2, int i3) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onSelected(i2, i3);
        }
    }

    public void setContentPositionDataProvider(ContentPositionDataProvider contentPositionDataProvider) {
        this.mContentPositionDataProvider = contentPositionDataProvider;
    }

    public void setContentView(View view) {
        setContentView(view, null);
    }

    public void setOnPagerTitleChangeListener(OnPagerTitleChangeListener onPagerTitleChangeListener) {
        this.mOnPagerTitleChangeListener = onPagerTitleChangeListener;
    }

    public void setContentView(View view, FrameLayout.LayoutParams layoutParams) {
        removeAllViews();
        if (view != null) {
            if (layoutParams == null) {
                layoutParams = new FrameLayout.LayoutParams(-1, -1);
            }
            addView(view, layoutParams);
        }
    }

    public void setContentView(int i2) {
        setContentView(LayoutInflater.from(getContext()).inflate(i2, (ViewGroup) null), null);
    }
}
