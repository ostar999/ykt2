package com.easefun.polyv.livecloudclass.modules.pagemenu.iframe;

import com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment;

/* loaded from: classes3.dex */
public class PLVLCIFrameFragment extends PLVSimpleWebViewFragment {
    private String url;

    public void init(String str) {
        this.url = str;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment
    public boolean isLoadUrl() {
        return true;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment
    public boolean isUseActionView() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment
    public String urlOrHtmlText() {
        return this.url;
    }
}
