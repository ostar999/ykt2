package com.plv.livescenes.feature.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livescenes.feature.login.PolyvLiveLoginResult;
import com.easefun.polyv.livescenes.feature.login.PolyvPlaybackLoginResult;
import com.easefun.polyv.livescenes.feature.login.model.PLVSLoginVO;
import com.plv.livescenes.feature.login.PLVSceneLoginManager;
import com.plv.livescenes.feature.login.model.PLVHCTeacherLoginVO;
import com.plv.livescenes.feature.login.model.PLVLoginVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentVerifyResultVO;

/* loaded from: classes4.dex */
public interface IPLVSceneLoginManager {
    public static final String ERROR_CHANNEL_NOT_EXIST = "channel not exist";
    public static final String ERROR_PASSWORD_IS_WRONG = "password is wrong";

    public interface OnLoginListener<T> {
        void onLoginFailed(String str, Throwable th);

        void onLoginSuccess(T t2);
    }

    public static abstract class OnStringCodeLoginListener<T> implements OnLoginListener<T> {
        public abstract void onLoginFailed(String str, @Nullable String str2, Throwable th);
    }

    void destroy();

    void loginHiClassStudent(@NonNull PLVSceneLoginManager.PLVHCStudentLoginVerifyType pLVHCStudentLoginVerifyType, @Nullable String str, @Nullable Long l2, @Nullable String str2, @Nullable String str3, @Nullable String str4, OnLoginListener<PLVHCStudentVerifyResultVO> onLoginListener);

    void loginHiClassTeacher(@Nullable String str, @NonNull String str2, @NonNull String str3, @Nullable Long l2, @Nullable String str4, OnLoginListener<PLVHCTeacherLoginVO> onLoginListener);

    @Deprecated
    void loginLive(String str, String str2, String str3, String str4, OnLoginListener<PolyvLiveLoginResult> onLoginListener);

    void loginLiveNew(String str, String str2, String str3, String str4, OnLoginListener<PLVLiveLoginResult> onLoginListener);

    @Deprecated
    void loginPlayback(String str, String str2, String str3, String str4, String str5, OnLoginListener<PolyvPlaybackLoginResult> onLoginListener);

    void loginPlaybackNew(String str, String str2, String str3, String str4, String str5, OnLoginListener<PLVPlaybackLoginResult> onLoginListener);

    @Deprecated
    void loginStreamer(String str, String str2, OnLoginListener<PLVSLoginVO> onLoginListener);

    void loginStreamerNew(String str, String str2, OnLoginListener<PLVLoginVO> onLoginListener);
}
