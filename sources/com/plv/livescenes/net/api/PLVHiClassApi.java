package com.plv.livescenes.net.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.livescenes.hiclass.vo.PLVHCChangeLessonRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCChangeLessonStatusVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonDetailVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonFinishVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonStatusVO;
import com.plv.livescenes.hiclass.vo.PLVHCLiveApiChannelTokenRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCLiveApiChannelTokenVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentVerifyRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentVerifyResultVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLessonListVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginRequestVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginResultVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginVerifyVO;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* loaded from: classes5.dex */
public interface PLVHiClassApi {
    @POST("teach/lesson/v1/change-status")
    Observable<PLVHCChangeLessonStatusVO> changeLessonStatus(@Body PLVHCChangeLessonRequestVO pLVHCChangeLessonRequestVO, @NonNull @Header("Authorization") String str);

    @GET("common/v1/get-lesson-finish")
    Observable<PLVHCLessonFinishVO> getLessonFinishInfo(@Query("lessonId") long j2, @NonNull @Header("Authorization") String str);

    @GET("common/v1/get-simple-info")
    Observable<PLVHCLessonSimpleInfoResultVO> getLessonSimpleInfo(@Nullable @Query("courseCode") String str, @Nullable @Query("lessonId") Long l2, @Query("timestamp") long j2, @NonNull @Query("sign") String str2);

    @GET("teach/lesson/v1/status")
    Observable<PLVHCLessonStatusVO> getLessonStatus(@NonNull @Header("Authorization") String str, @Query("lessonId") long j2);

    @POST("teach/common/v1/get-live-api-channel-token")
    Observable<PLVHCLiveApiChannelTokenVO> getLiveApiChannelToken(@NonNull @Header("Authorization") String str, @Body PLVHCLiveApiChannelTokenRequestVO pLVHCLiveApiChannelTokenRequestVO);

    @POST("teach/common/v1/get-live-api-channel-token")
    Call<PLVHCLiveApiChannelTokenVO> getLiveApiChannelTokenSync(@NonNull @Header("Authorization") String str, @Body PLVHCLiveApiChannelTokenRequestVO pLVHCLiveApiChannelTokenRequestVO);

    @POST("watch/common/v1/get-live-api-channel-token")
    Observable<PLVHCLiveApiChannelTokenVO> getStudentLiveApiChannelToken(@NonNull @Header("Authorization") String str, @Query("lessonId") long j2, @Body PLVHCLiveApiChannelTokenRequestVO pLVHCLiveApiChannelTokenRequestVO);

    @POST("watch/common/v1/get-live-api-channel-token")
    Call<PLVHCLiveApiChannelTokenVO> getStudentLiveApiChannelTokenSync(@NonNull @Header("Authorization") String str, @Query("lessonId") long j2, @Body PLVHCLiveApiChannelTokenRequestVO pLVHCLiveApiChannelTokenRequestVO);

    @GET("teach/lesson/v1/detail")
    Observable<PLVHCLessonDetailVO> getTeachLessonDetail(@Query("lessonId") long j2, @NonNull @Header("Authorization") String str);

    @GET("watch/lesson/v1/detail")
    Observable<PLVHCLessonDetailVO> getWatchLessonDetail(@Query("courseCode") String str, @Query("lessonId") long j2, @NonNull @Header("Authorization") String str2);

    @GET("watch/lesson/v1/list")
    Observable<PLVHCStudentLessonListVO> listStudentLesson(@NonNull @Header("Authorization") String str, @NonNull @Query("courseCode") String str2);

    @GET("teach/lesson/v1/list")
    Observable<PLVHCTeacherLessonListVO> listTeacherLesson(@NonNull @Header("Authorization") String str);

    @POST("watch/auth/v1/verify/code")
    Observable<PLVHCStudentVerifyResultVO> loginStudentWithCodeVerify(@Body PLVHCStudentVerifyRequestVO pLVHCStudentVerifyRequestVO);

    @POST("watch/auth/v1/verify/none")
    Observable<PLVHCStudentVerifyResultVO> loginStudentWithNoVerify(@Body PLVHCStudentVerifyRequestVO pLVHCStudentVerifyRequestVO);

    @POST("watch/auth/v1/verify/white")
    Observable<PLVHCStudentVerifyResultVO> loginStudentWithWhiteListVerify(@Body PLVHCStudentVerifyRequestVO pLVHCStudentVerifyRequestVO);

    @POST("teach/v1/login")
    Observable<PLVHCTeacherLoginResultVO> loginTeacher(@Body PLVHCTeacherLoginRequestVO pLVHCTeacherLoginRequestVO);

    @GET("teach/lesson/v1/verify")
    Observable<PLVHCTeacherLoginVerifyVO> verifyTeacherLogin(@NonNull @Header("Authorization") String str, @Query("lessonId") long j2);
}
