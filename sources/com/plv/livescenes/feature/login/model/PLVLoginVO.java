package com.plv.livescenes.feature.login.model;

import androidx.annotation.Nullable;
import com.easefun.polyv.livescenes.config.PolyvLiveChannelType;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.livescenes.config.PLVLiveChannelType;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVLoginVO {
    public static final String COLINMICTYPE_AUTO = "auto";
    public static final String COLINMICTYPE_MANUAL = "manual";
    private int InteractNumLimit;
    private String InteractUid;
    private String account;
    private String accountId;
    private String aiVirtualBgEnabled;
    private String appBeautyEnabled;
    private String appId;
    private String appSecret;
    private String appWebStartResolutionRatio;
    private String appWebStartResolutionRatioEnabled;
    private String avatar;
    private String awardTrophyEnabled;
    private String bakUrl;
    private String beautyEnabled;
    private String cdnType;
    private List<ChannelAccountListBean> channelAccountList;
    private String channelId;
    private String channelName;
    private String channelTransmitChannelId;
    private String channelTransmitReceiveChannelIds;
    private String channelType;
    private String chatApiDomain;
    private String chatDomain;
    private String childRoomEnabled;
    private ClientParamsBean clientParams;
    private String clientUseFixImageQualityEnabled;
    private String clientWebViewLoginToken;
    private String code;
    private String colinMicType;
    private ConfigurationBean configuration;
    private String consultingMenuEnabled;
    private String currentStream;
    private String customRecordFileEnabled;
    private String finishClassEnabled;
    private String guestLinkMicMode;
    private String guestMicEnable;
    private String guestTranAuthEnabled;
    private boolean isDoubleTeacherChannel;
    private String isLowLatency;
    private String isNgbEnabled;
    private String isOnlyAudio;
    private boolean isReceiveChannel;
    private String isSubChannel;
    private boolean isTransmitChannel;
    private String isUrlProtected;
    private String isVr;
    private String linkMicType;
    private String linkerCustomEnable;
    private String liveScene;
    private boolean liveStatus;
    private String maxRate;
    private String msg;
    private String multiplexingEnabled;
    private String needUpdate;
    private String newSeiEnabled;
    private String ngbUrl;
    private String nickname;
    private String oldLinkMicModeEnabled;
    private String portraitEnabled;
    private String pptAnimationEnabled;
    private String preview;
    private String pureRtcEnabled;
    private String pushSharingEnabled;
    private String qaMenuEnabled;
    private String qrCode;
    private String raceAnswerEnabled;
    private String rateControlModes;
    private String role;
    private String roomIds;
    private String rtcCdnEnabled;
    private int rtcMaxResolution;
    private String rtcRecordFileEnabled;
    private String rtcRecordNotifyEnabled;
    private String rtcRecordNotifyImg;
    private String rtcRecordNotifyTip;
    private String rtcRecordType;
    private String rtcType;
    private String rtsEnabled;
    private String status;
    private String stream;
    private String suffix;
    private String suffixCamera;
    private String teacherActor;
    private String teacherAvatar;
    private String teacherNickname;
    private String tuwenVoiceTextEnabled;
    private String url;
    private String useId;
    private String userStatus;
    private String viewerSignalEnabled;
    private String visitorLinkMicMode;
    private String visitorMicEnable;
    private String x264Options;

    public static class ChannelAccountListBean {
        private String accountId;
        private String accountName;

        public String getAccountId() {
            return this.accountId;
        }

        public String getAccountName() {
            return this.accountName;
        }

        public void setAccountId(String str) {
            this.accountId = str;
        }

        public void setAccountName(String str) {
            this.accountName = str;
        }
    }

    public static class ClientParamsBean {
        private int fps;
        private int gop;

        public int getFps() {
            return this.fps;
        }

        public int getGop() {
            return this.gop;
        }

        public void setFps(int i2) {
            this.fps = i2;
        }

        public void setGop(int i2) {
            this.gop = i2;
        }
    }

    public static class ConfigurationBean {
        private AliSpeechTranscriberConfigBean aliSpeechTranscriberConfig;

        public static class AliSpeechTranscriberConfigBean {
            private String accessKeyId;
            private String accesskeySecret;
            private String appKey;

            public String getAccessKeyId() {
                return this.accessKeyId;
            }

            public String getAccesskeySecret() {
                return this.accesskeySecret;
            }

            public String getAppKey() {
                return this.appKey;
            }

            public void setAccessKeyId(String str) {
                this.accessKeyId = str;
            }

            public void setAccesskeySecret(String str) {
                this.accesskeySecret = str;
            }

            public void setAppKey(String str) {
                this.appKey = str;
            }
        }

        public AliSpeechTranscriberConfigBean getAliSpeechTranscriberConfig() {
            return this.aliSpeechTranscriberConfig;
        }

        public void setAliSpeechTranscriberConfig(AliSpeechTranscriberConfigBean aliSpeechTranscriberConfigBean) {
            this.aliSpeechTranscriberConfig = aliSpeechTranscriberConfigBean;
        }
    }

    public String getAccount() {
        return this.account;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public String getAiVirtualBgEnabled() {
        return this.aiVirtualBgEnabled;
    }

    public String getAppBeautyEnabled() {
        return this.appBeautyEnabled;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public String getAppWebStartResolutionRatio() {
        return this.appWebStartResolutionRatio;
    }

    public String getAppWebStartResolutionRatioEnabled() {
        return this.appWebStartResolutionRatioEnabled;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getAwardTrophyEnabled() {
        return this.awardTrophyEnabled;
    }

    public String getBakUrl() {
        return this.bakUrl;
    }

    public String getBeautyEnabled() {
        return this.beautyEnabled;
    }

    public String getCdnType() {
        return this.cdnType;
    }

    public List<ChannelAccountListBean> getChannelAccountList() {
        return this.channelAccountList;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public String getChannelTransmitChannelId() {
        return this.channelTransmitChannelId;
    }

    public String getChannelTransmitReceiveChannelIds() {
        return this.channelTransmitReceiveChannelIds;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public String getChatApiDomain() {
        return this.chatApiDomain;
    }

    public String getChatDomain() {
        return this.chatDomain;
    }

    public String getChildRoomEnabled() {
        return this.childRoomEnabled;
    }

    public ClientParamsBean getClientParams() {
        return this.clientParams;
    }

    public String getClientUseFixImageQualityEnabled() {
        return this.clientUseFixImageQualityEnabled;
    }

    public String getClientWebViewLoginToken() {
        return this.clientWebViewLoginToken;
    }

    public String getCode() {
        return this.code;
    }

    public String getColinMicType() {
        return this.colinMicType;
    }

    public ConfigurationBean getConfiguration() {
        return this.configuration;
    }

    public String getConsultingMenuEnabled() {
        return this.consultingMenuEnabled;
    }

    public String getCurrentStream() {
        return this.currentStream;
    }

    public String getCustomRecordFileEnabled() {
        return this.customRecordFileEnabled;
    }

    public String getFinishClassEnabled() {
        return this.finishClassEnabled;
    }

    public String getGuestLinkMicMode() {
        return this.guestLinkMicMode;
    }

    public String getGuestMicEnable() {
        return this.guestMicEnable;
    }

    public String getGuestTranAuthEnabled() {
        return this.guestTranAuthEnabled;
    }

    public int getInteractNumLimit() {
        return this.InteractNumLimit;
    }

    public String getInteractUid() {
        return this.InteractUid;
    }

    public String getIsLowLatency() {
        return this.isLowLatency;
    }

    public String getIsNgbEnabled() {
        return this.isNgbEnabled;
    }

    public String getIsOnlyAudio() {
        return this.isOnlyAudio;
    }

    public String getIsSubChannel() {
        return this.isSubChannel;
    }

    public String getIsUrlProtected() {
        return this.isUrlProtected;
    }

    public String getIsVr() {
        return this.isVr;
    }

    public String getLinkMicType() {
        return this.linkMicType;
    }

    public String getLinkerCustomEnable() {
        return this.linkerCustomEnable;
    }

    @Nullable
    @Deprecated
    public PolyvLiveChannelType getLiveChannelType() {
        try {
            return PolyvLiveChannelType.mapFromServerString(this.liveScene);
        } catch (PolyvLiveChannelType.UnknownChannelTypeException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Nullable
    public PLVLiveChannelType getLiveChannelTypeNew() {
        try {
            return PLVLiveChannelType.mapFromServerString(this.liveScene);
        } catch (PLVLiveChannelType.UnknownChannelTypeException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String getLiveScene() {
        return this.liveScene;
    }

    public String getMaxRate() {
        return this.maxRate;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getMultiplexingEnabled() {
        return this.multiplexingEnabled;
    }

    public String getNeedUpdate() {
        return this.needUpdate;
    }

    public String getNewSeiEnabled() {
        return this.newSeiEnabled;
    }

    public String getNgbUrl() {
        return this.ngbUrl;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getOldLinkMicModeEnabled() {
        return this.oldLinkMicModeEnabled;
    }

    @Nullable
    public PLVLinkMicConstant.PushResolutionRatio getParsedAppWebStartResolutionRatio() {
        if (getAppWebStartResolutionRatio() == null) {
            return null;
        }
        String appWebStartResolutionRatio = getAppWebStartResolutionRatio();
        appWebStartResolutionRatio.hashCode();
        if (appWebStartResolutionRatio.equals("4:3")) {
            return PLVLinkMicConstant.PushResolutionRatio.RATIO_4_3;
        }
        if (appWebStartResolutionRatio.equals("16:9")) {
            return PLVLinkMicConstant.PushResolutionRatio.RATIO_16_9;
        }
        return null;
    }

    public String getPortraitEnabled() {
        return this.portraitEnabled;
    }

    public String getPptAnimationEnabled() {
        return this.pptAnimationEnabled;
    }

    public String getPreview() {
        return this.preview;
    }

    public String getPureRtcEnabled() {
        return this.pureRtcEnabled;
    }

    public String getPushSharingEnabled() {
        return this.pushSharingEnabled;
    }

    public String getQaMenuEnabled() {
        return this.qaMenuEnabled;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public String getRaceAnswerEnabled() {
        return this.raceAnswerEnabled;
    }

    public String getRateControlModes() {
        return this.rateControlModes;
    }

    public String getRole() {
        return this.role;
    }

    public String getRoomIds() {
        return this.roomIds;
    }

    public String getRtcCdnEnabled() {
        return this.rtcCdnEnabled;
    }

    public int getRtcMaxResolution() {
        return this.rtcMaxResolution;
    }

    public String getRtcRecordFileEnabled() {
        return this.rtcRecordFileEnabled;
    }

    public String getRtcRecordNotifyEnabled() {
        return this.rtcRecordNotifyEnabled;
    }

    public String getRtcRecordNotifyImg() {
        return this.rtcRecordNotifyImg;
    }

    public String getRtcRecordNotifyTip() {
        return this.rtcRecordNotifyTip;
    }

    public String getRtcRecordType() {
        return this.rtcRecordType;
    }

    public String getRtcType() {
        return this.rtcType;
    }

    public String getRtsEnabled() {
        return this.rtsEnabled;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStream() {
        return this.stream;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getSuffixCamera() {
        return this.suffixCamera;
    }

    public String getTeacherActor() {
        return this.teacherActor;
    }

    public String getTeacherAvatar() {
        return this.teacherAvatar;
    }

    public String getTeacherNickname() {
        return this.teacherNickname;
    }

    public String getTuwenVoiceTextEnabled() {
        return this.tuwenVoiceTextEnabled;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUseId() {
        return this.useId;
    }

    public String getUserStatus() {
        return this.userStatus;
    }

    public String getViewerSignalEnabled() {
        return this.viewerSignalEnabled;
    }

    public String getVisitorLinkMicMode() {
        return this.visitorLinkMicMode;
    }

    public String getVisitorMicEnable() {
        return this.visitorMicEnable;
    }

    public String getX264Options() {
        return this.x264Options;
    }

    public boolean isAppBeautyEnabled() {
        return "Y".equals(this.appBeautyEnabled);
    }

    public boolean isAppWebStartResolutionRatioEnabled() {
        return "Y".equals(this.appWebStartResolutionRatioEnabled);
    }

    public boolean isGuestTranAuthEnabled() {
        return "Y".equals(getGuestTranAuthEnabled());
    }

    public boolean isIsDoubleTeacherChannel() {
        return this.isDoubleTeacherChannel;
    }

    public boolean isIsReceiveChannel() {
        return this.isReceiveChannel;
    }

    public boolean isIsTransmitChannel() {
        return this.isTransmitChannel;
    }

    public boolean isLiveStatus() {
        return this.liveStatus;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public void setAccountId(String str) {
        this.accountId = str;
    }

    public void setAiVirtualBgEnabled(String str) {
        this.aiVirtualBgEnabled = str;
    }

    public PLVLoginVO setAppBeautyEnabled(String str) {
        this.appBeautyEnabled = str;
        return this;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setAppSecret(String str) {
        this.appSecret = str;
    }

    public PLVLoginVO setAppWebStartResolutionRatio(String str) {
        this.appWebStartResolutionRatio = str;
        return this;
    }

    public PLVLoginVO setAppWebStartResolutionRatioEnabled(String str) {
        this.appWebStartResolutionRatioEnabled = str;
        return this;
    }

    public void setAvatar(String str) {
        this.avatar = str;
    }

    public void setAwardTrophyEnabled(String str) {
        this.awardTrophyEnabled = str;
    }

    public void setBakUrl(String str) {
        this.bakUrl = str;
    }

    public void setBeautyEnabled(String str) {
        this.beautyEnabled = str;
    }

    public void setCdnType(String str) {
        this.cdnType = str;
    }

    public void setChannelAccountList(List<ChannelAccountListBean> list) {
        this.channelAccountList = list;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setChannelName(String str) {
        this.channelName = str;
    }

    public void setChannelTransmitChannelId(String str) {
        this.channelTransmitChannelId = str;
    }

    public void setChannelTransmitReceiveChannelIds(String str) {
        this.channelTransmitReceiveChannelIds = str;
    }

    public void setChannelType(String str) {
        this.channelType = str;
    }

    public void setChatApiDomain(String str) {
        this.chatApiDomain = str;
    }

    public void setChatDomain(String str) {
        this.chatDomain = str;
    }

    public void setChildRoomEnabled(String str) {
        this.childRoomEnabled = str;
    }

    public void setClientParams(ClientParamsBean clientParamsBean) {
        this.clientParams = clientParamsBean;
    }

    public void setClientUseFixImageQualityEnabled(String str) {
        this.clientUseFixImageQualityEnabled = str;
    }

    public void setClientWebViewLoginToken(String str) {
        this.clientWebViewLoginToken = str;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public void setColinMicType(String str) {
        this.colinMicType = str;
    }

    public void setConfiguration(ConfigurationBean configurationBean) {
        this.configuration = configurationBean;
    }

    public void setConsultingMenuEnabled(String str) {
        this.consultingMenuEnabled = str;
    }

    public void setCurrentStream(String str) {
        this.currentStream = str;
    }

    public void setCustomRecordFileEnabled(String str) {
        this.customRecordFileEnabled = str;
    }

    public void setFinishClassEnabled(String str) {
        this.finishClassEnabled = str;
    }

    public void setGuestLinkMicMode(String str) {
        this.guestLinkMicMode = str;
    }

    public void setGuestMicEnable(String str) {
        this.guestMicEnable = str;
    }

    public PLVLoginVO setGuestTranAuthEnabled(String str) {
        this.guestTranAuthEnabled = str;
        return this;
    }

    public void setInteractNumLimit(int i2) {
        this.InteractNumLimit = i2;
    }

    public void setInteractUid(String str) {
        this.InteractUid = str;
    }

    public void setIsDoubleTeacherChannel(boolean z2) {
        this.isDoubleTeacherChannel = z2;
    }

    public void setIsLowLatency(String str) {
        this.isLowLatency = str;
    }

    public void setIsNgbEnabled(String str) {
        this.isNgbEnabled = str;
    }

    public void setIsOnlyAudio(String str) {
        this.isOnlyAudio = str;
    }

    public void setIsReceiveChannel(boolean z2) {
        this.isReceiveChannel = z2;
    }

    public void setIsSubChannel(String str) {
        this.isSubChannel = str;
    }

    public void setIsTransmitChannel(boolean z2) {
        this.isTransmitChannel = z2;
    }

    public void setIsUrlProtected(String str) {
        this.isUrlProtected = str;
    }

    public void setIsVr(String str) {
        this.isVr = str;
    }

    public void setLinkMicType(String str) {
        this.linkMicType = str;
    }

    public void setLinkerCustomEnable(String str) {
        this.linkerCustomEnable = str;
    }

    public void setLiveScene(String str) {
        this.liveScene = str;
    }

    public void setLiveStatus(boolean z2) {
        this.liveStatus = z2;
    }

    public void setMaxRate(String str) {
        this.maxRate = str;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public void setMultiplexingEnabled(String str) {
        this.multiplexingEnabled = str;
    }

    public void setNeedUpdate(String str) {
        this.needUpdate = str;
    }

    public void setNewSeiEnabled(String str) {
        this.newSeiEnabled = str;
    }

    public void setNgbUrl(String str) {
        this.ngbUrl = str;
    }

    public void setNickname(String str) {
        this.nickname = str;
    }

    public void setOldLinkMicModeEnabled(String str) {
        this.oldLinkMicModeEnabled = str;
    }

    public void setPortraitEnabled(String str) {
        this.portraitEnabled = str;
    }

    public void setPptAnimationEnabled(String str) {
        this.pptAnimationEnabled = str;
    }

    public void setPreview(String str) {
        this.preview = str;
    }

    public void setPureRtcEnabled(String str) {
        this.pureRtcEnabled = str;
    }

    public void setPushSharingEnabled(String str) {
        this.pushSharingEnabled = str;
    }

    public void setQaMenuEnabled(String str) {
        this.qaMenuEnabled = str;
    }

    public void setQrCode(String str) {
        this.qrCode = str;
    }

    public void setRaceAnswerEnabled(String str) {
        this.raceAnswerEnabled = str;
    }

    public void setRateControlModes(String str) {
        this.rateControlModes = str;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public void setRoomIds(String str) {
        this.roomIds = str;
    }

    public void setRtcCdnEnabled(String str) {
        this.rtcCdnEnabled = str;
    }

    public void setRtcMaxResolution(int i2) {
        this.rtcMaxResolution = i2;
    }

    public void setRtcRecordFileEnabled(String str) {
        this.rtcRecordFileEnabled = str;
    }

    public void setRtcRecordNotifyEnabled(String str) {
        this.rtcRecordNotifyEnabled = str;
    }

    public void setRtcRecordNotifyImg(String str) {
        this.rtcRecordNotifyImg = str;
    }

    public void setRtcRecordNotifyTip(String str) {
        this.rtcRecordNotifyTip = str;
    }

    public void setRtcRecordType(String str) {
        this.rtcRecordType = str;
    }

    public void setRtcType(String str) {
        this.rtcType = str;
    }

    public void setRtsEnabled(String str) {
        this.rtsEnabled = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setStream(String str) {
        this.stream = str;
    }

    public void setSuffix(String str) {
        this.suffix = str;
    }

    public void setSuffixCamera(String str) {
        this.suffixCamera = str;
    }

    public void setTeacherActor(String str) {
        this.teacherActor = str;
    }

    public void setTeacherAvatar(String str) {
        this.teacherAvatar = str;
    }

    public void setTeacherNickname(String str) {
        this.teacherNickname = str;
    }

    public void setTuwenVoiceTextEnabled(String str) {
        this.tuwenVoiceTextEnabled = str;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setUseId(String str) {
        this.useId = str;
    }

    public void setUserStatus(String str) {
        this.userStatus = str;
    }

    public void setViewerSignalEnabled(String str) {
        this.viewerSignalEnabled = str;
    }

    public void setVisitorLinkMicMode(String str) {
        this.visitorLinkMicMode = str;
    }

    public void setVisitorMicEnable(String str) {
        this.visitorMicEnable = str;
    }

    public void setX264Options(String str) {
        this.x264Options = str;
    }
}
