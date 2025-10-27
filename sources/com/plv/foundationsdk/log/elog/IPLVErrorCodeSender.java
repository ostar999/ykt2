package com.plv.foundationsdk.log.elog;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;
import com.plv.foundationsdk.model.log.PLVStatisticsBase;

/* loaded from: classes4.dex */
public interface IPLVErrorCodeSender {

    public interface ELogVOCreator {
        <T extends PLVErrorCodeInfoBase> PLVStatisticsBase createElogVO(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase);
    }

    public static class SubmitResult {
        String counterPartMsgOfCode;
        int intactErrorCode;

        public SubmitResult(int i2, String str) {
            this.intactErrorCode = i2;
            this.counterPartMsgOfCode = str;
        }

        public String getCounterPartMsgOfCode() {
            return this.counterPartMsgOfCode;
        }

        public int getIntactErrorCode() {
            return this.intactErrorCode;
        }
    }

    void setElogVOCreator(ELogVOCreator eLogVOCreator);

    SubmitResult submitError(Class<? extends PLVErrorCodeInfoBase> cls, @IntRange(from = 0, to = 99) int i2, String str, @Nullable Throwable th);
}
