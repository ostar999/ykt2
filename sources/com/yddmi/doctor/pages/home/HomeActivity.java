package com.yddmi.doctor.pages.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LifecycleOwnerKt;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.azhon.appupdate.config.Constant;
import com.azhon.appupdate.manager.DownloadManager;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.mvvm.utils.GsonUtil;
import com.catchpig.mvvm.utils.NoDoubleClickUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.petterp.floatingx.FloatingX;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.HomeActivityBinding;
import com.yddmi.doctor.entity.request.IntegralAcquireReq;
import com.yddmi.doctor.entity.request.ShengYunSetsBean;
import com.yddmi.doctor.entity.result.AppVersion;
import com.yddmi.doctor.entity.result.AuthLoginResult;
import com.yddmi.doctor.entity.result.AuthLoginResult1;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.pages.exam.ExamActivity;
import com.yddmi.doctor.pages.home.HomeActivity;
import com.yddmi.doctor.pages.main.MainActivity;
import com.yddmi.doctor.pages.main.MsgDetailActivity;
import com.yddmi.doctor.pages.main.PopupAd;
import com.yddmi.doctor.pages.main.PopupCallMe;
import com.yddmi.doctor.pages.main.PopupCode;
import com.yddmi.doctor.pages.main.PopupInviteCode;
import com.yddmi.doctor.pages.main.PopupMsg;
import com.yddmi.doctor.pages.main.PopupRule;
import com.yddmi.doctor.pages.me.MeActivity;
import com.yddmi.doctor.pages.physical.PhysicalActivity;
import com.yddmi.doctor.pages.product.ProductActivity;
import com.yddmi.doctor.pages.set.FeedbackActivity;
import com.yddmi.doctor.pages.set.PopupWxQr;
import com.yddmi.doctor.pages.web.WebNoTitleBarActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.FucUtil;
import com.yddmi.doctor.utils.OtherUtils;
import com.yddmi.doctor.widget.PopupCommonDialog;
import com.yddmi.doctor.widget.PopupTipDialog;
import com.yikaobang.yixue.R2;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.KotlinNothingValueException;
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
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b)*\u0002\u00123\b\u0007\u0018\u0000 \u0086\u00012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0002\u0086\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u001c\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\n\b\u0002\u00109\u001a\u0004\u0018\u00010:H\u0002J\b\u0010;\u001a\u000206H\u0002J\b\u0010<\u001a\u000206H\u0002J\b\u0010=\u001a\u000206H\u0002J\u001e\u0010>\u001a\u0002062\n\b\u0002\u00107\u001a\u0004\u0018\u0001082\b\b\u0002\u0010?\u001a\u00020@H\u0002J\b\u0010A\u001a\u000206H\u0002J\b\u0010B\u001a\u000206H\u0002J\b\u0010C\u001a\u000206H\u0007J\b\u0010D\u001a\u000206H\u0007J\b\u0010E\u001a\u000206H\u0007J\b\u0010F\u001a\u000206H\u0007J\b\u0010G\u001a\u000206H\u0007J\b\u0010H\u001a\u000206H\u0007J\b\u0010I\u001a\u000206H\u0007J\b\u0010J\u001a\u000206H\u0007J\b\u0010K\u001a\u000206H\u0007J\b\u0010L\u001a\u000206H\u0007J\b\u0010M\u001a\u000206H\u0007J\b\u0010N\u001a\u000206H\u0007J\b\u0010O\u001a\u000206H\u0002J\u0010\u0010P\u001a\u0002062\u0006\u0010Q\u001a\u00020\u0015H\u0002J\b\u0010R\u001a\u000206H\u0002J\b\u0010S\u001a\u000206H\u0002J\b\u0010T\u001a\u000206H\u0002J\u0010\u0010T\u001a\u0002062\u0006\u0010U\u001a\u00020VH\u0002J\b\u0010W\u001a\u000206H\u0002J\u0012\u0010X\u001a\u0002062\b\u0010Y\u001a\u0004\u0018\u00010\u0015H\u0002J\b\u0010Z\u001a\u000206H\u0002J\b\u0010[\u001a\u000206H\u0002J\u0010\u0010\\\u001a\u0002062\u0006\u0010]\u001a\u00020^H\u0002J\b\u0010_\u001a\u000206H\u0002J \u0010`\u001a\u0002062\u0006\u0010a\u001a\u00020\u00152\u0006\u0010b\u001a\u00020\u00152\u0006\u0010c\u001a\u00020\u0015H\u0002J\b\u0010d\u001a\u000206H\u0002J\u0010\u0010e\u001a\u0002062\u0006\u0010f\u001a\u000208H\u0002J\u0010\u0010g\u001a\u0002062\u0006\u0010b\u001a\u00020\u0015H\u0002J\u0010\u0010h\u001a\u0002062\u0006\u0010i\u001a\u000208H\u0002J\b\u0010j\u001a\u000206H\u0016J\b\u0010k\u001a\u000206H\u0016J\b\u0010l\u001a\u000206H\u0016J\b\u0010m\u001a\u000206H\u0014J\b\u0010n\u001a\u000206H\u0014J\b\u0010o\u001a\u000206H\u0014J\b\u0010p\u001a\u000206H\u0014J\u0010\u0010q\u001a\u0002062\u0006\u0010r\u001a\u00020@H\u0016J\b\u0010s\u001a\u000206H\u0002J\b\u0010t\u001a\u000206H\u0002J0\u0010u\u001a\u0002062\b\u00107\u001a\u0004\u0018\u0001082\b\b\u0002\u0010v\u001a\u00020@2\b\b\u0002\u0010w\u001a\u00020@2\b\b\u0002\u0010x\u001a\u00020@H\u0002J\b\u0010y\u001a\u000206H\u0002J\b\u0010z\u001a\u000206H\u0002J\b\u0010{\u001a\u000206H\u0002J\u0010\u0010|\u001a\u0002062\u0006\u0010}\u001a\u000208H\u0002J\u0012\u0010~\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108H\u0002J\u0012\u0010\u007f\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108H\u0002J\t\u0010\u0080\u0001\u001a\u000206H\u0002J\t\u0010\u0081\u0001\u001a\u000206H\u0002J\t\u0010\u0082\u0001\u001a\u000206H\u0002J\t\u0010\u0083\u0001\u001a\u000206H\u0002J\u0012\u0010\u0084\u0001\u001a\u0002062\u0007\u0010\u0085\u0001\u001a\u00020\u0015H\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0010\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0010\u00100\u001a\u0004\u0018\u000101X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u000203X\u0082\u0004¢\u0006\u0004\n\u0002\u00104¨\u0006\u0087\u0001"}, d2 = {"Lcom/yddmi/doctor/pages/home/HomeActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/HomeActivityBinding;", "Lcom/yddmi/doctor/pages/home/HomeViewModel;", "()V", "finalPopup", "Lcom/yddmi/doctor/widget/PopupCommonDialog;", "getFinalPopup", "()Lcom/yddmi/doctor/widget/PopupCommonDialog;", "setFinalPopup", "(Lcom/yddmi/doctor/widget/PopupCommonDialog;)V", "mForegroundListener", "Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl$Listener;", "getMForegroundListener", "()Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl$Listener;", "setMForegroundListener", "(Lcom/catchpig/mvvm/lifecycle/ActivityLifeCycleCallbacksImpl$Listener;)V", "mHandler", "com/yddmi/doctor/pages/home/HomeActivity$mHandler$1", "Lcom/yddmi/doctor/pages/home/HomeActivity$mHandler$1;", "mPhoneNumber", "", "mShowType", "", "popupAd", "Lcom/yddmi/doctor/pages/main/PopupAd;", "getPopupAd", "()Lcom/yddmi/doctor/pages/main/PopupAd;", "setPopupAd", "(Lcom/yddmi/doctor/pages/main/PopupAd;)V", "popupCode", "Lcom/yddmi/doctor/pages/main/PopupCode;", "getPopupCode", "()Lcom/yddmi/doctor/pages/main/PopupCode;", "setPopupCode", "(Lcom/yddmi/doctor/pages/main/PopupCode;)V", "popupDialog", "Lcom/yddmi/doctor/widget/PopupTipDialog;", "getPopupDialog", "()Lcom/yddmi/doctor/widget/PopupTipDialog;", "setPopupDialog", "(Lcom/yddmi/doctor/widget/PopupTipDialog;)V", "popupInviteCode", "Lcom/yddmi/doctor/pages/main/PopupInviteCode;", "getPopupInviteCode", "()Lcom/yddmi/doctor/pages/main/PopupInviteCode;", "setPopupInviteCode", "(Lcom/yddmi/doctor/pages/main/PopupInviteCode;)V", "shengYunSetsDataBean", "Lcom/yddmi/doctor/entity/request/ShengYunSetsBean$DataBean;", "shengyunReceiver", "com/yddmi/doctor/pages/home/HomeActivity$shengyunReceiver$1", "Lcom/yddmi/doctor/pages/home/HomeActivity$shengyunReceiver$1;", "dealAdClick", "", am.aw, "Lcom/yddmi/doctor/entity/result/HomeMsg;", "view", "Landroid/view/View;", "dealClearWebCache", "dealForegroundListener", "dealGet", "dealGoBuyAll", "buy24", "", "dealStartAdCache", "dealWxLogin", "event401Deal", GlobalAction.eventCaptureFeedback, GlobalAction.eventCaptureWx, GlobalAction.eventHomeAdFromPush, GlobalAction.eventHomeGoBuyAll, "eventLoginDeal", "eventLogoffDeal", "eventLogoutDeal", GlobalAction.eventMsgNum, GlobalAction.eventPointSave, "eventTpshBind", "eventTpshUnBind", "getRulelatest", "httpAliOneClickGo", "token", "httpGetAppVersion", "httpGetAppWarn", "httpGetPersonInfo", "loginResult", "Lcom/yddmi/doctor/entity/result/AuthLoginResult;", "httpGetVersionU3d", "httpPostAgentLogin", "phoneNumber", "httpPostCouponRecord", "httpPostCouponRecord2", "httpPostIntegralAcquire", "data", "Lcom/yddmi/doctor/entity/result/SkillHome;", "httpPostIntegralFreeReceive", "httpPostInviteLogin", AliyunLogCommon.TERMINAL_TYPE, "code", "inviteCode", "httpPostInviterSave", "httpPostPopUserUpdateStatus", "msg", "httpPostRedeemCode", "httpReadNoticeMessageList", "m", "initFlow", "initParam", "initView", "onDestroy", "onResume", "onStart", "onStop", "onWindowFocusChanged", "hasFocus", "requestShengYunGoods", "viewCodeStatus", "viewPopAd", "oneDayShow", "get104", "first", "viewPopCallMe", "viewPopCode", "viewPopInviteCode", "viewPopMsg", "msgData", "viewPopRecommendAd", "viewPopRecommendQrAd", "viewPopRule", "viewPopWxQr", "viewSetImmersionBar", "viewShow401Popup", "viewShowTipPop", "str", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nHomeActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeActivity.kt\ncom/yddmi/doctor/pages/home/HomeActivity\n+ 2 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n+ 3 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1306:1\n11#2,7:1307\n11#2,7:1314\n15#2,3:1321\n15#2,3:1324\n15#2,3:1327\n15#2,3:1330\n18#3,2:1333\n1#4:1335\n*S KotlinDebug\n*F\n+ 1 HomeActivity.kt\ncom/yddmi/doctor/pages/home/HomeActivity\n*L\n442#1:1307,7\n484#1:1314,7\n611#1:1321,3\n619#1:1324,3\n627#1:1327,3\n667#1:1330,3\n685#1:1333,2\n685#1:1335\n*E\n"})
/* loaded from: classes6.dex */
public final class HomeActivity extends BaseVMActivity<HomeActivityBinding, HomeViewModel> {

    @NotNull
    private static final String TAG = "HomeActivity";
    public PopupCommonDialog finalPopup;

    @Nullable
    private ActivityLifeCycleCallbacksImpl.Listener mForegroundListener;

    @NotNull
    private final HomeActivity$mHandler$1 mHandler;

    @Nullable
    private String mPhoneNumber = "";
    private int mShowType;
    public PopupAd popupAd;

    @Nullable
    private PopupCode popupCode;
    public PopupTipDialog popupDialog;

    @Nullable
    private PopupInviteCode popupInviteCode;

    @Nullable
    private ShengYunSetsBean.DataBean shengYunSetsDataBean;

    @NotNull
    private final HomeActivity$shengyunReceiver$1 shengyunReceiver;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeActivity$initFlow$1", f = "HomeActivity.kt", i = {}, l = {R2.attr.arcTickSplitAngle}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07141 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07141(Continuation<? super C07141> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeActivity.this.new C07141(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07141) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> noticeMsf = HomeActivity.this.getViewModel().getNoticeMsf();
                final HomeActivity homeActivity = HomeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.home.HomeActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0 && homeActivity.getViewModel().getMMsgData() != null) {
                            HomeActivity homeActivity2 = homeActivity;
                            HomeMsg mMsgData = homeActivity2.getViewModel().getMMsgData();
                            Intrinsics.checkNotNull(mMsgData);
                            homeActivity2.viewPopMsg(mMsgData);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (noticeMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeActivity$initFlow$2", f = "HomeActivity.kt", i = {}, l = {R2.attr.autoCompleteTextViewStyle}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C07152 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07152(Continuation<? super C07152> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeActivity.this.new C07152(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07152) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> integralMsf = HomeActivity.this.getViewModel().getIntegralMsf();
                final HomeActivity homeActivity = HomeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.home.HomeActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            HomeActivity.access$getBodyBinding(homeActivity).integralTv.setText(YddUserManager.INSTANCE.getInstance().userIntegralGet() + homeActivity.getString(R.string.me_integral1));
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (integralMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeActivity$initFlow$3", f = "HomeActivity.kt", i = {}, l = {R2.attr.autoTransition}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeActivity$initFlow$3, reason: invalid class name and case insensitive filesystem */
    public static final class C07163 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07163(Continuation<? super C07163> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeActivity.this.new C07163(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07163) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> recommendMsf = HomeActivity.this.getViewModel().getRecommendMsf();
                final HomeActivity homeActivity = HomeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.home.HomeActivity.initFlow.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            removeMessages(105);
                            sendEmptyMessageDelayed(105, 1000L);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (recommendMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/HomeActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nHomeActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HomeActivity.kt\ncom/yddmi/doctor/pages/home/HomeActivity$initView$1\n+ 2 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,1306:1\n15#2,3:1307\n11#2,7:1310\n11#2,7:1317\n15#2,3:1324\n11#2,7:1327\n11#2,7:1334\n*S KotlinDebug\n*F\n+ 1 HomeActivity.kt\ncom/yddmi/doctor/pages/home/HomeActivity$initView$1\n*L\n281#1:1307,3\n287#1:1310,7\n339#1:1317,7\n352#1:1324,3\n359#1:1327,7\n366#1:1334,7\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.home.HomeActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07191 extends Lambda implements Function1<HomeActivityBinding, Unit> {
        public C07191() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(HomeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(HomeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                Intent intent = new Intent();
                intent.putExtra("showTabIndex", 5);
                intent.setClass(this$0, MeActivity.class);
                this$0.startActivity(intent);
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-INTEGRAL", "积分", null, 4, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(HomeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                Intent intent = new Intent();
                intent.setClass(this$0, MeActivity.class);
                this$0.startActivity(intent);
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CENTER", "个人中心", null, 4, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(HomeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.shengYunSetsDataBean == null) {
                Toaster.show((CharSequence) "资源不存在");
                return;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setPackage(this$0.getPackageName());
            ShengYunSetsBean.DataBean dataBean = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean);
            if (dataBean.getSeries().equals("0")) {
                intent.setData(Uri.parse("yikaobang.app://ykb_recdation_info/"));
            } else {
                intent.setData(Uri.parse("yikaobang.app://ykb_recdation/"));
            }
            ShengYunSetsBean.DataBean dataBean2 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean2);
            intent.putExtra("collection_id", dataBean2.getId());
            ShengYunSetsBean.DataBean dataBean3 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean3);
            intent.putExtra("category_id", dataBean3.getCid());
            ShengYunSetsBean.DataBean dataBean4 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean4);
            intent.putExtra("cover_img", dataBean4.getCover_img());
            ShengYunSetsBean.DataBean dataBean5 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean5);
            intent.putExtra("title", dataBean5.getTitle());
            ShengYunSetsBean.DataBean dataBean6 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean6);
            intent.putExtra("description", dataBean6.getDescription());
            ShengYunSetsBean.DataBean dataBean7 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean7);
            intent.putExtra("type", dataBean7.getType());
            ShengYunSetsBean.DataBean dataBean8 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean8);
            intent.putExtra("is_collected", dataBean8.getIs_collected());
            ShengYunSetsBean.DataBean dataBean9 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean9);
            intent.putExtra("create_time", dataBean9.getCreate_time());
            ShengYunSetsBean.DataBean dataBean10 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean10);
            intent.putExtra("update_time", dataBean10.getUpdate_time());
            ShengYunSetsBean.DataBean dataBean11 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean11);
            intent.putExtra("titleLabel", dataBean11.getLabel());
            ShengYunSetsBean.DataBean dataBean12 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean12);
            intent.putExtra(SocializeProtocolConstants.AUTHOR, dataBean12.getAuthor());
            ShengYunSetsBean.DataBean dataBean13 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean13);
            intent.putExtra("author_id", dataBean13.getAuthor_id());
            ShengYunSetsBean.DataBean dataBean14 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean14);
            intent.putExtra("series", dataBean14.getSeries());
            ShengYunSetsBean.DataBean dataBean15 = this$0.shengYunSetsDataBean;
            Intrinsics.checkNotNull(dataBean15);
            intent.putExtra("is_show_number", dataBean15.getIs_show_number());
            this$0.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(HomeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            YddUserManager.Companion companion = YddUserManager.INSTANCE;
            if (companion.getInstance().userIsLoginGo(this$0)) {
                if (this$0.mShowType == 156) {
                    Intent intent = new Intent();
                    intent.setClass(this$0, ExamActivity.class);
                    this$0.startActivity(intent);
                    YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST", "模拟考试", null, 4, null);
                    return;
                }
                if (this$0.mShowType == 157) {
                    DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_USER_TOKEN, companion.getInstance().userToken());
                    Intent intent2 = new Intent();
                    intent2.putExtra("code", "KQJN");
                    intent2.putExtra("title", "口腔执医");
                    intent2.setClass(this$0, PhysicalActivity.class);
                    this$0.startActivity(intent2);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(HomeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                Intent intent = new Intent();
                intent.setClass(this$0, PhysicalActivity.class);
                this$0.startActivity(intent);
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-BODYCHECK", "体格检查", null, 4, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$7(HomeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                Intent intent = new Intent();
                intent.setClass(this$0, MainActivity.class);
                this$0.startActivity(intent);
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-BASEOPERATE", "基本操作", null, 4, null);
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(HomeActivityBinding homeActivityBinding) {
            invoke2(homeActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull HomeActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView iconImgv = bodyBinding.iconImgv;
            Intrinsics.checkNotNullExpressionValue(iconImgv, "iconImgv");
            ViewExtKt.setDebounceClickListener$default(iconImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AppInformationUtil.isApkDebugable();
                }
            }, 0L, 2, null);
            ImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final HomeActivity homeActivity = HomeActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeActivity.C07191.invoke$lambda$1(homeActivity, view);
                }
            }, 0L, 2, null);
            ConstraintLayout integralCl = bodyBinding.integralCl;
            Intrinsics.checkNotNullExpressionValue(integralCl, "integralCl");
            final HomeActivity homeActivity2 = HomeActivity.this;
            ViewExtKt.setDebounceClickListener$default(integralCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeActivity.C07191.invoke$lambda$2(homeActivity2, view);
                }
            }, 0L, 2, null);
            TextView meTv = bodyBinding.meTv;
            Intrinsics.checkNotNullExpressionValue(meTv, "meTv");
            final HomeActivity homeActivity3 = HomeActivity.this;
            ViewExtKt.setDebounceClickListener$default(meTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeActivity.C07191.invoke$lambda$3(homeActivity3, view);
                }
            }, 0L, 2, null);
            ImageView xftzImgv = bodyBinding.xftzImgv;
            Intrinsics.checkNotNullExpressionValue(xftzImgv, "xftzImgv");
            final HomeActivity homeActivity4 = HomeActivity.this;
            ViewExtKt.setDebounceClickListener$default(xftzImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.e
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeActivity.C07191.invoke$lambda$4(homeActivity4, view);
                }
            }, 0L, 2, null);
            ImageView scoreImgv = bodyBinding.scoreImgv;
            Intrinsics.checkNotNullExpressionValue(scoreImgv, "scoreImgv");
            final HomeActivity homeActivity5 = HomeActivity.this;
            ViewExtKt.setDebounceClickListener$default(scoreImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeActivity.C07191.invoke$lambda$5(homeActivity5, view);
                }
            }, 0L, 2, null);
            ImageView bodyImgv = bodyBinding.bodyImgv;
            Intrinsics.checkNotNullExpressionValue(bodyImgv, "bodyImgv");
            final HomeActivity homeActivity6 = HomeActivity.this;
            ViewExtKt.setDebounceClickListener$default(bodyImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeActivity.C07191.invoke$lambda$6(homeActivity6, view);
                }
            }, 0L, 2, null);
            ImageView trainImgv = bodyBinding.trainImgv;
            Intrinsics.checkNotNullExpressionValue(trainImgv, "trainImgv");
            final HomeActivity homeActivity7 = HomeActivity.this;
            ViewExtKt.setDebounceClickListener$default(trainImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.home.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HomeActivity.C07191.invoke$lambda$7(homeActivity7, view);
                }
            }, 0L, 2, null);
            bodyBinding.integralTv.setText(YddUserManager.INSTANCE.getInstance().userIntegralGet() + HomeActivity.this.getString(R.string.me_integral1));
        }
    }

    @Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"com/yddmi/doctor/pages/home/HomeActivity$viewPopRecommendAd$2", "Lcom/yddmi/doctor/pages/main/PopupAd$OnPopupAdClickListener;", "onClick", "", "view", "Landroid/view/View;", "recommend", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.home.HomeActivity$viewPopRecommendAd$2, reason: invalid class name and case insensitive filesystem */
    public static final class C07252 implements PopupAd.OnPopupAdClickListener {
        final /* synthetic */ HomeMsg $ad;
        final /* synthetic */ PopupAd $popupAd;
        final /* synthetic */ HomeActivity this$0;

        public C07252(PopupAd popupAd, HomeActivity homeActivity, HomeMsg homeMsg) {
            this.$popupAd = popupAd;
            this.this$0 = homeActivity;
            this.$ad = homeMsg;
        }

        @Override // com.yddmi.doctor.pages.main.PopupAd.OnPopupAdClickListener
        public void onClick(@Nullable View view, boolean recommend) {
            if (!recommend || view == null) {
                return;
            }
            int id = view.getId();
            if (id == R.id.oneImgv) {
                this.$popupAd.dismiss();
                this.this$0.httpPostPopUserUpdateStatus(this.$ad);
            } else if (id == R.id.twoImgv) {
                this.$popupAd.dismiss();
                this.this$0.httpPostInviterSave();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.yddmi.doctor.pages.home.HomeActivity$shengyunReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.home.HomeActivity$mHandler$1] */
    public HomeActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.home.HomeActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                switch (msg.what) {
                    case 101:
                        removeMessages(101);
                        HomeActivity homeActivity = this.this$0;
                        homeActivity.httpPostAgentLogin(homeActivity.mPhoneNumber);
                        break;
                    case 102:
                        GlobalAction globalAction = GlobalAction.INSTANCE;
                        if (globalAction.getHomeShowAd()) {
                            globalAction.setHomeShowAd(false);
                            Object obj = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_MSG_DETAILS_AD);
                            HomeActivity.viewPopAd$default(this.this$0, obj instanceof HomeMsg ? (HomeMsg) obj : null, true, true, false, 8, null);
                            break;
                        }
                        break;
                    case 103:
                        GlobalAction globalAction2 = GlobalAction.INSTANCE;
                        if (globalAction2.getHomeStartAdClick()) {
                            globalAction2.setHomeStartAdClick(false);
                            Object obj2 = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_START_AD);
                            HomeMsg homeMsg = obj2 instanceof HomeMsg ? (HomeMsg) obj2 : null;
                            if (homeMsg != null) {
                                HomeActivity.dealAdClick$default(this.this$0, homeMsg, null, 2, null);
                                break;
                            }
                        }
                        break;
                    case 104:
                        GlobalAction globalAction3 = GlobalAction.INSTANCE;
                        if (globalAction3.getMRecommendRefresh()) {
                            globalAction3.setMRecommendRefresh(false);
                            break;
                        }
                        break;
                    case 105:
                        HomeActivity homeActivity2 = this.this$0;
                        homeActivity2.viewPopRecommendAd(homeActivity2.getViewModel().getMRecommendMsgData());
                        break;
                }
            }
        };
        this.shengyunReceiver = new BroadcastReceiver() { // from class: com.yddmi.doctor.pages.home.HomeActivity$shengyunReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(@NotNull Context context, @NotNull Intent intent) {
                List<ShengYunSetsBean.DataBean> data;
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                if (Intrinsics.areEqual(GlobalAction.ACTION_RECEIVE_SHENGYUN_SETS, intent.getAction())) {
                    ShengYunSetsBean shengYunSetsBean = (ShengYunSetsBean) GsonUtil.INSTANCE.GsonToBean(intent.getStringExtra(GlobalAction.EXTRA_SHENGYUN_SETS_DATA), ShengYunSetsBean.class);
                    if (shengYunSetsBean == null || (data = shengYunSetsBean.getData()) == null) {
                        return;
                    }
                    this.this$0.shengYunSetsDataBean = data.get(0);
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ HomeActivityBinding access$getBodyBinding(HomeActivity homeActivity) {
        return (HomeActivityBinding) homeActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dealAdClick(com.yddmi.doctor.entity.result.HomeMsg r18, android.view.View r19) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeActivity.dealAdClick(com.yddmi.doctor.entity.result.HomeMsg, android.view.View):void");
    }

    public static /* synthetic */ void dealAdClick$default(HomeActivity homeActivity, HomeMsg homeMsg, View view, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            view = null;
        }
        homeActivity.dealAdClick(homeMsg, view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealClearWebCache() {
        try {
            ContextManager.Companion companion = ContextManager.INSTANCE;
            companion.getInstance().getContext().deleteDatabase("webview.db");
            companion.getInstance().getContext().deleteDatabase("webviewCache.db");
        } catch (Throwable th) {
            LogExtKt.logd("dealClearWebCache() 清除Web缓存异常" + th, TAG);
        }
    }

    private final void dealForegroundListener() {
        if (this.mForegroundListener == null) {
            this.mForegroundListener = new ActivityLifeCycleCallbacksImpl.Listener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.dealForegroundListener.1
                @Override // com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl.Listener
                public void onBecameBackground() {
                    LogExtKt.logd("进入后台 onBecameBackground h0xta", HomeActivity.TAG);
                    HomeActivity.this.eventPointSave();
                }

                @Override // com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl.Listener
                public void onBecameForeground() {
                }
            };
        }
        ActivityLifeCycleCallbacksImpl activityLifeCycleCallbacksImpl = ActivityLifeCycleCallbacksImpl.INSTANCE.get(ContextManager.INSTANCE.getInstance().getContext());
        if (activityLifeCycleCallbacksImpl != null) {
            ActivityLifeCycleCallbacksImpl.Listener listener = this.mForegroundListener;
            Intrinsics.checkNotNull(listener);
            activityLifeCycleCallbacksImpl.addListener(listener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGet() {
        getViewModel().httpGetConfigWhite();
        getViewModel().httpGetContact();
        httpGetPersonInfo();
    }

    private final void dealGoBuyAll(HomeMsg ad, boolean buy24) {
        Intent intent = new Intent();
        if (ad == null) {
            intent.putExtra("type", 100);
        } else {
            intent.putExtra("type", ad.getType() != -1 ? ad.getType() : 100);
        }
        intent.putExtra("name", "");
        intent.putExtra("skillId", -1L);
        intent.putExtra("skill24", buy24 ? 1 : 3);
        intent.putExtra("fromHome", true);
        intent.setClass(this, ProductActivity.class);
        startActivity(intent);
    }

    public static /* synthetic */ void dealGoBuyAll$default(HomeActivity homeActivity, HomeMsg homeMsg, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            homeMsg = null;
        }
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        homeActivity.dealGoBuyAll(homeMsg, z2);
    }

    private final void dealStartAdCache() {
        GlobalAction globalAction = GlobalAction.INSTANCE;
        if (globalAction.getHomeStartAdProload()) {
            globalAction.setHomeStartAdProload(false);
            Object obj = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_START_AD);
            HomeMsg homeMsg = obj instanceof HomeMsg ? (HomeMsg) obj : null;
            if (homeMsg != null) {
                String coverUrl = homeMsg.getCoverUrl();
                if (coverUrl == null || coverUrl.length() == 0) {
                    return;
                }
                Object syncData = DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_START_AD, "");
                YddHostConfig.Companion companion = YddHostConfig.INSTANCE;
                if (Intrinsics.areEqual(syncData, companion.getInstance().getFileFullUrl(homeMsg.getCoverUrl()))) {
                    return;
                }
                final String fileFullUrl = companion.getInstance().getFileFullUrl(homeMsg.getCoverUrl());
                ImageViewExtKt.preloadImage(fileFullUrl, new Function1<Drawable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.dealStartAdCache.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Drawable drawable) {
                        invoke2(drawable);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@Nullable Drawable drawable) {
                        LogExtKt.logd("启动广告缓存成功 " + fileFullUrl, HomeActivity.TAG);
                        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_START_AD, fileFullUrl);
                    }
                }, new Function0<Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.dealStartAdCache.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        LogExtKt.logd("启动广告缓存失败 " + fileFullUrl, HomeActivity.TAG);
                    }
                });
            }
        }
    }

    private final void dealWxLogin() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (!companion.getInstance().getmWxApi().isWXAppInstalled()) {
            Toaster.show(R.string.me_wx_no);
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login";
        companion.getInstance().getmWxApi().sendReq(req);
    }

    private final void getRulelatest() {
        String mRuleData = getViewModel().getMRuleData();
        if (mRuleData == null || mRuleData.length() == 0) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getRulelatest(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.getRulelatest.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String message = it.getMessage();
                    if (message != null) {
                        Toaster.show((CharSequence) message);
                    }
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.getRulelatest.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable String str) {
                    HomeActivity.this.getViewModel().setMRuleData(str);
                    HomeActivity.this.viewPopRule();
                }
            });
        } else {
            viewPopRule();
        }
    }

    private final void httpAliOneClickGo(String token) {
        KeyboardUtils.hideSoftInput(this);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("phoneSystem", "android");
        linkedHashMap.put(SocialConstants.PARAM_SOURCE, "skill");
        linkedHashMap.put("logToken", token);
        linkedHashMap.put("orgId", "0");
        YddConfig yddConfig = YddConfig.INSTANCE;
        linkedHashMap.put("oaId", yddConfig.getAndroidInfo(100));
        linkedHashMap.put("mobileDeviceId", yddConfig.getAndroidInfo(100));
        String BRAND = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("mobileBrand", BRAND);
        String MODEL = Build.MODEL;
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("mobileModel", MODEL);
        String RELEASE = Build.VERSION.RELEASE;
        Intrinsics.checkNotNullExpressionValue(RELEASE, "RELEASE");
        linkedHashMap.put("mobileVersion", RELEASE);
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        linkedHashMap.put("inviteSign", companion.getInstance().loginShareCodeGet());
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("brandName", BRAND);
        linkedHashMap.put("operatingSystem", "android");
        linkedHashMap.put("deviceType", "2");
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("modelName", MODEL);
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        linkedHashMap.put("modelCode", DEVICE);
        if (AppInformationUtil.isApkDebugable()) {
            Toaster.showLong((CharSequence) ("ali手机号登录邀请码:" + companion.getInstance().loginShareCodeGet()));
        }
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postOneclickLogin(linkedHashMap), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpAliOneClickGo.1
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
                String message = it.getMessage();
                if (message == null) {
                    message = HomeActivity.this.getString(R.string.login_error1);
                    Intrinsics.checkNotNullExpressionValue(message, "getString(R.string.login_error1)");
                }
                Toaster.show((CharSequence) message);
            }
        }, new Function1<AuthLoginResult, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpAliOneClickGo.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AuthLoginResult authLoginResult) {
                invoke2(authLoginResult);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull AuthLoginResult lifecycleLoadingDialog) {
                Intrinsics.checkNotNullParameter(lifecycleLoadingDialog, "$this$lifecycleLoadingDialog");
                YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), lifecycleLoadingDialog, null, 2, null);
                HomeActivity.this.httpGetPersonInfo(lifecycleLoadingDialog);
            }
        });
    }

    private final void httpGetAppVersion() {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getAppVersion(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetAppVersion.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                LogExtKt.loge("检测升级error " + it, HomeActivity.TAG);
            }
        }, new Function1<AppVersion, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetAppVersion.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AppVersion appVersion) throws Resources.NotFoundException {
                invoke2(appVersion);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable AppVersion appVersion) throws Resources.NotFoundException {
                if (appVersion != null) {
                    HomeActivity homeActivity = HomeActivity.this;
                    if (1 == appVersion.getStatus() && FucUtil.compareVersion(appVersion.getExternalVersion(), AppUtils.getAppVersionName()) == 1) {
                        String versionUrl = appVersion.getVersionUrl();
                        if (versionUrl == null || versionUrl.length() == 0) {
                            return;
                        }
                        DownloadManager.Builder builder = new DownloadManager.Builder(homeActivity);
                        builder.apkUrl(appVersion.getVersionUrl());
                        builder.apkName(DateUtil.getTimeInMillis() + Constant.APK_SUFFIX);
                        builder.smallIcon(R.mipmap.ic_launcher);
                        builder.showNewerToast(true);
                        builder.apkVersionName(ExifInterface.GPS_MEASUREMENT_INTERRUPTED + appVersion.getExternalVersion());
                        String versionIntroduce = appVersion.getVersionIntroduce();
                        if (versionIntroduce == null) {
                            versionIntroduce = "";
                        }
                        builder.apkDescription(versionIntroduce);
                        builder.apkVersionCode(10000);
                        builder.enableLog(false);
                        builder.jumpInstallPage(true);
                        builder.dialogProgressBarColor(ContextExtKt.getColorM(homeActivity, R.color.c_0e9602));
                        builder.dialogButtonTextColor(-1);
                        builder.showNotification(true);
                        builder.showBgdToast(false);
                        builder.forcedUpgrade(appVersion.getUpgradeStatus() == 1);
                        builder.build().download();
                    }
                }
            }
        });
    }

    private final void httpGetAppWarn() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getAppWarn(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetAppWarn.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    LogExtKt.loge("App启动弹窗接口异常 " + it, HomeActivity.TAG);
                }
            }, new Function1<List<HomeMsg>, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetAppWarn.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(List<HomeMsg> list) {
                    invoke2(list);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable List<HomeMsg> list) {
                    List<HomeMsg> list2 = list;
                    if (list2 == null || list2.isEmpty()) {
                        return;
                    }
                    list.size();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPersonInfo() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getPersonInfoApp(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetPersonInfo.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String message = it.getMessage();
                    if (message != null) {
                        Toaster.show((CharSequence) message);
                    }
                }
            }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetPersonInfo.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(MeProfile meProfile) {
                    invoke2(meProfile);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable MeProfile meProfile) {
                    if (meProfile != null) {
                        YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), null, meProfile, 1, null);
                    }
                }
            });
        }
    }

    private final void httpGetVersionU3d() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getVersionU3d(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetVersionU3d.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    LogExtKt.loge("获取u3d版本异常" + it, HomeActivity.TAG);
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetVersionU3d.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable String str) {
                    if (str == null || str.length() == 0) {
                        return;
                    }
                    YddUserManager.Companion companion = YddUserManager.INSTANCE;
                    if (Intrinsics.areEqual(str, companion.getInstance().u3dVersionGet())) {
                        return;
                    }
                    companion.getInstance().u3dVersionSet(str);
                    HomeActivity.this.dealClearWebCache();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostAgentLogin(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() == 0) {
            Toaster.show((CharSequence) "号码异常,请返回重试");
            return;
        }
        KeyboardUtils.hideSoftInput(this);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("phoneSystem", "android");
        linkedHashMap.put(SocialConstants.PARAM_SOURCE, "skill");
        linkedHashMap.put(AliyunLogCommon.TERMINAL_TYPE, phoneNumber);
        linkedHashMap.put("orgId", "46");
        YddConfig yddConfig = YddConfig.INSTANCE;
        linkedHashMap.put("oaId", yddConfig.getAndroidInfo(100));
        linkedHashMap.put("mobileDeviceId", yddConfig.getAndroidInfo(100));
        String BRAND = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("mobileBrand", BRAND);
        String MODEL = Build.MODEL;
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("mobileModel", MODEL);
        String RELEASE = Build.VERSION.RELEASE;
        Intrinsics.checkNotNullExpressionValue(RELEASE, "RELEASE");
        linkedHashMap.put("mobileVersion", RELEASE);
        linkedHashMap.put("inviteSign", YddUserManager.INSTANCE.getInstance().loginShareCodeGet());
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("brandName", BRAND);
        linkedHashMap.put("operatingSystem", AppInformationUtil.isHarmonyOS() ? YddConfig.harmonyOs : "android");
        linkedHashMap.put("deviceType", "2");
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("modelName", MODEL);
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        linkedHashMap.put("modelCode", DEVICE);
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postAgentLogin(linkedHashMap, OtherUtils.INSTANCE.getHttpHeaderMap()), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostAgentLogin.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        }, new Function1<AuthLoginResult, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostAgentLogin.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AuthLoginResult authLoginResult) {
                invoke2(authLoginResult);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull AuthLoginResult lifecycle) {
                Intrinsics.checkNotNullParameter(lifecycle, "$this$lifecycle");
                YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), lifecycle, null, 2, null);
                HomeActivity.this.httpGetPersonInfo(lifecycle);
            }
        });
    }

    private final void httpPostCouponRecord() {
        FlowExtKt.lifecycleLoadingView(YddClinicRepository.INSTANCE.postCouponRecord(1), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostCouponRecord.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostCouponRecord.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable String str) {
            }
        });
    }

    private final void httpPostCouponRecord2() {
        GlobalAction globalAction = GlobalAction.INSTANCE;
        if (globalAction.getHomePayCancelTicket()) {
            globalAction.setHomePayCancelTicket(false);
            Object obj = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_AD_CANCEL_BUY);
            HomeMsg homeMsg = obj instanceof HomeMsg ? (HomeMsg) obj : null;
            if (homeMsg != null) {
                FlowExtKt.lifecycleLoadingView(YddClinicRepository.INSTANCE.postCouponRecord(2), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostCouponRecord2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull Throwable it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                    }
                }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostCouponRecord2.2
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str) {
                        invoke2(str);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@Nullable String str) {
                    }
                });
                viewPopAd$default(this, homeMsg, true, false, false, 12, null);
            }
        }
    }

    private final void httpPostIntegralAcquire(SkillHome data) {
        String id = data.getId();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postIntegralAcquire(new IntegralAcquireReq(id, companion.getInstance().userNickName(), companion.getInstance().userName(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostIntegralAcquire.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message != null) {
                    Toaster.show((CharSequence) message);
                }
            }
        }, new Function1<SkillShare, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostIntegralAcquire.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SkillShare skillShare) {
                invoke2(skillShare);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable SkillShare skillShare) {
                if (skillShare != null) {
                    HomeActivity.this.getViewModel().dealWxShare(skillShare);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostIntegralFreeReceive() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postIntegralFreeReceive(new IntegralAcquireReq("-1", companion.getInstance().userNickName(), companion.getInstance().userName(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostIntegralFreeReceive.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message != null) {
                    Toaster.show((CharSequence) message);
                }
            }
        }, new Function1<SkillShare, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostIntegralFreeReceive.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SkillShare skillShare) {
                invoke2(skillShare);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable SkillShare skillShare) {
                if (skillShare != null) {
                    HomeActivity.this.getViewModel().dealWxShare(skillShare);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostInviteLogin(String phone, String code, String inviteCode) {
        KeyboardUtils.hideSoftInput(this);
        FlowExtKt.lifecycleLoadingDialog(getViewModel().postInviteLogin(phone, code, inviteCode), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostInviteLogin.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message != null) {
                    Toaster.show((CharSequence) message);
                }
            }
        }, new Function1<AuthLoginResult1, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostInviteLogin.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AuthLoginResult1 authLoginResult1) {
                invoke2(authLoginResult1);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull AuthLoginResult1 lifecycleLoadingDialog) {
                Intrinsics.checkNotNullParameter(lifecycleLoadingDialog, "$this$lifecycleLoadingDialog");
                String log = lifecycleLoadingDialog.getLog();
                if (!(log == null || log.length() == 0)) {
                    Toaster.show((CharSequence) lifecycleLoadingDialog.getLog());
                }
                if (lifecycleLoadingDialog.getAccess_token() != null) {
                    YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), lifecycleLoadingDialog.getAccess_token(), null, 2, null);
                    HomeActivity.this.httpGetPersonInfo();
                    PopupInviteCode popupInviteCode = HomeActivity.this.getPopupInviteCode();
                    if (popupInviteCode != null) {
                        popupInviteCode.dismiss();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostInviterSave() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postInviterSave(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostInviterSave.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    LogExtKt.loge("成为推荐官失败 " + it, HomeActivity.TAG);
                }
            }, new Function1<HomeMsg, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostInviterSave.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(HomeMsg homeMsg) {
                    invoke2(homeMsg);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable HomeMsg homeMsg) {
                    LogExtKt.loge("成为推荐官", HomeActivity.TAG);
                    HomeActivity.this.viewPopRecommendQrAd(homeMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostPopUserUpdateStatus(HomeMsg msg) {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("popupId", msg.getPopupId());
            linkedHashMap.put("status", "0");
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postPopUserUpdateStatus(linkedHashMap), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostPopUserUpdateStatus.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    LogExtKt.loge("推荐官不再弹窗异常 " + it, HomeActivity.TAG);
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostPopUserUpdateStatus.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@Nullable String str) {
                    LogExtKt.loge("推荐官不再弹窗异常 ", HomeActivity.TAG);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostRedeemCode(String code) {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postRedeemCode(code), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostRedeemCode.1
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
                it.getMessage();
                PopupCode popupCode = HomeActivity.this.getPopupCode();
                if (popupCode != null) {
                    String message = it.getMessage();
                    if (message == null) {
                        message = HomeActivity.this.getString(R.string.home_integral_tip);
                        Intrinsics.checkNotNullExpressionValue(message, "getString(R.string.home_integral_tip)");
                    }
                    popupCode.viewStatus(101, message);
                }
            }
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpPostRedeemCode.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable String str) {
                PopupCode popupCode = HomeActivity.this.getPopupCode();
                if (popupCode != null) {
                    popupCode.viewStatus(102, str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpReadNoticeMessageList(HomeMsg m2) {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getReadNoticeId(m2.getId()), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpReadNoticeMessageList.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpReadNoticeMessageList.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable String str) {
                BusUtils.post(GlobalAction.eventMsgNum);
            }
        });
    }

    private final void requestShengYunGoods() {
        sendBroadcast(new Intent(GlobalAction.ACTION_REQUEST_SHENGYUN_SETS));
    }

    private final void viewCodeStatus() {
        LogExtKt.logd("viewCodeStatus 用户是否登录： " + YddUserManager.INSTANCE.getInstance().userIsLogin(), TAG);
    }

    private final void viewPopAd(final HomeMsg ad, boolean oneDayShow, final boolean get104, final boolean first) {
        if (ad != null) {
            String coverUrl = ad.getCoverUrl();
            if (coverUrl == null || coverUrl.length() == 0) {
                return;
            }
            if (oneDayShow) {
                YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
                String coverUrl2 = ad.getCoverUrl();
                if (coverUrl2 == null) {
                    coverUrl2 = "";
                }
                long jLongValue = ((Number) DataStoreUtils.INSTANCE.getSyncData(companion.getFileFullUrl(coverUrl2), 0L)).longValue();
                if (jLongValue != 0 && DateUtil.sameDate(jLongValue, DateUtil.getTimeInMillisLong())) {
                    return;
                }
            }
            if (this.popupAd == null) {
                setPopupAd(new PopupAd(this));
            }
            if (getPopupAd().isShow()) {
                getPopupAd().dismiss();
            }
            if (first) {
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-FIRSTBUY-POSTER", "首购福利-海报", null, 4, null);
            }
            PopupAd.setData$default(getPopupAd(), ad, oneDayShow, false, 4, null);
            getPopupAd().setOnPopupAdClickListener(new PopupAd.OnPopupAdClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewPopAd.2
                @Override // com.yddmi.doctor.pages.main.PopupAd.OnPopupAdClickListener
                public void onClick(@Nullable View view, boolean recommend) {
                    if (recommend) {
                        return;
                    }
                    GlobalAction.INSTANCE.setMRecommendRefresh(true);
                    Integer numValueOf = view != null ? Integer.valueOf(view.getId()) : null;
                    int i2 = R.id.xImgv;
                    if (numValueOf == null || numValueOf.intValue() != i2) {
                        this.dealAdClick(ad, view);
                        return;
                    }
                    if (first) {
                        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-FIRSTBUY-RETURN", "首购福利-返回", null, 4, null);
                    }
                    if (get104) {
                        sendEmptyMessageDelayed(104, 300L);
                    }
                }
            });
            new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(false).hasShadowBg(Boolean.TRUE).dismissOnTouchOutside(Boolean.FALSE).asCustom(getPopupAd()).show();
        }
    }

    public static /* synthetic */ void viewPopAd$default(HomeActivity homeActivity, HomeMsg homeMsg, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        if ((i2 & 4) != 0) {
            z3 = false;
        }
        if ((i2 & 8) != 0) {
            z4 = false;
        }
        homeActivity.viewPopAd(homeMsg, z2, z3, z4);
    }

    private final void viewPopCallMe() {
        if (getViewModel().getSkillCall() != null) {
            SkillCall skillCall = getViewModel().getSkillCall();
            String url = skillCall != null ? skillCall.getUrl() : null;
            if (!(url == null || url.length() == 0)) {
                PopupCallMe popupCallMe = new PopupCallMe(this);
                SkillCall skillCall2 = getViewModel().getSkillCall();
                Intrinsics.checkNotNull(skillCall2);
                final PopupCallMe data = popupCallMe.setData(skillCall2);
                data.setOnPopupCallMeClickListener(new PopupCallMe.OnPopupCallMeClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewPopCallMe.1
                    @Override // com.yddmi.doctor.pages.main.PopupCallMe.OnPopupCallMeClickListener
                    public void onClick(@Nullable View view) {
                        WebNoTitleBarActivity.INSTANCE.startWebActivity(HomeActivity.this, (R2.attr.actionModeShareDrawable & 2) != 0 ? "" : YddConfig.INSTANCE.getWebService(), (R2.attr.actionModeShareDrawable & 4) == 0 ? null : "", (R2.attr.actionModeShareDrawable & 8) != 0, (R2.attr.actionModeShareDrawable & 16) != 0 ? -1 : null, (R2.attr.actionModeShareDrawable & 32) != 0 ? -1 : null, (R2.attr.actionModeShareDrawable & 64) != 0 ? -1 : null, (R2.attr.actionModeShareDrawable & 128) != 0 ? -1L : 0L);
                        data.dismiss();
                    }
                });
                new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.TRUE).asCustom(data).show();
                return;
            }
        }
        Toaster.show(R.string.common_no_data1);
        getViewModel().httpGetContact();
    }

    private final void viewPopCode() {
        PopupCode popupCode = this.popupCode;
        if (popupCode != null) {
            popupCode.dismiss();
        }
        this.popupCode = null;
        PopupCode popupCode2 = new PopupCode(this);
        this.popupCode = popupCode2;
        Intrinsics.checkNotNull(popupCode2);
        popupCode2.setOnPopupCodeClickListener(new PopupCode.OnPopupCodeClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewPopCode.1
            @Override // com.yddmi.doctor.pages.main.PopupCode.OnPopupCodeClickListener
            public void onCodeClick(@Nullable View view, @Nullable String code) {
                if (!(code == null || code.length() == 0)) {
                    HomeActivity.this.httpPostRedeemCode(code);
                    return;
                }
                PopupCode popupCode3 = HomeActivity.this.getPopupCode();
                if (popupCode3 != null) {
                    popupCode3.dismiss();
                }
            }

            @Override // com.yddmi.doctor.pages.main.PopupCode.OnPopupCodeClickListener
            public void onCodeSuccess() {
                PopupCode popupCode3 = HomeActivity.this.getPopupCode();
                if (popupCode3 != null) {
                    popupCode3.dismiss();
                }
                HomeActivity.this.dealGet();
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(this.popupCode);
        PopupCode popupCode3 = this.popupCode;
        if (popupCode3 != null) {
            PopupCode.viewStatus$default(popupCode3, 100, null, 2, null);
        }
        PopupCode popupCode4 = this.popupCode;
        if (popupCode4 != null) {
            popupCode4.show();
        }
    }

    private final void viewPopInviteCode() {
        PopupInviteCode popupInviteCode = this.popupInviteCode;
        if (popupInviteCode != null) {
            popupInviteCode.dismiss();
        }
        this.popupInviteCode = null;
        PopupInviteCode popupInviteCode2 = new PopupInviteCode(this);
        this.popupInviteCode = popupInviteCode2;
        popupInviteCode2.setOnPopupCodeClickListener(new PopupInviteCode.OnPopupCodeClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewPopInviteCode.1
            @Override // com.yddmi.doctor.pages.main.PopupInviteCode.OnPopupCodeClickListener
            public void onCodeClick(@Nullable View view, @NotNull String phone, @NotNull String code, @NotNull String inviteCode) {
                Intrinsics.checkNotNullParameter(phone, "phone");
                Intrinsics.checkNotNullParameter(code, "code");
                Intrinsics.checkNotNullParameter(inviteCode, "inviteCode");
                HomeActivity.this.httpPostInviteLogin(phone, code, inviteCode);
            }

            @Override // com.yddmi.doctor.pages.main.PopupInviteCode.OnPopupCodeClickListener
            public void onCodeGet(@NotNull String phone) {
                Intrinsics.checkNotNullParameter(phone, "phone");
                HomeActivity.this.getViewModel().getPushCodeRegister(phone);
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(this.popupInviteCode);
        PopupInviteCode popupInviteCode3 = this.popupInviteCode;
        if (popupInviteCode3 != null) {
            PopupInviteCode.viewStatus$default(popupInviteCode3, 100, null, 2, null);
        }
        PopupInviteCode popupInviteCode4 = this.popupInviteCode;
        if (popupInviteCode4 != null) {
            popupInviteCode4.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopMsg(HomeMsg msgData) {
        final PopupMsg popupMsg = new PopupMsg(this);
        popupMsg.setData(msgData);
        popupMsg.setOnPopupMsgClickListener(new PopupMsg.OnPopupMsgClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewPopMsg.1
            @Override // com.yddmi.doctor.pages.main.PopupMsg.OnPopupMsgClickListener
            public void onMsgClick(@Nullable View view, @NotNull HomeMsg msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                msg.setRead(1);
                HomeActivity.this.httpReadNoticeMessageList(msg);
                YddConfig.INSTANCE.getKvData().put(YddConfig.KV_MSG_DETAILS, msg);
                HomeActivity homeActivity = HomeActivity.this;
                Intent intent = new Intent();
                intent.setClass(homeActivity, MsgDetailActivity.class);
                homeActivity.startActivity(intent);
                popupMsg.dismiss();
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupMsg).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopRecommendAd(HomeMsg ad) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopRecommendQrAd(HomeMsg ad) {
        if (ad != null) {
            String fileUrl = ad.getFileUrl();
            if (fileUrl == null || fileUrl.length() == 0) {
                return;
            }
            ad.setCoverUrl(ad.getFileUrl());
            PopupRecommend popupRecommend = new PopupRecommend(this);
            popupRecommend.setData(ad);
            new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(false).hasShadowBg(Boolean.TRUE).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupRecommend).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopRule() {
        PopupRule popupRule = new PopupRule(this);
        String mRuleData = getViewModel().getMRuleData();
        Intrinsics.checkNotNull(mRuleData);
        popupRule.setData(mRuleData);
        popupRule.setOnPopupRuleClickListener(new PopupRule.OnPopupRuleClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewPopRule.1
            @Override // com.yddmi.doctor.pages.main.PopupRule.OnPopupRuleClickListener
            public void onClick(@Nullable View view) {
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-FREEGET-SHARE", "免费领取-去分享", null, 4, null);
                HomeActivity.this.httpPostIntegralFreeReceive();
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupRule).show();
    }

    private final void viewPopWxQr() {
        if (getViewModel().getSkillCall() != null) {
            SkillCall skillCall = getViewModel().getSkillCall();
            String url = skillCall != null ? skillCall.getUrl() : null;
            if (!(url == null || url.length() == 0)) {
                PopupWxQr popupWxQr = new PopupWxQr(this);
                SkillCall skillCall2 = getViewModel().getSkillCall();
                Intrinsics.checkNotNull(skillCall2);
                String url2 = skillCall2.getUrl();
                if (url2 == null) {
                    url2 = "";
                }
                new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(false).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupWxQr.setData(url2)).show();
                return;
            }
        }
        Toaster.show(R.string.common_no_data1);
    }

    private final void viewSetImmersionBar() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.transparentBar();
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_BAR);
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR);
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.navigationBarColor(R.color.color_transparent);
        immersionBarWith.init();
        immersionBarWith.init();
    }

    private final void viewShow401Popup() {
        if (getFinalPopup().isShow()) {
            return;
        }
        boolean z2 = true;
        XPopup.Builder builderIsViewMode = new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).isViewMode(true);
        Boolean bool = Boolean.FALSE;
        builderIsViewMode.dismissOnTouchOutside(bool).dismissOnBackPressed(bool).asCustom(getFinalPopup()).show();
        GlobalAction globalAction = GlobalAction.INSTANCE;
        String m401ErrorMsg = globalAction.getM401ErrorMsg();
        if (m401ErrorMsg != null && m401ErrorMsg.length() != 0) {
            z2 = false;
        }
        String string = z2 ? getString(R.string.login_4011) : globalAction.getM401ErrorMsg();
        PopupCommonDialog finalPopup = getFinalPopup();
        Intrinsics.checkNotNull(string);
        String string2 = getString(R.string.common_know);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(R.string.common_know)");
        PopupCommonDialog.setCommonDialog$default(finalPopup, string, string2, null, null, 12, null).setOnPopupCommonClickListener(new PopupCommonDialog.OnPopupCommonDialogClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewShow401Popup.1
            @Override // com.yddmi.doctor.widget.PopupCommonDialog.OnPopupCommonDialogClickListener
            public void onClick(@Nullable View view) {
                if (view != null) {
                    HomeActivity homeActivity = HomeActivity.this;
                    if (view.getId() == R.id.singleTv) {
                        homeActivity.getFinalPopup().dismiss();
                        Intent intent = new Intent();
                        intent.setClass(homeActivity, HomeActivity.class);
                        homeActivity.startActivity(intent);
                        homeActivity.closeActivity();
                    }
                }
            }
        });
    }

    private final void viewShowTipPop(String str) {
        if (this.popupDialog == null) {
            setPopupDialog(new PopupTipDialog(this, false, 2, null));
        }
        if (getPopupDialog().isShow()) {
            return;
        }
        XPopup.Builder builderHasNavigationBar = new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasStatusBar(false).isViewMode(true).hasBlurBg(true).hasNavigationBar(false);
        Boolean bool = Boolean.TRUE;
        builderHasNavigationBar.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(getPopupDialog()).show();
        PopupTipDialog popupDialog = getPopupDialog();
        int i2 = R.color.c_2a57c2;
        PopupTipDialog.setTipDialog$default(popupDialog.setTitelColorSize(ContextExtKt.getColorM(this, i2), 26).setTipColorSize(ContextExtKt.getColorM(this, i2), 20), str, null, null, null, 0, 30, null).setOnPopupTipDialogClickListener(new PopupTipDialog.OnPopupTipDialogClickListener() { // from class: com.yddmi.doctor.pages.home.HomeActivity.viewShowTipPop.2
            @Override // com.yddmi.doctor.widget.PopupTipDialog.OnPopupTipDialogClickListener
            public void onClick(@NotNull View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                HomeActivity.this.dealGet();
            }
        });
    }

    @BusUtils.Bus(tag = GlobalAction.eventLogout401)
    public final void event401Deal() {
        LogExtKt.logd("event401Deal 您的账号已在其它地方登录 被挤下线", YddConfig.TAG);
        hideLoading();
        GlobalAction.INSTANCE.setMeInfoGet(true);
        YddUserManager.INSTANCE.getInstance().logoutUser(false);
        viewCodeStatus();
        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);
        if (NoDoubleClickUtil.isNoDoubleClick(600)) {
            sendEmptyMessage(101);
        }
    }

    @BusUtils.Bus(tag = GlobalAction.eventCaptureFeedback)
    public final void eventCaptureFeedback() {
        LogExtKt.logd("eventCaptureFeedback 截图问题反馈", TAG);
        FloatingX.control(YddConfig.floatingTag).hide();
        Intent intent = new Intent();
        intent.setClass(this, FeedbackActivity.class);
        startActivity(intent);
    }

    @BusUtils.Bus(tag = GlobalAction.eventCaptureWx)
    public final void eventCaptureWx() {
        LogExtKt.logd("eventCaptureWx 截图微信分享", TAG);
        FloatingX.control(YddConfig.floatingTag).hide();
        YddUserManager.INSTANCE.getInstance().getmWxApi().openWXApp();
    }

    @BusUtils.Bus(tag = GlobalAction.eventHomeAdFromPush)
    public final void eventHomeAdFromPush() {
        LogExtKt.logd("eventHomeAdFromPush 首页广告弹窗 " + GlobalAction.INSTANCE.getHomeShowAd(), TAG);
        sendEmptyMessageDelayed(102, 200L);
    }

    @BusUtils.Bus(tag = GlobalAction.eventHomeGoBuyAll)
    public final void eventHomeGoBuyAll() {
        dealGoBuyAll(null, true);
    }

    @BusUtils.Bus(tag = GlobalAction.eventLogIn)
    public final void eventLoginDeal() {
        LogExtKt.logd("eventLoginDeal 用户登录", YddConfig.TAG);
        dealGet();
    }

    @BusUtils.Bus(tag = GlobalAction.eventLogoff)
    public final void eventLogoffDeal() {
        LogExtKt.logd("eventLogoffDeal 用户注销", YddConfig.TAG);
        viewCodeStatus();
    }

    @BusUtils.Bus(tag = GlobalAction.eventLogout)
    public final void eventLogoutDeal() {
        LogExtKt.logd("eventLogoutDeal 用户登出", YddConfig.TAG);
        GlobalAction.INSTANCE.setMeInfoGet(true);
        getViewModel().dealTpushUserUnBind();
        viewCodeStatus();
    }

    @BusUtils.Bus(tag = GlobalAction.eventMsgNum)
    public final void eventMsgNum() {
        YddUserManager.INSTANCE.getInstance().userIsLogin();
    }

    @BusUtils.Bus(tag = GlobalAction.eventPointSave)
    public final void eventPointSave() {
        getViewModel().httpPointSave();
    }

    @BusUtils.Bus(tag = GlobalAction.eventUserTpushBind)
    public final void eventTpshBind() {
        viewCodeStatus();
    }

    @BusUtils.Bus(tag = GlobalAction.eventUserTpushUnBind)
    public final void eventTpshUnBind() {
        viewCodeStatus();
    }

    @NotNull
    public final PopupCommonDialog getFinalPopup() {
        PopupCommonDialog popupCommonDialog = this.finalPopup;
        if (popupCommonDialog != null) {
            return popupCommonDialog;
        }
        Intrinsics.throwUninitializedPropertyAccessException("finalPopup");
        return null;
    }

    @Nullable
    public final ActivityLifeCycleCallbacksImpl.Listener getMForegroundListener() {
        return this.mForegroundListener;
    }

    @NotNull
    public final PopupAd getPopupAd() {
        PopupAd popupAd = this.popupAd;
        if (popupAd != null) {
            return popupAd;
        }
        Intrinsics.throwUninitializedPropertyAccessException("popupAd");
        return null;
    }

    @Nullable
    public final PopupCode getPopupCode() {
        return this.popupCode;
    }

    @NotNull
    public final PopupTipDialog getPopupDialog() {
        PopupTipDialog popupTipDialog = this.popupDialog;
        if (popupTipDialog != null) {
            return popupTipDialog;
        }
        Intrinsics.throwUninitializedPropertyAccessException("popupDialog");
        return null;
    }

    @Nullable
    public final PopupInviteCode getPopupInviteCode() {
        return this.popupInviteCode;
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        LogExtKt.logd("initFlow", TAG);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07141(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07152(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07163(null), 3, null);
        if (NetworkUtils.isConnected()) {
            dealGet();
            return;
        }
        String string = getString(R.string.common_network_error);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.common_network_error)");
        viewShowTipPop(string);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        viewSetImmersionBar();
        this.mPhoneNumber = getIntent().getStringExtra("phoneNumber");
        this.mShowType = getIntent().getIntExtra("show_type", 0);
        dealForegroundListener();
        sendEmptyMessage(101);
        registerReceiver(this.shengyunReceiver, new IntentFilter(GlobalAction.ACTION_RECEIVE_SHENGYUN_SETS));
        int i2 = this.mShowType;
        if (i2 == 156) {
            bodyBinding(new Function1<HomeActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.initParam.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(HomeActivityBinding homeActivityBinding) {
                    invoke2(homeActivityBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull HomeActivityBinding bodyBinding) {
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    bodyBinding.xftzImgv.setVisibility(0);
                    bodyBinding.bodyImgv.setVisibility(0);
                    bodyBinding.trainImgv.setVisibility(0);
                    bodyBinding.scoreImgv.setVisibility(0);
                }
            });
        } else if (i2 == 157) {
            bodyBinding(new Function1<HomeActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.initParam.2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(HomeActivityBinding homeActivityBinding) {
                    invoke2(homeActivityBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull HomeActivityBinding bodyBinding) {
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    bodyBinding.xftzImgv.setVisibility(8);
                    bodyBinding.bodyImgv.setVisibility(8);
                    bodyBinding.trainImgv.setVisibility(8);
                    bodyBinding.scoreImgv.setVisibility(0);
                }
            });
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        setFinalPopup(new PopupCommonDialog(this));
        updateTitleLine(false);
        updateStatusBarTransparent();
        bodyBinding(new C07191());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        ActivityLifeCycleCallbacksImpl activityLifeCycleCallbacksImpl;
        super.onDestroy();
        removeCallbacksAndMessages(null);
        BusUtils.unregister(this);
        if (this.mForegroundListener != null && (activityLifeCycleCallbacksImpl = ActivityLifeCycleCallbacksImpl.INSTANCE.get(ContextManager.INSTANCE.getInstance().getContext())) != null) {
            ActivityLifeCycleCallbacksImpl.Listener listener = this.mForegroundListener;
            Intrinsics.checkNotNull(listener);
            activityLifeCycleCallbacksImpl.removeListener(listener);
        }
        YddConfig.INSTANCE.getKvData().clear();
        unregisterReceiver(this.shengyunReceiver);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        GlobalAction globalAction = GlobalAction.INSTANCE;
        if (globalAction.getHomeActRefresh()) {
            globalAction.setHomeActRefresh(false);
            dealGet();
        }
        getViewModel().httpGetIntegralApp();
        getViewModel().httpGetUserSpecificCoupon();
        httpPostCouponRecord2();
        getViewModel().httpPointSave();
        sendEmptyMessageDelayed(103, 600L);
        sendEmptyMessageDelayed(104, 800L);
        requestShengYunGoods();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        BusUtils.register(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
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

    public final void setFinalPopup(@NotNull PopupCommonDialog popupCommonDialog) {
        Intrinsics.checkNotNullParameter(popupCommonDialog, "<set-?>");
        this.finalPopup = popupCommonDialog;
    }

    public final void setMForegroundListener(@Nullable ActivityLifeCycleCallbacksImpl.Listener listener) {
        this.mForegroundListener = listener;
    }

    public final void setPopupAd(@NotNull PopupAd popupAd) {
        Intrinsics.checkNotNullParameter(popupAd, "<set-?>");
        this.popupAd = popupAd;
    }

    public final void setPopupCode(@Nullable PopupCode popupCode) {
        this.popupCode = popupCode;
    }

    public final void setPopupDialog(@NotNull PopupTipDialog popupTipDialog) {
        Intrinsics.checkNotNullParameter(popupTipDialog, "<set-?>");
        this.popupDialog = popupTipDialog;
    }

    public final void setPopupInviteCode(@Nullable PopupInviteCode popupInviteCode) {
        this.popupInviteCode = popupInviteCode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPersonInfo(final AuthLoginResult loginResult) {
        FlowExtKt.lifecycle(getViewModel().getPersonInfo(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetPersonInfo.3
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message != null) {
                    Toaster.show((CharSequence) message);
                }
            }
        }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.home.HomeActivity.httpGetPersonInfo.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeProfile meProfile) {
                invoke2(meProfile);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable MeProfile meProfile) {
                if (meProfile != null) {
                    YddUserManager.INSTANCE.getInstance().userInfoSave(loginResult, meProfile);
                }
            }
        });
    }
}
