package com.plv.beauty.api;

import android.content.Context;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.beauty.api.anno.OpenGLThread;
import com.plv.beauty.api.options.PLVBeautyOption;
import com.plv.beauty.api.options.PLVFilterOption;
import com.plv.beauty.api.vo.PLVBeautyInitParam;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPLVBeautyManager {

    public interface InitCallback {
        void onFinishInit(Integer num);

        void onStartInit();
    }

    public interface SetupCallback {
        void onSetup(boolean z2);
    }

    void addInitCallback(@NonNull WeakReference<InitCallback> weakReference);

    void clearBeautyOption();

    @MainThread
    void destroy();

    List<? extends PLVFilterOption> getSupportFilterOption();

    @MainThread
    void init(@NonNull Context context);

    boolean isBeautyOptionSupport(@NonNull PLVBeautyOption pLVBeautyOption);

    boolean isBeautySupport();

    @OpenGLThread
    int processTexture2dTo2d(int i2, int i3, int i4, int i5, long j2);

    @OpenGLThread
    void processTexture2dTo2d(int i2, int i3, int i4, int i5, int i6, long j2);

    @OpenGLThread
    int processTextureOesTo2d(int i2, int i3, int i4, int i5, long j2);

    @OpenGLThread
    void processTextureOesTo2d(int i2, int i3, int i4, int i5, int i6, long j2);

    @OpenGLThread
    void processTextureOesToRgba(int i2, ByteBuffer byteBuffer, int i3, int i4, int i5, long j2);

    @OpenGLThread
    void release();

    void removeBeautyOption(@NonNull PLVBeautyOption pLVBeautyOption);

    void setCameraFacing(boolean z2);

    void setFilterOption(@Nullable PLVFilterOption pLVFilterOption);

    void setInitParam(PLVBeautyInitParam pLVBeautyInitParam);

    @OpenGLThread
    void setup(@Nullable SetupCallback setupCallback);

    void updateBeautyOption(@NonNull PLVBeautyOption pLVBeautyOption);
}
