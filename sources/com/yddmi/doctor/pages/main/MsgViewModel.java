package com.yddmi.doctor.pages.main;

import androidx.lifecycle.ViewModelKt;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.hjq.toast.Toaster;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.yddmi.doctor.config.YddCacheManager;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.request.HomeMsgReq;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.HomeMsgList;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u0007J\u0018\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\"2\b\b\u0002\u0010#\u001a\u00020$J\u0010\u0010%\u001a\u00020\u001e2\b\b\u0002\u0010#\u001a\u00020$J*\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010'0\"2\u0006\u0010(\u001a\u00020\u00072\b\b\u0002\u0010)\u001a\u00020\u00042\b\b\u0002\u0010*\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/yddmi/doctor/pages/main/MsgViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "currentIndex", "", "currentNoticeIndex", "detailsMsg", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "getDetailsMsg", "()Lcom/yddmi/doctor/entity/result/HomeMsg;", "setDetailsMsg", "(Lcom/yddmi/doctor/entity/result/HomeMsg;)V", "lastTabType", "getLastTabType", "()I", "setLastTabType", "(I)V", "noticeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getNoticeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "noticeMsgList", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "getNoticeMsgList", "()Lcom/yddmi/doctor/entity/result/HomeMsgList;", "setNoticeMsgList", "(Lcom/yddmi/doctor/entity/result/HomeMsgList;)V", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "dealNoticeAllRead", "", "dealNoticeRead", "data", "getMessageList", "Lkotlinx/coroutines/flow/Flow;", "refresh", "", "getNoticeList", "readMessageList", "", "m", "read", RequestParameters.SUBRESOURCE_DELETE, "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class MsgViewModel extends BaseViewModel {

    @Nullable
    private HomeMsg detailsMsg;

    @Nullable
    private HomeMsgList noticeMsgList;
    private final int pageSize = 10;
    private int currentIndex = 1;
    private int currentNoticeIndex = 1;
    private int lastTabType = -1;

    @NotNull
    private final MutableStateFlow<Long> noticeMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MsgViewModel$dealNoticeAllRead$1", f = "MsgViewModel.kt", i = {}, l = {116}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nMsgViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MsgViewModel.kt\ncom/yddmi/doctor/pages/main/MsgViewModel$dealNoticeAllRead$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,123:1\n1855#2,2:124\n*S KotlinDebug\n*F\n+ 1 MsgViewModel.kt\ncom/yddmi/doctor/pages/main/MsgViewModel$dealNoticeAllRead$1\n*L\n112#1:124,2\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.main.MsgViewModel$dealNoticeAllRead$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MsgViewModel.this.new AnonymousClass1(continuation);
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
                    HomeMsgList noticeMsgList = MsgViewModel.this.getNoticeMsgList();
                    if ((noticeMsgList != null ? noticeMsgList.getRows() : null) != null) {
                        HomeMsgList noticeMsgList2 = MsgViewModel.this.getNoticeMsgList();
                        Intrinsics.checkNotNull(noticeMsgList2);
                        List<HomeMsg> rows = noticeMsgList2.getRows();
                        Intrinsics.checkNotNull(rows);
                        for (HomeMsg homeMsg : rows) {
                            if (homeMsg != null) {
                                homeMsg.setRead(1);
                            }
                        }
                    }
                    MutableStateFlow<Long> noticeMsf = MsgViewModel.this.getNoticeMsf();
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MsgViewModel$dealNoticeRead$1", f = "MsgViewModel.kt", i = {}, l = {98}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MsgViewModel$dealNoticeRead$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08191 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeMsg $data;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08191(HomeMsg homeMsg, Continuation<? super C08191> continuation) {
            super(2, continuation);
            this.$data = homeMsg;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08191(this.$data, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08191) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MsgViewModel$getNoticeList$1", f = "MsgViewModel.kt", i = {}, l = {70}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MsgViewModel$getNoticeList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08201 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $refresh;
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.main.MsgViewModel$getNoticeList$1$1", f = "MsgViewModel.kt", i = {}, l = {68}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.main.MsgViewModel$getNoticeList$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04281 extends SuspendLambda implements Function3<FlowCollector<? super HomeMsgList>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ MsgViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04281(MsgViewModel msgViewModel, Continuation<? super C04281> continuation) {
                super(3, continuation);
                this.this$0 = msgViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                C04281 c04281 = new C04281(this.this$0, continuation);
                c04281.L$0 = th;
                return c04281.invokeSuspend(Unit.INSTANCE);
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
        public C08201(boolean z2, Continuation<? super C08201> continuation) {
            super(2, continuation);
            this.$refresh = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MsgViewModel.this.new C08201(this.$refresh, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08201) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowM2338catch = FlowKt.m2338catch(YddClinicRepository.getNoticeList$default(YddClinicRepository.INSTANCE, Boxing.boxInt(MsgViewModel.this.currentNoticeIndex), Boxing.boxInt(MsgViewModel.this.pageSize), 0, 4, null), new C04281(MsgViewModel.this, null));
                final boolean z2 = this.$refresh;
                final MsgViewModel msgViewModel = MsgViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.main.MsgViewModel.getNoticeList.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((HomeMsgList) obj2, (Continuation<? super Unit>) continuation);
                    }

                    @Nullable
                    public final Object emit(@Nullable HomeMsgList homeMsgList, @NotNull Continuation<? super Unit> continuation) {
                        HomeMsgList noticeMsgList;
                        List<HomeMsg> rows;
                        if (z2) {
                            msgViewModel.setNoticeMsgList(homeMsgList);
                        } else {
                            if ((homeMsgList != null ? homeMsgList.getRows() : null) != null && (noticeMsgList = msgViewModel.getNoticeMsgList()) != null && (rows = noticeMsgList.getRows()) != null) {
                                Boxing.boxBoolean(rows.addAll(homeMsgList.getRows()));
                            }
                        }
                        Object objEmit = msgViewModel.getNoticeMsf().emit(Boxing.boxLong(DateUtil.getTimeInMillisLong()), continuation);
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

    public static /* synthetic */ Flow getMessageList$default(MsgViewModel msgViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        return msgViewModel.getMessageList(z2);
    }

    public static /* synthetic */ void getNoticeList$default(MsgViewModel msgViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        msgViewModel.getNoticeList(z2);
    }

    public static /* synthetic */ Flow readMessageList$default(MsgViewModel msgViewModel, HomeMsg homeMsg, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = -1;
        }
        if ((i4 & 4) != 0) {
            i3 = -1;
        }
        return msgViewModel.readMessageList(homeMsg, i2, i3);
    }

    public final void dealNoticeAllRead() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    public final void dealNoticeRead(@NotNull HomeMsg data) {
        Intrinsics.checkNotNullParameter(data, "data");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08191(data, null), 3, null);
    }

    @Nullable
    public final HomeMsg getDetailsMsg() {
        return this.detailsMsg;
    }

    public final int getLastTabType() {
        return this.lastTabType;
    }

    @NotNull
    public final Flow<HomeMsgList> getMessageList(boolean refresh) {
        this.currentIndex = refresh ? 1 : 1 + this.currentIndex;
        return YddClinicRepository.INSTANCE.getMessageList(YddUserManager.INSTANCE.getInstance().userId(), Integer.valueOf(this.currentIndex), Integer.valueOf(this.pageSize));
    }

    public final void getNoticeList(boolean refresh) {
        this.currentNoticeIndex = refresh ? 1 : 1 + this.currentNoticeIndex;
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08201(refresh, null), 3, null);
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
    public final Flow<String> readMessageList(@NotNull HomeMsg m2, int read, int delete) {
        Intrinsics.checkNotNullParameter(m2, "m");
        HomeMsgReq homeMsgReq = new HomeMsgReq(m2.getId(), YddUserManager.INSTANCE.getInstance().userId(), 0, 0, 12, (DefaultConstructorMarker) null);
        homeMsgReq.setDelete(delete);
        homeMsgReq.setRead(read);
        return YddClinicRepository.INSTANCE.readMessageList(homeMsgReq);
    }

    public final void setDetailsMsg(@Nullable HomeMsg homeMsg) {
        this.detailsMsg = homeMsg;
    }

    public final void setLastTabType(int i2) {
        this.lastTabType = i2;
    }

    public final void setNoticeMsgList(@Nullable HomeMsgList homeMsgList) {
        this.noticeMsgList = homeMsgList;
    }
}
