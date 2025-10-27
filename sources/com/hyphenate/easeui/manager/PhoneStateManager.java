package com.hyphenate.easeui.manager;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes4.dex */
public class PhoneStateManager {
    private static PhoneStateManager INSTANCE = null;
    private static final String TAG = "PhoneStateManager";
    private PhoneStateListener phoneStateListener = new PhoneStateListener() { // from class: com.hyphenate.easeui.manager.PhoneStateManager.1
        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i2, String str) {
            super.onCallStateChanged(i2, str);
            Iterator it = PhoneStateManager.this.stateCallbacks.iterator();
            while (it.hasNext()) {
                ((PhoneStateCallback) it.next()).onCallStateChanged(i2, str);
            }
        }
    };
    private List<PhoneStateCallback> stateCallbacks;
    private TelephonyManager telephonyManager;

    public interface PhoneStateCallback {
        void onCallStateChanged(int i2, String str);
    }

    private PhoneStateManager(Context context) {
        this.stateCallbacks = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        this.telephonyManager = telephonyManager;
        if (telephonyManager != null) {
            telephonyManager.listen(this.phoneStateListener, 32);
        }
        this.stateCallbacks = new CopyOnWriteArrayList();
    }

    public static PhoneStateManager get(Context context) {
        if (INSTANCE == null) {
            synchronized (PhoneStateManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhoneStateManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public void addStateCallback(PhoneStateCallback phoneStateCallback) {
        if (this.stateCallbacks.contains(phoneStateCallback)) {
            return;
        }
        this.stateCallbacks.add(phoneStateCallback);
    }

    public void finalize() throws Throwable {
        TelephonyManager telephonyManager = this.telephonyManager;
        if (telephonyManager != null) {
            telephonyManager.listen(this.phoneStateListener, 0);
        }
        super.finalize();
    }

    public void removeStateCallback(PhoneStateCallback phoneStateCallback) {
        if (this.stateCallbacks.contains(phoneStateCallback)) {
            this.stateCallbacks.remove(phoneStateCallback);
        }
    }
}
