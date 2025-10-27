package com.plv.socket.impl;

import android.text.TextUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.socketio.PLVSocketIO;
import io.socket.emitter.Emitter;
import java.util.Arrays;
import java.util.LinkedHashMap;

/* loaded from: classes5.dex */
public class PLVSocketListenMap {
    private LinkedHashMap<String, Emitter.Listener> map = new LinkedHashMap<>();

    public void clear() {
        this.map.clear();
    }

    public LinkedHashMap<String, Emitter.Listener> getMap() {
        return this.map;
    }

    public void put(String... strArr) {
        if (strArr != null) {
            for (String str : strArr) {
                this.map.put(str, new PLVListenEventListener(str));
            }
        }
    }

    public static class PLVListenEventListener implements Emitter.Listener {
        private String custeomerEventlistener;

        public PLVListenEventListener(String str) {
            this.custeomerEventlistener = str;
        }

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            String str = this.custeomerEventlistener;
            if (str != null) {
                call(str, objArr);
            }
        }

        public void call(String str, Object... objArr) {
            PLVCommonLog.d(PLVSocketIO.TAG, str + ": " + Arrays.toString(objArr));
            try {
                String str2 = (String) objArr[0];
                String event = PLVEventHelper.getEvent(str2);
                PLVSocketMessageObserver socketObserver = PLVSocketManager.getInstance().getSocketObserver();
                if (TextUtils.isEmpty(event)) {
                    event = str;
                }
                socketObserver.dispatchOnMessage(str, event, str2);
            } catch (Exception e2) {
                PLVCommonLog.exception(e2);
            }
        }
    }

    public void put(String str) {
        this.map.put(str, new PLVListenEventListener(str));
    }
}
