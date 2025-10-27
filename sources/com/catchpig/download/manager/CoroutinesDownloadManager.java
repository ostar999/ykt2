package com.catchpig.download.manager;

import androidx.exifinterface.media.ExifInterface;
import com.alipay.sdk.authjs.a;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.download.api.DownloadService;
import com.catchpig.download.callback.DownloadCallback;
import com.catchpig.download.callback.MultiDownloadCallback;
import com.catchpig.download.entity.DownloadProgress;
import com.catchpig.download.subscriber.DownloadSubscriber;
import com.catchpig.download.subscriber.MultiDownloadSubscriber;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.net.URL;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 -2\u00020\u0001:\u0002-.B\u0005¢\u0006\u0002\u0010\u0002Jc\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062!\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00040\b2%\b\u0002\u0010\f\u001a\u001f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u0004\u0018\u00010\bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000fJ¢\u0001\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062!\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00040\b2%\b\u0002\u0010\f\u001a\u001f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u0004\u0018\u00010\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u00112!\u0010\u0013\u001a\u001d\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u00040\bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0016J!\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0019Jc\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062!\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\u001b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\u00040\b2%\b\u0002\u0010\f\u001a\u001f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u0004\u0018\u00010\bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000fJ/\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010#Jo\u0010$\u001a\u00020\u00042\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060&2'\u0010\u0007\u001a#\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00060'¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b((\u0012\u0004\u0012\u00020\u00040\b2%\b\u0002\u0010\f\u001a\u001f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u0004\u0018\u00010\bH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010)J'\u0010$\u001a\u00020\u00042\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060&2\u0006\u0010*\u001a\u00020+H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010,\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006/"}, d2 = {"Lcom/catchpig/download/manager/CoroutinesDownloadManager;", "Lcom/catchpig/download/manager/DownloadManager;", "()V", AliyunLogCommon.SubModule.download, "", "downloadUrl", "", a.f3170c, "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "path", "progress", "Lcom/catchpig/download/entity/DownloadProgress;", "downloadProgress", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "Lkotlin/Function0;", "complete", "error", "", "t", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "downloadCallback", "Lcom/catchpig/download/callback/DownloadCallback;", "(Ljava/lang/String;Lcom/catchpig/download/callback/DownloadCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "downloadFile", "Ljava/io/File;", "file", "httpDownload", "Lkotlinx/coroutines/flow/Flow;", "downloadService", "Lcom/catchpig/download/api/DownloadService;", "url", "localFilePath", "(Lcom/catchpig/download/api/DownloadService;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "multiDownload", "downloadUrls", "", "", "paths", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "multiDownloadCallback", "Lcom/catchpig/download/callback/MultiDownloadCallback;", "(Ljava/lang/Iterable;Lcom/catchpig/download/callback/MultiDownloadCallback;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "CoroutinesDownloadManagerHolder", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nCoroutinesDownloadManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CoroutinesDownloadManager.kt\ncom/catchpig/download/manager/CoroutinesDownloadManager\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 4 SafeCollector.common.kt\nkotlinx/coroutines/flow/internal/SafeCollector_commonKt\n*L\n1#1,270:1\n47#2:271\n49#2:275\n50#3:272\n55#3:274\n106#4:273\n*S KotlinDebug\n*F\n+ 1 CoroutinesDownloadManager.kt\ncom/catchpig/download/manager/CoroutinesDownloadManager\n*L\n266#1:271\n266#1:275\n266#1:272\n266#1:274\n266#1:273\n*E\n"})
/* loaded from: classes2.dex */
public final class CoroutinesDownloadManager extends DownloadManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/catchpig/download/manager/CoroutinesDownloadManager$Companion;", "", "()V", "getInstance", "Lcom/catchpig/download/manager/CoroutinesDownloadManager;", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final CoroutinesDownloadManager getInstance() {
            return CoroutinesDownloadManagerHolder.INSTANCE.getHolder();
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/catchpig/download/manager/CoroutinesDownloadManager$CoroutinesDownloadManagerHolder;", "", "()V", "holder", "Lcom/catchpig/download/manager/CoroutinesDownloadManager;", "getHolder", "()Lcom/catchpig/download/manager/CoroutinesDownloadManager;", "download_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class CoroutinesDownloadManagerHolder {

        @NotNull
        public static final CoroutinesDownloadManagerHolder INSTANCE = new CoroutinesDownloadManagerHolder();

        @NotNull
        private static final CoroutinesDownloadManager holder = new CoroutinesDownloadManager();

        private CoroutinesDownloadManagerHolder() {
        }

        @NotNull
        public final CoroutinesDownloadManager getHolder() {
            return holder;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"", AdvanceSetting.NETWORK_TYPE, "Lkotlinx/coroutines/flow/Flow;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$download$6", f = "CoroutinesDownloadManager.kt", i = {}, l = {240}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$download$6, reason: invalid class name */
    public static final class AnonymousClass6 extends SuspendLambda implements Function2<String, Continuation<? super Flow<? extends String>>, Object> {
        final /* synthetic */ DownloadSubscriber $downloadSubscriber;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(DownloadSubscriber downloadSubscriber, Continuation<? super AnonymousClass6> continuation) {
            super(2, continuation);
            this.$downloadSubscriber = downloadSubscriber;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass6 anonymousClass6 = CoroutinesDownloadManager.this.new AnonymousClass6(this.$downloadSubscriber, continuation);
            anonymousClass6.L$0 = obj;
            return anonymousClass6;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(String str, Continuation<? super Flow<? extends String>> continuation) {
            return invoke2(str, (Continuation<? super Flow<String>>) continuation);
        }

        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(@NotNull String str, @Nullable Continuation<? super Flow<String>> continuation) {
            return ((AnonymousClass6) create(str, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                String str = (String) this.L$0;
                String strLocalFileName = CoroutinesDownloadManager.this.localFileName(str);
                File file = new File(strLocalFileName);
                URL url = new URL(str);
                if (file.exists()) {
                    long length = file.length();
                    if (length == url.openConnection().getContentLength()) {
                        this.$downloadSubscriber.update(length, length, true);
                        return FlowKt.flowOn(FlowKt.flowOf(strLocalFileName), Dispatchers.getIO());
                    }
                }
                DownloadService downloadServiceInitDownloadService = DownloadManager.INSTANCE.initDownloadService(url, this.$downloadSubscriber, false);
                CoroutinesDownloadManager coroutinesDownloadManager = CoroutinesDownloadManager.this;
                String file2 = url.getFile();
                Intrinsics.checkNotNullExpressionValue(file2, "url.file");
                String strSubstring = file2.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                this.label = 1;
                obj = coroutinesDownloadManager.httpDownload(downloadServiceInitDownloadService, strSubstring, strLocalFileName, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$download$7", f = "CoroutinesDownloadManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$download$7, reason: invalid class name */
    public static final class AnonymousClass7 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ DownloadSubscriber $downloadSubscriber;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(DownloadSubscriber downloadSubscriber, Continuation<? super AnonymousClass7> continuation) {
            super(2, continuation);
            this.$downloadSubscriber = downloadSubscriber;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass7(this.$downloadSubscriber, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass7) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$downloadSubscriber.onStart();
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$download$8", f = "CoroutinesDownloadManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$download$8, reason: invalid class name */
    public static final class AnonymousClass8 extends SuspendLambda implements Function3<FlowCollector<? super String>, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ DownloadSubscriber $downloadSubscriber;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(DownloadSubscriber downloadSubscriber, Continuation<? super AnonymousClass8> continuation) {
            super(3, continuation);
            this.$downloadSubscriber = downloadSubscriber;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return new AnonymousClass8(this.$downloadSubscriber, continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$downloadSubscriber.onComplete();
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$download$9", f = "CoroutinesDownloadManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$download$9, reason: invalid class name */
    public static final class AnonymousClass9 extends SuspendLambda implements Function3<FlowCollector<? super String>, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ DownloadSubscriber $downloadSubscriber;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass9(DownloadSubscriber downloadSubscriber, Continuation<? super AnonymousClass9> continuation) {
            super(3, continuation);
            this.$downloadSubscriber = downloadSubscriber;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass9 anonymousClass9 = new AnonymousClass9(this.$downloadSubscriber, continuation);
            anonymousClass9.L$0 = th;
            return anonymousClass9.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$downloadSubscriber.onError((Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lokhttp3/ResponseBody;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$2", f = "CoroutinesDownloadManager.kt", i = {}, l = {R2.attr.actionViewClass, R2.attr.actionViewClass}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$2, reason: invalid class name and case insensitive filesystem */
    public static final class C05062 extends SuspendLambda implements Function2<FlowCollector<? super ResponseBody>, Continuation<? super Unit>, Object> {
        final /* synthetic */ DownloadService $downloadService;
        final /* synthetic */ String $url;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05062(DownloadService downloadService, String str, Continuation<? super C05062> continuation) {
            super(2, continuation);
            this.$downloadService = downloadService;
            this.$url = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C05062 c05062 = new C05062(this.$downloadService, this.$url, continuation);
            c05062.L$0 = obj;
            return c05062;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super ResponseBody> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((C05062) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            FlowCollector flowCollector;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                DownloadService downloadService = this.$downloadService;
                String str = this.$url;
                this.L$0 = flowCollector;
                this.label = 1;
                obj = downloadService.coroutinesDownload(str, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.L$0 = null;
            this.label = 2;
            if (flowCollector.emit(obj, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00000\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"", AdvanceSetting.NETWORK_TYPE, "Lkotlinx/coroutines/flow/Flow;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$4", f = "CoroutinesDownloadManager.kt", i = {}, l = {90}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$4, reason: invalid class name and case insensitive filesystem */
    public static final class C05084 extends SuspendLambda implements Function2<String, Continuation<? super Flow<? extends String>>, Object> {
        final /* synthetic */ MultiDownloadSubscriber $multiDownloadSubscriber;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05084(MultiDownloadSubscriber multiDownloadSubscriber, Continuation<? super C05084> continuation) {
            super(2, continuation);
            this.$multiDownloadSubscriber = multiDownloadSubscriber;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C05084 c05084 = CoroutinesDownloadManager.this.new C05084(this.$multiDownloadSubscriber, continuation);
            c05084.L$0 = obj;
            return c05084;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(String str, Continuation<? super Flow<? extends String>> continuation) {
            return invoke2(str, (Continuation<? super Flow<String>>) continuation);
        }

        @Nullable
        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(@NotNull String str, @Nullable Continuation<? super Flow<String>> continuation) {
            return ((C05084) create(str, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                String str = (String) this.L$0;
                String strLocalFileName = CoroutinesDownloadManager.this.localFileName(str);
                File file = new File(strLocalFileName);
                URL url = new URL(str);
                if (file.exists()) {
                    long length = file.length();
                    if (length == url.openConnection().getContentLength()) {
                        this.$multiDownloadSubscriber.update(length, length, true);
                        return FlowKt.flowOf(strLocalFileName);
                    }
                }
                DownloadService downloadServiceInitDownloadService = DownloadManager.INSTANCE.initDownloadService(url, this.$multiDownloadSubscriber, false);
                CoroutinesDownloadManager coroutinesDownloadManager = CoroutinesDownloadManager.this;
                String file2 = url.getFile();
                Intrinsics.checkNotNullExpressionValue(file2, "url.file");
                String strSubstring = file2.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                this.label = 1;
                obj = coroutinesDownloadManager.httpDownload(downloadServiceInitDownloadService, strSubstring, strLocalFileName, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$5", f = "CoroutinesDownloadManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$5, reason: invalid class name */
    public static final class AnonymousClass5 extends SuspendLambda implements Function2<FlowCollector<? super String>, Continuation<? super Unit>, Object> {
        final /* synthetic */ MultiDownloadSubscriber $multiDownloadSubscriber;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(MultiDownloadSubscriber multiDownloadSubscriber, Continuation<? super AnonymousClass5> continuation) {
            super(2, continuation);
            this.$multiDownloadSubscriber = multiDownloadSubscriber;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass5(this.$multiDownloadSubscriber, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass5) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$multiDownloadSubscriber.onStart();
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$6", f = "CoroutinesDownloadManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$6, reason: invalid class name and case insensitive filesystem */
    public static final class C05096 extends SuspendLambda implements Function3<FlowCollector<? super String>, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ MultiDownloadSubscriber $multiDownloadSubscriber;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05096(MultiDownloadSubscriber multiDownloadSubscriber, Continuation<? super C05096> continuation) {
            super(3, continuation);
            this.$multiDownloadSubscriber = multiDownloadSubscriber;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @Nullable Throwable th, @Nullable Continuation<? super Unit> continuation) {
            return new C05096(this.$multiDownloadSubscriber, continuation).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$multiDownloadSubscriber.onComplete();
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", "t", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$7", f = "CoroutinesDownloadManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$multiDownload$7, reason: invalid class name and case insensitive filesystem */
    public static final class C05107 extends SuspendLambda implements Function3<FlowCollector<? super String>, Throwable, Continuation<? super Unit>, Object> {
        final /* synthetic */ MultiDownloadSubscriber $multiDownloadSubscriber;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05107(MultiDownloadSubscriber multiDownloadSubscriber, Continuation<? super C05107> continuation) {
            super(3, continuation);
            this.$multiDownloadSubscriber = multiDownloadSubscriber;
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            C05107 c05107 = new C05107(this.$multiDownloadSubscriber, continuation);
            c05107.L$0 = th;
            return c05107.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$multiDownloadSubscriber.onError((Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Object download$default(CoroutinesDownloadManager coroutinesDownloadManager, String str, Function1 function1, Function1 function12, Continuation continuation, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function12 = null;
        }
        return coroutinesDownloadManager.download(str, function1, function12, continuation);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Object downloadFile$default(CoroutinesDownloadManager coroutinesDownloadManager, String str, Function1 function1, Function1 function12, Continuation continuation, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function12 = null;
        }
        return coroutinesDownloadManager.downloadFile(str, function1, function12, continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object httpDownload(DownloadService downloadService, String str, final String str2, Continuation<? super Flow<String>> continuation) {
        final Flow flow = FlowKt.flow(new C05062(downloadService, str, null));
        return new Flow<String>() { // from class: com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1

            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 CoroutinesDownloadManager.kt\ncom/catchpig/download/manager/CoroutinesDownloadManager\n*L\n1#1,222:1\n48#2:223\n267#3:224\n*E\n"})
            /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ String $localFilePath$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ CoroutinesDownloadManager this$0;

                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1$2", f = "CoroutinesDownloadManager.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1$emit$1\n*L\n1#1,222:1\n*E\n"})
                /* renamed from: com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, CoroutinesDownloadManager coroutinesDownloadManager, String str) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = coroutinesDownloadManager;
                    this.$localFilePath$inlined = str;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r7) throws java.io.IOException {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1$2$1 r0 = (com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1$2$1 r0 = new com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L49
                    L29:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L31:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.flow.FlowCollector r7 = r5.$this_unsafeFlow
                        okhttp3.ResponseBody r6 = (okhttp3.ResponseBody) r6
                        com.catchpig.download.manager.CoroutinesDownloadManager r2 = r5.this$0
                        java.lang.String r4 = r5.$localFilePath$inlined
                        java.lang.String r6 = r2.writeCache(r6, r4)
                        r0.label = r3
                        java.lang.Object r6 = r7.emit(r6, r0)
                        if (r6 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.catchpig.download.manager.CoroutinesDownloadManager$httpDownload$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super String> flowCollector, @NotNull Continuation continuation2) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this, str2), continuation2);
                return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Object multiDownload$default(CoroutinesDownloadManager coroutinesDownloadManager, Iterable iterable, Function1 function1, Function1 function12, Continuation continuation, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function12 = null;
        }
        return coroutinesDownloadManager.multiDownload(iterable, function1, function12, continuation);
    }

    @Nullable
    public final Object download(@NotNull String str, @NotNull final Function1<? super String, Unit> function1, @Nullable final Function1<? super DownloadProgress, Unit> function12, @NotNull Continuation<? super Unit> continuation) {
        Object objDownload = download(str, new DownloadCallback() { // from class: com.catchpig.download.manager.CoroutinesDownloadManager.download.2
            @Override // com.catchpig.download.callback.DownloadCallback
            public void onComplete() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onError(@NotNull Throwable t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onProgress(@NotNull DownloadProgress downloadProgress) {
                Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                Function1<DownloadProgress, Unit> function13 = function12;
                if (function13 != null) {
                    function13.invoke(downloadProgress);
                }
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onStart() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onSuccess(@NotNull String path) {
                Intrinsics.checkNotNullParameter(path, "path");
                function1.invoke(path);
            }
        }, continuation);
        return objDownload == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDownload : Unit.INSTANCE;
    }

    @Nullable
    public final Object downloadFile(@NotNull String str, @NotNull final Function1<? super File, Unit> function1, @Nullable final Function1<? super DownloadProgress, Unit> function12, @NotNull Continuation<? super Unit> continuation) {
        Object objDownload = download(str, new DownloadCallback() { // from class: com.catchpig.download.manager.CoroutinesDownloadManager.downloadFile.2
            @Override // com.catchpig.download.callback.DownloadCallback
            public void onComplete() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onError(@NotNull Throwable t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onProgress(@NotNull DownloadProgress downloadProgress) {
                Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                Function1<DownloadProgress, Unit> function13 = function12;
                if (function13 != null) {
                    function13.invoke(downloadProgress);
                }
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onStart() {
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onSuccess(@NotNull String path) {
                Intrinsics.checkNotNullParameter(path, "path");
                function1.invoke(new File(path));
            }
        }, continuation);
        return objDownload == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDownload : Unit.INSTANCE;
    }

    @Nullable
    public final Object multiDownload(@NotNull Iterable<String> iterable, @NotNull final Function1<? super List<String>, Unit> function1, @Nullable final Function1<? super DownloadProgress, Unit> function12, @NotNull Continuation<? super Unit> continuation) {
        Object objMultiDownload = multiDownload(iterable, new MultiDownloadCallback() { // from class: com.catchpig.download.manager.CoroutinesDownloadManager.multiDownload.2
            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onComplete() {
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onError(@NotNull Throwable t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onProgress(@NotNull DownloadProgress downloadProgress) {
                Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                Function1<DownloadProgress, Unit> function13 = function12;
                if (function13 != null) {
                    function13.invoke(downloadProgress);
                }
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onStart() {
            }

            @Override // com.catchpig.download.callback.MultiDownloadCallback
            public void onSuccess(@NotNull List<String> paths) {
                Intrinsics.checkNotNullParameter(paths, "paths");
                function1.invoke(paths);
            }
        }, continuation);
        return objMultiDownload == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objMultiDownload : Unit.INSTANCE;
    }

    @Nullable
    public final Object download(@NotNull String str, @NotNull final Function1<? super String, Unit> function1, @Nullable final Function1<? super DownloadProgress, Unit> function12, @NotNull final Function0<Unit> function0, @NotNull final Function0<Unit> function02, @NotNull final Function1<? super Throwable, Unit> function13, @NotNull Continuation<? super Unit> continuation) {
        Object objDownload = download(str, new DownloadCallback() { // from class: com.catchpig.download.manager.CoroutinesDownloadManager.download.4
            @Override // com.catchpig.download.callback.DownloadCallback
            public void onComplete() {
                function02.invoke();
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onError(@NotNull Throwable t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                function13.invoke(t2);
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onProgress(@NotNull DownloadProgress downloadProgress) {
                Intrinsics.checkNotNullParameter(downloadProgress, "downloadProgress");
                Function1<DownloadProgress, Unit> function14 = function12;
                if (function14 != null) {
                    function14.invoke(downloadProgress);
                }
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onStart() {
                function0.invoke();
            }

            @Override // com.catchpig.download.callback.DownloadCallback
            public void onSuccess(@NotNull String path) {
                Intrinsics.checkNotNullParameter(path, "path");
                function1.invoke(path);
            }
        }, continuation);
        return objDownload == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDownload : Unit.INSTANCE;
    }

    @Nullable
    public final Object multiDownload(@NotNull Iterable<String> iterable, @NotNull MultiDownloadCallback multiDownloadCallback, @NotNull Continuation<? super Unit> continuation) {
        final MultiDownloadSubscriber multiDownloadSubscriber = new MultiDownloadSubscriber(CollectionsKt___CollectionsKt.count(iterable), multiDownloadCallback);
        Object objCollect = FlowKt.m2338catch(FlowKt.onCompletion(FlowKt.onStart(FlowKt.flowOn(FlowKt.flatMapConcat(FlowKt.asFlow(iterable), new C05084(multiDownloadSubscriber, null)), Dispatchers.getIO()), new AnonymousClass5(multiDownloadSubscriber, null)), new C05096(multiDownloadSubscriber, null)), new C05107(multiDownloadSubscriber, null)).collect(new FlowCollector() { // from class: com.catchpig.download.manager.CoroutinesDownloadManager.multiDownload.8
            @Override // kotlinx.coroutines.flow.FlowCollector
            public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation2) {
                return emit((String) obj, (Continuation<? super Unit>) continuation2);
            }

            @Nullable
            public final Object emit(@NotNull String str, @NotNull Continuation<? super Unit> continuation2) {
                multiDownloadSubscriber.onNext(str);
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }

    @Nullable
    public final Object download(@NotNull String str, @NotNull DownloadCallback downloadCallback, @NotNull Continuation<? super Unit> continuation) {
        final DownloadSubscriber downloadSubscriber = new DownloadSubscriber(downloadCallback);
        Object objCollect = FlowKt.m2338catch(FlowKt.onCompletion(FlowKt.onStart(FlowKt.flowOn(FlowKt.flatMapConcat(FlowKt.flowOf(str), new AnonymousClass6(downloadSubscriber, null)), Dispatchers.getIO()), new AnonymousClass7(downloadSubscriber, null)), new AnonymousClass8(downloadSubscriber, null)), new AnonymousClass9(downloadSubscriber, null)).collect(new FlowCollector() { // from class: com.catchpig.download.manager.CoroutinesDownloadManager.download.10
            @Override // kotlinx.coroutines.flow.FlowCollector
            public /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation2) {
                return emit((String) obj, (Continuation<? super Unit>) continuation2);
            }

            @Nullable
            public final Object emit(@NotNull String str2, @NotNull Continuation<? super Unit> continuation2) {
                downloadSubscriber.onNext(str2);
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
    }
}
