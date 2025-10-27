package com.psychiatrygarden.ranking;

import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.psychiatrygarden.bean.RankBeanData;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ActiveRankAdp extends BaseQuickAdapter<RankBeanData, BaseViewHolder> {
    private String mType;

    public ActiveRankAdp(String type) {
        super(R.layout.layout_rank_item_user_view);
        this.mType = type;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00a6  */
    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convert(com.aliyun.svideo.common.baseAdapter.BaseViewHolder r19, com.psychiatrygarden.bean.RankBeanData r20) {
        /*
            Method dump skipped, instructions count: 766
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.ranking.ActiveRankAdp.convert(com.aliyun.svideo.common.baseAdapter.BaseViewHolder, com.psychiatrygarden.bean.RankBeanData):void");
    }
}
