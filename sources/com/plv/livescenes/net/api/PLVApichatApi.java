package com.plv.livescenes.net.api;

import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.plv.foundationsdk.net.PLVResponseApiBean;
import com.plv.foundationsdk.net.PLVResponseApiBean2;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVMicphoneStatus;
import com.plv.livescenes.hiclass.vo.PLVHCGroupListUsersVO;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.model.PLVEmotionImageVO;
import com.plv.livescenes.model.PLVKickUsersVO;
import com.plv.livescenes.model.PLVListUsersVO;
import com.plv.livescenes.previous.model.PLVChapterDataVO;
import com.psychiatrygarden.utils.Constants;
import com.tencent.open.SocialConstants;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes5.dex */
public interface PLVApichatApi {
    @FormUrlEncoded
    @POST("front/closeRoom")
    Observable<ResponseBody> closeRoom(@Field("roomIds") String str, @Field("childRoomIds") String str2, @Field("close") String str3, @Field("ts") long j2, @Field("sign") String str4);

    @GET("ppt-records/playback/{channeId}/{videoId}.json")
    Observable<List<PLVChapterDataVO>> getChaptersList(@Path("channeId") String str, @Path("videoId") String str2);

    @GET("front/history")
    @Deprecated
    Observable<ResponseBody> getChatHistory(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("start") int i2, @Query("end") int i3, @Query("fullMessage") int i4);

    @GET("front/history")
    Observable<ResponseBody> getChatHistory(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("start") int i2, @Query("end") int i3, @Query("fullMessage") int i4, @Query("hasCustom") int i5);

    @GET("front/getChatHistory")
    Observable<ResponseBody> getChatHistory(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("start") int i2, @Query("end") int i3, @Query("fullMessage") int i4, @Query(SocialConstants.PARAM_SOURCE) String str2, @Query("hide") int i5, @Query("timestamp") long j2, @Query("sign") String str3);

    @GET("/front/getEmotionImages")
    Observable<PLVResponseApiBean2<PLVEmotionImageVO>> getEmotionImages(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("accountId") String str2, @Query(Constants.PARAMS_CONSTANTS.PARAMS_PAGE) int i2, @Query(DatabaseManager.SIZE) int i3);

    @GET("/front/getGroupList")
    Observable<PLVHCGroupListUsersVO> getGroupUserList(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("timestamp") long j2, @Query("sign") String str2, @Query("groupIds") String str3, @Query("origin") String str4);

    @GET("front/getInteractStatus")
    Observable<PLVLinkMicJoinStatus> getInteractStatus(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query(PLVLinkMicManager.SESSION_ID) String str2, @Query("getStatus") boolean z2);

    @GET("admin/listKicked")
    Observable<PLVKickUsersVO> getKickUsers(@Query(PLVLinkMicManager.ROOM_ID) String str);

    @GET("front/listUsers")
    Observable<PLVListUsersVO> getListUsers(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query(Constants.PARAMS_CONSTANTS.PARAMS_PAGE) int i2, @Query("len") int i3, @Query("getStatus") int i4, @Query("toGetSubRooms") boolean z2, @Query(PLVLinkMicManager.SESSION_ID) String str2);

    @GET("/front/channel/playback-history-list/{roomId}/{sessionId}/{id}")
    Observable<ResponseBody> getPlaybackHistoryList(@Path(PLVLinkMicManager.ROOM_ID) String str, @Path(PLVLinkMicManager.SESSION_ID) String str2, @Path("id") int i2);

    @GET("/front/channel/playback-history-part/{roomId}/{sessionId}/{page}")
    Observable<ResponseBody> getPlaybackHistoryPart(@Path(PLVLinkMicManager.ROOM_ID) String str, @Path(PLVLinkMicManager.SESSION_ID) String str2, @Path(Constants.PARAMS_CONSTANTS.PARAMS_PAGE) int i2);

    @FormUrlEncoded
    @POST("front/give-up-receive")
    Observable<PLVResponseApiBean> postLotteryAbandon(@Field("channelId") String str, @Field("userId") String str2);

    @FormUrlEncoded
    @POST("/front/v2/add-receive-info")
    Observable<PLVResponseApiBean> postLotteryWinnerInfoNew(@Field("channelId") String str, @Field("lotteryId") String str2, @Field("winnerCode") String str3, @Field(PLVLinkMicManager.VIEWER_ID) String str4, @Field("receiveInfo") String str5, @Field(PLVLinkMicManager.SESSION_ID) String str6);

    @GET("front/heartbeat")
    Observable<ResponseBody> requestHeartbeat(@Query("uid") String str);

    @GET("front/getMicrophoneStatus")
    Observable<PLVMicphoneStatus> requestMicroPhoneStatus(@Query(PLVLinkMicManager.ROOM_ID) String str);
}
