package com.psychiatrygarden.ranking;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class RankFragment extends BaseFragment {
    private LinearLayout mLyAddView;

    public static RankFragment newInstance() {
        Bundle bundle = new Bundle();
        RankFragment rankFragment = new RankFragment();
        rankFragment.setArguments(bundle);
        return rankFragment;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mLyAddView = (LinearLayout) holder.get(R.id.ly_add_view);
        new ArrayList();
    }
}
