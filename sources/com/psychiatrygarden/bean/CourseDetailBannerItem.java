package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* loaded from: classes5.dex */
public class CourseDetailBannerItem implements MultiItemEntity {
    public static final int TYPE_IMG = 1;
    public static final int TYPE_VIDEO = 2;
    private boolean clickPlay = false;
    private String imgUrl;
    private boolean loading;
    private boolean play;
    private int type;
    private String videoId;

    public CourseDetailBannerItem(String imgUrl, String videoId, int type) {
        this.imgUrl = imgUrl;
        this.videoId = videoId;
        this.type = type;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public int getType() {
        return this.type;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public boolean isClickPlay() {
        return this.clickPlay;
    }

    public boolean isLoading() {
        return this.loading;
    }

    public boolean isPlay() {
        return this.play;
    }

    public void setClickPlay(boolean clickPlay) {
        this.clickPlay = clickPlay;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
