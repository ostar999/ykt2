package com.yddmi.doctor.pages.me;

import androidx.lifecycle.ViewModelKt;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.mvvm.utils.GsonUtil;
import com.catchpig.mvvm.utils.RequestFileUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.hjq.toast.Toaster;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.HomeMsgList;
import com.yddmi.doctor.entity.result.LocalMedia;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.MeProfileIntegral;
import com.yddmi.doctor.entity.result.SkillHistory;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillHomeList;
import com.yddmi.doctor.entity.result.SkillIntegral;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import okhttp3.MultipartBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010W\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010Y0XJ\u0018\u0010Z\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010[0X2\b\b\u0002\u0010\\\u001a\u00020]J\u0006\u0010^\u001a\u00020_J\u0006\u0010`\u001a\u00020_J\u000e\u0010a\u001a\u00020_2\u0006\u0010b\u001a\u00020\u0018J\u0010\u0010c\u001a\u00020_2\b\b\u0002\u0010\\\u001a\u00020]J\u0006\u0010d\u001a\u00020_J\u0010\u0010e\u001a\u00020_2\b\b\u0002\u0010\\\u001a\u00020]J\u0016\u0010f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010g0X2\u0006\u0010h\u001a\u00020gJ\u0016\u0010i\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010g0X2\u0006\u0010j\u001a\u00020kR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\b\"\u0004\b\u0013\u0010\nR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010!R\u001a\u0010$\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\b\"\u0004\b&\u0010\nR\u001a\u0010'\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\b\"\u0004\b)\u0010\nR\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0017\u00100\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\b1\u0010!R\u001c\u00102\u001a\u0004\u0018\u000103X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u0017\u00108\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\b9\u0010!R\u000e\u0010:\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\"\u0010;\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010<X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\"\u0010A\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010<X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010>\"\u0004\bC\u0010@R\u0017\u0010D\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\bE\u0010!R\u001a\u0010F\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\b\"\u0004\bH\u0010\nR\u001c\u0010I\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010\u000e\"\u0004\bK\u0010\u0010R\u0017\u0010L\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e¢\u0006\b\n\u0000\u001a\u0004\bM\u0010!R\u001a\u0010N\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\b\"\u0004\bP\u0010\nR\u001c\u0010Q\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010\u000e\"\u0004\bS\u0010\u0010R\u001a\u0010T\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010\b\"\u0004\bV\u0010\n¨\u0006l"}, d2 = {"Lcom/yddmi/doctor/pages/me/MeViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "currentIndex", "", "currentMsgIndex", "examBodyIndex", "getExamBodyIndex", "()I", "setExamBodyIndex", "(I)V", "examBodyList", "Lcom/yddmi/doctor/entity/result/SkillHomeList;", "getExamBodyList", "()Lcom/yddmi/doctor/entity/result/SkillHomeList;", "setExamBodyList", "(Lcom/yddmi/doctor/entity/result/SkillHomeList;)V", "examIndex", "getExamIndex", "setExamIndex", "examList", "getExamList", "setExamList", "historyData", "Lcom/yddmi/doctor/entity/result/SkillHome;", "getHistoryData", "()Lcom/yddmi/doctor/entity/result/SkillHome;", "setHistoryData", "(Lcom/yddmi/doctor/entity/result/SkillHome;)V", "historyMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getHistoryMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "integralMsf", "getIntegralMsf", "lastTab", "getLastTab", "setLastTab", "lastTabDsl", "getLastTabDsl", "setLastTabDsl", "mRecommend", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "getMRecommend", "()Lcom/yddmi/doctor/entity/result/HomeMsg;", "setMRecommend", "(Lcom/yddmi/doctor/entity/result/HomeMsg;)V", "mRecommendMsf", "getMRecommendMsf", "msg0List", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "getMsg0List", "()Lcom/yddmi/doctor/entity/result/HomeMsgList;", "setMsg0List", "(Lcom/yddmi/doctor/entity/result/HomeMsgList;)V", "msg0Msf", "getMsg0Msf", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "skillBodyList", "", "getSkillBodyList", "()Ljava/util/List;", "setSkillBodyList", "(Ljava/util/List;)V", "skillList", "getSkillList", "setSkillList", "skillListMsf", "getSkillListMsf", "trainBodyIndex", "getTrainBodyIndex", "setTrainBodyIndex", "trainBodyList", "getTrainBodyList", "setTrainBodyList", "trainExamListMsf", "getTrainExamListMsf", "trainIndex", "getTrainIndex", "setTrainIndex", "trainList", "getTrainList", "setTrainList", "type", "getType", "setType", "getPersonInfo", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/MeProfile;", "getRecordEquityDetail", "Lcom/yddmi/doctor/entity/result/SkillIntegral;", "refresh", "", "httpGetIntegralApp", "", "httpGetInviterInfo", "httpGetPracticeRecord", "m", "httpGetReplyAppList", "httpGetSkillMy", "httpGetTrainExamList", "httpPostPersonInfoSava", "", "nickName", "uploadImagesHeader", RemoteMessageConst.Notification.ICON, "Lcom/yddmi/doctor/entity/result/LocalMedia;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class MeViewModel extends BaseViewModel {

    @Nullable
    private SkillHomeList examBodyList;

    @Nullable
    private SkillHomeList examList;

    @Nullable
    private SkillHome historyData;
    private int lastTabDsl;

    @Nullable
    private HomeMsg mRecommend;

    @Nullable
    private HomeMsgList msg0List;

    @Nullable
    private List<SkillHome> skillBodyList;

    @Nullable
    private List<SkillHome> skillList;

    @Nullable
    private SkillHomeList trainBodyList;

    @Nullable
    private SkillHomeList trainList;
    private int type = 100;
    private int lastTab = 1;
    private int trainIndex = 1;
    private int trainBodyIndex = 1;
    private int examIndex = 1;
    private int examBodyIndex = 1;

    @NotNull
    private final MutableStateFlow<Long> skillListMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> trainExamListMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> historyMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> msg0Msf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> integralMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> mRecommendMsf = StateFlowKt.MutableStateFlow(0L);
    private final int pageSize = 20;
    private int currentIndex = 1;
    private int currentMsgIndex = 1;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeViewModel$httpGetIntegralApp$1", f = "MeViewModel.kt", i = {}, l = {229, 233, 239}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeViewModel$httpGetIntegralApp$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeViewModel.this.new AnonymousClass1(continuation);
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
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> integralMsf = MeViewModel.this.getIntegralMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 3;
                if (integralMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                this.label = 1;
                obj = yddClinicRepository.getIntegralApp(this);
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
            MeProfileIntegral meProfileIntegral = (MeProfileIntegral) obj;
            if (meProfileIntegral != null) {
                YddUserManager.INSTANCE.getInstance().userIntegralSet(meProfileIntegral.getIntegral());
            }
            MutableStateFlow<Long> integralMsf2 = MeViewModel.this.getIntegralMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (integralMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeViewModel$httpGetInviterInfo$1", f = "MeViewModel.kt", i = {}, l = {R2.attr.alertDialogTheme, R2.attr.align, R2.attr.all_course_live_banner_end_bg_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeViewModel$httpGetInviterInfo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08381 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C08381(Continuation<? super C08381> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeViewModel.this.new C08381(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            MeViewModel meViewModel;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> mRecommendMsf = MeViewModel.this.getMRecommendMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.L$0 = null;
                this.label = 3;
                if (mRecommendMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                meViewModel = MeViewModel.this;
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                this.L$0 = meViewModel;
                this.label = 1;
                obj = yddClinicRepository.getInviterInfo(this);
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
                meViewModel = (MeViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            meViewModel.setMRecommend((HomeMsg) obj);
            MutableStateFlow<Long> mRecommendMsf2 = MeViewModel.this.getMRecommendMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.L$0 = null;
            this.label = 2;
            if (mRecommendMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeViewModel$httpGetPracticeRecord$1", f = "MeViewModel.kt", i = {}, l = {193, 201, 207}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeViewModel$httpGetPracticeRecord$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08391 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SkillHome $m;
        int label;
        final /* synthetic */ MeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08391(SkillHome skillHome, MeViewModel meViewModel, Continuation<? super C08391> continuation) {
            super(2, continuation);
            this.$m = skillHome;
            this.this$0 = meViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08391(this.$m, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08391) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeViewModel$httpGetReplyAppList$1", f = "MeViewModel.kt", i = {}, l = {R2.attr.adScopeRadius}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeViewModel$httpGetReplyAppList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08401 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $refresh;
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeViewModel$httpGetReplyAppList$1$1", f = "MeViewModel.kt", i = {}, l = {R2.attr.adScopeCircleColor}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.me.MeViewModel$httpGetReplyAppList$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04301 extends SuspendLambda implements Function3<FlowCollector<? super HomeMsgList>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MeViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04301(MeViewModel meViewModel, Continuation<? super C04301> continuation) {
                super(3, continuation);
                this.this$0 = meViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                C04301 c04301 = new C04301(this.this$0, continuation);
                c04301.L$0 = th;
                return c04301.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i2 = this.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    String message = ((Throwable) this.L$0).getMessage();
                    if (message != null) {
                        Toaster.show((CharSequence) message);
                    }
                    MutableStateFlow<Long> msg0Msf = this.this$0.getMsg0Msf();
                    Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                    this.label = 1;
                    if (msg0Msf.emit(lBoxLong, this) == coroutine_suspended) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08401(boolean z2, Continuation<? super C08401> continuation) {
            super(2, continuation);
            this.$refresh = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeViewModel.this.new C08401(this.$refresh, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08401) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(YddClinicRepository.INSTANCE.getReplyAppList(2, YddUserManager.INSTANCE.getInstance().userName(), MeViewModel.this.currentMsgIndex, 10), new C04301(MeViewModel.this, null));
                final boolean z2 = this.$refresh;
                final MeViewModel meViewModel = MeViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.me.MeViewModel.httpGetReplyAppList.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((HomeMsgList) obj2, (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(@Nullable HomeMsgList homeMsgList, @NotNull Continuation<? super Unit> continuation) {
                        HomeMsgList msg0List;
                        List<HomeMsg> rows;
                        if (z2) {
                            meViewModel.setMsg0List(homeMsgList);
                        } else {
                            if ((homeMsgList != null ? homeMsgList.getRows() : null) != null && (msg0List = meViewModel.getMsg0List()) != null && (rows = msg0List.getRows()) != null) {
                                Boxing.boxBoolean(rows.addAll(homeMsgList.getRows()));
                            }
                        }
                        Object objEmit = meViewModel.getMsg0Msf().emit(Boxing.boxLong(DateUtil.getTimeInMillisLong()), continuation);
                        return objEmit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEmit : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowM2338catch.collect(flowCollector, this) == coroutine_suspended) {
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeViewModel$httpGetSkillMy$1", f = "MeViewModel.kt", i = {}, l = {81, 83, 85, 91}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeViewModel$httpGetSkillMy$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08411 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C08411(Continuation<? super C08411> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeViewModel.this.new C08411(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08411) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            MeViewModel meViewModel;
            MeViewModel meViewModel2;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> skillListMsf = MeViewModel.this.getSkillListMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.L$0 = null;
                this.label = 4;
                if (skillListMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                if (MeViewModel.this.getLastTabDsl() == 0) {
                    meViewModel2 = MeViewModel.this;
                    YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                    this.L$0 = meViewModel2;
                    this.label = 1;
                    obj = yddClinicRepository.getSkillMy(1, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    meViewModel2.setSkillList((List) obj);
                } else {
                    meViewModel = MeViewModel.this;
                    YddClinicRepository yddClinicRepository2 = YddClinicRepository.INSTANCE;
                    this.L$0 = meViewModel;
                    this.label = 2;
                    obj = yddClinicRepository2.getSkillMy(3, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    meViewModel.setSkillBodyList((List) obj);
                }
            } else if (i2 == 1) {
                meViewModel2 = (MeViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                meViewModel2.setSkillList((List) obj);
            } else {
                if (i2 != 2) {
                    if (i2 != 3 && i2 != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                meViewModel = (MeViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                meViewModel.setSkillBodyList((List) obj);
            }
            MutableStateFlow<Long> skillListMsf2 = MeViewModel.this.getSkillListMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.L$0 = null;
            this.label = 3;
            if (skillListMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.me.MeViewModel$httpGetTrainExamList$1", f = "MeViewModel.kt", i = {}, l = {130, 178, 184}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.me.MeViewModel$httpGetTrainExamList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.IntRef $index;
        final /* synthetic */ boolean $refresh;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08421(Ref.IntRef intRef, boolean z2, Continuation<? super C08421> continuation) {
            super(2, continuation);
            this.$index = intRef;
            this.$refresh = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MeViewModel.this.new C08421(this.$index, this.$refresh, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object skillRecord;
            SkillHomeList trainList;
            List<SkillHome> rows;
            SkillHomeList trainBodyList;
            List<SkillHome> rows2;
            SkillHomeList examList;
            List<SkillHome> rows3;
            SkillHomeList examBodyList;
            List<SkillHome> rows4;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> trainExamListMsf = MeViewModel.this.getTrainExamListMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 3;
                if (trainExamListMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                int i3 = MeViewModel.this.getLastTab() == 3 ? 0 : 1;
                Integer numBoxInt = Boxing.boxInt(this.$index.element);
                Integer numBoxInt2 = Boxing.boxInt(10);
                int i4 = MeViewModel.this.getLastTabDsl() == 0 ? 1 : 3;
                this.label = 1;
                skillRecord = yddClinicRepository.getSkillRecord(i3, (16 & 2) != 0 ? 1 : numBoxInt, (16 & 4) != 0 ? 10 : numBoxInt2, i4, (16 & 16) != 0 ? 1 : 0, this);
                if (skillRecord == coroutine_suspended) {
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
                skillRecord = obj;
            }
            SkillHomeList skillHomeList = (SkillHomeList) skillRecord;
            int lastTab = MeViewModel.this.getLastTab();
            if (lastTab == 3) {
                int lastTabDsl = MeViewModel.this.getLastTabDsl();
                if (lastTabDsl != 0) {
                    if (lastTabDsl == 1) {
                        if (this.$refresh) {
                            MeViewModel.this.setTrainBodyList(null);
                            MeViewModel.this.setTrainBodyList(skillHomeList);
                        } else if (skillHomeList != null) {
                            List<SkillHome> rows5 = skillHomeList.getRows();
                            if (!(rows5 == null || rows5.isEmpty()) && (trainBodyList = MeViewModel.this.getTrainBodyList()) != null && (rows2 = trainBodyList.getRows()) != null) {
                                Boxing.boxBoolean(rows2.addAll(skillHomeList.getRows()));
                            }
                        }
                    }
                } else if (this.$refresh) {
                    MeViewModel.this.setTrainList(null);
                    MeViewModel.this.setTrainList(skillHomeList);
                } else if (skillHomeList != null) {
                    List<SkillHome> rows6 = skillHomeList.getRows();
                    if (!(rows6 == null || rows6.isEmpty()) && (trainList = MeViewModel.this.getTrainList()) != null && (rows = trainList.getRows()) != null) {
                        Boxing.boxBoolean(rows.addAll(skillHomeList.getRows()));
                    }
                }
            } else if (lastTab == 4) {
                int lastTabDsl2 = MeViewModel.this.getLastTabDsl();
                if (lastTabDsl2 != 0) {
                    if (lastTabDsl2 == 1) {
                        if (this.$refresh) {
                            MeViewModel.this.setExamBodyList(null);
                            MeViewModel.this.setExamBodyList(skillHomeList);
                        } else if (skillHomeList != null) {
                            List<SkillHome> rows7 = skillHomeList.getRows();
                            if (!(rows7 == null || rows7.isEmpty()) && (examBodyList = MeViewModel.this.getExamBodyList()) != null && (rows4 = examBodyList.getRows()) != null) {
                                Boxing.boxBoolean(rows4.addAll(skillHomeList.getRows()));
                            }
                        }
                    }
                } else if (this.$refresh) {
                    MeViewModel.this.setExamList(null);
                    MeViewModel.this.setExamList(skillHomeList);
                } else if (skillHomeList != null) {
                    List<SkillHome> rows8 = skillHomeList.getRows();
                    if (!(rows8 == null || rows8.isEmpty()) && (examList = MeViewModel.this.getExamList()) != null && (rows3 = examList.getRows()) != null) {
                        Boxing.boxBoolean(rows3.addAll(skillHomeList.getRows()));
                    }
                }
            }
            MutableStateFlow<Long> trainExamListMsf2 = MeViewModel.this.getTrainExamListMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (trainExamListMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ Flow getRecordEquityDetail$default(MeViewModel meViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        return meViewModel.getRecordEquityDetail(z2);
    }

    public static /* synthetic */ void httpGetReplyAppList$default(MeViewModel meViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        meViewModel.httpGetReplyAppList(z2);
    }

    public static /* synthetic */ void httpGetTrainExamList$default(MeViewModel meViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        meViewModel.httpGetTrainExamList(z2);
    }

    public final int getExamBodyIndex() {
        return this.examBodyIndex;
    }

    @Nullable
    public final SkillHomeList getExamBodyList() {
        return this.examBodyList;
    }

    public final int getExamIndex() {
        return this.examIndex;
    }

    @Nullable
    public final SkillHomeList getExamList() {
        return this.examList;
    }

    @Nullable
    public final SkillHome getHistoryData() {
        return this.historyData;
    }

    @NotNull
    public final MutableStateFlow<Long> getHistoryMsf() {
        return this.historyMsf;
    }

    @NotNull
    public final MutableStateFlow<Long> getIntegralMsf() {
        return this.integralMsf;
    }

    public final int getLastTab() {
        return this.lastTab;
    }

    public final int getLastTabDsl() {
        return this.lastTabDsl;
    }

    @Nullable
    public final HomeMsg getMRecommend() {
        return this.mRecommend;
    }

    @NotNull
    public final MutableStateFlow<Long> getMRecommendMsf() {
        return this.mRecommendMsf;
    }

    @Nullable
    public final HomeMsgList getMsg0List() {
        return this.msg0List;
    }

    @NotNull
    public final MutableStateFlow<Long> getMsg0Msf() {
        return this.msg0Msf;
    }

    @NotNull
    public final Flow<MeProfile> getPersonInfo() {
        return YddClinicRepository.INSTANCE.getPersonInfoApp();
    }

    @NotNull
    public final Flow<SkillIntegral> getRecordEquityDetail(boolean refresh) {
        this.currentIndex = refresh ? 1 : 1 + this.currentIndex;
        HashMap map = new HashMap();
        map.put("current", Integer.valueOf(this.currentIndex));
        map.put(DatabaseManager.SIZE, Integer.valueOf(this.pageSize));
        return YddClinicRepository.INSTANCE.getRecordEquityDetail(map);
    }

    @Nullable
    public final List<SkillHome> getSkillBodyList() {
        return this.skillBodyList;
    }

    @Nullable
    public final List<SkillHome> getSkillList() {
        return this.skillList;
    }

    @NotNull
    public final MutableStateFlow<Long> getSkillListMsf() {
        return this.skillListMsf;
    }

    public final int getTrainBodyIndex() {
        return this.trainBodyIndex;
    }

    @Nullable
    public final SkillHomeList getTrainBodyList() {
        return this.trainBodyList;
    }

    @NotNull
    public final MutableStateFlow<Long> getTrainExamListMsf() {
        return this.trainExamListMsf;
    }

    public final int getTrainIndex() {
        return this.trainIndex;
    }

    @Nullable
    public final SkillHomeList getTrainList() {
        return this.trainList;
    }

    public final int getType() {
        return this.type;
    }

    public final void httpGetIntegralApp() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
        }
    }

    public final void httpGetInviterInfo() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08381(null), 3, null);
        }
    }

    public final void httpGetPracticeRecord(@NotNull SkillHome m2) {
        Intrinsics.checkNotNullParameter(m2, "m");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08391(m2, this, null), 3, null);
    }

    public final void httpGetReplyAppList(boolean refresh) {
        this.currentMsgIndex = refresh ? 1 : 1 + this.currentMsgIndex;
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08401(refresh, null), 3, null);
    }

    public final void httpGetSkillMy() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08411(null), 3, null);
    }

    public final void httpGetTrainExamList(boolean refresh) {
        int i2;
        Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = 1;
        int i3 = this.lastTab;
        if (i3 == 3) {
            int i4 = this.lastTabDsl;
            if (i4 == 0) {
                i2 = refresh ? 1 : 1 + this.trainIndex;
                this.trainIndex = i2;
                intRef.element = i2;
            } else if (i4 == 1) {
                i2 = refresh ? 1 : 1 + this.trainBodyIndex;
                this.trainBodyIndex = i2;
                intRef.element = i2;
            }
        } else if (i3 == 4) {
            int i5 = this.lastTabDsl;
            if (i5 == 0) {
                i2 = refresh ? 1 : 1 + this.examIndex;
                this.examIndex = i2;
                intRef.element = i2;
            } else if (i5 == 1) {
                i2 = refresh ? 1 : 1 + this.examBodyIndex;
                this.examBodyIndex = i2;
                intRef.element = i2;
            }
        }
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08421(intRef, refresh, null), 3, null);
    }

    @NotNull
    public final Flow<String> httpPostPersonInfoSava(@NotNull String nickName) {
        Intrinsics.checkNotNullParameter(nickName, "nickName");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("nickName", nickName);
        return YddClinicRepository.INSTANCE.postPersonInfo(linkedHashMap);
    }

    public final void setExamBodyIndex(int i2) {
        this.examBodyIndex = i2;
    }

    public final void setExamBodyList(@Nullable SkillHomeList skillHomeList) {
        this.examBodyList = skillHomeList;
    }

    public final void setExamIndex(int i2) {
        this.examIndex = i2;
    }

    public final void setExamList(@Nullable SkillHomeList skillHomeList) {
        this.examList = skillHomeList;
    }

    public final void setHistoryData(@Nullable SkillHome skillHome) {
        this.historyData = skillHome;
    }

    public final void setLastTab(int i2) {
        this.lastTab = i2;
    }

    public final void setLastTabDsl(int i2) {
        this.lastTabDsl = i2;
    }

    public final void setMRecommend(@Nullable HomeMsg homeMsg) {
        this.mRecommend = homeMsg;
    }

    public final void setMsg0List(@Nullable HomeMsgList homeMsgList) {
        this.msg0List = homeMsgList;
    }

    public final void setSkillBodyList(@Nullable List<SkillHome> list) {
        this.skillBodyList = list;
    }

    public final void setSkillList(@Nullable List<SkillHome> list) {
        this.skillList = list;
    }

    public final void setTrainBodyIndex(int i2) {
        this.trainBodyIndex = i2;
    }

    public final void setTrainBodyList(@Nullable SkillHomeList skillHomeList) {
        this.trainBodyList = skillHomeList;
    }

    public final void setTrainIndex(int i2) {
        this.trainIndex = i2;
    }

    public final void setTrainList(@Nullable SkillHomeList skillHomeList) {
        this.trainList = skillHomeList;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    @NotNull
    public final Flow<String> uploadImagesHeader(@NotNull LocalMedia icon) {
        Intrinsics.checkNotNullParameter(icon, "icon");
        String compressPath = icon.getCompressPath();
        MultipartBody.Part part = RequestFileUtil.uploadFileImg("file", new File(compressPath == null || compressPath.length() == 0 ? icon.getRealPath() : icon.getCompressPath()));
        YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(part, "part");
        return yddClinicRepository.uploadImagesHeader(part);
    }
}
