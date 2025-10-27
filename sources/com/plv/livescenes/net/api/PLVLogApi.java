package com.plv.livescenes.net.api;

import com.mobile.auth.BuildConfig;
import com.plv.foundationsdk.net.PLVBaseResponseBean;
import com.plv.foundationsdk.sign.PLVSignCreator;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/* loaded from: classes5.dex */
public interface PLVLogApi {
    @FormUrlEncoded
    @POST("live/inner/v3/client/sdk/save-elog")
    Call<PLVBaseResponseBean> sendClientElogMessage(@Field("channelId") String str, @Field("timestamp") String str2, @Field("sign") String str3, @Field(BuildConfig.FLAVOR_type) String str4, @Field("ltype") int i2);

    @FormUrlEncoded
    @POST("live/v3/channel/save-elog")
    Call<PLVBaseResponseBean> sendElogMessage2(@Field("channelId") String str, @Field("timestamp") String str2, @Field("sign") String str3, @Field(BuildConfig.FLAVOR_type) String str4, @Field("appId") String str5, @Field("ltype") int i2, @Field("signatureMethod") String str6, @Field(PLVSignCreator.SIGNATURE_NONCE) String str7, @Field(PLVSignCreator.ENCRYPT_RESPONSE_TYPE) String str8);
}
