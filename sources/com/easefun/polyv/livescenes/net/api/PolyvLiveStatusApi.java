package com.easefun.polyv.livescenes.net.api;

import android.util.Pair;
import androidx.collection.ArrayMap;
import com.easefun.polyv.livescenes.model.commodity.saas.PolyvCommodityVO;
import com.plv.foundationsdk.net.PLVResponseApiBean;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.livescenes.model.PLVChannelSettingReqVO;
import com.plv.livescenes.model.PLVIncreasePageViewerVO;
import com.plv.livescenes.model.PLVLiveStatusVO2;
import com.plv.livescenes.model.commodity.saas.PLVCommodityVO;
import com.plv.livescenes.model.commodity.saas.PLVCommodityVO2;
import com.plv.livescenes.net.PLVApiManager;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Deprecated
/* loaded from: classes3.dex */
public enum PolyvLiveStatusApi {
    INSTANCE;

    public Observable<PLVLiveStatusVO2> getLiveStatusJson3(@Query("channelId") String str, @Query("timestamp") String str2, @Query("appId") String str3, String str4) {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", str3);
        arrayMap.put("timestamp", str2 + "");
        arrayMap.put("channelId", str);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str4, arrayMap);
        return PLVApiManager.getPlvLiveStatusApi().getLiveStatusJson3(str, str2 + "", str3, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVLiveStatusVO2>(String.class) { // from class: com.easefun.polyv.livescenes.net.api.PolyvLiveStatusApi.6
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVLiveStatusVO2 pLVLiveStatusVO2) {
                return new Pair<>(pLVLiveStatusVO2.getDataObj(), Boolean.valueOf(pLVLiveStatusVO2.isEncryption()));
            }
        });
    }

    public Observable<PolyvCommodityVO> getProductList(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, @Query("sign") String str3, @Query("signatureMethod") String str4) {
        return PLVApiManager.getPlvLiveStatusApi().getProductList(str, str2, j2, i2, str3, str4).map(new Function<PLVCommodityVO, PolyvCommodityVO>() { // from class: com.easefun.polyv.livescenes.net.api.PolyvLiveStatusApi.1
            @Override // io.reactivex.functions.Function
            public PolyvCommodityVO apply(@NonNull PLVCommodityVO pLVCommodityVO) throws Exception {
                return (PolyvCommodityVO) PLVReflectionUtil.copyField(pLVCommodityVO, new PolyvCommodityVO());
            }
        });
    }

    public Observable<PLVCommodityVO2> getProductList2(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, String str3) {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", str2);
        arrayMap.put("timestamp", j2 + "");
        arrayMap.put("channelId", str);
        arrayMap.put("count", i2 + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str3, arrayMap);
        return PLVApiManager.getPlvLiveStatusApi().getProductList2(str, str2, j2, i2, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVCommodityVO2>(PLVCommodityVO2.DataBean.class) { // from class: com.easefun.polyv.livescenes.net.api.PolyvLiveStatusApi.3
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVCommodityVO2 pLVCommodityVO2) {
                return new Pair<>(pLVCommodityVO2.getDataObj(), Boolean.valueOf(pLVCommodityVO2.isEncryption()));
            }
        });
    }

    public Observable<PLVResponseApiBean> increasePageViewer(@Field("channelId") int i2, @Field("appId") String str, @Field("timestamp") long j2, @Field("sign") String str2, @Field("signatureMethod") String str3, @Field("times") int i3) {
        return PLVApiManager.getPlvLiveStatusApi().increasePageViewer(i2, str, j2, str2, str3, i3);
    }

    public Observable<PLVIncreasePageViewerVO> increasePageViewer2(@Field("channelId") int i2, @Field("appId") String str, @Field("timestamp") long j2, String str2, @Field("times") int i3) {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("channelId", i2 + "");
        arrayMap.put("appId", str);
        arrayMap.put("timestamp", String.valueOf(j2));
        arrayMap.put("times", String.valueOf(i3));
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str2, arrayMap);
        return PLVApiManager.getPlvLiveStatusApi().increasePageViewer2(i2, str, j2, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), i3, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVIncreasePageViewerVO>(Integer.class) { // from class: com.easefun.polyv.livescenes.net.api.PolyvLiveStatusApi.5
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVIncreasePageViewerVO pLVIncreasePageViewerVO) {
                return new Pair<>(pLVIncreasePageViewerVO.getDataObj(), Boolean.valueOf(pLVIncreasePageViewerVO.isEncryption()));
            }
        });
    }

    public Observable<ResponseBody> updateChannelName(@Path("channelId") String str, @Field("ptime") long j2, @Field("name") String str2, @Field("sign") String str3) {
        return PLVApiManager.getPlvLiveStatusApi().updateChannelName(str, j2, str2, str3);
    }

    public Observable<ResponseBody> updateChannelSetting(String str, long j2, String str2, String str3, String str4) {
        PLVChannelSettingReqVO pLVChannelSettingReqVO = new PLVChannelSettingReqVO();
        PLVChannelSettingReqVO.BasicSettingBean basicSettingBean = new PLVChannelSettingReqVO.BasicSettingBean();
        basicSettingBean.setName(str3);
        pLVChannelSettingReqVO.setBasicSetting(basicSettingBean);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("channelId", str);
        arrayMap.put("timestamp", j2 + "");
        arrayMap.put("appId", str2);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str4, arrayMap);
        return PLVApiManager.getPlvLiveStatusApi().updateChannelSetting(str, j2, str2, strArrCreateSignWithSignatureNonceEncrypt[0], pLVChannelSettingReqVO, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]);
    }

    public Observable<PolyvCommodityVO> getProductList(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, @Query("rank") int i3, @Query("sign") String str3, @Query("signatureMethod") String str4) {
        return PLVApiManager.getPlvLiveStatusApi().getProductList(str, str2, j2, i2, i3, str3, str4).map(new Function<PLVCommodityVO, PolyvCommodityVO>() { // from class: com.easefun.polyv.livescenes.net.api.PolyvLiveStatusApi.2
            @Override // io.reactivex.functions.Function
            public PolyvCommodityVO apply(@NonNull PLVCommodityVO pLVCommodityVO) throws Exception {
                return (PolyvCommodityVO) PLVReflectionUtil.copyField(pLVCommodityVO, new PolyvCommodityVO());
            }
        });
    }

    public Observable<PLVCommodityVO2> getProductList2(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, @Query("rank") int i3, String str3) {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", str2);
        arrayMap.put("timestamp", j2 + "");
        arrayMap.put("channelId", str);
        arrayMap.put("count", i2 + "");
        if (i3 > -1) {
            arrayMap.put("rank", i3 + "");
        }
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str3, arrayMap);
        return PLVApiManager.getPlvLiveStatusApi().getProductList2(str, str2, j2, i2, i3, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVCommodityVO2>(PLVCommodityVO2.DataBean.class) { // from class: com.easefun.polyv.livescenes.net.api.PolyvLiveStatusApi.4
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVCommodityVO2 pLVCommodityVO2) {
                return new Pair<>(pLVCommodityVO2.getDataObj(), Boolean.valueOf(pLVCommodityVO2.isEncryption()));
            }
        });
    }
}
