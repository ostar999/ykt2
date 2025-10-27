package com.yddmi.doctor.pages.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.widget.dsbridge.DWebView;
import com.catchpig.mvvm.widget.dsbridge.OnReturnValue;
import com.catchpig.utils.ext.LogExtKt;
import com.google.gson.JsonObject;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.just.agentweb.DefaultWebClient;
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat;
import com.tencent.open.SocialConstants;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.ActivityWebLandscapeBinding;
import com.yddmi.doctor.pages.web.JsCallNativeApi;
import com.yddmi.doctor.pages.web.WebLandscapeActivity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u0000 *2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004:\u0001*B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\rH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016J\b\u0010\u0011\u001a\u00020\rH\u0002J\u0012\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\b\u0010\u0015\u001a\u00020\rH\u0002J\b\u0010\u0016\u001a\u00020\rH\u0002J\b\u0010\u0017\u001a\u00020\rH\u0016J\b\u0010\u0018\u001a\u00020\rH\u0014J\u001c\u0010\u0019\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0014H\u0016J&\u0010\u001d\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u001a\u0010 \u001a\u00020\r2\b\u0010!\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\"\u001a\u00020\u000bH\u0016J\u001c\u0010#\u001a\u00020\r2\b\u0010!\u001a\u0004\u0018\u00010\u001b2\b\u0010$\u001a\u0004\u0018\u00010\u0014H\u0016J\u0010\u0010%\u001a\u00020\r2\u0006\u0010&\u001a\u00020\tH\u0016J\b\u0010'\u001a\u00020\rH\u0002J\b\u0010(\u001a\u00020\rH\u0016J\b\u0010)\u001a\u00020\rH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/yddmi/doctor/pages/web/WebLandscapeActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/ActivityWebLandscapeBinding;", "Lcom/yddmi/doctor/pages/web/WebLandscapeViewModel;", "Lcom/catchpig/mvvm/widget/dsbridge/DWebView$OnDWebViewListener;", "()V", "jsCallNativeApi", "Lcom/yddmi/doctor/pages/web/JsCallNativeApi;", "mIsExam", "", "mIsPlay", "", "dealOpenWxService", "", "initFlow", "initParam", "initView", "loadWebCall", "loadWebHtml", "html", "", "loadWebUri", "nativeCallJs", "onBackPressed", "onDestroy", "onPageFinished", "webView", "Lcom/catchpig/mvvm/widget/dsbridge/DWebView;", "url", "onPageStarted", "bitmap", "Landroid/graphics/Bitmap;", "onProgressChanged", "view", "newProgress", "onReceivedTitle", "title", "onWindowFocusChanged", "hasFocus", "releaseWeb", "screenOrientation", "viewSetImmersionBar", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true, transparent = true)
@SourceDebugExtension({"SMAP\nWebLandscapeActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WebLandscapeActivity.kt\ncom/yddmi/doctor/pages/web/WebLandscapeActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,254:1\n18#2,2:255\n1#3:257\n*S KotlinDebug\n*F\n+ 1 WebLandscapeActivity.kt\ncom/yddmi/doctor/pages/web/WebLandscapeActivity\n*L\n117#1:255,2\n117#1:257\n*E\n"})
/* loaded from: classes6.dex */
public final class WebLandscapeActivity extends BaseVMActivity<ActivityWebLandscapeBinding, WebLandscapeViewModel> implements DWebView.OnDWebViewListener {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final String TAG = "WebLandscapeActivity";
    private JsCallNativeApi jsCallNativeApi;
    private boolean mIsExam;
    private int mIsPlay;

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/web/WebLandscapeActivity$Companion;", "", "()V", "TAG", "", "startWebActivity", "", "mContext", "Landroid/content/Context;", "loadUri", "id", "isPlay", "", "isExam", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startWebActivity(@NotNull Context mContext, @Nullable String loadUri, @Nullable String id, int isPlay, boolean isExam) {
            Intrinsics.checkNotNullParameter(mContext, "mContext");
            Intent intent = new Intent(mContext, (Class<?>) WebLandscapeActivity.class);
            intent.putExtra("loadUri", loadUri);
            intent.putExtra("id", id);
            intent.putExtra("isPlay", isPlay);
            intent.putExtra("isExam", isExam);
            mContext.startActivity(intent);
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/ActivityWebLandscapeBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.web.WebLandscapeActivity$loadWebUri$1, reason: invalid class name and case insensitive filesystem */
    public static final class C09031 extends Lambda implements Function1<ActivityWebLandscapeBinding, Unit> {
        public C09031() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(WebLandscapeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ActivityWebLandscapeBinding activityWebLandscapeBinding) {
            invoke2(activityWebLandscapeBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull ActivityWebLandscapeBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final WebLandscapeActivity webLandscapeActivity = WebLandscapeActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.web.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WebLandscapeActivity.C09031.invoke$lambda$0(webLandscapeActivity, view);
                }
            }, 0L, 2, null);
            bodyBinding.web.setScrollContainer(false);
            bodyBinding.web.loadUrl(WebLandscapeActivity.this.getViewModel().getLoadUri());
            bodyBinding.web.setOnDWebViewListener(WebLandscapeActivity.this);
        }
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

    /* JADX WARN: Multi-variable type inference failed */
    private final void loadWebCall() {
        if (this.jsCallNativeApi == null) {
            this.jsCallNativeApi = new JsCallNativeApi();
        }
        DWebView dWebView = ((ActivityWebLandscapeBinding) getBodyBinding()).web;
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
        jsCallNativeApi2.setOnJsCallListener(new JsCallNativeApi.OnJsCallListener() { // from class: com.yddmi.doctor.pages.web.WebLandscapeActivity.loadWebCall.2
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
                            WebLandscapeActivity.this.closeActivity();
                            return;
                        }
                        break;
                    case 1386804305:
                        if (action.equals("getAppParams")) {
                            LogExtKt.logd("getAppParams() js调原生", WebLandscapeActivity.TAG);
                            WebLandscapeActivity.this.nativeCallJs();
                            return;
                        }
                        break;
                    case 1822190954:
                        if (action.equals("openWxService")) {
                            WebLandscapeActivity.this.dealOpenWxService();
                            return;
                        }
                        break;
                    case 2105198265:
                        if (action.equals("hangupPayment")) {
                            LogExtKt.logd("hangupPayment() js调原生", WebLandscapeActivity.TAG);
                            BusUtils.post(GlobalAction.eventHomeGoBuyAll);
                            WebLandscapeActivity.this.closeActivity();
                            return;
                        }
                        break;
                }
                LogExtKt.logd("web调原生不支持的方法", WebLandscapeActivity.TAG);
            }
        });
    }

    private final void loadWebHtml(final String html) {
        if (html == null || html.length() == 0) {
            return;
        }
        bodyBinding(new Function1<ActivityWebLandscapeBinding, Unit>() { // from class: com.yddmi.doctor.pages.web.WebLandscapeActivity.loadWebHtml.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ActivityWebLandscapeBinding activityWebLandscapeBinding) {
                invoke2(activityWebLandscapeBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ActivityWebLandscapeBinding bodyBinding) {
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
        bodyBinding(new C09031());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void nativeCallJs() {
        JsonObject jsonObject = new JsonObject();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        jsonObject.addProperty("token", companion.getInstance().userToken());
        jsonObject.addProperty("userId", Integer.valueOf(companion.getInstance().userId()));
        jsonObject.addProperty(SocialConstants.PARAM_TYPE_ID, getViewModel().getId());
        jsonObject.addProperty("isPlay", Integer.valueOf(this.mIsPlay));
        jsonObject.addProperty("isExam", Boolean.valueOf(this.mIsExam));
        jsonObject.addProperty("currentHostType", Integer.valueOf(YddHostConfig.INSTANCE.getInstance().getCurrentHostNumber()));
        jsonObject.addProperty("os", "android");
        jsonObject.addProperty("typeFlag", "SJJN");
        jsonObject.addProperty("platformType", (Number) 1);
        jsonObject.addProperty("recordType", (Number) 2);
        jsonObject.addProperty(AliyunLogCommon.TERMINAL_TYPE, companion.getInstance().userPhone());
        LogExtKt.logd("nativeCallJs u3d参数 h5加载：" + jsonObject, TAG);
        ((ActivityWebLandscapeBinding) getBodyBinding()).web.callHandler("getU3dParams", new String[]{jsonObject.toString()}, new OnReturnValue() { // from class: com.yddmi.doctor.pages.web.c
            @Override // com.catchpig.mvvm.widget.dsbridge.OnReturnValue
            public final void onValue(Object obj) {
                WebLandscapeActivity.nativeCallJs$lambda$2(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void nativeCallJs$lambda$2(Object obj) {
        LogExtKt.logd("原生调用js返回：" + obj, TAG);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void releaseWeb() {
        ((ActivityWebLandscapeBinding) getBodyBinding()).web.loadUrl("about:blank");
        ((ActivityWebLandscapeBinding) getBodyBinding()).web.stopLoading();
        ((ActivityWebLandscapeBinding) getBodyBinding()).web.destroy();
        ((ActivityWebLandscapeBinding) getBodyBinding()).web.setWebViewClient(null);
        ((ActivityWebLandscapeBinding) getBodyBinding()).web.setWebChromeClient(null);
    }

    private final void viewSetImmersionBar() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.transparentBar();
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_BAR);
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_transparent);
        immersionBarWith.init();
        immersionBarWith.init();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        viewSetImmersionBar();
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
            this.mIsPlay = intent.getIntExtra("isPlay", 0);
            this.mIsExam = intent.getBooleanExtra("isExam", false);
            getViewModel().setLoadUri(stringExtra);
            getViewModel().setId(intent.getStringExtra("id"));
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        updateTitleLine(false);
        updateStatusBarTransparent();
        loadWebCall();
        loadWebUri();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (((ActivityWebLandscapeBinding) getBodyBinding()).web.canGoBack()) {
            ((ActivityWebLandscapeBinding) getBodyBinding()).web.goBack();
        } else {
            closeActivity();
        }
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        releaseWeb();
        super.onDestroy();
    }

    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onPageFinished(@Nullable DWebView webView, @Nullable String url) {
    }

    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onPageStarted(@Nullable DWebView webView, @Nullable String url, @Nullable Bitmap bitmap) {
    }

    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onProgressChanged(@Nullable DWebView view, int newProgress) {
    }

    @Override // com.catchpig.mvvm.widget.dsbridge.DWebView.OnDWebViewListener
    public void onReceivedTitle(@Nullable DWebView view, @Nullable String title) {
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LogExtKt.logd("焦点切换 onWindowFocusChanged " + hasFocus, TAG);
        if (!hasFocus || Build.VERSION.SDK_INT < 30) {
            return;
        }
        viewSetImmersionBar();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(6);
    }
}
