package com.plv.livescenes.net.api;

import com.mobile.auth.BuildConfig;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* loaded from: classes5.dex */
public interface PLVDocumentApi {
    @DELETE("api/channel/{channelId}/file/{fileId}")
    Observable<ResponseBody> delPPT(@Path("channelId") String str, @Path("fileId") String str2, @Query("sign") String str3);

    @FormUrlEncoded
    @POST("live/v2/channels/{channelId}/elog")
    Observable<ResponseBody> sendElogMessage(@Path("channelId") String str, @Field("ptime") String str2, @Field("sign") String str3, @Field(BuildConfig.FLAVOR_type) String str4, @Field("appId") String str5, @Field("ltype") int i2);
}
