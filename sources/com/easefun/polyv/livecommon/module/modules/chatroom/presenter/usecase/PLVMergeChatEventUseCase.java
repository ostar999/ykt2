package com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo.PLVChatEventWrapVO;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVMergeChatEventUseCase {
    private static boolean isSameIdItem(PLVChatEventWrapVO vo1, PLVChatEventWrapVO vo2) {
        return !TextUtils.isEmpty(vo1.getId()) && vo1.getId().equals(vo2.getId());
    }

    private static boolean isSendBySameUserAtSameTime(PLVChatEventWrapVO vo1, PLVChatEventWrapVO vo2) {
        return vo1.getUser().getUserId().equals(vo2.getUser().getUserId()) && vo1.getTime().equals(vo2.getTime());
    }

    private void sort(@NonNull List<PLVChatEventWrapVO> chatEvents) {
        Collections.sort(chatEvents, new Comparator<PLVChatEventWrapVO>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVMergeChatEventUseCase.1
            @Override // java.util.Comparator
            public int compare(PLVChatEventWrapVO o12, PLVChatEventWrapVO o2) {
                return o12.getTime().compareTo(o2.getTime());
            }
        });
    }

    public void merge(@NonNull List<PLVChatEventWrapVO> chatEvents, @NonNull PLVChatEventWrapVO chatEvent) {
        boolean z2 = false;
        int i2 = 0;
        for (int i3 = 0; i3 < chatEvents.size(); i3++) {
            PLVChatEventWrapVO pLVChatEventWrapVO = chatEvents.get(i3);
            if (isSendBySameUserAtSameTime(pLVChatEventWrapVO, chatEvent) || isSameIdItem(pLVChatEventWrapVO, chatEvent)) {
                chatEvents.set(i3, chatEvent);
                z2 = true;
                break;
            } else {
                if (pLVChatEventWrapVO.getTime().longValue() < chatEvent.getTime().longValue()) {
                    i2 = i3 + 1;
                }
            }
        }
        if (!z2) {
            chatEvents.add(i2, chatEvent);
        }
        sort(chatEvents);
    }
}
