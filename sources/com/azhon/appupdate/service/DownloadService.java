package com.azhon.appupdate.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.azhon.appupdate.base.BaseHttpDownloadManager;
import com.azhon.appupdate.base.bean.DownloadStatus;
import com.azhon.appupdate.config.Constant;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.manager.HttpDownloadManager;
import com.azhon.appupdate.util.ApkUtil;
import com.azhon.appupdate.util.FileUtil;
import com.azhon.appupdate.util.LogUtil;
import com.azhon.appupdate.util.NotificationUtil;
import com.catchpig.mvvm.R;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.plv.socket.user.PLVSocketUserConstant;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000  2\u00020\u00012\u00020\u0002:\u0001 B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\tH\u0002J\u0018\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0016J\u0010\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\tH\u0002J\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\"\u0010\u001b\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u0005H\u0016J\b\u0010\u001e\u001a\u00020\tH\u0002J\b\u0010\u001f\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/azhon/appupdate/service/DownloadService;", "Landroid/app/Service;", "Lcom/azhon/appupdate/listener/OnDownloadListener;", "()V", "lastProgress", "", PLVSocketUserConstant.USERTYPE_MANAGER, "Lcom/azhon/appupdate/manager/DownloadManager;", "cancel", "", "checkApkMd5", "", "done", "apk", "Ljava/io/File;", AliyunLogCommon.SubModule.download, DatabaseManager.DOWNLOADING, "max", "progress", "error", AliyunLogKey.KEY_EVENT, "", "init", "onBind", "Landroid/os/IBinder;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "onStartCommand", "flags", "startId", "releaseResources", "start", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDownloadService.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DownloadService.kt\ncom/azhon/appupdate/service/DownloadService\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,191:1\n1855#2,2:192\n1855#2,2:194\n1855#2,2:196\n1855#2,2:198\n1855#2,2:200\n*S KotlinDebug\n*F\n+ 1 DownloadService.kt\ncom/azhon/appupdate/service/DownloadService\n*L\n124#1:192,2\n140#1:194,2\n159#1:196,2\n171#1:198,2\n184#1:200,2\n*E\n"})
/* loaded from: classes2.dex */
public final class DownloadService extends Service implements OnDownloadListener {

    @NotNull
    private static final String TAG = "DownloadService";
    private int lastProgress;
    private DownloadManager manager;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.azhon.appupdate.service.DownloadService$download$1", f = "DownloadService.kt", i = {}, l = {98}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.azhon.appupdate.service.DownloadService$download$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return DownloadService.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DownloadManager downloadManager = DownloadService.this.manager;
                DownloadManager downloadManager2 = null;
                if (downloadManager == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                    downloadManager = null;
                }
                BaseHttpDownloadManager httpManager = downloadManager.getHttpManager();
                Intrinsics.checkNotNull(httpManager);
                DownloadManager downloadManager3 = DownloadService.this.manager;
                if (downloadManager3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                    downloadManager3 = null;
                }
                String apkUrl = downloadManager3.getApkUrl();
                DownloadManager downloadManager4 = DownloadService.this.manager;
                if (downloadManager4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                } else {
                    downloadManager2 = downloadManager4;
                }
                Flow<DownloadStatus> flowDownload = httpManager.download(apkUrl, downloadManager2.getApkName());
                final DownloadService downloadService = DownloadService.this;
                FlowCollector<? super DownloadStatus> flowCollector = new FlowCollector() { // from class: com.azhon.appupdate.service.DownloadService.download.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((DownloadStatus) obj2, (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(@NotNull DownloadStatus downloadStatus, @NotNull Continuation<? super Unit> continuation) throws Resources.NotFoundException {
                        if (downloadStatus instanceof DownloadStatus.Start) {
                            downloadService.start();
                        } else if (downloadStatus instanceof DownloadStatus.Downloading) {
                            DownloadStatus.Downloading downloading = (DownloadStatus.Downloading) downloadStatus;
                            downloadService.downloading(downloading.getMax(), downloading.getProgress());
                        } else if (downloadStatus instanceof DownloadStatus.Done) {
                            downloadService.done(((DownloadStatus.Done) downloadStatus).getApk());
                        } else if (downloadStatus instanceof DownloadStatus.Cancel) {
                            downloadService.cancel();
                        } else if (downloadStatus instanceof DownloadStatus.Error) {
                            downloadService.error(((DownloadStatus.Error) downloadStatus).getE());
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowDownload.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    private final boolean checkApkMd5() throws NoSuchAlgorithmException, IOException {
        DownloadManager downloadManager = this.manager;
        DownloadManager downloadManager2 = null;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        if (StringsKt__StringsJVMKt.isBlank(downloadManager.getApkMD5())) {
            return false;
        }
        DownloadManager downloadManager3 = this.manager;
        if (downloadManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager3 = null;
        }
        String downloadPath = downloadManager3.getDownloadPath();
        DownloadManager downloadManager4 = this.manager;
        if (downloadManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager4 = null;
        }
        File file = new File(downloadPath, downloadManager4.getApkName());
        if (!file.exists()) {
            return false;
        }
        String strMd5 = FileUtil.INSTANCE.md5(file);
        DownloadManager downloadManager5 = this.manager;
        if (downloadManager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager2 = downloadManager5;
        }
        return StringsKt__StringsJVMKt.equals(strMd5, downloadManager2.getApkMD5(), true);
    }

    private final synchronized void download() {
        DownloadManager downloadManager = this.manager;
        DownloadManager downloadManager2 = null;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        if (downloadManager.getDownloadState()) {
            LogUtil.INSTANCE.e(TAG, "Currently downloading, please download again!");
            return;
        }
        DownloadManager downloadManager3 = this.manager;
        if (downloadManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager3 = null;
        }
        if (downloadManager3.getHttpManager() == null) {
            DownloadManager downloadManager4 = this.manager;
            if (downloadManager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                downloadManager4 = null;
            }
            DownloadManager downloadManager5 = this.manager;
            if (downloadManager5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                downloadManager5 = null;
            }
            downloadManager4.setHttpManager$mvvm_release(new HttpDownloadManager(downloadManager5.getDownloadPath()));
        }
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain().plus(new CoroutineName(Constant.COROUTINE_NAME)), null, new AnonymousClass1(null), 2, null);
        DownloadManager downloadManager6 = this.manager;
        if (downloadManager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager2 = downloadManager6;
        }
        downloadManager2.setDownloadState(true);
    }

    private final void init() throws Resources.NotFoundException {
        DownloadManager downloadManager = null;
        DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
        if (instance$mvvm_release$default == null) {
            LogUtil.INSTANCE.e(TAG, "An exception occurred by DownloadManager=null,please check your code!");
            return;
        }
        this.manager = instance$mvvm_release$default;
        FileUtil.INSTANCE.createDirDirectory(instance$mvvm_release$default.getDownloadPath());
        boolean zNotificationEnable = NotificationUtil.INSTANCE.notificationEnable(this);
        LogUtil.Companion companion = LogUtil.INSTANCE;
        companion.d(TAG, zNotificationEnable ? "Notification switch status: opened" : " Notification switch status: closed");
        if (!checkApkMd5()) {
            companion.d(TAG, "Apk don't exist will start download.");
            download();
            return;
        }
        companion.d(TAG, "Apk already exist and install it directly.");
        DownloadManager downloadManager2 = this.manager;
        if (downloadManager2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager2 = null;
        }
        String downloadPath = downloadManager2.getDownloadPath();
        DownloadManager downloadManager3 = this.manager;
        if (downloadManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager = downloadManager3;
        }
        done(new File(downloadPath, downloadManager.getApkName()));
    }

    private final void releaseResources() {
        DownloadManager downloadManager = this.manager;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        downloadManager.release$mvvm_release();
        stopSelf();
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void cancel() {
        LogUtil.INSTANCE.i(TAG, "download cancel");
        DownloadManager downloadManager = this.manager;
        DownloadManager downloadManager2 = null;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        downloadManager.setDownloadState(false);
        DownloadManager downloadManager3 = this.manager;
        if (downloadManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager3 = null;
        }
        if (downloadManager3.getShowNotification()) {
            NotificationUtil.INSTANCE.cancelNotification(this);
        }
        DownloadManager downloadManager4 = this.manager;
        if (downloadManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager2 = downloadManager4;
        }
        Iterator<T> it = downloadManager2.getOnDownloadListeners$mvvm_release().iterator();
        while (it.hasNext()) {
            ((OnDownloadListener) it.next()).cancel();
        }
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void done(@NotNull File apk) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(apk, "apk");
        LogUtil.INSTANCE.d(TAG, "apk downloaded to " + apk.getPath());
        DownloadManager downloadManager = this.manager;
        DownloadManager downloadManager2 = null;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        downloadManager.setDownloadState(false);
        DownloadManager downloadManager3 = this.manager;
        if (downloadManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager3 = null;
        }
        if (downloadManager3.getShowNotification() || Build.VERSION.SDK_INT >= 29) {
            NotificationUtil.Companion companion = NotificationUtil.INSTANCE;
            DownloadManager downloadManager4 = this.manager;
            if (downloadManager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                downloadManager4 = null;
            }
            int smallIcon = downloadManager4.getSmallIcon();
            String string = getResources().getString(R.string.app_update_download_completed);
            Intrinsics.checkNotNullExpressionValue(string, "resources.getString(R.st…pdate_download_completed)");
            String string2 = getResources().getString(R.string.app_update_click_hint);
            Intrinsics.checkNotNullExpressionValue(string2, "resources.getString(R.st…ng.app_update_click_hint)");
            String authorities = Constant.INSTANCE.getAUTHORITIES();
            Intrinsics.checkNotNull(authorities);
            companion.showDoneNotification(this, smallIcon, string, string2, authorities, apk);
        }
        DownloadManager downloadManager5 = this.manager;
        if (downloadManager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager5 = null;
        }
        if (downloadManager5.getJumpInstallPage()) {
            ApkUtil.Companion companion2 = ApkUtil.INSTANCE;
            String authorities2 = Constant.INSTANCE.getAUTHORITIES();
            Intrinsics.checkNotNull(authorities2);
            companion2.installApk(this, authorities2, apk);
        }
        DownloadManager downloadManager6 = this.manager;
        if (downloadManager6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager2 = downloadManager6;
        }
        Iterator<T> it = downloadManager2.getOnDownloadListeners$mvvm_release().iterator();
        while (it.hasNext()) {
            ((OnDownloadListener) it.next()).done(apk);
        }
        releaseResources();
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void downloading(int max, int progress) throws Resources.NotFoundException {
        String string;
        DownloadManager downloadManager = this.manager;
        DownloadManager downloadManager2 = null;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        if (downloadManager.getShowNotification()) {
            int i2 = (int) ((progress / max) * 100.0d);
            if (i2 == this.lastProgress) {
                return;
            }
            LogUtil.INSTANCE.i(TAG, "downloading max: " + max + " --- progress: " + progress);
            this.lastProgress = i2;
            if (i2 < 0) {
                string = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(i2);
                sb.append('%');
                string = sb.toString();
            }
            String str = string;
            NotificationUtil.Companion companion = NotificationUtil.INSTANCE;
            DownloadManager downloadManager3 = this.manager;
            if (downloadManager3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                downloadManager3 = null;
            }
            int smallIcon = downloadManager3.getSmallIcon();
            String string2 = getResources().getString(R.string.app_update_start_downloading);
            Intrinsics.checkNotNullExpressionValue(string2, "resources.getString(R.st…update_start_downloading)");
            companion.showProgressNotification(this, smallIcon, string2, str, max != -1 ? 100 : -1, i2);
        }
        DownloadManager downloadManager4 = this.manager;
        if (downloadManager4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager2 = downloadManager4;
        }
        Iterator<T> it = downloadManager2.getOnDownloadListeners$mvvm_release().iterator();
        while (it.hasNext()) {
            ((OnDownloadListener) it.next()).downloading(max, progress);
        }
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void error(@NotNull Throwable e2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(e2, "e");
        LogUtil.INSTANCE.e(TAG, "download error: " + e2);
        DownloadManager downloadManager = this.manager;
        DownloadManager downloadManager2 = null;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        downloadManager.setDownloadState(false);
        DownloadManager downloadManager3 = this.manager;
        if (downloadManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager3 = null;
        }
        if (downloadManager3.getShowNotification()) {
            NotificationUtil.Companion companion = NotificationUtil.INSTANCE;
            DownloadManager downloadManager4 = this.manager;
            if (downloadManager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                downloadManager4 = null;
            }
            int smallIcon = downloadManager4.getSmallIcon();
            String string = getResources().getString(R.string.app_update_download_error);
            Intrinsics.checkNotNullExpressionValue(string, "resources.getString(R.st…pp_update_download_error)");
            String string2 = getResources().getString(R.string.app_update_continue_downloading);
            Intrinsics.checkNotNullExpressionValue(string2, "resources.getString(R.st…ate_continue_downloading)");
            companion.showErrorNotification(this, smallIcon, string, string2);
        }
        DownloadManager downloadManager5 = this.manager;
        if (downloadManager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager2 = downloadManager5;
        }
        Iterator<T> it = downloadManager2.getOnDownloadListeners$mvvm_release().iterator();
        while (it.hasNext()) {
            ((OnDownloadListener) it.next()).error(e2);
        }
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(@Nullable Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) throws Resources.NotFoundException {
        if (intent == null) {
            return 2;
        }
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // com.azhon.appupdate.listener.OnDownloadListener
    public void start() throws Resources.NotFoundException {
        LogUtil.INSTANCE.i(TAG, "download start");
        DownloadManager downloadManager = this.manager;
        DownloadManager downloadManager2 = null;
        if (downloadManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager = null;
        }
        if (downloadManager.getShowBgdToast()) {
            Toast.makeText(this, R.string.app_update_background_downloading, 0).show();
        }
        DownloadManager downloadManager3 = this.manager;
        if (downloadManager3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
            downloadManager3 = null;
        }
        if (downloadManager3.getShowNotification()) {
            NotificationUtil.Companion companion = NotificationUtil.INSTANCE;
            DownloadManager downloadManager4 = this.manager;
            if (downloadManager4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
                downloadManager4 = null;
            }
            int smallIcon = downloadManager4.getSmallIcon();
            String string = getResources().getString(R.string.app_update_start_download);
            Intrinsics.checkNotNullExpressionValue(string, "resources.getString(R.st…pp_update_start_download)");
            String string2 = getResources().getString(R.string.app_update_start_download_hint);
            Intrinsics.checkNotNullExpressionValue(string2, "resources.getString(R.st…date_start_download_hint)");
            companion.showNotification(this, smallIcon, string, string2);
        }
        DownloadManager downloadManager5 = this.manager;
        if (downloadManager5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(PLVSocketUserConstant.USERTYPE_MANAGER);
        } else {
            downloadManager2 = downloadManager5;
        }
        Iterator<T> it = downloadManager2.getOnDownloadListeners$mvvm_release().iterator();
        while (it.hasNext()) {
            ((OnDownloadListener) it.next()).start();
        }
    }
}
