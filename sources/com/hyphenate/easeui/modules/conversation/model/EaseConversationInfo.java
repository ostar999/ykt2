package com.hyphenate.easeui.modules.conversation.model;

import java.io.Serializable;

/* loaded from: classes4.dex */
public class EaseConversationInfo implements Serializable, Comparable<EaseConversationInfo> {
    private Object info;
    private boolean isGroup;
    private boolean isSelected;
    private boolean isTop;
    private OnSelectListener listener;
    private long timestamp;

    public interface OnSelectListener {
        void onSelect(boolean z2);
    }

    public Object getInfo() {
        return this.info;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public boolean isGroup() {
        return this.isGroup;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public boolean isTop() {
        return this.isTop;
    }

    public void setGroup(boolean z2) {
        this.isGroup = z2;
    }

    public void setInfo(Object obj) {
        this.info = obj;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.listener = onSelectListener;
    }

    public void setSelected(boolean z2) {
        this.isSelected = z2;
        OnSelectListener onSelectListener = this.listener;
        if (onSelectListener != null) {
            onSelectListener.onSelect(z2);
        }
    }

    public void setTimestamp(long j2) {
        this.timestamp = j2;
    }

    public void setTop(boolean z2) {
        this.isTop = z2;
    }

    @Override // java.lang.Comparable
    public int compareTo(EaseConversationInfo easeConversationInfo) {
        return this.timestamp > easeConversationInfo.timestamp ? -1 : 1;
    }
}
