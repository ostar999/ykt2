package com.plv.livescenes.model;

import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.socket.event.sclass.PLVInLiveAckResult;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVLiveClassDetailVO implements Serializable {
    public static final String MENUTYPE_BUY = "buy";
    public static final String MENUTYPE_CHAT = "chat";
    public static final String MENUTYPE_DESC = "desc";
    public static final String MENUTYPE_IFRAME = "iframe";
    public static final String MENUTYPE_PREVIOUS = "previous";
    public static final String MENUTYPE_QA = "qa";
    public static final String MENUTYPE_QUIZ = "quiz";
    public static final String MENUTYPE_TEXT = "text";
    public static final String MENUTYPE_TUWEN = "tuwen";
    private static final String TAG = "PLVLiveClassDetailVO";
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean implements Serializable {
        private List<AuthSettingsBean> authSettings;
        private String awardTrophyEnabled;
        private int channelId;
        private List<ChannelMenusBean> channelMenus;
        private String chatInputDisable;
        private String chatRobotEnabled;
        private String coverImage;
        private String desc;
        private boolean hasPlayback;
        private String httpDnsKey;
        private long likes;
        private Long maxViewer;
        private String name;
        private long pageView;
        private String playbackEnabled;
        private String publisher;
        private String pureRtcAvailState;
        private String pureRtcEnabled;
        private String quickLiveEnabled;
        private RecordFileSimpleModelBean recordFileSimpleModel;
        private String remindEnabled;
        private String restrictChatEnabled;
        private String roomId;
        private String rtcAudioSubEnabled;
        private String rtcType;
        private String scene;
        private String sessionId;
        private String splashEnabled;
        private String splashImg;
        private Object startTime;
        private String status;
        private String stream;
        private String userId;
        private String viewerSignalEnabled;
        private String warmUpFlv;
        private String warmUpImg;
        private String watchStatus;
        private WatchThemeModelBean watchThemeModel;

        public static class AuthSettingsBean implements Serializable {
            private Object authCode;
            private Object authTips;
            private String authType;
            private Object channelId;
            private Object codeAuthTips;
            private Object customKey;
            private Object customUri;
            private Object directKey;
            private Object enabled;
            private Object externalKey;
            private Object externalRedirectUri;
            private Object externalUri;
            private Object globalSettingEnabled;
            private Object infoAuthTips;
            private Object payAuthTips;
            private Object price;
            private Object qcodeImg;
            private Object qcodeTips;
            private Object rank;
            private Object trialWatchEnabled;
            private Object trialWatchEndTime;
            private Object trialWatchTime;
            private Object userId;
            private Object validTimePeriod;
            private Object watchEndTime;

            public Object getAuthCode() {
                return this.authCode;
            }

            public Object getAuthTips() {
                return this.authTips;
            }

            public String getAuthType() {
                return this.authType;
            }

            public Object getChannelId() {
                return this.channelId;
            }

            public Object getCodeAuthTips() {
                return this.codeAuthTips;
            }

            public Object getCustomKey() {
                return this.customKey;
            }

            public Object getCustomUri() {
                return this.customUri;
            }

            public Object getDirectKey() {
                return this.directKey;
            }

            public Object getEnabled() {
                return this.enabled;
            }

            public Object getExternalKey() {
                return this.externalKey;
            }

            public Object getExternalRedirectUri() {
                return this.externalRedirectUri;
            }

            public Object getExternalUri() {
                return this.externalUri;
            }

            public Object getGlobalSettingEnabled() {
                return this.globalSettingEnabled;
            }

            public Object getInfoAuthTips() {
                return this.infoAuthTips;
            }

            public Object getPayAuthTips() {
                return this.payAuthTips;
            }

            public Object getPrice() {
                return this.price;
            }

            public Object getQcodeImg() {
                return this.qcodeImg;
            }

            public Object getQcodeTips() {
                return this.qcodeTips;
            }

            public Object getRank() {
                return this.rank;
            }

            public Object getTrialWatchEnabled() {
                return this.trialWatchEnabled;
            }

            public Object getTrialWatchEndTime() {
                return this.trialWatchEndTime;
            }

            public Object getTrialWatchTime() {
                return this.trialWatchTime;
            }

            public Object getUserId() {
                return this.userId;
            }

            public Object getValidTimePeriod() {
                return this.validTimePeriod;
            }

            public Object getWatchEndTime() {
                return this.watchEndTime;
            }

            public void setAuthCode(Object obj) {
                this.authCode = obj;
            }

            public void setAuthTips(Object obj) {
                this.authTips = obj;
            }

            public void setAuthType(String str) {
                this.authType = str;
            }

            public void setChannelId(Object obj) {
                this.channelId = obj;
            }

            public void setCodeAuthTips(Object obj) {
                this.codeAuthTips = obj;
            }

            public void setCustomKey(Object obj) {
                this.customKey = obj;
            }

            public void setCustomUri(Object obj) {
                this.customUri = obj;
            }

            public void setDirectKey(Object obj) {
                this.directKey = obj;
            }

            public void setEnabled(Object obj) {
                this.enabled = obj;
            }

            public void setExternalKey(Object obj) {
                this.externalKey = obj;
            }

            public void setExternalRedirectUri(Object obj) {
                this.externalRedirectUri = obj;
            }

            public void setExternalUri(Object obj) {
                this.externalUri = obj;
            }

            public void setGlobalSettingEnabled(Object obj) {
                this.globalSettingEnabled = obj;
            }

            public void setInfoAuthTips(Object obj) {
                this.infoAuthTips = obj;
            }

            public void setPayAuthTips(Object obj) {
                this.payAuthTips = obj;
            }

            public void setPrice(Object obj) {
                this.price = obj;
            }

            public void setQcodeImg(Object obj) {
                this.qcodeImg = obj;
            }

            public void setQcodeTips(Object obj) {
                this.qcodeTips = obj;
            }

            public void setRank(Object obj) {
                this.rank = obj;
            }

            public void setTrialWatchEnabled(Object obj) {
                this.trialWatchEnabled = obj;
            }

            public void setTrialWatchEndTime(Object obj) {
                this.trialWatchEndTime = obj;
            }

            public void setTrialWatchTime(Object obj) {
                this.trialWatchTime = obj;
            }

            public void setUserId(Object obj) {
                this.userId = obj;
            }

            public void setValidTimePeriod(Object obj) {
                this.validTimePeriod = obj;
            }

            public void setWatchEndTime(Object obj) {
                this.watchEndTime = obj;
            }
        }

        public static class ChannelMenusBean implements Serializable {
            private String content;
            private String menuId;
            private String menuType;
            private String name;
            private int ordered;

            public String getContent() {
                return this.content;
            }

            public String getMenuId() {
                return this.menuId;
            }

            public String getMenuType() {
                return this.menuType;
            }

            public String getName() {
                return this.name;
            }

            public int getOrdered() {
                return this.ordered;
            }

            public void setContent(String str) {
                this.content = str;
            }

            public void setMenuId(String str) {
                this.menuId = str;
            }

            public void setMenuType(String str) {
                this.menuType = str;
            }

            public void setName(String str) {
                this.name = str;
            }

            public void setOrdered(int i2) {
                this.ordered = i2;
            }
        }

        public static class QADataBean implements Serializable {
            public static String LOCALE_ZH = "zh_CN";
            public static String THEME_BLACK = "black";
            private String channelId;
            private String roomId;
            private String sessionId;
            private String socketMsg;
            private String userId;
            private String userNick;
            private String userPic;
            private String theme = THEME_BLACK;
            private String locale = LOCALE_ZH;

            public String getChannelId() {
                return this.channelId;
            }

            public String getRoomId() {
                return this.roomId;
            }

            public String getSessionId() {
                return this.sessionId;
            }

            public String getSocketMsg() {
                return this.socketMsg;
            }

            public String getUserId() {
                return this.userId;
            }

            public String getUserNick() {
                return this.userNick;
            }

            public String getUserPic() {
                return this.userPic;
            }

            public void setChannelId(String str) {
                this.channelId = str;
            }

            public void setLocal(String str) {
                this.locale = str;
            }

            public void setRoomId(String str) {
                this.roomId = str;
            }

            public void setSessionId(String str) {
                this.sessionId = str;
            }

            public void setSocketMsg() {
                this.socketMsg = "{\"userInfo\": {\"nick\":\"" + this.userNick + "\",\"pic\":\"" + this.userPic + "\",\"userId\":\"" + this.userId + "\"},\"channelInfo\": {\"channelId\":\"" + this.channelId + "\",\"roomId\":\"" + this.roomId + "\",\"sessionId\":\"" + this.sessionId + "\"},\"locale\": \"" + this.locale + "\",\"qaSetting\": { \"theme\": \"" + this.theme + "\" }}";
            }

            public void setTheme(String str) {
                this.theme = str;
            }

            public void setUserId(String str) {
                this.userId = str;
            }

            public void setUserNick(String str) {
                this.userNick = str;
            }

            public void setUserPic(String str) {
                this.userPic = str;
            }
        }

        public static class RecordFileSimpleModelBean implements Serializable {
            private int channelId;
            private String channelSessionId;
            private Object daysLeft;
            private int duration;
            private String endTime;
            private String fileId;
            private String flv;
            private int height;
            private String liveType;
            private String m3u8;
            private String mp4;
            private String name;
            private String originSessionId;
            private String startTime;
            private int width;

            public int getChannelId() {
                return this.channelId;
            }

            public String getChannelSessionId() {
                return this.channelSessionId;
            }

            public Object getDaysLeft() {
                return this.daysLeft;
            }

            public int getDuration() {
                return this.duration;
            }

            public String getEndTime() {
                return this.endTime;
            }

            public String getFileId() {
                return this.fileId;
            }

            public String getFlv() {
                return this.flv;
            }

            public int getHeight() {
                return this.height;
            }

            public String getLiveType() {
                return this.liveType;
            }

            public String getM3u8() {
                return this.m3u8;
            }

            public String getMp4() {
                return this.mp4;
            }

            public String getName() {
                return this.name;
            }

            public String getOriginSessionId() {
                return this.originSessionId;
            }

            public String getStartTime() {
                return this.startTime;
            }

            public int getWidth() {
                return this.width;
            }

            public void setChannelId(int i2) {
                this.channelId = i2;
            }

            public void setChannelSessionId(String str) {
                this.channelSessionId = str;
            }

            public void setDaysLeft(Object obj) {
                this.daysLeft = obj;
            }

            public void setDuration(int i2) {
                this.duration = i2;
            }

            public void setEndTime(String str) {
                this.endTime = str;
            }

            public void setFileId(String str) {
                this.fileId = str;
            }

            public void setFlv(String str) {
                this.flv = str;
            }

            public void setHeight(int i2) {
                this.height = i2;
            }

            public void setLiveType(String str) {
                this.liveType = str;
            }

            public void setM3u8(String str) {
                this.m3u8 = str;
            }

            public void setMp4(String str) {
                this.mp4 = str;
            }

            public void setName(String str) {
                this.name = str;
            }

            public void setOriginSessionId(String str) {
                this.originSessionId = str;
            }

            public void setStartTime(String str) {
                this.startTime = str;
            }

            public void setWidth(int i2) {
                this.width = i2;
            }
        }

        public static class WatchThemeModelBean implements Serializable {
            private Object defaultTeacherImage;
            private String pageSkin;
            private String watchLayout;

            public Object getDefaultTeacherImage() {
                return this.defaultTeacherImage;
            }

            public String getPageSkin() {
                return this.pageSkin;
            }

            public String getWatchLayout() {
                return this.watchLayout;
            }

            public void setDefaultTeacherImage(Object obj) {
                this.defaultTeacherImage = obj;
            }

            public void setPageSkin(String str) {
                this.pageSkin = str;
            }

            public void setWatchLayout(String str) {
                this.watchLayout = str;
            }
        }

        public List<AuthSettingsBean> getAuthSettings() {
            return this.authSettings;
        }

        public String getAwardTrophyEnabled() {
            return this.awardTrophyEnabled;
        }

        public int getChannelId() {
            return this.channelId;
        }

        public List<ChannelMenusBean> getChannelMenus() {
            return this.channelMenus;
        }

        public String getChatRobotEnabled() {
            return this.chatRobotEnabled;
        }

        public String getCoverImage() {
            return this.coverImage;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getHttpDnsKey() {
            return this.httpDnsKey;
        }

        public long getLikes() {
            return this.likes;
        }

        public Long getMaxViewer() {
            return this.maxViewer;
        }

        public String getName() {
            return this.name;
        }

        public long getPageView() {
            return this.pageView;
        }

        public String getPlaybackEnabled() {
            return this.playbackEnabled;
        }

        public String getPublisher() {
            return this.publisher;
        }

        public String getPureRtcAvailState() {
            return this.pureRtcAvailState;
        }

        public String getPureRtcEnabled() {
            return this.pureRtcEnabled;
        }

        public String getQuickLiveEnabled() {
            return this.quickLiveEnabled;
        }

        public RecordFileSimpleModelBean getRecordFileSimpleModel() {
            return this.recordFileSimpleModel;
        }

        public String getRemindEnabled() {
            return this.remindEnabled;
        }

        public String getRestrictChatEnabled() {
            return this.restrictChatEnabled;
        }

        public String getRoomId() {
            return this.roomId;
        }

        public String getRtcAudioSubEnabled() {
            return this.rtcAudioSubEnabled;
        }

        public String getRtcType() {
            return this.rtcType;
        }

        public String getScene() {
            return this.scene;
        }

        public String getSessionId() {
            return this.sessionId;
        }

        public String getSplashEnabled() {
            return this.splashEnabled;
        }

        public String getSplashImg() {
            return this.splashImg;
        }

        public String getStartTime() {
            Object obj = this.startTime;
            return obj == null ? "" : obj.toString();
        }

        public String getStatus() {
            return this.status;
        }

        public String getStream() {
            return this.stream;
        }

        public String getUserId() {
            return this.userId;
        }

        public String getViewerSignalEnabled() {
            return this.viewerSignalEnabled;
        }

        public String getWarmUpFlv() {
            return this.warmUpFlv;
        }

        public String getWarmUpImg() {
            return this.warmUpImg;
        }

        public String getWatchStatus() {
            return this.watchStatus;
        }

        public WatchThemeModelBean getWatchThemeModel() {
            return this.watchThemeModel;
        }

        public boolean isChatPlaybackEnabled() {
            return "Y".equals(this.chatInputDisable);
        }

        public boolean isChatRobotEnabled() {
            return "Y".equals(this.chatRobotEnabled);
        }

        public boolean isEndStatus() {
            return "end".equals(getWatchStatus());
        }

        public boolean isHasPlayback() {
            return this.hasPlayback;
        }

        public boolean isLiveStatus() {
            return PLVInLiveAckResult.STATUS_LIVE.equals(getWatchStatus());
        }

        public boolean isPlaybackEnabled() {
            return "Y".equals(this.playbackEnabled);
        }

        public boolean isPlaybackStatus() {
            return "playback".equals(getWatchStatus());
        }

        public boolean isQuickLiveEnabled() {
            return "Y".equals(this.quickLiveEnabled);
        }

        public boolean isRemindEnabled() {
            return "Y".equals(this.remindEnabled);
        }

        public boolean isRestrictChatEnabled() {
            return "Y".equals(this.restrictChatEnabled);
        }

        public boolean isWaitingStatus() {
            return "waiting".equals(getWatchStatus());
        }

        public void setAuthSettings(List<AuthSettingsBean> list) {
            this.authSettings = list;
        }

        public void setAwardTrophyEnabled(String str) {
            this.awardTrophyEnabled = str;
        }

        public void setChannelId(int i2) {
            this.channelId = i2;
        }

        public void setChannelMenus(List<ChannelMenusBean> list) {
            this.channelMenus = list;
        }

        public DataBean setChatRobotEnabled(String str) {
            this.chatRobotEnabled = str;
            return this;
        }

        public void setCoverImage(String str) {
            this.coverImage = str;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        public void setHasPlayback(boolean z2) {
            this.hasPlayback = z2;
        }

        public void setLikes(long j2) {
            this.likes = j2;
        }

        public DataBean setMaxViewer(Long l2) {
            this.maxViewer = l2;
            return this;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setPageView(long j2) {
            this.pageView = j2;
        }

        public void setPlaybackEnabled(String str) {
            this.playbackEnabled = str;
        }

        public void setPublisher(String str) {
            this.publisher = str;
        }

        public void setPureRtcAvailState(String str) {
            this.pureRtcAvailState = str;
        }

        public void setPureRtcEnabled(String str) {
            this.pureRtcEnabled = str;
        }

        public void setQuickLiveEnabled(String str) {
            this.quickLiveEnabled = str;
        }

        public void setRecordFileSimpleModel(RecordFileSimpleModelBean recordFileSimpleModelBean) {
            this.recordFileSimpleModel = recordFileSimpleModelBean;
        }

        public void setRemindEnabled(String str) {
            this.remindEnabled = str;
        }

        public DataBean setRestrictChatEnabled(String str) {
            this.restrictChatEnabled = str;
            return this;
        }

        public void setRoomId(String str) {
            this.roomId = str;
        }

        public void setRtcAudioSubEnabled(String str) {
            this.rtcAudioSubEnabled = str;
        }

        public void setRtcType(String str) {
            this.rtcType = str;
        }

        public void setScene(String str) {
            this.scene = str;
        }

        public void setSessionId(String str) {
            this.sessionId = str;
        }

        public void setSplashEnabled(String str) {
            this.splashEnabled = str;
        }

        public void setSplashImg(String str) {
            this.splashImg = str;
        }

        public void setStartTime(Object obj) {
            this.startTime = obj;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public void setStream(String str) {
            this.stream = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setViewerSignalEnabled(String str) {
            this.viewerSignalEnabled = str;
        }

        public void setWarmUpFlv(String str) {
            this.warmUpFlv = str;
        }

        public void setWarmUpImg(String str) {
            this.warmUpImg = str;
        }

        public void setWatchStatus(String str) {
            this.watchStatus = str;
        }

        public void setWatchThemeModel(WatchThemeModelBean watchThemeModelBean) {
            this.watchThemeModel = watchThemeModelBean;
        }
    }

    public interface LiveStatus {
        public static final String LIVE_CLOSECALLLINKMIC = "closeCallLinkMic";
        public static final String LIVE_END = "end";
        public static final String LIVE_HIDESUBVIEW = "hideSubView";
        public static final String LIVE_N0_PPT = "live_no_ppt";
        public static final String LIVE_NO_STREAM = "no_stream";
        public static final String LIVE_OPENCALLLINKMIC = "openCallLinkMic";
        public static final String LIVE_OPEN_PPT = "live_ppt";
        public static final String LIVE_SHOWSUBVIEW = "showSubView";
        public static final String LIVE_START = "star";
        public static final String LOGIN_CHAT_ROOM = "loginChatRoom";
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return (DataBean) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isAwardTrophyEnabled() {
        return getData() != null && "Y".equals(getData().awardTrophyEnabled);
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public boolean isOpenCommodity() {
        try {
            Iterator<DataBean.ChannelMenusBean> it = getData().getChannelMenus().iterator();
            while (it.hasNext()) {
                if (MENUTYPE_BUY.equals(it.next().getMenuType())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, "isOpenCommodity:" + e2.getMessage());
            return false;
        }
    }

    public boolean isOpenQuizMenu() {
        try {
            Iterator<DataBean.ChannelMenusBean> it = getData().getChannelMenus().iterator();
            while (it.hasNext()) {
                if (MENUTYPE_QUIZ.equals(it.next().getMenuType())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, "isOpenQuizMenu:" + e2.getMessage());
            return false;
        }
    }

    public boolean isViewerSignalEnabled() {
        return getData() != null && "Y".equals(getData().viewerSignalEnabled);
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
