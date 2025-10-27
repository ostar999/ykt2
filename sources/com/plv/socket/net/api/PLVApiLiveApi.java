package com.plv.socket.net.api;

import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.socket.net.model.PLVNewChatTokenVO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes5.dex */
public interface PLVApiLiveApi {
    @GET("v3/channel/common/get-chat-token")
    Observable<PLVNewChatTokenVO> getChatToken(@Query("channelId") String str, @Query("appId") String str2, @Query("timestamp") String str3, @Query("sign") String str4, @Query("signatureMethod") String str5, @Query("role") String str6, @Query("userId") String str7, @Query("origin") String str8, @Query(PLVSignCreator.SIGNATURE_NONCE) String str9, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str10);
}
