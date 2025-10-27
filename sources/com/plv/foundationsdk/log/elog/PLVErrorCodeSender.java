package com.plv.foundationsdk.log.elog;

import android.util.Log;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.IPLVErrorCodeSender;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;

/* loaded from: classes4.dex */
public class PLVErrorCodeSender implements IPLVErrorCodeSender {
    private static final int ERROR_CREATING_ENTITY = -1;
    private IPLVErrorCodeSender.ELogVOCreator eLogVOCreator;

    private PLVStatisticsBase createLogEntity(Class<? extends PLVErrorCodeInfoBase> cls, int i2, String str, Throwable th) {
        PLVLogFileBase pLVLogFileBase = new PLVLogFileBase("", str + "\n" + Log.getStackTraceString(th));
        IPLVErrorCodeSender.ELogVOCreator eLogVOCreator = this.eLogVOCreator;
        if (eLogVOCreator != null) {
            return eLogVOCreator.createElogVO(cls, i2, "", pLVLogFileBase);
        }
        PLVCommonLog.exception(new RuntimeException("请调用setElogVOCreator()方法"));
        return null;
    }

    @Override // com.plv.foundationsdk.log.elog.IPLVErrorCodeSender
    public void setElogVOCreator(IPLVErrorCodeSender.ELogVOCreator eLogVOCreator) {
        this.eLogVOCreator = eLogVOCreator;
    }

    @Override // com.plv.foundationsdk.log.elog.IPLVErrorCodeSender
    public IPLVErrorCodeSender.SubmitResult submitError(Class<? extends PLVErrorCodeInfoBase> cls, int i2, String str, @Nullable Throwable th) {
        PLVStatisticsBase pLVStatisticsBaseCreateLogEntity = createLogEntity(cls, i2, str, th);
        if (pLVStatisticsBaseCreateLogEntity == null) {
            return new IPLVErrorCodeSender.SubmitResult(-1, "错误日志内部发生错误");
        }
        int errorCode = pLVStatisticsBaseCreateLogEntity.getErrorCode();
        String information = pLVStatisticsBaseCreateLogEntity.getLogFile().getInformation();
        PLVELogsService.getInstance().addStaticsLog(pLVStatisticsBaseCreateLogEntity);
        return new IPLVErrorCodeSender.SubmitResult(errorCode, information);
    }
}
