package com.plv.socket.socketio;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import com.plv.socket.status.PLVSocketStatus;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class PLVSocketIOObservable {
    protected Handler handler = new Handler(Looper.getMainLooper());
    private PLVSocketStatus lastDispatchStatus;
    private List<OnConnectStatusListener> onConnectStatusListeners;
    private Map<OnSocketEventListener, String> onSocketEventListenerStringMap;

    public interface OnConnectStatusListener {
        @MainThread
        void onStatus(PLVSocketStatus pLVSocketStatus);
    }

    public interface OnSocketEventListener {
        @MainThread
        void onMessage(String str, Object... objArr);
    }

    public void addOnConnectStatusListener(OnConnectStatusListener onConnectStatusListener) {
        if (this.onConnectStatusListeners == null) {
            this.onConnectStatusListeners = new ArrayList();
        }
        if (this.onConnectStatusListeners.contains(onConnectStatusListener)) {
            return;
        }
        this.onConnectStatusListeners.add(onConnectStatusListener);
    }

    public void addOnSocketEventListener(OnSocketEventListener onSocketEventListener) {
        addOnSocketEventListener(onSocketEventListener, null);
    }

    public void clean() {
        clearOnSocketEventListener();
        clearOnConnectStatusListener();
        this.handler.removeCallbacksAndMessages(null);
    }

    public void clearOnConnectStatusListener() {
        List<OnConnectStatusListener> list = this.onConnectStatusListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void clearOnSocketEventListener() {
        Map<OnSocketEventListener, String> map = this.onSocketEventListenerStringMap;
        if (map != null) {
            map.clear();
        }
    }

    public void dispatchOnMessage(final String str, final Object... objArr) {
        Map<OnSocketEventListener, String> map = this.onSocketEventListenerStringMap;
        if (map == null) {
            return;
        }
        for (Map.Entry<OnSocketEventListener, String> entry : map.entrySet()) {
            final OnSocketEventListener key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.equals(str)) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    key.onMessage(str, objArr);
                } else {
                    this.handler.post(new Runnable() { // from class: com.plv.socket.socketio.PLVSocketIOObservable.1
                        @Override // java.lang.Runnable
                        public void run() {
                            key.onMessage(str, objArr);
                        }
                    });
                }
            }
        }
    }

    public void dispatchOnStatus(final PLVSocketStatus pLVSocketStatus) {
        if (pLVSocketStatus == null || getLastDispatchStatus().getStatus() == pLVSocketStatus.getStatus()) {
            return;
        }
        this.lastDispatchStatus = pLVSocketStatus;
        List<OnConnectStatusListener> list = this.onConnectStatusListeners;
        if (list != null) {
            for (final OnConnectStatusListener onConnectStatusListener : list) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    onConnectStatusListener.onStatus(pLVSocketStatus);
                } else {
                    this.handler.post(new Runnable() { // from class: com.plv.socket.socketio.PLVSocketIOObservable.2
                        @Override // java.lang.Runnable
                        public void run() {
                            onConnectStatusListener.onStatus(pLVSocketStatus);
                        }
                    });
                }
            }
        }
    }

    public PLVSocketStatus getLastDispatchStatus() {
        PLVSocketStatus pLVSocketStatus = this.lastDispatchStatus;
        return pLVSocketStatus == null ? PLVSocketStatus.create(0) : pLVSocketStatus;
    }

    public void removeOnConnectStatusListener(OnConnectStatusListener onConnectStatusListener) {
        List<OnConnectStatusListener> list = this.onConnectStatusListeners;
        if (list != null) {
            list.remove(onConnectStatusListener);
        }
    }

    public void removeOnSocketEventListener(OnSocketEventListener onSocketEventListener) {
        Map<OnSocketEventListener, String> map = this.onSocketEventListenerStringMap;
        if (map != null) {
            map.remove(onSocketEventListener);
        }
    }

    public void addOnSocketEventListener(OnSocketEventListener onSocketEventListener, String str) {
        if (this.onSocketEventListenerStringMap == null) {
            this.onSocketEventListenerStringMap = new LinkedHashMap();
        }
        if (this.onSocketEventListenerStringMap.containsKey(onSocketEventListener)) {
            return;
        }
        this.onSocketEventListenerStringMap.put(onSocketEventListener, str);
    }
}
