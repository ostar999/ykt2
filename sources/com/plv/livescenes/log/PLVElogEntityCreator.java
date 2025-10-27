package com.plv.livescenes.log;

import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.elog.PLVElogHelper;
import com.plv.foundationsdk.log.elog.logcode.PLVErrorCodeInfoBase;
import com.plv.foundationsdk.model.log.PLVLogFileBase;

/* loaded from: classes4.dex */
public class PLVElogEntityCreator {
    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsBaseLive createLiveEntity(Class<T> cls, int i2, String str) {
        return createLiveEntity(cls, i2, str, PolyvLiveSDKClient.getInstance().getUserId());
    }

    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsPlayback createPlaybackEntity(Class<T> cls, int i2, String str) throws NoSuchMethodException, SecurityException {
        PLVErrorCodeInfoBase messageInfo = PLVElogHelper.getMessageInfo(cls, i2);
        if (messageInfo == null) {
            return null;
        }
        messageInfo.setMessage(messageInfo.getMessage() + "\n" + str);
        PLVStatisticsPlayback pLVStatisticsPlayback = new PLVStatisticsPlayback(new PLVLogFileBase(messageInfo.getMessage()));
        pLVStatisticsPlayback.setErrorCode(messageInfo.getCode());
        pLVStatisticsPlayback.setModule(messageInfo.createModuleName());
        pLVStatisticsPlayback.setEvent(messageInfo.createEventName());
        return pLVStatisticsPlayback;
    }

    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsBaseLive createLiveEntity(Class<T> cls, int i2, String str, String str2) throws NoSuchMethodException, SecurityException {
        PLVErrorCodeInfoBase messageInfo = PLVElogHelper.getMessageInfo(cls, i2);
        if (messageInfo == null) {
            return null;
        }
        messageInfo.setMessage(messageInfo.getMessage() + "\n" + str);
        PLVStatisticsBaseLive pLVStatisticsBaseLive = new PLVStatisticsBaseLive(new PLVLogFileBase(messageInfo.getMessage()));
        pLVStatisticsBaseLive.setErrorCode(messageInfo.getCode());
        pLVStatisticsBaseLive.setModule(messageInfo.createModuleName());
        pLVStatisticsBaseLive.setEvent(messageInfo.createEventName());
        pLVStatisticsBaseLive.setUserId2(str2);
        return pLVStatisticsBaseLive;
    }

    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsPlayback createPlaybackEntity(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase) throws NoSuchMethodException, SecurityException {
        PLVErrorCodeInfoBase messageInfo = PLVElogHelper.getMessageInfo(cls, i2);
        if (messageInfo == null) {
            return null;
        }
        messageInfo.setMessage(messageInfo.getMessage() + "\n" + str);
        pLVLogFileBase.setInformation(messageInfo.getMessage());
        PLVStatisticsPlayback pLVStatisticsPlayback = new PLVStatisticsPlayback(pLVLogFileBase);
        pLVStatisticsPlayback.setErrorCode(messageInfo.getCode());
        pLVStatisticsPlayback.setModule(messageInfo.createModuleName());
        pLVStatisticsPlayback.setEvent(messageInfo.createEventName());
        return pLVStatisticsPlayback;
    }

    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsBaseLive createLiveEntity(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase) {
        return createLiveEntity(cls, i2, str, pLVLogFileBase, PolyvLiveSDKClient.getInstance().getUserId());
    }

    public static <T extends PLVErrorCodeInfoBase> PLVStatisticsBaseLive createLiveEntity(Class<T> cls, int i2, String str, PLVLogFileBase pLVLogFileBase, String str2) throws NoSuchMethodException, SecurityException {
        PLVErrorCodeInfoBase messageInfo = PLVElogHelper.getMessageInfo(cls, i2);
        if (messageInfo == null) {
            return null;
        }
        messageInfo.setMessage(messageInfo.getMessage() + "\n" + str);
        pLVLogFileBase.setInformation(messageInfo.getMessage());
        PLVStatisticsBaseLive pLVStatisticsBaseLive = new PLVStatisticsBaseLive(pLVLogFileBase);
        pLVStatisticsBaseLive.setErrorCode(messageInfo.getCode());
        pLVStatisticsBaseLive.setModule(messageInfo.createModuleName());
        pLVStatisticsBaseLive.setEvent(messageInfo.createEventName());
        pLVStatisticsBaseLive.setUserId2(str2);
        return pLVStatisticsBaseLive;
    }
}
