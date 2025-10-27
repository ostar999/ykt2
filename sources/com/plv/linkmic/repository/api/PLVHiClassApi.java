package com.plv.linkmic.repository.api;

import androidx.annotation.NonNull;
import com.plv.linkmic.model.PLVHiClassRTCTokenVO;
import io.reactivex.Observable;
import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/* loaded from: classes4.dex */
public interface PLVHiClassApi {
    @GET("teach/chat/v1/get-app-mic-token")
    Observable<PLVHiClassRTCTokenVO> getTeachMicToken(@Query("lessonId") long j2, @Query("channelId") String str, @Query("userId") String str2, @Query("groupId") String str3, @Query("timestamp") String str4, @NonNull @Header("Authorization") String str5, @QueryMap Map<String, String> map);

    @GET("watch/chat/v1/get-app-mic-token")
    Observable<PLVHiClassRTCTokenVO> getWatchMicToken(@Query("courseCode") String str, @Query("lessonId") long j2, @Query("channelId") String str2, @Query("userId") String str3, @Query("groupId") String str4, @Query("timestamp") String str5, @NonNull @Header("Authorization") String str6, @QueryMap Map<String, String> map);
}
