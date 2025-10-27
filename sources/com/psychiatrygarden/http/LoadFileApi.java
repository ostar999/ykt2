package com.psychiatrygarden.http;

import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/* loaded from: classes4.dex */
public interface LoadFileApi {
    @Streaming
    @GET
    Call<ResponseBody> loadPdfFile(@Url String fileUrl);

    @Streaming
    @FormUrlEncoded
    @POST
    Call<ResponseBody> loadZipFile(@Url String fileUrl, @FieldMap HashMap<String, String> params, @Header("RANGE") String range);
}
