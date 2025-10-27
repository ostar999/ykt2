package com.yddmi.doctor.pages.set;

import androidx.lifecycle.ViewModelKt;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.hjq.toast.Toaster;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.psychiatrygarden.utils.CommonParameter;
import com.umeng.analytics.pro.am;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddCacheManager;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.request.BankReqResult;
import com.yddmi.doctor.entity.request.HomeMsgReq;
import com.yddmi.doctor.entity.request.MeReplyReq;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.HomeMsgList;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yikaobang.yixue.R2;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0010\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u00104\u001a\u000205J\u000e\u00106\u001a\u0002052\u0006\u00107\u001a\u000208J\u0006\u00109\u001a\u000205J&\u0010:\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010<0;2\u0006\u0010=\u001a\u00020<2\u0006\u0010>\u001a\u00020<2\u0006\u0010?\u001a\u00020<J\u0018\u0010@\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0;2\b\b\u0002\u0010A\u001a\u00020BJ\u0010\u0010C\u001a\u0002052\b\b\u0002\u0010A\u001a\u00020BJ\u0016\u0010D\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010<0;2\u0006\u0010=\u001a\u00020<J\u0006\u0010E\u001a\u000205J\u0006\u0010F\u001a\u000205J\u0010\u0010G\u001a\u0002052\b\b\u0002\u0010A\u001a\u00020BJ2\u0010H\u001a\u0002052\u0006\u0010I\u001a\u00020<2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010<2\n\b\u0002\u0010K\u001a\u0004\u0018\u00010<2\n\b\u0002\u0010L\u001a\u0004\u0018\u00010<J*\u0010M\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010<0;2\u0006\u0010N\u001a\u0002082\b\b\u0002\u0010O\u001a\u00020\t2\b\b\u0002\u0010P\u001a\u00020\tJ\u000e\u0010Q\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010<0;R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007R\u001a\u0010\u0016\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0007R\u001c\u0010#\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u000f\"\u0004\b%\u0010\u0011R\u000e\u0010&\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010'\u001a\u0004\u0018\u00010(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0007R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u0007R\u001a\u00101\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0018\"\u0004\b3\u0010\u001a¨\u0006R"}, d2 = {"Lcom/yddmi/doctor/pages/set/SetViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "bankInfoMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getBankInfoMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "currentFeedBackIndex", "", "currentIndex", "currentNoticeIndex", "feedBackList", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "getFeedBackList", "()Lcom/yddmi/doctor/entity/result/HomeMsgList;", "setFeedBackList", "(Lcom/yddmi/doctor/entity/result/HomeMsgList;)V", "feedBackListMsf", "getFeedBackListMsf", "feedbackMsf", "getFeedbackMsf", "lastTab", "getLastTab", "()I", "setLastTab", "(I)V", "mBankInfo", "Lcom/yddmi/doctor/entity/request/BankReqResult;", "getMBankInfo", "()Lcom/yddmi/doctor/entity/request/BankReqResult;", "setMBankInfo", "(Lcom/yddmi/doctor/entity/request/BankReqResult;)V", "noticeMsf", "getNoticeMsf", "noticeMsgList", "getNoticeMsgList", "setNoticeMsgList", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "skillCall", "Lcom/yddmi/doctor/entity/result/SkillCall;", "getSkillCall", "()Lcom/yddmi/doctor/entity/result/SkillCall;", "setSkillCall", "(Lcom/yddmi/doctor/entity/result/SkillCall;)V", "skillMsf", "getSkillMsf", "timeCostMsf", "getTimeCostMsf", "type", "getType", "setType", "dealNoticeAllRead", "", "dealNoticeRead", "data", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "dealTimeGo", "forgetPwd", "Lkotlinx/coroutines/flow/Flow;", "", AliyunLogCommon.TERMINAL_TYPE, "code", "psw", "getMessageList", "refresh", "", "getNoticeList", "getPushCodeForgetPwd", "httpGetContact", "httpGetPostUserBankInfoV", "httpGetReplyAppList", "httpPostReplySave", "con", "fileUp", "userName", "userPhone", "readMessageList", "m", "read", RequestParameters.SUBRESOURCE_DELETE, "userAccountCancel", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class SetViewModel extends BaseViewModel {

    @Nullable
    private HomeMsgList feedBackList;

    @Nullable
    private BankReqResult mBankInfo;

    @Nullable
    private HomeMsgList noticeMsgList;

    @Nullable
    private SkillCall skillCall;
    private int type = 100;
    private int lastTab = 1;

    @NotNull
    private final MutableStateFlow<Integer> timeCostMsf = StateFlowKt.MutableStateFlow(-1);

    @NotNull
    private final MutableStateFlow<Long> skillMsf = StateFlowKt.MutableStateFlow(-1L);

    @NotNull
    private final MutableStateFlow<Long> feedbackMsf = StateFlowKt.MutableStateFlow(-100L);

    @NotNull
    private final MutableStateFlow<Long> feedBackListMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> bankInfoMsf = StateFlowKt.MutableStateFlow(0L);
    private int currentFeedBackIndex = 1;
    private final int pageSize = 10;
    private int currentIndex = 1;
    private int currentNoticeIndex = 1;

    @NotNull
    private final MutableStateFlow<Long> noticeMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$dealNoticeAllRead$1", f = "SetViewModel.kt", i = {}, l = {227}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nSetViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetViewModel.kt\ncom/yddmi/doctor/pages/set/SetViewModel$dealNoticeAllRead$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,260:1\n1855#2,2:261\n*S KotlinDebug\n*F\n+ 1 SetViewModel.kt\ncom/yddmi/doctor/pages/set/SetViewModel$dealNoticeAllRead$1\n*L\n223#1:261,2\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$dealNoticeAllRead$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetViewModel.this.new AnonymousClass1(continuation);
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
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    HomeMsgList noticeMsgList = SetViewModel.this.getNoticeMsgList();
                    if ((noticeMsgList != null ? noticeMsgList.getRows() : null) != null) {
                        HomeMsgList noticeMsgList2 = SetViewModel.this.getNoticeMsgList();
                        Intrinsics.checkNotNull(noticeMsgList2);
                        List<HomeMsg> rows = noticeMsgList2.getRows();
                        Intrinsics.checkNotNull(rows);
                        for (HomeMsg homeMsg : rows) {
                            if (homeMsg != null) {
                                homeMsg.setRead(1);
                            }
                        }
                    }
                    MutableStateFlow<Long> noticeMsf = SetViewModel.this.getNoticeMsf();
                    Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                    this.label = 1;
                    if (noticeMsf.emit(lBoxLong, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$dealNoticeRead$1", f = "SetViewModel.kt", i = {}, l = {209}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$dealNoticeRead$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08901 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeMsg $data;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08901(HomeMsg homeMsg, Continuation<? super C08901> continuation) {
            super(2, continuation);
            this.$data = homeMsg;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08901(this.$data, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08901) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    YddCacheManager companion = YddCacheManager.INSTANCE.getInstance();
                    HomeMsg homeMsg = this.$data;
                    this.label = 1;
                    if (companion.saveNoticeMsg2Cache(homeMsg, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$dealTimeGo$1", f = "SetViewModel.kt", i = {0, 1}, l = {52, 53}, m = "invokeSuspend", n = {am.aC, am.aC}, s = {"I$0", "I$0"})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$dealTimeGo$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08911 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        int label;

        public C08911(Continuation<? super C08911> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetViewModel.this.new C08911(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08911) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0050  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0058  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0050 -> B:19:0x0053). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = -1
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L26
                if (r1 == r4) goto L1e
                if (r1 != r3) goto L16
                int r1 = r8.I$0
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r8
                goto L53
            L16:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L1e:
                int r1 = r8.I$0
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r1
                r1 = r8
                goto L43
            L26:
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = 60
                r1 = r8
            L2c:
                if (r2 >= r9) goto L58
                com.yddmi.doctor.pages.set.SetViewModel r5 = com.yddmi.doctor.pages.set.SetViewModel.this
                kotlinx.coroutines.flow.MutableStateFlow r5 = r5.getTimeCostMsf()
                java.lang.Integer r6 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r9)
                r1.I$0 = r9
                r1.label = r4
                java.lang.Object r5 = r5.emit(r6, r1)
                if (r5 != r0) goto L43
                return r0
            L43:
                r1.I$0 = r9
                r1.label = r3
                r5 = 1000(0x3e8, double:4.94E-321)
                java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r5, r1)
                if (r5 != r0) goto L50
                return r0
            L50:
                r7 = r1
                r1 = r9
                r9 = r7
            L53:
                int r1 = r1 + r2
                r7 = r1
                r1 = r9
                r9 = r7
                goto L2c
            L58:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.set.SetViewModel.C08911.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$getNoticeList$1", f = "SetViewModel.kt", i = {}, l = {192}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$getNoticeList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08921 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $refresh;
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$getNoticeList$1$1", f = "SetViewModel.kt", i = {}, l = {R2.array.ease_file_file_suffix}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$getNoticeList$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04351 extends SuspendLambda implements Function3<FlowCollector<? super HomeMsgList>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SetViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04351(SetViewModel setViewModel, Continuation<? super C04351> continuation) {
                super(3, continuation);
                this.this$0 = setViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                C04351 c04351 = new C04351(this.this$0, continuation);
                c04351.L$0 = th;
                return c04351.invokeSuspend(Unit.INSTANCE);
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
                    MutableStateFlow<Long> noticeMsf = this.this$0.getNoticeMsf();
                    Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                    this.label = 1;
                    if (noticeMsf.emit(lBoxLong, this) == coroutine_suspended) {
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
        public C08921(boolean z2, Continuation<? super C08921> continuation) {
            super(2, continuation);
            this.$refresh = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetViewModel.this.new C08921(this.$refresh, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08921) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(YddClinicRepository.getNoticeList$default(YddClinicRepository.INSTANCE, Boxing.boxInt(SetViewModel.this.currentNoticeIndex), Boxing.boxInt(SetViewModel.this.pageSize), 0, 4, null), new C04351(SetViewModel.this, null));
                final boolean z2 = this.$refresh;
                final SetViewModel setViewModel = SetViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetViewModel.getNoticeList.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((HomeMsgList) obj2, (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(@Nullable HomeMsgList homeMsgList, @NotNull Continuation<? super Unit> continuation) {
                        HomeMsgList noticeMsgList;
                        List<HomeMsg> rows;
                        if (z2) {
                            setViewModel.setNoticeMsgList(homeMsgList);
                        } else {
                            if ((homeMsgList != null ? homeMsgList.getRows() : null) != null && (noticeMsgList = setViewModel.getNoticeMsgList()) != null && (rows = noticeMsgList.getRows()) != null) {
                                Boxing.boxBoolean(rows.addAll(homeMsgList.getRows()));
                            }
                        }
                        Object objEmit = setViewModel.getNoticeMsf().emit(Boxing.boxLong(DateUtil.getTimeInMillisLong()), continuation);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$httpGetContact$1", f = "SetViewModel.kt", i = {}, l = {87, 88, 94}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$httpGetContact$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08931 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C08931(Continuation<? super C08931> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetViewModel.this.new C08931(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08931) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            SetViewModel setViewModel;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> skillMsf = SetViewModel.this.getSkillMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.L$0 = null;
                this.label = 3;
                if (skillMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                setViewModel = SetViewModel.this;
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                this.L$0 = setViewModel;
                this.label = 1;
                obj = yddClinicRepository.getContact(this);
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
                setViewModel = (SetViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            setViewModel.setSkillCall((SkillCall) obj);
            MutableStateFlow<Long> skillMsf2 = SetViewModel.this.getSkillMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.L$0 = null;
            this.label = 2;
            if (skillMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$httpGetPostUserBankInfoV$1", f = "SetViewModel.kt", i = {}, l = {244, R2.attr.actionModeFindDrawable, 254}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$httpGetPostUserBankInfoV$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08941 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C08941(Continuation<? super C08941> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetViewModel.this.new C08941(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08941) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            SetViewModel setViewModel;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            boolean z2 = true;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                MutableStateFlow<Long> bankInfoMsf = SetViewModel.this.getBankInfoMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.L$0 = null;
                this.label = 3;
                if (bankInfoMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                setViewModel = SetViewModel.this;
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                int iUserId = YddUserManager.INSTANCE.getInstance().userId();
                this.L$0 = setViewModel;
                this.label = 1;
                obj = yddClinicRepository.getPostUserBankInfo(iUserId, this);
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
                setViewModel = (SetViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            setViewModel.setMBankInfo((BankReqResult) obj);
            if (SetViewModel.this.getMBankInfo() != null) {
                z2 = false;
            }
            LogExtKt.logd("用户未登录 httpGetPostUserBankInfo() BBBBBBBBBBB " + z2, YddConfig.TAG);
            MutableStateFlow<Long> bankInfoMsf2 = SetViewModel.this.getBankInfoMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.L$0 = null;
            this.label = 2;
            if (bankInfoMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$httpGetReplyAppList$1", f = "SetViewModel.kt", i = {}, l = {144}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$httpGetReplyAppList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08951 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $refresh;
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$httpGetReplyAppList$1$1", f = "SetViewModel.kt", i = {}, l = {143}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$httpGetReplyAppList$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04361 extends SuspendLambda implements Function3<FlowCollector<? super HomeMsgList>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SetViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04361(SetViewModel setViewModel, Continuation<? super C04361> continuation) {
                super(3, continuation);
                this.this$0 = setViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                C04361 c04361 = new C04361(this.this$0, continuation);
                c04361.L$0 = th;
                return c04361.invokeSuspend(Unit.INSTANCE);
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
                    MutableStateFlow<Long> feedBackListMsf = this.this$0.getFeedBackListMsf();
                    Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                    this.label = 1;
                    if (feedBackListMsf.emit(lBoxLong, this) == coroutine_suspended) {
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
        public C08951(boolean z2, Continuation<? super C08951> continuation) {
            super(2, continuation);
            this.$refresh = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return SetViewModel.this.new C08951(this.$refresh, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08951) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(YddClinicRepository.INSTANCE.getReplyAppList(2, YddUserManager.INSTANCE.getInstance().userName(), SetViewModel.this.currentFeedBackIndex, 10), new C04361(SetViewModel.this, null));
                final boolean z2 = this.$refresh;
                final SetViewModel setViewModel = SetViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.set.SetViewModel.httpGetReplyAppList.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((HomeMsgList) obj2, (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(@Nullable HomeMsgList homeMsgList, @NotNull Continuation<? super Unit> continuation) {
                        HomeMsgList feedBackList;
                        List<HomeMsg> rows;
                        if (z2) {
                            setViewModel.setFeedBackList(homeMsgList);
                        } else {
                            if ((homeMsgList != null ? homeMsgList.getRows() : null) != null && (feedBackList = setViewModel.getFeedBackList()) != null && (rows = feedBackList.getRows()) != null) {
                                Boxing.boxBoolean(rows.addAll(homeMsgList.getRows()));
                            }
                        }
                        Object objEmit = setViewModel.getFeedBackListMsf().emit(Boxing.boxLong(DateUtil.getTimeInMillisLong()), continuation);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.SetViewModel$httpPostReplySave$1", f = "SetViewModel.kt", i = {}, l = {116, 117, 126}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.SetViewModel$httpPostReplySave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08961 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $con;
        final /* synthetic */ String $userName;
        final /* synthetic */ String $userPhone;
        int label;
        final /* synthetic */ SetViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08961(String str, String str2, String str3, SetViewModel setViewModel, Continuation<? super C08961> continuation) {
            super(2, continuation);
            this.$con = str;
            this.$userName = str2;
            this.$userPhone = str3;
            this.this$0 = setViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08961(this.$con, this.$userName, this.$userPhone, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08961) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                } else {
                    String message = th.getMessage();
                    if (message != null) {
                        Toaster.show((CharSequence) message);
                    }
                }
                MutableStateFlow<Long> feedbackMsf = this.this$0.getFeedbackMsf();
                Long lBoxLong = Boxing.boxLong(Random.INSTANCE.nextLong(100L, 199L));
                this.label = 3;
                if (feedbackMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                MeReplyReq meReplyReq = new MeReplyReq((String) null, 0, 0, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, 0, 1023, (DefaultConstructorMarker) null);
                meReplyReq.setContent(this.$con);
                meReplyReq.setType(4);
                YddUserManager.Companion companion = YddUserManager.INSTANCE;
                meReplyReq.setUserId(companion.getInstance().userId());
                meReplyReq.setFileId("");
                meReplyReq.setSys("android");
                meReplyReq.setName(companion.getInstance().userNickName());
                meReplyReq.setPhone(companion.getInstance().userName());
                meReplyReq.setUserName(this.$userName);
                meReplyReq.setUserPhone(this.$userPhone);
                meReplyReq.setSource(2);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                this.label = 1;
                if (yddClinicRepository.postReplySave(meReplyReq, this) == coroutine_suspended) {
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
            MutableStateFlow<Long> feedbackMsf2 = this.this$0.getFeedbackMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (feedbackMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ Flow getMessageList$default(SetViewModel setViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        return setViewModel.getMessageList(z2);
    }

    public static /* synthetic */ void getNoticeList$default(SetViewModel setViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        setViewModel.getNoticeList(z2);
    }

    public static /* synthetic */ void httpGetReplyAppList$default(SetViewModel setViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        setViewModel.httpGetReplyAppList(z2);
    }

    public static /* synthetic */ void httpPostReplySave$default(SetViewModel setViewModel, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = "";
        }
        if ((i2 & 4) != 0) {
            str3 = "";
        }
        if ((i2 & 8) != 0) {
            str4 = "";
        }
        setViewModel.httpPostReplySave(str, str2, str3, str4);
    }

    public static /* synthetic */ Flow readMessageList$default(SetViewModel setViewModel, HomeMsg homeMsg, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = -1;
        }
        if ((i4 & 4) != 0) {
            i3 = -1;
        }
        return setViewModel.readMessageList(homeMsg, i2, i3);
    }

    public final void dealNoticeAllRead() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    public final void dealNoticeRead(@NotNull HomeMsg data) {
        Intrinsics.checkNotNullParameter(data, "data");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08901(data, null), 3, null);
    }

    public final void dealTimeGo() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08911(null), 3, null);
    }

    @NotNull
    public final Flow<String> forgetPwd(@NotNull String phone, @NotNull String code, @NotNull String psw) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(psw, "psw");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(AliyunLogCommon.TERMINAL_TYPE, phone);
        linkedHashMap.put("code", code);
        linkedHashMap.put(CommonParameter.password, psw);
        return YddClinicRepository.INSTANCE.forgetPwd(linkedHashMap);
    }

    @NotNull
    public final MutableStateFlow<Long> getBankInfoMsf() {
        return this.bankInfoMsf;
    }

    @Nullable
    public final HomeMsgList getFeedBackList() {
        return this.feedBackList;
    }

    @NotNull
    public final MutableStateFlow<Long> getFeedBackListMsf() {
        return this.feedBackListMsf;
    }

    @NotNull
    public final MutableStateFlow<Long> getFeedbackMsf() {
        return this.feedbackMsf;
    }

    public final int getLastTab() {
        return this.lastTab;
    }

    @Nullable
    public final BankReqResult getMBankInfo() {
        return this.mBankInfo;
    }

    @NotNull
    public final Flow<HomeMsgList> getMessageList(boolean refresh) {
        this.currentIndex = refresh ? 1 : 1 + this.currentIndex;
        return YddClinicRepository.INSTANCE.getMessageList(YddUserManager.INSTANCE.getInstance().userId(), Integer.valueOf(this.currentIndex), Integer.valueOf(this.pageSize));
    }

    public final void getNoticeList(boolean refresh) {
        this.currentNoticeIndex = refresh ? 1 : 1 + this.currentNoticeIndex;
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08921(refresh, null), 3, null);
    }

    @NotNull
    public final MutableStateFlow<Long> getNoticeMsf() {
        return this.noticeMsf;
    }

    @Nullable
    public final HomeMsgList getNoticeMsgList() {
        return this.noticeMsgList;
    }

    @NotNull
    public final Flow<String> getPushCodeForgetPwd(@NotNull String phone) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        return YddClinicRepository.INSTANCE.getPushCodeForgetPwd(phone);
    }

    @Nullable
    public final SkillCall getSkillCall() {
        return this.skillCall;
    }

    @NotNull
    public final MutableStateFlow<Long> getSkillMsf() {
        return this.skillMsf;
    }

    @NotNull
    public final MutableStateFlow<Integer> getTimeCostMsf() {
        return this.timeCostMsf;
    }

    public final int getType() {
        return this.type;
    }

    public final void httpGetContact() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08931(null), 3, null);
    }

    public final void httpGetPostUserBankInfoV() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08941(null), 3, null);
        } else {
            LogExtKt.logd("用户未登录 httpGetPostUserBankInfo()", YddConfig.TAG);
        }
    }

    public final void httpGetReplyAppList(boolean refresh) {
        this.currentFeedBackIndex = refresh ? 1 : 1 + this.currentFeedBackIndex;
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08951(refresh, null), 3, null);
    }

    public final void httpPostReplySave(@NotNull String con, @Nullable String fileUp, @Nullable String userName, @Nullable String userPhone) {
        Intrinsics.checkNotNullParameter(con, "con");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08961(con, userName, userPhone, this, null), 3, null);
    }

    @NotNull
    public final Flow<String> readMessageList(@NotNull HomeMsg m2, int read, int delete) {
        Intrinsics.checkNotNullParameter(m2, "m");
        HomeMsgReq homeMsgReq = new HomeMsgReq(m2.getId(), YddUserManager.INSTANCE.getInstance().userId(), 0, 0, 12, (DefaultConstructorMarker) null);
        homeMsgReq.setDelete(delete);
        homeMsgReq.setRead(read);
        return YddClinicRepository.INSTANCE.readMessageList(homeMsgReq);
    }

    public final void setFeedBackList(@Nullable HomeMsgList homeMsgList) {
        this.feedBackList = homeMsgList;
    }

    public final void setLastTab(int i2) {
        this.lastTab = i2;
    }

    public final void setMBankInfo(@Nullable BankReqResult bankReqResult) {
        this.mBankInfo = bankReqResult;
    }

    public final void setNoticeMsgList(@Nullable HomeMsgList homeMsgList) {
        this.noticeMsgList = homeMsgList;
    }

    public final void setSkillCall(@Nullable SkillCall skillCall) {
        this.skillCall = skillCall;
    }

    public final void setType(int i2) {
        this.type = i2;
    }

    @NotNull
    public final Flow<String> userAccountCancel() {
        return YddClinicRepository.INSTANCE.userAccountCancel(new LinkedHashMap());
    }
}
