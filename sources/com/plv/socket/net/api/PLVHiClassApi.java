package com.plv.socket.net.api;

import androidx.annotation.NonNull;
import com.plv.socket.net.model.PLVHiClassChatTokenVO;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/* loaded from: classes5.dex */
public interface PLVHiClassApi {
    @GET("teach/chat/v1/get-chat-token")
    Observable<PLVHiClassChatTokenVO> getTeachChatToken(@Query("lessonId") long j2, @NonNull @Header("Authorization") String str);

    @GET("watch/chat/v1/get-chat-token")
    Observable<PLVHiClassChatTokenVO> getWatchChatToken(@Query("courseCode") String str, @Query("lessonId") long j2, @NonNull @Header("Authorization") String str2);
}
