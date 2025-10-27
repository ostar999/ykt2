package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class Bean {
    public static final int ITEM = 0;
    public static final int SECTION = 1;
    public String disUrl;
    public String go;
    public String imageurl;
    public String isOpen;
    public int listPosition;
    public String ratingBar;
    public String score;
    public int sectionPosition;
    public String stop;
    public String text;
    public String title;
    public String tv1;
    public String tv2;
    public String tv3;
    public String tv4;
    public String tv5;
    public String txtcount;
    public int type;

    public Bean() {
    }

    public String getDisUrl() {
        return this.disUrl;
    }

    public String getGo() {
        return this.go;
    }

    public String getImageurl() {
        return this.imageurl;
    }

    public String getIsOpen() {
        return this.isOpen;
    }

    public int getListPosition() {
        return this.listPosition;
    }

    public String getRatingBar() {
        return this.ratingBar;
    }

    public String getScore() {
        return this.score;
    }

    public int getSectionPosition() {
        return this.sectionPosition;
    }

    public String getStop() {
        return this.stop;
    }

    public String getText() {
        return this.text;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTv1() {
        return this.tv1;
    }

    public String getTv2() {
        return this.tv2;
    }

    public String getTv3() {
        return this.tv3;
    }

    public String getTv4() {
        return this.tv4;
    }

    public String getTv5() {
        return this.tv5;
    }

    public String getTxtcount() {
        return this.txtcount;
    }

    public int getType() {
        return this.type;
    }

    public void setDisUrl(String disUrl) {
        this.disUrl = disUrl;
    }

    public void setGo(String go) {
        this.go = go;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    public void setRatingBar(String ratingBar) {
        this.ratingBar = ratingBar;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setSectionPosition(int sectionPosition) {
        this.sectionPosition = sectionPosition;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTv1(String tv1) {
        this.tv1 = tv1;
    }

    public void setTv2(String tv2) {
        this.tv2 = tv2;
    }

    public void setTv3(String tv3) {
        this.tv3 = tv3;
    }

    public void setTv4(String tv4) {
        this.tv4 = tv4;
    }

    public void setTv5(String tv5) {
        this.tv5 = tv5;
    }

    public void setTxtcount(String txtcount) {
        this.txtcount = txtcount;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        return this.text;
    }

    public Bean(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public Bean(int type, String text, int sectionPosition, int listPosition) {
        this.type = type;
        this.text = text;
        this.sectionPosition = sectionPosition;
        this.listPosition = listPosition;
    }

    public Bean(int type, String text, String imageurl, String tv1, String ratingBar, String tv2, String tv3, String tv4, String tv5, String go, String stop) {
        this.type = type;
        this.text = text;
        this.imageurl = imageurl;
        this.tv1 = tv1;
        this.ratingBar = ratingBar;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
        this.tv5 = tv5;
        this.go = go;
        this.stop = stop;
    }
}
