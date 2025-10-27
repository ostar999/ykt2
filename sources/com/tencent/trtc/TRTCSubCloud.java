package com.tencent.trtc;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.basic.enums.b;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.f;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.liteav.trtc.impl.TRTCCloudImpl;
import com.tencent.liteav.trtc.impl.TRTCRoomInfo;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class TRTCSubCloud extends TRTCCloudImpl {
    private static final String TAG = "com.tencent.trtc.TRTCSubCloud";
    protected WeakReference<TRTCCloudImpl> mMainCloud;
    private a mVolumeLevelCalTask;

    /* renamed from: com.tencent.trtc.TRTCSubCloud$6, reason: invalid class name */
    public class AnonymousClass6 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f22311a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f22312b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ TRTCCloudListener.TRTCSnapshotListener f22313c;

        public AnonymousClass6(String str, int i2, TRTCCloudListener.TRTCSnapshotListener tRTCSnapshotListener) {
            this.f22311a = str;
            this.f22312b = i2;
            this.f22313c = tRTCSnapshotListener;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x006d  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r4 = this;
                java.lang.String r0 = r4.f22311a
                if (r0 == 0) goto L83
                com.tencent.trtc.TRTCSubCloud r0 = com.tencent.trtc.TRTCSubCloud.this
                com.tencent.liteav.trtc.impl.TRTCRoomInfo r0 = com.tencent.trtc.TRTCSubCloud.access$5700(r0)
                java.lang.String r1 = r4.f22311a
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$UserInfo r0 = r0.getUser(r1)
                int r1 = r4.f22312b
                r2 = 2
                if (r1 != r2) goto L41
                if (r0 == 0) goto L6d
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r1 = r0.mainRender
                if (r1 == 0) goto L6d
                com.tencent.liteav.TXCRenderAndDec r1 = r1.render
                if (r1 == 0) goto L6d
                com.tencent.trtc.TRTCSubCloud r1 = com.tencent.trtc.TRTCSubCloud.this
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "snapshotRemoteSubStreamView->userId: "
                r2.append(r3)
                java.lang.String r3 = r4.f22311a
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.tencent.trtc.TRTCSubCloud.access$5800(r1, r2)
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r0 = r0.subRender
                com.tencent.liteav.TXCRenderAndDec r0 = r0.render
                com.tencent.liteav.renderer.e r0 = r0.getVideoRender()
                goto L6e
            L41:
                if (r0 == 0) goto L6d
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r1 = r0.mainRender
                if (r1 == 0) goto L6d
                com.tencent.liteav.TXCRenderAndDec r1 = r1.render
                if (r1 == 0) goto L6d
                com.tencent.trtc.TRTCSubCloud r1 = com.tencent.trtc.TRTCSubCloud.this
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "snapshotRemoteView->userId: "
                r2.append(r3)
                java.lang.String r3 = r4.f22311a
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                com.tencent.trtc.TRTCSubCloud.access$5900(r1, r2)
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r0 = r0.mainRender
                com.tencent.liteav.TXCRenderAndDec r0 = r0.render
                com.tencent.liteav.renderer.e r0 = r0.getVideoRender()
                goto L6e
            L6d:
                r0 = 0
            L6e:
                if (r0 == 0) goto L79
                com.tencent.trtc.TRTCSubCloud$6$1 r1 = new com.tencent.trtc.TRTCSubCloud$6$1
                r1.<init>()
                r0.a(r1)
                goto L83
            L79:
                com.tencent.trtc.TRTCSubCloud r0 = com.tencent.trtc.TRTCSubCloud.this
                com.tencent.trtc.TRTCSubCloud$6$2 r1 = new com.tencent.trtc.TRTCSubCloud$6$2
                r1.<init>()
                com.tencent.trtc.TRTCSubCloud.access$6100(r0, r1)
            L83:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.trtc.TRTCSubCloud.AnonymousClass6.run():void");
        }
    }

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private WeakReference<TRTCSubCloud> f22326a;

        public a(TRTCSubCloud tRTCSubCloud) {
            this.f22326a = new WeakReference<>(tRTCSubCloud);
        }

        @Override // java.lang.Runnable
        public void run() {
            WeakReference<TRTCSubCloud> weakReference = this.f22326a;
            TRTCSubCloud tRTCSubCloud = weakReference != null ? weakReference.get() : null;
            if (tRTCSubCloud != null) {
                final ArrayList arrayList = new ArrayList();
                int softwareCaptureVolumeLevel = TXCAudioEngine.getInstance().getSoftwareCaptureVolumeLevel();
                if (softwareCaptureVolumeLevel > 0) {
                    TRTCCloudDef.TRTCVolumeInfo tRTCVolumeInfo = new TRTCCloudDef.TRTCVolumeInfo();
                    tRTCVolumeInfo.userId = ((TRTCCloudImpl) tRTCSubCloud).mRoomInfo.userId;
                    tRTCVolumeInfo.volume = softwareCaptureVolumeLevel;
                    arrayList.add(tRTCVolumeInfo);
                }
                ((TRTCCloudImpl) tRTCSubCloud).mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.trtc.TRTCSubCloud.a.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        int remotePlayoutVolumeLevel = TXCAudioEngine.getInstance().getRemotePlayoutVolumeLevel(String.valueOf(userInfo.tinyID));
                        if (remotePlayoutVolumeLevel > 0) {
                            TRTCCloudDef.TRTCVolumeInfo tRTCVolumeInfo2 = new TRTCCloudDef.TRTCVolumeInfo();
                            tRTCVolumeInfo2.userId = userInfo.userID;
                            tRTCVolumeInfo2.volume = remotePlayoutVolumeLevel;
                            arrayList.add(tRTCVolumeInfo2);
                        }
                    }
                });
                final TRTCCloudListener tRTCCloudListener = ((TRTCCloudImpl) tRTCSubCloud).mTRTCListener;
                tRTCSubCloud.runOnListenerThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TRTCCloudListener tRTCCloudListener2 = tRTCCloudListener;
                        if (tRTCCloudListener2 != null) {
                            tRTCCloudListener2.onUserVoiceVolume(arrayList, 0);
                        }
                    }
                });
                if (((TRTCCloudImpl) tRTCSubCloud).mAudioVolumeEvalInterval > 0) {
                    ((TRTCCloudImpl) tRTCSubCloud).mSDKHandler.postDelayed(tRTCSubCloud.mVolumeLevelCalTask, ((TRTCCloudImpl) tRTCSubCloud).mAudioVolumeEvalInterval);
                }
            }
        }
    }

    public TRTCSubCloud(Context context, WeakReference<TRTCCloudImpl> weakReference, f fVar) {
        super(context, fVar);
        this.mMainCloud = null;
        this.mVolumeLevelCalTask = null;
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        tRTCRoomInfo.muteLocalAudio = true;
        tRTCRoomInfo.muteLocalVideo = true;
        tRTCRoomInfo.muteLocalSubVideo = true;
        this.mMainCloud = weakReference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFramework(JSONObject jSONObject) throws JSONException {
        apiLog("setFramework params: " + jSONObject.toString());
        if (!jSONObject.has("framework")) {
            apiLog("setFramework[lack parameter]: framework");
            return;
        }
        if (!jSONObject.has("scene")) {
            apiLog("setFramework[lack parameter]: scene");
            return;
        }
        if (!jSONObject.has("sdkappid")) {
            apiLog("setFramework[lack parameter]: sdkappid");
            return;
        }
        int i2 = jSONObject.getInt("framework");
        int i3 = jSONObject.getInt("scene");
        int i4 = jSONObject.getInt("sdkappid");
        String strVersion = TXCBuild.Version();
        String strModel = TXCBuild.Model();
        TXCKeyPointReportProxy.a aVar = new TXCKeyPointReportProxy.a();
        aVar.f18476d = i3;
        aVar.f18477e = strModel;
        aVar.f18478f = strVersion;
        Context context = this.mContext;
        aVar.f18480h = context != null ? context.getPackageName() : "";
        aVar.f18474b = i4;
        aVar.f18479g = TXCCommonUtil.getSDKVersionStr();
        aVar.f18475c = i2;
        TXCKeyPointReportProxy.a(aVar);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void ConnectOtherRoom(String str) {
        super.ConnectOtherRoom(str);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void DisconnectOtherRoom() {
        super.DisconnectOtherRoom();
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void callExperimentalAPI(final String str) throws JSONException {
        final JSONObject jSONObject;
        JSONObject jSONObject2;
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("callExperimentalAPI  ");
            sb.append(str);
            sb.append(", roomid = ");
            TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
            long j2 = tRTCRoomInfo.roomId;
            sb.append(j2 != -1 ? Long.valueOf(j2) : tRTCRoomInfo.strRoomId);
            apiOnlineLog(sb.toString());
        }
        final String string = "";
        try {
            jSONObject2 = new JSONObject(str);
        } catch (Exception unused) {
            apiLog("callExperimentalAPI[failed]: " + str);
            jSONObject = null;
        }
        if (!jSONObject2.has("api")) {
            apiLog("callExperimentalAPI[lack api or illegal type]: " + str);
            return;
        }
        string = jSONObject2.getString("api");
        if (jSONObject2.has("params")) {
            jSONObject = jSONObject2.getJSONObject("params");
            if (string.equals("setEncodedDataProcessingListener")) {
                setEncodedDataProcessingListener(jSONObject);
            } else {
                runOnSDKThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.10
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (string.equals("setSEIPayloadType")) {
                                TRTCSubCloud.this.setSEIPayloadType(jSONObject);
                            } else if (!string.equals("setLocalAudioMuteMode") && !string.equals("setVideoEncodeParamEx") && !string.equals("setAudioSampleRate")) {
                                if (string.equals("muteRemoteAudioInSpeaker")) {
                                    TRTCSubCloud.this.muteRemoteAudioInSpeaker(jSONObject);
                                } else if (!string.equals("enableAudioAGC") && !string.equals("enableAudioAEC") && !string.equals("enableAudioANS")) {
                                    if (string.equals("setPerformanceMode")) {
                                        TRTCSubCloud.this.setPerformanceMode(jSONObject);
                                    } else if (!string.equals("setCustomRenderMode") && !string.equals("setMediaCodecConfig") && !string.equals("setKeepAVCaptureOption")) {
                                        if (string.equals("sendJsonCMD")) {
                                            TRTCSubCloud.this.sendJsonCmd(jSONObject, str);
                                        } else if (string.equals("updatePrivateMapKey")) {
                                            TRTCSubCloud.this.updatePrivateMapKey(jSONObject);
                                        } else if (string.equals("setRoomType")) {
                                            TRTCSubCloud.this.setRoomType(jSONObject);
                                        } else if (string.equals("checkDuplicateEnterRoom")) {
                                            TRTCSubCloud.this.checkDuplicateEnterRoom(jSONObject);
                                        } else if (string.equals("setFramework")) {
                                            TRTCSubCloud.this.setFramework(jSONObject);
                                        } else if (string.equals("setAudioCacheType")) {
                                            TRTCSubCloud.this.setAudioCacheType(jSONObject);
                                        } else if (string.equals("setQoSStrategy")) {
                                            TRTCSubCloud.this.setQoSStrategy(jSONObject);
                                        } else {
                                            TRTCSubCloud.this.apiLog("callExperimentalAPI[illegal api]: " + string);
                                        }
                                    }
                                }
                            }
                        } catch (Exception unused2) {
                            TRTCSubCloud.this.apiLog("callExperimentalAPI[failed]: " + str);
                        }
                    }
                });
            }
        }
    }

    public void collectCustomCaptureFps() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public TRTCCloud createSubCloud() {
        return null;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void destroy() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (((TRTCCloudImpl) TRTCSubCloud.this).mNativeLock) {
                    if (((TRTCCloudImpl) TRTCSubCloud.this).mNativeRtcContext != 0) {
                        TRTCSubCloud.this.apiLog("destroy context");
                        TRTCSubCloud tRTCSubCloud = TRTCSubCloud.this;
                        tRTCSubCloud.nativeDestroyContext(((TRTCCloudImpl) tRTCSubCloud).mNativeRtcContext);
                    }
                    ((TRTCCloudImpl) TRTCSubCloud.this).mNativeRtcContext = 0L;
                }
                ((TRTCCloudImpl) TRTCSubCloud.this).mTRTCListener = null;
                ((TRTCCloudImpl) TRTCSubCloud.this).mAudioFrameListener = null;
                ((TRTCCloudImpl) TRTCSubCloud.this).mCurrentPublishClouds.clear();
                ((TRTCCloudImpl) TRTCSubCloud.this).mSubClouds.clear();
                com.tencent.liteav.audio.a.a().a(TRTCSubCloud.this.hashCode());
            }
        });
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void enableAudioEarMonitoring(boolean z2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void enableAudioVolumeEvaluation(int i2) {
        super.enableAudioVolumeEvaluation(i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void enableCustomAudioCapture(boolean z2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void enableCustomVideoCapture(boolean z2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int enableEncSmallVideoStream(boolean z2, TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
        return -1;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void enableMixExternalAudioFrame(boolean z2, boolean z3) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean enableTorch(boolean z2) {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00ac  */
    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void enterRoom(com.tencent.trtc.TRTCCloudDef.TRTCParams r12, final int r13) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.trtc.TRTCSubCloud.enterRoom(com.tencent.trtc.TRTCCloudDef$TRTCParams, int):void");
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void exitRoom() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.4
            @Override // java.lang.Runnable
            public void run() {
                TRTCSubCloud.this.apiOnlineLog("exitRoom " + ((TRTCCloudImpl) TRTCSubCloud.this).mRoomInfo.getRoomId());
                TRTCSubCloud.this.exitRoomInternal(true, "call from api");
            }
        });
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void exitRoomInternal(boolean z2, String str) {
        apiLog("exitRoomInternal reqExit: " + z2 + ", reason: " + str + ", mRoomState: " + this.mRoomState);
        if (this.mRoomState == 0) {
            clearRemoteMuteStates();
            apiLog("exitRoom ignore when no in room");
            return;
        }
        this.mRoomState = 0;
        this.mRoomType = 0;
        stopCollectStatus();
        this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.trtc.TRTCSubCloud.5
            @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
            public void accept(String str2, TRTCRoomInfo.UserInfo userInfo) {
                TRTCSubCloud.this.stopRemoteRender(userInfo);
                com.tencent.liteav.audio.a.a().a(String.valueOf(userInfo.tinyID), TRTCSubCloud.this.hashCode());
                TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                if (tXCRenderAndDec != null) {
                    tXCRenderAndDec.setVideoFrameListener(null, b.UNKNOWN);
                }
                TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                if (tXCRenderAndDec2 != null) {
                    tXCRenderAndDec2.setVideoFrameListener(null, b.UNKNOWN);
                }
            }
        });
        if (z2) {
            nativeExitRoom(this.mNativeRtcContext);
        }
        this.mRoomInfo.clear();
        this.mRenderListenerMap.clear();
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void finalize() throws Throwable {
        this.mSDKHandler = null;
        super.finalize();
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int getAudioCaptureVolume() {
        return 0;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int getAudioPlayoutVolume() {
        return 0;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int getBGMDuration(String str) {
        return 0;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public TXBeautyManager getBeautyManager() {
        return null;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean isCameraAutoFocusFaceModeSupported() {
        return false;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean isCameraFocusPositionInPreviewSupported() {
        return false;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean isCameraTorchSupported() {
        return false;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean isCameraZoomSupported() {
        return false;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int mixExternalAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
        return -3;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteAllRemoteAudio(boolean z2) {
        super.muteAllRemoteAudio(z2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteAllRemoteVideoStreams(boolean z2) {
        super.muteAllRemoteVideoStreams(z2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteLocalAudio(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.9
            @Override // java.lang.Runnable
            public void run() {
                TRTCSubCloud.this.apiOnlineLog("muteLocalAudio " + z2 + ", roomId=" + ((TRTCCloudImpl) TRTCSubCloud.this).mRoomInfo.getRoomId());
                TRTCCloudImpl tRTCCloudImpl = TRTCSubCloud.this.mMainCloud.get();
                if (tRTCCloudImpl != null) {
                    tRTCCloudImpl.muteLocalAudio(z2, TRTCSubCloud.this);
                }
            }
        });
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteLocalVideo(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.7
            @Override // java.lang.Runnable
            public void run() {
                TRTCSubCloud.this.apiOnlineLog("muteLocalVideo mute:" + z2 + ", roomId=" + ((TRTCCloudImpl) TRTCSubCloud.this).mRoomInfo.getRoomId());
                TRTCCloudImpl tRTCCloudImpl = TRTCSubCloud.this.mMainCloud.get();
                if (tRTCCloudImpl != null) {
                    tRTCCloudImpl.muteLocalVideo(0, z2, TRTCSubCloud.this);
                    tRTCCloudImpl.muteLocalVideo(2, z2, TRTCSubCloud.this);
                }
            }
        });
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteRemoteAudio(String str, boolean z2) {
        super.muteRemoteAudio(str, z2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteRemoteVideoStream(String str, boolean z2) {
        super.muteRemoteVideoStream(str, z2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void onAudioQosChanged(int i2, int i3, int i4) {
        TRTCCloudImpl tRTCCloudImpl = this.mMainCloud.get();
        if (tRTCCloudImpl != null) {
            tRTCCloudImpl.onAudioQosChanged(this, i2, i3, i4);
        }
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void onIdrFpsChanged(int i2) {
        TRTCCloudImpl tRTCCloudImpl = this.mMainCloud.get();
        if (tRTCCloudImpl != null) {
            tRTCCloudImpl.onIdrFpsChanged(this, i2);
        }
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void onVideoConfigChanged(int i2, boolean z2) {
        TRTCCloudImpl tRTCCloudImpl = this.mMainCloud.get();
        if (tRTCCloudImpl != null) {
            tRTCCloudImpl.onVideoConfigChanged(this, i2, z2);
        }
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void onVideoQosChanged(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        TRTCCloudImpl tRTCCloudImpl = this.mMainCloud.get();
        if (tRTCCloudImpl != null) {
            tRTCCloudImpl.onVideoQosChanged(this, i2, i3, i4, i5, i6, i7, i8, i9);
        }
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void pauseAudioEffect(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void pauseBGM() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void playAudioEffect(TRTCCloudDef.TRTCAudioEffectParam tRTCAudioEffectParam) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void playBGM(String str, TRTCCloud.BGMNotify bGMNotify) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void resumeAudioEffect(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void resumeBGM() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void selectMotionTmpl(String str) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void sendCustomAudioData(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean sendCustomCmdMsg(int i2, byte[] bArr, boolean z2, boolean z3) {
        return super.sendCustomCmdMsg(i2, bArr, z2, z3);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void sendCustomVideoData(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean sendSEIMsg(byte[] bArr, int i2) {
        return super.sendSEIMsg(bArr, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setAllAudioEffectsVolume(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setAudioCaptureVolume(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setAudioEffectVolume(int i2, int i3) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setAudioFrameListener(final TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.2
            @Override // java.lang.Runnable
            public void run() {
                TRTCSubCloud.this.apiLog("SubCloud setAudioFrameListener " + tRTCAudioFrameListener);
                ((TRTCCloudImpl) TRTCSubCloud.this).mAudioFrameListener = tRTCAudioFrameListener;
                if (((TRTCCloudImpl) TRTCSubCloud.this).mAudioFrameListener == null) {
                    ((TRTCCloudImpl) TRTCSubCloud.this).mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.trtc.TRTCSubCloud.2.1
                        @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                        public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(userInfo.tinyID), null);
                        }
                    });
                } else {
                    ((TRTCCloudImpl) TRTCSubCloud.this).mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.trtc.TRTCSubCloud.2.2
                        @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                        public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(userInfo.tinyID), TRTCSubCloud.this);
                        }
                    });
                }
            }
        });
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setAudioPlayoutVolume(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setAudioRoute(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setBGMPlayoutVolume(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int setBGMPosition(int i2) {
        return 0;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setBGMPublishVolume(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setBGMVolume(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setBeautyStyle(int i2, int i3, int i4, int i5) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setChinLevel(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setDebugViewMargin(String str, TRTCCloud.TRTCViewMargin tRTCViewMargin) {
        super.setDebugViewMargin(str, tRTCViewMargin);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setDefaultStreamRecvMode(boolean z2, boolean z3) {
        super.setDefaultStreamRecvMode(z2, z3);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setEyeScaleLevel(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setFaceShortLevel(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setFaceSlimLevel(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setFaceVLevel(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setFilter(Bitmap bitmap) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setFilterConcentration(float f2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setFocusPosition(int i2, int i3) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setGSensorMode(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean setGreenScreenFile(String str) {
        return false;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setListener(TRTCCloudListener tRTCCloudListener) {
        super.setListener(tRTCCloudListener);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setListenerHandler(Handler handler) {
        super.setListenerHandler(handler);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int setLocalVideoRenderListener(int i2, int i3, TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener) {
        return -1;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setLocalViewFillMode(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setLocalViewMirror(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setLocalViewRotation(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setMicVolumeOnMixing(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setMixTranscodingConfig(TRTCCloudDef.TRTCTranscodingConfig tRTCTranscodingConfig) {
        super.setMixTranscodingConfig(tRTCTranscodingConfig);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setMotionMute(boolean z2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setNetworkQosParam(TRTCCloudDef.TRTCNetworkQosParam tRTCNetworkQosParam) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setNoseSlimLevel(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int setPriorRemoteVideoStreamType(int i2) {
        return super.setPriorRemoteVideoStreamType(i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setRemoteAudioVolume(String str, int i2) {
        super.setRemoteAudioVolume(str, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setRemoteSubStreamViewFillMode(String str, int i2) {
        super.setRemoteSubStreamViewFillMode(str, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setRemoteSubStreamViewRotation(String str, int i2) {
        super.setRemoteSubStreamViewRotation(str, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int setRemoteVideoRenderListener(String str, int i2, int i3, TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener) {
        return super.setRemoteVideoRenderListener(str, i2, i3, tRTCVideoRenderListener);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int setRemoteVideoStreamType(String str, int i2) {
        return super.setRemoteVideoStreamType(str, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setRemoteViewFillMode(String str, int i2) {
        super.setRemoteViewFillMode(str, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setRemoteViewRotation(String str, int i2) {
        super.setRemoteViewRotation(str, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setReverbType(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setSystemVolumeType(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setVideoEncoderMirror(boolean z2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setVideoEncoderParam(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setVideoEncoderRotation(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public boolean setVoiceChangerType(int i2) {
        return false;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setWatermark(Bitmap bitmap, int i2, float f2, float f3, float f4) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void setZoom(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void showDebugView(int i2) {
        super.showDebugView(i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void snapshotVideo(String str, int i2, TRTCCloudListener.TRTCSnapshotListener tRTCSnapshotListener) {
        apiLog(String.format("snapshotVideo user:%s streamType:%d", str, Integer.valueOf(i2)));
        runOnSDKThread(new AnonymousClass6(str, i2, tRTCSnapshotListener));
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public int startAudioRecording(TRTCCloudDef.TRTCAudioRecordingParams tRTCAudioRecordingParams) {
        return -1;
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startLocalAudio() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startLocalPreview(boolean z2, TXCloudVideoView tXCloudVideoView) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startPublishCDNStream(TRTCCloudDef.TRTCPublishCDNParam tRTCPublishCDNParam) {
        super.startPublishCDNStream(tRTCPublishCDNParam);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startPublishing(String str, int i2) {
        super.startPublishing(str, i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startRemoteSubStreamView(String str, TXCloudVideoView tXCloudVideoView) {
        super.startRemoteSubStreamView(str, tXCloudVideoView);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startRemoteView(String str, TXCloudVideoView tXCloudVideoView) {
        super.startRemoteView(str, tXCloudVideoView);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startScreenCapture(int i2, TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam, TRTCCloudDef.TRTCScreenShareParams tRTCScreenShareParams) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startScreenCapture(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam, TRTCCloudDef.TRTCScreenShareParams tRTCScreenShareParams) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void startSpeedTest(int i2, String str, String str2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl
    public void startVolumeLevelCal(boolean z2) {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(z2, this.mAudioVolumeEvalInterval);
        if (!z2) {
            this.mVolumeLevelCalTask = null;
            this.mAudioVolumeEvalInterval = 0;
        } else if (this.mVolumeLevelCalTask == null) {
            a aVar = new a(this);
            this.mVolumeLevelCalTask = aVar;
            this.mSDKHandler.postDelayed(aVar, this.mAudioVolumeEvalInterval);
        }
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopAllAudioEffects() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopAllRemoteView() {
        super.stopAllRemoteView();
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopAudioEffect(int i2) {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopAudioRecording() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopBGM() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopLocalAudio() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopLocalPreview() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopPublishCDNStream() {
        super.stopPublishCDNStream();
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopPublishing() {
        super.stopPublishing();
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopRemoteSubStreamView(String str) {
        super.stopRemoteSubStreamView(str);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopRemoteView(String str) {
        super.stopRemoteView(str);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void stopSpeedTest() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void switchCamera() {
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void switchRole(int i2) {
        super.switchRole(i2);
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteLocalVideo(final int i2, final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.trtc.TRTCSubCloud.8
            @Override // java.lang.Runnable
            public void run() {
                TRTCSubCloud.this.apiOnlineLog("muteLocalVideo mute:" + z2 + ", streamType: " + i2 + ", roomId=" + ((TRTCCloudImpl) TRTCSubCloud.this).mRoomInfo.getRoomId());
                TRTCCloudImpl tRTCCloudImpl = TRTCSubCloud.this.mMainCloud.get();
                if (tRTCCloudImpl != null) {
                    tRTCCloudImpl.muteLocalVideo(i2, z2, TRTCSubCloud.this);
                }
            }
        });
    }

    @Override // com.tencent.liteav.trtc.impl.TRTCCloudImpl, com.tencent.trtc.TRTCCloud
    public void muteRemoteVideoStream(String str, int i2, boolean z2) {
        super.muteRemoteVideoStream(str, i2, z2);
    }
}
