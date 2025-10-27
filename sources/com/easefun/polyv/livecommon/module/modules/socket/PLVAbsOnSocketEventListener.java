package com.easefun.polyv.livecommon.module.modules.socket;

import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager;
import com.plv.socket.event.login.PLVKickEvent;
import com.plv.socket.event.login.PLVLoginRefuseEvent;
import com.plv.socket.event.login.PLVReloginEvent;

/* loaded from: classes3.dex */
public abstract class PLVAbsOnSocketEventListener implements IPLVSocketLoginManager.OnSocketEventListener {
    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
    public void handleLoginFailed(@NonNull Throwable throwable) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
    public void handleLoginIng(boolean isReconnect) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
    public void handleLoginSuccess(boolean isReconnect) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
    public void onKickEvent(@NonNull PLVKickEvent kickEvent, boolean isOwn) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
    public void onLoginRefuseEvent(@NonNull PLVLoginRefuseEvent loginRefuseEvent) {
    }

    @Override // com.easefun.polyv.livecommon.module.modules.socket.IPLVSocketLoginManager.OnSocketEventListener
    public void onReloginEvent(@NonNull PLVReloginEvent reloginEvent) {
    }
}
