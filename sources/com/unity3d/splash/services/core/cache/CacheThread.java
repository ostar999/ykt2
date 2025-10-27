package com.unity3d.splash.services.core.cache;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.tencent.open.SocialConstants;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes6.dex */
public class CacheThread extends Thread {
    public static final int MSG_DOWNLOAD = 1;
    private static int _connectTimeout = 30000;
    private static CacheThreadHandler _handler = null;
    private static int _progressInterval = 0;
    private static int _readTimeout = 30000;
    private static boolean _ready = false;
    private static final Object _readyLock = new Object();

    public static void cancel() {
        if (_ready) {
            _handler.removeMessages(1);
            _handler.setCancelStatus(true);
        }
    }

    public static synchronized void download(String str, String str2, HashMap map, boolean z2) {
        if (!_ready) {
            init();
        }
        Bundle bundle = new Bundle();
        bundle.putString(SocialConstants.PARAM_SOURCE, str);
        bundle.putString(TypedValues.AttributesType.S_TARGET, str2);
        bundle.putInt("connectTimeout", _connectTimeout);
        bundle.putInt("readTimeout", _readTimeout);
        bundle.putInt("progressInterval", _progressInterval);
        bundle.putBoolean(RequestParameters.SUBRESOURCE_APPEND, z2);
        if (map != null) {
            for (String str3 : map.keySet()) {
                bundle.putStringArray(str3, (String[]) ((List) map.get(str3)).toArray(new String[((List) map.get(str3)).size()]));
            }
        }
        Message message = new Message();
        message.what = 1;
        message.setData(bundle);
        _handler.setCancelStatus(false);
        _handler.sendMessage(message);
    }

    public static int getConnectTimeout() {
        return _connectTimeout;
    }

    public static int getProgressInterval() {
        return _progressInterval;
    }

    public static int getReadTimeout() {
        return _readTimeout;
    }

    private static void init() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        CacheThread cacheThread = new CacheThread();
        cacheThread.setName("UnityAdsCacheThread");
        cacheThread.start();
        while (!_ready) {
            try {
                Object obj = _readyLock;
                synchronized (obj) {
                    obj.wait();
                }
            } catch (InterruptedException unused) {
                DeviceLog.debug("Couldn't synchronize thread");
            }
        }
    }

    public static boolean isActive() {
        if (_ready) {
            return _handler.isActive();
        }
        return false;
    }

    public static void setConnectTimeout(int i2) {
        _connectTimeout = i2;
    }

    public static void setProgressInterval(int i2) {
        _progressInterval = i2;
    }

    public static void setReadTimeout(int i2) {
        _readTimeout = i2;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Looper.prepare();
        _handler = new CacheThreadHandler();
        _ready = true;
        Object obj = _readyLock;
        synchronized (obj) {
            obj.notify();
        }
        Looper.loop();
    }
}
