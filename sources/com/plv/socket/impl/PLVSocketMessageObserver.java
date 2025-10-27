package com.plv.socket.impl;

import android.os.Looper;
import androidx.annotation.MainThread;
import com.plv.socket.socketio.PLVSocketIOObservable;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class PLVSocketMessageObserver extends PLVSocketIOObservable {
    private Map<OnMessageListener, String[]> onMessageListenerStringMap;

    public interface OnMessageListener {
        @MainThread
        void onMessage(String str, String str2, String str3);
    }

    public void addOnMessageListener(OnMessageListener onMessageListener) {
        addOnMessageListener(onMessageListener, null);
    }

    @Override // com.plv.socket.socketio.PLVSocketIOObservable
    public void clean() {
        clearOnMessageListener();
        super.clean();
    }

    public void clearOnMessageListener() {
        Map<OnMessageListener, String[]> map = this.onMessageListenerStringMap;
        if (map != null) {
            map.clear();
        }
    }

    public void dispatchOnMessage(final String str, final String str2, final String str3) {
        Map<OnMessageListener, String[]> map = this.onMessageListenerStringMap;
        if (map == null) {
            return;
        }
        for (Map.Entry<OnMessageListener, String[]> entry : map.entrySet()) {
            final OnMessageListener key = entry.getKey();
            String[] value = entry.getValue();
            boolean z2 = true;
            boolean z3 = value == null;
            if (z3) {
                z2 = z3;
            } else {
                for (String str4 : value) {
                    if (str4 != null && str4.equals(str)) {
                        break;
                    }
                }
                z2 = z3;
            }
            if (z2) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    key.onMessage(str, str2, str3);
                } else {
                    this.handler.post(new Runnable() { // from class: com.plv.socket.impl.PLVSocketMessageObserver.1
                        @Override // java.lang.Runnable
                        public void run() {
                            key.onMessage(str, str2, str3);
                        }
                    });
                }
            }
        }
    }

    public void removeOnMessageListener(OnMessageListener onMessageListener) {
        Map<OnMessageListener, String[]> map = this.onMessageListenerStringMap;
        if (map != null) {
            map.remove(onMessageListener);
        }
    }

    public void addOnMessageListener(OnMessageListener onMessageListener, String... strArr) {
        if (this.onMessageListenerStringMap == null) {
            this.onMessageListenerStringMap = new LinkedHashMap();
        }
        if (this.onMessageListenerStringMap.containsKey(onMessageListener)) {
            return;
        }
        this.onMessageListenerStringMap.put(onMessageListener, strArr);
    }
}
