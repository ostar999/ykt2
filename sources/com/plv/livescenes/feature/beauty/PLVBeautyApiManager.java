package com.plv.livescenes.feature.beauty;

import android.text.TextUtils;
import android.util.Pair;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.feature.beauty.vo.PLVBeautySettingVO;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.thirdpart.blankj.utilcode.util.AppUtils;
import com.plv.thirdpart.blankj.utilcode.util.EncodeUtils;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.text.Charsets;

/* loaded from: classes4.dex */
public class PLVBeautyApiManager {
    private static final PLVBeautyApiManager INSTANCE = new PLVBeautyApiManager();

    private PLVBeautyApiManager() {
    }

    public static PLVBeautyApiManager getInstance() {
        return INSTANCE;
    }

    public Observable<PLVBeautySettingVO> getBeautySetting() {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        final String appPackageName = AppUtils.getAppPackageName();
        long jCurrentTimeMillis = System.currentTimeMillis();
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, PLVSugarUtil.mapOf(PLVSugarUtil.pair("appId", appId), PLVSugarUtil.pair("packageName", appPackageName), PLVSugarUtil.pair("timestamp", String.valueOf(jCurrentTimeMillis))));
        return PLVApiManager.getPlvLiveStatusApi().getBeautySetting(appId, appPackageName, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVBeautySettingVO>(PLVBeautySettingVO.Data.class) { // from class: com.plv.livescenes.feature.beauty.PLVBeautyApiManager.2
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVBeautySettingVO pLVBeautySettingVO) {
                return new Pair<>(pLVBeautySettingVO.getDataObj(), Boolean.valueOf(pLVBeautySettingVO.isEncryption()));
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).doOnNext(new Consumer<PLVBeautySettingVO>() { // from class: com.plv.livescenes.feature.beauty.PLVBeautyApiManager.1
            private String dec(String str, String str2, String str3) throws Exception {
                SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(str3.getBytes());
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(2, secretKeySpec, ivParameterSpec);
                return new String(cipher.doFinal(EncodeUtils.base64Decode(str)), Charsets.UTF_8);
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(PLVBeautySettingVO pLVBeautySettingVO) throws Exception {
                if (pLVBeautySettingVO.getSuccess() == null || !pLVBeautySettingVO.getSuccess().booleanValue() || pLVBeautySettingVO.getData() == null || TextUtils.isEmpty(pLVBeautySettingVO.getData().getMaterialUrl())) {
                    return;
                }
                String lowerCase = EncryptUtils.encryptMD5ToString(appPackageName + pLVBeautySettingVO.getData().getNonce()).toLowerCase();
                String strSubstring = lowerCase.substring(0, 16);
                String strSubstring2 = lowerCase.substring(16, 32);
                pLVBeautySettingVO.getData().setKey(dec(pLVBeautySettingVO.getData().getKey(), strSubstring, strSubstring2));
                pLVBeautySettingVO.getData().setSecret(dec(pLVBeautySettingVO.getData().getSecret(), strSubstring, strSubstring2));
            }
        });
    }
}
