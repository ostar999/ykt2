package com.yddmi.doctor.pages.web;

import androidx.lifecycle.ViewModelKt;
import com.catchpig.download.entity.DownloadProgress;
import com.catchpig.download.manager.CoroutinesDownloadManager;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.entity.result.HomeClinicalDetail;
import com.yddmi.doctor.repository.YddClinicRepository;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u00109\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010;0:J\u0010\u0010<\u001a\u00020=2\b\u0010>\u001a\u0004\u0018\u00010\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0010\"\u0004\b\u001b\u0010\u0012R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00140\n¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\rR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0010\"\u0004\b&\u0010\u0012R\u001a\u0010'\u001a\u00020(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010*\"\u0004\b/\u0010,R\u001a\u00100\u001a\u00020(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010*\"\u0004\b2\u0010,R\u001c\u00103\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0010\"\u0004\b5\u0010\u0012R\u001a\u00106\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\u0006\"\u0004\b8\u0010\b¨\u0006?"}, d2 = {"Lcom/yddmi/doctor/pages/web/WebViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "categoryId", "", "getCategoryId", "()I", "setCategoryId", "(I)V", "fileDownPathChangeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getFileDownPathChangeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "fileUri", "getFileUri", "()Ljava/lang/String;", "setFileUri", "(Ljava/lang/String;)V", "id", "", "getId", "()J", "setId", "(J)V", "loadUri", "getLoadUri", "setLoadUri", "loadingHideChangeMsf", "getLoadingHideChangeMsf", "patientId", "getPatientId", "setPatientId", "rightActionDrawableId", "getRightActionDrawableId", "setRightActionDrawableId", "rightActionString", "getRightActionString", "setRightActionString", "showLoadProgress", "", "getShowLoadProgress", "()Z", "setShowLoadProgress", "(Z)V", "showTitleBar", "getShowTitleBar", "setShowTitleBar", "showTitleBarLine", "getShowTitleBarLine", "setShowTitleBarLine", "title", "getTitle", "setTitle", "trainId", "getTrainId", "setTrainId", "getClinicalGuidelineDetail", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/HomeClinicalDetail;", "httpDownLoadFile", "", "downFileUri", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class WebViewModel extends BaseViewModel {
    private int rightActionDrawableId;

    @NotNull
    private String loadUri = "";

    @Nullable
    private String fileUri = "";

    @Nullable
    private String title = "";

    @Nullable
    private String rightActionString = "";
    private boolean showTitleBar = true;
    private boolean showTitleBarLine = true;
    private boolean showLoadProgress = true;
    private int trainId = -1;
    private int patientId = -1;
    private long id = -1;
    private int categoryId = -1;

    @NotNull
    private final MutableStateFlow<String> fileDownPathChangeMsf = StateFlowKt.MutableStateFlow("");

    @NotNull
    private final MutableStateFlow<Long> loadingHideChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.web.WebViewModel$httpDownLoadFile$1", f = "WebViewModel.kt", i = {}, l = {58}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.web.WebViewModel$httpDownLoadFile$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $downFileUri;
        int label;
        final /* synthetic */ WebViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, WebViewModel webViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$downFileUri = str;
            this.this$0 = webViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$downFileUri, this.this$0, continuation);
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
                CoroutinesDownloadManager companion = CoroutinesDownloadManager.INSTANCE.getInstance();
                String str = this.$downFileUri;
                final WebViewModel webViewModel = this.this$0;
                Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.web.WebViewModel.httpDownLoadFile.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                        invoke2(str2);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        LogExtKt.logd("文件下载存储路径：" + it, YddConfig.TAG);
                        webViewModel.getFileDownPathChangeMsf().setValue(it);
                    }
                };
                AnonymousClass2 anonymousClass2 = new Function1<DownloadProgress, Unit>() { // from class: com.yddmi.doctor.pages.web.WebViewModel.httpDownLoadFile.1.2
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DownloadProgress downloadProgress) {
                        invoke2(downloadProgress);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull DownloadProgress downloadProgress) {
                        Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                        LogExtKt.logd("下载进度更新 " + downloadProgress, "WebViewModel");
                    }
                };
                AnonymousClass3 anonymousClass3 = new Function0<Unit>() { // from class: com.yddmi.doctor.pages.web.WebViewModel.httpDownLoadFile.1.3
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                AnonymousClass4 anonymousClass4 = new Function0<Unit>() { // from class: com.yddmi.doctor.pages.web.WebViewModel.httpDownLoadFile.1.4
                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                    }
                };
                final WebViewModel webViewModel2 = this.this$0;
                Function1<Throwable, Unit> function12 = new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.web.WebViewModel.httpDownLoadFile.1.5
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull Throwable it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        webViewModel2.getLoadingHideChangeMsf().setValue(Long.valueOf(DateUtil.getTimeInMillisLong()));
                    }
                };
                this.label = 1;
                if (companion.download(str, function1, anonymousClass2, anonymousClass3, anonymousClass4, function12, this) == coroutine_suspended) {
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

    public final int getCategoryId() {
        return this.categoryId;
    }

    @NotNull
    public final Flow<HomeClinicalDetail> getClinicalGuidelineDetail() {
        return YddClinicRepository.INSTANCE.getClinicalGuidelineDetail(this.categoryId);
    }

    @NotNull
    public final MutableStateFlow<String> getFileDownPathChangeMsf() {
        return this.fileDownPathChangeMsf;
    }

    @Nullable
    public final String getFileUri() {
        return this.fileUri;
    }

    public final long getId() {
        return this.id;
    }

    @NotNull
    public final String getLoadUri() {
        return this.loadUri;
    }

    @NotNull
    public final MutableStateFlow<Long> getLoadingHideChangeMsf() {
        return this.loadingHideChangeMsf;
    }

    public final int getPatientId() {
        return this.patientId;
    }

    public final int getRightActionDrawableId() {
        return this.rightActionDrawableId;
    }

    @Nullable
    public final String getRightActionString() {
        return this.rightActionString;
    }

    public final boolean getShowLoadProgress() {
        return this.showLoadProgress;
    }

    public final boolean getShowTitleBar() {
        return this.showTitleBar;
    }

    public final boolean getShowTitleBarLine() {
        return this.showTitleBarLine;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    public final int getTrainId() {
        return this.trainId;
    }

    public final void httpDownLoadFile(@Nullable String downFileUri) {
        if (downFileUri == null || downFileUri.length() == 0) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(downFileUri, this, null), 3, null);
    }

    public final void setCategoryId(int i2) {
        this.categoryId = i2;
    }

    public final void setFileUri(@Nullable String str) {
        this.fileUri = str;
    }

    public final void setId(long j2) {
        this.id = j2;
    }

    public final void setLoadUri(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.loadUri = str;
    }

    public final void setPatientId(int i2) {
        this.patientId = i2;
    }

    public final void setRightActionDrawableId(int i2) {
        this.rightActionDrawableId = i2;
    }

    public final void setRightActionString(@Nullable String str) {
        this.rightActionString = str;
    }

    public final void setShowLoadProgress(boolean z2) {
        this.showLoadProgress = z2;
    }

    public final void setShowTitleBar(boolean z2) {
        this.showTitleBar = z2;
    }

    public final void setShowTitleBarLine(boolean z2) {
        this.showTitleBarLine = z2;
    }

    public final void setTitle(@Nullable String str) {
        this.title = str;
    }

    public final void setTrainId(int i2) {
        this.trainId = i2;
    }
}
