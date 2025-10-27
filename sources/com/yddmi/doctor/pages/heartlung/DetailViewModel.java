package com.yddmi.doctor.pages.heartlung;

import androidx.lifecycle.ViewModelKt;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.manager.ContextManager;
import com.yddmi.doctor.R;
import com.yddmi.doctor.entity.result.HeartDetailIndicator;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u0015"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/DetailViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "dataIndicatorMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getDataIndicatorMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "mIndicatorList", "", "Lcom/yddmi/doctor/entity/result/HeartDetailIndicator;", "getMIndicatorList", "()Ljava/util/List;", "mVmCode", "", "getMVmCode", "()I", "setMVmCode", "(I)V", "dealIndicator", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class DetailViewModel extends BaseViewModel {
    private int mVmCode = 1;

    @NotNull
    private final List<HeartDetailIndicator> mIndicatorList = new ArrayList();

    @NotNull
    private final MutableStateFlow<Long> dataIndicatorMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.DetailViewModel$dealIndicator$1", f = "DetailViewModel.kt", i = {}, l = {120}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.DetailViewModel$dealIndicator$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return DetailViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DetailViewModel.this.getMIndicatorList().clear();
                int mVmCode = DetailViewModel.this.getMVmCode();
                if (mVmCode == 1) {
                    List<HeartDetailIndicator> mIndicatorList = DetailViewModel.this.getMIndicatorList();
                    ContextManager.Companion companion = ContextManager.INSTANCE;
                    String string = companion.getInstance().getContext().getString(R.string.heartlung_1001);
                    Intrinsics.checkNotNullExpressionValue(string, "ContextManager.getInstan…                        )");
                    mIndicatorList.add(new HeartDetailIndicator(string, 1001));
                    List<HeartDetailIndicator> mIndicatorList2 = DetailViewModel.this.getMIndicatorList();
                    String string2 = companion.getInstance().getContext().getString(R.string.heartlung_1002);
                    Intrinsics.checkNotNullExpressionValue(string2, "ContextManager.getInstan…                        )");
                    mIndicatorList2.add(new HeartDetailIndicator(string2, 1002));
                    List<HeartDetailIndicator> mIndicatorList3 = DetailViewModel.this.getMIndicatorList();
                    String string3 = companion.getInstance().getContext().getString(R.string.heartlung_1003);
                    Intrinsics.checkNotNullExpressionValue(string3, "ContextManager.getInstan…                        )");
                    mIndicatorList3.add(new HeartDetailIndicator(string3, 1003));
                    List<HeartDetailIndicator> mIndicatorList4 = DetailViewModel.this.getMIndicatorList();
                    String string4 = companion.getInstance().getContext().getString(R.string.heartlung_1004);
                    Intrinsics.checkNotNullExpressionValue(string4, "ContextManager.getInstan…                        )");
                    mIndicatorList4.add(new HeartDetailIndicator(string4, 1004));
                } else if (mVmCode == 2) {
                    List<HeartDetailIndicator> mIndicatorList5 = DetailViewModel.this.getMIndicatorList();
                    String string5 = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.heartlung_1011);
                    Intrinsics.checkNotNullExpressionValue(string5, "ContextManager.getInstan…                        )");
                    mIndicatorList5.add(new HeartDetailIndicator(string5, 1011));
                } else if (mVmCode == 3) {
                    List<HeartDetailIndicator> mIndicatorList6 = DetailViewModel.this.getMIndicatorList();
                    ContextManager.Companion companion2 = ContextManager.INSTANCE;
                    String string6 = companion2.getInstance().getContext().getString(R.string.heartlung_1021);
                    Intrinsics.checkNotNullExpressionValue(string6, "ContextManager.getInstan…                        )");
                    mIndicatorList6.add(new HeartDetailIndicator(string6, 1021));
                    List<HeartDetailIndicator> mIndicatorList7 = DetailViewModel.this.getMIndicatorList();
                    String string7 = companion2.getInstance().getContext().getString(R.string.heartlung_1022);
                    Intrinsics.checkNotNullExpressionValue(string7, "ContextManager.getInstan…                        )");
                    mIndicatorList7.add(new HeartDetailIndicator(string7, 1022));
                } else if (mVmCode == 4) {
                    List<HeartDetailIndicator> mIndicatorList8 = DetailViewModel.this.getMIndicatorList();
                    ContextManager.Companion companion3 = ContextManager.INSTANCE;
                    String string8 = companion3.getInstance().getContext().getString(R.string.heartlung_1031);
                    Intrinsics.checkNotNullExpressionValue(string8, "ContextManager.getInstan…                        )");
                    mIndicatorList8.add(new HeartDetailIndicator(string8, 1031));
                    List<HeartDetailIndicator> mIndicatorList9 = DetailViewModel.this.getMIndicatorList();
                    String string9 = companion3.getInstance().getContext().getString(R.string.heartlung_1032);
                    Intrinsics.checkNotNullExpressionValue(string9, "ContextManager.getInstan…                        )");
                    mIndicatorList9.add(new HeartDetailIndicator(string9, 1032));
                } else if (mVmCode == 5) {
                    List<HeartDetailIndicator> mIndicatorList10 = DetailViewModel.this.getMIndicatorList();
                    ContextManager.Companion companion4 = ContextManager.INSTANCE;
                    String string10 = companion4.getInstance().getContext().getString(R.string.heartlung_1041);
                    Intrinsics.checkNotNullExpressionValue(string10, "ContextManager.getInstan…                        )");
                    mIndicatorList10.add(new HeartDetailIndicator(string10, R2.attr.colorOnError));
                    List<HeartDetailIndicator> mIndicatorList11 = DetailViewModel.this.getMIndicatorList();
                    String string11 = companion4.getInstance().getContext().getString(R.string.heartlung_1032);
                    Intrinsics.checkNotNullExpressionValue(string11, "ContextManager.getInstan…                        )");
                    mIndicatorList11.add(new HeartDetailIndicator(string11, R2.attr.colorOnErrorContainer));
                }
                MutableStateFlow<Long> dataIndicatorMsf = DetailViewModel.this.getDataIndicatorMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 1;
                if (dataIndicatorMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public final void dealIndicator() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    @NotNull
    public final MutableStateFlow<Long> getDataIndicatorMsf() {
        return this.dataIndicatorMsf;
    }

    @NotNull
    public final List<HeartDetailIndicator> getMIndicatorList() {
        return this.mIndicatorList;
    }

    public final int getMVmCode() {
        return this.mVmCode;
    }

    public final void setMVmCode(int i2) {
        this.mVmCode = i2;
    }
}
