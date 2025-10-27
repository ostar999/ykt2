package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class CourseChapterBean {
    private String activity_id;
    private String akId;
    private String akSecret;
    private String category_id;
    private String chapter_id;
    public String collection;
    private String comment_count;
    private String count;
    private String ctime;
    private String discription;
    private String duration;
    public String expire_str;
    public long free_watch_time;
    public String goods_id;
    private String id;
    public Long isEncripted;
    private String is_del;
    private String lecturer;
    public String mFormat;
    public String mQuality;
    public String note;
    private String savepath;
    private ShareData share;
    private String share_thumb;
    private String st;
    private String thumb;
    private String title;
    private String vid;
    public String watch_permission;
    private String is_see = "0";
    private String sign_time = "";
    private String share_count = "";
    private boolean isAllSelect = false;
    private boolean isSelected = false;

    public class ShareData implements Serializable {
        private String obj_id;
        private String share_content;
        private String share_id;
        private String share_img;
        private String share_title;
        private String share_url;
        private String type = "0";

        public ShareData() {
        }

        public String getObj_id() {
            return this.obj_id;
        }

        public String getShare_content() {
            return this.share_content;
        }

        public String getShare_id() {
            return this.share_id;
        }

        public String getShare_img() {
            return this.share_img;
        }

        public String getShare_title() {
            return this.share_title;
        }

        public String getShare_url() {
            return this.share_url;
        }

        public String getType() {
            return this.type;
        }

        public void setObj_id(String obj_id) {
            this.obj_id = obj_id;
        }

        public void setShare_content(String share_content) {
            this.share_content = share_content;
        }

        public void setShare_id(String share_id) {
            this.share_id = share_id;
        }

        public void setShare_img(String share_img) {
            this.share_img = share_img;
        }

        public void setShare_title(String share_title) {
            this.share_title = share_title;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public String getActivity_id() {
        return this.activity_id;
    }

    public String getAkId() {
        return this.akId;
    }

    public String getAkSecret() {
        return this.akSecret;
    }

    public String getCategory_id() {
        return this.category_id;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getCollection() {
        return this.collection;
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

    public String getDuration() {
        return this.duration;
    }

    public String getExpire_str() {
        return this.expire_str;
    }

    public long getFree_watch_time() {
        return this.free_watch_time;
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

    public String getLecturer() {
        return this.lecturer;
    }

    public String getNote() {
        return this.note;
    }

    public String getSavepath() {
        return this.savepath;
    }

    public ShareData getShare() {
        return this.share;
    }

    public String getShare_count() {
        return this.share_count;
    }

    public String getShare_thumb() {
        return this.share_thumb;
    }

    public String getSign_time() {
        return this.sign_time;
    }

    public String getSt() {
        return this.st;
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

    public String getWatch_permission() {
        return this.watch_permission;
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

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public void setAkId(String akId) {
        this.akId = akId;
    }

    public void setAkSecret(String akSecret) {
        this.akSecret = akSecret;
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

    public void setCollection(String collection) {
        this.collection = collection;
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

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setExpire_str(String expire_str) {
        this.expire_str = expire_str;
    }

    public void setFree_watch_time(long free_watch_time) {
        this.free_watch_time = free_watch_time;
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

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public void setShare(ShareData share) {
        this.share = share;
    }

    public void setShare_count(String share_count) {
        this.share_count = share_count;
    }

    public void setShare_thumb(String share_thumb) {
        this.share_thumb = share_thumb;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public void setSt(String st) {
        this.st = st;
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

    public void setWatch_permission(String watch_permission) {
        this.watch_permission = watch_permission;
    }

    public void setmFormat(String mFormat) {
        this.mFormat = mFormat;
    }

    public void setmQuality(String mQuality) {
        this.mQuality = mQuality;
    }
}
