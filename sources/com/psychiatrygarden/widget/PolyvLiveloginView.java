package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.utils.result.PLVLaunchResult;
import com.google.gson.Gson;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.livescenes.config.PLVLiveChannelType;
import com.plv.livescenes.feature.login.IPLVSceneLoginManager;
import com.plv.livescenes.feature.login.PLVLiveLoginResult;
import com.plv.livescenes.feature.login.PLVSceneLoginManager;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.bean.VideoHandout;
import com.psychiatrygarden.config.UserConfig;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class PolyvLiveloginView {
    String appId;
    String appSecret;
    String channelId;
    private IPLVSceneLoginManager loginManager;
    private CustomerLivingLoading loginProgressDialog;
    private Context mContext;
    String userId;
    private List<VideoHandout> videoHandouts;

    public PolyvLiveloginView() {
    }

    private void init() {
        this.loginManager = new PLVSceneLoginManager();
        initDialogCustomer();
        loginLive();
    }

    private void initDialog() {
    }

    private void initDialogCustomer() {
        CustomerLivingLoading customerLivingLoading = new CustomerLivingLoading(this.mContext, "加载中...");
        this.loginProgressDialog = customerLivingLoading;
        customerLivingLoading.setCanceledOnTouchOutside(false);
        this.loginProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.psychiatrygarden.widget.PolyvLiveloginView.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                PolyvLiveloginView.this.loginManager.destroy();
            }
        });
        this.loginProgressDialog.show();
    }

    private void loginLive() {
        this.loginManager.loginLiveNew(this.appId, this.appSecret, this.userId, this.channelId, new IPLVSceneLoginManager.OnLoginListener<PLVLiveLoginResult>() { // from class: com.psychiatrygarden.widget.PolyvLiveloginView.1
            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginFailed(String msg, Throwable throwable) {
                if (PolyvLiveloginView.this.mContext == null) {
                    return;
                }
                PolyvLiveloginView.this.loginProgressDialog.dismiss();
                ToastUtils.showShort(msg);
                PLVCommonLog.e("云课堂直播error", "loginLive onLoginFailed:" + throwable.getMessage());
            }

            @Override // com.plv.livescenes.feature.login.IPLVSceneLoginManager.OnLoginListener
            public void onLoginSuccess(PLVLiveLoginResult plvLiveLoginResult) {
                if (PolyvLiveloginView.this.mContext == null) {
                    return;
                }
                PolyvLiveloginView.this.loginProgressDialog.dismiss();
                PolyvLiveloginView polyvLiveloginView = PolyvLiveloginView.this;
                PLVLiveChannelConfigFiller.setupAccount(polyvLiveloginView.userId, polyvLiveloginView.appId, polyvLiveloginView.appSecret);
                PLVLiveChannelType channelTypeNew = plvLiveLoginResult.getChannelTypeNew();
                if (!PLVLiveScene.isCloudClassSceneSupportType(channelTypeNew)) {
                    ToastUtils.showShort("云课堂场景仅支持三分屏和纯视频频道类型");
                    return;
                }
                PLVLaunchResult pLVLaunchResultLaunchLive = PLVLCCloudClassActivity.launchLive((Activity) PolyvLiveloginView.this.mContext, PolyvLiveloginView.this.channelId, channelTypeNew, UserConfig.getUserId(), UserConfig.getInstance().getUser().getNickname(), UserConfig.getInstance().getUser().getAvatar(), new Gson().toJson(PolyvLiveloginView.this.videoHandouts));
                if (pLVLaunchResultLaunchLive.isSuccess()) {
                    LocalBroadcastManager.getInstance(PolyvLiveloginView.this.mContext).sendBroadcast(new Intent().setAction("JOIN_LIVE"));
                } else {
                    ToastUtils.showShort(pLVLaunchResultLaunchLive.getErrorMessage());
                }
            }
        });
    }

    public void onDestroy() {
        IPLVSceneLoginManager iPLVSceneLoginManager = this.loginManager;
        if (iPLVSceneLoginManager != null) {
            iPLVSceneLoginManager.destroy();
        }
        CustomerLivingLoading customerLivingLoading = this.loginProgressDialog;
        if (customerLivingLoading != null) {
            customerLivingLoading.dismiss();
        }
    }

    public PolyvLiveloginView(Context mContext, String appId, String appSecret, String userId, String channelId) {
        this.mContext = mContext;
        this.appId = appId;
        this.appSecret = appSecret;
        this.userId = userId;
        this.channelId = channelId;
        init();
    }

    public PolyvLiveloginView(Context mContext, String appId, String appSecret, String userId, String channelId, List<VideoHandout> videoHandouts) {
        this.mContext = mContext;
        this.appId = appId;
        this.appSecret = appSecret;
        this.userId = userId;
        this.channelId = channelId;
        if (videoHandouts == null) {
            this.videoHandouts = new ArrayList();
        } else {
            List<VideoHandout> list = this.videoHandouts;
            if (list != null) {
                list.clear();
            } else {
                this.videoHandouts = new ArrayList();
            }
            this.videoHandouts.addAll(videoHandouts);
        }
        init();
    }
}
