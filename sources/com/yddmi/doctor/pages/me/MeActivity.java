package com.yddmi.doctor.pages.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.angcyo.tablayout.DslTabLayout;
import com.blankj.utilcode.util.KeyboardUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.NoDoubleClickUtil;
import com.catchpig.mvvm.utils.screenshot.ScreenshotUtil;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.TransformExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.MeActivityBinding;
import com.yddmi.doctor.entity.request.IntegralAcquireReq;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.IntegralRow;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillHomeList;
import com.yddmi.doctor.entity.result.SkillIntegral;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.pages.me.MeActivity;
import com.yddmi.doctor.pages.u3d.U3dActivity;
import com.yddmi.doctor.pages.u3d.U3dGoActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.PermissionInterceptor;
import com.yikaobang.yixue.R2;
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
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000]\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0019*\u0001\u000e\b\u0007\u0018\u0000 :2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001:B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0002J\u0012\u0010\u0019\u001a\u00020\u00152\b\b\u0002\u0010\u001a\u001a\u00020\u0013H\u0002J\b\u0010\u001b\u001a\u00020\u0015H\u0002J\u001a\u0010\u001c\u001a\u00020\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u0011H\u0002J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\u0015H\u0002J\b\u0010$\u001a\u00020\u0015H\u0016J\b\u0010%\u001a\u00020\u0015H\u0016J\b\u0010&\u001a\u00020\u0015H\u0016J\b\u0010'\u001a\u00020\u0015H\u0014J\b\u0010(\u001a\u00020\u0015H\u0014J\b\u0010)\u001a\u00020\u0015H\u0002J(\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00112\u0006\u0010-\u001a\u00020\u00132\u0006\u0010.\u001a\u00020\u0013H\u0002J\b\u0010/\u001a\u00020\u0015H\u0002J\b\u00100\u001a\u00020\u0015H\u0002J\b\u00101\u001a\u00020\u0015H\u0002J\b\u00102\u001a\u00020\u0015H\u0002J\u0012\u00103\u001a\u00020\u00152\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0002J\b\u00104\u001a\u00020\u0015H\u0002J\b\u00105\u001a\u00020\u0015H\u0002J\u001c\u00106\u001a\u00020\u00152\b\b\u0002\u00107\u001a\u00020\u00112\b\b\u0002\u00108\u001a\u00020\u0011H\u0002J\u0010\u00109\u001a\u00020\u00152\u0006\u00107\u001a\u00020\u0011H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lcom/yddmi/doctor/pages/me/MeActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/MeActivityBinding;", "Lcom/yddmi/doctor/pages/me/MeViewModel;", "()V", "adapter100Msg", "Lcom/yddmi/doctor/pages/me/AdapterMsg;", "adapterBuySkill", "Lcom/yddmi/doctor/pages/me/AdapterBuySkill1;", "adapterHistory", "Lcom/yddmi/doctor/pages/me/AdapterHistory;", "adapterIntegral", "Lcom/yddmi/doctor/pages/me/AdapterIntegral;", "mHandler", "com/yddmi/doctor/pages/me/MeActivity$mHandler$1", "Lcom/yddmi/doctor/pages/me/MeActivity$mHandler$1;", "mShowTabIndex", "", "softKeyBoardShow", "", "dealWxShare100", "", "share", "Lcom/yddmi/doctor/entity/result/SkillShare;", "httpGetPersonInfo", "httpGetRecordEquityDetail", "refresh", "httpNickNameSave", "httpPostAppRead", "m", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "postion", "httpPostIntegralAcquire", "data", "Lcom/yddmi/doctor/entity/result/SkillHome;", "httpPostInviterSave", "initFlow", "initParam", "initView", "onDestroy", "onResume", "saveQr2Photo", "viewDslTabChange", "fromIndex", "toIndex", "reselect", "fromUser", "viewGoCamera", "viewGoPhoto", "viewPopIconChange", "viewPopShowHistory", "viewShowFiveDetails", "viewShowRecommend", "viewShowUserInfo", "viewTabChange", "index", "dalTabIndex", "viewTabTvChange", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nMeActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MeActivity.kt\ncom/yddmi/doctor/pages/me/MeActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1092:1\n18#2,2:1093\n1#3:1095\n*S KotlinDebug\n*F\n+ 1 MeActivity.kt\ncom/yddmi/doctor/pages/me/MeActivity\n*L\n109#1:1093,2\n109#1:1095\n*E\n"})
/* loaded from: classes6.dex */
public final class MeActivity extends BaseVMActivity<MeActivityBinding, MeViewModel> {

    @NotNull
    private static final String TAG = "MeActivity";
    private AdapterMsg adapter100Msg;
    private AdapterBuySkill1 adapterBuySkill;
    private AdapterHistory adapterHistory;
    private AdapterIntegral adapterIntegral;

    @NotNull
    private final MeActivity$mHandler$1 mHandler;
    private int mShowTabIndex = 2;
    private boolean softKeyBoardShow;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeActivity$initFlow$1", f = "MeActivity.kt", i = {}, l = {R2.attr.all_course_search_icon}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08291 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08291(Continuation<? super C08291> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeActivity.this.new C08291(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> skillListMsf = MeActivity.this.getViewModel().getSkillListMsf();
                final MeActivity meActivity = MeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.me.MeActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            meActivity.hideLoading();
                            MeActivity.access$getBodyBinding(meActivity).srl.finishRefresh();
                            MeActivity.access$getBodyBinding(meActivity).srl.finishLoadMore();
                            if (meActivity.getViewModel().getLastTab() == 2) {
                                int lastTabDsl = meActivity.getViewModel().getLastTabDsl();
                                AdapterBuySkill1 adapterBuySkill1 = null;
                                if (lastTabDsl == 0) {
                                    AdapterBuySkill1 adapterBuySkill12 = meActivity.adapterBuySkill;
                                    if (adapterBuySkill12 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
                                    } else {
                                        adapterBuySkill1 = adapterBuySkill12;
                                    }
                                    adapterBuySkill1.set(meActivity.getViewModel().getSkillList());
                                } else if (lastTabDsl == 1) {
                                    AdapterBuySkill1 adapterBuySkill13 = meActivity.adapterBuySkill;
                                    if (adapterBuySkill13 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
                                    } else {
                                        adapterBuySkill1 = adapterBuySkill13;
                                    }
                                    adapterBuySkill1.set(meActivity.getViewModel().getSkillBodyList());
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (skillListMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeActivity$initFlow$2", f = "MeActivity.kt", i = {}, l = {317}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C08302 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08302(Continuation<? super C08302> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeActivity.this.new C08302(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08302) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> trainExamListMsf = MeActivity.this.getViewModel().getTrainExamListMsf();
                final MeActivity meActivity = MeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.me.MeActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:106:0x0246  */
                    /* JADX WARN: Removed duplicated region for block: B:136:0x02ec  */
                    /* JADX WARN: Removed duplicated region for block: B:42:0x00e6  */
                    /* JADX WARN: Removed duplicated region for block: B:72:0x018d  */
                    @org.jetbrains.annotations.Nullable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(long r4, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r6) {
                        /*
                            Method dump skipped, instructions count: 762
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.me.MeActivity.C08302.AnonymousClass1.emit(long, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                };
                this.label = 1;
                if (trainExamListMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeActivity$initFlow$3", f = "MeActivity.kt", i = {}, l = {R2.attr.assetName}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeActivity$initFlow$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeActivity.this.new AnonymousClass3(continuation);
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
                MutableStateFlow<Long> historyMsf = MeActivity.this.getViewModel().getHistoryMsf();
                final MeActivity meActivity = MeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.me.MeActivity.initFlow.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            meActivity.hideLoading();
                            meActivity.viewPopShowHistory();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (historyMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeActivity$initFlow$4", f = "MeActivity.kt", i = {}, l = {R2.attr.autoSizePresetSizes}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeActivity$initFlow$4, reason: invalid class name */
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeActivity.this.new AnonymousClass4(continuation);
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
                MutableStateFlow<Long> msg0Msf = MeActivity.this.getViewModel().getMsg0Msf();
                final MeActivity meActivity = MeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.me.MeActivity.initFlow.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:30:0x00b7  */
                    @org.jetbrains.annotations.Nullable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(long r3, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r5) {
                        /*
                            r2 = this;
                            r0 = 0
                            int r3 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
                            if (r3 <= 0) goto Lc2
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            r3.hideLoading()
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            com.yddmi.doctor.databinding.MeActivityBinding r3 = com.yddmi.doctor.pages.me.MeActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.fiveSrl
                            r3.finishRefresh()
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            com.yddmi.doctor.databinding.MeActivityBinding r3 = com.yddmi.doctor.pages.me.MeActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.fiveSrl
                            r3.finishLoadMore()
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            com.yddmi.doctor.pages.me.AdapterMsg r3 = com.yddmi.doctor.pages.me.MeActivity.access$getAdapter100Msg$p(r3)
                            r4 = 0
                            if (r3 != 0) goto L30
                            java.lang.String r3 = "adapter100Msg"
                            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
                            r3 = r4
                        L30:
                            com.yddmi.doctor.pages.me.MeActivity r5 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r5 = r5.getViewModel()
                            com.yddmi.doctor.pages.me.MeViewModel r5 = (com.yddmi.doctor.pages.me.MeViewModel) r5
                            com.yddmi.doctor.entity.result.HomeMsgList r5 = r5.getMsg0List()
                            if (r5 == 0) goto L42
                            java.util.List r4 = r5.getRows()
                        L42:
                            r3.set(r4)
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.me.MeViewModel r3 = (com.yddmi.doctor.pages.me.MeViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getMsg0List()
                            if (r3 == 0) goto Lb7
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.me.MeViewModel r3 = (com.yddmi.doctor.pages.me.MeViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getMsg0List()
                            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                            java.util.List r3 = r3.getRows()
                            java.util.Collection r3 = (java.util.Collection) r3
                            r4 = 1
                            r5 = 0
                            if (r3 == 0) goto L75
                            boolean r3 = r3.isEmpty()
                            if (r3 == 0) goto L73
                            goto L75
                        L73:
                            r3 = r5
                            goto L76
                        L75:
                            r3 = r4
                        L76:
                            if (r3 == 0) goto L79
                            goto Lb7
                        L79:
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.me.MeViewModel r3 = (com.yddmi.doctor.pages.me.MeViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getMsg0List()
                            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                            java.util.List r3 = r3.getRows()
                            if (r3 == 0) goto L93
                            int r3 = r3.size()
                            goto L94
                        L93:
                            r3 = r5
                        L94:
                            com.yddmi.doctor.pages.me.MeActivity r0 = r1
                            com.yddmi.doctor.databinding.MeActivityBinding r0 = com.yddmi.doctor.pages.me.MeActivity.access$getBodyBinding(r0)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r0.fiveSrl
                            com.yddmi.doctor.pages.me.MeActivity r1 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r1 = r1.getViewModel()
                            com.yddmi.doctor.pages.me.MeViewModel r1 = (com.yddmi.doctor.pages.me.MeViewModel) r1
                            com.yddmi.doctor.entity.result.HomeMsgList r1 = r1.getMsg0List()
                            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
                            int r1 = r1.getTotal()
                            if (r3 < r1) goto Lb2
                            goto Lb3
                        Lb2:
                            r4 = r5
                        Lb3:
                            r0.setNoMoreData(r4)
                            goto Lc2
                        Lb7:
                            com.yddmi.doctor.pages.me.MeActivity r3 = r1
                            com.yddmi.doctor.databinding.MeActivityBinding r3 = com.yddmi.doctor.pages.me.MeActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.fiveSrl
                            r3.finishRefreshWithNoMoreData()
                        Lc2:
                            kotlin.Unit r3 = kotlin.Unit.INSTANCE
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.me.MeActivity.AnonymousClass4.AnonymousClass1.emit(long, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                };
                this.label = 1;
                if (msg0Msf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeActivity$initFlow$5", f = "MeActivity.kt", i = {}, l = {411}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeActivity$initFlow$5, reason: invalid class name */
    public static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass5(Continuation<? super AnonymousClass5> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeActivity.this.new AnonymousClass5(continuation);
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
                MutableStateFlow<Long> integralMsf = MeActivity.this.getViewModel().getIntegralMsf();
                final MeActivity meActivity = MeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.me.MeActivity.initFlow.5.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            MeActivity.access$getBodyBinding(meActivity).integralTv.setText(YddUserManager.INSTANCE.getInstance().userIntegralGet() + meActivity.getString(R.string.me_score1));
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeActivity$initFlow$6", f = "MeActivity.kt", i = {}, l = {419}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeActivity$initFlow$6, reason: invalid class name */
    public static final class AnonymousClass6 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass6(Continuation<? super AnonymousClass6> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeActivity.this.new AnonymousClass6(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass6) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> mRecommendMsf = MeActivity.this.getViewModel().getMRecommendMsf();
                final MeActivity meActivity = MeActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.me.MeActivity.initFlow.6.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            meActivity.viewShowRecommend();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (mRecommendMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/MeActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.me.MeActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08331 extends Lambda implements Function1<MeActivityBinding, Unit> {
        public C08331() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewPopIconChange();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$10(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            MeActivity.viewTabChange$default(this$0, 6, 0, 2, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$11(MeActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            int lastTab = this$0.getViewModel().getLastTab();
            if (lastTab == 2) {
                this$0.getViewModel().httpGetSkillMy();
            } else if (lastTab == 3) {
                this$0.getViewModel().httpGetTrainExamList(true);
            } else {
                if (lastTab != 4) {
                    return;
                }
                this$0.getViewModel().httpGetTrainExamList(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$12(MeActivity this$0, MeActivityBinding this_bodyBinding, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(it, "it");
            int lastTab = this$0.getViewModel().getLastTab();
            if (lastTab == 2) {
                this_bodyBinding.srl.finishLoadMoreWithNoMoreData();
            } else if (lastTab == 3) {
                this$0.getViewModel().httpGetTrainExamList(false);
            } else {
                if (lastTab != 4) {
                    return;
                }
                this$0.getViewModel().httpGetTrainExamList(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$13(MeActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            LogExtKt.logd("关闭点击", MeActivity.TAG);
            this_bodyBinding.detailsCl.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$15(MeActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.httpGetRecordEquityDetail(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$16(MeActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.httpGetRecordEquityDetail(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(MeActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.nameEt.setEnabled(!r2.isEnabled());
            if (this_bodyBinding.nameEt.isEnabled()) {
                this_bodyBinding.nameEt.requestFocus();
                KeyboardUtils.showSoftInput(this_bodyBinding.nameEt);
                EditText nameEt = this_bodyBinding.nameEt;
                Intrinsics.checkNotNullExpressionValue(nameEt, "nameEt");
                ViewExtKt.selectionEndGo(nameEt);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invoke$lambda$3(MeActivity this$0, TextView textView, int i2, KeyEvent keyEvent) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (i2 != 6) {
                return true;
            }
            this$0.httpNickNameSave();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(MeActivity this$0, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.softKeyBoardShow && i2 < 20) {
                this$0.softKeyBoardShow = false;
                this$0.httpNickNameSave();
            }
            if (i2 > 200) {
                this$0.softKeyBoardShow = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            MeActivity.viewTabChange$default(this$0, 1, 0, 2, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            MeActivity.viewTabChange$default(this$0, 2, 0, 2, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$7(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            MeActivity.viewTabChange$default(this$0, 3, 0, 2, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$8(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            MeActivity.viewTabChange$default(this$0, 4, 0, 2, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$9(MeActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            MeActivity.viewTabChange$default(this$0, 5, 0, 2, null);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(MeActivityBinding meActivityBinding) {
            invoke2(meActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final MeActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final MeActivity meActivity = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$0(meActivity, view);
                }
            }, 0L, 2, null);
            ImageView userImgv = bodyBinding.userImgv;
            Intrinsics.checkNotNullExpressionValue(userImgv, "userImgv");
            final MeActivity meActivity2 = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(userImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$1(meActivity2, view);
                }
            }, 0L, 2, null);
            ImageView editImgv = bodyBinding.editImgv;
            Intrinsics.checkNotNullExpressionValue(editImgv, "editImgv");
            ViewExtKt.setDebounceClickListener$default(editImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$2(bodyBinding, view);
                }
            }, 0L, 2, null);
            EditText editText = bodyBinding.nameEt;
            final MeActivity meActivity3 = MeActivity.this;
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.yddmi.doctor.pages.me.s
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                    return MeActivity.C08331.invoke$lambda$3(meActivity3, textView, i2, keyEvent);
                }
            });
            final MeActivity meActivity4 = MeActivity.this;
            KeyboardUtils.registerSoftInputChangedListener(meActivity4, new KeyboardUtils.OnSoftInputChangedListener() { // from class: com.yddmi.doctor.pages.me.e
                @Override // com.blankj.utilcode.util.KeyboardUtils.OnSoftInputChangedListener
                public final void onSoftInputChanged(int i2) {
                    MeActivity.C08331.invoke$lambda$4(meActivity4, i2);
                }
            });
            TextView oneTv = bodyBinding.oneTv;
            Intrinsics.checkNotNullExpressionValue(oneTv, "oneTv");
            final MeActivity meActivity5 = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(oneTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$5(meActivity5, view);
                }
            }, 0L, 2, null);
            TextView twoTv = bodyBinding.twoTv;
            Intrinsics.checkNotNullExpressionValue(twoTv, "twoTv");
            final MeActivity meActivity6 = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(twoTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$6(meActivity6, view);
                }
            }, 0L, 2, null);
            TextView threeTv = bodyBinding.threeTv;
            Intrinsics.checkNotNullExpressionValue(threeTv, "threeTv");
            final MeActivity meActivity7 = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(threeTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$7(meActivity7, view);
                }
            }, 0L, 2, null);
            TextView fourTv = bodyBinding.fourTv;
            Intrinsics.checkNotNullExpressionValue(fourTv, "fourTv");
            final MeActivity meActivity8 = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(fourTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$8(meActivity8, view);
                }
            }, 0L, 2, null);
            TextView fiveTv = bodyBinding.fiveTv;
            Intrinsics.checkNotNullExpressionValue(fiveTv, "fiveTv");
            final MeActivity meActivity9 = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(fiveTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$9(meActivity9, view);
                }
            }, 0L, 2, null);
            TextView sixTv = bodyBinding.sixTv;
            Intrinsics.checkNotNullExpressionValue(sixTv, "sixTv");
            final MeActivity meActivity10 = MeActivity.this;
            ViewExtKt.setDebounceClickListener$default(sixTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$10(meActivity10, view);
                }
            }, 0L, 2, null);
            SmartRefreshLayout smartRefreshLayout = bodyBinding.srl;
            final MeActivity meActivity11 = MeActivity.this;
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.yddmi.doctor.pages.me.l
                @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    MeActivity.C08331.invoke$lambda$11(meActivity11, refreshLayout);
                }
            });
            SmartRefreshLayout smartRefreshLayout2 = bodyBinding.srl;
            final MeActivity meActivity12 = MeActivity.this;
            smartRefreshLayout2.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.yddmi.doctor.pages.me.m
                @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
                public final void onLoadMore(RefreshLayout refreshLayout) {
                    MeActivity.C08331.invoke$lambda$12(meActivity12, bodyBinding, refreshLayout);
                }
            });
            DslTabLayout dslTablayout = bodyBinding.dslTablayout;
            Intrinsics.checkNotNullExpressionValue(dslTablayout, "dslTablayout");
            final MeActivity meActivity13 = MeActivity.this;
            Function4<Integer, Integer, Boolean, Boolean, Unit> function4 = new Function4<Integer, Integer, Boolean, Boolean, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.initView.1.14
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2, Boolean bool, Boolean bool2) {
                    invoke(num.intValue(), num2.intValue(), bool.booleanValue(), bool2.booleanValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(int i2, int i3, boolean z2, boolean z3) {
                    LogExtKt.logd("dslTablayout 标签切换 " + i2 + " " + i3 + " " + z2 + " " + z3, MeActivity.TAG);
                    meActivity13.viewDslTabChange(i2, i3, z2, z3);
                }
            };
            AdapterMsg adapterMsg = null;
            DslTabLayout.observeIndexChange$default(dslTablayout, null, function4, 1, null);
            bodyBinding.meTv.setVisibility(4);
            bodyBinding.integralTv.setVisibility(4);
            ImageView x110Imgv = bodyBinding.x110Imgv;
            Intrinsics.checkNotNullExpressionValue(x110Imgv, "x110Imgv");
            ViewExtKt.setDebounceClickListener$default(x110Imgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.n
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MeActivity.C08331.invoke$lambda$13(bodyBinding, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView = MeActivity.access$getBodyBinding(MeActivity.this).fiveRv;
            MeActivity meActivity14 = MeActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            AdapterMsg adapterMsg2 = meActivity14.adapter100Msg;
            if (adapterMsg2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter100Msg");
                adapterMsg2 = null;
            }
            recyclerView.setAdapter(adapterMsg2);
            AdapterMsg adapterMsg3 = MeActivity.this.adapter100Msg;
            if (adapterMsg3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter100Msg");
            } else {
                adapterMsg = adapterMsg3;
            }
            final MeActivity meActivity15 = MeActivity.this;
            adapterMsg.setOnItemClickListener(new Function2<HomeMsg, Integer, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.initView.1.17
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(HomeMsg homeMsg, Integer num) {
                    invoke(homeMsg, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(@Nullable HomeMsg homeMsg, int i2) {
                    meActivity15.viewShowFiveDetails(homeMsg);
                    meActivity15.httpPostAppRead(homeMsg, i2);
                }
            });
            SmartRefreshLayout smartRefreshLayout3 = bodyBinding.fiveSrl;
            final MeActivity meActivity16 = MeActivity.this;
            smartRefreshLayout3.setOnRefreshListener(new OnRefreshListener() { // from class: com.yddmi.doctor.pages.me.o
                @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    MeActivity.C08331.invoke$lambda$15(meActivity16, refreshLayout);
                }
            });
            SmartRefreshLayout smartRefreshLayout4 = bodyBinding.fiveSrl;
            final MeActivity meActivity17 = MeActivity.this;
            smartRefreshLayout4.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.yddmi.doctor.pages.me.p
                @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
                public final void onLoadMore(RefreshLayout refreshLayout) {
                    MeActivity.C08331.invoke$lambda$16(meActivity17, refreshLayout);
                }
            });
            bodyBinding.fiveRv.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.me.MeActivity.initView.1.20
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    if (event == null) {
                        return false;
                    }
                    MeActivityBinding meActivityBinding = bodyBinding;
                    if (event.getAction() != 1) {
                        return false;
                    }
                    meActivityBinding.webImgv.setVisibility(4);
                    return false;
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.yddmi.doctor.pages.me.MeActivity$mHandler$1] */
    public MeActivity() {
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.me.MeActivity$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (msg.what == 102) {
                    this.this$0.hideLoading();
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ MeActivityBinding access$getBodyBinding(MeActivity meActivity) {
        return (MeActivityBinding) meActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealWxShare100(SkillShare share) {
        WXWebpageObject wXWebpageObject = new WXWebpageObject();
        String str = share.getShareJumpLink() + "?inviteSign=" + share.getInviteSign() + "&practiceListId=" + share.getPracticeListId();
        wXWebpageObject.webpageUrl = str;
        LogExtKt.logd("微信分享" + share + " 打开链接" + str, TAG);
        WXMediaMessage wXMediaMessage = new WXMediaMessage(wXWebpageObject);
        wXMediaMessage.title = share.getShareTitle();
        wXMediaMessage.description = share.getShareIntroduce();
        Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(), R.drawable.common_share);
        Intrinsics.checkNotNullExpressionValue(thumbBmp, "thumbBmp");
        wXMediaMessage.thumbData = TransformExtKt.toByteArray$default(thumbBmp, (Bitmap.CompressFormat) null, 1, (Object) null);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = wXMediaMessage;
        req.scene = 0;
        YddUserManager.INSTANCE.getInstance().getmWxApi().sendReq(req);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPersonInfo() {
        FlowExtKt.lifecycle(getViewModel().getPersonInfo(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpGetPersonInfo.1
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
        }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpGetPersonInfo.2
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
                    MeActivity meActivity = MeActivity.this;
                    YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), null, meProfile, 1, null);
                    meActivity.viewShowUserInfo();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetRecordEquityDetail(final boolean refresh) {
        FlowExtKt.lifecycle(getViewModel().getRecordEquityDetail(refresh), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpGetRecordEquityDetail.1
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
                MeActivity.access$getBodyBinding(MeActivity.this).fiveSrl.finishRefresh();
                MeActivity.access$getBodyBinding(MeActivity.this).fiveSrl.finishLoadMore();
            }
        }, new Function1<SkillIntegral, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpGetRecordEquityDetail.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SkillIntegral skillIntegral) {
                invoke2(skillIntegral);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable SkillIntegral skillIntegral) {
                if (skillIntegral != null) {
                    boolean z2 = refresh;
                    MeActivity meActivity = MeActivity.this;
                    AdapterIntegral adapterIntegral = null;
                    if (z2) {
                        AdapterIntegral adapterIntegral2 = meActivity.adapterIntegral;
                        if (adapterIntegral2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapterIntegral");
                            adapterIntegral2 = null;
                        }
                        adapterIntegral2.set(skillIntegral.getRecords());
                        List<IntegralRow> records = skillIntegral.getRecords();
                        if (records == null || records.isEmpty()) {
                            meActivity.bodyBinding(new Function1<MeActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity$httpGetRecordEquityDetail$2$1$1
                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(MeActivityBinding meActivityBinding) {
                                    invoke2(meActivityBinding);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(@NotNull MeActivityBinding bodyBinding) {
                                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                                    bodyBinding.noDataCl.setVisibility(0);
                                    bodyBinding.fiveBfl.setVisibility(8);
                                }
                            });
                        } else {
                            meActivity.bodyBinding(new Function1<MeActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity$httpGetRecordEquityDetail$2$1$2
                                @Override // kotlin.jvm.functions.Function1
                                public /* bridge */ /* synthetic */ Unit invoke(MeActivityBinding meActivityBinding) {
                                    invoke2(meActivityBinding);
                                    return Unit.INSTANCE;
                                }

                                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(@NotNull MeActivityBinding bodyBinding) {
                                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                                    bodyBinding.noDataCl.setVisibility(8);
                                    bodyBinding.fiveBfl.setVisibility(0);
                                }
                            });
                        }
                    } else {
                        AdapterIntegral adapterIntegral3 = meActivity.adapterIntegral;
                        if (adapterIntegral3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("adapterIntegral");
                            adapterIntegral3 = null;
                        }
                        adapterIntegral3.add(skillIntegral.getRecords());
                    }
                    SmartRefreshLayout smartRefreshLayout = MeActivity.access$getBodyBinding(meActivity).fiveSrl;
                    AdapterIntegral adapterIntegral4 = meActivity.adapterIntegral;
                    if (adapterIntegral4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapterIntegral");
                    } else {
                        adapterIntegral = adapterIntegral4;
                    }
                    smartRefreshLayout.setNoMoreData(adapterIntegral.getData().size() >= skillIntegral.getTotal());
                }
                MeActivity.access$getBodyBinding(MeActivity.this).fiveSrl.finishRefresh();
                MeActivity.access$getBodyBinding(MeActivity.this).fiveSrl.finishLoadMore();
            }
        });
    }

    public static /* synthetic */ void httpGetRecordEquityDetail$default(MeActivity meActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        meActivity.httpGetRecordEquityDetail(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpNickNameSave() {
        if (NoDoubleClickUtil.isNoDoubleClick(1000)) {
            String string = StringsKt__StringsKt.trim((CharSequence) ((MeActivityBinding) getBodyBinding()).nameEt.getText().toString()).toString();
            ((MeActivityBinding) getBodyBinding()).nameEt.setEnabled(false);
            if (!(string.length() == 0)) {
                FlowExtKt.lifecycleLoadingDialog(getViewModel().httpPostPersonInfoSava(string), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpNickNameSave.1
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
                }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpNickNameSave.2
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
                        MeActivity.this.httpGetPersonInfo();
                    }
                });
            } else {
                Toaster.show(R.string.me_input_nickname);
                ((MeActivityBinding) getBodyBinding()).nameEt.setText(YddUserManager.INSTANCE.getInstance().userNickName());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostAppRead(final HomeMsg m2, final int postion) {
        if (m2 != null) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postAppRead(String.valueOf(m2.getId())), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity$httpPostAppRead$1$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity$httpPostAppRead$1$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                    m2.setRead(1);
                    AdapterMsg adapterMsg = this.adapter100Msg;
                    if (adapterMsg == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter100Msg");
                        adapterMsg = null;
                    }
                    adapterMsg.notifyItemChanged(postion);
                }
            });
        }
    }

    private final void httpPostIntegralAcquire(SkillHome data) {
        String id = data.getId();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postIntegralAcquire(new IntegralAcquireReq(id, companion.getInstance().userNickName(), companion.getInstance().userName(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpPostIntegralAcquire.1
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
        }, new Function1<SkillShare, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpPostIntegralAcquire.2
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
                    MeActivity.this.dealWxShare100(skillShare);
                }
            }
        });
    }

    private final void httpPostInviterSave() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postInviterSave(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpPostInviterSave.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    LogExtKt.loge("成为推荐官失败 " + it, MeActivity.TAG);
                }
            }, new Function1<HomeMsg, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.httpPostInviterSave.2
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
                    LogExtKt.loge("成为推荐官", MeActivity.TAG);
                    MeActivity.this.getViewModel().httpGetInviterInfo();
                }
            });
        }
    }

    private final void saveQr2Photo() throws NoSuchMethodException, SecurityException {
        XXPermissions.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE).interceptor(new PermissionInterceptor(105)).request(new OnPermissionCallback() { // from class: com.yddmi.doctor.pages.me.MeActivity.saveQr2Photo.1
            @Override // com.hjq.permissions.OnPermissionCallback
            public void onDenied(@NotNull List<String> permissions, boolean doNotAskAgain) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (!doNotAskAgain) {
                    Toaster.show(R.string.common_permissions);
                } else {
                    Toaster.show(R.string.notification_file);
                    XXPermissions.startPermissionActivity((Activity) MeActivity.this, permissions);
                }
            }

            @Override // com.hjq.permissions.OnPermissionCallback
            public void onGranted(@NotNull List<String> permissions, boolean allGranted) {
                Intrinsics.checkNotNullParameter(permissions, "permissions");
                if (!allGranted) {
                    Toaster.show(R.string.common_permissions);
                } else if (ScreenshotUtil.saveScreenshot((ConstraintLayout) MeActivity.this.findViewById(R.id.qrCl), MeActivity.this)) {
                    Toaster.show(R.string.me_save_success);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewDslTabChange(int fromIndex, int toIndex, boolean reselect, boolean fromUser) {
        LogExtKt.logd("viewDslTabChange " + getViewModel().getLastTab() + " " + toIndex + " " + reselect, TAG);
        if (reselect) {
            return;
        }
        getViewModel().setLastTabDsl(toIndex);
        int lastTab = getViewModel().getLastTab();
        AdapterBuySkill1 adapterBuySkill1 = null;
        AdapterBuySkill1 adapterBuySkill12 = null;
        if (lastTab == 2) {
            if (toIndex == 0) {
                AdapterBuySkill1 adapterBuySkill13 = this.adapterBuySkill;
                if (adapterBuySkill13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
                } else {
                    adapterBuySkill1 = adapterBuySkill13;
                }
                adapterBuySkill1.set(getViewModel().getSkillList());
                return;
            }
            if (toIndex != 1) {
                return;
            }
            List<SkillHome> skillBodyList = getViewModel().getSkillBodyList();
            if (skillBodyList == null || skillBodyList.isEmpty()) {
                loadingView();
                getViewModel().httpGetSkillMy();
                return;
            }
            AdapterBuySkill1 adapterBuySkill14 = this.adapterBuySkill;
            if (adapterBuySkill14 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
            } else {
                adapterBuySkill12 = adapterBuySkill14;
            }
            adapterBuySkill12.set(getViewModel().getSkillBodyList());
            return;
        }
        if (lastTab == 3) {
            if (toIndex == 0) {
                AdapterHistory adapterHistory = this.adapterHistory;
                if (adapterHistory == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
                    adapterHistory = null;
                }
                SkillHomeList trainList = getViewModel().getTrainList();
                adapterHistory.set(trainList != null ? trainList.getRows() : null);
                return;
            }
            if (toIndex != 1) {
                return;
            }
            SkillHomeList trainBodyList = getViewModel().getTrainBodyList();
            List<SkillHome> rows = trainBodyList != null ? trainBodyList.getRows() : null;
            if ((rows == null || rows.isEmpty()) == true) {
                loadingView();
                MeViewModel.httpGetTrainExamList$default(getViewModel(), false, 1, null);
                return;
            }
            AdapterHistory adapterHistory2 = this.adapterHistory;
            if (adapterHistory2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
                adapterHistory2 = null;
            }
            SkillHomeList trainBodyList2 = getViewModel().getTrainBodyList();
            adapterHistory2.set(trainBodyList2 != null ? trainBodyList2.getRows() : null);
            return;
        }
        if (lastTab != 4) {
            return;
        }
        if (toIndex == 0) {
            AdapterHistory adapterHistory3 = this.adapterHistory;
            if (adapterHistory3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
                adapterHistory3 = null;
            }
            SkillHomeList examList = getViewModel().getExamList();
            adapterHistory3.set(examList != null ? examList.getRows() : null);
            return;
        }
        if (toIndex != 1) {
            return;
        }
        SkillHomeList examBodyList = getViewModel().getExamBodyList();
        List<SkillHome> rows2 = examBodyList != null ? examBodyList.getRows() : null;
        if ((rows2 == null || rows2.isEmpty()) == true) {
            loadingView();
            MeViewModel.httpGetTrainExamList$default(getViewModel(), false, 1, null);
            return;
        }
        AdapterHistory adapterHistory4 = this.adapterHistory;
        if (adapterHistory4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
            adapterHistory4 = null;
        }
        SkillHomeList examBodyList2 = getViewModel().getExamBodyList();
        adapterHistory4.set(examBodyList2 != null ? examBodyList2.getRows() : null);
    }

    private final void viewGoCamera() {
    }

    private final void viewGoPhoto() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopIconChange() {
        new XPopup.Builder(this).isViewMode(true).borderRadius(CommonExtKt.dp2px(this, 2)).isDestroyOnDismiss(true).asBottomList(null, new String[]{getString(R.string.me_photo), getString(R.string.me_select)}, new OnSelectListener() { // from class: com.yddmi.doctor.pages.me.c
            @Override // com.lxj.xpopup.interfaces.OnSelectListener
            public final void onSelect(int i2, String str) {
                MeActivity.viewPopIconChange$lambda$6(this.f25957a, i2, str);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewPopIconChange$lambda$6(MeActivity this$0, int i2, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i2 == 0) {
            this$0.viewGoCamera();
        } else {
            this$0.viewGoPhoto();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopShowHistory() {
        if (getViewModel().getHistoryData() == null) {
            Toaster.show(R.string.common_no_data1);
            return;
        }
        SkillHome historyData = getViewModel().getHistoryData();
        Intrinsics.checkNotNull(historyData);
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(new PopupHistory(this, historyData, getViewModel().getLastTab() == 4)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowFiveDetails(final HomeMsg m2) {
        if (m2 == null) {
            Toaster.show(R.string.common_search_no2);
        } else {
            bodyBinding(new Function1<MeActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.viewShowFiveDetails.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(MeActivityBinding meActivityBinding) {
                    invoke2(meActivityBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull MeActivityBinding bodyBinding) {
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    bodyBinding.detailsCl.setVisibility(0);
                    ImageView msgUserImgv = bodyBinding.msgUserImgv;
                    Intrinsics.checkNotNullExpressionValue(msgUserImgv, "msgUserImgv");
                    String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(YddUserManager.INSTANCE.getInstance().userAvatar());
                    int i2 = R.drawable.common_user_icon0;
                    ImageViewExtKt.load(msgUserImgv, fileFullUrl, (261628 & 2) != 0 ? 0 : i2, (261628 & 4) != 0 ? 0 : i2, (261628 & 8) != 0 ? false : true, (261628 & 16) != 0 ? false : true, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                    bodyBinding.msgHiImgv.setImageResource(R.drawable.common_share1);
                    bodyBinding.userTv.setText(m2.getContent());
                    String reply = m2.getReply();
                    if (reply == null || reply.length() == 0) {
                        bodyBinding.hiTv.setText("");
                        bodyBinding.msgHiImgv.setVisibility(4);
                    } else {
                        bodyBinding.hiTv.setText(m2.getReply());
                        bodyBinding.msgHiImgv.setVisibility(0);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowRecommend() {
        if (getViewModel().getLastTab() != 6) {
            return;
        }
        if (getViewModel().getMRecommend() != null) {
            HomeMsg mRecommend = getViewModel().getMRecommend();
            String inviterUrl = mRecommend != null ? mRecommend.getInviterUrl() : null;
            boolean z2 = true;
            if ((inviterUrl == null || inviterUrl.length() == 0) == false) {
                ((MeActivityBinding) getBodyBinding()).goCl.setVisibility(8);
                ((MeActivityBinding) getBodyBinding()).recomCl.setVisibility(0);
                ImageView imageView = ((MeActivityBinding) getBodyBinding()).ad1Imgv;
                Intrinsics.checkNotNullExpressionValue(imageView, "bodyBinding.ad1Imgv");
                YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
                HomeMsg mRecommend2 = getViewModel().getMRecommend();
                ImageViewExtKt.load(imageView, companion.getFileFullUrl(mRecommend2 != null ? mRecommend2.getFileUrl() : null), (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                HomeMsg mRecommend3 = getViewModel().getMRecommend();
                String inviterUrl2 = mRecommend3 != null ? mRecommend3.getInviterUrl() : null;
                if (inviterUrl2 != null && inviterUrl2.length() != 0) {
                    z2 = false;
                }
                if (z2) {
                    ((MeActivityBinding) getBodyBinding()).qrImgv.setVisibility(4);
                    return;
                }
                Drawable drawable = ContextManager.INSTANCE.getInstance().getContext().getDrawable(R.drawable.common_icon5);
                ((MeActivityBinding) getBodyBinding()).qrImgv.setImageBitmap(drawable != null ? TransformExtKt.toBitmap(drawable) : null);
                ((MeActivityBinding) getBodyBinding()).qrImgv.setVisibility(0);
                TextView textView = ((MeActivityBinding) getBodyBinding()).codeTv;
                HomeMsg mRecommend4 = getViewModel().getMRecommend();
                Intrinsics.checkNotNull(mRecommend4);
                textView.setText(mRecommend4.getInviterCode());
                ((MeActivityBinding) getBodyBinding()).codeTv.setVisibility(0);
                ImageView imageView2 = ((MeActivityBinding) getBodyBinding()).saveImgv;
                Intrinsics.checkNotNullExpressionValue(imageView2, "bodyBinding.saveImgv");
                ViewExtKt.setDebounceClickListener$default(imageView2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.b
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws NoSuchMethodException, SecurityException {
                        MeActivity.viewShowRecommend$lambda$8(this.f25956c, view);
                    }
                }, 0L, 2, null);
                return;
            }
        }
        ((MeActivityBinding) getBodyBinding()).goCl.setVisibility(0);
        ((MeActivityBinding) getBodyBinding()).recomCl.setVisibility(8);
        ImageView imageView3 = ((MeActivityBinding) getBodyBinding()).adImgv;
        Intrinsics.checkNotNullExpressionValue(imageView3, "bodyBinding.adImgv");
        YddHostConfig companion2 = YddHostConfig.INSTANCE.getInstance();
        HomeMsg mRecommend5 = getViewModel().getMRecommend();
        ImageViewExtKt.load(imageView3, companion2.getFileFullUrl(mRecommend5 != null ? mRecommend5.getFileUrl() : null), (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
        ImageView imageView4 = ((MeActivityBinding) getBodyBinding()).twoImgv;
        Intrinsics.checkNotNullExpressionValue(imageView4, "bodyBinding.twoImgv");
        ViewExtKt.setDebounceClickListener$default(imageView4, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.me.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MeActivity.viewShowRecommend$lambda$7(this.f25955c, view);
            }
        }, 0L, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewShowRecommend$lambda$7(MeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.httpPostInviterSave();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewShowRecommend$lambda$8(MeActivity this$0, View view) throws NoSuchMethodException, SecurityException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveQr2Photo();
        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CENTER-EXPAND-SAVEPHOTO", "推广赚钱-保存到相册", null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowUserInfo() {
        bodyBinding(new Function1<MeActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.viewShowUserInfo.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeActivityBinding meActivityBinding) {
                invoke2(meActivityBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull MeActivityBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                ImageView userImgv = bodyBinding.userImgv;
                Intrinsics.checkNotNullExpressionValue(userImgv, "userImgv");
                YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
                YddUserManager.Companion companion2 = YddUserManager.INSTANCE;
                String fileFullUrl = companion.getFileFullUrl(companion2.getInstance().userAvatar());
                int i2 = R.drawable.common_user_icon0;
                ImageViewExtKt.load(userImgv, fileFullUrl, (261628 & 2) != 0 ? 0 : i2, (261628 & 4) != 0 ? 0 : i2, (261628 & 8) != 0 ? false : true, (261628 & 16) != 0 ? false : true, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                bodyBinding.nameEt.setText(companion2.getInstance().userNickName());
                bodyBinding.idTv.setText("ID:" + companion2.getInstance().userId());
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x04ac  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02bc  */
    /* JADX WARN: Type inference failed for: r9v105 */
    /* JADX WARN: Type inference failed for: r9v106 */
    /* JADX WARN: Type inference failed for: r9v108 */
    /* JADX WARN: Type inference failed for: r9v123 */
    /* JADX WARN: Type inference failed for: r9v124 */
    /* JADX WARN: Type inference failed for: r9v136 */
    /* JADX WARN: Type inference failed for: r9v181, types: [android.view.View, androidx.recyclerview.widget.RecyclerView] */
    /* JADX WARN: Type inference failed for: r9v205 */
    /* JADX WARN: Type inference failed for: r9v206 */
    /* JADX WARN: Type inference failed for: r9v208 */
    /* JADX WARN: Type inference failed for: r9v223 */
    /* JADX WARN: Type inference failed for: r9v224 */
    /* JADX WARN: Type inference failed for: r9v236 */
    /* JADX WARN: Type inference failed for: r9v257, types: [android.view.View, androidx.recyclerview.widget.RecyclerView] */
    /* JADX WARN: Type inference failed for: r9v31, types: [android.view.View, androidx.recyclerview.widget.RecyclerView] */
    /* JADX WARN: Type inference failed for: r9v81, types: [android.view.View, androidx.recyclerview.widget.RecyclerView] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void viewTabChange(int r9, int r10) {
        /*
            Method dump skipped, instructions count: 1472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.me.MeActivity.viewTabChange(int, int):void");
    }

    public static /* synthetic */ void viewTabChange$default(MeActivity meActivity, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i2 = 2;
        }
        if ((i4 & 2) != 0) {
            i3 = 0;
        }
        meActivity.viewTabChange(i2, i3);
    }

    private final void viewTabTvChange(final int index) {
        getViewModel().setLastTab(index);
        LogExtKt.logd("viewTabTvChange  下标切换：" + index + " " + getViewModel().getLastTab(), TAG);
        bodyBinding(new Function1<MeActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.viewTabTvChange.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeActivityBinding meActivityBinding) {
                invoke2(meActivityBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull MeActivityBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.oneTv.setBackground(null);
                bodyBinding.twoTv.setBackground(null);
                bodyBinding.threeTv.setBackground(null);
                bodyBinding.fourTv.setBackground(null);
                bodyBinding.fiveTv.setBackground(null);
                bodyBinding.sixTv.setBackground(null);
                TextView textView = bodyBinding.oneTv;
                MeActivity meActivity = MeActivity.this;
                int i2 = R.color.c_173a8e;
                textView.setTextColor(ContextExtKt.getColorM(meActivity, i2));
                bodyBinding.twoTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, i2));
                bodyBinding.threeTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, i2));
                bodyBinding.fourTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, i2));
                bodyBinding.fiveTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, i2));
                bodyBinding.sixTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, i2));
                switch (index) {
                    case 1:
                        bodyBinding.oneTv.setBackgroundResource(R.drawable.me_select_bg);
                        bodyBinding.oneTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, R.color.color_white));
                        break;
                    case 2:
                        bodyBinding.twoTv.setBackgroundResource(R.drawable.me_select_bg);
                        bodyBinding.twoTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, R.color.color_white));
                        break;
                    case 3:
                        bodyBinding.threeTv.setBackgroundResource(R.drawable.me_select_bg);
                        bodyBinding.threeTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, R.color.color_white));
                        break;
                    case 4:
                        bodyBinding.fourTv.setBackgroundResource(R.drawable.me_select_bg);
                        bodyBinding.fourTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, R.color.color_white));
                        break;
                    case 5:
                        bodyBinding.fiveTv.setBackgroundResource(R.drawable.me_select_bg);
                        bodyBinding.fiveTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, R.color.color_white));
                        break;
                    case 6:
                        bodyBinding.sixTv.setBackgroundResource(R.drawable.me_select_bg);
                        bodyBinding.sixTv.setTextColor(ContextExtKt.getColorM(MeActivity.this, R.color.color_white));
                        break;
                }
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08291(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08302(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass3(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass4(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass5(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass6(null), 3, null);
        httpGetPersonInfo();
        getViewModel().httpGetIntegralApp();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.transparentBar();
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_BAR);
        immersionBarWith.statusBarDarkFont(true);
        immersionBarWith.navigationBarDarkIcon(true);
        immersionBarWith.init();
        immersionBarWith.init();
        Intent intent = getIntent();
        if (intent != null) {
            getViewModel().setType(intent.getIntExtra("type", 100));
            int intExtra = intent.getIntExtra("showTabIndex", 1);
            this.mShowTabIndex = intExtra;
            if (intExtra == 1) {
                this.mShowTabIndex = 2;
            }
        }
        this.adapterIntegral = new AdapterIntegral();
        this.adapterBuySkill = new AdapterBuySkill1();
        this.adapterHistory = new AdapterHistory();
        this.adapter100Msg = new AdapterMsg(100, false, false, 6, null);
        AdapterBuySkill1 adapterBuySkill1 = this.adapterBuySkill;
        AdapterHistory adapterHistory = null;
        if (adapterBuySkill1 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
            adapterBuySkill1 = null;
        }
        adapterBuySkill1.setEmptyMShow(true);
        AdapterBuySkill1 adapterBuySkill12 = this.adapterBuySkill;
        if (adapterBuySkill12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
            adapterBuySkill12 = null;
        }
        adapterBuySkill12.setEmptyDawableWidthH(CommonExtKt.dp2px(this, 100), CommonExtKt.dp2px(this, 82));
        AdapterBuySkill1 adapterBuySkill13 = this.adapterBuySkill;
        if (adapterBuySkill13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
            adapterBuySkill13 = null;
        }
        adapterBuySkill13.setEmptyDawableId(R.drawable.me_nodata1);
        AdapterBuySkill1 adapterBuySkill14 = this.adapterBuySkill;
        if (adapterBuySkill14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
            adapterBuySkill14 = null;
        }
        String string = getString(R.string.me_no_tip1);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.me_no_tip1)");
        adapterBuySkill14.setEmptyTip(string);
        AdapterBuySkill1 adapterBuySkill15 = this.adapterBuySkill;
        if (adapterBuySkill15 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
            adapterBuySkill15 = null;
        }
        adapterBuySkill15.setEmptyTipColor(R.color.color_white);
        AdapterBuySkill1 adapterBuySkill16 = this.adapterBuySkill;
        if (adapterBuySkill16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterBuySkill");
            adapterBuySkill16 = null;
        }
        adapterBuySkill16.setOnItemClickListener(new Function2<SkillHome, Integer, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.initParam.3
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
                if ((1 == m2.getLockStatus() || 1 == m2.isPlay()) && NoDoubleClickUtil.isNoDoubleClick(1500)) {
                    MeActivity.this.loadingDialog();
                    Intent intent2 = new Intent();
                    intent2.putExtra("typeId", String.valueOf(m2.getId()));
                    intent2.putExtra("url", m2.getUrl());
                    intent2.putExtra("skillType", m2.getType());
                    if (MeActivity.this.getViewModel().getLastTabDsl() == 0) {
                        intent2.putExtra("CurrentProject", 1);
                    }
                    MeActivity meActivity = MeActivity.this;
                    intent2.setClass(meActivity, U3dGoActivity.class);
                    meActivity.startActivity(intent2);
                    MeActivity meActivity2 = MeActivity.this;
                    Intent intent3 = new Intent();
                    intent3.setClass(meActivity2, U3dActivity.class);
                    meActivity2.startActivity(intent3);
                    sendEmptyMessageDelayed(102, 1200L);
                }
                MeActivity.this.getViewModel().setExamList(null);
                MeActivity.this.getViewModel().setTrainList(null);
            }
        });
        AdapterHistory adapterHistory2 = this.adapterHistory;
        if (adapterHistory2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
        } else {
            adapterHistory = adapterHistory2;
        }
        adapterHistory.setOnItemClickListener(new Function2<SkillHome, Integer, Unit>() { // from class: com.yddmi.doctor.pages.me.MeActivity.initParam.4
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
                MeActivity.this.loadingDialog();
                MeActivity.this.getViewModel().httpGetPracticeRecord(m2);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C08331());
        viewShowUserInfo();
        int i2 = this.mShowTabIndex;
        if (i2 == 1 || i2 >= 6) {
            return;
        }
        viewTabChange$default(this, i2, 0, 2, null);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(getWindow());
        removeCallbacksAndMessages(null);
        getViewModel().setLastTab(1);
        getViewModel().setTrainIndex(1);
        getViewModel().setExamIndex(1);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        GlobalAction globalAction = GlobalAction.INSTANCE;
        if (globalAction.getHomeDataRefresh()) {
            globalAction.setHomeDataRefresh(false);
            getViewModel().httpGetSkillMy();
        }
    }
}
