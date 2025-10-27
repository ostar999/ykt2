package com.hyphenate.chat.adapter;

import com.hyphenate.chat.adapter.message.EMAMessage;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class EMAChatConfig extends EMABase {
    public static void logD(String str, String str2) {
        nativeLogD(str, str2);
    }

    public static void logE(String str, String str2) {
        nativeLogE(str, str2);
    }

    public static void logI(String str, String str2) {
        nativeLogI(str, str2);
    }

    public static void logV(String str, String str2) {
        nativeLogV(str, str2);
    }

    public static void logW(String str, String str2) {
        nativeLogW(str, str2);
    }

    public static native void nativeLogD(String str, String str2);

    public static native void nativeLogE(String str, String str2);

    public static native void nativeLogI(String str, String str2);

    public static native void nativeLogV(String str, String str2);

    public static native void nativeLogW(String str, String str2);

    public void enableDnsConfig(boolean z2) {
        nativeenableDnsConfig(z2);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String getAccessToken() {
        return getAccessToken(false);
    }

    public String getAccessToken(boolean z2) {
        return nativegetAccessToken(z2);
    }

    public String getAppKey() {
        return nativegetAppKey();
    }

    public boolean getAutoAccept() {
        return nativegetAutoAccept();
    }

    public boolean getAutoAcceptGroupInvitation() {
        return nativegetAutoAcceptGroupInvitation();
    }

    public boolean getAutoConversationLoaded() {
        return nativegetAutoConversationLoaded();
    }

    public boolean getAutoLogin() {
        return nativegetAutoLogin();
    }

    public boolean getAutodownloadThumbnail() {
        return nativeGetAutodownloadThumbnail();
    }

    public String getBaseUrl(boolean z2, boolean z3) {
        return nativegetBaseUrl(z2, z3);
    }

    public String getChatAddress() {
        return nativegetChatAddress();
    }

    public String getChatDomain() {
        return nativegetChatDomain();
    }

    public boolean getDeleteMessageAsExitChatRoom() {
        return nativegetDeleteMessageAsExitChatRoom();
    }

    public boolean getDeleteMessageAsExitGroup() {
        return nativegetDeleteMessageAsExitGroup();
    }

    public String getDnsUrl() {
        return nativeGetDnsUrl();
    }

    public String getDownloadPath() {
        return nativegetDownloadPath();
    }

    public boolean getEnableConsoleLog() {
        return nativegetEnableConsoleLog();
    }

    public boolean getFpaEnable() {
        return nativeGetFpaEnable();
    }

    public String getGaoDeDiscoverKey() {
        return nativeGetGaoDeDiscoverKey();
    }

    public String getGaoDeLocationKey() {
        return nativeGetGaoDeLocationKey();
    }

    public String getGroupDomain() {
        return nativegetGroupDomain();
    }

    public boolean getIsChatroomOwnerLeaveAllowed() {
        return nativegetIsChatroomOwnerLeaveAllowed();
    }

    public boolean getIsSandboxMode() {
        return nativegetIsSandboxMode();
    }

    public EMAHeartBeatCustomizedParams getMobileHeartBeatCustomizedParams() {
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicInteger atomicInteger2 = new AtomicInteger();
        AtomicInteger atomicInteger3 = new AtomicInteger();
        nativeGetMobileHeartBeatCustomizedParams(atomicInteger, atomicInteger2, atomicInteger3);
        EMAHeartBeatCustomizedParams eMAHeartBeatCustomizedParams = new EMAHeartBeatCustomizedParams();
        eMAHeartBeatCustomizedParams.minInterval = atomicInteger.get();
        eMAHeartBeatCustomizedParams.maxInterval = atomicInteger2.get();
        eMAHeartBeatCustomizedParams.defaultInterval = atomicInteger3.get();
        return eMAHeartBeatCustomizedParams;
    }

    public String getNextAvailableBaseUrl() {
        return nativegetNextAvailableBaseUrl();
    }

    public boolean getRequireDeliveryAck() {
        return nativegetRequireDeliveryAck();
    }

    public boolean getRequireReadAck() {
        return nativegetRequireReadAck();
    }

    public String getResourcePath() {
        return nativegetResourcePath();
    }

    public String getRestServer() {
        return nativegetRestServer();
    }

    public String getRtcConfigUrl() {
        return nativeGetRtcConfigUrl();
    }

    public boolean getSortMessageByServerTime() {
        return nativeGetSortMessageByServerTime();
    }

    public long getTokenSaveTime() {
        return nativegetTokenSaveTime();
    }

    public boolean getTransferAttachments() {
        return nativeGetTransferAttachments();
    }

    public boolean getUseAws() {
        return nativeGetUseAws();
    }

    public boolean getUseRtcConfig() {
        return nativeGetUseRtcConfig();
    }

    public boolean getUsingHttpsOnly() {
        return nativeGetUsingHttpsOnly();
    }

    public boolean getUsingSQLCipher() {
        return nativeGetUsingSQLCipher();
    }

    public EMAHeartBeatCustomizedParams getWifiHeartBeatCustomizedParams() {
        AtomicInteger atomicInteger = new AtomicInteger();
        AtomicInteger atomicInteger2 = new AtomicInteger();
        AtomicInteger atomicInteger3 = new AtomicInteger();
        nativeGetWifiHeartBeatCustomizedParams(atomicInteger, atomicInteger2, atomicInteger3);
        EMAHeartBeatCustomizedParams eMAHeartBeatCustomizedParams = new EMAHeartBeatCustomizedParams();
        eMAHeartBeatCustomizedParams.minInterval = atomicInteger.get();
        eMAHeartBeatCustomizedParams.maxInterval = atomicInteger2.get();
        eMAHeartBeatCustomizedParams.defaultInterval = atomicInteger3.get();
        return eMAHeartBeatCustomizedParams;
    }

    public String getWorkPath() {
        return nativegetWorkPath();
    }

    public boolean hasHeartBeatCustomizedParams() {
        return nativeHasHeartBeatCustomizedParams();
    }

    public void importBlackList(List<String> list) {
        nativeImportBlackList(list);
    }

    public void importChatRoom(String str, String str2, String str3, String str4, List<String> list, int i2) {
        nativeImportChatRoom(str, str2, str3, str4, list, i2);
    }

    public void importContacts(List<String> list) {
        nativeImportContacts(list);
    }

    public void importConversation(String str, int i2, String str2) {
        nativeImportConversation(str, i2, str2);
    }

    public void importGroup(String str, int i2, String str2, String str3, String str4, List<String> list, boolean z2, int i3) {
        nativeImportGroup(str, i2, str2, str3, str4, list, z2, i3);
    }

    public void importMessages(List<EMAMessage> list) {
        nativeImportMessages(list);
    }

    public void init(String str, String str2, String str3) {
        nativeInit(str, str2, str3);
    }

    public boolean isEnableDnsConfig() {
        return nativeisEnableDnsConfig();
    }

    public boolean isGcmEnabled() {
        return nativeIsGcmEnabled();
    }

    public boolean isNewLoginOnDevice() {
        return nativeIsNewLoginOnDevice();
    }

    public native void nativeFinalize();

    public native boolean nativeGetAutodownloadThumbnail();

    public native String nativeGetDnsUrl();

    public native boolean nativeGetFpaEnable();

    public native String nativeGetGaoDeDiscoverKey();

    public native String nativeGetGaoDeLocationKey();

    public native void nativeGetMobileHeartBeatCustomizedParams(AtomicInteger atomicInteger, AtomicInteger atomicInteger2, AtomicInteger atomicInteger3);

    public native String nativeGetRtcConfigUrl();

    public native boolean nativeGetSortMessageByServerTime();

    public native boolean nativeGetTransferAttachments();

    public native boolean nativeGetUseAws();

    public native boolean nativeGetUseRtcConfig();

    public native boolean nativeGetUsingHttpsOnly();

    public native boolean nativeGetUsingSQLCipher();

    public native void nativeGetWifiHeartBeatCustomizedParams(AtomicInteger atomicInteger, AtomicInteger atomicInteger2, AtomicInteger atomicInteger3);

    public native boolean nativeHasHeartBeatCustomizedParams();

    public native void nativeImportBlackList(List<String> list);

    public native void nativeImportChatRoom(String str, String str2, String str3, String str4, List<String> list, int i2);

    public native void nativeImportContacts(List<String> list);

    public native void nativeImportConversation(String str, int i2, String str2);

    public native void nativeImportGroup(String str, int i2, String str2, String str3, String str4, List<String> list, boolean z2, int i3);

    public native void nativeImportMessages(List<EMAMessage> list);

    public native void nativeInit(String str, String str2, String str3);

    public native boolean nativeIsGcmEnabled();

    public native boolean nativeIsNewLoginOnDevice();

    public native boolean nativeOpenDatabase(String str);

    public native void nativeReloadAll();

    public native void nativeSetAppId(String str);

    public native void nativeSetAreaCode(int i2);

    public native void nativeSetAutodownloadThumbnail(boolean z2);

    public native void nativeSetCallbackNet(EMANetCallback eMANetCallback);

    public native void nativeSetDebugMode(boolean z2);

    public native void nativeSetDnsUrl(String str);

    public native void nativeSetOSVersion(String str);

    public native void nativeSetRtcConfigUrl(String str);

    public native void nativeSetSDKVersion(String str);

    public native void nativeSetSortMessageByServerTime(boolean z2);

    public native void nativeSetTransferAttachments(boolean z2);

    public native void nativeSetUseAws(boolean z2);

    public native void nativeSetUseHttps(boolean z2);

    public native void nativeSetUseRtcConfig(boolean z2);

    public native void nativeSetUsingHttpsOnly(boolean z2);

    public native void nativeUpdateConversationUnreadCount(String str, int i2);

    public native void nativeUploadLog(EMACallback eMACallback);

    public native void nativeenableDnsConfig(boolean z2);

    public native String nativegetAccessToken(boolean z2);

    public native String nativegetAppKey();

    public native boolean nativegetAutoAccept();

    public native boolean nativegetAutoAcceptGroupInvitation();

    public native boolean nativegetAutoConversationLoaded();

    public native boolean nativegetAutoLogin();

    public native String nativegetBaseUrl(boolean z2, boolean z3);

    public native String nativegetChatAddress();

    public native String nativegetChatDomain();

    public native boolean nativegetDeleteMessageAsExitChatRoom();

    public native boolean nativegetDeleteMessageAsExitGroup();

    public native String nativegetDownloadPath();

    public native boolean nativegetEnableConsoleLog();

    public native String nativegetGroupDomain();

    public native boolean nativegetIsChatroomOwnerLeaveAllowed();

    public native boolean nativegetIsSandboxMode();

    public native String nativegetNextAvailableBaseUrl();

    public native boolean nativegetRequireDeliveryAck();

    public native boolean nativegetRequireReadAck();

    public native String nativegetResourcePath();

    public native String nativegetRestServer();

    public native long nativegetTokenSaveTime();

    public native String nativegetWorkPath();

    public native boolean nativeisEnableDnsConfig();

    public native void nativeretrieveDNSConfig();

    public native void nativesetAppKey(String str);

    public native void nativesetAutoAccept(boolean z2);

    public native void nativesetAutoAcceptGroupInvitation(boolean z2);

    public native void nativesetAutoConversationLoaded(boolean z2);

    public native void nativesetAutoLogin(boolean z2);

    public native void nativesetChatDomain(String str);

    public native void nativesetChatPort(int i2);

    public native void nativesetChatServer(String str);

    public native void nativesetDeleteMessageAsExitChatRoom(boolean z2);

    public native void nativesetDeleteMessageAsExitGroup(boolean z2);

    public native void nativesetDeviceName(String str);

    public native void nativesetDeviceUuid(String str);

    public native void nativesetDid(String str);

    public native void nativesetDownloadPath(String str);

    public native void nativesetEnableConsoleLog(boolean z2);

    public native void nativesetEnableFpa(boolean z2);

    public native void nativesetGroupDomain(String str);

    public native void nativesetIsChatroomOwnerLeaveAllowed(boolean z2);

    public native void nativesetIsSandboxMode(boolean z2);

    public native void nativesetLogPath(String str);

    public native void nativesetRequireDeliveryAck(boolean z2);

    public native void nativesetRequireReadAck(boolean z2);

    public native void nativesetRestServer(String str);

    public native void nativesetRtcServer(String str);

    public native void nativesetServiceId(String str);

    public native boolean nativeuseHttps();

    public boolean openDatabase(String str) {
        return nativeOpenDatabase(str);
    }

    public void reloadAll() {
        nativeReloadAll();
    }

    public void retrieveDNSConfig() {
        nativeretrieveDNSConfig();
    }

    public void setAppId(String str) {
        nativeSetAppId(str);
    }

    public void setAppKey(String str) {
        nativesetAppKey(str);
    }

    public void setAreaCode(int i2) {
        nativeSetAreaCode(i2);
    }

    public void setAutoAccept(boolean z2) {
        nativesetAutoAccept(z2);
    }

    public void setAutoAcceptGroupInvitation(boolean z2) {
        nativesetAutoAcceptGroupInvitation(z2);
    }

    public void setAutoConversationLoaded(boolean z2) {
        nativesetAutoConversationLoaded(z2);
    }

    public void setAutoLogin(boolean z2) {
        nativesetAutoLogin(z2);
    }

    public void setAutodownloadThumbnail(boolean z2) {
        nativeSetAutodownloadThumbnail(z2);
    }

    public void setChatDomain(String str) {
        nativesetChatDomain(str);
    }

    public void setChatPort(int i2) {
        nativesetChatPort(i2);
    }

    public void setChatServer(String str) {
        nativesetChatServer(str);
    }

    public void setDebugMode(boolean z2) {
        nativeSetDebugMode(z2);
    }

    public void setDeleteMessageAsExitChatRoom(boolean z2) {
        nativesetDeleteMessageAsExitChatRoom(z2);
    }

    public void setDeleteMessageAsExitGroup(boolean z2) {
        nativesetDeleteMessageAsExitGroup(z2);
    }

    public void setDeviceName(String str) {
        nativesetDeviceName(str);
    }

    public void setDeviceUuid(String str) {
        nativesetDeviceUuid(str);
    }

    public void setDid(String str) {
        nativesetDid(str);
    }

    public void setDnsUrl(String str) {
        nativeSetDnsUrl(str);
    }

    public void setDownloadPath(String str) {
        nativesetDownloadPath(str);
    }

    public void setEnableConsoleLog(boolean z2) {
        nativesetEnableConsoleLog(z2);
    }

    public void setFpaEnable(boolean z2) {
        nativesetEnableFpa(z2);
    }

    public void setGroupDomain(String str) {
        nativesetGroupDomain(str);
    }

    public void setIsChatroomOwnerLeaveAllowed(boolean z2) {
        nativesetIsChatroomOwnerLeaveAllowed(z2);
    }

    public void setIsSandboxMode(boolean z2) {
        nativesetIsSandboxMode(z2);
    }

    public void setLogPath(String str) {
        nativesetLogPath(str);
    }

    public void setNetCallback(EMANetCallback eMANetCallback) {
        nativeSetCallbackNet(eMANetCallback);
    }

    public void setOSVersion(String str) {
        nativeSetOSVersion(str);
    }

    public void setRequireDeliveryAck(boolean z2) {
        nativesetRequireDeliveryAck(z2);
    }

    public void setRequireReadAck(boolean z2) {
        nativesetRequireReadAck(z2);
    }

    public void setRestServer(String str) {
        nativesetRestServer(str);
    }

    public void setRtcConfigUrl(String str) {
        nativeSetRtcConfigUrl(str);
    }

    public void setRtcServer(String str) {
        nativesetRtcServer(str);
    }

    public void setSDKVersion(String str) {
        nativeSetSDKVersion(str);
    }

    public void setServiceId(String str) {
        nativesetServiceId(str);
    }

    public void setSortMessageByServerTime(boolean z2) {
        nativeSetSortMessageByServerTime(z2);
    }

    public void setTransferAttachments(boolean z2) {
        nativeSetTransferAttachments(z2);
    }

    public void setUseAws(boolean z2) {
        nativeSetUseAws(z2);
    }

    public void setUseHttps(boolean z2) {
        nativeSetUseHttps(z2);
    }

    public void setUseRtcConfig(boolean z2) {
        nativeSetUseRtcConfig(z2);
    }

    public void setUsingHttpsOnly(boolean z2) {
        nativeSetUsingHttpsOnly(z2);
    }

    public void updateConversationUnreadCount(String str, int i2) {
        nativeUpdateConversationUnreadCount(str, i2);
    }

    public void uploadLog(EMACallback eMACallback) {
        nativeUploadLog(eMACallback);
    }

    public boolean useHttps() {
        return false;
    }
}
