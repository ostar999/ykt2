package com.plv.linkmic.repository.api;

import com.mobile.auth.gatewayauth.Constant;
import com.plv.foundationsdk.net.PLVResponseApiBean;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.linkmic.model.PLVEncryptDataVO;
import com.plv.linkmic.model.PLVLinkMicJoinStatusVO;
import com.plv.linkmic.model.PLVQuerySessionIdDataBean;
import com.plv.linkmic.model.PLVRTCMixActionResultFullVO;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/* loaded from: classes4.dex */
public interface PLVApiLiveApi {
    @GET("v4/chat/get-interact-status")
    Observable<PLVLinkMicJoinStatusVO> getInteractStatus2(@Query("channelId") String str, @Query(PLVLinkMicManager.SESSION_ID) String str2, @Query("getStatus") boolean z2, @Query("appId") String str3, @Query("timestamp") String str4, @Query("sign") String str5, @Query("signatureMethod") String str6, @Query(PLVSignCreator.SIGNATURE_NONCE) String str7, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);

    @GET("channel-sessionid/client/query")
    Observable<ResponseBody> getLinkMicSession(@Query("channelId") String str, @Query("stream") String str2);

    @GET("v3/channel/session/client-query")
    Observable<PLVEncryptDataVO<PLVQuerySessionIdDataBean>> getLinkMicSessionV3(@Query("channelId") String str, @Query("stream") String str2, @Query("appId") String str3, @Query("timestamp") String str4, @Query("sign") String str5, @Query("signatureMethod") String str6, @Query(PLVSignCreator.SIGNATURE_NONCE) String str7, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);

    @GET("v2/channels/{roomId}/mic-auth")
    Observable<PLVEncryptDataVO<String>> getMicAuth(@Path(PLVLinkMicManager.ROOM_ID) String str, @Query("appId") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @QueryMap Map<String, String> map, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("v3/trtc/auth")
    Observable<PLVEncryptDataVO<String>> getTRTCMicAuth(@Query("channelId") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query("appId") String str5, @QueryMap Map<String, String> map, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("v3/ucloud/auth")
    Observable<PLVEncryptDataVO<String>> getUCloudMicAuth(@Query("channelId") String str, @Query("userId") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query("appId") String str6, @QueryMap Map<String, String> map, @Query(PLVSignCreator.SIGNATURE_NONCE) String str7, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);

    @GET("v3/zego/auth")
    Observable<PLVResponseApiBean> getZegoMicAuth(@Query("channelId") String str, @Query("userId") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query("appId") String str6, @QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("v3/channel/mcu/mix/action")
    Observable<PLVRTCMixActionResultFullVO> mixAction(@Field("appId") String str, @Field("timestamp") String str2, @Field("data") String str3, @Field("sign") String str4, @Field("signatureMethod") String str5, @Field(PLVSignCreator.SIGNATURE_NONCE) String str6, @Field(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("client/livestatus/end")
    Observable<ResponseBody> notifyLiveEnd(@Query(Constant.LOGIN_ACTIVITY_NUMBER) String str, @Query("stream") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("version") String str5);

    @GET("v3/channel/client/live-status/end")
    Observable<PLVEncryptDataVO<String>> notifyLiveEndV3(@Query("channelId") String str, @Query("stream") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("version") String str5, @Query("appId") String str6, @Query("signatureMethod") String str7, @Query(PLVSignCreator.SIGNATURE_NONCE) String str8, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str9);

    @FormUrlEncoded
    @POST("api/v2/notify_stream2.json")
    Observable<ResponseBody> notifyStream(@Field(Constant.LOGIN_ACTIVITY_NUMBER) String str, @Field("stream") String str2, @Field(PLVLinkMicManager.SESSION_ID) String str3, @Field("sign") String str4, @Field("timestamp") String str5, @Field("videowidth") int i2, @Field("videoheight") int i3, @Field("rtcEnabled") String str6, @Field("pushClient") String str7, @Field("goOn") String str8);

    @FormUrlEncoded
    @POST("v3/client/notify-stream2")
    Observable<PLVEncryptDataVO<String>> notifyStreamV3(@Field("channelId") String str, @Field("stream") String str2, @Field(PLVLinkMicManager.SESSION_ID) String str3, @Field("sign") String str4, @Field("timestamp") String str5, @Field("videoWidth") String str6, @Field("videoHeight") String str7, @Field("rtcEnabled") String str8, @Field("pushClient") String str9, @Field("goOn") String str10, @Field("appId") String str11, @Field("signatureMethod") String str12, @Field(PLVSignCreator.SIGNATURE_NONCE) String str13, @Field(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str14);
}
