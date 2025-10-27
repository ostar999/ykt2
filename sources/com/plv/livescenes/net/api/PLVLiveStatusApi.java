package com.plv.livescenes.net.api;

import cn.hutool.core.text.StrPool;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.mobile.auth.gatewayauth.Constant;
import com.plv.foundationsdk.net.PLVResponseApiBean;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.linkmic.model.PLVEncryptDataVO;
import com.plv.linkmic.model.PLVMicphoneStatusVO;
import com.plv.livescenes.document.model.PLVPPTInfo;
import com.plv.livescenes.feature.beauty.vo.PLVBeautySettingVO;
import com.plv.livescenes.feature.login.model.PLVLoginVO;
import com.plv.livescenes.feature.pointreward.vo.PLVDonateGoodResponseVO;
import com.plv.livescenes.hiclass.vo.PLVHCGroupInfoVO;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.model.PLVChannelSettingReqVO;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVEmotionImageFullVO;
import com.plv.livescenes.model.PLVIncreasePageViewerVO;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.plv.livescenes.model.PLVLiveCountdownVO;
import com.plv.livescenes.model.PLVLiveStatusVO;
import com.plv.livescenes.model.PLVLiveStatusVO2;
import com.plv.livescenes.model.PLVPlaybackChannelDetailVO;
import com.plv.livescenes.model.PLVPlaybackListVO;
import com.plv.livescenes.model.PLVPlaybackVO;
import com.plv.livescenes.model.PLVPlaybackVO2;
import com.plv.livescenes.model.PLVPlaybackVideoVO;
import com.plv.livescenes.model.PLVTempStorePlaybackVideoVO;
import com.plv.livescenes.model.PLVUploadTokenVO;
import com.plv.livescenes.model.commodity.saas.PLVCommodityVO;
import com.plv.livescenes.model.commodity.saas.PLVCommodityVO2;
import com.plv.livescenes.model.pointreward.PLVPointRewardVO;
import com.plv.livescenes.model.pointreward.PLVRewardSettingFullVO;
import com.plv.livescenes.playback.video.PLVPlaybackVideoView;
import com.plv.livescenes.upload.model.PLVPPTConvertStatusFullVO;
import com.plv.livescenes.upload.model.PLVPPTUploadTokenVO;
import com.psychiatrygarden.utils.Constants;
import io.reactivex.Observable;
import java.util.Map;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/* loaded from: classes5.dex */
public interface PLVLiveStatusApi {
    @GET("live/v4/user/beauty-setting/get")
    Observable<PLVBeautySettingVO> getBeautySetting(@Query("appId") String str, @Query("packageName") String str2, @Query("timestamp") long j2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query(PLVSignCreator.SIGNATURE_NONCE) String str5, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str6);

    @GET("live/inner/v3/channel/switch/get")
    Observable<PLVChatFunctionSwitchVO> getChatFunctionSwitch(@Query("timestamp") long j2, @Query("sign") String str, @Query("channelId") String str2, @Query("signatureMethod") String str3, @Query(PLVSignCreator.SIGNATURE_NONCE) String str4, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str5);

    @GET("live/v3/channel/chat/get-history-contents")
    Observable<ResponseBody> getChatHistory2(@Query("channelId") String str, @Query("start") int i2, @Query("end") int i3, @Query("fullMessage") int i4, @Query("hasCustom") int i5, @Query("appId") String str2, @Query("sign") String str3, @Query("timestamp") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("v2/danmu")
    Observable<ResponseBody> getDanmaku(@QueryMap Map<String, Object> map);

    @GET("live/v3/channel/document/status/get")
    Observable<PLVPPTConvertStatusFullVO> getDocumentConvertStatus(@Query("appId") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query("channelId") int i2, @Query("fileId") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/channel/document/status/get")
    Observable<PLVResponseApiBean> getDocumentConvertStatusWithChannelToken(@Query("appId") String str, @Query("timestamp") String str2, @Query("channelId") int i2, @Query("fileId") String str3, @Query("channelToken") String str4);

    @GET("live/v4/chat/get-emotion-images")
    Observable<PLVEmotionImageFullVO> getEmotionImages2(@Query("appId") String str, @Query("channelId") String str2, @Query("userId") String str3, @Query("timestamp") String str4, @Query("sign") String str5, @Query("pageNumber") int i2, @Query(ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE) int i3, @Query("signatureMethod") String str6, @Query(PLVSignCreator.SIGNATURE_NONCE) String str7, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);

    @GET("live/v3/channel/group/list")
    Observable<PLVHCGroupInfoVO> getGroupNameList(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("channelToken") String str3);

    @GET("live/v3/applet/sdk/get-channel-detail")
    Observable<PLVLiveClassDetailVO> getLiveClassDetail(@Query("channelId") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query("appId") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("/live/v2/channelSetting/{channelId}/get-countdown")
    Observable<PLVLiveCountdownVO> getLiveCountdown(@Path("channelId") String str, @Query("appId") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/live-status-sessionid/query")
    Observable<ResponseBody> getLiveStatusByStreamV3(@Query("stream") String str, @Query("channelId") String str2, @QueryMap @Nullable Map<String, String> map);

    @GET("live/v2/channels/{channelId}/live-status2")
    Observable<PLVLiveStatusVO> getLiveStatusJson2(@Path("channelId") String str);

    @GET("live/v3/channel/live-status2")
    Observable<PLVLiveStatusVO2> getLiveStatusJson3(@Query("channelId") String str, @Query("timestamp") String str2, @Query("appId") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/channel/document/simple-list")
    Observable<PLVPPTInfo> getPPTList(@Query("channelId") String str, @Query("sign") String str2, @Query("signatureMethod") String str3, @Query("timestamp") long j2, @Query("isShowUrl") String str4, @Query(Constants.PARAMS_CONSTANTS.PARAMS_PAGE) int i2, @Query("limit") int i3, @Query("appId") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/channel/document/simple-list")
    Observable<PLVPPTInfo> getPPTListWithChannelToken(@Query("channelId") String str, @Query("timestamp") long j2, @Query("isShowUrl") String str2, @Query("appId") String str3, @Query("channelToken") String str4);

    @GET("live/v3/channel/document/get-server-token")
    Call<PLVPPTUploadTokenVO> getPPTUploadToken(@Query("appId") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query("channelId") int i2, @Query("fileId") String str5, @Query("fileName") String str6, @Query("type") String str7, @Query(PLVSignCreator.SIGNATURE_NONCE) String str8, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str9);

    @GET("live/v3/channel/document/get-server-token")
    Call<PLVPPTUploadTokenVO> getPPTUploadTokenWithChannelToken(@Query("appId") String str, @Query("timestamp") String str2, @Query("channelId") int i2, @Query("fileId") String str3, @Query("fileName") String str4, @Query("type") String str5, @Query("channelToken") String str6);

    @GET("v2/watch/channel/detail")
    Observable<PLVPlaybackChannelDetailVO> getPlaybackChannelDetail(@Query("channelId") String str, @Query(StrPool.UNDERLINE) String str2);

    @GET("live/v3/channel/playback/sdk")
    Observable<PLVPlaybackVO2> getPlaybackData(@Query("appId") String str, @Query("channelId") String str2, @Query("timestamp") long j2, @Nullable @Query("vid") String str3, @Nullable @Query("playbackType") String str4, @Query("sign") String str5, @Query("signatureMethod") String str6, @Query(PLVSignCreator.SIGNATURE_NONCE) String str7, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);

    @GET("live/v2/channel/recordFile/{channelId}/playback/list")
    Observable<PLVPlaybackListVO> getPlaybackList(@Path("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query(Constants.PARAMS_CONSTANTS.PARAMS_PAGE) int i2, @Query(ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE) int i3, @Query(PLVPlaybackVideoView.LIST_TYPE) String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/channel/playback/get-video-by-vid")
    Observable<PLVPlaybackVideoVO> getPlaybackVO(@Query("appId") String str, @Query("channelId") String str2, @Query("vid") String str3, @Query("timestamp") long j2, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/channel/playback/get-video-by-vid")
    Observable<PLVPlaybackVO> getPlaybackVO(@Query("appId") String str, @Query("channelId") String str2, @Query("vid") String str3, @Query("timestamp") long j2, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVPlaybackVideoView.LIST_TYPE) String str6, @Query(PLVSignCreator.SIGNATURE_NONCE) String str7, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8, @Query("ignoreScene") String str9);

    @GET("/live/v4/watch/channel/donate/setting/get")
    Observable<PLVRewardSettingFullVO> getPointRewardSetting(@Query("appId") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("channelId") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/channel/product/getListByRank")
    Observable<PLVCommodityVO> getProductList(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, @Query("rank") int i3, @Query("sign") String str3, @Query("signatureMethod") String str4);

    @GET("live/v3/channel/product/getListByRank")
    Observable<PLVCommodityVO> getProductList(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, @Query("sign") String str3, @Query("signatureMethod") String str4);

    @GET("live/v3/channel/product/getListByRank")
    Observable<PLVCommodityVO2> getProductList2(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, @Query("rank") int i3, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query(PLVSignCreator.SIGNATURE_NONCE) String str5, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str6);

    @GET("live/v3/channel/product/getListByRank")
    Observable<PLVCommodityVO2> getProductList2(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") long j2, @Query("count") int i2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query(PLVSignCreator.SIGNATURE_NONCE) String str5, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str6);

    @GET("live/v3/channel/donate/point/get")
    Observable<PLVPointRewardVO> getRewardPoint(@Query("channelId") int i2, @Query("appId") String str, @Query("timestamp") long j2, @Query("sign") String str2, @Query("signatureMethod") String str3, @Query(PLVLinkMicManager.VIEWER_ID) String str4, @Query("nickname") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/channel/record/get")
    Observable<PLVTempStorePlaybackVideoVO> getTempStorePlaybackVO(@Query("appId") String str, @Query("channelId") String str2, @Query("fileId") String str3, @Query("timestamp") long j2, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @GET("live/v3/common/image/get-token")
    Observable<PLVUploadTokenVO> getUploadToken2(@Query("appId") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("signatureMethod") String str4, @Query(PLVSignCreator.SIGNATURE_NONCE) String str5, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str6);

    @FormUrlEncoded
    @POST("live/v4/watch/channel/donate/donate-good")
    Observable<PLVDonateGoodResponseVO> giftCashReward(@Query("appId") String str, @Query("timestamp") long j2, @Query("sign") String str2, @Field("channelId") int i2, @Field("authType") String str3, @Field("donateType") String str4, @Field(PLVLinkMicManager.VIEWER_ID) String str5, @Field("nickname") String str6, @Field("avatar") String str7, @Field("goodId") int i3, @Field("goodNum") int i4, @Field(PLVLinkMicManager.SESSION_ID) String str8, @Query("signatureMethod") String str9, @Query(PLVSignCreator.SIGNATURE_NONCE) String str10, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str11);

    @FormUrlEncoded
    @POST("/live/v3/channel/watch/increase-page-viewer")
    Observable<PLVResponseApiBean> increasePageViewer(@Field("channelId") int i2, @Field("appId") String str, @Field("timestamp") long j2, @Field("sign") String str2, @Field("signatureMethod") String str3, @Field("times") int i3);

    @FormUrlEncoded
    @POST("/live/v3/channel/watch/increase-page-viewer")
    Observable<PLVIncreasePageViewerVO> increasePageViewer2(@Field("channelId") int i2, @Field("appId") String str, @Field("timestamp") long j2, @Field("sign") String str2, @Field("signatureMethod") String str3, @Field("times") int i3, @Field(PLVSignCreator.SIGNATURE_NONCE) String str4, @Field(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str5);

    @FormUrlEncoded
    @POST("live/client/teacher_login.json")
    Observable<PLVLoginVO> login(@Field(Constant.LOGIN_ACTIVITY_NUMBER) String str, @Field("passwd") String str2, @Field("timestamp") String str3, @Field("sign") String str4, @Field("scene") String str5, @Field("version") String str6);

    @FormUrlEncoded
    @POST("live/inner/v3/sdk/teacher-login")
    Observable<PLVEncryptDataVO<PLVLoginVO>> loginV3(@Field("channelId") String str, @Field("passwd") String str2, @Field("timestamp") String str3, @Field("sign") String str4, @Field("scene") String str5, @Field("version") String str6, @Field("signatureMethod") String str7, @Field(PLVSignCreator.SIGNATURE_NONCE) String str8, @Field(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str9);

    @FormUrlEncoded
    @POST("live/v3/channel/donate/point/reward")
    Observable<PLVPointRewardVO> pointReward(@Field("channelId") int i2, @Field("appId") String str, @Field("timestamp") long j2, @Field("sign") String str2, @Field("signatureMethod") String str3, @Field(PLVLinkMicManager.VIEWER_ID) String str4, @Field("goodId") int i3, @Field("goodNum") int i4, @Field("nickname") String str5, @Field("avatar") String str6, @Field(PLVSignCreator.SIGNATURE_NONCE) String str7, @Field(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);

    @GET("live/v3/channel/chat/get-microphone-status")
    Observable<PLVMicphoneStatusVO> requestMicroPhoneStatus2(@Query("channelId") String str, @Query("appId") String str2, @Query("sign") String str3, @Query("timestamp") String str4, @Query("signatureMethod") String str5, @Query(PLVSignCreator.SIGNATURE_NONCE) String str6, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @FormUrlEncoded
    @POST("v2/danmu/add")
    Observable<ResponseBody> sendDanmaku(@FieldMap Map<String, Object> map);

    @GET("live/v2/channels/{channelId}/like")
    Observable<ResponseBody> sendLikes(@Path("channelId") String str, @Query("appId") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query(PLVLinkMicManager.VIEWER_ID) String str6, @Query("times") int i2, @Query(PLVSignCreator.SIGNATURE_NONCE) String str7, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);

    @FormUrlEncoded
    @POST("live/v3/agora/relation")
    Observable<PLVResponseApiBean> setLinkMicIdRelation(@Field("channelId") int i2, @Field(PLVLinkMicManager.VIEWER_ID) String str, @Field("uid") String str2, @Field("appId") String str3, @Field("timestamp") long j2, @Field("sign") String str4, @Field("signatureMethod") String str5, @Field(PLVSignCreator.SIGNATURE_NONCE) String str6, @Field(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str7);

    @FormUrlEncoded
    @POST("live/v1/channels/{channelId}/update")
    Observable<ResponseBody> updateChannelName(@Path("channelId") String str, @Field("ptime") long j2, @Field("name") String str2, @Field("sign") String str3);

    @POST("live/v3/channel/basic/update")
    Observable<ResponseBody> updateChannelSetting(@Query("channelId") String str, @Query("timestamp") long j2, @Query("appId") String str2, @Query("sign") String str3, @Body PLVChannelSettingReqVO pLVChannelSettingReqVO, @Query("signatureMethod") String str4, @Query(PLVSignCreator.SIGNATURE_NONCE) String str5, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str6);
}
