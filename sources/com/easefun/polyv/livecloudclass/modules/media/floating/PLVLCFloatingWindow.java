package com.easefun.polyv.livecloudclass.modules.media.floating;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.widget.floating.enums.PLVFloatingEnums;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.PLVFloatPermissionUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.feature.login.IPLVSceneLoginManager;
import com.plv.livescenes.feature.login.PLVLiveLoginResult;
import com.plv.livescenes.feature.login.PLVSceneLoginManager;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVLCFloatingWindow {
    private static final String TAG = "PLVLCFloatingWindow";

    @Nullable
    private PLVSwitchViewAnchorLayout contentAnchorLayout;

    @Nullable
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private boolean requestShowByCommodityPage = false;
    private boolean requestShowByUser = false;
    private PLVFloatingEnums.ShowType showType = PLVFloatingEnums.ShowType.SHOW_ONLY_FOREGROUND;

    /* renamed from: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (PLVLCFloatingWindow.this.isNeedShow()) {
                PLVFloatingPlayerManager.getInstance().setFloatingSize(ConvertUtils.dp2px(200.0f), ConvertUtils.dp2px(112.5f)).setFloatingPosition(ScreenUtils.getScreenOrientatedWidth() - ConvertUtils.dp2px(216.0f), ScreenUtils.getScreenOrientatedHeight() - ConvertUtils.dp2px(146.5f)).updateShowType(PLVLCFloatingWindow.this.showType).setOnGoBackListener(new PLVFloatingPlayerManager.OnGoBackListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow.1.2
                    @Override // com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager.OnGoBackListener
                    public void onGoBack(@Nullable final Intent intent) {
                        if (intent == null) {
                            return;
                        }
                        PLVLCFloatingWindow.this.requestShowByCommodityPage = false;
                        PLVLCFloatingWindow.this.requestShowByUser = false;
                        PLVFloatingPlayerManager.getInstance().clear();
                        intent.addFlags(335544320);
                        if (((Activity) PLVLCFloatingWindow.this.contentAnchorLayout.getContext()).isFinishing()) {
                            PLVLCFloatingWindow.this.reLoginWatchThenRun(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow.1.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    PLVLCFloatingWindow.this.contentAnchorLayout.getContext().startActivity(intent);
                                }
                            });
                        } else {
                            PLVLCFloatingWindow.this.contentAnchorLayout.getContext().startActivity(intent);
                        }
                    }
                }).setOnCloseFloatingWindowListener(new PLVFloatingPlayerManager.OnCloseFloatingWindowListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow.1.1
                    @Override // com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerManager.OnCloseFloatingWindowListener
                    public void onClosedFloatingWindow(@Nullable String str) {
                        PLVLCFloatingWindow.this.requestShowByCommodityPage = false;
                        PLVLCFloatingWindow.this.requestShowByUser = false;
                        PLVFloatingPlayerManager.getInstance().clear();
                    }
                }).bindContentLayout(PLVLCFloatingWindow.this.contentAnchorLayout).show();
            }
        }
    }

    private void checkFloatingWindowPermissionThenRun(final Context context, final Runnable runnable) {
        if (PLVFloatPermissionUtils.checkPermission((Activity) context)) {
            runnable.run();
            return;
        }
        Context topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) {
            topActivity = context;
        }
        new AlertDialog.Builder(topActivity).setMessage("悬浮小窗播放功能需要在应用设置中开启悬浮窗权限，是否前往开启权限？").setPositiveButton("是", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                PLVFloatPermissionUtils.requestPermission((Activity) context, new PLVFloatPermissionUtils.IPLVOverlayPermissionListener() { // from class: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow.3.1
                    @Override // com.easefun.polyv.livecommon.ui.widget.floating.permission.PLVFloatPermissionUtils.IPLVOverlayPermissionListener
                    public void onResult(boolean z2) {
                        if (z2) {
                            runnable.run();
                        }
                    }
                });
            }
        }).setNegativeButton("否", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNeedShow() {
        return this.requestShowByUser || this.requestShowByCommodityPage;
    }

    private void onRequestShowChanged() {
        if (this.requestShowByUser) {
            this.showType = PLVFloatingEnums.ShowType.SHOW_ALWAYS;
        } else if (this.requestShowByCommodityPage) {
            this.showType = PLVFloatingEnums.ShowType.SHOW_ONLY_FOREGROUND;
        }
        PLVFloatingPlayerManager.getInstance().updateShowType(this.showType);
        boolean zIsNeedShow = isNeedShow();
        boolean zIsFloatingWindowShowing = PLVFloatingPlayerManager.getInstance().isFloatingWindowShowing();
        if (zIsNeedShow && !zIsFloatingWindowShowing) {
            playOnFloatingWindow();
        } else {
            if (zIsNeedShow || !zIsFloatingWindowShowing) {
                return;
            }
            PLVFloatingPlayerManager.getInstance().hide();
        }
    }

    private void playOnFloatingWindow() {
        if (this.contentAnchorLayout == null) {
            return;
        }
        checkFloatingWindowPermissionThenRun(this.contentAnchorLayout.getContext(), new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reLoginWatchThenRun(final Runnable runnable) {
        if (this.liveRoomDataManager == null) {
            return;
        }
        new PLVSceneLoginManager().loginLiveNew(this.liveRoomDataManager.getConfig().getAccount().getAppId(), this.liveRoomDataManager.getConfig().getAccount().getAppSecret(), this.liveRoomDataManager.getConfig().getAccount().getUserId(), this.liveRoomDataManager.getConfig().getChannelId(), new IPLVSceneLoginManager.OnLoginListener<PLVLiveLoginResult>() { // from class: com.easefun.polyv.livecloudclass.modules.media.floating.PLVLCFloatingWindow.2
            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginFailed(String str, Throwable th) {
                PLVCommonLog.w(PLVLCFloatingWindow.TAG, "onLoginFailed: " + str + " " + th);
            }

            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginSuccess(PLVLiveLoginResult pLVLiveLoginResult) {
                runnable.run();
            }
        });
    }

    public void bindContentView(PLVSwitchViewAnchorLayout pLVSwitchViewAnchorLayout) {
        this.contentAnchorLayout = pLVSwitchViewAnchorLayout;
    }

    public boolean isRequestingShowByUser() {
        return this.requestShowByUser;
    }

    public void setLiveRoomData(IPLVLiveRoomDataManager iPLVLiveRoomDataManager) {
        this.liveRoomDataManager = iPLVLiveRoomDataManager;
    }

    public void showByCommodityPage(boolean z2) {
        this.requestShowByCommodityPage = z2;
        onRequestShowChanged();
    }

    public void showByUser(boolean z2) {
        this.requestShowByUser = z2;
        onRequestShowChanged();
    }
}
