package com.easefun.polyv.livecommon.module.modules.beauty.helper;

import android.app.Activity;
import android.content.Context;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.hjq.permissions.Permission;
import com.plv.beauty.api.IPLVBeautyManager;
import com.plv.beauty.api.PLVBeautyManager;
import com.plv.beauty.api.resource.RemoteResource;
import com.plv.beauty.api.vo.PLVBeautyInitParam;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.feature.beauty.PLVBeautyApiManager;
import com.plv.livescenes.feature.beauty.vo.PLVBeautySettingVO;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PLVBeautyInitHelper {
    private static final String TAG = "PLVBeautyInitHelper";
    private static volatile PLVBeautyInitHelper instance;
    private IPLVBeautyManager.InitCallback beautyInitCallback;
    private final LifecycleObserver clearCallbackOnDestroy = new GenericLifecycleObserver() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.helper.PLVBeautyInitHelper.1
        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                PLVBeautyInitHelper.this.beautyInitCallback = null;
                if (PLVBeautyInitHelper.this.getBeautySettingDisposable != null) {
                    PLVBeautyInitHelper.this.getBeautySettingDisposable.dispose();
                    PLVBeautyInitHelper.this.getBeautySettingDisposable = null;
                }
                source.getLifecycle().removeObserver(this);
            }
        }
    };
    private Disposable getBeautySettingDisposable;

    private PLVBeautyInitHelper() {
    }

    public static PLVBeautyInitHelper getInstance() {
        if (instance == null) {
            synchronized (PLVBeautyInitHelper.class) {
                if (instance == null) {
                    instance = new PLVBeautyInitHelper();
                }
            }
        }
        return instance;
    }

    private void requirePermissionThenRun(final Context context, final Runnable onGrantedPermission) {
        if (PLVFastPermission.hasPermission(context, Permission.WRITE_EXTERNAL_STORAGE)) {
            onGrantedPermission.run();
        } else {
            PLVFastPermission.getInstance().start((Activity) context, PLVSugarUtil.listOf(Permission.WRITE_EXTERNAL_STORAGE), new PLVOnPermissionCallback() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.helper.PLVBeautyInitHelper.3
                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onAllGranted() {
                    onGrantedPermission.run();
                }

                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupInitParam(final PLVBeautySettingVO beautySettingVO) {
        RemoteResource remoteResource = new RemoteResource();
        remoteResource.setName("BeautyEffectResource");
        remoteResource.setUrl(beautySettingVO.getData().getMaterialUrl());
        remoteResource.setHash(beautySettingVO.getData().getMaterialMd5());
        PLVBeautyManager.getInstance().setInitParam(new PLVBeautyInitParam().setRemoteResourceList(PLVSugarUtil.listOf(remoteResource)).setOnlineLicense(true).setLicenseKey(beautySettingVO.getData().getKey()).setLicenseSecret(beautySettingVO.getData().getSecret()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void startBeautyInit(final Context context, final PLVSugarUtil.Consumer<Boolean> onInitFinishCallback) {
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this.clearCallbackOnDestroy);
        }
        Disposable disposable = this.getBeautySettingDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.getBeautySettingDisposable = PLVBeautyApiManager.getInstance().getBeautySetting().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVBeautySettingVO>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.helper.PLVBeautyInitHelper.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVBeautySettingVO beautySettingVO) throws Exception {
                PLVBeautyInitHelper.this.setupInitParam(beautySettingVO);
                PLVBeautyInitHelper.this.startInnerInit(context, onInitFinishCallback);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.helper.PLVBeautyInitHelper.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVSugarUtil.Consumer consumer = onInitFinishCallback;
                if (consumer != null) {
                    consumer.accept(Boolean.FALSE);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startInnerInit(final Context context, final PLVSugarUtil.Consumer<Boolean> callback) {
        this.beautyInitCallback = new IPLVBeautyManager.InitCallback() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.helper.PLVBeautyInitHelper.6
            @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
            public void onFinishInit(Integer code) {
                PLVCommonLog.i(PLVBeautyInitHelper.TAG, "onBeautyFinishInit, code: " + code);
                boolean z2 = code != null && code.intValue() == 0;
                PLVSugarUtil.Consumer consumer = callback;
                if (consumer != null) {
                    consumer.accept(Boolean.valueOf(z2));
                }
                PLVBeautyInitHelper.this.beautyInitCallback = null;
            }

            @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
            public void onStartInit() {
            }
        };
        PLVBeautyManager.getInstance().addInitCallback(new WeakReference<>(this.beautyInitCallback));
        PLVCommonLog.d(TAG, "startBeautyInitInner");
        PLVBeautyManager.getInstance().init(context);
    }

    public void destroy() {
        Disposable disposable = this.getBeautySettingDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.getBeautySettingDisposable = null;
        }
        PLVBeautyManager.getInstance().destroy();
    }

    public void init(final Context context, final PLVSugarUtil.Consumer<Boolean> onInitFinishCallback) {
        if (PLVBeautyManager.getInstance().isBeautySupport()) {
            requirePermissionThenRun(context, new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.beauty.helper.PLVBeautyInitHelper.2
                @Override // java.lang.Runnable
                public void run() {
                    PLVBeautyInitHelper.this.startBeautyInit(context, onInitFinishCallback);
                }
            });
        } else if (onInitFinishCallback != null) {
            onInitFinishCallback.accept(Boolean.FALSE);
        }
    }
}
