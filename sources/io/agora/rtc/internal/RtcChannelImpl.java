package io.agora.rtc.internal;

import io.agora.rtc.IMetadataObserver;
import io.agora.rtc.RtcChannel;
import io.agora.rtc.internal.RtcEngineMessage;
import io.agora.rtc.live.LiveInjectStreamConfig;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.mediaio.AgoraDefaultRender;
import io.agora.rtc.mediaio.IVideoSink;
import io.agora.rtc.models.ChannelMediaOptions;
import io.agora.rtc.video.ChannelMediaInfo;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes8.dex */
public class RtcChannelImpl extends RtcChannel {
    private long mNativeHandle = 0;
    private volatile boolean mInitialized = false;
    private RtcEngineImpl mRtcEngineImpl = null;
    private boolean mJoined = false;

    private native int nativeRtcChannelAddInjectStreamUrl(long nativeHandle, String url, byte[] config);

    private native int nativeRtcChannelAddPublishStreamUrl(long nativeHandle, String url, boolean transcodingEnabled);

    private native int nativeRtcChannelAddRemoteVideoRender(long nativeHandle, int uid, IVideoSink render, int type);

    private native String nativeRtcChannelChannelId(long nativeHandle);

    private native int nativeRtcChannelCreateDataStream(long nativeHandle, boolean reliable, boolean ordered);

    private native String nativeRtcChannelGetCallId(long nativeHandle);

    private native int nativeRtcChannelGetConncetionState(long nativeHandle);

    private native int nativeRtcChannelJoinChannel(long nativeHandle, String token, String info, int uid, Object options);

    private native int nativeRtcChannelJoinChannelWithUserAccount(long nativeHandle, String token, String userAccount, Object options);

    private native int nativeRtcChannelLeaveChannel(long nativeHandle);

    private native int nativeRtcChannelMuteAllRemoteAudioStreams(long nativeHandle, boolean mute);

    private native int nativeRtcChannelMuteAllRemoteVideoStreams(long nativeHandle, boolean mute);

    private native int nativeRtcChannelMuteRemoteAudioStream(long nativeHandle, int uid, boolean mute);

    private native int nativeRtcChannelMuteRemoteVideoStream(long nativehandle, int uid, boolean mute);

    private native int nativeRtcChannelPublish(long nativeHandle);

    private native int nativeRtcChannelRegisterMediaMetadataObserver(long nativeHandle, Object observer, int type);

    private native int nativeRtcChannelRemoveInjectStreamUrl(long nativeHandle, String url);

    private native int nativeRtcChannelRemovePublishStreamUrl(long nativeHandle, String url);

    private native int nativeRtcChannelRenewToken(long nativeHandle, String token);

    private native int nativeRtcChannelSendStreamMessage(long nativeHandle, int streamId, byte[] data);

    private native int nativeRtcChannelSetClientRole(long nativeHandle, int role);

    private native int nativeRtcChannelSetDefaultMuteAllRemoteAudioStreams(long nativeHandle, boolean mute);

    private native int nativeRtcChannelSetDefaultMuteAllRemoteVideoStreams(long nativeHandle, boolean mute);

    private native int nativeRtcChannelSetEncryptionMode(long nativeHandle, String encryptionMode);

    private native int nativeRtcChannelSetEncryptionSecret(long nativeHandle, String secret);

    private native int nativeRtcChannelSetLiveTranscoding(long nativeHandle, byte[] transcoding);

    private native int nativeRtcChannelSetRemoteDefaultVideoStreamType(long nativeHandle, int streamType);

    private native int nativeRtcChannelSetRemoteRenderMode(long nativeHandle, int uid, int renderMode);

    private native int nativeRtcChannelSetRemoteUserPriority(long nativeHandle, int uid, int userPriority);

    private native int nativeRtcChannelSetRemoteVideoStreamType(long nativeHandle, int uid, int streamType);

    private native int nativeRtcChannelSetRemoteVoicePosition(long nativeHandle, int uid, double pan, double gain);

    private native int nativeRtcChannelStartChannelMediaRelay(long nativeHandle, byte[] channelMediaRelayInfos);

    private native int nativeRtcChannelStopChannelMediaRelay(long nativeHandle);

    private native int nativeRtcChannelUnpublish(long nativeHandle);

    private native int nativeRtcChannelUpdateChannelMediaRelay(long nativeHandle, byte[] channelMediaRelayInfos);

    @Override // io.agora.rtc.RtcChannel
    public int addInjectStreamUrl(String url, LiveInjectStreamConfig config) {
        if (!this.mInitialized) {
            return -7;
        }
        if (url == null || config == null) {
            return -2;
        }
        return nativeRtcChannelAddInjectStreamUrl(this.mNativeHandle, url, new RtcEngineMessage.PInjectStreamConfig().marshall(config));
    }

    @Override // io.agora.rtc.RtcChannel
    public int addPublishStreamUrl(String url, boolean transcodingEnabled) {
        if (this.mInitialized) {
            return nativeRtcChannelAddPublishStreamUrl(this.mNativeHandle, url, transcodingEnabled);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public String channelId() {
        return !this.mInitialized ? "" : nativeRtcChannelChannelId(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcChannel
    public int createDataStream(boolean reliable, boolean ordered) {
        if (this.mInitialized) {
            return nativeRtcChannelCreateDataStream(this.mNativeHandle, reliable, ordered);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int destroy() {
        if (!this.mInitialized) {
            return -7;
        }
        this.mInitialized = false;
        return this.mRtcEngineImpl.destroyRtcChannel(channelId());
    }

    @Override // io.agora.rtc.RtcChannel
    public String getCallId() {
        return !this.mInitialized ? "" : nativeRtcChannelGetCallId(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcChannel
    public int getConnectionState() {
        if (this.mInitialized) {
            return nativeRtcChannelGetConncetionState(this.mNativeHandle);
        }
        return 1;
    }

    public long getNativeHandle() {
        return this.mNativeHandle;
    }

    public boolean hasJoined() {
        return this.mJoined;
    }

    public int initialize(RtcEngineImpl rtcEngineImpl, long nativeHandle) {
        this.mRtcEngineImpl = rtcEngineImpl;
        this.mNativeHandle = nativeHandle;
        this.mInitialized = true;
        return 0;
    }

    public boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // io.agora.rtc.RtcChannel
    public int joinChannel(String token, String optionalInfo, int optionalUid, ChannelMediaOptions options) {
        if (!this.mInitialized || this.mRtcEngineImpl.getContext() == null) {
            return -7;
        }
        if (options == null) {
            return -2;
        }
        this.mRtcEngineImpl.onRtcChannelJoinChannel();
        this.mJoined = true;
        return nativeRtcChannelJoinChannel(this.mNativeHandle, token, optionalInfo, optionalUid, options);
    }

    @Override // io.agora.rtc.RtcChannel
    public int joinChannelWithUserAccount(String token, String userAccount, ChannelMediaOptions options) {
        if (!this.mInitialized || this.mRtcEngineImpl.getContext() == null) {
            return -7;
        }
        if (options == null) {
            return -2;
        }
        this.mRtcEngineImpl.onRtcChannelJoinChannel();
        this.mJoined = true;
        return nativeRtcChannelJoinChannelWithUserAccount(this.mNativeHandle, token, userAccount, options);
    }

    @Override // io.agora.rtc.RtcChannel
    public int leaveChannel() {
        if (!this.mInitialized) {
            return -7;
        }
        this.mJoined = false;
        this.mRtcEngineImpl.onRtcChannelLeaveChannel();
        return nativeRtcChannelLeaveChannel(this.mNativeHandle);
    }

    @Override // io.agora.rtc.RtcChannel
    public int muteAllRemoteAudioStreams(boolean muted) {
        if (this.mInitialized) {
            return nativeRtcChannelMuteAllRemoteAudioStreams(this.mNativeHandle, muted);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int muteAllRemoteVideoStreams(boolean muted) {
        if (this.mInitialized) {
            return nativeRtcChannelMuteAllRemoteVideoStreams(this.mNativeHandle, muted);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int muteRemoteAudioStream(int uid, boolean muted) {
        if (this.mInitialized) {
            return nativeRtcChannelMuteRemoteAudioStream(this.mNativeHandle, uid, muted);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int muteRemoteVideoStream(int uid, boolean muted) {
        if (this.mInitialized) {
            return nativeRtcChannelMuteRemoteVideoStream(this.mNativeHandle, uid, muted);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int publish() {
        if (this.mInitialized) {
            return nativeRtcChannelPublish(this.mNativeHandle);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int registerMediaMetadataObserver(IMetadataObserver observer, int type) {
        if (this.mInitialized) {
            return nativeRtcChannelRegisterMediaMetadataObserver(this.mNativeHandle, observer, type);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int removeInjectStreamUrl(String url) {
        if (this.mInitialized) {
            return nativeRtcChannelRemoveInjectStreamUrl(this.mNativeHandle, url);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int removePublishStreamUrl(String url) {
        if (this.mInitialized) {
            return nativeRtcChannelRemovePublishStreamUrl(this.mNativeHandle, url);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int renewToken(String token) {
        if (this.mInitialized) {
            return nativeRtcChannelRenewToken(this.mNativeHandle, token);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int sendStreamMessage(int streamId, byte[] message) {
        if (this.mInitialized) {
            return nativeRtcChannelSendStreamMessage(this.mNativeHandle, streamId, message);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setClientRole(int role) {
        if (this.mInitialized) {
            return nativeRtcChannelSetClientRole(this.mNativeHandle, role);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setDefaultMuteAllRemoteAudioStreams(boolean muted) {
        if (this.mInitialized) {
            return nativeRtcChannelSetDefaultMuteAllRemoteAudioStreams(this.mNativeHandle, muted);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setDefaultMuteAllRemoteVideoStreams(boolean muted) {
        if (this.mInitialized) {
            return nativeRtcChannelSetDefaultMuteAllRemoteVideoStreams(this.mNativeHandle, muted);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setEncryptionMode(String encryptionMode) {
        if (this.mInitialized) {
            return nativeRtcChannelSetEncryptionMode(this.mNativeHandle, encryptionMode);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setEncryptionSecret(String secret) {
        if (this.mInitialized) {
            return nativeRtcChannelSetEncryptionSecret(this.mNativeHandle, secret);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setLiveTranscoding(LiveTranscoding transcoding) {
        if (!this.mInitialized) {
            return -7;
        }
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
        return nativeRtcChannelSetLiveTranscoding(this.mNativeHandle, new RtcEngineMessage.PLiveTranscoding().marshall(transcoding));
    }

    @Override // io.agora.rtc.RtcChannel
    public int setRemoteDefaultVideoStreamType(int streamType) {
        if (this.mInitialized) {
            return nativeRtcChannelSetRemoteDefaultVideoStreamType(this.mNativeHandle, streamType);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setRemoteRenderMode(int uid, int mode) {
        if (this.mInitialized) {
            return nativeRtcChannelSetRemoteRenderMode(this.mNativeHandle, uid, mode);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setRemoteUserPriority(int uid, int userPriority) {
        if (this.mInitialized) {
            return nativeRtcChannelSetRemoteUserPriority(this.mNativeHandle, uid, userPriority);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setRemoteVideoRenderer(int uid, IVideoSink render) {
        if (this.mInitialized) {
            return nativeRtcChannelAddRemoteVideoRender(this.mNativeHandle, uid, render, render == null ? 0 : render instanceof AgoraDefaultRender ? 1 : 2);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setRemoteVideoStreamType(int uid, int streamType) {
        if (this.mInitialized) {
            return nativeRtcChannelSetRemoteVideoStreamType(this.mNativeHandle, uid, streamType);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int setRemoteVoicePosition(int uid, double pan, double gain) {
        if (this.mInitialized) {
            return nativeRtcChannelSetRemoteVoicePosition(this.mNativeHandle, uid, pan, gain);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int startChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration) {
        if (!this.mInitialized) {
            return -7;
        }
        if (channelMediaRelayConfiguration == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() == 0 || channelMediaRelayConfiguration.getSrcChannelMediaInfo() == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() > 4) {
            return -2;
        }
        for (Map.Entry<String, ChannelMediaInfo> entry : channelMediaRelayConfiguration.getDestChannelMediaInfos().entrySet()) {
            if (entry.getValue().channelName == null || entry.getValue().channelName.length() == 0) {
                return -2;
            }
        }
        return nativeRtcChannelStartChannelMediaRelay(this.mNativeHandle, new RtcEngineMessage.PChannelMediaRelayConfiguration().marshall(channelMediaRelayConfiguration));
    }

    @Override // io.agora.rtc.RtcChannel
    public int stopChannelMediaRelay() {
        if (this.mInitialized) {
            return nativeRtcChannelStopChannelMediaRelay(this.mNativeHandle);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int unpublish() {
        if (this.mInitialized) {
            return nativeRtcChannelUnpublish(this.mNativeHandle);
        }
        return -7;
    }

    @Override // io.agora.rtc.RtcChannel
    public int updateChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration) {
        if (!this.mInitialized) {
            return -7;
        }
        if (channelMediaRelayConfiguration == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() == 0 || channelMediaRelayConfiguration.getSrcChannelMediaInfo() == null || channelMediaRelayConfiguration.getDestChannelMediaInfos().size() > 4) {
            return -2;
        }
        for (Map.Entry<String, ChannelMediaInfo> entry : channelMediaRelayConfiguration.getDestChannelMediaInfos().entrySet()) {
            if (entry.getValue().channelName == null || entry.getValue().channelName.length() == 0) {
                return -2;
            }
        }
        return nativeRtcChannelUpdateChannelMediaRelay(this.mNativeHandle, new RtcEngineMessage.PChannelMediaRelayConfiguration().marshall(channelMediaRelayConfiguration));
    }
}
