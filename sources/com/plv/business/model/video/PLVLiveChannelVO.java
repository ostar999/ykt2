package com.plv.business.model.video;

import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.easefun.polyv.businesssdk.model.video.PolyvLiveLinesVO;
import com.plv.business.model.PLVBaseVO;
import com.plv.foundationsdk.utils.PLVUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVLiveChannelVO implements PLVBaseVO {
    private int advertDuration;
    private String advertFlvUrl;
    private int advertHeight;
    private String advertHref;
    private String advertImage;
    private String advertType;
    private int advertWidth;
    private String bakUrl;
    private int bitNum;
    private String cameraSign;
    private int channelId;
    private String channelSessionId;
    private PLVWatermarkVO channelWatermarkModel;
    private String closeDanmuEnable;
    private String coverHref;
    private String coverImage;
    private long currentTimeMillis;
    private int currentTimeSecs;
    private String description;
    private String headAdvertClickUrl;
    private String headAdvertShowUrl;
    private boolean isList;
    private boolean isNgbEnabled;
    private String isOnlyAudio;
    private boolean isSupportRTCLive;
    private boolean isUrlProtected;
    private List<PolyvLiveLinesVO> lines;
    private String logoHref;
    private String logoImage;
    private Double logoOpacity;
    private String logoPosition;
    private String m3u8Url;
    private String m3u8Url1;
    private String m3u8Url2;
    private String m3u8Url3;
    private String marquee;
    private String marqueeAutoZoomEnabled;
    private String marqueeFontColor;
    private int marqueeFontSize;
    private String marqueeOpacity;
    private String marqueeSetting;
    private String marqueeSign;
    private String marqueeType;
    private String multirateEnabled;
    private PLVBitrateVO multirateModel;
    private String name;
    private String ngbUrl;
    private String playbackUrl;
    private String recordFileSessionId;
    private int reportFreq;
    private String sign;
    private String stopAdvertClickUrl;
    private String stopAdvertShowUrl;
    private String stream;
    private String streamSign;
    private int ts;
    private String url;
    private String userId;
    private String waitImage;

    public PLVLiveMarqueeVO generateMarqueeVo() {
        String lowerCase = PLVUtils.MD5(this.marquee + this.marqueeFontColor + this.marqueeFontSize + this.marqueeOpacity + this.marqueeType).toLowerCase();
        if (!TextUtils.isEmpty(this.marqueeSign) && !lowerCase.equals(this.marqueeSign)) {
            return null;
        }
        PLVLiveMarqueeVO pLVLiveMarqueeVO = new PLVLiveMarqueeVO(this.marquee, this.marqueeType, this.marqueeFontColor, this.marqueeFontSize, this.marqueeOpacity, this.marqueeSetting, this.marqueeAutoZoomEnabled);
        pLVLiveMarqueeVO.setUserId(this.userId);
        pLVLiveMarqueeVO.setChannelId(this.channelId + "");
        return pLVLiveMarqueeVO;
    }

    public int getAdvertDuration() {
        return this.advertDuration;
    }

    public String getAdvertFlvUrl() {
        return this.advertFlvUrl;
    }

    public int getAdvertHeight() {
        return this.advertHeight;
    }

    public String getAdvertHref() {
        return this.advertHref;
    }

    public String getAdvertImage() {
        return this.advertImage;
    }

    public String getAdvertType() {
        return this.advertType;
    }

    public int getAdvertWidth() {
        return this.advertWidth;
    }

    public String getBakUrl() {
        return this.bakUrl;
    }

    public int getBitNum() {
        return this.bitNum;
    }

    public String getCameraSign() {
        return this.cameraSign;
    }

    public int getChannelId() {
        return this.channelId;
    }

    public String getChannelSessionId() {
        return this.channelSessionId;
    }

    public PLVWatermarkVO getChannelWatermarkModel() {
        return this.channelWatermarkModel;
    }

    public String getCloseDanmuEnable() {
        return this.closeDanmuEnable;
    }

    public String getCoverHref() {
        return this.coverHref;
    }

    public String getCoverImage() {
        return this.coverImage;
    }

    public long getCurrentTimeMillis() {
        return this.currentTimeMillis;
    }

    public int getCurrentTimeSecs() {
        return this.currentTimeSecs;
    }

    public String getDescription() {
        return this.description;
    }

    public String getHeadAdvertClickUrl() {
        return this.headAdvertClickUrl;
    }

    public String getHeadAdvertShowUrl() {
        return this.headAdvertShowUrl;
    }

    public String getIsOnlyAudio() {
        return this.isOnlyAudio;
    }

    public List<PolyvLiveLinesVO> getLines() {
        return this.lines;
    }

    public String getLogoHref() {
        return this.logoHref;
    }

    public String getLogoImage() {
        return this.logoImage;
    }

    public Double getLogoOpacity() {
        return this.logoOpacity;
    }

    public String getLogoPosition() {
        return this.logoPosition;
    }

    public String getM3u8Url() {
        return this.m3u8Url;
    }

    public String getM3u8Url1() {
        return this.m3u8Url1;
    }

    public String getM3u8Url2() {
        return this.m3u8Url2;
    }

    public String getM3u8Url3() {
        return this.m3u8Url3;
    }

    public String getMarquee() {
        return this.marquee;
    }

    public String getMarqueeAutoZoomEnabled() {
        return this.marqueeAutoZoomEnabled;
    }

    public String getMarqueeFontColor() {
        return this.marqueeFontColor;
    }

    public int getMarqueeFontSize() {
        return this.marqueeFontSize;
    }

    public String getMarqueeOpacity() {
        return this.marqueeOpacity;
    }

    public String getMarqueeSetting() {
        return this.marqueeSetting;
    }

    public String getMarqueeSign() {
        return this.marqueeSign;
    }

    public String getMarqueeType() {
        return this.marqueeType;
    }

    public String getMultirateEnabled() {
        return this.multirateEnabled;
    }

    public PLVBitrateVO getMultirateModel() {
        return this.multirateModel;
    }

    public String getName() {
        return this.name;
    }

    public String getNgbUrl() {
        return this.ngbUrl;
    }

    public String getPlaybackUrl() {
        return this.playbackUrl;
    }

    public String getRecordFileSessionId() {
        return this.recordFileSessionId;
    }

    public int getReportFreq() {
        return this.reportFreq * 1000;
    }

    public String getSign() {
        return this.sign;
    }

    public String getStopAdvertClickUrl() {
        return this.stopAdvertClickUrl;
    }

    public String getStopAdvertShowUrl() {
        return this.stopAdvertShowUrl;
    }

    public String getStream() {
        return this.stream;
    }

    public String getStreamSign() {
        return this.streamSign;
    }

    public int getTs() {
        return this.ts;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getWaitImage() {
        return this.waitImage;
    }

    public boolean isList() {
        return this.isList;
    }

    public boolean isMutilrateEnable() {
        return "Y".equals(this.multirateEnabled);
    }

    public boolean isNgbEnabled() {
        return this.isNgbEnabled;
    }

    public boolean isSupportRTCLive() {
        return this.isSupportRTCLive;
    }

    public boolean isUrlProtected() {
        return this.isUrlProtected;
    }

    public void setAdvertDuration(int i2) {
        this.advertDuration = i2;
    }

    public void setAdvertFlvUrl(String str) {
        this.advertFlvUrl = str;
    }

    public void setAdvertHeight(int i2) {
        this.advertHeight = i2;
    }

    public void setAdvertHref(String str) {
        this.advertHref = str;
    }

    public void setAdvertImage(String str) {
        this.advertImage = str;
    }

    public void setAdvertType(String str) {
        this.advertType = str;
    }

    public void setAdvertWidth(int i2) {
        this.advertWidth = i2;
    }

    public void setBakUrl(String str) {
        this.bakUrl = str;
    }

    public void setBitNum(int i2) {
        this.bitNum = i2;
    }

    public void setCameraSign(String str) {
        this.cameraSign = str;
    }

    public void setChannelId(int i2) {
        this.channelId = i2;
    }

    public void setChannelSessionId(String str) {
        this.channelSessionId = str;
    }

    public void setChannelWatermarkModel(PLVWatermarkVO pLVWatermarkVO) {
        this.channelWatermarkModel = pLVWatermarkVO;
    }

    public void setCloseDanmuEnable(String str) {
        this.closeDanmuEnable = str;
    }

    public void setCoverHref(String str) {
        this.coverHref = str;
    }

    public void setCoverImage(String str) {
        this.coverImage = str;
    }

    public void setCurrentTimeMillis(long j2) {
        this.currentTimeMillis = j2;
    }

    public void setCurrentTimeSecs(int i2) {
        this.currentTimeSecs = i2;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setHeadAdvertClickUrl(String str) {
        this.headAdvertClickUrl = str;
    }

    public void setHeadAdvertShowUrl(String str) {
        this.headAdvertShowUrl = str;
    }

    public void setIsOnlyAudio(String str) {
        this.isOnlyAudio = str;
    }

    public void setLines(List<PolyvLiveLinesVO> list) {
        this.lines = list;
    }

    public void setList(boolean z2) {
        this.isList = z2;
    }

    public void setLogoHref(String str) {
        this.logoHref = str;
    }

    public void setLogoImage(String str) {
        this.logoImage = str;
    }

    public void setLogoOpacity(Double d3) {
        this.logoOpacity = d3;
    }

    public void setLogoPosition(String str) {
        this.logoPosition = str;
    }

    public void setM3u8Url(String str) {
        this.m3u8Url = str;
    }

    public void setM3u8Url1(String str) {
        this.m3u8Url1 = str;
    }

    public void setM3u8Url2(String str) {
        this.m3u8Url2 = str;
    }

    public void setM3u8Url3(String str) {
        this.m3u8Url3 = str;
    }

    public void setMarquee(String str) {
        this.marquee = str;
    }

    public void setMarqueeAutoZoomEnabled(String str) {
        this.marqueeAutoZoomEnabled = str;
    }

    public void setMarqueeFontColor(String str) {
        this.marqueeFontColor = str;
    }

    public void setMarqueeFontSize(int i2) {
        this.marqueeFontSize = i2;
    }

    public void setMarqueeOpacity(String str) {
        this.marqueeOpacity = str;
    }

    public void setMarqueeSetting(String str) {
        this.marqueeSetting = str;
    }

    public void setMarqueeSign(String str) {
        this.marqueeSign = str;
    }

    public void setMarqueeType(String str) {
        this.marqueeType = str;
    }

    public void setMultirateEnabled(String str) {
        this.multirateEnabled = str;
    }

    public void setMultirateModel(PLVBitrateVO pLVBitrateVO) {
        this.multirateModel = pLVBitrateVO;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setNgbEnabled(boolean z2) {
        this.isNgbEnabled = z2;
    }

    public void setNgbUrl(String str) {
        this.ngbUrl = str;
    }

    public void setPlaybackUrl(String str) {
        this.playbackUrl = str;
    }

    public void setRecordFileSessionId(String str) {
        this.recordFileSessionId = str;
    }

    public void setReportFreq(int i2) {
        this.reportFreq = i2;
    }

    public void setSign(String str) {
        this.sign = str;
    }

    public void setStopAdvertClickUrl(String str) {
        this.stopAdvertClickUrl = str;
    }

    public void setStopAdvertShowUrl(String str) {
        this.stopAdvertShowUrl = str;
    }

    public void setStream(String str) {
        this.stream = str;
    }

    public void setStreamSign(String str) {
        this.streamSign = str;
    }

    public void setSupportRTCLive(String str) {
        this.isSupportRTCLive = "Y".equals(str);
    }

    public void setTs(int i2) {
        this.ts = i2;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setUrlProtected(boolean z2) {
        this.isUrlProtected = z2;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setWaitImage(String str) {
        this.waitImage = str;
    }

    public String toString() {
        return "PLVLiveChannelVO{ts=" + this.ts + ", sign='" + this.sign + CharPool.SINGLE_QUOTE + ", channelId=" + this.channelId + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", name='" + this.name + CharPool.SINGLE_QUOTE + ", description='" + this.description + CharPool.SINGLE_QUOTE + ", url='" + this.url + CharPool.SINGLE_QUOTE + ", stream='" + this.stream + CharPool.SINGLE_QUOTE + ", logoImage='" + this.logoImage + CharPool.SINGLE_QUOTE + ", logoOpacity=" + this.logoOpacity + ", logoPosition='" + this.logoPosition + CharPool.SINGLE_QUOTE + ", logoHref='" + this.logoHref + CharPool.SINGLE_QUOTE + ", advertType='" + this.advertType + CharPool.SINGLE_QUOTE + ", advertDuration=" + this.advertDuration + ", advertWidth=" + this.advertWidth + ", advertHeight=" + this.advertHeight + ", advertImage='" + this.advertImage + CharPool.SINGLE_QUOTE + ", advertFlvUrl='" + this.advertFlvUrl + CharPool.SINGLE_QUOTE + ", advertHref='" + this.advertHref + CharPool.SINGLE_QUOTE + ", headAdvertShowUrl='" + this.headAdvertShowUrl + CharPool.SINGLE_QUOTE + ", headAdvertClickUrl='" + this.headAdvertClickUrl + CharPool.SINGLE_QUOTE + ", stopAdvertShowUrl='" + this.stopAdvertShowUrl + CharPool.SINGLE_QUOTE + ", stopAdvertClickUrl='" + this.stopAdvertClickUrl + CharPool.SINGLE_QUOTE + ", m3u8Url='" + this.m3u8Url + CharPool.SINGLE_QUOTE + ", m3u8Url1='" + this.m3u8Url1 + CharPool.SINGLE_QUOTE + ", m3u8Url2='" + this.m3u8Url2 + CharPool.SINGLE_QUOTE + ", m3u8Url3='" + this.m3u8Url3 + CharPool.SINGLE_QUOTE + ", currentTimeSecs=" + this.currentTimeSecs + ", streamSign='" + this.streamSign + CharPool.SINGLE_QUOTE + ", cameraSign='" + this.cameraSign + CharPool.SINGLE_QUOTE + ", isNgbEnabled=" + this.isNgbEnabled + ", isUrlProtected=" + this.isUrlProtected + ", ngbUrl='" + this.ngbUrl + CharPool.SINGLE_QUOTE + ", bakUrl='" + this.bakUrl + CharPool.SINGLE_QUOTE + ", currentTimeMillis=" + this.currentTimeMillis + ", reportFreq=" + this.reportFreq + ", coverImage='" + this.coverImage + CharPool.SINGLE_QUOTE + ", coverHref='" + this.coverHref + CharPool.SINGLE_QUOTE + ", waitImage='" + this.waitImage + CharPool.SINGLE_QUOTE + ", marquee='" + this.marquee + CharPool.SINGLE_QUOTE + ", marqueeType='" + this.marqueeType + CharPool.SINGLE_QUOTE + ", marqueeFontColor='" + this.marqueeFontColor + CharPool.SINGLE_QUOTE + ", marqueeFontSize=" + this.marqueeFontSize + ", marqueeSetting=" + this.marqueeSetting + ", marqueeOpacity='" + this.marqueeOpacity + CharPool.SINGLE_QUOTE + ", marqueeSign='" + this.marqueeSign + CharPool.SINGLE_QUOTE + ", marqueeAutoZoomEnabled='" + this.marqueeAutoZoomEnabled + CharPool.SINGLE_QUOTE + ", recordFileSessionId='" + this.recordFileSessionId + CharPool.SINGLE_QUOTE + ", playbackUrl='" + this.playbackUrl + CharPool.SINGLE_QUOTE + ", isList=" + this.isList + ", channelSessionId='" + this.channelSessionId + CharPool.SINGLE_QUOTE + ", bitNum=" + this.bitNum + ", isSupportRTCLive=" + this.isSupportRTCLive + ", multirateEnabled='" + this.multirateEnabled + CharPool.SINGLE_QUOTE + ", multirateModel=" + this.multirateModel + ", lines=" + this.lines + ", closeDanmuEnable='" + this.closeDanmuEnable + CharPool.SINGLE_QUOTE + ", isOnlyAudio='" + this.isOnlyAudio + CharPool.SINGLE_QUOTE + ", channelWatermarkModel='" + this.channelWatermarkModel + CharPool.SINGLE_QUOTE + '}';
    }
}
