package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QuestionCacheVideoBean {
    public String category_id;
    public String chapter_id;
    public String comment_count;
    public String count;
    public String ctime;
    public String discription;
    public String download_state;
    public String duration;
    public String goods_id;
    public String id;
    public boolean isAllSelect;
    public Long isEncripted;
    public boolean isSelect;
    public String is_del;
    public String is_see;
    public String is_select;
    public String lecturer;
    public String mFormat;
    public String mQuality;
    public String parent_id;
    public int progress;
    public String savepath;
    public String service_id;
    public String share_thumb;
    public Long sort;
    public String thumb;
    public String title;
    public String vid;
    public String videsize;

    public QuestionCacheVideoBean() {
        this.mQuality = "";
        this.videsize = "0";
        this.parent_id = "0";
        this.progress = 0;
        this.isAllSelect = false;
        this.isSelect = false;
    }

    public String getCategory_id() {
        return this.category_id;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getComment_count() {
        return this.comment_count;
    }

    public String getCount() {
        return this.count;
    }

    public String getCtime() {
        return this.ctime;
    }

    public String getDiscription() {
        return this.discription;
    }

    public String getDownload_state() {
        return this.download_state;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getGoods_id() {
        return this.goods_id;
    }

    public String getId() {
        return this.id;
    }

    public Long getIsEncripted() {
        return this.isEncripted;
    }

    public String getIs_del() {
        return this.is_del;
    }

    public String getIs_see() {
        return this.is_see;
    }

    public String getIs_select() {
        return this.is_select;
    }

    public String getLecturer() {
        return this.lecturer;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public int getProgress() {
        return this.progress;
    }

    public String getSavepath() {
        return this.savepath;
    }

    public String getService_id() {
        return this.service_id;
    }

    public String getShare_thumb() {
        return this.share_thumb;
    }

    public Long getSort() {
        return this.sort;
    }

    public String getThumb() {
        return this.thumb;
    }

    public String getTitle() {
        return this.title;
    }

    public String getVid() {
        return this.vid;
    }

    public String getVidesize() {
        return this.videsize;
    }

    public String getmFormat() {
        return this.mFormat;
    }

    public String getmQuality() {
        return this.mQuality;
    }

    public boolean isAllSelect() {
        return this.isAllSelect;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setAllSelect(boolean allSelect) {
        this.isAllSelect = allSelect;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setDownload_state(String download_state) {
        this.download_state = download_state;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsEncripted(Long isEncripted) {
        this.isEncripted = isEncripted;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public void setIs_see(String is_see) {
        this.is_see = is_see;
    }

    public void setIs_select(String is_select) {
        this.is_select = is_select;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setShare_thumb(String share_thumb) {
        this.share_thumb = share_thumb;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setVidesize(String videsize) {
        this.videsize = videsize;
    }

    public void setmFormat(String mFormat) {
        this.mFormat = mFormat;
    }

    public void setmQuality(String mQuality) {
        this.mQuality = mQuality;
    }

    public QuestionCacheVideoBean(String id, String title, String discription, String thumb, String category_id, String chapter_id, String vid, String ctime, String is_del, Long sort, String duration, String share_thumb, String lecturer, String comment_count, String count, String is_see, String mQuality, String mFormat, Long isEncripted, String download_state, String service_id, String goods_id, String is_select, String savepath, String videsize) {
        this.parent_id = "0";
        this.progress = 0;
        this.isAllSelect = false;
        this.isSelect = false;
        this.id = id;
        this.title = title;
        this.discription = discription;
        this.thumb = thumb;
        this.category_id = category_id;
        this.chapter_id = chapter_id;
        this.vid = vid;
        this.ctime = ctime;
        this.is_del = is_del;
        this.sort = sort;
        this.duration = duration;
        this.share_thumb = share_thumb;
        this.lecturer = lecturer;
        this.comment_count = comment_count;
        this.count = count;
        this.is_see = is_see;
        this.mQuality = mQuality;
        this.mFormat = mFormat;
        this.isEncripted = isEncripted;
        this.download_state = download_state;
        this.service_id = service_id;
        this.goods_id = goods_id;
        this.is_select = is_select;
        this.savepath = savepath;
        this.videsize = videsize;
    }
}
