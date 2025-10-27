package com.aliyun.svideo.common.bottomnavigationbar;

/* loaded from: classes2.dex */
public class BottomNavigationEntity {
    private int badgeNum;
    private int selectedIcon;
    private String text;
    private int unSelectIcon;

    public BottomNavigationEntity(String str, int i2, int i3) {
        this.text = str;
        this.unSelectIcon = i2;
        this.selectedIcon = i3;
    }

    public int getBadgeNum() {
        return this.badgeNum;
    }

    public int getSelectedIcon() {
        return this.selectedIcon;
    }

    public String getText() {
        return this.text;
    }

    public int getUnSelectIcon() {
        return this.unSelectIcon;
    }

    public void setBadgeNum(int i2) {
        this.badgeNum = i2;
    }

    public void setSelectedIcon(int i2) {
        this.selectedIcon = i2;
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setUnSelectIcon(int i2) {
        this.unSelectIcon = i2;
    }

    public BottomNavigationEntity(String str, int i2, int i3, int i4) {
        this.text = str;
        this.unSelectIcon = i2;
        this.selectedIcon = i3;
        this.badgeNum = i4;
    }

    public BottomNavigationEntity(int i2, int i3) {
        this.unSelectIcon = i2;
        this.selectedIcon = i3;
    }

    public BottomNavigationEntity(int i2, int i3, int i4) {
        this.unSelectIcon = i2;
        this.selectedIcon = i3;
        this.badgeNum = i4;
    }
}
