package com.easefun.polyv.livecommon.module.modules.linkmic.model;

import androidx.annotation.IntRange;
import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVEventHelper;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class PLVLinkMicItemDataBean {
    public static final int MAX_VOLUME = 100;
    public static final String STATUS_IDLE = "idle";
    public static final String STATUS_JOIN = "join";
    public static final String STATUS_JOINING = "joining";
    public static final String STATUS_RTC_JOIN = "rtcJoin";
    public static final String STATUS_WAIT = "wait";
    private String actor;
    private int cupNum;
    private boolean isHasPaint;
    private boolean isHasSpeaker;
    private boolean isRaiseHand;
    private String linkMicId;
    private boolean muteAudio;
    private boolean muteVideo;
    private String nick;
    private String pic;
    private Runnable statusMethodCallListener;
    private String userId;
    private String userType;

    @IntRange(from = 0, to = 100)
    private int curVolume = 0;
    private boolean isFirstScreen = false;
    private boolean isScreenShare = false;
    private transient boolean isFullScreen = false;
    private String status = STATUS_IDLE;
    private Map<Integer, MuteMedia> muteVideoInRtcJoinListMap = new HashMap();
    private Map<Integer, MuteMedia> muteAudioInRtcJoinListMap = new HashMap();
    private int streamType = 0;

    public static class MuteMedia {
        boolean isMute;
        int streamType;

        public MuteMedia(boolean isMute) {
            this(isMute, 0);
        }

        public int getStreamType() {
            return this.streamType;
        }

        public boolean isMute() {
            return this.isMute;
        }

        public void setMute(boolean mute) {
            this.isMute = mute;
        }

        public void setStreamType(int streamType) {
            this.streamType = streamType;
        }

        public MuteMedia(boolean isMute, int streamType) {
            this.isMute = isMute;
            this.streamType = streamType;
        }
    }

    private void callStatusMethodTouch() {
        Runnable runnable = this.statusMethodCallListener;
        if (runnable != null) {
            runnable.run();
        }
    }

    public boolean equalStreamType(int streamType) {
        return this.streamType == streamType;
    }

    public String getActor() {
        return this.actor;
    }

    public int getCupNum() {
        return this.cupNum;
    }

    public int getCurVolume() {
        return this.curVolume;
    }

    public String getLinkMicId() {
        return this.linkMicId;
    }

    public MuteMedia getMuteAudioInRtcJoinList() {
        return getMuteAudioInRtcJoinList(0);
    }

    public MuteMedia getMuteVideoInRtcJoinList() {
        return getMuteVideoInRtcJoinList(0);
    }

    public String getNick() {
        return this.nick;
    }

    public String getPic() {
        return PLVEventHelper.fixChatPic(this.pic);
    }

    public String getStatus() {
        return this.status;
    }

    public int getStreamType() {
        return this.streamType;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserType() {
        return this.userType;
    }

    public boolean includeStreamType(int streamType) {
        return equalStreamType(streamType) || this.streamType == 0;
    }

    public boolean isFirstScreen() {
        return this.isFirstScreen;
    }

    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    public boolean isGuest() {
        return "guest".equals(this.userType);
    }

    public boolean isHasPaint() {
        return this.isHasPaint;
    }

    public boolean isHasSpeaker() {
        return this.isHasSpeaker;
    }

    public boolean isIdleStatus() {
        return STATUS_IDLE.equals(this.status);
    }

    public boolean isJoinStatus() {
        return STATUS_JOIN.equals(this.status);
    }

    public boolean isJoiningStatus() {
        return STATUS_JOINING.equals(this.status);
    }

    public boolean isMuteAudio() {
        return this.muteAudio;
    }

    public boolean isMuteVideo() {
        return this.muteVideo;
    }

    public boolean isRaiseHand() {
        return this.isRaiseHand;
    }

    public boolean isRtcJoinStatus() {
        return STATUS_RTC_JOIN.equals(this.status);
    }

    public boolean isScreenShare() {
        return this.isScreenShare;
    }

    public boolean isTeacher() {
        return "teacher".equals(this.userType);
    }

    public boolean isWaitStatus() {
        return "wait".equals(this.status);
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public void setCupNum(int cupNum) {
        this.cupNum = cupNum;
    }

    public void setCurVolume(int curVolume) {
        this.curVolume = curVolume;
    }

    public PLVLinkMicItemDataBean setFirstScreen(boolean firstScreen) {
        this.isFirstScreen = firstScreen;
        return this;
    }

    public PLVLinkMicItemDataBean setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
        return this;
    }

    public void setHasPaint(boolean hasPaint) {
        this.isHasPaint = hasPaint;
    }

    public void setHasSpeaker(boolean hasSpeaker) {
        this.isHasSpeaker = hasSpeaker;
    }

    public void setLinkMicId(String linkMicId) {
        this.linkMicId = linkMicId;
    }

    public void setMuteAudio(boolean muteAudio) {
        this.muteAudio = muteAudio;
    }

    public void setMuteAudioInRtcJoinList(MuteMedia muteAudioInRtcJoinList) {
        this.muteAudioInRtcJoinListMap.put(Integer.valueOf(muteAudioInRtcJoinList.getStreamType()), muteAudioInRtcJoinList);
        if (includeStreamType(muteAudioInRtcJoinList.getStreamType())) {
            setMuteAudio(muteAudioInRtcJoinList.isMute);
        }
    }

    public void setMuteVideo(boolean muteVideo) {
        this.muteVideo = muteVideo;
    }

    public void setMuteVideoInRtcJoinList(MuteMedia muteVideoInRtcJoinList) {
        this.muteVideoInRtcJoinListMap.put(Integer.valueOf(muteVideoInRtcJoinList.getStreamType()), muteVideoInRtcJoinList);
        if (includeStreamType(muteVideoInRtcJoinList.getStreamType())) {
            setMuteVideo(muteVideoInRtcJoinList.isMute);
        }
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setRaiseHand(boolean raiseHand) {
        this.isRaiseHand = raiseHand;
    }

    public void setScreenShare(boolean screenShared) {
        this.isScreenShare = screenShared;
    }

    public void setStatus(String status) {
        this.status = status;
        callStatusMethodTouch();
    }

    public void setStatusMethodCallListener(Runnable runnable) {
        this.statusMethodCallListener = runnable;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String toString() {
        return "PLVLinkMicItemDataBean{nick='" + this.nick + CharPool.SINGLE_QUOTE + ", linkMicId='" + this.linkMicId + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", muteVideo=" + this.muteVideo + ", muteAudio=" + this.muteAudio + ", cupNum=" + this.cupNum + ", isRaiseHand=" + this.isRaiseHand + ", isHasPaint=" + this.isHasPaint + ", isHasSpeaker=" + this.isHasSpeaker + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + ", actor='" + this.actor + CharPool.SINGLE_QUOTE + ", pic='" + this.pic + CharPool.SINGLE_QUOTE + ", curVolume=" + this.curVolume + ", isFirstScreen=" + this.isFirstScreen + ", isScreenShare=" + this.isScreenShare + ", isFullScreen=" + this.isFullScreen + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", muteVideoInRtcJoinListMap=" + this.muteVideoInRtcJoinListMap + ", muteAudioInRtcJoinListMap=" + this.muteAudioInRtcJoinListMap + ", streamType=" + this.streamType + ", statusMethodCallListener=" + this.statusMethodCallListener + '}';
    }

    public MuteMedia getMuteAudioInRtcJoinList(int streamType) {
        return this.muteAudioInRtcJoinListMap.get(Integer.valueOf(streamType));
    }

    public MuteMedia getMuteVideoInRtcJoinList(int streamType) {
        return this.muteVideoInRtcJoinListMap.get(Integer.valueOf(streamType));
    }
}
