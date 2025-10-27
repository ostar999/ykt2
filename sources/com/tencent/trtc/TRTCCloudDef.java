package com.tencent.trtc;

import android.view.View;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class TRTCCloudDef {
    public static final int TRTCAVStatusChangeReasonBufferingBegin = 1;
    public static final int TRTCAVStatusChangeReasonBufferingEnd = 2;
    public static final int TRTCAVStatusChangeReasonInternal = 0;
    public static final int TRTCAVStatusChangeReasonLocalStarted = 3;
    public static final int TRTCAVStatusChangeReasonLocalStopped = 4;
    public static final int TRTCAVStatusChangeReasonRemoteStarted = 5;
    public static final int TRTCAVStatusChangeReasonRemoteStopped = 6;
    public static final int TRTCAVStatusLoading = 2;
    public static final int TRTCAVStatusPlaying = 1;
    public static final int TRTCAVStatusStopped = 0;
    public static final int TRTCAudioCapabilityLowLatencyChorus = 1;
    public static final int TRTCAudioCapabilityLowLatencyEarMonitor = 2;
    public static final int TRTCAudioSampleRate16000 = 16000;
    public static final int TRTCAudioSampleRate32000 = 32000;
    public static final int TRTCAudioSampleRate44100 = 44100;
    public static final int TRTCAudioSampleRate48000 = 48000;
    public static final int TRTCRoleAnchor = 20;
    public static final int TRTCRoleAudience = 21;
    public static final int TRTCSystemVolumeTypeAuto = 0;
    public static final int TRTCSystemVolumeTypeMedia = 1;
    public static final int TRTCSystemVolumeTypeVOIP = 2;
    public static final int TRTC_APP_SCENE_AUDIOCALL = 2;
    public static final int TRTC_APP_SCENE_LIVE = 1;
    public static final int TRTC_APP_SCENE_VIDEOCALL = 0;
    public static final int TRTC_APP_SCENE_VOICE_CHATROOM = 3;
    public static final int TRTC_AUDIO_FRAME_FORMAT_PCM = 1;
    public static final int TRTC_AUDIO_QUALITY_DEFAULT = 2;
    public static final int TRTC_AUDIO_QUALITY_MUSIC = 3;
    public static final int TRTC_AUDIO_QUALITY_SPEECH = 1;
    public static final int TRTC_AUDIO_ROUTE_EARPIECE = 1;
    public static final int TRTC_AUDIO_ROUTE_SPEAKER = 0;
    public static final int TRTC_AudioRecordingContent_All = 0;
    public static final int TRTC_AudioRecordingContent_Local = 1;
    public static final int TRTC_AudioRecordingContent_Remote = 2;
    public static final int TRTC_BEAUTY_STYLE_NATURE = 1;
    public static final int TRTC_BEAUTY_STYLE_PITU = 2;
    public static final int TRTC_BEAUTY_STYLE_SMOOTH = 0;
    public static final int TRTC_DEBUG_VIEW_LEVEL_ALL = 2;
    public static final int TRTC_DEBUG_VIEW_LEVEL_GONE = 0;
    public static final int TRTC_DEBUG_VIEW_LEVEL_STATUS = 1;
    public static final int TRTC_GSENSOR_MODE_DISABLE = 0;
    public static final int TRTC_GSENSOR_MODE_UIAUTOLAYOUT = 1;
    public static final int TRTC_GSENSOR_MODE_UIFIXLAYOUT = 2;
    public static final int TRTC_LOG_LEVEL_DEBUG = 1;
    public static final int TRTC_LOG_LEVEL_ERROR = 4;
    public static final int TRTC_LOG_LEVEL_FATAL = 5;
    public static final int TRTC_LOG_LEVEL_INFO = 2;
    public static final int TRTC_LOG_LEVEL_NULL = 6;
    public static final int TRTC_LOG_LEVEL_VERBOSE = 0;
    public static final int TRTC_LOG_LEVEL_WARN = 3;
    public static final int TRTC_MixInputType_AudioVideo = 1;
    public static final int TRTC_MixInputType_PureAudio = 3;
    public static final int TRTC_MixInputType_PureVideo = 2;
    public static final int TRTC_MixInputType_Undefined = 0;
    public static final int TRTC_QUALITY_Bad = 4;
    public static final int TRTC_QUALITY_Down = 6;
    public static final int TRTC_QUALITY_Excellent = 1;
    public static final int TRTC_QUALITY_Good = 2;
    public static final int TRTC_QUALITY_Poor = 3;
    public static final int TRTC_QUALITY_UNKNOWN = 0;
    public static final int TRTC_QUALITY_Vbad = 5;
    public static final int TRTC_RECORD_TYPE_AUDIO = 0;
    public static final int TRTC_RECORD_TYPE_BOTH = 2;
    public static final int TRTC_RECORD_TYPE_VIDEO = 1;
    public static final int TRTC_REVERB_TYPE_0 = 0;
    public static final int TRTC_REVERB_TYPE_1 = 1;
    public static final int TRTC_REVERB_TYPE_2 = 2;
    public static final int TRTC_REVERB_TYPE_3 = 3;
    public static final int TRTC_REVERB_TYPE_4 = 4;
    public static final int TRTC_REVERB_TYPE_5 = 5;
    public static final int TRTC_REVERB_TYPE_6 = 6;
    public static final int TRTC_REVERB_TYPE_7 = 7;
    public static final String TRTC_SDK_VERSION = "9.3.10768";
    public static final int TRTC_TranscodingConfigMode_Manual = 1;
    public static final int TRTC_TranscodingConfigMode_Template_PresetLayout = 3;
    public static final int TRTC_TranscodingConfigMode_Template_PureAudio = 2;
    public static final int TRTC_TranscodingConfigMode_Template_ScreenSharing = 4;
    public static final int TRTC_TranscodingConfigMode_Unknown = 0;
    public static final int TRTC_VIDEO_BUFFER_TYPE_BYTE_ARRAY = 2;
    public static final int TRTC_VIDEO_BUFFER_TYPE_BYTE_BUFFER = 1;
    public static final int TRTC_VIDEO_BUFFER_TYPE_TEXTURE = 3;
    public static final int TRTC_VIDEO_BUFFER_TYPE_UNKNOWN = 0;
    public static final int TRTC_VIDEO_MIRROR_TYPE_AUTO = 0;
    public static final int TRTC_VIDEO_MIRROR_TYPE_DISABLE = 2;
    public static final int TRTC_VIDEO_MIRROR_TYPE_ENABLE = 1;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_I420 = 1;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_NV21 = 4;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_RGBA = 5;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_TEXTURE_EXTERNAL_OES = 3;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_Texture_2D = 2;
    public static final int TRTC_VIDEO_PIXEL_FORMAT_UNKNOWN = 0;
    public static final int TRTC_VIDEO_QOS_PREFERENCE_CLEAR = 2;
    public static final int TRTC_VIDEO_QOS_PREFERENCE_SMOOTH = 1;
    public static final int TRTC_VIDEO_RENDER_MODE_FILL = 0;
    public static final int TRTC_VIDEO_RENDER_MODE_FIT = 1;
    public static final int TRTC_VIDEO_RESOLUTION_120_120 = 1;
    public static final int TRTC_VIDEO_RESOLUTION_1280_720 = 112;
    public static final int TRTC_VIDEO_RESOLUTION_160_120 = 50;
    public static final int TRTC_VIDEO_RESOLUTION_160_160 = 3;
    public static final int TRTC_VIDEO_RESOLUTION_160_90 = 100;
    public static final int TRTC_VIDEO_RESOLUTION_1920_1080 = 114;
    public static final int TRTC_VIDEO_RESOLUTION_240_180 = 52;
    public static final int TRTC_VIDEO_RESOLUTION_256_144 = 102;
    public static final int TRTC_VIDEO_RESOLUTION_270_270 = 5;
    public static final int TRTC_VIDEO_RESOLUTION_280_210 = 54;
    public static final int TRTC_VIDEO_RESOLUTION_320_180 = 104;
    public static final int TRTC_VIDEO_RESOLUTION_320_240 = 56;
    public static final int TRTC_VIDEO_RESOLUTION_400_300 = 58;
    public static final int TRTC_VIDEO_RESOLUTION_480_270 = 106;
    public static final int TRTC_VIDEO_RESOLUTION_480_360 = 60;
    public static final int TRTC_VIDEO_RESOLUTION_480_480 = 7;
    public static final int TRTC_VIDEO_RESOLUTION_640_360 = 108;
    public static final int TRTC_VIDEO_RESOLUTION_640_480 = 62;
    public static final int TRTC_VIDEO_RESOLUTION_960_540 = 110;
    public static final int TRTC_VIDEO_RESOLUTION_960_720 = 64;
    public static final int TRTC_VIDEO_RESOLUTION_MODE_LANDSCAPE = 0;
    public static final int TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT = 1;
    public static final int TRTC_VIDEO_ROTATION_0 = 0;
    public static final int TRTC_VIDEO_ROTATION_180 = 2;
    public static final int TRTC_VIDEO_ROTATION_270 = 3;
    public static final int TRTC_VIDEO_ROTATION_90 = 1;
    public static final int TRTC_VIDEO_STREAM_TYPE_BIG = 0;
    public static final int TRTC_VIDEO_STREAM_TYPE_SMALL = 1;
    public static final int TRTC_VIDEO_STREAM_TYPE_SUB = 2;
    public static final int TRTC_VOICE_CHANGER_TYPE_0 = 0;
    public static final int TRTC_VOICE_CHANGER_TYPE_1 = 1;
    public static final int TRTC_VOICE_CHANGER_TYPE_10 = 10;
    public static final int TRTC_VOICE_CHANGER_TYPE_11 = 11;
    public static final int TRTC_VOICE_CHANGER_TYPE_2 = 2;
    public static final int TRTC_VOICE_CHANGER_TYPE_3 = 3;
    public static final int TRTC_VOICE_CHANGER_TYPE_4 = 4;
    public static final int TRTC_VOICE_CHANGER_TYPE_5 = 5;
    public static final int TRTC_VOICE_CHANGER_TYPE_6 = 6;
    public static final int TRTC_VOICE_CHANGER_TYPE_7 = 7;
    public static final int TRTC_VOICE_CHANGER_TYPE_8 = 8;
    public static final int TRTC_VOICE_CHANGER_TYPE_9 = 9;
    public static final int VIDEO_QOS_CONTROL_CLIENT = 0;
    public static final int VIDEO_QOS_CONTROL_SERVER = 1;

    public static class TRTCAudioEffectParam {
        public int effectId;
        public String path;
        public int loopCount = 0;
        public boolean publish = false;
        public int volume = 100;

        public TRTCAudioEffectParam(int i2, String str) {
            this.path = str;
            this.effectId = i2;
        }
    }

    public static class TRTCAudioFrame {
        public int channel;
        public byte[] data;
        public byte[] extraData;
        public int sampleRate;
        public long timestamp;
    }

    public static class TRTCAudioFrameCallbackFormat {
        public int sampleRate = 0;
        public int channel = 0;
        public int samplesPerCall = 0;
    }

    public static class TRTCAudioRecordingParams {
        public String filePath = "";
        public int recordingContent = 0;
    }

    public static class TRTCLocalRecordingParams {
        public String filePath = "";
        public int recordType = 2;
        public int interval = -1;

        public String toString() {
            return "filePath=" + this.filePath + ", recordType=" + this.recordType + ", interval=" + this.interval;
        }
    }

    public static class TRTCNetworkQosParam {
        public int controlMode;
        public int preference;
    }

    public static class TRTCPublishCDNParam {
        public int appId;
        public int bizId;
        public String url;
    }

    public static class TRTCQuality {
        public int quality;
        public String userId;
    }

    public static class TRTCRenderParams {
        public int fillMode;
        public int mirrorType;
        public int rotation;
    }

    public static class TRTCScreenShareParams {
        public View floatingView;
    }

    public static class TRTCSpeedTestParams {
        public int expectedDownBandwidth;
        public int expectedUpBandwidth;
        public int sdkAppId;
        public String userId;
        public String userSig;

        public String toString() {
            return "sdkAppId:" + this.sdkAppId + " userId:" + this.userId + " expectedUpBandwidth:" + this.expectedDownBandwidth + " expectedDownBandwidth:" + this.expectedDownBandwidth;
        }
    }

    public static class TRTCSpeedTestResult {
        public int availableDownBandwidth;
        public int availableUpBandwidth;
        public float downLostRate;
        public String errMsg;
        public String ip;
        public int quality;
        public int rtt;
        public boolean success;
        public float upLostRate;

        public String toString() {
            return "success:" + this.success + " errMsg:" + this.errMsg + " ip:" + this.ip + " quality:" + this.quality + " upLostRate:" + this.upLostRate + " downLostRate:" + this.downLostRate + " rtt:" + this.rtt + " availableUpBandwidth:" + this.availableUpBandwidth + " availableDownBandwidth:" + this.availableDownBandwidth;
        }
    }

    public static class TRTCSwitchRoomConfig {
        public int roomId = 0;
        public String strRoomId = null;
        public String userSig = null;
        public String privateMapKey = null;
    }

    public static class TRTCTexture {
        public EGLContext eglContext10;
        public android.opengl.EGLContext eglContext14;
        public int textureId;
    }

    public static class TRTCVideoEncParam {
        public boolean enableAdjustRes;
        public int minVideoBitrate;
        public int videoBitrate;
        public int videoFps;
        public int videoResolution;
        public int videoResolutionMode;
    }

    public static class TRTCVideoFrame {
        public ByteBuffer buffer;
        public int bufferType;
        public byte[] data;
        public int height;
        public int pixelFormat;
        public int rotation;
        public TRTCTexture texture;
        public long timestamp;
        public int width;
    }

    public static class TRTCVolumeInfo {
        public String userId;
        public int volume;
    }

    public static class TRTCMixUser {
        public int height;
        public int inputType;
        public boolean pureAudio;
        public String roomId;
        public int streamType;
        public String userId;
        public int width;

        /* renamed from: x, reason: collision with root package name */
        public int f22290x;

        /* renamed from: y, reason: collision with root package name */
        public int f22291y;
        public int zOrder;

        public TRTCMixUser() {
            this.userId = "";
            this.f22290x = 0;
            this.f22291y = 0;
            this.width = 0;
            this.height = 0;
            this.zOrder = 0;
            this.streamType = 0;
            this.inputType = 0;
        }

        public String toString() {
            return "userId=" + this.userId + ", roomId=" + this.roomId + ", x=" + this.f22290x + ", y=" + this.f22291y + ", width=" + this.width + ", height=" + this.height + ", zOrder=" + this.zOrder + ", streamType=" + this.streamType + ", pureAudio=" + this.pureAudio + ", inputType=" + this.inputType;
        }

        public TRTCMixUser(String str, int i2, int i3, int i4, int i5, int i6) {
            this.userId = str;
            this.f22290x = i2;
            this.f22291y = i3;
            this.width = i4;
            this.height = i5;
            this.zOrder = i6;
            this.streamType = 0;
            this.inputType = 0;
        }

        public TRTCMixUser(TRTCMixUser tRTCMixUser) {
            this.userId = tRTCMixUser.userId;
            this.roomId = tRTCMixUser.roomId;
            this.f22290x = tRTCMixUser.f22290x;
            this.f22291y = tRTCMixUser.f22291y;
            this.width = tRTCMixUser.width;
            this.height = tRTCMixUser.height;
            this.zOrder = tRTCMixUser.zOrder;
            this.streamType = tRTCMixUser.streamType;
            this.pureAudio = tRTCMixUser.pureAudio;
            this.inputType = tRTCMixUser.inputType;
        }
    }

    public static class TRTCParams {
        public String businessInfo;
        public String privateMapKey;
        public int role;
        public int roomId;
        public int sdkAppId;
        public String strRoomId;
        public String streamId;
        public String userDefineRecordId;
        public String userId;
        public String userSig;

        public TRTCParams() {
            this.sdkAppId = 0;
            this.userId = "";
            this.userSig = "";
            this.roomId = 0;
            this.strRoomId = "";
            this.role = 20;
            this.privateMapKey = "";
            this.businessInfo = "";
        }

        public TRTCParams(int i2, String str, String str2, int i3, String str3, String str4) {
            this.role = 20;
            this.sdkAppId = i2;
            this.userId = str;
            this.userSig = str2;
            this.roomId = i3;
            this.strRoomId = "";
            this.privateMapKey = str3;
            this.businessInfo = str4;
        }

        public TRTCParams(int i2, String str, String str2, String str3, String str4, String str5) {
            this.roomId = 0;
            this.role = 20;
            this.sdkAppId = i2;
            this.userId = str;
            this.userSig = str2;
            this.strRoomId = str3;
            this.privateMapKey = str4;
            this.businessInfo = str5;
        }

        public TRTCParams(TRTCParams tRTCParams) {
            this.sdkAppId = 0;
            this.userId = "";
            this.userSig = "";
            this.roomId = 0;
            this.strRoomId = "";
            this.role = 20;
            this.privateMapKey = "";
            this.businessInfo = "";
            this.sdkAppId = tRTCParams.sdkAppId;
            this.userId = tRTCParams.userId;
            this.userSig = tRTCParams.userSig;
            this.roomId = tRTCParams.roomId;
            this.strRoomId = tRTCParams.strRoomId;
            this.role = tRTCParams.role;
            this.streamId = tRTCParams.streamId;
            this.userDefineRecordId = tRTCParams.userDefineRecordId;
            this.privateMapKey = tRTCParams.privateMapKey;
            this.businessInfo = tRTCParams.businessInfo;
        }
    }

    public static class TRTCTranscodingConfig {
        public int appId;
        public int audioBitrate;
        public int audioChannels;
        public int audioSampleRate;
        public int backgroundColor;
        public String backgroundImage;
        public int bizId;
        public ArrayList<TRTCMixUser> mixUsers;
        public int mode;
        public String streamId;
        public int videoBitrate;
        public int videoFramerate;
        public int videoGOP;
        public int videoHeight;
        public int videoWidth;

        public TRTCTranscodingConfig() {
            this.mode = 0;
            this.videoWidth = 0;
            this.videoHeight = 0;
            this.videoBitrate = 0;
            this.videoFramerate = 15;
            this.videoGOP = 2;
            this.backgroundColor = 0;
            this.audioSampleRate = 48000;
            this.audioBitrate = 64;
            this.audioChannels = 1;
            this.streamId = null;
        }

        public String toString() {
            return "appId=" + this.appId + ", bizId=" + this.bizId + ", mode=" + this.mode + ", videoWidth=" + this.videoWidth + ", videoHeight=" + this.videoHeight + ", videoBitrate=" + this.videoBitrate + ", videoFramerate=" + this.videoFramerate + ", videoGOP=" + this.videoGOP + ", backgroundColor=" + this.backgroundColor + ", backgroundImage=" + this.backgroundImage + ", audioSampleRate=" + this.audioSampleRate + ", audioBitrate=" + this.audioBitrate + ", audioChannels=" + this.audioChannels + ", streamId=" + this.streamId + ", mixUsers=" + this.mixUsers;
        }

        public TRTCTranscodingConfig(TRTCTranscodingConfig tRTCTranscodingConfig) {
            this.appId = tRTCTranscodingConfig.appId;
            this.bizId = tRTCTranscodingConfig.bizId;
            this.mode = tRTCTranscodingConfig.mode;
            this.videoWidth = tRTCTranscodingConfig.videoWidth;
            this.videoHeight = tRTCTranscodingConfig.videoHeight;
            this.videoBitrate = tRTCTranscodingConfig.videoBitrate;
            this.videoFramerate = tRTCTranscodingConfig.videoFramerate;
            this.videoGOP = tRTCTranscodingConfig.videoGOP;
            this.backgroundColor = tRTCTranscodingConfig.backgroundColor;
            this.backgroundImage = tRTCTranscodingConfig.backgroundImage;
            this.audioSampleRate = tRTCTranscodingConfig.audioSampleRate;
            this.audioBitrate = tRTCTranscodingConfig.audioBitrate;
            this.audioChannels = tRTCTranscodingConfig.audioChannels;
            this.streamId = tRTCTranscodingConfig.streamId;
            this.mixUsers = new ArrayList<>(tRTCTranscodingConfig.mixUsers);
        }
    }
}
