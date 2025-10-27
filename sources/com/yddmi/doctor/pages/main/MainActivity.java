package com.yddmi.doctor.pages.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.angcyo.tablayout.DslTabLayout;
import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.mvvm.utils.GsonUtil;
import com.catchpig.mvvm.utils.NoDoubleClickUtil;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.google.android.exoplayer2.C;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.umeng.analytics.pro.am;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.MainActivityBinding;
import com.yddmi.doctor.entity.request.IntegralAcquireReq;
import com.yddmi.doctor.entity.request.IntegralUnlockReq;
import com.yddmi.doctor.entity.request.ShengYunGoodsBean;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.pages.exam.PopupExamCall;
import com.yddmi.doctor.pages.main.AdapterSkill1;
import com.yddmi.doctor.pages.main.MainActivity;
import com.yddmi.doctor.pages.main.PopupAd;
import com.yddmi.doctor.pages.main.PopupBuy;
import com.yddmi.doctor.pages.main.PopupCallMe;
import com.yddmi.doctor.pages.main.PopupCode;
import com.yddmi.doctor.pages.main.PopupMsg;
import com.yddmi.doctor.pages.main.PopupRule;
import com.yddmi.doctor.pages.me.MeActivity;
import com.yddmi.doctor.pages.product.PopupBuyStatus;
import com.yddmi.doctor.pages.product.ProductActivity;
import com.yddmi.doctor.pages.set.PopupWxQr;
import com.yddmi.doctor.pages.set.SetActivity;
import com.yddmi.doctor.pages.u3d.U3dActivity;
import com.yddmi.doctor.pages.u3d.U3dGoActivity;
import com.yddmi.doctor.pages.web.WebNoTitleBarActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.widget.PopupCommonDialog;
import com.yddmi.doctor.widget.PopupTipDialog;
import com.yikaobang.yixue.R2;
import java.util.Iterator;
import java.util.List;
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
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u009f\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010!\n\u0002\b\u0004*\u0003\u001e#H\b\u0007\u0018\u0000 |2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001|B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010J\u001a\u00020KH\u0002J\b\u0010L\u001a\u00020KH\u0002J\b\u0010M\u001a\u00020KH\u0002J\u001a\u0010N\u001a\u00020K2\u0006\u0010O\u001a\u00020F2\b\b\u0002\u0010P\u001a\u00020\fH\u0002J\b\u0010Q\u001a\u00020KH\u0002J\b\u0010R\u001a\u00020KH\u0002J\b\u0010S\u001a\u00020KH\u0002J\b\u0010T\u001a\u00020KH\u0002J\b\u0010U\u001a\u00020KH\u0002J\u0010\u0010V\u001a\u00020K2\u0006\u0010W\u001a\u00020XH\u0002J\b\u0010Y\u001a\u00020KH\u0002J\u0010\u0010Z\u001a\u00020K2\u0006\u0010W\u001a\u00020XH\u0002J\u0010\u0010[\u001a\u00020K2\u0006\u0010\\\u001a\u00020]H\u0002J\b\u0010^\u001a\u00020KH\u0016J\b\u0010_\u001a\u00020KH\u0016J\b\u0010`\u001a\u00020KH\u0016J\b\u0010a\u001a\u00020KH\u0014J\b\u0010b\u001a\u00020KH\u0014J\b\u0010c\u001a\u00020KH\u0014J\b\u0010d\u001a\u00020KH\u0014J\u0010\u0010e\u001a\u00020K2\u0006\u0010f\u001a\u00020!H\u0016J\u0012\u0010g\u001a\u00020K2\b\u0010W\u001a\u0004\u0018\u00010\fH\u0002J\b\u0010h\u001a\u00020KH\u0002J\u001c\u0010i\u001a\u00020K2\b\u0010j\u001a\u0004\u0018\u00010]2\b\b\u0002\u0010k\u001a\u00020!H\u0002J\u0010\u0010l\u001a\u00020K2\u0006\u0010W\u001a\u00020XH\u0002J\b\u0010m\u001a\u00020KH\u0002J\b\u0010n\u001a\u00020KH\u0002J\b\u0010o\u001a\u00020KH\u0002J\u0010\u0010p\u001a\u00020K2\u0006\u0010q\u001a\u00020]H\u0002J\b\u0010r\u001a\u00020KH\u0002J\b\u0010s\u001a\u00020KH\u0002J\b\u0010t\u001a\u00020KH\u0002J\b\u0010u\u001a\u00020KH\u0002J\b\u0010v\u001a\u00020KH\u0002J\b\u0010w\u001a\u00020KH\u0002J\u0016\u0010x\u001a\u00020K2\u000e\u0010W\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010yJ\u0010\u0010z\u001a\u00020K2\u0006\u0010{\u001a\u00020\fH\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u00020#X\u0082\u000e¢\u0006\u0004\n\u0002\u0010$R\u000e\u0010%\u001a\u00020&X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010'\u001a\u00020(X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010-\u001a\u0004\u0018\u00010.X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001c\u00103\u001a\u0004\u0018\u000104X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001a\u00109\u001a\u00020:X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001a\u0010?\u001a\u00020@X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u000e\u0010E\u001a\u00020FX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010G\u001a\u00020HX\u0082\u0004¢\u0006\u0004\n\u0002\u0010I¨\u0006}"}, d2 = {"Lcom/yddmi/doctor/pages/main/MainActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/MainActivityBinding;", "Lcom/yddmi/doctor/pages/main/MainViewModel;", "()V", "finalPopup", "Lcom/yddmi/doctor/widget/PopupCommonDialog;", "getFinalPopup", "()Lcom/yddmi/doctor/widget/PopupCommonDialog;", "setFinalPopup", "(Lcom/yddmi/doctor/widget/PopupCommonDialog;)V", "goodsId", "", "getGoodsId", "()Ljava/lang/String;", "setGoodsId", "(Ljava/lang/String;)V", "leftAdapter", "Lcom/yddmi/doctor/pages/main/AdapterCheck;", "getLeftAdapter", "()Lcom/yddmi/doctor/pages/main/AdapterCheck;", "setLeftAdapter", "(Lcom/yddmi/doctor/pages/main/AdapterCheck;)V", "linearLayoutManager", "Landroidx/recyclerview/widget/LinearLayoutManager;", "getLinearLayoutManager", "()Landroidx/recyclerview/widget/LinearLayoutManager;", "setLinearLayoutManager", "(Landroidx/recyclerview/widget/LinearLayoutManager;)V", "mHandler", "com/yddmi/doctor/pages/main/MainActivity$mHandler$1", "Lcom/yddmi/doctor/pages/main/MainActivity$mHandler$1;", "mOpen", "", "mReceiver", "com/yddmi/doctor/pages/main/MainActivity$mReceiver$1", "Lcom/yddmi/doctor/pages/main/MainActivity$mReceiver$1;", "mRuleCloseTime", "", "popupAd", "Lcom/yddmi/doctor/pages/main/PopupAd;", "getPopupAd", "()Lcom/yddmi/doctor/pages/main/PopupAd;", "setPopupAd", "(Lcom/yddmi/doctor/pages/main/PopupAd;)V", "popupBuy", "Lcom/yddmi/doctor/pages/main/PopupBuy;", "getPopupBuy", "()Lcom/yddmi/doctor/pages/main/PopupBuy;", "setPopupBuy", "(Lcom/yddmi/doctor/pages/main/PopupBuy;)V", "popupCode", "Lcom/yddmi/doctor/pages/main/PopupCode;", "getPopupCode", "()Lcom/yddmi/doctor/pages/main/PopupCode;", "setPopupCode", "(Lcom/yddmi/doctor/pages/main/PopupCode;)V", "popupDialog", "Lcom/yddmi/doctor/widget/PopupTipDialog;", "getPopupDialog", "()Lcom/yddmi/doctor/widget/PopupTipDialog;", "setPopupDialog", "(Lcom/yddmi/doctor/widget/PopupTipDialog;)V", "rightAdapter1", "Lcom/yddmi/doctor/pages/main/AdapterSkill1;", "getRightAdapter1", "()Lcom/yddmi/doctor/pages/main/AdapterSkill1;", "setRightAdapter1", "(Lcom/yddmi/doctor/pages/main/AdapterSkill1;)V", "rightLastClickIndex", "", "shengyunReceiver", "com/yddmi/doctor/pages/main/MainActivity$shengyunReceiver$1", "Lcom/yddmi/doctor/pages/main/MainActivity$shengyunReceiver$1;", "dealClearWebCache", "", "dealForegroundListener", "dealGet", "dealGoBuyAll", "type", "skillId", "getRulelatest", "httpGetHorseLamp", "httpGetPersonInfo", "httpGetVersionU3d", "httpPostCouponRecord2", "httpPostIntegralAcquire", "data", "Lcom/yddmi/doctor/entity/result/SkillHome;", "httpPostIntegralFreeReceive", "httpPostIntegralUnlock", "httpReadNoticeMessageList", "m", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "initFlow", "initParam", "initView", "onDestroy", "onResume", "onStart", "onStop", "onWindowFocusChanged", "hasFocus", "processShengYunData", "requestShengYunGoods", "viewPopAd", am.aw, "oneDayShow", "viewPopBuy", "viewPopBuyStatus", "viewPopCallMe", "viewPopCode", "viewPopMsg", "msgData", "viewPopRule", "viewPopWxQr", "viewRuleAnimation", "viewSetImmersionBar", "viewShowCall", "viewShowLabel", "viewShowMarquee", "", "viewShowTipPop", "str", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nMainActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MainActivity.kt\ncom/yddmi/doctor/pages/main/MainActivity\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n+ 4 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1053:1\n1#2:1054\n1#2:1060\n15#3,3:1055\n18#4,2:1058\n1855#5,2:1061\n*S KotlinDebug\n*F\n+ 1 MainActivity.kt\ncom/yddmi/doctor/pages/main/MainActivity\n*L\n539#1:1060\n533#1:1055,3\n539#1:1058,2\n874#1:1061,2\n*E\n"})
/* loaded from: classes6.dex */
public final class MainActivity extends BaseVMActivity<MainActivityBinding, MainViewModel> {

    @NotNull
    private static final String TAG = "MainActivity";
    public PopupCommonDialog finalPopup;

    @NotNull
    private String goodsId;
    public AdapterCheck leftAdapter;
    public LinearLayoutManager linearLayoutManager;

    @NotNull
    private final MainActivity$mHandler$1 mHandler;
    public PopupAd popupAd;

    @Nullable
    private PopupBuy popupBuy;

    @Nullable
    private PopupCode popupCode;
    public PopupTipDialog popupDialog;
    public AdapterSkill1 rightAdapter1;

    @NotNull
    private final MainActivity$shengyunReceiver$1 shengyunReceiver;
    private int rightLastClickIndex = -1;
    private boolean mOpen = true;
    private final long mRuleCloseTime = com.heytap.mcssdk.constant.a.f7153q;

    @NotNull
    private MainActivity$mReceiver$1 mReceiver = new BroadcastReceiver() { // from class: com.yddmi.doctor.pages.main.MainActivity$mReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(@Nullable Context context, @Nullable Intent intent) {
            for (SkillHome skillHome : this.this$0.getRightAdapter1().getData()) {
                if (skillHome.isPlay() == 1) {
                    skillHome.setLockStatus(1);
                }
            }
            this.this$0.getRightAdapter1().notifyDataSetChanged();
        }
    };

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainActivity$initFlow$1", f = "MainActivity.kt", i = {}, l = {425}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07911 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07911(Continuation<? super C07911> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainActivity.this.new C07911(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07911) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> noticeMsf = MainActivity.this.getViewModel().getNoticeMsf();
                final MainActivity mainActivity = MainActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.main.MainActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0 && mainActivity.getViewModel().getMMsgData() != null) {
                            MainActivity mainActivity2 = mainActivity;
                            HomeMsg mMsgData = mainActivity2.getViewModel().getMMsgData();
                            Intrinsics.checkNotNull(mMsgData);
                            mainActivity2.viewPopMsg(mMsgData);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainActivity$initFlow$2", f = "MainActivity.kt", i = {}, l = {R2.attr.banner_indicator_height}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C07922 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07922(Continuation<? super C07922> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainActivity.this.new C07922(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07922) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> leftListMsf = MainActivity.this.getViewModel().getLeftListMsf();
                final MainActivity mainActivity = MainActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.main.MainActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        mainActivity.hideLoading();
                        MainActivity mainActivity2 = mainActivity;
                        if (mainActivity2.popupDialog != null) {
                            mainActivity2.getPopupDialog().dismiss();
                        }
                        mainActivity.getLeftAdapter().set(mainActivity.getViewModel().getLeftList());
                        mainActivity.getViewModel().changeLeftIndex(0);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (leftListMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainActivity$initFlow$3", f = "MainActivity.kt", i = {}, l = {R2.attr.banner_indicator_selected_width}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainActivity$initFlow$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainActivity.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> rightListChange = MainActivity.this.getViewModel().getRightListChange();
                final MainActivity mainActivity = MainActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.main.MainActivity.initFlow.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            mainActivity.getRightAdapter1().set(mainActivity.getViewModel().getRightList());
                            removeMessages(101);
                            sendEmptyMessageDelayed(101, 160L);
                            Iterator<T> it = mainActivity.getRightAdapter1().getData().iterator();
                            boolean z2 = false;
                            while (it.hasNext()) {
                                if (((SkillHome) it.next()).isPlay() == 1) {
                                    z2 = true;
                                }
                            }
                            if (z2) {
                                LocalBroadcastManager.getInstance(mainActivity).sendBroadcast(new Intent().setAction("SHARE_SHENG_YUN").putExtra("query", true));
                                LocalBroadcastManager.getInstance(mainActivity).sendBroadcast(new Intent().setAction("DOCTOR_TOP").putExtra("create", true));
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (rightListChange.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainActivity$initFlow$4", f = "MainActivity.kt", i = {}, l = {R2.attr.barrierDirection}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainActivity$initFlow$4, reason: invalid class name */
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainActivity.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> getSkillErrorMsf = MainActivity.this.getViewModel().getGetSkillErrorMsf();
                final MainActivity mainActivity = MainActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.main.MainActivity.initFlow.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            String skillGetError = mainActivity.getViewModel().getSkillGetError();
                            String string = skillGetError == null || skillGetError.length() == 0 ? mainActivity.getString(R.string.common_network_error) : mainActivity.getViewModel().getSkillGetError();
                            MainActivity mainActivity2 = mainActivity;
                            Intrinsics.checkNotNull(string);
                            mainActivity2.viewShowTipPop(string);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (getSkillErrorMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainActivity$initFlow$5", f = "MainActivity.kt", i = {}, l = {R2.attr.behavior_hideable}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainActivity$initFlow$5, reason: invalid class name */
    public static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass5(Continuation<? super AnonymousClass5> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainActivity.this.new AnonymousClass5(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> getLabelMsf = MainActivity.this.getViewModel().getGetLabelMsf();
                final MainActivity mainActivity = MainActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.main.MainActivity.initFlow.5.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            mainActivity.viewShowLabel();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (getLabelMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/MainActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nMainActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MainActivity.kt\ncom/yddmi/doctor/pages/main/MainActivity$initView$1\n+ 2 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,1053:1\n15#2,3:1054\n11#2,7:1057\n15#2,3:1064\n11#2,7:1067\n11#2,7:1074\n11#2,7:1081\n*S KotlinDebug\n*F\n+ 1 MainActivity.kt\ncom/yddmi/doctor/pages/main/MainActivity$initView$1\n*L\n349#1:1054,3\n350#1:1057,7\n372#1:1064,3\n373#1:1067,7\n388#1:1074,7\n393#1:1081,7\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.main.MainActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07961 extends Lambda implements Function1<MainActivityBinding, Unit> {
        public C07961() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                this$0.viewPopCode();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                if (Intrinsics.areEqual("-1", this$0.getViewModel().getNewId()) || Intrinsics.areEqual("", this$0.getViewModel().getNewId())) {
                    Toaster.show(R.string.common_no_data2);
                    return;
                }
                if (NoDoubleClickUtil.isNoDoubleClick(1500)) {
                    this$0.loadingDialog();
                    Intent intent = new Intent();
                    intent.putExtra("typeId", this$0.getViewModel().getNewId());
                    intent.putExtra("url", this$0.getViewModel().getNewUrl());
                    intent.putExtra("CurrentProject", 1);
                    intent.putExtra("showNew", true);
                    intent.setClass(this$0, U3dGoActivity.class);
                    this$0.startActivity(intent);
                    Intent intent2 = new Intent();
                    intent2.setClass(this$0, U3dActivity.class);
                    this$0.startActivity(intent2);
                    this$0.mHandler.sendEmptyMessageDelayed(102, 1200L);
                }
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-BASEOPERATE-GUIDANCE", "新手引导", null, 4, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                if (Intrinsics.areEqual("-1", this$0.getViewModel().getNewId()) || Intrinsics.areEqual("", this$0.getViewModel().getNewId())) {
                    Toaster.show(R.string.common_no_data2);
                    return;
                }
                if (NoDoubleClickUtil.isNoDoubleClick(1500)) {
                    this$0.loadingDialog();
                    Intent intent = new Intent();
                    intent.putExtra("typeId", this$0.getViewModel().getNewId());
                    intent.putExtra("url", this$0.getViewModel().getNewUrl());
                    intent.putExtra("CurrentProject", 1);
                    intent.putExtra("skillType", 1);
                    intent.putExtra("showNew", true);
                    intent.setClass(this$0, U3dGoActivity.class);
                    this$0.startActivity(intent);
                    Intent intent2 = new Intent();
                    intent2.setClass(this$0, U3dActivity.class);
                    this$0.startActivity(intent2);
                    this$0.mHandler.sendEmptyMessageDelayed(102, 1200L);
                }
                YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-BASEOPERATE-GUIDANCE", "新手引导", null, 4, null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                this$0.viewPopCallMe();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                Intent intent = new Intent();
                intent.setClass(this$0, MeActivity.class);
                this$0.startActivity(intent);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                Intent intent = new Intent();
                intent.setClass(this$0, SetActivity.class);
                this$0.startActivity(intent);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$7(MainActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(this$0)) {
                this$0.getRulelatest();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$8(MainActivity this$0, MainActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this$0.mHandler.removeMessages(103);
            this$0.mHandler.sendEmptyMessageDelayed(103, this$0.mRuleCloseTime);
            this_bodyBinding.ruleImgv.setVisibility(8);
            this$0.viewRuleAnimation();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(MainActivityBinding mainActivityBinding) {
            invoke2(mainActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final MainActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView iconImgv = bodyBinding.iconImgv;
            Intrinsics.checkNotNullExpressionValue(iconImgv, "iconImgv");
            final MainActivity mainActivity = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(iconImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$0(mainActivity, view);
                }
            }, 0L, 2, null);
            ImageView codeImgv = bodyBinding.codeImgv;
            Intrinsics.checkNotNullExpressionValue(codeImgv, "codeImgv");
            final MainActivity mainActivity2 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(codeImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.e
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$1(mainActivity2, view);
                }
            }, 0L, 2, null);
            ImageView newImgv = bodyBinding.newImgv;
            Intrinsics.checkNotNullExpressionValue(newImgv, "newImgv");
            final MainActivity mainActivity3 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(newImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$2(mainActivity3, view);
                }
            }, 0L, 2, null);
            TextView newTv = bodyBinding.newTv;
            Intrinsics.checkNotNullExpressionValue(newTv, "newTv");
            final MainActivity mainActivity4 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(newTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$3(mainActivity4, view);
                }
            }, 0L, 2, null);
            ImageView callImgv = bodyBinding.callImgv;
            Intrinsics.checkNotNullExpressionValue(callImgv, "callImgv");
            final MainActivity mainActivity5 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(callImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$4(mainActivity5, view);
                }
            }, 0L, 2, null);
            ImageView meImgv = bodyBinding.meImgv;
            Intrinsics.checkNotNullExpressionValue(meImgv, "meImgv");
            final MainActivity mainActivity6 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(meImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$5(mainActivity6, view);
                }
            }, 0L, 2, null);
            ImageView setImgv = bodyBinding.setImgv;
            Intrinsics.checkNotNullExpressionValue(setImgv, "setImgv");
            final MainActivity mainActivity7 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(setImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$6(mainActivity7, view);
                }
            }, 0L, 2, null);
            TextView ruleTv = bodyBinding.ruleTv;
            Intrinsics.checkNotNullExpressionValue(ruleTv, "ruleTv");
            final MainActivity mainActivity8 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(ruleTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$7(mainActivity8, view);
                }
            }, 0L, 2, null);
            ImageView ruleImgv = bodyBinding.ruleImgv;
            Intrinsics.checkNotNullExpressionValue(ruleImgv, "ruleImgv");
            final MainActivity mainActivity9 = MainActivity.this;
            ViewExtKt.setDebounceClickListener$default(ruleImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.C07961.invoke$lambda$8(mainActivity9, bodyBinding, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView = bodyBinding.leftRv;
            MainActivity mainActivity10 = MainActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(mainActivity10.getLeftAdapter());
            RecyclerView recyclerView2 = bodyBinding.rightRv;
            MainActivity mainActivity11 = MainActivity.this;
            mainActivity11.setLinearLayoutManager(new LinearLayoutManager(recyclerView2.getContext()));
            mainActivity11.getLinearLayoutManager().setOrientation(1);
            recyclerView2.setLayoutManager(mainActivity11.getLinearLayoutManager());
            recyclerView2.setAdapter(mainActivity11.getRightAdapter1());
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.yddmi.doctor.pages.main.MainActivity$mReceiver$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.yddmi.doctor.pages.main.MainActivity$shengyunReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.main.MainActivity$mHandler$1] */
    public MainActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.main.MainActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                int i2 = msg.what;
                if (i2 == 102) {
                    this.this$0.hideLoading();
                } else {
                    if (i2 != 105) {
                        return;
                    }
                    this.this$0.viewPopBuyStatus();
                }
            }
        };
        this.goodsId = "";
        this.shengyunReceiver = new BroadcastReceiver() { // from class: com.yddmi.doctor.pages.main.MainActivity$shengyunReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(@NotNull Context context, @NotNull Intent intent) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                if (Intrinsics.areEqual(GlobalAction.ACTION_RECEIVE_SHENGYUN_GOODS, intent.getAction())) {
                    String stringExtra = intent.getStringExtra(GlobalAction.EXTRA_SHENGYUN_GOODS_DATA);
                    Log.e("wwwwwwwww", "获取数据成功 - PhysicalActivity 拿到数据: " + stringExtra);
                    this.this$0.processShengYunData(stringExtra);
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ MainActivityBinding access$getBodyBinding(MainActivity mainActivity) {
        return (MainActivityBinding) mainActivity.getBodyBinding();
    }

    private final void dealClearWebCache() {
        try {
            ContextManager.Companion companion = ContextManager.INSTANCE;
            companion.getInstance().getContext().deleteDatabase("webview.db");
            companion.getInstance().getContext().deleteDatabase("webviewCache.db");
        } catch (Throwable th) {
            LogExtKt.logd("dealClearWebCache() 清除Web缓存异常" + th, TAG);
        }
    }

    private final void dealForegroundListener() {
        ActivityLifeCycleCallbacksImpl activityLifeCycleCallbacksImpl = ActivityLifeCycleCallbacksImpl.INSTANCE.get(ContextManager.INSTANCE.getInstance().getContext());
        if (activityLifeCycleCallbacksImpl != null) {
            activityLifeCycleCallbacksImpl.addListener(new ActivityLifeCycleCallbacksImpl.Listener() { // from class: com.yddmi.doctor.pages.main.MainActivity.dealForegroundListener.1
                @Override // com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl.Listener
                public void onBecameBackground() {
                    LogExtKt.logd("进入后台 onBecameBackground h0xta", MainActivity.TAG);
                }

                @Override // com.catchpig.mvvm.lifecycle.ActivityLifeCycleCallbacksImpl.Listener
                public void onBecameForeground() {
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGet() {
        getViewModel().httpGetSkillBasicHome();
        getViewModel().httpGetContact();
        getViewModel().httpGetIntegralApp();
        httpGetPersonInfo();
        httpGetVersionU3d();
        httpGetHorseLamp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGoBuyAll(int type, String skillId) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("name", "");
        intent.putExtra("skillId", skillId);
        intent.putExtra("skill24", 1);
        intent.setClass(this, ProductActivity.class);
        startActivity(intent);
    }

    public static /* synthetic */ void dealGoBuyAll$default(MainActivity mainActivity, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "-1";
        }
        mainActivity.dealGoBuyAll(i2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getRulelatest() {
        String mRuleData = getViewModel().getMRuleData();
        if (mRuleData == null || mRuleData.length() == 0) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getRulelatest(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.getRulelatest.1
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
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.getRulelatest.2
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
                    MainActivity.this.getViewModel().setMRuleData(str);
                    MainActivity.this.viewPopRule();
                }
            });
        } else {
            viewPopRule();
        }
    }

    private final void httpGetHorseLamp() {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getHorseLamp(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpGetHorseLamp.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        }, new Function1<List<String>, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpGetHorseLamp.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<String> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable List<String> list) {
                MainActivity.this.viewShowMarquee(list);
            }
        });
    }

    private final void httpGetPersonInfo() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getPersonInfoApp(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpGetPersonInfo.1
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
            }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpGetPersonInfo.2
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
        YddUserManager.INSTANCE.getInstance().userIsLogin();
    }

    private final void httpPostCouponRecord2() {
        GlobalAction globalAction = GlobalAction.INSTANCE;
        if (globalAction.getHomePayCancelTicket()) {
            globalAction.setHomePayCancelTicket(false);
            Object obj = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_AD_CANCEL_BUY);
            HomeMsg homeMsg = obj instanceof HomeMsg ? (HomeMsg) obj : null;
            if (homeMsg != null) {
                viewPopAd(homeMsg, true);
                FlowExtKt.lifecycleLoadingView(YddClinicRepository.INSTANCE.postCouponRecord(2), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostCouponRecord2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                        invoke2(th);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull Throwable it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                    }
                }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostCouponRecord2.2
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
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostIntegralAcquire(SkillHome data) {
        String id = data.getId();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postIntegralAcquire(new IntegralAcquireReq(id, companion.getInstance().userNickName(), companion.getInstance().userName(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostIntegralAcquire.1
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
        }, new Function1<SkillShare, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostIntegralAcquire.2
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
                    MainActivity.this.getViewModel().dealWxShare(skillShare);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostIntegralFreeReceive() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postIntegralFreeReceive(new IntegralAcquireReq("-1", companion.getInstance().userNickName(), companion.getInstance().userName(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostIntegralFreeReceive.1
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
        }, new Function1<SkillShare, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostIntegralFreeReceive.2
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
                    MainActivity.this.getViewModel().dealWxShare(skillShare);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostIntegralUnlock(SkillHome data) {
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postIntegralUnlock(new IntegralUnlockReq(data.getId(), data.getSkillExchangeIntegral(), YddUserManager.INSTANCE.getInstance().userName())), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostIntegralUnlock.1
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
        }, new Function1<Boolean, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpPostIntegralUnlock.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z2) {
                if (!z2) {
                    PopupBuy popupBuy = MainActivity.this.getPopupBuy();
                    if (popupBuy != null) {
                        PopupBuy.viewStatus$default(popupBuy, 101, null, 2, null);
                        return;
                    }
                    return;
                }
                MainActivity.this.getViewModel().httpGetSkillBasicHome();
                MainActivity.this.getViewModel().httpGetIntegralApp();
                PopupBuy popupBuy2 = MainActivity.this.getPopupBuy();
                if (popupBuy2 != null) {
                    PopupBuy.viewStatus$default(popupBuy2, 102, null, 2, null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpReadNoticeMessageList(HomeMsg m2) {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getReadNoticeId(m2.getId()), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpReadNoticeMessageList.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.httpReadNoticeMessageList.2
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void processShengYunData(String data) {
        if (data == null || data.length() == 0) {
            return;
        }
        try {
            ShengYunGoodsBean shengYunGoodsBean = (ShengYunGoodsBean) GsonUtil.INSTANCE.GsonToBean(data, ShengYunGoodsBean.class);
            if (shengYunGoodsBean != null) {
                Log.e("wwwwwwwww goodsId ", shengYunGoodsBean.getData().getGoods_id());
                String goods_id = shengYunGoodsBean.getData().getGoods_id();
                Intrinsics.checkNotNullExpressionValue(goods_id, "goodsData.data.goods_id");
                this.goodsId = goods_id;
                Intent intent = new Intent();
                intent.setData(Uri.parse("yikaobang.app://ykb_goodsnew_home/"));
                intent.setAction("android.intent.action.VIEW");
                intent.setPackage(getPackageName());
                intent.putExtra("goods_id", this.goodsId);
                startActivity(intent);
            }
        } catch (Exception e2) {
            Log.e("HomeActivity", "解析数据失败", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestShengYunGoods() {
        sendBroadcast(new Intent(GlobalAction.ACTION_REQUEST_SHENGYUN_GOODS));
    }

    private final void viewPopAd(HomeMsg ad, boolean oneDayShow) {
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
            PopupAd.setData$default(getPopupAd(), ad, oneDayShow, false, 4, null);
            getPopupAd().setOnPopupAdClickListener(new PopupAd.OnPopupAdClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewPopAd.2
                @Override // com.yddmi.doctor.pages.main.PopupAd.OnPopupAdClickListener
                public void onClick(@Nullable View view, boolean re) {
                    MainActivity.dealGoBuyAll$default(MainActivity.this, 102, null, 2, null);
                }
            });
            new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(false).hasShadowBg(Boolean.TRUE).dismissOnTouchOutside(Boolean.FALSE).asCustom(getPopupAd()).show();
        }
    }

    public static /* synthetic */ void viewPopAd$default(MainActivity mainActivity, HomeMsg homeMsg, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        mainActivity.viewPopAd(homeMsg, z2);
    }

    private final void viewPopBuy(final SkillHome data) {
        PopupBuy popupBuy = this.popupBuy;
        if (popupBuy != null) {
            popupBuy.dismiss();
        }
        this.popupBuy = null;
        PopupBuy popupBuy2 = new PopupBuy(this);
        this.popupBuy = popupBuy2;
        popupBuy2.buyGoodsNameSet(data.getName(), data.getSkillExchangeIntegral(), YddUserManager.INSTANCE.getInstance().userIntegralGet());
        PopupBuy popupBuy3 = this.popupBuy;
        if (popupBuy3 != null) {
            popupBuy3.setOnPopupBuyClickListener(new PopupBuy.OnPopupBuyClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewPopBuy.1
                @Override // com.yddmi.doctor.pages.main.PopupBuy.OnPopupBuyClickListener
                public void onClick(@Nullable View view) {
                    if (view != null) {
                        SkillHome skillHome = data;
                        MainActivity mainActivity = this;
                        int id = view.getId();
                        if (id == R.id.shareBtnTv) {
                            if (YddUserManager.INSTANCE.getInstance().userIntegralGet() >= skillHome.getSkillExchangeIntegral()) {
                                mainActivity.httpPostIntegralUnlock(skillHome);
                                YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                                String label = skillHome.getLabel();
                                if (label.length() == 0) {
                                    label = skillHome.getName();
                                }
                                companion.addPoint("SJ-A-HOME-BASEOPERATE-EXCAHNGE", "兑换(" + ((Object) label) + ")", String.valueOf(skillHome.getId()));
                                return;
                            }
                            mainActivity.httpPostIntegralAcquire(skillHome);
                            PopupBuy popupBuy4 = mainActivity.getPopupBuy();
                            if (popupBuy4 != null) {
                                popupBuy4.dismiss();
                            }
                            YddPointManager companion2 = YddPointManager.INSTANCE.getInstance();
                            String label2 = skillHome.getLabel();
                            if (label2.length() == 0) {
                                label2 = skillHome.getName();
                            }
                            companion2.addPoint("SJ-A-HOME-BASEOPERATE-INTEGRAL", "分享得积分(" + ((Object) label2) + ")", String.valueOf(skillHome.getId()));
                            return;
                        }
                        if (id == R.id.buyBtnTv) {
                            mainActivity.dealGoBuyAll(101, String.valueOf(skillHome.getId()));
                            PopupBuy popupBuy5 = mainActivity.getPopupBuy();
                            if (popupBuy5 != null) {
                                popupBuy5.dismiss();
                            }
                            YddPointManager companion3 = YddPointManager.INSTANCE.getInstance();
                            String label3 = skillHome.getLabel();
                            if (label3.length() == 0) {
                                label3 = skillHome.getName();
                            }
                            companion3.addPoint("SJ-A-HOME-BASEOPERATE-BUYING", "购买(" + ((Object) label3) + ")", String.valueOf(skillHome.getId()));
                            return;
                        }
                        if (id != R.id.goTv) {
                            if (id != R.id.shareTv) {
                                if (id == R.id.closeImgv) {
                                    YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-BASEOPERATE-RETURN", "返回", null, 4, null);
                                    return;
                                }
                                return;
                            } else {
                                mainActivity.httpPostIntegralAcquire(skillHome);
                                PopupBuy popupBuy6 = mainActivity.getPopupBuy();
                                if (popupBuy6 != null) {
                                    popupBuy6.dismiss();
                                    return;
                                }
                                return;
                            }
                        }
                        PopupBuy popupBuy7 = mainActivity.getPopupBuy();
                        if (popupBuy7 != null) {
                            popupBuy7.dismiss();
                        }
                        if (NoDoubleClickUtil.isNoDoubleClick(1500)) {
                            mainActivity.loadingDialog();
                            Intent intent = new Intent();
                            intent.putExtra("typeId", skillHome.getId());
                            intent.putExtra("url", skillHome.getUrl());
                            intent.putExtra("isPlay", skillHome.isPlay());
                            intent.putExtra("CurrentProject", 1);
                            intent.setClass(mainActivity, U3dGoActivity.class);
                            mainActivity.startActivity(intent);
                            Intent intent2 = new Intent();
                            intent2.setClass(mainActivity, U3dActivity.class);
                            mainActivity.startActivity(intent2);
                            mainActivity.mHandler.sendEmptyMessageDelayed(102, 1200L);
                        }
                    }
                }
            });
        }
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(this.popupBuy).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopBuyStatus() {
        PopupBuyStatus popupBuyStatus = new PopupBuyStatus(this);
        SkillCall skillCall = getViewModel().getSkillCall();
        popupBuyStatus.setData(skillCall != null ? skillCall.getUrl() : null, 100);
        popupBuyStatus.setOnPopupBuyStatusClickListener(new PopupBuyStatus.OnPopupBuyStatusClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewPopBuyStatus.1
            @Override // com.yddmi.doctor.pages.product.PopupBuyStatus.OnPopupBuyStatusClickListener
            public void onClick(@Nullable View view) {
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupBuyStatus).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopCallMe() {
        if (getViewModel().getSkillCall() != null) {
            SkillCall skillCall = getViewModel().getSkillCall();
            String url = skillCall != null ? skillCall.getUrl() : null;
            if (!(url == null || url.length() == 0)) {
                PopupCallMe popupCallMe = new PopupCallMe(this);
                SkillCall skillCall2 = getViewModel().getSkillCall();
                Intrinsics.checkNotNull(skillCall2);
                final PopupCallMe data = popupCallMe.setData(skillCall2);
                data.setOnPopupCallMeClickListener(new PopupCallMe.OnPopupCallMeClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewPopCallMe.1
                    @Override // com.yddmi.doctor.pages.main.PopupCallMe.OnPopupCallMeClickListener
                    public void onClick(@Nullable View view) {
                        WebNoTitleBarActivity.INSTANCE.startWebActivity(MainActivity.this, (R2.attr.actionModeShareDrawable & 2) != 0 ? "" : YddConfig.INSTANCE.getWebService(), (R2.attr.actionModeShareDrawable & 4) == 0 ? null : "", (R2.attr.actionModeShareDrawable & 8) != 0, (R2.attr.actionModeShareDrawable & 16) != 0 ? -1 : null, (R2.attr.actionModeShareDrawable & 32) != 0 ? -1 : null, (R2.attr.actionModeShareDrawable & 64) != 0 ? -1 : null, (R2.attr.actionModeShareDrawable & 128) != 0 ? -1L : 0L);
                        data.dismiss();
                    }
                });
                new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.TRUE).asCustom(data).show();
                return;
            }
        }
        Toaster.show(R.string.common_no_data1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopCode() {
        PopupCode popupCode = this.popupCode;
        if (popupCode != null) {
            popupCode.dismiss();
        }
        this.popupCode = null;
        PopupCode popupCode2 = new PopupCode(this);
        this.popupCode = popupCode2;
        Intrinsics.checkNotNull(popupCode2);
        popupCode2.setOnPopupCodeClickListener(new PopupCode.OnPopupCodeClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewPopCode.1
            @Override // com.yddmi.doctor.pages.main.PopupCode.OnPopupCodeClickListener
            public void onCodeClick(@Nullable View view, @Nullable String code) {
                PopupCode popupCode3;
                if (!(code == null || code.length() == 0) || (popupCode3 = MainActivity.this.getPopupCode()) == null) {
                    return;
                }
                popupCode3.dismiss();
            }

            @Override // com.yddmi.doctor.pages.main.PopupCode.OnPopupCodeClickListener
            public void onCodeSuccess() {
                PopupCode popupCode3 = MainActivity.this.getPopupCode();
                if (popupCode3 != null) {
                    popupCode3.dismiss();
                }
                MainActivity.this.dealGet();
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopMsg(HomeMsg msgData) {
        final PopupMsg popupMsg = new PopupMsg(this);
        popupMsg.setData(msgData);
        popupMsg.setOnPopupMsgClickListener(new PopupMsg.OnPopupMsgClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewPopMsg.1
            @Override // com.yddmi.doctor.pages.main.PopupMsg.OnPopupMsgClickListener
            public void onMsgClick(@Nullable View view, @NotNull HomeMsg msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                msg.setRead(1);
                MainActivity.this.httpReadNoticeMessageList(msg);
                YddConfig.INSTANCE.getKvData().put(YddConfig.KV_MSG_DETAILS, msg);
                MainActivity mainActivity = MainActivity.this;
                Intent intent = new Intent();
                intent.setClass(mainActivity, MsgDetailActivity.class);
                mainActivity.startActivity(intent);
                popupMsg.dismiss();
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupMsg).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopRule() {
        PopupRule popupRule = new PopupRule(this);
        String mRuleData = getViewModel().getMRuleData();
        Intrinsics.checkNotNull(mRuleData);
        popupRule.setData(mRuleData);
        popupRule.setOnPopupRuleClickListener(new PopupRule.OnPopupRuleClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewPopRule.1
            @Override // com.yddmi.doctor.pages.main.PopupRule.OnPopupRuleClickListener
            public void onClick(@Nullable View view) {
                MainActivity.this.httpPostIntegralFreeReceive();
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewRuleAnimation() {
        final Ref.IntRef intRef = new Ref.IntRef();
        int iDp2px = CommonExtKt.dp2px(this, 82);
        if (this.mOpen) {
            intRef.element = iDp2px;
            iDp2px = 0;
        } else {
            intRef.element = 0;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(iDp2px, intRef.element);
        valueAnimatorOfInt.setDuration(360L);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.yddmi.doctor.pages.main.c
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MainActivity.viewRuleAnimation$lambda$2(this.f25917c, valueAnimator);
            }
        });
        valueAnimatorOfInt.addListener(new Animator.AnimatorListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewRuleAnimation.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                MainActivity.this.mOpen = intRef.element == 0;
                if (!MainActivity.this.mOpen) {
                    removeMessages(103);
                    MainActivity.access$getBodyBinding(MainActivity.this).ruleImgv.setVisibility(0);
                } else {
                    removeMessages(103);
                    sendEmptyMessageDelayed(103, MainActivity.this.mRuleCloseTime);
                    MainActivity.access$getBodyBinding(MainActivity.this).ruleImgv.setVisibility(8);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(@NotNull Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
            }
        });
        valueAnimatorOfInt.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void viewRuleAnimation$lambda$2(MainActivity this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) animatedValue).intValue();
        ViewGroup.LayoutParams layoutParams = ((MainActivityBinding) this$0.getBodyBinding()).ruleTv.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.setMarginStart(iIntValue);
        ((MainActivityBinding) this$0.getBodyBinding()).ruleTv.setLayoutParams(layoutParams2);
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

    private final void viewShowCall() {
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(new PopupExamCall(this)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowLabel() {
        List<SkillHome> labelList = getViewModel().getLabelList();
        if (labelList == null || labelList.isEmpty()) {
            ((MainActivityBinding) getBodyBinding()).tableBfl.setVisibility(8);
            return;
        }
        ((MainActivityBinding) getBodyBinding()).tableBfl.setVisibility(0);
        DslTabLayout dslTabLayout = ((MainActivityBinding) getBodyBinding()).dslTablayout;
        dslTabLayout.removeAllViews();
        List<SkillHome> labelList2 = getViewModel().getLabelList();
        if (labelList2 != null) {
            for (SkillHome skillHome : labelList2) {
                TextView textView = new TextView(dslTabLayout.getContext());
                textView.setText(skillHome.getLabel());
                textView.setGravity(17);
                dslTabLayout.addView(textView);
            }
        }
        dslTabLayout.setCurrentItem(0, true, false);
        dslTabLayout.updateTabLayout();
        DslTabLayout dslTabLayout2 = ((MainActivityBinding) getBodyBinding()).dslTablayout;
        Intrinsics.checkNotNullExpressionValue(dslTabLayout2, "bodyBinding.dslTablayout");
        DslTabLayout.observeIndexChange$default(dslTabLayout2, null, new Function4<Integer, Integer, Boolean, Boolean, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewShowLabel.2
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2, Boolean bool, Boolean bool2) {
                invoke(num.intValue(), num2.intValue(), bool.booleanValue(), bool2.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2, int i3, boolean z2, boolean z3) {
                LogExtKt.logd("标签切换 " + i2 + " " + i3 + " " + z2 + " " + z3, MainActivity.TAG);
                if (z2 || !z3 || MainActivity.this.getViewModel().getLabelList() == null) {
                    return;
                }
                List<SkillHome> labelList3 = MainActivity.this.getViewModel().getLabelList();
                Intrinsics.checkNotNull(labelList3);
                if (labelList3.size() > i3) {
                    List<SkillHome> labelList4 = MainActivity.this.getViewModel().getLabelList();
                    Intrinsics.checkNotNull(labelList4);
                    SkillHome skillHome2 = labelList4.get(i3);
                    MainActivity.this.getViewModel().dealChangeLabel(skillHome2.getSkills());
                    YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                    String label = skillHome2.getLabel();
                    if (label.length() == 0) {
                        label = skillHome2.getName();
                    }
                    companion.addPoint("SJ-A-HOME-BASEOPERATE-LEVELTWO", label, String.valueOf(skillHome2.getSkillCategoryId()));
                }
            }
        }, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowTipPop(String str) {
        if (this.popupDialog == null) {
            setPopupDialog(new PopupTipDialog(this, false, 2, null));
        }
        if (getPopupDialog().isShow()) {
            return;
        }
        new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasStatusBar(false).isViewMode(true).hasBlurBg(true).hasNavigationBar(false).dismissOnBackPressed(Boolean.TRUE).dismissOnTouchOutside(Boolean.FALSE).asCustom(getPopupDialog()).show();
        PopupTipDialog popupDialog = getPopupDialog();
        int i2 = R.color.c_2a57c2;
        PopupTipDialog.setTipDialog$default(popupDialog.setTitelColorSize(ContextExtKt.getColorM(this, i2), 26).setTipColorSize(ContextExtKt.getColorM(this, i2), 20), str, null, null, null, 0, 30, null).setOnPopupTipDialogClickListener(new PopupTipDialog.OnPopupTipDialogClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.viewShowTipPop.2
            @Override // com.yddmi.doctor.widget.PopupTipDialog.OnPopupTipDialogClickListener
            public void onClick(@NotNull View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                MainActivity.this.dealGet();
            }
        });
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

    @NotNull
    public final String getGoodsId() {
        return this.goodsId;
    }

    @NotNull
    public final AdapterCheck getLeftAdapter() {
        AdapterCheck adapterCheck = this.leftAdapter;
        if (adapterCheck != null) {
            return adapterCheck;
        }
        Intrinsics.throwUninitializedPropertyAccessException("leftAdapter");
        return null;
    }

    @NotNull
    public final LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager linearLayoutManager = this.linearLayoutManager;
        if (linearLayoutManager != null) {
            return linearLayoutManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("linearLayoutManager");
        return null;
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
    public final PopupBuy getPopupBuy() {
        return this.popupBuy;
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

    @NotNull
    public final AdapterSkill1 getRightAdapter1() {
        AdapterSkill1 adapterSkill1 = this.rightAdapter1;
        if (adapterSkill1 != null) {
            return adapterSkill1;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rightAdapter1");
        return null;
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        LogExtKt.logd("initFlow", TAG);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07911(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C07922(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass3(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass4(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass5(null), 3, null);
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
        setLeftAdapter(new AdapterCheck(R.drawable.home_left_item_bg));
        setRightAdapter1(new AdapterSkill1());
        getRightAdapter1().setShareUnlockListener(new AdapterSkill1.ShareUnlockListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.initParam.1
            @Override // com.yddmi.doctor.pages.main.AdapterSkill1.ShareUnlockListener
            public void shareClick(int position) {
                LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(new Intent().setAction("SHARE_SHENG_YUN").putExtra("query", false));
            }
        });
        getLeftAdapter().setOnItemClickListener(new Function2<SkillHome, Integer, Unit>() { // from class: com.yddmi.doctor.pages.main.MainActivity.initParam.2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(SkillHome skillHome, Integer num) {
                invoke(skillHome, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull SkillHome m2, int i2) {
                Intrinsics.checkNotNullParameter(m2, "m");
                if (i2 != MainActivity.this.getViewModel().getLeftLastIndex()) {
                    MainActivity.this.getViewModel().getLeftList().get(MainActivity.this.getViewModel().getLeftLastIndex()).setMSelected(false);
                    MainActivity.this.getViewModel().getLeftList().get(i2).setMSelected(true);
                    MainActivity.this.getLeftAdapter().notifyItemChanged(MainActivity.this.getViewModel().getLeftLastIndex());
                    MainActivity.this.getLeftAdapter().notifyItemChanged(i2);
                    MainActivity.this.getViewModel().changeLeftIndex(i2);
                    LogExtKt.logd("埋点 " + m2.getSkillCategoryId(), MainActivity.TAG);
                    YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                    String label = m2.getLabel();
                    if (label.length() == 0) {
                        label = m2.getName();
                    }
                    companion.addPoint("SJ-A-HOME-BASEOPERATE-LEVELONE", label, String.valueOf(m2.getSkillCategoryId()));
                }
            }
        });
        getRightAdapter1().setOnItemChildClickListener(new AdapterSkill1.OnItemChildClickListener() { // from class: com.yddmi.doctor.pages.main.MainActivity.initParam.3
            @Override // com.yddmi.doctor.pages.main.AdapterSkill1.OnItemChildClickListener
            public void onClick(@NotNull View view, int position, @NotNull SkillHome m2) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(m2, "m");
                if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(MainActivity.this)) {
                    if (view.getId() == R.id.shareCl) {
                        MainActivity.this.httpPostIntegralAcquire(m2);
                        YddPointManager companion = YddPointManager.INSTANCE.getInstance();
                        String label = m2.getLabel();
                        if (label.length() == 0) {
                            label = m2.getName();
                        }
                        companion.addPoint("SJ-A-HOME-BASEOPERATE-SHAREING", "分享有礼(" + ((Object) label) + ")", String.valueOf(m2.getId()));
                        return;
                    }
                    if (1 == m2.getLockStatus() || 1 == m2.isPlay()) {
                        if (m2.getUrl().length() == 0) {
                            Toaster.show(R.string.home_ongoing_tip);
                        } else if (NoDoubleClickUtil.isNoDoubleClick(1500)) {
                            MainActivity.this.loadingDialog();
                            Intent intent = new Intent();
                            intent.putExtra("typeId", m2.getId());
                            intent.putExtra("url", m2.getUrl());
                            intent.putExtra("isPlay", m2.isPlay());
                            intent.putExtra("skillType", m2.getType());
                            intent.putExtra("CurrentProject", 1);
                            MainActivity mainActivity = MainActivity.this;
                            intent.setClass(mainActivity, U3dGoActivity.class);
                            mainActivity.startActivity(intent);
                            MainActivity mainActivity2 = MainActivity.this;
                            Intent intent2 = new Intent();
                            intent2.setClass(mainActivity2, U3dActivity.class);
                            mainActivity2.startActivity(intent2);
                            sendEmptyMessageDelayed(102, 1200L);
                        }
                    } else {
                        MainActivity.this.requestShengYunGoods();
                    }
                    MainActivity.this.getRightAdapter1().setCurrentSelectItem(m2);
                    MainActivity.this.getRightAdapter1().notifyItemChanged(position);
                    if (MainActivity.this.rightLastClickIndex > -1) {
                        MainActivity.this.getRightAdapter1().notifyItemChanged(MainActivity.this.rightLastClickIndex);
                    }
                    MainActivity.this.rightLastClickIndex = position;
                    YddPointManager companion2 = YddPointManager.INSTANCE.getInstance();
                    String label2 = m2.getLabel();
                    if (label2.length() == 0) {
                        label2 = m2.getName();
                    }
                    companion2.addPoint("SJ-A-HOME-BASEOPERATE-SKILL", label2, String.valueOf(m2.getId()));
                }
            }
        });
        registerReceiver(this.shengyunReceiver, new IntentFilter(GlobalAction.ACTION_RECEIVE_SHENGYUN_GOODS));
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        setFinalPopup(new PopupCommonDialog(this));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("SHARE_SUCCESS");
        registerReceiver(this.mReceiver, intentFilter);
        updateTitleLine(false);
        updateStatusBarTransparent();
        sendEmptyMessageDelayed(100, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        sendEmptyMessageDelayed(103, this.mRuleCloseTime);
        bodyBinding(new C07961());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        removeCallbacksAndMessages(null);
        if (!isDestroyed()) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent().setAction("DOCTOR_TOP").putExtra("create", false));
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);
        }
        BusUtils.unregister(this);
        unregisterReceiver(this.shengyunReceiver);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        Object next;
        super.onResume();
        GlobalAction globalAction = GlobalAction.INSTANCE;
        if (globalAction.getHomeDataRefresh()) {
            globalAction.setHomeDataRefresh(false);
            getViewModel().httpGetSkillBasicHome();
        }
        if (globalAction.getHomeShowBuyStatus()) {
            globalAction.setHomeShowBuyStatus(false);
            sendEmptyMessageDelayed(105, 300L);
        }
        if (getRightAdapter1() != null) {
            Iterator<T> it = getRightAdapter1().getData().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (((SkillHome) next).getLockStatus() == 0) {
                        break;
                    }
                }
            }
            SkillHome skillHome = (SkillHome) next;
            if (skillHome != null && skillHome.isPlay() == 1) {
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent().setAction("SHARE_SHENG_YUN").putExtra("query", true));
            }
        }
        httpPostCouponRecord2();
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

    public final void setGoodsId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.goodsId = str;
    }

    public final void setLeftAdapter(@NotNull AdapterCheck adapterCheck) {
        Intrinsics.checkNotNullParameter(adapterCheck, "<set-?>");
        this.leftAdapter = adapterCheck;
    }

    public final void setLinearLayoutManager(@NotNull LinearLayoutManager linearLayoutManager) {
        Intrinsics.checkNotNullParameter(linearLayoutManager, "<set-?>");
        this.linearLayoutManager = linearLayoutManager;
    }

    public final void setPopupAd(@NotNull PopupAd popupAd) {
        Intrinsics.checkNotNullParameter(popupAd, "<set-?>");
        this.popupAd = popupAd;
    }

    public final void setPopupBuy(@Nullable PopupBuy popupBuy) {
        this.popupBuy = popupBuy;
    }

    public final void setPopupCode(@Nullable PopupCode popupCode) {
        this.popupCode = popupCode;
    }

    public final void setPopupDialog(@NotNull PopupTipDialog popupTipDialog) {
        Intrinsics.checkNotNullParameter(popupTipDialog, "<set-?>");
        this.popupDialog = popupTipDialog;
    }

    public final void setRightAdapter1(@NotNull AdapterSkill1 adapterSkill1) {
        Intrinsics.checkNotNullParameter(adapterSkill1, "<set-?>");
        this.rightAdapter1 = adapterSkill1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowMarquee(@Nullable List<String> data) {
        if (data == null) {
            ((MainActivityBinding) getBodyBinding()).margueeCl.setVisibility(8);
        } else {
            ((MainActivityBinding) getBodyBinding()).margueeCl.setVisibility(0);
            ((MainActivityBinding) getBodyBinding()).margueeV.startWithList(data);
        }
    }
}
