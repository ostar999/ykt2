package com.plv.livescenes.model;

import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.google.gson.annotations.SerializedName;
import com.plv.livescenes.model.PLVEmotionImageVO;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVEmotionImageVO2 {

    @SerializedName("contents")
    private List<PLVEmotionImageVO.EmotionImage> contents;

    @SerializedName("pageNumber")
    private Integer pageNumber;

    @SerializedName(ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE)
    private Integer pageSize;

    @SerializedName("totalPages")
    private Integer totalPages;

    public List<PLVEmotionImageVO.EmotionImage> getContents() {
        return this.contents;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public void setContents(List<PLVEmotionImageVO.EmotionImage> list) {
        this.contents = list;
    }

    public void setPageNumber(Integer num) {
        this.pageNumber = num;
    }

    public void setPageSize(Integer num) {
        this.pageSize = num;
    }

    public void setTotalPages(Integer num) {
        this.totalPages = num;
    }
}
