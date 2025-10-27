package com.tencent.liteav.trtc.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Pair;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import androidx.core.internal.view.SupportMenu;
import androidx.exifinterface.media.ExifInterface;
import c.i;
import cn.hutool.core.text.StrPool;
import com.aliyun.player.aliyunplayerbase.bean.AliyunVideoListBean;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.tencent.liteav.AudioServerConfig;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.audio.TXAudioEffectManagerImpl;
import com.tencent.liteav.audio.TXCAudioEncoderConfig;
import com.tencent.liteav.audio.TXCAudioEngine;
import com.tencent.liteav.audio.TXCLiveBGMPlayer;
import com.tencent.liteav.audio.TXCSoundEffectPlayer;
import com.tencent.liteav.audio.b;
import com.tencent.liteav.audio.d;
import com.tencent.liteav.audio.e;
import com.tencent.liteav.audio.f;
import com.tencent.liteav.audio.g;
import com.tencent.liteav.audio.h;
import com.tencent.liteav.audio.impl.TXCAudioEngineJNI;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCEventRecorderProxy;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.opengl.e;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.g;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.liteav.d;
import com.tencent.liteav.device.TXDeviceManager;
import com.tencent.liteav.device.TXDeviceManagerImpl;
import com.tencent.liteav.g;
import com.tencent.liteav.o;
import com.tencent.liteav.screencapture.a;
import com.tencent.liteav.trtc.impl.TRTCEncodeTypeDecision;
import com.tencent.liteav.trtc.impl.TRTCRoomInfo;
import com.tencent.liteav.videoencoder.TXCSWVideoEncoder;
import com.tencent.liteav.videoencoder.c;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.tencent.trtc.TRTCStatistics;
import com.tencent.trtc.TRTCSubCloud;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.microedition.khronos.egl.EGLContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class TRTCCloudImpl extends TRTCCloud implements TXCRenderAndDec.b, b, d, e, f, g, h, com.tencent.liteav.basic.b.b, d.a, o, a.InterfaceC0337a {
    private static final int CURRENT_ENCODE_TYPE_H264 = 0;
    private static final int CURRENT_ENCODE_TYPE_H265 = 1;
    private static final int DEFAULT_FPS_FOR_SCREEN_CAPTURE = 10;
    private static final int DEFAULT_GOP_FOR_SCREEN_CAPTURE = 3;
    private static final int DEFAULT_VOLUME_LEVEL = 100;
    private static final String KEY_CONFIG_ADJUST_RESOLUTION = "config_adjust_resolution";
    private static final String KEY_CONFIG_FPS = "config_fps";
    private static final String KEY_CONFIG_GOP = "config_gop";
    private static final int LOCAL_AUDIO_MUTE_MODE_DEFAULT = 0;
    private static final int LOCAL_AUDIO_MUTE_MODE_EOS = 1;
    private static final int LOCAL_AUDIO_MUTE_MODE_NO_PACKET = 2;
    private static final int MIN_VOLUME_EVALUATION_INTERVAL_MS = 100;
    private static final int RECV_MODE_AUTO_AUDIO_ONLY = 2;
    private static final int RECV_MODE_AUTO_AUDIO_VIDEO = 1;
    private static final int RECV_MODE_AUTO_VIDEO_ONLY = 3;
    private static final int RECV_MODE_MANUAL = 4;
    private static final int RECV_MODE_UNKNOWN = 0;
    protected static final int ROOM_STATE_ENTRING = 1;
    private static final int ROOM_STATE_IN = 2;
    protected static final int ROOM_STATE_OUT = 0;
    private static final int START_LOCAL_RECORDING_FAILED = -1;
    private static final int STATE_INTERVAL = 2000;
    private static final String TAG = "TRTCCloudImpl";
    private static TRTCCloudImpl sInstance;
    protected int mAppScene;
    private int mAudioCaptureVolume;
    protected TRTCCloudListener.TRTCAudioFrameListener mAudioFrameListener;
    private int mAudioPlayoutVolume;
    protected int mAudioVolumeEvalInterval;
    private TRTCCloud.BGMNotify mBGMNotify;
    private int mBackground;
    private com.tencent.liteav.basic.b.a mCallback;
    protected com.tencent.liteav.d mCaptureAndEnc;
    protected int mCheckDuplicateEnterRoom;
    private int mCodecType;
    protected com.tencent.liteav.g mConfig;
    protected Context mContext;
    private int mCurrentOrientation;
    protected HashMap<Integer, TRTCCloudImpl> mCurrentPublishClouds;
    protected int mCurrentRole;
    private int mCustomCaptureGLSyncMode;
    private final Object mCustomCaptureLock;
    private boolean mCustomRemoteRender;
    private TRTCCustomTextureUtil mCustomSubStreamVideoUtil;
    private TRTCCustomTextureUtil mCustomVideoUtil;
    protected int mDebugType;
    private TXDeviceManagerImpl mDeviceManager;
    private TXDeviceManagerImpl.TXDeviceManagerListener mDeviceManagerListener;
    private boolean mEnableCustomAudioCapture;
    private boolean mEnableCustomAudioRendering;
    private boolean mEnableCustomVideoCapture;
    protected boolean mEnableEosMode;
    private boolean mEnableLowLatencyMode;
    private boolean mEnableSmallStream;
    private boolean mEnableSoftAEC;
    private boolean mEnableSoftAGC;
    private boolean mEnableSoftANS;
    private View mFloatingWindow;
    private int mFramework;
    private TRTCEncodeTypeDecision mH265Decision;
    private boolean mIsAudioCapturing;
    private boolean mIsDestroyed;
    protected boolean mIsExitOldRoom;
    private AtomicBoolean mIsSDKThreadAlive;
    private boolean mIsVideoCapturing;
    private boolean mKeepAVCaptureWhenEnterRoomFailed;
    private long mLastLogCustomCmdMsgTs;
    private long mLastLogSEIMsgTs;
    private long mLastSendMsgTimeMs;
    protected long mLastStateTimeMs;
    private final Bundle mLatestParamsOfBigEncoder;
    private final Bundle mLatestParamsOfSmallEncoder;
    private final Bundle mLatestParamsOfSubEncoder;
    private Handler mListenerHandler;
    private final SurfaceHolder.Callback mLocalPreviewSurfaceViewCallback;
    private com.tencent.liteav.basic.util.f mMainHandler;
    private VideoSourceType mMainStreamVideoSourceType;
    protected Object mNativeLock;
    protected long mNativeRtcContext;
    private int mNetType;
    private DisplayOrientationDetector mOrientationEventListener;
    private int mOriginalFramework;
    private boolean mOverrideFPSFromUser;
    protected int mPerformanceMode;
    private int mPlayoutAudioTunnelId;
    protected int mPriorStreamType;
    private int mPublishAudioTunnelId;
    protected int mQoSStrategy;
    private int mQosMode;
    private int mQosPreference;
    private long mRecvCustomCmdMsgCountInPeriod;
    protected int mRecvMode;
    private long mRecvSEIMsgCountInPeriod;
    protected HashMap<String, RenderListenerAdapter> mRenderListenerMap;
    protected TRTCRoomInfo mRoomInfo;
    protected int mRoomState;
    protected int mRoomType;
    protected com.tencent.liteav.basic.util.f mSDKHandler;
    private int mSendMsgCount;
    private int mSendMsgSize;
    private int mSensorMode;
    private final TRTCCloudDef.TRTCVideoEncParam mSmallEncParam;
    private int mSoftAECLevel;
    private int mSoftAGCLevel;
    private int mSoftANSLevel;
    private StatusTask mStatusNotifyTask;
    private Set<Integer> mStreamTypes;
    protected ArrayList<WeakReference<TRTCCloudImpl>> mSubClouds;
    protected com.tencent.liteav.d mSubStreamCaptureAndEnc;
    protected com.tencent.liteav.g mSubStreamConfig;
    private VideoSourceType mSubStreamVideoSourceType;
    protected TRTCCloudListener mTRTCListener;
    protected int mTargetRole;
    private final TRTCVideoPreprocessListenerAdapter mVideoPreprocessListenerAdapter;
    private int mVideoRenderMirror;
    private TRTCVideoServerConfig mVideoServerConfig;
    private VolumeLevelNotifyTask mVolumeLevelNotifyTask;
    final ArrayList<String> reverbParamKeyNames;
    final TXAudioEffectManager.TXVoiceReverbType[] reverbTypes;
    final TXAudioEffectManager.TXVoiceChangerType[] voiceChangerTypes;

    /* renamed from: com.tencent.liteav.trtc.impl.TRTCCloudImpl$118, reason: invalid class name */
    public class AnonymousClass118 implements Runnable {
        final /* synthetic */ int val$showType;

        public AnonymousClass118(int i2) {
            this.val$showType = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            TRTCCloudImpl.this.apiLog("showDebugView " + this.val$showType);
            TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
            tRTCCloudImpl.mDebugType = this.val$showType;
            final TXCloudVideoView tXCloudVideoView = tRTCCloudImpl.mRoomInfo.localView;
            if (tXCloudVideoView != null) {
                tRTCCloudImpl.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.118.1
                    @Override // java.lang.Runnable
                    public void run() {
                        tXCloudVideoView.showVideoDebugLog(AnonymousClass118.this.val$showType);
                    }
                });
            }
            TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.118.2
                @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                    final TXCloudVideoView tXCloudVideoView2 = userInfo.mainRender.view;
                    final TXCloudVideoView tXCloudVideoView3 = userInfo.subRender.view;
                    if (tXCloudVideoView2 == null && tXCloudVideoView3 == null) {
                        return;
                    }
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.118.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TXCloudVideoView tXCloudVideoView4 = tXCloudVideoView2;
                            if (tXCloudVideoView4 != null) {
                                tXCloudVideoView4.showVideoDebugLog(AnonymousClass118.this.val$showType);
                            }
                            TXCloudVideoView tXCloudVideoView5 = tXCloudVideoView3;
                            if (tXCloudVideoView5 != null) {
                                tXCloudVideoView5.showVideoDebugLog(AnonymousClass118.this.val$showType);
                            }
                        }
                    });
                }
            });
        }
    }

    /* renamed from: com.tencent.liteav.trtc.impl.TRTCCloudImpl$145, reason: invalid class name */
    public class AnonymousClass145 implements Runnable {
        final /* synthetic */ int val$err;

        public AnonymousClass145(int i2) {
            this.val$err = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
            if (tRTCCloudImpl.mIsExitOldRoom) {
                tRTCCloudImpl.mIsExitOldRoom = false;
                tRTCCloudImpl.apiLog("exit no current room, ignore onExitRoom.");
            } else {
                if (!tRTCCloudImpl.mRoomInfo.isMicStard()) {
                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.145.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass145 anonymousClass145 = AnonymousClass145.this;
                            TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                            if (tRTCCloudListener != null) {
                                tRTCCloudListener.onExitRoom(anonymousClass145.val$err);
                            }
                        }
                    });
                    return;
                }
                TRTCCloudImpl.this.mRoomInfo.setRoomExit(true, this.val$err);
                TRTCCloudImpl.this.apiLog("onExitRoom delay 2s when mic is not release.");
                TRTCCloudImpl.this.runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.145.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TRTCCloudImpl.this.mRoomInfo.isRoomExit()) {
                            TRTCCloudImpl.this.apiLog("force onExitRoom after 2s");
                            final int roomExitCode = TRTCCloudImpl.this.mRoomInfo.getRoomExitCode();
                            TRTCCloudImpl.this.mRoomInfo.setRoomExit(false, 0);
                            TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.145.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                                    if (tRTCCloudListener != null) {
                                        tRTCCloudListener.onExitRoom(roomExitCode);
                                    }
                                }
                            });
                        }
                    }
                }, 2000);
            }
        }
    }

    /* renamed from: com.tencent.liteav.trtc.impl.TRTCCloudImpl$208, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass208 {
        static final /* synthetic */ int[] $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType;

        static {
            int[] iArr = new int[TXDeviceManager.TXSystemVolumeType.values().length];
            $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType = iArr;
            try {
                iArr[TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeAuto.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType[TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeVOIP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType[TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeMedia.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* renamed from: com.tencent.liteav.trtc.impl.TRTCCloudImpl$25, reason: invalid class name */
    public class AnonymousClass25 implements Runnable {
        final /* synthetic */ TRTCCloudListener.TRTCSnapshotListener val$listener;
        final /* synthetic */ int val$streamType;
        final /* synthetic */ String val$userId;

        public AnonymousClass25(String str, TRTCCloudListener.TRTCSnapshotListener tRTCSnapshotListener, int i2) {
            this.val$userId = str;
            this.val$listener = tRTCSnapshotListener;
            this.val$streamType = i2;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x007f  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r4 = this;
                java.lang.String r0 = r4.val$userId
                if (r0 != 0) goto L1a
                com.tencent.liteav.trtc.impl.TRTCCloudImpl r0 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                java.lang.String r1 = "snapshotLocalView"
                r0.apiLog(r1)
                com.tencent.liteav.trtc.impl.TRTCCloudImpl r0 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                com.tencent.liteav.d r0 = r0.mCaptureAndEnc
                com.tencent.liteav.trtc.impl.TRTCCloudImpl$25$1 r1 = new com.tencent.liteav.trtc.impl.TRTCCloudImpl$25$1
                r1.<init>()
                r0.a(r1)
                goto L95
            L1a:
                com.tencent.liteav.trtc.impl.TRTCCloudImpl r1 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                com.tencent.liteav.trtc.impl.TRTCRoomInfo r1 = r1.mRoomInfo
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$UserInfo r0 = r1.getUser(r0)
                int r1 = r4.val$streamType
                r2 = 2
                if (r1 != r2) goto L53
                if (r0 == 0) goto L7f
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r1 = r0.mainRender
                if (r1 == 0) goto L7f
                com.tencent.liteav.TXCRenderAndDec r1 = r1.render
                if (r1 == 0) goto L7f
                com.tencent.liteav.trtc.impl.TRTCCloudImpl r1 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "snapshotRemoteSubStreamView->userId: "
                r2.append(r3)
                java.lang.String r3 = r4.val$userId
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                r1.apiLog(r2)
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r0 = r0.subRender
                com.tencent.liteav.TXCRenderAndDec r0 = r0.render
                com.tencent.liteav.renderer.e r0 = r0.getVideoRender()
                goto L80
            L53:
                if (r0 == 0) goto L7f
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r1 = r0.mainRender
                if (r1 == 0) goto L7f
                com.tencent.liteav.TXCRenderAndDec r1 = r1.render
                if (r1 == 0) goto L7f
                com.tencent.liteav.trtc.impl.TRTCCloudImpl r1 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "snapshotRemoteView->userId: "
                r2.append(r3)
                java.lang.String r3 = r4.val$userId
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                r1.apiLog(r2)
                com.tencent.liteav.trtc.impl.TRTCRoomInfo$RenderInfo r0 = r0.mainRender
                com.tencent.liteav.TXCRenderAndDec r0 = r0.render
                com.tencent.liteav.renderer.e r0 = r0.getVideoRender()
                goto L80
            L7f:
                r0 = 0
            L80:
                if (r0 == 0) goto L8b
                com.tencent.liteav.trtc.impl.TRTCCloudImpl$25$2 r1 = new com.tencent.liteav.trtc.impl.TRTCCloudImpl$25$2
                r1.<init>()
                r0.a(r1)
                goto L95
            L8b:
                com.tencent.liteav.trtc.impl.TRTCCloudImpl r0 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                com.tencent.liteav.trtc.impl.TRTCCloudImpl$25$3 r1 = new com.tencent.liteav.trtc.impl.TRTCCloudImpl$25$3
                r1.<init>()
                r0.runOnListenerThread(r1)
            L95:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCCloudImpl.AnonymousClass25.run():void");
        }
    }

    public static class DisplayOrientationDetector extends OrientationEventListener {
        public int mCurOrientation;
        private int mCurrentDisplayRotation;
        private WeakReference<TRTCCloudImpl> mTRTCEngine;

        public DisplayOrientationDetector(Context context, TRTCCloudImpl tRTCCloudImpl) {
            super(context);
            this.mCurOrientation = -1;
            this.mCurrentDisplayRotation = 0;
            this.mTRTCEngine = new WeakReference<>(tRTCCloudImpl);
        }

        public void checkOrientation() {
            int displayRotation;
            TRTCCloudImpl tRTCCloudImpl = this.mTRTCEngine.get();
            if (tRTCCloudImpl == null || this.mCurrentDisplayRotation == (displayRotation = tRTCCloudImpl.getDisplayRotation())) {
                return;
            }
            this.mCurrentDisplayRotation = displayRotation;
            tRTCCloudImpl.setOrientation(this.mCurOrientation);
        }

        @Override // android.view.OrientationEventListener
        public void onOrientationChanged(int i2) {
            if (i2 == -1) {
                TXCLog.i("DisplayOrientationDetector", "rotation-change invalid " + i2);
                return;
            }
            int i3 = 1;
            if (i2 > 45) {
                if (i2 <= 135) {
                    i3 = 2;
                } else if (i2 <= 225) {
                    i3 = 3;
                } else if (i2 <= 315) {
                    i3 = 0;
                }
            }
            if (this.mCurOrientation != i3) {
                this.mCurOrientation = i3;
                TRTCCloudImpl tRTCCloudImpl = this.mTRTCEngine.get();
                if (tRTCCloudImpl != null) {
                    this.mCurrentDisplayRotation = tRTCCloudImpl.getDisplayRotation();
                    tRTCCloudImpl.setOrientation(this.mCurOrientation);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("rotation-change onOrientationChanged ");
                sb.append(i2);
                sb.append(", orientation ");
                sb.append(this.mCurOrientation);
                sb.append(" self:");
                sb.append(tRTCCloudImpl != null ? Integer.valueOf(tRTCCloudImpl.hashCode()) : "");
                TXCLog.d("DisplayOrientationDetector", sb.toString());
            }
        }
    }

    public class LocalPreviewTextureViewListener implements TextureView.SurfaceTextureListener {
        private final TextureView mHost;
        private Surface mSurfaceFromView;

        public LocalPreviewTextureViewListener(TextureView textureView) {
            this.mHost = textureView;
            if (textureView != null && textureView.getSurfaceTexture() != null) {
                this.mSurfaceFromView = new Surface(textureView.getSurfaceTexture());
            }
            TXCLog.i(TRTCCloudImpl.TAG, "TextureViewListener created with host: %s", textureView);
        }

        private boolean isTextureViewCurrentlyInUse() {
            TXCloudVideoView tXCloudVideoView;
            TRTCRoomInfo tRTCRoomInfo = TRTCCloudImpl.this.mRoomInfo;
            return (tRTCRoomInfo == null || (tXCloudVideoView = tRTCRoomInfo.localView) == null || this.mHost != tXCloudVideoView.getHWVideoView()) ? false : true;
        }

        public Surface getSurface() {
            return this.mSurfaceFromView;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
            this.mSurfaceFromView = new Surface(surfaceTexture);
            if (isTextureViewCurrentlyInUse()) {
                TXCLog.i(TRTCCloudImpl.TAG, "local preview surfaceCreated, surface: %s, host: %s", surfaceTexture, this.mHost);
                TRTCCloudImpl.this.mCaptureAndEnc.a(this.mSurfaceFromView);
                TRTCCloudImpl.this.mCaptureAndEnc.a(i2, i3);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (isTextureViewCurrentlyInUse()) {
                TXCLog.i(TRTCCloudImpl.TAG, "local preview surfaceDestroyed %s, host: %s", surfaceTexture, this.mHost);
                TRTCCloudImpl.this.mCaptureAndEnc.a((Surface) null);
            }
            Surface surface = this.mSurfaceFromView;
            if (surface != null) {
                surface.release();
                this.mSurfaceFromView = null;
            }
            return true;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
            if (isTextureViewCurrentlyInUse()) {
                TXCLog.i(TRTCCloudImpl.TAG, "local preview surfaceChanged %s width: %d, height: %d", surfaceTexture, Integer.valueOf(i2), Integer.valueOf(i3));
                TRTCCloudImpl.this.mCaptureAndEnc.a(i2, i3);
            }
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    public static class RenderListenerAdapter {
        public int bufferType;
        public TRTCCloudListener.TRTCVideoRenderListener listener;
        public int pixelFormat;
        public String strTinyID;
    }

    public static class StatusTask implements Runnable {
        private WeakReference<TRTCCloudImpl> mTRTCEngine;

        public StatusTask(TRTCCloudImpl tRTCCloudImpl) {
            this.mTRTCEngine = new WeakReference<>(tRTCCloudImpl);
        }

        @Override // java.lang.Runnable
        public void run() {
            int i2;
            TRTCCloudImpl tRTCCloudImpl = this.mTRTCEngine.get();
            if (tRTCCloudImpl == null) {
                return;
            }
            int iE = com.tencent.liteav.basic.util.h.e(tRTCCloudImpl.mContext);
            int[] iArrA = com.tencent.liteav.basic.util.h.a();
            int iB = com.tencent.liteav.basic.util.h.b() * 1024;
            TXCStatus.a("18446744073709551615", R2.drawable.jingyan_help_top, Integer.valueOf(iE));
            TXCStatus.a("18446744073709551615", R2.drawable.jiav, Integer.valueOf(iArrA[0] / 10));
            TXCStatus.a("18446744073709551615", R2.drawable.jiav_night, Integer.valueOf(iArrA[1] / 10));
            TXCStatus.a("18446744073709551615", R2.drawable.jieshu, Integer.valueOf(iB));
            if (com.tencent.liteav.basic.util.h.a(tRTCCloudImpl.mContext)) {
                TXCStatus.a("18446744073709551615", R2.drawable.jin, (Object) 1);
                i2 = 1;
            } else {
                TXCStatus.a("18446744073709551615", R2.drawable.jin, (Object) 0);
                i2 = 0;
            }
            if (tRTCCloudImpl.mNetType != iE) {
                if (tRTCCloudImpl.mNetType >= 0 && iE > 0) {
                    tRTCCloudImpl.nativeReenterRoom(tRTCCloudImpl.mNativeRtcContext, 100, tRTCCloudImpl.mNetType != 0);
                }
                TXCEventRecorderProxy.a("18446744073709551615", 1003, iE == 0 ? 0L : iE, -1L, "", 0);
                Monitor.a(2, String.format("network switch from:%d to %d", Integer.valueOf(tRTCCloudImpl.mNetType), Integer.valueOf(iE)) + " self:" + tRTCCloudImpl.hashCode(), "1:wifi/2:4G/3:3G/4:2G/5:Cable/6:5G", 0);
                tRTCCloudImpl.mNetType = iE;
                TXCKeyPointReportProxy.a(40039, iE, 0);
            }
            if (tRTCCloudImpl.mBackground != i2) {
                TXCEventRecorderProxy.a("18446744073709551615", 2001, i2, -1L, "", 0);
                tRTCCloudImpl.mBackground = i2;
                if (i2 == 0) {
                    tRTCCloudImpl.apiOnlineLog("onAppDidBecomeActive");
                } else {
                    tRTCCloudImpl.apiOnlineLog("onAppEnterBackground");
                }
                TXCKeyPointReportProxy.c(50001, i2);
            }
            TXCKeyPointReportProxy.a(iArrA[0] / 10, iArrA[1] / 10);
            TXCKeyPointReportProxy.a();
            tRTCCloudImpl.checkRTCState();
            tRTCCloudImpl.checkDashBoard();
            tRTCCloudImpl.collectCustomCaptureFps();
            tRTCCloudImpl.startCollectStatus();
            if (tRTCCloudImpl.mSensorMode != 0) {
                tRTCCloudImpl.mOrientationEventListener.checkOrientation();
            }
        }
    }

    public enum VideoSourceType {
        NONE,
        CAMERA,
        SCREEN,
        CUSTOM
    }

    public static class VolumeLevelNotifyTask implements Runnable {
        private WeakReference<TRTCCloudImpl> mWeakTRTCEngine;

        public VolumeLevelNotifyTask(TRTCCloudImpl tRTCCloudImpl) {
            this.mWeakTRTCEngine = new WeakReference<>(tRTCCloudImpl);
        }

        @Override // java.lang.Runnable
        public void run() {
            WeakReference<TRTCCloudImpl> weakReference = this.mWeakTRTCEngine;
            TRTCCloudImpl tRTCCloudImpl = weakReference != null ? weakReference.get() : null;
            if (tRTCCloudImpl != null) {
                final ArrayList arrayList = new ArrayList();
                int softwareCaptureVolumeLevel = tRTCCloudImpl.mCaptureAndEnc != null ? TXCAudioEngine.getInstance().getSoftwareCaptureVolumeLevel() : 0;
                if (softwareCaptureVolumeLevel > 0) {
                    TRTCCloudDef.TRTCVolumeInfo tRTCVolumeInfo = new TRTCCloudDef.TRTCVolumeInfo();
                    tRTCVolumeInfo.userId = tRTCCloudImpl.mRoomInfo.userId;
                    tRTCVolumeInfo.volume = softwareCaptureVolumeLevel;
                    arrayList.add(tRTCVolumeInfo);
                }
                tRTCCloudImpl.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.VolumeLevelNotifyTask.1
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
                final int mixingPlayoutVolumeLevel = TXCAudioEngine.getMixingPlayoutVolumeLevel();
                final TRTCCloudListener tRTCCloudListener = tRTCCloudImpl.mTRTCListener;
                tRTCCloudImpl.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.VolumeLevelNotifyTask.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TRTCCloudListener tRTCCloudListener2 = tRTCCloudListener;
                        if (tRTCCloudListener2 != null) {
                            tRTCCloudListener2.onUserVoiceVolume(arrayList, mixingPlayoutVolumeLevel);
                        }
                    }
                });
                if (tRTCCloudImpl.mAudioVolumeEvalInterval > 0) {
                    tRTCCloudImpl.mSDKHandler.postDelayed(tRTCCloudImpl.mVolumeLevelNotifyTask, tRTCCloudImpl.mAudioVolumeEvalInterval);
                }
            }
        }
    }

    static {
        com.tencent.liteav.basic.util.h.d();
    }

    public TRTCCloudImpl(Context context) {
        this.reverbTypes = new TXAudioEffectManager.TXVoiceReverbType[]{TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_0, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_1, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_2, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_3, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_4, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_5, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_6, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_7, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_8, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_9, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_10};
        this.voiceChangerTypes = new TXAudioEffectManager.TXVoiceChangerType[]{TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_0, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_1, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_2, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_3, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_4, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_5, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_6, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_7, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_8, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_9, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_10, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_11};
        this.reverbParamKeyNames = new ArrayList<>(Arrays.asList("dampingFreq", "density", "bandwidthFreq", "decay", "preDelay", "roomSize", "gain", "mix", "earlyMix"));
        this.mNativeLock = new Object();
        this.mIsDestroyed = false;
        this.mAudioFrameListener = null;
        this.mCustomCaptureLock = new Object();
        this.mCustomSubStreamVideoUtil = null;
        this.mPublishAudioTunnelId = -1;
        this.mPlayoutAudioTunnelId = -1;
        this.mPriorStreamType = 2;
        this.mEnableSmallStream = false;
        this.mVideoRenderMirror = 0;
        this.mCustomRemoteRender = false;
        this.mAudioVolumeEvalInterval = 0;
        this.mSmallEncParam = new TRTCCloudDef.TRTCVideoEncParam();
        this.mQosMode = 1;
        this.mEnableEosMode = false;
        this.mCodecType = 2;
        this.mEnableSoftAEC = true;
        this.mEnableSoftANS = false;
        this.mEnableSoftAGC = false;
        this.mSoftAECLevel = 100;
        this.mSoftANSLevel = 100;
        this.mSoftAGCLevel = 100;
        this.mAudioCaptureVolume = 100;
        this.mAudioPlayoutVolume = 100;
        this.mCustomVideoUtil = null;
        this.mEnableCustomAudioCapture = false;
        this.mEnableCustomAudioRendering = false;
        this.mEnableCustomVideoCapture = false;
        this.mCurrentRole = 20;
        this.mTargetRole = 20;
        this.mVideoPreprocessListenerAdapter = new TRTCVideoPreprocessListenerAdapter();
        this.mPerformanceMode = 0;
        this.mRoomType = 0;
        this.mCurrentOrientation = -1;
        this.mFloatingWindow = null;
        this.mOverrideFPSFromUser = false;
        this.mLatestParamsOfBigEncoder = new Bundle();
        this.mLatestParamsOfSmallEncoder = new Bundle();
        this.mLatestParamsOfSubEncoder = new Bundle();
        this.mFramework = 1;
        this.mOriginalFramework = 1;
        this.mCheckDuplicateEnterRoom = 0;
        this.mCustomCaptureGLSyncMode = 0;
        this.mQoSStrategy = 0;
        this.mLocalPreviewSurfaceViewCallback = new SurfaceHolder.Callback() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.1
            private boolean isSameHolderCurrentlyInUse(SurfaceHolder surfaceHolder) {
                TXCloudVideoView tXCloudVideoView;
                SurfaceView surfaceView;
                TRTCRoomInfo tRTCRoomInfo = TRTCCloudImpl.this.mRoomInfo;
                return (tRTCRoomInfo == null || (tXCloudVideoView = tRTCRoomInfo.localView) == null || (surfaceView = tXCloudVideoView.getSurfaceView()) == null || surfaceView.getHolder() != surfaceHolder) ? false : true;
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                if (isSameHolderCurrentlyInUse(surfaceHolder)) {
                    TRTCCloudImpl.this.apiLog("local view surfaceChanged, size: %dx%d, surface: %s, holder: %s", Integer.valueOf(i3), Integer.valueOf(i4), surfaceHolder.getSurface(), surfaceHolder);
                    TRTCCloudImpl.this.mCaptureAndEnc.a(i3, i4);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (isSameHolderCurrentlyInUse(surfaceHolder) && surfaceHolder.getSurface().isValid()) {
                    TRTCCloudImpl.this.apiLog("local view surfaceCreated, surface: %s, holder: %s", surfaceHolder.getSurface(), surfaceHolder);
                    TRTCCloudImpl.this.mCaptureAndEnc.a(surfaceHolder.getSurface());
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (isSameHolderCurrentlyInUse(surfaceHolder)) {
                    TRTCCloudImpl.this.apiLog("local view surfaceDestroyed, surface: %s, holder: %s", surfaceHolder.getSurface(), surfaceHolder);
                    TRTCCloudImpl.this.mCaptureAndEnc.a((Surface) null);
                }
            }
        };
        this.mKeepAVCaptureWhenEnterRoomFailed = false;
        this.mCallback = new com.tencent.liteav.basic.b.a() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.2
            @Override // com.tencent.liteav.basic.b.a
            public void onError(String str, int i2, String str2, String str3) {
                if (TRTCCloudImpl.this.mTRTCListener != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("EVT_USERID", str);
                    bundle.putInt("EVT_ID", i2);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (str2 != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(str3 != null ? str3 : "");
                        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, sb.toString());
                    }
                    TRTCCloudImpl.this.onNotifyEvent(i2, bundle);
                }
                Monitor.a(3, i2, "onError => msg:" + str2 + " params:" + str3 + " code:" + i2 + " id:" + str, "", 0, 0);
                if (i2 == -1302 || i2 == -1317 || i2 == -1318 || i2 == -1319) {
                    TXCKeyPointReportProxy.b(30002, i2);
                    if (i2 == -1317) {
                        TXCEventRecorderProxy.a("18446744073709551615", 2002, 4L, -1L, "", 0);
                    }
                }
            }

            @Override // com.tencent.liteav.basic.b.a
            public void onEvent(String str, int i2, String str2, String str3) {
                onEventInternal(str, i2, str2, str3);
                Monitor.a(2, i2, "onEvent => msg:" + str2 + " params:" + str3 + " code:" + i2 + " id:" + str, "", 0, 0);
                if (i2 == 2027) {
                    TXCKeyPointReportProxy.b(30002, 0);
                }
            }

            public void onEventInternal(String str, int i2, String str2, String str3) {
                if (TRTCCloudImpl.this.mTRTCListener != null) {
                    switch (i2) {
                        case 10046:
                        case 10047:
                        case 10050:
                        case 10052:
                        case 10054:
                            i2 = 1204;
                            break;
                        case 10048:
                        case 10049:
                        case 10053:
                            i2 = 1205;
                            break;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("EVT_USERID", str);
                    bundle.putInt("EVT_ID", i2);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (str2 != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        if (str3 == null) {
                            str3 = "";
                        }
                        sb.append(str3);
                        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, sb.toString());
                    }
                    TRTCCloudImpl.this.onNotifyEvent(i2, bundle);
                }
            }

            @Override // com.tencent.liteav.basic.b.a
            public void onWarning(String str, int i2, String str2, String str3) {
                onEventInternal(str, i2, str2, str3);
                Monitor.a(4, i2, "onWarning => msg:" + str2 + " params:" + str3 + " code:" + i2 + " id:" + str, "", 0, 0);
            }
        };
        this.mDeviceManagerListener = new TXDeviceManagerImpl.TXDeviceManagerListener() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.3
            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onCameraParamChange(com.tencent.liteav.g gVar) {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                com.tencent.liteav.g gVar2 = tRTCCloudImpl.mConfig;
                gVar2.f19329c = gVar.f19329c;
                gVar2.f19330d = gVar.f19330d;
                if (tRTCCloudImpl.mPerformanceMode != 1) {
                    gVar2.X = gVar.X;
                }
                gVar2.W = gVar.W;
                tRTCCloudImpl.mCaptureAndEnc.a(gVar2);
            }

            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onSwitchAutoFocus(boolean z2) {
                TRTCCloudImpl.this.mConfig.N = !z2;
            }

            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onSwitchCamera(boolean z2) {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.mConfig.f19342p = z2;
                tRTCCloudImpl.updateOrientation();
            }

            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onSwitchSystemVolumeType(TXDeviceManager.TXSystemVolumeType tXSystemVolumeType) {
                int i2 = AnonymousClass208.$SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType[tXSystemVolumeType.ordinal()];
                if (i2 == 1) {
                    TRTCCloudImpl.this.mConfig.A = 0;
                } else if (i2 == 2) {
                    TRTCCloudImpl.this.mConfig.A = 2;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    TRTCCloudImpl.this.mConfig.A = 1;
                }
            }
        };
        this.mSubClouds = new ArrayList<>();
        this.mCurrentPublishClouds = new HashMap<>();
        this.mVolumeLevelNotifyTask = null;
        this.mDebugType = 0;
        this.mStatusNotifyTask = null;
        this.mNetType = -1;
        this.mBackground = -1;
        init(context, null);
        TXCCommonUtil.setAppContext(this.mContext);
        TXCLog.init();
        AudioServerConfig audioServerConfigLoadFromSharedPreferences = AudioServerConfig.loadFromSharedPreferences(context);
        TXCLog.i(TAG, "audio config from shared preference: %s", audioServerConfigLoadFromSharedPreferences);
        TXCAudioEngine.CreateInstanceWithoutInitDevice(this.mContext, TXCAudioEngine.buildTRAEConfig(context, Boolean.valueOf(audioServerConfigLoadFromSharedPreferences.enableOpenSL), audioServerConfigLoadFromSharedPreferences.isLowLatencySampleRateSupported, audioServerConfigLoadFromSharedPreferences.lowLatencySampleRateBlockTime), audioServerConfigLoadFromSharedPreferences.isAudioDeviceDSPEnabled());
        TXCAudioEngine.getInstance().clean();
        TXCAudioEngine.getInstance().setAudioCaptureDataListener(this);
        TXCAudioEngine.getInstance().addEventCallback(new WeakReference<>(this.mCallback));
        TXCAudioEngine.getInstance().enableAutoRestartDevice(audioServerConfigLoadFromSharedPreferences.enableAutoRestartDevice);
        TXCAudioEngine.getInstance().setMaxSelectedPlayStreams(audioServerConfigLoadFromSharedPreferences.maxSelectedPlayStreams);
        TXCAudioEngine.getInstance().enableInbandFEC(audioServerConfigLoadFromSharedPreferences.enableInbandFEC != 0);
        TXCAudioEngine.getInstance().enableDeviceAbnormalDetection(audioServerConfigLoadFromSharedPreferences.enableDeviceAbnormalDetection != 0);
        TXCAudioEngineJNI.nativeSetAudioPlayoutTunnelEnabled(true);
        TXCAudioEngine.getInstance().setAudioQuality(audioServerConfigLoadFromSharedPreferences.audioSampleRate, audioServerConfigLoadFromSharedPreferences.audioChannel, audioServerConfigLoadFromSharedPreferences.audioBitrate, audioServerConfigLoadFromSharedPreferences.encodeMode, audioServerConfigLoadFromSharedPreferences.systemVolumeType, 5);
        com.tencent.liteav.d dVarCreateCaptureAndEnc = createCaptureAndEnc(2, this.mConfig);
        this.mCaptureAndEnc = dVarCreateCaptureAndEnc;
        dVarCreateCaptureAndEnc.d();
        this.mDeviceManager.setCaptureAndEnc(this.mCaptureAndEnc);
        this.mDeviceManager.setDeviceManagerListener(this.mDeviceManagerListener);
        TXCKeyPointReportProxy.a(this.mContext);
        apiLog("reset audio volume");
        setAudioCaptureVolume(100);
        setAudioPlayoutVolume(100);
        TXCSoundEffectPlayer.getInstance().setSoundEffectListener(this);
        this.mH265Decision = new TRTCEncodeTypeDecision(this);
    }

    private int GetPublishingCloudState(int i2) {
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(Integer.valueOf(i2));
        if (tRTCCloudImpl != null) {
            return tRTCCloudImpl.mRoomState;
        }
        return 0;
    }

    public static /* synthetic */ long access$11108(TRTCCloudImpl tRTCCloudImpl) {
        long j2 = tRTCCloudImpl.mRecvSEIMsgCountInPeriod;
        tRTCCloudImpl.mRecvSEIMsgCountInPeriod = 1 + j2;
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRemoteStatistics(TXCRenderAndDec tXCRenderAndDec, TRTCRoomInfo.UserInfo userInfo, TRTCStatistics tRTCStatistics, ArrayList<TRTCCloudDef.TRTCQuality> arrayList) {
        TRTCStatistics.TRTCRemoteStatistics remoteStatistics = getRemoteStatistics(tXCRenderAndDec, userInfo);
        tRTCStatistics.remoteArray.add(remoteStatistics);
        tRTCStatistics.downLoss = TXCStatus.c(String.valueOf(userInfo.tinyID), R2.id.layout_main);
        TRTCCloudDef.TRTCQuality tRTCQuality = new TRTCCloudDef.TRTCQuality();
        tRTCQuality.userId = userInfo.userID;
        tRTCQuality.quality = getNetworkQuality(tRTCStatistics.rtt, remoteStatistics.finalLoss);
        arrayList.add(tRTCQuality);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addUpStreamType(int i2) {
        if (i2 == 7 && this.mSubStreamVideoSourceType == VideoSourceType.NONE) {
            TXCLog.i("addUpstream", "return, add sub upstream when not start");
            return;
        }
        if (!this.mStreamTypes.contains(Integer.valueOf(i2))) {
            this.mStreamTypes.add(Integer.valueOf(i2));
        }
        addUpstream(i2);
    }

    private void addUpstream(int i2) {
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(Integer.valueOf(i2));
        if (tRTCCloudImpl != null) {
            nativeAddUpstream(tRTCCloudImpl.getNetworkContext(), i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendDashboardLog(String str, int i2, String str2) {
        appendDashboardLog(str, i2, str2, "");
    }

    private void applyRenderConfig(TXCRenderAndDec tXCRenderAndDec) {
        com.tencent.liteav.h hVar = new com.tencent.liteav.h();
        hVar.f19363h = false;
        if (this.mAppScene == 1) {
            hVar.f19363h = true;
        }
        int iC = TXCStatus.c("18446744073709551615", 17020);
        if (iC == 0) {
            iC = 600;
        }
        hVar.f19359d = iC;
        hVar.f19375t = this.mRoomInfo.decProperties;
        applyRenderPlayStrategy(tXCRenderAndDec, hVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyRenderPlayStrategy(TXCRenderAndDec tXCRenderAndDec, com.tencent.liteav.h hVar) {
        hVar.f19362g = true;
        int i2 = this.mCurrentRole;
        if (i2 == 20) {
            hVar.f19356a = com.tencent.liteav.basic.enums.a.f18401a;
            hVar.f19358c = com.tencent.liteav.basic.enums.a.f18402b;
            hVar.f19357b = com.tencent.liteav.basic.enums.a.f18403c;
        } else if (i2 == 21) {
            hVar.f19356a = com.tencent.liteav.basic.enums.a.f18404d;
            hVar.f19358c = com.tencent.liteav.basic.enums.a.f18405e;
            hVar.f19357b = com.tencent.liteav.basic.enums.a.f18406f;
        }
        tXCRenderAndDec.setConfig(hVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkRTCState() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis < this.mLastStateTimeMs + ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
            return;
        }
        this.mLastStateTimeMs = jCurrentTimeMillis;
        int[] iArrA = com.tencent.liteav.basic.util.h.a();
        final ArrayList arrayList = new ArrayList();
        final TRTCStatistics tRTCStatistics = new TRTCStatistics();
        tRTCStatistics.appCpu = iArrA[0] / 10;
        tRTCStatistics.systemCpu = iArrA[1] / 10;
        tRTCStatistics.rtt = TXCStatus.c("18446744073709551615", R2.drawable.quan);
        tRTCStatistics.gatewayRtt = TXCStatus.c("18446744073709551615", R2.drawable.question_comment_flag_icon_two_night2);
        tRTCStatistics.sendBytes = TXCStatus.a("18446744073709551615", R2.drawable.question_comment_flag_icon);
        tRTCStatistics.receiveBytes = TXCStatus.a("18446744073709551615", R2.id.layout_nine_grid);
        tRTCStatistics.upLoss = TXCStatus.c("18446744073709551615", R2.drawable.question_comment_flag);
        tRTCStatistics.localArray = new ArrayList<>();
        tRTCStatistics.remoteArray = new ArrayList<>();
        tRTCStatistics.localArray.add(getLocalStatistics(2));
        if (this.mEnableSmallStream) {
            tRTCStatistics.localArray.add(getLocalStatistics(3));
        }
        this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.201
            @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
            public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                if (tXCRenderAndDec != null) {
                    TRTCCloudImpl.this.addRemoteStatistics(tXCRenderAndDec, userInfo, tRTCStatistics, arrayList);
                }
                TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                if (tXCRenderAndDec2 == null || !tXCRenderAndDec2.isRendering()) {
                    return;
                }
                TRTCCloudImpl.this.addRemoteStatistics(userInfo.subRender.render, userInfo, tRTCStatistics, arrayList);
            }
        });
        final TRTCCloudDef.TRTCQuality tRTCQuality = new TRTCCloudDef.TRTCQuality();
        tRTCQuality.userId = this.mRoomInfo.getUserId();
        tRTCQuality.quality = TXCStatus.c("18446744073709551615", R2.drawable.question_comment_flag_icon_night);
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.202
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onStatistics(tRTCStatistics);
                    tRTCCloudListener.onNetworkQuality(tRTCQuality, arrayList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkRenderRotation(int i2) {
        int displayRotation = getDisplayRotation();
        com.tencent.liteav.g gVar = this.mConfig;
        int i3 = gVar.f19341o;
        int i4 = ((360 - displayRotation) - ((i3 - 1) * 90)) % 360;
        int i5 = i2 % 2;
        int i6 = displayRotation % 2;
        boolean z2 = (i5 == i6 && i3 == 1) || (i5 != i6 && i3 == 0);
        int i7 = this.mVideoRenderMirror;
        if (i7 != 1 ? !(i7 != 2 || !gVar.f19342p || !z2) : !(gVar.f19342p || !z2)) {
            i4 += 180;
        }
        TXCLog.d(TAG, String.format("vrotation rotation-change %d-%d-%d ======= renderRotation %d-%d", Integer.valueOf(i2), Integer.valueOf(this.mConfig.f19341o), Integer.valueOf(displayRotation), Integer.valueOf(i4), Integer.valueOf(this.mRoomInfo.localRenderRotation)) + " self:" + hashCode());
        this.mCaptureAndEnc.h((this.mRoomInfo.localRenderRotation + i4) % 360);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkVideoEncRotation(int i2) {
        int i3;
        int i4;
        com.tencent.liteav.g gVar = this.mConfig;
        if (gVar.f19341o != 1) {
            boolean z2 = gVar.V;
            i3 = (!(z2 && gVar.f19342p) && (z2 || gVar.f19342p)) ? 270 : 90;
        } else {
            i3 = 0;
        }
        if (i2 == 0) {
            i4 = (i3 + 90) % 360;
            if (!gVar.f19342p) {
                i4 = (i4 + 180) % 360;
            }
            if (gVar.V) {
                i4 = (i4 + 180) % 360;
            }
        } else if (i2 == 1) {
            i4 = (i3 + 0) % 360;
        } else if (i2 != 2) {
            i4 = i2 != 3 ? 0 : (i3 + 180) % 360;
        } else {
            i4 = (i3 + 270) % 360;
            if (!gVar.f19342p) {
                i4 = (i4 + 180) % 360;
            }
            if (gVar.V) {
                i4 = (i4 + 180) % 360;
            }
        }
        TXCLog.d(TAG, String.format("vrotation rotation-change %d-%d ======= encRotation %d", Integer.valueOf(i2), Integer.valueOf(this.mConfig.f19341o), Integer.valueOf(i4)) + " self:" + hashCode());
        this.mCurrentOrientation = i2;
        this.mCaptureAndEnc.a(i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectCustomCaptureFps() {
        TRTCCustomTextureUtil tRTCCustomTextureUtil = this.mCustomVideoUtil;
        if (tRTCCustomTextureUtil != null) {
            TXCStatus.a("18446744073709551615", 1001, 2, Double.valueOf(tRTCCustomTextureUtil.getCurrentFPS()));
        }
        TRTCCustomTextureUtil tRTCCustomTextureUtil2 = this.mCustomSubStreamVideoUtil;
        if (tRTCCustomTextureUtil2 != null) {
            TXCStatus.a("18446744073709551615", 1001, 7, Double.valueOf(tRTCCustomTextureUtil2.getCurrentFPS()));
        }
    }

    private static AudioServerConfig createAudioServerConfigFromNative() {
        return new AudioServerConfig();
    }

    private com.tencent.liteav.d createCaptureAndEnc(int i2, com.tencent.liteav.g gVar) {
        com.tencent.liteav.d dVar = new com.tencent.liteav.d(this.mContext);
        dVar.j(i2);
        dVar.a(gVar);
        dVar.i(true);
        dVar.g(isRPSSupported());
        dVar.a((com.tencent.liteav.basic.b.b) this);
        dVar.a((d.a) this);
        dVar.setID("18446744073709551615");
        dVar.h(true);
        return dVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TXCRenderAndDec createRender(long j2, int i2) {
        TXCRenderAndDec tXCRenderAndDec = new TXCRenderAndDec(this.mContext);
        tXCRenderAndDec.setID(String.valueOf(j2));
        com.tencent.liteav.renderer.a aVar = new com.tencent.liteav.renderer.a();
        aVar.b(true);
        tXCRenderAndDec.setVideoRender(aVar);
        tXCRenderAndDec.setStreamType(i2);
        tXCRenderAndDec.setNotifyListener(this);
        tXCRenderAndDec.setRenderAndDecDelegate(this);
        tXCRenderAndDec.setRenderMode(0);
        tXCRenderAndDec.enableDecoderChange(this.mPerformanceMode != 1);
        tXCRenderAndDec.enableRestartDecoder(this.mRoomInfo.enableRestartDecoder);
        tXCRenderAndDec.enableLimitDecCache(this.mVideoServerConfig.enableHWVUI);
        applyRenderConfig(tXCRenderAndDec);
        return tXCRenderAndDec;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TRTCRoomInfo.UserInfo createUserInfo(String str) {
        TRTCRoomInfo.UserInfo userInfo = new TRTCRoomInfo.UserInfo(0L, str, 0, 0);
        TRTCRoomInfo.RenderInfo renderInfo = userInfo.mainRender;
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState = tRTCRoomInfo.muteRemoteVideo;
        renderInfo.muteVideo = tRTCRemoteMuteState;
        renderInfo.muteAudio = tRTCRoomInfo.muteRemoteAudio;
        userInfo.subRender.muteVideo = tRTCRemoteMuteState;
        return userInfo;
    }

    private static TRTCVideoServerConfig createVideoServerConfigFromNative() {
        return new TRTCVideoServerConfig();
    }

    public static void destroySharedInstance() {
        synchronized (TRTCCloudImpl.class) {
            if (sInstance != null) {
                TXCLog.i(TAG, "trtc_api destroy instance self:" + sInstance.hashCode());
                sInstance.destroy();
                sInstance = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectOtherRoom(String str) {
        apiOnlineLog("DisconnectOtherRoom");
        nativeDisconnectOtherRoom(this.mNativeRtcContext, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableAudioAEC(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("enable")) {
            apiLog("enableAudioAEC[lack parameter or illegal type]: enable");
            return;
        }
        if (jSONObject.getInt("enable") == 0) {
            this.mEnableSoftAEC = false;
        } else {
            this.mEnableSoftAEC = true;
        }
        if (jSONObject.has(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL)) {
            this.mSoftAECLevel = jSONObject.getInt(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL);
        } else {
            this.mSoftAECLevel = 100;
        }
        TXCAudioEngine.getInstance().enableSoftAEC(this.mEnableSoftAEC, this.mSoftAECLevel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableAudioAGC(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("enable")) {
            apiLog("enableAudioAGC[lack parameter or illegal type]: enable");
            return;
        }
        if (jSONObject.getInt("enable") == 0) {
            this.mEnableSoftAGC = false;
        } else {
            this.mEnableSoftAGC = true;
        }
        if (jSONObject.has(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL)) {
            this.mSoftAGCLevel = jSONObject.getInt(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL);
        } else {
            this.mSoftAGCLevel = 100;
        }
        TXCAudioEngine.getInstance().enableSoftAGC(this.mEnableSoftAGC, this.mSoftAGCLevel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableAudioANS(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("enable")) {
            apiLog("enableAudioANS[lack parameter or illegal type]: enable");
            return;
        }
        if (jSONObject.getInt("enable") == 0) {
            this.mEnableSoftANS = false;
        } else {
            this.mEnableSoftANS = true;
        }
        if (jSONObject.has(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL)) {
            this.mSoftANSLevel = jSONObject.getInt(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL);
        } else {
            this.mSoftANSLevel = 100;
        }
        TXCAudioEngine.getInstance().enableSoftANS(this.mEnableSoftANS, this.mSoftANSLevel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableBlackStream(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("enable")) {
            apiLog("callExperimentalAPI[lack parameter or illegal type]: enable");
            return;
        }
        boolean z2 = jSONObject.getBoolean("enable");
        apiLog("enableBlackStream " + z2);
        enableNetworkBlackStream(z2);
        com.tencent.liteav.d dVar = this.mCaptureAndEnc;
        if (dVar != null) {
            dVar.c(z2);
        }
        if (z2) {
            addUpstream(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableMainStreamCustomCapture(boolean z2) {
        if (z2 && this.mMainStreamVideoSourceType != VideoSourceType.NONE) {
            notifyCaptureStarted("Has started capturing, ignore enableCustomVideoCapture");
            apiLog("Has started capturing, ignore enableCustomVideoCapture");
            return;
        }
        if (!z2 && this.mMainStreamVideoSourceType != VideoSourceType.CUSTOM) {
            apiLog("Has not started capturing, ignore disableCustomVideoCapture");
            return;
        }
        setStartVideoEncodeCodec();
        this.mMainStreamVideoSourceType = z2 ? VideoSourceType.CUSTOM : VideoSourceType.NONE;
        if (z2) {
            this.mConfig.U |= 2;
            if (this.mCurrentRole == 21) {
                runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.85
                    @Override // java.lang.Runnable
                    public void run() {
                        TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                        if (tRTCCloudListener == null) {
                            return;
                        }
                        tRTCCloudListener.onWarning(6001, "ignore send custom video,for role audience", null);
                    }
                });
                apiLog("ignore enableCustomVideoCapture,for role audience");
            }
        } else {
            this.mConfig.U &= -3;
            synchronized (this.mCustomCaptureLock) {
                TRTCCustomTextureUtil tRTCCustomTextureUtil = this.mCustomVideoUtil;
                if (tRTCCustomTextureUtil != null) {
                    tRTCCustomTextureUtil.release();
                    this.mCustomVideoUtil = null;
                }
            }
        }
        this.mCaptureAndEnc.a(this.mConfig);
        this.mEnableCustomVideoCapture = z2;
        apiOnlineLog("enableMainStreamCustomCapture " + z2);
        if (z2) {
            enableVideoStream(0, true);
        } else if (!this.mIsVideoCapturing) {
            enableVideoStream(0, false);
        }
        TXCKeyPointReportProxy.a(40046, z2 ? 1 : 0, 2);
    }

    private void enableRealtimeChorus(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("enableRealtimeChorus param is null");
        } else {
            if (!jSONObject.has("enable")) {
                apiLog("enableRealtimeChorus[lack parameter]: enable");
                return;
            }
            int iOptInt = jSONObject.optInt("enable", 0);
            this.mEnableLowLatencyMode = iOptInt == 1;
            nativeEnableLowLatencyMode(this.mNativeRtcContext, iOptInt == 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableSubStreamCustomCapture(boolean z2) {
        if (z2 && this.mSubStreamVideoSourceType != VideoSourceType.NONE) {
            notifyCaptureStarted("Has started capturing, ignore enableCustomVideoCapture");
            apiLog("Has started capturing, ignore enableCustomVideoCapture");
            return;
        }
        if (!z2 && this.mSubStreamVideoSourceType != VideoSourceType.CUSTOM) {
            apiLog("Has not started capturing, ignore disableCustomVideoCapture");
            return;
        }
        this.mSubStreamVideoSourceType = z2 ? VideoSourceType.CUSTOM : VideoSourceType.NONE;
        if (!z2) {
            synchronized (this.mCustomCaptureLock) {
                TRTCCustomTextureUtil tRTCCustomTextureUtil = this.mCustomSubStreamVideoUtil;
                if (tRTCCustomTextureUtil != null) {
                    tRTCCustomTextureUtil.release();
                    this.mCustomSubStreamVideoUtil = null;
                }
            }
        } else if (this.mCurrentRole == 21) {
            runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.86
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                    if (tRTCCloudListener == null) {
                        return;
                    }
                    tRTCCloudListener.onWarning(6001, "ignore send custom video,for role audience", null);
                }
            });
            apiLog("ignore enableCustomVideoCapture,for role audience");
        }
        apiOnlineLog("enableSubStreamCustomCapture enable:%b", Boolean.valueOf(z2));
        if (z2) {
            addUpStreamType(7);
        } else {
            removeUpStreamType(7);
        }
        TXCKeyPointReportProxy.a(40046, z2 ? 1 : 0, 7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceCallbackMixedPlayAudioFrame(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("forceCallbackMixedPlayAudioFrame param is null");
        } else if (!jSONObject.has("enable")) {
            apiLog("forceCallbackMixedPlayAudioFrame[lack parameter]: enable");
        } else {
            TXCAudioEngine.getInstance().forceCallbackMixedPlayAudioFrame(jSONObject.optInt("enable", 0) != 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCompatibleRotation(int i2) {
        if (i2 < 0) {
            return 0;
        }
        return i2 > 3 ? (i2 / 90) * 90 : i2 * 90;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDisplayRotation() {
        int iG = com.tencent.liteav.basic.util.h.g(this.mContext);
        if (iG == 0) {
            return 0;
        }
        if (iG == 1) {
            return 90;
        }
        if (iG != 2) {
            return iG != 3 ? 0 : 270;
        }
        return 180;
    }

    private CharSequence getDownloadStreamInfo(TXCRenderAndDec tXCRenderAndDec, TRTCRoomInfo.UserInfo userInfo) {
        String strValueOf = String.valueOf(userInfo.tinyID);
        int[] iArrA = com.tencent.liteav.basic.util.h.a();
        int streamType = tXCRenderAndDec.getStreamType();
        long jA = TXCStatus.a(strValueOf, 17014, streamType);
        int iC = TXCStatus.c(strValueOf, 5003, streamType);
        String str = String.format("REMOTE: [%s]%s RTT:%dms\n", userInfo.userID, streamType == 3 ? ExifInterface.LATITUDE_SOUTH : streamType == 7 ? "Sub" : streamType == 1 ? ExifInterface.GPS_MEASUREMENT_IN_PROGRESS : "B", Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.quan))) + String.format(Locale.CHINA, "RECV:%dkbps LOSS:%d-%d-%d%%|%d-%d-%d%%|%d%%\n", Integer.valueOf(TXCStatus.c(strValueOf, R2.id.msg1ScrollImgv, streamType) + TXCStatus.c(strValueOf, R2.id.right_image)), Integer.valueOf(TXCStatus.c(strValueOf, 17010, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, 17005, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, 17011, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.ring)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rightcheck)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rl)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.layout_main))) + String.format(Locale.CHINA, "BIT:%d|%dkbps RES:%dx%d FPS:%d-%d\n", Integer.valueOf(TXCStatus.c(strValueOf, 17002, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, 18002)), Integer.valueOf(iC >> 16), Integer.valueOf(iC & 65535), Integer.valueOf((int) TXCStatus.d(strValueOf, 6002, streamType)), Integer.valueOf((int) TXCStatus.d(strValueOf, R2.id.msgCl, streamType))) + String.format(Locale.CHINA, "FEC:%d-%d-%d%%|%d-%d-%d%%    ARQ:%d-%d|%d-%d\n", Integer.valueOf(TXCStatus.c(strValueOf, 17007, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, 17005, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, 17006, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rightlayout)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rightcheck)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rightimg)), Integer.valueOf(TXCStatus.c(strValueOf, 17009, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, 17008, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rimg)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rightswitch))) + String.format(Locale.CHINA, "CPU:%d%%|%d%%  RPS:%d  LFR:%d  DERR:%d\n", Integer.valueOf(iArrA[0] / 10), Integer.valueOf(iArrA[1] / 10), Integer.valueOf(TXCStatus.c(strValueOf, 17012, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.msgScrollV, streamType)), Long.valueOf(jA)) + String.format(Locale.CHINA, "Jitter: %d,%d|%d,%d|%d  p2pDelay: %d  ADROP: %d\n", Integer.valueOf(TXCStatus.c(strValueOf, 2007)), Integer.valueOf(TXCStatus.c(strValueOf, R2.dimen.alivc_common_font_32, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, R2.dimen.alivc_common_font_7, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, R2.dimen.alivc_common_font_8, streamType)), Integer.valueOf(TXCStatus.c(strValueOf, 2021)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rl_circle_topic_list_search)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rlFriendNotice))) + String.format(Locale.CHINA, "QUALITY: %d   LEN: %d\n", Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rl_analyze_user)), Integer.valueOf(TXCStatus.c(strValueOf, R2.id.rlRichpushTitleBar)));
        SpannableString spannableString = new SpannableString(str);
        int iLastIndexOf = str.lastIndexOf("DECERR:");
        if (-1 != iLastIndexOf && jA > 0) {
            spannableString.setSpan(new ForegroundColorSpan(SupportMenu.CATEGORY_MASK), iLastIndexOf + 7, str.length(), 33);
        }
        return spannableString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<Integer, String> getEncoderTypeAndMsg(Bundle bundle) {
        String str;
        int i2 = 0;
        int i3 = bundle.getInt("EVT_PARAM1", 0);
        if (i3 == c.a.HW_ENCODER_H264.a() || i3 == c.a.SW_ENCODER_H264.a()) {
            str = String.format(Locale.getDefault(), "Current encode type is %s encoder", j.a.f27383l);
        } else if (i3 == c.a.HW_ENCODER_H265.a() || i3 == c.a.SW_ENCODER_H265.a()) {
            str = String.format(Locale.getDefault(), "Current encode type is %s encoder", "H265");
            i2 = 1;
        } else {
            str = "";
            i2 = i3;
        }
        return new Pair<>(Integer.valueOf(i2), str);
    }

    private TRTCStatistics.TRTCLocalStatistics getLocalStatistics(int i2) {
        int iC = TXCStatus.c("18446744073709551615", 4003, i2);
        TRTCStatistics.TRTCLocalStatistics tRTCLocalStatistics = new TRTCStatistics.TRTCLocalStatistics();
        tRTCLocalStatistics.width = iC >> 16;
        tRTCLocalStatistics.height = iC & 65535;
        tRTCLocalStatistics.frameRate = (int) (TXCStatus.d("18446744073709551615", 4001, i2) + 0.5d);
        tRTCLocalStatistics.videoBitrate = TXCStatus.c("18446744073709551615", R2.drawable.umeng_socialize_button_login, i2);
        tRTCLocalStatistics.audioSampleRate = TXCStatus.c("18446744073709551615", 14003);
        tRTCLocalStatistics.audioBitrate = TXCStatus.c("18446744073709551615", 14002);
        tRTCLocalStatistics.streamType = translateStreamType(i2);
        tRTCLocalStatistics.audioCaptureState = translateAudioAbnormalDetectState(TXCStatus.c("18446744073709551615", 14032));
        return tRTCLocalStatistics;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.tencent.liteav.basic.enums.b getPixelFormat(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? com.tencent.liteav.basic.enums.b.UNKNOWN : com.tencent.liteav.basic.enums.b.RGBA : com.tencent.liteav.basic.enums.b.NV21 : com.tencent.liteav.basic.enums.b.TEXTURE_EXTERNAL_OES : com.tencent.liteav.basic.enums.b.TEXTURE_2D : com.tencent.liteav.basic.enums.b.I420;
    }

    private String getQosValue(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? "ERR" : "DOWN" : "UP" : "HOLD";
    }

    private TRTCStatistics.TRTCRemoteStatistics getRemoteStatistics(TXCRenderAndDec tXCRenderAndDec, TRTCRoomInfo.UserInfo userInfo) {
        String strValueOf = String.valueOf(userInfo.tinyID);
        int streamType = tXCRenderAndDec.getStreamType();
        int iC = TXCStatus.c(strValueOf, 5003, streamType);
        int iC2 = TXCStatus.c(strValueOf, 17011, streamType);
        int iC3 = TXCStatus.c(strValueOf, R2.id.rl_collect_ziliao);
        TRTCStatistics.TRTCRemoteStatistics tRTCRemoteStatistics = new TRTCStatistics.TRTCRemoteStatistics();
        tRTCRemoteStatistics.userId = userInfo.userID;
        tRTCRemoteStatistics.videoPacketLoss = iC2;
        tRTCRemoteStatistics.audioPacketLoss = iC3;
        if (iC3 > iC2) {
            iC2 = iC3;
        }
        tRTCRemoteStatistics.finalLoss = iC2;
        tRTCRemoteStatistics.width = iC >> 16;
        tRTCRemoteStatistics.height = 65535 & iC;
        tRTCRemoteStatistics.frameRate = (int) (TXCStatus.d(strValueOf, 6002, streamType) + 0.5d);
        tRTCRemoteStatistics.videoBitrate = TXCStatus.c(strValueOf, 17002, streamType);
        tRTCRemoteStatistics.audioSampleRate = TXCStatus.c(strValueOf, R2.id.right_ly);
        tRTCRemoteStatistics.audioBitrate = TXCStatus.c(strValueOf, 18002);
        tRTCRemoteStatistics.jitterBufferDelay = TXCStatus.c(strValueOf, 2007);
        tRTCRemoteStatistics.point2PointDelay = TXCStatus.c(strValueOf, R2.id.rl_circle_topic_list_search);
        tRTCRemoteStatistics.streamType = translateStreamType(streamType);
        tRTCRemoteStatistics.audioTotalBlockTime = TXCStatus.c(strValueOf, R2.id.rl_bg_container);
        int iC4 = TXCStatus.c(strValueOf, R2.id.rl_askbar_layout);
        tRTCRemoteStatistics.audioBlockRate = iC4 > 0 ? (int) (((tRTCRemoteStatistics.audioTotalBlockTime * 100.0d) / iC4) + 0.9d) : 0;
        tRTCRemoteStatistics.videoTotalBlockTime = TXCStatus.c(strValueOf, 6006, streamType);
        int iC5 = TXCStatus.c(strValueOf, R2.dimen.abc_action_bar_stacked_max_height, streamType);
        tRTCRemoteStatistics.videoBlockRate = iC5 > 0 ? (int) (((tRTCRemoteStatistics.videoTotalBlockTime * 100.0d) / iC5) + 0.9d) : 0;
        return tRTCRemoteStatistics;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TRTCRoomInfo.RenderInfo getRenderInfo(String str, int i2) {
        TRTCRoomInfo.UserInfo user = this.mRoomInfo.getUser(str);
        if (user == null) {
            user = createUserInfo(str);
            this.mRoomInfo.addUserInfo(str, user);
        }
        return (i2 == 0 || i2 == 1) ? user.mainRender : user.subRender;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public g.a getSizeByResolution(int i2, int i3) {
        int i4 = 720;
        int i5 = 960;
        switch (i2) {
            case 1:
                i4 = 128;
                i5 = i4;
                break;
            case 3:
                i4 = 160;
                i5 = i4;
                break;
            case 5:
                i4 = 272;
                i5 = i4;
                break;
            case 7:
                i4 = 480;
                i5 = i4;
                break;
            case 50:
                i5 = 176;
                i4 = 128;
                break;
            case 52:
                i4 = 192;
                i5 = 256;
                break;
            case 54:
                i5 = R2.attr.alignContent;
                i4 = 224;
                break;
            case 56:
                i5 = 320;
                i4 = 240;
                break;
            case 58:
                i5 = 400;
                i4 = 304;
                break;
            case 60:
                i4 = 368;
                i5 = 480;
                break;
            case 62:
                i5 = 640;
                i4 = 480;
                break;
            case 64:
                break;
            case 100:
                i4 = 96;
                i5 = 176;
                break;
            case 102:
                i4 = 144;
                i5 = 256;
                break;
            case 104:
                i5 = R2.attr.app_title_color;
                i4 = 192;
                break;
            case 106:
                i4 = 272;
                i5 = 480;
                break;
            case 108:
            default:
                i5 = 640;
                i4 = 368;
                break;
            case 110:
                i4 = R2.attr.bl_checked_gradient_type;
                break;
            case 112:
                i5 = 1280;
                break;
            case 114:
                i5 = R2.attr.iconTint;
                i4 = R2.attr.columnOrderPreserved;
                break;
        }
        g.a aVar = new g.a();
        if (i3 == 1) {
            aVar.f19354a = i4;
            aVar.f19355b = i5;
        } else {
            aVar.f19354a = i5;
            aVar.f19355b = i4;
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideFloatingWindow() {
        View view = this.mFloatingWindow;
        if (view == null) {
            return;
        }
        ((WindowManager) view.getContext().getSystemService("window")).removeViewImmediate(this.mFloatingWindow);
        this.mFloatingWindow = null;
    }

    private void identifyTRTCFrameworkType() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int i2 = 0;
            while (true) {
                if (i2 >= stackTrace.length) {
                    break;
                }
                String className = stackTrace[i2].getClassName();
                if (className.contains("TUIKitImpl")) {
                    TXCLog.i(TAG, "identifyTRTCFrameworkType callName:" + className);
                    this.mFramework = 6;
                    break;
                }
                if (className.contains("WXTRTCCloud")) {
                    TXCLog.i(TAG, "identifyTRTCFrameworkType callName:" + className);
                    this.mFramework = 3;
                    break;
                }
                if (className.contains("TRTCCloudPlugin")) {
                    TXCLog.i(TAG, "identifyTRTCFrameworkType callName:" + className);
                    this.mFramework = 7;
                    break;
                }
                if (className.contains("TRTCMeetingImpl") || className.contains("TRTCLiveRoomImpl") || className.contains("TRTCAudioCallImpl") || className.contains("TRTCVideoCallImpl") || className.contains("TRTCVoiceRoomImpl") || className.contains("TRTCAVCallImpl") || className.contains("TRTCChatSalonImpl")) {
                    TXCLog.i(TAG, "identifyTRTCFrameworkType callName:" + className);
                    this.mFramework = 5;
                }
                i2++;
            }
            this.mOriginalFramework = this.mFramework;
        } catch (Exception e2) {
            TXCLog.e(TAG, "identifyTRTCFrameworkType catch exception:" + e2.getCause());
        }
    }

    private void init(Context context, com.tencent.liteav.basic.util.f fVar) {
        this.mCurrentPublishClouds.put(2, this);
        this.mCurrentPublishClouds.put(3, this);
        this.mCurrentPublishClouds.put(7, this);
        this.mCurrentPublishClouds.put(1, this);
        this.mContext = context.getApplicationContext();
        com.tencent.liteav.g gVar = new com.tencent.liteav.g();
        this.mConfig = gVar;
        gVar.f19340n = com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_640_360;
        gVar.aa = 90;
        gVar.f19339m = 1;
        gVar.S = true;
        gVar.f19337k = 15;
        gVar.N = false;
        gVar.W = false;
        gVar.X = false;
        gVar.f19327a = R2.attr.arcStrokeCap;
        gVar.f19328b = 640;
        gVar.f19331e = R2.attr.bl_unPressed_gradient_centerY;
        gVar.f19333g = 0;
        gVar.Z = false;
        TRTCRoomInfo tRTCRoomInfo = new TRTCRoomInfo();
        this.mRoomInfo = tRTCRoomInfo;
        g.a aVar = tRTCRoomInfo.bigEncSize;
        aVar.f19354a = R2.attr.arcStrokeCap;
        aVar.f19355b = 640;
        this.mMainHandler = new com.tencent.liteav.basic.util.f(context.getMainLooper());
        this.mListenerHandler = new Handler(context.getMainLooper());
        this.mIsSDKThreadAlive = new AtomicBoolean(true);
        if (fVar != null) {
            this.mSDKHandler = fVar;
        } else {
            HandlerThread handlerThread = new HandlerThread("TRTCCloudApi");
            handlerThread.start();
            this.mSDKHandler = new com.tencent.liteav.basic.util.f(handlerThread.getLooper());
        }
        this.mDeviceManager = new TXDeviceManagerImpl(this.mSDKHandler);
        this.mStatusNotifyTask = new StatusTask(this);
        this.mLastSendMsgTimeMs = 0L;
        this.mSendMsgCount = 0;
        this.mSendMsgSize = 0;
        this.mSensorMode = 2;
        this.mAppScene = 0;
        this.mQosPreference = 2;
        this.mQosMode = 1;
        this.mOrientationEventListener = new DisplayOrientationDetector(this.mContext, this);
        this.mRenderListenerMap = new HashMap<>();
        this.mStreamTypes = new HashSet();
        synchronized (this.mNativeLock) {
            int[] sDKVersion = TXCCommonUtil.getSDKVersion();
            this.mNativeRtcContext = nativeCreateContext(sDKVersion.length >= 1 ? sDKVersion[0] : 0, sDKVersion.length >= 2 ? sDKVersion[1] : 0, sDKVersion.length >= 3 ? sDKVersion[2] : 0);
        }
        apiLog("trtc cloud create");
        this.mRoomState = 0;
        VideoSourceType videoSourceType = VideoSourceType.NONE;
        this.mMainStreamVideoSourceType = videoSourceType;
        this.mSubStreamVideoSourceType = videoSourceType;
        this.mIsAudioCapturing = false;
        this.mIsVideoCapturing = false;
        this.mCurrentRole = 20;
        this.mTargetRole = 20;
        this.mRecvMode = 1;
        this.mLatestParamsOfBigEncoder.putInt(KEY_CONFIG_GOP, this.mConfig.f19338l);
        this.mLatestParamsOfSmallEncoder.putInt(KEY_CONFIG_GOP, this.mConfig.f19338l);
        com.tencent.liteav.g gVar2 = new com.tencent.liteav.g();
        this.mSubStreamConfig = gVar2;
        gVar2.W = false;
        this.mLatestParamsOfSubEncoder.putInt(KEY_CONFIG_GOP, gVar2.f19338l);
        identifyTRTCFrameworkType();
        this.mVideoServerConfig = TRTCVideoServerConfig.loadFromSharedPreferences(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNumericRoom(int i2) {
        return (i2 == 0 || i2 == -1) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isRPSSupported() {
        return TXCSWVideoEncoder.isRPSSupported();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeSureSubStreamCaptureCreated() {
        synchronized (this.mCustomCaptureLock) {
            if (this.mSubStreamCaptureAndEnc == null) {
                com.tencent.liteav.d dVarCreateCaptureAndEnc = createCaptureAndEnc(7, this.mSubStreamConfig);
                this.mSubStreamCaptureAndEnc = dVarCreateCaptureAndEnc;
                dVarCreateCaptureAndEnc.d();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void muteUpstream(int i2, boolean z2) {
        boolean z3;
        if (i2 == 7 && !z2 && this.mSubStreamVideoSourceType == VideoSourceType.NONE) {
            TXCLog.i("muteUpstream", "return, unmute sub upstream when not start");
            z3 = false;
        } else {
            z3 = true;
        }
        boolean z4 = z3;
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(Integer.valueOf(i2));
        if (tRTCCloudImpl != null) {
            nativeMuteUpstream(tRTCCloudImpl.getNetworkContext(), i2, z2, z4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeCancelDownStream(long j2, long j3, int i2, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeChangeRole(long j2, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeConnectOtherRoom(long j2, String str);

    private native int nativeDisconnectOtherRoom(long j2, String str);

    private native void nativeEnableBlackStream(long j2, boolean z2);

    private native void nativeEnableLowLatencyMode(long j2, boolean z2);

    private native void nativeEnableSmallStream(long j2, boolean z2);

    private native int nativeGetNetworkQUality(long j2, int i2, int i3);

    private native void nativeMuteUpstream(long j2, int i2, boolean z2, boolean z3);

    private native void nativePushVideo(long j2, int i2, int i3, int i4, byte[] bArr, long j3, long j4, long j5, long j6, long j7);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeReenterRoom(long j2, int i2, boolean z2);

    private native int nativeRemoveUpstream(long j2, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeRequestDownStream(long j2, long j3, int i2, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeRequestKeyFrame(long j2, long j3, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSendCustomCmdMsg(long j2, int i2, byte[] bArr, boolean z2, boolean z3);

    private native void nativeSendJsonCmd(long j2, String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSendSEIMsg(long j2, byte[] bArr, int i2);

    private native void nativeSetAECMuteDataEnabled(long j2, boolean z2);

    private native void nativeSetAVSyncPlaySources(long j2, String str, String str2);

    private native void nativeSetAllowSwitchToHighPerformanceMode(long j2, boolean z2);

    private native void nativeSetAudioCacheType(long j2, int i2);

    private native void nativeSetAudioEncodeConfiguration(long j2, int i2, int i3, int i4, int i5);

    private native void nativeSetAudioPacketExtraDataListener(long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeSetDataReportDeviceInfo(String str, String str2, int i2);

    private native void nativeSetEncodedDataProcessingListener(long j2, long j3);

    private native void nativeSetHeartBeatTimeoutSec(long j2, int i2);

    private native void nativeSetLocalAudioMuteMode(long j2, int i2);

    private native void nativeSetNetEnv(long j2, int i2);

    private native boolean nativeSetSEIPayloadType(long j2, int i2);

    private native void nativeSetVideoEncoderConfiguration(long j2, int i2, int i3, int i4, int i5, int i6, int i7, boolean z2, int i8, boolean z3);

    private native void nativeSetVideoQuality(long j2, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStartLocalRecording(long j2, TRTCCloudDef.TRTCLocalRecordingParams tRTCLocalRecordingParams);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStartPublishCDNStream(long j2, TRTCCloudDef.TRTCPublishCDNParam tRTCPublishCDNParam);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStartPublishing(long j2, String str, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeStartSpeedTest(long j2, int i2, String str, String str2, int i3, int i4, int i5);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStopLocalRecording(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStopPublishCDNStream(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStopPublishing(long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public native void nativeStopSpeedTest(long j2);

    private native void nativeUpdatePrivateMapKey(long j2, String str);

    private void notifyCaptureStarted(final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.204
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener == null) {
                    return;
                }
                tRTCCloudListener.onWarning(4000, str, null);
            }
        });
        apiLog(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyEventByUserId(final String str, final int i2, final Bundle bundle) {
        if (str == null || bundle == null) {
            return;
        }
        this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.192
            @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
            public void accept(String str2, TRTCRoomInfo.UserInfo userInfo) {
                if (str.equalsIgnoreCase(String.valueOf(userInfo.tinyID))) {
                    TRTCCloudImpl.this.notifyEvent(userInfo.userID, i2, bundle);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLogByUserId(String str, int i2, int i3, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("EVT_ID", i3);
        bundle.putLong("EVT_TIME", System.currentTimeMillis());
        bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str2);
        bundle.putInt("EVT_STREAM_TYPE", i2);
        notifyEventByUserId(str, i3, bundle);
    }

    private void onAVMemberChange(final long j2, final String str, int i2, final int i3, final int i4) {
        final WeakReference weakReference = new WeakReference(this);
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.157
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                if (tRTCCloudImpl.mRoomState == 0) {
                    tRTCCloudImpl.apiLog("ignore onAVMemberChange when out room");
                    return;
                }
                if (((TRTCCloudImpl) weakReference.get()) == null) {
                    return;
                }
                TRTCCloudImpl.this.apiLog("onAVMemberChange " + j2 + ", " + str + ", old state:" + i4 + ", new state:" + i3);
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user != null) {
                    int i5 = i3;
                    user.streamState = i5;
                    TRTCCloudImpl.this.checkUserState(str, j2, i5, i4);
                }
            }
        });
    }

    private void onAddUpStream(final int i2, final String str, int i3) {
        apiOnlineLog("onAddUpStream  streamType:%d, err:%d, msg:%s", Integer.valueOf(i3), Integer.valueOf(i2), str);
        if (i3 != 7 || i2 == 0) {
            return;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.151
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.stopSubStreamScreenCapture();
                TRTCCloudImpl.this.enableCustomVideoCapture(2, false);
            }
        });
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.152
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onError(i2, str, null);
                }
            }
        });
    }

    private void onCallExperimentalAPI(int i2, String str) {
        apiLog("onCallExperimentalAPI " + i2 + ", " + str);
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.150
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
            }
        });
    }

    private void onChangeRole(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.182
            @Override // java.lang.Runnable
            public void run() {
                int i3 = i2;
                if (i3 == 0) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.mCurrentRole = tRTCCloudImpl.mTargetRole;
                } else {
                    TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                    tRTCCloudImpl2.mCurrentRole = 21;
                    tRTCCloudImpl2.mTargetRole = 21;
                }
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onSwitchRole(i3, str);
                }
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.182.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str2, TRTCRoomInfo.UserInfo userInfo) {
                        TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                        if (tXCRenderAndDec != null) {
                            TRTCCloudImpl.this.applyRenderPlayStrategy(tXCRenderAndDec, tXCRenderAndDec.getConfig());
                        }
                    }
                });
                TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                tRTCCloudImpl3.notifyEvent(tRTCCloudImpl3.mRoomInfo.getUserId(), 0, "onChangeRole:" + i2);
                TRTCCloudImpl.this.apiOnlineLog("onChangeRole err:%d, msg:%s", Integer.valueOf(i2), str);
            }
        });
    }

    private void onConnectOtherRoom(final String str, final int i2, final String str2) {
        apiOnlineLog("onConnectOtherRoom userId:%s err:%d, msg:%s", str, Integer.valueOf(i2), str2);
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.147
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onConnectOtherRoom(str, i2, str2);
                }
            }
        });
    }

    private void onConnectionLost() {
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        tRTCRoomInfo.networkStatus = 1;
        notifyEvent(tRTCRoomInfo.getUserId(), 0, "Network anomaly.");
        apiOnlineLog("onConnectionLost");
        resetFeelingBlockReport();
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.166
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onConnectionLost();
                }
            }
        });
    }

    private void onConnectionRecovery() {
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        tRTCRoomInfo.networkStatus = 3;
        notifyEvent(tRTCRoomInfo.getUserId(), 0, "Network recovered. Successfully re-enter room");
        apiOnlineLog("onConnectionRecovery");
        resetFeelingBlockReport();
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.168
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onConnectionRecovery();
                }
            }
        });
    }

    private void onDisConnectOtherRoom(final int i2, final String str) {
        apiOnlineLog("onDisConnectOtherRoom err:%d, msg:%s", Integer.valueOf(i2), str);
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.148
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onDisConnectOtherRoom(i2, str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExitRoom(int i2, String str) {
        apiOnlineLog("onExitRoom err:" + i2 + ", msg:" + str);
        runOnSDKThread(new AnonymousClass145(i2));
    }

    private void onKickOut(final int i2, final String str) {
        apiLog("onKickOut " + i2 + ", " + str);
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.146
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.exitRoomInternal(false, "onKickOut " + str);
                TRTCCloudImpl.this.onExitRoom(i2, str);
            }
        });
    }

    private void onLocalRecordBegin(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.186
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("onLocalRecordBegin " + i2 + " " + str);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onLocalRecordBegin(i2, str);
                }
            }
        });
    }

    private void onLocalRecordComplete(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.187
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("onLocalRecordComplete " + i2 + " " + str);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onLocalRecordComplete(i2, str);
                }
            }
        });
    }

    private void onLocalRecording(final long j2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.188
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onLocalRecording(j2, str);
                }
            }
        });
    }

    private void onNotify(long j2, int i2, int i3, String str) {
        apiLog(j2 + " event " + i3 + ", " + str);
        String strValueOf = String.valueOf(j2);
        Bundle bundle = new Bundle();
        bundle.putLong("EVT_ID", (long) i3);
        bundle.putLong("EVT_TIME", System.currentTimeMillis());
        bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str);
        bundle.putInt("EVT_STREAM_TYPE", i2);
        if (TextUtils.isEmpty(strValueOf) || j2 == 0 || strValueOf.equalsIgnoreCase("18446744073709551615") || strValueOf.equalsIgnoreCase(this.mRoomInfo.getTinyId())) {
            notifyEvent(this.mRoomInfo.getUserId(), i3, bundle);
        } else {
            notifyLogByUserId(String.valueOf(j2), i2, i3, str);
        }
    }

    private void onRecvAudioServerConfig(AudioServerConfig audioServerConfig) {
        TXCLog.i(TAG, "on receive audio config: [%s]", audioServerConfig);
        AudioServerConfig.saveToSharedPreferences(this.mContext, audioServerConfig);
        TXCAudioEngine.getInstance().enableAutoRestartDevice(audioServerConfig.enableAutoRestartDevice);
        TXCAudioEngine.getInstance().setMaxSelectedPlayStreams(audioServerConfig.maxSelectedPlayStreams);
        TXCAudioEngine.getInstance().enableInbandFEC(audioServerConfig.enableInbandFEC != 0);
        TXCAudioEngine.getInstance().enableDeviceAbnormalDetection(audioServerConfig.enableDeviceAbnormalDetection != 0);
        TXCAudioEngine.getInstance().setAudioQuality(audioServerConfig.audioSampleRate, audioServerConfig.audioChannel, audioServerConfig.audioBitrate, audioServerConfig.encodeMode, audioServerConfig.systemVolumeType, 5);
    }

    private void onRecvCustomCmdMsg(final String str, long j2, final int i2, final int i3, final byte[] bArr, final boolean z2, final int i4, long j3) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mRecvCustomCmdMsgCountInPeriod++;
        if (jCurrentTimeMillis - this.mLastLogCustomCmdMsgTs > com.heytap.mcssdk.constant.a.f7153q) {
            TXCLog.i(TAG, "onRecvMsg. tinyId=" + j2 + ", streamId = " + i2 + ", msg = " + bArr + ", recvTime = " + j3 + ", recvCustomMsgCountInPeriod = " + this.mRecvCustomCmdMsgCountInPeriod + " self:" + hashCode());
            this.mLastLogCustomCmdMsgTs = jCurrentTimeMillis;
            this.mRecvCustomCmdMsgCountInPeriod = 0L;
        }
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.169
            @Override // java.lang.Runnable
            public void run() {
                int i5;
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onRecvCustomCmdMsg(str, i2, i3, bArr);
                    if (!z2 || (i5 = i4) <= 0) {
                        return;
                    }
                    tRTCCloudListener.onMissCustomCmdMsg(str, i2, -1, i5);
                }
            }
        });
    }

    private void onRecvEnterRoomVideoConfig(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.189
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.mH265Decision.setEnableH265EncodeByServer(z2, TRTCEncodeTypeDecision.ModifyCodecReason.REASON_ENTERROOM_RESPOND);
                boolean zIsVideoEncoderCodecUsingH265 = TRTCCloudImpl.this.mH265Decision.isVideoEncoderCodecUsingH265();
                if (TRTCCloudImpl.this.mCaptureAndEnc != null) {
                    TXCLog.i(TRTCCloudImpl.TAG, "codecability onRecvEnterRoomVideoConfig: enabledHevc =" + zIsVideoEncoderCodecUsingH265);
                    TRTCCloudImpl.this.mCaptureAndEnc.j(zIsVideoEncoderCodecUsingH265);
                }
            }
        });
    }

    private void onRecvFirstAudio(long j2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.174
            @Override // java.lang.Runnable
            public void run() {
            }
        });
    }

    private void onRecvFirstVideo(final long j2, final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.175
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = null;
                try {
                    String userIdByTinyId = TRTCCloudImpl.this.mRoomInfo.getUserIdByTinyId(j2);
                    if (userIdByTinyId != null) {
                        user = TRTCCloudImpl.this.mRoomInfo.getUser(userIdByTinyId);
                    }
                } catch (Exception e2) {
                    TXCLog.e(TRTCCloudImpl.TAG, "get user info failed.", e2);
                }
                if (user == null) {
                    return;
                }
                int iRecvFirstIFrame = TRTCCloudImpl.this.mRoomInfo.recvFirstIFrame(j2, i2);
                TRTCCloudImpl.this.apiLog("onRecvFirstVideo " + j2 + ", " + iRecvFirstIFrame + ", streamType=" + i2);
                boolean z2 = false;
                if (i2 == 7) {
                    TRTCCloudImpl.this.updateRemoteVideoStatusByFirstFrame(user, 7, iRecvFirstIFrame == 1);
                } else {
                    TRTCCloudImpl.this.updateRemoteVideoStatusByFirstFrame(user, 2, iRecvFirstIFrame == 1);
                }
                int i3 = TRTCCloudImpl.this.mRecvMode;
                if (i3 != 3 && i3 != 1) {
                    z2 = true;
                }
                if (iRecvFirstIFrame > 1 || z2) {
                    return;
                }
                final String str = user.userID;
                if ((TRTCRoomInfo.hasMainVideo(user.streamState) || TRTCRoomInfo.hasSmallVideo(user.streamState)) && !TRTCRoomInfo.isMuteMainVideo(user.streamState)) {
                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.175.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                            if (tRTCCloudListener != null) {
                                tRTCCloudListener.onUserVideoAvailable(str, true);
                                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                                String userId = tRTCCloudImpl.mRoomInfo.getUserId();
                                Boolean bool = Boolean.TRUE;
                                tRTCCloudImpl.appendDashboardLog(userId, 0, String.format("[%s]video Available[%b]", str, bool));
                                Monitor.a(2, String.format("onUserVideoAvailable by recv first video. userID:%s, bAvailable:%b", str, bool) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            }
                        }
                    });
                }
            }
        });
    }

    private void onRecvSEIMsg(final long j2, final byte[] bArr) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.170
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                TRTCCloudListener tRTCCloudListener = tRTCCloudImpl.mTRTCListener;
                if (tRTCCloudListener != null) {
                    try {
                        String userIdByTinyId = tRTCCloudImpl.mRoomInfo.getUserIdByTinyId(j2);
                        if (userIdByTinyId == null) {
                            TXCLog.i(TRTCCloudImpl.TAG, "onRecvSEIMsg Error, user id is null for tinyId=" + j2 + " self:" + TRTCCloudImpl.this.hashCode());
                            return;
                        }
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        TRTCCloudImpl.access$11108(TRTCCloudImpl.this);
                        if (jCurrentTimeMillis - TRTCCloudImpl.this.mLastLogSEIMsgTs > com.heytap.mcssdk.constant.a.f7153q) {
                            TXCLog.i(TRTCCloudImpl.TAG, "onRecvSEIMsg. userId=" + userIdByTinyId + ", message = " + new String(bArr) + ", recvSEIMsgCountInPeriod = " + TRTCCloudImpl.this.mRecvSEIMsgCountInPeriod + " self:" + TRTCCloudImpl.this.hashCode());
                            TRTCCloudImpl.this.mLastLogSEIMsgTs = jCurrentTimeMillis;
                            TRTCCloudImpl.this.mRecvSEIMsgCountInPeriod = 0L;
                        }
                        tRTCCloudListener.onRecvSEIMsg(userIdByTinyId, bArr);
                    } catch (Exception e2) {
                        TXCLog.e(TRTCCloudImpl.TAG, "onRecvSEIMsg failed.", e2);
                    }
                }
            }
        });
    }

    private void onRecvVideoServerConfig(final TRTCVideoServerConfig tRTCVideoServerConfig) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.185
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("onRecvVideoServerConfig " + tRTCVideoServerConfig);
                TRTCCloudImpl.this.mVideoServerConfig = tRTCVideoServerConfig;
                TRTCVideoServerConfig.saveToSharedPreferences(TRTCCloudImpl.this.mContext, tRTCVideoServerConfig);
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.185.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                        if (tXCRenderAndDec != null) {
                            tXCRenderAndDec.enableLimitDecCache(TRTCCloudImpl.this.mVideoServerConfig.enableHWVUI);
                        }
                        TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                        if (tXCRenderAndDec2 != null) {
                            tXCRenderAndDec2.enableLimitDecCache(TRTCCloudImpl.this.mVideoServerConfig.enableHWVUI);
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRemoteAudioStatusUpdatedInternal(TRTCRoomInfo.UserInfo userInfo, int i2, int i3) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRemoteVideoStatusUpdatedInternal(final TRTCRoomInfo.UserInfo userInfo, int i2, final int i3, final int i4) {
        if (userInfo == null) {
            return;
        }
        int i5 = 2;
        if (i2 == 7) {
            TRTCRoomInfo.RenderInfo renderInfo = userInfo.subRender;
            int i6 = renderInfo.lastVideoStreamStatus;
            if (i3 == i6) {
                return;
            }
            if (i6 == 0 && i3 == 2) {
                return;
            }
            if (i4 == 2 && i6 != 2) {
                return;
            }
            if (i4 == 1 && i6 != 1) {
                return;
            }
            if (i6 == 2 && i4 == 3) {
                return;
            } else {
                renderInfo.lastVideoStreamStatus = i3;
            }
        } else {
            TRTCRoomInfo.RenderInfo renderInfo2 = userInfo.mainRender;
            int i7 = renderInfo2.lastVideoStreamStatus;
            if (i3 == i7) {
                return;
            }
            if (i7 == 0 && i3 == 2) {
                return;
            }
            if (i4 == 2 && i7 != 2) {
                return;
            }
            if (i4 == 1 && i7 != 1) {
                return;
            }
            if (i7 == 2 && i4 == 3) {
                return;
            }
            renderInfo2.lastVideoStreamStatus = i3;
            i5 = 0;
        }
        final int i8 = i5;
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.206
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener == null) {
                    return;
                }
                tRTCCloudListener.onRemoteVideoStatusUpdated(userInfo.userID, i8, i3, i4, null);
                String str = String.format("video status change userId:%s, streamType:%d, streamStatus:%d, reason:%d", userInfo.userID, Integer.valueOf(i8), Integer.valueOf(i3), Integer.valueOf(i4));
                TRTCCloudImpl.this.apiOnlineLog(str);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.appendDashboardLog(tRTCCloudImpl.mRoomInfo.getUserId(), i8, str);
            }
        });
    }

    private void onRequestAccIP(int i2, String str, boolean z2) {
        apiLog("onRequestAccIP err:" + i2 + " " + str + " isAcc:" + z2);
        if (i2 == 0) {
            String str2 = z2 ? "connect ACC" : "connect PROXY";
            Bundle bundle = new Bundle();
            bundle.putLong("EVT_ID", i2);
            bundle.putLong("EVT_TIME", System.currentTimeMillis());
            bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str2);
            bundle.putInt("EVT_STREAM_TYPE", 2);
            notifyEvent(this.mRoomInfo.getUserId(), i2, bundle);
        }
    }

    private void onRequestDownStream(final int i2, final String str, final long j2, final int i3) {
        if (i2 != 0) {
            runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.153
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                    if (tRTCCloudListener != null) {
                        tRTCCloudListener.onError(i2, str, null);
                    }
                }
            });
        } else {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.154
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.154.1
                        @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                        public void accept(String str2, TRTCRoomInfo.UserInfo userInfo) {
                            AnonymousClass154 anonymousClass154 = AnonymousClass154.this;
                            if (i3 == 1 || j2 != userInfo.tinyID) {
                                return;
                            }
                            TRTCCloudImpl.this.apiLog("onRequestDownStream " + userInfo.tinyID + ", " + userInfo.userID + ", " + i3);
                            if (i3 == 7) {
                                TXCRenderAndDec tXCRenderAndDec = userInfo.subRender.render;
                                if (tXCRenderAndDec == null || tXCRenderAndDec.getStreamType() == i3) {
                                    return;
                                }
                                userInfo.subRender.render.stopVideo();
                                userInfo.subRender.render.setStreamType(i3);
                                userInfo.subRender.render.startVideo();
                                return;
                            }
                            TXCRenderAndDec tXCRenderAndDec2 = userInfo.mainRender.render;
                            if (tXCRenderAndDec2 == null || tXCRenderAndDec2.getStreamType() == i3) {
                                return;
                            }
                            userInfo.mainRender.render.stopVideo();
                            userInfo.mainRender.render.setStreamType(i3);
                            userInfo.mainRender.render.startVideo();
                            TXCKeyPointReportProxy.a(String.valueOf(userInfo.tinyID), 40038, 0L, i3);
                        }
                    });
                }
            });
        }
    }

    private void onRequestToken(int i2, String str, final long j2, final byte[] bArr) {
        apiLog("onRequestToken " + j2 + "," + i2 + ", " + str);
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.142
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.mRoomInfo.setTinyId(String.valueOf(j2));
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.mRoomInfo.setToken(tRTCCloudImpl.mContext, bArr);
            }
        });
    }

    private void onSendCustomCmdMsgResult(int i2, int i3, int i4, String str) {
    }

    private void onSpeedTest(final String str, final int i2, final float f2, final float f3, final int i3, final int i4) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.171
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult = new TRTCCloudDef.TRTCSpeedTestResult();
                    tRTCSpeedTestResult.ip = str;
                    int i5 = i2;
                    tRTCSpeedTestResult.rtt = i5;
                    float f4 = f2;
                    tRTCSpeedTestResult.upLostRate = f4;
                    float f5 = f3;
                    tRTCSpeedTestResult.downLostRate = f5;
                    if (f4 >= f5) {
                        tRTCSpeedTestResult.quality = TRTCCloudImpl.this.getNetworkQuality(i5, (int) (f4 * 100.0f));
                    } else {
                        tRTCSpeedTestResult.quality = TRTCCloudImpl.this.getNetworkQuality(i5, (int) (f5 * 100.0f));
                    }
                    tRTCCloudListener.onSpeedTest(tRTCSpeedTestResult, i3, i4);
                    TRTCCloudImpl.this.apiLog("SpeedTest progress %d/%d, result: %s", Integer.valueOf(i3), Integer.valueOf(i4), tRTCSpeedTestResult.toString());
                }
            }
        });
    }

    private void onStartPublishing(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.176
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("onStartPublishing err:" + i2 + ", msg:" + str);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onStartPublishing(i2, str);
                }
            }
        });
    }

    private void onStopPublishing(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.177
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("onStopPublishing err:" + i2 + ", msg:" + str);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onStopPublishing(i2, str);
                }
            }
        });
    }

    private void onStreamPublished(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.178
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("onStreamPublished err:" + i2 + ", msg:" + str);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onStartPublishCDNStream(i2, str);
                }
            }
        });
    }

    private void onStreamUnpublished(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.179
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("onStreamUnpublished err:" + i2 + ", msg:" + str);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onStopPublishCDNStream(i2, str);
                }
            }
        });
    }

    private void onSwitchRoom(final int i2, final String str) {
        apiOnlineLog(String.format("onSwitchRoom err:%d, msg:%s", Integer.valueOf(i2), str));
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.149
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onSwitchRoom(i2, str);
                }
            }
        });
    }

    private void onTranscodingUpdated(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.180
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("onTranscodingUpdated err:" + i2 + ", msg:" + str);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onSetMixTranscodingConfig(i2, str);
                }
            }
        });
    }

    private void onTryToReconnect() {
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        tRTCRoomInfo.networkStatus = 2;
        notifyEvent(tRTCRoomInfo.getUserId(), 0, "Retry enter room.");
        apiOnlineLog("onTryToReconnect");
        resetFeelingBlockReport();
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.167
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onTryToReconnect();
                }
            }
        });
    }

    private void onVideoBlockThresholdChanged(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.164
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.164.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                        if (tXCRenderAndDec != null) {
                            tXCRenderAndDec.setBlockInterval(i2);
                        }
                        TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                        if (tXCRenderAndDec2 != null) {
                            tXCRenderAndDec2.setBlockInterval(i2);
                        }
                    }
                });
            }
        });
    }

    private void onWholeMemberEnter(long j2, final String str) {
        final WeakReference weakReference = new WeakReference(this);
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.158
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener;
                if (((TRTCCloudImpl) weakReference.get()) == null || (tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener) == null) {
                    return;
                }
                tRTCCloudListener.onRemoteUserEnterRoom(str);
            }
        });
    }

    private void onWholeMemberExit(long j2, final String str, final int i2) {
        final WeakReference weakReference = new WeakReference(this);
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.159
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener;
                if (((TRTCCloudImpl) weakReference.get()) == null || (tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener) == null) {
                    return;
                }
                tRTCCloudListener.onRemoteUserLeaveRoom(str, i2);
            }
        });
    }

    private int outerStreamTypeToInnerStreamType(int i2) {
        if (i2 == 0) {
            return 2;
        }
        return i2 == 1 ? 3 : 7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void preloadMusic(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("preLoadMusic param is null");
            return;
        }
        if (!jSONObject.has("musicId")) {
            apiLog("preLoadMusic[lack parameter]: musicId");
            return;
        }
        if (!jSONObject.has("path")) {
            apiLog("preLoadMusic[lack parameter]: path");
            return;
        }
        int iOptInt = jSONObject.optInt("musicId", 0);
        String strOptString = jSONObject.optString("path", "");
        long jOptLong = jSONObject.optLong("startTimeMS", 0L);
        TXAudioEffectManager.AudioMusicParam audioMusicParam = new TXAudioEffectManager.AudioMusicParam(iOptInt, strOptString);
        audioMusicParam.startTimeMS = jOptLong;
        TXAudioEffectManagerImpl.getAutoCacheHolder().preloadMusic(audioMusicParam);
    }

    private void pushVideoFrame(TXSNALPacket tXSNALPacket) {
        TRTCCloudImpl tRTCCloudImpl;
        synchronized (this.mCurrentPublishClouds) {
            tRTCCloudImpl = this.mCurrentPublishClouds.get(Integer.valueOf(tXSNALPacket.streamType));
        }
        if (tRTCCloudImpl != null) {
            nativePushVideo(tRTCCloudImpl.getNetworkContext(), tXSNALPacket.streamType, tXSNALPacket.codecId == 1 ? 14 : 1, tXSNALPacket.nalType, tXSNALPacket.nalData, tXSNALPacket.gopIndex, tXSNALPacket.gopFrameIndex, tXSNALPacket.refFremeIndex, tXSNALPacket.pts, tXSNALPacket.dts);
        }
    }

    private String query(String str) {
        return this.mRoomInfo.query(this.mContext, str);
    }

    private void removeRemoteView(final String str, final int i2) {
        runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.34
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    return;
                }
                TRTCRoomInfo.RenderInfo renderInfo = i2 == 2 ? user.subRender : user.mainRender;
                TXCloudVideoView tXCloudVideoView = renderInfo.view;
                if (tXCloudVideoView != null) {
                    SurfaceView surfaceView = tXCloudVideoView.getSurfaceView();
                    if (surfaceView != null) {
                        surfaceView.getHolder().removeCallback(renderInfo);
                    }
                    tXCloudVideoView.removeVideoView();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUpStreamType(int i2) {
        if (this.mStreamTypes.contains(Integer.valueOf(i2))) {
            this.mStreamTypes.remove(Integer.valueOf(i2));
        }
        removeUpstream(i2);
    }

    private void removeUpstream(int i2) {
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(Integer.valueOf(i2));
        if (tRTCCloudImpl != null) {
            nativeRemoveUpstream(tRTCCloudImpl.getNetworkContext(), i2);
        }
    }

    private void resetFeelingBlockReport() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.165
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.165.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                        if (tXCRenderAndDec != null) {
                            tXCRenderAndDec.resetPeriodFeelingStatistics();
                        }
                        TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                        if (tXCRenderAndDec2 != null) {
                            tXCRenderAndDec2.resetPeriodFeelingStatistics();
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetVolumeInternal() {
        setAudioCaptureVolume(100);
        setAudioPlayoutVolume(100);
        TXAudioEffectManagerImpl.getInstance().setAllMusicVolume(100);
        TXAudioEffectManagerImpl.getCacheInstance().setAllMusicVolume(100);
        getAudioEffectManager().setAllMusicVolume(100);
        getAudioEffectManager().setVoiceEarMonitorVolume(100);
    }

    private void runOnMainThreadAndWaitDone(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainHandler.a(runnable);
        } else {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runOnSDKThread(Runnable runnable, int i2) {
        com.tencent.liteav.basic.util.f fVar = this.mSDKHandler;
        if (fVar != null) {
            fVar.postDelayed(runnable, i2);
        }
    }

    private void runOnSDKThreadAndWaitDone(Runnable runnable, long j2) throws InterruptedException {
        if (this.mSDKHandler != null) {
            if (Looper.myLooper() != this.mSDKHandler.getLooper()) {
                this.mSDKHandler.a(runnable, j2);
            } else {
                runnable.run();
            }
        }
    }

    private void sendMainStreamCustomVideoData(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        if (this.mMainStreamVideoSourceType != VideoSourceType.CUSTOM || this.mRoomInfo.muteLocalVideo) {
            return;
        }
        synchronized (this.mCustomCaptureLock) {
            if (this.mCustomVideoUtil == null) {
                this.mCustomVideoUtil = new TRTCCustomTextureUtil(this.mCaptureAndEnc, this.mCustomCaptureGLSyncMode);
            }
            TRTCCustomTextureUtil tRTCCustomTextureUtil = this.mCustomVideoUtil;
            if (tRTCCustomTextureUtil != null) {
                tRTCCustomTextureUtil.sendCustomTexture(tRTCVideoFrame);
            }
        }
    }

    private void sendSubStreamCustomVideoData(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        if (this.mSubStreamVideoSourceType != VideoSourceType.CUSTOM || this.mRoomInfo.muteLocalSubVideo) {
            return;
        }
        synchronized (this.mCustomCaptureLock) {
            if (this.mCustomSubStreamVideoUtil == null) {
                makeSureSubStreamCaptureCreated();
                this.mCustomSubStreamVideoUtil = new TRTCCustomTextureUtil(this.mSubStreamCaptureAndEnc, this.mCustomCaptureGLSyncMode);
            }
            TRTCCustomTextureUtil tRTCCustomTextureUtil = this.mCustomSubStreamVideoUtil;
            if (tRTCCustomTextureUtil != null) {
                tRTCCustomTextureUtil.sendCustomTexture(tRTCVideoFrame);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAVSyncPlaySources(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setAVSyncPlaySources param is null");
            return;
        }
        if (!jSONObject.has("audioSourceUserID")) {
            apiLog("setAVSyncPlaySources[lack parameter]: audioSourceUserID");
            return;
        }
        if (!jSONObject.has("videoSourceUserID")) {
            apiLog("setAVSyncPlaySources[lack parameter]: videoSourceUserID");
            return;
        }
        String string = jSONObject.getString("audioSourceUserID");
        if (string == null) {
            apiLog("setAVSyncPlaySources[illegal type]: audioSourceUserID");
            return;
        }
        String string2 = jSONObject.getString("videoSourceUserID");
        if (string2 == null) {
            apiLog("setAVSyncPlaySources[illegal type]: videoSourceUserID");
        } else {
            nativeSetAVSyncPlaySources(this.mNativeRtcContext, string, string2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAudioEncodeConfiguration() {
        setQoSParams();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAudioQualityEx(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setAudioQualityEx param is null");
            return;
        }
        TXCAudioEngine.getInstance().setAudioQuality(jSONObject.optInt("sampleRate", 0), jSONObject.optInt("channel", 0), jSONObject.optInt(IjkMediaMeta.IJKM_KEY_BITRATE, -1), jSONObject.optInt("encodeMode", 0), jSONObject.optInt("systemVolumeType", -1), 4);
        if (this.mIsAudioCapturing || this.mEnableCustomAudioCapture) {
            setQoSParams();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAudioSampleRate(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("sampleRate")) {
            apiLog("setAudioSampleRate[lack parameter or illegal type]: sampleRate");
            return;
        }
        int i2 = jSONObject.getInt("sampleRate");
        if (this.mEnableCustomAudioCapture || this.mIsAudioCapturing) {
            apiLog("setAudioSampleRate[illegal state]");
            return;
        }
        if (16000 == i2 || 48000 == i2) {
            TXCAudioEngine.getInstance().setEncoderSampleRate(i2);
            return;
        }
        apiLog("muteRemoteAudioInSpeaker[illegal sampleRate]: " + i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCustomCaptureGLSyncMode(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("callExperimentalAPI[setCustomCaptureGLMode failed, params is null");
            return;
        }
        this.mCustomCaptureGLSyncMode = jSONObject.optInt("mode", 0);
        apiLog("setCustomCaptureGLMode: mode:" + this.mCustomCaptureGLSyncMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCustomRenderMode(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setCustomRenderMode param is null");
            return;
        }
        if (!jSONObject.has("mode")) {
            apiLog("setCustomRenderMode[lack parameter]: mode");
            return;
        }
        int iOptInt = jSONObject.optInt("mode", 0);
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        boolean z2 = iOptInt == 1;
        tRTCRoomInfo.enableCustomPreprocessor = z2;
        this.mCaptureAndEnc.a(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEnableH265Encoder(JSONObject jSONObject) {
        if (jSONObject == null) {
            apiLog("setEnableH265Encoder param is null");
            return;
        }
        if (jSONObject.has("enable")) {
            this.mH265Decision.setEnableH265EncodeByPrivateAPI(jSONObject.optInt("enable", 0) != 0);
            if (this.mCaptureAndEnc == null || this.mAppScene != 1) {
                return;
            }
            boolean zIsVideoEncoderCodecUsingH265 = this.mH265Decision.isVideoEncoderCodecUsingH265();
            TXCLog.i(TAG, "enableH265 = " + zIsVideoEncoderCodecUsingH265 + " ,mRoomState= " + this.mRoomState);
            this.mCaptureAndEnc.j(zIsVideoEncoderCodecUsingH265);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFramework(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setFramework[lack parameter]");
        } else if (jSONObject.has("framework")) {
            this.mFramework = jSONObject.getInt("framework");
        } else {
            apiLog("setFramework[lack parameter]: framework");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHeartBeatTimeoutSec(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setHeartBeatTimeoutSec param is null");
        } else if (!jSONObject.has("timeoutSec")) {
            apiLog("setHeartBeatTimeoutSec[lack parameter]: timeoutSec");
        } else {
            nativeSetHeartBeatTimeoutSec(this.mNativeRtcContext, jSONObject.optInt("timeoutSec", 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setKeepAVCaptureOption(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setKeepAVCaptureOption param is null");
            return;
        }
        this.mKeepAVCaptureWhenEnterRoomFailed = jSONObject.optInt("keepWhenEnterRoomFailed", 0) != 0;
        apiLog("setKeepAVCaptureOption " + this.mKeepAVCaptureWhenEnterRoomFailed);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLocalAudioMuteAction(JSONObject jSONObject) {
        if (jSONObject == null) {
            apiLog("setLocalAudioMuteAction param is null");
        } else {
            TXCAudioEngine.getInstance().setLocalAudioMuteAction(jSONObject.has("volumeEvaluation") ? jSONObject.optInt("volumeEvaluation", -1) : -1, jSONObject.has("muteCapturedAudioFrameAfterCallback") ? jSONObject.optInt("muteCapturedAudioFrameAfterCallback", -1) : -1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLocalAudioMuteMode(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("mode")) {
            apiLog("setLocalAudioMuteMode[lack parameter or illegal type]: mode");
        }
        int i2 = jSONObject.getInt("mode");
        if (1 == i2) {
            this.mEnableEosMode = true;
        } else {
            this.mEnableEosMode = false;
        }
        TXCAudioEngine.getInstance().enableCaptureEOSMode(this.mEnableEosMode);
        nativeSetLocalAudioMuteMode(getNetworkContext(), i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMediaCodecConfig(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setMediaCodecConfig param is null");
            return;
        }
        JSONArray jSONArray = jSONObject.has("encProperties") ? jSONObject.getJSONArray("encProperties") : null;
        com.tencent.liteav.g gVar = this.mConfig;
        gVar.ab = jSONArray;
        this.mCaptureAndEnc.a(gVar);
        this.mRoomInfo.decProperties = jSONObject.has("decProperties") ? jSONObject.getJSONArray("decProperties") : null;
        int i2 = jSONObject.has("restartDecoder") ? jSONObject.getInt("restartDecoder") : 0;
        this.mRoomInfo.enableRestartDecoder = i2 != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNetEnv(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setNetEnv param is null");
        } else if (!jSONObject.has("env")) {
            apiLog("setNetEnv[lack parameter]: env");
        } else {
            nativeSetNetEnv(this.mNativeRtcContext, jSONObject.optInt("env", 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOrientation(final int i2) {
        if (i2 == -1) {
            return;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.203
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.checkRenderRotation(i2);
                if (TRTCCloudImpl.this.mSensorMode != 0) {
                    TRTCCloudImpl.this.checkVideoEncRotation(i2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setQoSParams() {
        TXCAudioEncoderConfig audioEncoderConfig = TXCAudioEngine.getInstance().getAudioEncoderConfig();
        TXCLog.i("", "setQoSParams:" + audioEncoderConfig.sampleRate + " " + audioEncoderConfig.channels + " " + audioEncoderConfig.minBitrate + " " + audioEncoderConfig.maxBitrate);
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(1);
        if (tRTCCloudImpl != null) {
            nativeSetAudioEncodeConfiguration(tRTCCloudImpl.getNetworkContext(), audioEncoderConfig.minBitrate, audioEncoderConfig.maxBitrate, audioEncoderConfig.sampleRate, audioEncoderConfig.channels);
        }
    }

    private void setRemoteViewFillMode(final String str, final int i2, final int i3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.42
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setRemoteViewFillMode->userId:%s, streamType:%d, renderMode:%d", str, Integer.valueOf(i2), Integer.valueOf(i3));
                TRTCRoomInfo.RenderInfo renderInfo = TRTCCloudImpl.this.getRenderInfo(str, i2);
                if (renderInfo != null) {
                    int i4 = i3;
                    renderInfo.renderMode = i4;
                    TXCRenderAndDec tXCRenderAndDec = renderInfo.render;
                    if (tXCRenderAndDec != null) {
                        tXCRenderAndDec.setRenderMode(i4);
                    }
                }
            }
        });
    }

    private void setRemoteViewMirror(final String str, final int i2, final int i3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.44
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setRemoteViewMirror->userId:%s, streamType:%d, mirrorType:%d", str, Integer.valueOf(i2), Integer.valueOf(i3));
                TRTCRoomInfo.RenderInfo renderInfo = TRTCCloudImpl.this.getRenderInfo(str, i2);
                if (renderInfo != null) {
                    int i4 = i3;
                    renderInfo.mirrorType = i4;
                    TXCRenderAndDec tXCRenderAndDec = renderInfo.render;
                    if (tXCRenderAndDec != null) {
                        if (i4 == 1) {
                            tXCRenderAndDec.setRenderMirrorType(1);
                        } else {
                            tXCRenderAndDec.setRenderMirrorType(2);
                        }
                    }
                }
            }
        });
    }

    private void setRemoteViewRotation(final String str, final int i2, final int i3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.43
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setRemoteViewRotation->userId:%s, streamType:%d, rotation:%d", str, Integer.valueOf(i2), Integer.valueOf(i3));
                TRTCRoomInfo.RenderInfo renderInfo = TRTCCloudImpl.this.getRenderInfo(str, i2);
                if (renderInfo != null) {
                    int compatibleRotation = TRTCCloudImpl.this.getCompatibleRotation(i3);
                    renderInfo.rotation = compatibleRotation;
                    TXCRenderAndDec tXCRenderAndDec = renderInfo.render;
                    if (tXCRenderAndDec != null) {
                        tXCRenderAndDec.setRenderRotation(compatibleRotation);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreenCaptureCropRect(JSONObject jSONObject) {
        int iOptInt = jSONObject.optInt("x", 0);
        int iOptInt2 = jSONObject.optInt("y", 0);
        int iOptInt3 = jSONObject.optInt("w", 0);
        int iOptInt4 = jSONObject.optInt("h", 0);
        apiLog("setScreenCaptureCropRect [%d,%d,%d,%d]", Integer.valueOf(iOptInt), Integer.valueOf(iOptInt2), Integer.valueOf(iOptInt3), Integer.valueOf(iOptInt4));
        if (iOptInt == 0 && iOptInt2 == 0 && iOptInt3 == 0 && iOptInt4 == 0) {
            apiLog("clear screen capture crop rect");
            this.mCaptureAndEnc.a((com.tencent.liteav.basic.opengl.a) null);
            com.tencent.liteav.d dVar = this.mSubStreamCaptureAndEnc;
            if (dVar != null) {
                dVar.a((com.tencent.liteav.basic.opengl.a) null);
                return;
            }
            return;
        }
        if (iOptInt < 0 || iOptInt2 < 0 || iOptInt3 <= 0 || iOptInt4 <= 0) {
            return;
        }
        this.mCaptureAndEnc.a(new com.tencent.liteav.basic.opengl.a(iOptInt, iOptInt2, iOptInt3, iOptInt4));
        com.tencent.liteav.d dVar2 = this.mSubStreamCaptureAndEnc;
        if (dVar2 != null) {
            dVar2.a(new com.tencent.liteav.basic.opengl.a(iOptInt, iOptInt2, iOptInt3, iOptInt4));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStartVideoEncodeCodec() {
        if (this.mCaptureAndEnc != null) {
            this.mCaptureAndEnc.j(this.mH265Decision.isVideoEncoderStartCodecUsingH265());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoEncConfig(int i2, int i3, int i4, int i5, int i6, boolean z2, int i7, boolean z3) {
        if (GetPublishingCloudState(i2) == 0) {
            apiLog("setVideoEncConfig ignore when no in room");
        } else if (this.mCodecType != 2) {
            setVideoEncoderConfiguration(i2, i3, i4, i5, i6, 1, z2, i7, z3);
        } else {
            setVideoEncoderConfiguration(i2, i3, i4, i5, i6, isRPSSupported() ? this.mAppScene : 1, z2, i7, z3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoEncoderConfiguration(int i2, int i3, int i4, int i5, int i6, int i7, boolean z2, int i8, boolean z3) {
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(Integer.valueOf(i2));
        if (tRTCCloudImpl != null) {
            nativeSetVideoEncoderConfiguration(tRTCCloudImpl.getNetworkContext(), i2, i3, i4, i5, i6, i7, z2, i8, z3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoEncoderParamEx(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptJSONObject;
        if (jSONObject == null) {
            apiLog("callExperimentalAPI[lack parameter or illegal type]: codecType");
            return;
        }
        int iOptInt = jSONObject.optInt("codecType", -1);
        if (iOptInt != -1) {
            this.mCodecType = iOptInt;
            if (iOptInt == 0 && (jSONObjectOptJSONObject = jSONObject.optJSONObject("softwareCodecParams")) != null) {
                this.mConfig.S = jSONObjectOptJSONObject.optInt("enableRealTime") != 0;
                this.mConfig.f19343q = jSONObjectOptJSONObject.optInt(Scopes.PROFILE);
            }
        }
        int iOptInt2 = jSONObject.optInt("videoWidth", 0);
        int iOptInt3 = jSONObject.optInt("videoHeight", 0);
        int iOptInt4 = jSONObject.optInt("videoFps", 0);
        int iOptInt5 = jSONObject.optInt("videoBitrate", 0);
        int iOptInt6 = jSONObject.optInt("minVideoBitrate", 0);
        int iOptInt7 = jSONObject.optInt("rcMethod", 0);
        if (iOptInt2 <= 0 || iOptInt3 <= 0) {
            return;
        }
        int i2 = R2.attr.iconTint;
        if (iOptInt2 > 1920) {
            iOptInt3 = (iOptInt3 * R2.attr.iconTint) / R2.attr.iconTint;
            iOptInt2 = 1920;
        }
        if (iOptInt3 > 1920) {
            iOptInt2 = (iOptInt2 * R2.attr.iconTint) / R2.attr.iconTint;
        } else {
            i2 = iOptInt3;
        }
        int i3 = 90;
        if (iOptInt2 < 90) {
            i2 = (i2 * 90) / 90;
            iOptInt2 = 90;
        }
        if (i2 < 90) {
            iOptInt2 = (iOptInt2 * 90) / 90;
        } else {
            i3 = i2;
        }
        int i4 = ((iOptInt2 + 15) / 16) * 16;
        int i5 = ((i3 + 15) / 16) * 16;
        int iOptInt8 = jSONObject.optInt("streamType", 0);
        if (iOptInt8 == 0) {
            this.mConfig.f19336j = true;
            this.mLatestParamsOfBigEncoder.putInt(KEY_CONFIG_FPS, iOptInt4);
            boolean z2 = i4 <= i5;
            com.tencent.liteav.g gVar = this.mConfig;
            updateMainStreamEncoder(z2, i4, i5, iOptInt4, iOptInt5, gVar.f19345s, iOptInt6, gVar.f19336j);
            this.mCaptureAndEnc.n(iOptInt7);
        } else if (iOptInt8 == 1) {
            this.mLatestParamsOfSmallEncoder.putInt(KEY_CONFIG_FPS, iOptInt4);
            updateSmallStreamEncoder(i4, i5, iOptInt4, iOptInt5, iOptInt6);
        } else if (iOptInt8 == 2) {
            this.mLatestParamsOfSubEncoder.putInt(KEY_CONFIG_FPS, iOptInt4);
            boolean z3 = i4 <= i5;
            com.tencent.liteav.g gVar2 = this.mSubStreamConfig;
            updateSubStreamEncoder(z3, i4, i5, iOptInt4, iOptInt5, gVar2.f19345s, iOptInt6, gVar2.f19336j);
        }
        apiLog("vsize setVideoEncoderParamEx->width:" + this.mRoomInfo.bigEncSize.f19354a + ", height:" + this.mRoomInfo.bigEncSize.f19355b + ", fps:" + iOptInt4 + ", bitrate:" + iOptInt5 + ", stream:" + iOptInt8);
        updateOrientation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoEncoderParamInternal(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
        if (tRTCVideoEncParam == null) {
            apiLog("setVideoEncoderParam param is null");
            return;
        }
        this.mLatestParamsOfBigEncoder.putInt(KEY_CONFIG_FPS, tRTCVideoEncParam.videoFps);
        this.mLatestParamsOfBigEncoder.putBoolean(KEY_CONFIG_ADJUST_RESOLUTION, tRTCVideoEncParam.enableAdjustRes);
        g.a sizeByResolution = getSizeByResolution(tRTCVideoEncParam.videoResolution, tRTCVideoEncParam.videoResolutionMode);
        this.mConfig.f19336j = true;
        updateMainStreamEncoder(tRTCVideoEncParam.videoResolutionMode == 1, sizeByResolution.f19354a, sizeByResolution.f19355b, tRTCVideoEncParam.videoFps, tRTCVideoEncParam.videoBitrate, tRTCVideoEncParam.enableAdjustRes, tRTCVideoEncParam.minVideoBitrate, true);
        apiOnlineLog(String.format("setVideoEncoderParam width:%d, height:%d, fps:%d, bitrate:%d, mode:%d, minBitrate:%d", Integer.valueOf(this.mRoomInfo.bigEncSize.f19354a), Integer.valueOf(this.mRoomInfo.bigEncSize.f19355b), Integer.valueOf(tRTCVideoEncParam.videoFps), Integer.valueOf(tRTCVideoEncParam.videoBitrate), Integer.valueOf(tRTCVideoEncParam.videoResolutionMode), Integer.valueOf(tRTCVideoEncParam.minVideoBitrate)));
        updateOrientation();
        g.a aVar = this.mRoomInfo.bigEncSize;
        TXCEventRecorderProxy.a("18446744073709551615", 4007, aVar.f19354a, aVar.f19355b, "", 2);
        TXCEventRecorderProxy.a("18446744073709551615", 4008, tRTCVideoEncParam.videoFps, -1L, "", 2);
        TXCEventRecorderProxy.a("18446744073709551615", R2.attr.ykbViewRoundWidth, tRTCVideoEncParam.videoBitrate, -1L, "", 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoQuality(int i2, int i3) {
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(2);
        if (tRTCCloudImpl != null) {
            nativeSetVideoQuality(tRTCCloudImpl.getNetworkContext(), i2, i3);
        }
    }

    public static TRTCCloud sharedInstance(Context context) {
        TRTCCloudImpl tRTCCloudImpl;
        synchronized (TRTCCloudImpl.class) {
            if (sInstance == null) {
                sInstance = new TRTCCloudImpl(context);
            }
            tRTCCloudImpl = sInstance;
        }
        return tRTCCloudImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFloatingWindow(View view) {
        if (view == null) {
            return;
        }
        if (TXCBuild.VersionInt() >= 23 && !Settings.canDrawOverlays(view.getContext())) {
            TXCLog.e(TAG, "can't show floating window for no drawing overlay permission");
            return;
        }
        this.mFloatingWindow = view;
        WindowManager windowManager = (WindowManager) view.getContext().getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(TXCBuild.VersionInt() >= 26 ? R2.attr.index_change_img : TXCBuild.VersionInt() > 24 ? 2002 : 2005);
        layoutParams.flags = 8 | 262144;
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        windowManager.addView(view, layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean startMainStreamScreenCapture(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
        if (this.mMainStreamVideoSourceType == VideoSourceType.NONE) {
            VideoSourceType videoSourceType = this.mSubStreamVideoSourceType;
            VideoSourceType videoSourceType2 = VideoSourceType.SCREEN;
            if (videoSourceType != videoSourceType2) {
                this.mMainStreamVideoSourceType = videoSourceType2;
                this.mSensorMode = 0;
                this.mOrientationEventListener.disable();
                setStartVideoEncodeCodec();
                if (tRTCVideoEncParam != null) {
                    this.mOverrideFPSFromUser = false;
                    setVideoEncoderParamInternal(tRTCVideoEncParam);
                } else {
                    this.mOverrideFPSFromUser = true;
                }
                this.mCaptureAndEnc.a(0);
                updateStreamEncoder(true);
                TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam2 = this.mSmallEncParam;
                g.a sizeByResolution = getSizeByResolution(tRTCVideoEncParam2.videoResolution, tRTCVideoEncParam2.videoResolutionMode);
                int i2 = sizeByResolution.f19354a;
                int i3 = sizeByResolution.f19355b;
                TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam3 = this.mSmallEncParam;
                updateSmallStreamEncoder(i2, i3, tRTCVideoEncParam3.videoFps, tRTCVideoEncParam3.videoBitrate, tRTCVideoEncParam3.minVideoBitrate);
                this.mRoomInfo.localView = null;
                enableVideoStream(0, true);
                TXCKeyPointReportProxy.a(40046, 1, 2);
                TXCEventRecorderProxy.a("18446744073709551615", 4006, 2L, -1L, "", 2);
                this.mCaptureAndEnc.a((a.InterfaceC0337a) this);
                return true;
            }
        }
        notifyCaptureStarted("Has started capturing, ignore startMainStreamScreenCapture");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRemoteRender(TXCRenderAndDec tXCRenderAndDec, int i2) {
        tXCRenderAndDec.stopVideo();
        tXCRenderAndDec.setStreamType(i2);
        tXCRenderAndDec.startVideo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean startSubStreamScreenCapture(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
        if (this.mSubStreamVideoSourceType == VideoSourceType.NONE) {
            VideoSourceType videoSourceType = this.mMainStreamVideoSourceType;
            VideoSourceType videoSourceType2 = VideoSourceType.SCREEN;
            if (videoSourceType != videoSourceType2) {
                this.mSubStreamVideoSourceType = videoSourceType2;
                makeSureSubStreamCaptureCreated();
                if (tRTCVideoEncParam != null) {
                    this.mOverrideFPSFromUser = false;
                    setSubStreamEncoderParam(tRTCVideoEncParam);
                } else {
                    this.mOverrideFPSFromUser = true;
                }
                updateStreamEncoder(false);
                addUpStreamType(7);
                TXCKeyPointReportProxy.a(40046, 1, 7);
                TXCEventRecorderProxy.a("18446744073709551615", 4006, 2L, -1L, "", 7);
                this.mSubStreamCaptureAndEnc.a((a.InterfaceC0337a) this);
                return true;
            }
        }
        notifyCaptureStarted("Has started capturing, ignore startSubStreamScreenCapture");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLocalAudioInternal() {
        if (!this.mIsAudioCapturing) {
            apiLog("stopLocalAudio when no capturing audio, ignore!!!");
            return;
        }
        apiOnlineLog("stopLocalAudio");
        TXCEventRecorderProxy.a("18446744073709551615", 3001, 2L, -1L, "", 0);
        this.mIsAudioCapturing = false;
        TXCAudioEngine.getInstance().stopLocalAudio();
        if (!this.mEnableCustomAudioCapture) {
            enableAudioStream(false);
        }
        TXCKeyPointReportProxy.a(40050, 0, 1);
        TXCLog.i(TAG, "(%d)stopLocalAudioInternal end", Integer.valueOf(hashCode()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopMainStreamScreenCapture() {
        if (this.mMainStreamVideoSourceType != VideoSourceType.SCREEN) {
            return;
        }
        apiOnlineLog("stopMainStreamScreenCapture");
        this.mMainStreamVideoSourceType = VideoSourceType.NONE;
        this.mCaptureAndEnc.k();
        this.mRoomInfo.localView = null;
        enableVideoStream(0, false);
        TXCKeyPointReportProxy.a(40046, 0, 2);
        TXCEventRecorderProxy.a("18446744073709551615", 4006, 3L, -1L, "", 2);
        com.tencent.liteav.g gVar = this.mConfig;
        gVar.f19337k = this.mLatestParamsOfBigEncoder.getInt(KEY_CONFIG_FPS, gVar.f19337k);
        com.tencent.liteav.g gVar2 = this.mConfig;
        gVar2.f19338l = this.mLatestParamsOfBigEncoder.getInt(KEY_CONFIG_GOP, gVar2.f19338l);
        com.tencent.liteav.g gVar3 = this.mConfig;
        gVar3.f19345s = this.mLatestParamsOfBigEncoder.getBoolean(KEY_CONFIG_ADJUST_RESOLUTION, gVar3.f19345s);
        TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam = this.mSmallEncParam;
        tRTCVideoEncParam.videoFps = this.mLatestParamsOfSmallEncoder.getInt(KEY_CONFIG_FPS, tRTCVideoEncParam.videoFps);
        TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam2 = this.mSmallEncParam;
        tRTCVideoEncParam2.enableAdjustRes = this.mLatestParamsOfSmallEncoder.getBoolean(KEY_CONFIG_ADJUST_RESOLUTION, tRTCVideoEncParam2.enableAdjustRes);
        TXCLog.i(TAG, String.format(Locale.ENGLISH, "restore big encoder's fps: %d, gop: %d, small encoder's fps: %d", Integer.valueOf(this.mConfig.f19337k), Integer.valueOf(this.mConfig.f19338l), Integer.valueOf(this.mSmallEncParam.videoFps)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRemoteMainRender(TRTCRoomInfo.UserInfo userInfo, Boolean bool) {
        if (userInfo == null) {
            return;
        }
        apiLog(String.format("stopRemoteRender userID:%s tinyID:%d streamType:%d", userInfo.userID, Long.valueOf(userInfo.tinyID), Integer.valueOf(userInfo.streamType)));
        nativeCancelDownStream(this.mNativeRtcContext, userInfo.tinyID, 2, bool.booleanValue());
        nativeCancelDownStream(this.mNativeRtcContext, userInfo.tinyID, 3, bool.booleanValue());
        userInfo.mainRender.lastVideoStatusChangeOperation = 1;
        onRemoteVideoStatusUpdatedInternal(userInfo, 2, 0, 4);
        TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.stopVideo();
        }
    }

    private void stopRemoteRender(final String str, final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.35
            @Override // java.lang.Runnable
            public void run() {
                final TRTCRoomInfo.RenderInfo renderInfo;
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    TRTCCloudImpl.this.apiLog("stopRemoteRender user[%s] is not exist ", str);
                    return;
                }
                TRTCCloudImpl.this.apiOnlineLog(String.format("stopRemoteView userID:%s tinyID:%d streamType:%d", str, Long.valueOf(user.tinyID), Integer.valueOf(i2)));
                TXCEventRecorderProxy.a(String.valueOf(user.tinyID), R2.attr.zantong, 0L, -1L, "", i2);
                if (i2 == 2) {
                    renderInfo = user.subRender;
                    TRTCCloudImpl.this.stopRemoteSubRender(user);
                } else {
                    renderInfo = user.mainRender;
                    TRTCCloudImpl.this.stopRemoteMainRender(user, Boolean.FALSE);
                }
                final TXCloudVideoView tXCloudVideoView = renderInfo.view;
                TRTCCloudImpl.this.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.35.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SurfaceView surfaceView;
                        TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                        if (tXCloudVideoView2 == null || (surfaceView = tXCloudVideoView2.getSurfaceView()) == null) {
                            return;
                        }
                        surfaceView.getHolder().removeCallback(renderInfo);
                    }
                });
                renderInfo.view = null;
                renderInfo.startRenderView = Boolean.FALSE;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRemoteSubRender(TRTCRoomInfo.UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        apiLog(String.format("stopRemoteRender userID:%s tinyID:%d streamType:%d", userInfo.userID, Long.valueOf(userInfo.tinyID), 7));
        nativeCancelDownStream(this.mNativeRtcContext, userInfo.tinyID, 7, false);
        userInfo.subRender.lastVideoStatusChangeOperation = 1;
        onRemoteVideoStatusUpdatedInternal(userInfo, 7, 0, 4);
        TXCRenderAndDec tXCRenderAndDec = userInfo.subRender.render;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.stopVideo();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSubStreamScreenCapture() {
        if (this.mSubStreamVideoSourceType != VideoSourceType.SCREEN) {
            return;
        }
        apiOnlineLog("stopSubStreamScreenCapture");
        makeSureSubStreamCaptureCreated();
        this.mSubStreamVideoSourceType = VideoSourceType.NONE;
        this.mSubStreamCaptureAndEnc.k();
        com.tencent.liteav.g gVar = this.mSubStreamConfig;
        gVar.f19337k = this.mLatestParamsOfSubEncoder.getInt(KEY_CONFIG_FPS, gVar.f19337k);
        com.tencent.liteav.g gVar2 = this.mSubStreamConfig;
        gVar2.f19338l = this.mLatestParamsOfSubEncoder.getInt(KEY_CONFIG_GOP, gVar2.f19338l);
        com.tencent.liteav.g gVar3 = this.mSubStreamConfig;
        gVar3.f19345s = this.mLatestParamsOfSubEncoder.getBoolean(KEY_CONFIG_ADJUST_RESOLUTION, gVar3.f19345s);
        this.mSubStreamCaptureAndEnc.a(this.mSubStreamConfig);
        removeUpStreamType(7);
        TXCKeyPointReportProxy.a(40046, 0, 7);
        TXCEventRecorderProxy.a("18446744073709551615", 4006, 3L, -1L, "", 7);
    }

    private void store(String str, String str2) {
        this.mRoomInfo.store(this.mContext, str, str2);
    }

    private int translateAudioAbnormalDetectState(int i2) {
        if ((i2 & 1) != 0) {
            return 1;
        }
        if ((i2 & 2) != 0) {
            return 2;
        }
        return (i2 & 4) != 0 ? 3 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int translateStreamType(int i2) {
        if (i2 != 3) {
            return i2 != 7 ? 0 : 2;
        }
        return 1;
    }

    private void updateEncType() {
        int i2 = this.mCodecType;
        if (i2 == 0 || i2 == 1) {
            this.mConfig.f19339m = i2;
        } else if (this.mAppScene == 1) {
            this.mConfig.f19339m = 1;
        }
    }

    private void updateEncoder(com.tencent.liteav.g gVar, VideoSourceType videoSourceType, int i2, boolean z2, int i3, int i4, int i5, int i6, boolean z3, int i7, boolean z4) {
        VideoSourceType videoSourceType2 = VideoSourceType.SCREEN;
        if (videoSourceType == videoSourceType2 || z2) {
            gVar.f19341o = 1;
            gVar.f19327a = i3;
            gVar.f19328b = i4;
        } else {
            gVar.f19341o = 0;
            gVar.f19327a = i4;
            gVar.f19328b = i3;
        }
        gVar.f19340n = com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_INVALID;
        if (i5 > 0) {
            if (i5 > 30) {
                apiLog("setVideoEncoderParam fps > 30, limit fps to 30");
                gVar.f19337k = 30;
            } else {
                gVar.f19337k = i5;
            }
        }
        if (i6 > 0) {
            gVar.f19331e = i6;
        }
        if (i7 >= 0) {
            gVar.f19333g = i7;
        }
        if (videoSourceType == videoSourceType2) {
            gVar.f19338l = 3;
            gVar.f19345s = false;
            if (this.mOverrideFPSFromUser) {
                gVar.f19337k = 10;
            }
        } else {
            gVar.f19345s = z3;
        }
        setVideoEncConfig(i2, i3, i4, gVar.f19337k, gVar.f19331e, gVar.f19345s, gVar.f19333g, z4);
        if (this.mCodecType != 2 || gVar.f19327a * gVar.f19328b < 518400) {
            return;
        }
        gVar.f19339m = 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLocalViewInternal(final TXCloudVideoView tXCloudVideoView) {
        final SurfaceView surfaceView = tXCloudVideoView != null ? tXCloudVideoView.getSurfaceView() : null;
        final TextureView hWVideoView = tXCloudVideoView != null ? tXCloudVideoView.getHWVideoView() : null;
        final Surface[] surfaceArr = new Surface[1];
        final com.tencent.liteav.basic.util.e eVar = new com.tencent.liteav.basic.util.e();
        runOnMainThreadAndWaitDone(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.19
            @Override // java.lang.Runnable
            public void run() {
                SurfaceView surfaceView2 = surfaceView;
                if (surfaceView2 != null) {
                    SurfaceHolder holder = surfaceView2.getHolder();
                    holder.removeCallback(TRTCCloudImpl.this.mLocalPreviewSurfaceViewCallback);
                    holder.addCallback(TRTCCloudImpl.this.mLocalPreviewSurfaceViewCallback);
                    if (holder.getSurface().isValid()) {
                        TRTCCloudImpl.this.apiLog("updateLocalViewInternal with valid surface %s, width: %d, height: %d", holder.getSurface(), Integer.valueOf(surfaceView.getWidth()), Integer.valueOf(surfaceView.getHeight()));
                        surfaceArr[0] = holder.getSurface();
                        eVar.f18712a = surfaceView.getWidth();
                        eVar.f18713b = surfaceView.getHeight();
                    } else {
                        TRTCCloudImpl.this.apiLog("updateLocalViewInternal with surfaceView add callback");
                    }
                }
                TextureView textureView = hWVideoView;
                if (textureView != null) {
                    LocalPreviewTextureViewListener localPreviewTextureViewListener = TRTCCloudImpl.this.new LocalPreviewTextureViewListener(textureView);
                    hWVideoView.setSurfaceTextureListener(localPreviewTextureViewListener);
                    if (localPreviewTextureViewListener.getSurface() != null) {
                        surfaceArr[0] = localPreviewTextureViewListener.getSurface();
                        eVar.f18712a = hWVideoView.getWidth();
                        eVar.f18713b = hWVideoView.getHeight();
                        TRTCCloudImpl.this.apiLog("updateLocalViewInternal with TextureView, SurfaceTexture: %s, width: %d, height: %d", hWVideoView.getSurfaceTexture(), Integer.valueOf(eVar.f18712a), Integer.valueOf(eVar.f18713b));
                    } else {
                        TRTCCloudImpl.this.apiLog("updateLocalViewInternal with textureView add callback");
                    }
                }
                TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                if (tXCloudVideoView2 != null) {
                    tXCloudVideoView2.showVideoDebugLog(TRTCCloudImpl.this.mDebugType);
                    TRTCCloud.TRTCViewMargin tRTCViewMargin = TRTCCloudImpl.this.mRoomInfo.debugMargin;
                    if (tRTCViewMargin != null) {
                        tXCloudVideoView.setLogMarginRatio(tRTCViewMargin.leftMargin, tRTCViewMargin.rightMargin, tRTCViewMargin.topMargin, tRTCViewMargin.bottomMargin);
                    }
                }
            }
        });
        Surface surface = surfaceArr[0];
        if (surface != null) {
            this.mCaptureAndEnc.a(surface);
            this.mCaptureAndEnc.a(eVar.f18712a, eVar.f18713b);
        }
    }

    private void updateMainStreamEncoder(boolean z2, int i2, int i3, int i4, int i5, boolean z3, int i6, boolean z4) {
        if (i2 > 0 && i3 > 0) {
            g.a aVar = this.mRoomInfo.bigEncSize;
            aVar.f19354a = i2;
            aVar.f19355b = i3;
        }
        com.tencent.liteav.g gVar = this.mConfig;
        VideoSourceType videoSourceType = this.mMainStreamVideoSourceType;
        g.a aVar2 = this.mRoomInfo.bigEncSize;
        updateEncoder(gVar, videoSourceType, 2, z2, aVar2.f19354a, aVar2.f19355b, i4, i5, z3, i6, z4);
        this.mCaptureAndEnc.f(this.mConfig.f19337k);
        this.mCaptureAndEnc.d(this.mConfig.f19331e);
        this.mCaptureAndEnc.a(this.mConfig);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOrientation() {
        VideoSourceType videoSourceType = this.mMainStreamVideoSourceType;
        if (videoSourceType == VideoSourceType.CUSTOM || videoSourceType == VideoSourceType.SCREEN) {
            return;
        }
        if (this.mCurrentOrientation == -1) {
            if (com.tencent.liteav.basic.util.h.g(this.mContext) == 1) {
                this.mCurrentOrientation = 0;
            } else {
                this.mCurrentOrientation = 1;
            }
        }
        setOrientation(this.mCurrentOrientation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRemoteVideoStatusByFirstFrame(TRTCRoomInfo.UserInfo userInfo, int i2, boolean z2) {
        int i3;
        int i4;
        if (i2 == 7) {
            if (userInfo.subRender.render == null || !TRTCRoomInfo.hasSubVideo(userInfo.streamState) || TRTCRoomInfo.isMuteSubVideo(userInfo.streamState)) {
                return;
            }
            TRTCRoomInfo.RenderInfo renderInfo = userInfo.subRender;
            if (renderInfo.muteVideo != TRTCRoomInfo.TRTCRemoteMuteState.MUTE) {
                if (z2 || (i4 = renderInfo.lastVideoStatusChangeOperation) == 0) {
                    onRemoteVideoStatusUpdatedInternal(userInfo, 7, 1, 3);
                    userInfo.subRender.lastVideoStatusChangeOperation = 2;
                    return;
                } else if (i4 == 2) {
                    onRemoteVideoStatusUpdatedInternal(userInfo, 7, 1, 3);
                    return;
                } else {
                    if (i4 == 4) {
                        onRemoteVideoStatusUpdatedInternal(userInfo, 7, 1, 5);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (userInfo.mainRender.render != null) {
            if ((TRTCRoomInfo.hasMainVideo(userInfo.streamState) || TRTCRoomInfo.hasSmallVideo(userInfo.streamState)) && !TRTCRoomInfo.isMuteMainVideo(userInfo.streamState)) {
                TRTCRoomInfo.RenderInfo renderInfo2 = userInfo.mainRender;
                if (renderInfo2.muteVideo != TRTCRoomInfo.TRTCRemoteMuteState.MUTE) {
                    if (z2 || (i3 = renderInfo2.lastVideoStatusChangeOperation) == 0) {
                        onRemoteVideoStatusUpdatedInternal(userInfo, 2, 1, 3);
                        userInfo.mainRender.lastVideoStatusChangeOperation = 2;
                    } else if (i3 == 2) {
                        onRemoteVideoStatusUpdatedInternal(userInfo, 2, 1, 3);
                    } else if (i3 == 4) {
                        onRemoteVideoStatusUpdatedInternal(userInfo, 2, 1, 5);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRemoteViewInternal(String str, int i2, TXCloudVideoView tXCloudVideoView) {
        TRTCRoomInfo.RenderInfo renderInfo;
        TXCloudVideoView tXCloudVideoView2;
        TRTCRoomInfo.UserInfo user = this.mRoomInfo.getUser(str);
        if (user == null) {
            apiLog(TAG, "Can't get userinfo for %s, you should startRemoteView first.", str);
            return;
        }
        if (i2 == 0 || i2 == 1) {
            renderInfo = user.mainRender;
        } else {
            if (i2 != 2) {
                TXCLog.e(TAG, "updateRemoteView unsupported streamType:" + i2);
                return;
            }
            renderInfo = user.subRender;
        }
        if (tXCloudVideoView == null || tXCloudVideoView.getHWVideoView() == null || (tXCloudVideoView2 = renderInfo.view) == null || tXCloudVideoView2.getHWVideoView() == null) {
            apiLog("updateRemoteView only support TXCloudVideoView with TextureView. view: %s, remoteView: %s", tXCloudVideoView, renderInfo.view);
            return;
        }
        if (tXCloudVideoView == renderInfo.view) {
            apiLog("update remote view is same as the one being used.");
            return;
        }
        renderInfo.view = tXCloudVideoView;
        if (renderInfo.tinyID == 0) {
            apiLog("update remote view when tinyID is 0, ignore %s", user.userID);
            return;
        }
        setRenderView(user.userID, renderInfo, tXCloudVideoView, user.debugMargin);
        apiOnlineLog("Remote-updateRemoteView userID:%s tinyID:%d streamType:%d view:%s", user.userID, Long.valueOf(user.tinyID), Integer.valueOf(i2), tXCloudVideoView);
        startRemoteRender(renderInfo.render, outerStreamTypeToInnerStreamType(i2));
    }

    private void updateSmallStreamEncoder(int i2, int i3, int i4, int i5, int i6) {
        if (i2 > 0 && i3 > 0) {
            g.a aVar = this.mRoomInfo.smallEncSize;
            aVar.f19354a = i2;
            aVar.f19355b = i3;
        }
        if (i4 > 0) {
            if (i4 > 20) {
                apiLog("setVideoSmallEncoderParam fps > 20, limit fps to 20");
                this.mSmallEncParam.videoFps = 20;
            } else {
                this.mSmallEncParam.videoFps = i4;
            }
        }
        if (i5 > 0) {
            this.mSmallEncParam.videoBitrate = i5;
        }
        if (i6 >= 0) {
            this.mSmallEncParam.minVideoBitrate = i6;
        }
        int i7 = this.mConfig.f19338l;
        if (this.mMainStreamVideoSourceType == VideoSourceType.SCREEN) {
            TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam = this.mSmallEncParam;
            tRTCVideoEncParam.enableAdjustRes = false;
            if (this.mOverrideFPSFromUser) {
                tRTCVideoEncParam.videoFps = 10;
            }
            i7 = 3;
        }
        int i8 = i7;
        com.tencent.liteav.d dVar = this.mCaptureAndEnc;
        boolean z2 = this.mEnableSmallStream;
        g.a aVar2 = this.mRoomInfo.smallEncSize;
        int i9 = aVar2.f19354a;
        int i10 = aVar2.f19355b;
        TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam2 = this.mSmallEncParam;
        dVar.a(z2, i9, i10, tRTCVideoEncParam2.videoFps, tRTCVideoEncParam2.videoBitrate, i8);
        g.a aVar3 = this.mRoomInfo.smallEncSize;
        int i11 = aVar3.f19354a;
        int i12 = aVar3.f19355b;
        TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam3 = this.mSmallEncParam;
        setVideoEncConfig(3, i11, i12, tRTCVideoEncParam3.videoFps, tRTCVideoEncParam3.videoBitrate, this.mConfig.f19345s, tRTCVideoEncParam3.minVideoBitrate, false);
    }

    private void updateStreamEncoder(boolean z2) {
        com.tencent.liteav.g gVar = z2 ? this.mConfig : this.mSubStreamConfig;
        int i2 = gVar.f19341o;
        boolean z3 = true;
        if (i2 != 1 && i2 != 3) {
            z3 = false;
        }
        boolean z4 = z3;
        if (z2) {
            updateMainStreamEncoder(z4, gVar.f19327a, gVar.f19328b, gVar.f19337k, gVar.f19331e, gVar.f19345s, gVar.f19333g, gVar.f19336j);
        } else {
            updateSubStreamEncoder(z4, gVar.f19327a, gVar.f19328b, gVar.f19337k, gVar.f19331e, gVar.f19345s, gVar.f19333g, gVar.f19336j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSubStreamEncoder(boolean z2, int i2, int i3, int i4, int i5, boolean z3, int i6, boolean z4) {
        makeSureSubStreamCaptureCreated();
        updateEncoder(this.mSubStreamConfig, this.mSubStreamVideoSourceType, 7, z2, i2, i3, i4, i5, z3, i6, z4);
        this.mSubStreamCaptureAndEnc.f(this.mSubStreamConfig.f19337k);
        this.mSubStreamCaptureAndEnc.d(this.mSubStreamConfig.f19331e);
        this.mSubStreamCaptureAndEnc.a(this.mSubStreamConfig);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void ConnectOtherRoom(final String str) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.11
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("ConnectOtherRoom");
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeConnectOtherRoom(tRTCCloudImpl.mNativeRtcContext, str);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void DisconnectOtherRoom() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.12
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.disconnectOtherRoom("");
            }
        });
    }

    public void OnRemoteAudioStatusUpdated(final String str, final int i2, final int i3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.207
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = null;
                try {
                    String userIdByTinyId = TRTCCloudImpl.this.mRoomInfo.getUserIdByTinyId(Long.valueOf(str).longValue());
                    if (userIdByTinyId != null) {
                        user = TRTCCloudImpl.this.mRoomInfo.getUser(userIdByTinyId);
                    }
                } catch (Exception e2) {
                    TXCLog.e(TRTCCloudImpl.TAG, "get user info failed.", e2);
                }
                TRTCCloudImpl.this.onRemoteAudioStatusUpdatedInternal(user, i2, i3);
            }
        });
    }

    public void OnRemoteVideoStatusUpdated(final String str, final int i2, final int i3, final int i4) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.205
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = null;
                try {
                    String userIdByTinyId = TRTCCloudImpl.this.mRoomInfo.getUserIdByTinyId(Long.valueOf(str).longValue());
                    if (userIdByTinyId != null) {
                        user = TRTCCloudImpl.this.mRoomInfo.getUser(userIdByTinyId);
                    }
                } catch (Exception e2) {
                    TXCLog.e(TRTCCloudImpl.TAG, "get user info failed.", e2);
                }
                TRTCCloudImpl.this.onRemoteVideoStatusUpdatedInternal(user, i2, i3, i4);
            }
        });
    }

    public void apiLog(String str) {
        TXCLog.i(TAG, "(" + hashCode() + ")trtc_api " + str);
    }

    public void apiOnlineLog(String str) {
        Monitor.a(1, str + " self:" + hashCode(), "", 0, "(" + hashCode() + ")trtc_api");
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void callExperimentalAPI(final String str) throws JSONException {
        final JSONObject jSONObject;
        boolean z2;
        JSONObject jSONObject2;
        final String string = "";
        try {
            jSONObject2 = new JSONObject(str);
        } catch (Exception e2) {
            apiLog("callExperimentalAPI[failed]: " + str + " " + e2);
            jSONObject = null;
        }
        if (!jSONObject2.has("api")) {
            apiLog("callExperimentalAPI[lack api or illegal type]: " + str);
            return;
        }
        string = jSONObject2.getString("api");
        if (!jSONObject2.has("params")) {
            apiLog("callExperimentalAPI[lack params or illegal type]: " + str);
            return;
        }
        jSONObject = jSONObject2.getJSONObject("params");
        Iterator it = Arrays.asList("updatePrivateMapKey", "disconnectOtherRoom").iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            } else if (string.equals((String) it.next())) {
                z2 = true;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("callExperimentalAPI ");
        sb.append(z2 ? string : str);
        sb.append(", roomid = ");
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        long j2 = tRTCRoomInfo.roomId;
        sb.append(j2 != -1 ? Long.valueOf(j2) : tRTCRoomInfo.strRoomId);
        apiOnlineLog(sb.toString());
        if (string.equals("setEncodedDataProcessingListener")) {
            setEncodedDataProcessingListener(jSONObject);
            return;
        }
        if (string.equals("setAudioPacketExtraDataListener")) {
            setAudioPacketExtraDataListener(jSONObject);
            return;
        }
        try {
            if (string.equals("enableRealtimeChorus")) {
                enableRealtimeChorus(jSONObject);
                return;
            }
        } catch (Exception e3) {
            apiLog("callExperimentalAPI[failed]: " + e3.toString());
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.91
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (string.equals("enableBlackStream")) {
                        TRTCCloudImpl.this.enableBlackStream(jSONObject);
                    } else if (string.equals("setSEIPayloadType")) {
                        TRTCCloudImpl.this.setSEIPayloadType(jSONObject);
                    } else if (string.equals("setLocalAudioMuteMode")) {
                        TRTCCloudImpl.this.setLocalAudioMuteMode(jSONObject);
                    } else if (string.equals("setVideoEncodeParamEx")) {
                        TRTCCloudImpl.this.setVideoEncoderParamEx(jSONObject);
                    } else if (string.equals("setAudioSampleRate")) {
                        TRTCCloudImpl.this.setAudioSampleRate(jSONObject);
                    } else if (string.equals("muteRemoteAudioInSpeaker")) {
                        TRTCCloudImpl.this.muteRemoteAudioInSpeaker(jSONObject);
                    } else if (string.equals("enableAudioAGC")) {
                        TRTCCloudImpl.this.enableAudioAGC(jSONObject);
                    } else if (string.equals("enableAudioAEC")) {
                        TRTCCloudImpl.this.enableAudioAEC(jSONObject);
                    } else if (string.equals("enableAudioANS")) {
                        TRTCCloudImpl.this.enableAudioANS(jSONObject);
                    } else if (string.equals("setPerformanceMode")) {
                        TRTCCloudImpl.this.setPerformanceMode(jSONObject);
                    } else if (string.equals("setCustomRenderMode")) {
                        TRTCCloudImpl.this.setCustomRenderMode(jSONObject);
                    } else if (string.equals("setMediaCodecConfig")) {
                        TRTCCloudImpl.this.setMediaCodecConfig(jSONObject);
                    } else if (string.equals("sendJsonCMD")) {
                        TRTCCloudImpl.this.sendJsonCmd(jSONObject, str);
                    } else if (string.equals("updatePrivateMapKey")) {
                        TRTCCloudImpl.this.updatePrivateMapKey(jSONObject);
                    } else if (string.equals("setFramework")) {
                        TRTCCloudImpl.this.setFramework(jSONObject);
                    } else if (string.equals("forceCallbackMixedPlayAudioFrame")) {
                        TRTCCloudImpl.this.forceCallbackMixedPlayAudioFrame(jSONObject);
                    } else if (string.equals("setSystemAudioKitEnabled")) {
                        TXCAudioEngine.getInstance().setSystemAudioKitEnabled();
                    } else if (string.equals("setKeepAVCaptureOption")) {
                        TRTCCloudImpl.this.setKeepAVCaptureOption(jSONObject);
                    } else if (string.equals("setHeartBeatTimeoutSec")) {
                        TRTCCloudImpl.this.setHeartBeatTimeoutSec(jSONObject);
                    } else if (string.equals("setNetEnv")) {
                        TRTCCloudImpl.this.setNetEnv(jSONObject);
                    } else if (string.equals("setAudioQualityEx")) {
                        TRTCCloudImpl.this.setAudioQualityEx(jSONObject);
                    } else if (string.equals("SetAudioCacheParams")) {
                        TXCAudioEngine.getInstance().SetAudioCacheParams(jSONObject.getInt("min_cache_time"), jSONObject.getInt("max_cache_time"));
                    } else if (string.equals("setRoomType")) {
                        TRTCCloudImpl.this.setRoomType(jSONObject);
                    } else if (string.equals("setAVSyncPlaySources")) {
                        TRTCCloudImpl.this.setAVSyncPlaySources(jSONObject);
                    } else if (string.equals("enableHevcEncode")) {
                        TRTCCloudImpl.this.setEnableH265Encoder(jSONObject);
                    } else if (string.equals("setCustomCaptureGLSyncMode")) {
                        TRTCCloudImpl.this.setCustomCaptureGLSyncMode(jSONObject);
                    } else if (string.equals("checkDuplicateEnterRoom")) {
                        TRTCCloudImpl.this.checkDuplicateEnterRoom(jSONObject);
                    } else if (string.equals("preloadMusic")) {
                        TRTCCloudImpl.this.preloadMusic(jSONObject);
                    } else if (string.equals("setAudioCacheType")) {
                        TRTCCloudImpl.this.setAudioCacheType(jSONObject);
                    } else if (string.equals("muteDuringAECWarmUp")) {
                        TRTCCloudImpl.this.muteDuringAECWarmUp(jSONObject);
                    } else if (string.equals("setLocalAudioMuteAction")) {
                        TRTCCloudImpl.this.setLocalAudioMuteAction(jSONObject);
                    } else if (string.equals("setReverbParam")) {
                        TRTCCloudImpl.this.setReverbParam(jSONObject);
                    } else if (string.equals("setScreenCaptureCropRect")) {
                        TRTCCloudImpl.this.setScreenCaptureCropRect(jSONObject);
                    } else if (string.equals("setQoSStrategy")) {
                        TRTCCloudImpl.this.setQoSStrategy(jSONObject);
                    } else if (string.equals("disconnectOtherRoom")) {
                        TRTCCloudImpl.this.disconnectOtherRoom(jSONObject.toString());
                    } else if (string.equals("setEqualizationParam")) {
                        TRTCCloudImpl.this.setEqualizationParam(jSONObject);
                    } else {
                        TRTCCloudImpl.this.apiLog("callExperimentalAPI[illegal api]: " + string);
                    }
                } catch (Exception e4) {
                    TRTCCloudImpl.this.apiLog("callExperimentalAPI[failed]: " + e4);
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int checkAudioCapabilitySupport(int i2) {
        AudioServerConfig audioServerConfigLoadFromSharedPreferences = AudioServerConfig.loadFromSharedPreferences(this.mContext);
        if (i2 == 1) {
            return audioServerConfigLoadFromSharedPreferences.supportAAudio ? 1 : 0;
        }
        if (i2 == 2) {
            return audioServerConfigLoadFromSharedPreferences.isLowLatencySampleRateSupported ? 1 : 0;
        }
        apiLog("checkAudioCapabilitySupport params not valid, capabilityType: " + i2);
        return 0;
    }

    public void checkDashBoard() {
        final TXCloudVideoView tXCloudVideoView;
        if (this.mDebugType != 0 && (tXCloudVideoView = this.mRoomInfo.localView) != null) {
            final CharSequence uploadStreamInfo = getUploadStreamInfo();
            TXCLog.i(TAG, "[STATUS]" + uploadStreamInfo.toString().replace("\n", "") + " self:" + hashCode());
            runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.199
                @Override // java.lang.Runnable
                public void run() {
                    tXCloudVideoView.setDashBoardStatusInfo(uploadStreamInfo);
                }
            });
        }
        this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.200
            @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
            public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                if (tXCRenderAndDec != null && tXCRenderAndDec.isRendering()) {
                    userInfo.mainRender.render.updateLoadInfo();
                }
                TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                if (tXCRenderAndDec2 != null && tXCRenderAndDec2.isRendering()) {
                    userInfo.subRender.render.updateLoadInfo();
                }
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                if (tRTCCloudImpl.mDebugType != 0) {
                    TRTCRoomInfo.RenderInfo renderInfo = userInfo.mainRender;
                    tRTCCloudImpl.checkRemoteDashBoard(renderInfo.view, renderInfo.render, userInfo);
                    TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                    TRTCRoomInfo.RenderInfo renderInfo2 = userInfo.subRender;
                    tRTCCloudImpl2.checkRemoteDashBoard(renderInfo2.view, renderInfo2.render, userInfo);
                }
            }
        });
    }

    public void checkDuplicateEnterRoom(JSONObject jSONObject) {
        if (jSONObject == null) {
            apiLog("checkDuplicateEnterRoom param is null");
        } else if (jSONObject.has(AliyunVideoListBean.STATUS_CENSOR_WAIT)) {
            this.mCheckDuplicateEnterRoom = jSONObject.optInt(AliyunVideoListBean.STATUS_CENSOR_WAIT, 0);
        }
    }

    public void checkRemoteDashBoard(final TXCloudVideoView tXCloudVideoView, TXCRenderAndDec tXCRenderAndDec, TRTCRoomInfo.UserInfo userInfo) {
        if (tXCloudVideoView == null || tXCRenderAndDec == null || !tXCRenderAndDec.isRendering()) {
            return;
        }
        final CharSequence downloadStreamInfo = getDownloadStreamInfo(tXCRenderAndDec, userInfo);
        TXCLog.i(TAG, "[STATUS]" + downloadStreamInfo.toString().replace("\n", "") + " self:" + hashCode());
        runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.198
            @Override // java.lang.Runnable
            public void run() {
                tXCloudVideoView.setDashBoardStatusInfo(downloadStreamInfo);
            }
        });
    }

    public void checkUserState(final String str, long j2, int i2, int i3) {
        TRTCRoomInfo.UserInfo user;
        final TRTCCloudListener tRTCCloudListener = this.mTRTCListener;
        if (tRTCCloudListener == null || TextUtils.isEmpty(str)) {
            return;
        }
        final boolean z2 = TRTCRoomInfo.hasAudio(i2) && !TRTCRoomInfo.isMuteAudio(i2);
        if ((TRTCRoomInfo.hasAudio(i3) && !TRTCRoomInfo.isMuteAudio(i3)) != z2) {
            runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.195
                @Override // java.lang.Runnable
                public void run() {
                    tRTCCloudListener.onUserAudioAvailable(str, z2);
                }
            });
            appendDashboardLog(this.mRoomInfo.getUserId(), 0, String.format("[%s]audio Available[%b]", str, Boolean.valueOf(z2)));
            Monitor.a(2, String.format("onUserAudioAvailable userID:%s, bAvailable:%b", str, Boolean.valueOf(z2)) + " self:" + hashCode(), "", 0);
        }
        final boolean z3 = (TRTCRoomInfo.hasMainVideo(i2) || TRTCRoomInfo.hasSmallVideo(i2)) && !TRTCRoomInfo.isMuteMainVideo(i2);
        boolean z4 = ((TRTCRoomInfo.hasMainVideo(i3) || TRTCRoomInfo.hasSmallVideo(i3)) && !TRTCRoomInfo.isMuteMainVideo(i3)) != z3;
        int i4 = this.mRecvMode;
        boolean z5 = (i4 == 3 || i4 == 1) ? false : true;
        if (z4 && (user = this.mRoomInfo.getUser(str)) != null) {
            if (z3) {
                TRTCRoomInfo.RenderInfo renderInfo = user.mainRender;
                if (renderInfo.lastVideoStatusChangeOperation != 0) {
                    renderInfo.lastVideoStatusChangeOperation = 4;
                }
            } else {
                user.mainRender.lastVideoStatusChangeOperation = 3;
                onRemoteVideoStatusUpdatedInternal(user, 2, 0, 6);
            }
            TXCRenderAndDec tXCRenderAndDec = user.mainRender.render;
            if (tXCRenderAndDec != null) {
                tXCRenderAndDec.resetPeriodStatistics();
                user.mainRender.render.enableReport(z3);
            }
        }
        if (z4 && (this.mRoomInfo.hasRecvFirstIFrame(j2, 2) || z5)) {
            runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.196
                @Override // java.lang.Runnable
                public void run() {
                    tRTCCloudListener.onUserVideoAvailable(str, z3);
                }
            });
            appendDashboardLog(this.mRoomInfo.getUserId(), 0, String.format("[%s]video Available[%b]", str, Boolean.valueOf(z3)));
            Monitor.a(2, String.format("onUserVideoAvailable userID:%s, bAvailable:%b", str, Boolean.valueOf(z3)) + " self:" + hashCode(), "", 0);
        }
        final boolean z6 = TRTCRoomInfo.hasSubVideo(i2) && !TRTCRoomInfo.isMuteSubVideo(i2);
        if ((TRTCRoomInfo.hasSubVideo(i3) && !TRTCRoomInfo.isMuteSubVideo(i3)) != z6) {
            TRTCRoomInfo.UserInfo user2 = this.mRoomInfo.getUser(str);
            if (user2 != null) {
                TRTCRoomInfo.RenderInfo renderInfo2 = user2.subRender;
                if (renderInfo2.render != null) {
                    if (!z6) {
                        renderInfo2.lastVideoStatusChangeOperation = 3;
                        onRemoteVideoStatusUpdatedInternal(user2, 7, 0, 6);
                    } else if (renderInfo2.lastVideoStatusChangeOperation != 0) {
                        renderInfo2.lastVideoStatusChangeOperation = 4;
                    }
                    user2.subRender.render.resetPeriodStatistics();
                    user2.subRender.render.enableReport(z6);
                }
            }
            runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.197
                @Override // java.lang.Runnable
                public void run() {
                    tRTCCloudListener.onUserSubStreamAvailable(str, z6);
                }
            });
            appendDashboardLog(this.mRoomInfo.getUserId(), 0, String.format("[%s]subVideo Available[%b]", str, Boolean.valueOf(z6)));
            Monitor.a(2, String.format("onUserSubStreamAvailable userID:%s, bAvailable:%b", str, Boolean.valueOf(z6)) + " self:" + hashCode(), "", 0);
        }
    }

    public void clearRemoteMuteStates() {
        TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
        TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState = TRTCRoomInfo.TRTCRemoteMuteState.UNSET;
        tRTCRoomInfo.muteRemoteAudio = tRTCRemoteMuteState;
        tRTCRoomInfo.muteRemoteVideo = tRTCRemoteMuteState;
        tRTCRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.9
            @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
            public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                TRTCRoomInfo.RenderInfo renderInfo = userInfo.mainRender;
                TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState2 = TRTCRoomInfo.TRTCRemoteMuteState.UNSET;
                renderInfo.muteAudio = tRTCRemoteMuteState2;
                renderInfo.muteVideo = tRTCRemoteMuteState2;
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public TRTCCloud createSubCloud() {
        final TRTCSubCloud tRTCSubCloud = new TRTCSubCloud(this.mContext, new WeakReference(this), this.mSDKHandler);
        tRTCSubCloud.setListenerHandler(this.mListenerHandler);
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.15
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.mSubClouds.add(new WeakReference<>(tRTCSubCloud));
            }
        });
        return tRTCSubCloud;
    }

    public void destroy() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.4
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl;
                if (TRTCCloudImpl.this.mIsDestroyed) {
                    TRTCCloudImpl.this.apiLog("has been destroyed");
                    return;
                }
                TXCAudioEngineJNI.nativeSetAudioPlayoutTunnelEnabled(false);
                synchronized (TRTCCloudImpl.this.mNativeLock) {
                    TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                    if (tRTCCloudImpl2.mNativeRtcContext != 0) {
                        tRTCCloudImpl2.apiLog("destroy context");
                        TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                        tRTCCloudImpl3.nativeDestroyContext(tRTCCloudImpl3.mNativeRtcContext);
                    }
                    tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.mNativeRtcContext = 0L;
                }
                tRTCCloudImpl.mTRTCListener = null;
                tRTCCloudImpl.mAudioFrameListener = null;
                tRTCCloudImpl.resetVolumeInternal();
                TXCSoundEffectPlayer.getInstance().setSoundEffectListener(null);
                TXCAudioEngine.getInstance().clean();
                synchronized (TRTCCloudImpl.this.mCurrentPublishClouds) {
                    TRTCCloudImpl.this.mCurrentPublishClouds.clear();
                }
                Iterator<WeakReference<TRTCCloudImpl>> it = TRTCCloudImpl.this.mSubClouds.iterator();
                while (it.hasNext()) {
                    TRTCCloudImpl tRTCCloudImpl4 = it.next().get();
                    if (tRTCCloudImpl4 != null) {
                        tRTCCloudImpl4.destroy();
                    }
                }
                TRTCCloudImpl.this.mSubClouds.clear();
                com.tencent.liteav.audio.a.a().a(TRTCCloudImpl.this.hashCode());
                TRTCCloudImpl.this.mIsSDKThreadAlive.set(false);
                try {
                    com.tencent.liteav.basic.util.f fVar = TRTCCloudImpl.this.mSDKHandler;
                    if (fVar != null) {
                        fVar.getLooper().quit();
                    }
                } catch (Error unused) {
                    TXCLog.e(TRTCCloudImpl.TAG, "(" + TRTCCloudImpl.this.hashCode() + ") error occur when quit looper.");
                } catch (Exception e2) {
                    TXCLog.e(TRTCCloudImpl.TAG, "(" + TRTCCloudImpl.this.hashCode() + ") error occur when quit looper.", e2);
                }
                TXCAudioEngine.UninitInstance();
                TRTCCloudImpl.this.mIsDestroyed = true;
                TRTCCloudImpl.this.apiLog("destroy instance finish.");
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void destroySubCloud(final TRTCCloud tRTCCloud) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.16
            @Override // java.lang.Runnable
            public void run() {
                Iterator<WeakReference<TRTCCloudImpl>> it = TRTCCloudImpl.this.mSubClouds.iterator();
                while (it.hasNext()) {
                    TRTCCloudImpl tRTCCloudImpl = it.next().get();
                    if (tRTCCloudImpl != null && tRTCCloudImpl == tRTCCloud) {
                        tRTCCloudImpl.destroy();
                        it.remove();
                        return;
                    }
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void enableAudioEarMonitoring(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.62
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("enableAudioEarMonitoring enable:%b", Boolean.valueOf(z2));
                TXAudioEffectManagerImpl.getInstance().enableVoiceEarMonitor(z2);
            }
        });
    }

    public void enableAudioStream(boolean z2) {
        if (z2) {
            addUpStreamType(1);
        } else {
            removeUpStreamType(1);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void enableAudioVolumeEvaluation(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.67
            @Override // java.lang.Runnable
            public void run() {
                int i3 = i2;
                if (i3 <= 0) {
                    i3 = 0;
                } else if (i3 < 100) {
                    i3 = 100;
                }
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                if (i3 == tRTCCloudImpl.mAudioVolumeEvalInterval) {
                    return;
                }
                tRTCCloudImpl.apiLog("enableAudioVolumeEvaluation " + i3);
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                tRTCCloudImpl2.mAudioVolumeEvalInterval = i3;
                if (i3 > 0) {
                    tRTCCloudImpl2.startVolumeLevelCal(true);
                } else {
                    tRTCCloudImpl2.startVolumeLevelCal(false);
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void enableCustomAudioCapture(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.95
            @Override // java.lang.Runnable
            public void run() {
                boolean z3 = TRTCCloudImpl.this.mEnableCustomAudioCapture;
                boolean z4 = z2;
                if (z3 == z4) {
                    return;
                }
                TRTCCloudImpl.this.mEnableCustomAudioCapture = z4;
                if (z2) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.mConfig.U |= 1;
                    if (tRTCCloudImpl.mCurrentRole == 21) {
                        tRTCCloudImpl.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.95.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                                if (tRTCCloudListener == null) {
                                    return;
                                }
                                tRTCCloudListener.onWarning(6001, "ignore send custom audio,for role audience", null);
                            }
                        });
                        TRTCCloudImpl.this.apiLog("ignore enableCustomAudioCapture,for role audience");
                    }
                } else {
                    TRTCCloudImpl.this.mConfig.U &= -2;
                }
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                tRTCCloudImpl2.mCaptureAndEnc.a(tRTCCloudImpl2.mConfig);
                TRTCCloudImpl.this.apiOnlineLog("enableCustomAudioCapture " + z2);
                if (!TRTCCloudImpl.this.mIsAudioCapturing) {
                    TRTCCloudImpl.this.enableAudioStream(z2);
                }
                if (z2) {
                    TRTCCloudImpl.this.setQoSParams();
                    TXCAudioEngineJNI.nativeUseSysAudioDevice(false);
                    TXCAudioEngine.getInstance().startLocalAudio(11, true);
                    TXCAudioEngine.getInstance().enableEncodedDataPackWithTRAEHeaderCallback(true);
                    TXCEventRecorderProxy.a("18446744073709551615", 3003, 11L, -1L, "", 0);
                } else {
                    TXCAudioEngine.getInstance().stopLocalAudio();
                }
                TXCKeyPointReportProxy.a(40050, z2 ? 1 : 0, 1);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void enableCustomAudioRendering(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.97
            @Override // java.lang.Runnable
            public void run() {
                if (TRTCCloudImpl.this.mEnableCustomAudioRendering == z2) {
                    return;
                }
                TRTCCloudImpl.this.apiOnlineLog("enableCustomAudioRendering enable:" + z2);
                TRTCCloudImpl.this.mEnableCustomAudioRendering = z2;
                TXCAudioEngineJNI.nativeEnableCustomAudioRendering(z2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void enableCustomVideoCapture(final int i2, final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.84
            @Override // java.lang.Runnable
            public void run() {
                if (i2 == 2) {
                    TRTCCloudImpl.this.enableSubStreamCustomCapture(z2);
                } else {
                    TRTCCloudImpl.this.enableMainStreamCustomCapture(z2);
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int enableEncSmallVideoStream(final boolean z2, final TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.49
            @Override // java.lang.Runnable
            public void run() {
                int i2;
                boolean z3;
                TRTCCloudImpl.this.mEnableSmallStream = z2;
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.enableNetworkSmallStream(tRTCCloudImpl.mEnableSmallStream);
                if (tRTCVideoEncParam != null) {
                    TRTCCloudImpl.this.mSmallEncParam.videoBitrate = tRTCVideoEncParam.videoBitrate;
                    TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate = tRTCVideoEncParam.minVideoBitrate;
                    TRTCCloudImpl.this.mSmallEncParam.videoFps = tRTCVideoEncParam.videoFps;
                    TRTCCloudImpl.this.mSmallEncParam.videoResolution = tRTCVideoEncParam.videoResolution;
                    TRTCCloudImpl.this.mSmallEncParam.videoResolutionMode = tRTCVideoEncParam.videoResolutionMode;
                    TRTCCloudImpl.this.mLatestParamsOfSmallEncoder.putInt(TRTCCloudImpl.KEY_CONFIG_FPS, tRTCVideoEncParam.videoFps);
                    TRTCCloudImpl.this.mLatestParamsOfSmallEncoder.putBoolean(TRTCCloudImpl.KEY_CONFIG_ADJUST_RESOLUTION, tRTCVideoEncParam.enableAdjustRes);
                }
                TRTCCloudImpl.this.apiLog("enableEncSmallVideoStream enable:" + z2 + " videoBitrate:" + TRTCCloudImpl.this.mSmallEncParam.videoBitrate + " minVideoBitrate:" + TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate + " videoFps:" + TRTCCloudImpl.this.mSmallEncParam.videoFps + " videoResolution:" + TRTCCloudImpl.this.mSmallEncParam.videoResolution + " videoResolutionMode:" + TRTCCloudImpl.this.mSmallEncParam.videoResolutionMode + " enableAdjustRes:" + TRTCCloudImpl.this.mSmallEncParam.enableAdjustRes);
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                com.tencent.liteav.g gVar = tRTCCloudImpl2.mConfig;
                boolean z4 = gVar.f19345s;
                int i3 = gVar.f19338l;
                if (tRTCCloudImpl2.mMainStreamVideoSourceType == VideoSourceType.SCREEN) {
                    if (TRTCCloudImpl.this.mOverrideFPSFromUser) {
                        TRTCCloudImpl.this.mSmallEncParam.videoFps = 10;
                    }
                    z3 = false;
                    i2 = 3;
                } else {
                    i2 = i3;
                    z3 = z4;
                }
                TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                tRTCCloudImpl3.mRoomInfo.smallEncSize = tRTCCloudImpl3.getSizeByResolution(tRTCCloudImpl3.mSmallEncParam.videoResolution, TRTCCloudImpl.this.mSmallEncParam.videoResolutionMode);
                TRTCCloudImpl tRTCCloudImpl4 = TRTCCloudImpl.this;
                com.tencent.liteav.d dVar = tRTCCloudImpl4.mCaptureAndEnc;
                boolean z5 = tRTCCloudImpl4.mEnableSmallStream;
                TRTCCloudImpl tRTCCloudImpl5 = TRTCCloudImpl.this;
                g.a aVar = tRTCCloudImpl5.mRoomInfo.smallEncSize;
                dVar.a(z5, aVar.f19354a, aVar.f19355b, tRTCCloudImpl5.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, i2);
                if (!TRTCCloudImpl.this.mEnableSmallStream) {
                    TRTCCloudImpl tRTCCloudImpl6 = TRTCCloudImpl.this;
                    tRTCCloudImpl6.setVideoEncoderConfiguration(3, 0, 0, 0, 0, 1, tRTCCloudImpl6.mConfig.f19345s, 0, false);
                    TRTCCloudImpl.this.removeUpStreamType(3);
                } else {
                    TRTCCloudImpl tRTCCloudImpl7 = TRTCCloudImpl.this;
                    g.a aVar2 = tRTCCloudImpl7.mRoomInfo.smallEncSize;
                    tRTCCloudImpl7.setVideoEncConfig(3, aVar2.f19354a, aVar2.f19355b, tRTCCloudImpl7.mSmallEncParam.videoFps, TRTCCloudImpl.this.mSmallEncParam.videoBitrate, z3, TRTCCloudImpl.this.mSmallEncParam.minVideoBitrate, false);
                    TRTCCloudImpl.this.addUpStreamType(3);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void enableMixExternalAudioFrame(boolean z2, boolean z3) {
        if (!z2) {
            int i2 = this.mPublishAudioTunnelId;
            if (i2 >= 0) {
                TXCAudioEngineJNI.nativeCloseAudioTunnel(i2);
                this.mPublishAudioTunnelId = -1;
            }
        } else if (this.mPublishAudioTunnelId < 0) {
            this.mPublishAudioTunnelId = TXCAudioEngineJNI.nativeOpenAudioTunnel(true);
        }
        if (z3) {
            if (this.mPlayoutAudioTunnelId < 0) {
                this.mPlayoutAudioTunnelId = TXCAudioEngineJNI.nativeOpenAudioTunnel(false);
            }
        } else {
            int i3 = this.mPlayoutAudioTunnelId;
            if (i3 >= 0) {
                TXCAudioEngineJNI.nativeCloseAudioTunnel(i3);
                this.mPlayoutAudioTunnelId = -1;
            }
        }
    }

    public void enableNetworkBlackStream(boolean z2) {
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(2);
        if (tRTCCloudImpl != null) {
            nativeEnableBlackStream(tRTCCloudImpl.getNetworkContext(), z2);
        }
    }

    public void enableNetworkSmallStream(boolean z2) {
        TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(2);
        if (tRTCCloudImpl != null) {
            nativeEnableSmallStream(tRTCCloudImpl.getNetworkContext(), z2);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public boolean enableTorch(boolean z2) {
        return this.mDeviceManager.enableCameraTorch(z2);
    }

    public void enableVideoStream(int i2, boolean z2) {
        if (!z2) {
            if (i2 == 2) {
                removeUpStreamType(7);
                return;
            }
            if (!this.mCaptureAndEnc.h()) {
                removeUpStreamType(2);
            }
            removeUpStreamType(3);
            return;
        }
        if (i2 == 2) {
            addUpStreamType(7);
            return;
        }
        addUpStreamType(2);
        if (this.mEnableSmallStream) {
            addUpStreamType(3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a3  */
    @Override // com.tencent.trtc.TRTCCloud
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void enterRoom(com.tencent.trtc.TRTCCloudDef.TRTCParams r17, final int r18) {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCCloudImpl.enterRoom(com.tencent.trtc.TRTCCloudDef$TRTCParams, int):void");
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void exitRoom() throws InterruptedException {
        runOnSDKThreadAndWaitDone(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.8
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.mConfig.f19336j = false;
                tRTCCloudImpl.exitRoomInternal(true, "call from api");
            }
        }, TimeUnit.SECONDS.toMillis(2L));
    }

    public void exitRoomInternal(boolean z2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.mH265Decision.setEnableH265EncodeByServer(false);
        this.mH265Decision.setEnableH265EncodeByPrivateAPI(false);
        String str2 = String.format(Locale.ENGLISH, "exitRoom %s, self: %d, reason: %s", Long.valueOf(this.mRoomInfo.getRoomId()), Integer.valueOf(hashCode()), str);
        apiOnlineLog(str2);
        Monitor.a(1, str2, "", 0);
        if (this.mRoomState == 0 && !this.mKeepAVCaptureWhenEnterRoomFailed) {
            clearRemoteMuteStates();
            Monitor.a();
            apiLog("exitRoom ignore when no in room.");
            return;
        }
        this.mRoomState = 0;
        this.mCaptureAndEnc.e();
        TXCSoundEffectPlayer.getInstance().stopAllEffect();
        stopCollectStatus();
        startVolumeLevelCal(false);
        this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.10
            @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
            public void accept(String str3, TRTCRoomInfo.UserInfo userInfo) {
                TRTCCloudImpl.this.stopRemoteRender(userInfo);
                com.tencent.liteav.audio.a.a().a(String.valueOf(userInfo.tinyID), TRTCCloudImpl.this.hashCode());
                TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                if (tXCRenderAndDec != null) {
                    tXCRenderAndDec.setVideoFrameListener(null, com.tencent.liteav.basic.enums.b.UNKNOWN);
                }
                TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                if (tXCRenderAndDec2 != null) {
                    tXCRenderAndDec2.setVideoFrameListener(null, com.tencent.liteav.basic.enums.b.UNKNOWN);
                }
            }
        });
        TXCAudioEngine.getInstance();
        TXCAudioEngine.setPlayoutDataListener(null);
        enableVideoStream(2, false);
        enableVideoStream(0, false);
        enableAudioStream(false);
        if (z2) {
            nativeExitRoom(this.mNativeRtcContext);
        }
        enableAudioEarMonitoring(false);
        stopBGM();
        TXAudioEffectManagerImpl.getInstance().stopAllMusics(false);
        TXAudioEffectManagerImpl.getAutoCacheHolder().stopAllMusics(false);
        TXAudioEffectManagerImpl.getCacheInstance().stopAllMusics(false);
        stopLocalAudioInternal();
        TXCKeyPointReportProxy.a(31004);
        stopLocalPreview();
        stopScreenCapture();
        TXCKeyPointReportProxy.b(31004, 0);
        com.tencent.liteav.g gVar = this.mConfig;
        gVar.D = null;
        gVar.F = 10;
        this.mRoomInfo.clear();
        this.mRenderListenerMap.clear();
        this.mEnableSmallStream = false;
        this.mEnableEosMode = false;
        this.mCodecType = 2;
        this.mRoomType = 0;
        this.mFramework = this.mOriginalFramework;
        this.mEnableSoftAEC = true;
        this.mEnableSoftANS = false;
        this.mEnableSoftAGC = false;
        this.mCaptureAndEnc.a(false);
        TXCAudioEngine.getInstance().muteLocalAudio(false);
        TXCAudioEngine.getInstance().clean();
        enableCustomAudioCapture(false);
        enableMixExternalAudioFrame(false, false);
        enableCustomVideoCapture(0, false);
        enableCustomVideoCapture(2, false);
        this.mCaptureAndEnc.a((o) null, 0);
        stopAudioRecording();
        stopLocalRecording();
        TXCSoundEffectPlayer.getInstance().clearCache();
        resetVolumeInternal();
        Monitor.a();
        TXCLog.i(TAG, "(%d) exitRoomInternal end", Integer.valueOf(hashCode()));
    }

    public void extractBizInfo(JSONObject jSONObject, String str, StringBuilder sb) {
        if (str.equals("strGroupId")) {
            sb.append(jSONObject.optString("strGroupId").toString());
            jSONObject.remove("strGroupId");
            jSONObject.remove("Role");
        }
        apiLog("extractBizInfo: key" + str + " value:" + sb.toString());
    }

    public void finalize() throws Throwable {
        super.finalize();
        try {
            destroy();
        } catch (Error | Exception unused) {
        }
    }

    public void flushBigVideoEncParamsIntoNetwork() {
        g.a aVar = this.mRoomInfo.bigEncSize;
        int i2 = aVar.f19354a;
        int i3 = aVar.f19355b;
        com.tencent.liteav.g gVar = this.mConfig;
        setVideoEncConfig(2, i2, i3, gVar.f19337k, gVar.f19331e, gVar.f19345s, gVar.f19333g, gVar.f19336j);
    }

    public void flushSmallVideoEncParamsIntoNetwork() {
        if (!this.mEnableSmallStream) {
            setVideoEncoderConfiguration(3, 0, 0, 0, 0, 1, this.mConfig.f19345s, 0, false);
            return;
        }
        g.a aVar = this.mRoomInfo.smallEncSize;
        int i2 = aVar.f19354a;
        int i3 = aVar.f19355b;
        TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam = this.mSmallEncParam;
        int i4 = tRTCVideoEncParam.videoFps;
        int i5 = tRTCVideoEncParam.videoBitrate;
        com.tencent.liteav.g gVar = this.mConfig;
        setVideoEncConfig(3, i2, i3, i4, i5, gVar.f19345s, tRTCVideoEncParam.minVideoBitrate, gVar.f19336j);
    }

    public void flushSubVideoEncParamsIntoNetwork() {
        com.tencent.liteav.g gVar = this.mSubStreamConfig;
        setVideoEncConfig(7, gVar.f19327a, gVar.f19328b, gVar.f19337k, gVar.f19331e, gVar.f19345s, gVar.f19333g, gVar.f19336j);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public long generateCustomPTS() {
        return TXCTimeUtil.generatePtsMS();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int getAudioCaptureVolume() {
        return this.mAudioCaptureVolume;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public TXAudioEffectManager getAudioEffectManager() {
        return TXAudioEffectManagerImpl.getAutoCacheHolder();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int getAudioPlayoutVolume() {
        return this.mAudioPlayoutVolume;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int getBGMDuration(String str) {
        return TXCLiveBGMPlayer.getInstance().getBGMDuration(str);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public TXBeautyManager getBeautyManager() {
        if (this.mCaptureAndEnc == null) {
            this.mCaptureAndEnc = new com.tencent.liteav.d(this.mContext);
        }
        return this.mCaptureAndEnc.b();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void getCustomAudioRenderingFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
        if (!this.mEnableCustomAudioRendering || tRTCAudioFrame == null) {
            return;
        }
        TXCAudioEngineJNI.nativeGetCustomAudioRenderingFrame(tRTCAudioFrame.data, tRTCAudioFrame.sampleRate, tRTCAudioFrame.channel);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public TXDeviceManager getDeviceManager() {
        return this.mDeviceManager;
    }

    public long getNetworkContext() {
        return this.mNativeRtcContext;
    }

    public int getNetworkQuality(int i2, int i3) {
        if (com.tencent.liteav.basic.util.h.d(this.mContext)) {
            return nativeGetNetworkQUality(this.mNativeRtcContext, i2, i3);
        }
        return 6;
    }

    public CharSequence getUploadStreamInfo() {
        int[] iArrA = com.tencent.liteav.basic.util.h.a();
        int iC = TXCStatus.c("18446744073709551615", 4003, 2);
        return String.format("LOCAL: [%s] RTT:%dms\n", this.mRoomInfo.getUserId(), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.quan))) + String.format(Locale.CHINA, "SEND:% 5dkbps LOSS:%d-%d-%d-%d|%d-%d-%d-%d|%d%%\n", Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.qrcodeimg_night)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.umeng_socialize_default_avatar, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.umeng_socialize_delete, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", 13013, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", 13010, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", 14011)), Integer.valueOf(TXCStatus.c("18446744073709551615", 14012)), Integer.valueOf(TXCStatus.c("18446744073709551615", 14013)), Integer.valueOf(TXCStatus.c("18446744073709551615", 14010)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.question_comment_flag))) + String.format(Locale.CHINA, "BIT:%d|%d|%dkbps RES:%dx%d FPS:%d-%d\n", Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.umeng_socialize_button_login, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.drawable.umeng_socialize_button_login, 3)), Integer.valueOf(TXCStatus.c("18446744073709551615", 14002)), Integer.valueOf(iC >> 16), Integer.valueOf(iC & 65535), Integer.valueOf((int) TXCStatus.d("18446744073709551615", 4001, 2)), Integer.valueOf((int) TXCStatus.d("18446744073709551615", 13014, 2))) + String.format(Locale.CHINA, "FEC:%d%%|%d%%  ARQ:%d|%dkbps  RPS:%d\n", Integer.valueOf(TXCStatus.c("18446744073709551615", 13004, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.id.category_layout)), Integer.valueOf(TXCStatus.c("18446744073709551615", 13008, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", 14008)), Integer.valueOf(TXCStatus.c("18446744073709551615", 13007, 2))) + String.format(Locale.CHINA, "CPU:%d%%|%d%%    QOS:%s|%dkbps|%d-%d\n", Integer.valueOf(iArrA[0] / 10), Integer.valueOf(iArrA[1] / 10), getQosValue(TXCStatus.c("18446744073709551615", R2.id.hide_text, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.id.headerview, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.id.hideable, 2)), Integer.valueOf(TXCStatus.c("18446744073709551615", R2.id.help_et, 2))) + String.format(Locale.CHINA, "SVR:%s", TXCStatus.b("18446744073709551615", 10001));
    }

    @Override // com.tencent.trtc.TRTCCloud
    public boolean isCameraAutoFocusFaceModeSupported() {
        return this.mCaptureAndEnc.o();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public boolean isCameraFocusPositionInPreviewSupported() {
        return this.mCaptureAndEnc.n();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public boolean isCameraTorchSupported() {
        return this.mCaptureAndEnc.m();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public boolean isCameraZoomSupported() {
        return this.mCaptureAndEnc.l();
    }

    public boolean isPublishingInCloud(TRTCCloudImpl tRTCCloudImpl, int i2) {
        synchronized (this.mCurrentPublishClouds) {
            return this.mCurrentPublishClouds.get(Integer.valueOf(i2)) == tRTCCloudImpl;
        }
    }

    public void makeStreamsEffectiveAfterNetworkInited() {
        enableNetworkSmallStream(this.mEnableSmallStream);
        enableNetworkBlackStream(this.mCaptureAndEnc.h());
        Iterator<Integer> it = this.mStreamTypes.iterator();
        while (it.hasNext()) {
            addUpStreamType(it.next().intValue());
        }
        flushBigVideoEncParamsIntoNetwork();
        flushSmallVideoEncParamsIntoNetwork();
        if (this.mSubStreamVideoSourceType != VideoSourceType.NONE) {
            flushSubVideoEncParamsIntoNetwork();
        }
        boolean z2 = this.mRoomInfo.muteLocalVideo;
        if (z2 && this.mConfig.D == null) {
            muteUpstream(2, z2);
        }
        boolean z3 = this.mRoomInfo.muteLocalSubVideo;
        if (z3) {
            muteUpstream(7, z3);
        }
        boolean z4 = this.mRoomInfo.muteLocalAudio;
        if (z4) {
            muteUpstream(1, z4);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int mixExternalAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
        byte[] bArr;
        if (tRTCAudioFrame == null || (bArr = tRTCAudioFrame.data) == null || bArr.length == 0) {
            return -2;
        }
        int i2 = this.mPublishAudioTunnelId;
        if (i2 < 0 && this.mPlayoutAudioTunnelId < 0) {
            return -1;
        }
        int iNativeWriteDataToTunnel = i2 >= 0 ? TXCAudioEngineJNI.nativeWriteDataToTunnel(i2, tRTCAudioFrame.sampleRate, tRTCAudioFrame.channel, 16, bArr) : Integer.MAX_VALUE;
        int i3 = this.mPlayoutAudioTunnelId;
        int iNativeWriteDataToTunnel2 = i3 >= 0 ? TXCAudioEngineJNI.nativeWriteDataToTunnel(i3, tRTCAudioFrame.sampleRate, tRTCAudioFrame.channel, 16, tRTCAudioFrame.data) : Integer.MAX_VALUE;
        if (iNativeWriteDataToTunnel < iNativeWriteDataToTunnel2) {
            return iNativeWriteDataToTunnel;
        }
        if (iNativeWriteDataToTunnel2 != Integer.MAX_VALUE) {
            return iNativeWriteDataToTunnel2;
        }
        return -4;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteAllRemoteAudio(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.60
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("muteAllRemoteAudio " + z2);
                TRTCRoomInfo tRTCRoomInfo = TRTCCloudImpl.this.mRoomInfo;
                tRTCRoomInfo.muteRemoteAudio = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                tRTCRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.60.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        userInfo.mainRender.muteAudio = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                        TXCAudioEngine.getInstance().muteRemoteAudio(String.valueOf(userInfo.tinyID), z2);
                        AnonymousClass60 anonymousClass60 = AnonymousClass60.this;
                        if (z2) {
                            TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                            tRTCCloudImpl.nativeCancelDownStream(tRTCCloudImpl.mNativeRtcContext, userInfo.tinyID, 1, true);
                        } else {
                            TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                            tRTCCloudImpl2.nativeRequestDownStream(tRTCCloudImpl2.mNativeRtcContext, userInfo.tinyID, 1, true);
                        }
                    }
                });
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteAllRemoteVideoStreams(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.38
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("muteAllRemoteVideoStreams mute " + z2);
                TRTCRoomInfo tRTCRoomInfo = TRTCCloudImpl.this.mRoomInfo;
                tRTCRoomInfo.muteRemoteVideo = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                tRTCRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.38.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        AnonymousClass38 anonymousClass38 = AnonymousClass38.this;
                        TRTCCloudImpl.this.updateRemoteVideoStatusByMute(userInfo, z2, 2);
                        AnonymousClass38 anonymousClass382 = AnonymousClass38.this;
                        TRTCCloudImpl.this.updateRemoteVideoStatusByMute(userInfo, z2, 7);
                        TRTCRoomInfo.RenderInfo renderInfo = userInfo.mainRender;
                        AnonymousClass38 anonymousClass383 = AnonymousClass38.this;
                        renderInfo.muteVideo = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                        TRTCCloudImpl.this.apiLog("muteRemoteVideoStream " + userInfo.userID + ", mute " + z2);
                        TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                        if (tXCRenderAndDec != null) {
                            tXCRenderAndDec.resetPeriodStatistics();
                            userInfo.mainRender.render.enableReport(!z2);
                        }
                        TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                        if (tXCRenderAndDec2 != null) {
                            tXCRenderAndDec2.resetPeriodStatistics();
                            userInfo.subRender.render.enableReport(!z2);
                        }
                        AnonymousClass38 anonymousClass384 = AnonymousClass38.this;
                        if (z2) {
                            TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                            tRTCCloudImpl.nativeCancelDownStream(tRTCCloudImpl.mNativeRtcContext, userInfo.tinyID, 2, true);
                            TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                            tRTCCloudImpl2.nativeCancelDownStream(tRTCCloudImpl2.mNativeRtcContext, userInfo.tinyID, 3, true);
                            TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                            tRTCCloudImpl3.nativeCancelDownStream(tRTCCloudImpl3.mNativeRtcContext, userInfo.tinyID, 7, true);
                        } else {
                            TXCRenderAndDec tXCRenderAndDec3 = userInfo.mainRender.render;
                            if (tXCRenderAndDec3 != null && tXCRenderAndDec3.isRendering()) {
                                TRTCCloudImpl tRTCCloudImpl4 = TRTCCloudImpl.this;
                                tRTCCloudImpl4.nativeRequestDownStream(tRTCCloudImpl4.mNativeRtcContext, userInfo.tinyID, userInfo.streamType, true);
                                TXCKeyPointReportProxy.a(String.valueOf(userInfo.tinyID), 40021, 0L, userInfo.streamType);
                            }
                            TXCRenderAndDec tXCRenderAndDec4 = userInfo.subRender.render;
                            if (tXCRenderAndDec4 != null && tXCRenderAndDec4.isRendering()) {
                                TRTCCloudImpl tRTCCloudImpl5 = TRTCCloudImpl.this;
                                tRTCCloudImpl5.nativeRequestDownStream(tRTCCloudImpl5.mNativeRtcContext, userInfo.tinyID, 7, true);
                                TXCKeyPointReportProxy.a(String.valueOf(userInfo.tinyID), 40021, 0L, 7);
                            }
                        }
                        TXCEventRecorderProxy.a(String.valueOf(userInfo.tinyID), R2.attr.zantd, z2 ? 1L : 0L, -1L, "", 2);
                        TXCEventRecorderProxy.a(String.valueOf(userInfo.tinyID), R2.attr.zantd, z2 ? 1L : 0L, -1L, "", 7);
                    }
                });
            }
        });
    }

    public void muteDuringAECWarmUp(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has("enabled")) {
            apiLog("muteDuringAECWarmUp without params.");
            return;
        }
        boolean z2 = jSONObject.optInt("enabled", 1) > 0;
        nativeSetAECMuteDataEnabled(this.mNativeRtcContext, z2);
        apiLog("muteDuringAECWarmUp enabled: " + z2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteLocalAudio(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.57
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("muteLocalAudio " + z2);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.muteLocalAudio(z2, tRTCCloudImpl);
                if (z2) {
                    TXCEventRecorderProxy.a("18446744073709551615", 3001, 1L, -1L, "", 0);
                } else {
                    TXCEventRecorderProxy.a("18446744073709551615", 3001, 3L, -1L, "", 0);
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteLocalVideo(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.30
            @Override // java.lang.Runnable
            public void run() {
                boolean z3;
                TRTCCloudImpl.this.apiOnlineLog("muteLocalVideo mute:" + z2 + ", pauseImg:" + TRTCCloudImpl.this.mConfig.D);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this.mCurrentPublishClouds.get(2);
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                if (tRTCCloudImpl == tRTCCloudImpl2 || (z3 = z2)) {
                    tRTCCloudImpl2.muteLocalVideo(0, z2, tRTCCloudImpl2);
                    return;
                }
                tRTCCloudImpl2.muteLocalVideo(0, z3, tRTCCloudImpl2);
                TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                tRTCCloudImpl3.muteLocalVideo(2, z2, tRTCCloudImpl3);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteRemoteAudio(final String str, final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.59
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    TRTCCloudImpl.this.apiLog("muteRemoteAudio " + str + " no exist.");
                    TRTCRoomInfo.UserInfo userInfoCreateUserInfo = TRTCCloudImpl.this.createUserInfo(str);
                    userInfoCreateUserInfo.mainRender.muteAudio = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                    TRTCCloudImpl.this.mRoomInfo.addUserInfo(str, userInfoCreateUserInfo);
                    return;
                }
                TRTCRoomInfo.RenderInfo renderInfo = user.mainRender;
                boolean z3 = z2;
                renderInfo.muteAudio = z3 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                TRTCCloudImpl.this.apiOnlineLog("muteRemoteAudio userId:%s mute:%b", str, Boolean.valueOf(z3));
                if (user.tinyID == 0) {
                    return;
                }
                TXCAudioEngine.getInstance().muteRemoteAudio(String.valueOf(user.tinyID), z2);
                if (z2) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.nativeCancelDownStream(tRTCCloudImpl.mNativeRtcContext, user.tinyID, 1, true);
                } else {
                    TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                    tRTCCloudImpl2.nativeRequestDownStream(tRTCCloudImpl2.mNativeRtcContext, user.tinyID, 1, true);
                }
            }
        });
    }

    public void muteRemoteAudioInSpeaker(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("muteRemoteAudioInSpeaker[lack parameter]");
            return;
        }
        if (!jSONObject.has("userID")) {
            apiLog("muteRemoteAudioInSpeaker[lack parameter]: userID");
            return;
        }
        String string = jSONObject.getString("userID");
        if (string == null) {
            apiLog("muteRemoteAudioInSpeaker[illegal type]: userID");
            return;
        }
        if (!jSONObject.has(i.f2279j)) {
            apiLog("muteRemoteAudioInSpeaker[lack parameter]: mute");
            return;
        }
        int i2 = jSONObject.getInt(i.f2279j);
        TRTCRoomInfo.UserInfo user = this.mRoomInfo.getUser(string);
        if (user != null) {
            TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(String.valueOf(user.tinyID), i2 == 1);
            return;
        }
        apiLog("muteRemoteAudioInSpeaker " + string + " no exist, create one.");
        TRTCRoomInfo.UserInfo userInfoCreateUserInfo = createUserInfo(string);
        userInfoCreateUserInfo.muteAudioInSpeaker = i2 == 1;
        this.mRoomInfo.addUserInfo(string, userInfoCreateUserInfo);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteRemoteVideoStream(final String str, final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.36
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("muteRemoteVideoStream userId:" + str + ", mute:" + z2);
                TRTCCloudImpl.this.muteRemoteVideoStreamInternal(str, 0, z2, true);
            }
        });
    }

    public void muteRemoteVideoStreamInternal(String str, int i2, boolean z2, boolean z3) {
        if (str.isEmpty()) {
            TXCLog.e(TAG, "muteRemoteVideoStream return. userId is null");
            return;
        }
        TRTCRoomInfo.UserInfo user = this.mRoomInfo.getUser(str);
        if (user == null) {
            apiLog("muteRemoteVideoStream " + str + " no exist.");
            TRTCRoomInfo.UserInfo userInfoCreateUserInfo = createUserInfo(str);
            if (i2 != 2) {
                TRTCRoomInfo.RenderInfo renderInfo = userInfoCreateUserInfo.mainRender;
                renderInfo.muteVideo = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                TRTCRoomInfo.RenderInfo renderInfo2 = userInfoCreateUserInfo.subRender;
                TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
                renderInfo2.muteVideo = tRTCRoomInfo.muteRemoteVideo;
                renderInfo.muteAudio = tRTCRoomInfo.muteRemoteAudio;
            } else {
                userInfoCreateUserInfo.subRender.muteVideo = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
                TRTCRoomInfo.RenderInfo renderInfo3 = userInfoCreateUserInfo.mainRender;
                TRTCRoomInfo tRTCRoomInfo2 = this.mRoomInfo;
                renderInfo3.muteVideo = tRTCRoomInfo2.muteRemoteVideo;
                renderInfo3.muteAudio = tRTCRoomInfo2.muteRemoteAudio;
            }
            this.mRoomInfo.addUserInfo(str, userInfoCreateUserInfo);
            return;
        }
        int i3 = i2 == 2 ? 7 : user.streamType;
        if (user.tinyID != 0) {
            updateRemoteVideoStatusByMute(user, z2, i3);
        }
        if (z3) {
            if (i3 != 7) {
                user.mainRender.muteVideo = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
            } else {
                user.subRender.muteVideo = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
            }
        }
        long j2 = user.tinyID;
        if (j2 == 0) {
            return;
        }
        if (!z2) {
            if (i3 != 7) {
                nativeRequestDownStream(this.mNativeRtcContext, j2, user.streamType, true);
            } else {
                nativeRequestDownStream(this.mNativeRtcContext, j2, 7, true);
            }
            TXCKeyPointReportProxy.a(String.valueOf(user.tinyID), 40021, 0L, i3);
        } else if (i3 != 7) {
            nativeCancelDownStream(this.mNativeRtcContext, j2, 2, true);
            nativeCancelDownStream(this.mNativeRtcContext, user.tinyID, 3, true);
        } else {
            nativeCancelDownStream(this.mNativeRtcContext, j2, 7, true);
        }
        if (i2 != 7) {
            TXCRenderAndDec tXCRenderAndDec = user.mainRender.render;
            if (tXCRenderAndDec != null) {
                tXCRenderAndDec.resetPeriodStatistics();
                user.mainRender.render.enableReport(!z2);
            }
        } else {
            TXCRenderAndDec tXCRenderAndDec2 = user.subRender.render;
            if (tXCRenderAndDec2 != null) {
                tXCRenderAndDec2.resetPeriodStatistics();
                user.subRender.render.enableReport(!z2);
            }
        }
        TXCEventRecorderProxy.a(String.valueOf(user.tinyID), R2.attr.zantd, z2 ? 1L : 0L, -1L, "", i3);
    }

    public native int nativeAddUpstream(long j2, int i2);

    public native long nativeCreateContext(int i2, int i3, int i4);

    public native void nativeDestroyContext(long j2);

    public native int nativeEnterRoom(long j2, long j3, String str, String str2, String str3, int i2, int i3, int i4, int i5, int i6, String str4, String str5, String str6, int i7, String str7, String str8, int i8, int i9, int i10, boolean z2, int i11);

    public native int nativeExitRoom(long j2);

    public native void nativeFlushC2SVideoCodecConfig(long j2, int i2, int i3);

    public native void nativeInit(long j2, int i2, String str, String str2, byte[] bArr);

    public native void nativeSetMixTranscodingConfig(long j2, TRTCTranscodingConfigInner tRTCTranscodingConfigInner);

    public native int nativeSetPriorRemoteVideoStreamType(long j2, int i2);

    public native void nativeSwitchRoom(long j2, long j3, String str, String str2, String str3);

    public void notifyCurrentEncodeType(final boolean z2) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.162
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("type", z2 ? 1 : 0);
                Object[] objArr = new Object[1];
                objArr[0] = z2 ? "H265" : j.a.f27383l;
                tRTCCloudListener.onWarning(1104, String.format("Current encoder type is %s encoder", objArr), bundle);
            }
        });
    }

    public void notifyEvent(final String str, final int i2, final Bundle bundle) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.194
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.appendDashboardLog(str, bundle.getInt("EVT_STREAM_TYPE", 2), bundle.getString(TXLiveConstants.EVT_DESCRIPTION, ""), String.format("event %d, ", Integer.valueOf(i2)));
                int i3 = i2;
                if (i3 == 2029) {
                    TRTCCloudImpl.this.apiLog("release mic~");
                    if (TRTCCloudImpl.this.mRoomInfo.isRoomExit()) {
                        TRTCCloudImpl.this.apiLog("onExitRoom when mic release.");
                        final int roomExitCode = TRTCCloudImpl.this.mRoomInfo.getRoomExitCode();
                        TRTCCloudImpl.this.mRoomInfo.setRoomExit(false, 0);
                        TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.194.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                                if (tRTCCloudListener != null) {
                                    tRTCCloudListener.onExitRoom(roomExitCode);
                                }
                            }
                        });
                    } else {
                        TRTCCloudImpl.this.mRoomInfo.micStart(false);
                    }
                } else if (i3 == 2027) {
                    TRTCCloudImpl.this.apiLog(String.format("onMicDidReady~", new Object[0]));
                    TRTCCloudImpl.this.mRoomInfo.micStart(true);
                } else {
                    if (i3 == -2304 || i3 == -2312) {
                        TXCLog.i(TRTCCloudImpl.TAG, "codecability eventCode = ERR_H265_DECODE_FAIL");
                        TRTCCloudImpl.this.mH265Decision.setEnableH265EncodeByPrivateAPI(false, TRTCEncodeTypeDecision.ModifyCodecReason.REASON_DECODE_ERROR);
                        TRTCCloudImpl.this.mCaptureAndEnc.j(false);
                        TRTCCloudImpl.this.notifyCurrentEncodeType(false);
                        TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                        tRTCCloudImpl.nativeFlushC2SVideoCodecConfig(tRTCCloudImpl.mNativeRtcContext, 3, g.a.CODEC_TYPE_H264.a());
                        return;
                    }
                    if (i3 == -2310 || i3 == -2311) {
                        TXCLog.i(TRTCCloudImpl.TAG, "codecability eventCode: " + i2);
                        TRTCCloudImpl.this.mH265Decision.setEnableH265EncodeByPrivateAPI(false, TRTCEncodeTypeDecision.ModifyCodecReason.REASON_ENCODE_ERROR);
                        int h265DecoderValue = TRTCEncodeTypeDecision.getH265DecoderValue() | 3;
                        TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                        tRTCCloudImpl2.nativeFlushC2SVideoCodecConfig(tRTCCloudImpl2.mNativeRtcContext, h265DecoderValue, g.a.CODEC_TYPE_H264.a());
                        return;
                    }
                    if (i3 == 1008) {
                        TXCEventRecorderProxy.a("18446744073709551615", R2.attr.zantong_new, ((Integer) r1.first).intValue(), -1L, (String) TRTCCloudImpl.this.getEncoderTypeAndMsg(bundle).second, bundle.getInt("EVT_STREAM_TYPE", 0));
                    }
                }
                final int iTranslateStreamType = TRTCCloudImpl.this.translateStreamType(bundle.getInt("EVT_STREAM_TYPE", 2));
                TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.194.2
                    @Override // java.lang.Runnable
                    public void run() {
                        AnonymousClass194 anonymousClass194 = AnonymousClass194.this;
                        TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                        TRTCCloudListener tRTCCloudListener = tRTCCloudImpl3.mTRTCListener;
                        if (tRTCCloudListener == null) {
                            return;
                        }
                        int i4 = i2;
                        if (i4 == 2003) {
                            String str2 = str;
                            if (str2 != null && str2.equals(tRTCCloudImpl3.mRoomInfo.getUserId())) {
                                TRTCCloudImpl.this.apiLog("onFirstVideoFrame local.");
                                tRTCCloudListener.onFirstVideoFrame(null, iTranslateStreamType, bundle.getInt("EVT_PARAM1"), bundle.getInt("EVT_PARAM2"));
                                return;
                            }
                            TRTCCloudImpl.this.apiLog("onFirstVideoFrame " + str);
                            AnonymousClass194 anonymousClass1942 = AnonymousClass194.this;
                            tRTCCloudListener.onFirstVideoFrame(str, iTranslateStreamType, bundle.getInt("EVT_PARAM1"), bundle.getInt("EVT_PARAM2"));
                            return;
                        }
                        if (i4 == 2026) {
                            tRTCCloudImpl3.apiLog("onFirstAudioFrame " + str);
                            tRTCCloudListener.onFirstAudioFrame(str);
                            AnonymousClass194 anonymousClass1943 = AnonymousClass194.this;
                            TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                            if (user != null) {
                                TXCKeyPointReportProxy.b(user.tinyID + "", 32006);
                                return;
                            }
                            return;
                        }
                        if (i4 == 1003) {
                            tRTCCloudListener.onCameraDidReady();
                            TRTCCloudImpl.this.apiOnlineLog("onCameraDidReady");
                            return;
                        }
                        if (i4 == 2027) {
                            tRTCCloudListener.onMicDidReady();
                            TRTCCloudImpl.this.apiOnlineLog("onMicDidReady");
                            return;
                        }
                        if (i4 == 1008) {
                            return;
                        }
                        if (i4 < 0) {
                            tRTCCloudListener.onError(i4, bundle.getString(TXLiveConstants.EVT_DESCRIPTION, ""), bundle);
                            Monitor.a(3, String.format("onError event:%d, msg:%s", Integer.valueOf(i2), bundle) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            TXCKeyPointReportProxy.b(i2);
                            return;
                        }
                        if ((i4 <= 1100 || i4 >= 1110) && ((i4 <= 1200 || i4 >= 1206) && ((i4 <= 2100 || i4 >= 2110) && ((i4 <= 3001 || i4 >= 3011) && (i4 <= 5100 || i4 >= 5104))))) {
                            return;
                        }
                        tRTCCloudListener.onWarning(i4, bundle.getString(TXLiveConstants.EVT_DESCRIPTION, ""), bundle);
                        if (i2 != 2105) {
                            Monitor.a(4, String.format("onWarning event:%d, msg:%s", Integer.valueOf(i2), bundle) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                        }
                        int i5 = i2;
                        if (i5 == 1103 || i5 == 1109 || i5 == 2106 || i5 == 2109 || i5 == 2101 || i5 == 2102) {
                            TXCKeyPointReportProxy.b(i5);
                        }
                    }
                });
            }
        });
    }

    public void onAVMemberEnter(final long j2, final String str, final int i2, final int i3) {
        final WeakReference weakReference = new WeakReference(this);
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.155
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                if (tRTCCloudImpl.mRoomState == 0) {
                    tRTCCloudImpl.apiLog("ignore onAVMemberEnter when out room.");
                    return;
                }
                if (((TRTCCloudImpl) weakReference.get()) == null) {
                    return;
                }
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user != null) {
                    TRTCCloudImpl.this.apiLog(" user " + str + "enter room when user is in room " + j2);
                }
                String strValueOf = String.valueOf(j2);
                if (user == null) {
                    user = TRTCCloudImpl.this.createUserInfo(str);
                }
                TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(strValueOf, TRTCCloudImpl.this);
                if (TRTCCloudImpl.this.mAudioFrameListener != null) {
                    TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(strValueOf, TRTCCloudImpl.this);
                }
                com.tencent.liteav.audio.a.a().a(strValueOf, true, TRTCCloudImpl.this.hashCode());
                TXCAudioEngine.getInstance().muteRemoteAudioInSpeaker(strValueOf, user.muteAudioInSpeaker);
                TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState = user.mainRender.muteAudio;
                TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState2 = TRTCRoomInfo.TRTCRemoteMuteState.MUTE;
                if (tRTCRemoteMuteState == tRTCRemoteMuteState2) {
                    TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                    tRTCCloudImpl2.nativeCancelDownStream(tRTCCloudImpl2.mNativeRtcContext, j2, 1, true);
                    TXCAudioEngine.getInstance().muteRemoteAudio(strValueOf, true);
                } else if (tRTCRemoteMuteState == TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE) {
                    TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                    tRTCCloudImpl3.nativeRequestDownStream(tRTCCloudImpl3.mNativeRtcContext, j2, 1, true);
                    TXCAudioEngine.getInstance().muteRemoteAudio(strValueOf, false);
                }
                TRTCCloudImpl tRTCCloudImpl4 = TRTCCloudImpl.this;
                TXCRenderAndDec tXCRenderAndDecCreateRender = tRTCCloudImpl4.createRender(j2, tRTCCloudImpl4.mPriorStreamType);
                RenderListenerAdapter renderListenerAdapter = TRTCCloudImpl.this.mRenderListenerMap.get(str);
                if (renderListenerAdapter != null) {
                    renderListenerAdapter.strTinyID = strValueOf;
                    if (renderListenerAdapter.listener != null) {
                        TRTCCloudImpl tRTCCloudImpl5 = TRTCCloudImpl.this;
                        tXCRenderAndDecCreateRender.setVideoFrameListener(tRTCCloudImpl5, tRTCCloudImpl5.getPixelFormat(renderListenerAdapter.pixelFormat));
                    }
                }
                long j3 = j2;
                user.tinyID = j3;
                user.userID = str;
                user.terminalType = i2;
                user.streamState = i3;
                TRTCRoomInfo.RenderInfo renderInfo = user.mainRender;
                renderInfo.render = tXCRenderAndDecCreateRender;
                renderInfo.tinyID = j3;
                user.streamType = TRTCCloudImpl.this.mPriorStreamType;
                if (renderInfo.startRenderView.booleanValue()) {
                    TRTCCloudImpl tRTCCloudImpl6 = TRTCCloudImpl.this;
                    String str2 = str;
                    TRTCRoomInfo.RenderInfo renderInfo2 = user.mainRender;
                    tRTCCloudImpl6.setRenderView(str2, renderInfo2, renderInfo2.view, user.debugMargin);
                    TRTCCloudImpl.this.apiLog(String.format("startRemoteView when user enter userID:%s tinyID:%d streamType:%d", str, Long.valueOf(user.tinyID), Integer.valueOf(user.streamType)));
                    TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(user.tinyID), user.streamType, 0, "Start watching " + str);
                    TXCEventRecorderProxy.a(String.valueOf(user.tinyID), R2.attr.zantong, 1L, -1L, "", user.streamType);
                    TRTCCloudImpl.this.startRemoteRender(user.mainRender.render, user.streamType);
                    TRTCCloudImpl.this.muteRemoteVideoStreamInternal(user.userID, 0, user.mainRender.muteVideo == tRTCRemoteMuteState2, false);
                }
                TRTCRoomInfo.RenderInfo renderInfo3 = user.mainRender;
                renderInfo3.render.setRenderRotation(renderInfo3.rotation);
                TRTCRoomInfo.RenderInfo renderInfo4 = user.mainRender;
                renderInfo4.render.setRenderMode(renderInfo4.renderMode);
                TRTCRoomInfo.RenderInfo renderInfo5 = user.mainRender;
                if (renderInfo5.mirrorType == 1) {
                    renderInfo5.render.setRenderMirrorType(1);
                } else {
                    renderInfo5.render.setRenderMirrorType(2);
                }
                TXCRenderAndDec tXCRenderAndDecCreateRender2 = TRTCCloudImpl.this.createRender(j2, 7);
                TRTCRoomInfo.RenderInfo renderInfo6 = user.subRender;
                renderInfo6.render = tXCRenderAndDecCreateRender2;
                renderInfo6.tinyID = j2;
                if (renderInfo6.startRenderView.booleanValue()) {
                    TRTCCloudImpl tRTCCloudImpl7 = TRTCCloudImpl.this;
                    String str3 = str;
                    TRTCRoomInfo.RenderInfo renderInfo7 = user.subRender;
                    tRTCCloudImpl7.setRenderView(str3, renderInfo7, renderInfo7.view, user.debugMargin);
                    TRTCCloudImpl.this.apiOnlineLog(String.format("onUserScreenAvailable when user enter userID:%s tinyID:%d streamType:%d", str, Long.valueOf(user.tinyID), 7));
                    TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(user.tinyID), 7, 0, "Start watching " + str);
                    TXCEventRecorderProxy.a(String.valueOf(user.tinyID), R2.attr.zantong, 1L, -1L, "", 7);
                    TRTCCloudImpl.this.startRemoteRender(user.subRender.render, 7);
                    TRTCCloudImpl.this.muteRemoteVideoStreamInternal(user.userID, 2, user.subRender.muteVideo == tRTCRemoteMuteState2, false);
                }
                TRTCRoomInfo.RenderInfo renderInfo8 = user.subRender;
                renderInfo8.render.setRenderRotation(renderInfo8.rotation);
                TRTCRoomInfo.RenderInfo renderInfo9 = user.subRender;
                renderInfo9.render.setRenderMode(renderInfo9.renderMode);
                TRTCRoomInfo.RenderInfo renderInfo10 = user.subRender;
                if (renderInfo10.mirrorType == 1) {
                    renderInfo10.render.setRenderMirrorType(1);
                } else {
                    renderInfo10.render.setRenderMirrorType(2);
                }
                TRTCCloudImpl.this.mRoomInfo.addUserInfo(str, user);
                TRTCCloudImpl.this.apiLog("onAVMemberEnter " + j2 + ", " + str + ", " + i3);
                TRTCCloudImpl tRTCCloudImpl8 = TRTCCloudImpl.this;
                final TRTCCloudListener tRTCCloudListener = tRTCCloudImpl8.mTRTCListener;
                tRTCCloudImpl8.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.155.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TRTCCloudListener tRTCCloudListener2 = tRTCCloudListener;
                        if (tRTCCloudListener2 != null) {
                            tRTCCloudListener2.onUserEnter(str);
                        }
                    }
                });
                final boolean z2 = TRTCRoomInfo.hasAudio(i3) && !TRTCRoomInfo.isMuteAudio(i3);
                if (z2) {
                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.155.2
                        @Override // java.lang.Runnable
                        public void run() {
                            TRTCCloudListener tRTCCloudListener2 = tRTCCloudListener;
                            if (tRTCCloudListener2 != null) {
                                tRTCCloudListener2.onUserAudioAvailable(str, z2);
                            }
                            Monitor.a(2, String.format("onUserAudioAvailable userID:%s, bAvailable:%b", str, Boolean.valueOf(z2)) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                        }
                    });
                    TRTCCloudImpl tRTCCloudImpl9 = TRTCCloudImpl.this;
                    tRTCCloudImpl9.appendDashboardLog(tRTCCloudImpl9.mRoomInfo.getUserId(), 0, String.format("[%s]audio Available[true]", str));
                }
                final boolean z3 = (TRTCRoomInfo.hasMainVideo(i3) || TRTCRoomInfo.hasSmallVideo(i3)) && !TRTCRoomInfo.isMuteMainVideo(i3);
                TRTCCloudImpl tRTCCloudImpl10 = TRTCCloudImpl.this;
                int i4 = tRTCCloudImpl10.mRecvMode;
                boolean z4 = (i4 == 3 || i4 == 1) ? false : true;
                if (z3 && (tRTCCloudImpl10.mRoomInfo.hasRecvFirstIFrame(j2, 2) || z4)) {
                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.155.3
                        @Override // java.lang.Runnable
                        public void run() {
                            TRTCCloudListener tRTCCloudListener2 = tRTCCloudListener;
                            if (tRTCCloudListener2 != null) {
                                tRTCCloudListener2.onUserVideoAvailable(str, z3);
                            }
                            Monitor.a(2, String.format("onUserVideoAvailable userID:%s, bAvailable:%b", str, Boolean.valueOf(z3)) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                        }
                    });
                    TRTCCloudImpl tRTCCloudImpl11 = TRTCCloudImpl.this;
                    tRTCCloudImpl11.appendDashboardLog(tRTCCloudImpl11.mRoomInfo.getUserId(), 0, String.format("[%s]video Available[true]", str));
                }
                final boolean z5 = TRTCRoomInfo.hasSubVideo(i3) && !TRTCRoomInfo.isMuteSubVideo(i3);
                if (z5) {
                    TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.155.4
                        @Override // java.lang.Runnable
                        public void run() {
                            TRTCCloudListener tRTCCloudListener2 = tRTCCloudListener;
                            if (tRTCCloudListener2 != null) {
                                tRTCCloudListener2.onUserSubStreamAvailable(str, z5);
                            }
                            Monitor.a(2, String.format("onUserSubStreamAvailable userID:%s, bAvailable:%b", str, Boolean.valueOf(z5)) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                        }
                    });
                    TRTCCloudImpl tRTCCloudImpl12 = TRTCCloudImpl.this;
                    tRTCCloudImpl12.appendDashboardLog(tRTCCloudImpl12.mRoomInfo.getUserId(), 0, String.format("[%s]subvideo Available[true]", str));
                }
                TRTCCloudImpl tRTCCloudImpl13 = TRTCCloudImpl.this;
                tRTCCloudImpl13.notifyEvent(tRTCCloudImpl13.mRoomInfo.getUserId(), 0, String.format("[%s]enter room", str));
            }
        });
    }

    public void onAVMemberExit(final long j2, final String str, int i2, final int i3) {
        final WeakReference weakReference = new WeakReference(this);
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.156
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                if (tRTCCloudImpl.mRoomState == 0) {
                    tRTCCloudImpl.apiLog("ignore onAVMemberExit when out room.");
                    return;
                }
                if (((TRTCCloudImpl) weakReference.get()) == null) {
                    return;
                }
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user != null) {
                    TRTCCloudImpl.this.onRemoteVideoStatusUpdatedInternal(user, 2, 0, 6);
                    TRTCCloudImpl.this.onRemoteVideoStatusUpdatedInternal(user, 7, 0, 6);
                    TRTCCloudImpl.this.stopRemoteRender(user);
                    TRTCCloudImpl.this.mRoomInfo.removeRenderInfo(user.userID);
                } else {
                    TRTCCloudImpl.this.apiLog("user " + str + " exit room when user is not in room " + j2);
                }
                com.tencent.liteav.audio.a.a().a(String.valueOf(j2), TRTCCloudImpl.this.hashCode());
                TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(j2), null);
                TXCAudioEngine.getInstance().setRemoteAudioStreamEventListener(String.valueOf(j2), null);
                TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.156.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TRTCCloudImpl.this.apiLog("onAVMemberExit " + j2 + ", " + str + ", " + i3);
                        AnonymousClass156 anonymousClass156 = AnonymousClass156.this;
                        TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                        if (tRTCCloudListener != null) {
                            if (TRTCRoomInfo.hasAudio(i3) && !TRTCRoomInfo.isMuteAudio(i3)) {
                                tRTCCloudListener.onUserAudioAvailable(str, false);
                                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                                String userId = tRTCCloudImpl2.mRoomInfo.getUserId();
                                Boolean bool = Boolean.FALSE;
                                tRTCCloudImpl2.appendDashboardLog(userId, 0, String.format("[%s]audio Available[%b]", str, bool));
                                Monitor.a(2, String.format("onUserAudioAvailable userID:%s, bAvailable:%b", str, bool) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            }
                            if ((TRTCRoomInfo.hasMainVideo(i3) || TRTCRoomInfo.hasSmallVideo(i3)) && !TRTCRoomInfo.isMuteMainVideo(i3)) {
                                tRTCCloudListener.onUserVideoAvailable(str, false);
                                TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                                String userId2 = tRTCCloudImpl3.mRoomInfo.getUserId();
                                Boolean bool2 = Boolean.FALSE;
                                tRTCCloudImpl3.appendDashboardLog(userId2, 0, String.format("[%s]video Available[%b]", str, bool2));
                                Monitor.a(2, String.format("onUserVideoAvailable userID:%s, bAvailable:%b", str, bool2) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            }
                            if (TRTCRoomInfo.hasSubVideo(i3) && !TRTCRoomInfo.isMuteSubVideo(i3)) {
                                tRTCCloudListener.onUserSubStreamAvailable(str, false);
                                TRTCCloudImpl tRTCCloudImpl4 = TRTCCloudImpl.this;
                                String userId3 = tRTCCloudImpl4.mRoomInfo.getUserId();
                                Boolean bool3 = Boolean.FALSE;
                                tRTCCloudImpl4.appendDashboardLog(userId3, 0, String.format("[%s]subVideo Available[%b]", str, bool3));
                                Monitor.a(2, String.format("onUserSubStreamAvailable userID:%s, bAvailable:%b", str, bool3) + " self:" + TRTCCloudImpl.this.hashCode(), "", 0);
                            }
                            tRTCCloudListener.onUserExit(str, 0);
                        }
                    }
                });
            }
        });
        notifyEvent(this.mRoomInfo.getUserId(), 0, String.format("[%s]leave room", str));
    }

    public void onAudioJitterBufferError(String str, int i2, String str2) {
    }

    @Override // com.tencent.liteav.audio.d
    public void onAudioJitterBufferNotify(final String str, final int i2, final String str2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.137
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putLong("EVT_ID", i2);
                bundle.putLong("EVT_TIME", System.currentTimeMillis());
                bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str2);
                TRTCCloudImpl.this.notifyEventByUserId(str, i2, bundle);
            }
        });
    }

    @Override // com.tencent.liteav.audio.f
    public void onAudioPlayPcmData(final String str, final byte[] bArr, final long j2, final int i2, final int i3, final byte[] bArr2) {
        if (str != null) {
            runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.136
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener = TRTCCloudImpl.this.mAudioFrameListener;
                    if (tRTCAudioFrameListener != null) {
                        TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame = new TRTCCloudDef.TRTCAudioFrame();
                        tRTCAudioFrame.data = bArr;
                        tRTCAudioFrame.timestamp = j2;
                        tRTCAudioFrame.sampleRate = i2;
                        tRTCAudioFrame.channel = i3;
                        tRTCAudioFrame.extraData = bArr2;
                        try {
                            tRTCAudioFrameListener.onRemoteUserAudioFrame(tRTCAudioFrame, TRTCCloudImpl.this.mRoomInfo.getUserIdByTinyId(Long.valueOf(str).longValue()));
                        } catch (Exception e2) {
                            TXCLog.e(TRTCCloudImpl.TAG, "onPlayAudioFrame failed." + e2.getMessage());
                        }
                    }
                }
            });
            return;
        }
        TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener = this.mAudioFrameListener;
        if (tRTCAudioFrameListener != null) {
            TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame = new TRTCCloudDef.TRTCAudioFrame();
            tRTCAudioFrame.data = bArr;
            tRTCAudioFrame.timestamp = j2;
            tRTCAudioFrame.sampleRate = i2;
            tRTCAudioFrame.channel = i3;
            tRTCAudioFrameListener.onMixedPlayAudioFrame(tRTCAudioFrame);
        }
    }

    public void onAudioQosChanged(int i2, int i3, int i4) {
        onAudioQosChanged(this, i2, i3, i4);
    }

    @Override // com.tencent.liteav.d.a
    public void onBackgroudPushStop() {
    }

    public void onCancelTranscoding(final int i2, final String str) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.181
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog(String.format("onCancelTranscoding err:%d, msg:%s", Integer.valueOf(i2), str));
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onSetMixTranscodingConfig(i2, str);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.b
    public void onEffectPlayFinish(final int i2) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.109
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("onEffectPlayFinish -> effectId = " + i2);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onAudioEffectFinished(i2, 0);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.b
    public void onEffectPlayStart(final int i2, final int i3) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.110
            @Override // java.lang.Runnable
            public void run() {
                int i4;
                TRTCCloudImpl.this.apiLog("onEffectPlayStart -> effectId = " + i2 + " code = " + i3);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener == null || (i4 = i3) >= 0) {
                    return;
                }
                tRTCCloudListener.onAudioEffectFinished(i2, i4);
            }
        });
    }

    @Override // com.tencent.liteav.d.a
    public void onEncVideo(TXSNALPacket tXSNALPacket) {
        if (tXSNALPacket == null) {
            return;
        }
        synchronized (this.mNativeLock) {
            pushVideoFrame(tXSNALPacket);
        }
    }

    @Override // com.tencent.liteav.d.a
    public void onEncVideoFormat(MediaFormat mediaFormat) {
    }

    public void onEnterRoom(final int i2, final String str) {
        apiOnlineLog("onEnterRoom err:" + i2 + ", msg:" + str);
        if (i2 == 0) {
            TXCEventRecorderProxy.a("18446744073709551615", 5003, 1L, -1L, "", 0);
        } else {
            TXCEventRecorderProxy.a("18446744073709551615", 5003, 0L, -1L, "", 0);
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.143
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                int i3 = i2;
                if (i3 == 0) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.mRoomState = 2;
                    TRTCRoomInfo tRTCRoomInfo = tRTCCloudImpl.mRoomInfo;
                    tRTCRoomInfo.networkStatus = 3;
                    tRTCCloudImpl.notifyEvent(tRTCRoomInfo.getUserId(), 0, "Enter room success");
                    TXCKeyPointReportProxy.b(30001, i2);
                    return;
                }
                if (i3 != -3340) {
                    if (TRTCCloudImpl.this.mKeepAVCaptureWhenEnterRoomFailed) {
                        TRTCCloudImpl.this.mRoomState = 0;
                    } else {
                        TRTCCloudImpl.this.exitRoomInternal(false, "enter room failed");
                    }
                    TXCKeyPointReportProxy.b(30001, i2);
                }
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                tRTCCloudImpl2.notifyEvent(tRTCCloudImpl2.mRoomInfo.getUserId(), i2, "Enter room fail " + str);
            }
        });
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.144
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                TRTCCloudListener tRTCCloudListener = tRTCCloudImpl.mTRTCListener;
                if (tRTCCloudListener != null) {
                    int i3 = i2;
                    if (i3 == 0) {
                        tRTCCloudListener.onEnterRoom(tRTCCloudImpl.mRoomInfo.getRoomElapsed());
                    } else {
                        tRTCCloudListener.onEnterRoom(i3);
                    }
                }
            }
        });
    }

    public void onIdrFpsChanged(int i2) {
        onIdrFpsChanged(this, i2);
    }

    @Override // com.tencent.liteav.audio.e
    public void onMixedAllData(byte[] bArr, int i2, int i3) {
        final TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame = new TRTCCloudDef.TRTCAudioFrame();
        tRTCAudioFrame.data = bArr;
        tRTCAudioFrame.timestamp = 0L;
        tRTCAudioFrame.sampleRate = i2;
        tRTCAudioFrame.channel = i3;
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.138
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener = TRTCCloudImpl.this.mAudioFrameListener;
                if (tRTCAudioFrameListener != null) {
                    tRTCAudioFrameListener.onMixedAllAudioFrame(tRTCAudioFrame);
                }
            }
        });
    }

    @Override // com.tencent.liteav.basic.b.b
    public void onNotifyEvent(final int i2, final Bundle bundle) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.134
            @Override // java.lang.Runnable
            public void run() {
                Bundle bundle2 = bundle;
                if (bundle2 == null) {
                    return;
                }
                String string = bundle2.getString("EVT_USERID", "");
                if (!TextUtils.isEmpty(string) && !string.equalsIgnoreCase("18446744073709551615") && !string.equalsIgnoreCase(TRTCCloudImpl.this.mRoomInfo.getTinyId())) {
                    TRTCCloudImpl.this.notifyEventByUserId(string, i2, bundle);
                } else {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.notifyEvent(tRTCCloudImpl.mRoomInfo.getUserId(), i2, bundle);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.h
    public void onPlayEnd(final int i2) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.140
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloud.BGMNotify bGMNotify = TRTCCloudImpl.this.mBGMNotify;
                if (bGMNotify != null) {
                    bGMNotify.onBGMComplete(i2);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.h
    public void onPlayProgress(final long j2, final long j3) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.141
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloud.BGMNotify bGMNotify = TRTCCloudImpl.this.mBGMNotify;
                if (bGMNotify != null) {
                    bGMNotify.onBGMProgress(j2, j3);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.h
    public void onPlayStart() {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.139
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloud.BGMNotify bGMNotify = TRTCCloudImpl.this.mBGMNotify;
                if (bGMNotify != null) {
                    bGMNotify.onBGMStart(0);
                }
            }
        });
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordEncData(byte[] bArr, long j2, int i2, int i3, int i4) {
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordError(int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("EVT_USERID", "18446744073709551615");
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        TXCLog.e(TAG, "onRecordError code = " + i2 + ":" + str + " self:" + hashCode());
        if (i2 == -1) {
            bundle.putInt("EVT_ID", -1302);
            onNotifyEvent(-1302, bundle);
        }
        if (i2 == -6) {
            bundle.putInt("EVT_ID", 2027);
            onNotifyEvent(2027, bundle);
        }
        if (i2 == -7) {
            bundle.putInt("EVT_ID", 2029);
            onNotifyEvent(2029, bundle);
        }
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordPcmData(byte[] bArr, long j2, int i2, int i3, int i4) {
        TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener = this.mAudioFrameListener;
        if (tRTCAudioFrameListener != null) {
            TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame = new TRTCCloudDef.TRTCAudioFrame();
            tRTCAudioFrame.data = bArr;
            tRTCAudioFrame.timestamp = j2;
            tRTCAudioFrame.sampleRate = i2;
            tRTCAudioFrame.channel = i3;
            tRTCAudioFrameListener.onLocalProcessedAudioFrame(tRTCAudioFrame);
            byte[] bArr2 = tRTCAudioFrame.extraData;
            if (bArr2 == null || tRTCAudioFrame.data.length == 0 || bArr2.length >= 100) {
                return;
            }
            TXCAudioEngine.getInstance().setAudioFrameExtraData(tRTCAudioFrame.extraData);
        }
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordRawPcmData(byte[] bArr, long j2, int i2, int i3, int i4, boolean z2) {
        TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener = this.mAudioFrameListener;
        if (tRTCAudioFrameListener != null) {
            TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame = new TRTCCloudDef.TRTCAudioFrame();
            tRTCAudioFrame.data = bArr;
            tRTCAudioFrame.timestamp = j2;
            tRTCAudioFrame.sampleRate = i2;
            tRTCAudioFrame.channel = i3;
            tRTCAudioFrameListener.onCapturedRawAudioFrame(tRTCAudioFrame);
        }
    }

    @Override // com.tencent.liteav.o
    public void onRenderVideoFrame(String str, int i2, TXSVideoFrame tXSVideoFrame) {
        TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener;
        String key;
        if (tXSVideoFrame == null) {
            return;
        }
        TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame = new TRTCCloudDef.TRTCVideoFrame();
        tRTCVideoFrame.width = tXSVideoFrame.width;
        tRTCVideoFrame.height = tXSVideoFrame.height;
        tRTCVideoFrame.rotation = tXSVideoFrame.rotation;
        tRTCVideoFrame.timestamp = tXSVideoFrame.pts;
        int iTranslateStreamType = translateStreamType(i2);
        boolean z2 = TextUtils.isEmpty(str) || str.equalsIgnoreCase("18446744073709551615") || str.equalsIgnoreCase(this.mRoomInfo.getTinyId());
        if (!z2) {
            Iterator<Map.Entry<String, RenderListenerAdapter>> it = this.mRenderListenerMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    tRTCVideoRenderListener = null;
                    key = "";
                    break;
                }
                Map.Entry<String, RenderListenerAdapter> next = it.next();
                RenderListenerAdapter value = next.getValue();
                if (value != null && str.equalsIgnoreCase(next.getValue().strTinyID)) {
                    tRTCVideoFrame.pixelFormat = value.pixelFormat;
                    tRTCVideoFrame.bufferType = value.bufferType;
                    tRTCVideoRenderListener = value.listener;
                    key = next.getKey();
                    break;
                }
            }
        } else {
            key = this.mRoomInfo.getUserId();
            TRTCRoomInfo tRTCRoomInfo = this.mRoomInfo;
            tRTCVideoFrame.pixelFormat = tRTCRoomInfo.localPixelFormat;
            tRTCVideoFrame.bufferType = tRTCRoomInfo.localBufferType;
            tRTCVideoRenderListener = tRTCRoomInfo.localListener;
        }
        if (tRTCVideoRenderListener != null) {
            int i3 = tRTCVideoFrame.bufferType;
            if (i3 == 1) {
                if (tXSVideoFrame.eglContext != null) {
                    ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(tXSVideoFrame.width * tXSVideoFrame.height * 4);
                    tRTCVideoFrame.buffer = byteBufferAllocateDirect;
                    TXCOpenGlUtils.a(e.a.RGBA, tXSVideoFrame.width, tXSVideoFrame.height, byteBufferAllocateDirect);
                } else {
                    if (tXSVideoFrame.buffer == null) {
                        tXSVideoFrame.loadYUVBufferFromGL();
                    }
                    tRTCVideoFrame.buffer = tXSVideoFrame.buffer;
                }
            } else if (i3 == 2) {
                if (tXSVideoFrame.eglContext != null) {
                    int i4 = tXSVideoFrame.width;
                    int i5 = tXSVideoFrame.height;
                    byte[] bArr = new byte[i4 * i5 * 4];
                    tRTCVideoFrame.data = bArr;
                    TXCOpenGlUtils.a(e.a.RGBA, i4, i5, bArr);
                } else {
                    byte[] bArr2 = tXSVideoFrame.data;
                    tRTCVideoFrame.data = bArr2;
                    if (bArr2 == null) {
                        byte[] bArr3 = new byte[((tXSVideoFrame.width * tXSVideoFrame.height) * 3) / 2];
                        tRTCVideoFrame.data = bArr3;
                        tXSVideoFrame.loadYUVArray(bArr3);
                    }
                }
            } else if (i3 == 3) {
                if (tXSVideoFrame.eglContext == null) {
                    return;
                }
                TRTCCloudDef.TRTCTexture tRTCTexture = new TRTCCloudDef.TRTCTexture();
                tRTCVideoFrame.texture = tRTCTexture;
                tRTCTexture.textureId = tXSVideoFrame.textureId;
                Object obj = tXSVideoFrame.eglContext;
                if (obj instanceof EGLContext) {
                    tRTCTexture.eglContext10 = (EGLContext) obj;
                } else if (obj instanceof android.opengl.EGLContext) {
                    tRTCTexture.eglContext14 = (android.opengl.EGLContext) obj;
                }
            }
            tRTCVideoRenderListener.onRenderVideoFrame(key, iTranslateStreamType, tRTCVideoFrame);
            if (this.mRoomInfo.enableCustomPreprocessor && z2) {
                int i6 = tRTCVideoFrame.bufferType;
                if (i6 == 2) {
                    tXSVideoFrame.data = tRTCVideoFrame.data;
                } else if (i6 == 3) {
                    tXSVideoFrame.textureId = tRTCVideoFrame.texture.textureId;
                }
            }
        }
    }

    @Override // com.tencent.liteav.TXCRenderAndDec.b
    public void onRequestKeyFrame(final String str, final int i2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.135
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeRequestKeyFrame(tRTCCloudImpl.mNativeRtcContext, Long.valueOf(str).longValue(), i2);
            }
        });
    }

    @Override // com.tencent.liteav.screencapture.a.InterfaceC0337a
    public void onScreenCapturePaused() {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.65
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onScreenCapturePaused();
                }
            }
        });
    }

    @Override // com.tencent.liteav.screencapture.a.InterfaceC0337a
    public void onScreenCaptureResumed() {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.64
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onScreenCaptureResumed();
                }
            }
        });
    }

    @Override // com.tencent.liteav.screencapture.a.InterfaceC0337a
    public void onScreenCaptureStarted() {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.63
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onScreenCaptureStarted();
                }
            }
        });
    }

    @Override // com.tencent.liteav.screencapture.a.InterfaceC0337a
    public void onScreenCaptureStopped(final int i2) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.66
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onScreenCaptureStopped(i2);
                }
            }
        });
    }

    public void onSendFirstLocalAudioFrame() {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.184
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("onSendFirstLocalAudioFrame");
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.appendDashboardLog(tRTCCloudImpl.mRoomInfo.getUserId(), 0, "onSendFirstLocalAudioFrame");
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onSendFirstLocalAudioFrame();
                }
            }
        });
    }

    public void onSendFirstLocalVideoFrame(final int i2) {
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.183
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("onSendFirstLocalVideoFrame " + i2);
                int iTranslateStreamType = TRTCCloudImpl.this.translateStreamType(i2);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.appendDashboardLog(tRTCCloudImpl.mRoomInfo.getUserId(), 0, "onSendFirstLocalVideoFrame:" + iTranslateStreamType);
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    tRTCCloudListener.onSendFirstLocalVideoFrame(iTranslateStreamType);
                }
            }
        });
    }

    public void onVideoConfigChanged(int i2, boolean z2) {
        onVideoConfigChanged(this, i2, z2);
    }

    public void onVideoQosChanged(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        onVideoQosChanged(this, i2, i3, i4, i5, i6, i7, i8, i9);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void pauseAudioEffect(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.116
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("pauseAudioEffect -> effectId = " + i2);
                TXCSoundEffectPlayer.getInstance().pauseEffectWithId(i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void pauseBGM() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.100
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("pauseBGM");
                TXCLiveBGMPlayer.getInstance().pause();
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void pauseScreenCapture() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.28
            @Override // java.lang.Runnable
            public void run() {
                VideoSourceType videoSourceType = TRTCCloudImpl.this.mMainStreamVideoSourceType;
                VideoSourceType videoSourceType2 = VideoSourceType.SCREEN;
                if (videoSourceType == videoSourceType2) {
                    TRTCCloudImpl.this.apiOnlineLog("pause mainStream screenCapture");
                    TRTCCloudImpl.this.mCaptureAndEnc.f();
                } else if (TRTCCloudImpl.this.mSubStreamVideoSourceType == videoSourceType2) {
                    TRTCCloudImpl.this.apiOnlineLog("pause subStream screenCapture");
                    TRTCCloudImpl.this.mSubStreamCaptureAndEnc.f();
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void playAudioEffect(final TRTCCloudDef.TRTCAudioEffectParam tRTCAudioEffectParam) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.111
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("playAudioEffect -> effectId = " + tRTCAudioEffectParam.effectId + " path = " + tRTCAudioEffectParam.path + " publish = " + tRTCAudioEffectParam.publish + " loopCount = " + tRTCAudioEffectParam.loopCount);
                TXCSoundEffectPlayer tXCSoundEffectPlayer = TXCSoundEffectPlayer.getInstance();
                TRTCCloudDef.TRTCAudioEffectParam tRTCAudioEffectParam2 = tRTCAudioEffectParam;
                tXCSoundEffectPlayer.playEffectWithId(tRTCAudioEffectParam2.effectId, tRTCAudioEffectParam2.path, tRTCAudioEffectParam2.publish, tRTCAudioEffectParam2.loopCount);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void playBGM(final String str, final TRTCCloud.BGMNotify bGMNotify) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.98
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("playBGM");
                TRTCCloudImpl.this.mBGMNotify = bGMNotify;
                if (TRTCCloudImpl.this.mBGMNotify != null) {
                    TXCLiveBGMPlayer.getInstance().setOnPlayListener(TRTCCloudImpl.this);
                } else {
                    TXCLiveBGMPlayer.getInstance().setOnPlayListener(null);
                }
                TXCLiveBGMPlayer.getInstance().startPlay(str);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void resumeAudioEffect(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.117
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("resumeAudioEffect -> effectId = " + i2);
                TXCSoundEffectPlayer.getInstance().resumeEffectWithId(i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void resumeBGM() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.101
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("resumeBGM");
                TXCLiveBGMPlayer.getInstance().resume();
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void resumeScreenCapture() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.29
            @Override // java.lang.Runnable
            public void run() {
                VideoSourceType videoSourceType = TRTCCloudImpl.this.mMainStreamVideoSourceType;
                VideoSourceType videoSourceType2 = VideoSourceType.SCREEN;
                if (videoSourceType == videoSourceType2) {
                    TRTCCloudImpl.this.apiOnlineLog("resume mainStream screenCapture");
                    TRTCCloudImpl.this.mCaptureAndEnc.g();
                } else if (TRTCCloudImpl.this.mSubStreamVideoSourceType == videoSourceType2) {
                    TRTCCloudImpl.this.apiOnlineLog("resume subStream screenCapture");
                    TRTCCloudImpl.this.mSubStreamCaptureAndEnc.g();
                }
            }
        });
    }

    public void runOnListenerThread(Runnable runnable) {
        Handler handler = this.mListenerHandler;
        if (handler == null) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mMainHandler.post(runnable);
                return;
            } else {
                runnable.run();
                return;
            }
        }
        if (Looper.myLooper() != handler.getLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void selectMotionTmpl(final String str) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.74
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("selectMotionTmpl " + str);
                TRTCCloudImpl.this.getBeautyManager().setMotionTmpl(str);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void sendCustomAudioData(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
        if (tRTCAudioFrame == null) {
            apiLog("sendCustomAudioData parameter is null");
            return;
        }
        final com.tencent.liteav.basic.structs.a aVar = new com.tencent.liteav.basic.structs.a();
        byte[] bArr = tRTCAudioFrame.data;
        byte[] bArr2 = new byte[bArr.length];
        aVar.f18649f = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        aVar.f18644a = tRTCAudioFrame.sampleRate;
        aVar.f18645b = tRTCAudioFrame.channel;
        aVar.f18646c = 16;
        long j2 = tRTCAudioFrame.timestamp;
        if (0 == j2) {
            aVar.f18648e = TXCTimeUtil.generatePtsMS();
        } else {
            aVar.f18648e = j2;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.96
            @Override // java.lang.Runnable
            public void run() {
                if (TRTCCloudImpl.this.mEnableCustomAudioCapture) {
                    TXCAudioEngine.getInstance().sendCustomPCMData(aVar);
                } else {
                    TRTCCloudImpl.this.apiLog("sendCustomAudioData when mEnableCustomAudioCapture is false");
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0063  */
    @Override // com.tencent.trtc.TRTCCloud
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean sendCustomCmdMsg(final int r9, final byte[] r10, final boolean r11, final boolean r12) {
        /*
            r8 = this;
            r0 = 0
            if (r10 != 0) goto L4
            return r0
        L4:
            int r1 = r8.mCurrentRole
            r2 = 21
            if (r1 != r2) goto L10
            java.lang.String r9 = "ignore send custom cmd msg for audience"
            r8.apiLog(r9)
            return r0
        L10:
            long r1 = java.lang.System.currentTimeMillis()
            long r3 = r8.mLastSendMsgTimeMs
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L1e
            r8.mLastSendMsgTimeMs = r1
        L1e:
            long r3 = r8.mLastSendMsgTimeMs
            long r3 = r1 - r3
            r5 = 1000(0x3e8, double:4.94E-321)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            r4 = 1
            if (r3 >= 0) goto L59
            int r1 = r8.mSendMsgCount
            r2 = 40
            if (r1 >= r2) goto L3d
            int r2 = r8.mSendMsgSize
            r3 = 8192(0x2000, float:1.148E-41)
            if (r2 >= r3) goto L3d
            int r1 = r1 + r4
            r8.mSendMsgCount = r1
            int r0 = r10.length
            int r2 = r2 + r0
            r8.mSendMsgSize = r2
            goto L60
        L3d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "send msg too more self:"
            r1.append(r2)
            int r2 = r8.hashCode()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "TRTCCloudImpl"
            com.tencent.liteav.basic.log.TXCLog.e(r2, r1)
            goto L61
        L59:
            r8.mLastSendMsgTimeMs = r1
            r8.mSendMsgCount = r4
            int r0 = r10.length
            r8.mSendMsgSize = r0
        L60:
            r0 = r4
        L61:
            if (r0 == 0) goto L71
            com.tencent.liteav.trtc.impl.TRTCCloudImpl$128 r7 = new com.tencent.liteav.trtc.impl.TRTCCloudImpl$128
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r1.<init>()
            r8.runOnSDKThread(r7)
        L71:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCCloudImpl.sendCustomCmdMsg(int, byte[], boolean, boolean):boolean");
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void sendCustomVideoData(int i2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        if (tRTCVideoFrame == null) {
            apiLog("sendCustomVideoData parameter is null");
            return;
        }
        int i3 = tRTCVideoFrame.pixelFormat;
        if (i3 != 1 && i3 != 4 && i3 != 2) {
            apiLog("sendCustomVideoData parameter error unsupported pixel format " + tRTCVideoFrame.pixelFormat);
            return;
        }
        if (tRTCVideoFrame.bufferType == 2 || tRTCVideoFrame.texture != null) {
            if (i2 == 0) {
                sendMainStreamCustomVideoData(tRTCVideoFrame);
                return;
            } else {
                sendSubStreamCustomVideoData(tRTCVideoFrame);
                return;
            }
        }
        apiLog("sendCustomVideoData parameter error unsupported buffer type " + tRTCVideoFrame.bufferType);
    }

    public void sendJsonCmd(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null || !jSONObject.has("jsonParam") || !(jSONObject.get("jsonParam") instanceof JSONObject)) {
            apiLog("callExperimentalAPI[lack parameter or illegal type]: sendJsonCMD");
        } else {
            nativeSendJsonCmd(this.mNativeRtcContext, jSONObject.getJSONObject("jsonParam").toString(), str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0063  */
    @Override // com.tencent.trtc.TRTCCloud
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean sendSEIMsg(final byte[] r8, final int r9) {
        /*
            r7 = this;
            r0 = 0
            if (r8 != 0) goto L4
            return r0
        L4:
            int r1 = r7.mCurrentRole
            r2 = 21
            if (r1 != r2) goto L10
            java.lang.String r8 = "ignore send sei msg for audience"
            r7.apiLog(r8)
            return r0
        L10:
            long r1 = java.lang.System.currentTimeMillis()
            long r3 = r7.mLastSendMsgTimeMs
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L1e
            r7.mLastSendMsgTimeMs = r1
        L1e:
            long r3 = r7.mLastSendMsgTimeMs
            long r3 = r1 - r3
            r5 = 1000(0x3e8, double:4.94E-321)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            r4 = 1
            if (r3 >= 0) goto L59
            int r1 = r7.mSendMsgCount
            r2 = 40
            if (r1 >= r2) goto L3d
            int r2 = r7.mSendMsgSize
            r3 = 8192(0x2000, float:1.148E-41)
            if (r2 >= r3) goto L3d
            int r1 = r1 + r4
            r7.mSendMsgCount = r1
            int r0 = r8.length
            int r2 = r2 + r0
            r7.mSendMsgSize = r2
            goto L60
        L3d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "send msg too more self:"
            r1.append(r2)
            int r2 = r7.hashCode()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "TRTCCloudImpl"
            com.tencent.liteav.basic.log.TXCLog.e(r2, r1)
            goto L61
        L59:
            r7.mLastSendMsgTimeMs = r1
            r7.mSendMsgCount = r4
            int r0 = r8.length
            r7.mSendMsgSize = r0
        L60:
            r0 = r4
        L61:
            if (r0 == 0) goto L6b
            com.tencent.liteav.trtc.impl.TRTCCloudImpl$129 r1 = new com.tencent.liteav.trtc.impl.TRTCCloudImpl$129
            r1.<init>()
            r7.runOnSDKThread(r1)
        L6b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCCloudImpl.sendSEIMsg(byte[], int):boolean");
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setAllAudioEffectsVolume(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.115
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setAllAudioEffectsVolume volume = " + i2);
                TXCSoundEffectPlayer.getInstance().setEffectsVolume(((float) i2) / 100.0f);
            }
        });
    }

    public void setAudioCacheType(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setAudioCacheType param is null");
            return;
        }
        if (!jSONObject.has("type")) {
            apiLog("setAudioCacheType[lack parameter]: type");
            return;
        }
        int iOptInt = jSONObject.optInt("type", 0);
        nativeSetAudioCacheType(this.mNativeRtcContext, iOptInt);
        apiLog("setAudioCacheType: type " + iOptInt);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setAudioCaptureVolume(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.mAudioCaptureVolume = i2;
        apiLog("setAudioCaptureVolume:  volume=" + this.mAudioCaptureVolume);
        TXAudioEffectManagerImpl.getInstance().setVoiceCaptureVolume(i2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setAudioEffectVolume(final int i2, final int i3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.112
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setAudioEffectVolume -> effectId = " + i2 + " volume = " + i3);
                TXCSoundEffectPlayer.getInstance().setVolumeOfEffect(i2, ((float) i3) / 100.0f);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setAudioFrameListener(final TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.130
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setAudioFrameListener " + tRTCAudioFrameListener);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener2 = tRTCAudioFrameListener;
                tRTCCloudImpl.mAudioFrameListener = tRTCAudioFrameListener2;
                if (tRTCAudioFrameListener2 == null) {
                    TXCAudioEngine.setPlayoutDataListener(null);
                    TXCAudioEngine.getInstance().setAudioCaptureDataListener(null);
                    TXCAudioEngine.getInstance().setMixedAllDataListener(null);
                    TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.130.1
                        @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                        public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                            TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(userInfo.tinyID), null);
                        }
                    });
                    return;
                }
                TXCAudioEngine.setPlayoutDataListener(tRTCCloudImpl);
                TXCAudioEngine.getInstance().setAudioCaptureDataListener(TRTCCloudImpl.this);
                TXCAudioEngine.getInstance().setMixedAllDataListener(TRTCCloudImpl.this);
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.130.2
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        TXCAudioEngine.getInstance().setSetAudioEngineRemoteStreamDataListener(String.valueOf(userInfo.tinyID), TRTCCloudImpl.this);
                    }
                });
            }
        });
    }

    public void setAudioPacketExtraDataListener(JSONObject jSONObject) throws JSONException {
        long j2;
        if (jSONObject == null || !jSONObject.has(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER)) {
            apiLog("setAudioPacketExtraDataListener [lack parameter or illegal type]: listener");
            return;
        }
        try {
            j2 = jSONObject.getLong(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        } catch (JSONException e2) {
            e2.printStackTrace();
            j2 = 0;
        }
        apiLog("setAudioPacketExtraDataListener:" + Long.toHexString(j2));
        nativeSetAudioPacketExtraDataListener(this.mNativeRtcContext, j2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setAudioPlayoutVolume(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        this.mAudioPlayoutVolume = i2;
        apiLog("setAudioPlayoutVolume:  volume=" + this.mAudioPlayoutVolume);
        TXAudioEffectManagerImpl.getInstance().setAudioPlayoutVolume(i2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setAudioQuality(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.53
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setAudioQuality " + i2);
                TXCAudioEngine.getInstance().setAudioQuality(i2, 2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setAudioRoute(int i2) {
        TXDeviceManager.TXAudioRoute tXAudioRoute = TXDeviceManager.TXAudioRoute.TXAudioRouteEarpiece;
        if (i2 == 0) {
            tXAudioRoute = TXDeviceManager.TXAudioRoute.TXAudioRouteSpeakerphone;
        }
        this.mDeviceManager.setAudioRoute(tXAudioRoute);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setBGMPlayoutVolume(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.105
            @Override // java.lang.Runnable
            public void run() {
                float f2 = i2 / 100.0f;
                TRTCCloudImpl.this.apiLog("setBGMPlayoutVolume:" + i2 + " fVolume:" + f2);
                TXCLiveBGMPlayer.getInstance().setPlayoutVolume(f2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setBGMPosition(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.102
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("setBGMPosition " + i2);
                TXCLiveBGMPlayer.getInstance().setBGMPosition(i2);
            }
        });
        return 0;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setBGMPublishVolume(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.106
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setBGMPublishVolume " + i2);
                TXCLiveBGMPlayer.getInstance().setPublishVolume(i2 / 100.0f);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setBGMVolume(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.104
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setBGMVolume " + i2);
                TXCLiveBGMPlayer.getInstance().setVolume(((float) i2) / 100.0f);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setBeautyStyle(final int i2, final int i3, final int i4, final int i5) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.71
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.getBeautyManager().setBeautyStyle(i2);
                TRTCCloudImpl.this.getBeautyManager().setBeautyLevel(i3);
                TRTCCloudImpl.this.getBeautyManager().setWhitenessLevel(i4);
                TRTCCloudImpl.this.getBeautyManager().setRuddyLevel(i5);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setCapturedRawAudioFrameCallbackFormat(final TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat) {
        apiLog(String.format("setCaptureAudioFrameCallbackFormat sample_rate: %d, channel: %d, samplesPerCall %d", Integer.valueOf(tRTCAudioFrameCallbackFormat.sampleRate), Integer.valueOf(tRTCAudioFrameCallbackFormat.channel), Integer.valueOf(tRTCAudioFrameCallbackFormat.samplesPerCall)));
        if (TXCAudioEngine.getInstance().IsDataCallbackFormatInvalid(tRTCAudioFrameCallbackFormat.sampleRate, tRTCAudioFrameCallbackFormat.channel, tRTCAudioFrameCallbackFormat.samplesPerCall)) {
            return -1;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.131
            @Override // java.lang.Runnable
            public void run() {
                TXCAudioEngine tXCAudioEngine = TXCAudioEngine.getInstance();
                TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat2 = tRTCAudioFrameCallbackFormat;
                tXCAudioEngine.setCaptureDataCallbackFormat(tRTCAudioFrameCallbackFormat2.sampleRate, tRTCAudioFrameCallbackFormat2.channel, tRTCAudioFrameCallbackFormat2.samplesPerCall);
            }
        });
        return 0;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setChinLevel(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.81
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setChinLevel " + i2);
                TRTCCloudImpl.this.getBeautyManager().setChinLevel((float) i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setDebugViewMargin(final String str, final TRTCCloud.TRTCViewMargin tRTCViewMargin) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.119
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setDebugViewMargin");
                final TXCloudVideoView tXCloudVideoView = TRTCCloudImpl.this.mRoomInfo.localView;
                if (tXCloudVideoView != null && str.equalsIgnoreCase(tXCloudVideoView.getUserId())) {
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.119.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                            TRTCCloud.TRTCViewMargin tRTCViewMargin2 = tRTCViewMargin;
                            tXCloudVideoView2.setLogMarginRatio(tRTCViewMargin2.leftMargin, tRTCViewMargin2.rightMargin, tRTCViewMargin2.topMargin, tRTCViewMargin2.bottomMargin);
                        }
                    });
                }
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user != null) {
                    user.debugMargin = tRTCViewMargin;
                    final TXCloudVideoView tXCloudVideoView2 = user.mainRender.view;
                    final TXCloudVideoView tXCloudVideoView3 = user.subRender.view;
                    if (tXCloudVideoView2 == null && tXCloudVideoView3 == null) {
                        return;
                    }
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.119.2
                        @Override // java.lang.Runnable
                        public void run() {
                            TXCloudVideoView tXCloudVideoView4 = tXCloudVideoView2;
                            if (tXCloudVideoView4 != null) {
                                TRTCCloud.TRTCViewMargin tRTCViewMargin2 = tRTCViewMargin;
                                tXCloudVideoView4.setLogMarginRatio(tRTCViewMargin2.leftMargin, tRTCViewMargin2.rightMargin, tRTCViewMargin2.topMargin, tRTCViewMargin2.bottomMargin);
                            }
                            TXCloudVideoView tXCloudVideoView5 = tXCloudVideoView3;
                            if (tXCloudVideoView5 != null) {
                                TRTCCloud.TRTCViewMargin tRTCViewMargin3 = tRTCViewMargin;
                                tXCloudVideoView5.setLogMarginRatio(tRTCViewMargin3.leftMargin, tRTCViewMargin3.rightMargin, tRTCViewMargin3.topMargin, tRTCViewMargin3.bottomMargin);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setDefaultStreamRecvMode(final boolean z2, final boolean z3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.13
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.mRecvMode = 0;
                boolean z4 = z2;
                if (z4 && z3) {
                    tRTCCloudImpl.mRecvMode = 1;
                } else if (z4) {
                    tRTCCloudImpl.mRecvMode = 2;
                } else if (z3) {
                    tRTCCloudImpl.mRecvMode = 3;
                } else {
                    tRTCCloudImpl.mRecvMode = 4;
                }
                TRTCCloudImpl.this.apiOnlineLog(String.format("setDefaultStreamRecvMode audio:%b, video:%b", Boolean.valueOf(z2), Boolean.valueOf(z3)) + " self:" + TRTCCloudImpl.this.hashCode());
            }
        });
    }

    public void setEncodedDataProcessingListener(JSONObject jSONObject) throws JSONException {
        long j2;
        if (jSONObject == null || !jSONObject.has(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER)) {
            apiLog("setEncodedDataProcessingListener [lack parameter or illegal type]: listener");
            return;
        }
        try {
            j2 = jSONObject.getLong(ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        } catch (JSONException e2) {
            e2.printStackTrace();
            j2 = 0;
        }
        apiLog("setEncodedDataProcessingListener:" + Long.toHexString(j2));
        nativeSetEncodedDataProcessingListener(this.mNativeRtcContext, j2);
    }

    public void setEqualizationParam(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("equalization_type")) {
            apiLog("setEqualizationParam[lack parameter]: equalization_type");
            return;
        }
        int iOptInt = jSONObject.optInt("equalization_type", 0);
        if (iOptInt < 0 || iOptInt > 11) {
            apiLog("setEqualizationParam[illegal value]: equalization_type" + iOptInt);
            return;
        }
        int[] iArr = new int[10];
        if (iOptInt == 11) {
            if (!jSONObject.has("gain") || jSONObject.getJSONArray("gain").length() != 10) {
                apiLog("setEqualizationParam[illegal parameter]: gain");
                return;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("gain");
            for (int i2 = 0; i2 < 10; i2++) {
                iArr[i2] = jSONArray.getInt(i2);
            }
        }
        TXCAudioEngine.getInstance().setCaptureEqualizationType(iOptInt);
        if (iOptInt == 11) {
            for (int i3 = 0; i3 < 10; i3++) {
                TXCAudioEngine.getInstance().setCaptureEqualizationParam(i3, iArr[i3]);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setEyeScaleLevel(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.77
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setEyeScaleLevel " + i2);
                TRTCCloudImpl.this.getBeautyManager().setEyeScaleLevel((float) i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setFaceShortLevel(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.80
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setFaceShortLevel " + i2);
                TRTCCloudImpl.this.getBeautyManager().setFaceShortLevel((float) i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setFaceSlimLevel(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.78
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setFaceSlimLevel " + i2);
                TRTCCloudImpl.this.getBeautyManager().setFaceSlimLevel((float) i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setFaceVLevel(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.79
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setFaceVLevel " + i2);
                TRTCCloudImpl.this.getBeautyManager().setFaceVLevel((float) i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setFilter(final Bitmap bitmap) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.72
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setFilter");
                TRTCCloudImpl.this.getBeautyManager().setFilter(bitmap);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setFilterConcentration(final float f2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.73
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setFilterStrength: " + f2);
                TRTCCloudImpl.this.getBeautyManager().setFilterStrength(f2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setFocusPosition(int i2, int i3) {
        this.mDeviceManager.setCameraFocusPosition(i2, i3);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setGSensorMode(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.48
            @Override // java.lang.Runnable
            public void run() {
                if (TRTCCloudImpl.this.mMainStreamVideoSourceType == VideoSourceType.SCREEN) {
                    TRTCCloudImpl.this.apiLog("setGSensorMode has been ignored for screen capturing");
                    return;
                }
                TRTCCloudImpl.this.mSensorMode = i2;
                TRTCCloudImpl.this.apiLog("vrotation setGSensorMode " + i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    @TargetApi(18)
    public boolean setGreenScreenFile(final String str) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.76
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setGreenScreenFile " + str);
                TRTCCloudImpl.this.getBeautyManager().setGreenScreenFile(str);
            }
        });
        return true;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setListener(final TRTCCloudListener tRTCCloudListener) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.5
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setListener " + tRTCCloudListener);
                TRTCCloudImpl.this.mTRTCListener = tRTCCloudListener;
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setListenerHandler(Handler handler) {
        apiLog("setListenerHandler " + handler);
        if (handler == null) {
            this.mListenerHandler = new Handler(Looper.getMainLooper());
        } else {
            this.mListenerHandler = handler;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.6
            @Override // java.lang.Runnable
            public void run() {
                Iterator<WeakReference<TRTCCloudImpl>> it = TRTCCloudImpl.this.mSubClouds.iterator();
                while (it.hasNext()) {
                    TRTCCloudImpl tRTCCloudImpl = it.next().get();
                    if (tRTCCloudImpl != null) {
                        tRTCCloudImpl.setListenerHandler(TRTCCloudImpl.this.mListenerHandler);
                    } else {
                        it.remove();
                    }
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setLocalProcessedAudioFrameCallbackFormat(final TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat) {
        apiLog(String.format("setLocalProcessedAudioFrameCallbackFormat sample_rate: %d, channel: %d, samplesPerCall %d", Integer.valueOf(tRTCAudioFrameCallbackFormat.sampleRate), Integer.valueOf(tRTCAudioFrameCallbackFormat.channel), Integer.valueOf(tRTCAudioFrameCallbackFormat.samplesPerCall)));
        if (TXCAudioEngine.getInstance().IsDataCallbackFormatInvalid(tRTCAudioFrameCallbackFormat.sampleRate, tRTCAudioFrameCallbackFormat.channel, tRTCAudioFrameCallbackFormat.samplesPerCall)) {
            return -1;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.132
            @Override // java.lang.Runnable
            public void run() {
                TXCAudioEngine tXCAudioEngine = TXCAudioEngine.getInstance();
                TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat2 = tRTCAudioFrameCallbackFormat;
                tXCAudioEngine.setLocalProcessedDataCallbackFormat(tRTCAudioFrameCallbackFormat2.sampleRate, tRTCAudioFrameCallbackFormat2.channel, tRTCAudioFrameCallbackFormat2.samplesPerCall);
            }
        });
        return 0;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setLocalRenderParams(TRTCCloudDef.TRTCRenderParams tRTCRenderParams) {
        setLocalViewFillMode(tRTCRenderParams.fillMode);
        setLocalViewRotation(tRTCRenderParams.rotation);
        setLocalViewMirror(tRTCRenderParams.mirrorType);
    }

    public void setLocalSurface(final Surface surface) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.89
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setLocalSurface " + surface);
                TRTCCloudImpl.this.mCaptureAndEnc.a(surface);
            }
        });
    }

    public void setLocalSurfaceSize(final int i2, final int i3) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.90
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setLocalSurfaceSize: " + i2 + "," + i3);
                TRTCCloudImpl.this.mCaptureAndEnc.a(i2, i3);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setLocalVideoProcessListener(final int i2, final int i3, final TRTCCloudListener.TRTCVideoFrameListener tRTCVideoFrameListener) {
        if (i2 != 1 && i2 != 4 && i2 != 2) {
            apiLog("setLocalVideoRenderListener unsupported pixelFormat : " + i2);
            return TXLiteAVCode.ERR_PIXEL_FORMAT_UNSUPPORTED;
        }
        if (i3 == 1 || i3 == 2 || i3 == 3) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.93
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.apiLog("setLocalVideoPreprocessListener pixelFormat: %d, bufferType: %d, listener: %s", Integer.valueOf(i2), Integer.valueOf(i3), tRTCVideoFrameListener);
                    TRTCCloudImpl.this.mVideoPreprocessListenerAdapter.setListener(i2, i3, tRTCVideoFrameListener);
                    TRTCCloudImpl.this.mCaptureAndEnc.b(tRTCVideoFrameListener == null);
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.mCaptureAndEnc.a(tRTCCloudImpl.mVideoPreprocessListenerAdapter);
                }
            });
            return 0;
        }
        apiLog("setLocalVideoRenderListener unsupported bufferType : " + i3);
        return TXLiteAVCode.ERR_BUFFER_TYPE_UNSUPPORTED;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setLocalVideoRenderListener(final int i2, final int i3, final TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener) {
        if (i2 != 1 && i2 != 4 && i2 != 2 && i2 != 5) {
            apiLog("setLocalVideoRenderListener unsupported pixelFormat : " + i2);
            return TXLiteAVCode.ERR_PIXEL_FORMAT_UNSUPPORTED;
        }
        if (i3 == 1 || i3 == 2 || i3 == 3) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.92
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.apiLog(String.format("setLocalVideoRenderListener pixelFormat:%d bufferType:%d", Integer.valueOf(i2), Integer.valueOf(i3)));
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    TRTCRoomInfo tRTCRoomInfo = tRTCCloudImpl.mRoomInfo;
                    int i4 = i2;
                    tRTCRoomInfo.localPixelFormat = i4;
                    tRTCRoomInfo.localBufferType = i3;
                    TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener2 = tRTCVideoRenderListener;
                    tRTCRoomInfo.localListener = tRTCVideoRenderListener2;
                    if (tRTCVideoRenderListener2 == null) {
                        tRTCCloudImpl.mCaptureAndEnc.a((o) null, i4);
                    } else {
                        tRTCCloudImpl.mCaptureAndEnc.a(tRTCCloudImpl, i4);
                    }
                }
            });
            return 0;
        }
        apiLog("setLocalVideoRenderListener unsupported bufferType : " + i3);
        return TXLiteAVCode.ERR_BUFFER_TYPE_UNSUPPORTED;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setLocalViewFillMode(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.45
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setLocalViewFillMode " + i2);
                TRTCCloudImpl.this.mCaptureAndEnc.g(i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setLocalViewMirror(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.51
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.mVideoRenderMirror = i2;
                TRTCCloudImpl.this.apiLog("setLocalViewMirror " + i2);
                TRTCCloudImpl.this.mCaptureAndEnc.b(i2);
                TRTCCloudImpl.this.updateOrientation();
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setLocalViewRotation(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.46
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("vrotation setLocalViewRotation " + i2);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.mRoomInfo.localRenderRotation = tRTCCloudImpl.getCompatibleRotation(i2);
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                tRTCCloudImpl2.mCaptureAndEnc.h(tRTCCloudImpl2.mRoomInfo.localRenderRotation);
                TRTCCloudImpl.this.updateOrientation();
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setMicVolumeOnMixing(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.103
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setMicVolume " + i2);
                TXCAudioEngine.getInstance().setSoftwareCaptureVolume(((float) i2) / 100.0f);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setMixExternalAudioVolume(int i2, int i3) {
        int i4 = this.mPublishAudioTunnelId;
        if (i4 >= 0 && i2 >= 0) {
            TXCAudioEngineJNI.nativeSetVolumeToTunnel(i4, i2);
        }
        int i5 = this.mPlayoutAudioTunnelId;
        if (i5 < 0 || i3 < 0) {
            return;
        }
        TXCAudioEngineJNI.nativeSetVolumeToTunnel(i5, i3);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setMixTranscodingConfig(TRTCCloudDef.TRTCTranscodingConfig tRTCTranscodingConfig) {
        final TRTCTranscodingConfigInner tRTCTranscodingConfigInner = tRTCTranscodingConfig != null ? new TRTCTranscodingConfigInner(tRTCTranscodingConfig) : null;
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.127
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("setMixTranscodingConfig " + tRTCTranscodingConfigInner);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeSetMixTranscodingConfig(tRTCCloudImpl.mNativeRtcContext, tRTCTranscodingConfigInner);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setMixedPlayAudioFrameCallbackFormat(final TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat) {
        apiLog(String.format("setMixedPlayAudioFrameCallbackFormat sample_rate: %d, channel: %d, samplesPerCall %d", Integer.valueOf(tRTCAudioFrameCallbackFormat.sampleRate), Integer.valueOf(tRTCAudioFrameCallbackFormat.channel), Integer.valueOf(tRTCAudioFrameCallbackFormat.samplesPerCall)));
        if (TXCAudioEngine.getInstance().IsDataCallbackFormatInvalid(tRTCAudioFrameCallbackFormat.sampleRate, tRTCAudioFrameCallbackFormat.channel, tRTCAudioFrameCallbackFormat.samplesPerCall)) {
            return -1;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.133
            @Override // java.lang.Runnable
            public void run() {
                TXCAudioEngine tXCAudioEngine = TXCAudioEngine.getInstance();
                TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat2 = tRTCAudioFrameCallbackFormat;
                tXCAudioEngine.setPlayoutDataCallbackFormat(tRTCAudioFrameCallbackFormat2.sampleRate, tRTCAudioFrameCallbackFormat2.channel, tRTCAudioFrameCallbackFormat2.samplesPerCall);
            }
        });
        return 0;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setMotionMute(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.75
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setMotionMute " + z2);
                TRTCCloudImpl.this.getBeautyManager().setMotionMute(z2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setNetworkQosParam(final TRTCCloudDef.TRTCNetworkQosParam tRTCNetworkQosParam) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.41
            @Override // java.lang.Runnable
            public void run() {
                if (tRTCNetworkQosParam == null) {
                    TRTCCloudImpl.this.apiLog("setNetworkQosParam param is null");
                    return;
                }
                TRTCCloudImpl.this.apiLog("setNetworkQosParam");
                TRTCCloudImpl.this.mQosPreference = tRTCNetworkQosParam.preference;
                TRTCCloudImpl.this.mQosMode = tRTCNetworkQosParam.controlMode;
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.setVideoQuality(tRTCCloudImpl.mQosMode, TRTCCloudImpl.this.mQosPreference);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setNoseSlimLevel(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.82
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setNoseSlimLevel " + i2);
                TRTCCloudImpl.this.getBeautyManager().setNoseSlimLevel((float) i2);
            }
        });
    }

    public void setPerformanceMode(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setPerformanceMode[lack parameter]");
            return;
        }
        if (!jSONObject.has("mode")) {
            apiLog("setPerformanceMode[lack parameter]: mode");
            return;
        }
        int i2 = jSONObject.getInt("mode");
        if (i2 == 1) {
            this.mPerformanceMode = 1;
            this.mCaptureAndEnc.b().enableSharpnessEnhancement(false);
            TRTCCloudImpl tRTCCloudImpl = this.mCurrentPublishClouds.get(2);
            if (tRTCCloudImpl != null) {
                nativeSetAllowSwitchToHighPerformanceMode(tRTCCloudImpl.getNetworkContext(), true);
                return;
            }
            return;
        }
        if (i2 == 2) {
            TRTCCloudImpl tRTCCloudImpl2 = this.mCurrentPublishClouds.get(2);
            if (tRTCCloudImpl2 != null) {
                nativeSetAllowSwitchToHighPerformanceMode(tRTCCloudImpl2.getNetworkContext(), false);
                return;
            }
            return;
        }
        this.mPerformanceMode = 0;
        TRTCCloudImpl tRTCCloudImpl3 = this.mCurrentPublishClouds.get(2);
        if (tRTCCloudImpl3 != null) {
            nativeSetAllowSwitchToHighPerformanceMode(tRTCCloudImpl3.getNetworkContext(), true);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setPriorRemoteVideoStreamType(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.50
            @Override // java.lang.Runnable
            public void run() {
                int i3 = i2;
                if (i3 != 0 && i3 == 1) {
                    TRTCCloudImpl.this.mPriorStreamType = 3;
                } else {
                    TRTCCloudImpl.this.mPriorStreamType = 2;
                }
                TRTCCloudImpl.this.apiLog("setPriorRemoteVideoStreamType " + TRTCCloudImpl.this.mPriorStreamType);
            }
        });
        return 0;
    }

    public void setQoSStrategy(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setQoSStrategy param is null");
        } else if (jSONObject.has(DBHelpTool.RecordEntry.COLUMN_NAME_STRATEGY)) {
            this.mQoSStrategy = jSONObject.optInt(DBHelpTool.RecordEntry.COLUMN_NAME_STRATEGY, 0);
        } else {
            apiLog("setQoSStrategy[lack parameter]: strategy");
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setRemoteAudioVolume(final String str, final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.61
            @Override // java.lang.Runnable
            public void run() {
                int i3 = i2;
                if (i3 < 0) {
                    i3 = 0;
                }
                TRTCCloudImpl.this.apiLog("setRemoteAudioVolume: userId = " + str + " volume = " + i3);
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user != null) {
                    TXCAudioEngine.getInstance().setRemotePlayoutVolume(String.valueOf(user.tinyID), i3);
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setRemoteRenderParams(String str, int i2, TRTCCloudDef.TRTCRenderParams tRTCRenderParams) {
        if (i2 == 0 || i2 == 1 || i2 == 2) {
            setRemoteViewFillMode(str, i2, tRTCRenderParams.fillMode);
            setRemoteViewRotation(str, i2, tRTCRenderParams.rotation);
            setRemoteViewMirror(str, i2, tRTCRenderParams.mirrorType);
        } else {
            TXCLog.e(TAG, "setRemoteRenderParams unsupported streamType:" + i2);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setRemoteSubStreamViewFillMode(String str, int i2) {
        setRemoteViewFillMode(str, 2, i2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setRemoteSubStreamViewRotation(String str, int i2) {
        setRemoteViewRotation(str, 2, i2);
    }

    public void setRemoteSurface(final String str, final int i2, final Surface surface) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.87
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setRemoteSurface " + str + ", " + surface);
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    TRTCCloudImpl.this.apiLog("user no exist");
                    return;
                }
                TXCRenderAndDec tXCRenderAndDec = (i2 == 2 ? user.subRender : user.mainRender).render;
                if (tXCRenderAndDec == null) {
                    TRTCCloudImpl.this.apiLog("render no exist");
                    return;
                }
                com.tencent.liteav.renderer.e videoRender = tXCRenderAndDec.getVideoRender();
                if (videoRender != null) {
                    videoRender.a(surface);
                } else {
                    TRTCCloudImpl.this.apiLog("videoRender no exist");
                }
            }
        });
    }

    public void setRemoteSurfaceSize(final String str, final int i2, final int i3, final int i4) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.88
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setRemoteSurfaceSize: " + str + ", " + i3 + "," + i4);
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    TRTCCloudImpl.this.apiLog("user no exist");
                    return;
                }
                TXCRenderAndDec tXCRenderAndDec = (i2 == 2 ? user.subRender : user.mainRender).render;
                if (tXCRenderAndDec == null) {
                    TRTCCloudImpl.this.apiLog("render no exist");
                    return;
                }
                com.tencent.liteav.renderer.e videoRender = tXCRenderAndDec.getVideoRender();
                if (videoRender != null) {
                    videoRender.d(i3, i4);
                } else {
                    TRTCCloudImpl.this.apiLog("videoRender no exist");
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setRemoteVideoRenderListener(final String str, final int i2, final int i3, final TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener) {
        if (i2 != 1 && i2 != 4 && i2 != 2 && i2 != 5) {
            apiLog("setRemoteVideoRenderListener unsupported pixelFormat : " + i2);
            return TXLiteAVCode.ERR_PIXEL_FORMAT_UNSUPPORTED;
        }
        if (i3 == 1 || i3 == 2 || i3 == 3) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.94
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.apiLog(String.format("setRemoteVideoRenderListener userid:%s pixelFormat:%d bufferType:%d", str, Integer.valueOf(i2), Integer.valueOf(i3)));
                    if (tRTCVideoRenderListener == null) {
                        TRTCCloudImpl.this.mRenderListenerMap.remove(str);
                    } else {
                        RenderListenerAdapter renderListenerAdapter = new RenderListenerAdapter();
                        renderListenerAdapter.bufferType = i3;
                        renderListenerAdapter.pixelFormat = i2;
                        renderListenerAdapter.listener = tRTCVideoRenderListener;
                        TRTCCloudImpl.this.mRenderListenerMap.put(str, renderListenerAdapter);
                        TRTCCloudImpl.this.mCustomRemoteRender = true;
                    }
                    TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.94.1
                        @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                        public void accept(String str2, TRTCRoomInfo.UserInfo userInfo) {
                            if (str2.equalsIgnoreCase(str)) {
                                AnonymousClass94 anonymousClass94 = AnonymousClass94.this;
                                RenderListenerAdapter renderListenerAdapter2 = TRTCCloudImpl.this.mRenderListenerMap.get(str);
                                if (renderListenerAdapter2 != null) {
                                    renderListenerAdapter2.strTinyID = String.valueOf(userInfo.tinyID);
                                }
                                AnonymousClass94 anonymousClass942 = AnonymousClass94.this;
                                TRTCCloudImpl tRTCCloudImpl = tRTCVideoRenderListener != null ? TRTCCloudImpl.this : null;
                                com.tencent.liteav.basic.enums.b pixelFormat = renderListenerAdapter2 != null ? TRTCCloudImpl.this.getPixelFormat(renderListenerAdapter2.pixelFormat) : com.tencent.liteav.basic.enums.b.UNKNOWN;
                                TXCRenderAndDec tXCRenderAndDec = userInfo.mainRender.render;
                                if (tXCRenderAndDec != null) {
                                    tXCRenderAndDec.setVideoFrameListener(tRTCCloudImpl, pixelFormat);
                                }
                                TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
                                if (tXCRenderAndDec2 != null) {
                                    tXCRenderAndDec2.setVideoFrameListener(tRTCCloudImpl, pixelFormat);
                                }
                            }
                        }
                    });
                }
            });
            return 0;
        }
        apiLog("setRemoteVideoRenderListener unsupported bufferType : " + i3);
        return TXLiteAVCode.ERR_BUFFER_TYPE_UNSUPPORTED;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int setRemoteVideoStreamType(final String str, final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.56
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    return;
                }
                int i3 = i2 == 1 ? 3 : 2;
                if (user.streamType == i3) {
                    return;
                }
                user.streamType = i3;
                TRTCCloudImpl.this.apiLog("setRemoteVideoStreamType " + str + ", " + i3 + ", " + user.tinyID);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeRequestDownStream(tRTCCloudImpl.mNativeRtcContext, user.tinyID, i3, false);
            }
        });
        return 0;
    }

    public void setRenderView(final String str, final TRTCRoomInfo.RenderInfo renderInfo, final TXCloudVideoView tXCloudVideoView, final TRTCCloud.TRTCViewMargin tRTCViewMargin) {
        TXCRenderAndDec tXCRenderAndDec;
        if (renderInfo == null || (tXCRenderAndDec = renderInfo.render) == null || tXCRenderAndDec.getVideoRender() == null) {
            return;
        }
        final com.tencent.liteav.renderer.e videoRender = renderInfo.render.getVideoRender();
        if (tXCloudVideoView != null && tXCloudVideoView.getOpenGLContext() == null) {
            runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.190
                @Override // java.lang.Runnable
                public void run() {
                    SurfaceView surfaceView = tXCloudVideoView.getSurfaceView();
                    if (surfaceView == null) {
                        TextureView textureView = new TextureView(tXCloudVideoView.getContext());
                        tXCloudVideoView.addVideoView(textureView);
                        tXCloudVideoView.setVisibility(0);
                        tXCloudVideoView.setUserId(str);
                        tXCloudVideoView.showVideoDebugLog(TRTCCloudImpl.this.mDebugType);
                        TRTCCloud.TRTCViewMargin tRTCViewMargin2 = tRTCViewMargin;
                        if (tRTCViewMargin2 != null) {
                            tXCloudVideoView.setLogMarginRatio(tRTCViewMargin2.leftMargin, tRTCViewMargin2.rightMargin, tRTCViewMargin2.topMargin, tRTCViewMargin2.bottomMargin);
                        }
                        videoRender.a(textureView);
                        return;
                    }
                    surfaceView.setVisibility(0);
                    SurfaceHolder holder = surfaceView.getHolder();
                    holder.removeCallback(renderInfo);
                    holder.addCallback(renderInfo);
                    if (holder.getSurface().isValid()) {
                        TRTCCloudImpl.this.apiLog(String.format(Locale.ENGLISH, "startRemoteView with valid surface %s, width: %d, height: %d", holder.getSurface(), Integer.valueOf(surfaceView.getWidth()), Integer.valueOf(surfaceView.getHeight())));
                        videoRender.a(holder.getSurface());
                        videoRender.d(surfaceView.getWidth(), surfaceView.getHeight());
                    } else {
                        TRTCCloudImpl.this.apiLog("startRemoteView with surfaceView add callback " + renderInfo);
                    }
                }
            });
            return;
        }
        Object openGLContext = tXCloudVideoView == null ? null : tXCloudVideoView.getOpenGLContext();
        apiLog("setRenderView sharedContext: " + openGLContext);
        videoRender.c(openGLContext);
    }

    public void setReverbParam(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setReverbParam param is null");
            return;
        }
        TXCAudioEngine.getInstance().setReverbType(255);
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            double d3 = jSONObject.getDouble(next);
            if (this.reverbParamKeyNames.contains(next)) {
                TXCAudioEngine.getInstance().setReverbParamType(this.reverbParamKeyNames.indexOf(next), (float) d3);
            }
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setReverbType(final int i2) {
        if (i2 >= TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_0.getNativeValue() && i2 <= TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_10.getNativeValue()) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.107
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.apiLog("setLocalViewFillMode");
                    TXAudioEffectManagerImpl.getInstance().setVoiceReverbType(TRTCCloudImpl.this.reverbTypes[i2]);
                }
            });
            return;
        }
        TXCLog.e(TAG, "reverbType not support :" + i2);
    }

    public void setRoomType(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("setRoomType param is null");
        } else if (jSONObject.has("type")) {
            this.mRoomType = jSONObject.optInt("type", 0);
        } else {
            apiLog("setRoomType[lack parameter]: type");
        }
    }

    public void setSEIPayloadType(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null || !jSONObject.has("payloadType")) {
            apiLog("callExperimentalAPI[lack parameter or illegal type]: payloadType");
            return;
        }
        int i2 = jSONObject.getInt("payloadType");
        if (i2 != 5 && i2 != 243 && i2 != 242) {
            apiLog("callExperimentalAPI[invalid param]: payloadType[" + i2 + StrPool.BRACKET_END);
            return;
        }
        if (nativeSetSEIPayloadType(this.mNativeRtcContext, i2)) {
            apiLog("callExperimentalAPI[succeeded]: setSEIPayloadType (" + i2 + ")");
            return;
        }
        apiLog("callExperimentalAPI[failed]: setSEIPayloadType (" + i2 + ")");
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setSubStreamEncoderParam(final TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.40
            @Override // java.lang.Runnable
            public void run() {
                if (tRTCVideoEncParam == null) {
                    TRTCCloudImpl.this.apiLog("setSubStreamEncoderParam param is null");
                    return;
                }
                TRTCCloudImpl.this.makeSureSubStreamCaptureCreated();
                TRTCCloudImpl.this.mLatestParamsOfSubEncoder.putInt(TRTCCloudImpl.KEY_CONFIG_FPS, tRTCVideoEncParam.videoFps);
                TRTCCloudImpl.this.mLatestParamsOfSubEncoder.putBoolean(TRTCCloudImpl.KEY_CONFIG_ADJUST_RESOLUTION, tRTCVideoEncParam.enableAdjustRes);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam2 = tRTCVideoEncParam;
                g.a sizeByResolution = tRTCCloudImpl.getSizeByResolution(tRTCVideoEncParam2.videoResolution, tRTCVideoEncParam2.videoResolutionMode);
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam3 = tRTCVideoEncParam;
                tRTCCloudImpl2.updateSubStreamEncoder(tRTCVideoEncParam3.videoResolutionMode == 1, sizeByResolution.f19354a, sizeByResolution.f19355b, tRTCVideoEncParam3.videoFps, tRTCVideoEncParam3.videoBitrate, tRTCVideoEncParam3.enableAdjustRes, tRTCVideoEncParam3.minVideoBitrate, true);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setSystemVolumeType(int i2) {
        TXDeviceManager.TXSystemVolumeType tXSystemVolumeType = TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeAuto;
        if (i2 != 0) {
            if (i2 == 1) {
                tXSystemVolumeType = TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeMedia;
            } else if (i2 == 2) {
                tXSystemVolumeType = TXDeviceManager.TXSystemVolumeType.TXSystemVolumeTypeVOIP;
            }
        }
        this.mDeviceManager.setSystemVolumeType(tXSystemVolumeType);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setVideoEncoderMirror(final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.52
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("setVideoEncoderMirror " + z2);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                com.tencent.liteav.g gVar = tRTCCloudImpl.mConfig;
                boolean z3 = z2;
                gVar.V = z3;
                tRTCCloudImpl.mCaptureAndEnc.f(z3);
                TRTCCloudImpl.this.updateOrientation();
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setVideoEncoderParam(final TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.39
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.setVideoEncoderParamInternal(tRTCVideoEncParam);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setVideoEncoderRotation(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.47
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("vrotation setVideoEncoderRotation " + i2 + ", g sensor mode " + TRTCCloudImpl.this.mSensorMode);
                if (TRTCCloudImpl.this.mSensorMode == 0) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.mCaptureAndEnc.a(tRTCCloudImpl.getCompatibleRotation(i2));
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setVideoMuteImage(final Bitmap bitmap, final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.32
            /* JADX WARN: Removed duplicated region for block: B:4:0x0029 A[PHI: r1
              0x0029: PHI (r1v11 int) = (r1v2 int), (r1v3 int) binds: [B:3:0x0027, B:6:0x002c] A[DONT_GENERATE, DONT_INLINE]] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    r4 = this;
                    com.tencent.liteav.trtc.impl.TRTCCloudImpl r0 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    r1.<init>()
                    java.lang.String r2 = "setVideoMuteImage "
                    r1.append(r2)
                    android.graphics.Bitmap r2 = r2
                    r1.append(r2)
                    java.lang.String r2 = ", "
                    r1.append(r2)
                    int r2 = r3
                    r1.append(r2)
                    java.lang.String r1 = r1.toString()
                    r0.apiLog(r1)
                    int r0 = r3
                    r1 = 20
                    if (r0 <= r1) goto L2b
                L29:
                    r0 = r1
                    goto L2f
                L2b:
                    r1 = 5
                    if (r0 >= r1) goto L2f
                    goto L29
                L2f:
                    com.tencent.liteav.trtc.impl.TRTCCloudImpl r1 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                    com.tencent.liteav.g r2 = r1.mConfig
                    android.graphics.Bitmap r3 = r2
                    r2.D = r3
                    r2.F = r0
                    r0 = -1
                    r2.E = r0
                    com.tencent.liteav.d r0 = r1.mCaptureAndEnc
                    r0.a(r2)
                    com.tencent.liteav.trtc.impl.TRTCCloudImpl r0 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                    com.tencent.liteav.trtc.impl.TRTCRoomInfo r1 = r0.mRoomInfo
                    boolean r1 = r1.muteLocalVideo
                    if (r1 == 0) goto L67
                    com.tencent.liteav.g r1 = r0.mConfig
                    android.graphics.Bitmap r1 = r1.D
                    r2 = 2
                    if (r1 == 0) goto L5c
                    com.tencent.liteav.d r0 = r0.mCaptureAndEnc
                    r0.f()
                    com.tencent.liteav.trtc.impl.TRTCCloudImpl r0 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                    r1 = 0
                    com.tencent.liteav.trtc.impl.TRTCCloudImpl.access$4300(r0, r2, r1)
                    goto L67
                L5c:
                    com.tencent.liteav.d r0 = r0.mCaptureAndEnc
                    r0.g()
                    com.tencent.liteav.trtc.impl.TRTCCloudImpl r0 = com.tencent.liteav.trtc.impl.TRTCCloudImpl.this
                    r1 = 1
                    com.tencent.liteav.trtc.impl.TRTCCloudImpl.access$4300(r0, r2, r1)
                L67:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCCloudImpl.AnonymousClass32.run():void");
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public boolean setVoiceChangerType(final int i2) {
        if (i2 >= 0 && i2 <= 11) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.108
                @Override // java.lang.Runnable
                public void run() {
                    TXAudioEffectManagerImpl.getInstance().setVoiceChangerType(TRTCCloudImpl.this.voiceChangerTypes[i2]);
                }
            });
            return true;
        }
        TXCLog.e(TAG, "voiceChangerType not support :" + i2);
        return false;
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setWatermark(final Bitmap bitmap, final int i2, final float f2, final float f3, final float f4) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.83
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("addWatermark stream:" + i2);
                if (i2 != 2) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    com.tencent.liteav.g gVar = tRTCCloudImpl.mConfig;
                    Bitmap bitmap2 = bitmap;
                    gVar.H = bitmap2;
                    float f5 = f2;
                    gVar.K = f5;
                    float f6 = f3;
                    gVar.L = f6;
                    float f7 = f4;
                    gVar.M = f7;
                    tRTCCloudImpl.mCaptureAndEnc.a(bitmap2, f5, f6, f7);
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setZoom(int i2) {
        this.mDeviceManager.setCameraZoomRatio(i2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void showDebugView(int i2) {
        runOnSDKThread(new AnonymousClass118(i2));
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void snapshotVideo(String str, int i2, TRTCCloudListener.TRTCSnapshotListener tRTCSnapshotListener) {
        apiLog(String.format("snapshotVideo user:%s streamType:%d", str, Integer.valueOf(i2)));
        runOnSDKThread(new AnonymousClass25(str, tRTCSnapshotListener, i2));
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int startAudioRecording(TRTCCloudDef.TRTCAudioRecordingParams tRTCAudioRecordingParams) {
        if (TextUtils.isEmpty(tRTCAudioRecordingParams.filePath)) {
            apiLog("startLocalAudioRecord error:" + tRTCAudioRecordingParams.filePath);
            return -1;
        }
        apiLog("startLocalAudioRecord:" + tRTCAudioRecordingParams.filePath);
        TXCAudioEngine.getInstance().setAudioDumpingListener(new TXCAudioEngineJNI.a() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.68
            @Override // com.tencent.liteav.audio.impl.TXCAudioEngineJNI.a
            public void onLocalAudioWriteFailed() {
                TRTCCloudImpl.this.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.68.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                        TRTCCloudListener tRTCCloudListener = tRTCCloudImpl.mTRTCListener;
                        if (tRTCCloudListener == null) {
                            return;
                        }
                        tRTCCloudImpl.apiLog("startLocalAudioRecord onWarning:7001");
                        tRTCCloudListener.onWarning(7001, "write file failed when recording audio.", null);
                    }
                });
            }
        });
        return TXCAudioEngine.getInstance().startLocalAudioDumping(48000, 16, tRTCAudioRecordingParams.recordingContent, tRTCAudioRecordingParams.filePath);
    }

    public void startCollectStatus() {
        com.tencent.liteav.basic.util.f fVar = this.mSDKHandler;
        if (fVar != null) {
            fVar.postDelayed(this.mStatusNotifyTask, 1000L);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startLocalAudio(int i2) {
        setAudioQuality(i2);
        startLocalAudio();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startLocalPreview(final boolean z2, final TXCloudVideoView tXCloudVideoView) {
        this.mDeviceManager.setFrontCamera(z2);
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.18
            @Override // java.lang.Runnable
            public void run() {
                VideoSourceType videoSourceType = TRTCCloudImpl.this.mMainStreamVideoSourceType;
                VideoSourceType videoSourceType2 = VideoSourceType.NONE;
                boolean z3 = videoSourceType != videoSourceType2;
                if (z3) {
                    TRTCCloudImpl.this.apiLog("startLocalPreview just reset view when is started");
                }
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                if (tRTCCloudImpl.mCurrentRole == 21) {
                    tRTCCloudImpl.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.18.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                            if (tRTCCloudListener == null) {
                                return;
                            }
                            tRTCCloudListener.onWarning(6001, "ignore start local preview,for role audience", null);
                        }
                    });
                    TRTCCloudImpl.this.apiLog("ignore startLocalPreview for audience");
                }
                StringBuilder sb = new StringBuilder();
                sb.append("startLocalPreview front:");
                sb.append(z2);
                sb.append(", view:");
                TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                sb.append(tXCloudVideoView2 != null ? Integer.valueOf(tXCloudVideoView2.hashCode()) : "");
                sb.append(" ");
                sb.append(TRTCCloudImpl.this.hashCode());
                TRTCCloudImpl.this.apiOnlineLog(sb.toString());
                TXCEventRecorderProxy.a("18446744073709551615", 4006, 2L, -1L, "", 2);
                TRTCCloudImpl.this.setStartVideoEncodeCodec();
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                tRTCCloudImpl2.mRoomInfo.localView = tXCloudVideoView;
                com.tencent.liteav.g gVar = tRTCCloudImpl2.mConfig;
                gVar.f19342p = z2;
                if (tRTCCloudImpl2.mPerformanceMode == 1) {
                    gVar.X = true;
                }
                tRTCCloudImpl2.mCaptureAndEnc.a(gVar);
                TXCKeyPointReportProxy.a(40046, 1, 2);
                TRTCCloudImpl.this.mIsVideoCapturing = true;
                TRTCCloudImpl.this.mOrientationEventListener.enable();
                TRTCCloudImpl.this.updateOrientation();
                TRTCCloudImpl.this.enableVideoStream(0, true);
                TXCloudVideoView tXCloudVideoView3 = tXCloudVideoView;
                SurfaceView surfaceView = tXCloudVideoView3 != null ? tXCloudVideoView3.getSurfaceView() : null;
                TXCloudVideoView tXCloudVideoView4 = tXCloudVideoView;
                TextureView hWVideoView = tXCloudVideoView4 != null ? tXCloudVideoView4.getHWVideoView() : null;
                if (surfaceView == null && hWVideoView == null) {
                    if (z3 || TRTCCloudImpl.this.mMainStreamVideoSourceType != videoSourceType2) {
                        TRTCCloudImpl.this.apiLog("startLocalPreview with view view when is started");
                    } else {
                        TRTCCloudImpl.this.mMainStreamVideoSourceType = VideoSourceType.CAMERA;
                        TRTCCloudImpl.this.mCaptureAndEnc.a(tXCloudVideoView);
                    }
                } else if (z3 || TRTCCloudImpl.this.mMainStreamVideoSourceType != videoSourceType2) {
                    TRTCCloudImpl.this.apiLog("startLocalPreview with surface view when is started");
                } else {
                    TRTCCloudImpl.this.mMainStreamVideoSourceType = VideoSourceType.CAMERA;
                    TRTCCloudImpl.this.mCaptureAndEnc.a((TXCloudVideoView) null);
                }
                TRTCCloudImpl.this.updateLocalViewInternal(tXCloudVideoView);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startLocalRecording(final TRTCCloudDef.TRTCLocalRecordingParams tRTCLocalRecordingParams) {
        if (tRTCLocalRecordingParams == null) {
            apiLog("startLocalRecording params is null!");
            onLocalRecordBegin(-1, "");
            return;
        }
        int i2 = tRTCLocalRecordingParams.recordType;
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            apiLog("startLocalRecording recordType invalid: " + tRTCLocalRecordingParams.recordType);
            onLocalRecordBegin(-1, tRTCLocalRecordingParams.filePath);
            return;
        }
        int i3 = tRTCLocalRecordingParams.interval;
        if ((i3 >= 1000 && i3 <= 10000) || i3 == -1) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.69
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.apiLog("startLocalRecording params: " + tRTCLocalRecordingParams);
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.nativeStartLocalRecording(tRTCCloudImpl.mNativeRtcContext, tRTCLocalRecordingParams);
                    TRTCCloudImpl.this.mCaptureAndEnc.k(2);
                }
            });
            return;
        }
        apiLog("startLocalRecording interval invalid: " + tRTCLocalRecordingParams.interval);
        onLocalRecordBegin(-1, tRTCLocalRecordingParams.filePath);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startPublishCDNStream(final TRTCCloudDef.TRTCPublishCDNParam tRTCPublishCDNParam) {
        if (tRTCPublishCDNParam == null) {
            apiLog("startPublishCDNStream param is null");
        } else {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.123
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.apiLog("startPublishCDNStream");
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.nativeStartPublishCDNStream(tRTCCloudImpl.mNativeRtcContext, tRTCPublishCDNParam);
                }
            });
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startPublishing(final String str, final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.126
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("startPublishing streamId:" + str + ", streamType:" + i2);
                int i3 = i2 == 2 ? 7 : 2;
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeStartPublishing(tRTCCloudImpl.mNativeRtcContext, str, i3);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startRemoteSubStreamView(final String str, final TXCloudVideoView tXCloudVideoView) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.23
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    TRTCCloudImpl.this.apiLog("startRemoteSubStreamView user is not exist save view" + str);
                    TRTCRoomInfo.UserInfo userInfoCreateUserInfo = TRTCCloudImpl.this.createUserInfo(str);
                    TRTCRoomInfo.RenderInfo renderInfo = userInfoCreateUserInfo.subRender;
                    renderInfo.view = tXCloudVideoView;
                    renderInfo.startRenderView = Boolean.TRUE;
                    TRTCRoomInfo.RenderInfo renderInfo2 = userInfoCreateUserInfo.mainRender;
                    TRTCRoomInfo tRTCRoomInfo = TRTCCloudImpl.this.mRoomInfo;
                    TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState = tRTCRoomInfo.muteRemoteVideo;
                    renderInfo2.muteVideo = tRTCRemoteMuteState;
                    renderInfo2.muteAudio = tRTCRoomInfo.muteRemoteAudio;
                    renderInfo.muteVideo = tRTCRemoteMuteState;
                    tRTCRoomInfo.addUserInfo(str, userInfoCreateUserInfo);
                    return;
                }
                TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                if (tXCloudVideoView2 != null && tXCloudVideoView2.equals(user.subRender.view)) {
                    TRTCCloudImpl.this.apiLog("startRemoteSubStreamView user view is the same, ignore " + str);
                    return;
                }
                TRTCRoomInfo.RenderInfo renderInfo3 = user.subRender;
                boolean z2 = renderInfo3.view != null;
                renderInfo3.view = tXCloudVideoView;
                renderInfo3.startRenderView = Boolean.TRUE;
                if (renderInfo3.tinyID == 0) {
                    TRTCCloudImpl.this.apiLog("startRemoteSubStreamView user tinyID is 0, ignore " + str);
                    return;
                }
                TXCEventRecorderProxy.a(String.valueOf(user.tinyID), R2.attr.zantong, 1L, -1L, "", 7);
                TRTCCloudImpl.this.setRenderView(str, user.subRender, tXCloudVideoView, user.debugMargin);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                Object[] objArr = new Object[4];
                objArr[0] = str;
                objArr[1] = Long.valueOf(user.tinyID);
                objArr[2] = 7;
                TXCloudVideoView tXCloudVideoView3 = tXCloudVideoView;
                objArr[3] = Integer.valueOf(tXCloudVideoView3 != null ? tXCloudVideoView3.hashCode() : 0);
                tRTCCloudImpl.apiOnlineLog(String.format("startRemoteSubStreamView userID:%s tinyID:%d streamType:%d view:%d", objArr));
                TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(user.tinyID), 7, 0, "Start watching " + str);
                if (!z2 || !user.subRender.render.isRendering()) {
                    TRTCCloudImpl.this.startRemoteRender(user.subRender.render, 7);
                }
                TRTCRoomInfo.RenderInfo renderInfo4 = user.subRender;
                if (renderInfo4.muteVideo == TRTCRoomInfo.TRTCRemoteMuteState.MUTE) {
                    TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                    tRTCCloudImpl2.nativeCancelDownStream(tRTCCloudImpl2.mNativeRtcContext, user.tinyID, 7, true);
                } else {
                    renderInfo4.lastVideoStatusChangeOperation = 2;
                    TRTCCloudImpl tRTCCloudImpl3 = TRTCCloudImpl.this;
                    tRTCCloudImpl3.nativeRequestDownStream(tRTCCloudImpl3.mNativeRtcContext, user.tinyID, 7, true);
                    TXCKeyPointReportProxy.a(String.valueOf(user.tinyID), 40021, 0L, 7);
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startRemoteView(final String str, final TXCloudVideoView tXCloudVideoView) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.22
            @Override // java.lang.Runnable
            public void run() {
                TRTCRoomInfo.UserInfo user = TRTCCloudImpl.this.mRoomInfo.getUser(str);
                if (user == null) {
                    TRTCRoomInfo.UserInfo userInfoCreateUserInfo = TRTCCloudImpl.this.createUserInfo(str);
                    TRTCRoomInfo.RenderInfo renderInfo = userInfoCreateUserInfo.mainRender;
                    renderInfo.view = tXCloudVideoView;
                    renderInfo.startRenderView = Boolean.TRUE;
                    TRTCRoomInfo tRTCRoomInfo = TRTCCloudImpl.this.mRoomInfo;
                    TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState = tRTCRoomInfo.muteRemoteVideo;
                    renderInfo.muteVideo = tRTCRemoteMuteState;
                    renderInfo.muteAudio = tRTCRoomInfo.muteRemoteAudio;
                    userInfoCreateUserInfo.subRender.muteVideo = tRTCRemoteMuteState;
                    tRTCRoomInfo.addUserInfo(str, userInfoCreateUserInfo);
                    TRTCCloudImpl.this.apiOnlineLog(String.format("Remote-startRemoteView userID:%s (save view before user enter)", str) + " self:" + TRTCCloudImpl.this.hashCode());
                    return;
                }
                TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                if (tXCloudVideoView2 != null && tXCloudVideoView2.equals(user.mainRender.view)) {
                    TRTCCloudImpl.this.apiLog("startRemoteView user view is the same, ignore " + str);
                    return;
                }
                TRTCRoomInfo.RenderInfo renderInfo2 = user.mainRender;
                boolean z2 = renderInfo2.view != null;
                TXCloudVideoView tXCloudVideoView3 = tXCloudVideoView;
                renderInfo2.view = tXCloudVideoView3;
                renderInfo2.startRenderView = Boolean.TRUE;
                if (renderInfo2.tinyID == 0) {
                    TRTCCloudImpl.this.apiLog("startRemoteView user tinyID is 0, ignore " + str);
                    return;
                }
                TRTCCloudImpl.this.setRenderView(str, renderInfo2, tXCloudVideoView3, user.debugMargin);
                StringBuilder sb = new StringBuilder();
                Object[] objArr = new Object[4];
                objArr[0] = str;
                objArr[1] = Long.valueOf(user.tinyID);
                objArr[2] = Integer.valueOf(user.streamType);
                TXCloudVideoView tXCloudVideoView4 = tXCloudVideoView;
                objArr[3] = Integer.valueOf(tXCloudVideoView4 != null ? tXCloudVideoView4.hashCode() : 0);
                sb.append(String.format("Remote-startRemoteView userID:%s tinyID:%d streamType:%d view:%d", objArr));
                sb.append(" self:");
                sb.append(TRTCCloudImpl.this.hashCode());
                TRTCCloudImpl.this.apiOnlineLog(sb.toString());
                TRTCCloudImpl.this.notifyLogByUserId(String.valueOf(user.tinyID), user.streamType, 0, "Start watching " + str);
                if (!z2 || !user.mainRender.render.isRendering()) {
                    TRTCCloudImpl.this.startRemoteRender(user.mainRender.render, user.streamType);
                }
                TRTCRoomInfo.RenderInfo renderInfo3 = user.mainRender;
                if (renderInfo3.muteVideo == TRTCRoomInfo.TRTCRemoteMuteState.MUTE) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.nativeCancelDownStream(tRTCCloudImpl.mNativeRtcContext, user.tinyID, user.streamType, true);
                } else {
                    renderInfo3.lastVideoStatusChangeOperation = 2;
                    TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                    tRTCCloudImpl2.nativeRequestDownStream(tRTCCloudImpl2.mNativeRtcContext, user.tinyID, user.streamType, true);
                    TXCKeyPointReportProxy.a(String.valueOf(user.tinyID), 40021, 0L, user.streamType);
                }
                TXCEventRecorderProxy.a(String.valueOf(user.tinyID), R2.attr.zantong, 1L, -1L, "", 0);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startScreenCapture(final int i2, final TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam, final TRTCCloudDef.TRTCScreenShareParams tRTCScreenShareParams) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.26
            @Override // java.lang.Runnable
            public void run() {
                if (i2 == 2 ? TRTCCloudImpl.this.startSubStreamScreenCapture(tRTCVideoEncParam) : TRTCCloudImpl.this.startMainStreamScreenCapture(tRTCVideoEncParam)) {
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    if (tRTCCloudImpl.mCurrentRole == 21) {
                        tRTCCloudImpl.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.26.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                                if (tRTCCloudListener == null) {
                                    return;
                                }
                                tRTCCloudListener.onWarning(6001, "ignore start local preview,for role audience", null);
                            }
                        });
                        TRTCCloudImpl.this.apiLog("ignore startLocalPreview for audience");
                    }
                    TRTCCloudImpl.this.apiOnlineLog("startScreenCapture streamType:%d", Integer.valueOf(i2));
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.26.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass26 anonymousClass26 = AnonymousClass26.this;
                            TRTCCloudDef.TRTCScreenShareParams tRTCScreenShareParams2 = tRTCScreenShareParams;
                            if (tRTCScreenShareParams2 != null) {
                                TRTCCloudImpl.this.showFloatingWindow(tRTCScreenShareParams2.floatingView);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startSpeedTest(final int i2, final String str, final String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.120
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.apiLog("startSpeedTest");
                    TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                    tRTCCloudImpl.nativeStartSpeedTest(tRTCCloudImpl.mNativeRtcContext, i2, str, str2, -1, -1, 0);
                }
            });
            return;
        }
        TXCLog.e(TAG, "startSpeedTest failed with invalid params. userId = " + str + ", userSig = " + str2 + " self:" + hashCode());
    }

    public void startVolumeLevelCal(boolean z2) {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioVolumeEvaluation(z2, this.mAudioVolumeEvalInterval);
        if (!z2) {
            this.mVolumeLevelNotifyTask = null;
            this.mAudioVolumeEvalInterval = 0;
        } else if (this.mVolumeLevelNotifyTask == null) {
            VolumeLevelNotifyTask volumeLevelNotifyTask = new VolumeLevelNotifyTask(this);
            this.mVolumeLevelNotifyTask = volumeLevelNotifyTask;
            this.mSDKHandler.postDelayed(volumeLevelNotifyTask, this.mAudioVolumeEvalInterval);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopAllAudioEffects() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.114
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("stopAllAudioEffects");
                TXCSoundEffectPlayer.getInstance().stopAllEffect();
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopAllRemoteView() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.24
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("stopAllRemoteView");
                TRTCCloudImpl.this.mRoomInfo.forEachUser(new TRTCRoomInfo.UserAction() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.24.1
                    @Override // com.tencent.liteav.trtc.impl.TRTCRoomInfo.UserAction
                    public void accept(String str, TRTCRoomInfo.UserInfo userInfo) {
                        TRTCCloudImpl.this.stopRemoteMainRender(userInfo, Boolean.TRUE);
                        TRTCCloudImpl.this.stopRemoteSubRender(userInfo);
                        TRTCRoomInfo.RenderInfo renderInfo = userInfo.mainRender;
                        renderInfo.view = null;
                        Boolean bool = Boolean.FALSE;
                        renderInfo.startRenderView = bool;
                        TRTCRoomInfo.RenderInfo renderInfo2 = userInfo.subRender;
                        renderInfo2.view = null;
                        renderInfo2.startRenderView = bool;
                    }
                });
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopAudioEffect(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.113
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("stopAudioEffect -> effectId = " + i2);
                TXCSoundEffectPlayer.getInstance().stopEffectWithId(i2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopAudioRecording() {
        TXCAudioEngine.getInstance().stopLocalAudioDumping();
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopBGM() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.99
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                TRTCCloudImpl.this.apiLog("stopBGM");
                TXCLiveBGMPlayer.getInstance().stopPlay();
                TRTCCloudImpl.this.mBGMNotify = null;
            }
        });
    }

    public void stopCollectStatus() {
        com.tencent.liteav.basic.util.f fVar = this.mSDKHandler;
        if (fVar != null) {
            fVar.removeCallbacks(this.mStatusNotifyTask);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopLocalAudio() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.55
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.stopLocalAudioInternal();
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopLocalPreview() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.21
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("stopLocalPreview");
                if (TRTCCloudImpl.this.mMainStreamVideoSourceType == VideoSourceType.CAMERA) {
                    TRTCCloudImpl.this.mMainStreamVideoSourceType = VideoSourceType.NONE;
                    TRTCCloudImpl.this.mCaptureAndEnc.d(true);
                }
                TXCloudVideoView tXCloudVideoView = TRTCCloudImpl.this.mRoomInfo.localView;
                if (tXCloudVideoView != null) {
                    final SurfaceView surfaceView = tXCloudVideoView.getSurfaceView();
                    final TextureView hWVideoView = TRTCCloudImpl.this.mRoomInfo.localView.getHWVideoView();
                    TRTCCloudImpl.this.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.21.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SurfaceView surfaceView2 = surfaceView;
                            if (surfaceView2 != null) {
                                surfaceView2.getHolder().removeCallback(TRTCCloudImpl.this.mLocalPreviewSurfaceViewCallback);
                                return;
                            }
                            TextureView textureView = hWVideoView;
                            if (textureView != null) {
                                textureView.setSurfaceTextureListener(null);
                            }
                        }
                    });
                }
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.mRoomInfo.localView = null;
                tRTCCloudImpl.mIsVideoCapturing = false;
                TRTCCloudImpl.this.mOrientationEventListener.disable();
                if (!TRTCCloudImpl.this.mEnableCustomVideoCapture) {
                    TRTCCloudImpl.this.enableVideoStream(0, false);
                }
                TXCKeyPointReportProxy.a(40046, 0, 2);
                TXCEventRecorderProxy.a("18446744073709551615", 4006, 3L, -1L, "", 2);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopLocalRecording() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.70
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("stopLocalRecording");
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeStopLocalRecording(tRTCCloudImpl.mNativeRtcContext);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopPublishCDNStream() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.125
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("stopPublishCDNStream");
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeStopPublishCDNStream(tRTCCloudImpl.mNativeRtcContext);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopPublishing() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.124
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("stopPublishing");
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeStopPublishing(tRTCCloudImpl.mNativeRtcContext);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopRemoteSubStreamView(String str) {
        stopRemoteView(str, 2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopRemoteView(String str) {
        stopRemoteView(str, 0);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopScreenCapture() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.27
            @Override // java.lang.Runnable
            public void run() {
                VideoSourceType videoSourceType = TRTCCloudImpl.this.mMainStreamVideoSourceType;
                VideoSourceType videoSourceType2 = VideoSourceType.SCREEN;
                if (videoSourceType != videoSourceType2 && TRTCCloudImpl.this.mSubStreamVideoSourceType != videoSourceType2) {
                    TRTCCloudImpl.this.apiOnlineLog("stopScreenCapture been ignored for Screen capture is not started");
                    return;
                }
                if (TRTCCloudImpl.this.mMainStreamVideoSourceType == videoSourceType2) {
                    TRTCCloudImpl.this.stopMainStreamScreenCapture();
                } else {
                    TRTCCloudImpl.this.stopSubStreamScreenCapture();
                }
                TRTCCloudImpl.this.runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.27.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TRTCCloudImpl.this.hideFloatingWindow();
                    }
                });
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopSpeedTest() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.122
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("stopSpeedTest");
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.nativeStopSpeedTest(tRTCCloudImpl.mNativeRtcContext);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void switchCamera() {
        this.mDeviceManager.switchCamera(!r0.isFrontCamera());
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void switchRole(final int i2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.14
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                Object[] objArr = new Object[1];
                objArr[0] = i2 == 20 ? "Anchor" : "Audience";
                tRTCCloudImpl.apiOnlineLog(String.format("switchRole:%s", objArr));
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                int i3 = i2;
                tRTCCloudImpl2.mTargetRole = i3;
                tRTCCloudImpl2.nativeChangeRole(tRTCCloudImpl2.mNativeRtcContext, i3);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void switchRoom(final TRTCCloudDef.TRTCSwitchRoomConfig tRTCSwitchRoomConfig) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.17
            /* JADX WARN: Removed duplicated region for block: B:27:0x00fc  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x0108  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x0113  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    Method dump skipped, instructions count: 295
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.trtc.impl.TRTCCloudImpl.AnonymousClass17.run():void");
            }
        });
    }

    public void updateAppScene(int i2) {
        this.mAppScene = i2;
        if (i2 != 0 && i2 != 1) {
            this.mAppScene = 0;
        }
        com.tencent.liteav.g gVar = this.mConfig;
        if (gVar.f19327a * gVar.f19328b >= 518400) {
            this.mAppScene = 1;
        }
        updateEncType();
        apiLog(String.format("update appScene[%d] for video enc[%d] source scene[%d]", Integer.valueOf(this.mAppScene), Integer.valueOf(this.mConfig.f19339m), Integer.valueOf(i2)));
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void updateLocalView(final TXCloudVideoView tXCloudVideoView) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.20
            @Override // java.lang.Runnable
            public void run() {
                TXCloudVideoView tXCloudVideoView2;
                if (TRTCCloudImpl.this.mMainStreamVideoSourceType == VideoSourceType.NONE) {
                    TRTCCloudImpl.this.apiLog("local capture is not started, you should start it first.");
                    return;
                }
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                TXCloudVideoView tXCloudVideoView3 = tRTCCloudImpl.mRoomInfo.localView;
                TXCloudVideoView tXCloudVideoView4 = tXCloudVideoView;
                if (tXCloudVideoView3 == tXCloudVideoView4) {
                    tRTCCloudImpl.apiLog("update local view with same view, ignore this call");
                    return;
                }
                if (tXCloudVideoView4 == null || tXCloudVideoView4.getHWVideoView() == null || (tXCloudVideoView2 = TRTCCloudImpl.this.mRoomInfo.localView) == null || tXCloudVideoView2.getHWVideoView() == null) {
                    TRTCCloudImpl.this.apiLog("updateLocalView only support TXCloudVideoView with TextureView.");
                    return;
                }
                TRTCCloudImpl.this.apiLog("update local view with %s", tXCloudVideoView);
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                TRTCRoomInfo tRTCRoomInfo = tRTCCloudImpl2.mRoomInfo;
                TXCloudVideoView tXCloudVideoView5 = tXCloudVideoView;
                tRTCRoomInfo.localView = tXCloudVideoView5;
                tRTCCloudImpl2.updateLocalViewInternal(tXCloudVideoView5);
            }
        });
    }

    public void updatePrivateMapKey(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            apiLog("callExperimentalAPI[update private map key fail, params is null");
            return;
        }
        String string = jSONObject.getString("privateMapKey");
        if (TextUtils.isEmpty(string)) {
            apiLog("callExperimentalAPI[update private map key fail, key is empty");
        } else {
            nativeUpdatePrivateMapKey(this.mNativeRtcContext, string);
        }
    }

    public void updateRemoteVideoStatusByMute(TRTCRoomInfo.UserInfo userInfo, boolean z2, int i2) {
        TRTCRoomInfo.TRTCRemoteMuteState tRTCRemoteMuteState = z2 ? TRTCRoomInfo.TRTCRemoteMuteState.MUTE : TRTCRoomInfo.TRTCRemoteMuteState.UNMUTE;
        if (i2 == 7) {
            TRTCRoomInfo.RenderInfo renderInfo = userInfo.subRender;
            if (renderInfo.muteVideo == tRTCRemoteMuteState || renderInfo.render == null) {
                return;
            }
            if (!z2) {
                renderInfo.lastVideoStatusChangeOperation = 2;
                return;
            } else {
                renderInfo.lastVideoStatusChangeOperation = 1;
                onRemoteVideoStatusUpdatedInternal(userInfo, 7, 0, 4);
                return;
            }
        }
        TRTCRoomInfo.RenderInfo renderInfo2 = userInfo.mainRender;
        if (renderInfo2.muteVideo == tRTCRemoteMuteState || renderInfo2.render == null) {
            return;
        }
        if (!z2) {
            renderInfo2.lastVideoStatusChangeOperation = 2;
        } else {
            renderInfo2.lastVideoStatusChangeOperation = 1;
            onRemoteVideoStatusUpdatedInternal(userInfo, 2, 0, 4);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void updateRemoteView(final String str, final int i2, final TXCloudVideoView tXCloudVideoView) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.33
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.updateRemoteViewInternal(str, i2, tXCloudVideoView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendDashboardLog(String str, int i2, final String str2, String str3) {
        final TXCloudVideoView tXCloudVideoView;
        String str4;
        apiLog(str3 + str2);
        if (TextUtils.isEmpty(str) || ((str4 = this.mRoomInfo.userId) != null && str.equalsIgnoreCase(str4))) {
            tXCloudVideoView = this.mRoomInfo.localView;
        } else {
            TRTCRoomInfo.UserInfo user = this.mRoomInfo.getUser(str);
            tXCloudVideoView = user != null ? i2 == 7 ? user.subRender.view : user.mainRender.view : null;
        }
        runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.193
            @Override // java.lang.Runnable
            public void run() {
                TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                if (tXCloudVideoView2 != null) {
                    tXCloudVideoView2.appendEventInfo(str2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyEvent(String str, int i2, String str2) {
        Bundle bundle = new Bundle();
        bundle.putLong("EVT_ID", i2);
        bundle.putLong("EVT_TIME", System.currentTimeMillis());
        bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str2);
        notifyEvent(str, i2, bundle);
    }

    private void onSpeedTest(final boolean z2, final String str, final String str2, final int i2, final float f2, final float f3, final int i3, final int i4, final int i5) {
        apiLog("onSpeedTest success:%b errMsg:%s ip:%s quality:%d upLoss:%f downLoss:%f rtt:%d availableUpBandwidth:%d availableDownBandwidth:%d", Boolean.valueOf(z2), str, str2, Integer.valueOf(i2), Float.valueOf(f2), Float.valueOf(f3), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.172
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                if (tRTCCloudListener != null) {
                    TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult = new TRTCCloudDef.TRTCSpeedTestResult();
                    tRTCSpeedTestResult.success = z2;
                    tRTCSpeedTestResult.errMsg = str;
                    tRTCSpeedTestResult.ip = str2;
                    tRTCSpeedTestResult.quality = i2;
                    tRTCSpeedTestResult.upLostRate = f2;
                    tRTCSpeedTestResult.downLostRate = f3;
                    tRTCSpeedTestResult.rtt = i3;
                    tRTCSpeedTestResult.availableUpBandwidth = i4;
                    tRTCSpeedTestResult.availableDownBandwidth = i5;
                    tRTCCloudListener.onSpeedTestResult(tRTCSpeedTestResult);
                }
            }
        });
    }

    public void apiLog(String str, Object... objArr) {
        TXCLog.i(TAG, "(" + hashCode() + ")trtc_api " + String.format(str, objArr));
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void enableCustomVideoCapture(boolean z2) {
        enableCustomVideoCapture(0, z2);
    }

    public void muteLocalAudio(final boolean z2, final TRTCCloudImpl tRTCCloudImpl) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.58
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this.mCurrentPublishClouds.get(1);
                boolean z3 = z2;
                if (z3) {
                    if (tRTCCloudImpl2 == tRTCCloudImpl) {
                        TRTCCloudImpl.this.mRoomInfo.muteLocalAudio = z3;
                        TXCAudioEngine.getInstance().muteLocalAudio(z2);
                        TRTCCloudImpl.this.muteUpstream(1, z2);
                        return;
                    }
                    return;
                }
                if (tRTCCloudImpl2 != tRTCCloudImpl) {
                    TRTCCloudImpl.this.enableAudioStream(false);
                    synchronized (TRTCCloudImpl.this.mCurrentPublishClouds) {
                        TRTCCloudImpl.this.mCurrentPublishClouds.put(1, tRTCCloudImpl);
                    }
                    TRTCCloudImpl.this.setAudioEncodeConfiguration();
                }
                TRTCCloudImpl.this.mRoomInfo.muteLocalAudio = z2;
                TXCAudioEngine.getInstance().muteLocalAudio(z2);
                TRTCCloudImpl.this.muteUpstream(1, z2);
                TRTCCloudImpl.this.enableAudioStream(true);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteLocalVideo(final int i2, final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.31
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("muteLocalVideo mute:" + z2 + "streamType:" + i2 + ", pauseImg:" + TRTCCloudImpl.this.mConfig.D);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.muteLocalVideo(i2, z2, tRTCCloudImpl);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void muteRemoteVideoStream(final String str, final int i2, final boolean z2) {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.37
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiOnlineLog("muteRemoteVideoStream userId:" + str + ", streamType:" + i2 + ", mute:" + z2);
                TRTCCloudImpl.this.muteRemoteVideoStreamInternal(str, i2, z2, true);
            }
        });
    }

    public void onAudioQosChanged(TRTCCloudImpl tRTCCloudImpl, final int i2, final int i3, final int i4) {
        if (isPublishingInCloud(tRTCCloudImpl, 1)) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.160
                @Override // java.lang.Runnable
                public void run() {
                    TXCAudioEngine.getInstance().setAudioEncoderParam(i2, i3);
                    TXCAudioEngine.getInstance().setEncoderFECPercent(i4);
                }
            });
        }
    }

    public void onIdrFpsChanged(TRTCCloudImpl tRTCCloudImpl, final int i2) {
        if (isPublishingInCloud(tRTCCloudImpl, 2)) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.163
                @Override // java.lang.Runnable
                public void run() {
                    TRTCCloudImpl.this.mCaptureAndEnc.c(i2);
                }
            });
        }
    }

    public void onVideoConfigChanged(TRTCCloudImpl tRTCCloudImpl, final int i2, final boolean z2) {
        if (isPublishingInCloud(tRTCCloudImpl, i2)) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.173
                @Override // java.lang.Runnable
                public void run() {
                    if (i2 == 2) {
                        if (TRTCCloudImpl.this.isRPSSupported()) {
                            TRTCCloudImpl.this.mCaptureAndEnc.g(z2);
                        } else {
                            TRTCCloudImpl.this.mCaptureAndEnc.g(false);
                        }
                    }
                }
            });
        }
    }

    public void onVideoQosChanged(TRTCCloudImpl tRTCCloudImpl, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int i8, final int i9) {
        if (isPublishingInCloud(tRTCCloudImpl, i2)) {
            runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.161
                @Override // java.lang.Runnable
                public void run() {
                    boolean z2 = i9 == 1;
                    int i10 = i2;
                    if (i10 == 7) {
                        com.tencent.liteav.d dVar = TRTCCloudImpl.this.mSubStreamCaptureAndEnc;
                        if (dVar != null) {
                            dVar.a(i10, i3, i4, i5, i6, i7, i8, z2);
                        }
                    } else if (i10 == 2) {
                        TRTCCloudImpl.this.mCaptureAndEnc.a(i10, i3, i4, i5, i6, i7, i8, z2);
                        int i11 = i3;
                        int i12 = i4;
                        int i13 = i11 <= i12 ? 1 : 0;
                        TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                        com.tencent.liteav.g gVar = tRTCCloudImpl2.mConfig;
                        if (gVar.f19341o != i13 && i11 != i12) {
                            gVar.f19341o = i13;
                            tRTCCloudImpl2.updateOrientation();
                        }
                    }
                    TRTCCloudImpl.this.mH265Decision.setEnableH265EncodeByServer(z2, TRTCEncodeTypeDecision.ModifyCodecReason.REASON_QOS);
                }
            });
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setRemoteViewFillMode(String str, int i2) {
        setRemoteViewFillMode(str, 0, i2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void setRemoteViewRotation(String str, int i2) {
        setRemoteViewRotation(str, 0, i2);
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startRemoteView(String str, int i2, TXCloudVideoView tXCloudVideoView) {
        if (i2 == 0 || i2 == 1) {
            startRemoteView(str, tXCloudVideoView);
            setRemoteVideoStreamType(str, i2);
        } else {
            if (i2 == 2) {
                startRemoteSubStreamView(str, tXCloudVideoView);
                return;
            }
            TXCLog.e(TAG, "startRemoteView unsupported streamType:" + i2);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startScreenCapture(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam, TRTCCloudDef.TRTCScreenShareParams tRTCScreenShareParams) {
        startScreenCapture(0, tRTCVideoEncParam, tRTCScreenShareParams);
    }

    public void stopRemoteRender(TRTCRoomInfo.UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        apiLog(String.format("stopRemoteRender userID:%s tinyID:%d streamType:%d", userInfo.userID, Long.valueOf(userInfo.tinyID), Integer.valueOf(userInfo.streamType)));
        com.tencent.liteav.audio.a.a().a(String.valueOf(userInfo.tinyID), hashCode());
        TRTCRoomInfo.RenderInfo renderInfo = userInfo.mainRender;
        final TXCloudVideoView tXCloudVideoView = renderInfo.view;
        final TXCloudVideoView tXCloudVideoView2 = userInfo.subRender.view;
        TXCRenderAndDec tXCRenderAndDec = renderInfo.render;
        if (tXCRenderAndDec != null) {
            tXCRenderAndDec.setVideoFrameListener(null, com.tencent.liteav.basic.enums.b.UNKNOWN);
            userInfo.mainRender.render.stop();
            if ((tXCloudVideoView == null || tXCloudVideoView.getOpenGLContext() != null) && userInfo.mainRender.render.getVideoRender() != null) {
                userInfo.mainRender.render.getVideoRender().d();
            }
        }
        TXCRenderAndDec tXCRenderAndDec2 = userInfo.subRender.render;
        if (tXCRenderAndDec2 != null) {
            tXCRenderAndDec2.setVideoFrameListener(null, com.tencent.liteav.basic.enums.b.UNKNOWN);
            userInfo.subRender.render.stop();
            if ((tXCloudVideoView2 == null || tXCloudVideoView2.getOpenGLContext() != null) && userInfo.subRender.render.getVideoRender() != null) {
                userInfo.subRender.render.getVideoRender().d();
            }
        }
        userInfo.mainRender.stop();
        userInfo.subRender.stop();
        runOnMainThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.191
            @Override // java.lang.Runnable
            public void run() {
                TXCloudVideoView tXCloudVideoView3 = tXCloudVideoView;
                if (tXCloudVideoView3 != null) {
                    tXCloudVideoView3.removeVideoView();
                }
                TXCloudVideoView tXCloudVideoView4 = tXCloudVideoView2;
                if (tXCloudVideoView4 != null) {
                    tXCloudVideoView4.removeVideoView();
                }
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void stopRemoteView(String str, int i2) {
        if (i2 == 0 || i2 == 1 || i2 == 2) {
            removeRemoteView(str, i2);
            stopRemoteRender(str, i2);
        } else {
            TXCLog.e(TAG, "stopRemoteView unsupported streamType:" + i2);
        }
    }

    public void apiOnlineLog(String str, Object... objArr) {
        Monitor.a(1, String.format(str, objArr) + " self:" + hashCode(), "", 0, "(" + hashCode() + ")trtc_api");
    }

    public void muteLocalVideo(int i2, boolean z2, TRTCCloudImpl tRTCCloudImpl) {
        int i3 = i2 == 2 ? 7 : 2;
        TRTCCloudImpl tRTCCloudImpl2 = this.mCurrentPublishClouds.get(Integer.valueOf(i3));
        if (z2) {
            if (tRTCCloudImpl2 == tRTCCloudImpl) {
                if (i2 != 2) {
                    this.mRoomInfo.muteLocalVideo = z2;
                    enableNetworkBlackStream(this.mCaptureAndEnc.h());
                    if (this.mConfig.D != null) {
                        this.mCaptureAndEnc.f();
                    } else {
                        muteUpstream(2, z2);
                        if (this.mEnableSmallStream) {
                            TXCEventRecorderProxy.a("18446744073709551615", 4006, 1L, -1L, "", 3);
                        }
                    }
                } else {
                    this.mRoomInfo.muteLocalSubVideo = z2;
                    muteUpstream(7, z2);
                }
                TXCEventRecorderProxy.a("18446744073709551615", 4006, 1L, -1L, "", i3);
                return;
            }
            return;
        }
        if (tRTCCloudImpl2 != tRTCCloudImpl) {
            enableVideoStream(i2, false);
            synchronized (this.mCurrentPublishClouds) {
                if (i2 != 2) {
                    this.mCurrentPublishClouds.put(2, tRTCCloudImpl);
                    this.mCurrentPublishClouds.put(3, tRTCCloudImpl);
                } else {
                    this.mCurrentPublishClouds.put(7, tRTCCloudImpl);
                }
            }
            setVideoQuality(this.mQosMode, this.mQosPreference);
            if (i2 != 2) {
                enableNetworkBlackStream(this.mCaptureAndEnc.h());
                enableNetworkSmallStream(this.mEnableSmallStream);
                flushBigVideoEncParamsIntoNetwork();
                flushSmallVideoEncParamsIntoNetwork();
            } else {
                flushSubVideoEncParamsIntoNetwork();
            }
            enableVideoStream(i2, true);
        }
        if (i2 != 2) {
            this.mCaptureAndEnc.g();
            this.mRoomInfo.muteLocalVideo = z2;
            enableNetworkBlackStream(this.mCaptureAndEnc.h());
            muteUpstream(2, z2);
            this.mCaptureAndEnc.k(2);
            if (this.mEnableSmallStream) {
                this.mCaptureAndEnc.k(3);
                TXCEventRecorderProxy.a("18446744073709551615", 4006, 0L, -1L, "", 3);
            }
        } else {
            this.mRoomInfo.muteLocalSubVideo = z2;
            muteUpstream(7, z2);
            this.mCaptureAndEnc.k(7);
        }
        TXCEventRecorderProxy.a("18446744073709551615", 4006, 0L, -1L, "", i3);
    }

    public void runOnSDKThread(Runnable runnable) {
        if (this.mSDKHandler != null && this.mIsSDKThreadAlive.get()) {
            if (Looper.myLooper() != this.mSDKHandler.getLooper()) {
                try {
                    this.mSDKHandler.post(runnable);
                    return;
                } catch (Exception e2) {
                    TXCLog.e(TAG, "(" + hashCode() + ")trtc_api run on sdk fail. alive:" + this.mIsSDKThreadAlive.get(), e2);
                    return;
                }
            }
            runnable.run();
            return;
        }
        TXCLog.e(TAG, "(" + hashCode() + ")trtc_api sdk thread is dead, ignore task.");
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void startLocalAudio() {
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.54
            @Override // java.lang.Runnable
            public void run() {
                if (TRTCCloudImpl.this.mEnableCustomAudioCapture) {
                    TRTCCloudImpl.this.apiLog("startLocalAudio when enable custom audio capturing, ignore!!!");
                    return;
                }
                if (TRTCCloudImpl.this.mIsAudioCapturing) {
                    TRTCCloudImpl.this.apiLog("startLocalAudio when capturing audio, ignore!!!");
                    return;
                }
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                if (tRTCCloudImpl.mCurrentRole == 21) {
                    tRTCCloudImpl.runOnListenerThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.54.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TRTCCloudListener tRTCCloudListener = TRTCCloudImpl.this.mTRTCListener;
                            if (tRTCCloudListener == null) {
                                return;
                            }
                            tRTCCloudListener.onWarning(6001, "ignore start local audio,for role audience", null);
                        }
                    });
                    TRTCCloudImpl.this.apiLog("ignore startLocalAudio,for role audience");
                }
                TRTCCloudImpl.this.apiOnlineLog("startLocalAudio");
                TXCEventRecorderProxy.a("18446744073709551615", 3001, 0L, -1L, "", 0);
                TXCKeyPointReportProxy.a(30002);
                TRTCCloudImpl.this.mIsAudioCapturing = true;
                TRTCCloudImpl tRTCCloudImpl2 = TRTCCloudImpl.this;
                tRTCCloudImpl2.mCaptureAndEnc.a(tRTCCloudImpl2.mConfig);
                TRTCCloudImpl.this.setQoSParams();
                TXCAudioEngine.getInstance().enableCaptureEOSMode(TRTCCloudImpl.this.mEnableEosMode);
                TXCAudioEngineJNI.nativeUseSysAudioDevice(false);
                TXCAudioEngine.getInstance().startLocalAudio(11, false);
                TXCAudioEngine.getInstance().enableEncodedDataPackWithTRAEHeaderCallback(true);
                TXCAudioEngine.getInstance().muteLocalAudio(TRTCCloudImpl.this.mRoomInfo.muteLocalAudio);
                TXCEventRecorderProxy.a("18446744073709551615", 3003, 11L, -1L, "", 0);
                TRTCCloudImpl.this.enableAudioStream(true);
                TXCKeyPointReportProxy.a(40050, 1, 1);
            }
        });
    }

    @Override // com.tencent.trtc.TRTCCloud
    public int startSpeedTest(final TRTCCloudDef.TRTCSpeedTestParams tRTCSpeedTestParams) {
        if (tRTCSpeedTestParams == null) {
            TXCLog.e(TAG, "startSpeedTest failed. empty params. self:" + hashCode());
            return -1;
        }
        runOnSDKThread(new Runnable() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.121
            @Override // java.lang.Runnable
            public void run() {
                TRTCCloudImpl.this.apiLog("startSpeedTest parmas:" + tRTCSpeedTestParams);
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                long j2 = tRTCCloudImpl.mNativeRtcContext;
                TRTCCloudDef.TRTCSpeedTestParams tRTCSpeedTestParams2 = tRTCSpeedTestParams;
                tRTCCloudImpl.nativeStartSpeedTest(j2, tRTCSpeedTestParams2.sdkAppId, tRTCSpeedTestParams2.userId, tRTCSpeedTestParams2.userSig, tRTCSpeedTestParams2.expectedUpBandwidth, tRTCSpeedTestParams2.expectedDownBandwidth, 1);
            }
        });
        return 0;
    }

    private void runOnListenerThread(Runnable runnable, int i2) {
        Handler handler = this.mListenerHandler;
        if (handler == null) {
            this.mMainHandler.postDelayed(runnable, i2);
        } else {
            handler.postDelayed(runnable, i2);
        }
    }

    @Override // com.tencent.trtc.TRTCCloud
    public void sendCustomVideoData(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        sendCustomVideoData(0, tRTCVideoFrame);
    }

    public TRTCCloudImpl(Context context, com.tencent.liteav.basic.util.f fVar) {
        this.reverbTypes = new TXAudioEffectManager.TXVoiceReverbType[]{TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_0, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_1, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_2, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_3, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_4, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_5, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_6, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_7, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_8, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_9, TXAudioEffectManager.TXVoiceReverbType.TXLiveVoiceReverbType_10};
        this.voiceChangerTypes = new TXAudioEffectManager.TXVoiceChangerType[]{TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_0, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_1, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_2, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_3, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_4, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_5, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_6, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_7, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_8, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_9, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_10, TXAudioEffectManager.TXVoiceChangerType.TXLiveVoiceChangerType_11};
        this.reverbParamKeyNames = new ArrayList<>(Arrays.asList("dampingFreq", "density", "bandwidthFreq", "decay", "preDelay", "roomSize", "gain", "mix", "earlyMix"));
        this.mNativeLock = new Object();
        this.mIsDestroyed = false;
        this.mAudioFrameListener = null;
        this.mCustomCaptureLock = new Object();
        this.mCustomSubStreamVideoUtil = null;
        this.mPublishAudioTunnelId = -1;
        this.mPlayoutAudioTunnelId = -1;
        this.mPriorStreamType = 2;
        this.mEnableSmallStream = false;
        this.mVideoRenderMirror = 0;
        this.mCustomRemoteRender = false;
        this.mAudioVolumeEvalInterval = 0;
        this.mSmallEncParam = new TRTCCloudDef.TRTCVideoEncParam();
        this.mQosMode = 1;
        this.mEnableEosMode = false;
        this.mCodecType = 2;
        this.mEnableSoftAEC = true;
        this.mEnableSoftANS = false;
        this.mEnableSoftAGC = false;
        this.mSoftAECLevel = 100;
        this.mSoftANSLevel = 100;
        this.mSoftAGCLevel = 100;
        this.mAudioCaptureVolume = 100;
        this.mAudioPlayoutVolume = 100;
        this.mCustomVideoUtil = null;
        this.mEnableCustomAudioCapture = false;
        this.mEnableCustomAudioRendering = false;
        this.mEnableCustomVideoCapture = false;
        this.mCurrentRole = 20;
        this.mTargetRole = 20;
        this.mVideoPreprocessListenerAdapter = new TRTCVideoPreprocessListenerAdapter();
        this.mPerformanceMode = 0;
        this.mRoomType = 0;
        this.mCurrentOrientation = -1;
        this.mFloatingWindow = null;
        this.mOverrideFPSFromUser = false;
        this.mLatestParamsOfBigEncoder = new Bundle();
        this.mLatestParamsOfSmallEncoder = new Bundle();
        this.mLatestParamsOfSubEncoder = new Bundle();
        this.mFramework = 1;
        this.mOriginalFramework = 1;
        this.mCheckDuplicateEnterRoom = 0;
        this.mCustomCaptureGLSyncMode = 0;
        this.mQoSStrategy = 0;
        this.mLocalPreviewSurfaceViewCallback = new SurfaceHolder.Callback() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.1
            private boolean isSameHolderCurrentlyInUse(SurfaceHolder surfaceHolder) {
                TXCloudVideoView tXCloudVideoView;
                SurfaceView surfaceView;
                TRTCRoomInfo tRTCRoomInfo = TRTCCloudImpl.this.mRoomInfo;
                return (tRTCRoomInfo == null || (tXCloudVideoView = tRTCRoomInfo.localView) == null || (surfaceView = tXCloudVideoView.getSurfaceView()) == null || surfaceView.getHolder() != surfaceHolder) ? false : true;
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                if (isSameHolderCurrentlyInUse(surfaceHolder)) {
                    TRTCCloudImpl.this.apiLog("local view surfaceChanged, size: %dx%d, surface: %s, holder: %s", Integer.valueOf(i3), Integer.valueOf(i4), surfaceHolder.getSurface(), surfaceHolder);
                    TRTCCloudImpl.this.mCaptureAndEnc.a(i3, i4);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (isSameHolderCurrentlyInUse(surfaceHolder) && surfaceHolder.getSurface().isValid()) {
                    TRTCCloudImpl.this.apiLog("local view surfaceCreated, surface: %s, holder: %s", surfaceHolder.getSurface(), surfaceHolder);
                    TRTCCloudImpl.this.mCaptureAndEnc.a(surfaceHolder.getSurface());
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (isSameHolderCurrentlyInUse(surfaceHolder)) {
                    TRTCCloudImpl.this.apiLog("local view surfaceDestroyed, surface: %s, holder: %s", surfaceHolder.getSurface(), surfaceHolder);
                    TRTCCloudImpl.this.mCaptureAndEnc.a((Surface) null);
                }
            }
        };
        this.mKeepAVCaptureWhenEnterRoomFailed = false;
        this.mCallback = new com.tencent.liteav.basic.b.a() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.2
            @Override // com.tencent.liteav.basic.b.a
            public void onError(String str, int i2, String str2, String str3) {
                if (TRTCCloudImpl.this.mTRTCListener != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("EVT_USERID", str);
                    bundle.putInt("EVT_ID", i2);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (str2 != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        sb.append(str3 != null ? str3 : "");
                        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, sb.toString());
                    }
                    TRTCCloudImpl.this.onNotifyEvent(i2, bundle);
                }
                Monitor.a(3, i2, "onError => msg:" + str2 + " params:" + str3 + " code:" + i2 + " id:" + str, "", 0, 0);
                if (i2 == -1302 || i2 == -1317 || i2 == -1318 || i2 == -1319) {
                    TXCKeyPointReportProxy.b(30002, i2);
                    if (i2 == -1317) {
                        TXCEventRecorderProxy.a("18446744073709551615", 2002, 4L, -1L, "", 0);
                    }
                }
            }

            @Override // com.tencent.liteav.basic.b.a
            public void onEvent(String str, int i2, String str2, String str3) {
                onEventInternal(str, i2, str2, str3);
                Monitor.a(2, i2, "onEvent => msg:" + str2 + " params:" + str3 + " code:" + i2 + " id:" + str, "", 0, 0);
                if (i2 == 2027) {
                    TXCKeyPointReportProxy.b(30002, 0);
                }
            }

            public void onEventInternal(String str, int i2, String str2, String str3) {
                if (TRTCCloudImpl.this.mTRTCListener != null) {
                    switch (i2) {
                        case 10046:
                        case 10047:
                        case 10050:
                        case 10052:
                        case 10054:
                            i2 = 1204;
                            break;
                        case 10048:
                        case 10049:
                        case 10053:
                            i2 = 1205;
                            break;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("EVT_USERID", str);
                    bundle.putInt("EVT_ID", i2);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    if (str2 != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str2);
                        if (str3 == null) {
                            str3 = "";
                        }
                        sb.append(str3);
                        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, sb.toString());
                    }
                    TRTCCloudImpl.this.onNotifyEvent(i2, bundle);
                }
            }

            @Override // com.tencent.liteav.basic.b.a
            public void onWarning(String str, int i2, String str2, String str3) {
                onEventInternal(str, i2, str2, str3);
                Monitor.a(4, i2, "onWarning => msg:" + str2 + " params:" + str3 + " code:" + i2 + " id:" + str, "", 0, 0);
            }
        };
        this.mDeviceManagerListener = new TXDeviceManagerImpl.TXDeviceManagerListener() { // from class: com.tencent.liteav.trtc.impl.TRTCCloudImpl.3
            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onCameraParamChange(com.tencent.liteav.g gVar) {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                com.tencent.liteav.g gVar2 = tRTCCloudImpl.mConfig;
                gVar2.f19329c = gVar.f19329c;
                gVar2.f19330d = gVar.f19330d;
                if (tRTCCloudImpl.mPerformanceMode != 1) {
                    gVar2.X = gVar.X;
                }
                gVar2.W = gVar.W;
                tRTCCloudImpl.mCaptureAndEnc.a(gVar2);
            }

            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onSwitchAutoFocus(boolean z2) {
                TRTCCloudImpl.this.mConfig.N = !z2;
            }

            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onSwitchCamera(boolean z2) {
                TRTCCloudImpl tRTCCloudImpl = TRTCCloudImpl.this;
                tRTCCloudImpl.mConfig.f19342p = z2;
                tRTCCloudImpl.updateOrientation();
            }

            @Override // com.tencent.liteav.device.TXDeviceManagerImpl.TXDeviceManagerListener
            public void onSwitchSystemVolumeType(TXDeviceManager.TXSystemVolumeType tXSystemVolumeType) {
                int i2 = AnonymousClass208.$SwitchMap$com$tencent$liteav$device$TXDeviceManager$TXSystemVolumeType[tXSystemVolumeType.ordinal()];
                if (i2 == 1) {
                    TRTCCloudImpl.this.mConfig.A = 0;
                } else if (i2 == 2) {
                    TRTCCloudImpl.this.mConfig.A = 2;
                } else {
                    if (i2 != 3) {
                        return;
                    }
                    TRTCCloudImpl.this.mConfig.A = 1;
                }
            }
        };
        this.mSubClouds = new ArrayList<>();
        this.mCurrentPublishClouds = new HashMap<>();
        this.mVolumeLevelNotifyTask = null;
        this.mDebugType = 0;
        this.mStatusNotifyTask = null;
        this.mNetType = -1;
        this.mBackground = -1;
        init(context, fVar);
        this.mCurrentRole = 21;
        this.mTargetRole = 21;
        this.mH265Decision = new TRTCEncodeTypeDecision(this);
    }
}
