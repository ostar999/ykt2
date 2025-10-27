package com.plv.socket.event.chat;

import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVOTeacherInfoEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "O_TEACHER_INFO";
    private PLVSocketUserBean data;

    public PLVSocketUserBean getData() {
        return this.data;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "O_TEACHER_INFO";
    }

    public void setData(PLVSocketUserBean pLVSocketUserBean) {
        this.data = pLVSocketUserBean;
    }
}
