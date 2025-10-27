package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.pro.d;

/* loaded from: classes5.dex */
public class BookShelfDataBean implements MultiItemEntity {
    public static final int MODE_GRID = 2;
    public static final int MODE_LIST = 1;
    public static final int TYPE_ADD = 2;
    public static final int TYPE_NORMAL = 1;
    private String author;

    @SerializedName("book_id")
    private String bookId;

    @SerializedName("book_title")
    private String bookTitle;
    private String ctime;
    private String describe;
    private boolean editMode;

    @SerializedName(d.f22695q)
    private String endTime;
    private String grade;
    private String id;

    @SerializedName("is_bookshelf")
    private String isBookshelf;

    @SerializedName("last_chapter_id")
    private String lastChapterId;

    @SerializedName("last_chapter_title")
    private String lastChapterTitle;
    private String renew;
    private String renew_time;
    private boolean select;
    private String sort;
    private String thumbnail;

    @SerializedName("today_duration")
    private String todayDuration;
    private int showMode = 1;
    private int type = 1;

    public String getAuthor() {
        return this.author;
    }

    public String getBookId() {
        return this.bookId;
    }

    public String getBookTitle() {
        return this.bookTitle;
    }

    public String getCtime() {
        return this.ctime;
    }

    public String getDescribe() {
        return this.describe;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getGrade() {
        return this.grade;
    }

    public String getId() {
        return this.id;
    }

    public String getIsBookshelf() {
        return this.isBookshelf;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public String getLastChapterId() {
        return this.lastChapterId;
    }

    public String getLastChapterTitle() {
        return this.lastChapterTitle;
    }

    public String getRenew() {
        return this.renew;
    }

    public String getRenew_time() {
        return this.renew_time;
    }

    public int getShowMode() {
        return this.showMode;
    }

    public String getSort() {
        return this.sort;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getTodayDuration() {
        return this.todayDuration;
    }

    public int getType() {
        return this.type;
    }

    public boolean isEditMode() {
        return this.editMode;
    }

    public boolean isSelect() {
        return this.select;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsBookshelf(String isBookshelf) {
        this.isBookshelf = isBookshelf;
    }

    public void setLastChapterId(String lastChapterId) {
        this.lastChapterId = lastChapterId;
    }

    public void setLastChapterTitle(String lastChapterTitle) {
        this.lastChapterTitle = lastChapterTitle;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }

    public void setRenew_time(String renew_time) {
        this.renew_time = renew_time;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setShowMode(int showMode) {
        this.showMode = showMode;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setTodayDuration(String todayDuration) {
        this.todayDuration = todayDuration;
    }

    public void setType(int type) {
        this.type = type;
    }
}
