package com.easefun.polyv.livecloudclass.modules.pagemenu.previous;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.pagemenu.previous.adapter.PLVLCChapterAdapter;
import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.easefun.polyv.livecommon.module.modules.previous.customview.PLVChapterAdapter;
import com.easefun.polyv.livecommon.module.modules.previous.customview.PLVChapterView;
import com.easefun.polyv.livecommon.ui.window.PLVBaseFragment;

/* loaded from: classes3.dex */
public class PLVLCPlaybackChapterFragment extends PLVBaseFragment {
    private PLVChapterView plvChapterView;
    private IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter previousPresenter;

    private void initData() {
        PLVChapterView pLVChapterView = this.plvChapterView;
        if (pLVChapterView != null) {
            pLVChapterView.requestChapterList();
        }
    }

    private void initView() {
        this.plvChapterView = (PLVChapterView) findViewById(R.id.plv_chapter_view);
        PLVChapterView.Builder builder = new PLVChapterView.Builder(getContext());
        PLVLCChapterAdapter pLVLCChapterAdapter = new PLVLCChapterAdapter();
        pLVLCChapterAdapter.setOnViewActionListener(new PLVChapterAdapter.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.pagemenu.previous.PLVLCPlaybackChapterFragment.1
            @Override // com.easefun.polyv.livecommon.module.modules.previous.customview.PLVChapterAdapter.OnViewActionListener
            public void changeVideoSeekClick(int i2) {
                if (PLVLCPlaybackChapterFragment.this.plvChapterView != null) {
                    PLVLCPlaybackChapterFragment.this.plvChapterView.changePlaybackVideoSeek(i2);
                }
            }
        });
        builder.setAdapter(pLVLCChapterAdapter).setRecyclerViewLayoutManager(new LinearLayoutManager(getContext()));
        this.plvChapterView.setParams(builder);
        IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter = this.previousPresenter;
        if (iPreviousPlaybackPresenter != null) {
            iPreviousPlaybackPresenter.registerView(this.plvChapterView.getPreviousView());
        }
    }

    public void init(IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter iPreviousPlaybackPresenter) {
        this.previousPresenter = iPreviousPlaybackPresenter;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.plvlc_chapter_fragment, viewGroup, false);
        initView();
        initData();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PLVChapterView pLVChapterView = this.plvChapterView;
        if (pLVChapterView != null) {
            pLVChapterView.onDestroy();
        }
    }
}
