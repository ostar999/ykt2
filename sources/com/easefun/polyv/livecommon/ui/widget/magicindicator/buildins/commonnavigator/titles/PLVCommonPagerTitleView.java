package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView;

/* loaded from: classes3.dex */
public class PLVCommonPagerTitleView extends FrameLayout implements IPLVMeasurablePagerTitleView {
    private ContentPositionDataProvider mContentPositionDataProvider;
    private OnPagerTitleChangeListener mOnPagerTitleChangeListener;

    public interface ContentPositionDataProvider {
        int getContentBottom();

        int getContentLeft();

        int getContentRight();

        int getContentTop();
    }

    public interface OnPagerTitleChangeListener {
        void onDeselected(int index, int totalCount);

        void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight);

        void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight);

        void onSelected(int index, int totalCount);
    }

    public PLVCommonPagerTitleView(Context context) {
        super(context);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentBottom() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentBottom() : getBottom();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentLeft() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentLeft() : getLeft();
    }

    public ContentPositionDataProvider getContentPositionDataProvider() {
        return this.mContentPositionDataProvider;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentRight() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentRight() : getRight();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentTop() {
        ContentPositionDataProvider contentPositionDataProvider = this.mContentPositionDataProvider;
        return contentPositionDataProvider != null ? contentPositionDataProvider.getContentTop() : getTop();
    }

    public OnPagerTitleChangeListener getOnPagerTitleChangeListener() {
        return this.mOnPagerTitleChangeListener;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onDeselected(int index, int totalCount) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onDeselected(index, totalCount);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onEnter(index, totalCount, enterPercent, leftToRight);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onLeave(index, totalCount, leavePercent, leftToRight);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onSelected(int index, int totalCount) {
        OnPagerTitleChangeListener onPagerTitleChangeListener = this.mOnPagerTitleChangeListener;
        if (onPagerTitleChangeListener != null) {
            onPagerTitleChangeListener.onSelected(index, totalCount);
        }
    }

    public void setContentPositionDataProvider(ContentPositionDataProvider contentPositionDataProvider) {
        this.mContentPositionDataProvider = contentPositionDataProvider;
    }

    public void setContentView(View contentView) {
        setContentView(contentView, null);
    }

    public void setOnPagerTitleChangeListener(OnPagerTitleChangeListener onPagerTitleChangeListener) {
        this.mOnPagerTitleChangeListener = onPagerTitleChangeListener;
    }

    public void setContentView(View contentView, FrameLayout.LayoutParams lp) {
        removeAllViews();
        if (contentView != null) {
            if (lp == null) {
                lp = new FrameLayout.LayoutParams(-1, -1);
            }
            addView(contentView, lp);
        }
    }

    public void setContentView(int layoutId) {
        setContentView(LayoutInflater.from(getContext()).inflate(layoutId, (ViewGroup) null), null);
    }
}
