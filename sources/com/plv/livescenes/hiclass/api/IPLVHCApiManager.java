package com.plv.livescenes.hiclass.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.plv.livescenes.hiclass.vo.PLVHCChangeLessonStatusVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonDetailVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonFinishVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonStatusVO;
import com.plv.livescenes.hiclass.vo.PLVHCLiveApiChannelTokenVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLessonListVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginVerifyVO;
import io.reactivex.Observable;
import java.io.IOException;

/* loaded from: classes4.dex */
public interface IPLVHCApiManager {
    Observable<PLVHCChangeLessonStatusVO> changeLessonStatus(int i2);

    Observable<String> getGroupName(String str);

    Observable<PLVHCLessonDetailVO> getLessonDetail();

    Observable<PLVHCLessonDetailVO> getLessonDetail(boolean z2, @Nullable String str, long j2, @NonNull String str2);

    Observable<PLVHCLessonFinishVO> getLessonFinishInfo();

    Observable<PLVHCLessonSimpleInfoResultVO> getLessonSimpleInfo(@Nullable String str, @Nullable Long l2);

    Observable<PLVHCLessonStatusVO> getLessonStatus(@NonNull String str, long j2);

    Observable<PLVHCLiveApiChannelTokenVO> getLiveApiChannelToken(@NonNull String str, long j2);

    @Nullable
    @WorkerThread
    PLVHCLiveApiChannelTokenVO getLiveApiChannelTokenSync(@NonNull String str, long j2) throws IOException;

    Observable<PLVHCStudentLessonListVO.DataVO> getWatchNextLesson();

    Observable<PLVHCStudentLessonListVO> listStudentLesson(@NonNull String str, @NonNull String str2);

    Observable<PLVHCTeacherLessonListVO> listTeacherLesson(@NonNull String str);

    Observable<PLVHCTeacherLoginVerifyVO> verifyTeacherLogin();
}
