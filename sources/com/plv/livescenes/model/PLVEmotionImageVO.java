package com.plv.livescenes.model;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.utils.Constants;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVEmotionImageVO {

    @SerializedName("list")
    private List<EmotionImage> list;

    @SerializedName(Constants.PARAMS_CONSTANTS.PARAMS_PAGE)
    private Integer page;

    @SerializedName(DatabaseManager.SIZE)
    private Integer size;

    @SerializedName("total")
    private Integer total;

    public static class EmotionImage {

        @SerializedName("id")
        private String id;

        @SerializedName("title")
        private String title;

        @SerializedName("url")
        private String url;

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public String getUrl() {
            return this.url;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public void setUrl(String str) {
            this.url = str;
        }
    }

    public List<EmotionImage> getList() {
        return this.list;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getSize() {
        return this.size;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setList(List<EmotionImage> list) {
        this.list = list;
    }

    public void setPage(Integer num) {
        this.page = num;
    }

    public void setSize(Integer num) {
        this.size = num;
    }

    public void setTotal(Integer num) {
        this.total = num;
    }
}
