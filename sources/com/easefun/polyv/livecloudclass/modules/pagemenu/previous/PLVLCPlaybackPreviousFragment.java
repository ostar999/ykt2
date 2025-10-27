package com.easefun.polyv.livecloudclass.modules.pagemenu.previous;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter.PLVLCPreviousAdapter;
import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.easefun.polyv.livecommon.module.modules.previous.customview.PLVPreviousAdapter;
import com.easefun.polyv.livecommon.module.modules.previous.customview.PLVPreviousView;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVLCPlaybackPreviousFragment extends PLVBaseFragment {
    private PLVPreviousView plvPreviousView;
    private IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter previousPresenter;

    private void initView() {
        this.plvPreviousView = (PLVPreviousView) findViewById(R.id.plvlc_previous_view);
        PLVPreviousView.Builder builder = new PLVPreviousView.Builder(getContext());
        PLVLCPreviousAdapter pLVLCPreviousAdapter = new PLVLCPreviousAdapter();
        pLVLCPreviousAdapter.setOnViewActionListener(new PLVPreviousAdapter.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.previous.PLVLCPlaybackPreviousFragment.1
            @Override // com.easefun.polyv.livecommon.module.modules.previous.customview.PLVPreviousAdapter.OnViewActionListener
            public void changeVideoVidClick(String str) {
                PLVLCPlaybackPreviousFragment.this.plvPreviousView.changePlaybackVideoVid(str);
            }
        });
        builder.setAdapter(pLVLCPreviousAdapter).setRecyclerViewItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.previous.PLVLCPlaybackPreviousFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                rect.left = ConvertUtils.dp2px(16.0f);
                rect.top = ConvertUtils.dp2px(16.0f);
            }
        }).setRecyclerViewLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        this.plvPreviousView.setParams(builder);
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.registerView(this.plvPreviousView.getPreviousView());
        }
    }

    public void init(IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter) {
        this.previousPresenter = iPreviousPlaybackPresenter;
    }

    public void initData() {
        PLVPreviousView pLVPreviousView = this.plvPreviousView;
        if (pLVPreviousView != null) {
            pLVPreviousView.requestPreviousList();
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.plvlc_previous_fragment, viewGroup, false);
        initView();
        initData();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PLVPreviousView pLVPreviousView = this.plvPreviousView;
        if (pLVPreviousView != null) {
            pLVPreviousView.onDestroy();
        }
    }
}
