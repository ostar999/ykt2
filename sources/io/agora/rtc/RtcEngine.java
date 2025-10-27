package io.agora.rtc;

import android.content.Context;
import android.view.SurfaceView;
import io.agora.rtc.audio.AudioRecordingConfiguration;
import io.agora.rtc.internal.DeviceUtils;
import io.agora.rtc.internal.LastmileProbeConfig;
import io.agora.rtc.internal.RtcEngineImpl;
import io.agora.rtc.live.LiveInjectStreamConfig;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.mediaio.IVideoSink;
import io.agora.rtc.mediaio.IVideoSource;
import io.agora.rtc.models.UserInfo;
import io.agora.rtc.video.AgoraImage;
import io.agora.rtc.video.AgoraVideoFrame;
import io.agora.rtc.video.BeautyOptions;
import io.agora.rtc.video.CameraCapturerConfiguration;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;
import io.agora.rtc.video.ViEAndroidGLES20;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

/* loaded from: classes8.dex */
public abstract class RtcEngine {
    private static RtcEngineImpl mInstance;

    public static SurfaceView CreateRendererView(Context context) {
        RtcEngineImpl.checkIfInUIThread("CreateRendererView");
        SurfaceView viEAndroidGLES20 = ViEAndroidGLES20.IsSupported(context) ? new ViEAndroidGLES20(context) : new SurfaceView(context);
        viEAndroidGLES20.setVisibility(0);
        return viEAndroidGLES20;
    }

    public static synchronized RtcEngine create(Context context, String appId, IRtcEngineEventHandler handler) throws Exception {
        if (context != null) {
            if (RtcEngineImpl.initializeNativeLibs()) {
                RtcEngineImpl rtcEngineImpl = mInstance;
                if (rtcEngineImpl == null) {
                    mInstance = new RtcEngineImpl(context, appId, handler);
                } else {
                    rtcEngineImpl.reinitialize(context, appId, handler);
                }
                return mInstance;
            }
        }
        return null;
    }

    public static synchronized void destroy() {
        RtcEngineImpl rtcEngineImpl = mInstance;
        if (rtcEngineImpl == null) {
            return;
        }
        rtcEngineImpl.doDestroy();
        mInstance = null;
        System.gc();
    }

    public static String getErrorDescription(int error) {
        return !RtcEngineImpl.initializeNativeLibs() ? "" : RtcEngineImpl.nativeGetErrorDescription(error);
    }

    @Deprecated
    public static String getMediaEngineVersion() {
        return !RtcEngineImpl.initializeNativeLibs() ? "" : RtcEngineImpl.nativeGetChatEngineVersion();
    }

    @Deprecated
    public static int getRecommendedEncoderType() {
        return DeviceUtils.getRecommendedEncoderType();
    }

    public static String getSdkVersion() {
        return !RtcEngineImpl.initializeNativeLibs() ? "" : RtcEngineImpl.nativeGetSdkVersion();
    }

    public void addHandler(IRtcEngineEventHandler handler) {
        mInstance.addHandler(handler);
    }

    public abstract int addInjectStreamUrl(String url, LiveInjectStreamConfig config);

    public abstract int addPublishStreamUrl(String url, boolean transcodingEnabled);

    public abstract int addVideoWatermark(AgoraImage watermark);

    public abstract int adjustAudioMixingPlayoutVolume(int volume);

    public abstract int adjustAudioMixingPublishVolume(int volume);

    public abstract int adjustAudioMixingVolume(int volume);

    public abstract int adjustPlaybackSignalVolume(int volume);

    public abstract int adjustRecordingSignalVolume(int volume);

    public abstract int clearVideoWatermarks();

    public abstract int complain(String callId, String description);

    public abstract int createDataStream(boolean reliable, boolean ordered);

    public abstract RtcChannel createRtcChannel(String channelId);

    public abstract int disableAudio();

    public abstract int disableLastmileTest();

    public abstract int disableVideo();

    public abstract int enableAudio();

    @Deprecated
    public abstract int enableAudioQualityIndication(boolean enabled);

    public abstract int enableAudioVolumeIndication(int interval, int smooth, boolean report_vad);

    public abstract int enableDualStreamMode(boolean enabled);

    @Deprecated
    public abstract boolean enableHighPerfWifiMode(boolean enable);

    public abstract int enableInEarMonitoring(boolean enabled);

    public abstract int enableLastmileTest();

    public abstract int enableLocalAudio(boolean enabled);

    public abstract int enableLocalVideo(boolean enabled);

    public abstract int enableSoundPositionIndication(boolean enabled);

    public abstract int enableVideo();

    public abstract int enableWebSdkInteroperability(boolean enabled);

    public abstract IAudioEffectManager getAudioEffectManager();

    public abstract int getAudioMixingCurrentPosition();

    public abstract int getAudioMixingDuration();

    public abstract int getAudioMixingPlayoutVolume();

    public abstract int getAudioMixingPublishVolume();

    public abstract String getCallId();

    public abstract float getCameraMaxZoomFactor();

    public abstract int getConnectionState();

    public abstract long getNativeHandle();

    public abstract String getParameter(String parameter, String args);

    public abstract int getUserInfoByUid(int uid, UserInfo userInfo);

    public abstract int getUserInfoByUserAccount(String userAccount, UserInfo userInfo);

    public abstract boolean isCameraAutoFocusFaceModeSupported();

    public abstract boolean isCameraExposurePositionSupported();

    public abstract boolean isCameraFocusSupported();

    public abstract boolean isCameraTorchSupported();

    public abstract boolean isCameraZoomSupported();

    public abstract boolean isSpeakerphoneEnabled();

    public abstract boolean isTextureEncodeSupported();

    public abstract int joinChannel(String token, String channelName, String optionalInfo, int optionalUid);

    public abstract int joinChannelWithUserAccount(String token, String channelName, String userAccount);

    public abstract int leaveChannel();

    @Deprecated
    public abstract void monitorBluetoothHeadsetEvent(boolean monitor);

    @Deprecated
    public abstract void monitorHeadsetEvent(boolean monitor);

    public abstract int muteAllRemoteAudioStreams(boolean muted);

    public abstract int muteAllRemoteVideoStreams(boolean muted);

    public abstract int muteLocalAudioStream(boolean muted);

    public abstract int muteLocalVideoStream(boolean muted);

    public abstract int muteRemoteAudioStream(int uid, boolean muted);

    public abstract int muteRemoteVideoStream(int uid, boolean muted);

    @Deprecated
    public abstract int pauseAudio();

    public abstract int pauseAudioMixing();

    public abstract int pullPlaybackAudioFrame(byte[] data, int lengthInByte);

    public abstract int pushExternalAudioFrame(byte[] data, long timestamp);

    public abstract boolean pushExternalVideoFrame(AgoraVideoFrame frame);

    public abstract int rate(String callId, int rating, String description);

    public abstract int registerAudioFrameObserver(IAudioFrameObserver observer);

    public abstract int registerLocalUserAccount(String appId, String userAccount);

    public abstract int registerMediaMetadataObserver(IMetadataObserver observer, int type);

    public abstract int releaseLogWriter();

    public void removeHandler(IRtcEngineEventHandler handler) {
        mInstance.removeHandler(handler);
    }

    public abstract int removeInjectStreamUrl(String url);

    public abstract int removePublishStreamUrl(String url);

    public abstract int renewToken(String token);

    @Deprecated
    public abstract int resumeAudio();

    public abstract int resumeAudioMixing();

    public abstract int sendCustomReportMessage(String id, String category, String event, String label, int value);

    public abstract int sendStreamMessage(int streamId, byte[] message);

    public abstract int setAudioMixingPosition(int pos);

    public abstract int setAudioProfile(int profile, int scenario);

    public abstract int setBeautyEffectOptions(boolean enabled, BeautyOptions options);

    public abstract int setCameraAutoFocusFaceModeEnabled(boolean enabled);

    public abstract int setCameraCapturerConfiguration(CameraCapturerConfiguration config);

    public abstract int setCameraExposurePosition(float positionXinView, float positionYinView);

    public abstract int setCameraFocusPositionInPreview(float positionX, float positionY);

    public abstract int setCameraTorchOn(boolean isOn);

    public abstract int setCameraZoomFactor(float factor);

    public abstract int setChannelProfile(int profile);

    public abstract int setClientRole(int role);

    public abstract int setDefaultAudioRoutetoSpeakerphone(boolean defaultToSpeaker);

    public abstract int setDefaultMuteAllRemoteAudioStreams(boolean muted);

    public abstract int setDefaultMuteAllRemoteVideoStreams(boolean muted);

    public abstract int setEnableSpeakerphone(boolean enabled);

    public abstract int setEncryptionMode(String encryptionMode);

    public abstract int setEncryptionSecret(String secret);

    public abstract int setExternalAudioSink(boolean enabled, int sampleRate, int channels);

    public abstract int setExternalAudioSource(boolean enabled, int sampleRate, int channels);

    public abstract void setExternalVideoSource(boolean enable, boolean useTexture, boolean pushMode);

    @Deprecated
    public abstract int setHighQualityAudioParameters(boolean fullband, boolean stereo, boolean fullBitrate);

    public abstract int setInEarMonitoringVolume(int volume);

    public abstract int setLiveTranscoding(LiveTranscoding transcoding);

    public abstract int setLocalPublishFallbackOption(int option);

    public abstract int setLocalRenderMode(int mode);

    public abstract int setLocalVideoMirrorMode(int mode);

    public abstract int setLocalVideoRenderer(IVideoSink render);

    public abstract int setLocalVoiceChanger(int voiceChanger);

    public abstract int setLocalVoiceEqualization(int bandFrequency, int bandGain);

    public abstract int setLocalVoicePitch(double pitch);

    public abstract int setLocalVoiceReverb(int reverbKey, int value);

    public abstract int setLocalVoiceReverbPreset(int preset);

    public abstract int setLogFile(String filePath);

    public abstract int setLogFileSize(int fileSizeInKBytes);

    public abstract int setLogFilter(int filter);

    public abstract int setLogWriter(ILogWriter logWriter);

    public abstract int setMixedAudioFrameParameters(int sampleRate, int samplesPerCall);

    public abstract int setParameters(String parameters);

    public abstract int setPlaybackAudioFrameParameters(int sampleRate, int channel, int mode, int samplesPerCall);

    @Deprecated
    public abstract void setPreferHeadset(boolean enabled);

    public abstract int setRecordingAudioFrameParameters(int sampleRate, int channel, int mode, int samplesPerCall);

    public abstract int setRemoteDefaultVideoStreamType(int streamType);

    public abstract int setRemoteRenderMode(int uid, int mode);

    public abstract int setRemoteSubscribeFallbackOption(int option);

    public abstract int setRemoteUserPriority(int uid, int userPriority);

    public abstract int setRemoteVideoRenderer(int uid, IVideoSink render);

    public abstract int setRemoteVideoStreamType(int uid, int streamType);

    public abstract int setRemoteVoicePosition(int uid, double pan, double gain);

    public abstract int setVideoEncoderConfiguration(VideoEncoderConfiguration config);

    @Deprecated
    public abstract int setVideoProfile(int width, int height, int frameRate, int bitrate);

    @Deprecated
    public abstract int setVideoProfile(int profile, boolean swapWidthAndHeight);

    @Deprecated
    public abstract int setVideoQualityParameters(boolean preferFrameRateOverImageQuality);

    public abstract int setVideoSource(IVideoSource source);

    public abstract int setupLocalVideo(VideoCanvas local);

    public abstract int setupRemoteVideo(VideoCanvas remote);

    public abstract int startAudioMixing(String filePath, boolean loopback, boolean replace, int cycle);

    public abstract int startAudioRecording(AudioRecordingConfiguration config);

    public abstract int startAudioRecording(String filePath, int quality);

    public abstract int startChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration);

    @Deprecated
    public abstract int startEchoTest();

    public abstract int startEchoTest(int intervalInSeconds);

    public abstract int startLastmileProbeTest(LastmileProbeConfig config);

    public abstract int startPreview();

    public abstract int stopAudioMixing();

    public abstract int stopAudioRecording();

    public abstract int stopChannelMediaRelay();

    public abstract int stopEchoTest();

    public abstract int stopLastmileProbeTest();

    public abstract int stopPreview();

    public abstract int switchCamera();

    public abstract int switchChannel(String token, String channelName);

    public abstract int updateChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration);

    @Deprecated
    public abstract int useExternalAudioDevice();
}
