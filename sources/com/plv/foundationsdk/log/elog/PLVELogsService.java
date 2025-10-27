package com.plv.foundationsdk.log.elog;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.huawei.hms.support.api.push.PushException;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.model.log.PLVELogSendType;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class PLVELogsService {
    private static final int LOG_CAPACITY = 20;
    private static final int LOG_CAPACITY_SPLIT = 100;
    private static final int LOG_PATCHES_SEND_INTERVAL = 60000;
    private static final String TAG = "PLVELogsService";
    private static volatile PLVELogsService instance;

    @Nullable
    private IPLVStaticELogs eLogSender;
    private Disposable patchesTimer;

    @NonNull
    private ConcurrentHashMap<String, CopyOnWriteArrayList<PLVStatisticsBase>> immediatelyUnSendCaches = new ConcurrentHashMap<>();

    @NonNull
    private ConcurrentHashMap<String, CopyOnWriteArrayList<PLVStatisticsBase>> batchesUnSendCaches = new ConcurrentHashMap<>();
    private Executor sendLogsExecutor = Executors.newSingleThreadExecutor();

    public static class PLVSendLogsResult<T> {

        @Nullable
        private T data;
        private String errMsg;
        private boolean isSuccess;

        private PLVSendLogsResult() {
        }

        public static <T> PLVSendLogsResult<T> fail(String str) {
            PLVSendLogsResult<T> pLVSendLogsResult = new PLVSendLogsResult<>();
            ((PLVSendLogsResult) pLVSendLogsResult).data = null;
            ((PLVSendLogsResult) pLVSendLogsResult).errMsg = str;
            ((PLVSendLogsResult) pLVSendLogsResult).isSuccess = false;
            return pLVSendLogsResult;
        }

        public static <T> PLVSendLogsResult<T> success(T t2) {
            PLVSendLogsResult<T> pLVSendLogsResult = new PLVSendLogsResult<>();
            ((PLVSendLogsResult) pLVSendLogsResult).isSuccess = true;
            ((PLVSendLogsResult) pLVSendLogsResult).data = t2;
            return pLVSendLogsResult;
        }
    }

    private PLVELogsService() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PLVSendLogsResult<String> dispatchStaticsLogs(String str, PLVELogSendType pLVELogSendType, ConcurrentHashMap<String, CopyOnWriteArrayList<PLVStatisticsBase>> concurrentHashMap) {
        String str2 = TAG;
        PLVCommonLog.d(str2, "send message:" + str);
        List listSubList = concurrentHashMap.get(str);
        if (listSubList == null) {
            return PLVSendLogsResult.fail("elog list must not be null");
        }
        if (listSubList.isEmpty()) {
            return PLVSendLogsResult.success("");
        }
        if (listSubList.size() > 100) {
            PLVCommonLog.d(str2, "reach max " + listSubList.size());
            listSubList = listSubList.subList(0, 100);
        }
        String json = PLVGsonUtil.toJson(listSubList);
        boolean zSendELogSync = sendELogSync(json, pLVELogSendType);
        PLVCommonLog.d(str2, "send message:" + zSendELogSync + "\n" + json);
        if (!zSendELogSync) {
            return PLVSendLogsResult.fail("msg fail to send");
        }
        concurrentHashMap.get(str).removeAll(listSubList);
        PLVElogFileService.getInstance().deleteCache();
        return PLVSendLogsResult.success("");
    }

    public static PLVELogsService getInstance() {
        if (instance == null) {
            synchronized (PLVELogsService.class) {
                if (instance == null) {
                    instance = new PLVELogsService();
                }
            }
        }
        return instance;
    }

    private void startLogTimer(final String str, final PLVELogSendType pLVELogSendType) {
        try {
            if (this.patchesTimer != null) {
                return;
            }
            this.patchesTimer = PLVRxTimer.timer(60000, 60000, new Consumer<Long>() { // from class: com.plv.foundationsdk.log.elog.PLVELogsService.4
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l2) throws Exception {
                    PLVELogsService pLVELogsService = PLVELogsService.this;
                    PLVSendLogsResult pLVSendLogsResultDispatchStaticsLogs = pLVELogsService.dispatchStaticsLogs(str, pLVELogSendType, pLVELogsService.batchesUnSendCaches);
                    if (!pLVSendLogsResultDispatchStaticsLogs.isSuccess) {
                        PLVCommonLog.exception(new Exception(pLVSendLogsResultDispatchStaticsLogs.errMsg));
                    }
                    if (PLVELogsService.this.immediatelyUnSendCaches.get(str) != null) {
                        PLVELogsService pLVELogsService2 = PLVELogsService.this;
                        PLVSendLogsResult pLVSendLogsResultDispatchStaticsLogs2 = pLVELogsService2.dispatchStaticsLogs(str, pLVELogSendType, pLVELogsService2.immediatelyUnSendCaches);
                        if (pLVSendLogsResultDispatchStaticsLogs2.isSuccess) {
                            return;
                        }
                        PLVCommonLog.exception(new Exception(pLVSendLogsResultDispatchStaticsLogs2.errMsg));
                    }
                }
            }, false);
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSendLog(PLVStatisticsBase pLVStatisticsBase) {
        if (TextUtils.isEmpty(pLVStatisticsBase.getProjectName())) {
            PLVCommonLog.exception(new Exception("project name cannot be null"));
            return;
        }
        String projectName = pLVStatisticsBase.getProjectName();
        PLVELogSendType eLogSendType = pLVStatisticsBase.getELogSendType();
        PLVElogFileService.getInstance().registerProject(pLVStatisticsBase);
        boolean z2 = !pLVStatisticsBase.isNeedBatches();
        ConcurrentHashMap<String, CopyOnWriteArrayList<PLVStatisticsBase>> concurrentHashMap = !z2 ? this.batchesUnSendCaches : this.immediatelyUnSendCaches;
        CopyOnWriteArrayList<PLVStatisticsBase> copyOnWriteArrayList = concurrentHashMap.get(projectName);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            concurrentHashMap.put(projectName, copyOnWriteArrayList);
        }
        copyOnWriteArrayList.add(pLVStatisticsBase);
        if (z2) {
            PLVSendLogsResult<String> pLVSendLogsResultDispatchStaticsLogs = dispatchStaticsLogs(projectName, eLogSendType, concurrentHashMap);
            if (((PLVSendLogsResult) pLVSendLogsResultDispatchStaticsLogs).isSuccess) {
                return;
            }
            PLVCommonLog.exception(new Exception(((PLVSendLogsResult) pLVSendLogsResultDispatchStaticsLogs).errMsg));
            return;
        }
        if (concurrentHashMap.get(projectName).size() < 20) {
            startLogTimer(projectName, eLogSendType);
            return;
        }
        PLVSendLogsResult<String> pLVSendLogsResultDispatchStaticsLogs2 = dispatchStaticsLogs(projectName, eLogSendType, concurrentHashMap);
        if (!((PLVSendLogsResult) pLVSendLogsResultDispatchStaticsLogs2).isSuccess) {
            PLVCommonLog.exception(new Exception(((PLVSendLogsResult) pLVSendLogsResultDispatchStaticsLogs2).errMsg));
        }
        Disposable disposable = this.patchesTimer;
        if (disposable != null) {
            disposable.dispose();
            this.patchesTimer = null;
        }
    }

    public synchronized void addStaticsLog(final PLVStatisticsBase pLVStatisticsBase) {
        if (pLVStatisticsBase == null) {
            return;
        }
        PLVElogErrorCodeCallbackRegistry.getInstance().dispatchErrorMessage(pLVStatisticsBase.getModule(), pLVStatisticsBase);
        this.sendLogsExecutor.execute(new Runnable() { // from class: com.plv.foundationsdk.log.elog.PLVELogsService.1
            @Override // java.lang.Runnable
            public void run() {
                PLVELogsService.this.startSendLog(pLVStatisticsBase);
            }
        });
    }

    public void destroy() {
        this.eLogSender = null;
        this.batchesUnSendCaches.clear();
        this.immediatelyUnSendCaches.clear();
        PLVElogFileService.getInstance().destroy();
        Disposable disposable = this.patchesTimer;
        if (disposable != null) {
            disposable.dispose();
            this.patchesTimer = null;
        }
        PLVElogErrorCodeCallbackRegistry.getInstance().destroy();
    }

    public void readLogCaches(List<PLVStatisticsBase> list, String str) {
        if (list == null) {
            return;
        }
        CopyOnWriteArrayList<PLVStatisticsBase> copyOnWriteArrayList = this.immediatelyUnSendCaches.get(str);
        CopyOnWriteArrayList<PLVStatisticsBase> copyOnWriteArrayList2 = this.batchesUnSendCaches.get(str);
        if (copyOnWriteArrayList != null) {
            list.addAll(copyOnWriteArrayList);
        }
        if (copyOnWriteArrayList2 != null) {
            list.addAll(copyOnWriteArrayList2);
        }
    }

    public boolean sendELogSync(String str, PLVELogSendType pLVELogSendType) {
        IPLVStaticELogs iPLVStaticELogs = this.eLogSender;
        boolean z2 = false;
        if (iPLVStaticELogs != null) {
            try {
                if (iPLVStaticELogs.sendLogs(pLVELogSendType, str).execute().isSuccessful()) {
                    PLVCommonLog.d(TAG, "send message success");
                    z2 = true;
                } else {
                    PLVCommonLog.d(TAG, PushException.EXCEPTION_SEND_FAILED);
                }
            } catch (Exception e2) {
                PLVCommonLog.e(TAG, "sendELogSync:" + e2.getMessage());
            }
        }
        return z2;
    }

    public void setELogSender(@Nullable IPLVStaticELogs iPLVStaticELogs) {
        this.eLogSender = iPLVStaticELogs;
    }

    public void updateLocalCaches(List<PLVStatisticsBase> list, String str) {
        if (list == null) {
            return;
        }
        CopyOnWriteArrayList<PLVStatisticsBase> copyOnWriteArrayList = this.immediatelyUnSendCaches.get(str);
        if (copyOnWriteArrayList == null) {
            copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            this.immediatelyUnSendCaches.put(str, copyOnWriteArrayList);
        }
        copyOnWriteArrayList.addAll(list);
    }

    public <T extends PLVStatisticsBase> void addStaticsLog(final Class<T> cls, final String str, final String str2, final String... strArr) {
        this.sendLogsExecutor.execute(new Runnable() { // from class: com.plv.foundationsdk.log.elog.PLVELogsService.2
            @Override // java.lang.Runnable
            public void run() {
                PLVStatisticsBase pLVStatisticsBaseCreateElogNormalEntity = PLVElogHelper.createElogNormalEntity((Class<PLVStatisticsBase>) cls, str, str2, strArr);
                if (pLVStatisticsBaseCreateElogNormalEntity == null) {
                    return;
                }
                PLVELogsService.this.startSendLog(pLVStatisticsBaseCreateElogNormalEntity);
            }
        });
    }

    public <T extends PLVStatisticsBase> void addStaticsLog(final Class<T> cls, final String str, final PLVLogFileBase pLVLogFileBase, final String... strArr) {
        this.sendLogsExecutor.execute(new Runnable() { // from class: com.plv.foundationsdk.log.elog.PLVELogsService.3
            @Override // java.lang.Runnable
            public void run() throws NoSuchMethodException, SecurityException {
                PLVStatisticsBase pLVStatisticsBaseCreateElogNormalEntity = PLVElogHelper.createElogNormalEntity((Class<PLVStatisticsBase>) cls, str, pLVLogFileBase, strArr);
                if (pLVStatisticsBaseCreateElogNormalEntity == null) {
                    return;
                }
                PLVELogsService.this.startSendLog(pLVStatisticsBaseCreateElogNormalEntity);
            }
        });
    }
}
