package com.plv.business.model.video;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.business.model.PLVBaseVO;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVVideoVO implements PLVBaseVO {
    public static final String MODE_AUDIO = "audio";
    public static final String MODE_VIDEO = "video";
    private static final String TAG = "PLVVideoVO";
    private String aac_link;
    private List<PLVADMatterVO> adMatterMap;
    private long cataId;
    private List<Long> cataTree;
    private String cdn_types;
    private int df_num;
    private String disable_host;
    private String duration;
    private String enable_host;
    private List<Long> fileSize;
    private String first_image;
    private String fullmp4;
    private List<String> hls;
    private List<String> hls15X;
    private String hls15XIndex;
    private List<String> hls2;
    private String hlsIndex;
    private String hlsIndex2;
    private String hlsLevel;
    private List<String> hls_backup;
    private boolean interactiveVideo;
    private String keepsource;
    private List<String> mp4;
    private int my_br;
    private int openDanmu;
    private String out_br;
    private boolean outflow;
    private List<String> packageUrl;
    private String play_source_url;
    private PLVPlayerVO player;
    private double ratio;
    private List<String> resolution;
    private int seed;
    private int setting_type;
    private long source_fileSize;
    private int status;
    private String swf_link;
    private int teaser_show;
    private String teaser_time;
    private String teaser_url;
    private boolean timeoutFlow;
    private long timestamp;
    private String title;
    private String tsCdns;
    private List<Long> tsFileSize;
    private String validUrl;
    private String vid;
    private String videoLink;
    private Map<String, String> video_srt;
    private int seed_const = 0;
    private boolean isFromDownload = false;

    public enum PLVHlsSpeedType {
        SPEED_1X("1x"),
        SPEED_1_5X("15x");

        private final String name;

        PLVHlsSpeedType(String str) {
            this.name = str;
        }

        @Nullable
        public static PLVHlsSpeedType getHlsSpeedType(@NonNull String str) {
            PLVHlsSpeedType pLVHlsSpeedType = SPEED_1X;
            if (pLVHlsSpeedType.getName().equals(str)) {
                return pLVHlsSpeedType;
            }
            PLVHlsSpeedType pLVHlsSpeedType2 = SPEED_1_5X;
            if (pLVHlsSpeedType2.getName().equals(str)) {
                return pLVHlsSpeedType2;
            }
            return null;
        }

        public String getName() {
            return this.name;
        }
    }

    private boolean isAvalidate(int i2) {
        List<PLVADMatterVO> adMatterMap = getAdMatterMap();
        List<Long> cataTreeList = getCataTreeList();
        if (adMatterMap.isEmpty()) {
            return false;
        }
        for (PLVADMatterVO pLVADMatterVO : adMatterMap) {
            if (pLVADMatterVO.getAdType() == i2 && cataTreeList.contains(Long.valueOf(pLVADMatterVO.getCataId()))) {
                String matterUrl = pLVADMatterVO.getMatterUrl();
                if (PLVUtils.isImageUrl(matterUrl) || PLVUtils.isVideoUrl(matterUrl)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getAacLink() {
        return this.aac_link;
    }

    public String getAac_link() {
        return this.aac_link;
    }

    public List<PLVADMatterVO> getAdMatterMap() {
        return this.adMatterMap;
    }

    public long getCataId() {
        return this.cataId;
    }

    public String getCataTree() {
        StringBuilder sb = new StringBuilder();
        int size = this.cataTree.size();
        Iterator<Long> it = this.cataTree.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            sb.append(it.next().longValue());
            i2++;
            if (i2 < size) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public List<Long> getCataTreeList() {
        return this.cataTree;
    }

    public List<String> getCdnTypes() {
        return Arrays.asList(this.cdn_types.split(","));
    }

    public int getDfNum() {
        return this.df_num;
    }

    public String getDisableHost() {
        return this.disable_host;
    }

    public String getDuration() {
        return this.duration;
    }

    public long getDuration2Millisecond() {
        try {
            return (long) (Float.parseFloat(getDuration()) * 1000.0f);
        } catch (NumberFormatException e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            return 0L;
        }
    }

    public String getEnableHost() {
        return this.enable_host;
    }

    public List<Long> getFileSize() {
        return this.fileSize;
    }

    public List<Long> getFileSizeList() {
        int videoType = getVideoType();
        if (videoType == 1) {
            return getFileSize();
        }
        if (videoType == 2) {
            return getTsFileSize();
        }
        if (videoType == 3) {
            return getTsFileSize();
        }
        if (videoType != 4) {
            return getFileSize();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Long.valueOf(getSourceFileSize()));
        return arrayList;
    }

    public long getFileSizeMatchVideoType(int i2) {
        int videoType = getVideoType();
        return videoType != 1 ? videoType != 2 ? videoType != 3 ? videoType != 4 ? getFileSize(i2) : getSourceFileSize() : getTsFileLength(i2) : getTsFileLength(i2) : getFileSize(i2);
    }

    public String getFirstImage() {
        return this.first_image;
    }

    public String getFullmp4() {
        return this.fullmp4;
    }

    public List<String> getHls() {
        return this.hls;
    }

    public List<String> getHls15X() {
        return this.hls15X;
    }

    public String getHls15XIndex() {
        return this.hls15XIndex;
    }

    public List<String> getHls2() {
        return this.hls2;
    }

    public String getHlsIndex() {
        return this.hlsIndex;
    }

    public String getHlsIndex2() {
        return this.hlsIndex2;
    }

    public String getHlsLevel() {
        return this.hlsLevel;
    }

    public List<String> getHls_backup() {
        return this.hls_backup;
    }

    public String getKeepsource() {
        return this.keepsource;
    }

    public List<String> getMp4() {
        return this.mp4;
    }

    public int getMyBr() {
        return this.my_br;
    }

    public int getOpenDanmu() {
        return this.openDanmu;
    }

    public String getOutBr() {
        return this.out_br;
    }

    public List<String> getPackageUrl() {
        return this.packageUrl;
    }

    public String getPlaySourceUrl() {
        return this.play_source_url;
    }

    public PLVPlayerVO getPlayer() {
        return this.player;
    }

    public double getRatio() {
        return this.ratio;
    }

    public List<String> getResolution() {
        return this.resolution;
    }

    public int getSeed() {
        return this.seed;
    }

    public int getSeedConst() {
        return this.seed_const;
    }

    public int getSettingType() {
        return this.setting_type;
    }

    public long getSourceFileSize() {
        return this.source_fileSize;
    }

    public int getStatus() {
        return this.status;
    }

    public String getSwfLink() {
        return this.swf_link;
    }

    public int getTeaserShow() {
        return this.teaser_show;
    }

    public String getTeaserTime() {
        return this.teaser_time;
    }

    public String getTeaserUrl() {
        return this.teaser_url;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getTsCdns() {
        return Arrays.asList(this.tsCdns.split(","));
    }

    public long getTsFileLength(int i2) {
        if (this.tsFileSize.isEmpty()) {
            return getFileSize(i2);
        }
        if (this.tsFileSize.size() >= i2) {
            return this.tsFileSize.get(i2 - 1).longValue();
        }
        return -1L;
    }

    public List<Long> getTsFileSize() {
        return this.tsFileSize;
    }

    public String getValidUrl() {
        return this.validUrl;
    }

    public String getVid() {
        return this.vid;
    }

    public String getVideoLink() {
        return this.videoLink;
    }

    public Map<String, String> getVideoSRT() {
        return this.video_srt;
    }

    public int getVideoType() {
        if ("1".equals(getKeepsource())) {
            return 4;
        }
        if (getSeed() == 0 && "0".equals(getFullmp4())) {
            return 1;
        }
        if (getSeed() == 0 && "1".equals(getFullmp4())) {
            return 2;
        }
        return (getSeed() == 1 || "1".equals(getFullmp4())) ? 3 : 1;
    }

    public boolean hasAudioPath() {
        return !TextUtils.isEmpty(this.aac_link);
    }

    public boolean isFromDownload() {
        return this.isFromDownload;
    }

    public boolean isHaveAdvertFirst() {
        return isAvalidate(1);
    }

    public boolean isHaveAdvertLast() {
        return isAvalidate(3);
    }

    public boolean isHaveTeaser() {
        return getTeaserShow() != 0 && PLVUtils.isVideoUrl(getTeaserUrl());
    }

    public boolean isInteractiveVideo() {
        return this.interactiveVideo;
    }

    public boolean isOutflow() {
        return this.outflow;
    }

    public boolean isTimeoutFlow() {
        return this.timeoutFlow;
    }

    public void setAacLink(String str) {
        this.aac_link = str;
    }

    public void setAac_link(String str) {
        this.aac_link = str;
    }

    public void setAdMatterMap(List<PLVADMatterVO> list) {
        this.adMatterMap = list;
    }

    public void setCataId(long j2) {
        this.cataId = j2;
    }

    public void setCataTree(List<Long> list) {
        this.cataTree = list;
    }

    public void setCdnTypes(String str) {
        this.cdn_types = str;
    }

    public void setDfNum(int i2) {
        this.df_num = i2;
    }

    public void setDisableHost(String str) {
        this.disable_host = str;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public void setEnableHost(String str) {
        this.enable_host = str;
    }

    public void setFileSize(List<Long> list) {
        this.fileSize = list;
    }

    public void setFirstImage(String str) {
        this.first_image = str;
    }

    public void setFromDownload(boolean z2) {
        this.isFromDownload = z2;
    }

    public void setFullmp4(String str) {
        this.fullmp4 = str;
    }

    public void setHls(List<String> list) {
        this.hls = list;
    }

    public void setHls15X(List<String> list) {
        this.hls15X = list;
    }

    public void setHls15XIndex(String str) {
        this.hls15XIndex = str;
    }

    public void setHls2(List<String> list) {
        this.hls2 = list;
    }

    public void setHlsIndex(String str) {
        this.hlsIndex = str;
    }

    public void setHlsIndex2(String str) {
        this.hlsIndex2 = str;
    }

    public void setHlsLevel(String str) {
        this.hlsLevel = str;
    }

    public void setHls_backup(List<String> list) {
        this.hls_backup = list;
    }

    public void setInteractiveVideo(boolean z2) {
        this.interactiveVideo = z2;
    }

    public void setKeepsource(String str) {
        this.keepsource = str;
    }

    public void setMp4(List<String> list) {
        this.mp4 = list;
    }

    public void setMyBr(int i2) {
        this.my_br = i2;
    }

    public void setOpenDanmu(int i2) {
        this.openDanmu = i2;
    }

    public void setOutBr(String str) {
        this.out_br = str;
    }

    public void setOutflow(boolean z2) {
        this.outflow = z2;
    }

    public void setPackageUrl(List<String> list) {
        this.packageUrl = list;
    }

    public void setPlaySourceUrl(String str) {
        this.play_source_url = str;
    }

    public void setPlayer(PLVPlayerVO pLVPlayerVO) {
        this.player = pLVPlayerVO;
    }

    public void setRatio(double d3) {
        this.ratio = d3;
    }

    public void setResolution(List<String> list) {
        this.resolution = list;
    }

    public void setSeed(int i2) {
        this.seed = i2;
    }

    public void setSeedConst(int i2) {
        this.seed_const = i2;
    }

    public void setSettingType(int i2) {
        this.setting_type = i2;
    }

    public void setSourceFileSize(long j2) {
        this.source_fileSize = j2;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public void setSwfLink(String str) {
        this.swf_link = str;
    }

    public void setTeaserShow(int i2) {
        this.teaser_show = i2;
    }

    public void setTeaserTime(String str) {
        this.teaser_time = str;
    }

    public void setTeaserUrl(String str) {
        this.teaser_url = str;
    }

    public void setTimeoutFlow(boolean z2) {
        this.timeoutFlow = z2;
    }

    public void setTimestamp(long j2) {
        this.timestamp = j2;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTsCdns(String str) {
        this.tsCdns = str;
    }

    public void setTsFileSize(List<Long> list) {
        this.tsFileSize = list;
    }

    public void setValidUrl(String str) {
        this.validUrl = str;
    }

    public void setVid(String str) {
        this.vid = str;
    }

    public void setVideoLink(String str) {
        this.videoLink = str;
    }

    public void setVideoSRT(Map<String, String> map) {
        this.video_srt = map;
    }

    private long getFileSize(int i2) {
        if (this.fileSize.size() >= i2) {
            return this.fileSize.get(i2 - 1).longValue();
        }
        return -1L;
    }
}
