package com.plv.business.net.api;

import com.mobile.auth.BuildConfig;
import com.plv.business.model.video.PLVLiveChannelSessionVO;
import com.plv.business.model.video.PLVLiveRTCStatusVO;
import com.plv.business.model.video.PLVPlayBackFullVO;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.plv.foundationsdk.net.PLVBaseResponseBean;
import com.plv.foundationsdk.sign.PLVSignCreator;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/* loaded from: classes4.dex */
public interface PLVApiApi {
    @GET("/live/v3/channel/common/get-channel-session")
    Observable<PLVLiveChannelSessionVO> getChannelSession(@Query("sign") String str, @Query("signatureMethod") String str2, @Query("appId") String str3, @Query("channelId") String str4, @Query("timestamp") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("v2/danmu")
    Call<List<PLVDanmakuInfo>> getDanmaku(@QueryMap Map<String, Object> map);

    @GET("/live/v3/channel/get-rtc-enabled")
    Observable<PLVLiveRTCStatusVO> getLiveRTCStatus(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("/live/inner/v3/sdk/playback/get-type")
    Observable<PLVPlayBackFullVO> getPlayBackStatus(@Query("vid") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query(PLVSignCreator.SIGNATURE_NONCE) String str5, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str6);

    @FormUrlEncoded
    @POST("v2/danmu/add")
    Call<ResponseBody> sendDanmaku(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("/v2/elog/{userid}/store")
    Call<PLVBaseResponseBean> sendElogMessage(@Path("userid") String str, @Field("ptime") String str2, @Field("sign") String str3, @Field(BuildConfig.FLAVOR_type) String str4, @Field("ltype") int i2);
}
