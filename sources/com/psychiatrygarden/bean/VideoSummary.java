package com.psychiatrygarden.bean;

import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes5.dex */
public class VideoSummary {

    @SerializedName("app_id")
    private String appId;
    private boolean currentPlay;
    private boolean hasJump;

    @SerializedName("identity_id")
    private String identityId;

    @SerializedName("is_do")
    private String isDo;

    @SerializedName("module_type")
    private String moduleType;

    @SerializedName("question_id")
    private String questionId;

    @SerializedName("summary_id")
    private String summaryId;

    @SerializedName("time_point")
    private String timePoint;

    @SerializedName("point_title")
    private String title;

    public String getAppId() {
        return this.appId;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public String getIsDo() {
        return this.isDo;
    }

    public String getModuleType() {
        return this.moduleType;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public int getRealPoint() {
        if (TextUtils.isEmpty(this.timePoint) || !this.timePoint.matches(RegexPool.NUMBERS)) {
            return 0;
        }
        return Integer.parseInt(this.timePoint) * 1000;
    }

    public String getSummaryId() {
        return this.summaryId;
    }

    public String getTimePoint() {
        return this.timePoint;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isCurrentPlay() {
        return this.currentPlay;
    }

    public boolean isHasJump() {
        return this.hasJump;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setCurrentPlay(boolean currentPlay) {
        this.currentPlay = currentPlay;
    }

    public void setHasJump(boolean hasJump) {
        this.hasJump = hasJump;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public void setIsDo(String isDo) {
        this.isDo = isDo;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setSummaryId(String summaryId) {
        this.summaryId = summaryId;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
