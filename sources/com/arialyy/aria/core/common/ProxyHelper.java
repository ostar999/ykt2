package com.arialyy.aria.core.common;

import com.arialyy.annotations.TaskEnum;
import com.arialyy.aria.core.download.DownloadGroupTaskListener;
import com.arialyy.aria.core.download.DownloadTaskListener;
import com.arialyy.aria.core.scheduler.M3U8PeerTaskListener;
import com.arialyy.aria.core.scheduler.SubTaskListener;
import com.arialyy.aria.core.scheduler.TaskInternalListenerInterface;
import com.arialyy.aria.core.upload.UploadTaskListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class ProxyHelper {
    public static volatile ProxyHelper INSTANCE = null;
    public static int PROXY_TYPE_DOWNLOAD = 1;
    public static int PROXY_TYPE_DOWNLOAD_GROUP = 2;
    public static int PROXY_TYPE_DOWNLOAD_GROUP_SUB = 5;
    public static int PROXY_TYPE_M3U8_PEER = 4;
    public static int PROXY_TYPE_UPLOAD = 3;
    public Map<String, Set<Integer>> mProxyCache = new ConcurrentHashMap();

    private ProxyHelper() {
    }

    private boolean checkProxyExist(String str, String str2) throws ClassNotFoundException {
        String strConcat = str.concat(str2);
        try {
            if (getClass().getClassLoader().loadClass(strConcat) != null) {
                return true;
            }
            Class.forName(strConcat);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private Set<Integer> checkProxyTypeByInterface(Class cls) {
        if (!TaskInternalListenerInterface.class.isAssignableFrom(cls)) {
            return null;
        }
        HashSet hashSet = new HashSet();
        if (DownloadGroupTaskListener.class.isAssignableFrom(cls)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_DOWNLOAD_GROUP));
        }
        if (DownloadTaskListener.class.isAssignableFrom(cls)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_DOWNLOAD));
        }
        if (UploadTaskListener.class.isAssignableFrom(cls)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_UPLOAD));
        }
        if (M3U8PeerTaskListener.class.isAssignableFrom(cls)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_M3U8_PEER));
        }
        if (SubTaskListener.class.isAssignableFrom(cls)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_DOWNLOAD_GROUP_SUB));
        }
        return hashSet;
    }

    private Set<Integer> checkProxyTypeByProxyClass(Class cls) {
        String name = cls.getName();
        HashSet hashSet = new HashSet();
        if (checkProxyExist(name, TaskEnum.DOWNLOAD_GROUP.proxySuffix)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_DOWNLOAD_GROUP));
        }
        if (checkProxyExist(name, TaskEnum.DOWNLOAD.proxySuffix)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_DOWNLOAD));
        }
        if (checkProxyExist(name, TaskEnum.UPLOAD.proxySuffix)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_UPLOAD));
        }
        if (checkProxyExist(name, TaskEnum.M3U8_PEER.proxySuffix)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_M3U8_PEER));
        }
        if (checkProxyExist(name, TaskEnum.DOWNLOAD_GROUP_SUB.proxySuffix)) {
            hashSet.add(Integer.valueOf(PROXY_TYPE_DOWNLOAD_GROUP_SUB));
        }
        return hashSet;
    }

    public static ProxyHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (ProxyHelper.class) {
                INSTANCE = new ProxyHelper();
            }
        }
        return INSTANCE;
    }

    public Set<Integer> checkProxyType(Class cls) {
        Set<Integer> set = this.mProxyCache.get(cls.getName());
        if (set != null) {
            return set;
        }
        Set<Integer> setCheckProxyTypeByInterface = checkProxyTypeByInterface(cls);
        if (setCheckProxyTypeByInterface != null) {
            return setCheckProxyTypeByInterface;
        }
        Set<Integer> setCheckProxyTypeByProxyClass = checkProxyTypeByProxyClass(cls);
        if (!setCheckProxyTypeByProxyClass.isEmpty()) {
            this.mProxyCache.put(cls.getName(), setCheckProxyTypeByProxyClass);
        }
        return setCheckProxyTypeByProxyClass;
    }
}
