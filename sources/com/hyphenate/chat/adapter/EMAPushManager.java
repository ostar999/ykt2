package com.hyphenate.chat.adapter;

import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EMAPushManager extends EMABase {
    public void clearRemindTypeForConversation(String str, int i2, EMAError eMAError) {
        nativeClearRemindTypeForConversation(str, i2, eMAError);
    }

    public void disableOfflineNotification(int i2, int i3, EMAError eMAError) {
        nativeDisableOfflineNotification(i2, i3, eMAError);
    }

    public void enableOfflineNotification(EMAError eMAError) {
        nativeEnableOfflineNotification(eMAError);
    }

    public List<String> getNoPushGroups() {
        return nativeGetNoPushGroups();
    }

    public List<String> getNoPushUsers() {
        return nativeGetNoPushUsers();
    }

    public EMAPushConfigs getPushConfigs() {
        return nativeGetPushConfigs();
    }

    public EMAPushConfigs getPushConfigsFromServer(EMAError eMAError) {
        return nativeGetPushConfigsFromServer(eMAError);
    }

    public String getPushPerformLanguage(EMAError eMAError) {
        return nativeGetPushPerformLanguage(eMAError);
    }

    public String getPushTemplate(EMAError eMAError) {
        return nativeGetPushTemplate(eMAError);
    }

    public EMASilentModeItem getSilentModeForAll(EMAError eMAError) {
        return nativeGetSilentModeForAll(eMAError);
    }

    public EMASilentModeItem getSilentModeForConversation(String str, int i2, EMAError eMAError) {
        return nativeGetSilentModeForConversation(str, i2, eMAError);
    }

    public List<EMASilentModeItem> getSilentModeForConversations(Map<String, String> map, EMAError eMAError) {
        return nativeGetSilentModeForConversations(map, eMAError);
    }

    public native void nativeClearRemindTypeForConversation(String str, int i2, EMAError eMAError);

    public native void nativeDisableOfflineNotification(int i2, int i3, EMAError eMAError);

    public native void nativeEnableOfflineNotification(EMAError eMAError);

    public native List<String> nativeGetNoPushGroups();

    public native List<String> nativeGetNoPushUsers();

    public native EMAPushConfigs nativeGetPushConfigs();

    public native EMAPushConfigs nativeGetPushConfigsFromServer(EMAError eMAError);

    public native String nativeGetPushPerformLanguage(EMAError eMAError);

    public native String nativeGetPushTemplate(EMAError eMAError);

    public native EMASilentModeItem nativeGetSilentModeForAll(EMAError eMAError);

    public native EMASilentModeItem nativeGetSilentModeForConversation(String str, int i2, EMAError eMAError);

    public native List<EMASilentModeItem> nativeGetSilentModeForConversations(Map<String, String> map, EMAError eMAError);

    public native void nativeReportPushAction(String str, EMAError eMAError);

    public native void nativeSetPushPerformLanguage(String str, EMAError eMAError);

    public native void nativeSetPushTemplate(String str, EMAError eMAError);

    public native EMASilentModeItem nativeSetSilentModeForAll(EMASilentModeParam eMASilentModeParam, EMAError eMAError);

    public native EMASilentModeItem nativeSetSilentModeForConversation(String str, int i2, EMASilentModeParam eMASilentModeParam, EMAError eMAError);

    public native void nativeUpdatePushDisplayStyle(int i2, EMAError eMAError);

    public native void nativeUpdatePushNickname(String str, EMAError eMAError);

    public native void nativeUpdatePushServiceForGroup(List<String> list, boolean z2, EMAError eMAError);

    public native void nativeUpdatePushServiceForUsers(List<String> list, boolean z2, EMAError eMAError);

    public void reportPushAction(String str, EMAError eMAError) {
        nativeReportPushAction(str, eMAError);
    }

    public void setPushPerformLanguage(String str, EMAError eMAError) {
        nativeSetPushPerformLanguage(str, eMAError);
    }

    public void setPushTemplate(String str, EMAError eMAError) {
        nativeSetPushTemplate(str, eMAError);
    }

    public EMASilentModeItem setSilentModeForAll(EMASilentModeParam eMASilentModeParam, EMAError eMAError) {
        return nativeSetSilentModeForAll(eMASilentModeParam, eMAError);
    }

    public EMASilentModeItem setSilentModeForConversation(String str, int i2, EMASilentModeParam eMASilentModeParam, EMAError eMAError) {
        return nativeSetSilentModeForConversation(str, i2, eMASilentModeParam, eMAError);
    }

    public void updatePushDisplayStyle(int i2, EMAError eMAError) {
        nativeUpdatePushDisplayStyle(i2, eMAError);
    }

    public void updatePushNickname(String str, EMAError eMAError) {
        nativeUpdatePushNickname(str, eMAError);
    }

    public void updatePushServiceForGroup(List<String> list, boolean z2, EMAError eMAError) {
        nativeUpdatePushServiceForGroup(list, z2, eMAError);
    }

    public void updatePushServiceForUsers(List<String> list, boolean z2, EMAError eMAError) {
        nativeUpdatePushServiceForUsers(list, z2, eMAError);
    }
}
