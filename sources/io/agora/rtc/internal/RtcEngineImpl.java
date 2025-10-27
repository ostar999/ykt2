package io.agora.rtc.internal;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import com.google.android.material.timepicker.TimeModel;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.liteav.audio.TXEAudioDef;
import io.agora.rtc.IAudioEffectManager;
import io.agora.rtc.IAudioFrameObserver;
import io.agora.rtc.ILogWriter;
import io.agora.rtc.IMetadataObserver;
import io.agora.rtc.IRtcChannelEventHandler;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.IRtcEngineEventHandlerEx;
import io.agora.rtc.RtcChannel;
import io.agora.rtc.RtcEngineEx;
import io.agora.rtc.audio.AgoraRhythmPlayerConfig;
import io.agora.rtc.audio.AudioRecordingConfiguration;
import io.agora.rtc.internal.RtcEngineMessage;
import io.agora.rtc.live.LiveInjectStreamConfig;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.mediaio.AgoraDefaultRender;
import io.agora.rtc.mediaio.AgoraDefaultSource;
import io.agora.rtc.mediaio.IVideoSink;
import io.agora.rtc.mediaio.IVideoSource;
import io.agora.rtc.models.UserInfo;
import io.agora.rtc.video.AgoraImage;
import io.agora.rtc.video.AgoraVideoFrame;
import io.agora.rtc.video.BeautyOptions;
import io.agora.rtc.video.CameraCapturerConfiguration;
import io.agora.rtc.video.ChannelMediaInfo;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.microedition.khronos.egl.EGLContext;
import net.lingala.zip4j.util.InternalZipConstants;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes8.dex */
public class RtcEngineImpl extends RtcEngineEx implements IAudioEffectManager {
    private static final int ORIENTATION_HYSTERESIS = 0;
    private static final String TAG = "RtcEngine";
    public static final int VIDEO_SOURCE_TYPE_CUSTOMIZED = 2;
    public static final int VIDEO_SOURCE_TYPE_DEFAULT = 1;
    private static final int VIDEO_SOURCE_TYPE_EXTERNAL_DEPRECATED = 3;
    public static final int VIDEO_SOURCE_TYPE_NULL = 0;
    private static boolean sLibLoaded = false;
    static float[] sMatrix = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private WeakReference<Context> mContext;
    private long mNativeHandle;
    private OrientationEventListener mOrientationListener;
    private int mVideoSourceType = 1;
    private boolean mLocalVideoEnabled = false;
    private boolean mUseLocalView = false;
    private int mExAudioSampleRate = 0;
    private int mExAudioChannels = 0;
    private int mSetedAudioSinkChannels = -1;
    private int mSetedAudioSinkSampleRate = -1;
    private long lastOrientationTs = 0;
    private int mTotalRotation = 1000;
    private final ConcurrentHashMap<IRtcEngineEventHandler, Integer> mRtcHandlers = new ConcurrentHashMap<>();
    private RtcChannelImpl mDefaultRtcChannel = null;
    private final LinkedList<RtcChannelImpl> mRtcChannels = new LinkedList<>();
    private IRtcEngineEventHandler.RtcStats mRtcStats = null;
    private WifiManager.WifiLock mWifiLock = null;
    private int mChannelProfile = 0;
    private int mClientRole = 2;
    private int mOrientation = -1;

    public RtcEngineImpl(Context context, String appId, IRtcEngineEventHandler handler) throws Exception {
        this.mNativeHandle = 0L;
        this.mContext = new WeakReference<>(context);
        addHandler(handler);
        this.mNativeHandle = nativeObjectInit(context, appId, "", "", "", "", "", "");
    }

    public static boolean checkIfInUIThread(String name) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            Logging.i(TAG, name + " in UI Thread");
            return true;
        }
        Logging.i(TAG, name + " not in UI Thread");
        return false;
    }

    private synchronized boolean checkStatus() {
        if (this.mNativeHandle == 0) {
            throw new IllegalStateException("RtcEngine does not initialize or it may be destroyed");
        }
        return true;
    }

    private void checkVoipPermissions(Context context, String perm) throws SecurityException {
        if (context == null || context.checkCallingOrSelfPermission(perm) != 0) {
            throw new SecurityException(perm + " is not granted");
        }
    }

    private native int deliverFrame(long nativeHandle, byte[] buf, int stride, int height, int cropLeft, int cropTop, int cropRight, int cropBottom, int rotation, long ts, int format);

    private int doCheckPermission(Context context) {
        try {
            checkVoipPermissions(context, "android.permission.INTERNET");
            return 0;
        } catch (SecurityException unused) {
            Logging.e(TAG, "can't join channel because no permission");
            return -9;
        }
    }

    private void doJoinChannelCheck(Context context) {
        if (joinChannelFirstTimeOrAllChannelLeft()) {
            doMonitorSystemEvent(context);
            doCheckPermission(context);
            if (this.mUseLocalView) {
                return;
            }
            try {
                if (this.mOrientationListener == null) {
                    this.mOrientationListener = new OrientationEventListener(context, 2) { // from class: io.agora.rtc.internal.RtcEngineImpl.1
                        @Override // android.view.OrientationEventListener
                        public void onOrientationChanged(int orientation) {
                            if (RtcEngineImpl.this.mUseLocalView || orientation == -1) {
                                return;
                            }
                            RtcEngineImpl.this.updateViewOrientation(orientation);
                        }
                    };
                }
                this.mOrientationListener.enable();
            } catch (Exception e2) {
                Logging.e(TAG, "Unable to create OrientationEventListener, ", e2);
            }
        }
    }

    private void doLeaveChannelCheck() {
        if (joinChannelFirstTimeOrAllChannelLeft()) {
            doStopMonitorSystemEvent();
        }
    }

    private void doMonitorSystemEvent(Context context) {
        WifiManager.WifiLock wifiLock;
        if (context != null && context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0 && Connectivity.getNetworkType(context) == 2 && context.checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE") == 0 && (wifiLock = this.mWifiLock) != null) {
            wifiLock.acquire();
            Logging.i(TAG, "hp connection mode detected");
        }
    }

    private void doStopMonitorSystemEvent() {
        OrientationEventListener orientationEventListener = this.mOrientationListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
            this.mOrientationListener = null;
        }
        WifiManager.WifiLock wifiLock = this.mWifiLock;
        if (wifiLock == null || !wifiLock.isHeld()) {
            return;
        }
        this.mWifiLock.release();
        Logging.i(TAG, "hp connection mode ended");
    }

    private static String formatString(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    public static String getLocalHost() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (!networkInterface.getName().startsWith("usb")) {
                    Iterator it = Collections.list(networkInterface.getInetAddresses()).iterator();
                    while (it.hasNext()) {
                        String strInetAddressToIpAddress = inetAddressToIpAddress((InetAddress) it.next());
                        if (strInetAddressToIpAddress != null && !strInetAddressToIpAddress.isEmpty()) {
                            return strInetAddressToIpAddress;
                        }
                    }
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    private RtcEngineMessage.PVideoNetOptions getOptionsByVideoProfile(int profile) {
        try {
            byte[] bArrNativeGetOptionsByVideoProfile = nativeGetOptionsByVideoProfile(this.mNativeHandle, profile);
            if (bArrNativeGetOptionsByVideoProfile == null) {
                return null;
            }
            RtcEngineMessage.PVideoNetOptions pVideoNetOptions = new RtcEngineMessage.PVideoNetOptions();
            pVideoNetOptions.unmarshall(bArrNativeGetOptionsByVideoProfile);
            return pVideoNetOptions;
        } catch (Exception unused) {
            return null;
        }
    }

    private static String inetAddressToIpAddress(InetAddress address) {
        if (address.isLoopbackAddress()) {
            return null;
        }
        if (address instanceof Inet4Address) {
            return ((Inet4Address) address).getHostAddress();
        }
        boolean z2 = address instanceof Inet6Address;
        return null;
    }

    public static synchronized boolean initializeNativeLibs() {
        if (!sLibLoaded) {
            loadNativeLibrary();
            sLibLoaded = nativeClassInit() == 0;
        }
        return sLibLoaded;
    }

    private boolean joinChannelFirstTimeOrAllChannelLeft() {
        synchronized (this) {
            boolean z2 = false;
            if (this.mDefaultRtcChannel != null) {
                return false;
            }
            Iterator<RtcChannelImpl> it = this.mRtcChannels.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = true;
                    break;
                }
                if (it.next().hasJoined()) {
                    break;
                }
            }
            return z2;
        }
    }

    public static synchronized void loadNativeLibrary() {
        System.loadLibrary("agora-rtc-sdk-jni");
    }

    public static synchronized boolean loadNativeLibsExternal() {
        if (!sLibLoaded) {
            sLibLoaded = nativeClassInit() == 0;
        }
        return sLibLoaded;
    }

    private native int nativeAddInjectStreamUrl(long nativeHandle, String url, byte[] config);

    private native int nativeAddLocalVideoRender(long nativeHandle, IVideoSink render, int type);

    private native int nativeAddPublishStreamUrl(long nativeHandle, String url, boolean transcodingEnabled);

    private native int nativeAddRemoteVideoRender(long nativeHandle, int uid, IVideoSink render, int type);

    private native int nativeAddVideoCapturer(long nativeHandle, IVideoSource capturer, int type);

    private native int nativeAddVideoWatermark(long nativeHandle, String url, int x2, int y2, int width, int height);

    private static native int nativeClassInit();

    private native int nativeClearVideoWatermarks(long nativeHandle);

    private native int nativeComplain(long nativeHandle, String callId, String description);

    private native int nativeCreateDataStream(long nativeHandle, boolean reliable, boolean ordered);

    private native long nativeCreateRtcChannel(long nativeHandle, String channel);

    private native int nativeDestroy(long nativeHandle);

    private native int nativeDisableVideo(long nativeHandle);

    private native int nativeEnableLocalAudio(long nativeHandle, boolean enabled);

    private native int nativeEnableVideo(long nativeHandle);

    private native String nativeGetCallId(long nativeHandle);

    public static native String nativeGetChatEngineVersion();

    private native int nativeGetConncetionState(long nativeHandle);

    private native long nativeGetDefaultRtcChannel(long nativeHandle);

    public static native String nativeGetErrorDescription(int err);

    private native long nativeGetHandle(long nativeHandle);

    private native int nativeGetIntParameter(long nativeHandle, String parameter, String args);

    private static native byte[] nativeGetOptionsByVideoProfile(long nativeHandle, int profile);

    private native String nativeGetParameter(long nativeHandle, String parameter, String args);

    private native String nativeGetParameters(long nativeHandle, String parameters);

    private native String nativeGetProfile(long nativeHandle);

    public static native String nativeGetSdkVersion();

    private native int nativeGetUserInfoByUid(long nativeHandle, int userAccount, Object userInfo);

    private native int nativeGetUserInfoByUserAccount(long nativeHandle, String userAccount, Object userInfo);

    private native boolean nativeIsSpeakerphoneEnabled(long nativeHandle);

    private native int nativeJoinChannel(long nativeHandle, byte[] appContext, String token, String channelName, String info, int uid);

    private native int nativeJoinChannelWithUserAccount(long nativeHandle, String token, String channelName, String userAccount);

    private native int nativeLeaveChannel(long nativeHandle);

    public static native int nativeLog(int level, String msg);

    private native String nativeMakeQualityReportUrl(long nativeHandle, String channel, int listenerUid, int speakerUid, int format);

    private native int nativeMuteAllRemoteVideoStreams(long nativeHandle, boolean muted);

    private native int nativeMuteLocalVideoStream(long nativeHandle, boolean muted);

    private native long nativeObjectInit(Object context, String appId, String deviceId, String deviceInfo, String systemInfo, String appStorageDir, String cacheDir, String pluginDir);

    private native int nativePullAudioFrame(long nativeHandle, byte[] data, int length, int channels);

    private native int nativePushExternalAudioFrameRawData(long nativeHandle, byte[] data, long timestamp, int frequency, int channels);

    private native int nativeRate(long nativeHandle, String callId, int rating, String description);

    private native int nativeRegisterAudioFrameObserver(long nativeHandle, Object observer);

    private native int nativeRegisterLocalUserAccount(long nativeHandle, String appId, String userAccount);

    private native int nativeRegisterMediaMetadataObserver(long nativeHandle, Object observer, int type);

    private native int nativeReleaseLogWriter(long nativeHandle);

    private native int nativeRemoveInjectStreamUrl(long nativeHandle, String url);

    private native int nativeRemovePublishStreamUrl(long nativeHandle, String url);

    private native int nativeRemoveVideoReceiveTrack(long nativeHandle, int uid);

    private native int nativeRenewChannelKey(long nativeHandle, String channelKey);

    private native int nativeRenewToken(long nativeHandle, String token);

    private native int nativeRtcChannelRelease(long nativeHandle);

    private native int nativeSendCustomReportMessage(long nativeHandle, String id, String category, String event, String label, int value);

    private native int nativeSendStreamMessage(long nativeHandle, int streamId, byte[] data);

    private native int nativeSetApiCallMode(long nativeHandle, int syncCallTimeout);

    private native int nativeSetAppType(long nativeHandle, int appType);

    private native int nativeSetAudioProfile(long nativeHandle, int profile, int scenario);

    private native int nativeSetBeautyEffectOptions(long nativeHandle, boolean enabled, int contrastLevel, float lighteningLevel, float smoothnessLevel, float rednessLevel);

    private native int nativeSetChannelProfile(long nativeHandle, int profile);

    private native int nativeSetDefaultAudioRoutetoSpeakerphone(long nativeHandle, boolean defaultToSpeaker);

    private native int nativeSetEGL10Context(long nativeHandle, EGLContext sharedContext);

    private native int nativeSetEGL10TextureId(long nativeHandle, int id, EGLContext sharedContext, int format, int width, int height, long ts, float[] matrix);

    private native int nativeSetEGL14Context(long nativeHandle, android.opengl.EGLContext sharedContext);

    private native int nativeSetEGL14TextureId(long nativeHandle, int id, android.opengl.EGLContext sharedContext, int format, int width, int height, long ts, float[] matrix);

    private native int nativeSetEnableSpeakerphone(long nativeHandle, boolean speakerOn);

    private native int nativeSetEncryptionSecret(long nativeHandle, String secret);

    private native int nativeSetLiveTranscoding(long nativeHandle, byte[] transcoding);

    private native int nativeSetLogWriter(long nativeHandle, Object logWriter);

    private native int nativeSetParameters(long nativeHandle, String parameters);

    private native int nativeSetProfile(long nativeHandle, String profile, boolean merge);

    private native int nativeSetRemoteUserPriority(long nativeHandle, int uid, int userPriority);

    private native int nativeSetVideoEncoderConfiguration(long nativeHandle, int width, int height, int frameRate, int minFrameRate, int bitrate, int minBitrate, int orientationMode, int degradationPrefer, int mirrorMode);

    private native int nativeSetVideoProfileEx(long nativeHandle, int width, int height, int frameRate, int bitrate);

    private native int nativeSetupVideoLocal(long nativeHandle, SurfaceView view, int renderMode);

    private native int nativeSetupVideoRemote(long nativeHandle, SurfaceView view, int renderMode, String channel, int uid);

    private native int nativeStartAudioRecording(long nativeHandle, String filePath, int quality, int position);

    private native int nativeStartChannelMediaRelay(long nativeHandle, byte[] channelMediaRelayInfos);

    private native int nativeStartEchoTest(long nativeHandle, byte[] appContext);

    private native int nativeStartEchoTestWithInterval(long nativeHandle, byte[] appContext, int intervalInSeconds);

    private native int nativeStartLastmileProbeTest(long nativeHandle, byte[] appContext, boolean probeUplink, boolean probeDownlink, int expectedUplinkBitrate, int expectedDownlinkBitrate);

    private native int nativeStartPreview(long nativeHandle);

    private native int nativeStopAudioRecording(long nativeHandle);

    private native int nativeStopChannelMediaRelay(long nativeHandle);

    private native int nativeStopEchoTest(long nativeHandle);

    private native int nativeStopLastmileProbeTest(long nativeHandle);

    private native int nativeSwitchCamera(long nativeHandle);

    private native int nativeSwitchChannel(long nativeHandle, String token, String channelName);

    private native int nativeUpdateChannelMediaRelay(long nativeHandle, byte[] channelMediaRelayInfos);

    private void onApiCallExecuted(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PApiCallExecuted pApiCallExecuted = new RtcEngineMessage.PApiCallExecuted();
        pApiCallExecuted.unmarshall(evt);
        handler.onApiCallExecuted(pApiCallExecuted.error, pApiCallExecuted.api, pApiCallExecuted.result);
    }

    private void onCameraExposureAreaChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PCameraExposureAreaChanged pCameraExposureAreaChanged = new RtcEngineMessage.PCameraExposureAreaChanged();
        pCameraExposureAreaChanged.unmarshall(evt);
        int i2 = pCameraExposureAreaChanged.f27138x;
        int i3 = pCameraExposureAreaChanged.f27139y;
        handler.onCameraExposureAreaChanged(new Rect(i2, i3, pCameraExposureAreaChanged.width + i2, pCameraExposureAreaChanged.height + i3));
    }

    private void onCameraFocusAreaChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PCameraFocusAreaChanged pCameraFocusAreaChanged = new RtcEngineMessage.PCameraFocusAreaChanged();
        pCameraFocusAreaChanged.unmarshall(evt);
        int i2 = pCameraFocusAreaChanged.f27140x;
        int i3 = pCameraFocusAreaChanged.f27141y;
        handler.onCameraFocusAreaChanged(new Rect(i2, i3, pCameraFocusAreaChanged.width + i2, pCameraFocusAreaChanged.height + i3));
    }

    private void onChannelMediaRelayEvent(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PCrossChannelEvent pCrossChannelEvent = new RtcEngineMessage.PCrossChannelEvent();
        pCrossChannelEvent.unmarshall(data);
        handler.onChannelMediaRelayEvent(pCrossChannelEvent.code);
    }

    private void onChannelMediaRelayStateChanged(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PCrossChannelState pCrossChannelState = new RtcEngineMessage.PCrossChannelState();
        pCrossChannelState.unmarshall(data);
        handler.onChannelMediaRelayStateChanged(pCrossChannelState.state, pCrossChannelState.code);
    }

    private void onFirstLocalAudioFrame(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PFirstLocalAudioFrame pFirstLocalAudioFrame = new RtcEngineMessage.PFirstLocalAudioFrame();
        pFirstLocalAudioFrame.unmarshall(data);
        handler.onFirstLocalAudioFrame(pFirstLocalAudioFrame.elapsed);
    }

    private void onFirstLocalVideoFrame(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PFirstLocalVideoFrame pFirstLocalVideoFrame = new RtcEngineMessage.PFirstLocalVideoFrame();
        pFirstLocalVideoFrame.unmarshall(evt);
        handler.onFirstLocalVideoFrame(pFirstLocalVideoFrame.width, pFirstLocalVideoFrame.height, pFirstLocalVideoFrame.elapsed);
    }

    private void onFirstRemoteAudioFrame(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PFirstRemoteAudioFrame pFirstRemoteAudioFrame = new RtcEngineMessage.PFirstRemoteAudioFrame();
        pFirstRemoteAudioFrame.unmarshall(data);
        handler.onFirstRemoteAudioFrame(pFirstRemoteAudioFrame.uid, pFirstRemoteAudioFrame.elapsed);
    }

    private void onFirstRemoteVideoDecoded(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PFirstRemoteVideoDecoded pFirstRemoteVideoDecoded = new RtcEngineMessage.PFirstRemoteVideoDecoded();
        pFirstRemoteVideoDecoded.unmarshall(evt);
        handler.onFirstRemoteVideoDecoded(pFirstRemoteVideoDecoded.uid, pFirstRemoteVideoDecoded.width, pFirstRemoteVideoDecoded.height, pFirstRemoteVideoDecoded.elapsed);
    }

    private void onFirstRemoteVideoFrame(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PFirstRemoteVideoFrame pFirstRemoteVideoFrame = new RtcEngineMessage.PFirstRemoteVideoFrame();
        pFirstRemoteVideoFrame.unmarshall(data);
        handler.onFirstRemoteVideoFrame(pFirstRemoteVideoFrame.uid, pFirstRemoteVideoFrame.width, pFirstRemoteVideoFrame.height, pFirstRemoteVideoFrame.elapsed);
    }

    private void onLocalAudioStat(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PLocalAudioStat pLocalAudioStat = new RtcEngineMessage.PLocalAudioStat();
        pLocalAudioStat.unmarshall(data);
        handler.onLocalAudioStats(pLocalAudioStat.stats);
    }

    private void onLocalVideoStat(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PLocalVideoStat pLocalVideoStat = new RtcEngineMessage.PLocalVideoStat();
        pLocalVideoStat.unmarshall(data);
        handler.onLocalVideoStats(pLocalVideoStat.stats);
    }

    private void onLogEvent(int level, String message) {
    }

    private void onPublishAudioStateChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PPublishAudioState pPublishAudioState = new RtcEngineMessage.PPublishAudioState();
        pPublishAudioState.unmarshall(evt);
        handler.onPublishAudioStateChanged(pPublishAudioState.channel, pPublishAudioState.oldstate, pPublishAudioState.newstate, pPublishAudioState.elapsed);
    }

    private void onPublishVideoStateChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PPublishVideoState pPublishVideoState = new RtcEngineMessage.PPublishVideoState();
        pPublishVideoState.unmarshall(evt);
        handler.onPublishVideoStateChanged(pPublishVideoState.channel, pPublishVideoState.oldstate, pPublishVideoState.newstate, pPublishVideoState.elapsed);
    }

    private void onRemoteAudioStat(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PRemoteAudioStat pRemoteAudioStat = new RtcEngineMessage.PRemoteAudioStat();
        pRemoteAudioStat.unmarshall(data);
        IRtcEngineEventHandler.RemoteAudioStats remoteAudioStats = pRemoteAudioStat.stats;
        if (remoteAudioStats.uid == 0) {
            return;
        }
        handler.onRemoteAudioStats(remoteAudioStats);
    }

    private void onRemoteAudioStateChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PRemoteAudioState pRemoteAudioState = new RtcEngineMessage.PRemoteAudioState();
        pRemoteAudioState.unmarshall(evt);
        handler.onRemoteAudioStateChanged(pRemoteAudioState.uid, pRemoteAudioState.state, pRemoteAudioState.reason, pRemoteAudioState.elapsed);
    }

    private void onRemoteVideoStat(byte[] data, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PRemoteVideoStat pRemoteVideoStat = new RtcEngineMessage.PRemoteVideoStat();
        pRemoteVideoStat.unmarshall(data);
        IRtcEngineEventHandler.RemoteVideoStats remoteVideoStats = pRemoteVideoStat.stats;
        if (remoteVideoStats.uid == 0) {
            return;
        }
        handler.onRemoteVideoStats(remoteVideoStats);
    }

    private void onRemoteVideoStateChangedExt(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PRemoteVideoStateExt pRemoteVideoStateExt = new RtcEngineMessage.PRemoteVideoStateExt();
        pRemoteVideoStateExt.unmarshall(evt);
        handler.onRemoteVideoStateChanged(pRemoteVideoStateExt.uid, pRemoteVideoStateExt.state, pRemoteVideoStateExt.reason, pRemoteVideoStateExt.elapsed);
    }

    private void onRtcChannelChannelMediaRelayEvent(byte[] data, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PCrossChannelEvent pCrossChannelEvent = new RtcEngineMessage.PCrossChannelEvent();
        pCrossChannelEvent.unmarshall(data);
        handler.onChannelMediaRelayEvent(rtcChannel, pCrossChannelEvent.code);
    }

    private void onRtcChannelChannelMediaRelayStateChanged(byte[] data, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PCrossChannelState pCrossChannelState = new RtcEngineMessage.PCrossChannelState();
        pCrossChannelState.unmarshall(data);
        handler.onChannelMediaRelayStateChanged(rtcChannel, pCrossChannelState.state, pCrossChannelState.code);
    }

    private void onRtcChannelFirstRemoteAudioFrame(byte[] data, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PFirstRemoteAudioFrame pFirstRemoteAudioFrame = new RtcEngineMessage.PFirstRemoteAudioFrame();
        pFirstRemoteAudioFrame.unmarshall(data);
        handler.onFirstRemoteAudioFrame(rtcChannel, pFirstRemoteAudioFrame.uid, pFirstRemoteAudioFrame.elapsed);
    }

    private void onRtcChannelFirstRemoteVideoFrame(byte[] data, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PFirstRemoteVideoFrame pFirstRemoteVideoFrame = new RtcEngineMessage.PFirstRemoteVideoFrame();
        pFirstRemoteVideoFrame.unmarshall(data);
        handler.onFirstRemoteVideoFrame(rtcChannel, pFirstRemoteVideoFrame.uid, pFirstRemoteVideoFrame.width, pFirstRemoteVideoFrame.height, pFirstRemoteVideoFrame.elapsed);
    }

    private void onRtcChannelPublishAudioStateChanged(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PPublishAudioState pPublishAudioState = new RtcEngineMessage.PPublishAudioState();
        pPublishAudioState.unmarshall(evt);
        handler.onPublishAudioStateChanged(rtcChannel, pPublishAudioState.oldstate, pPublishAudioState.newstate, pPublishAudioState.elapsed);
    }

    private void onRtcChannelPublishVideoStateChanged(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PPublishVideoState pPublishVideoState = new RtcEngineMessage.PPublishVideoState();
        pPublishVideoState.unmarshall(evt);
        handler.onPublishVideoStateChanged(rtcChannel, pPublishVideoState.oldstate, pPublishVideoState.newstate, pPublishVideoState.elapsed);
    }

    private void onRtcChannelRemoteAudioStat(byte[] data, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PRemoteAudioStat pRemoteAudioStat = new RtcEngineMessage.PRemoteAudioStat();
        pRemoteAudioStat.unmarshall(data);
        IRtcEngineEventHandler.RemoteAudioStats remoteAudioStats = pRemoteAudioStat.stats;
        if (remoteAudioStats.uid == 0) {
            return;
        }
        handler.onRemoteAudioStats(rtcChannel, remoteAudioStats);
    }

    private void onRtcChannelRemoteAudioStateChanged(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PRemoteAudioState pRemoteAudioState = new RtcEngineMessage.PRemoteAudioState();
        pRemoteAudioState.unmarshall(evt);
        handler.onRemoteAudioStateChanged(rtcChannel, pRemoteAudioState.uid, pRemoteAudioState.state, pRemoteAudioState.reason, pRemoteAudioState.elapsed);
    }

    private void onRtcChannelRemoteVideoStat(byte[] data, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PRemoteVideoStat pRemoteVideoStat = new RtcEngineMessage.PRemoteVideoStat();
        pRemoteVideoStat.unmarshall(data);
        IRtcEngineEventHandler.RemoteVideoStats remoteVideoStats = pRemoteVideoStat.stats;
        if (remoteVideoStats.uid == 0) {
            return;
        }
        handler.onRemoteVideoStats(rtcChannel, remoteVideoStats);
    }

    private void onRtcChannelRemoteVideoStateChangedExt(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PRemoteVideoStateExt pRemoteVideoStateExt = new RtcEngineMessage.PRemoteVideoStateExt();
        pRemoteVideoStateExt.unmarshall(evt);
        handler.onRemoteVideoStateChanged(rtcChannel, pRemoteVideoStateExt.uid, pRemoteVideoStateExt.state, pRemoteVideoStateExt.reason, pRemoteVideoStateExt.elapsed);
    }

    private void onRtcChannelStreamMessage(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PStreamMessage pStreamMessage = new RtcEngineMessage.PStreamMessage();
        pStreamMessage.unmarshall(evt);
        handler.onStreamMessage(rtcChannel, pStreamMessage.uid, pStreamMessage.streamId, pStreamMessage.payload);
    }

    private void onRtcChannelStreamMessageError(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PStreamMessageError pStreamMessageError = new RtcEngineMessage.PStreamMessageError();
        pStreamMessageError.unmarshall(evt);
        handler.onStreamMessageError(rtcChannel, pStreamMessageError.uid, pStreamMessageError.streamId, pStreamMessageError.error, pStreamMessageError.missed, pStreamMessageError.cached);
    }

    private void onRtcChannelSubscribeAudioStateChanged(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PSubscribeAudioState pSubscribeAudioState = new RtcEngineMessage.PSubscribeAudioState();
        pSubscribeAudioState.unmarshall(evt);
        handler.onSubscribeAudioStateChanged(rtcChannel, pSubscribeAudioState.uid, pSubscribeAudioState.oldstate, pSubscribeAudioState.newstate, pSubscribeAudioState.elapsed);
    }

    private void onRtcChannelSubscribeVideoStateChanged(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PSubscribeVideoState pSubscribeVideoState = new RtcEngineMessage.PSubscribeVideoState();
        pSubscribeVideoState.unmarshall(evt);
        handler.onSubscribeVideoStateChanged(rtcChannel, pSubscribeVideoState.uid, pSubscribeVideoState.oldstate, pSubscribeVideoState.newstate, pSubscribeVideoState.elapsed);
    }

    private void onRtcChannelVideoSizeChanged(byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        RtcEngineMessage.PVideoSizeChanged pVideoSizeChanged = new RtcEngineMessage.PVideoSizeChanged();
        pVideoSizeChanged.unmarshall(evt);
        handler.onVideoSizeChanged(rtcChannel, pVideoSizeChanged.uid, pVideoSizeChanged.width, pVideoSizeChanged.height, pVideoSizeChanged.rotation);
    }

    private void onSpeakersReport(byte[] evt, IRtcEngineEventHandler handler) {
        if (evt == null) {
            return;
        }
        RtcEngineMessage.PMediaResSpeakersReport pMediaResSpeakersReport = new RtcEngineMessage.PMediaResSpeakersReport();
        pMediaResSpeakersReport.unmarshall(evt);
        RtcEngineMessage.PMediaResSpeakersReport.Speaker[] speakerArr = pMediaResSpeakersReport.speakers;
        if (speakerArr == null || speakerArr.length < 0) {
            handler.onAudioVolumeIndication(new IRtcEngineEventHandler.AudioVolumeInfo[0], pMediaResSpeakersReport.mixVolume);
            return;
        }
        IRtcEngineEventHandler.AudioVolumeInfo[] audioVolumeInfoArr = new IRtcEngineEventHandler.AudioVolumeInfo[speakerArr.length];
        for (int i2 = 0; i2 < pMediaResSpeakersReport.speakers.length; i2++) {
            IRtcEngineEventHandler.AudioVolumeInfo audioVolumeInfo = new IRtcEngineEventHandler.AudioVolumeInfo();
            audioVolumeInfoArr[i2] = audioVolumeInfo;
            RtcEngineMessage.PMediaResSpeakersReport.Speaker speaker = pMediaResSpeakersReport.speakers[i2];
            audioVolumeInfo.uid = speaker.uid;
            audioVolumeInfo.volume = speaker.volume;
            audioVolumeInfo.vad = speaker.vad;
            audioVolumeInfo.channelId = speaker.channelId;
        }
        handler.onAudioVolumeIndication(audioVolumeInfoArr, pMediaResSpeakersReport.mixVolume);
    }

    private void onStreamMessage(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PStreamMessage pStreamMessage = new RtcEngineMessage.PStreamMessage();
        pStreamMessage.unmarshall(evt);
        handler.onStreamMessage(pStreamMessage.uid, pStreamMessage.streamId, pStreamMessage.payload);
    }

    private void onStreamMessageError(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PStreamMessageError pStreamMessageError = new RtcEngineMessage.PStreamMessageError();
        pStreamMessageError.unmarshall(evt);
        handler.onStreamMessageError(pStreamMessageError.uid, pStreamMessageError.streamId, pStreamMessageError.error, pStreamMessageError.missed, pStreamMessageError.cached);
    }

    private void onSubscribeAudioStateChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PSubscribeAudioState pSubscribeAudioState = new RtcEngineMessage.PSubscribeAudioState();
        pSubscribeAudioState.unmarshall(evt);
        handler.onSubscribeAudioStateChanged(pSubscribeAudioState.channel, pSubscribeAudioState.uid, pSubscribeAudioState.oldstate, pSubscribeAudioState.newstate, pSubscribeAudioState.elapsed);
    }

    private void onSubscribeVideoStateChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PSubscribeVideoState pSubscribeVideoState = new RtcEngineMessage.PSubscribeVideoState();
        pSubscribeVideoState.unmarshall(evt);
        handler.onSubscribeVideoStateChanged(pSubscribeVideoState.channel, pSubscribeVideoState.uid, pSubscribeVideoState.oldstate, pSubscribeVideoState.newstate, pSubscribeVideoState.elapsed);
    }

    private void onVideoSizeChanged(byte[] evt, IRtcEngineEventHandler handler) {
        RtcEngineMessage.PVideoSizeChanged pVideoSizeChanged = new RtcEngineMessage.PVideoSizeChanged();
        pVideoSizeChanged.unmarshall(evt);
        handler.onVideoSizeChanged(pVideoSizeChanged.uid, pVideoSizeChanged.width, pVideoSizeChanged.height, pVideoSizeChanged.rotation);
    }

    private static int roundOrientation(int orientation, int orientationHistory) {
        boolean z2 = true;
        if (orientationHistory != -1) {
            int iAbs = Math.abs(orientation - orientationHistory);
            if (Math.min(iAbs, 360 - iAbs) < 45) {
                z2 = false;
            }
        }
        return z2 ? (((orientation + 45) / 90) * 90) % 360 : orientationHistory;
    }

    private void sendLogEvent(byte[] evt) {
        try {
            onLogEvent(0, new String(evt, "ISO-8859-1"));
        } catch (UnsupportedEncodingException unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setDeviceOrientation(int r11) {
        /*
            r10 = this;
            long r0 = java.lang.System.currentTimeMillis()
            long r2 = r10.lastOrientationTs
            long r2 = r0 - r2
            r4 = 100
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto Lf
            return
        Lf:
            double r2 = (double) r11
            r4 = 4636033603912859648(0x4056800000000000, double:90.0)
            double r2 = r2 / r4
            long r2 = java.lang.Math.round(r2)
            r4 = 90
            long r2 = r2 * r4
            int r2 = (int) r2
            int r2 = r2 % 360
            int r3 = r2 - r11
            int r4 = java.lang.Math.abs(r3)
            r5 = 2
            r6 = 40
            r7 = 20
            r8 = 0
            r9 = 1
            if (r4 >= r7) goto L31
            r3 = r9
            goto L3a
        L31:
            int r3 = java.lang.Math.abs(r3)
            if (r3 >= r6) goto L39
            r3 = r5
            goto L3a
        L39:
            r3 = r8
        L3a:
            if (r2 != 0) goto L49
            r4 = 180(0xb4, float:2.52E-43)
            if (r11 <= r4) goto L49
            int r11 = 360 - r11
            if (r11 >= r7) goto L46
            r5 = r9
            goto L4a
        L46:
            if (r11 >= r6) goto L49
            goto L4a
        L49:
            r5 = r3
        L4a:
            if (r5 <= 0) goto L68
            android.hardware.Camera$CameraInfo r11 = new android.hardware.Camera$CameraInfo     // Catch: java.lang.Exception -> L60
            r11.<init>()     // Catch: java.lang.Exception -> L60
            if (r5 != r9) goto L54
            goto L56
        L54:
            int r2 = r2 + 5
        L56:
            int r11 = r10.mTotalRotation     // Catch: java.lang.Exception -> L60
            if (r11 == 0) goto L5d
            r10.setVideoRotateCapturedFrames(r8, r2)     // Catch: java.lang.Exception -> L60
        L5d:
            r10.mTotalRotation = r8     // Catch: java.lang.Exception -> L60
            goto L68
        L60:
            r11 = move-exception
            java.lang.String r2 = "RtcEngine"
            java.lang.String r3 = "Unable to get camera info, "
            io.agora.rtc.internal.Logging.e(r2, r3, r11)
        L68:
            r10.lastOrientationTs = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.internal.RtcEngineImpl.setDeviceOrientation(int):void");
    }

    private native int setExtVideoSource(long nativeHandle, int enable, int pushMode);

    private int setParameter(String key, boolean value) {
        return setParameters(formatString("{\"%s\":%b}", key, Boolean.valueOf(value)));
    }

    private int setParameterObject(String key, String value) {
        return setParameters(formatString("{\"%s\":%s}", key, value));
    }

    private int setVideoRotateCapturedFrames(int degree, int rotation) {
        return setParameterObject("che.video.local.rotate_video", formatString("{\"degree\":%d,\"rotation\":%d}", Integer.valueOf(degree), Integer.valueOf(rotation)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViewOrientation(int orientation) {
        int iRoundOrientation = roundOrientation(orientation, this.mOrientation);
        if (this.mOrientation != iRoundOrientation) {
            this.mOrientation = iRoundOrientation;
            if ((iRoundOrientation / 90) % 2 != 0) {
                iRoundOrientation = (iRoundOrientation + 180) % 360;
            }
            setParameterObject("che.video.view_orientation", formatString("{\"uid\":%d,\"orientation\":%d}", 0, Integer.valueOf(iRoundOrientation / 90)));
        }
    }

    @Override // io.agora.rtc.RtcEngine
    public void addHandler(IRtcEngineEventHandler handler) {
        this.mRtcHandlers.put(handler, 0);
    }

    @Override // io.agora.rtc.RtcEngine
    public int addInjectStreamUrl(String url, LiveInjectStreamConfig config) {
        if (url == null || config == null) {
            return -2;
        }
        return nativeAddInjectStreamUrl(this.mNativeHandle, url, new RtcEngineMessage.PInjectStreamConfig().marshall(config));
    }

    @Override // io.agora.rtc.RtcEngine
    public int addPublishStreamUrl(String url, boolean transcodingEnabled) {
        return nativeAddPublishStreamUrl(this.mNativeHandle, url, transcodingEnabled);
    }

    @Override // io.agora.rtc.RtcEngine
    public int addVideoWatermark(AgoraImage watermark) {
        if (watermark == null || TextUtils.isEmpty(watermark.url)) {
            return -2;
        }
        return nativeAddVideoWatermark(this.mNativeHandle, watermark.url, watermark.f27145x, watermark.f27146y, watermark.width, watermark.height);
    }

    @Override // io.agora.rtc.RtcEngine
    public int adjustAudioMixingPlayoutVolume(int volume) {
        return setParameter("che.audio.set_file_as_playout_volume", volume);
    }

    @Override // io.agora.rtc.RtcEngine
    public int adjustAudioMixingPublishVolume(int volume) {
        return setParameter("che.audio.set_file_as_playout_publish_volume", volume);
    }

    @Override // io.agora.rtc.RtcEngine
    public int adjustAudioMixingVolume(int volume) {
        int iAdjustAudioMixingPlayoutVolume = adjustAudioMixingPlayoutVolume(volume);
        if (iAdjustAudioMixingPlayoutVolume == 0) {
            adjustAudioMixingPublishVolume(volume);
        }
        return iAdjustAudioMixingPlayoutVolume;
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int adjustEffectPlayoutVolume(int soundId, int volume) {
        return setParameterObject("che.audio.set_effect_file_playout_volume", formatString("{\"soundId\":%d,\"effectPlayoutVolume\":%d}", Integer.valueOf(soundId), Integer.valueOf(volume)));
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int adjustEffectPublishVolume(int soundId, int volume) {
        return setParameterObject("che.audio.set_effect_file_publish_volume", formatString("{\"soundId\":%d,\"effectPublishVolume\":%d}", Integer.valueOf(soundId), Integer.valueOf(volume)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int adjustPlaybackSignalVolume(int volume) {
        if (volume < 0) {
            volume = 0;
        } else if (volume > 400) {
            volume = 400;
        }
        return setParameter("che.audio.playout.signal.volume", volume);
    }

    @Override // io.agora.rtc.RtcEngine
    public int adjustRecordingSignalVolume(int volume) {
        if (volume < 0) {
            volume = 0;
        } else if (volume > 400) {
            volume = 400;
        }
        return setParameter("che.audio.record.signal.volume", volume);
    }

    @Override // io.agora.rtc.RtcEngine
    public int clearVideoWatermarks() {
        return nativeClearVideoWatermarks(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public int complain(String callId, String description) {
        return nativeComplain(this.mNativeHandle, callId, description);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int configRhythmPlayer(AgoraRhythmPlayerConfig agoraRhythmPlayerConfig) {
        return setParameterObject("che.audio.config_rhythm", formatString("{\"beatsPerMeasure\":%d,\"beatsPerMinute\":%d,\"publish\":%d}", Integer.valueOf(agoraRhythmPlayerConfig.beatsPerMeasure), Integer.valueOf(agoraRhythmPlayerConfig.beatsPerMinute), Integer.valueOf(agoraRhythmPlayerConfig.publish ? 1 : 0)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int createDataStream(boolean reliable, boolean ordered) {
        return nativeCreateDataStream(this.mNativeHandle, reliable, ordered);
    }

    @Override // io.agora.rtc.RtcEngine
    public RtcChannel createRtcChannel(String channelId) {
        if (channelId == null || channelId.length() <= 0) {
            return null;
        }
        synchronized (this) {
            RtcChannelImpl rtcChannelImpl = this.mDefaultRtcChannel;
            if (rtcChannelImpl != null && rtcChannelImpl.channelId().equals(channelId) && this.mDefaultRtcChannel.isInitialized()) {
                return this.mDefaultRtcChannel;
            }
            Iterator<RtcChannelImpl> it = this.mRtcChannels.iterator();
            while (it.hasNext()) {
                RtcChannelImpl next = it.next();
                if (next.channelId() != null && next.channelId().equals(channelId) && next.isInitialized()) {
                    return next;
                }
            }
            long jNativeCreateRtcChannel = nativeCreateRtcChannel(this.mNativeHandle, channelId);
            if (jNativeCreateRtcChannel == 0) {
                return null;
            }
            RtcChannelImpl rtcChannelImpl2 = new RtcChannelImpl();
            rtcChannelImpl2.initialize(this, jNativeCreateRtcChannel);
            this.mRtcChannels.add(rtcChannelImpl2);
            return rtcChannelImpl2;
        }
    }

    public int destroyRtcChannel(String channelId) {
        if (channelId == null || channelId.length() <= 0) {
            return TXEAudioDef.TXE_AUDIO_PLAY_ERR_AUDIO_TYPE_NOT_SUPPORT;
        }
        synchronized (this) {
            RtcChannelImpl rtcChannelImpl = this.mDefaultRtcChannel;
            if (rtcChannelImpl != null && rtcChannelImpl.channelId().equals(channelId)) {
                return -5;
            }
            Iterator<RtcChannelImpl> it = this.mRtcChannels.iterator();
            while (it.hasNext()) {
                RtcChannelImpl next = it.next();
                if (next.channelId() != null && next.channelId().equals(channelId)) {
                    int iNativeRtcChannelRelease = nativeRtcChannelRelease(next.getNativeHandle());
                    this.mRtcChannels.remove(next);
                    return iNativeRtcChannelRelease;
                }
            }
            return 0;
        }
    }

    @Override // io.agora.rtc.RtcEngine
    public int disableAudio() {
        Boolean bool = Boolean.FALSE;
        return setParameters(formatString("{\"rtc.audio.enabled\":%b, \"che.audio.enable.recording.device\":%b}", bool, bool));
    }

    @Override // io.agora.rtc.RtcEngine
    public int disableLastmileTest() {
        return setParameter("rtc.lastmile_test", false);
    }

    @Override // io.agora.rtc.RtcEngine
    public int disableVideo() {
        this.mLocalVideoEnabled = false;
        return nativeDisableVideo(this.mNativeHandle);
    }

    public void doDestroy() {
        long j2 = this.mNativeHandle;
        setExternalVideoSource(false, false, true);
        this.mNativeHandle = 0L;
        doStopMonitorSystemEvent();
        nativeDestroy(j2);
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableAudio() {
        Boolean bool = Boolean.TRUE;
        return setParameters(formatString("{\"rtc.audio.enabled\":%b, \"che.audio.enable.recording.device\":%b}", bool, bool));
    }

    @Override // io.agora.rtc.RtcEngine
    @Deprecated
    public int enableAudioQualityIndication(boolean enabled) {
        return setParameter("rtc.audio_quality_indication", enabled);
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableAudioVolumeIndication(int interval, int smooth, boolean report_vad) {
        if (interval < 0) {
            interval = 0;
        }
        return report_vad ? setParameterObject("che.audio.volume_indication", formatString("{\"interval\":%d,\"smooth\":%d,\"vad\":%d}", Integer.valueOf(interval), Integer.valueOf(smooth), 1)) : setParameterObject("che.audio.volume_indication", formatString("{\"interval\":%d,\"smooth\":%d,\"vad\":%d}", Integer.valueOf(interval), Integer.valueOf(smooth), 0));
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableDualStreamMode(boolean z2) {
        return setParameters(String.format("{\"rtc.dual_stream_mode\":%b,\"che.video.enableLowBitRateStream\":%d}", Boolean.valueOf(z2), Integer.valueOf(z2 ? 1 : 0)));
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean enableHighPerfWifiMode(boolean enable) {
        Context context = this.mContext.get();
        if (context == null) {
            return false;
        }
        if (!enable) {
            this.mWifiLock = null;
            return true;
        }
        if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") != 0) {
            this.mWifiLock = null;
            return false;
        }
        if (this.mWifiLock != null) {
            return true;
        }
        this.mWifiLock = ((WifiManager) context.getSystemService("wifi")).createWifiLock(3, "agora.voip.lock");
        return true;
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableInEarMonitoring(boolean enabled) {
        return setParameter("che.audio.headset.monitoring", enabled);
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableLastmileTest() {
        return setParameter("rtc.lastmile_test", true);
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableLocalAudio(boolean enabled) {
        return nativeEnableLocalAudio(this.mNativeHandle, enabled);
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableLocalVideo(boolean enabled) {
        this.mLocalVideoEnabled = enabled;
        return setParameters(String.format("{\"rtc.video.capture\":%b,\"che.video.local.capture\":%b,\"che.video.local.render\":%b,\"che.video.local.send\":%b}", Boolean.valueOf(enabled), Boolean.valueOf(enabled), Boolean.valueOf(enabled), Boolean.valueOf(enabled)));
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int enableRecap(int interval) {
        if (interval < 0) {
            interval = 0;
        }
        return setParameter("che.audio.recap.interval", interval);
    }

    public int enableRemoteVideo(boolean enabled, int uid) {
        return setParameterObject("che.video.peer.receive", formatString("{\"enable\":%b, \"uid\":%d}", Boolean.valueOf(enabled), Long.valueOf(uid & InternalZipConstants.ZIP_64_LIMIT)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableSoundPositionIndication(boolean enabled) {
        return setParameter("che.audio.enable_sound_position", enabled);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int enableTransportQualityIndication(boolean enabled) {
        return setParameter("rtc.transport_quality_indication", enabled);
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableVideo() {
        this.mLocalVideoEnabled = true;
        return nativeEnableVideo(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public int enableWebSdkInteroperability(boolean enabled) {
        return setParameters(String.format("{\"rtc.video.web_h264_interop_enable\":%b,\"che.video.web_h264_interop_enable\":%b}", Boolean.valueOf(enabled), Boolean.valueOf(enabled)));
    }

    public void finalize() {
        long j2 = this.mNativeHandle;
        if (j2 != 0) {
            nativeDestroy(j2);
        }
    }

    public ActivityManager getActivityManager(Context context) {
        if (context == null) {
            return null;
        }
        return (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
    }

    @Override // io.agora.rtc.RtcEngine
    public IAudioEffectManager getAudioEffectManager() {
        return this;
    }

    public AudioManager getAudioManager(Context context) {
        if (context == null) {
            return null;
        }
        return (AudioManager) context.getSystemService("audio");
    }

    @Override // io.agora.rtc.RtcEngine
    public int getAudioMixingCurrentPosition() {
        return nativeGetIntParameter(this.mNativeHandle, "che.audio.get_mixing_file_played_ms", null);
    }

    @Override // io.agora.rtc.RtcEngine
    public int getAudioMixingDuration() {
        return nativeGetIntParameter(this.mNativeHandle, "che.audio.get_mixing_file_length_ms", null);
    }

    @Override // io.agora.rtc.RtcEngine
    public int getAudioMixingPlayoutVolume() {
        return nativeGetIntParameter(this.mNativeHandle, "che.audio.get_file_as_playout_volume", null);
    }

    @Override // io.agora.rtc.RtcEngine
    public int getAudioMixingPublishVolume() {
        return nativeGetIntParameter(this.mNativeHandle, "che.audio.get_file_as_playout_publish_volume", null);
    }

    @Override // io.agora.rtc.RtcEngine
    public String getCallId() {
        return nativeGetCallId(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public float getCameraMaxZoomFactor() {
        String strNativeGetParameter = nativeGetParameter(this.mNativeHandle, "che.video.camera.get_max_zoom", null);
        if (strNativeGetParameter == null) {
            return 1.0f;
        }
        return Double.valueOf(strNativeGetParameter).floatValue();
    }

    @Override // io.agora.rtc.RtcEngine
    public int getConnectionState() {
        return nativeGetConncetionState(this.mNativeHandle);
    }

    public Context getContext() {
        return this.mContext.get();
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int getEffectCurrentPosition(int soundId) {
        int iNativeGetIntParameter = nativeGetIntParameter(this.mNativeHandle, "che.audio.get_effect_file_position", formatString(TimeModel.NUMBER_FORMAT, Integer.valueOf(soundId)));
        if (iNativeGetIntParameter < 0) {
            return 0;
        }
        return iNativeGetIntParameter;
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int getEffectDuration(String filePath) {
        int iNativeGetIntParameter = nativeGetIntParameter(this.mNativeHandle, "che.audio.get_effect_file_duration", formatString("%s", filePath));
        if (iNativeGetIntParameter < 0) {
            return 0;
        }
        return iNativeGetIntParameter;
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int getEffectPlayoutVolume(int soundId) {
        int iNativeGetIntParameter = nativeGetIntParameter(this.mNativeHandle, "che.audio.get_effect_file_playout_volume", formatString(TimeModel.NUMBER_FORMAT, Integer.valueOf(soundId)));
        if (iNativeGetIntParameter < 0) {
            return 0;
        }
        return iNativeGetIntParameter;
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int getEffectPublishVolume(int soundId) {
        int iNativeGetIntParameter = nativeGetIntParameter(this.mNativeHandle, "che.audio.get_effect_file_publish_volume", formatString(TimeModel.NUMBER_FORMAT, Integer.valueOf(soundId)));
        if (iNativeGetIntParameter < 0) {
            return 0;
        }
        return iNativeGetIntParameter;
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public double getEffectsVolume() {
        double dNativeGetIntParameter = nativeGetIntParameter(this.mNativeHandle, "che.audio.game_get_effects_volume", null);
        if (dNativeGetIntParameter < 0.0d) {
            return 0.0d;
        }
        return dNativeGetIntParameter;
    }

    @Override // io.agora.rtc.RtcEngine
    public long getNativeHandle() {
        return nativeGetHandle(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public String getParameter(String parameter, String args) {
        return nativeGetParameter(this.mNativeHandle, parameter, args);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public String getParameters(String parameters) {
        return nativeGetParameters(this.mNativeHandle, parameters);
    }

    public String getProfile() {
        return nativeGetProfile(this.mNativeHandle);
    }

    public IRtcEngineEventHandler.RtcStats getRtcStats() {
        if (this.mRtcStats == null) {
            this.mRtcStats = new IRtcEngineEventHandler.RtcStats();
        }
        return this.mRtcStats;
    }

    @Override // io.agora.rtc.RtcEngine
    public int getUserInfoByUid(int uid, UserInfo userInfo) {
        return nativeGetUserInfoByUid(this.mNativeHandle, uid, userInfo);
    }

    @Override // io.agora.rtc.RtcEngine
    public int getUserInfoByUserAccount(String userAccount, UserInfo userInfo) {
        return nativeGetUserInfoByUserAccount(this.mNativeHandle, userAccount, userInfo);
    }

    public void handleChannelEvent(int eventId, byte[] evt, IRtcChannelEventHandler handler, RtcChannelImpl rtcChannel) {
        if (handler == null || rtcChannel == null) {
            return;
        }
        switch (eventId) {
            case 101:
                RtcEngineMessage.PError pError = new RtcEngineMessage.PError();
                pError.unmarshall(evt);
                int i2 = pError.err;
                if ((i2 >= 1151 && i2 <= 1164) || (i2 >= 1001 && i2 < 1033 && getParameters("[\"che.audio.adm.active\"]").equals("2"))) {
                    Logging.e(TAG, "ADM Error code " + pError.err + " restart ADM");
                    setParameter("che.audio.opensl", false);
                    setParameters("che.audio.restart");
                }
                handler.onChannelError(rtcChannel, pError.err);
                break;
            case 102:
                RtcEngineMessage.PError pError2 = new RtcEngineMessage.PError();
                pError2.unmarshall(evt);
                int i3 = pError2.err;
                if ((i3 == 1019 || i3 == 1052) && getParameters("[\"che.audio.adm.active\"]").equals("2")) {
                    Logging.e(TAG, "ADM Error code " + pError2.err + " restart ADM");
                    setParameter("che.audio.opensl", false);
                    setParameters("che.audio.restart");
                }
                handler.onChannelWarning(rtcChannel, pError2.err);
                break;
            case 1108:
                handler.onRequestToken(rtcChannel);
                break;
            case 1109:
                RtcEngineMessage.PClientRoleChanged pClientRoleChanged = new RtcEngineMessage.PClientRoleChanged();
                pClientRoleChanged.unmarshall(evt);
                handler.onClientRoleChanged(rtcChannel, pClientRoleChanged.oldRole, pClientRoleChanged.newRole);
                break;
            case 1110:
                RtcEngineMessage.PStreamPublished pStreamPublished = new RtcEngineMessage.PStreamPublished();
                pStreamPublished.unmarshall(evt);
                handler.onStreamPublished(rtcChannel, pStreamPublished.url, pStreamPublished.error);
                break;
            case 1111:
                RtcEngineMessage.PStreamUnPublished pStreamUnPublished = new RtcEngineMessage.PStreamUnPublished();
                pStreamUnPublished.unmarshall(evt);
                handler.onStreamUnpublished(rtcChannel, pStreamUnPublished.url);
                break;
            case 1112:
                handler.onTranscodingUpdated(rtcChannel);
                break;
            case 1116:
                RtcEngineMessage.PStreamInjectedStatus pStreamInjectedStatus = new RtcEngineMessage.PStreamInjectedStatus();
                pStreamInjectedStatus.unmarshall(evt);
                handler.onStreamInjectedStatus(rtcChannel, pStreamInjectedStatus.url, pStreamInjectedStatus.uid, pStreamInjectedStatus.status);
                break;
            case 1117:
                RtcEngineMessage.PPrivilegeWillExpire pPrivilegeWillExpire = new RtcEngineMessage.PPrivilegeWillExpire();
                pPrivilegeWillExpire.unmarshall(evt);
                handler.onTokenPrivilegeWillExpire(rtcChannel, pPrivilegeWillExpire.token);
                break;
            case 1119:
                RtcEngineMessage.PRtmpStreamingState pRtmpStreamingState = new RtcEngineMessage.PRtmpStreamingState();
                pRtmpStreamingState.unmarshall(evt);
                handler.onRtmpStreamingStateChanged(rtcChannel, pRtmpStreamingState.url, pRtmpStreamingState.state, pRtmpStreamingState.error);
                break;
            case 13001:
                RtcEngineMessage.PMediaResJoinMedia pMediaResJoinMedia = new RtcEngineMessage.PMediaResJoinMedia();
                pMediaResJoinMedia.unmarshall(evt);
                if (!pMediaResJoinMedia.firstSuccess) {
                    handler.onRejoinChannelSuccess(rtcChannel, pMediaResJoinMedia.uid, pMediaResJoinMedia.elapsed);
                    break;
                } else {
                    handler.onJoinChannelSuccess(rtcChannel, pMediaResJoinMedia.uid, pMediaResJoinMedia.elapsed);
                    break;
                }
            case 13006:
                Context context = this.mContext.get();
                if (context != null) {
                    getAudioManager(context).setMode(0);
                }
                RtcEngineMessage.PMediaResRtcStats pMediaResRtcStats = new RtcEngineMessage.PMediaResRtcStats();
                pMediaResRtcStats.unmarshall(evt);
                updateRtcStats(pMediaResRtcStats);
                handler.onLeaveChannel(rtcChannel, getRtcStats());
                break;
            case 13007:
                RtcEngineMessage.PMediaResNetworkQuality pMediaResNetworkQuality = new RtcEngineMessage.PMediaResNetworkQuality();
                pMediaResNetworkQuality.unmarshall(evt);
                handler.onNetworkQuality(rtcChannel, pMediaResNetworkQuality.uid, pMediaResNetworkQuality.txQuality, pMediaResNetworkQuality.rxQuality);
                break;
            case 13008:
                RtcEngineMessage.PMediaResUserOfflineEvent pMediaResUserOfflineEvent = new RtcEngineMessage.PMediaResUserOfflineEvent();
                pMediaResUserOfflineEvent.unmarshall(evt);
                handler.onUserOffline(rtcChannel, pMediaResUserOfflineEvent.uid, pMediaResUserOfflineEvent.reason);
                break;
            case 13010:
                RtcEngineMessage.PMediaResRtcStats pMediaResRtcStats2 = new RtcEngineMessage.PMediaResRtcStats();
                pMediaResRtcStats2.unmarshall(evt);
                updateRtcStats(pMediaResRtcStats2);
                handler.onRtcStats(rtcChannel, getRtcStats());
                break;
            case 13013:
                RtcEngineMessage.PMediaResUserJoinedEvent pMediaResUserJoinedEvent = new RtcEngineMessage.PMediaResUserJoinedEvent();
                pMediaResUserJoinedEvent.unmarshall(evt);
                handler.onUserJoined(rtcChannel, pMediaResUserJoinedEvent.uid, pMediaResUserJoinedEvent.elapsed);
                break;
            case 13014:
                RtcEngineMessage.PMediaResUserState pMediaResUserState = new RtcEngineMessage.PMediaResUserState();
                pMediaResUserState.unmarshall(evt);
                handler.onUserMuteAudio(rtcChannel, pMediaResUserState.uid, pMediaResUserState.state);
                break;
            case 14002:
                onRtcChannelFirstRemoteVideoFrame(evt, handler, rtcChannel);
                break;
            case 14004:
                onRtcChannelRemoteVideoStat(evt, handler, rtcChannel);
                break;
            case 14008:
                handler.onConnectionLost(rtcChannel);
                break;
            case 14009:
                onRtcChannelStreamMessage(evt, handler, rtcChannel);
                break;
            case 14012:
                onRtcChannelStreamMessageError(evt, handler, rtcChannel);
                break;
            case 14013:
                onRtcChannelVideoSizeChanged(evt, handler, rtcChannel);
                break;
            case 14015:
                onRtcChannelFirstRemoteAudioFrame(evt, handler, rtcChannel);
                break;
            case 14016:
                RtcEngineMessage.PActiveSpeaker pActiveSpeaker = new RtcEngineMessage.PActiveSpeaker();
                pActiveSpeaker.unmarshall(evt);
                handler.onActiveSpeaker(rtcChannel, pActiveSpeaker.uid);
                break;
            case 14023:
                RtcEngineMessage.PMediaResUserState pMediaResUserState2 = new RtcEngineMessage.PMediaResUserState();
                pMediaResUserState2.unmarshall(evt);
                handler.onRemoteSubscribeFallbackToAudioOnly(rtcChannel, pMediaResUserState2.uid, pMediaResUserState2.state);
                break;
            case 14028:
                RtcEngineMessage.PConnectionState pConnectionState = new RtcEngineMessage.PConnectionState();
                pConnectionState.unmarshall(evt);
                handler.onConnectionStateChanged(rtcChannel, pConnectionState.state, pConnectionState.reason);
                break;
            case 14030:
                onRtcChannelRemoteAudioStat(evt, handler, rtcChannel);
                break;
            case 14033:
                RtcEngineMessage.PMediaResFirstRemoteAudioDecoded pMediaResFirstRemoteAudioDecoded = new RtcEngineMessage.PMediaResFirstRemoteAudioDecoded();
                pMediaResFirstRemoteAudioDecoded.unmarshall(evt);
                handler.onFirstRemoteAudioDecoded(rtcChannel, pMediaResFirstRemoteAudioDecoded.uid, pMediaResFirstRemoteAudioDecoded.elapsed);
                break;
            case 14036:
                onRtcChannelRemoteVideoStateChangedExt(evt, handler, rtcChannel);
                break;
            case 14037:
                onRtcChannelChannelMediaRelayStateChanged(evt, handler, rtcChannel);
                break;
            case 14038:
                onRtcChannelChannelMediaRelayEvent(evt, handler, rtcChannel);
                break;
            case 14040:
                onRtcChannelRemoteAudioStateChanged(evt, handler, rtcChannel);
                break;
            case 14043:
                onRtcChannelPublishAudioStateChanged(evt, handler, rtcChannel);
                break;
            case 14044:
                onRtcChannelPublishVideoStateChanged(evt, handler, rtcChannel);
                break;
            case 14045:
                onRtcChannelSubscribeAudioStateChanged(evt, handler, rtcChannel);
                break;
            case 14046:
                onRtcChannelSubscribeVideoStateChanged(evt, handler, rtcChannel);
                break;
        }
    }

    public void handleEvent(int eventId, byte[] evt, IRtcEngineEventHandler handler) {
        if (handler == null) {
        }
        if (eventId == 1101) {
            RtcEngineMessage.PMediaResTransportQuality pMediaResTransportQuality = new RtcEngineMessage.PMediaResTransportQuality();
            pMediaResTransportQuality.unmarshall(evt);
            if (pMediaResTransportQuality.isAudio) {
                ((IRtcEngineEventHandlerEx) handler).onAudioTransportQuality(pMediaResTransportQuality.peer_uid, pMediaResTransportQuality.bitrate, pMediaResTransportQuality.delay, pMediaResTransportQuality.lost);
                return;
            } else {
                ((IRtcEngineEventHandlerEx) handler).onVideoTransportQuality(pMediaResTransportQuality.peer_uid, pMediaResTransportQuality.bitrate, pMediaResTransportQuality.delay, pMediaResTransportQuality.lost);
                return;
            }
        }
        if (eventId == 1102) {
            RtcEngineMessage.PMediaResAudioQuality pMediaResAudioQuality = new RtcEngineMessage.PMediaResAudioQuality();
            pMediaResAudioQuality.unmarshall(evt);
            handler.onAudioQuality(pMediaResAudioQuality.peer_uid, pMediaResAudioQuality.quality, pMediaResAudioQuality.delay, pMediaResAudioQuality.lost);
            return;
        }
        switch (eventId) {
            case 100:
                sendLogEvent(evt);
                break;
            case 101:
                RtcEngineMessage.PError pError = new RtcEngineMessage.PError();
                pError.unmarshall(evt);
                int i2 = pError.err;
                if ((i2 >= 1151 && i2 <= 1164) || (i2 >= 1001 && i2 < 1033 && getParameters("[\"che.audio.adm.active\"]").equals("2"))) {
                    Logging.e(TAG, "ADM Error code " + pError.err + " restart ADM");
                    setParameter("che.audio.opensl", false);
                    setParameters("che.audio.restart");
                }
                handler.onError(pError.err);
                break;
            case 102:
                RtcEngineMessage.PError pError2 = new RtcEngineMessage.PError();
                pError2.unmarshall(evt);
                int i3 = pError2.err;
                if ((i3 == 1019 || i3 == 1052) && getParameters("[\"che.audio.adm.active\"]").equals("2")) {
                    Logging.e(TAG, "ADM Error code " + pError2.err + " restart ADM");
                    setParameter("che.audio.opensl", false);
                    setParameters("che.audio.restart");
                }
                handler.onWarning(pError2.err);
                break;
            default:
                switch (eventId) {
                    case 1002:
                        break;
                    case 1104:
                        RtcEngineMessage.PMediaEngineEvent pMediaEngineEvent = new RtcEngineMessage.PMediaEngineEvent();
                        pMediaEngineEvent.unmarshall(evt);
                        int i4 = pMediaEngineEvent.code;
                        if (i4 == 10) {
                            handler.onAudioMixingFinished();
                            break;
                        } else if (i4 == 14) {
                            handler.onMicrophoneEnabled(true);
                            break;
                        } else if (i4 == 15) {
                            handler.onMicrophoneEnabled(false);
                            break;
                        } else {
                            switch (i4) {
                                case 20:
                                case 21:
                                case 22:
                                case 23:
                                    break;
                                default:
                                    if (i4 >= 701 && i4 <= 713) {
                                        if (i4 >= 701 && i4 <= 703) {
                                            handler.onAudioMixingStateChanged(714, i4);
                                            break;
                                        } else if (i4 == 712) {
                                            Logging.d(TAG, "AudioMixing restart");
                                            break;
                                        } else {
                                            handler.onAudioMixingStateChanged(i4, 0);
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                    case 1106:
                        onApiCallExecuted(evt, handler);
                        break;
                    case 10001:
                        new RtcEngineMessage.MediaResSetupTime().unmarshall(evt);
                        break;
                    case 13001:
                        RtcEngineMessage.PMediaResJoinMedia pMediaResJoinMedia = new RtcEngineMessage.PMediaResJoinMedia();
                        pMediaResJoinMedia.unmarshall(evt);
                        if (!pMediaResJoinMedia.firstSuccess) {
                            handler.onRejoinChannelSuccess(pMediaResJoinMedia.channel, pMediaResJoinMedia.uid, pMediaResJoinMedia.elapsed);
                            break;
                        } else {
                            handler.onJoinChannelSuccess(pMediaResJoinMedia.channel, pMediaResJoinMedia.uid, pMediaResJoinMedia.elapsed);
                            break;
                        }
                    case 13010:
                        RtcEngineMessage.PMediaResRtcStats pMediaResRtcStats = new RtcEngineMessage.PMediaResRtcStats();
                        pMediaResRtcStats.unmarshall(evt);
                        updateRtcStats(pMediaResRtcStats);
                        handler.onRtcStats(getRtcStats());
                        break;
                    case 13013:
                        RtcEngineMessage.PMediaResUserJoinedEvent pMediaResUserJoinedEvent = new RtcEngineMessage.PMediaResUserJoinedEvent();
                        pMediaResUserJoinedEvent.unmarshall(evt);
                        handler.onUserJoined(pMediaResUserJoinedEvent.uid, pMediaResUserJoinedEvent.elapsed);
                        break;
                    case 13014:
                        RtcEngineMessage.PMediaResUserState pMediaResUserState = new RtcEngineMessage.PMediaResUserState();
                        pMediaResUserState.unmarshall(evt);
                        handler.onUserMuteAudio(pMediaResUserState.uid, pMediaResUserState.state);
                        break;
                    case 13015:
                        RtcEngineMessage.PMediaResUserState pMediaResUserState2 = new RtcEngineMessage.PMediaResUserState();
                        pMediaResUserState2.unmarshall(evt);
                        handler.onUserMuteVideo(pMediaResUserState2.uid, pMediaResUserState2.state);
                        break;
                    case 13016:
                        RtcEngineMessage.PMediaResUserState pMediaResUserState3 = new RtcEngineMessage.PMediaResUserState();
                        pMediaResUserState3.unmarshall(evt);
                        handler.onUserEnableVideo(pMediaResUserState3.uid, pMediaResUserState3.state);
                        break;
                    case 13017:
                        RtcEngineMessage.PMediaResLastmileQuality pMediaResLastmileQuality = new RtcEngineMessage.PMediaResLastmileQuality();
                        pMediaResLastmileQuality.unmarshall(evt);
                        handler.onLastmileQuality(pMediaResLastmileQuality.quality);
                        break;
                    case 13018:
                        RtcEngineMessage.PMediaResAudioEffectFinished pMediaResAudioEffectFinished = new RtcEngineMessage.PMediaResAudioEffectFinished();
                        pMediaResAudioEffectFinished.unmarshall(evt);
                        handler.onAudioEffectFinished(pMediaResAudioEffectFinished.soundId);
                        break;
                    case 13019:
                        RtcEngineMessage.PMediaResUserState pMediaResUserState4 = new RtcEngineMessage.PMediaResUserState();
                        pMediaResUserState4.unmarshall(evt);
                        handler.onUserEnableLocalVideo(pMediaResUserState4.uid, pMediaResUserState4.state);
                        break;
                    case 13020:
                        RtcEngineMessage.PMediaResLastmileProbeResult pMediaResLastmileProbeResult = new RtcEngineMessage.PMediaResLastmileProbeResult();
                        pMediaResLastmileProbeResult.unmarshall(evt);
                        IRtcEngineEventHandler.LastmileProbeResult lastmileProbeResult = new IRtcEngineEventHandler.LastmileProbeResult();
                        lastmileProbeResult.state = pMediaResLastmileProbeResult.state;
                        lastmileProbeResult.rtt = pMediaResLastmileProbeResult.rtt;
                        IRtcEngineEventHandler.LastmileProbeResult.LastmileProbeOneWayResult lastmileProbeOneWayResult = lastmileProbeResult.uplinkReport;
                        RtcEngineMessage.PMediaResLastmileProbeResult.LastmileProbeOneWayResult lastmileProbeOneWayResult2 = pMediaResLastmileProbeResult.uplinkReport;
                        lastmileProbeOneWayResult.packetLossRate = lastmileProbeOneWayResult2.packetLossRate;
                        lastmileProbeOneWayResult.jitter = lastmileProbeOneWayResult2.jitter;
                        lastmileProbeOneWayResult.availableBandwidth = lastmileProbeOneWayResult2.availableBandwidth;
                        IRtcEngineEventHandler.LastmileProbeResult.LastmileProbeOneWayResult lastmileProbeOneWayResult3 = lastmileProbeResult.downlinkReport;
                        RtcEngineMessage.PMediaResLastmileProbeResult.LastmileProbeOneWayResult lastmileProbeOneWayResult4 = pMediaResLastmileProbeResult.downlinkReport;
                        lastmileProbeOneWayResult3.packetLossRate = lastmileProbeOneWayResult4.packetLossRate;
                        lastmileProbeOneWayResult3.jitter = lastmileProbeOneWayResult4.jitter;
                        lastmileProbeOneWayResult3.availableBandwidth = lastmileProbeOneWayResult4.availableBandwidth;
                        handler.onLastmileProbeResult(lastmileProbeResult);
                        break;
                    case 14019:
                        handler.onConnectionBanned();
                        break;
                    case 14020:
                        onCameraFocusAreaChanged(evt, handler);
                        break;
                    case 14022:
                        RtcEngineMessage.PLocalFallbackStatus pLocalFallbackStatus = new RtcEngineMessage.PLocalFallbackStatus();
                        pLocalFallbackStatus.unmarshall(evt);
                        handler.onLocalPublishFallbackToAudioOnly(pLocalFallbackStatus.state);
                        break;
                    case 14023:
                        RtcEngineMessage.PMediaResUserState pMediaResUserState5 = new RtcEngineMessage.PMediaResUserState();
                        pMediaResUserState5.unmarshall(evt);
                        handler.onRemoteSubscribeFallbackToAudioOnly(pMediaResUserState5.uid, pMediaResUserState5.state);
                        break;
                    case 14024:
                        RtcEngineMessage.PUserTransportStat pUserTransportStat = new RtcEngineMessage.PUserTransportStat();
                        pUserTransportStat.unmarshall(evt);
                        if (!pUserTransportStat.isAudio) {
                            handler.onRemoteVideoTransportStats(pUserTransportStat.peer_uid, pUserTransportStat.delay, pUserTransportStat.lost, pUserTransportStat.rxKBitRate);
                            break;
                        } else {
                            handler.onRemoteAudioTransportStats(pUserTransportStat.peer_uid, pUserTransportStat.delay, pUserTransportStat.lost, pUserTransportStat.rxKBitRate);
                            break;
                        }
                    case 14028:
                        RtcEngineMessage.PConnectionState pConnectionState = new RtcEngineMessage.PConnectionState();
                        pConnectionState.unmarshall(evt);
                        handler.onConnectionStateChanged(pConnectionState.state, pConnectionState.reason);
                        break;
                    case 14029:
                        onCameraExposureAreaChanged(evt, handler);
                        break;
                    case 14030:
                        onRemoteAudioStat(evt, handler);
                        break;
                    case 14031:
                        RtcEngineMessage.PNetworkTypeChanged pNetworkTypeChanged = new RtcEngineMessage.PNetworkTypeChanged();
                        pNetworkTypeChanged.unmarshall(evt);
                        handler.onNetworkTypeChanged(pNetworkTypeChanged.type);
                        break;
                    case 14032:
                        RtcEngineMessage.PAudioRoutingChanged pAudioRoutingChanged = new RtcEngineMessage.PAudioRoutingChanged();
                        pAudioRoutingChanged.unmarshall(evt);
                        handler.onAudioRouteChanged(pAudioRoutingChanged.routing);
                        break;
                    case 14033:
                        RtcEngineMessage.PMediaResFirstRemoteAudioDecoded pMediaResFirstRemoteAudioDecoded = new RtcEngineMessage.PMediaResFirstRemoteAudioDecoded();
                        pMediaResFirstRemoteAudioDecoded.unmarshall(evt);
                        handler.onFirstRemoteAudioDecoded(pMediaResFirstRemoteAudioDecoded.uid, pMediaResFirstRemoteAudioDecoded.elapsed);
                        break;
                    case 14034:
                        RtcEngineMessage.PUserAccountInfo pUserAccountInfo = new RtcEngineMessage.PUserAccountInfo();
                        pUserAccountInfo.unmarshall(evt);
                        handler.onLocalUserRegistered(pUserAccountInfo.uid, pUserAccountInfo.userAccount);
                        break;
                    case 14035:
                        RtcEngineMessage.PUserAccountInfo pUserAccountInfo2 = new RtcEngineMessage.PUserAccountInfo();
                        pUserAccountInfo2.unmarshall(evt);
                        UserInfo userInfo = new UserInfo();
                        int i5 = pUserAccountInfo2.uid;
                        userInfo.uid = i5;
                        userInfo.userAccount = pUserAccountInfo2.userAccount;
                        handler.onUserInfoUpdated(i5, userInfo);
                        break;
                    case 14036:
                        onRemoteVideoStateChangedExt(evt, handler);
                        break;
                    case 14037:
                        onChannelMediaRelayStateChanged(evt, handler);
                        break;
                    case 14038:
                        onChannelMediaRelayEvent(evt, handler);
                        break;
                    case 14040:
                        onRemoteAudioStateChanged(evt, handler);
                        break;
                    case 14041:
                        onLocalAudioStat(evt, handler);
                        break;
                    case 14042:
                        RtcEngineMessage.PMediaResLocalAudioStateChanged pMediaResLocalAudioStateChanged = new RtcEngineMessage.PMediaResLocalAudioStateChanged();
                        pMediaResLocalAudioStateChanged.unmarshall(evt);
                        handler.onLocalAudioStateChanged(pMediaResLocalAudioStateChanged.state, pMediaResLocalAudioStateChanged.error);
                        break;
                    case 14043:
                        onPublishAudioStateChanged(evt, handler);
                        break;
                    case 14044:
                        onPublishVideoStateChanged(evt, handler);
                        break;
                    case 14045:
                        onSubscribeAudioStateChanged(evt, handler);
                        break;
                    case 14046:
                        onSubscribeVideoStateChanged(evt, handler);
                        break;
                    default:
                        switch (eventId) {
                            case 1005:
                                handler.onCameraReady();
                                break;
                            case 1006:
                                handler.onMediaEngineStartCallSuccess();
                                break;
                            case 1007:
                                handler.onVideoStopped();
                                break;
                            default:
                                switch (eventId) {
                                    case 1108:
                                        handler.onRequestToken();
                                        break;
                                    case 1109:
                                        RtcEngineMessage.PClientRoleChanged pClientRoleChanged = new RtcEngineMessage.PClientRoleChanged();
                                        pClientRoleChanged.unmarshall(evt);
                                        handler.onClientRoleChanged(pClientRoleChanged.oldRole, pClientRoleChanged.newRole);
                                        break;
                                    case 1110:
                                        RtcEngineMessage.PStreamPublished pStreamPublished = new RtcEngineMessage.PStreamPublished();
                                        pStreamPublished.unmarshall(evt);
                                        handler.onStreamPublished(pStreamPublished.url, pStreamPublished.error);
                                        break;
                                    case 1111:
                                        RtcEngineMessage.PStreamUnPublished pStreamUnPublished = new RtcEngineMessage.PStreamUnPublished();
                                        pStreamUnPublished.unmarshall(evt);
                                        handler.onStreamUnpublished(pStreamUnPublished.url);
                                        break;
                                    case 1112:
                                        handler.onTranscodingUpdated();
                                        break;
                                    default:
                                        switch (eventId) {
                                            case 1116:
                                                RtcEngineMessage.PStreamInjectedStatus pStreamInjectedStatus = new RtcEngineMessage.PStreamInjectedStatus();
                                                pStreamInjectedStatus.unmarshall(evt);
                                                handler.onStreamInjectedStatus(pStreamInjectedStatus.url, pStreamInjectedStatus.uid, pStreamInjectedStatus.status);
                                                break;
                                            case 1117:
                                                RtcEngineMessage.PPrivilegeWillExpire pPrivilegeWillExpire = new RtcEngineMessage.PPrivilegeWillExpire();
                                                pPrivilegeWillExpire.unmarshall(evt);
                                                handler.onTokenPrivilegeWillExpire(pPrivilegeWillExpire.token);
                                                break;
                                            case 1118:
                                                RtcEngineMessage.PMediaResLocalVideoStateChanged pMediaResLocalVideoStateChanged = new RtcEngineMessage.PMediaResLocalVideoStateChanged();
                                                pMediaResLocalVideoStateChanged.unmarshall(evt);
                                                handler.onLocalVideoStateChanged(pMediaResLocalVideoStateChanged.localVideoState, pMediaResLocalVideoStateChanged.error);
                                                break;
                                            case 1119:
                                                RtcEngineMessage.PRtmpStreamingState pRtmpStreamingState = new RtcEngineMessage.PRtmpStreamingState();
                                                pRtmpStreamingState.unmarshall(evt);
                                                handler.onRtmpStreamingStateChanged(pRtmpStreamingState.url, pRtmpStreamingState.state, pRtmpStreamingState.error);
                                                break;
                                            default:
                                                switch (eventId) {
                                                    case 13006:
                                                        Context context = this.mContext.get();
                                                        if (context != null) {
                                                            getAudioManager(context).setMode(0);
                                                        }
                                                        RtcEngineMessage.PMediaResRtcStats pMediaResRtcStats2 = new RtcEngineMessage.PMediaResRtcStats();
                                                        pMediaResRtcStats2.unmarshall(evt);
                                                        updateRtcStats(pMediaResRtcStats2);
                                                        handler.onLeaveChannel(getRtcStats());
                                                        break;
                                                    case 13007:
                                                        RtcEngineMessage.PMediaResNetworkQuality pMediaResNetworkQuality = new RtcEngineMessage.PMediaResNetworkQuality();
                                                        pMediaResNetworkQuality.unmarshall(evt);
                                                        handler.onNetworkQuality(pMediaResNetworkQuality.uid, pMediaResNetworkQuality.txQuality, pMediaResNetworkQuality.rxQuality);
                                                        break;
                                                    case 13008:
                                                        RtcEngineMessage.PMediaResUserOfflineEvent pMediaResUserOfflineEvent = new RtcEngineMessage.PMediaResUserOfflineEvent();
                                                        pMediaResUserOfflineEvent.unmarshall(evt);
                                                        handler.onUserOffline(pMediaResUserOfflineEvent.uid, pMediaResUserOfflineEvent.reason);
                                                        break;
                                                    default:
                                                        switch (eventId) {
                                                            case 14000:
                                                                ((IRtcEngineEventHandlerEx) handler).onRecap(evt);
                                                                break;
                                                            case 14001:
                                                                onSpeakersReport(evt, handler);
                                                                break;
                                                            case 14002:
                                                                onFirstRemoteVideoFrame(evt, handler);
                                                                break;
                                                            case 14003:
                                                                onLocalVideoStat(evt, handler);
                                                                break;
                                                            case 14004:
                                                                onRemoteVideoStat(evt, handler);
                                                                break;
                                                            case 14005:
                                                                onFirstLocalVideoFrame(evt, handler);
                                                                break;
                                                            default:
                                                                switch (eventId) {
                                                                    case 14007:
                                                                        onFirstRemoteVideoDecoded(evt, handler);
                                                                        break;
                                                                    case 14008:
                                                                        handler.onConnectionLost();
                                                                        break;
                                                                    case 14009:
                                                                        onStreamMessage(evt, handler);
                                                                        break;
                                                                    case 14010:
                                                                        handler.onConnectionInterrupted();
                                                                        break;
                                                                    default:
                                                                        switch (eventId) {
                                                                            case 14012:
                                                                                onStreamMessageError(evt, handler);
                                                                                break;
                                                                            case 14013:
                                                                                onVideoSizeChanged(evt, handler);
                                                                                break;
                                                                            case 14014:
                                                                                onFirstLocalAudioFrame(evt, handler);
                                                                                break;
                                                                            case 14015:
                                                                                onFirstRemoteAudioFrame(evt, handler);
                                                                                break;
                                                                            case 14016:
                                                                                RtcEngineMessage.PActiveSpeaker pActiveSpeaker = new RtcEngineMessage.PActiveSpeaker();
                                                                                pActiveSpeaker.unmarshall(evt);
                                                                                handler.onActiveSpeaker(pActiveSpeaker.uid);
                                                                                break;
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
                handler.onMediaEngineLoadSuccess();
                break;
        }
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean isCameraAutoFocusFaceModeSupported() {
        return Boolean.valueOf(nativeGetParameter(this.mNativeHandle, "che.video.camera.face_focus_supported", null)).booleanValue();
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean isCameraExposurePositionSupported() {
        return Boolean.valueOf(nativeGetParameter(this.mNativeHandle, "che.video.camera.exposure_supported", null)).booleanValue();
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean isCameraFocusSupported() {
        return Boolean.valueOf(nativeGetParameter(this.mNativeHandle, "che.video.camera.focus_supported", null)).booleanValue();
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean isCameraTorchSupported() {
        return Boolean.valueOf(nativeGetParameter(this.mNativeHandle, "che.video.camera.torch_supported", null)).booleanValue();
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean isCameraZoomSupported() {
        return Boolean.valueOf(nativeGetParameter(this.mNativeHandle, "che.video.camera.zoom_supported", null)).booleanValue();
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean isSpeakerphoneEnabled() {
        return nativeIsSpeakerphoneEnabled(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean isTextureEncodeSupported() {
        return DeviceUtils.getRecommendedEncoderType() == 0;
    }

    @Override // io.agora.rtc.RtcEngine
    public int joinChannel(String key, String channelName, String optionalInfo, int optionalUid) {
        Context context = this.mContext.get();
        if (context == null) {
            return -7;
        }
        doJoinChannelCheck(context);
        int iNativeJoinChannel = nativeJoinChannel(this.mNativeHandle, null, key, channelName, optionalInfo, optionalUid);
        synchronized (this) {
            if (this.mDefaultRtcChannel == null) {
                this.mDefaultRtcChannel = new RtcChannelImpl();
            }
            if (iNativeJoinChannel == 0) {
                this.mDefaultRtcChannel.initialize(this, nativeGetDefaultRtcChannel(this.mNativeHandle));
            }
        }
        return iNativeJoinChannel;
    }

    @Override // io.agora.rtc.RtcEngine
    public int joinChannelWithUserAccount(String token, String channelId, String userAccount) {
        int iNativeJoinChannelWithUserAccount = nativeJoinChannelWithUserAccount(this.mNativeHandle, token, channelId, userAccount);
        synchronized (this) {
            if (this.mDefaultRtcChannel == null) {
                this.mDefaultRtcChannel = new RtcChannelImpl();
            }
            if (iNativeJoinChannelWithUserAccount == 0) {
                this.mDefaultRtcChannel.initialize(this, nativeGetDefaultRtcChannel(this.mNativeHandle));
            }
        }
        return iNativeJoinChannelWithUserAccount;
    }

    @Override // io.agora.rtc.RtcEngine
    public int leaveChannel() {
        synchronized (this) {
            if (this.mDefaultRtcChannel != null) {
                this.mDefaultRtcChannel = null;
            }
        }
        doLeaveChannelCheck();
        return nativeLeaveChannel(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public String makeQualityReportUrl(String channelName, int listenerUid, int speakerUid, int format) {
        return nativeMakeQualityReportUrl(this.mNativeHandle, channelName, listenerUid, speakerUid, format);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int monitorAudioRouteChange(boolean isMonitoring) {
        Logging.i("API call monitorAudioRouteChange:" + isMonitoring);
        return 0;
    }

    @Override // io.agora.rtc.RtcEngine
    @TargetApi(11)
    @Deprecated
    public void monitorBluetoothHeadsetEvent(boolean monitor) {
        Logging.i(TAG, "enter monitorBluetoothHeadsetEvent:" + monitor);
    }

    @Override // io.agora.rtc.RtcEngine
    @Deprecated
    public void monitorHeadsetEvent(boolean monitor) {
        Logging.i(TAG, "enter monitorHeadsetEvent:" + monitor);
    }

    @Override // io.agora.rtc.RtcEngine
    public int muteAllRemoteAudioStreams(boolean muted) {
        return setParameter("rtc.audio.mute_peers", muted);
    }

    @Override // io.agora.rtc.RtcEngine
    public int muteAllRemoteVideoStreams(boolean muted) {
        return nativeMuteAllRemoteVideoStreams(this.mNativeHandle, muted);
    }

    @Override // io.agora.rtc.RtcEngine
    public int muteLocalAudioStream(boolean muted) {
        return setParameters(formatString("{\"rtc.audio.mute_me\":%b, \"che.audio.mute_me\":%b}", Boolean.valueOf(muted), Boolean.valueOf(muted)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int muteLocalVideoStream(boolean muted) {
        return nativeMuteLocalVideoStream(this.mNativeHandle, muted);
    }

    @Override // io.agora.rtc.RtcEngine
    public int muteRemoteAudioStream(int uid, boolean muted) {
        return setParameters(formatString("{\"rtc.audio.mute_peer\":{\"uid\":%d,\"mute\":%b}}", Long.valueOf(uid & InternalZipConstants.ZIP_64_LIMIT), Boolean.valueOf(muted)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int muteRemoteVideoStream(int uid, boolean muted) {
        return setParameters(formatString("{\"rtc.video.mute_peer\":{\"uid\":%d,\"mute\":%b}}", Long.valueOf(uid & InternalZipConstants.ZIP_64_LIMIT), Boolean.valueOf(muted)));
    }

    public void onChannelEvent(String channel, int eventId, byte[] evt) {
        RtcChannelImpl next;
        if (channel == null || channel.length() <= 0) {
            return;
        }
        synchronized (this) {
            Iterator<RtcChannelImpl> it = this.mRtcChannels.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (next.channelId() != null && next.channelId().equals(channel)) {
                    break;
                }
            }
        }
        if (next == null || !next.isInitialized() || next.getEventHandler() == null) {
            return;
        }
        handleChannelEvent(eventId, evt, next.getEventHandler(), next);
    }

    public void onEvent(int eventId, byte[] evt) {
        try {
            Iterator<IRtcEngineEventHandler> it = this.mRtcHandlers.keySet().iterator();
            while (it.hasNext()) {
                IRtcEngineEventHandler next = it.next();
                if (next == null) {
                    it.remove();
                } else {
                    handleEvent(eventId, evt, next);
                }
            }
        } catch (Exception e2) {
            Log.e(TAG, "onEvent: " + e2.toString());
        }
    }

    public void onRtcChannelJoinChannel() {
        doJoinChannelCheck(getContext());
    }

    public void onRtcChannelLeaveChannel() {
        doLeaveChannelCheck();
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int pauseAllEffects() {
        return setParameter("che.audio.game_pause_all_effects", true);
    }

    @Override // io.agora.rtc.RtcEngine
    public int pauseAudio() {
        return setParameter("rtc.audio.paused", true);
    }

    @Override // io.agora.rtc.RtcEngine
    public int pauseAudioMixing() {
        return setParameter("che.audio.pause_file_as_playout", true);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int pauseEffect(int soundId) {
        return setParameter("che.audio.game_pause_effect", soundId);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    @Deprecated
    public int playEffect(int soundId, String filePath, int loopCount, double pitch, double pan, double gain) {
        return playEffect(soundId, filePath, loopCount, pitch, pan, gain, false, 0);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int playRecap() {
        return setParameter("che.audio.recap.start_play", true);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int preloadEffect(int soundId, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return -2;
        }
        return setParameterObject("che.audio.game_preload_effect", formatString("{\"soundId\":%d,\"filePath\":\"%s\"}", Integer.valueOf(soundId), filePath));
    }

    @Override // io.agora.rtc.RtcEngine
    public int pullPlaybackAudioFrame(byte[] data, int lengthInBytes) {
        int i2 = this.mSetedAudioSinkChannels;
        if (i2 == 1 || i2 == 2) {
            return nativePullAudioFrame(this.mNativeHandle, data, lengthInBytes, i2);
        }
        return -1;
    }

    @Override // io.agora.rtc.RtcEngine
    public int pushExternalAudioFrame(byte[] data, long timestamp) {
        return nativePushExternalAudioFrameRawData(this.mNativeHandle, data, timestamp, this.mExAudioSampleRate, this.mExAudioChannels);
    }

    @Override // io.agora.rtc.RtcEngine
    public boolean pushExternalVideoFrame(AgoraVideoFrame frame) {
        int i2;
        if (frame == null || (i2 = frame.format) == 12) {
            Logging.e("pushExternalVideoFrame failed!! invalid video frame.");
            return false;
        }
        if (this.mVideoSourceType != 3) {
            Logging.e("pushExternalVideoFrame failed!! Call setExternalVideoSource to enable enable external video source!!");
            return false;
        }
        if (i2 != 10 && i2 != 11) {
            return i2 > 0 && i2 <= 8 && deliverFrame(this.mNativeHandle, frame.buf, frame.stride, frame.height, frame.cropLeft, frame.cropTop, frame.cropRight, frame.cropBottom, frame.rotation, frame.timeStamp, i2) == 0;
        }
        android.opengl.EGLContext eGLContext = frame.eglContext14;
        if (eGLContext != null) {
            return updateSharedContext(eGLContext) == 0 && setTextureIdWithMatrix(frame.textureID, frame.eglContext14, frame.format, frame.stride, frame.height, frame.timeStamp, frame.transform) == 0;
        }
        EGLContext eGLContext2 = frame.eglContext11;
        return eGLContext2 != null && updateSharedContext(eGLContext2) == 0 && setTextureIdWithMatrix(frame.textureID, frame.eglContext11, frame.format, frame.stride, frame.height, frame.timeStamp, frame.transform) == 0;
    }

    @Override // io.agora.rtc.RtcEngine
    public int rate(String callId, int rating, String description) {
        return nativeRate(this.mNativeHandle, callId, rating, description);
    }

    @Override // io.agora.rtc.RtcEngine
    public int registerAudioFrameObserver(IAudioFrameObserver observer) {
        return nativeRegisterAudioFrameObserver(this.mNativeHandle, observer);
    }

    @Override // io.agora.rtc.RtcEngine
    public int registerLocalUserAccount(String appId, String userAccount) {
        if (appId == null || userAccount == null) {
            return -2;
        }
        return nativeRegisterLocalUserAccount(this.mNativeHandle, appId, userAccount);
    }

    @Override // io.agora.rtc.RtcEngine
    public int registerMediaMetadataObserver(IMetadataObserver observer, int type) {
        return nativeRegisterMediaMetadataObserver(this.mNativeHandle, observer, type);
    }

    public void reinitialize(Context context, String appId, IRtcEngineEventHandler handler) {
        addHandler(handler);
    }

    @Override // io.agora.rtc.RtcEngine
    public int releaseLogWriter() {
        return nativeReleaseLogWriter(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public void removeHandler(IRtcEngineEventHandler handler) {
        if (this.mRtcHandlers.containsKey(handler)) {
            this.mRtcHandlers.remove(handler);
        }
    }

    @Override // io.agora.rtc.RtcEngine
    public int removeInjectStreamUrl(String url) {
        if (url == null) {
            return -2;
        }
        return nativeRemoveInjectStreamUrl(this.mNativeHandle, url);
    }

    @Override // io.agora.rtc.RtcEngine
    public int removePublishStreamUrl(String url) {
        return nativeRemovePublishStreamUrl(this.mNativeHandle, url);
    }

    public int removeRemoteVideoTrack(int uid) {
        return nativeRemoveVideoReceiveTrack(this.mNativeHandle, uid);
    }

    @Override // io.agora.rtc.RtcEngine
    public int renewToken(String token) {
        if (token == null) {
            return -2;
        }
        return setParameter("rtc.renew_token", token);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int resumeAllEffects() {
        return setParameter("che.audio.game_resume_all_effects", true);
    }

    @Override // io.agora.rtc.RtcEngine
    public int resumeAudio() {
        return setParameter("rtc.audio.paused", false);
    }

    @Override // io.agora.rtc.RtcEngine
    public int resumeAudioMixing() {
        return setParameter("che.audio.pause_file_as_playout", false);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int resumeEffect(int soundId) {
        return setParameter("che.audio.game_resume_effect", soundId);
    }

    @Override // io.agora.rtc.RtcEngine
    public int sendCustomReportMessage(String id, String category, String event, String label, int value) {
        return nativeSendCustomReportMessage(this.mNativeHandle, id, category, event, label, value);
    }

    @Override // io.agora.rtc.RtcEngine
    public int sendStreamMessage(int streamId, byte[] message) {
        return nativeSendStreamMessage(this.mNativeHandle, streamId, message);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int setApiCallMode(int syncCallTimeout) {
        return nativeSetApiCallMode(this.mNativeHandle, syncCallTimeout);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int setAppType(int appType) {
        return nativeSetAppType(this.mNativeHandle, appType);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setAudioMixingPosition(int pos) {
        return setParameter("che.audio.mixing.file.position", pos);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setAudioProfile(int profile, int scenario) {
        return nativeSetAudioProfile(this.mNativeHandle, profile, scenario);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setBeautyEffectOptions(boolean enabled, BeautyOptions options) {
        if (options == null) {
            if (enabled) {
                return -2;
            }
            options = new BeautyOptions();
        }
        return nativeSetBeautyEffectOptions(this.mNativeHandle, enabled, options.lighteningContrastLevel, options.lighteningLevel, options.smoothnessLevel, options.rednessLevel);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setCameraAutoFocusFaceModeEnabled(boolean enabled) {
        return setParameter("che.video.camera.face_detection", enabled);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setCameraCapturerConfiguration(CameraCapturerConfiguration config) {
        return setParameter("che.video.camera_capture_mode", config.preference.getValue());
    }

    @Override // io.agora.rtc.RtcEngine
    public int setCameraExposurePosition(float positionXinView, float positionYinView) {
        return setParameterObject("che.video.camera.exposure", formatString("{\"x\":%f,\"y\":%f,\"preview\":%b}", Float.valueOf(positionXinView), Float.valueOf(positionYinView), Boolean.TRUE));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setCameraFocusPositionInPreview(float positionX, float positionY) {
        return setParameterObject("che.video.camera.focus", formatString("{\"x\":%f,\"y\":%f,\"preview\":%b}", Float.valueOf(positionX), Float.valueOf(positionY), Boolean.TRUE));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setCameraTorchOn(boolean isOn) {
        return setParameter("che.video.camera.flash", isOn);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setCameraZoomFactor(float factor) {
        return setParameter("che.video.camera.zoom", factor);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setChannelProfile(int profile) {
        return nativeSetChannelProfile(this.mNativeHandle, profile);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setClientRole(int role) {
        if (this.mContext.get() == null) {
            return -7;
        }
        if (role != 1 && role != 2) {
            return -2;
        }
        this.mClientRole = role;
        return setParameter("rtc.client_role", role);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setDefaultAudioRoutetoSpeakerphone(boolean defaultToSpeaker) {
        Logging.i(String.format("API call to setDefaultAudioRoutetoSpeakerphone :%b", Boolean.valueOf(defaultToSpeaker)));
        return nativeSetDefaultAudioRoutetoSpeakerphone(this.mNativeHandle, defaultToSpeaker);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setDefaultMuteAllRemoteAudioStreams(boolean muted) {
        return setParameter("rtc.audio.set_default_mute_peers", muted);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setDefaultMuteAllRemoteVideoStreams(boolean muted) {
        return setParameter("rtc.video.set_default_mute_peers", muted);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int setEffectPosition(int soundId, int pos) {
        return setParameterObject("che.audio.set_effect_file_position", formatString("{\"soundId\":%d,\"effectPos\":%d}", Integer.valueOf(soundId), Integer.valueOf(pos)));
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int setEffectsVolume(double volume) {
        return setParameter("che.audio.game_set_effects_volume", volume);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setEnableSpeakerphone(boolean speakerOn) {
        Logging.i(String.format("API call to setEnableSpeakerphone to %b", Boolean.valueOf(speakerOn)));
        return nativeSetEnableSpeakerphone(this.mNativeHandle, speakerOn);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setEncryptionMode(String encryptionMode) {
        return setParameter("rtc.encryption.mode", encryptionMode);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setEncryptionSecret(String secret) {
        return nativeSetEncryptionSecret(this.mNativeHandle, secret);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setExternalAudioSink(boolean enabled, int sampleRate, int channels) {
        if (channels != 1 && channels != 2) {
            return -1;
        }
        if (sampleRate != 8000 && sampleRate != 16000 && sampleRate != 32000 && sampleRate != 44100 && sampleRate != 48000) {
            return -2;
        }
        this.mSetedAudioSinkChannels = channels;
        this.mSetedAudioSinkSampleRate = sampleRate;
        return enabled ? setParameters(formatString("{\"che.audio.external_render\":%b,\"che.audio.external_render.pull\":%b,\"che.audio.set_render_raw_audio_format\":{\"sampleRate\":%d,\"channelCnt\":%d,\"mode\":%d}}", Boolean.valueOf(enabled), Boolean.valueOf(enabled), Integer.valueOf(sampleRate), Integer.valueOf(channels), 0)) : setParameters(formatString("{\"che.audio.external_render\":%b,\"che.audio.external_render\":%b,\"che.audio.external_render.pull\":%b}", Boolean.valueOf(enabled), Boolean.valueOf(enabled), Boolean.valueOf(enabled)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setExternalAudioSource(boolean enabled, int sampleRate, int channels) {
        this.mExAudioSampleRate = sampleRate;
        this.mExAudioChannels = channels;
        return enabled ? setParameters(formatString("{\"che.audio.external_capture\":%b,\"che.audio.external_capture.push\":%b,\"che.audio.set_capture_raw_audio_format\":{\"sampleRate\":%d,\"channelCnt\":%d,\"mode\":%d}}", Boolean.valueOf(enabled), Boolean.valueOf(enabled), Integer.valueOf(sampleRate), Integer.valueOf(channels), 2)) : setParameters(formatString("{\"che.audio.external_capture\":%b,\"che.audio.external_capture\":%b,\"che.audio.external_capture.push\":%b}", Boolean.valueOf(enabled), Boolean.valueOf(enabled), Boolean.valueOf(enabled)));
    }

    @Override // io.agora.rtc.RtcEngine
    public void setExternalVideoSource(boolean z2, boolean z3, boolean z4) {
        if (z2) {
            this.mVideoSourceType = 3;
        } else {
            this.mVideoSourceType = 1;
        }
        if (z3) {
            if (z2) {
                setParameter("che.video.enable_external_texture_input", true);
            } else {
                setParameter("che.video.enable_external_texture_input", false);
                Logging.w("setExternalVideoSource: on Android, texture mode cannot be disabled once enabled.");
            }
        }
        setExtVideoSource(this.mNativeHandle, z2 ? 1 : 0, z4 ? 1 : 0);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setHighQualityAudioParameters(boolean fullband, boolean stereo, boolean fullBitrate) {
        return setParameterObject("che.audio.codec.hq", formatString("{\"fullband\":%b,\"stereo\":%b,\"fullBitrate\":%b}", Boolean.valueOf(fullband), Boolean.valueOf(stereo), Boolean.valueOf(fullBitrate)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setInEarMonitoringVolume(int volume) {
        return setParameter("che.audio.headset.monitoring.parameter", volume);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLiveTranscoding(LiveTranscoding transcoding) {
        if (transcoding == null) {
            return -2;
        }
        if (transcoding.getUsers() != null) {
            Iterator<LiveTranscoding.TranscodingUser> it = transcoding.getUsers().iterator();
            while (it.hasNext()) {
                LiveTranscoding.TranscodingUser next = it.next();
                if (next.width <= 0 || next.height <= 0) {
                    throw new IllegalArgumentException("transcoding user's width and height must be >0");
                }
            }
        }
        return nativeSetLiveTranscoding(this.mNativeHandle, new RtcEngineMessage.PLiveTranscoding().marshall(transcoding));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalPublishFallbackOption(int option) {
        return setParameter("rtc.local_publish_fallback_option", option);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalRenderMode(int mode) {
        return setRemoteRenderMode(0, mode);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalVideoMirrorMode(int mode) {
        String str;
        if (mode == 0) {
            str = ServletHandler.__DEFAULT_SERVLET;
        } else if (mode == 1) {
            str = "forceMirror";
        } else {
            if (mode != 2) {
                return -2;
            }
            str = "disableMirror";
        }
        return setParameter("che.video.localViewMirrorSetting", str);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalVideoRenderer(IVideoSink render) {
        return nativeAddLocalVideoRender(this.mNativeHandle, render, render == null ? 0 : render instanceof AgoraDefaultRender ? 1 : 2);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalVoiceChanger(int voiceChanger) {
        return setParameter("che.audio.morph.voice_changer", voiceChanger);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalVoiceEqualization(int bandFrequency, int bandGain) {
        return setParameterObject("che.audio.morph.equalization", formatString("{\"index\":%d,\"gain\":%d}", Integer.valueOf(bandFrequency), Integer.valueOf(bandGain)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalVoicePitch(double pitch) {
        return setParameter("che.audio.morph.pitch_shift", (int) (pitch * 100.0d));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalVoiceReverb(int reverbKey, int value) {
        return setParameterObject("che.audio.morph.reverb", formatString("{\"key\":%d,\"value\":%d}", Integer.valueOf(reverbKey), Integer.valueOf(value)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLocalVoiceReverbPreset(int preset) {
        return setParameter("che.audio.morph.reverb_preset", preset);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLogFile(String filePath) {
        return setParameter("rtc.log_file", filePath);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLogFileSize(int fileSizeInKBytes) {
        return setParameter("rtc.log_size", fileSizeInKBytes);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLogFilter(int filter) {
        return setParameter("rtc.log_filter", filter & 2063);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setLogWriter(ILogWriter logWriter) {
        return nativeSetLogWriter(this.mNativeHandle, logWriter);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setMixedAudioFrameParameters(int sampleRate, int samplesPerCall) {
        return setParameterObject("che.audio.set_mixed_raw_audio_format", formatString("{\"sampleRate\":%d,\"samplesPerCall\":%d}", Integer.valueOf(sampleRate), Integer.valueOf(samplesPerCall)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setParameters(String parameters) {
        return nativeSetParameters(this.mNativeHandle, parameters);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setPlaybackAudioFrameParameters(int sampleRate, int channel, int mode, int samplesPerCall) {
        return setParameterObject("che.audio.set_render_raw_audio_format", formatString("{\"sampleRate\":%d,\"channelCnt\":%d,\"mode\":%d,\"samplesPerCall\":%d}", Integer.valueOf(sampleRate), Integer.valueOf(channel), Integer.valueOf(mode), Integer.valueOf(samplesPerCall)));
    }

    @Override // io.agora.rtc.RtcEngine
    @Deprecated
    public void setPreferHeadset(boolean enabled) {
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int setProfile(String profile, boolean merge) {
        return nativeSetProfile(this.mNativeHandle, profile, merge);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRecordingAudioFrameParameters(int sampleRate, int channel, int mode, int samplesPerCall) {
        return setParameterObject("che.audio.set_capture_raw_audio_format", formatString("{\"sampleRate\":%d,\"channelCnt\":%d,\"mode\":%d,\"samplesPerCall\":%d}", Integer.valueOf(sampleRate), Integer.valueOf(channel), Integer.valueOf(mode), Integer.valueOf(samplesPerCall)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRemoteDefaultVideoStreamType(int streamType) {
        return setParameter("rtc.video.set_remote_default_video_stream_type", streamType);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRemoteRenderMode(int uid, int mode) {
        return setParameterObject("che.video.render_mode", formatString("{\"uid\":%d,\"mode\":%d}", Long.valueOf(uid & InternalZipConstants.ZIP_64_LIMIT), Integer.valueOf(mode)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRemoteSubscribeFallbackOption(int option) {
        return setParameter("rtc.remote_subscribe_fallback_option", option);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRemoteUserPriority(int uid, int userPriority) {
        return nativeSetRemoteUserPriority(this.mNativeHandle, uid, userPriority);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRemoteVideoRenderer(int uid, IVideoSink render) {
        return nativeAddRemoteVideoRender(this.mNativeHandle, uid, render, render == null ? 0 : render instanceof AgoraDefaultRender ? 1 : 2);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRemoteVideoStreamType(int uid, int streamType) {
        long j2 = uid & InternalZipConstants.ZIP_64_LIMIT;
        return setParameters(formatString("{\"rtc.video.set_remote_video_stream\":{\"uid\":%d,\"stream\":%d},\"che.video.setstream\":{\"uid\":%d,\"stream\":%d}}", Long.valueOf(j2), Integer.valueOf(streamType), Long.valueOf(j2), Integer.valueOf(streamType)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setRemoteVoicePosition(int uid, double pan, double gain) {
        return setParameterObject("che.audio.game_place_sound_position", formatString("{\"uid\":%d,\"pan\":%f,\"gain\":%f}", Integer.valueOf(uid), Double.valueOf(pan), Double.valueOf(gain)));
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int setTextureId(int id, EGLContext sharedContext, int width, int height, long ts) {
        return nativeSetEGL10TextureId(this.mNativeHandle, id, sharedContext, 10, width, height, ts, sMatrix);
    }

    public int setTextureIdWithMatrix(int id, EGLContext sharedContext, int format, int width, int height, long ts, float[] matrix) {
        if (matrix == null) {
            return nativeSetEGL10TextureId(this.mNativeHandle, id, sharedContext, format, width, height, ts, sMatrix);
        }
        if (matrix.length < 16) {
            return -2;
        }
        return nativeSetEGL10TextureId(this.mNativeHandle, id, sharedContext, format, width, height, ts, matrix);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setVideoEncoderConfiguration(VideoEncoderConfiguration config) {
        long j2 = this.mNativeHandle;
        VideoEncoderConfiguration.VideoDimensions videoDimensions = config.dimensions;
        return nativeSetVideoEncoderConfiguration(j2, videoDimensions.width, videoDimensions.height, config.frameRate, config.minFrameRate, config.bitrate, config.minBitrate, config.orientationMode.getValue(), config.degradationPrefer.getValue(), config.mirrorMode);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setVideoProfile(int profile, boolean swapWidthAndHeight) {
        if (profile < 0) {
            return -2;
        }
        return setParameters(formatString("{\"rtc.video.profile\":[%d,%b]}", Integer.valueOf(profile), Boolean.valueOf(swapWidthAndHeight)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setVideoQualityParameters(boolean preferFrameRateOverImageQuality) {
        return setParameters(String.format("{\"rtc.video.prefer_frame_rate\":%b,\"che.video.prefer_frame_rate\":%b}", Boolean.valueOf(preferFrameRateOverImageQuality), Boolean.valueOf(preferFrameRateOverImageQuality)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setVideoSource(IVideoSource videoSource) {
        if (videoSource == null) {
            this.mVideoSourceType = 0;
        } else if (videoSource instanceof AgoraDefaultSource) {
            this.mVideoSourceType = 1;
        } else {
            this.mVideoSourceType = 2;
        }
        return nativeAddVideoCapturer(this.mNativeHandle, videoSource, this.mVideoSourceType);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int setVolumeOfEffect(int soundId, double volume) {
        return setParameterObject("che.audio.game_adjust_effect_volume", formatString("{\"soundId\":%d,\"gain\":%f}", Integer.valueOf(soundId), Double.valueOf(volume)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int setupLocalVideo(VideoCanvas local) {
        checkIfInUIThread("setupLocalVideo");
        if (this.mVideoSourceType == 3) {
            return -1;
        }
        if (local != null) {
            this.mUseLocalView = true;
            nativeSetupVideoLocal(this.mNativeHandle, local.view, local.renderMode);
        } else {
            this.mUseLocalView = false;
            nativeSetupVideoLocal(this.mNativeHandle, null, 1);
        }
        return 0;
    }

    @Override // io.agora.rtc.RtcEngine
    public int setupRemoteVideo(VideoCanvas remote) {
        checkIfInUIThread("setupRemoteVideo");
        if (remote == null) {
            return -1;
        }
        String str = remote.channelId;
        return str != null ? nativeSetupVideoRemote(this.mNativeHandle, remote.view, remote.renderMode, str, remote.uid) : nativeSetupVideoRemote(this.mNativeHandle, remote.view, remote.renderMode, "", remote.uid);
    }

    @Override // io.agora.rtc.RtcEngine
    public int startAudioMixing(String filePath, boolean loopback, boolean replace, int cycle) {
        return setParameterObject("che.audio.start_file_as_playout", formatString("{\"filePath\":\"%s\", \"loopback\":%b, \"replace\":%b, \"cycle\":%d}", filePath, Boolean.valueOf(loopback), Boolean.valueOf(replace), Integer.valueOf(cycle)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int startAudioRecording(String filePath, int quality) {
        if (TextUtils.isEmpty(filePath)) {
            return -2;
        }
        return setParameterObject("che.audio.start_recording", formatString("{\"filePath\":\"%s\", \"quality\":%d}", filePath, Integer.valueOf(quality)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int startChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration) {
        if (channelMediaRelayConfiguration == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() == 0 || channelMediaRelayConfiguration.getSrcChannelMediaInfo() == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() > 4) {
            return -2;
        }
        for (Map.Entry<String, ChannelMediaInfo> entry : channelMediaRelayConfiguration.getDestChannelMediaInfos().entrySet()) {
            if (entry.getValue().channelName == null || entry.getValue().channelName.length() == 0) {
                return -2;
            }
        }
        return nativeStartChannelMediaRelay(this.mNativeHandle, new RtcEngineMessage.PChannelMediaRelayConfiguration().marshall(channelMediaRelayConfiguration));
    }

    @Override // io.agora.rtc.RtcEngine
    public int startEchoTest() {
        Context context = this.mContext.get();
        if (context == null) {
            return -7;
        }
        doMonitorSystemEvent(context);
        return nativeStartEchoTest(this.mNativeHandle, null);
    }

    @Override // io.agora.rtc.RtcEngine
    public int startLastmileProbeTest(LastmileProbeConfig config) {
        Context context = this.mContext.get();
        if (context == null) {
            return -7;
        }
        doMonitorSystemEvent(context);
        return nativeStartLastmileProbeTest(this.mNativeHandle, null, config.probeUplink, config.probeDownlink, config.expectedUplinkBitrate, config.expectedDownlinkBitrate);
    }

    @Override // io.agora.rtc.RtcEngine
    public int startPreview() {
        if (this.mVideoSourceType == 3) {
            return -4;
        }
        return nativeStartPreview(this.mNativeHandle);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int startRhythmPlayer(String str, String str2, AgoraRhythmPlayerConfig agoraRhythmPlayerConfig) {
        return setParameterObject("che.audio.play_rhythm", formatString("{\"file1\":\"%s\",\"file2\":\"%s\",\"beatsPerMeasure\":%d, \"beatsPerMinute\":%d,\"publish\":%d}", str, str2, Integer.valueOf(agoraRhythmPlayerConfig.beatsPerMeasure), Integer.valueOf(agoraRhythmPlayerConfig.beatsPerMinute), Integer.valueOf(agoraRhythmPlayerConfig.publish ? 1 : 0)));
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int stopAllEffects() {
        return setParameter("che.audio.game_stop_all_effects", true);
    }

    public int stopAllRemoteVideo() {
        return setParameter("che.video.peer.stop_all_renders", true);
    }

    @Override // io.agora.rtc.RtcEngine
    public int stopAudioMixing() {
        return setParameter("che.audio.stop_file_as_playout", true);
    }

    @Override // io.agora.rtc.RtcEngine
    public int stopAudioRecording() {
        return nativeStopAudioRecording(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public int stopChannelMediaRelay() {
        return nativeStopChannelMediaRelay(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public int stopEchoTest() {
        return nativeStopEchoTest(this.mNativeHandle);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int stopEffect(int soundId) {
        return setParameter("che.audio.game_stop_effect", soundId);
    }

    @Override // io.agora.rtc.RtcEngine
    public int stopLastmileProbeTest() {
        return nativeStopLastmileProbeTest(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public int stopPreview() {
        return setParameter("rtc.video.preview", false);
    }

    public int stopRemoteVideo(int uid) {
        return setParameter("che.video.peer.stop_video", uid & InternalZipConstants.ZIP_64_LIMIT);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int stopRhythmPlayer() {
        return setParameter("che.audio.stop_rhythm", true);
    }

    @Override // io.agora.rtc.RtcEngine
    public int switchCamera() {
        if (this.mVideoSourceType != 1) {
            return -1;
        }
        return nativeSwitchCamera(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcEngine
    public int switchChannel(String key, String channelName) {
        return nativeSwitchChannel(this.mNativeHandle, key, channelName);
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int unloadEffect(int soundId) {
        return setParameter("che.audio.game_unload_effect", soundId);
    }

    @Override // io.agora.rtc.RtcEngine
    public int updateChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration) {
        if (channelMediaRelayConfiguration == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() == 0 || channelMediaRelayConfiguration.getSrcChannelMediaInfo() == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() > 4) {
            return -2;
        }
        for (Map.Entry<String, ChannelMediaInfo> entry : channelMediaRelayConfiguration.getDestChannelMediaInfos().entrySet()) {
            if (entry.getValue().channelName == null || entry.getValue().channelName.length() == 0) {
                return -2;
            }
        }
        return nativeUpdateChannelMediaRelay(this.mNativeHandle, new RtcEngineMessage.PChannelMediaRelayConfiguration().marshall(channelMediaRelayConfiguration));
    }

    public synchronized void updateRtcStats(RtcEngineMessage.PMediaResRtcStats stats) {
        IRtcEngineEventHandler.RtcStats rtcStats = getRtcStats();
        if (rtcStats == null) {
            return;
        }
        rtcStats.totalDuration = stats.totalDuration;
        rtcStats.txBytes = stats.totalTxBytes;
        rtcStats.rxBytes = stats.totalRxBytes;
        rtcStats.txAudioBytes = stats.txAudioBytes;
        rtcStats.txVideoBytes = stats.txVideoBytes;
        rtcStats.rxAudioBytes = stats.rxAudioBytes;
        rtcStats.rxVideoBytes = stats.rxVideoBytes;
        rtcStats.txKBitRate = stats.txKBitRate;
        rtcStats.rxKBitRate = stats.rxKBitRate;
        rtcStats.txAudioKBitRate = stats.txAudioKBitRate;
        rtcStats.rxAudioKBitRate = stats.rxAudioKBitRate;
        rtcStats.txVideoKBitRate = stats.txVideoKBitRate;
        rtcStats.rxVideoKBitRate = stats.rxVideoKBitRate;
        rtcStats.lastmileDelay = stats.lastmileDelay;
        rtcStats.txPacketLossRate = stats.txPacketLossRate;
        rtcStats.rxPacketLossRate = stats.rxPacketLossRate;
        rtcStats.users = stats.users;
        rtcStats.cpuTotalUsage = stats.cpuTotalUsage / 100.0d;
        rtcStats.cpuAppUsage = stats.cpuAppUsage / 100.0d;
        rtcStats.gatewayRtt = stats.gatewayRtt;
        rtcStats.memoryAppUsageRatio = stats.memoryAppUsageRatio;
        rtcStats.memoryTotalUsageRatio = stats.memoryTotalUsageRatio;
        rtcStats.memoryAppUsageInKbytes = stats.memoryAppUsageInKbytes;
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int updateSharedContext(EGLContext sharedContext) {
        return nativeSetEGL10Context(this.mNativeHandle, sharedContext);
    }

    @Override // io.agora.rtc.RtcEngine
    public int useExternalAudioDevice() {
        return setParameters("{\"che.audio.audioSampleRate\":32000, \"che.audio.external_device\":true}");
    }

    private int setParameter(String key, int value) {
        return setParameters(formatString("{\"%s\":%d}", key, Integer.valueOf(value)));
    }

    @Override // io.agora.rtc.IAudioEffectManager
    public int playEffect(int i2, String str, int i3, double d3, double d4, double d5, boolean z2, int i4) {
        return setParameterObject("che.audio.game_play_effect", formatString("{\"soundId\":%d,\"filePath\":\"%s\",\"loopCount\":%d, \"pitch\":%f,\"pan\":%f,\"gain\":%f, \"send2far\":%d, \"startPos\":%d}", Integer.valueOf(i2), str, Integer.valueOf(i3), Double.valueOf(d3), Double.valueOf(d4), Double.valueOf(d5), Integer.valueOf(z2 ? 1 : 0), Integer.valueOf(i4)));
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int setTextureId(int id, android.opengl.EGLContext sharedContext, int width, int height, long ts) {
        return nativeSetEGL14TextureId(this.mNativeHandle, id, sharedContext, 11, width, height, ts, sMatrix);
    }

    @Override // io.agora.rtc.RtcEngine
    public int setVideoProfile(int width, int height, int frameRate, int bitrate) {
        return nativeSetVideoProfileEx(this.mNativeHandle, width, height, frameRate, bitrate);
    }

    @Override // io.agora.rtc.RtcEngineEx
    public int updateSharedContext(android.opengl.EGLContext sharedContext) {
        return nativeSetEGL14Context(this.mNativeHandle, sharedContext);
    }

    private int setParameter(String key, long value) {
        return setParameters(formatString("{\"%s\":%d}", key, Long.valueOf(value)));
    }

    @Override // io.agora.rtc.RtcEngine
    public int startAudioRecording(AudioRecordingConfiguration config) {
        return nativeStartAudioRecording(this.mNativeHandle, config.filePath, config.recordingQuality, config.recordingPosition);
    }

    private int setParameter(String key, double value) {
        return setParameters(formatString("{\"%s\":%f}", key, Double.valueOf(value)));
    }

    public int setTextureIdWithMatrix(int id, android.opengl.EGLContext sharedContext, int format, int width, int height, long ts, float[] matrix) {
        if (matrix == null) {
            return nativeSetEGL14TextureId(this.mNativeHandle, id, sharedContext, format, width, height, ts, sMatrix);
        }
        if (matrix.length < 16) {
            return -2;
        }
        return nativeSetEGL14TextureId(this.mNativeHandle, id, sharedContext, format, width, height, ts, matrix);
    }

    @Override // io.agora.rtc.RtcEngine
    public int startEchoTest(int intervalInSeconds) {
        Context context = this.mContext.get();
        if (context == null) {
            return -7;
        }
        doMonitorSystemEvent(context);
        return nativeStartEchoTestWithInterval(this.mNativeHandle, null, intervalInSeconds);
    }

    private int setParameter(String key, String value) {
        return setParameters(formatString("{\"%s\":\"%s\"}", key, value));
    }
}
