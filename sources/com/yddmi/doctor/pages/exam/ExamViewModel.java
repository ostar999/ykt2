package com.yddmi.doctor.pages.exam;

import androidx.lifecycle.ViewModelKt;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.mvvm.utils.GsonUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.result.SkillHistory;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillHomeList;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010!\n\u0002\b\u001d\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020$J\u0006\u0010^\u001a\u00020\\J\u000e\u0010_\u001a\u00020\\2\u0006\u0010]\u001a\u00020$J\u0006\u0010`\u001a\u00020\\J\u0010\u0010a\u001a\u00020\\2\b\b\u0002\u0010b\u001a\u00020\u0015R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000b\"\u0004\b\u001c\u0010\rR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0011\"\u0004\b\u001f\u0010\u0013R\u001a\u0010 \u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0017\"\u0004\b\"\u0010\u0019R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0007R\u001a\u0010+\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u000b\"\u0004\b-\u0010\rR\u001a\u0010.\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0017\"\u0004\b0\u0010\u0019R\u001c\u00101\u001a\u0004\u0018\u000102X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001c\u00107\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010&\"\u0004\b9\u0010(R\u001c\u0010:\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010&\"\u0004\b<\u0010(R\"\u0010=\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010>X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR \u0010C\u001a\b\u0012\u0004\u0012\u00020$0>X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010@\"\u0004\bE\u0010BR\u0017\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010\u0007R\u001c\u0010H\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010&\"\u0004\bJ\u0010(R\u001c\u0010K\u001a\u0004\u0018\u00010$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010&\"\u0004\bM\u0010(R\"\u0010N\u001a\n\u0012\u0004\u0012\u00020$\u0018\u00010>X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010@\"\u0004\bP\u0010BR \u0010Q\u001a\b\u0012\u0004\u0012\u00020$0>X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010@\"\u0004\bS\u0010BR\u0017\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\bU\u0010\u0007R\u0017\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\bW\u0010\u0007R\u001a\u0010X\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010\u000b\"\u0004\bZ\u0010\r¨\u0006c"}, d2 = {"Lcom/yddmi/doctor/pages/exam/ExamViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "dataChangeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getDataChangeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "examBodyIndex", "", "getExamBodyIndex", "()I", "setExamBodyIndex", "(I)V", "examBodyList", "Lcom/yddmi/doctor/entity/result/SkillHomeList;", "getExamBodyList", "()Lcom/yddmi/doctor/entity/result/SkillHomeList;", "setExamBodyList", "(Lcom/yddmi/doctor/entity/result/SkillHomeList;)V", "examBodyRefresh", "", "getExamBodyRefresh", "()Z", "setExamBodyRefresh", "(Z)V", "examIndex", "getExamIndex", "setExamIndex", "examList", "getExamList", "setExamList", "examRefresh", "getExamRefresh", "setExamRefresh", "historyData", "Lcom/yddmi/doctor/entity/result/SkillHome;", "getHistoryData", "()Lcom/yddmi/doctor/entity/result/SkillHome;", "setHistoryData", "(Lcom/yddmi/doctor/entity/result/SkillHome;)V", "historyMsf", "getHistoryMsf", "lastTabDsl", "getLastTabDsl", "setLastTabDsl", "mCurrentShowTop24", "getMCurrentShowTop24", "setMCurrentShowTop24", "mRuleData", "", "getMRuleData", "()Ljava/lang/String;", "setMRuleData", "(Ljava/lang/String;)V", "top24FirstFolderSkill", "getTop24FirstFolderSkill", "setTop24FirstFolderSkill", "top24FirstSkill", "getTop24FirstSkill", "setTop24FirstSkill", "top24List", "", "getTop24List", "()Ljava/util/List;", "setTop24List", "(Ljava/util/List;)V", "top24ShowList", "getTop24ShowList", "setTop24ShowList", "top24ShowMsf", "getTop24ShowMsf", "topBodyFirstFolderSkill", "getTopBodyFirstFolderSkill", "setTopBodyFirstFolderSkill", "topBodyFirstSkill", "getTopBodyFirstSkill", "setTopBodyFirstSkill", "topBodyList", "getTopBodyList", "setTopBodyList", "topBodyShowList", "getTopBodyShowList", "setTopBodyShowList", "topBodyShowMsf", "getTopBodyShowMsf", "trainExamListMsf", "getTrainExamListMsf", "type", "getType", "setType", "dealTopTreeOnlyOneOpen", "", "m", "httpGetBodyCheck", "httpGetPracticeRecord", "httpGetSkillHome", "httpGetTrainExamList", "refresh", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ExamViewModel extends BaseViewModel {

    @Nullable
    private SkillHomeList examBodyList;
    private boolean examBodyRefresh;

    @Nullable
    private SkillHomeList examList;
    private boolean examRefresh;

    @Nullable
    private SkillHome historyData;
    private int lastTabDsl;

    @Nullable
    private SkillHome top24FirstFolderSkill;

    @Nullable
    private SkillHome top24FirstSkill;

    @Nullable
    private List<SkillHome> top24List;

    @Nullable
    private SkillHome topBodyFirstFolderSkill;

    @Nullable
    private SkillHome topBodyFirstSkill;

    @Nullable
    private List<SkillHome> topBodyList;
    private int type = 100;

    @Nullable
    private String mRuleData = "";
    private int examIndex = 1;
    private int examBodyIndex = 1;

    @NotNull
    private List<SkillHome> top24ShowList = new ArrayList();

    @NotNull
    private List<SkillHome> topBodyShowList = new ArrayList();
    private boolean mCurrentShowTop24 = true;

    @NotNull
    private final MutableStateFlow<Long> dataChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> historyMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> trainExamListMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> top24ShowMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> topBodyShowMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamViewModel$dealTopTreeOnlyOneOpen$1", f = "ExamViewModel.kt", i = {}, l = {76, 91}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nExamViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExamViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamViewModel$dealTopTreeOnlyOneOpen$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,251:1\n1864#2,3:252\n1864#2,3:255\n*S KotlinDebug\n*F\n+ 1 ExamViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamViewModel$dealTopTreeOnlyOneOpen$1\n*L\n64#1:252,3\n79#1:255,3\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamViewModel$dealTopTreeOnlyOneOpen$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SkillHome $m;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SkillHome skillHome, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$m = skillHome;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamViewModel.this.new AnonymousClass1(this.$m, continuation);
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
                if (ExamViewModel.this.getMCurrentShowTop24()) {
                    ExamViewModel.this.getTop24ShowList().clear();
                    List<SkillHome> top24List = ExamViewModel.this.getTop24List();
                    if (top24List != null) {
                        SkillHome skillHome = this.$m;
                        ExamViewModel examViewModel = ExamViewModel.this;
                        int i3 = 0;
                        for (Object obj2 : top24List) {
                            int i4 = i3 + 1;
                            if (i3 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                            }
                            SkillHome skillHome2 = (SkillHome) obj2;
                            if (Intrinsics.areEqual(skillHome.getName(), skillHome2.getName())) {
                                skillHome2.setMItemState(1);
                                examViewModel.getTop24ShowList().add(skillHome2);
                                List<SkillHome> children = skillHome2.getChildren();
                                if (!(children == null || children.isEmpty())) {
                                    examViewModel.getTop24ShowList().addAll(skillHome2.getChildren());
                                }
                            } else {
                                skillHome2.setMItemState(0);
                                examViewModel.getTop24ShowList().add(skillHome2);
                            }
                            i3 = i4;
                        }
                    }
                    MutableStateFlow<Long> top24ShowMsf = ExamViewModel.this.getTop24ShowMsf();
                    Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                    this.label = 1;
                    if (top24ShowMsf.emit(lBoxLong, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    ExamViewModel.this.getTopBodyShowList().clear();
                    List<SkillHome> topBodyList = ExamViewModel.this.getTopBodyList();
                    if (topBodyList != null) {
                        SkillHome skillHome3 = this.$m;
                        ExamViewModel examViewModel2 = ExamViewModel.this;
                        int i5 = 0;
                        for (Object obj3 : topBodyList) {
                            int i6 = i5 + 1;
                            if (i5 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                            }
                            SkillHome skillHome4 = (SkillHome) obj3;
                            if (Intrinsics.areEqual(skillHome3.getName(), skillHome4.getName())) {
                                skillHome4.setMItemState(1);
                                examViewModel2.getTopBodyShowList().add(skillHome4);
                                List<SkillHome> children2 = skillHome4.getChildren();
                                if (!(children2 == null || children2.isEmpty())) {
                                    examViewModel2.getTopBodyShowList().addAll(skillHome4.getChildren());
                                }
                            } else {
                                skillHome4.setMItemState(0);
                                examViewModel2.getTopBodyShowList().add(skillHome4);
                            }
                            i5 = i6;
                        }
                    }
                    MutableStateFlow<Long> topBodyShowMsf = ExamViewModel.this.getTopBodyShowMsf();
                    Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                    this.label = 2;
                    if (topBodyShowMsf.emit(lBoxLong2, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i2 != 1 && i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamViewModel$httpGetBodyCheck$1", f = "ExamViewModel.kt", i = {}, l = {214}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nExamViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExamViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamViewModel$httpGetBodyCheck$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,251:1\n1864#2,2:252\n1864#2,3:254\n1866#2:257\n*S KotlinDebug\n*F\n+ 1 ExamViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamViewModel$httpGetBodyCheck$1\n*L\n217#1:252,2\n224#1:254,3\n217#1:257\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamViewModel$httpGetBodyCheck$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06501 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06501(Continuation<? super C06501> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamViewModel.this.new C06501(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06501) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                    this.label = 1;
                    obj = YddClinicRepository.getBodyCheck$default(yddClinicRepository, null, null, this, 3, null);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                List<SkillHome> list = (List) obj;
                if (list != null) {
                    Ref.IntRef intRef = new Ref.IntRef();
                    intRef.element = -100;
                    ExamViewModel examViewModel = ExamViewModel.this;
                    int i3 = 0;
                    for (Object obj2 : list) {
                        int i4 = i3 + 1;
                        if (i3 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        SkillHome skillHome = (SkillHome) obj2;
                        skillHome.setMLevel(1);
                        skillHome.setMFolder(1);
                        skillHome.setMItemState(0);
                        if (i3 == 0) {
                            examViewModel.setTopBodyFirstFolderSkill(skillHome);
                        }
                        List<SkillHome> children = skillHome.getChildren();
                        if (children != null) {
                            int i5 = 0;
                            for (Object obj3 : children) {
                                int i6 = i5 + 1;
                                if (i5 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                SkillHome skillHome2 = (SkillHome) obj3;
                                skillHome2.setMLevel(2);
                                skillHome2.setMFolder(0);
                                if (i3 == 0 && i5 == 0) {
                                    examViewModel.setTopBodyFirstSkill(skillHome2);
                                }
                                i5 = i6;
                            }
                        }
                        if (Intrinsics.areEqual(skillHome.getName(), ContextManager.INSTANCE.getInstance().getContext().getString(R.string.exam_try))) {
                            intRef.element = i3;
                        }
                        i3 = i4;
                    }
                    int i7 = intRef.element;
                    if (i7 > -1) {
                        list.remove(i7);
                    }
                    ExamViewModel.this.setTopBodyList(list);
                }
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamViewModel$httpGetPracticeRecord$1", f = "ExamViewModel.kt", i = {}, l = {102, 110, 116}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamViewModel$httpGetPracticeRecord$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06511 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SkillHome $m;
        int label;
        final /* synthetic */ ExamViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06511(SkillHome skillHome, ExamViewModel examViewModel, Continuation<? super C06511> continuation) {
            super(2, continuation);
            this.$m = skillHome;
            this.this$0 = examViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C06511(this.$m, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06511) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            String content;
            List<SkillHistory> mContentList;
            List<SkillHistory> mContentList2;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> historyMsf = this.this$0.getHistoryMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 3;
                if (historyMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                String id = this.$m.getId();
                int iUserId = YddUserManager.INSTANCE.getInstance().userId();
                this.label = 1;
                obj = yddClinicRepository.getPracticeRecord(id, iUserId, this);
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
            SkillHome skillHome = (SkillHome) obj;
            GsonUtil gsonUtil = GsonUtil.INSTANCE;
            if (skillHome == null || (content = skillHome.getContent()) == null) {
                content = "";
            }
            List listGsonToMutableList = gsonUtil.GsonToMutableList(content, SkillHistory.class);
            if (listGsonToMutableList == null) {
                if (skillHome != null && (mContentList2 = skillHome.getMContentList()) != null) {
                    mContentList2.clear();
                }
            } else if (skillHome != null && (mContentList = skillHome.getMContentList()) != null) {
                Boxing.boxBoolean(mContentList.addAll(listGsonToMutableList));
            }
            this.this$0.setHistoryData(skillHome);
            MutableStateFlow<Long> historyMsf2 = this.this$0.getHistoryMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (historyMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamViewModel$httpGetSkillHome$1", f = "ExamViewModel.kt", i = {}, l = {R2.anim.window_ios_in}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nExamViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExamViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamViewModel$httpGetSkillHome$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,251:1\n1864#2,2:252\n1864#2,3:254\n1866#2:257\n*S KotlinDebug\n*F\n+ 1 ExamViewModel.kt\ncom/yddmi/doctor/pages/exam/ExamViewModel$httpGetSkillHome$1\n*L\n177#1:252,2\n184#1:254,3\n177#1:257\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamViewModel$httpGetSkillHome$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06521 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C06521(Continuation<? super C06521> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ExamViewModel.this.new C06521(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06521) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                    this.label = 1;
                    obj = yddClinicRepository.getSkillHome(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                List<SkillHome> list = (List) obj;
                if (list != null) {
                    Ref.IntRef intRef = new Ref.IntRef();
                    intRef.element = -100;
                    ExamViewModel examViewModel = ExamViewModel.this;
                    int i3 = 0;
                    for (Object obj2 : list) {
                        int i4 = i3 + 1;
                        if (i3 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        SkillHome skillHome = (SkillHome) obj2;
                        skillHome.setMLevel(1);
                        skillHome.setMFolder(1);
                        skillHome.setMItemState(0);
                        if (i3 == 0) {
                            examViewModel.setTop24FirstFolderSkill(skillHome);
                        }
                        List<SkillHome> children = skillHome.getChildren();
                        if (children != null) {
                            int i5 = 0;
                            for (Object obj3 : children) {
                                int i6 = i5 + 1;
                                if (i5 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                SkillHome skillHome2 = (SkillHome) obj3;
                                skillHome2.setMLevel(2);
                                skillHome2.setMFolder(0);
                                if (i3 == 0 && i5 == 0) {
                                    examViewModel.setTop24FirstSkill(skillHome2);
                                }
                                i5 = i6;
                            }
                        }
                        if (Intrinsics.areEqual(skillHome.getName(), ContextManager.INSTANCE.getInstance().getContext().getString(R.string.exam_try))) {
                            intRef.element = i3;
                        }
                        i3 = i4;
                    }
                    int i7 = intRef.element;
                    if (i7 > -1) {
                        list.remove(i7);
                    }
                    ExamViewModel.this.setTop24List(list);
                }
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.exam.ExamViewModel$httpGetTrainExamList$1", f = "ExamViewModel.kt", i = {}, l = {136, 160, 166}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.exam.ExamViewModel$httpGetTrainExamList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06531 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.IntRef $index;
        final /* synthetic */ boolean $refresh;
        int label;
        final /* synthetic */ ExamViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C06531(Ref.IntRef intRef, ExamViewModel examViewModel, boolean z2, Continuation<? super C06531> continuation) {
            super(2, continuation);
            this.$index = intRef;
            this.this$0 = examViewModel;
            this.$refresh = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C06531(this.$index, this.this$0, this.$refresh, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06531) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            SkillHomeList examList;
            List<SkillHome> rows;
            SkillHomeList examBodyList;
            List<SkillHome> rows2;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            boolean z2 = true;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> trainExamListMsf = this.this$0.getTrainExamListMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 3;
                if (trainExamListMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                Integer numBoxInt = Boxing.boxInt(this.$index.element);
                Integer numBoxInt2 = Boxing.boxInt(10);
                int i3 = this.this$0.getLastTabDsl() == 0 ? 1 : 3;
                this.label = 1;
                obj = yddClinicRepository.getSkillRecord(2, (16 & 2) != 0 ? 1 : numBoxInt, (16 & 4) != 0 ? 10 : numBoxInt2, i3, (16 & 16) != 0 ? 1 : 0, this);
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
            SkillHomeList skillHomeList = (SkillHomeList) obj;
            int lastTabDsl = this.this$0.getLastTabDsl();
            if (lastTabDsl != 0) {
                if (lastTabDsl == 1) {
                    if (this.$refresh) {
                        this.this$0.setExamBodyRefresh(false);
                        this.this$0.setExamBodyList(null);
                        this.this$0.setExamBodyList(skillHomeList);
                    } else if (skillHomeList != null) {
                        List<SkillHome> rows3 = skillHomeList.getRows();
                        if (rows3 != null && !rows3.isEmpty()) {
                            z2 = false;
                        }
                        if (!z2 && (examBodyList = this.this$0.getExamBodyList()) != null && (rows2 = examBodyList.getRows()) != null) {
                            Boxing.boxBoolean(rows2.addAll(skillHomeList.getRows()));
                        }
                    }
                }
            } else if (this.$refresh) {
                this.this$0.setExamRefresh(false);
                this.this$0.setExamList(null);
                this.this$0.setExamList(skillHomeList);
            } else if (skillHomeList != null) {
                List<SkillHome> rows4 = skillHomeList.getRows();
                if (rows4 != null && !rows4.isEmpty()) {
                    z2 = false;
                }
                if (!z2 && (examList = this.this$0.getExamList()) != null && (rows = examList.getRows()) != null) {
                    Boxing.boxBoolean(rows.addAll(skillHomeList.getRows()));
                }
            }
            MutableStateFlow<Long> trainExamListMsf2 = this.this$0.getTrainExamListMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (trainExamListMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ void httpGetTrainExamList$default(ExamViewModel examViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        examViewModel.httpGetTrainExamList(z2);
    }

    public final void dealTopTreeOnlyOneOpen(@NotNull SkillHome m2) {
        Intrinsics.checkNotNullParameter(m2, "m");
        LogExtKt.logd("dealTopTreeOnlyOneOpen " + m2.getName(), YddConfig.TAG);
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(m2, null), 3, null);
    }

    @NotNull
    public final MutableStateFlow<Long> getDataChangeMsf() {
        return this.dataChangeMsf;
    }

    public final int getExamBodyIndex() {
        return this.examBodyIndex;
    }

    @Nullable
    public final SkillHomeList getExamBodyList() {
        return this.examBodyList;
    }

    public final boolean getExamBodyRefresh() {
        return this.examBodyRefresh;
    }

    public final int getExamIndex() {
        return this.examIndex;
    }

    @Nullable
    public final SkillHomeList getExamList() {
        return this.examList;
    }

    public final boolean getExamRefresh() {
        return this.examRefresh;
    }

    @Nullable
    public final SkillHome getHistoryData() {
        return this.historyData;
    }

    @NotNull
    public final MutableStateFlow<Long> getHistoryMsf() {
        return this.historyMsf;
    }

    public final int getLastTabDsl() {
        return this.lastTabDsl;
    }

    public final boolean getMCurrentShowTop24() {
        return this.mCurrentShowTop24;
    }

    @Nullable
    public final String getMRuleData() {
        return this.mRuleData;
    }

    @Nullable
    public final SkillHome getTop24FirstFolderSkill() {
        return this.top24FirstFolderSkill;
    }

    @Nullable
    public final SkillHome getTop24FirstSkill() {
        return this.top24FirstSkill;
    }

    @Nullable
    public final List<SkillHome> getTop24List() {
        return this.top24List;
    }

    @NotNull
    public final List<SkillHome> getTop24ShowList() {
        return this.top24ShowList;
    }

    @NotNull
    public final MutableStateFlow<Long> getTop24ShowMsf() {
        return this.top24ShowMsf;
    }

    @Nullable
    public final SkillHome getTopBodyFirstFolderSkill() {
        return this.topBodyFirstFolderSkill;
    }

    @Nullable
    public final SkillHome getTopBodyFirstSkill() {
        return this.topBodyFirstSkill;
    }

    @Nullable
    public final List<SkillHome> getTopBodyList() {
        return this.topBodyList;
    }

    @NotNull
    public final List<SkillHome> getTopBodyShowList() {
        return this.topBodyShowList;
    }

    @NotNull
    public final MutableStateFlow<Long> getTopBodyShowMsf() {
        return this.topBodyShowMsf;
    }

    @NotNull
    public final MutableStateFlow<Long> getTrainExamListMsf() {
        return this.trainExamListMsf;
    }

    public final int getType() {
        return this.type;
    }

    public final void httpGetBodyCheck() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C06501(null), 3, null);
    }

    public final void httpGetPracticeRecord(@NotNull SkillHome m2) {
        Intrinsics.checkNotNullParameter(m2, "m");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C06511(m2, this, null), 3, null);
    }

    public final void httpGetSkillHome() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C06521(null), 3, null);
    }

    public final void httpGetTrainExamList(boolean refresh) {
        int i2;
        Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = 1;
        int i3 = this.lastTabDsl;
        if (i3 == 0) {
            i2 = refresh ? 1 : 1 + this.examIndex;
            this.examIndex = i2;
            intRef.element = i2;
        } else if (i3 == 1) {
            i2 = refresh ? 1 : 1 + this.examBodyIndex;
            this.examBodyIndex = i2;
            intRef.element = i2;
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C06531(intRef, this, refresh, null), 3, null);
    }

    public final void setExamBodyIndex(int i2) {
        this.examBodyIndex = i2;
    }

    public final void setExamBodyList(@Nullable SkillHomeList skillHomeList) {
        this.examBodyList = skillHomeList;
    }

    public final void setExamBodyRefresh(boolean z2) {
        this.examBodyRefresh = z2;
    }

    public final void setExamIndex(int i2) {
        this.examIndex = i2;
    }

    public final void setExamList(@Nullable SkillHomeList skillHomeList) {
        this.examList = skillHomeList;
    }

    public final void setExamRefresh(boolean z2) {
        this.examRefresh = z2;
    }

    public final void setHistoryData(@Nullable SkillHome skillHome) {
        this.historyData = skillHome;
    }

    public final void setLastTabDsl(int i2) {
        this.lastTabDsl = i2;
    }

    public final void setMCurrentShowTop24(boolean z2) {
        this.mCurrentShowTop24 = z2;
    }

    public final void setMRuleData(@Nullable String str) {
        this.mRuleData = str;
    }

    public final void setTop24FirstFolderSkill(@Nullable SkillHome skillHome) {
        this.top24FirstFolderSkill = skillHome;
    }

    public final void setTop24FirstSkill(@Nullable SkillHome skillHome) {
        this.top24FirstSkill = skillHome;
    }

    public final void setTop24List(@Nullable List<SkillHome> list) {
        this.top24List = list;
    }

    public final void setTop24ShowList(@NotNull List<SkillHome> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.top24ShowList = list;
    }

    public final void setTopBodyFirstFolderSkill(@Nullable SkillHome skillHome) {
        this.topBodyFirstFolderSkill = skillHome;
    }

    public final void setTopBodyFirstSkill(@Nullable SkillHome skillHome) {
        this.topBodyFirstSkill = skillHome;
    }

    public final void setTopBodyList(@Nullable List<SkillHome> list) {
        this.topBodyList = list;
    }

    public final void setTopBodyShowList(@NotNull List<SkillHome> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.topBodyShowList = list;
    }

    public final void setType(int i2) {
        this.type = i2;
    }
}
