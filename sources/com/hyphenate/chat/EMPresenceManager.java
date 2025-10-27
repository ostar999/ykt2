package com.hyphenate.chat;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMPresenceListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.chat.adapter.EMAPresence;
import com.hyphenate.chat.adapter.EMAPresenceManager;
import com.hyphenate.chat.adapter.EMAPresenceManagerListener;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes4.dex */
public class EMPresenceManager {
    EMAPresenceManager emaObject;
    private EMAPresenceManagerListener listenerImpl;
    private final String TAG = getClass().getSimpleName();
    private List<EMPresenceListener> listeners = new CopyOnWriteArrayList();

    public EMPresenceManager(EMAPresenceManager eMAPresenceManager) {
        EMAPresenceManagerListener eMAPresenceManagerListener = new EMAPresenceManagerListener() { // from class: com.hyphenate.chat.EMPresenceManager.1
            @Override // com.hyphenate.chat.adapter.EMAPresenceManagerListener, com.hyphenate.chat.adapter.EMAPresenceManagerListenerInterface
            public void onPresenceUpdated(List<EMAPresence> list) {
                synchronized (EMPresenceManager.this.listeners) {
                    Iterator it = EMPresenceManager.this.listeners.iterator();
                    while (it.hasNext()) {
                        try {
                            ((EMPresenceListener) it.next()).onPresenceUpdated(EMPresenceManager.this.convertToEMPresence(list));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            EMLog.e(EMPresenceManager.this.TAG, "EMPresenceManager->listenerImpl:onPresenceUpdated() Error:" + e2.getMessage());
                        }
                    }
                }
            }
        };
        this.listenerImpl = eMAPresenceManagerListener;
        this.emaObject = eMAPresenceManager;
        eMAPresenceManager.addListener(eMAPresenceManagerListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<EMPresence> convertToEMPresence(List<EMAPresence> list) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(new EMPresence(list.get(i2)));
        }
        return arrayList;
    }

    public void addListener(EMPresenceListener eMPresenceListener) {
        if (this.listeners.contains(eMPresenceListener)) {
            return;
        }
        this.listeners.add(eMPresenceListener);
    }

    public void clearListeners() {
        this.listeners.clear();
    }

    public void fetchPresenceStatus(final List<String> list, final EMValueCallBack<List<EMPresence>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPresenceManager.6
            @Override // java.lang.Runnable
            public void run() {
                EMAError eMAError = new EMAError();
                List<EMAPresence> listFetchPresenceStatus = EMPresenceManager.this.emaObject.fetchPresenceStatus(list, eMAError);
                if (eMAError.errCode() == 0) {
                    eMValueCallBack.onSuccess(EMPresenceManager.this.convertToEMPresence(listFetchPresenceStatus));
                } else {
                    eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                }
            }
        });
    }

    public void fetchSubscribedMembers(final int i2, final int i3, final EMValueCallBack<List<String>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPresenceManager.5
            @Override // java.lang.Runnable
            public void run() {
                EMAError eMAError = new EMAError();
                List<String> listFetchSubscribedMembers = EMPresenceManager.this.emaObject.fetchSubscribedMembers(i2, i3, eMAError);
                if (eMAError.errCode() == 0) {
                    eMValueCallBack.onSuccess(listFetchSubscribedMembers);
                } else {
                    eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                }
            }
        });
    }

    public void publishPresence(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPresenceManager.2
            @Override // java.lang.Runnable
            public void run() {
                EMAError eMAError = new EMAError();
                EMPresenceManager.this.emaObject.publishPresence(str, eMAError);
                if (eMAError.errCode() == 0) {
                    eMCallBack.onSuccess();
                } else {
                    eMCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                }
            }
        });
    }

    public void removeListener(EMPresenceListener eMPresenceListener) {
        if (eMPresenceListener != null) {
            this.listeners.remove(eMPresenceListener);
        }
    }

    public void subscribePresences(final List<String> list, final long j2, final EMValueCallBack<List<EMPresence>> eMValueCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPresenceManager.3
            @Override // java.lang.Runnable
            public void run() {
                EMAError eMAError = new EMAError();
                List<EMAPresence> listSubscribePresences = EMPresenceManager.this.emaObject.subscribePresences(list, j2, eMAError);
                if (eMAError.errCode() == 0) {
                    eMValueCallBack.onSuccess(EMPresenceManager.this.convertToEMPresence(listSubscribePresences));
                } else {
                    eMValueCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                }
            }
        });
    }

    public void unsubscribePresences(final List<String> list, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPresenceManager.4
            @Override // java.lang.Runnable
            public void run() {
                EMAError eMAError = new EMAError();
                EMPresenceManager.this.emaObject.unsubscribePresences(list, eMAError);
                if (eMAError.errCode() == 0) {
                    eMCallBack.onSuccess();
                } else {
                    eMCallBack.onError(eMAError.errCode(), eMAError.errMsg());
                }
            }
        });
    }
}
