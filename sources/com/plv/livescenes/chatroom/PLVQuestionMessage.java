package com.plv.livescenes.chatroom;

import com.plv.livescenes.socket.PLVSocketWrapper;

/* loaded from: classes4.dex */
public class PLVQuestionMessage extends PLVBaseHolder {
    private String questionMessage;

    public PLVQuestionMessage(String str) {
        this.questionMessage = str;
    }

    public String getQuestionMessage() {
        return this.questionMessage;
    }

    public String getUserId() {
        return PLVSocketWrapper.getInstance().getLoginVO().getUserId();
    }

    public void setQuestionMessage(String str) {
        this.questionMessage = str;
    }
}
