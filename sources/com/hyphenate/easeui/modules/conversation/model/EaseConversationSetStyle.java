package com.hyphenate.easeui.modules.conversation.model;

import com.hyphenate.easeui.modules.EaseBaseSetStyle;

/* loaded from: classes4.dex */
public class EaseConversationSetStyle extends EaseBaseSetStyle {
    private String bottomLineColor;
    private int contentTextColor;
    private float contentTextSize;
    private int dateTextColor;
    private float dateTextSize;
    private boolean hideUnreadDot;
    private int mentionTextColor;
    private float mentionTextSize;
    private boolean showSystemMessage;
    private int titleTextColor;
    private float titleTextSize;
    private UnreadDotPosition unreadDotPosition;

    public enum UnreadDotPosition {
        LEFT,
        RIGHT
    }

    public String getBottomLineColor() {
        return this.bottomLineColor;
    }

    public int getContentTextColor() {
        return this.contentTextColor;
    }

    public float getContentTextSize() {
        return this.contentTextSize;
    }

    public int getDateTextColor() {
        return this.dateTextColor;
    }

    public float getDateTextSize() {
        return this.dateTextSize;
    }

    public int getMentionTextColor() {
        return this.mentionTextColor;
    }

    public float getMentionTextSize() {
        return this.mentionTextSize;
    }

    public int getTitleTextColor() {
        return this.titleTextColor;
    }

    public float getTitleTextSize() {
        return this.titleTextSize;
    }

    public UnreadDotPosition getUnreadDotPosition() {
        return this.unreadDotPosition;
    }

    public boolean isHideUnreadDot() {
        return this.hideUnreadDot;
    }

    public boolean isShowSystemMessage() {
        return this.showSystemMessage;
    }

    public void setBottomLineColor(String str) {
        this.bottomLineColor = str;
    }

    public void setContentTextColor(int i2) {
        this.contentTextColor = i2;
    }

    public void setContentTextSize(float f2) {
        this.contentTextSize = f2;
    }

    public void setDateTextColor(int i2) {
        this.dateTextColor = i2;
    }

    public void setDateTextSize(float f2) {
        this.dateTextSize = f2;
    }

    public void setHideUnreadDot(boolean z2) {
        this.hideUnreadDot = z2;
    }

    public void setMentionTextColor(int i2) {
        this.mentionTextColor = i2;
    }

    public void setMentionTextSize(float f2) {
        this.mentionTextSize = f2;
    }

    public void setShowSystemMessage(boolean z2) {
        this.showSystemMessage = z2;
    }

    public void setTitleTextColor(int i2) {
        this.titleTextColor = i2;
    }

    public void setTitleTextSize(float f2) {
        this.titleTextSize = f2;
    }

    public void setUnreadDotPosition(UnreadDotPosition unreadDotPosition) {
        this.unreadDotPosition = unreadDotPosition;
    }
}
