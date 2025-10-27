package com.easefun.polyv.livecloudclass.modules.pagemenu.commodity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow;
import com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity;
import com.plv.foundationsdk.component.di.PLVDependManager;

/* loaded from: classes3.dex */
public class PLVLCCommodityDetailActivity extends PLVSimpleWebViewActivity {
    private static final String EXTRA_COMMODITY_DETAIL_URL = "commodityDetailUrl";
    private String commodityDetailUrl;
    private PLVLCFloatingWindow floatingWindow;

    private void initData() {
        this.commodityDetailUrl = getIntent().getStringExtra(EXTRA_COMMODITY_DETAIL_URL);
    }

    private void initFloatingWindow() {
        this.floatingWindow = (PLVLCFloatingWindow) PLVDependManager.getInstance().get(PLVLCFloatingWindow.class);
    }

    public static void start(Context context, String str) {
        Intent intent = new Intent(context, (Class<?>) PLVLCCommodityDetailActivity.class);
        intent.putExtra(EXTRA_COMMODITY_DETAIL_URL, str);
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

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity, com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        initData();
        initFloatingWindow();
        super.onCreate(bundle);
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity, com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.floatingWindow.showByCommodityPage(false);
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity, com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.floatingWindow.showByCommodityPage(true);
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVSimpleWebViewActivity
    public String urlOrHtmlText() {
        return this.commodityDetailUrl;
    }
}
