package io.agora.rtc;

import io.agora.rtc.live.LiveInjectStreamConfig;
import io.agora.rtc.live.LiveTranscoding;
import io.agora.rtc.mediaio.IVideoSink;
import io.agora.rtc.models.ChannelMediaOptions;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;

/* loaded from: classes8.dex */
public abstract class RtcChannel {
    private IRtcChannelEventHandler mEventHandler = null;

    public abstract int addInjectStreamUrl(String url, LiveInjectStreamConfig config);

    public abstract int addPublishStreamUrl(String url, boolean transcodingEnabled);

    public abstract String channelId();

    public abstract int createDataStream(boolean reliable, boolean ordered);

    public abstract int destroy();

    public abstract String getCallId();

    public abstract int getConnectionState();

    public IRtcChannelEventHandler getEventHandler() {
        return this.mEventHandler;
    }

    public abstract int joinChannel(String token, String optionalInfo, int optionalUid, ChannelMediaOptions options);

    public abstract int joinChannelWithUserAccount(String token, String userAccount, ChannelMediaOptions options);

    public abstract int leaveChannel();

    public abstract int muteAllRemoteAudioStreams(boolean muted);

    public abstract int muteAllRemoteVideoStreams(boolean muted);

    public abstract int muteRemoteAudioStream(int uid, boolean muted);

    public abstract int muteRemoteVideoStream(int uid, boolean muted);

    public abstract int publish();

    public abstract int registerMediaMetadataObserver(IMetadataObserver observer, int type);

    public abstract int removeInjectStreamUrl(String url);

    public abstract int removePublishStreamUrl(String url);

    public abstract int renewToken(String token);

    public abstract int sendStreamMessage(int streamId, byte[] message);

    public abstract int setClientRole(int role);

    public abstract int setDefaultMuteAllRemoteAudioStreams(boolean muted);

    public abstract int setDefaultMuteAllRemoteVideoStreams(boolean muted);

    public abstract int setEncryptionMode(String encryptionMode);

    public abstract int setEncryptionSecret(String secret);

    public abstract int setLiveTranscoding(LiveTranscoding transcoding);

    public abstract int setRemoteDefaultVideoStreamType(int streamType);

    public abstract int setRemoteRenderMode(int uid, int mode);

    public abstract int setRemoteUserPriority(int uid, int userPriority);

    public abstract int setRemoteVideoRenderer(int uid, IVideoSink render);

    public abstract int setRemoteVideoStreamType(int uid, int streamType);

    public abstract int setRemoteVoicePosition(int uid, double pan, double gain);

    public void setRtcChannelEventHandler(IRtcChannelEventHandler eventHandler) {
        this.mEventHandler = eventHandler;
    }

    public abstract int startChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration);

    public abstract int stopChannelMediaRelay();

    public abstract int unpublish();

    public abstract int updateChannelMediaRelay(ChannelMediaRelayConfiguration channelMediaRelayConfiguration);
}
