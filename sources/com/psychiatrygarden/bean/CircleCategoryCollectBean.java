package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class CircleCategoryCollectBean {
    private Long cate_id;
    private String parent_cate;
    private String today_topic_num;
    private Long user_id;

    public CircleCategoryCollectBean() {
    }

    public Long getCate_id() {
        return this.cate_id;
    }

    public String getParent_cate() {
        return this.parent_cate;
    }

    public String getToday_topic_num() {
        return this.today_topic_num;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setCate_id(Long cate_id) {
        this.cate_id = cate_id;
    }

    public void setParent_cate(String parent_cate) {
        this.parent_cate = parent_cate;
    }

    public void setToday_topic_num(String today_topic_num) {
        this.today_topic_num = today_topic_num;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public CircleCategoryCollectBean(Long cate_id) {
        this.cate_id = cate_id;
    }

    public CircleCategoryCollectBean(Long user_id, String parent_cate, Long cate_id, String today_topic_num) {
        this.user_id = user_id;
        this.parent_cate = parent_cate;
        this.cate_id = cate_id;
        this.today_topic_num = today_topic_num;
    }
}
