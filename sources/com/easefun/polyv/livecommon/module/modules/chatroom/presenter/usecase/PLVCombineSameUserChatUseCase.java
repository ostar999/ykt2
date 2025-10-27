package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo.PLVChatEventWrapVO;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVCombineSameUserChatUseCase {
    @NonNull
    public List<PLVChatEventWrapVO> combine(List<PLVChatEventWrapVO> originList) {
        ArrayList arrayList = new ArrayList();
        if (originList != null && !originList.isEmpty()) {
            PLVChatEventWrapVO events = null;
            for (int i2 = 0; i2 < originList.size(); i2++) {
                PLVChatEventWrapVO pLVChatEventWrapVO = originList.get(i2);
                if (events == null || !events.isSameUserWith(pLVChatEventWrapVO)) {
                    events = new PLVChatEventWrapVO().setId(pLVChatEventWrapVO.getId()).setTime(pLVChatEventWrapVO.getTime()).setUser(pLVChatEventWrapVO.getUser()).setEvents(new ArrayList());
                    arrayList.add(events);
                }
                events.setLastEventTime(pLVChatEventWrapVO.getLastEventTime());
                events.getEvents().add(pLVChatEventWrapVO.getEvents().get(0));
            }
        }
        return arrayList;
    }
}
