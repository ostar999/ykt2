package com.plv.linkmic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVRTCConfig;
import com.plv.linkmic.processor.a;
import com.plv.linkmic.processor.b;
import com.plv.linkmic.processor.c;
import com.plv.linkmic.repository.PLVLinkMicEngineToken;
import com.plv.linkmic.screenshare.IPLVScreenShareListener;
import com.plv.linkmic.screenshare.PLVScreenCaptureUtils;

/* loaded from: classes4.dex */
public class PLVLinkMicWrapper implements b {
    public static final int ERROR_RET_VAL = -1;
    private static final String TAG = "PLVLinkMicWrapper";
    private Runnable cachedJoinChannelRequest;
    private Context context;
    private boolean isScreenShaing;
    private volatile boolean isTokenExpired = true;
    private Handler linkHandler = new Handler(Looper.getMainLooper());
    private a linkMicEventDispatcher;
    private String mUid;
    private c micProcessor;
    private IPLVLinkMicWrapperCallback micWrapperCallback;
    private PLVLinkMicEventHandler screenEventHandler;
    private IPLVScreenShareListener screenShareListener;

    /* renamed from: com.plv.linkmic.PLVLinkMicWrapper$1, reason: invalid class name */
    public class AnonymousClass1 implements IPLVTokenRequester.OnRequestTokenListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ IPLVLinkMicWrapperCallback val$linkMicWrapperCallback;
        final /* synthetic */ PLVRTCConfig val$rtcConfig;
        final /* synthetic */ IPLVTokenRequester val$tokenRequester;

        public AnonymousClass1(PLVRTCConfig pLVRTCConfig, IPLVTokenRequester iPLVTokenRequester, Context context, IPLVLinkMicWrapperCallback iPLVLinkMicWrapperCallback) {
            this.val$rtcConfig = pLVRTCConfig;
            this.val$tokenRequester = iPLVTokenRequester;
            this.val$context = context;
            this.val$linkMicWrapperCallback = iPLVLinkMicWrapperCallback;
        }

        @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVTokenRequester.OnRequestTokenListener
        public void onFail(Throwable th) {
            PLVCommonLog.exception(th);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:4:0x0017  */
        @Override // com.plv.linkmic.PLVLinkMicWrapper.IPLVTokenRequester.OnRequestTokenListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onSuccess(com.plv.linkmic.repository.PLVLinkMicEngineToken r5) {
            /*
                r4 = this;
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                r1 = 0
                com.plv.linkmic.PLVLinkMicWrapper.access$002(r0, r1)
                com.plv.linkmic.model.PLVRTCConfig r0 = r4.val$rtcConfig
                java.lang.String r0 = r0.getRtcType()
                r0.hashCode()
                int r2 = r0.hashCode()
                r3 = -1
                switch(r2) {
                    case 3569005: goto L2f;
                    case 3598796: goto L24;
                    case 92760312: goto L19;
                    default: goto L17;
                }
            L17:
                r1 = r3
                goto L38
            L19:
                java.lang.String r1 = "agora"
                boolean r0 = r0.equals(r1)
                if (r0 != 0) goto L22
                goto L17
            L22:
                r1 = 2
                goto L38
            L24:
                java.lang.String r1 = "urtc"
                boolean r0 = r0.equals(r1)
                if (r0 != 0) goto L2d
                goto L17
            L2d:
                r1 = 1
                goto L38
            L2f:
                java.lang.String r2 = "trtc"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L38
                goto L17
            L38:
                switch(r1) {
                    case 0: goto L8e;
                    case 1: goto L55;
                    case 2: goto L3c;
                    default: goto L3b;
                }
            L3b:
                goto La8
            L3c:
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.a.a r1 = new com.plv.linkmic.processor.a.a
                java.lang.String r2 = com.plv.linkmic.PLVLinkMicWrapper.access$200(r0)
                r1.<init>(r2)
                com.plv.linkmic.PLVLinkMicWrapper.access$102(r0, r1)
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.a.b r1 = new com.plv.linkmic.processor.a.b
                r1.<init>()
                com.plv.linkmic.PLVLinkMicWrapper.access$302(r0, r1)
                goto La8
            L55:
                int r0 = android.os.Build.VERSION.SDK_INT
                r1 = 31
                if (r0 < r1) goto L75
                java.lang.String r0 = "android.permission.BLUETOOTH_CONNECT"
                java.lang.String[] r0 = new java.lang.String[]{r0}
                android.app.Activity r1 = com.plv.thirdpart.blankj.utilcode.util.ActivityUtils.getTopActivity()
                boolean r1 = com.plv.foundationsdk.permission.PLVFastPermission.hasPermission(r1, r0)
                if (r1 != 0) goto L75
                android.app.Activity r1 = com.plv.thirdpart.blankj.utilcode.util.ActivityUtils.getTopActivity()
                r2 = 1011(0x3f3, float:1.417E-42)
                r3 = 0
                com.plv.thirdpart.blankj.utilcode.util.PermissionUtils.requestPermissions(r1, r2, r0, r3)
            L75:
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.c.a r1 = new com.plv.linkmic.processor.c.a
                java.lang.String r2 = com.plv.linkmic.PLVLinkMicWrapper.access$200(r0)
                r1.<init>(r2)
                com.plv.linkmic.PLVLinkMicWrapper.access$102(r0, r1)
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.c.b r1 = new com.plv.linkmic.processor.c.b
                r1.<init>()
                com.plv.linkmic.PLVLinkMicWrapper.access$302(r0, r1)
                goto La8
            L8e:
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.b.a r1 = new com.plv.linkmic.processor.b.a
                com.plv.linkmic.PLVLinkMicWrapper r2 = com.plv.linkmic.PLVLinkMicWrapper.this
                java.lang.String r2 = com.plv.linkmic.PLVLinkMicWrapper.access$200(r2)
                r1.<init>(r2)
                com.plv.linkmic.PLVLinkMicWrapper.access$102(r0, r1)
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.b.b r1 = new com.plv.linkmic.processor.b.b
                r1.<init>()
                com.plv.linkmic.PLVLinkMicWrapper.access$302(r0, r1)
            La8:
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.PLVLinkMicWrapper$1$1 r1 = new com.plv.linkmic.PLVLinkMicWrapper$1$1
                r1.<init>()
                r0.addEventHandler(r1)
                com.plv.linkmic.PLVLinkMicWrapper r0 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.c r0 = com.plv.linkmic.PLVLinkMicWrapper.access$300(r0)
                com.plv.linkmic.model.PLVRTCConfig r1 = r4.val$rtcConfig
                android.content.Context r2 = r4.val$context
                com.plv.linkmic.PLVLinkMicWrapper r3 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.processor.a r3 = com.plv.linkmic.PLVLinkMicWrapper.access$100(r3)
                boolean r0 = r0.a(r5, r1, r2, r3)
                if (r0 != 0) goto Ld6
                com.plv.linkmic.PLVLinkMicWrapper$IPLVLinkMicWrapperCallback r0 = r4.val$linkMicWrapperCallback
                java.lang.Throwable r1 = new java.lang.Throwable
                java.lang.String r2 = r5.toString()
                r1.<init>(r2)
                r0.onLinkMicEngineCreateFailed(r1)
            Ld6:
                com.plv.linkmic.PLVLinkMicWrapper$IPLVLinkMicWrapperCallback r0 = r4.val$linkMicWrapperCallback
                java.lang.String r5 = r5.getToken()
                r0.onLinkMicEngineCreated(r5)
                com.plv.linkmic.PLVLinkMicWrapper r5 = com.plv.linkmic.PLVLinkMicWrapper.this
                com.plv.linkmic.PLVLinkMicWrapper.access$400(r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.plv.linkmic.PLVLinkMicWrapper.AnonymousClass1.onSuccess(com.plv.linkmic.repository.PLVLinkMicEngineToken):void");
        }
    }

    public interface IPLVLinkMicWrapperCallback {
        void onJoinChannelError(String str);

        void onLinkMicEngineCreateFailed(Throwable th);

        void onLinkMicEngineCreated(String str);
    }

    public interface IPLVTokenRequester {

        public interface OnRequestTokenListener {
            void onFail(Throwable th);

            void onSuccess(PLVLinkMicEngineToken pLVLinkMicEngineToken);
        }

        void requestToken(OnRequestTokenListener onRequestTokenListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void cleanCachedJoinChannelRunnable() {
        Runnable runnable = this.cachedJoinChannelRequest;
        if (runnable != null) {
            runnable.run();
            this.cachedJoinChannelRequest = null;
        }
    }

    private void listenScreenShareStatus() {
        if (this.screenEventHandler == null) {
            PLVLinkMicEventHandler pLVLinkMicEventHandler = new PLVLinkMicEventHandler() { // from class: com.plv.linkmic.PLVLinkMicWrapper.6
                @Override // com.plv.linkmic.PLVLinkMicEventHandler
                public void onScreenShare(boolean z2, int i2) {
                    PLVLinkMicWrapper.this.isScreenShaing = z2;
                    if (i2 == 1060500) {
                        PLVLinkMicWrapper.this.screenShareListener.onScreenShare(z2);
                        return;
                    }
                    if (PLVLinkMicWrapper.this.micProcessor instanceof com.plv.linkmic.processor.b.b) {
                        PLVLinkMicWrapper.this.stopShareScreen();
                    }
                    PLVLinkMicWrapper.this.screenShareListener.onScreenShareError(i2);
                }
            };
            this.screenEventHandler = pLVLinkMicEventHandler;
            addEventHandler(pLVLinkMicEventHandler);
        }
    }

    public void addEventHandler(PLVLinkMicEventHandler pLVLinkMicEventHandler) {
        a aVar = this.linkMicEventDispatcher;
        if (aVar == null) {
            return;
        }
        aVar.addEventHandler(pLVLinkMicEventHandler);
    }

    @Override // com.plv.linkmic.processor.b
    public int addPublishStreamUrl(String str, boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.addPublishStreamUrl(str, z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int adjustRecordingSignalVolume(int i2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.adjustRecordingSignalVolume(i2);
    }

    @Override // com.plv.linkmic.processor.b
    public SurfaceView createRendererView(Context context) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return null;
        }
        return cVar.createRendererView(context);
    }

    @Override // com.plv.linkmic.processor.b
    public TextureView createTextureRenderView(Context context) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return null;
        }
        return cVar.createTextureRenderView(context);
    }

    @Override // com.plv.linkmic.processor.b
    public void destroy() {
        leaveChannel(false);
        this.mUid = "";
        this.isTokenExpired = true;
        this.cachedJoinChannelRequest = null;
        Handler handler = this.linkHandler;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.plv.linkmic.PLVLinkMicWrapper.4
                @Override // java.lang.Runnable
                public void run() {
                    if (PLVLinkMicWrapper.this.micProcessor != null) {
                        PLVLinkMicWrapper.this.micProcessor.destroy();
                        PLVLinkMicWrapper.this.micProcessor = null;
                    }
                    if (PLVLinkMicWrapper.this.linkHandler != null) {
                        PLVLinkMicWrapper.this.linkHandler.removeCallbacksAndMessages(null);
                        PLVLinkMicWrapper.this.linkHandler = null;
                    }
                }
            });
        }
        a aVar = this.linkMicEventDispatcher;
        if (aVar != null) {
            aVar.removeAllEventHandler();
            this.linkMicEventDispatcher.destroy();
            this.linkMicEventDispatcher = null;
        }
    }

    @Override // com.plv.linkmic.processor.b
    public int enableLocalVideo(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.enableLocalVideo(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int enableTorch(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.enableTorch(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public String getLinkMicUid() {
        return this.mUid;
    }

    public void init(Context context, PLVRTCConfig pLVRTCConfig, IPLVTokenRequester iPLVTokenRequester, @NonNull IPLVLinkMicWrapperCallback iPLVLinkMicWrapperCallback) {
        this.micWrapperCallback = iPLVLinkMicWrapperCallback;
        this.mUid = pLVRTCConfig.getUid();
        this.isTokenExpired = true;
        this.context = context;
        iPLVTokenRequester.requestToken(new AnonymousClass1(pLVRTCConfig, iPLVTokenRequester, context, iPLVLinkMicWrapperCallback));
    }

    @Override // com.plv.linkmic.processor.b
    public boolean isScreenSharing() {
        return this.isScreenShaing;
    }

    @Override // com.plv.linkmic.processor.b
    public int joinChannel(final String str) {
        PLVCommonLog.d(TAG, "joinChannel ");
        if (this.micProcessor == null) {
            return -1;
        }
        Runnable runnable = new Runnable() { // from class: com.plv.linkmic.PLVLinkMicWrapper.2
            @Override // java.lang.Runnable
            public void run() {
                int iJoinChannel;
                if (PLVLinkMicWrapper.this.micProcessor == null || (iJoinChannel = PLVLinkMicWrapper.this.micProcessor.joinChannel(str)) == 0) {
                    return;
                }
                PLVLinkMicWrapper.this.micWrapperCallback.onJoinChannelError("errorCode=" + iJoinChannel);
            }
        };
        if (this.isTokenExpired) {
            this.cachedJoinChannelRequest = runnable;
            return 0;
        }
        this.linkHandler.post(runnable);
        return 0;
    }

    @Override // com.plv.linkmic.processor.b
    public void leaveChannel(final boolean z2) {
        PLVCommonLog.d(TAG, "leaveChannel->keepPreview=" + z2);
        Handler handler = this.linkHandler;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.plv.linkmic.PLVLinkMicWrapper.3
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLinkMicWrapper.this.micProcessor == null) {
                    return;
                }
                PLVLinkMicWrapper.this.micProcessor.leaveChannel(z2);
            }
        });
    }

    @Override // com.plv.linkmic.processor.b
    public int muteAllRemoteAudio(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.muteAllRemoteAudio(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int muteAllRemoteVideo(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.muteAllRemoteVideo(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int muteLocalAudio(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.muteLocalAudio(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int muteLocalVideo(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.muteLocalVideo(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int muteRemoteAudio(String str, boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.muteRemoteAudio(str, z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int muteRemoteVideo(String str, boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.muteRemoteVideo(str, z2);
    }

    @Override // com.plv.linkmic.processor.b
    public void releaseRenderView(View view) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return;
        }
        cVar.releaseRenderView(view);
    }

    public void removeAllEventHandler() {
        a aVar = this.linkMicEventDispatcher;
        if (aVar == null) {
            return;
        }
        aVar.removeAllEventHandler();
    }

    public void removeEventHandler(PLVLinkMicEventHandler pLVLinkMicEventHandler) {
        a aVar = this.linkMicEventDispatcher;
        if (aVar == null) {
            return;
        }
        aVar.removeEventHandler(pLVLinkMicEventHandler);
    }

    @Override // com.plv.linkmic.processor.b
    public int removePublishStreamUrl(String str) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.removePublishStreamUrl(str);
    }

    @Override // com.plv.linkmic.processor.b
    public int renewToken(String str) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.renewToken(str);
    }

    @Override // com.plv.linkmic.processor.b
    public void requestAndStartShareScreen(Activity activity) {
        listenScreenShareStatus();
        c cVar = this.micProcessor;
        if (cVar instanceof com.plv.linkmic.processor.b.b) {
            cVar.startShareScreen();
        } else {
            PLVScreenCaptureUtils.requestCapture(activity, new PLVScreenCaptureUtils.IPLVOnRequestResultListener() { // from class: com.plv.linkmic.PLVLinkMicWrapper.5
                @Override // com.plv.linkmic.screenshare.PLVScreenCaptureUtils.IPLVOnRequestResultListener
                public void onRequestResult(int i2, int i3, Intent intent) {
                    if (i3 == -1) {
                        PLVLinkMicWrapper.this.micProcessor.setScreenCaptureSource(intent);
                        PLVLinkMicWrapper.this.micProcessor.startShareScreen();
                    } else {
                        PLVLinkMicWrapper.this.isScreenShaing = false;
                        if (PLVLinkMicWrapper.this.screenShareListener != null) {
                            PLVLinkMicWrapper.this.screenShareListener.onScreenShareError(1060501);
                        }
                    }
                }
            });
        }
    }

    @Override // com.plv.linkmic.processor.b
    public void setBitrate(int i2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return;
        }
        cVar.setBitrate(i2);
    }

    @Override // com.plv.linkmic.processor.b
    public int setCameraZoomRatio(float f2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setCameraZoomRatio(f2);
    }

    @Override // com.plv.linkmic.processor.b
    public int setLocalPreviewMirror(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setLocalPreviewMirror(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int setLocalPushMirror(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setLocalPushMirror(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int setOnlyAudio(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setOnlyAudio(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public int setPushPictureResolutionType(int i2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setPushPictureResolutionType(i2);
    }

    @Override // com.plv.linkmic.processor.b
    public int setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setPushResolutionRatio(pushResolutionRatio);
    }

    @Override // com.plv.linkmic.processor.b
    public void setScreenCaptureSource(Intent intent) {
        this.micProcessor.setScreenCaptureSource(intent);
    }

    public void setScreenShareListener(IPLVScreenShareListener iPLVScreenShareListener) {
        this.screenShareListener = iPLVScreenShareListener;
    }

    @Override // com.plv.linkmic.processor.b
    public int setVideoMuteImage(String str) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setVideoMuteImage(str);
    }

    @Override // com.plv.linkmic.processor.b
    public int setupLocalVideo(View view, int i2, String str) {
        PLVCommonLog.d(TAG, "setupLocalVideo");
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setupLocalVideo(view, i2, str);
    }

    @Override // com.plv.linkmic.processor.b
    public void setupRemoteVideo(View view, int i2, String str) {
        PLVCommonLog.d(TAG, "setupRemoteVideo");
        c cVar = this.micProcessor;
        if (cVar == null) {
            return;
        }
        cVar.setupRemoteVideo(view, i2, str);
    }

    @Override // com.plv.linkmic.processor.b
    public void startPreview() {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return;
        }
        cVar.startPreview();
    }

    @Override // com.plv.linkmic.processor.b
    public int startPushImageStream(String str) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.startPushImageStream(str);
    }

    @Override // com.plv.linkmic.processor.b
    public int startShareScreen() {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.startShareScreen();
    }

    @Override // com.plv.linkmic.processor.b
    public int stopPushImageStream() {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.stopPushImageStream();
    }

    @Override // com.plv.linkmic.processor.b
    public int stopShareScreen() {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.stopShareScreen();
    }

    @Override // com.plv.linkmic.processor.b
    public void switchBeauty(boolean z2) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return;
        }
        cVar.switchBeauty(z2);
    }

    @Override // com.plv.linkmic.processor.b
    public void switchCamera() {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return;
        }
        cVar.switchCamera();
    }

    @Override // com.plv.linkmic.processor.b
    public int switchRoleToAudience() {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.switchRoleToAudience();
    }

    @Override // com.plv.linkmic.processor.b
    public int switchRoleToBroadcaster() {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.switchRoleToBroadcaster();
    }

    @Override // com.plv.linkmic.processor.b
    public void takeSnapshot(String str, PLVSugarUtil.Consumer<Bitmap> consumer) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return;
        }
        cVar.takeSnapshot(str, consumer);
    }

    @Override // com.plv.linkmic.processor.b
    public int updateSEIFrameTimeStamp(String str) {
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.updateSEIFrameTimeStamp(str);
    }

    @Override // com.plv.linkmic.processor.b
    public int setupRemoteVideo(View view, int i2, String str, int i3) {
        PLVCommonLog.d(TAG, "setupRemoteVideo() called with: surfaceView = [" + view + "], renderMode = [" + i2 + "], uid = [" + str + "], streamType = [" + i3 + StrPool.BRACKET_END);
        c cVar = this.micProcessor;
        if (cVar == null) {
            return -1;
        }
        return cVar.setupRemoteVideo(view, i2, str, i3);
    }
}
