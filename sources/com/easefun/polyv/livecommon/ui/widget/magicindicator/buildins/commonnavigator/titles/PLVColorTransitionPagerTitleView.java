package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVArgbEvaluatorHolder;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVColorTransitionPagerTitleView extends PLVSimplePagerTitleView {
    private static final String TAG = "PLVColorTransitionPager";

    public PLVColorTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.PLVSimplePagerTitleView, com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onDeselected(int index, int totalCount) {
        PLVCommonLog.d(TAG, "onDeselected index:" + index + " totalCount:" + totalCount);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.PLVSimplePagerTitleView, com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        setTextColor(PLVArgbEvaluatorHolder.eval(enterPercent, this.mNormalColor, this.mSelectedColor));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.PLVSimplePagerTitleView, com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        setTextColor(PLVArgbEvaluatorHolder.eval(leavePercent, this.mSelectedColor, this.mNormalColor));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.PLVSimplePagerTitleView, com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onSelected(int index, int totalCount) {
        PLVCommonLog.d(TAG, "onSelected index:" + index + " totalCount:" + totalCount);
    }
}
