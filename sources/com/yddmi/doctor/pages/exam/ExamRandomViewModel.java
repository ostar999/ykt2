package com.yddmi.doctor.pages.exam;

import androidx.lifecycle.ViewModelKt;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
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
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020!R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\"\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u0006#"}, d2 = {"Lcom/yddmi/doctor/pages/exam/ExamRandomViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "dataChangeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getDataChangeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "examRandomListMsf", "getExamRandomListMsf", "m3SkillId", "", "getM3SkillId", "()Ljava/lang/String;", "setM3SkillId", "(Ljava/lang/String;)V", "m3SkillUrl", "getM3SkillUrl", "setM3SkillUrl", "mRandomSkillList", "", "Lcom/yddmi/doctor/entity/result/SkillHome;", "getMRandomSkillList", "()Ljava/util/List;", "setMRandomSkillList", "(Ljava/util/List;)V", "type", "", "getType", "()I", "setType", "(I)V", "deal", "", "httpGetSkillRandom", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ExamRandomViewModel extends BaseViewModel {

    @Nullable
    private List<SkillHome> mRandomSkillList;
    private int type = 1;

    @NotNull
    private String m3SkillId = "";

    @NotNull
    private String m3SkillUrl = "";

    @NotNull
    private final MutableStateFlow<Long> dataChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> examRandomListMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamRandomViewModel$deal$1", f = "ExamRandomViewModel.kt", i = {}, l = {34}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamRandomViewModel$deal$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamRandomViewModel.this.new AnonymousClass1(continuation);
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
                MutableStateFlow<Long> dataChangeMsf = ExamRandomViewModel.this.getDataChangeMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 1;
                if (dataChangeMsf.emit(lBoxLong, this) == coroutine_suspended) {
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

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamRandomViewModel$httpGetSkillRandom$1", f = "ExamRandomViewModel.kt", i = {}, l = {43, 60, 66}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nExamRandomViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExamRandomViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamRandomViewModel$httpGetSkillRandom$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,71:1\n1855#2,2:72\n*S KotlinDebug\n*F\n+ 1 ExamRandomViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamRandomViewModel$httpGetSkillRandom$1\n*L\n49#1:72,2\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamRandomViewModel$httpGetSkillRandom$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06491 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06491(Continuation<? super C06491> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamRandomViewModel.this.new C06491(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06491) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge("异常 " + th, YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> examRandomListMsf = ExamRandomViewModel.this.getExamRandomListMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 3;
                if (examRandomListMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                int type = ExamRandomViewModel.this.getType();
                this.label = 1;
                obj = yddClinicRepository.getSkillRandom(type, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 != 2 && i2 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            List<SkillHome> list = (List) obj;
            if (list != null) {
                ExamRandomViewModel.this.setMRandomSkillList(list);
                if (list.size() > 1) {
                    StringBuffer stringBuffer = new StringBuffer();
                    StringBuffer stringBuffer2 = new StringBuffer();
                    List<SkillHome> mRandomSkillList = ExamRandomViewModel.this.getMRandomSkillList();
                    if (mRandomSkillList != null) {
                        for (SkillHome skillHome : mRandomSkillList) {
                            stringBuffer.append(skillHome.getId() + ",");
                            stringBuffer2.append(skillHome.getUrl() + ",");
                        }
                    }
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    stringBuffer2.deleteCharAt(stringBuffer2.length() - 1);
                    ExamRandomViewModel examRandomViewModel = ExamRandomViewModel.this;
                    String string = stringBuffer.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "strb.toString()");
                    examRandomViewModel.setM3SkillId(string);
                    ExamRandomViewModel examRandomViewModel2 = ExamRandomViewModel.this;
                    String string2 = stringBuffer2.toString();
                    Intrinsics.checkNotNullExpressionValue(string2, "strb1.toString()");
                    examRandomViewModel2.setM3SkillUrl(string2);
                }
            }
            MutableStateFlow<Long> examRandomListMsf2 = ExamRandomViewModel.this.getExamRandomListMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (examRandomListMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public final void deal() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    @NotNull
    public final MutableStateFlow<Long> getDataChangeMsf() {
        return this.dataChangeMsf;
    }

    @NotNull
    public final MutableStateFlow<Long> getExamRandomListMsf() {
        return this.examRandomListMsf;
    }

    @NotNull
    public final String getM3SkillId() {
        return this.m3SkillId;
    }

    @NotNull
    public final String getM3SkillUrl() {
        return this.m3SkillUrl;
    }

    @Nullable
    public final List<SkillHome> getMRandomSkillList() {
        return this.mRandomSkillList;
    }

    public final int getType() {
        return this.type;
    }

    public final void httpGetSkillRandom() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C06491(null), 3, null);
    }

    public final void setM3SkillId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.m3SkillId = str;
    }

    public final void setM3SkillUrl(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.m3SkillUrl = str;
    }

    public final void setMRandomSkillList(@Nullable List<SkillHome> list) {
        this.mRandomSkillList = list;
    }

    public final void setType(int i2) {
        this.type = i2;
    }
}
