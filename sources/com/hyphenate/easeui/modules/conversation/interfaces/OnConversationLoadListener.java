package com.hyphenate.easeui.modules.conversation.interfaces;

import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface OnConversationLoadListener {
    void loadDataFail(String str);

    void loadDataFinish(List<EaseConversationInfo> list);
}
