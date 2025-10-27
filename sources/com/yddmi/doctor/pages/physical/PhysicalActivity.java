package com.yddmi.doctor.pages.physical;

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
import com.catchpig.mvvm.utils.GsonUtil;
import com.catchpig.mvvm.utils.NoDoubleClickUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.PhysicalActivityBinding;
import com.yddmi.doctor.entity.request.IntegralAcquireReq;
import com.yddmi.doctor.entity.request.IntegralUnlockReq;
import com.yddmi.doctor.entity.request.ShengYunGoodsBean;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.pages.main.AdapterCheck;
import com.yddmi.doctor.pages.main.AdapterSkill1;
import com.yddmi.doctor.pages.physical.PhysicalActivity;
import com.yddmi.doctor.pages.product.ProductActivity;
import com.yddmi.doctor.pages.u3d.U3dActivity;
import com.yddmi.doctor.pages.u3d.U3dGoActivity;
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
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000s\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\t*\u0003\u001e!2\b\u0007\u0018\u0000 Q2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001QB\u0005¢\u0006\u0002\u0010\u0004J\b\u00105\u001a\u000206H\u0002J\u001a\u00107\u001a\u0002062\u0006\u00108\u001a\u0002002\b\b\u0002\u00109\u001a\u00020\fH\u0002J\b\u0010:\u001a\u000206H\u0002J\u0010\u0010;\u001a\u0002062\u0006\u0010<\u001a\u00020=H\u0002J\b\u0010>\u001a\u000206H\u0002J\u0010\u0010?\u001a\u0002062\u0006\u0010<\u001a\u00020=H\u0002J\b\u0010@\u001a\u000206H\u0016J\b\u0010A\u001a\u000206H\u0016J\b\u0010B\u001a\u000206H\u0016J\b\u0010C\u001a\u000206H\u0014J\b\u0010D\u001a\u000206H\u0014J\b\u0010E\u001a\u000206H\u0014J\b\u0010F\u001a\u000206H\u0014J\u0010\u0010G\u001a\u0002062\u0006\u0010H\u001a\u00020IH\u0016J\u0012\u0010J\u001a\u0002062\b\u0010<\u001a\u0004\u0018\u00010\fH\u0002J\b\u0010K\u001a\u000206H\u0002J\b\u0010L\u001a\u000206H\u0016J\b\u0010M\u001a\u000206H\u0002J\b\u0010N\u001a\u000206H\u0002J\u0010\u0010O\u001a\u0002062\u0006\u0010P\u001a\u00020\fH\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u0010\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\"R\u001a\u0010#\u001a\u00020$X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u001a\u0010)\u001a\u00020*X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u000e\u0010/\u001a\u000200X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u000202X\u0082\u0004¢\u0006\u0004\n\u0002\u00103R\u000e\u00104\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006R"}, d2 = {"Lcom/yddmi/doctor/pages/physical/PhysicalActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/PhysicalActivityBinding;", "Lcom/yddmi/doctor/pages/physical/PhysicalViewModel;", "()V", "finalPopup", "Lcom/yddmi/doctor/widget/PopupCommonDialog;", "getFinalPopup", "()Lcom/yddmi/doctor/widget/PopupCommonDialog;", "setFinalPopup", "(Lcom/yddmi/doctor/widget/PopupCommonDialog;)V", "goodsId", "", "getGoodsId", "()Ljava/lang/String;", "setGoodsId", "(Ljava/lang/String;)V", "leftAdapter", "Lcom/yddmi/doctor/pages/main/AdapterCheck;", "getLeftAdapter", "()Lcom/yddmi/doctor/pages/main/AdapterCheck;", "setLeftAdapter", "(Lcom/yddmi/doctor/pages/main/AdapterCheck;)V", "linearLayoutManager", "Landroidx/recyclerview/widget/LinearLayoutManager;", "getLinearLayoutManager", "()Landroidx/recyclerview/widget/LinearLayoutManager;", "setLinearLayoutManager", "(Landroidx/recyclerview/widget/LinearLayoutManager;)V", "mHandler", "com/yddmi/doctor/pages/physical/PhysicalActivity$mHandler$1", "Lcom/yddmi/doctor/pages/physical/PhysicalActivity$mHandler$1;", "mReceiver", "com/yddmi/doctor/pages/physical/PhysicalActivity$mReceiver$1", "Lcom/yddmi/doctor/pages/physical/PhysicalActivity$mReceiver$1;", "popupDialog", "Lcom/yddmi/doctor/widget/PopupTipDialog;", "getPopupDialog", "()Lcom/yddmi/doctor/widget/PopupTipDialog;", "setPopupDialog", "(Lcom/yddmi/doctor/widget/PopupTipDialog;)V", "rightAdapter1", "Lcom/yddmi/doctor/pages/main/AdapterSkill1;", "getRightAdapter1", "()Lcom/yddmi/doctor/pages/main/AdapterSkill1;", "setRightAdapter1", "(Lcom/yddmi/doctor/pages/main/AdapterSkill1;)V", "rightLastClickIndex", "", "shengyunReceiver", "com/yddmi/doctor/pages/physical/PhysicalActivity$shengyunReceiver$1", "Lcom/yddmi/doctor/pages/physical/PhysicalActivity$shengyunReceiver$1;", "titleStr", "dealGet", "", "dealGoBuyAll", "type", "skillId", "httpGetPersonInfo", "httpPostIntegralAcquire", "data", "Lcom/yddmi/doctor/entity/result/SkillHome;", "httpPostIntegralFreeReceive", "httpPostIntegralUnlock", "initFlow", "initParam", "initView", "onDestroy", "onResume", "onStart", "onStop", "onWindowFocusChanged", "hasFocus", "", "processShengYunData", "requestShengYunGoods", "screenOrientation", "viewSetImmersionBar", "viewShowLabel", "viewShowTipPop", "str", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nPhysicalActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PhysicalActivity.kt\ncom/yddmi/doctor/pages/physical/PhysicalActivity\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n+ 4 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,534:1\n1#2:535\n1#2:541\n15#3,3:536\n18#4,2:539\n1855#5,2:542\n*S KotlinDebug\n*F\n+ 1 PhysicalActivity.kt\ncom/yddmi/doctor/pages/physical/PhysicalActivity\n*L\n391#1:541\n385#1:536,3\n391#1:539,2\n432#1:542,2\n*E\n"})
/* loaded from: classes6.dex */
public final class PhysicalActivity extends BaseVMActivity<PhysicalActivityBinding, PhysicalViewModel> {

    @NotNull
    private static final String TAG = "PhysicalActivity";
    public PopupCommonDialog finalPopup;
    public AdapterCheck leftAdapter;
    public LinearLayoutManager linearLayoutManager;

    @NotNull
    private final PhysicalActivity$mHandler$1 mHandler;
    public PopupTipDialog popupDialog;
    public AdapterSkill1 rightAdapter1;

    @NotNull
    private final PhysicalActivity$shengyunReceiver$1 shengyunReceiver;
    private int rightLastClickIndex = -1;

    @NotNull
    private PhysicalActivity$mReceiver$1 mReceiver = new BroadcastReceiver() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity$mReceiver$1
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

    @NotNull
    private String titleStr = "";

    @NotNull
    private String goodsId = "";

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$1", f = "PhysicalActivity.kt", i = {}, l = {312}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08491 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08491(Continuation<? super C08491> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return PhysicalActivity.this.new C08491(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08491) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> leftListMsf = PhysicalActivity.this.getViewModel().getLeftListMsf();
                final PhysicalActivity physicalActivity = PhysicalActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        physicalActivity.hideLoading();
                        PhysicalActivity physicalActivity2 = physicalActivity;
                        if (physicalActivity2.popupDialog != null) {
                            physicalActivity2.getPopupDialog().dismiss();
                        }
                        physicalActivity.getLeftAdapter().set(physicalActivity.getViewModel().getLeftList());
                        physicalActivity.getViewModel().changeLeftIndex(0);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$2", f = "PhysicalActivity.kt", i = {}, l = {322}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C08502 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08502(Continuation<? super C08502> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return PhysicalActivity.this.new C08502(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08502) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> rightListChange = PhysicalActivity.this.getViewModel().getRightListChange();
                final PhysicalActivity physicalActivity = PhysicalActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        physicalActivity.hideLoading();
                        if (j2 > 0) {
                            physicalActivity.getRightAdapter1().set(physicalActivity.getViewModel().getRightList());
                            removeMessages(101);
                            sendEmptyMessageDelayed(101, 160L);
                            Iterator<T> it = physicalActivity.getRightAdapter1().getData().iterator();
                            boolean z2 = false;
                            while (it.hasNext()) {
                                if (((SkillHome) it.next()).isPlay() == 1) {
                                    z2 = true;
                                }
                            }
                            if (z2) {
                                LocalBroadcastManager.getInstance(physicalActivity).sendBroadcast(new Intent().setAction("SHARE_SHENG_YUN").putExtra("query", true));
                                LocalBroadcastManager.getInstance(physicalActivity).sendBroadcast(new Intent().setAction("DOCTOR_TOP").putExtra("create", true));
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$3", f = "PhysicalActivity.kt", i = {}, l = {R2.attr.arcBlockAngle}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return PhysicalActivity.this.new AnonymousClass3(continuation);
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
                MutableStateFlow<Long> getSkillErrorMsf = PhysicalActivity.this.getViewModel().getGetSkillErrorMsf();
                final PhysicalActivity physicalActivity = PhysicalActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.initFlow.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        physicalActivity.hideLoading();
                        if (j2 > 0) {
                            String skillGetError = physicalActivity.getViewModel().getSkillGetError();
                            Toaster.show((CharSequence) (skillGetError == null || skillGetError.length() == 0 ? physicalActivity.getString(R.string.common_network_error) : physicalActivity.getViewModel().getSkillGetError()));
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$4", f = "PhysicalActivity.kt", i = {}, l = {R2.attr.arcMax}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalActivity$initFlow$4, reason: invalid class name */
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return PhysicalActivity.this.new AnonymousClass4(continuation);
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
                MutableStateFlow<Long> getLabelMsf = PhysicalActivity.this.getViewModel().getGetLabelMsf();
                final PhysicalActivity physicalActivity = PhysicalActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.initFlow.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            physicalActivity.viewShowLabel();
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/PhysicalActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08541 extends Lambda implements Function1<PhysicalActivityBinding, Unit> {
        public C08541() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(PhysicalActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(PhysicalActivityBinding physicalActivityBinding) {
            invoke2(physicalActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull PhysicalActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView iconImgv = bodyBinding.iconImgv;
            Intrinsics.checkNotNullExpressionValue(iconImgv, "iconImgv");
            final PhysicalActivity physicalActivity = PhysicalActivity.this;
            ViewExtKt.setDebounceClickListener$default(iconImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.physical.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PhysicalActivity.C08541.invoke$lambda$0(physicalActivity, view);
                }
            }, 0L, 2, null);
            bodyBinding.titleTv.setText(PhysicalActivity.this.titleStr);
            RecyclerView recyclerView = bodyBinding.leftRv;
            PhysicalActivity physicalActivity2 = PhysicalActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(physicalActivity2.getLeftAdapter());
            RecyclerView recyclerView2 = bodyBinding.rightRv;
            PhysicalActivity physicalActivity3 = PhysicalActivity.this;
            physicalActivity3.setLinearLayoutManager(new LinearLayoutManager(recyclerView2.getContext()));
            physicalActivity3.getLinearLayoutManager().setOrientation(1);
            recyclerView2.setLayoutManager(physicalActivity3.getLinearLayoutManager());
            recyclerView2.setAdapter(physicalActivity3.getRightAdapter1());
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.yddmi.doctor.pages.physical.PhysicalActivity$mReceiver$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.yddmi.doctor.pages.physical.PhysicalActivity$shengyunReceiver$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.physical.PhysicalActivity$mHandler$1] */
    public PhysicalActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (msg.what != 102) {
                    return;
                }
                this.this$0.hideLoading();
            }
        };
        this.shengyunReceiver = new BroadcastReceiver() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity$shengyunReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(@NotNull Context context, @NotNull Intent intent) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(intent, "intent");
                if (Intrinsics.areEqual(GlobalAction.ACTION_RECEIVE_SHENGYUN_GOODS, intent.getAction())) {
                    this.this$0.processShengYunData(intent.getStringExtra(GlobalAction.EXTRA_SHENGYUN_GOODS_DATA));
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGet() {
        getViewModel().httpGetSkillBodyHome();
        getViewModel().httpGetIntegralApp();
        httpGetPersonInfo();
    }

    private final void dealGoBuyAll(int type, String skillId) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("name", "");
        intent.putExtra("skillId", skillId);
        intent.putExtra("skill24", 3);
        intent.setClass(this, ProductActivity.class);
        startActivity(intent);
    }

    public static /* synthetic */ void dealGoBuyAll$default(PhysicalActivity physicalActivity, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "-1";
        }
        physicalActivity.dealGoBuyAll(i2, str);
    }

    private final void httpGetPersonInfo() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getPersonInfoApp(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpGetPersonInfo.1
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
            }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpGetPersonInfo.2
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

    private final void httpPostIntegralAcquire(SkillHome data) {
        String id = data.getId();
        if (id == null) {
            id = "";
        }
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postIntegralAcquire(new IntegralAcquireReq(id, companion.getInstance().userNickName(), companion.getInstance().userName(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpPostIntegralAcquire.1
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
        }, new Function1<SkillShare, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpPostIntegralAcquire.2
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
                    PhysicalActivity.this.getViewModel().dealWxShare(skillShare);
                }
            }
        });
    }

    private final void httpPostIntegralFreeReceive() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postIntegralFreeReceive(new IntegralAcquireReq("-1", companion.getInstance().userNickName(), companion.getInstance().userName(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpPostIntegralFreeReceive.1
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
        }, new Function1<SkillShare, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpPostIntegralFreeReceive.2
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
                    PhysicalActivity.this.getViewModel().dealWxShare(skillShare);
                }
            }
        });
    }

    private final void httpPostIntegralUnlock(SkillHome data) {
        String id = data.getId();
        if (id == null) {
            id = "";
        }
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postIntegralUnlock(new IntegralUnlockReq(id, data.getSkillExchangeIntegral(), YddUserManager.INSTANCE.getInstance().userName())), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpPostIntegralUnlock.1
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
        }, new Function1<Boolean, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.httpPostIntegralUnlock.2
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
                    Toaster.show(R.string.home_integral_tip);
                    return;
                }
                PhysicalActivity.this.getViewModel().httpGetSkillBodyHome();
                PhysicalActivity.this.getViewModel().httpGetIntegralApp();
                Toaster.show(R.string.home_integral_tip_success);
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

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowLabel() {
        List<SkillHome> labelList = getViewModel().getLabelList();
        LogExtKt.logd("viewShowLabel " + (labelList == null || labelList.isEmpty()), TAG);
        List<SkillHome> labelList2 = getViewModel().getLabelList();
        if (labelList2 == null || labelList2.isEmpty()) {
            ((PhysicalActivityBinding) getBodyBinding()).tableBfl.setVisibility(8);
            return;
        }
        ((PhysicalActivityBinding) getBodyBinding()).tableBfl.setVisibility(0);
        DslTabLayout dslTabLayout = ((PhysicalActivityBinding) getBodyBinding()).dslTablayout;
        dslTabLayout.removeAllViews();
        List<SkillHome> labelList3 = getViewModel().getLabelList();
        if (labelList3 != null) {
            for (SkillHome skillHome : labelList3) {
                TextView textView = new TextView(dslTabLayout.getContext());
                textView.setText(skillHome.getLabel());
                textView.setGravity(17);
                dslTabLayout.addView(textView);
            }
        }
        dslTabLayout.setCurrentItem(0, true, false);
        dslTabLayout.updateTabLayout();
        DslTabLayout dslTabLayout2 = ((PhysicalActivityBinding) getBodyBinding()).dslTablayout;
        Intrinsics.checkNotNullExpressionValue(dslTabLayout2, "bodyBinding.dslTablayout");
        DslTabLayout.observeIndexChange$default(dslTabLayout2, null, new Function4<Integer, Integer, Boolean, Boolean, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.viewShowLabel.2
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2, Boolean bool, Boolean bool2) {
                invoke(num.intValue(), num2.intValue(), bool.booleanValue(), bool2.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2, int i3, boolean z2, boolean z3) {
                LogExtKt.logd("标签切换 " + i2 + " " + i3 + " " + z2 + " " + z3, PhysicalActivity.TAG);
                if (z2 || !z3 || PhysicalActivity.this.getViewModel().getLabelList() == null) {
                    return;
                }
                List<SkillHome> labelList4 = PhysicalActivity.this.getViewModel().getLabelList();
                Intrinsics.checkNotNull(labelList4);
                if (labelList4.size() > i3) {
                    List<SkillHome> labelList5 = PhysicalActivity.this.getViewModel().getLabelList();
                    Intrinsics.checkNotNull(labelList5);
                    PhysicalActivity.this.getViewModel().dealChangeLabel(labelList5.get(i3).getSkills());
                }
            }
        }, 1, null);
    }

    private final void viewShowTipPop(String str) {
        boolean z2 = false;
        if (this.popupDialog == null) {
            setPopupDialog(new PopupTipDialog(this, z2, 2, null));
        }
        if (getPopupDialog().isShow()) {
            return;
        }
        XPopup.Builder builderHasNavigationBar = new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasStatusBar(false).isViewMode(true).hasBlurBg(true).hasNavigationBar(false);
        Boolean bool = Boolean.TRUE;
        builderHasNavigationBar.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(getPopupDialog()).show();
        PopupTipDialog popupDialog = getPopupDialog();
        int i2 = R.color.c_2a57c2;
        PopupTipDialog.setTipDialog$default(popupDialog.setTitelColorSize(ContextExtKt.getColorM(this, i2), 26).setTipColorSize(ContextExtKt.getColorM(this, i2), 20), str, null, null, null, 0, 30, null).setOnPopupTipDialogClickListener(new PopupTipDialog.OnPopupTipDialogClickListener() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.viewShowTipPop.2
            @Override // com.yddmi.doctor.widget.PopupTipDialog.OnPopupTipDialogClickListener
            public void onClick(@NotNull View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                PhysicalActivity.this.dealGet();
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
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08491(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08502(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass3(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass4(null), 3, null);
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
        String str;
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra("title");
            if (stringExtra == null) {
                stringExtra = getString(R.string.physical_title);
                str = "getString(R.string.physical_title)";
            } else {
                str = "it.getStringExtra(\"title…(R.string.physical_title)";
            }
            Intrinsics.checkNotNullExpressionValue(stringExtra, str);
            this.titleStr = stringExtra;
            PhysicalViewModel viewModel = getViewModel();
            String stringExtra2 = intent.getStringExtra("code");
            if (stringExtra2 == null) {
                stringExtra2 = "TGJC";
            } else {
                Intrinsics.checkNotNullExpressionValue(stringExtra2, "it.getStringExtra(\"code\") ?: \"TGJC\"");
            }
            viewModel.setCode(stringExtra2);
        }
        viewSetImmersionBar();
        setLeftAdapter(new AdapterCheck(R.drawable.home_left_item_bg));
        setRightAdapter1(new AdapterSkill1());
        getRightAdapter1().setShareUnlockListener(new AdapterSkill1.ShareUnlockListener() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.initParam.2
            @Override // com.yddmi.doctor.pages.main.AdapterSkill1.ShareUnlockListener
            public void shareClick(int position) {
                LocalBroadcastManager.getInstance(PhysicalActivity.this).sendBroadcast(new Intent().setAction("SHARE_SHENG_YUN").putExtra("query", false));
            }
        });
        getLeftAdapter().setOnItemClickListener(new Function2<SkillHome, Integer, Unit>() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.initParam.3
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
                if (i2 != PhysicalActivity.this.getViewModel().getLeftLastIndex()) {
                    PhysicalActivity.this.getViewModel().getLeftList().get(PhysicalActivity.this.getViewModel().getLeftLastIndex()).setMSelected(false);
                    PhysicalActivity.this.getViewModel().getLeftList().get(i2).setMSelected(true);
                    PhysicalActivity.this.getLeftAdapter().notifyItemChanged(PhysicalActivity.this.getViewModel().getLeftLastIndex());
                    PhysicalActivity.this.getLeftAdapter().notifyItemChanged(i2);
                    PhysicalActivity.this.getViewModel().changeLeftIndex(i2);
                }
            }
        });
        getRightAdapter1().setOnItemChildClickListener(new AdapterSkill1.OnItemChildClickListener() { // from class: com.yddmi.doctor.pages.physical.PhysicalActivity.initParam.4
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
             */
            @Override // com.yddmi.doctor.pages.main.AdapterSkill1.OnItemChildClickListener
            public void onClick(@NotNull View view, int position, @NotNull SkillHome m2) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(m2, "m");
                if (YddUserManager.INSTANCE.getInstance().userIsLoginGo(PhysicalActivity.this)) {
                    if (1 == m2.getLockStatus()) {
                        if (m2.getUrl().length() == 0) {
                            Toaster.show(R.string.home_ongoing_tip);
                        } else if (NoDoubleClickUtil.isNoDoubleClick(1500)) {
                            PhysicalActivity.this.loadingDialog();
                            Intent intent2 = new Intent();
                            intent2.putExtra("typeId", String.valueOf(m2.getId()));
                            intent2.putExtra("url", m2.getUrl());
                            intent2.putExtra("isPlay", m2.isPlay());
                            intent2.putExtra("skillType", m2.getType());
                            String code = PhysicalActivity.this.getViewModel().getCode();
                            switch (code.hashCode()) {
                                case 2194413:
                                    if (code.equals("GPJN")) {
                                        intent2.putExtra("CurrentProject", 4);
                                        break;
                                    }
                                    break;
                                case 2220360:
                                    if (code.equals("HLJN")) {
                                        intent2.putExtra("CurrentProject", 3);
                                        break;
                                    }
                                    break;
                                case 2314538:
                                    if (code.equals("KQJN")) {
                                        intent2.putExtra("CurrentProject", 5);
                                        break;
                                    }
                                    break;
                                case 2573036:
                                    if (code.equals("TGJC")) {
                                        intent2.putExtra("CurrentProject", 2);
                                        break;
                                    }
                                    break;
                            }
                            PhysicalActivity physicalActivity = PhysicalActivity.this;
                            intent2.setClass(physicalActivity, U3dGoActivity.class);
                            physicalActivity.startActivity(intent2);
                            PhysicalActivity physicalActivity2 = PhysicalActivity.this;
                            Intent intent3 = new Intent();
                            intent3.setClass(physicalActivity2, U3dActivity.class);
                            physicalActivity2.startActivity(intent3);
                            sendEmptyMessageDelayed(102, 1200L);
                        }
                    } else {
                        PhysicalActivity.this.requestShengYunGoods();
                    }
                    PhysicalActivity.this.getRightAdapter1().setCurrentSelectItem(m2);
                    PhysicalActivity.this.getRightAdapter1().notifyItemChanged(position);
                    if (PhysicalActivity.this.rightLastClickIndex > -1) {
                        PhysicalActivity.this.getRightAdapter1().notifyItemChanged(PhysicalActivity.this.rightLastClickIndex);
                    }
                    PhysicalActivity.this.rightLastClickIndex = position;
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
        bodyBinding(new C08541());
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
        if (globalAction.getPhysicalDataRefresh()) {
            globalAction.setPhysicalDataRefresh(false);
            getViewModel().httpGetSkillBodyHome();
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
            if (skillHome == null || skillHome.isPlay() != 1) {
                return;
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent().setAction("SHARE_SHENG_YUN").putExtra("query", true));
        }
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

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(6);
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

    public final void setPopupDialog(@NotNull PopupTipDialog popupTipDialog) {
        Intrinsics.checkNotNullParameter(popupTipDialog, "<set-?>");
        this.popupDialog = popupTipDialog;
    }

    public final void setRightAdapter1(@NotNull AdapterSkill1 adapterSkill1) {
        Intrinsics.checkNotNullParameter(adapterSkill1, "<set-?>");
        this.rightAdapter1 = adapterSkill1;
    }
}
