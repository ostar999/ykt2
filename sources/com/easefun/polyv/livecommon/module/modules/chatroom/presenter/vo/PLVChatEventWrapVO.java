package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVChatEventWrapVO {
    private List<PLVBaseEvent> events;
    private String id;
    private Long lastEventTime;
    private Long time;
    private PLVSocketUserBean user;

    public boolean equals(Object o2) {
        if (this == o2) {
            return true;
        }
        if (o2 == null || getClass() != o2.getClass()) {
            return false;
        }
        PLVChatEventWrapVO pLVChatEventWrapVO = (PLVChatEventWrapVO) o2;
        String str = this.id;
        if (str == null ? pLVChatEventWrapVO.id != null : !str.equals(pLVChatEventWrapVO.id)) {
            return false;
        }
        List<PLVBaseEvent> list = this.events;
        List<PLVBaseEvent> list2 = pLVChatEventWrapVO.events;
        return list != null ? list.equals(list2) : list2 == null;
    }

    public List<PLVBaseEvent> getEvents() {
        return this.events;
    }

    public String getId() {
        return this.id;
    }

    public Long getLastEventTime() {
        return this.lastEventTime;
    }

    public Long getTime() {
        return this.time;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public int hashCode() {
        String str = this.id;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        List<PLVBaseEvent> list = this.events;
        return iHashCode + (list != null ? list.hashCode() : 0);
    }

    public boolean isSameUserWith(PLVChatEventWrapVO vo) {
        PLVSocketUserBean pLVSocketUserBean;
        return (vo == null || (pLVSocketUserBean = vo.user) == null || !isSameUserWith(pLVSocketUserBean.getUserId())) ? false : true;
    }

    public boolean isValid() {
        return (this.time == null || this.user == null || this.events == null) ? false : true;
    }

    public PLVChatEventWrapVO setEvent(PLVBaseEvent event) {
        this.events = PLVSugarUtil.listOf(event);
        return this;
    }

    public PLVChatEventWrapVO setEvents(List<PLVBaseEvent> events) {
        this.events = events;
        return this;
    }

    public PLVChatEventWrapVO setId(String id) {
        this.id = id;
        return this;
    }

    public PLVChatEventWrapVO setLastEventTime(Long lastEventTime) {
        this.lastEventTime = lastEventTime;
        return this;
    }

    public PLVChatEventWrapVO setTime(Long time) {
        this.time = time;
        return this;
    }

    public PLVChatEventWrapVO setUser(PLVSocketUserBean user) {
        this.user = user;
        return this;
    }

    public String toString() {
        return "PLVChatEventWrapVO{id='" + this.id + CharPool.SINGLE_QUOTE + ", time=" + this.time + ", user=" + this.user + ", events=" + this.events + '}';
    }

    public boolean isSameUserWith(String userId) {
        PLVSocketUserBean pLVSocketUserBean = this.user;
        return (pLVSocketUserBean == null || pLVSocketUserBean.getUserId() == null || !this.user.getUserId().equals(userId)) ? false : true;
    }
}
