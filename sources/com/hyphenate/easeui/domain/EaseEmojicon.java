package com.hyphenate.easeui.domain;

/* loaded from: classes4.dex */
public class EaseEmojicon {
    private int bigIcon;
    private String bigIconPath;
    private String emojiText;
    private int icon;
    private String iconPath;
    private String identityCode;
    private String name;
    private Type type;

    public enum Type {
        NORMAL,
        BIG_EXPRESSION
    }

    public EaseEmojicon() {
    }

    public static String newEmojiText(int i2) {
        return Character.charCount(i2) == 1 ? String.valueOf(i2) : new String(Character.toChars(i2));
    }

    public int getBigIcon() {
        return this.bigIcon;
    }

    public String getBigIconPath() {
        return this.bigIconPath;
    }

    public String getEmojiText() {
        return this.emojiText;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public String getIdentityCode() {
        return this.identityCode;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public void setBigIcon(int i2) {
        this.bigIcon = i2;
    }

    public void setBigIconPath(String str) {
        this.bigIconPath = str;
    }

    public void setEmojiText(String str) {
        this.emojiText = str;
    }

    public void setIcon(int i2) {
        this.icon = i2;
    }

    public void setIconPath(String str) {
        this.iconPath = str;
    }

    public void setIdentityCode(String str) {
        this.identityCode = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public EaseEmojicon(int i2, String str) {
        this.icon = i2;
        this.emojiText = str;
        this.type = Type.NORMAL;
    }

    public EaseEmojicon(int i2, String str, Type type) {
        this.icon = i2;
        this.emojiText = str;
        this.type = type;
    }
}
