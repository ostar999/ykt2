package com.easefun.polyv.livecommon.module.modules.reward.view.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.reward.view.adapter.PLVRewardListAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPointRewardFragment extends PLVBaseFragment {
    private static int spanCount = 5;
    private PLVRewardListAdapter adapter;
    private List<PLVBaseViewData> dataList;
    private PLVRewardListAdapter.OnCheckItemListener onCheckItemListener;
    private RecyclerView rvPointRewardList;

    public void clearSelectState() {
        PLVRewardListAdapter pLVRewardListAdapter = this.adapter;
        if (pLVRewardListAdapter != null) {
            pLVRewardListAdapter.clearSelectState();
        }
    }

    public void init(List<PLVBaseViewData> dataList) {
        this.dataList = dataList;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.rvPointRewardList.setLayoutManager(new GridLayoutManager(getContext(), spanCount) { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVPointRewardFragment.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        this.rvPointRewardList.setHasFixedSize(true);
        PLVRewardListAdapter pLVRewardListAdapter = new PLVRewardListAdapter();
        this.adapter = pLVRewardListAdapter;
        pLVRewardListAdapter.setOnCheckItemListener(this.onCheckItemListener);
        this.adapter.setDataList(new ArrayList(this.dataList));
        this.rvPointRewardList.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.plv_point_reward_fragment, container, false);
        this.rvPointRewardList = (RecyclerView) findViewById(R.id.rv_point_reward_list);
        return this.view;
    }

    public void setOnCheckItemListener(PLVRewardListAdapter.OnCheckItemListener onCheckItemListener) {
        this.onCheckItemListener = onCheckItemListener;
    }
}
