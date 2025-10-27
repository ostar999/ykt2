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
import com.hjq.toast.Toaster;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.GarbledHeartlungActivityCollectBinding;
import com.yddmi.doctor.entity.result.HeartData;
import com.yddmi.doctor.entity.result.HeartDetail;
import com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
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

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0007\u0018\u0000 \u00122\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0004J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/HeartlungCollectActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityCollectBinding;", "Lcom/yddmi/doctor/pages/heartlung/HeartlungCollectViewModel;", "()V", "mAdapter", "Lcom/yddmi/doctor/pages/heartlung/AdapterMastered;", "mIsExchange", "", "httpGetAppKnowledgeStatistics", "", "code", "refresh", "", "initFlow", "initParam", "initView", "screenOrientation", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true, transparent = true)
@SourceDebugExtension({"SMAP\nHeartlungCollectActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeartlungCollectActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungCollectActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,107:1\n18#2,2:108\n1#3:110\n*S KotlinDebug\n*F\n+ 1 HeartlungCollectActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungCollectActivity\n*L\n36#1:108,2\n36#1:110\n*E\n"})
/* loaded from: classes6.dex */
public final class HeartlungCollectActivity extends BaseVMActivity<GarbledHeartlungActivityCollectBinding, HeartlungCollectViewModel> {

    @NotNull
    private static final String TAG = "HeartlungCollectActivity";
    private AdapterMastered mAdapter;
    private int mIsExchange;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity$initFlow$1", f = "HeartlungCollectActivity.kt", i = {}, l = {76}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06681 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06681(Continuation<? super C06681> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungCollectActivity.this.new C06681(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06681) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> dataChangeMsf = HeartlungCollectActivity.this.getViewModel().getDataChangeMsf();
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity.initFlow.1.1
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityCollectBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06691 extends Lambda implements Function1<GarbledHeartlungActivityCollectBinding, Unit> {
        public C06691() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(HeartlungCollectActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungActivityCollectBinding garbledHeartlungActivityCollectBinding) {
            invoke2(garbledHeartlungActivityCollectBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull GarbledHeartlungActivityCollectBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            AppCompatImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final HeartlungCollectActivity heartlungCollectActivity = HeartlungCollectActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.r
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungCollectActivity.C06691.invoke$lambda$0(heartlungCollectActivity, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView = bodyBinding.rv;
            HeartlungCollectActivity heartlungCollectActivity2 = HeartlungCollectActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            AdapterMastered adapterMastered = heartlungCollectActivity2.mAdapter;
            if (adapterMastered == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                adapterMastered = null;
            }
            recyclerView.setAdapter(adapterMastered);
        }
    }

    private final void httpGetAppKnowledgeStatistics(int code, boolean refresh) {
        getViewModel().setCurrentIndex(refresh ? 1 : getViewModel().getCurrentIndex() + 1);
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.INSTANCE.getAppKnowledgeStatistics(1, code, getViewModel().getCurrentIndex(), 1000), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity.httpGetAppKnowledgeStatistics.1
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
        }, new Function1<HeartData, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity.httpGetAppKnowledgeStatistics.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(HeartData heartData) {
                invoke2(heartData);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable HeartData heartData) {
                if (heartData == null) {
                    Toaster.show(R.string.common_no_data);
                    return;
                }
                AdapterMastered adapterMastered = HeartlungCollectActivity.this.mAdapter;
                if (adapterMastered == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    adapterMastered = null;
                }
                adapterMastered.set(heartData.getMedicalKnowledgeNum());
            }
        });
    }

    public static /* synthetic */ void httpGetAppKnowledgeStatistics$default(HeartlungCollectActivity heartlungCollectActivity, int i2, boolean z2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            z2 = true;
        }
        heartlungCollectActivity.httpGetAppKnowledgeStatistics(i2, z2);
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06681(null), 3, null);
        httpGetAppKnowledgeStatistics$default(this, getViewModel().getCode(), false, 2, null);
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
            getViewModel().setCode(intent.getIntExtra("code", 1));
            this.mIsExchange = intent.getIntExtra("isExchange", 0);
        }
        AdapterMastered adapterMastered = new AdapterMastered(false);
        this.mAdapter = adapterMastered;
        adapterMastered.setEmptyMShow(true);
        AdapterMastered adapterMastered2 = this.mAdapter;
        AdapterMastered adapterMastered3 = null;
        if (adapterMastered2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterMastered2 = null;
        }
        String string = getString(R.string.common_no_data1);
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.common_no_data1)");
        adapterMastered2.setEmptyTip(string);
        AdapterMastered adapterMastered4 = this.mAdapter;
        if (adapterMastered4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            adapterMastered3 = adapterMastered4;
        }
        adapterMastered3.setOnItemClickListener(new Function2<HeartDetail, Integer, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungCollectActivity.initParam.3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(HeartDetail heartDetail, Integer num) {
                invoke(heartDetail, num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull HeartDetail m2, int i2) {
                Intrinsics.checkNotNullParameter(m2, "m");
                Intent intent2 = new Intent();
                intent2.putExtra("categoryId", m2.getCategoryId());
                intent2.putExtra("categoryName", m2.getCategoryName());
                intent2.putExtra("medicalKnowledgeId", m2.getMedicalKnowledgeId());
                intent2.putExtra("isFavorite", 1);
                intent2.putExtra("isExchange", HeartlungCollectActivity.this.mIsExchange);
                HeartlungCollectActivity heartlungCollectActivity = HeartlungCollectActivity.this;
                intent2.setClass(heartlungCollectActivity, HeartlungDetailActivity.class);
                heartlungCollectActivity.startActivity(intent2);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C06691());
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
