package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo.PLVChatEventWrapVO;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVCalculateUnreadMessageCountUseCase {
    public int calculate(@NonNull List<PLVChatEventWrapVO> chatEvents, final long lastReadTime) {
        int i2 = 0;
        for (int size = chatEvents.size() - 1; size >= 0; size--) {
            PLVChatEventWrapVO pLVChatEventWrapVO = chatEvents.get(size);
            if (pLVChatEventWrapVO.isValid() && pLVChatEventWrapVO.getTime().longValue() > lastReadTime) {
                i2++;
            }
        }
        return i2;
    }
}
