package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo;

import java.util.List;

/* loaded from: classes3.dex */
public class PLVManagerChatVO {
    private List<PLVChatEventWrapVO> chatEventWrapVOList;

    public PLVManagerChatVO() {
    }

    public List<PLVChatEventWrapVO> getChatEventWrapVOList() {
        return this.chatEventWrapVOList;
    }

    public PLVManagerChatVO setChatEventWrapVOList(List<PLVChatEventWrapVO> chatEventWrapVOList) {
        this.chatEventWrapVOList = chatEventWrapVOList;
        return this;
    }

    public String toString() {
        return "PLVManagerChatVO{chatEventWrapVOList=" + this.chatEventWrapVOList + '}';
    }

    public PLVManagerChatVO(final List<PLVChatEventWrapVO> chatEventWrapVOList) {
        this.chatEventWrapVOList = chatEventWrapVOList;
    }
}
