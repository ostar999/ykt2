package com.yddmi.doctor.pages.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.lifecycle.LifecycleOwnerKt;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.FileUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.annotation.Title;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.config.Config;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.widget.dsbridge.DWebView;
import com.catchpig.mvvm.widget.dsbridge.OnReturnValue;
import com.catchpig.utils.ext.LogExtKt;
import com.google.gson.JsonObject;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.just.agentweb.DefaultWebClient;
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat;
import com.tencent.open.SocialConstants;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.tbs.reader.TbsFileInterfaceImpl;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.ActivityWebBinding;
import com.yddmi.doctor.entity.result.HomeClinicalDetail;
import com.yddmi.doctor.pages.web.JsCallNativeApi;
import com.yddmi.doctor.utils.OtherUtils;
import com.yikaobang.yixue.R2;
import java.io.File;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\b\u0007\u0018\u0000 +2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004:\u0001+B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u000bH\u0002J\b\u0010\u000e\u001a\u00020\u000bH\u0016J\b\u0010\u000f\u001a\u00020\u000bH\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0016J\u0012\u0010\u0011\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u000bH\u0002J\u0012\u0010\u0015\u001a\u00020\u000b2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0002J\u0012\u0010\u0016\u001a\u00020\u000b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0013H\u0002J\b\u0010\u0018\u001a\u00020\u000bH\u0002J\b\u0010\u0019\u001a\u00020\u000bH\u0002J\b\u0010\u001a\u001a\u00020\u000bH\u0016J\b\u0010\u001b\u001a\u00020\u000bH\u0014J\u001c\u0010\u001c\u001a\u00020\u000b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0013H\u0016J&\u0010 \u001a\u00020\u000b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00132\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\u001a\u0010#\u001a\u00020\u000b2\b\u0010$\u001a\u0004\u0018\u00010\u001e2\u0006\u0010%\u001a\u00020&H\u0016J\u001c\u0010'\u001a\u00020\u000b2\b\u0010$\u001a\u0004\u0018\u00010\u001e2\b\u0010(\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010)\u001a\u00020\u000bH\u0002J\b\u0010*\u001a\u00020\u000bH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/yddmi/doctor/pages/web/WebActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/ActivityWebBinding;", "Lcom/yddmi/doctor/pages/web/WebViewModel;", "Lcom/catchpig/mvvm/widget/dsbridge/DWebView$OnDWebViewListener;", "()V", "jsCallNativeApi", "Lcom/yddmi/doctor/pages/web/JsCallNativeApi;", "x5FileSuccess", "", "dealInitX5File", "", "dealOpenWxService", "httpGetClinicalGuidelineDetail", "initFlow", "initParam", "initView", "loadAppFile", "path", "", "loadWebCall", "loadWebFile", "loadWebHtml", "html", "loadWebUri", "nativeCallJs", "onBackPressed", "onDestroy", "onPageFinished", "webView", "Lcom/catchpig/mvvm/widget/dsbridge/DWebView;", "url", "onPageStarted", "bitmap", "Landroid/graphics/Bitmap;", "onProgressChanged", "view", "newProgress", "", "onReceivedTitle", "title", "releaseWeb", "screenOrientation", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(transparent = false)
@Title
@SourceDebugExtension({"SMAP\nWebActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WebActivity.kt\ncom/yddmi/doctor/pages/web/WebActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,357:1\n18#2,2:358\n1#3:360\n*S KotlinDebug\n*F\n+ 1 WebActivity.kt\ncom/yddmi/doctor/pages/web/WebActivity\n*L\n89#1:358,2\n89#1:360\n*E\n"})
/* loaded from: classes6.dex */
public final class WebActivity extends BaseVMActivity<ActivityWebBinding, WebViewModel> implements DWebView.OnDWebViewListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TAG = "WebActivity";
    private JsCallNativeApi jsCallNativeApi;
    private boolean x5FileSuccess;

    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u009b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00102\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/yddmi/doctor/pages/web/WebActivity$Companion;", "", "()V", "TAG", "", "startWebActivity", "", "mContext", "Landroid/content/Context;", "loadUri", "fileUri", "title", "rightActionString", "rightActionDrawableId", "", "showTitle", "", "showTitleLine", "showLoadProgress", "trainId", "patientId", "categoryId", "id", "", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;ZZZLjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;J)V", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startWebActivity(@NotNull Context mContext, @Nullable String loadUri, @Nullable String fileUri, @Nullable String title, @Nullable String rightActionString, @Nullable Integer rightActionDrawableId, boolean showTitle, boolean showTitleLine, boolean showLoadProgress, @Nullable Integer trainId, @Nullable Integer patientId, @Nullable Integer categoryId, long id) {
            Intrinsics.checkNotNullParameter(mContext, "mContext");
            Intent intent = new Intent(mContext, (Class<?>) WebActivity.class);
            intent.putExtra("loadUri", loadUri);
            intent.putExtra("fileUri", fileUri);
            intent.putExtra("title", title);
            intent.putExtra("rightActionString", rightActionString);
            intent.putExtra("rightActionDrawableId", rightActionDrawableId);
            intent.putExtra("showTitleBar", showTitle);
            intent.putExtra("showTitleBarLine", showTitleLine);
            intent.putExtra("showLoadProgress", showLoadProgress);
            intent.putExtra("trainId", trainId);
            intent.putExtra("patientId", patientId);
            intent.putExtra("categoryId", categoryId);
            intent.putExtra("id", id);
            mContext.startActivity(intent);
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.web.WebActivity$initFlow$1", f = "WebActivity.kt", i = {}, l = {145}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.web.WebActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08971 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08971(Continuation<? super C08971> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return WebActivity.this.new C08971(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08971) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> loadingHideChangeMsf = WebActivity.this.getViewModel().getLoadingHideChangeMsf();
                final WebActivity webActivity = WebActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.web.WebActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            webActivity.hideLoading();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (loadingHideChangeMsf.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.web.WebActivity$initFlow$2", f = "WebActivity.kt", i = {}, l = {152}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.web.WebActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C08982 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08982(Continuation<? super C08982> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return WebActivity.this.new C08982(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08982) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<String> fileDownPathChangeMsf = WebActivity.this.getViewModel().getFileDownPathChangeMsf();
                final WebActivity webActivity = WebActivity.this;
                FlowCollector<? super String> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.web.WebActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((String) obj2, (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(@NotNull String str, @NotNull Continuation<? super Unit> continuation) {
                        webActivity.hideLoading();
                        if (!(str == null || str.length() == 0)) {
                            webActivity.loadAppFile(str);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (fileDownPathChangeMsf.collect(flowCollector, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.web.WebActivity$loadAppFile$1", f = "WebActivity.kt", i = {}, l = {R2.attr.alertDialogTheme}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.web.WebActivity$loadAppFile$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08991 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $path;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08991(String str, Continuation<? super C08991> continuation) {
            super(2, continuation);
            this.$path = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08991(this.$path, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08991) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                OtherUtils otherUtils = OtherUtils.INSTANCE;
                String str = this.$path;
                this.label = 1;
                if (otherUtils.openFile(str, this) == coroutine_suspended) {
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

    private final void dealInitX5File() {
        TbsFileInterfaceImpl.setLicense(Config.X5_FILE_LICENSE_KEY);
        this.x5FileSuccess = TbsFileInterfaceImpl.initEngine(getApplicationContext()) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealOpenWxService() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (companion.getInstance().getmWxApi().getWXAppSupportAPI() < 671090490) {
            Toaster.show(R.string.me_set_wx_tip1);
            return;
        }
        WXOpenCustomerServiceChat.Req req = new WXOpenCustomerServiceChat.Req();
        req.corpId = "ww60996ae31a291c9b";
        req.url = "https://work.weixin.qq.com/kfid/kfc241775a09d6dfaab";
        companion.getInstance().getmWxApi().sendReq(req);
    }

    private final void httpGetClinicalGuidelineDetail() {
        if (-1 != getViewModel().getCategoryId()) {
            FlowExtKt.lifecycleLoadingView(getViewModel().getClinicalGuidelineDetail(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.web.WebActivity.httpGetClinicalGuidelineDetail.1
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
                    WebActivity.this.hideLoading();
                }
            }, new Function1<HomeClinicalDetail, Unit>() { // from class: com.yddmi.doctor.pages.web.WebActivity.httpGetClinicalGuidelineDetail.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(HomeClinicalDetail homeClinicalDetail) {
                    invoke2(homeClinicalDetail);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable HomeClinicalDetail homeClinicalDetail) {
                    WebActivity.this.loadWebHtml(homeClinicalDetail != null ? homeClinicalDetail.getContent() : null);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadAppFile(String path) {
        if ((path == null || path.length() == 0) || !FileUtils.isFile(path) || StringsKt__StringsKt.indexOf$default((CharSequence) path, StrPool.DOT, 0, false, 6, (Object) null) < 0) {
            Toaster.show(R.string.common_file_no);
        } else {
            BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, null, null, new C08991(path, null), 3, null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void loadWebCall() {
        if (this.jsCallNativeApi == null) {
            this.jsCallNativeApi = new JsCallNativeApi();
        }
        DWebView dWebView = ((ActivityWebBinding) getBodyBinding()).web;
        JsCallNativeApi jsCallNativeApi = this.jsCallNativeApi;
        JsCallNativeApi jsCallNativeApi2 = null;
        if (jsCallNativeApi == null) {
            Intrinsics.throwUninitializedPropertyAccessException("jsCallNativeApi");
            jsCallNativeApi = null;
        }
        dWebView.addJavascriptObject(jsCallNativeApi, "WebAction");
        JsCallNativeApi jsCallNativeApi3 = this.jsCallNativeApi;
        if (jsCallNativeApi3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("jsCallNativeApi");
        } else {
            jsCallNativeApi2 = jsCallNativeApi3;
        }
        jsCallNativeApi2.setOnJsCallListener(new JsCallNativeApi.OnJsCallListener() { // from class: com.yddmi.doctor.pages.web.WebActivity.loadWebCall.2
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
             */
            @Override // com.yddmi.doctor.pages.web.JsCallNativeApi.OnJsCallListener
            public void onJsCall(@NotNull String action, @Nullable Object data) {
                Intrinsics.checkNotNullParameter(action, "action");
                switch (action.hashCode()) {
                    case 1092817468:
                        if (action.equals("closeWeb")) {
                            WebActivity.this.closeActivity();
                            return;
                        }
                        break;
                    case 1386804305:
                        if (action.equals("getAppParams")) {
                            LogExtKt.logd("getAppParams() js调原生", WebActivity.TAG);
                            WebActivity.this.nativeCallJs();
                            return;
                        }
                        break;
                    case 1822190954:
                        if (action.equals("openWxService")) {
                            WebActivity.this.dealOpenWxService();
                            return;
                        }
                        break;
                    case 2105198265:
                        if (action.equals("hangupPayment")) {
                            LogExtKt.logd("hangupPayment() js调原生", WebActivity.TAG);
                            BusUtils.post(GlobalAction.eventHomeGoBuyAll);
                            WebActivity.this.closeActivity();
                            return;
                        }
                        break;
                }
                LogExtKt.logd("web调原生不支持的方法", WebActivity.TAG);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void loadWebFile(String path) {
        if (!TbsFileInterfaceImpl.isEngineLoaded()) {
            dealInitX5File();
        }
        if ((path == null || path.length() == 0) || !FileUtils.isFile(path) || !this.x5FileSuccess || StringsKt__StringsKt.indexOf$default((CharSequence) path, StrPool.DOT, 0, false, 6, (Object) null) < 0) {
            Toaster.show(R.string.common_file_no);
            return;
        }
        ((ActivityWebBinding) getBodyBinding()).fl.setVisibility(0);
        Bundle bundle = new Bundle();
        bundle.putString(TbsReaderView.KEY_FILE_PATH, path);
        String strSubstring = path.substring(StringsKt__StringsKt.lastIndexOf$default((CharSequence) path, StrPool.DOT, 0, false, 6, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
        LogExtKt.logd("文件路径： " + path + " 文件类型：" + strSubstring + " ", TAG);
        bundle.putString("fileExt", strSubstring);
        File externalFilesDir = getExternalFilesDir("temp");
        Intrinsics.checkNotNull(externalFilesDir);
        bundle.putString(TbsReaderView.KEY_TEMP_PATH, externalFilesDir.getAbsolutePath());
        if (!TbsFileInterfaceImpl.canOpenFileExt(strSubstring)) {
            Toaster.show(R.string.common_file_cant);
            return;
        }
        int iOpenFileReader = TbsFileInterfaceImpl.getInstance().openFileReader(this, bundle, null, ((ActivityWebBinding) getBodyBinding()).fl);
        if (iOpenFileReader != 0) {
            LogExtKt.logd("x5文件浏览失败 openFileReader失败, ret = " + iOpenFileReader, TAG);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadWebHtml(final String html) {
        if (html == null || html.length() == 0) {
            return;
        }
        bodyBinding(new Function1<ActivityWebBinding, Unit>() { // from class: com.yddmi.doctor.pages.web.WebActivity.loadWebHtml.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ActivityWebBinding activityWebBinding) {
                invoke2(activityWebBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ActivityWebBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.web.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                bodyBinding.web.setOnDWebViewListener(this);
            }
        });
    }

    private final void loadWebUri() {
        LogExtKt.logd("WebActivity地址加载  " + getViewModel().getLoadUri(), YddConfig.TAG);
        String loadUri = getViewModel().getLoadUri();
        if (loadUri == null || loadUri.length() == 0) {
            return;
        }
        bodyBinding(new Function1<ActivityWebBinding, Unit>() { // from class: com.yddmi.doctor.pages.web.WebActivity.loadWebUri.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ActivityWebBinding activityWebBinding) {
                invoke2(activityWebBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ActivityWebBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.web.loadUrl(WebActivity.this.getViewModel().getLoadUri());
                bodyBinding.web.setOnDWebViewListener(WebActivity.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void nativeCallJs() {
        JsonObject jsonObject = new JsonObject();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        jsonObject.addProperty("token", companion.getInstance().userToken());
        jsonObject.addProperty("userId", Integer.valueOf(companion.getInstance().userId()));
        jsonObject.addProperty(SocialConstants.PARAM_TYPE_ID, Long.valueOf(getViewModel().getId()));
        jsonObject.addProperty("currentHostType", Integer.valueOf(YddHostConfig.INSTANCE.getInstance().getCurrentHostNumber()));
        jsonObject.addProperty("os", "android");
        jsonObject.addProperty("typeFlag", "SJJN");
        jsonObject.addProperty("platformType", (Number) 1);
        jsonObject.addProperty("recordType", (Number) 2);
        jsonObject.addProperty(AliyunLogCommon.TERMINAL_TYPE, companion.getInstance().userPhone());
        LogExtKt.logd("nativeCallJs u3d参数 竖屏h5加载：" + jsonObject, TAG);
        ((ActivityWebBinding) getBodyBinding()).web.callHandler("getU3dParams", new String[]{jsonObject.toString()}, new OnReturnValue() { // from class: com.yddmi.doctor.pages.web.a
            @Override // com.catchpig.mvvm.widget.dsbridge.OnReturnValue
            public final void onValue(Object obj) {
                WebActivity.nativeCallJs$lambda$2(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void nativeCallJs$lambda$2(Object obj) {
        LogExtKt.logd("原生调用js返回：" + obj, TAG);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void releaseWeb() {
        DWebView dWebView = ((ActivityWebBinding) getBodyBinding()).web;
        if (dWebView != null) {
            dWebView.loadUrl("about:blank");
        }
        DWebView dWebView2 = ((ActivityWebBinding) getBodyBinding()).web;
        if (dWebView2 != null) {
            dWebView2.stopLoading();
        }
        DWebView dWebView3 = ((ActivityWebBinding) getBodyBinding()).web;
        if (dWebView3 != null) {
            dWebView3.destroy();
        }
        DWebView dWebView4 = ((ActivityWebBinding) getBodyBinding()).web;
        if (dWebView4 != null) {
            dWebView4.setWebViewClient(null);
        }
        DWebView dWebView5 = ((ActivityWebBinding) getBodyBinding()).web;
        if (dWebView5 == null) {
            return;
        }
        dWebView5.setWebChromeClient(null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08971(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08982(null), 3, null);
        httpGetClinicalGuidelineDetail();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_white);
        immersionBarWith.init();
        immersionBarWith.init();
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra("loadUri");
            if (stringExtra == null) {
                stringExtra = "";
            }
            Intrinsics.checkNotNullExpressionValue(stringExtra, "it.getStringExtra(\"loadUri\") ?: \"\"");
            if (!(stringExtra.length() == 0) && StringsKt__StringsJVMKt.startsWith$default(stringExtra, "www", false, 2, null)) {
                stringExtra = DefaultWebClient.HTTP_SCHEME + stringExtra;
            }
            getViewModel().setLoadUri(stringExtra);
            String stringExtra2 = intent.getStringExtra("fileUri");
            if (stringExtra2 == null) {
                stringExtra2 = "";
            }
            Intrinsics.checkNotNullExpressionValue(stringExtra2, "it.getStringExtra(\"fileUri\") ?: \"\"");
            if (!(stringExtra2.length() == 0) && StringsKt__StringsJVMKt.startsWith$default(stringExtra2, "www", false, 2, null)) {
                stringExtra2 = DefaultWebClient.HTTP_SCHEME + stringExtra2;
            }
            getViewModel().setFileUri(YddHostConfig.INSTANCE.getInstance().getFileFullUrl(stringExtra2));
            String fileUri = getViewModel().getFileUri();
            if (!(fileUri == null || fileUri.length() == 0)) {
                loadingDialog();
                dealInitX5File();
                getViewModel().httpDownLoadFile(getViewModel().getFileUri());
            }
            WebViewModel viewModel = getViewModel();
            String stringExtra3 = intent.getStringExtra("title");
            if (stringExtra3 == null) {
                stringExtra3 = "";
            }
            viewModel.setTitle(stringExtra3);
            WebViewModel viewModel2 = getViewModel();
            String stringExtra4 = intent.getStringExtra("rightActionString");
            viewModel2.setRightActionString(stringExtra4 != null ? stringExtra4 : "");
            getViewModel().setRightActionDrawableId(intent.getIntExtra("rightActionDrawableId", 0));
            getViewModel().setShowTitleBar(intent.getBooleanExtra("showTitleBar", true));
            getViewModel().setShowTitleBarLine(intent.getBooleanExtra("showTitleBarLine", true));
            getViewModel().setShowLoadProgress(intent.getBooleanExtra("showLoadProgress", true));
            getViewModel().setTrainId(intent.getIntExtra("trainId", -1));
            getViewModel().setPatientId(intent.getIntExtra("patientId", -1));
            getViewModel().setCategoryId(intent.getIntExtra("categoryId", -1));
            getViewModel().setId(intent.getLongExtra("id", -1L));
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        if (getViewModel().getShowTitleBar()) {
            String title = getViewModel().getTitle();
            boolean z2 = true;
            if (!(title == null || title.length() == 0)) {
                String title2 = getViewModel().getTitle();
                Intrinsics.checkNotNull(title2);
                updateTitle(title2);
            }
            String rightActionString = getViewModel().getRightActionString();
            if (rightActionString != null && rightActionString.length() != 0) {
                z2 = false;
            }
            if (!z2) {
                updateTitleRightFirst(getViewModel().getRightActionString());
            }
            hideShowTitleBar(0);
        } else {
            hideShowTitleBar(8);
        }
        loadWebCall();
        loadWebUri();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (((ActivityWebBinding) getBodyBinding()).web.canGoBack()) {
            ((ActivityWebBinding) getBodyBinding()).web.goBack();
        } else {
            closeActivity();
        }
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        releaseWeb();
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onPageFinished(@Nullable DWebView webView, @Nullable String url) {
        ((ActivityWebBinding) getBodyBinding()).webPb.setVisibility(8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onPageStarted(@Nullable DWebView webView, @Nullable String url, @Nullable Bitmap bitmap) {
        ((ActivityWebBinding) getBodyBinding()).webPb.setVisibility(0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onProgressChanged(@Nullable DWebView view, int newProgress) {
        if (getViewModel().getShowLoadProgress()) {
            ((ActivityWebBinding) getBodyBinding()).webPb.setProgress(newProgress);
        }
    }

    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onReceivedTitle(@Nullable DWebView view, @Nullable String title) {
        String title2 = getViewModel().getTitle();
        if (title2 == null || title2.length() == 0) {
            if (title == null || title.length() == 0) {
                return;
            }
            updateTitle(title);
        }
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
