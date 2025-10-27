package com.yddmi.doctor.pages.set;

import androidx.lifecycle.ViewModelKt;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.hjq.toast.Toaster;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.request.MeReplyReq;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J2\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0011R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lcom/yddmi/doctor/pages/set/FeedbackViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "feedbackMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getFeedbackMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "type", "", "getType", "()I", "setType", "(I)V", "httpPostReplySave", "", "con", "", "fileUp", "userName", "userPhone", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class FeedbackViewModel extends BaseViewModel {
    private int type = 100;

    @NotNull
    private final MutableStateFlow<Long> feedbackMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.set.FeedbackViewModel$httpPostReplySave$1", f = "FeedbackViewModel.kt", i = {}, l = {44, 45, 54}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.set.FeedbackViewModel$httpPostReplySave$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $con;
        final /* synthetic */ String $fileUp;
        final /* synthetic */ String $userName;
        final /* synthetic */ String $userPhone;
        int label;
        final /* synthetic */ FeedbackViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, String str2, String str3, String str4, FeedbackViewModel feedbackViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$con = str;
            this.$fileUp = str2;
            this.$userName = str3;
            this.$userPhone = str4;
            this.this$0 = feedbackViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$con, this.$fileUp, this.$userName, this.$userPhone, this.this$0, continuation);
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
                meReplyReq.setFileId(this.$fileUp);
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

    public static /* synthetic */ void httpPostReplySave$default(FeedbackViewModel feedbackViewModel, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = "";
        }
        if ((i2 & 4) != 0) {
            str3 = "";
        }
        if ((i2 & 8) != 0) {
            str4 = "";
        }
        feedbackViewModel.httpPostReplySave(str, str2, str3, str4);
    }

    @NotNull
    public final MutableStateFlow<Long> getFeedbackMsf() {
        return this.feedbackMsf;
    }

    public final int getType() {
        return this.type;
    }

    public final void httpPostReplySave(@NotNull String con, @Nullable String fileUp, @Nullable String userName, @Nullable String userPhone) {
        Intrinsics.checkNotNullParameter(con, "con");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(con, fileUp, userName, userPhone, this, null), 3, null);
    }

    public final void setType(int i2) {
        this.type = i2;
    }
}
