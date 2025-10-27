package com.hyphenate.easeui.model;

import android.text.TextUtils;
import com.hyphenate.easeui.constants.EaseConstant;
import java.io.Serializable;

/* loaded from: classes4.dex */
public class EaseEvent implements Serializable {
    public String event;
    public String message;
    public boolean refresh;
    public TYPE type;

    public enum TYPE {
        GROUP,
        GROUP_LEAVE,
        CONTACT,
        MESSAGE,
        NOTIFY,
        CHAT_ROOM,
        CHAT_ROOM_LEAVE,
        ACCOUNT
    }

    public EaseEvent() {
    }

    public static EaseEvent create(String str, TYPE type) {
        return new EaseEvent(str, type);
    }

    public boolean isAccountChange() {
        return this.type == TYPE.ACCOUNT;
    }

    public boolean isChatRoomLeave() {
        return this.type == TYPE.CHAT_ROOM_LEAVE;
    }

    public boolean isContactChange() {
        return this.type == TYPE.CONTACT;
    }

    public boolean isGroupChange() {
        return this.type == TYPE.GROUP;
    }

    public boolean isGroupLeave() {
        return this.type == TYPE.GROUP_LEAVE || TextUtils.equals(this.event, EaseConstant.GROUP_LEAVE);
    }

    public boolean isMessageChange() {
        return this.type == TYPE.MESSAGE;
    }

    public boolean isNotifyChange() {
        return this.type == TYPE.NOTIFY;
    }

    public EaseEvent(String str, TYPE type, boolean z2) {
        this.refresh = z2;
        this.event = str;
        this.type = type;
    }

    public static EaseEvent create(String str, TYPE type, String str2) {
        EaseEvent easeEvent = new EaseEvent(str, type);
        easeEvent.message = str2;
        return easeEvent;
    }

    public static EaseEvent create(String str, TYPE type, boolean z2) {
        return new EaseEvent(str, type, z2);
    }

    public EaseEvent(String str, TYPE type) {
        this.refresh = true;
        this.event = str;
        this.type = type;
    }
}
