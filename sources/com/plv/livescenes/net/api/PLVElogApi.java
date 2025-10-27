package com.plv.livescenes.net.api;

import com.mobile.auth.BuildConfig;
import com.plv.foundationsdk.net.PLVBaseResponseBean;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/* loaded from: classes5.dex */
public interface PLVElogApi {
    @FormUrlEncoded
    @POST("v4/live/save-elog")
    Call<PLVBaseResponseBean> sendLiveElog(@Field("channelId") String str, @Field(BuildConfig.FLAVOR_type) String str2, @Field("ltype") int i2, @Field("timestamp") String str3, @Field("sign") String str4);
}
