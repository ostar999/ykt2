package com.yddmi.doctor.pages.heartlung;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.databinding.GarbledHeartlungActivityCorrectionBinding;
import com.yddmi.doctor.entity.result.FeedBackPageItem;
import com.yddmi.doctor.entity.result.FeedBackPageList;
import com.yddmi.doctor.pages.heartlung.CorrectionActivity;
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
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0007\u0018\u0000 \u00102\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\bH\u0016J\b\u0010\f\u001a\u00020\bH\u0016J\b\u0010\r\u001a\u00020\bH\u0016J\b\u0010\u000e\u001a\u00020\bH\u0014J\b\u0010\u000f\u001a\u00020\bH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/CorrectionActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityCorrectionBinding;", "Lcom/yddmi/doctor/pages/heartlung/CorrectionViewModel;", "()V", "mAdapter", "Lcom/yddmi/doctor/pages/heartlung/AdapterCorrection;", "httpGetFeedBackPage", "", "refresh", "", "initFlow", "initParam", "initView", "onDestroy", "screenOrientation", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true, transparent = true)
@SourceDebugExtension({"SMAP\nCorrectionActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CorrectionActivity.kt\ncom/yddmi/doctor/pages/heartlung/CorrectionActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,111:1\n18#2,2:112\n1#3:114\n*S KotlinDebug\n*F\n+ 1 CorrectionActivity.kt\ncom/yddmi/doctor/pages/heartlung/CorrectionActivity\n*L\n36#1:112,2\n36#1:114\n*E\n"})
/* loaded from: classes6.dex */
public final class CorrectionActivity extends BaseVMActivity<GarbledHeartlungActivityCorrectionBinding, CorrectionViewModel> {

    @NotNull
    private static final String TAG = "CorrectionActivity";
    private AdapterCorrection mAdapter;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.CorrectionActivity$initFlow$1", f = "CorrectionActivity.kt", i = {}, l = {79}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.CorrectionActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06541 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06541(Continuation<? super C06541> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return CorrectionActivity.this.new C06541(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06541) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> dataChangeMsf = CorrectionActivity.this.getViewModel().getDataChangeMsf();
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.heartlung.CorrectionActivity.initFlow.1.1
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
                if (dataChangeMsf.collect(flowCollector, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityCorrectionBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.CorrectionActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06551 extends Lambda implements Function1<GarbledHeartlungActivityCorrectionBinding, Unit> {
        public C06551() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(CorrectionActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(CorrectionActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.httpGetFeedBackPage(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(CorrectionActivity this$0, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.httpGetFeedBackPage(false);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungActivityCorrectionBinding garbledHeartlungActivityCorrectionBinding) {
            invoke2(garbledHeartlungActivityCorrectionBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull GarbledHeartlungActivityCorrectionBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final CorrectionActivity correctionActivity = CorrectionActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CorrectionActivity.C06551.invoke$lambda$0(correctionActivity, view);
                }
            }, 0L, 2, null);
            SmartRefreshLayout smartRefreshLayout = bodyBinding.srl;
            final CorrectionActivity correctionActivity2 = CorrectionActivity.this;
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.yddmi.doctor.pages.heartlung.c
                @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    CorrectionActivity.C06551.invoke$lambda$1(correctionActivity2, refreshLayout);
                }
            });
            SmartRefreshLayout smartRefreshLayout2 = bodyBinding.srl;
            final CorrectionActivity correctionActivity3 = CorrectionActivity.this;
            smartRefreshLayout2.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.yddmi.doctor.pages.heartlung.d
                @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
                public final void onLoadMore(RefreshLayout refreshLayout) {
                    CorrectionActivity.C06551.invoke$lambda$2(correctionActivity3, refreshLayout);
                }
            });
            RecyclerView recyclerView = bodyBinding.rv;
            CorrectionActivity correctionActivity4 = CorrectionActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            AdapterCorrection adapterCorrection = correctionActivity4.mAdapter;
            if (adapterCorrection == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                adapterCorrection = null;
            }
            recyclerView.setAdapter(adapterCorrection);
            bodyBinding.srl.autoRefresh(300);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ GarbledHeartlungActivityCorrectionBinding access$getBodyBinding(CorrectionActivity correctionActivity) {
        return (GarbledHeartlungActivityCorrectionBinding) correctionActivity.getBodyBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void httpGetFeedBackPage(final boolean refresh) {
        FlowExtKt.lifecycle(getViewModel().getFeedBackPage(refresh), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.CorrectionActivity.httpGetFeedBackPage.1
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
                CorrectionActivity.access$getBodyBinding(CorrectionActivity.this).srl.finishRefresh();
                CorrectionActivity.access$getBodyBinding(CorrectionActivity.this).srl.finishLoadMore();
            }
        }, new Function1<FeedBackPageList, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.CorrectionActivity.httpGetFeedBackPage.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FeedBackPageList feedBackPageList) {
                invoke2(feedBackPageList);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable FeedBackPageList feedBackPageList) {
                if (feedBackPageList != null) {
                    boolean z2 = refresh;
                    CorrectionActivity correctionActivity = CorrectionActivity.this;
                    AdapterCorrection adapterCorrection = null;
                    if (z2) {
                        AdapterCorrection adapterCorrection2 = correctionActivity.mAdapter;
                        if (adapterCorrection2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            adapterCorrection2 = null;
                        }
                        adapterCorrection2.set(feedBackPageList.getRows());
                    } else {
                        AdapterCorrection adapterCorrection3 = correctionActivity.mAdapter;
                        if (adapterCorrection3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                            adapterCorrection3 = null;
                        }
                        adapterCorrection3.add(feedBackPageList.getRows());
                    }
                    SmartRefreshLayout smartRefreshLayout = CorrectionActivity.access$getBodyBinding(correctionActivity).srl;
                    AdapterCorrection adapterCorrection4 = correctionActivity.mAdapter;
                    if (adapterCorrection4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    } else {
                        adapterCorrection = adapterCorrection4;
                    }
                    smartRefreshLayout.setNoMoreData(adapterCorrection.getData().size() >= feedBackPageList.getTotal());
                }
                CorrectionActivity.access$getBodyBinding(CorrectionActivity.this).srl.finishRefresh();
                CorrectionActivity.access$getBodyBinding(CorrectionActivity.this).srl.finishLoadMore();
            }
        });
    }

    public static /* synthetic */ void httpGetFeedBackPage$default(CorrectionActivity correctionActivity, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        correctionActivity.httpGetFeedBackPage(z2);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06541(null), 3, null);
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
            getViewModel().setType(intent.getIntExtra("type", 100));
            CorrectionViewModel viewModel = getViewModel();
            String stringExtra = intent.getStringExtra("categoryId");
            if (stringExtra == null) {
                stringExtra = "";
            } else {
                Intrinsics.checkNotNullExpressionValue(stringExtra, "it.getStringExtra(\"categoryId\") ?: \"\"");
            }
            viewModel.setCategoryId(stringExtra);
            CorrectionViewModel viewModel2 = getViewModel();
            String stringExtra2 = intent.getStringExtra("medicalKnowledgeId");
            viewModel2.setMedicalKnowledgeId(stringExtra2 != null ? stringExtra2 : "");
        }
        AdapterCorrection adapterCorrection = new AdapterCorrection();
        this.mAdapter = adapterCorrection;
        adapterCorrection.setEmptyMShow(true);
        AdapterCorrection adapterCorrection2 = this.mAdapter;
        AdapterCorrection adapterCorrection3 = null;
        if (adapterCorrection2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterCorrection2 = null;
        }
        String string = getString(R.string.common_no_data1);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.common_no_data1)");
        adapterCorrection2.setEmptyTip(string);
        AdapterCorrection adapterCorrection4 = this.mAdapter;
        if (adapterCorrection4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            adapterCorrection3 = adapterCorrection4;
        }
        adapterCorrection3.setOnItemClickListener(new Function2<FeedBackPageItem, Integer, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.CorrectionActivity.initParam.3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FeedBackPageItem feedBackPageItem, Integer num) {
                invoke(feedBackPageItem, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull FeedBackPageItem m2, int i2) {
                Intrinsics.checkNotNullParameter(m2, "m");
                GlobalAction.INSTANCE.setAutoGoMedicalKnowledgeId(m2.getMedicalKnowledgeId());
                CorrectionActivity.this.closeActivity();
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C06551());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
