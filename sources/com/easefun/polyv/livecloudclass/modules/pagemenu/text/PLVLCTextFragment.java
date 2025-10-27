package com.easefun.polyv.livecloudclass.modules.pagemenu.text;

import android.graphics.Color;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewContentUtils;
import com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment;

/* loaded from: classes3.dex */
public class PLVLCTextFragment extends PLVSimpleWebViewFragment {
    private String htmlText;

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment
    public int getBackgroundColor() {
        return Color.parseColor("#202127");
    }

    public void init(String str) {
        this.htmlText = PLVWebViewContentUtils.toWebViewContent(str, "#ADADC0");
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment
    public boolean isLoadUrl() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewFragment
    public String urlOrHtmlText() {
        return this.htmlText;
    }
}
