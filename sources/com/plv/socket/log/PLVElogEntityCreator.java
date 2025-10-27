package com.plv.socket.log;

import com.plv.foundationsdk.log.elog.PLVElogHelper;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;

/* loaded from: classes5.dex */
public class PLVElogEntityCreator {
    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsBaseSocket createElogErrorEntity(Class<T> cls, int i2, String str) throws NoSuchMethodException, SecurityException {
        PLVErrorCodeInfoBase messageInfo = PLVElogHelper.getMessageInfo(cls, i2);
        if (messageInfo == null) {
            return null;
        }
        messageInfo.setMessage(messageInfo.getMessage() + "\n" + str);
        PLVStatisticsBaseSocket pLVStatisticsBaseSocket = new PLVStatisticsBaseSocket(new PLVLogFileBase(messageInfo.getMessage()));
        pLVStatisticsBaseSocket.setErrorCode(messageInfo.getCode());
        pLVStatisticsBaseSocket.setModule(messageInfo.createModuleName());
        pLVStatisticsBaseSocket.setEvent(messageInfo.createEventName());
        return pLVStatisticsBaseSocket;
    }

    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsBaseSocket createElogErrorEntity(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase) {
        PLVErrorCodeInfoBase messageInfo = PLVElogHelper.getMessageInfo(cls, i2);
        if (messageInfo == null) {
            return null;
        }
        messageInfo.setMessage(messageInfo.getMessage() + "\n" + str);
        pLVLogFileBase.setInformation(messageInfo.getMessage());
        PLVStatisticsBaseSocket pLVStatisticsBaseSocket = new PLVStatisticsBaseSocket(pLVLogFileBase);
        pLVStatisticsBaseSocket.setErrorCode(messageInfo.getCode());
        pLVStatisticsBaseSocket.setModule(messageInfo.createModuleName());
        pLVStatisticsBaseSocket.setEvent(messageInfo.createEventName());
        return pLVStatisticsBaseSocket;
    }
}
