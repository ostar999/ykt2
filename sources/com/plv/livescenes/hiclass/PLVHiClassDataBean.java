package com.plv.livescenes.hiclass;

import cn.hutool.core.text.CharPool;
import com.plv.thirdpart.blankj.utilcode.util.ImageUtils;

/* loaded from: classes4.dex */
public class PLVHiClassDataBean {
    public static final int STATUS_IN_CLASS = 1;
    public static final int STATUS_NOT_CLASS = 0;
    public static final int STATUS_OVER_CLASS = 2;
    private String autoConnectMicroEnabled;
    private String autoRecordCourseEnabled;
    private Integer channelId;
    private Long classTime;
    private String cover;
    private Long delayClassTime;
    private Integer duration;
    private Long gapClassTime;
    private String httpDnsKey;
    private Long inClassTime;
    private String lessonCode;
    private Long lessonEndTime;
    private Long lessonId;
    private Long lessonStartTime;
    private Integer linkNumber;
    private String name;
    private String pptAnimationEnabled;
    private Integer resolution;
    private Integer rtcMaxResolution;
    private Long serverTime;
    private String startDate;
    private String startTime;
    private Integer status;

    public String getAutoConnectMicroEnabled() {
        return this.autoConnectMicroEnabled;
    }

    public String getAutoRecordCourseEnabled() {
        return this.autoRecordCourseEnabled;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public Long getClassTime() {
        return this.classTime;
    }

    public String getCover() {
        return ImageUtils.fixImageUrl(this.cover);
    }

    public Long getDelayClassTime() {
        return this.delayClassTime;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Long getGapClassTime() {
        return this.gapClassTime;
    }

    public String getHttpDnsKey() {
        return this.httpDnsKey;
    }

    public Long getInClassTime() {
        return this.inClassTime;
    }

    public String getLessonCode() {
        return this.lessonCode;
    }

    public Long getLessonEndTime() {
        return this.lessonEndTime;
    }

    public Long getLessonId() {
        return this.lessonId;
    }

    public Long getLessonStartTime() {
        return this.lessonStartTime;
    }

    public Integer getLinkNumber() {
        return this.linkNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getPptAnimationEnabled() {
        return this.pptAnimationEnabled;
    }

    public Integer getResolution() {
        return this.resolution;
    }

    public Integer getRtcMaxResolution() {
        return this.rtcMaxResolution;
    }

    public Long getServerTime() {
        return this.serverTime;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public boolean isAutoConnectMicroEnabled() {
        return "Y".equals(this.autoConnectMicroEnabled);
    }

    public void setAutoConnectMicroEnabled(String str) {
        this.autoConnectMicroEnabled = str;
    }

    public void setAutoRecordCourseEnabled(String str) {
        this.autoRecordCourseEnabled = str;
    }

    public void setChannelId(Integer num) {
        this.channelId = num;
    }

    public void setClassTime(Long l2) {
        this.classTime = l2;
    }

    public void setCover(String str) {
        this.cover = str;
    }

    public void setDelayClassTime(Long l2) {
        this.delayClassTime = l2;
    }

    public void setDuration(Integer num) {
        this.duration = num;
    }

    public void setGapClassTime(Long l2) {
        this.gapClassTime = l2;
    }

    public void setInClassTime(Long l2) {
        this.inClassTime = l2;
    }

    public void setLessonCode(String str) {
        this.lessonCode = str;
    }

    public void setLessonEndTime(Long l2) {
        this.lessonEndTime = l2;
    }

    public void setLessonId(Long l2) {
        this.lessonId = l2;
    }

    public void setLessonStartTime(Long l2) {
        this.lessonStartTime = l2;
    }

    public void setLinkNumber(Integer num) {
        this.linkNumber = num;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPptAnimationEnabled(String str) {
        this.pptAnimationEnabled = str;
    }

    public void setResolution(Integer num) {
        this.resolution = num;
    }

    public void setRtcMaxResolution(Integer num) {
        this.rtcMaxResolution = num;
    }

    public void setServerTime(Long l2) {
        this.serverTime = l2;
    }

    public void setStartDate(String str) {
        this.startDate = str;
    }

    public void setStartTime(String str) {
        this.startTime = str;
    }

    public void setStatus(Integer num) {
        this.status = num;
    }

    public String toString() {
        return "PLVHiClassDataBean{lessonId=" + this.lessonId + ", linkNumber=" + this.linkNumber + ", autoConnectMicroEnabled='" + this.autoConnectMicroEnabled + CharPool.SINGLE_QUOTE + ", autoRecordCourseEnabled='" + this.autoRecordCourseEnabled + CharPool.SINGLE_QUOTE + ", status=" + this.status + ", lessonCode='" + this.lessonCode + CharPool.SINGLE_QUOTE + ", name='" + this.name + CharPool.SINGLE_QUOTE + ", cover='" + this.cover + CharPool.SINGLE_QUOTE + ", inClassTime=" + this.inClassTime + ", delayClassTime=" + this.delayClassTime + ", gapClassTime=" + this.gapClassTime + ", serverTime=" + this.serverTime + ", classTime=" + this.classTime + ", lessonEndTime=" + this.lessonEndTime + ", lessonStartTime=" + this.lessonStartTime + ", startDate='" + this.startDate + CharPool.SINGLE_QUOTE + ", startTime='" + this.startTime + CharPool.SINGLE_QUOTE + ", duration=" + this.duration + ", channelId=" + this.channelId + ", pptAnimationEnabled='" + this.pptAnimationEnabled + CharPool.SINGLE_QUOTE + ", rtcMaxResolution=" + this.rtcMaxResolution + ", resolution=" + this.resolution + ", httpDnsKey='" + this.httpDnsKey + CharPool.SINGLE_QUOTE + '}';
    }
}
