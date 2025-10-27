package com.easefun.polyv.livecloudclass.modules.media.danmu;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVLCDanmuWrapper {
    View anchorView;
    IPLVLCDanmuController danmuController;
    View danmuSwitchLandView;
    boolean isDanmuToggleOpen = true;
    boolean isEnableDanmuInPortrait = false;
    boolean isServerDanmuOpen = false;
    PLVOrientationManager.OnConfigurationChangedListener onConfigurationChangedListener;

    public PLVLCDanmuWrapper(@NonNull final View view) {
        this.anchorView = view;
        this.onConfigurationChangedListener = new PLVOrientationManager.OnConfigurationChangedListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuWrapper.1
            @Override // com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager.OnConfigurationChangedListener
            public void onCall(Context context, boolean z2) {
                if (context == view.getContext()) {
                    PLVLCDanmuWrapper.this.postRefreshDanmuStatus();
                }
            }
        };
        PLVOrientationManager.getInstance().addOnConfigurationChangedListener(this.onConfigurationChangedListener);
    }

    public void dispatchDanmuSwitchOnClicked(View view) {
        toggleDanmu();
    }

    public void enableDanmuInPortrait() {
        this.isEnableDanmuInPortrait = true;
        refreshDanmuStatus();
    }

    public void init() {
        this.anchorView.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuWrapper.2
            @Override // java.lang.Runnable
            public void run() {
                PLVLCDanmuWrapper.this.setupToggleDanmuStatus();
                PLVLCDanmuWrapper.this.refreshDanmuStatus();
            }
        });
    }

    public void postRefreshDanmuStatus() {
        this.anchorView.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.danmu.PLVLCDanmuWrapper.3
            @Override // java.lang.Runnable
            public void run() {
                PLVLCDanmuWrapper.this.refreshDanmuStatus();
            }
        });
    }

    public void refreshDanmuStatus() {
        if (!this.isServerDanmuOpen) {
            IPLVLCDanmuController iPLVLCDanmuController = this.danmuController;
            if (iPLVLCDanmuController != null) {
                iPLVLCDanmuController.hide();
            }
            View view = this.danmuSwitchLandView;
            if (view != null) {
                view.setVisibility(8);
                return;
            }
            return;
        }
        View view2 = this.danmuSwitchLandView;
        if (view2 != null) {
            view2.setVisibility(0);
        }
        if (this.isEnableDanmuInPortrait) {
            if (this.isDanmuToggleOpen) {
                IPLVLCDanmuController iPLVLCDanmuController2 = this.danmuController;
                if (iPLVLCDanmuController2 != null) {
                    iPLVLCDanmuController2.show();
                    return;
                }
                return;
            }
            IPLVLCDanmuController iPLVLCDanmuController3 = this.danmuController;
            if (iPLVLCDanmuController3 != null) {
                iPLVLCDanmuController3.hide();
                return;
            }
            return;
        }
        if (ScreenUtils.isPortrait()) {
            IPLVLCDanmuController iPLVLCDanmuController4 = this.danmuController;
            if (iPLVLCDanmuController4 != null) {
                iPLVLCDanmuController4.hide();
                return;
            }
            return;
        }
        if (this.isDanmuToggleOpen) {
            IPLVLCDanmuController iPLVLCDanmuController5 = this.danmuController;
            if (iPLVLCDanmuController5 != null) {
                iPLVLCDanmuController5.show();
                return;
            }
            return;
        }
        IPLVLCDanmuController iPLVLCDanmuController6 = this.danmuController;
        if (iPLVLCDanmuController6 != null) {
            iPLVLCDanmuController6.hide();
        }
    }

    public void release() {
        PLVOrientationManager.getInstance().removeOnConfigurationChangedListener(this.onConfigurationChangedListener);
    }

    public void setDanmuController(@NonNull IPLVLCDanmuController iPLVLCDanmuController) {
        this.danmuController = iPLVLCDanmuController;
        init();
    }

    public void setDanmuSwitchLandView(@NonNull View view) {
        this.danmuSwitchLandView = view;
        init();
    }

    public void setOnServerDanmuOpen(boolean z2) {
        this.isServerDanmuOpen = z2;
        refreshDanmuStatus();
    }

    public void setupToggleDanmuStatus() {
        View view = this.danmuSwitchLandView;
        if (view != null) {
            view.setSelected(!this.isDanmuToggleOpen);
        }
        if (this.isDanmuToggleOpen && this.isServerDanmuOpen) {
            IPLVLCDanmuController iPLVLCDanmuController = this.danmuController;
            if (iPLVLCDanmuController != null) {
                iPLVLCDanmuController.show();
                return;
            }
            return;
        }
        IPLVLCDanmuController iPLVLCDanmuController2 = this.danmuController;
        if (iPLVLCDanmuController2 != null) {
            iPLVLCDanmuController2.hide();
        }
    }

    public void toggleDanmu() {
        this.isDanmuToggleOpen = !this.isDanmuToggleOpen;
        setupToggleDanmuStatus();
    }
}
