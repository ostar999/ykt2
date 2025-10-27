package com.psychiatrygarden.bean;

import android.text.TextUtils;
import com.chad.library.adapter.base.entity.JSectionEntity;

/* loaded from: classes5.dex */
public class NewHomeKingKongItem extends JSectionEntity {
    private String book_id;
    private String chapter_id;
    private String cid;
    private String collection_id;
    private String course_id;
    private String course_is_del;
    private String cover_img;
    private String daily_task;
    private String description;
    private String goods_id;
    private String goods_type;
    private String group_id;
    private String h5_path;
    private String icon;
    private String id;
    private String is_del;
    private String is_live_broadcast;
    private String is_permission;
    private String is_rich_text;
    private String jpush_type;
    private String json_path;
    private String label;
    private String module_type;
    private String name;
    private String order_no;
    private String push_type;
    private String qr_code;
    private String question_file;
    private String school_id;
    private String series;
    private String show_title;
    private String show_type;
    private String start_position;
    private String title;
    private String type;
    private String we_chat_id;
    private String web_link;

    public String getBook_id() {
        return this.book_id;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getCid() {
        return this.cid;
    }

    public String getCollection_id() {
        return this.collection_id;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getCourse_is_del() {
        return this.course_is_del;
    }

    public String getCover_img() {
        return this.cover_img;
    }

    public String getDaily_task() {
        return this.daily_task;
    }

    public String getDescription() {
        return this.description;
    }

    public String getGoods_id() {
        return this.goods_id;
    }

    public String getGoods_type() {
        return this.goods_type;
    }

    public String getGroup_id() {
        return this.group_id;
    }

    public String getH5_path() {
        return this.h5_path;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getId() {
        return this.id;
    }

    public String getIs_del() {
        return this.is_del;
    }

    public String getIs_live_broadcast() {
        return this.is_live_broadcast;
    }

    public String getIs_permission() {
        return this.is_permission;
    }

    public String getIs_rich_text() {
        return this.is_rich_text;
    }

    @Override // com.chad.library.adapter.base.entity.JSectionEntity, com.chad.library.adapter.base.entity.SectionEntity, com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return (TextUtils.isEmpty(getIs_live_broadcast()) || !getIs_live_broadcast().equals("1")) ? 1 : 0;
    }

    public String getJpush_type() {
        return this.jpush_type;
    }

    public String getJson_path() {
        return this.json_path;
    }

    public String getLabel() {
        return this.label;
    }

    public String getModule_type() {
        return this.module_type;
    }

    public String getName() {
        return this.name;
    }

    public String getOrder_no() {
        return this.order_no;
    }

    public String getPush_type() {
        return this.push_type;
    }

    public String getQr_code() {
        return this.qr_code;
    }

    public String getQuestion_file() {
        return this.question_file;
    }

    public String getSchool_id() {
        return this.school_id;
    }

    public String getSeries() {
        return this.series;
    }

    public String getShow_title() {
        return this.show_title;
    }

    public String getShow_type() {
        return this.show_type;
    }

    public String getStart_position() {
        return this.start_position;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getWe_chat_id() {
        return this.we_chat_id;
    }

    public String getWeb_link() {
        return this.web_link;
    }

    @Override // com.chad.library.adapter.base.entity.SectionEntity
    public boolean isHeader() {
        return false;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setCourse_is_del(String course_is_del) {
        this.course_is_del = course_is_del;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public void setDaily_task(String daily_task) {
        this.daily_task = daily_task;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setH5_path(String h5_path) {
        this.h5_path = h5_path;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public void setIs_live_broadcast(String is_live_broadcast) {
        this.is_live_broadcast = is_live_broadcast;
    }

    public void setIs_permission(String is_permission) {
        this.is_permission = is_permission;
    }

    public void setIs_rich_text(String is_rich_text) {
        this.is_rich_text = is_rich_text;
    }

    public void setJpush_type(String jpush_type) {
        this.jpush_type = jpush_type;
    }

    public void setJson_path(String json_path) {
        this.json_path = json_path;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setModule_type(String module_type) {
        this.module_type = module_type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public void setPush_type(String push_type) {
        this.push_type = push_type;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public void setQuestion_file(String question_file) {
        this.question_file = question_file;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public void setStart_position(String start_position) {
        this.start_position = start_position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWe_chat_id(String we_chat_id) {
        this.we_chat_id = we_chat_id;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }
}
