package com.plv.livescenes.hiclass;

import androidx.annotation.Nullable;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.livescenes.net.IPLVDataRequestListener;

/* loaded from: classes4.dex */
public interface IPLVHiClassManager {

    public interface OnHiClassListener {
        void onLessonEnd(long j2, boolean z2, @Nullable PLVHCStudentLessonListVO.DataVO dataVO);

        void onLessonLateTooLong(long j2);

        void onLessonPreparing(long j2, long j3);

        void onLessonStarted(boolean z2);

        void onLimitLinkNumber(int i2);

        void onRepeatLogin(String str);
    }

    void changeLessonStatus(IPLVDataRequestListener<String> iPLVDataRequestListener, int i2);

    void destroy();

    int getLessonStatus();

    int getLimitLinkNumber();

    boolean isAutoConnectEnabledWithTimeRange();

    void setOnHiClassListener(OnHiClassListener onHiClassListener);
}
