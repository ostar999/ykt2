package com.easefun.polyv.livecommon.module.modules.reward.view.dialog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.easefun.polyv.livecommon.module.modules.reward.view.adapter.PLVRewardListAdapter;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVRewardPageAdapter extends FragmentStatePagerAdapter {
    private List<PLVBaseViewData> dataList;
    private boolean enablePage;
    private int fragmentCount;
    private List<PLVPointRewardFragment> fragmentList;
    private int pageSize;
    private int selectPosition;

    public PLVRewardPageAdapter(FragmentManager fm, List<PLVBaseViewData> list, boolean enablePage, int pageSize) {
        super(fm);
        this.selectPosition = -1;
        this.fragmentList = new ArrayList();
        this.enablePage = enablePage;
        this.dataList = list;
        this.pageSize = pageSize;
        if (list.size() % pageSize == 0) {
            this.fragmentCount = list.size() / pageSize;
        } else {
            this.fragmentCount = (list.size() / pageSize) + 1;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.fragmentCount;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(final int position) {
        PLVPointRewardFragment pLVPointRewardFragment = new PLVPointRewardFragment();
        if (this.enablePage) {
            int size = this.dataList.size();
            int i2 = this.pageSize;
            if (size <= i2) {
                pLVPointRewardFragment.init(this.dataList);
            } else {
                pLVPointRewardFragment.init(this.dataList.subList(position * i2, Math.min((position + 1) * i2, this.dataList.size())));
            }
        } else {
            pLVPointRewardFragment.init(this.dataList);
        }
        pLVPointRewardFragment.setOnCheckItemListener(new PLVRewardListAdapter.OnCheckItemListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.dialog.PLVRewardPageAdapter.1
            @Override // com.easefun.polyv.livecommon.module.modules.reward.view.adapter.PLVRewardListAdapter.OnCheckItemListener
            public void onItemCheck(PLVBaseViewData selectData, int itemPosition) {
                PLVRewardPageAdapter pLVRewardPageAdapter = PLVRewardPageAdapter.this;
                pLVRewardPageAdapter.selectPosition = (position * pLVRewardPageAdapter.pageSize) + itemPosition;
                for (int i3 = 0; i3 < PLVRewardPageAdapter.this.fragmentCount; i3++) {
                    if (position != i3) {
                        ((PLVPointRewardFragment) PLVRewardPageAdapter.this.fragmentList.get(i3)).clearSelectState();
                    }
                }
            }
        });
        if (!this.fragmentList.contains(pLVPointRewardFragment)) {
            this.fragmentList.add(pLVPointRewardFragment);
        }
        return pLVPointRewardFragment;
    }

    public int getPageCount() {
        return this.fragmentCount;
    }

    public PLVBaseViewData getSelectData() {
        int i2 = this.selectPosition;
        if (i2 != -1) {
            return this.dataList.get(i2);
        }
        return null;
    }
}
