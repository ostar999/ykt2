package com.hyphenate.chat.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class EMAContactManager extends EMABase {
    private Set<EMAContactListener> listeners = new HashSet();

    public EMAContactManager() {
    }

    public EMAContactManager(EMAContactManager eMAContactManager) {
        nativeInit(eMAContactManager);
    }

    public void acceptInvitation(String str, EMAError eMAError) {
        nativeAcceptInvitation(str, eMAError);
    }

    public void addToBlackList(String str, boolean z2, EMAError eMAError) {
        nativeAddToBlackList(str, z2, eMAError);
    }

    public void declineInvitation(String str, EMAError eMAError) {
        nativeDeclineInvitation(str, eMAError);
    }

    public void deleteContact(String str, EMAError eMAError, boolean z2) {
        nativeDeleteContact(str, eMAError, z2);
    }

    public List<String> getBlackListFromDB(EMAError eMAError) {
        return nativeGetBlackListFromDB(eMAError);
    }

    public List<String> getBlackListFromServer(EMAError eMAError) {
        return nativeGetBlackListFromServer(eMAError);
    }

    public List<String> getContactsFromDB(EMAError eMAError) {
        return nativeGetContactsFromDB(eMAError);
    }

    public List<String> getContactsFromServer(EMAError eMAError) {
        return nativeGetContactsFromServer(eMAError);
    }

    public List<String> getSelfIdsOnOtherPlatform(EMAError eMAError) {
        return nativeGetSelfIdsOnOtherPlatform(eMAError);
    }

    public void inviteContact(String str, String str2, EMAError eMAError) {
        nativeInviteContact(str, str2, eMAError);
    }

    public native void nativeAcceptInvitation(String str, EMAError eMAError);

    public native void nativeAddToBlackList(String str, boolean z2, EMAError eMAError);

    public native void nativeDeclineInvitation(String str, EMAError eMAError);

    public native void nativeDeleteContact(String str, EMAError eMAError, boolean z2);

    public native List<String> nativeGetBlackListFromDB(EMAError eMAError);

    public native List<String> nativeGetBlackListFromServer(EMAError eMAError);

    public native List<String> nativeGetContactsFromDB(EMAError eMAError);

    public native List<String> nativeGetContactsFromServer(EMAError eMAError);

    public native List<String> nativeGetSelfIdsOnOtherPlatform(EMAError eMAError);

    public native void nativeInit(EMAContactManager eMAContactManager);

    public native void nativeInviteContact(String str, String str2, EMAError eMAError);

    public native void nativeRegisterContactListener(EMAContactListener eMAContactListener);

    public native void nativeRemoveContactListener(EMAContactListener eMAContactListener);

    public native void nativeRemoveFromBlackList(String str, EMAError eMAError);

    public native void nativeSaveBlackList(List<String> list, EMAError eMAError);

    public native void nativeSetSupportRosterVersion(boolean z2);

    public void registerContactListener(EMAContactListener eMAContactListener) {
        this.listeners.add(eMAContactListener);
        nativeRegisterContactListener(eMAContactListener);
    }

    public void removeContactListener(EMAContactListener eMAContactListener) {
        this.listeners.remove(eMAContactListener);
        nativeRemoveContactListener(eMAContactListener);
    }

    public void removeFromBlackList(String str, EMAError eMAError) {
        nativeRemoveFromBlackList(str, eMAError);
    }

    public void saveBlackList(List<String> list, EMAError eMAError) {
        nativeSaveBlackList(list, eMAError);
    }

    public void setSupportRosterVersion(boolean z2) {
        nativeSetSupportRosterVersion(z2);
    }
}
