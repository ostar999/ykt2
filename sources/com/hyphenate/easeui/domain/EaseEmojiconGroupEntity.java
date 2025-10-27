package com.hyphenate.easeui.domain;

import com.hyphenate.easeui.domain.EaseEmojicon;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseEmojiconGroupEntity {
    private List<EaseEmojicon> emojiconList;
    private int icon;
    private String name;
    private EaseEmojicon.Type type;

    public EaseEmojiconGroupEntity() {
    }

    public List<EaseEmojicon> getEmojiconList() {
        return this.emojiconList;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public EaseEmojicon.Type getType() {
        return this.type;
    }

    public void setEmojiconList(List<EaseEmojicon> list) {
        this.emojiconList = list;
    }

    public void setIcon(int i2) {
        this.icon = i2;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setType(EaseEmojicon.Type type) {
        this.type = type;
    }

    public EaseEmojiconGroupEntity(int i2, List<EaseEmojicon> list) {
        this.icon = i2;
        this.emojiconList = list;
        this.type = EaseEmojicon.Type.NORMAL;
    }

    public EaseEmojiconGroupEntity(int i2, List<EaseEmojicon> list, EaseEmojicon.Type type) {
        this.icon = i2;
        this.emojiconList = list;
        this.type = type;
    }
}
