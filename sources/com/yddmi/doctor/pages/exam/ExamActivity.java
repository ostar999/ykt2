package com.yddmi.doctor.pages.exam;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.angcyo.tablayout.DslTabLayout;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.SpanExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.exoplayer2.util.MimeTypes;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.databinding.ExamActivityBinding;
import com.yddmi.doctor.entity.result.ExamLeaderBoardItemResult;
import com.yddmi.doctor.entity.result.ExamLeaderBoardResult;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillHomeList;
import com.yddmi.doctor.pages.exam.AdapterTopTree;
import com.yddmi.doctor.pages.exam.ExamActivity;
import com.yddmi.doctor.pages.me.AdapterHistory;
import com.yddmi.doctor.pages.me.PopupHistory;
import com.yddmi.doctor.pages.product.ProductActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 72\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u00017B\u0005¢\u0006\u0002\u0010\u0004J\"\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0002J\u0010\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\fH\u0002J\b\u0010\u0018\u001a\u00020\u0010H\u0002J\b\u0010\u0019\u001a\u00020\u0010H\u0002J\b\u0010\u001a\u001a\u00020\u0010H\u0002J\u0010\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0010H\u0016J\b\u0010\u001f\u001a\u00020\u0010H\u0016J\b\u0010 \u001a\u00020\u0010H\u0016J\b\u0010!\u001a\u00020\u0010H\u0016J\b\u0010\"\u001a\u00020\u0010H\u0014J(\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\f2\u0006\u0010'\u001a\u00020\fH\u0002J\u0010\u0010(\u001a\u00020\u00102\u0006\u0010)\u001a\u00020\fH\u0002J\b\u0010*\u001a\u00020\u0010H\u0002J\u0010\u0010+\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\b\u0010,\u001a\u00020\u0010H\u0002J\b\u0010-\u001a\u00020\u0010H\u0002J\u001a\u0010.\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010/\u001a\u0004\u0018\u000100H\u0002J\u0018\u00101\u001a\u00020\u00102\u000e\u0010/\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u000102H\u0002J\u0012\u00103\u001a\u00020\u00102\b\u0010/\u001a\u0004\u0018\u000104H\u0002J\u0012\u00105\u001a\u00020\u00102\b\b\u0002\u00106\u001a\u00020\fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lcom/yddmi/doctor/pages/exam/ExamActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/ExamActivityBinding;", "Lcom/yddmi/doctor/pages/exam/ExamViewModel;", "()V", "adapterHistory", "Lcom/yddmi/doctor/pages/me/AdapterHistory;", "adapterTop", "Lcom/yddmi/doctor/pages/exam/AdapterTop;", "adapterTopTree", "Lcom/yddmi/doctor/pages/exam/AdapterTopTree;", "mNeedRefresh", "", "maxSkillNum", "", "dealGoBuyAll", "", "type", "skill24", "skillId", "", "dealGoRandom", "dealOpenFirst", "open24", "httpGetRulelatest", "httpGetSkillTimes", "httpPostHorseLamp", "httpPostLeaderBoard", "m", "Lcom/yddmi/doctor/entity/result/SkillHome;", "initFlow", "initParam", "initView", "onBackPressed", "onResume", "viewDslTabChange", "fromIndex", "toIndex", "reselect", "fromUser", "viewIconShow", "history", "viewMaxNumShow", "viewPopMax", "viewPopRule", "viewPopShowHistory", "viewShowLeaderBoard", "data", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardResult;", "viewShowMarquee", "", "viewShowUser", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;", "viewTopShow", "show24", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true)
@SourceDebugExtension({"SMAP\nExamActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExamActivity.kt\ncom/yddmi/doctor/pages/exam/ExamActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,566:1\n18#2,2:567\n1#3:569\n15#4,3:570\n15#4,3:573\n*S KotlinDebug\n*F\n+ 1 ExamActivity.kt\ncom/yddmi/doctor/pages/exam/ExamActivity\n*L\n79#1:567,2\n79#1:569\n295#1:570,3\n325#1:573,3\n*E\n"})
/* loaded from: classes6.dex */
public final class ExamActivity extends BaseVMActivity<ExamActivityBinding, ExamViewModel> {

    @NotNull
    private static final String TAG = "ExamActivity";
    private AdapterHistory adapterHistory;
    private AdapterTop adapterTop;
    private AdapterTopTree adapterTopTree;
    private boolean mNeedRefresh;
    private int maxSkillNum;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamActivity$initFlow$1", f = "ExamActivity.kt", i = {}, l = {227}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06361 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06361(Continuation<? super C06361> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamActivity.this.new C06361(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06361) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> historyMsf = ExamActivity.this.getViewModel().getHistoryMsf();
                final ExamActivity examActivity = ExamActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.initFlow.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            examActivity.hideLoading();
                            examActivity.viewPopShowHistory();
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamActivity$initFlow$2", f = "ExamActivity.kt", i = {}, l = {236}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamActivity$initFlow$2, reason: invalid class name and case insensitive filesystem */
    public static final class C06372 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06372(Continuation<? super C06372> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamActivity.this.new C06372(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06372) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> trainExamListMsf = ExamActivity.this.getViewModel().getTrainExamListMsf();
                final ExamActivity examActivity = ExamActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.initFlow.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:37:0x00d2  */
                    /* JADX WARN: Removed duplicated region for block: B:67:0x0178  */
                    @org.jetbrains.annotations.Nullable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(long r3, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r5) {
                        /*
                            Method dump skipped, instructions count: 390
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.exam.ExamActivity.C06372.AnonymousClass1.emit(long, kotlin.coroutines.Continuation):java.lang.Object");
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamActivity$initFlow$3", f = "ExamActivity.kt", i = {}, l = {R2.attr.adScopeStrokeWidth}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamActivity$initFlow$3, reason: invalid class name */
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamActivity.this.new AnonymousClass3(continuation);
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
                MutableStateFlow<Long> top24ShowMsf = ExamActivity.this.getViewModel().getTop24ShowMsf();
                final ExamActivity examActivity = ExamActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.initFlow.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            examActivity.hideLoading();
                            AdapterTopTree adapterTopTree = examActivity.adapterTopTree;
                            AdapterTopTree adapterTopTree2 = null;
                            if (adapterTopTree == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                                adapterTopTree = null;
                            }
                            adapterTopTree.set(examActivity.getViewModel().getTop24ShowList());
                            AdapterTopTree adapterTopTree3 = examActivity.adapterTopTree;
                            if (adapterTopTree3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                            } else {
                                adapterTopTree2 = adapterTopTree3;
                            }
                            adapterTopTree2.notifyDataSetChanged();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (top24ShowMsf.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamActivity$initFlow$4", f = "ExamActivity.kt", i = {}, l = {R2.attr.add_address_bg_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamActivity$initFlow$4, reason: invalid class name */
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamActivity.this.new AnonymousClass4(continuation);
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
                MutableStateFlow<Long> topBodyShowMsf = ExamActivity.this.getViewModel().getTopBodyShowMsf();
                final ExamActivity examActivity = ExamActivity.this;
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.initFlow.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Number) obj2).longValue(), (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(long j2, @NotNull Continuation<? super Unit> continuation) {
                        if (j2 > 0) {
                            examActivity.hideLoading();
                            AdapterTopTree adapterTopTree = examActivity.adapterTopTree;
                            AdapterTopTree adapterTopTree2 = null;
                            if (adapterTopTree == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                                adapterTopTree = null;
                            }
                            adapterTopTree.set(examActivity.getViewModel().getTopBodyShowList());
                            AdapterTopTree adapterTopTree3 = examActivity.adapterTopTree;
                            if (adapterTopTree3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                            } else {
                                adapterTopTree2 = adapterTopTree3;
                            }
                            adapterTopTree2.notifyDataSetChanged();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (topBodyShowMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/ExamActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.exam.ExamActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06401 extends Lambda implements Function1<ExamActivityBinding, Unit> {
        public C06401() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.httpGetRulelatest();
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-RULE", "规则", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$11(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            List<SkillHome> top24List = this$0.getViewModel().getTop24List();
            if (!(top24List == null || top24List.isEmpty())) {
                this$0.viewTopShow(true);
            } else {
                Toaster.show(R.string.common_no_data2);
                this$0.getViewModel().httpGetSkillHome();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$12(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            List<SkillHome> topBodyList = this$0.getViewModel().getTopBodyList();
            if (!(topBodyList == null || topBodyList.isEmpty())) {
                this$0.viewTopShow(false);
            } else {
                Toaster.show(R.string.common_no_data2);
                this$0.getViewModel().httpGetBodyCheck();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewIconShow(true);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-RECORD", "记录", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewIconShow(false);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-RANK", "排行", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.viewPopMax(1)) {
                return;
            }
            this$0.dealGoRandom(1);
            this$0.getViewModel().setExamRefresh(true);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-BASEOPERATE", "【基本操作】模拟考试", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(ExamActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (this$0.viewPopMax(3)) {
                return;
            }
            this$0.dealGoRandom(3);
            this$0.getViewModel().setExamBodyRefresh(true);
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-MODELTEST-BODYCHECK", "【体格检查】模拟考试", null, 4, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(ExamActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.popCl.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$7(ExamActivityBinding this_bodyBinding, View view) {
            Intrinsics.checkNotNullParameter(this_bodyBinding, "$this_bodyBinding");
            this_bodyBinding.popCl.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$8(ExamActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.getViewModel().httpGetTrainExamList(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$9(ExamActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.getViewModel().httpGetTrainExamList(false);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ExamActivityBinding examActivityBinding) {
            invoke2(examActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull final ExamActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            ImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final ExamActivity examActivity = ExamActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$0(examActivity, view);
                }
            }, 0L, 2, null);
            ConstraintLayout ruleCl = bodyBinding.ruleCl;
            Intrinsics.checkNotNullExpressionValue(ruleCl, "ruleCl");
            final ExamActivity examActivity2 = ExamActivity.this;
            ViewExtKt.setDebounceClickListener(ruleCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$1(examActivity2, view);
                }
            }, 800L);
            ConstraintLayout historyCl = bodyBinding.historyCl;
            Intrinsics.checkNotNullExpressionValue(historyCl, "historyCl");
            final ExamActivity examActivity3 = ExamActivity.this;
            ViewExtKt.setDebounceClickListener$default(historyCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.h
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$2(examActivity3, view);
                }
            }, 0L, 2, null);
            ConstraintLayout topCl = bodyBinding.topCl;
            Intrinsics.checkNotNullExpressionValue(topCl, "topCl");
            final ExamActivity examActivity4 = ExamActivity.this;
            ViewExtKt.setDebounceClickListener$default(topCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.i
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$3(examActivity4, view);
                }
            }, 0L, 2, null);
            ImageView random24Imgv = bodyBinding.random24Imgv;
            Intrinsics.checkNotNullExpressionValue(random24Imgv, "random24Imgv");
            final ExamActivity examActivity5 = ExamActivity.this;
            ViewExtKt.setDebounceClickListener$default(random24Imgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.j
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$4(examActivity5, view);
                }
            }, 0L, 2, null);
            ImageView randomBodyImgv = bodyBinding.randomBodyImgv;
            Intrinsics.checkNotNullExpressionValue(randomBodyImgv, "randomBodyImgv");
            final ExamActivity examActivity6 = ExamActivity.this;
            ViewExtKt.setDebounceClickListener$default(randomBodyImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$5(examActivity6, view);
                }
            }, 0L, 2, null);
            AppCompatImageView xImgv = bodyBinding.xImgv;
            Intrinsics.checkNotNullExpressionValue(xImgv, "xImgv");
            ViewExtKt.setDebounceClickListener$default(xImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.l
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$6(bodyBinding, view);
                }
            }, 0L, 2, null);
            AppCompatImageView x1Imgv = bodyBinding.x1Imgv;
            Intrinsics.checkNotNullExpressionValue(x1Imgv, "x1Imgv");
            ViewExtKt.setDebounceClickListener$default(x1Imgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.m
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$7(bodyBinding, view);
                }
            }, 0L, 2, null);
            SmartRefreshLayout smartRefreshLayout = bodyBinding.srl;
            final ExamActivity examActivity7 = ExamActivity.this;
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.yddmi.doctor.pages.exam.c
                @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    ExamActivity.C06401.invoke$lambda$8(examActivity7, refreshLayout);
                }
            });
            SmartRefreshLayout smartRefreshLayout2 = bodyBinding.srl;
            final ExamActivity examActivity8 = ExamActivity.this;
            smartRefreshLayout2.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.yddmi.doctor.pages.exam.d
                @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
                public final void onLoadMore(RefreshLayout refreshLayout) {
                    ExamActivity.C06401.invoke$lambda$9(examActivity8, refreshLayout);
                }
            });
            DslTabLayout dslTablayout = bodyBinding.dslTablayout;
            Intrinsics.checkNotNullExpressionValue(dslTablayout, "dslTablayout");
            final ExamActivity examActivity9 = ExamActivity.this;
            Function4<Integer, Integer, Boolean, Boolean, Unit> function4 = new Function4<Integer, Integer, Boolean, Boolean, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.initView.1.11
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(Integer num, Integer num2, Boolean bool, Boolean bool2) {
                    invoke(num.intValue(), num2.intValue(), bool.booleanValue(), bool2.booleanValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(int i2, int i3, boolean z2, boolean z3) {
                    LogExtKt.logd("dslTablayout 标签切换 " + i2 + " " + i3 + " " + z2 + " " + z3, ExamActivity.TAG);
                    examActivity9.viewDslTabChange(i2, i3, z2, z3);
                }
            };
            AdapterTop adapterTop = null;
            DslTabLayout.observeIndexChange$default(dslTablayout, null, function4, 1, null);
            RecyclerView recyclerView = bodyBinding.rv;
            ExamActivity examActivity10 = ExamActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            AdapterHistory adapterHistory = examActivity10.adapterHistory;
            if (adapterHistory == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
                adapterHistory = null;
            }
            recyclerView.setAdapter(adapterHistory);
            TextView go24Tv = bodyBinding.go24Tv;
            Intrinsics.checkNotNullExpressionValue(go24Tv, "go24Tv");
            final ExamActivity examActivity11 = ExamActivity.this;
            ViewExtKt.setDebounceClickListener$default(go24Tv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.e
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$11(examActivity11, view);
                }
            }, 0L, 2, null);
            TextView goBodyTv = bodyBinding.goBodyTv;
            Intrinsics.checkNotNullExpressionValue(goBodyTv, "goBodyTv");
            final ExamActivity examActivity12 = ExamActivity.this;
            ViewExtKt.setDebounceClickListener$default(goBodyTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.f
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ExamActivity.C06401.invoke$lambda$12(examActivity12, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView2 = bodyBinding.leftRv;
            ExamActivity examActivity13 = ExamActivity.this;
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(recyclerView2.getContext());
            linearLayoutManager2.setOrientation(1);
            recyclerView2.setLayoutManager(linearLayoutManager2);
            AdapterTopTree adapterTopTree = examActivity13.adapterTopTree;
            if (adapterTopTree == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                adapterTopTree = null;
            }
            recyclerView2.setAdapter(adapterTopTree);
            RecyclerView recyclerView3 = bodyBinding.rightRv;
            ExamActivity examActivity14 = ExamActivity.this;
            LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(recyclerView3.getContext());
            linearLayoutManager3.setOrientation(1);
            recyclerView3.setLayoutManager(linearLayoutManager3);
            AdapterTop adapterTop2 = examActivity14.adapterTop;
            if (adapterTop2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterTop");
            } else {
                adapterTop = adapterTop2;
            }
            recyclerView3.setAdapter(adapterTop);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ ExamActivityBinding access$getBodyBinding(ExamActivity examActivity) {
        return (ExamActivityBinding) examActivity.getBodyBinding();
    }

    private final void dealGoBuyAll(int type, int skill24, String skillId) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("name", "");
        intent.putExtra("skillId", skillId);
        intent.putExtra("skill24", skill24);
        intent.setClass(this, ProductActivity.class);
        startActivity(intent);
    }

    public static /* synthetic */ void dealGoBuyAll$default(ExamActivity examActivity, int i2, int i3, String str, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            str = "-1";
        }
        examActivity.dealGoBuyAll(i2, i3, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void dealGoRandom(int type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(this, ExamRandomActivity.class);
        startActivity(intent);
        this.mNeedRefresh = true;
    }

    private final void dealOpenFirst(boolean open24) {
        AdapterTopTree adapterTopTree = null;
        if (open24) {
            if (getViewModel().getTop24FirstFolderSkill() != null) {
                if (getViewModel().getTop24FirstSkill() != null) {
                    AdapterTopTree adapterTopTree2 = this.adapterTopTree;
                    if (adapterTopTree2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                        adapterTopTree2 = null;
                    }
                    SkillHome top24FirstSkill = getViewModel().getTop24FirstSkill();
                    Intrinsics.checkNotNull(top24FirstSkill);
                    adapterTopTree2.setCurrentSkill(top24FirstSkill);
                    SkillHome top24FirstSkill2 = getViewModel().getTop24FirstSkill();
                    Intrinsics.checkNotNull(top24FirstSkill2);
                    httpPostLeaderBoard(top24FirstSkill2);
                }
                AdapterTopTree adapterTopTree3 = this.adapterTopTree;
                if (adapterTopTree3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                } else {
                    adapterTopTree = adapterTopTree3;
                }
                SkillHome top24FirstFolderSkill = getViewModel().getTop24FirstFolderSkill();
                Intrinsics.checkNotNull(top24FirstFolderSkill);
                adapterTopTree.onOpen(top24FirstFolderSkill, 0);
                return;
            }
            return;
        }
        if (getViewModel().getTopBodyFirstFolderSkill() != null) {
            if (getViewModel().getTopBodyFirstSkill() != null) {
                AdapterTopTree adapterTopTree4 = this.adapterTopTree;
                if (adapterTopTree4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                    adapterTopTree4 = null;
                }
                SkillHome topBodyFirstSkill = getViewModel().getTopBodyFirstSkill();
                Intrinsics.checkNotNull(topBodyFirstSkill);
                adapterTopTree4.setCurrentSkill(topBodyFirstSkill);
                SkillHome topBodyFirstSkill2 = getViewModel().getTopBodyFirstSkill();
                Intrinsics.checkNotNull(topBodyFirstSkill2);
                httpPostLeaderBoard(topBodyFirstSkill2);
            }
            AdapterTopTree adapterTopTree5 = this.adapterTopTree;
            if (adapterTopTree5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
            } else {
                adapterTopTree = adapterTopTree5;
            }
            SkillHome topBodyFirstFolderSkill = getViewModel().getTopBodyFirstFolderSkill();
            Intrinsics.checkNotNull(topBodyFirstFolderSkill);
            adapterTopTree.onOpen(topBodyFirstFolderSkill, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetRulelatest() {
        String mRuleData = getViewModel().getMRuleData();
        if (!(mRuleData == null || mRuleData.length() == 0)) {
            viewPopRule();
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(MimeTypes.BASE_TYPE_APPLICATION, 4);
        linkedHashMap.put(UriUtil.QUERY_CATEGORY, 5);
        linkedHashMap.put("port", 2);
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getMaintain(linkedHashMap), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpGetRulelatest.1
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
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpGetRulelatest.2
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
                ExamActivity.this.getViewModel().setMRuleData(str != null ? StringsKt__StringsJVMKt.replace$default(str, ",", "\n", false, 4, (Object) null) : null);
                ExamActivity.this.viewPopRule();
            }
        });
    }

    private final void httpGetSkillTimes() {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getSkillTimes(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpGetSkillTimes.1
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
                LogExtKt.logw("httpGetSkillTimes 获取抽题次数error " + it, ExamActivity.TAG);
                ExamActivity.this.maxSkillNum = 0;
                ExamActivity.this.viewMaxNumShow();
            }
        }, new Function1<Integer, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpGetSkillTimes.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i2) {
                ExamActivity.this.maxSkillNum = i2;
                ExamActivity.this.viewMaxNumShow();
            }
        });
    }

    private final void httpPostHorseLamp() {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getExamHorseLamp(), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpPostHorseLamp.1
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
                ExamActivity.this.viewShowMarquee(null);
            }
        }, new Function1<List<String>, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpPostHorseLamp.2
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
                ExamActivity.this.viewShowMarquee(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpPostLeaderBoard(final SkillHome m2) {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getLeaderBoard(String.valueOf(m2.getId())), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpPostLeaderBoard.1
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
        }, new Function1<ExamLeaderBoardResult, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.httpPostLeaderBoard.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ExamLeaderBoardResult examLeaderBoardResult) {
                invoke2(examLeaderBoardResult);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable ExamLeaderBoardResult examLeaderBoardResult) {
                ExamActivity.this.viewShowLeaderBoard(m2, examLeaderBoardResult);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewDslTabChange(int fromIndex, int toIndex, boolean reselect, boolean fromUser) {
        LogExtKt.logd("viewDslTabChange " + toIndex + " " + reselect, TAG);
        if (reselect) {
            return;
        }
        getViewModel().setLastTabDsl(toIndex);
        if (toIndex == 0) {
            if (getViewModel().getExamRefresh()) {
                loadingView();
                ExamViewModel.httpGetTrainExamList$default(getViewModel(), false, 1, null);
                return;
            }
            SkillHomeList examList = getViewModel().getExamList();
            List<SkillHome> rows = examList != null ? examList.getRows() : null;
            if (rows == null || rows.isEmpty()) {
                loadingView();
                ExamViewModel.httpGetTrainExamList$default(getViewModel(), false, 1, null);
                return;
            }
            AdapterHistory adapterHistory = this.adapterHistory;
            if (adapterHistory == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
                adapterHistory = null;
            }
            SkillHomeList examList2 = getViewModel().getExamList();
            adapterHistory.set(examList2 != null ? examList2.getRows() : null);
            return;
        }
        if (toIndex != 1) {
            return;
        }
        if (getViewModel().getExamBodyRefresh()) {
            loadingView();
            ExamViewModel.httpGetTrainExamList$default(getViewModel(), false, 1, null);
            return;
        }
        SkillHomeList examBodyList = getViewModel().getExamBodyList();
        List<SkillHome> rows2 = examBodyList != null ? examBodyList.getRows() : null;
        if (rows2 == null || rows2.isEmpty()) {
            loadingView();
            ExamViewModel.httpGetTrainExamList$default(getViewModel(), false, 1, null);
            return;
        }
        AdapterHistory adapterHistory2 = this.adapterHistory;
        if (adapterHistory2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
            adapterHistory2 = null;
        }
        SkillHomeList examBodyList2 = getViewModel().getExamBodyList();
        adapterHistory2.set(examBodyList2 != null ? examBodyList2.getRows() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewIconShow(boolean history) {
        ((ExamActivityBinding) getBodyBinding()).popCl.setVisibility(0);
        if (history) {
            ((ExamActivityBinding) getBodyBinding()).historyFl.setVisibility(0);
            ((ExamActivityBinding) getBodyBinding()).topPopCl.setVisibility(8);
            ((ExamActivityBinding) getBodyBinding()).dslTablayout.setCurrentItem(0, true, false);
            viewDslTabChange(0, 0, false, false);
            return;
        }
        ((ExamActivityBinding) getBodyBinding()).historyFl.setVisibility(8);
        ((ExamActivityBinding) getBodyBinding()).topPopCl.setVisibility(0);
        List<SkillHome> top24List = getViewModel().getTop24List();
        if (!(top24List == null || top24List.isEmpty())) {
            viewTopShow$default(this, false, 1, null);
        } else {
            Toaster.show(R.string.common_no_data2);
            getViewModel().httpGetSkillHome();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewMaxNumShow() {
        TextView viewMaxNumShow$lambda$2 = ((ExamActivityBinding) getBodyBinding()).maxNumTv;
        viewMaxNumShow$lambda$2.setText("");
        viewMaxNumShow$lambda$2.append("您今日有");
        Intrinsics.checkNotNullExpressionValue(viewMaxNumShow$lambda$2, "viewMaxNumShow$lambda$2");
        SpanExtKt.appendColorSpan(viewMaxNumShow$lambda$2, String.valueOf(this.maxSkillNum), ContextExtKt.getColorM(this, R.color.c_ff585a));
        viewMaxNumShow$lambda$2.append("次抽题机会");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean viewPopMax(int skill24) {
        if (this.maxSkillNum > 0) {
            return false;
        }
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(new PopupExamCall(this)).show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopRule() {
        PopupExamRule popupExamRule = new PopupExamRule(this);
        String mRuleData = getViewModel().getMRuleData();
        Intrinsics.checkNotNull(mRuleData);
        popupExamRule.setData(mRuleData);
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(popupExamRule).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewPopShowHistory() {
        if (getViewModel().getHistoryData() == null) {
            Toaster.show(R.string.common_no_data1);
            return;
        }
        SkillHome historyData = getViewModel().getHistoryData();
        Intrinsics.checkNotNull(historyData);
        new XPopup.Builder(this).isDestroyOnDismiss(true).isViewMode(true).hasBlurBg(true).dismissOnTouchOutside(Boolean.FALSE).asCustom(new PopupHistory(this, historyData, true)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowLeaderBoard(SkillHome m2, ExamLeaderBoardResult data) {
        AdapterTop adapterTop = null;
        if (data == null) {
            AdapterTopTree adapterTopTree = this.adapterTopTree;
            if (adapterTopTree == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                adapterTopTree = null;
            }
            adapterTopTree.setCurrentSkill(m2);
            AdapterTop adapterTop2 = this.adapterTop;
            if (adapterTop2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterTop");
                adapterTop2 = null;
            }
            adapterTop2.set(null);
            return;
        }
        AdapterTopTree adapterTopTree2 = this.adapterTopTree;
        if (adapterTopTree2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
            adapterTopTree2 = null;
        }
        adapterTopTree2.setCurrentSkill(m2);
        AdapterTop adapterTop3 = this.adapterTop;
        if (adapterTop3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterTop");
        } else {
            adapterTop = adapterTop3;
        }
        adapterTop.set(data.getLeaderBoard());
        List<ExamLeaderBoardItemResult> leaderBoard = data.getLeaderBoard();
        if (leaderBoard == null || leaderBoard.isEmpty()) {
            ((ExamActivityBinding) getBodyBinding()).userCl.setVisibility(4);
        } else {
            viewShowUser(data.getUserRanking());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowMarquee(List<String> data) {
        List<String> list = data;
        if (list == null || list.isEmpty()) {
            ((ExamActivityBinding) getBodyBinding()).margueeCl.setVisibility(8);
            ((ExamActivityBinding) getBodyBinding()).tipTv.setVisibility(0);
            ((ExamActivityBinding) getBodyBinding()).tipImgv.setImageResource(R.drawable.exam_hodler1);
        } else {
            ((ExamActivityBinding) getBodyBinding()).margueeCl.setVisibility(0);
            ((ExamActivityBinding) getBodyBinding()).tipTv.setVisibility(8);
            ((ExamActivityBinding) getBodyBinding()).tipImgv.setImageResource(R.drawable.exam_hodler);
            ((ExamActivityBinding) getBodyBinding()).margueeV.startWithList(data);
        }
    }

    private final void viewShowUser(final ExamLeaderBoardItemResult data) {
        bodyBinding(new Function1<ExamActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.viewShowUser.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ExamActivityBinding examActivityBinding) {
                invoke2(examActivityBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ExamActivityBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.userCl.setVisibility(0);
                ImageView userImgv = bodyBinding.userImgv;
                Intrinsics.checkNotNullExpressionValue(userImgv, "userImgv");
                YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
                YddUserManager.Companion companion2 = YddUserManager.INSTANCE;
                String fileFullUrl = companion.getFileFullUrl(companion2.getInstance().userAvatar());
                int i2 = R.drawable.common_user_icon0;
                ImageViewExtKt.load(userImgv, fileFullUrl, (261628 & 2) != 0 ? 0 : i2, (261628 & 4) != 0 ? 0 : i2, (261628 & 8) != 0 ? false : true, (261628 & 16) != 0 ? false : true, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                ExamLeaderBoardItemResult examLeaderBoardItemResult = data;
                if (examLeaderBoardItemResult == null) {
                    bodyBinding.nameTv.setText(companion2.getInstance().userNickName());
                    bodyBinding.topTv.setText(this.getString(R.string.exam_top0) + "：");
                    bodyBinding.timeTv.setText(this.getString(R.string.exam_top2) + "：");
                    bodyBinding.scoreTv.setText(this.getString(R.string.exam_top3) + "：");
                    return;
                }
                bodyBinding.nameTv.setText(String.valueOf(examLeaderBoardItemResult.getUser()));
                bodyBinding.topTv.setText(this.getString(R.string.exam_top0) + "：" + data.getRowNum());
                bodyBinding.timeTv.setText(this.getString(R.string.exam_top2) + "：" + DateUtil.second2Time6(Integer.parseInt(data.getPracticeTime())));
                bodyBinding.scoreTv.setText(this.getString(R.string.exam_top3) + "：" + data.getScore());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewTopShow(boolean show24) {
        getViewModel().setMCurrentShowTop24(show24);
        AdapterTopTree adapterTopTree = null;
        try {
            if (show24) {
                bodyBinding(new Function1<ExamActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.viewTopShow.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ExamActivityBinding examActivityBinding) {
                        invoke2(examActivityBinding);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull ExamActivityBinding bodyBinding) {
                        Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                        TextView go24Tv = bodyBinding.go24Tv;
                        Intrinsics.checkNotNullExpressionValue(go24Tv, "go24Tv");
                        ViewExtKt.setWh(go24Tv, CommonExtKt.dp2px(ExamActivity.this, 82), CommonExtKt.dp2px(ExamActivity.this, 38));
                        bodyBinding.go24Tv.setBackgroundResource(R.drawable.exam_top_bg1);
                        bodyBinding.go24Tv.setTextSize(1, 14.0f);
                        TextView goBodyTv = bodyBinding.goBodyTv;
                        Intrinsics.checkNotNullExpressionValue(goBodyTv, "goBodyTv");
                        ViewExtKt.setWh(goBodyTv, CommonExtKt.dp2px(ExamActivity.this, 70), CommonExtKt.dp2px(ExamActivity.this, 34));
                        bodyBinding.goBodyTv.setBackgroundResource(R.drawable.exam_top_bg2);
                        bodyBinding.goBodyTv.setTextSize(1, 12.0f);
                    }
                });
                AdapterTopTree adapterTopTree2 = this.adapterTopTree;
                if (adapterTopTree2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                } else {
                    adapterTopTree = adapterTopTree2;
                }
                adapterTopTree.set(getViewModel().getTop24List());
                dealOpenFirst(true);
                return;
            }
            bodyBinding(new Function1<ExamActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.viewTopShow.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExamActivityBinding examActivityBinding) {
                    invoke2(examActivityBinding);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull ExamActivityBinding bodyBinding) {
                    Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                    TextView go24Tv = bodyBinding.go24Tv;
                    Intrinsics.checkNotNullExpressionValue(go24Tv, "go24Tv");
                    ViewExtKt.setWh(go24Tv, CommonExtKt.dp2px(ExamActivity.this, 70), CommonExtKt.dp2px(ExamActivity.this, 34));
                    bodyBinding.go24Tv.setBackgroundResource(R.drawable.exam_top_bg2);
                    bodyBinding.go24Tv.setTextSize(1, 12.0f);
                    TextView goBodyTv = bodyBinding.goBodyTv;
                    Intrinsics.checkNotNullExpressionValue(goBodyTv, "goBodyTv");
                    ViewExtKt.setWh(goBodyTv, CommonExtKt.dp2px(ExamActivity.this, 82), CommonExtKt.dp2px(ExamActivity.this, 38));
                    bodyBinding.goBodyTv.setBackgroundResource(R.drawable.exam_top_bg1);
                    bodyBinding.goBodyTv.setTextSize(1, 14.0f);
                }
            });
            AdapterTopTree adapterTopTree3 = this.adapterTopTree;
            if (adapterTopTree3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
            } else {
                adapterTopTree = adapterTopTree3;
            }
            adapterTopTree.set(getViewModel().getTopBodyList());
            dealOpenFirst(false);
        } catch (Throwable th) {
            LogExtKt.loge("463 " + th, TAG);
        }
    }

    public static /* synthetic */ void viewTopShow$default(ExamActivity examActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        examActivity.viewTopShow(z2);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06361(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06372(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass3(null), 3, null);
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass4(null), 3, null);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initParam() {
        ImmersionBar immersionBarWith = ImmersionBar.with((Activity) this, false);
        Intrinsics.checkNotNullExpressionValue(immersionBarWith, "this");
        immersionBarWith.transparentBar();
        immersionBarWith.hideBar(BarHide.FLAG_HIDE_BAR);
        immersionBarWith.statusBarDarkFont(false);
        immersionBarWith.navigationBarDarkIcon(false);
        immersionBarWith.init();
        immersionBarWith.init();
        Intent intent = getIntent();
        if (intent != null) {
            getViewModel().setType(intent.getIntExtra("type", 100));
        }
        AdapterHistory adapterHistory = new AdapterHistory();
        this.adapterHistory = adapterHistory;
        adapterHistory.setEmptyMShow(true);
        AdapterHistory adapterHistory2 = this.adapterHistory;
        AdapterHistory adapterHistory3 = null;
        if (adapterHistory2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
            adapterHistory2 = null;
        }
        adapterHistory2.setEmptyDawableWidthH(CommonExtKt.dp2px(this, 100), CommonExtKt.dp2px(this, 82));
        AdapterHistory adapterHistory4 = this.adapterHistory;
        if (adapterHistory4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
            adapterHistory4 = null;
        }
        adapterHistory4.setEmptyDawableId(R.drawable.me_nodata3);
        AdapterHistory adapterHistory5 = this.adapterHistory;
        if (adapterHistory5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
            adapterHistory5 = null;
        }
        String string = getString(R.string.me_no_tip3);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.me_no_tip3)");
        adapterHistory5.setEmptyTip(string);
        AdapterHistory adapterHistory6 = this.adapterHistory;
        if (adapterHistory6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
            adapterHistory6 = null;
        }
        adapterHistory6.setEmptyTipColor(R.color.color_white);
        AdapterHistory adapterHistory7 = this.adapterHistory;
        if (adapterHistory7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapterHistory");
        } else {
            adapterHistory3 = adapterHistory7;
        }
        adapterHistory3.setOnItemClickListener(new Function2<SkillHome, Integer, Unit>() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.initParam.3
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
                ExamActivity.this.loadingDialog();
                ExamActivity.this.getViewModel().httpGetPracticeRecord(m2);
            }
        });
        this.adapterTop = new AdapterTop();
        AdapterTopTree adapterTopTree = new AdapterTopTree();
        this.adapterTopTree = adapterTopTree;
        adapterTopTree.setOnItemClickListenerAbsolutePosition(new AdapterTopTree.OnItemClickListenerAbsolutePosition() { // from class: com.yddmi.doctor.pages.exam.ExamActivity.initParam.4
            @Override // com.yddmi.doctor.pages.exam.AdapterTopTree.OnItemClickListenerAbsolutePosition
            public void onItemClickListenerAbsolutePosition(@NotNull SkillHome m2, int absolutePosition) {
                Intrinsics.checkNotNullParameter(m2, "m");
                boolean z2 = true;
                if (1 != m2.getMFolder()) {
                    ExamActivity.this.httpPostLeaderBoard(m2);
                    return;
                }
                AdapterTopTree adapterTopTree2 = null;
                if (m2.getMItemState() != 0) {
                    AdapterTopTree adapterTopTree3 = ExamActivity.this.adapterTopTree;
                    if (adapterTopTree3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                    } else {
                        adapterTopTree2 = adapterTopTree3;
                    }
                    adapterTopTree2.onClose(m2, absolutePosition);
                    return;
                }
                List<SkillHome> children = m2.getChildren();
                if (children != null && !children.isEmpty()) {
                    z2 = false;
                }
                if (z2) {
                    LogExtKt.logd("无数据目录", ExamActivity.TAG);
                    return;
                }
                AdapterTopTree adapterTopTree4 = ExamActivity.this.adapterTopTree;
                if (adapterTopTree4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapterTopTree");
                } else {
                    adapterTopTree2 = adapterTopTree4;
                }
                adapterTopTree2.setCurrentSkillFolder(m2, absolutePosition);
                ExamActivity.this.getViewModel().dealTopTreeOnlyOneOpen(m2);
            }
        });
        httpPostHorseLamp();
        httpGetSkillTimes();
        getViewModel().httpGetSkillHome();
        getViewModel().httpGetBodyCheck();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C06401());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        LogExtKt.logd("onBackPressed 返回点击", TAG);
        if (((ExamActivityBinding) getBodyBinding()).popCl.getVisibility() == 0) {
            ((ExamActivityBinding) getBodyBinding()).popCl.setVisibility(8);
        } else {
            closeActivity();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.mNeedRefresh) {
            this.mNeedRefresh = false;
            this.maxSkillNum = GlobalAction.INSTANCE.getGlobalMaxSkillNum();
            viewMaxNumShow();
            httpGetSkillTimes();
        }
    }
}
