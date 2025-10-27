package com.azhon.appupdate.manager;

import com.aliyun.vod.log.core.AliyunLogCommon;
import com.azhon.appupdate.base.BaseHttpDownloadManager;
import com.azhon.appupdate.base.bean.DownloadStatus;
import com.azhon.appupdate.util.LogUtil;
import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J/\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00112\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\b\u0010\u0012\u001a\u00020\bH\u0016J\b\u0010\u0013\u001a\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0015"}, d2 = {"Lcom/azhon/appupdate/manager/HttpDownloadManager;", "Lcom/azhon/appupdate/base/BaseHttpDownloadManager;", "path", "", "(Ljava/lang/String;)V", "shutdown", "", "cancel", "", "connectToDownload", "apkUrl", "apkName", "flow", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "(Ljava/lang/String;Ljava/lang/String;Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", AliyunLogCommon.SubModule.download, "Lkotlinx/coroutines/flow/Flow;", "release", "trustAllHosts", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nHttpDownloadManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HttpDownloadManager.kt\ncom/azhon/appupdate/manager/HttpDownloadManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,135:1\n1#2:136\n*E\n"})
/* loaded from: classes2.dex */
public final class HttpDownloadManager extends BaseHttpDownloadManager {

    @NotNull
    private static final String TAG = "HttpDownloadManager";

    @NotNull
    private final String path;
    private boolean shutdown;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "com.azhon.appupdate.manager.HttpDownloadManager", f = "HttpDownloadManager.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4}, l = {75, 81, 83, 94, 97}, m = "connectToDownload", n = {"this", "flow", "con", "inStream", "len", "progress", "buffer", "file", "out", SessionDescription.ATTR_LENGTH, "con", "con", "con", "con"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$9", "I$0", "L$0", "L$0", "L$0", "L$0"})
    /* renamed from: com.azhon.appupdate.manager.HttpDownloadManager$connectToDownload$1, reason: invalid class name */
    public static final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return HttpDownloadManager.this.connectToDownload(null, null, null, this);
        }
    }

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00010\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.azhon.appupdate.manager.HttpDownloadManager$download$2", f = "HttpDownloadManager.kt", i = {0}, l = {47, 48}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
    /* renamed from: com.azhon.appupdate.manager.HttpDownloadManager$download$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<FlowCollector<? super DownloadStatus>, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $apkName;
        final /* synthetic */ String $apkUrl;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, String str2, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$apkUrl = str;
            this.$apkName = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = HttpDownloadManager.this.new AnonymousClass2(this.$apkUrl, this.$apkName, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super DownloadStatus> flowCollector, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
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
                DownloadStatus.Start start = DownloadStatus.Start.INSTANCE;
                this.L$0 = flowCollector;
                this.label = 1;
                if (flowCollector.emit(start, this) == coroutine_suspended) {
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
            HttpDownloadManager httpDownloadManager = HttpDownloadManager.this;
            String str = this.$apkUrl;
            String str2 = this.$apkName;
            this.L$0 = null;
            this.label = 2;
            if (httpDownloadManager.connectToDownload(str, str2, flowCollector, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/azhon/appupdate/base/bean/DownloadStatus;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.azhon.appupdate.manager.HttpDownloadManager$download$3", f = "HttpDownloadManager.kt", i = {}, l = {50}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.azhon.appupdate.manager.HttpDownloadManager$download$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function3<FlowCollector<? super DownloadStatus>, Throwable, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super DownloadStatus> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
            anonymousClass3.L$0 = flowCollector;
            anonymousClass3.L$1 = th;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                DownloadStatus.Error error = new DownloadStatus.Error((Throwable) this.L$1);
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(error, this) == coroutine_suspended) {
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

    public HttpDownloadManager(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.io.FileOutputStream, java.io.OutputStream, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object connectToDownload(java.lang.String r17, java.lang.String r18, kotlinx.coroutines.flow.FlowCollector<? super com.azhon.appupdate.base.bean.DownloadStatus> r19, kotlin.coroutines.Continuation<? super kotlin.Unit> r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 488
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.azhon.appupdate.manager.HttpDownloadManager.connectToDownload(java.lang.String, java.lang.String, kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void trustAllHosts() throws NoSuchAlgorithmException, KeyManagementException {
        X509TrustManager x509TrustManager = new X509TrustManager() { // from class: com.azhon.appupdate.manager.HttpDownloadManager$trustAllHosts$manager$1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(@Nullable X509Certificate[] chain, @Nullable String authType) {
                LogUtil.INSTANCE.d("HttpDownloadManager", "checkClientTrusted");
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(@Nullable X509Certificate[] chain, @Nullable String authType) {
                LogUtil.INSTANCE.d("HttpDownloadManager", "checkServerTrusted");
            }

            @Override // javax.net.ssl.X509TrustManager
            @NotNull
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            sSLContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
        } catch (Exception e2) {
            LogUtil.INSTANCE.e(TAG, "trustAllHosts error: " + e2);
        }
    }

    @Override // com.azhon.appupdate.base.BaseHttpDownloadManager
    public void cancel() {
        this.shutdown = true;
    }

    @Override // com.azhon.appupdate.base.BaseHttpDownloadManager
    @NotNull
    public Flow<DownloadStatus> download(@NotNull String apkUrl, @NotNull String apkName) throws NoSuchAlgorithmException, KeyManagementException {
        Intrinsics.checkNotNullParameter(apkUrl, "apkUrl");
        Intrinsics.checkNotNullParameter(apkName, "apkName");
        trustAllHosts();
        this.shutdown = false;
        File file = new File(this.path, apkName);
        if (file.exists()) {
            file.delete();
        }
        return FlowKt.flowOn(FlowKt.m2338catch(FlowKt.flow(new AnonymousClass2(apkUrl, apkName, null)), new AnonymousClass3(null)), Dispatchers.getIO());
    }

    @Override // com.azhon.appupdate.base.BaseHttpDownloadManager
    public void release() {
        cancel();
    }
}
