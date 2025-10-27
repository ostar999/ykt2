package com.plv.livescenes.linkmic.manager;

import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.IPLVErrorCodeSender;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.linkmic.log.PLVLinkMicErrorCodeSender;
import com.plv.linkmic.log.PLVLinkMicTraceLogSender;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.log.PLVElogEntityCreator;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;

/* loaded from: classes4.dex */
public class PLVLinkMicManagerFactory {
    private static final String TAG = "PLVLinkMicManagerFactory";

    public static IPLVLinkMicManager createNewLinkMicManager() {
        PLVLinkMicErrorCodeSender pLVLinkMicErrorCodeSender = new PLVLinkMicErrorCodeSender();
        pLVLinkMicErrorCodeSender.setElogVOCreator(new IPLVErrorCodeSender.ELogVOCreator() { // from class: com.plv.livescenes.linkmic.manager.PLVLinkMicManagerFactory.1
            @Override // com.plv.foundationsdk.log.elog.IPLVErrorCodeSender.ELogVOCreator
            public <T extends PLVErrorCodeInfoBase> PLVStatisticsBase createElogVO(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase) {
                return PLVElogEntityCreator.createLiveEntity(cls, i2, str, pLVLogFileBase, "");
            }
        });
        PLVLinkMicTraceLogSender pLVLinkMicTraceLogSender = new PLVLinkMicTraceLogSender();
        pLVLinkMicTraceLogSender.setLogModuleClass(PLVLinkMicELog.class);
        PLVLinkMicManager pLVLinkMicManager = new PLVLinkMicManager(pLVLinkMicErrorCodeSender, pLVLinkMicTraceLogSender, new PLVLinkMicDataRepository());
        PLVCommonLog.d(TAG, "createNewLinkMicManager");
        return pLVLinkMicManager;
    }
}
