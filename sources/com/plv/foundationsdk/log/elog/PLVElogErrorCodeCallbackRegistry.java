package com.plv.foundationsdk.log.elog;

import android.os.Handler;
import android.os.Looper;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class PLVElogErrorCodeCallbackRegistry {
    private static final Handler S_HANDLER = new Handler(Looper.getMainLooper());
    private static PLVElogErrorCodeCallbackRegistry instance;
    private Map<String, List<ElogErrorCallback>> errorCallbackMap = new ConcurrentHashMap();

    public interface ElogErrorCallback {
        void elogError(PLVErrorCodeInfoBase pLVErrorCodeInfoBase);
    }

    public static PLVElogErrorCodeCallbackRegistry getInstance() {
        if (instance == null) {
            instance = new PLVElogErrorCodeCallbackRegistry();
        }
        return instance;
    }

    public void destroy() {
        Map<String, List<ElogErrorCallback>> map = this.errorCallbackMap;
        if (map != null) {
            map.clear();
        }
    }

    public void dispatchErrorMessage(String str, final PLVStatisticsBase pLVStatisticsBase) {
        if (this.errorCallbackMap.containsKey(str)) {
            final List<ElogErrorCallback> list = this.errorCallbackMap.get(str);
            final PLVErrorCodeInfoBase pLVErrorCodeInfoBase = new PLVErrorCodeInfoBase(pLVStatisticsBase.getErrorCode()) { // from class: com.plv.foundationsdk.log.elog.PLVElogErrorCodeCallbackRegistry.1
                @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
                public String createEventName() {
                    return null;
                }

                @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
                public int createModuleCode() {
                    return 0;
                }

                @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
                public String createModuleName() {
                    return pLVStatisticsBase.getModule();
                }

                @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
                public int firstTag() {
                    return 0;
                }

                @Override // com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase
                public String getErrorMessage(int i2) {
                    return null;
                }

                @Override // com.plv.foundationsdk.log.elog.logcode.PLVElogCodeModuleDes
                public int moduleCode() {
                    return 0;
                }
            };
            pLVErrorCodeInfoBase.setCode(pLVStatisticsBase.getErrorCode());
            pLVErrorCodeInfoBase.setMessage(pLVStatisticsBase.getLogFile().getInformation());
            if (Looper.myLooper() != Looper.getMainLooper()) {
                S_HANDLER.post(new Runnable() { // from class: com.plv.foundationsdk.log.elog.PLVElogErrorCodeCallbackRegistry.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            ((ElogErrorCallback) it.next()).elogError(pLVErrorCodeInfoBase);
                        }
                    }
                });
                return;
            }
            Iterator<ElogErrorCallback> it = list.iterator();
            while (it.hasNext()) {
                it.next().elogError(pLVErrorCodeInfoBase);
            }
        }
    }

    public List<ElogErrorCallback> moduleCallbacks(Class cls) {
        return Collections.unmodifiableList(this.errorCallbackMap.get(cls));
    }

    public void registerCallback(String str, ElogErrorCallback elogErrorCallback) {
        if (!this.errorCallbackMap.containsKey(str)) {
            this.errorCallbackMap.put(str, new ArrayList());
        }
        this.errorCallbackMap.get(str).add(elogErrorCallback);
    }

    public void removeCallback(Class cls, ElogErrorCallback elogErrorCallback) {
        if (this.errorCallbackMap.containsKey(cls)) {
            this.errorCallbackMap.get(cls).remove(elogErrorCallback);
        }
    }
}
