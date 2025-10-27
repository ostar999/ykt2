package com.easefun.polyv.livecommon.module.utils;

import android.view.View;
import android.view.ViewGroup;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class PLVViewSwitcher {
    private static final String SWITCH_VIEW = "switchView:";
    private static final String TAG = "PLVViewSwitcher";
    private boolean isViewSwitched = false;
    private WeakReference<PLVSwitchViewAnchorLayout> switchViewAwr;
    private WeakReference<PLVSwitchViewAnchorLayout> switchViewBwr;

    private void exchangeView(ViewGroup viewACurParent, View viewA, ViewGroup viewBCurParent, View viewB) {
        viewACurParent.removeView(viewA);
        viewBCurParent.removeView(viewB);
        viewACurParent.addView(viewB);
        viewBCurParent.addView(viewA);
    }

    public boolean isViewSwitched() {
        return this.isViewSwitched;
    }

    public void registerSwitchView(PLVSwitchViewAnchorLayout switchViewA, PLVSwitchViewAnchorLayout switchViewB) {
        this.switchViewAwr = new WeakReference<>(switchViewA);
        this.switchViewBwr = new WeakReference<>(switchViewB);
    }

    public void switchView() {
        PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout = this.switchViewAwr.get();
        PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout2 = this.switchViewBwr.get();
        if (pLVSwitchViewAnchorLayout == null || pLVSwitchViewAnchorLayout2 == null) {
            return;
        }
        if (this.isViewSwitched) {
            pLVSwitchViewAnchorLayout.notifySwitchBackBefore();
            pLVSwitchViewAnchorLayout2.notifySwitchBackBefore();
            try {
                View switchView = pLVSwitchViewAnchorLayout2.getSwitchView();
                View switchView2 = pLVSwitchViewAnchorLayout.getSwitchView();
                try {
                    exchangeView(pLVSwitchViewAnchorLayout2, switchView, pLVSwitchViewAnchorLayout, switchView2);
                    this.isViewSwitched = false;
                    pLVSwitchViewAnchorLayout.notifySwitchBackAfter();
                    pLVSwitchViewAnchorLayout2.notifySwitchBackAfter();
                    PLVCommonLog.d(TAG, switchView + " and " + switchView2 + " switch back to their origin parent");
                    return;
                } catch (Exception e2) {
                    PLVCommonLog.e(TAG, SWITCH_VIEW + e2.getMessage());
                    return;
                }
            } catch (IllegalAccessException e3) {
                PLVCommonLog.e(TAG, SWITCH_VIEW + e3.getMessage());
                return;
            }
        }
        pLVSwitchViewAnchorLayout.notifySwitchElsewhereBefore();
        pLVSwitchViewAnchorLayout2.notifySwitchElsewhereBefore();
        try {
            View switchView3 = pLVSwitchViewAnchorLayout.getSwitchView();
            View switchView4 = pLVSwitchViewAnchorLayout2.getSwitchView();
            try {
                exchangeView(pLVSwitchViewAnchorLayout, switchView3, pLVSwitchViewAnchorLayout2, switchView4);
                this.isViewSwitched = true;
                pLVSwitchViewAnchorLayout.notifySwitchElsewhereAfter();
                pLVSwitchViewAnchorLayout2.notifySwitchElsewhereAfter();
                PLVCommonLog.d(TAG, switchView3 + " and " + switchView4 + " switch to new parent of each");
            } catch (Exception e4) {
                PLVCommonLog.e(TAG, SWITCH_VIEW + e4.getMessage());
            }
        } catch (IllegalAccessException e5) {
            PLVCommonLog.e(TAG, SWITCH_VIEW + e5.getMessage());
        }
    }
}
