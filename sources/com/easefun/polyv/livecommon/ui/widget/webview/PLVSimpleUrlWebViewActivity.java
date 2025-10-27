package com.easefun.polyv.livecommon.ui.widget.webview;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity;

/* loaded from: classes3.dex */
public class PLVSimpleUrlWebViewActivity extends PLVSimpleWebViewActivity {
    public static final String EXTRA_URL = "extra_url";

    public static void start(Context context, @NonNull String url) {
        Intent intent = new Intent(context, (Class<?>) PLVSimpleUrlWebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity
    public boolean isLoadUrl() {
        return true;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity
    public boolean isUseActionView() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity
    public String urlOrHtmlText() {
        return getIntent().getExtras().getString(EXTRA_URL);
    }
}
