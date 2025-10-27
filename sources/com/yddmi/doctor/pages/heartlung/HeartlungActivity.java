package com.yddmi.doctor.pages.heartlung;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.catchpig.annotation.StatusBar;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.XPopup;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.GarbledHeartlungActivityBinding;
import com.yddmi.doctor.entity.result.HeartData;
import com.yddmi.doctor.entity.result.HeartDetail;
import com.yddmi.doctor.pages.heartlung.HeartlungActivity;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.widget.PopupListAttach;
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

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00182\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0002J\b\u0010\u000f\u001a\u00020\fH\u0016J\b\u0010\u0010\u001a\u00020\fH\u0016J\b\u0010\u0011\u001a\u00020\fH\u0016J\b\u0010\u0012\u001a\u00020\fH\u0014J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/HeartlungActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityBinding;", "Lcom/yddmi/doctor/pages/heartlung/HeartlungViewModel;", "()V", "mAdapter", "Lcom/yddmi/doctor/pages/heartlung/AdapterHeartlung;", "mIsExchange", "", "mRefresh", "", "httpGetAppKnowledgeStatistics", "", "type", "code", "initFlow", "initParam", "initView", "onResume", "screenOrientation", "viewShow", "data", "Lcom/yddmi/doctor/entity/result/HeartData;", "viewShowPopGroupAttach", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = true, transparent = true)
@SourceDebugExtension({"SMAP\nHeartlungActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeartlungActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,192:1\n18#2,2:193\n1#3:195\n*S KotlinDebug\n*F\n+ 1 HeartlungActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungActivity\n*L\n55#1:193,2\n55#1:195\n*E\n"})
/* loaded from: classes6.dex */
public final class HeartlungActivity extends BaseVMActivity<GarbledHeartlungActivityBinding, HeartlungViewModel> {

    @NotNull
    private static final String TAG = "HeartlungActivity";
    private AdapterHeartlung mAdapter;
    private int mIsExchange;
    private boolean mRefresh;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungActivity$initFlow$1", f = "HeartlungActivity.kt", i = {}, l = {134}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06641 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06641(Continuation<? super C06641> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungActivity.this.new C06641(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06641) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MutableStateFlow<Long> dataChangeMsf = HeartlungActivity.this.getViewModel().getDataChangeMsf();
                FlowCollector<? super Long> flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungActivity.initFlow.1.1
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

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungActivityBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nHeartlungActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeartlungActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungActivity$initView$1\n+ 2 ActivityExt.kt\ncom/catchpig/utils/ext/ActivityExtKt\n*L\n1#1,192:1\n15#2,3:193\n*S KotlinDebug\n*F\n+ 1 HeartlungActivity.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungActivity$initView$1\n*L\n121#1:193,3\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungActivity$initView$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06651 extends Lambda implements Function1<GarbledHeartlungActivityBinding, Unit> {
        public C06651() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(HeartlungActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.closeActivity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(HeartlungActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewShowPopGroupAttach();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(HeartlungActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.viewShowPopGroupAttach();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$3(HeartlungActivity this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intent intent = new Intent();
            intent.putExtra("code", this$0.getViewModel().getCode());
            intent.putExtra("isExchange", this$0.mIsExchange);
            intent.setClass(this$0, HeartlungCollectActivity.class);
            this$0.startActivity(intent);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungActivityBinding garbledHeartlungActivityBinding) {
            invoke2(garbledHeartlungActivityBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull GarbledHeartlungActivityBinding bodyBinding) {
            Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
            String titleName = HeartlungActivity.this.getViewModel().getTitleName();
            if (titleName == null || titleName.length() == 0) {
                int code = HeartlungActivity.this.getViewModel().getCode();
                if (code == 1) {
                    bodyBinding.titleTv.setText(HeartlungActivity.this.getString(R.string.heartlung_100));
                } else if (code == 2) {
                    bodyBinding.titleTv.setText(HeartlungActivity.this.getString(R.string.heartlung_101));
                } else if (code == 3) {
                    bodyBinding.titleTv.setText(HeartlungActivity.this.getString(R.string.heartlung_102));
                } else if (code == 4) {
                    bodyBinding.titleTv.setText(HeartlungActivity.this.getString(R.string.heartlung_103));
                } else if (code == 5) {
                    bodyBinding.titleTv.setText(HeartlungActivity.this.getString(R.string.heartlung_105));
                }
            } else {
                bodyBinding.titleTv.setText(HeartlungActivity.this.getViewModel().getTitleName());
            }
            AppCompatImageView backImgv = bodyBinding.backImgv;
            Intrinsics.checkNotNullExpressionValue(backImgv, "backImgv");
            final HeartlungActivity heartlungActivity = HeartlungActivity.this;
            ViewExtKt.setDebounceClickListener$default(backImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.m
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungActivity.C06651.invoke$lambda$0(heartlungActivity, view);
                }
            }, 0L, 2, null);
            TextView titleTv = bodyBinding.titleTv;
            Intrinsics.checkNotNullExpressionValue(titleTv, "titleTv");
            final HeartlungActivity heartlungActivity2 = HeartlungActivity.this;
            ViewExtKt.setDebounceClickListener$default(titleTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.n
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungActivity.C06651.invoke$lambda$1(heartlungActivity2, view);
                }
            }, 0L, 2, null);
            ImageView updownImgv = bodyBinding.updownImgv;
            Intrinsics.checkNotNullExpressionValue(updownImgv, "updownImgv");
            final HeartlungActivity heartlungActivity3 = HeartlungActivity.this;
            ViewExtKt.setDebounceClickListener$default(updownImgv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungActivity.C06651.invoke$lambda$2(heartlungActivity3, view);
                }
            }, 0L, 2, null);
            TextView collectTv = bodyBinding.collectTv;
            Intrinsics.checkNotNullExpressionValue(collectTv, "collectTv");
            final HeartlungActivity heartlungActivity4 = HeartlungActivity.this;
            ViewExtKt.setDebounceClickListener$default(collectTv, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HeartlungActivity.C06651.invoke$lambda$3(heartlungActivity4, view);
                }
            }, 0L, 2, null);
            RecyclerView recyclerView = bodyBinding.rv;
            HeartlungActivity heartlungActivity5 = HeartlungActivity.this;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(1);
            recyclerView.setLayoutManager(linearLayoutManager);
            AdapterHeartlung adapterHeartlung = heartlungActivity5.mAdapter;
            if (adapterHeartlung == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                adapterHeartlung = null;
            }
            recyclerView.setAdapter(adapterHeartlung);
        }
    }

    private final void httpGetAppKnowledgeStatistics(int type, int code) {
        FlowExtKt.lifecycleLoadingDialog(YddClinicRepository.getAppKnowledgeStatistics$default(YddClinicRepository.INSTANCE, type, code, 0, 0, 12, null), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungActivity.httpGetAppKnowledgeStatistics.1
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
        }, new Function1<HeartData, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungActivity.httpGetAppKnowledgeStatistics.2
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
                if (heartData != null) {
                    HeartlungActivity.this.viewShow(heartData);
                } else {
                    Toaster.show(R.string.common_no_data);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void viewShow(final HeartData data) {
        this.mIsExchange = data.isExchange();
        bodyBinding(new Function1<GarbledHeartlungActivityBinding, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungActivity.viewShow.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungActivityBinding garbledHeartlungActivityBinding) {
                invoke2(garbledHeartlungActivityBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull GarbledHeartlungActivityBinding bodyBinding) {
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                bodyBinding.mesteredNumTv.setText(String.valueOf(data.getMasterNum()));
                bodyBinding.mesteredNoNumTv.setText(String.valueOf(data.getUnMasterNum()));
                AdapterHeartlung adapterHeartlung = this.mAdapter;
                if (adapterHeartlung == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
                    adapterHeartlung = null;
                }
                adapterHeartlung.set(data.getMedicalKnowledgeNum());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void viewShowPopGroupAttach() {
        final PopupListAttach popupListAttach = new PopupListAttach(this);
        popupListAttach.setWh(CommonExtKt.dp2px(this, 120), CommonExtKt.dp2px(this, 220));
        PopupListAttach.setDataGo$default(popupListAttach, getViewModel().getCode() - 1, getViewModel().getMGroupNameAll(), 0, 0, 0, 28, null);
        popupListAttach.setOnPopupAttachClickListener(new PopupListAttach.OnPopupAttachClickListener() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungActivity.viewShowPopGroupAttach.1
            @Override // com.yddmi.doctor.widget.PopupListAttach.OnPopupAttachClickListener
            public void onPopupAttachClick(@NotNull String str, int index) {
                Intrinsics.checkNotNullParameter(str, "str");
                LogExtKt.logd("152 " + HeartlungActivity.this.getViewModel().getCode() + "  " + index, HeartlungActivity.TAG);
                if (HeartlungActivity.this.getViewModel().getCode() - 1 == index) {
                    popupListAttach.dismiss();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("code", index + 1);
                HeartlungActivity heartlungActivity = HeartlungActivity.this;
                intent.setClass(heartlungActivity, HeartlungActivity.class);
                heartlungActivity.startActivity(intent);
                HeartlungActivity.this.closeActivity();
            }
        });
        XPopup.Builder builderAtView = new XPopup.Builder(this).isDestroyOnDismiss(true).enableDrag(false).isViewMode(true).atView(((GarbledHeartlungActivityBinding) getBodyBinding()).titleTv);
        Boolean bool = Boolean.TRUE;
        builderAtView.hasShadowBg(bool).dismissOnTouchOutside(bool).asCustom(popupListAttach).show();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C06641(null), 3, null);
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
            getViewModel().setCode(intent.getIntExtra("code", 0));
            getViewModel().setTitleName(intent.getStringExtra("titleName"));
        }
        AdapterHeartlung adapterHeartlung = new AdapterHeartlung();
        this.mAdapter = adapterHeartlung;
        adapterHeartlung.setOnItemClickListener(new Function2<HeartDetail, Integer, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.HeartlungActivity.initParam.3
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
                intent2.putExtra("code", HeartlungActivity.this.getViewModel().getCode());
                intent2.putExtra("categoryId", m2.getCategoryId());
                intent2.putExtra("categoryName", m2.getCategoryName());
                intent2.putExtra("medicalKnowledgeId", m2.getMedicalKnowledgeId());
                intent2.putExtra("isExchange", HeartlungActivity.this.mIsExchange);
                HeartlungActivity heartlungActivity = HeartlungActivity.this;
                intent2.setClass(heartlungActivity, HeartlungDetailActivity.class);
                heartlungActivity.startActivity(intent2);
                HeartlungActivity.this.mRefresh = true;
            }
        });
        httpGetAppKnowledgeStatistics(getViewModel().getType(), getViewModel().getCode());
        getViewModel().dealMockGroup();
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        bodyBinding(new C06651());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.mRefresh) {
            this.mRefresh = false;
            httpGetAppKnowledgeStatistics(getViewModel().getType(), getViewModel().getCode());
        }
    }

    @Override // com.catchpig.mvvm.base.activity.BaseActivity
    public void screenOrientation() {
        setRequestedOrientation(1);
    }
}
