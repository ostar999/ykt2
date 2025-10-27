package com.plv.livescenes.linkmic;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.NonNull;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVLinkMicJoinSuccess;
import com.plv.linkmic.model.PLVLinkMicMedia;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;
import com.plv.livescenes.linkmic.listener.PLVLinkMicListener;
import com.plv.livescenes.linkmic.vo.PLVLinkMicEngineParam;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public interface IPLVLinkMicManager {

    public interface OnSendJoinSuccessMsgListener {
        void onSendJoinSuccessMsg(PLVLinkMicJoinSuccess pLVLinkMicJoinSuccess);
    }

    void addEventHandler(PLVLinkMicEventListener pLVLinkMicEventListener);

    SurfaceView createRendererView(Context context);

    TextureView createTextureRenderView(Context context);

    void destroy();

    void enableAutoRequestPermission(boolean z2);

    void enableLocalVideo(boolean z2);

    String getLinkMicUid();

    void getLinkStatus(String str, PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> iPLVLinkMicDataRepoListener);

    void initEngine(@NonNull PLVLinkMicEngineParam pLVLinkMicEngineParam, PLVLinkMicListener pLVLinkMicListener);

    void joinChannel();

    void leaveChannel();

    void muteAllRemoteAudio(boolean z2);

    void muteAllRemoteVideo(boolean z2);

    void muteLocalAudio(boolean z2);

    void muteLocalVideo(boolean z2);

    void muteRemoteAudio(String str, boolean z2);

    void muteRemoteVideo(String str, boolean z2);

    void releaseRenderView(View view);

    void removeEventHandler(PLVLinkMicEventListener pLVLinkMicEventListener);

    void resetRequestPermissionList(ArrayList<String> arrayList);

    void sendJoinLeaveMsg(String str);

    void sendJoinRequestMsg();

    @Deprecated
    PLVLinkMicJoinSuccess sendJoinSuccessMsg(String str);

    PLVLinkMicJoinSuccess sendJoinSuccessMsg(String str, OnSendJoinSuccessMsgListener onSendJoinSuccessMsgListener);

    boolean sendMuteEventMsg(PLVLinkMicMedia pLVLinkMicMedia);

    void setBitrate(int i2);

    void setLocalPreviewMirror(boolean z2);

    void setLocalPushMirror(boolean z2);

    void setPushPictureResolutionType(int i2);

    void setupLocalVideo(View view, int i2);

    void setupLocalVideo(View view, String str);

    void setupRemoteVideo(View view, String str);

    void setupRemoteVideo(View view, String str, int i2);

    void switchCamera();

    void switchRoleToAudience();

    void switchRoleToBroadcaster();

    void takeSnapshot(String str, PLVSugarUtil.Consumer<Bitmap> consumer);
}
