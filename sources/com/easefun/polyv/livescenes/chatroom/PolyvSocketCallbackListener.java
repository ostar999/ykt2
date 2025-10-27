package com.easefun.polyv.livescenes.chatroom;

import com.easefun.polyv.livescenes.model.PolyvInteractiveCallbackVO;
import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.livescenes.chatroom.PLVSocketCallbackListener;
import com.plv.livescenes.model.PLVInteractiveCallbackVO;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PolyvSocketCallbackListener implements PLVSocketCallbackListener {
    public abstract void socketCallback(PolyvInteractiveCallbackVO polyvInteractiveCallbackVO);

    @Override // com.plv.livescenes.chatroom.PLVSocketCallbackListener
    public final void socketCallback(PLVInteractiveCallbackVO pLVInteractiveCallbackVO) {
        socketCallback((PolyvInteractiveCallbackVO) PLVReflectionUtil.copyField(pLVInteractiveCallbackVO, new PolyvInteractiveCallbackVO()));
    }
}
