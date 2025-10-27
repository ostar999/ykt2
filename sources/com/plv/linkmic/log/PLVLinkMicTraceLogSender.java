package com.plv.linkmic.log;

import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;

/* loaded from: classes4.dex */
public class PLVLinkMicTraceLogSender implements IPLVLinkMicTraceLogSender {
    private Class<? extends PLVStatisticsBase> logModuleClass;

    @Override // com.plv.linkmic.log.IPLVLinkMicTraceLogSender
    public void setLogModuleClass(Class<? extends PLVStatisticsBase> cls) {
        this.logModuleClass = cls;
    }

    @Override // com.plv.linkmic.log.IPLVLinkMicTraceLogSender
    public void submitTraceLog(String str, String str2) {
        if (this.logModuleClass == null) {
            PLVCommonLog.exception(new RuntimeException("请调用setLogModuleClass()方法"));
        } else {
            PLVELogsService.getInstance().addStaticsLog(this.logModuleClass, str, str2, new String[0]);
        }
    }
}
