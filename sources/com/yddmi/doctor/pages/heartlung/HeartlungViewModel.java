package com.yddmi.doctor.pages.heartlung;

import androidx.lifecycle.ViewModelKt;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.repository.YddClinicRepository;
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

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020\u001fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u0004X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0006R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\r¨\u0006!"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/HeartlungViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "code", "", "getCode", "()I", "setCode", "(I)V", "dataChangeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getDataChangeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "mGroupNameAll", "", "", "getMGroupNameAll", "()Ljava/util/List;", "setMGroupNameAll", "(Ljava/util/List;)V", "titleName", "getTitleName", "()Ljava/lang/String;", "setTitleName", "(Ljava/lang/String;)V", "type", "getType", "whiteCallMsf", "getWhiteCallMsf", "dealMockGroup", "", "httpGetConfigWhite", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class HeartlungViewModel extends BaseViewModel {
    private int code;
    private final int type;

    @Nullable
    private String titleName = "";

    @NotNull
    private List<String> mGroupNameAll = new ArrayList();

    @NotNull
    private final MutableStateFlow<Long> dataChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> whiteCallMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungViewModel$dealMockGroup$1", f = "HeartlungViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungViewModel$dealMockGroup$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            HeartlungViewModel.this.getMGroupNameAll().clear();
            List<String> mGroupNameAll = HeartlungViewModel.this.getMGroupNameAll();
            ContextManager.Companion companion = ContextManager.INSTANCE;
            String string = companion.getInstance().getContext().getString(R.string.heartlung_100);
            Intrinsics.checkNotNullExpressionValue(string, "ContextManager.getInstan…g(R.string.heartlung_100)");
            mGroupNameAll.add(string);
            List<String> mGroupNameAll2 = HeartlungViewModel.this.getMGroupNameAll();
            String string2 = companion.getInstance().getContext().getString(R.string.heartlung_101);
            Intrinsics.checkNotNullExpressionValue(string2, "ContextManager.getInstan…g(R.string.heartlung_101)");
            mGroupNameAll2.add(string2);
            List<String> mGroupNameAll3 = HeartlungViewModel.this.getMGroupNameAll();
            String string3 = companion.getInstance().getContext().getString(R.string.heartlung_102);
            Intrinsics.checkNotNullExpressionValue(string3, "ContextManager.getInstan…g(R.string.heartlung_102)");
            mGroupNameAll3.add(string3);
            List<String> mGroupNameAll4 = HeartlungViewModel.this.getMGroupNameAll();
            String string4 = companion.getInstance().getContext().getString(R.string.heartlung_103);
            Intrinsics.checkNotNullExpressionValue(string4, "ContextManager.getInstan…g(R.string.heartlung_103)");
            mGroupNameAll4.add(string4);
            List<String> mGroupNameAll5 = HeartlungViewModel.this.getMGroupNameAll();
            String string5 = companion.getInstance().getContext().getString(R.string.heartlung_105);
            Intrinsics.checkNotNullExpressionValue(string5, "ContextManager.getInstan…g(R.string.heartlung_105)");
            mGroupNameAll5.add(string5);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungViewModel$httpGetConfigWhite$1", f = "HeartlungViewModel.kt", i = {}, l = {52, 61}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungViewModel$httpGetConfigWhite$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06801 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06801(Continuation<? super C06801> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungViewModel.this.new C06801(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06801) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                YddConfig.INSTANCE.getKvData().remove(YddConfig.KV_CONFIG);
                LogExtKt.loge("397 App配置开关异常 " + th, YddConfig.TAG);
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                this.label = 1;
                obj = yddClinicRepository.getConfigWhite(this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            List list = (List) obj;
            if (list != null) {
                YddConfig.INSTANCE.getKvData().put(YddConfig.KV_CONFIG, list);
            }
            LogExtKt.logd("394 App配置开关更新" + list, YddConfig.TAG);
            MutableStateFlow<Long> whiteCallMsf = HeartlungViewModel.this.getWhiteCallMsf();
            Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (whiteCallMsf.emit(lBoxLong, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public final void dealMockGroup() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    public final int getCode() {
        return this.code;
    }

    @NotNull
    public final MutableStateFlow<Long> getDataChangeMsf() {
        return this.dataChangeMsf;
    }

    @NotNull
    public final List<String> getMGroupNameAll() {
        return this.mGroupNameAll;
    }

    @Nullable
    public final String getTitleName() {
        return this.titleName;
    }

    public final int getType() {
        return this.type;
    }

    @NotNull
    public final MutableStateFlow<Long> getWhiteCallMsf() {
        return this.whiteCallMsf;
    }

    public final void httpGetConfigWhite() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C06801(null), 3, null);
    }

    public final void setCode(int i2) {
        this.code = i2;
    }

    public final void setMGroupNameAll(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.mGroupNameAll = list;
    }

    public final void setTitleName(@Nullable String str) {
        this.titleName = str;
    }
}
