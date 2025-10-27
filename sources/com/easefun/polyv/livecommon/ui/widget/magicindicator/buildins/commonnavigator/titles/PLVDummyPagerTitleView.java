package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.view.View;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVDummyPagerTitleView extends View implements IPLVPagerTitleView {
    private static final String TAG = "PLVDummyPagerTitleView";

    public PLVDummyPagerTitleView(Context context) {
        super(context);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onDeselected(int index, int totalCount) {
        PLVCommonLog.d(TAG, "onDeselected index:" + index + " totalCount:" + totalCount);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onSelected(int index, int totalCount) {
        PLVCommonLog.d(TAG, "onSelected index:" + index + " totalCount:" + totalCount);
    }
}
