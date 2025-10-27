package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMAChatClient extends EMABase {
    private EMAChatManager chatManager = null;
    private EMAChatRoomManager chatRoomManager = null;
    private EMAGroupManager groupManager = null;
    private EMAContactManager contactManager = null;
    private EMAPushManager pushManager = null;
    private EMAUserInfoManager userInfoManager = null;
    private EMASessionManager sessionManager = null;
    private EMATranslateManager translateManager = null;
    private EMAPresenceManager presenceManager = null;
    private EMAReactionManager reactionManager = null;
    private EMAThreadManager threadManager = null;

    public static class CheckResultListener {
        public void onResult(int i2, int i3, String str) {
        }
    }

    public enum EMANetwork {
        NETWORK_NONE,
        NETWORK_CABLE,
        NETWORK_WIFI,
        NETWORK_MOBILE
    }

    public static EMAChatClient create(EMAChatConfig eMAChatConfig) {
        EMAChatClient eMAChatClient = new EMAChatClient();
        eMAChatClient.nativeHandler = native_create(eMAChatConfig);
        if (eMAChatClient.getChatManager() == null) {
            eMAChatClient.chatManager = new EMAChatManager();
            eMAChatClient.getChatManager().nativeHandler = eMAChatClient.native_getChatManager();
        }
        if (eMAChatClient.getChatRoomManager() == null) {
            eMAChatClient.chatRoomManager = new EMAChatRoomManager();
            eMAChatClient.getChatRoomManager().nativeHandler = eMAChatClient.native_getChatRoomManager();
        }
        if (eMAChatClient.getGroupManager() == null) {
            eMAChatClient.groupManager = new EMAGroupManager();
            eMAChatClient.getGroupManager().nativeHandler = eMAChatClient.native_getGroupManager();
        }
        if (eMAChatClient.getContactManager() == null) {
            eMAChatClient.contactManager = new EMAContactManager();
            eMAChatClient.getContactManager().nativeHandler = eMAChatClient.native_getContactManager();
        }
        if (eMAChatClient.getPushMnager() == null) {
            eMAChatClient.pushManager = new EMAPushManager();
            eMAChatClient.getPushMnager().nativeHandler = eMAChatClient.native_getPushManager();
        }
        if (eMAChatClient.getUserInfoManager() == null) {
            eMAChatClient.userInfoManager = new EMAUserInfoManager();
            eMAChatClient.getUserInfoManager().nativeHandler = eMAChatClient.native_getUserInfoManager();
        }
        if (eMAChatClient.getSessionManager() == null) {
            eMAChatClient.sessionManager = new EMASessionManager();
            eMAChatClient.getSessionManager().nativeHandler = eMAChatClient.native_getSessionManager();
        }
        if (eMAChatClient.getTranslateManager() == null) {
            eMAChatClient.translateManager = new EMATranslateManager();
            eMAChatClient.getTranslateManager().nativeHandler = eMAChatClient.native_getTranslateManager();
        }
        if (eMAChatClient.getPresenceManager() == null) {
            eMAChatClient.presenceManager = new EMAPresenceManager();
            eMAChatClient.getPresenceManager().nativeHandler = eMAChatClient.native_getPresenceManager();
        }
        if (eMAChatClient.getReactionManager() == null) {
            eMAChatClient.reactionManager = new EMAReactionManager();
            eMAChatClient.getReactionManager().nativeHandler = eMAChatClient.native_getReactionManager();
        }
        if (eMAChatClient.getThreadManager() == null) {
            eMAChatClient.threadManager = new EMAThreadManager();
            eMAChatClient.getThreadManager().nativeHandler = eMAChatClient.native_getThreadManager();
        }
        return eMAChatClient;
    }

    public static native long native_create(EMAChatConfig eMAChatConfig);

    public void addConnectionListener(EMAConnectionListener eMAConnectionListener) {
        native_addConnectionListener(eMAConnectionListener);
    }

    public void addLogCallbackListener(EMALogCallbackListener eMALogCallbackListener) {
        native_addLogCallbackListener(eMALogCallbackListener);
    }

    public void addMultiDeviceListener(EMAMultiDeviceListener eMAMultiDeviceListener) {
        native_addMultiDeviceListener(eMAMultiDeviceListener);
    }

    public EMAError changeAppkey(String str) {
        return native_changeAppkey(str);
    }

    public void check(String str, String str2, CheckResultListener checkResultListener) {
        nativeCheck(str, str2, checkResultListener);
    }

    public String compressLogs(EMAError eMAError) {
        return native_compressLogs(eMAError);
    }

    public EMAError createAccount(String str, String str2) {
        return native_createAccount(str, str2);
    }

    public void disconnect() {
        native_disconnect();
    }

    public EMAChatManager getChatManager() {
        return this.chatManager;
    }

    public EMAChatRoomManager getChatRoomManager() {
        return this.chatRoomManager;
    }

    public String getChatTokenbyAgoraToken(String str, EMAError eMAError) {
        return native_getChatTokenbyAgoraToken(str, eMAError);
    }

    public EMAContactManager getContactManager() {
        return this.contactManager;
    }

    public EMAGroupManager getGroupManager() {
        return this.groupManager;
    }

    public List<EMADeviceInfo> getLoggedInDevicesFromServer(String str, String str2, EMAError eMAError) {
        return nativeGetLoggedInDevicesFromServer(str, str2, eMAError);
    }

    public EMAPresenceManager getPresenceManager() {
        return this.presenceManager;
    }

    public EMAPushManager getPushMnager() {
        return this.pushManager;
    }

    public EMAReactionManager getReactionManager() {
        return this.reactionManager;
    }

    public EMASessionManager getSessionManager() {
        return this.sessionManager;
    }

    public EMAThreadManager getThreadManager() {
        return this.threadManager;
    }

    public EMATranslateManager getTranslateManager() {
        return this.translateManager;
    }

    public EMAUserInfoManager getUserInfoManager() {
        return this.userInfoManager;
    }

    public String getUserToken(boolean z2, EMAError eMAError) {
        return native_getUserToken(z2, eMAError);
    }

    public String getUserTokenFromServer(String str, String str2, EMAError eMAError) {
        return native_getUserTokenFromServer(str, str2, eMAError);
    }

    public boolean isConnected() {
        return native_isConnected();
    }

    public boolean isLoggedIn() {
        return native_isLoggedIn();
    }

    public boolean isLogout() {
        return native_isLogout();
    }

    public void kickAllDevices(String str, String str2, EMAError eMAError) {
        nativeKickAllDevices(str, str2, eMAError);
    }

    public void kickDevice(String str, String str2, String str3, EMAError eMAError) {
        nativeKickDevice(str, str2, str3, eMAError);
    }

    public void login(String str, String str2, boolean z2, int i2, EMAError eMAError) {
        native_login(str, str2, z2, i2, eMAError);
    }

    public void logout() {
        native_logout();
    }

    public native void nativeCheck(String str, String str2, CheckResultListener checkResultListener);

    public native List<EMADeviceInfo> nativeGetLoggedInDevicesFromServer(String str, String str2, EMAError eMAError);

    public native void nativeKickAllDevices(String str, String str2, EMAError eMAError);

    public native void nativeKickDevice(String str, String str2, String str3, EMAError eMAError);

    public native void native_addConnectionListener(EMAConnectionListener eMAConnectionListener);

    public native void native_addLogCallbackListener(EMALogCallbackListener eMALogCallbackListener);

    public native void native_addMultiDeviceListener(EMAMultiDeviceListener eMAMultiDeviceListener);

    public native EMAError native_changeAppkey(String str);

    public native String native_compressLogs(EMAError eMAError);

    public native EMAError native_createAccount(String str, String str2);

    public native void native_disconnect();

    public native long native_getCallManager();

    public native long native_getChatManager();

    public native long native_getChatRoomManager();

    public native String native_getChatTokenbyAgoraToken(String str, EMAError eMAError);

    public native long native_getContactManager();

    public native long native_getGroupManager();

    public native long native_getPresenceManager();

    public native long native_getPushManager();

    public native long native_getReactionManager();

    public native long native_getRtcConfigManager();

    public native long native_getSessionManager();

    public native long native_getThreadManager();

    public native long native_getTranslateManager();

    public native long native_getUserInfoManager();

    public native String native_getUserToken(boolean z2, EMAError eMAError);

    public native String native_getUserTokenFromServer(String str, String str2, EMAError eMAError);

    public native boolean native_isConnected();

    public native boolean native_isLoggedIn();

    public native boolean native_isLogout();

    public native void native_login(String str, String str2, boolean z2, int i2, EMAError eMAError);

    public native void native_logout();

    public native void native_onNetworkChanged(int i2);

    public native void native_removeConnectionListener(EMAConnectionListener eMAConnectionListener);

    public native void native_removeLogCallbackListener(EMALogCallbackListener eMALogCallbackListener);

    public native void native_removeMultiDeviceListener(EMAMultiDeviceListener eMAMultiDeviceListener);

    public native void native_renewToken(String str);

    public native boolean native_sendPing(boolean z2, long j2);

    public native void native_setPresence(String str);

    public native void natvie_reconnect();

    public void onNetworkChanged(EMANetwork eMANetwork) {
        native_onNetworkChanged(eMANetwork.ordinal());
    }

    public void reconnect() {
        natvie_reconnect();
    }

    public void removeConnectionListener(EMAConnectionListener eMAConnectionListener) {
        native_removeConnectionListener(eMAConnectionListener);
    }

    public void removeLogCallbackListener(EMALogCallbackListener eMALogCallbackListener) {
        native_removeLogCallbackListener(eMALogCallbackListener);
    }

    public void renewToken(String str) {
        native_renewToken(str);
    }

    public boolean sendPing(boolean z2, long j2) {
        return native_sendPing(z2, j2);
    }

    public void setPresence(String str) {
        native_setPresence(str);
    }
}
