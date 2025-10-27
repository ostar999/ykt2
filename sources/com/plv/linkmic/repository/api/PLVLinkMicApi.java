package com.plv.linkmic.repository.api;

import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* loaded from: classes4.dex */
public interface PLVLinkMicApi {
    @GET("front/getInteractStatus")
    Observable<PLVLinkMicJoinStatus> getInteractStatus(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query(PLVLinkMicManager.SESSION_ID) String str2, @Query("getStatus") boolean z2);
}
