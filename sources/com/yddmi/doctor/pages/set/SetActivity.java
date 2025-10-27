package com.yddmi.doctor.pages.set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.NoDoubleClickUtil;
import com.catchpig.mvvm.widget.dsbridge.DWebView;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.StringExtKt;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.noober.background.drawable.DrawableCreator;
import com.noober.background.view.BLTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.SetActivityBinding;
import com.yddmi.doctor.entity.request.BankReqResult;
import com.yddmi.doctor.entity.request.LoginWxBindingReq;
import com.yddmi.doctor.entity.request.LoginWxReBindingReq;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.entity.result.WxToken;
import com.yddmi.doctor.pages.me.AdapterMsg;
import com.yddmi.doctor.pages.set.PopupIdBank;
import com.yddmi.doctor.pages.set.SetActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.widget.PopupLogoutDialog;
import com.yikaobang.yixue.R2;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import net.center.blurview.ShapeBlurView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0019\b\u0007\u0018\u0000 02\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u00010B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0002J\b\u0010\u000f\u001a\u00020\rH\u0002J\b\u0010\u0010\u001a\u00020\rH\u0007J\b\u0010\u0011\u001a\u00020\rH\u0002J\b\u0010\u0012\u001a\u00020\rH\u0002J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001a\u0010\u0016\u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\u001a\u0010\u001a\u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u000bH\u0002J\b\u0010\u001b\u001a\u00020\rH\u0002J\b\u0010\u001c\u001a\u00020\rH\u0002J\b\u0010\u001d\u001a\u00020\rH\u0002J\b\u0010\u001e\u001a\u00020\rH\u0002J\b\u0010\u001f\u001a\u00020\rH\u0016J\b\u0010 \u001a\u00020\rH\u0016J\b\u0010!\u001a\u00020\rH\u0016J\b\u0010\"\u001a\u00020\rH\u0014J\b\u0010#\u001a\u00020\rH\u0014J\b\u0010$\u001a\u00020\rH\u0014J\b\u0010%\u001a\u00020\rH\u0002J\b\u0010&\u001a\u00020\rH\u0002J\b\u0010'\u001a\u00020\rH\u0002J\u0012\u0010(\u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\b\u0010)\u001a\u00020\rH\u0002J\u0012\u0010*\u001a\u00020\r2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\b\u0010+\u001a\u00020\rH\u0002J\u000e\u0010,\u001a\u00020\r2\u0006\u0010-\u001a\u00020\u000bJ\u0010\u0010.\u001a\u00020\r2\u0006\u0010/\u001a\u00020\u000bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lcom/yddmi/doctor/pages/set/SetActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/SetActivityBinding;", "Lcom/yddmi/doctor/pages/set/SetViewModel;", "()V", "adapterFeedback", "Lcom/yddmi/doctor/pages/me/AdapterMsg;", "adapterMsg", "mPopupIdBank", "Lcom/yddmi/doctor/pages/set/PopupIdBank;", "mShowTabIndex", "", "dealFeedBack", "", "dealWx", "dealWxLogin", "eventDeal", "httpDealSendCode", "httpGetPersonInfo", "httpGetPostUserBankInfo", TtmlNode.TAG_BODY, "Lcom/yddmi/doctor/entity/request/BankReqResult;", "httpGetReadNoticeId", "m", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "postion", "httpPostAppRead", "httpSavePsw", "httpUserAccountCancel", "httpUserBinding", "httpUserReBinding", "initFlow", "initParam", "initView", "onDestroy", "onStart", "onStop", "viewPopInviteCode", "viewPopWxQr", "viewSetSendCodeBg", "viewShowFeedbackDetails", "viewShowLogoutPop", "viewShowMsgDetails", "viewStatus", "viewSwitchTab", "tab", "viewTabTvChange", "index", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nSetActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetActivity.kt\ncom/yddmi/doctor/pages/set/SetActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,889:1\n18#2,2:890\n1#3:892\n*S KotlinDebug\n*F\n+ 1 SetActivity.kt\ncom/yddmi/doctor/pages/set/SetActivity\n*L\n82#1:890,2\n82#1:892\n*E\n"})
/* loaded from: classes6.dex */
public final class SetActivity extends BaseVMActivity<SetActivityBinding, SetViewModel> {

    @NotNull
    private static final String TAG = "SetActivity";
    private AdapterMsg adapterFeedback;
    private AdapterMsg adapterMsg;

    @Nullable
    private PopupIdBank mPopupIdBank;
    private int mShowTabIndex = 1;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetActivity$initFlow$1", f = "SetActivity.kt", i = {}, l = {323}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08801 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08801(Continuation<? super C08801> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetActivity.this.new C08801(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08801) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Integer> timeCostMsf = SetActivity.this.getViewModel().getTimeCostMsf();
                final SetActivity setActivity = SetActivity.this;
                FlowCollector<? super Integer> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).intValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(int i3, @NotNull Continuation<? super Unit> continuation) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                        if (i3 > -1) {
                            if (i3 == 0) {
                                SetActivity.access$getBodyBinding(setActivity).sendCodeTv.setEnabled(true);
                                SetActivity.access$getBodyBinding(setActivity).sendCodeTv.setText(setActivity.getString(R.string.login_send_num_again));
                                setActivity.viewSetSendCodeBg();
                            } else {
                                SetActivity.access$getBodyBinding(setActivity).sendCodeTv.setText(i3 + setActivity.getString(R.string.time_seconds));
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (timeCostMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetActivity$initFlow$2", f = "SetActivity.kt", i = {}, l = {R2.attr.app_title_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C08812 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08812(Continuation<? super C08812> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetActivity.this.new C08812(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08812) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> skillMsf = SetActivity.this.getViewModel().getSkillMsf();
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (skillMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetActivity$initFlow$3", f = "SetActivity.kt", i = {}, l = {R2.attr.applyMotionScene}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetActivity$initFlow$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetActivity.this.new AnonymousClass3(continuation);
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
                MutableStateFlow<Long> feedbackMsf = SetActivity.this.getViewModel().getFeedbackMsf();
                final SetActivity setActivity = SetActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetActivity.initFlow.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > -1) {
                            setActivity.hideLoading();
                            if (j2 > 200) {
                                SetActivity.access$getBodyBinding(setActivity).submitCl.setVisibility(8);
                                SetActivity.access$getBodyBinding(setActivity).twoFl.setVisibility(0);
                                SetActivity.access$getBodyBinding(setActivity).goSubmitTv.setVisibility(0);
                                SetActivity.access$getBodyBinding(setActivity).et.setText("");
                                Toaster.show(R.string.common_commit_success);
                                SetActivity.access$getBodyBinding(setActivity).fiveSrl.autoRefresh();
                            } else {
                                Toaster.show(R.string.common_commit_error);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (feedbackMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetActivity$initFlow$4", f = "SetActivity.kt", i = {}, l = {360}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetActivity$initFlow$4, reason: invalid class name */
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetActivity.this.new AnonymousClass4(continuation);
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
                MutableStateFlow<Long> feedBackListMsf = SetActivity.this.getViewModel().getFeedBackListMsf();
                final SetActivity setActivity = SetActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetActivity.initFlow.4.1
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
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            r3.hideLoading()
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r3 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.fiveSrl
                            r3.finishRefresh()
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r3 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.fiveSrl
                            r3.finishLoadMore()
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.pages.me.AdapterMsg r3 = com.yddmi.doctor.pages.set.SetActivity.access$getAdapterFeedback$p(r3)
                            r4 = 0
                            if (r3 != 0) goto L30
                            java.lang.String r3 = "adapterFeedback"
                            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
                            r3 = r4
                        L30:
                            com.yddmi.doctor.pages.set.SetActivity r5 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r5 = r5.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r5 = (com.yddmi.doctor.pages.set.SetViewModel) r5
                            com.yddmi.doctor.entity.result.HomeMsgList r5 = r5.getFeedBackList()
                            if (r5 == 0) goto L42
                            java.util.List r4 = r5.getRows()
                        L42:
                            r3.set(r4)
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r3 = (com.yddmi.doctor.pages.set.SetViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getFeedBackList()
                            if (r3 == 0) goto Lb7
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r3 = (com.yddmi.doctor.pages.set.SetViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getFeedBackList()
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
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r3 = (com.yddmi.doctor.pages.set.SetViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getFeedBackList()
                            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                            java.util.List r3 = r3.getRows()
                            if (r3 == 0) goto L93
                            int r3 = r3.size()
                            goto L94
                        L93:
                            r3 = r5
                        L94:
                            com.yddmi.doctor.pages.set.SetActivity r0 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r0 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r0)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r0.fiveSrl
                            com.yddmi.doctor.pages.set.SetActivity r1 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r1 = r1.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r1 = (com.yddmi.doctor.pages.set.SetViewModel) r1
                            com.yddmi.doctor.entity.result.HomeMsgList r1 = r1.getFeedBackList()
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
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r3 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.fiveSrl
                            r3.finishRefreshWithNoMoreData()
                        Lc2:
                            kotlin.Unit r3 = kotlin.Unit.INSTANCE
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.set.SetActivity.AnonymousClass4.AnonymousClass1.emit(long, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                };
                this.label = 1;
                if (feedBackListMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetActivity$initFlow$5", f = "SetActivity.kt", i = {}, l = {R2.attr.arcTickPadding}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetActivity$initFlow$5, reason: invalid class name */
    public static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass5(Continuation<? super AnonymousClass5> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetActivity.this.new AnonymousClass5(continuation);
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
                MutableStateFlow<Long> noticeMsf = SetActivity.this.getViewModel().getNoticeMsf();
                final SetActivity setActivity = SetActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetActivity.initFlow.5.1
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
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            r3.hideLoading()
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r3 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.msgSrl
                            r3.finishRefresh()
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r3 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.msgSrl
                            r3.finishLoadMore()
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.pages.me.AdapterMsg r3 = com.yddmi.doctor.pages.set.SetActivity.access$getAdapterMsg$p(r3)
                            r4 = 0
                            if (r3 != 0) goto L30
                            java.lang.String r3 = "adapterMsg"
                            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r3)
                            r3 = r4
                        L30:
                            com.yddmi.doctor.pages.set.SetActivity r5 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r5 = r5.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r5 = (com.yddmi.doctor.pages.set.SetViewModel) r5
                            com.yddmi.doctor.entity.result.HomeMsgList r5 = r5.getNoticeMsgList()
                            if (r5 == 0) goto L42
                            java.util.List r4 = r5.getRows()
                        L42:
                            r3.set(r4)
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r3 = (com.yddmi.doctor.pages.set.SetViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getNoticeMsgList()
                            if (r3 == 0) goto Lb7
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r3 = (com.yddmi.doctor.pages.set.SetViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getNoticeMsgList()
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
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r3 = r3.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r3 = (com.yddmi.doctor.pages.set.SetViewModel) r3
                            com.yddmi.doctor.entity.result.HomeMsgList r3 = r3.getNoticeMsgList()
                            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                            java.util.List r3 = r3.getRows()
                            if (r3 == 0) goto L93
                            int r3 = r3.size()
                            goto L94
                        L93:
                            r3 = r5
                        L94:
                            com.yddmi.doctor.pages.set.SetActivity r0 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r0 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r0)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r0.msgSrl
                            com.yddmi.doctor.pages.set.SetActivity r1 = r1
                            com.catchpig.mvvm.base.viewmodel.BaseViewModel r1 = r1.getViewModel()
                            com.yddmi.doctor.pages.set.SetViewModel r1 = (com.yddmi.doctor.pages.set.SetViewModel) r1
                            com.yddmi.doctor.entity.result.HomeMsgList r1 = r1.getNoticeMsgList()
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
                            com.yddmi.doctor.pages.set.SetActivity r3 = r1
                            com.yddmi.doctor.databinding.SetActivityBinding r3 = com.yddmi.doctor.pages.set.SetActivity.access$getBodyBinding(r3)
                            com.scwang.smart.refresh.layout.SmartRefreshLayout r3 = r3.msgSrl
                            r3.finishRefreshWithNoMoreData()
                        Lc2:
                            kotlin.Unit r3 = kotlin.Unit.INSTANCE
                            return r3
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.set.SetActivity.AnonymousClass5.AnonymousClass1.emit(long, kotlin.coroutines.Continuation):java.lang.Object");
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetActivity$initFlow$6", f = "SetActivity.kt", i = {}, l = {R2.attr.autoTransition}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetActivity$initFlow$6, reason: invalid class name */
    public static final class AnonymousClass6 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass6(Continuation<? super AnonymousClass6> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetActivity.this.new AnonymousClass6(continuation);
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
                MutableStateFlow<Long> bankInfoMsf = SetActivity.this.getViewModel().getBankInfoMsf();
                final SetActivity setActivity = SetActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetActivity.initFlow.6.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > -1) {
                            if (setActivity.getViewModel().getMBankInfo() == null) {
                                SetActivity.access$getBodyBinding(setActivity).dotV.setVisibility(0);
                            } else {
                                SetActivity.access$getBodyBinding(setActivity).dotV.setVisibility(4);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (bankInfoMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/SetActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.set.SetActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08821 extends Lambda implements Function1<SetActivityBinding, Unit> {
        public C08821() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewSwitchTab(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$10(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CONFIG-EXIT", "退出帐号", null, 4, null);
            BusUtils.post(GlobalAction.eventPointSave);
            this$0.viewShowLogoutPop();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$11(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.blurView.setVisibility(0);
            this_bodyBinding.pswCl.setVisibility(0);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CONFIG-ACCOUNT-EDITPSW", "账号设置-修改密码", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$12(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealWx();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$13(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.blurView.setVisibility(0);
            this_bodyBinding.logoffCl.setVisibility(0);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CONFIG-ACCOUNT-CANCEL", "账号设置-注销账号", null, 4, null);
            BusUtils.post(GlobalAction.eventPointSave);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$14(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewPopInviteCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$15(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.pswCl.setVisibility(8);
            this_bodyBinding.logoffCl.setVisibility(8);
            this_bodyBinding.blurView.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean invoke$lambda$16(SetActivityBinding this_bodyBinding, SetActivity this$0, TextView textView, int i2, KeyEvent keyEvent) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (i2 != 4 || !this_bodyBinding.sendCodeTv.isEnabled()) {
                StringExtKt.isVerifyCode(StringsKt__StringsKt.trim((CharSequence) SetActivity.access$getBodyBinding(this$0).codeEt.getText().toString()).toString());
                return false;
            }
            this$0.httpDealSendCode();
            this_bodyBinding.codeEt.setImeOptions(5);
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$17(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.detailsCl.setVisibility(8);
            this_bodyBinding.fiveBfl.setVisibility(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$18(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.submitCl.setVisibility(8);
            this_bodyBinding.twoFl.setVisibility(0);
            this_bodyBinding.goSubmitTv.setVisibility(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$19(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.submitCl.setVisibility(0);
            this_bodyBinding.twoFl.setVisibility(8);
            this_bodyBinding.goSubmitTv.setVisibility(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewSwitchTab(2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$20(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dealFeedBack();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$22(SetActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.getViewModel().httpGetReplyAppList(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$23(SetActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.getViewModel().httpGetReplyAppList(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$24(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.msgBfl.setVisibility(0);
            this_bodyBinding.msgDetailsCl.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$26(SetActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.getViewModel().getNoticeList(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$27(SetActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.getViewModel().getNoticeList(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewSwitchTab(3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewSwitchTab(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewSwitchTab(5);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(SetActivity this$0, View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpDealSendCode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$7(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpSavePsw();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$8(SetActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpUserAccountCancel();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$9(SetActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.logoffCl.setVisibility(8);
            this_bodyBinding.blurView.setVisibility(8);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(SetActivityBinding setActivityBinding) {
            invoke2(setActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final SetActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final SetActivity setActivity = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$0(setActivity, view);
                }
            }, 0L, 2, null);
            TextView oneTv = bodyBinding.oneTv;
            Intrinsics.checkNotNullExpressionValue(oneTv, "oneTv");
            final SetActivity setActivity2 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(oneTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$1(setActivity2, view);
                }
            }, 0L, 2, null);
            TextView twoTv = bodyBinding.twoTv;
            Intrinsics.checkNotNullExpressionValue(twoTv, "twoTv");
            final SetActivity setActivity3 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(twoTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$2(setActivity3, view);
                }
            }, 0L, 2, null);
            TextView threeTv = bodyBinding.threeTv;
            Intrinsics.checkNotNullExpressionValue(threeTv, "threeTv");
            final SetActivity setActivity4 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(threeTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$3(setActivity4, view);
                }
            }, 0L, 2, null);
            TextView fourTv = bodyBinding.fourTv;
            Intrinsics.checkNotNullExpressionValue(fourTv, "fourTv");
            final SetActivity setActivity5 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(fourTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$4(setActivity5, view);
                }
            }, 0L, 2, null);
            TextView fiveTv = bodyBinding.fiveTv;
            Intrinsics.checkNotNullExpressionValue(fiveTv, "fiveTv");
            final SetActivity setActivity6 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(fiveTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$5(setActivity6, view);
                }
            }, 0L, 2, null);
            BLTextView sendCodeTv = bodyBinding.sendCodeTv;
            Intrinsics.checkNotNullExpressionValue(sendCodeTv, "sendCodeTv");
            final SetActivity setActivity7 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(sendCodeTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.u
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    SetActivity.C08821.invoke$lambda$6(setActivity7, view);
                }
            }, 0L, 2, null);
            ImageView downImgv = bodyBinding.downImgv;
            Intrinsics.checkNotNullExpressionValue(downImgv, "downImgv");
            final SetActivity setActivity8 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(downImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.v
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$7(setActivity8, view);
                }
            }, 0L, 2, null);
            TextView leftTv = bodyBinding.leftTv;
            Intrinsics.checkNotNullExpressionValue(leftTv, "leftTv");
            final SetActivity setActivity9 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(leftTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.w
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$8(setActivity9, view);
                }
            }, 0L, 2, null);
            TextView rightTv = bodyBinding.rightTv;
            Intrinsics.checkNotNullExpressionValue(rightTv, "rightTv");
            ViewExtKt.setDebounceClickListener$default(rightTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.x
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$9(bodyBinding, view);
                }
            }, 0L, 2, null);
            TextView logoutTv = bodyBinding.logoutTv;
            Intrinsics.checkNotNullExpressionValue(logoutTv, "logoutTv");
            final SetActivity setActivity10 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(logoutTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$10(setActivity10, view);
                }
            }, 0L, 2, null);
            ConstraintLayout itemPswCl = bodyBinding.itemPswCl;
            Intrinsics.checkNotNullExpressionValue(itemPswCl, "itemPswCl");
            ViewExtKt.setDebounceClickListener$default(itemPswCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.y
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$11(bodyBinding, view);
                }
            }, 0L, 2, null);
            ConstraintLayout itemWxCl = bodyBinding.itemWxCl;
            Intrinsics.checkNotNullExpressionValue(itemWxCl, "itemWxCl");
            final SetActivity setActivity11 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(itemWxCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.z
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$12(setActivity11, view);
                }
            }, 0L, 2, null);
            ConstraintLayout itemLogeoffCl = bodyBinding.itemLogeoffCl;
            Intrinsics.checkNotNullExpressionValue(itemLogeoffCl, "itemLogeoffCl");
            ViewExtKt.setDebounceClickListener$default(itemLogeoffCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$13(bodyBinding, view);
                }
            }, 0L, 2, null);
            ConstraintLayout itemBankCl = bodyBinding.itemBankCl;
            Intrinsics.checkNotNullExpressionValue(itemBankCl, "itemBankCl");
            final SetActivity setActivity12 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(itemBankCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.b0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$14(setActivity12, view);
                }
            }, 0L, 2, null);
            bodyBinding.pswCl.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.16
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    return true;
                }
            });
            bodyBinding.logoffCl.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.17
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    return true;
                }
            });
            ShapeBlurView blurView = bodyBinding.blurView;
            Intrinsics.checkNotNullExpressionValue(blurView, "blurView");
            ViewExtKt.setDebounceClickListener$default(blurView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.c0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$15(bodyBinding, view);
                }
            }, 0L, 2, null);
            EditText editText = bodyBinding.codeEt;
            final SetActivity setActivity13 = SetActivity.this;
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.yddmi.doctor.pages.set.d0
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                    return SetActivity.C08821.invoke$lambda$16(bodyBinding, setActivity13, textView, i2, keyEvent);
                }
            });
            bodyBinding.webV.setBackgroundColor(0);
            bodyBinding.webV.getBackground().setAlpha(0);
            bodyBinding.web2V.setBackgroundColor(0);
            bodyBinding.web2V.getBackground().setAlpha(0);
            DWebView dWebView = bodyBinding.webV;
            YddConfig yddConfig = YddConfig.INSTANCE;
            dWebView.loadUrl(yddConfig.getWebAgreeUrl());
            bodyBinding.web2V.loadUrl(yddConfig.getWebPrivacyUrl());
            if (YddUserManager.INSTANCE.getInstance().getmWxApi().isWXAppInstalled()) {
                bodyBinding.itemWxCl.setVisibility(0);
            } else {
                bodyBinding.itemWxCl.setVisibility(8);
            }
            ImageView x110Imgv = bodyBinding.x110Imgv;
            Intrinsics.checkNotNullExpressionValue(x110Imgv, "x110Imgv");
            ViewExtKt.setDebounceClickListener$default(x110Imgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.e0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$17(bodyBinding, view);
                }
            }, 0L, 2, null);
            ImageView x111Imgv = bodyBinding.x111Imgv;
            Intrinsics.checkNotNullExpressionValue(x111Imgv, "x111Imgv");
            ViewExtKt.setDebounceClickListener$default(x111Imgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.f0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$18(bodyBinding, view);
                }
            }, 0L, 2, null);
            BLTextView goSubmitTv = bodyBinding.goSubmitTv;
            Intrinsics.checkNotNullExpressionValue(goSubmitTv, "goSubmitTv");
            ViewExtKt.setDebounceClickListener$default(goSubmitTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$19(bodyBinding, view);
                }
            }, 0L, 2, null);
            TextView submitTv = bodyBinding.submitTv;
            Intrinsics.checkNotNullExpressionValue(submitTv, "submitTv");
            final SetActivity setActivity14 = SetActivity.this;
            ViewExtKt.setDebounceClickListener$default(submitTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$20(setActivity14, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView = bodyBinding.fiveRv;
            SetActivity setActivity15 = SetActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            AdapterMsg adapterMsg = setActivity15.adapterFeedback;
            AdapterMsg adapterMsg2 = null;
            if (adapterMsg == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterFeedback");
                adapterMsg = null;
            }
            recyclerView.setAdapter(adapterMsg);
            AdapterMsg adapterMsg3 = SetActivity.this.adapterFeedback;
            if (adapterMsg3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterFeedback");
                adapterMsg3 = null;
            }
            final SetActivity setActivity16 = SetActivity.this;
            adapterMsg3.setOnItemClickListener(new Function2<HomeMsg, Integer, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.25
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(HomeMsg homeMsg, Integer num) {
                    invoke(homeMsg, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(@Nullable HomeMsg homeMsg, int i2) {
                    setActivity16.viewShowFeedbackDetails(homeMsg);
                    setActivity16.httpPostAppRead(homeMsg, i2);
                }
            });
            SmartRefreshLayout smartRefreshLayout = bodyBinding.fiveSrl;
            final SetActivity setActivity17 = SetActivity.this;
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.yddmi.doctor.pages.set.k
                @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    SetActivity.C08821.invoke$lambda$22(setActivity17, refreshLayout);
                }
            });
            SmartRefreshLayout smartRefreshLayout2 = bodyBinding.fiveSrl;
            final SetActivity setActivity18 = SetActivity.this;
            smartRefreshLayout2.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.yddmi.doctor.pages.set.l
                @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
                public final void onLoadMore(RefreshLayout refreshLayout) {
                    SetActivity.C08821.invoke$lambda$23(setActivity18, refreshLayout);
                }
            });
            bodyBinding.fiveRv.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.28
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    if (event == null) {
                        return false;
                    }
                    SetActivityBinding setActivityBinding = bodyBinding;
                    if (event.getAction() != 1) {
                        return false;
                    }
                    setActivityBinding.msgScrol0lImgv.setVisibility(4);
                    return false;
                }
            });
            ImageView x116Imgv = bodyBinding.x116Imgv;
            Intrinsics.checkNotNullExpressionValue(x116Imgv, "x116Imgv");
            ViewExtKt.setDebounceClickListener$default(x116Imgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.m
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetActivity.C08821.invoke$lambda$24(bodyBinding, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView2 = bodyBinding.msgRv;
            SetActivity setActivity19 = SetActivity.this;
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(recyclerView2.getContext());
            linearLayoutManager2.setOrientation(1);
            recyclerView2.setLayoutManager(linearLayoutManager2);
            AdapterMsg adapterMsg4 = setActivity19.adapterMsg;
            if (adapterMsg4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterMsg");
                adapterMsg4 = null;
            }
            recyclerView2.setAdapter(adapterMsg4);
            AdapterMsg adapterMsg5 = SetActivity.this.adapterMsg;
            if (adapterMsg5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterMsg");
            } else {
                adapterMsg2 = adapterMsg5;
            }
            final SetActivity setActivity20 = SetActivity.this;
            adapterMsg2.setOnItemClickListener(new Function2<HomeMsg, Integer, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.31
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(HomeMsg homeMsg, Integer num) {
                    invoke(homeMsg, num.intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(@Nullable HomeMsg homeMsg, int i2) {
                    if (homeMsg != null) {
                        setActivity20.httpGetReadNoticeId(homeMsg, i2);
                    }
                    setActivity20.viewShowMsgDetails(homeMsg);
                }
            });
            SmartRefreshLayout smartRefreshLayout3 = bodyBinding.msgSrl;
            final SetActivity setActivity21 = SetActivity.this;
            smartRefreshLayout3.setOnRefreshListener(new OnRefreshListener() { // from class: com.yddmi.doctor.pages.set.n
                @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    SetActivity.C08821.invoke$lambda$26(setActivity21, refreshLayout);
                }
            });
            SmartRefreshLayout smartRefreshLayout4 = bodyBinding.msgSrl;
            final SetActivity setActivity22 = SetActivity.this;
            smartRefreshLayout4.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.yddmi.doctor.pages.set.o
                @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
                public final void onLoadMore(RefreshLayout refreshLayout) {
                    SetActivity.C08821.invoke$lambda$27(setActivity22, refreshLayout);
                }
            });
            bodyBinding.msgRv.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.34
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    if (event == null) {
                        return false;
                    }
                    SetActivityBinding setActivityBinding = bodyBinding;
                    if (event.getAction() != 1) {
                        return false;
                    }
                    setActivityBinding.msgImgv.setVisibility(4);
                    return false;
                }
            });
            bodyBinding.msgScrollV.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.35
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    if (event == null) {
                        return false;
                    }
                    SetActivityBinding setActivityBinding = bodyBinding;
                    if (event.getAction() != 1) {
                        return false;
                    }
                    setActivityBinding.msg1ScrollImgv.setVisibility(4);
                    return false;
                }
            });
            bodyBinding.webV.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.36
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    if (event == null) {
                        return false;
                    }
                    SetActivityBinding setActivityBinding = bodyBinding;
                    if (event.getAction() != 1) {
                        return false;
                    }
                    setActivityBinding.webImgv.setVisibility(4);
                    return false;
                }
            });
            bodyBinding.web2V.setOnTouchListener(new View.OnTouchListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.initView.1.37
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(@Nullable View v2, @Nullable MotionEvent event) {
                    if (event == null) {
                        return false;
                    }
                    SetActivityBinding setActivityBinding = bodyBinding;
                    if (event.getAction() != 1) {
                        return false;
                    }
                    setActivityBinding.web2Imgv.setVisibility(4);
                    return false;
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ SetActivityBinding access$getBodyBinding(SetActivity setActivity) {
        return (SetActivityBinding) setActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void dealFeedBack() {
        String string = StringsKt__StringsKt.trim((CharSequence) String.valueOf(((SetActivityBinding) getBodyBinding()).et.getText())).toString();
        if ((string.length() == 0) || string.length() < 5) {
            Toaster.show(R.string.me_feedback_tip2);
            return;
        }
        loadingDialog();
        SetViewModel viewModel = getViewModel();
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        SetViewModel.httpPostReplySave$default(viewModel, string, companion.getInstance().userName(), companion.getInstance().userPhone(), null, 8, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealWx() {
        if (YddUserManager.INSTANCE.getInstance().getWxUnionFlag() == 1) {
            httpUserReBinding();
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CONFIG-ACCOUNT-UNBINDWX", "账号设置-解绑微信", null, 4, null);
        } else {
            dealWxLogin();
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CONFIG-ACCOUNT-BINDWX", "账号设置-绑定微信", null, 4, null);
        }
    }

    private final void dealWxLogin() {
        YddUserManager.Companion companion = YddUserManager.INSTANCE;
        if (!companion.getInstance().getmWxApi().isWXAppInstalled()) {
            Toaster.show(R.string.me_wx_no);
            return;
        }
        GlobalAction.INSTANCE.setWxBinding(true);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login";
        companion.getInstance().getmWxApi().sendReq(req);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpDealSendCode() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (getViewModel().getTimeCostMsf().getValue().intValue() == 0 || -1 == getViewModel().getTimeCostMsf().getValue().intValue()) {
            String string = StringsKt__StringsKt.trim((CharSequence) ((SetActivityBinding) getBodyBinding()).phoneEt.getText().toString()).toString();
            if (!StringExtKt.isPhoneNumber(string)) {
                Toaster.show(R.string.login_phone_error);
                return;
            }
            ((SetActivityBinding) getBodyBinding()).sendCodeTv.setEnabled(false);
            viewSetSendCodeBg();
            FlowExtKt.lifecycle(getViewModel().getPushCodeForgetPwd(string), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpDealSendCode.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String message = it.getMessage();
                    if (message != null) {
                        Toaster.show((CharSequence) message);
                    }
                    SetActivity.access$getBodyBinding(SetActivity.this).sendCodeTv.setEnabled(true);
                    SetActivity.this.viewSetSendCodeBg();
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpDealSendCode.2
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
                    SetActivity.this.getViewModel().dealTimeGo();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPersonInfo() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getPersonInfoApp(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpGetPersonInfo.1
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
            }, new Function1<MeProfile, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpGetPersonInfo.2
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
                        SetActivity setActivity = SetActivity.this;
                        YddUserManager.userInfoSave$default(YddUserManager.INSTANCE.getInstance(), null, meProfile, 1, null);
                        setActivity.viewStatus();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetPostUserBankInfo(BankReqResult body) {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getPostUserBankInfo(body), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpGetPostUserBankInfo.1
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
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpGetPostUserBankInfo.2
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
                    PopupIdBank popupIdBank = SetActivity.this.mPopupIdBank;
                    if (popupIdBank != null) {
                        popupIdBank.dismiss();
                    }
                    SetActivity.this.getViewModel().httpGetPostUserBankInfoV();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetReadNoticeId(final HomeMsg m2, final int postion) {
        if (m2 != null) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getReadNoticeId(m2.getId()), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity$httpGetReadNoticeId$1$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity$httpGetReadNoticeId$1$2
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
                    AdapterMsg adapterMsg = this.adapterMsg;
                    if (adapterMsg == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapterMsg");
                        adapterMsg = null;
                    }
                    adapterMsg.notifyItemChanged(postion);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostAppRead(final HomeMsg m2, final int postion) {
        if (m2 != null) {
            FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.postAppRead(String.valueOf(m2.getId())), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity$httpPostAppRead$1$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity$httpPostAppRead$1$2
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
                    AdapterMsg adapterMsg = this.adapterFeedback;
                    if (adapterMsg == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapterFeedback");
                        adapterMsg = null;
                    }
                    adapterMsg.notifyItemChanged(postion);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void httpSavePsw() {
        String string = StringsKt__StringsKt.trim((CharSequence) ((SetActivityBinding) getBodyBinding()).phoneEt.getText().toString()).toString();
        String string2 = StringsKt__StringsKt.trim((CharSequence) ((SetActivityBinding) getBodyBinding()).codeEt.getText().toString()).toString();
        String string3 = StringsKt__StringsKt.trim((CharSequence) ((SetActivityBinding) getBodyBinding()).pswEt.getText().toString()).toString();
        String string4 = StringsKt__StringsKt.trim((CharSequence) ((SetActivityBinding) getBodyBinding()).pswAgainEt.getText().toString()).toString();
        if (!StringExtKt.isPhoneNumber(string)) {
            Toaster.show(R.string.login_phone_error);
            return;
        }
        if (!StringExtKt.isVerifyCode(string2)) {
            Toaster.show(R.string.login_code2);
            return;
        }
        if (!StringExtKt.isPswVerify(string3)) {
            Toaster.show(R.string.login_psw_tip);
        } else if (Intrinsics.areEqual(string3, string4)) {
            FlowExtKt.lifecycleLoadingDialog(getViewModel().forgetPwd(string, string2, string3), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpSavePsw.1
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
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpSavePsw.2
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
                    Toaster.show(R.string.me_set_psw_success);
                    SetActivity.this.closeActivity();
                }
            });
        } else {
            Toaster.show(R.string.login_psw_different);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpUserAccountCancel() {
        FlowExtKt.lifecycleLoadingDialog(getViewModel().userAccountCancel(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpUserAccountCancel.1
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
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpUserAccountCancel.2
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
                BusUtils.post(GlobalAction.eventLogoff);
                YddUserManager.INSTANCE.getInstance().logoutUser(true);
                SetActivity.this.closeActivity();
            }
        });
    }

    private final void httpUserBinding() {
        Object obj = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_WX_TOKEN);
        WxToken wxToken = obj instanceof WxToken ? (WxToken) obj : null;
        LogExtKt.logd("httpUserBinding() 微信绑定 " + wxToken, TAG);
        if (wxToken == null) {
            Toaster.show(R.string.login_binding_error1);
        } else {
            FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postUserBinding(new LoginWxBindingReq(wxToken.getUnionid(), wxToken.getOpenid(), YddUserManager.INSTANCE.getInstance().userId(), 1, wxToken.getMCode(), wxToken.getAccess_token())), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpUserBinding.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                    invoke2(th);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull Throwable it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String message = it.getMessage();
                    if (message == null || message.length() == 0) {
                        return;
                    }
                    Toaster.show((CharSequence) it.getMessage());
                }
            }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpUserBinding.2
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
                    SetActivity.this.httpGetPersonInfo();
                    SetActivity.this.viewStatus();
                    Toaster.show(R.string.me_wx_bind);
                }
            });
        }
    }

    private final void httpUserReBinding() {
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.postUserReBinding(new LoginWxReBindingReq(YddUserManager.INSTANCE.getInstance().userId(), 1)), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpUserReBinding.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String message = it.getMessage();
                if (message == null || message.length() == 0) {
                    return;
                }
                Toaster.show((CharSequence) it.getMessage());
            }
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.httpUserReBinding.2
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
                SetActivity.this.httpGetPersonInfo();
                SetActivity.this.viewStatus();
                Toaster.show(R.string.me_wx_unbind);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopInviteCode() {
        PopupIdBank popupIdBank = this.mPopupIdBank;
        if (popupIdBank != null) {
            popupIdBank.dismiss();
        }
        this.mPopupIdBank = null;
        PopupIdBank popupIdBank2 = new PopupIdBank(this);
        this.mPopupIdBank = popupIdBank2;
        popupIdBank2.setOnPopupCodeClickListener(new PopupIdBank.OnPopupCodeClickListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.viewPopInviteCode.1
            @Override // com.yddmi.doctor.pages.set.PopupIdBank.OnPopupCodeClickListener
            public void onCodeClick(@Nullable View view, @NotNull String phone, @NotNull String name, @NotNull String id, @NotNull String bank, @NotNull String bankOpen) {
                Intrinsics.checkNotNullParameter(phone, "phone");
                Intrinsics.checkNotNullParameter(name, "name");
                Intrinsics.checkNotNullParameter(id, "id");
                Intrinsics.checkNotNullParameter(bank, "bank");
                Intrinsics.checkNotNullParameter(bankOpen, "bankOpen");
                SetActivity.this.httpGetPostUserBankInfo(new BankReqResult(bankOpen, bank, id, name, YddUserManager.INSTANCE.getInstance().userId()));
            }
        });
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(this.mPopupIdBank);
        PopupIdBank popupIdBank3 = this.mPopupIdBank;
        if (popupIdBank3 != null) {
            PopupIdBank.viewStatus$default(popupIdBank3, 100, null, getViewModel().getMBankInfo(), 2, null);
        }
        PopupIdBank popupIdBank4 = this.mPopupIdBank;
        if (popupIdBank4 != null) {
            popupIdBank4.show();
        }
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
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewSetSendCodeBg() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Drawable drawableBuild;
        BLTextView viewSetSendCodeBg$lambda$2 = ((SetActivityBinding) getBodyBinding()).sendCodeTv;
        if (viewSetSendCodeBg$lambda$2.isEnabled()) {
            DrawableCreator.Builder builder = new DrawableCreator.Builder();
            Context context = viewSetSendCodeBg$lambda$2.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            drawableBuild = builder.setCornersRadius(CommonExtKt.dp2px(context, 10)).setSolidColor(ContextExtKt.getColorM(this, R.color.c_2a57c2)).build();
        } else {
            DrawableCreator.Builder builder2 = new DrawableCreator.Builder();
            Context context2 = viewSetSendCodeBg$lambda$2.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "context");
            drawableBuild = builder2.setCornersRadius(CommonExtKt.dp2px(context2, 10)).setSolidColor(ContextExtKt.getColorM(this, R.color.c_8598a2)).build();
        }
        Intrinsics.checkNotNullExpressionValue(viewSetSendCodeBg$lambda$2, "viewSetSendCodeBg$lambda$2");
        ViewExtKt.setBackgroundJellyBean16(viewSetSendCodeBg$lambda$2, drawableBuild);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowFeedbackDetails(final HomeMsg m2) {
        if (m2 == null) {
            Toaster.show(R.string.common_search_no2);
        } else {
            bodyBinding(new Function1<SetActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.viewShowFeedbackDetails.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SetActivityBinding setActivityBinding) {
                    invoke2(setActivityBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull SetActivityBinding bodyBinding) {
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    bodyBinding.detailsCl.setVisibility(0);
                    bodyBinding.fiveBfl.setVisibility(8);
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
    public final void viewShowLogoutPop() {
        PopupLogoutDialog popupLogoutDialog = new PopupLogoutDialog(this);
        XPopup.Builder builderHasNavigationBar = new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).hasStatusBar(false).isViewMode(true).hasBlurBg(true).hasNavigationBar(false);
        Boolean bool = Boolean.FALSE;
        final BasePopupView basePopupViewShow = builderHasNavigationBar.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(popupLogoutDialog).show();
        popupLogoutDialog.setOnPopupLogoutDialogClickListener(new PopupLogoutDialog.OnPopupLogoutDialogClickListener() { // from class: com.yddmi.doctor.pages.set.SetActivity.viewShowLogoutPop.1
            @Override // com.yddmi.doctor.widget.PopupLogoutDialog.OnPopupLogoutDialogClickListener
            public void onClick(@NotNull View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                int id = view.getId();
                if (id == R.id.oneTv) {
                    YddUserManager.INSTANCE.getInstance().logoutUser(true);
                    basePopupViewShow.dismiss();
                    this.closeActivity();
                } else if (id == R.id.twoTv) {
                    basePopupViewShow.dismiss();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShowMsgDetails(final HomeMsg m2) {
        if (m2 == null) {
            Toaster.show(R.string.common_search_no2);
        } else {
            bodyBinding(new Function1<SetActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.viewShowMsgDetails.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SetActivityBinding setActivityBinding) {
                    invoke2(setActivityBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull SetActivityBinding bodyBinding) {
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    bodyBinding.msgBfl.setVisibility(8);
                    bodyBinding.msgDetailsCl.setVisibility(0);
                    TextView textView = bodyBinding.msgTitleTv;
                    String typeName = m2.getTypeName();
                    if (typeName == null) {
                        typeName = "";
                    }
                    textView.setText(typeName);
                    bodyBinding.msgHiTv.setText(m2.getContent());
                    if (m2.getContent().length() > 300) {
                        bodyBinding.msg1ScrollImgv.setVisibility(0);
                    } else {
                        bodyBinding.msg1ScrollImgv.setVisibility(4);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewStatus() {
        bodyBinding(new Function1<SetActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.viewStatus.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SetActivityBinding setActivityBinding) {
                invoke2(setActivityBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull SetActivityBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                YddUserManager.Companion companion = YddUserManager.INSTANCE;
                boolean z2 = true;
                if (companion.getInstance().getWxUnionFlag() != 1) {
                    bodyBinding.wxTipTv.setText(R.string.me_set_wx);
                    bodyBinding.wxTip1Tv.setText(SetActivity.this.getString(R.string.me_set_wx_tipa));
                    return;
                }
                bodyBinding.wxTipTv.setText(R.string.me_set_wx_unbind);
                String wxNickName = companion.getInstance().getWxNickName();
                if (wxNickName != null && wxNickName.length() != 0) {
                    z2 = false;
                }
                if (z2) {
                    bodyBinding.wxTip1Tv.setText(String.valueOf(SetActivity.this.getString(R.string.login_binded)));
                    return;
                }
                bodyBinding.wxTip1Tv.setText(SetActivity.this.getString(R.string.login_binded) + ":(" + companion.getInstance().getWxNickName() + ")");
            }
        });
    }

    private final void viewTabTvChange(final int index) {
        getViewModel().setLastTab(index);
        bodyBinding(new Function1<SetActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.viewTabTvChange.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SetActivityBinding setActivityBinding) {
                invoke2(setActivityBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull SetActivityBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.oneTv.setBackground(null);
                bodyBinding.twoTv.setBackground(null);
                bodyBinding.threeTv.setBackground(null);
                bodyBinding.fourTv.setBackground(null);
                bodyBinding.fiveTv.setBackground(null);
                TextView textView = bodyBinding.oneTv;
                SetActivity setActivity = SetActivity.this;
                int i2 = R.color.c_2a57c2;
                textView.setTextColor(ContextExtKt.getColorM(setActivity, i2));
                bodyBinding.twoTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, i2));
                bodyBinding.threeTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, i2));
                bodyBinding.fourTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, i2));
                bodyBinding.fiveTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, i2));
                int i3 = index;
                if (i3 == 1) {
                    bodyBinding.oneTv.setBackgroundResource(R.drawable.set_left_item_bg);
                    bodyBinding.oneTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, R.color.c_173a8e));
                    return;
                }
                if (i3 == 2) {
                    bodyBinding.twoTv.setBackgroundResource(R.drawable.set_left_item_bg);
                    bodyBinding.twoTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, R.color.c_173a8e));
                    return;
                }
                if (i3 == 3) {
                    bodyBinding.threeTv.setBackgroundResource(R.drawable.set_left_item_bg);
                    bodyBinding.threeTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, R.color.c_173a8e));
                } else if (i3 == 4) {
                    bodyBinding.fourTv.setBackgroundResource(R.drawable.set_left_item_bg);
                    bodyBinding.fourTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, R.color.c_173a8e));
                } else {
                    if (i3 != 5) {
                        return;
                    }
                    bodyBinding.fiveTv.setBackgroundResource(R.drawable.set_left_item_bg);
                    bodyBinding.fiveTv.setTextColor(ContextExtKt.getColorM(SetActivity.this, R.color.c_173a8e));
                }
            }
        });
    }

    @BusUtils.Bus(tag = GlobalAction.eventWxToken)
    public final void eventDeal() {
        LogExtKt.logd("eventDeal() eventWxToken ", TAG);
        if (NoDoubleClickUtil.isDoubleClick()) {
            return;
        }
        httpUserBinding();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08801(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08812(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass3(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass4(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass5(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass6(null), 3, null);
        httpGetPersonInfo();
        getViewModel().httpGetPostUserBankInfoV();
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
            this.mShowTabIndex = intent.getIntExtra("showTabIndex", 1);
        }
        this.adapterFeedback = new AdapterMsg(100, false, false, 6, null);
        this.adapterMsg = new AdapterMsg(101, true, true);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        viewStatus();
        bodyBinding(new C08821());
        int i2 = this.mShowTabIndex;
        if (i2 == 1 || i2 >= 6) {
            return;
        }
        viewSwitchTab(i2);
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        BusUtils.unregister(this);
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

    public final void viewSwitchTab(final int tab) {
        viewTabTvChange(tab);
        bodyBinding(new Function1<SetActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.set.SetActivity.viewSwitchTab.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SetActivityBinding setActivityBinding) {
                invoke2(setActivityBinding);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:22:0x0063  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x00c9  */
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void invoke2(@org.jetbrains.annotations.NotNull com.yddmi.doctor.databinding.SetActivityBinding r7) {
                /*
                    Method dump skipped, instructions count: 360
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.set.SetActivity.C08881.invoke2(com.yddmi.doctor.databinding.SetActivityBinding):void");
            }
        });
    }
}
