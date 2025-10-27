package com.plv.business.service;

import android.text.TextUtils;
import android.util.Pair;
import com.plv.business.model.video.PLVPlayBackFullVO;
import com.plv.business.model.video.PLVPlayBackVO;
import com.plv.business.net.PLVCommonApiManager;
import com.plv.foundationsdk.config.PLVVideoViewConstant;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.model.domain.PLVChatDomain;
import com.plv.foundationsdk.model.domain.PLVChatDomainVO;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.net.api.PLVFoundationApiManager;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class PLVLoginManager {
    private static final String APP_ID = "appId";
    private static final String APP_SECRET = "appSecret";
    private static final String CHANNEL_ID = "channelId";
    private static final String TIMESTAMP = "timestamp";
    private static final String USER_ID = "userId";
    private static final String VID = "vid";

    public static Disposable checkLoginToken(String str, String str2, String str3, String str4, String str5, PLVrResponseCallback<PLVChatDomain> pLVrResponseCallback) {
        try {
            String strNotBlank = notBlank(str);
            notBlank(str2);
            String strNotBlank2 = notBlank(str3);
            String strNotBlank3 = notBlank(str4);
            String strNotBlank4 = notBlank(str5);
            Integer numValueOf = strNotBlank3 != null ? Integer.valueOf(strNotBlank3) : null;
            long jCurrentTimeMillis = System.currentTimeMillis();
            HashMap map = new HashMap();
            map.put("appId", strNotBlank2);
            map.put("vid", strNotBlank4);
            map.put("channelId", strNotBlank3);
            map.put("timestamp", jCurrentTimeMillis + "");
            map.put("appSecret", null);
            map.put("userId", strNotBlank);
            String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PLVVideoViewConstant.PREFIX, map);
            return PLVResponseExcutor.excuteUndefinData(PLVFoundationApiManager.getFoundationApi().requestLoginStatus(numValueOf, strNotBlank2, jCurrentTimeMillis + "", strArrCreateSignWithSignatureNonceEncrypt[0], strNotBlank4, strNotBlank, null, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVChatDomainVO>(PLVChatDomain.class) { // from class: com.plv.business.service.PLVLoginManager.2
                @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
                public Pair<Object, Boolean> accept(PLVChatDomainVO pLVChatDomainVO) {
                    return new Pair<>(pLVChatDomainVO.getDataObj(), Boolean.valueOf(pLVChatDomainVO.isEncryption()));
                }
            }).map(new Function<PLVChatDomainVO, PLVChatDomain>() { // from class: com.plv.business.service.PLVLoginManager.1
                @Override // io.reactivex.functions.Function
                public PLVChatDomain apply(PLVChatDomainVO pLVChatDomainVO) throws Exception {
                    return pLVChatDomainVO.getData();
                }
            }), pLVrResponseCallback);
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return null;
        }
    }

    public static Disposable getPlayBackType(String str, PLVrResponseCallback<PLVPlayBackVO> pLVrResponseCallback) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("vid", str);
        map.put("timestamp", jCurrentTimeMillis + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PLVVideoViewConstant.PREFIX, map);
        return PLVResponseExcutor.excuteUndefinData(PLVCommonApiManager.getPlvApiApi().getPlayBackStatus(str, jCurrentTimeMillis + "", strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPlayBackFullVO>(PLVPlayBackVO.class) { // from class: com.plv.business.service.PLVLoginManager.4
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPlayBackFullVO pLVPlayBackFullVO) {
                return new Pair<>(pLVPlayBackFullVO.getDataObj(), Boolean.valueOf(pLVPlayBackFullVO.isEncryption()));
            }
        }).map(new Function<PLVPlayBackFullVO, PLVPlayBackVO>() { // from class: com.plv.business.service.PLVLoginManager.3
            @Override // io.reactivex.functions.Function
            public PLVPlayBackVO apply(PLVPlayBackFullVO pLVPlayBackFullVO) throws Exception {
                return pLVPlayBackFullVO.getData();
            }
        }), pLVrResponseCallback);
    }

    private static String notBlank(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str;
    }
}
