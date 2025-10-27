package com.unity3d.splash.services.ads.load;

import android.text.TextUtils;
import com.unity3d.splash.services.core.configuration.IInitializationListener;
import com.unity3d.splash.services.core.configuration.IInitializationNotificationCenter;
import com.unity3d.splash.services.core.configuration.InitializationNotificationCenter;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.util.LinkedHashMap;

/* loaded from: classes6.dex */
public class LoadModule implements IInitializationListener {
    private static LoadModule instance;
    private IInitializationNotificationCenter _initializationNotificationCenter;
    private ILoadBridge _loadBridge;
    private LinkedHashMap _loadEventBuffer = new LinkedHashMap();

    public LoadModule(ILoadBridge iLoadBridge, IInitializationNotificationCenter iInitializationNotificationCenter) {
        this._loadBridge = iLoadBridge;
        this._initializationNotificationCenter = iInitializationNotificationCenter;
        iInitializationNotificationCenter.addListener(this);
    }

    private void addPlacementId(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!this._loadEventBuffer.containsKey(str)) {
            this._loadEventBuffer.put(str, new Integer(1));
            return;
        }
        Integer num = (Integer) this._loadEventBuffer.get(str);
        if (num == null) {
            this._loadEventBuffer.put(str, new Integer(1));
        } else {
            this._loadEventBuffer.put(str, Integer.valueOf(num.intValue() + 1));
        }
    }

    public static LoadModule getInstance() {
        if (instance == null) {
            instance = new LoadModule(new LoadBridge(), InitializationNotificationCenter.getInstance());
        }
        return instance;
    }

    private void sendLoadEvents() {
        if (this._loadEventBuffer.keySet().size() > 0) {
            this._loadBridge.loadPlacements(this._loadEventBuffer);
        }
        this._loadEventBuffer = new LinkedHashMap();
    }

    public synchronized void load(String str) {
        addPlacementId(str);
        if (SdkProperties.isInitialized()) {
            sendLoadEvents();
        }
    }

    @Override // com.unity3d.splash.services.core.configuration.IInitializationListener
    public void onSdkInitializationFailed(String str, int i2) {
    }

    @Override // com.unity3d.splash.services.core.configuration.IInitializationListener
    public synchronized void onSdkInitialized() {
        sendLoadEvents();
    }
}
