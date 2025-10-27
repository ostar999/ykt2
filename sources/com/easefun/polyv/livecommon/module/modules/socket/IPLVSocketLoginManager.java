package com.easefun.polyv.livecommon.module.modules.socket;

import androidx.annotation.NonNull;
import com.plv.socket.event.login.PLVKickEvent;
import com.plv.socket.event.login.PLVLoginRefuseEvent;
import com.plv.socket.event.login.PLVReloginEvent;

/* loaded from: classes3.dex */
public interface IPLVSocketLoginManager {

    public interface OnSocketEventListener {
        void handleLoginFailed(@NonNull Throwable throwable);

        void handleLoginIng(boolean isReconnect);

        void handleLoginSuccess(boolean isReconnect);

        void onKickEvent(@NonNull PLVKickEvent kickEvent, boolean isOwn);

        void onLoginRefuseEvent(@NonNull PLVLoginRefuseEvent loginRefuseEvent);

        void onReloginEvent(@NonNull PLVReloginEvent reloginEvent);
    }

    void destroy();

    void disconnect();

    void init();

    void login();

    void setAllowChildRoom(boolean allow);

    void setOnSocketEventListener(OnSocketEventListener listener);
}
