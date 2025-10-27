package com.plv.foundationsdk.net.api;

import com.heytap.mcssdk.constant.b;
import com.plv.foundationsdk.model.domain.PLVChatDomainVO;
import com.plv.foundationsdk.sign.PLVSignCreator;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes4.dex */
public interface PLVFoundationApi {
    @GET("/live/inner/v3/sdk/verify")
    Observable<PLVChatDomainVO> requestLoginStatus(@Query("channelId") Integer num, @Query("appId") String str, @Query("timestamp") String str2, @Query("sign") String str3, @Query("vid") String str4, @Query("userId") String str5, @Query(b.A) String str6, @Query("signatureMethod") String str7, @Query(PLVSignCreator.SIGNATURE_NONCE) String str8, @Query(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str9);
}
