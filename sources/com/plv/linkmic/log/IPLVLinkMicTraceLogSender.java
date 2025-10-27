package com.plv.linkmic.log;

import com.plv.foundationsdk.model.log.PLVStatisticsBase;

/* loaded from: classes4.dex */
public interface IPLVLinkMicTraceLogSender {
    void setLogModuleClass(Class<? extends PLVStatisticsBase> cls);

    void submitTraceLog(String str, String str2);
}
