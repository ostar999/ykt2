package com.psychiatrygarden.bean;

import com.yikaobang.yixue.R;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes5.dex */
public enum KingKongItem {
    WRONG_QUESTION("error", "错题", R.attr.wrongimg_new),
    COLLECT_QUESTION("collection", "收藏", R.attr.collectimg_new),
    NOTE_QUESTION("note", "笔记", R.attr.noteimg_new),
    COMMENT_QUESTION(ClientCookie.COMMENT_ATTR, "评论", R.attr.pinglun_new),
    LIKE_QUESTION("praise", "点赞", R.attr.zantong_new),
    COMBINATION_QUESTION("COMBINATION", "组卷", R.attr.zujuan);

    private int iconAttr;
    private String label;
    private String type;

    KingKongItem(String type, String label, int icon) {
        this.label = label;
        this.type = type;
        this.iconAttr = icon;
    }

    public int getIconAttr() {
        return this.iconAttr;
    }

    public String getLabel() {
        return this.label;
    }

    public String getType() {
        return this.type;
    }

    public void setIconAttr(int iconAttr) {
        this.iconAttr = iconAttr;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }
}
