package com.aliyun.svideo.common.baseAdapter;

import com.aliyun.svideo.common.R;

/* loaded from: classes2.dex */
public final class SimpleLoadMoreView extends LoadMoreView {
    @Override // com.aliyun.svideo.common.baseAdapter.LoadMoreView
    public int getLayoutId() {
        return R.layout.alivc_quick_view_load_more;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.LoadMoreView
    public int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.LoadMoreView
    public int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.LoadMoreView
    public int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }
}
