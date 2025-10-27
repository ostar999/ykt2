package com.plv.livescenes.model;

import java.util.List;

/* loaded from: classes5.dex */
public class PLVPlaybackChannelDetailVO {
    private int code;
    private DataBean data;
    private Object error;
    private String message;
    private String status;

    public static class DataBean {
        private String authPcRedircetEnabled;
        private List<AuthSettingsBean> authSettings;
        private ChannelBean channel;
        private ChannelAdvertBean channelAdvert;
        private ChannelBookingBean channelBooking;
        private Object channelCCBTemplateDto;
        private ChannelDataBean channelData;
        private ChannelEnrollBean channelEnroll;
        private String channelId;
        private ChannelItemAvailStateBean channelItemAvailState;
        private int channelLiveStatusQueryFrequency;
        private List<ChannelMenusBean> channelMenus;
        private ChannelPlaybackBean channelPlayback;
        private ChannelPromotionBean channelPromotion;
        private String channelSessionId;
        private ChannelSwitchBean channelSwitch;
        private ChannelTeacherBean channelTeacher;
        private String channelType;
        private int channelWatchQueryFrequency;
        private Object channelWxBooking;
        private String chatRobotEnabled;
        private String countEnabled;
        private String coverImg;
        private CurrentChannelBean currentChannel;
        private String currentStream;
        private Object customIcon;
        private String description;
        private DomainDtoBean domainDto;
        private DonateSettingBean donateSetting;
        private FooterSettingBean footerSetting;
        private Object infoFields;
        private InteractiveGameBean interactiveGame;
        private Object ipCountry;
        private String isClosePreview;
        private String isPullStreamSecret;
        private String liveStatus;
        private String liveType;
        private long maxViewers;
        private String mobileEnabled;
        private MultiMeetingDetailBean multiMeetingDetail;
        private Object multiMeetings;
        private String name;
        private String newScene;
        private Object openid;
        private PageWatchBean pageWatch;
        private String playMode;
        private PlayerSettingModelBean playerSettingModel;
        private Object promoteId;
        private String publisher;
        private RedpackRainSkinBean redpackRainSkin;
        private RedpackSkinBean redpackSkin;
        private String restrictChatEnabled;
        private Object roomIds;
        private String scene;
        private Object seminarHost;
        private String splashEnabled;
        private String splashImg;
        private long startTime;
        private String stream;
        private String streamStatus;
        private Object transmitMasterStream;
        private Object trialWatchTime;
        private UserConfigBean userConfig;
        private String userId;
        private Object viewerId;
        private String watchChatEnabled;
        private WatchInfoBean watchInfo;
        private String watchStatus;
        private Object wxUser;

        public static class AuthSettingsBean {
            private Object authTips;
            private String authType;
            private Object codeAuthTips;
            private Object customUri;
            private Object enabled;
            private Object externalEntryText;
            private Object externalRedirectUri;
            private Object externalUri;
            private Object infoAuthTips;
            private Object infoDesc;
            private Object payAuthTips;
            private Object price;
            private Object qcodeImg;
            private Object qcodeTips;
            private Object rank;
            private Object trialWatchEnabled;
            private Object trialWatchEndTime;
            private Object trialWatchTime;
            private Object validTimePeriod;
            private Object watchEndTime;
            private Object whiteListEntryText;
            private Object whiteListInputTips;

            public Object getAuthTips() {
                return this.authTips;
            }

            public String getAuthType() {
                return this.authType;
            }

            public Object getCodeAuthTips() {
                return this.codeAuthTips;
            }

            public Object getCustomUri() {
                return this.customUri;
            }

            public Object getEnabled() {
                return this.enabled;
            }

            public Object getExternalEntryText() {
                return this.externalEntryText;
            }

            public Object getExternalRedirectUri() {
                return this.externalRedirectUri;
            }

            public Object getExternalUri() {
                return this.externalUri;
            }

            public Object getInfoAuthTips() {
                return this.infoAuthTips;
            }

            public Object getInfoDesc() {
                return this.infoDesc;
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

            public Object getValidTimePeriod() {
                return this.validTimePeriod;
            }

            public Object getWatchEndTime() {
                return this.watchEndTime;
            }

            public Object getWhiteListEntryText() {
                return this.whiteListEntryText;
            }

            public Object getWhiteListInputTips() {
                return this.whiteListInputTips;
            }

            public void setAuthTips(Object obj) {
                this.authTips = obj;
            }

            public void setAuthType(String str) {
                this.authType = str;
            }

            public void setCodeAuthTips(Object obj) {
                this.codeAuthTips = obj;
            }

            public void setCustomUri(Object obj) {
                this.customUri = obj;
            }

            public void setEnabled(Object obj) {
                this.enabled = obj;
            }

            public void setExternalEntryText(Object obj) {
                this.externalEntryText = obj;
            }

            public void setExternalRedirectUri(Object obj) {
                this.externalRedirectUri = obj;
            }

            public void setExternalUri(Object obj) {
                this.externalUri = obj;
            }

            public void setInfoAuthTips(Object obj) {
                this.infoAuthTips = obj;
            }

            public void setInfoDesc(Object obj) {
                this.infoDesc = obj;
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

            public void setValidTimePeriod(Object obj) {
                this.validTimePeriod = obj;
            }

            public void setWatchEndTime(Object obj) {
                this.watchEndTime = obj;
            }

            public void setWhiteListEntryText(Object obj) {
                this.whiteListEntryText = obj;
            }

            public void setWhiteListInputTips(Object obj) {
                this.whiteListInputTips = obj;
            }
        }

        public static class ChannelAdvertBean {
            private Object advertVid;
            private String closeAdvertEnabled;
            private Object df;
            private Object headAdvertClickUrl;
            private int headAdvertDuration;
            private String headAdvertEnabled;
            private int headAdvertHeight;
            private Object headAdvertHref;
            private Object headAdvertMediaUrl;
            private Object headAdvertMp4;
            private Object headAdvertShowUrl;
            private String headAdvertType;
            private int headAdvertWidth;
            private String pageAdvertEnabled;
            private Object pageAdvertHref1;
            private Object pageAdvertHref2;
            private Object pageAdvertHref3;
            private Object pageAdvertHref4;
            private Object pageAdvertHref5;
            private Object pageAdvertImg1;
            private Object pageAdvertImg2;
            private Object pageAdvertImg3;
            private Object pageAdvertImg4;
            private Object pageAdvertImg5;
            private Object pageAdvertText1;
            private Object pageAdvertText2;
            private Object pageAdvertText3;
            private Object pageAdvertText4;
            private Object pageAdvertText5;
            private Object stopAdvertClickUrl;
            private String stopAdvertEnabled;
            private Object stopAdvertHref;
            private Object stopAdvertImage;
            private Object stopAdvertShowUrl;
            private Object vidStatus;
            private Object videoSource;

            public Object getAdvertVid() {
                return this.advertVid;
            }

            public String getCloseAdvertEnabled() {
                return this.closeAdvertEnabled;
            }

            public Object getDf() {
                return this.df;
            }

            public Object getHeadAdvertClickUrl() {
                return this.headAdvertClickUrl;
            }

            public int getHeadAdvertDuration() {
                return this.headAdvertDuration;
            }

            public String getHeadAdvertEnabled() {
                return this.headAdvertEnabled;
            }

            public int getHeadAdvertHeight() {
                return this.headAdvertHeight;
            }

            public Object getHeadAdvertHref() {
                return this.headAdvertHref;
            }

            public Object getHeadAdvertMediaUrl() {
                return this.headAdvertMediaUrl;
            }

            public Object getHeadAdvertMp4() {
                return this.headAdvertMp4;
            }

            public Object getHeadAdvertShowUrl() {
                return this.headAdvertShowUrl;
            }

            public String getHeadAdvertType() {
                return this.headAdvertType;
            }

            public int getHeadAdvertWidth() {
                return this.headAdvertWidth;
            }

            public String getPageAdvertEnabled() {
                return this.pageAdvertEnabled;
            }

            public Object getPageAdvertHref1() {
                return this.pageAdvertHref1;
            }

            public Object getPageAdvertHref2() {
                return this.pageAdvertHref2;
            }

            public Object getPageAdvertHref3() {
                return this.pageAdvertHref3;
            }

            public Object getPageAdvertHref4() {
                return this.pageAdvertHref4;
            }

            public Object getPageAdvertHref5() {
                return this.pageAdvertHref5;
            }

            public Object getPageAdvertImg1() {
                return this.pageAdvertImg1;
            }

            public Object getPageAdvertImg2() {
                return this.pageAdvertImg2;
            }

            public Object getPageAdvertImg3() {
                return this.pageAdvertImg3;
            }

            public Object getPageAdvertImg4() {
                return this.pageAdvertImg4;
            }

            public Object getPageAdvertImg5() {
                return this.pageAdvertImg5;
            }

            public Object getPageAdvertText1() {
                return this.pageAdvertText1;
            }

            public Object getPageAdvertText2() {
                return this.pageAdvertText2;
            }

            public Object getPageAdvertText3() {
                return this.pageAdvertText3;
            }

            public Object getPageAdvertText4() {
                return this.pageAdvertText4;
            }

            public Object getPageAdvertText5() {
                return this.pageAdvertText5;
            }

            public Object getStopAdvertClickUrl() {
                return this.stopAdvertClickUrl;
            }

            public String getStopAdvertEnabled() {
                return this.stopAdvertEnabled;
            }

            public Object getStopAdvertHref() {
                return this.stopAdvertHref;
            }

            public Object getStopAdvertImage() {
                return this.stopAdvertImage;
            }

            public Object getStopAdvertShowUrl() {
                return this.stopAdvertShowUrl;
            }

            public Object getVidStatus() {
                return this.vidStatus;
            }

            public Object getVideoSource() {
                return this.videoSource;
            }

            public void setAdvertVid(Object obj) {
                this.advertVid = obj;
            }

            public void setCloseAdvertEnabled(String str) {
                this.closeAdvertEnabled = str;
            }

            public void setDf(Object obj) {
                this.df = obj;
            }

            public void setHeadAdvertClickUrl(Object obj) {
                this.headAdvertClickUrl = obj;
            }

            public void setHeadAdvertDuration(int i2) {
                this.headAdvertDuration = i2;
            }

            public void setHeadAdvertEnabled(String str) {
                this.headAdvertEnabled = str;
            }

            public void setHeadAdvertHeight(int i2) {
                this.headAdvertHeight = i2;
            }

            public void setHeadAdvertHref(Object obj) {
                this.headAdvertHref = obj;
            }

            public void setHeadAdvertMediaUrl(Object obj) {
                this.headAdvertMediaUrl = obj;
            }

            public void setHeadAdvertMp4(Object obj) {
                this.headAdvertMp4 = obj;
            }

            public void setHeadAdvertShowUrl(Object obj) {
                this.headAdvertShowUrl = obj;
            }

            public void setHeadAdvertType(String str) {
                this.headAdvertType = str;
            }

            public void setHeadAdvertWidth(int i2) {
                this.headAdvertWidth = i2;
            }

            public void setPageAdvertEnabled(String str) {
                this.pageAdvertEnabled = str;
            }

            public void setPageAdvertHref1(Object obj) {
                this.pageAdvertHref1 = obj;
            }

            public void setPageAdvertHref2(Object obj) {
                this.pageAdvertHref2 = obj;
            }

            public void setPageAdvertHref3(Object obj) {
                this.pageAdvertHref3 = obj;
            }

            public void setPageAdvertHref4(Object obj) {
                this.pageAdvertHref4 = obj;
            }

            public void setPageAdvertHref5(Object obj) {
                this.pageAdvertHref5 = obj;
            }

            public void setPageAdvertImg1(Object obj) {
                this.pageAdvertImg1 = obj;
            }

            public void setPageAdvertImg2(Object obj) {
                this.pageAdvertImg2 = obj;
            }

            public void setPageAdvertImg3(Object obj) {
                this.pageAdvertImg3 = obj;
            }

            public void setPageAdvertImg4(Object obj) {
                this.pageAdvertImg4 = obj;
            }

            public void setPageAdvertImg5(Object obj) {
                this.pageAdvertImg5 = obj;
            }

            public void setPageAdvertText1(Object obj) {
                this.pageAdvertText1 = obj;
            }

            public void setPageAdvertText2(Object obj) {
                this.pageAdvertText2 = obj;
            }

            public void setPageAdvertText3(Object obj) {
                this.pageAdvertText3 = obj;
            }

            public void setPageAdvertText4(Object obj) {
                this.pageAdvertText4 = obj;
            }

            public void setPageAdvertText5(Object obj) {
                this.pageAdvertText5 = obj;
            }

            public void setStopAdvertClickUrl(Object obj) {
                this.stopAdvertClickUrl = obj;
            }

            public void setStopAdvertEnabled(String str) {
                this.stopAdvertEnabled = str;
            }

            public void setStopAdvertHref(Object obj) {
                this.stopAdvertHref = obj;
            }

            public void setStopAdvertImage(Object obj) {
                this.stopAdvertImage = obj;
            }

            public void setStopAdvertShowUrl(Object obj) {
                this.stopAdvertShowUrl = obj;
            }

            public void setVidStatus(Object obj) {
                this.vidStatus = obj;
            }

            public void setVideoSource(Object obj) {
                this.videoSource = obj;
            }
        }

        public static class ChannelBean {
            private String channelId;
            private Object defaultCodeRate;
            private String isOnlyAudio;
            private String multirateEnabled;
            private String multirates;
            private String pureRtcEnabled;
            private String rtcAudioSubEnabled;
            private String rtcType;
            private String trl;

            public String getChannelId() {
                return this.channelId;
            }

            public Object getDefaultCodeRate() {
                return this.defaultCodeRate;
            }

            public String getIsOnlyAudio() {
                return this.isOnlyAudio;
            }

            public String getMultirateEnabled() {
                return this.multirateEnabled;
            }

            public String getMultirates() {
                return this.multirates;
            }

            public String getPureRtcEnabled() {
                return this.pureRtcEnabled;
            }

            public String getRtcAudioSubEnabled() {
                return this.rtcAudioSubEnabled;
            }

            public String getRtcType() {
                return this.rtcType;
            }

            public String getTrl() {
                return this.trl;
            }

            public void setChannelId(String str) {
                this.channelId = str;
            }

            public void setDefaultCodeRate(Object obj) {
                this.defaultCodeRate = obj;
            }

            public void setIsOnlyAudio(String str) {
                this.isOnlyAudio = str;
            }

            public void setMultirateEnabled(String str) {
                this.multirateEnabled = str;
            }

            public void setMultirates(String str) {
                this.multirates = str;
            }

            public void setPureRtcEnabled(String str) {
                this.pureRtcEnabled = str;
            }

            public void setRtcAudioSubEnabled(String str) {
                this.rtcAudioSubEnabled = str;
            }

            public void setRtcType(String str) {
                this.rtcType = str;
            }

            public void setTrl(String str) {
                this.trl = str;
            }
        }

        public static class ChannelBookingBean {
            private String booking;
            private String bookingEnabled;

            public String getBooking() {
                return this.booking;
            }

            public String getBookingEnabled() {
                return this.bookingEnabled;
            }

            public void setBooking(String str) {
                this.booking = str;
            }

            public void setBookingEnabled(String str) {
                this.bookingEnabled = str;
            }
        }

        public static class ChannelDataBean {
            private int likes;
            private int pageView;

            public int getLikes() {
                return this.likes;
            }

            public int getPageView() {
                return this.pageView;
            }

            public void setLikes(int i2) {
                this.likes = i2;
            }

            public void setPageView(int i2) {
                this.pageView = i2;
            }
        }

        public static class ChannelEnrollBean {
            private ChannelEnrollSettingBean channelEnrollSetting;
            private Object enrollFieldList;
            private String hasAudited;
            private String hasEnrolled;

            public static class ChannelEnrollSettingBean {
                private String auditEnabled;
                private String enabled;
                private String smsEnabled;
                private String title;

                public String getAuditEnabled() {
                    return this.auditEnabled;
                }

                public String getEnabled() {
                    return this.enabled;
                }

                public String getSmsEnabled() {
                    return this.smsEnabled;
                }

                public String getTitle() {
                    return this.title;
                }

                public void setAuditEnabled(String str) {
                    this.auditEnabled = str;
                }

                public void setEnabled(String str) {
                    this.enabled = str;
                }

                public void setSmsEnabled(String str) {
                    this.smsEnabled = str;
                }

                public void setTitle(String str) {
                    this.title = str;
                }
            }

            public ChannelEnrollSettingBean getChannelEnrollSetting() {
                return this.channelEnrollSetting;
            }

            public Object getEnrollFieldList() {
                return this.enrollFieldList;
            }

            public String getHasAudited() {
                return this.hasAudited;
            }

            public String getHasEnrolled() {
                return this.hasEnrolled;
            }

            public void setChannelEnrollSetting(ChannelEnrollSettingBean channelEnrollSettingBean) {
                this.channelEnrollSetting = channelEnrollSettingBean;
            }

            public void setEnrollFieldList(Object obj) {
                this.enrollFieldList = obj;
            }

            public void setHasAudited(String str) {
                this.hasAudited = str;
            }

            public void setHasEnrolled(String str) {
                this.hasEnrolled = str;
            }
        }

        public static class ChannelItemAvailStateBean {
            private String pureRtcAvailState;

            public String getPureRtcAvailState() {
                return this.pureRtcAvailState;
            }

            public void setPureRtcAvailState(String str) {
                this.pureRtcAvailState = str;
            }
        }

        public static class ChannelMenusBean {
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

        public static class ChannelPlaybackBean {
            private String enablePlayBack;
            private String hasPlaybackVideo;
            private String hasRecordFile;
            private String playbackOrigin;
            private Object playbackType;
            private Object recordFile;
            private String sectionEnabled;
            private TargetPlaybackVideoBean targetPlaybackVideo;
            private String targetSessionId;
            private String type;
            private Object vodSessionId;
            private Object vodUrl;
            private String vodUserStatus;

            public static class TargetPlaybackVideoBean {
                private String channelSessionId;
                private String duration;
                private String fileUrl;
                private String originSessionId;
                private int seed;
                private String userId;
                private String videoId;
                private String videoPoolId;

                public String getChannelSessionId() {
                    return this.channelSessionId;
                }

                public String getDuration() {
                    return this.duration;
                }

                public String getFileUrl() {
                    return this.fileUrl;
                }

                public String getOriginSessionId() {
                    return this.originSessionId;
                }

                public int getSeed() {
                    return this.seed;
                }

                public String getUserId() {
                    return this.userId;
                }

                public String getVideoId() {
                    return this.videoId;
                }

                public String getVideoPoolId() {
                    return this.videoPoolId;
                }

                public void setChannelSessionId(String str) {
                    this.channelSessionId = str;
                }

                public void setDuration(String str) {
                    this.duration = str;
                }

                public void setFileUrl(String str) {
                    this.fileUrl = str;
                }

                public void setOriginSessionId(String str) {
                    this.originSessionId = str;
                }

                public void setSeed(int i2) {
                    this.seed = i2;
                }

                public void setUserId(String str) {
                    this.userId = str;
                }

                public void setVideoId(String str) {
                    this.videoId = str;
                }

                public void setVideoPoolId(String str) {
                    this.videoPoolId = str;
                }
            }

            public String getEnablePlayBack() {
                return this.enablePlayBack;
            }

            public String getHasPlaybackVideo() {
                return this.hasPlaybackVideo;
            }

            public String getHasRecordFile() {
                return this.hasRecordFile;
            }

            public String getPlaybackOrigin() {
                return this.playbackOrigin;
            }

            public Object getPlaybackType() {
                return this.playbackType;
            }

            public Object getRecordFile() {
                return this.recordFile;
            }

            public String getSectionEnabled() {
                return this.sectionEnabled;
            }

            public TargetPlaybackVideoBean getTargetPlaybackVideo() {
                return this.targetPlaybackVideo;
            }

            public String getTargetSessionId() {
                return this.targetSessionId;
            }

            public String getType() {
                return this.type;
            }

            public Object getVodSessionId() {
                return this.vodSessionId;
            }

            public Object getVodUrl() {
                return this.vodUrl;
            }

            public String getVodUserStatus() {
                return this.vodUserStatus;
            }

            public void setEnablePlayBack(String str) {
                this.enablePlayBack = str;
            }

            public void setHasPlaybackVideo(String str) {
                this.hasPlaybackVideo = str;
            }

            public void setHasRecordFile(String str) {
                this.hasRecordFile = str;
            }

            public void setPlaybackOrigin(String str) {
                this.playbackOrigin = str;
            }

            public void setPlaybackType(Object obj) {
                this.playbackType = obj;
            }

            public void setRecordFile(Object obj) {
                this.recordFile = obj;
            }

            public void setSectionEnabled(String str) {
                this.sectionEnabled = str;
            }

            public void setTargetPlaybackVideo(TargetPlaybackVideoBean targetPlaybackVideoBean) {
                this.targetPlaybackVideo = targetPlaybackVideoBean;
            }

            public void setTargetSessionId(String str) {
                this.targetSessionId = str;
            }

            public void setType(String str) {
                this.type = str;
            }

            public void setVodSessionId(Object obj) {
                this.vodSessionId = obj;
            }

            public void setVodUrl(Object obj) {
                this.vodUrl = obj;
            }

            public void setVodUserStatus(String str) {
                this.vodUserStatus = str;
            }
        }

        public static class ChannelPromotionBean {
            private String followAutoShow;
            private String followEnabled;
            private String followEntrance;
            private String followImage;
            private String followTips;

            public String getFollowAutoShow() {
                return this.followAutoShow;
            }

            public String getFollowEnabled() {
                return this.followEnabled;
            }

            public String getFollowEntrance() {
                return this.followEntrance;
            }

            public String getFollowImage() {
                return this.followImage;
            }

            public String getFollowTips() {
                return this.followTips;
            }

            public void setFollowAutoShow(String str) {
                this.followAutoShow = str;
            }

            public void setFollowEnabled(String str) {
                this.followEnabled = str;
            }

            public void setFollowEntrance(String str) {
                this.followEntrance = str;
            }

            public void setFollowImage(String str) {
                this.followImage = str;
            }

            public void setFollowTips(String str) {
                this.followTips = str;
            }
        }

        public static class ChannelSwitchBean {
            private String audioOnlyEnabled;
            private int autoPlay;
            private String autoPlayEnabled;
            private String autoStopQuestionEnabled;
            private String autoStopQuestionnaireEnabled;
            private String bookingEnabled;
            private String channelId;
            private String chatEnabled;
            private String chatOnlineNumberEnable;
            private String chatPlayBackEnabled;
            private String chatTranslateEnabled;
            private String clientChatListEnabled;
            private String closeChaterListEnable;
            private String closeDanmuEnable;
            private String closeWithdrawEnabled;
            private String consultingMenuEnabled;
            private Object createdTime;
            private String emotionEnabled;
            private String englishSettingEnabled;
            private String filterManagerMsgEnabled;
            private String forbidFirefoxEnabled;
            private String isClosePreviewEnabled;
            private Object lastModified;
            private String loginEnabled;
            private String mobileAudioEnabled;
            private String mobileWatchEnabled;
            private String praiseEnabled;
            private String pushSharingEnabled;
            private String qaMenuEnabled;
            private String questionImageEnabled;
            private String recordingProtectEnabled;
            private String redPackEnabled;
            private String remindEnabled;
            private Object rtcCdnEnabled;
            private String rtsEnabled;
            private String sendFlowersEnabled;
            private String shareBtnEnabled;
            private String showCustomMessageEnabled;
            private Object startSideRedpackEnabled;
            private Object startSideWordRedpackEnabled;
            private String subtitleTransfyEnabled;
            private Object timelyConvertEnabled;
            private String useGlobalSwitchEnabled;
            private Object userId;
            private String viewerSendImgEnabled;
            private String welcomeEnabled;

            public String getAudioOnlyEnabled() {
                return this.audioOnlyEnabled;
            }

            public int getAutoPlay() {
                return this.autoPlay;
            }

            public String getAutoPlayEnabled() {
                return this.autoPlayEnabled;
            }

            public String getAutoStopQuestionEnabled() {
                return this.autoStopQuestionEnabled;
            }

            public String getAutoStopQuestionnaireEnabled() {
                return this.autoStopQuestionnaireEnabled;
            }

            public String getBookingEnabled() {
                return this.bookingEnabled;
            }

            public String getChannelId() {
                return this.channelId;
            }

            public String getChatEnabled() {
                return this.chatEnabled;
            }

            public String getChatOnlineNumberEnable() {
                return this.chatOnlineNumberEnable;
            }

            public String getChatPlayBackEnabled() {
                return this.chatPlayBackEnabled;
            }

            public String getChatTranslateEnabled() {
                return this.chatTranslateEnabled;
            }

            public String getClientChatListEnabled() {
                return this.clientChatListEnabled;
            }

            public String getCloseChaterListEnable() {
                return this.closeChaterListEnable;
            }

            public String getCloseDanmuEnable() {
                return this.closeDanmuEnable;
            }

            public String getCloseWithdrawEnabled() {
                return this.closeWithdrawEnabled;
            }

            public String getConsultingMenuEnabled() {
                return this.consultingMenuEnabled;
            }

            public Object getCreatedTime() {
                return this.createdTime;
            }

            public String getEmotionEnabled() {
                return this.emotionEnabled;
            }

            public String getEnglishSettingEnabled() {
                return this.englishSettingEnabled;
            }

            public String getFilterManagerMsgEnabled() {
                return this.filterManagerMsgEnabled;
            }

            public String getForbidFirefoxEnabled() {
                return this.forbidFirefoxEnabled;
            }

            public String getIsClosePreviewEnabled() {
                return this.isClosePreviewEnabled;
            }

            public Object getLastModified() {
                return this.lastModified;
            }

            public String getLoginEnabled() {
                return this.loginEnabled;
            }

            public String getMobileAudioEnabled() {
                return this.mobileAudioEnabled;
            }

            public String getMobileWatchEnabled() {
                return this.mobileWatchEnabled;
            }

            public String getPraiseEnabled() {
                return this.praiseEnabled;
            }

            public String getPushSharingEnabled() {
                return this.pushSharingEnabled;
            }

            public String getQaMenuEnabled() {
                return this.qaMenuEnabled;
            }

            public String getQuestionImageEnabled() {
                return this.questionImageEnabled;
            }

            public String getRecordingProtectEnabled() {
                return this.recordingProtectEnabled;
            }

            public String getRedPackEnabled() {
                return this.redPackEnabled;
            }

            public String getRemindEnabled() {
                return this.remindEnabled;
            }

            public Object getRtcCdnEnabled() {
                return this.rtcCdnEnabled;
            }

            public String getRtsEnabled() {
                return this.rtsEnabled;
            }

            public String getSendFlowersEnabled() {
                return this.sendFlowersEnabled;
            }

            public String getShareBtnEnabled() {
                return this.shareBtnEnabled;
            }

            public String getShowCustomMessageEnabled() {
                return this.showCustomMessageEnabled;
            }

            public Object getStartSideRedpackEnabled() {
                return this.startSideRedpackEnabled;
            }

            public Object getStartSideWordRedpackEnabled() {
                return this.startSideWordRedpackEnabled;
            }

            public String getSubtitleTransfyEnabled() {
                return this.subtitleTransfyEnabled;
            }

            public Object getTimelyConvertEnabled() {
                return this.timelyConvertEnabled;
            }

            public String getUseGlobalSwitchEnabled() {
                return this.useGlobalSwitchEnabled;
            }

            public Object getUserId() {
                return this.userId;
            }

            public String getViewerSendImgEnabled() {
                return this.viewerSendImgEnabled;
            }

            public String getWelcomeEnabled() {
                return this.welcomeEnabled;
            }

            public void setAudioOnlyEnabled(String str) {
                this.audioOnlyEnabled = str;
            }

            public void setAutoPlay(int i2) {
                this.autoPlay = i2;
            }

            public void setAutoPlayEnabled(String str) {
                this.autoPlayEnabled = str;
            }

            public void setAutoStopQuestionEnabled(String str) {
                this.autoStopQuestionEnabled = str;
            }

            public void setAutoStopQuestionnaireEnabled(String str) {
                this.autoStopQuestionnaireEnabled = str;
            }

            public void setBookingEnabled(String str) {
                this.bookingEnabled = str;
            }

            public void setChannelId(String str) {
                this.channelId = str;
            }

            public void setChatEnabled(String str) {
                this.chatEnabled = str;
            }

            public void setChatOnlineNumberEnable(String str) {
                this.chatOnlineNumberEnable = str;
            }

            public void setChatPlayBackEnabled(String str) {
                this.chatPlayBackEnabled = str;
            }

            public void setChatTranslateEnabled(String str) {
                this.chatTranslateEnabled = str;
            }

            public void setClientChatListEnabled(String str) {
                this.clientChatListEnabled = str;
            }

            public void setCloseChaterListEnable(String str) {
                this.closeChaterListEnable = str;
            }

            public void setCloseDanmuEnable(String str) {
                this.closeDanmuEnable = str;
            }

            public void setCloseWithdrawEnabled(String str) {
                this.closeWithdrawEnabled = str;
            }

            public void setConsultingMenuEnabled(String str) {
                this.consultingMenuEnabled = str;
            }

            public void setCreatedTime(Object obj) {
                this.createdTime = obj;
            }

            public void setEmotionEnabled(String str) {
                this.emotionEnabled = str;
            }

            public void setEnglishSettingEnabled(String str) {
                this.englishSettingEnabled = str;
            }

            public void setFilterManagerMsgEnabled(String str) {
                this.filterManagerMsgEnabled = str;
            }

            public void setForbidFirefoxEnabled(String str) {
                this.forbidFirefoxEnabled = str;
            }

            public void setIsClosePreviewEnabled(String str) {
                this.isClosePreviewEnabled = str;
            }

            public void setLastModified(Object obj) {
                this.lastModified = obj;
            }

            public void setLoginEnabled(String str) {
                this.loginEnabled = str;
            }

            public void setMobileAudioEnabled(String str) {
                this.mobileAudioEnabled = str;
            }

            public void setMobileWatchEnabled(String str) {
                this.mobileWatchEnabled = str;
            }

            public void setPraiseEnabled(String str) {
                this.praiseEnabled = str;
            }

            public void setPushSharingEnabled(String str) {
                this.pushSharingEnabled = str;
            }

            public void setQaMenuEnabled(String str) {
                this.qaMenuEnabled = str;
            }

            public void setQuestionImageEnabled(String str) {
                this.questionImageEnabled = str;
            }

            public void setRecordingProtectEnabled(String str) {
                this.recordingProtectEnabled = str;
            }

            public void setRedPackEnabled(String str) {
                this.redPackEnabled = str;
            }

            public void setRemindEnabled(String str) {
                this.remindEnabled = str;
            }

            public void setRtcCdnEnabled(Object obj) {
                this.rtcCdnEnabled = obj;
            }

            public void setRtsEnabled(String str) {
                this.rtsEnabled = str;
            }

            public void setSendFlowersEnabled(String str) {
                this.sendFlowersEnabled = str;
            }

            public void setShareBtnEnabled(String str) {
                this.shareBtnEnabled = str;
            }

            public void setShowCustomMessageEnabled(String str) {
                this.showCustomMessageEnabled = str;
            }

            public void setStartSideRedpackEnabled(Object obj) {
                this.startSideRedpackEnabled = obj;
            }

            public void setStartSideWordRedpackEnabled(Object obj) {
                this.startSideWordRedpackEnabled = obj;
            }

            public void setSubtitleTransfyEnabled(String str) {
                this.subtitleTransfyEnabled = str;
            }

            public void setTimelyConvertEnabled(Object obj) {
                this.timelyConvertEnabled = obj;
            }

            public void setUseGlobalSwitchEnabled(String str) {
                this.useGlobalSwitchEnabled = str;
            }

            public void setUserId(Object obj) {
                this.userId = obj;
            }

            public void setViewerSendImgEnabled(String str) {
                this.viewerSendImgEnabled = str;
            }

            public void setWelcomeEnabled(String str) {
                this.welcomeEnabled = str;
            }
        }

        public static class ChannelTeacherBean {
            private String actor;
            private String avatar;
            private String checkinEnabled;
            private String globalSettingEnabled;
            private String lotteryEnabled;
            private String nickname;
            private String notifyEnabled;
            private String userId;
            private String voteEnabled;

            public String getActor() {
                return this.actor;
            }

            public String getAvatar() {
                return this.avatar;
            }

            public String getCheckinEnabled() {
                return this.checkinEnabled;
            }

            public String getGlobalSettingEnabled() {
                return this.globalSettingEnabled;
            }

            public String getLotteryEnabled() {
                return this.lotteryEnabled;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getNotifyEnabled() {
                return this.notifyEnabled;
            }

            public String getUserId() {
                return this.userId;
            }

            public String getVoteEnabled() {
                return this.voteEnabled;
            }

            public void setActor(String str) {
                this.actor = str;
            }

            public void setAvatar(String str) {
                this.avatar = str;
            }

            public void setCheckinEnabled(String str) {
                this.checkinEnabled = str;
            }

            public void setGlobalSettingEnabled(String str) {
                this.globalSettingEnabled = str;
            }

            public void setLotteryEnabled(String str) {
                this.lotteryEnabled = str;
            }

            public void setNickname(String str) {
                this.nickname = str;
            }

            public void setNotifyEnabled(String str) {
                this.notifyEnabled = str;
            }

            public void setUserId(String str) {
                this.userId = str;
            }

            public void setVoteEnabled(String str) {
                this.voteEnabled = str;
            }
        }

        public static class CurrentChannelBean {
            private String channelId;
            private Object defaultCodeRate;
            private String isOnlyAudio;
            private String multirateEnabled;
            private String multirates;
            private String pureRtcEnabled;
            private String rtcAudioSubEnabled;
            private String rtcType;
            private String trl;

            public String getChannelId() {
                return this.channelId;
            }

            public Object getDefaultCodeRate() {
                return this.defaultCodeRate;
            }

            public String getIsOnlyAudio() {
                return this.isOnlyAudio;
            }

            public String getMultirateEnabled() {
                return this.multirateEnabled;
            }

            public String getMultirates() {
                return this.multirates;
            }

            public String getPureRtcEnabled() {
                return this.pureRtcEnabled;
            }

            public String getRtcAudioSubEnabled() {
                return this.rtcAudioSubEnabled;
            }

            public String getRtcType() {
                return this.rtcType;
            }

            public String getTrl() {
                return this.trl;
            }

            public void setChannelId(String str) {
                this.channelId = str;
            }

            public void setDefaultCodeRate(Object obj) {
                this.defaultCodeRate = obj;
            }

            public void setIsOnlyAudio(String str) {
                this.isOnlyAudio = str;
            }

            public void setMultirateEnabled(String str) {
                this.multirateEnabled = str;
            }

            public void setMultirates(String str) {
                this.multirates = str;
            }

            public void setPureRtcEnabled(String str) {
                this.pureRtcEnabled = str;
            }

            public void setRtcAudioSubEnabled(String str) {
                this.rtcAudioSubEnabled = str;
            }

            public void setRtcType(String str) {
                this.rtcType = str;
            }

            public void setTrl(String str) {
                this.trl = str;
            }
        }

        public static class DomainDtoBean {
            private String assetsUrlPrefix;
            private String chatApiDomain;
            private String chatDomain;
            private String watchDomain;

            public String getAssetsUrlPrefix() {
                return this.assetsUrlPrefix;
            }

            public String getChatApiDomain() {
                return this.chatApiDomain;
            }

            public String getChatDomain() {
                return this.chatDomain;
            }

            public String getWatchDomain() {
                return this.watchDomain;
            }

            public void setAssetsUrlPrefix(String str) {
                this.assetsUrlPrefix = str;
            }

            public void setChatApiDomain(String str) {
                this.chatApiDomain = str;
            }

            public void setChatDomain(String str) {
                this.chatDomain = str;
            }

            public void setWatchDomain(String str) {
                this.watchDomain = str;
            }
        }

        public static class DonateSettingBean {
            private double cashMin;
            private List<Double> cashes;
            private String donateCashEnabled;
            private String donateGoodEnabled;
            private String donatePointEnabled;
            private Object donateTips;
            private String globalSettingEnabled;
            private List<GoodsBean> goods;
            private Object pointUnit;

            public static class GoodsBean {
                private String goodEnabled;
                private String goodImg;
                private String goodName;
                private float goodPrice;

                public String getGoodEnabled() {
                    return this.goodEnabled;
                }

                public String getGoodImg() {
                    return this.goodImg;
                }

                public String getGoodName() {
                    return this.goodName;
                }

                public float getGoodPrice() {
                    return this.goodPrice;
                }

                public void setGoodEnabled(String str) {
                    this.goodEnabled = str;
                }

                public void setGoodImg(String str) {
                    this.goodImg = str;
                }

                public void setGoodName(String str) {
                    this.goodName = str;
                }

                public void setGoodPrice(float f2) {
                    this.goodPrice = f2;
                }
            }

            public double getCashMin() {
                return this.cashMin;
            }

            public List<Double> getCashes() {
                return this.cashes;
            }

            public String getDonateCashEnabled() {
                return this.donateCashEnabled;
            }

            public String getDonateGoodEnabled() {
                return this.donateGoodEnabled;
            }

            public String getDonatePointEnabled() {
                return this.donatePointEnabled;
            }

            public Object getDonateTips() {
                return this.donateTips;
            }

            public String getGlobalSettingEnabled() {
                return this.globalSettingEnabled;
            }

            public List<GoodsBean> getGoods() {
                return this.goods;
            }

            public Object getPointUnit() {
                return this.pointUnit;
            }

            public void setCashMin(double d3) {
                this.cashMin = d3;
            }

            public void setCashes(List<Double> list) {
                this.cashes = list;
            }

            public void setDonateCashEnabled(String str) {
                this.donateCashEnabled = str;
            }

            public void setDonateGoodEnabled(String str) {
                this.donateGoodEnabled = str;
            }

            public void setDonatePointEnabled(String str) {
                this.donatePointEnabled = str;
            }

            public void setDonateTips(Object obj) {
                this.donateTips = obj;
            }

            public void setGlobalSettingEnabled(String str) {
                this.globalSettingEnabled = str;
            }

            public void setGoods(List<GoodsBean> list) {
                this.goods = list;
            }

            public void setPointUnit(Object obj) {
                this.pointUnit = obj;
            }
        }

        public static class FooterSettingBean {
            private String footTextLinkProtocol;
            private String footTextLinkUrl;
            private String footerText;
            private String showFooterEnabled;

            public String getFootTextLinkProtocol() {
                return this.footTextLinkProtocol;
            }

            public String getFootTextLinkUrl() {
                return this.footTextLinkUrl;
            }

            public String getFooterText() {
                return this.footerText;
            }

            public String getShowFooterEnabled() {
                return this.showFooterEnabled;
            }

            public void setFootTextLinkProtocol(String str) {
                this.footTextLinkProtocol = str;
            }

            public void setFootTextLinkUrl(String str) {
                this.footTextLinkUrl = str;
            }

            public void setFooterText(String str) {
                this.footerText = str;
            }

            public void setShowFooterEnabled(String str) {
                this.showFooterEnabled = str;
            }
        }

        public static class InteractiveGameBean {
            private Object associatedAccount;
            private Object code;
            private String enabled;
            private Object iconUrl;

            public Object getAssociatedAccount() {
                return this.associatedAccount;
            }

            public Object getCode() {
                return this.code;
            }

            public String getEnabled() {
                return this.enabled;
            }

            public Object getIconUrl() {
                return this.iconUrl;
            }

            public void setAssociatedAccount(Object obj) {
                this.associatedAccount = obj;
            }

            public void setCode(Object obj) {
                this.code = obj;
            }

            public void setEnabled(String str) {
                this.enabled = str;
            }

            public void setIconUrl(Object obj) {
                this.iconUrl = obj;
            }
        }

        public static class MultiMeetingDetailBean {
            private Object isNeedToMainChannel;
            private String isSubChannel;
            private String mainChannelId;
            private String multiMeetingEnabled;

            public Object getIsNeedToMainChannel() {
                return this.isNeedToMainChannel;
            }

            public String getIsSubChannel() {
                return this.isSubChannel;
            }

            public String getMainChannelId() {
                return this.mainChannelId;
            }

            public String getMultiMeetingEnabled() {
                return this.multiMeetingEnabled;
            }

            public void setIsNeedToMainChannel(Object obj) {
                this.isNeedToMainChannel = obj;
            }

            public void setIsSubChannel(String str) {
                this.isSubChannel = str;
            }

            public void setMainChannelId(String str) {
                this.mainChannelId = str;
            }

            public void setMultiMeetingEnabled(String str) {
                this.multiMeetingEnabled = str;
            }
        }

        public static class PageWatchBean {
            private String banBarrageBtn;
            private int canSendBarr;
            private int chatEnable;
            private String chatToken;
            private String chatTranslateEnabled;
            private String cnAndEnLiveEnabled;
            private String forceIframe;
            private String h5PageEnabled;
            private String h5enabled;
            private String ignoreWx;
            private String isBMW;
            private String isCcb;
            private String isCpic;
            private String isDebug;
            private String isInfinitus;
            private String isOrientSecurities;
            private String isRzy;
            private String isShowSevenLanguage;
            private String isTransmitChannel;
            private String isUseSkinLight;
            private String isWanda;
            private String isWeixin;
            private String japLangEnabled;
            private String language;
            private int linkMicLimit;
            private Object marqueeType;
            private String mobilePvShowLocation;
            private String multilingualEnabled;
            private String polyvWatchDomainEnabled;
            private String pvShowEnabled;
            private String quickLiveEnabled;
            private String redpackEnabled;
            private int resolutionHeight;
            private int resolutionWidth;
            private String rtcEnabled;
            private String rtcRecordNotifyEnable;
            private String rtcRecordNotifyImg;
            private String rtcRecordNotifyTip;
            private String showInviteAccess;
            private String singleSessionVerify;
            private int streamRate;
            private String streamType;
            private Object transmitMasterChannelId;
            private String transmitType;
            private String watchCustomWeixinAuthEnabled;
            private WatchThemeBean watchTheme;
            private String watermarkContent;
            private String watermarkFontSize;
            private String watermarkOpacity;
            private String watermarkRestrict;
            private String watermarkType;
            private Object webShareCustomUrl;
            private String zone;

            public static class WatchThemeBean {
                private String aloneWatchLayout;
                private Object customPCWatchBackgroundImage;
                private Object defaultTeacherImage;
                private String firButtonColor;
                private int menuSize;
                private String newPageSkin;
                private String pageSkin;
                private String secButtonColor;
                private String themeColor;
                private String watchLayout;
                private String watchTemplateType;

                public String getAloneWatchLayout() {
                    return this.aloneWatchLayout;
                }

                public Object getCustomPCWatchBackgroundImage() {
                    return this.customPCWatchBackgroundImage;
                }

                public Object getDefaultTeacherImage() {
                    return this.defaultTeacherImage;
                }

                public String getFirButtonColor() {
                    return this.firButtonColor;
                }

                public int getMenuSize() {
                    return this.menuSize;
                }

                public String getNewPageSkin() {
                    return this.newPageSkin;
                }

                public String getPageSkin() {
                    return this.pageSkin;
                }

                public String getSecButtonColor() {
                    return this.secButtonColor;
                }

                public String getThemeColor() {
                    return this.themeColor;
                }

                public String getWatchLayout() {
                    return this.watchLayout;
                }

                public String getWatchTemplateType() {
                    return this.watchTemplateType;
                }

                public void setAloneWatchLayout(String str) {
                    this.aloneWatchLayout = str;
                }

                public void setCustomPCWatchBackgroundImage(Object obj) {
                    this.customPCWatchBackgroundImage = obj;
                }

                public void setDefaultTeacherImage(Object obj) {
                    this.defaultTeacherImage = obj;
                }

                public void setFirButtonColor(String str) {
                    this.firButtonColor = str;
                }

                public void setMenuSize(int i2) {
                    this.menuSize = i2;
                }

                public void setNewPageSkin(String str) {
                    this.newPageSkin = str;
                }

                public void setPageSkin(String str) {
                    this.pageSkin = str;
                }

                public void setSecButtonColor(String str) {
                    this.secButtonColor = str;
                }

                public void setThemeColor(String str) {
                    this.themeColor = str;
                }

                public void setWatchLayout(String str) {
                    this.watchLayout = str;
                }

                public void setWatchTemplateType(String str) {
                    this.watchTemplateType = str;
                }
            }

            public String getBanBarrageBtn() {
                return this.banBarrageBtn;
            }

            public int getCanSendBarr() {
                return this.canSendBarr;
            }

            public int getChatEnable() {
                return this.chatEnable;
            }

            public String getChatToken() {
                return this.chatToken;
            }

            public String getChatTranslateEnabled() {
                return this.chatTranslateEnabled;
            }

            public String getCnAndEnLiveEnabled() {
                return this.cnAndEnLiveEnabled;
            }

            public String getForceIframe() {
                return this.forceIframe;
            }

            public String getH5PageEnabled() {
                return this.h5PageEnabled;
            }

            public String getH5enabled() {
                return this.h5enabled;
            }

            public String getIgnoreWx() {
                return this.ignoreWx;
            }

            public String getIsBMW() {
                return this.isBMW;
            }

            public String getIsCcb() {
                return this.isCcb;
            }

            public String getIsCpic() {
                return this.isCpic;
            }

            public String getIsDebug() {
                return this.isDebug;
            }

            public String getIsInfinitus() {
                return this.isInfinitus;
            }

            public String getIsOrientSecurities() {
                return this.isOrientSecurities;
            }

            public String getIsRzy() {
                return this.isRzy;
            }

            public String getIsShowSevenLanguage() {
                return this.isShowSevenLanguage;
            }

            public String getIsTransmitChannel() {
                return this.isTransmitChannel;
            }

            public String getIsUseSkinLight() {
                return this.isUseSkinLight;
            }

            public String getIsWanda() {
                return this.isWanda;
            }

            public String getIsWeixin() {
                return this.isWeixin;
            }

            public String getJapLangEnabled() {
                return this.japLangEnabled;
            }

            public String getLanguage() {
                return this.language;
            }

            public int getLinkMicLimit() {
                return this.linkMicLimit;
            }

            public Object getMarqueeType() {
                return this.marqueeType;
            }

            public String getMobilePvShowLocation() {
                return this.mobilePvShowLocation;
            }

            public String getMultilingualEnabled() {
                return this.multilingualEnabled;
            }

            public String getPolyvWatchDomainEnabled() {
                return this.polyvWatchDomainEnabled;
            }

            public String getPvShowEnabled() {
                return this.pvShowEnabled;
            }

            public String getQuickLiveEnabled() {
                return this.quickLiveEnabled;
            }

            public String getRedpackEnabled() {
                return this.redpackEnabled;
            }

            public int getResolutionHeight() {
                return this.resolutionHeight;
            }

            public int getResolutionWidth() {
                return this.resolutionWidth;
            }

            public String getRtcEnabled() {
                return this.rtcEnabled;
            }

            public String getRtcRecordNotifyEnable() {
                return this.rtcRecordNotifyEnable;
            }

            public String getRtcRecordNotifyImg() {
                return this.rtcRecordNotifyImg;
            }

            public String getRtcRecordNotifyTip() {
                return this.rtcRecordNotifyTip;
            }

            public String getShowInviteAccess() {
                return this.showInviteAccess;
            }

            public String getSingleSessionVerify() {
                return this.singleSessionVerify;
            }

            public int getStreamRate() {
                return this.streamRate;
            }

            public String getStreamType() {
                return this.streamType;
            }

            public Object getTransmitMasterChannelId() {
                return this.transmitMasterChannelId;
            }

            public String getTransmitType() {
                return this.transmitType;
            }

            public String getWatchCustomWeixinAuthEnabled() {
                return this.watchCustomWeixinAuthEnabled;
            }

            public WatchThemeBean getWatchTheme() {
                return this.watchTheme;
            }

            public String getWatermarkContent() {
                return this.watermarkContent;
            }

            public String getWatermarkFontSize() {
                return this.watermarkFontSize;
            }

            public String getWatermarkOpacity() {
                return this.watermarkOpacity;
            }

            public String getWatermarkRestrict() {
                return this.watermarkRestrict;
            }

            public String getWatermarkType() {
                return this.watermarkType;
            }

            public Object getWebShareCustomUrl() {
                return this.webShareCustomUrl;
            }

            public String getZone() {
                return this.zone;
            }

            public void setBanBarrageBtn(String str) {
                this.banBarrageBtn = str;
            }

            public void setCanSendBarr(int i2) {
                this.canSendBarr = i2;
            }

            public void setChatEnable(int i2) {
                this.chatEnable = i2;
            }

            public void setChatToken(String str) {
                this.chatToken = str;
            }

            public void setChatTranslateEnabled(String str) {
                this.chatTranslateEnabled = str;
            }

            public void setCnAndEnLiveEnabled(String str) {
                this.cnAndEnLiveEnabled = str;
            }

            public void setForceIframe(String str) {
                this.forceIframe = str;
            }

            public void setH5PageEnabled(String str) {
                this.h5PageEnabled = str;
            }

            public void setH5enabled(String str) {
                this.h5enabled = str;
            }

            public void setIgnoreWx(String str) {
                this.ignoreWx = str;
            }

            public void setIsBMW(String str) {
                this.isBMW = str;
            }

            public void setIsCcb(String str) {
                this.isCcb = str;
            }

            public void setIsCpic(String str) {
                this.isCpic = str;
            }

            public void setIsDebug(String str) {
                this.isDebug = str;
            }

            public void setIsInfinitus(String str) {
                this.isInfinitus = str;
            }

            public void setIsOrientSecurities(String str) {
                this.isOrientSecurities = str;
            }

            public void setIsRzy(String str) {
                this.isRzy = str;
            }

            public void setIsShowSevenLanguage(String str) {
                this.isShowSevenLanguage = str;
            }

            public void setIsTransmitChannel(String str) {
                this.isTransmitChannel = str;
            }

            public void setIsUseSkinLight(String str) {
                this.isUseSkinLight = str;
            }

            public void setIsWanda(String str) {
                this.isWanda = str;
            }

            public void setIsWeixin(String str) {
                this.isWeixin = str;
            }

            public void setJapLangEnabled(String str) {
                this.japLangEnabled = str;
            }

            public void setLanguage(String str) {
                this.language = str;
            }

            public void setLinkMicLimit(int i2) {
                this.linkMicLimit = i2;
            }

            public void setMarqueeType(Object obj) {
                this.marqueeType = obj;
            }

            public void setMobilePvShowLocation(String str) {
                this.mobilePvShowLocation = str;
            }

            public void setMultilingualEnabled(String str) {
                this.multilingualEnabled = str;
            }

            public void setPolyvWatchDomainEnabled(String str) {
                this.polyvWatchDomainEnabled = str;
            }

            public void setPvShowEnabled(String str) {
                this.pvShowEnabled = str;
            }

            public void setQuickLiveEnabled(String str) {
                this.quickLiveEnabled = str;
            }

            public void setRedpackEnabled(String str) {
                this.redpackEnabled = str;
            }

            public void setResolutionHeight(int i2) {
                this.resolutionHeight = i2;
            }

            public void setResolutionWidth(int i2) {
                this.resolutionWidth = i2;
            }

            public void setRtcEnabled(String str) {
                this.rtcEnabled = str;
            }

            public void setRtcRecordNotifyEnable(String str) {
                this.rtcRecordNotifyEnable = str;
            }

            public void setRtcRecordNotifyImg(String str) {
                this.rtcRecordNotifyImg = str;
            }

            public void setRtcRecordNotifyTip(String str) {
                this.rtcRecordNotifyTip = str;
            }

            public void setShowInviteAccess(String str) {
                this.showInviteAccess = str;
            }

            public void setSingleSessionVerify(String str) {
                this.singleSessionVerify = str;
            }

            public void setStreamRate(int i2) {
                this.streamRate = i2;
            }

            public void setStreamType(String str) {
                this.streamType = str;
            }

            public void setTransmitMasterChannelId(Object obj) {
                this.transmitMasterChannelId = obj;
            }

            public void setTransmitType(String str) {
                this.transmitType = str;
            }

            public void setWatchCustomWeixinAuthEnabled(String str) {
                this.watchCustomWeixinAuthEnabled = str;
            }

            public void setWatchTheme(WatchThemeBean watchThemeBean) {
                this.watchTheme = watchThemeBean;
            }

            public void setWatermarkContent(String str) {
                this.watermarkContent = str;
            }

            public void setWatermarkFontSize(String str) {
                this.watermarkFontSize = str;
            }

            public void setWatermarkOpacity(String str) {
                this.watermarkOpacity = str;
            }

            public void setWatermarkRestrict(String str) {
                this.watermarkRestrict = str;
            }

            public void setWatermarkType(String str) {
                this.watermarkType = str;
            }

            public void setWebShareCustomUrl(Object obj) {
                this.webShareCustomUrl = obj;
            }

            public void setZone(String str) {
                this.zone = str;
            }
        }

        public static class PlayerSettingModelBean {
            private int autoPlay;
            private String closeDanmu;
            private String h5LowLatency;
            private Object isExpired;
            private String logoHref;
            private String logoImage;
            private int logoOpacity;
            private String logoPosition;
            private String playerColor;
            private String playerTheme;
            private String playerType;
            private String showDanmuInfoEnabled;
            private String switchPlayerEnabled;

            public int getAutoPlay() {
                return this.autoPlay;
            }

            public String getCloseDanmu() {
                return this.closeDanmu;
            }

            public String getH5LowLatency() {
                return this.h5LowLatency;
            }

            public Object getIsExpired() {
                return this.isExpired;
            }

            public String getLogoHref() {
                return this.logoHref;
            }

            public String getLogoImage() {
                return this.logoImage;
            }

            public int getLogoOpacity() {
                return this.logoOpacity;
            }

            public String getLogoPosition() {
                return this.logoPosition;
            }

            public String getPlayerColor() {
                return this.playerColor;
            }

            public String getPlayerTheme() {
                return this.playerTheme;
            }

            public String getPlayerType() {
                return this.playerType;
            }

            public String getShowDanmuInfoEnabled() {
                return this.showDanmuInfoEnabled;
            }

            public String getSwitchPlayerEnabled() {
                return this.switchPlayerEnabled;
            }

            public void setAutoPlay(int i2) {
                this.autoPlay = i2;
            }

            public void setCloseDanmu(String str) {
                this.closeDanmu = str;
            }

            public void setH5LowLatency(String str) {
                this.h5LowLatency = str;
            }

            public void setIsExpired(Object obj) {
                this.isExpired = obj;
            }

            public void setLogoHref(String str) {
                this.logoHref = str;
            }

            public void setLogoImage(String str) {
                this.logoImage = str;
            }

            public void setLogoOpacity(int i2) {
                this.logoOpacity = i2;
            }

            public void setLogoPosition(String str) {
                this.logoPosition = str;
            }

            public void setPlayerColor(String str) {
                this.playerColor = str;
            }

            public void setPlayerTheme(String str) {
                this.playerTheme = str;
            }

            public void setPlayerType(String str) {
                this.playerType = str;
            }

            public void setShowDanmuInfoEnabled(String str) {
                this.showDanmuInfoEnabled = str;
            }

            public void setSwitchPlayerEnabled(String str) {
                this.switchPlayerEnabled = str;
            }
        }

        public static class RedpackRainSkinBean {
            private String backgroundImage;
            private String customEnabled;
            private List<String> images;

            public String getBackgroundImage() {
                return this.backgroundImage;
            }

            public String getCustomEnabled() {
                return this.customEnabled;
            }

            public List<String> getImages() {
                return this.images;
            }

            public void setBackgroundImage(String str) {
                this.backgroundImage = str;
            }

            public void setCustomEnabled(String str) {
                this.customEnabled = str;
            }

            public void setImages(List<String> list) {
                this.images = list;
            }
        }

        public static class RedpackSkinBean {
            private Object redpackImage;

            public Object getRedpackImage() {
                return this.redpackImage;
            }

            public void setRedpackImage(Object obj) {
                this.redpackImage = obj;
            }
        }

        public static class UserConfigBean {
            private String azureADEnabled;
            private String checkSmsImageEnabled;
            private String coverImgType;
            private String multirateEnabled;
            private String redPackRainEnabled;
            private String redpackRainSkin;
            private String redpackRainSkinCustomEnabled;
            private String startSideRedpackSkinEnabled;
            private String weixinAccountFunctionEnabled;

            public String getAzureADEnabled() {
                return this.azureADEnabled;
            }

            public String getCheckSmsImageEnabled() {
                return this.checkSmsImageEnabled;
            }

            public String getCoverImgType() {
                return this.coverImgType;
            }

            public String getMultirateEnabled() {
                return this.multirateEnabled;
            }

            public String getRedPackRainEnabled() {
                return this.redPackRainEnabled;
            }

            public String getRedpackRainSkin() {
                return this.redpackRainSkin;
            }

            public String getRedpackRainSkinCustomEnabled() {
                return this.redpackRainSkinCustomEnabled;
            }

            public String getStartSideRedpackSkinEnabled() {
                return this.startSideRedpackSkinEnabled;
            }

            public String getWeixinAccountFunctionEnabled() {
                return this.weixinAccountFunctionEnabled;
            }

            public void setAzureADEnabled(String str) {
                this.azureADEnabled = str;
            }

            public void setCheckSmsImageEnabled(String str) {
                this.checkSmsImageEnabled = str;
            }

            public void setCoverImgType(String str) {
                this.coverImgType = str;
            }

            public void setMultirateEnabled(String str) {
                this.multirateEnabled = str;
            }

            public void setRedPackRainEnabled(String str) {
                this.redPackRainEnabled = str;
            }

            public void setRedpackRainSkin(String str) {
                this.redpackRainSkin = str;
            }

            public void setRedpackRainSkinCustomEnabled(String str) {
                this.redpackRainSkinCustomEnabled = str;
            }

            public void setStartSideRedpackSkinEnabled(String str) {
                this.startSideRedpackSkinEnabled = str;
            }

            public void setWeixinAccountFunctionEnabled(String str) {
                this.weixinAccountFunctionEnabled = str;
            }
        }

        public static class WatchInfoBean {
            private Object channelRoomId;
            private String encodedNickname;
            private String encodedViewerId;
            private Object inviteUser;
            private String marqueeName;
            private String micUserId;
            private String userSessionId;
            private ViewerInfoBean viewerInfo;

            public static class ViewerInfoBean {
                private Object actor;
                private Object actorBgColor;
                private Object actorFColor;
                private String authType;
                private Object avatar;
                private Object enrollNickEnabled;
                private Object marqueeName;
                private String nickname;
                private Object openid;
                private Object param4;
                private Object param5;
                private Object param6;
                private Object param7;
                private Object param8;
                private Object param9;
                private Object unionId;
                private boolean verifiedMobile;
                private String viewerId;

                public Object getActor() {
                    return this.actor;
                }

                public Object getActorBgColor() {
                    return this.actorBgColor;
                }

                public Object getActorFColor() {
                    return this.actorFColor;
                }

                public String getAuthType() {
                    return this.authType;
                }

                public Object getAvatar() {
                    return this.avatar;
                }

                public Object getEnrollNickEnabled() {
                    return this.enrollNickEnabled;
                }

                public Object getMarqueeName() {
                    return this.marqueeName;
                }

                public String getNickname() {
                    return this.nickname;
                }

                public Object getOpenid() {
                    return this.openid;
                }

                public Object getParam4() {
                    return this.param4;
                }

                public Object getParam5() {
                    return this.param5;
                }

                public Object getParam6() {
                    return this.param6;
                }

                public Object getParam7() {
                    return this.param7;
                }

                public Object getParam8() {
                    return this.param8;
                }

                public Object getParam9() {
                    return this.param9;
                }

                public Object getUnionId() {
                    return this.unionId;
                }

                public String getViewerId() {
                    return this.viewerId;
                }

                public boolean isVerifiedMobile() {
                    return this.verifiedMobile;
                }

                public void setActor(Object obj) {
                    this.actor = obj;
                }

                public void setActorBgColor(Object obj) {
                    this.actorBgColor = obj;
                }

                public void setActorFColor(Object obj) {
                    this.actorFColor = obj;
                }

                public void setAuthType(String str) {
                    this.authType = str;
                }

                public void setAvatar(Object obj) {
                    this.avatar = obj;
                }

                public void setEnrollNickEnabled(Object obj) {
                    this.enrollNickEnabled = obj;
                }

                public void setMarqueeName(Object obj) {
                    this.marqueeName = obj;
                }

                public void setNickname(String str) {
                    this.nickname = str;
                }

                public void setOpenid(Object obj) {
                    this.openid = obj;
                }

                public void setParam4(Object obj) {
                    this.param4 = obj;
                }

                public void setParam5(Object obj) {
                    this.param5 = obj;
                }

                public void setParam6(Object obj) {
                    this.param6 = obj;
                }

                public void setParam7(Object obj) {
                    this.param7 = obj;
                }

                public void setParam8(Object obj) {
                    this.param8 = obj;
                }

                public void setParam9(Object obj) {
                    this.param9 = obj;
                }

                public void setUnionId(Object obj) {
                    this.unionId = obj;
                }

                public void setVerifiedMobile(boolean z2) {
                    this.verifiedMobile = z2;
                }

                public void setViewerId(String str) {
                    this.viewerId = str;
                }
            }

            public Object getChannelRoomId() {
                return this.channelRoomId;
            }

            public String getEncodedNickname() {
                return this.encodedNickname;
            }

            public String getEncodedViewerId() {
                return this.encodedViewerId;
            }

            public Object getInviteUser() {
                return this.inviteUser;
            }

            public String getMarqueeName() {
                return this.marqueeName;
            }

            public String getMicUserId() {
                return this.micUserId;
            }

            public String getUserSessionId() {
                return this.userSessionId;
            }

            public ViewerInfoBean getViewerInfo() {
                return this.viewerInfo;
            }

            public void setChannelRoomId(Object obj) {
                this.channelRoomId = obj;
            }

            public void setEncodedNickname(String str) {
                this.encodedNickname = str;
            }

            public void setEncodedViewerId(String str) {
                this.encodedViewerId = str;
            }

            public void setInviteUser(Object obj) {
                this.inviteUser = obj;
            }

            public void setMarqueeName(String str) {
                this.marqueeName = str;
            }

            public void setMicUserId(String str) {
                this.micUserId = str;
            }

            public void setUserSessionId(String str) {
                this.userSessionId = str;
            }

            public void setViewerInfo(ViewerInfoBean viewerInfoBean) {
                this.viewerInfo = viewerInfoBean;
            }
        }

        public String getAuthPcRedircetEnabled() {
            return this.authPcRedircetEnabled;
        }

        public List<AuthSettingsBean> getAuthSettings() {
            return this.authSettings;
        }

        public ChannelBean getChannel() {
            return this.channel;
        }

        public ChannelAdvertBean getChannelAdvert() {
            return this.channelAdvert;
        }

        public ChannelBookingBean getChannelBooking() {
            return this.channelBooking;
        }

        public Object getChannelCCBTemplateDto() {
            return this.channelCCBTemplateDto;
        }

        public ChannelDataBean getChannelData() {
            return this.channelData;
        }

        public ChannelEnrollBean getChannelEnroll() {
            return this.channelEnroll;
        }

        public String getChannelId() {
            return this.channelId;
        }

        public ChannelItemAvailStateBean getChannelItemAvailState() {
            return this.channelItemAvailState;
        }

        public int getChannelLiveStatusQueryFrequency() {
            return this.channelLiveStatusQueryFrequency;
        }

        public List<ChannelMenusBean> getChannelMenus() {
            return this.channelMenus;
        }

        public ChannelPlaybackBean getChannelPlayback() {
            return this.channelPlayback;
        }

        public ChannelPromotionBean getChannelPromotion() {
            return this.channelPromotion;
        }

        public String getChannelSessionId() {
            return this.channelSessionId;
        }

        public ChannelSwitchBean getChannelSwitch() {
            return this.channelSwitch;
        }

        public ChannelTeacherBean getChannelTeacher() {
            return this.channelTeacher;
        }

        public String getChannelType() {
            return this.channelType;
        }

        public int getChannelWatchQueryFrequency() {
            return this.channelWatchQueryFrequency;
        }

        public Object getChannelWxBooking() {
            return this.channelWxBooking;
        }

        public String getChatRobotEnabled() {
            return this.chatRobotEnabled;
        }

        public String getCountEnabled() {
            return this.countEnabled;
        }

        public String getCoverImg() {
            return this.coverImg;
        }

        public CurrentChannelBean getCurrentChannel() {
            return this.currentChannel;
        }

        public String getCurrentStream() {
            return this.currentStream;
        }

        public Object getCustomIcon() {
            return this.customIcon;
        }

        public String getDescription() {
            return this.description;
        }

        public DomainDtoBean getDomainDto() {
            return this.domainDto;
        }

        public DonateSettingBean getDonateSetting() {
            return this.donateSetting;
        }

        public FooterSettingBean getFooterSetting() {
            return this.footerSetting;
        }

        public Object getInfoFields() {
            return this.infoFields;
        }

        public InteractiveGameBean getInteractiveGame() {
            return this.interactiveGame;
        }

        public Object getIpCountry() {
            return this.ipCountry;
        }

        public String getIsClosePreview() {
            return this.isClosePreview;
        }

        public String getIsPullStreamSecret() {
            return this.isPullStreamSecret;
        }

        public String getLiveStatus() {
            return this.liveStatus;
        }

        public String getLiveType() {
            return this.liveType;
        }

        public long getMaxViewers() {
            return this.maxViewers;
        }

        public String getMobileEnabled() {
            return this.mobileEnabled;
        }

        public MultiMeetingDetailBean getMultiMeetingDetail() {
            return this.multiMeetingDetail;
        }

        public Object getMultiMeetings() {
            return this.multiMeetings;
        }

        public String getName() {
            return this.name;
        }

        public String getNewScene() {
            return this.newScene;
        }

        public Object getOpenid() {
            return this.openid;
        }

        public PageWatchBean getPageWatch() {
            return this.pageWatch;
        }

        public String getPlayMode() {
            return this.playMode;
        }

        public PlayerSettingModelBean getPlayerSettingModel() {
            return this.playerSettingModel;
        }

        public Object getPromoteId() {
            return this.promoteId;
        }

        public String getPublisher() {
            return this.publisher;
        }

        public RedpackRainSkinBean getRedpackRainSkin() {
            return this.redpackRainSkin;
        }

        public RedpackSkinBean getRedpackSkin() {
            return this.redpackSkin;
        }

        public String getRestrictChatEnabled() {
            return this.restrictChatEnabled;
        }

        public Object getRoomIds() {
            return this.roomIds;
        }

        public String getScene() {
            return this.scene;
        }

        public Object getSeminarHost() {
            return this.seminarHost;
        }

        public String getSplashEnabled() {
            return this.splashEnabled;
        }

        public String getSplashImg() {
            return this.splashImg;
        }

        public long getStartTime() {
            return this.startTime;
        }

        public String getStream() {
            return this.stream;
        }

        public String getStreamStatus() {
            return this.streamStatus;
        }

        public Object getTransmitMasterStream() {
            return this.transmitMasterStream;
        }

        public Object getTrialWatchTime() {
            return this.trialWatchTime;
        }

        public UserConfigBean getUserConfig() {
            return this.userConfig;
        }

        public String getUserId() {
            return this.userId;
        }

        public Object getViewerId() {
            return this.viewerId;
        }

        public String getWatchChatEnabled() {
            return this.watchChatEnabled;
        }

        public WatchInfoBean getWatchInfo() {
            return this.watchInfo;
        }

        public String getWatchStatus() {
            return this.watchStatus;
        }

        public Object getWxUser() {
            return this.wxUser;
        }

        public void setAuthPcRedircetEnabled(String str) {
            this.authPcRedircetEnabled = str;
        }

        public void setAuthSettings(List<AuthSettingsBean> list) {
            this.authSettings = list;
        }

        public void setChannel(ChannelBean channelBean) {
            this.channel = channelBean;
        }

        public void setChannelAdvert(ChannelAdvertBean channelAdvertBean) {
            this.channelAdvert = channelAdvertBean;
        }

        public void setChannelBooking(ChannelBookingBean channelBookingBean) {
            this.channelBooking = channelBookingBean;
        }

        public void setChannelCCBTemplateDto(Object obj) {
            this.channelCCBTemplateDto = obj;
        }

        public void setChannelData(ChannelDataBean channelDataBean) {
            this.channelData = channelDataBean;
        }

        public void setChannelEnroll(ChannelEnrollBean channelEnrollBean) {
            this.channelEnroll = channelEnrollBean;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setChannelItemAvailState(ChannelItemAvailStateBean channelItemAvailStateBean) {
            this.channelItemAvailState = channelItemAvailStateBean;
        }

        public void setChannelLiveStatusQueryFrequency(int i2) {
            this.channelLiveStatusQueryFrequency = i2;
        }

        public void setChannelMenus(List<ChannelMenusBean> list) {
            this.channelMenus = list;
        }

        public void setChannelPlayback(ChannelPlaybackBean channelPlaybackBean) {
            this.channelPlayback = channelPlaybackBean;
        }

        public void setChannelPromotion(ChannelPromotionBean channelPromotionBean) {
            this.channelPromotion = channelPromotionBean;
        }

        public void setChannelSessionId(String str) {
            this.channelSessionId = str;
        }

        public void setChannelSwitch(ChannelSwitchBean channelSwitchBean) {
            this.channelSwitch = channelSwitchBean;
        }

        public void setChannelTeacher(ChannelTeacherBean channelTeacherBean) {
            this.channelTeacher = channelTeacherBean;
        }

        public void setChannelType(String str) {
            this.channelType = str;
        }

        public void setChannelWatchQueryFrequency(int i2) {
            this.channelWatchQueryFrequency = i2;
        }

        public void setChannelWxBooking(Object obj) {
            this.channelWxBooking = obj;
        }

        public void setChatRobotEnabled(String str) {
            this.chatRobotEnabled = str;
        }

        public void setCountEnabled(String str) {
            this.countEnabled = str;
        }

        public void setCoverImg(String str) {
            this.coverImg = str;
        }

        public void setCurrentChannel(CurrentChannelBean currentChannelBean) {
            this.currentChannel = currentChannelBean;
        }

        public void setCurrentStream(String str) {
            this.currentStream = str;
        }

        public void setCustomIcon(Object obj) {
            this.customIcon = obj;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public void setDomainDto(DomainDtoBean domainDtoBean) {
            this.domainDto = domainDtoBean;
        }

        public void setDonateSetting(DonateSettingBean donateSettingBean) {
            this.donateSetting = donateSettingBean;
        }

        public void setFooterSetting(FooterSettingBean footerSettingBean) {
            this.footerSetting = footerSettingBean;
        }

        public void setInfoFields(Object obj) {
            this.infoFields = obj;
        }

        public void setInteractiveGame(InteractiveGameBean interactiveGameBean) {
            this.interactiveGame = interactiveGameBean;
        }

        public void setIpCountry(Object obj) {
            this.ipCountry = obj;
        }

        public void setIsClosePreview(String str) {
            this.isClosePreview = str;
        }

        public void setIsPullStreamSecret(String str) {
            this.isPullStreamSecret = str;
        }

        public void setLiveStatus(String str) {
            this.liveStatus = str;
        }

        public void setLiveType(String str) {
            this.liveType = str;
        }

        public void setMaxViewers(long j2) {
            this.maxViewers = j2;
        }

        public void setMobileEnabled(String str) {
            this.mobileEnabled = str;
        }

        public void setMultiMeetingDetail(MultiMeetingDetailBean multiMeetingDetailBean) {
            this.multiMeetingDetail = multiMeetingDetailBean;
        }

        public void setMultiMeetings(Object obj) {
            this.multiMeetings = obj;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setNewScene(String str) {
            this.newScene = str;
        }

        public void setOpenid(Object obj) {
            this.openid = obj;
        }

        public void setPageWatch(PageWatchBean pageWatchBean) {
            this.pageWatch = pageWatchBean;
        }

        public void setPlayMode(String str) {
            this.playMode = str;
        }

        public void setPlayerSettingModel(PlayerSettingModelBean playerSettingModelBean) {
            this.playerSettingModel = playerSettingModelBean;
        }

        public void setPromoteId(Object obj) {
            this.promoteId = obj;
        }

        public void setPublisher(String str) {
            this.publisher = str;
        }

        public void setRedpackRainSkin(RedpackRainSkinBean redpackRainSkinBean) {
            this.redpackRainSkin = redpackRainSkinBean;
        }

        public void setRedpackSkin(RedpackSkinBean redpackSkinBean) {
            this.redpackSkin = redpackSkinBean;
        }

        public void setRestrictChatEnabled(String str) {
            this.restrictChatEnabled = str;
        }

        public void setRoomIds(Object obj) {
            this.roomIds = obj;
        }

        public void setScene(String str) {
            this.scene = str;
        }

        public void setSeminarHost(Object obj) {
            this.seminarHost = obj;
        }

        public void setSplashEnabled(String str) {
            this.splashEnabled = str;
        }

        public void setSplashImg(String str) {
            this.splashImg = str;
        }

        public void setStartTime(long j2) {
            this.startTime = j2;
        }

        public void setStream(String str) {
            this.stream = str;
        }

        public void setStreamStatus(String str) {
            this.streamStatus = str;
        }

        public void setTransmitMasterStream(Object obj) {
            this.transmitMasterStream = obj;
        }

        public void setTrialWatchTime(Object obj) {
            this.trialWatchTime = obj;
        }

        public void setUserConfig(UserConfigBean userConfigBean) {
            this.userConfig = userConfigBean;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setViewerId(Object obj) {
            this.viewerId = obj;
        }

        public void setWatchChatEnabled(String str) {
            this.watchChatEnabled = str;
        }

        public void setWatchInfo(WatchInfoBean watchInfoBean) {
            this.watchInfo = watchInfoBean;
        }

        public void setWatchStatus(String str) {
            this.watchStatus = str;
        }

        public void setWxUser(Object obj) {
            this.wxUser = obj;
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public Object getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setError(Object obj) {
        this.error = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
