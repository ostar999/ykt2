package com.plv.livescenes.streamer.manager;

import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.IPLVErrorCodeSender;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;
import com.plv.linkmic.log.PLVLinkMicErrorCodeSender;
import com.plv.linkmic.log.PLVLinkMicTraceLogSender;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.livescenes.log.PLVElogEntityCreator;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import com.plv.livescenes.streamer.IPLVStreamerManager;

/* loaded from: classes5.dex */
public class PLVStreamerManagerFactory {
    private static final String TAG = "PLVStreamerManagerFactory";

    public static IPLVStreamerManager createNewStreamerManager() {
        PLVLinkMicErrorCodeSender pLVLinkMicErrorCodeSender = new PLVLinkMicErrorCodeSender();
        pLVLinkMicErrorCodeSender.setElogVOCreator(new IPLVErrorCodeSender.ELogVOCreator() { // from class: com.plv.livescenes.streamer.manager.PLVStreamerManagerFactory.1
            @Override // com.plv.foundationsdk.log.elog.IPLVErrorCodeSender.ELogVOCreator
            public <T extends PLVErrorCodeInfoBase> PLVStatisticsBase createElogVO(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase) {
                return PLVElogEntityCreator.createLiveEntity(cls, i2, str, pLVLogFileBase);
            }
        });
        PLVLinkMicTraceLogSender pLVLinkMicTraceLogSender = new PLVLinkMicTraceLogSender();
        pLVLinkMicTraceLogSender.setLogModuleClass(PLVLinkMicELog.class);
        PLVLinkMicSipDecorator pLVLinkMicSipDecorator = new PLVLinkMicSipDecorator(new PLVStreamerManager(pLVLinkMicErrorCodeSender, pLVLinkMicTraceLogSender, new PLVLinkMicDataRepository()));
        PLVCommonLog.d(TAG, "createNewStreamerManager");
        return pLVLinkMicSipDecorator;
    }
}
