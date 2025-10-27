package com.aliyun.player.alivcplayerexpand.bean;

import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class VideoSummary {

    @SerializedName("app_id")
    private String appId;
    private boolean currentPlay;

    @SerializedName("identity_id")
    private String identityId;

    @SerializedName("question_id")
    private String questionId;

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

    public String getQuestionId() {
        return this.questionId;
    }

    public int getRealPoint() {
        if (TextUtils.isEmpty(this.timePoint) || !this.timePoint.matches(RegexPool.NUMBERS)) {
            return 0;
        }
        return Integer.parseInt(this.timePoint);
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

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setCurrentPlay(boolean z2) {
        this.currentPlay = z2;
    }

    public void setIdentityId(String str) {
        this.identityId = str;
    }

    public void setQuestionId(String str) {
        this.questionId = str;
    }

    public void setTimePoint(String str) {
        this.timePoint = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
