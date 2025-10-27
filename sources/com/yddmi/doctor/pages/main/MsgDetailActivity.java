package com.yddmi.doctor.pages.main;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwnerKt;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.annotation.StatusBar;
import com.catchpig.annotation.Title;
import com.catchpig.mvvm.base.activity.BaseVMActivity;
import com.catchpig.mvvm.ext.FlowExtKt;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.Toaster;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.databinding.ActivityHomeMsgBinding;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.repository.YddClinicRepository;
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
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u0000 \u001a2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0012H\u0016J\b\u0010\u0017\u001a\u00020\u0012H\u0016J\b\u0010\u0018\u001a\u00020\u0012H\u0016J\b\u0010\u0019\u001a\u00020\u0012H\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001b"}, d2 = {"Lcom/yddmi/doctor/pages/main/MsgDetailActivity;", "Lcom/catchpig/mvvm/base/activity/BaseVMActivity;", "Lcom/yddmi/doctor/databinding/ActivityHomeMsgBinding;", "Lcom/yddmi/doctor/pages/main/MsgViewModel;", "()V", "dealRead", "", "getDealRead", "()Z", "setDealRead", "(Z)V", com.alipay.sdk.authjs.a.f3175h, "", "getMsgType", "()I", "setMsgType", "(I)V", "httpReadMessageList", "", "m", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "httpReadNoticeMessageList", "initFlow", "initParam", "initView", "viewShowDetails", "Companion", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@StatusBar(enabled = false)
@Title
@SourceDebugExtension({"SMAP\nMsgDetailActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MsgDetailActivity.kt\ncom/yddmi/doctor/pages/main/MsgDetailActivity\n+ 2 ImmersionBar.kt\ncom/gyf/immersionbar/ktx/ImmersionBarKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,142:1\n18#2,2:143\n1#3:145\n*S KotlinDebug\n*F\n+ 1 MsgDetailActivity.kt\ncom/yddmi/doctor/pages/main/MsgDetailActivity\n*L\n38#1:143,2\n38#1:145\n*E\n"})
/* loaded from: classes6.dex */
public final class MsgDetailActivity extends BaseVMActivity<ActivityHomeMsgBinding, MsgViewModel> {

    @NotNull
    private static final String TAG = "MsgDetailActivity";
    private boolean dealRead;
    private int msgType = -1;

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MsgDetailActivity$initFlow$1", f = "MsgDetailActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MsgDetailActivity$initFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08171 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08171(Continuation<? super C08171> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08171(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08171) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
    }

    private final void httpReadMessageList(HomeMsg m2) {
        FlowExtKt.lifecycle(getViewModel().readMessageList(m2, 1, -1), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MsgDetailActivity.httpReadMessageList.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.main.MsgDetailActivity.httpReadMessageList.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable String str) {
                BusUtils.post(GlobalAction.eventMsgNum);
            }
        });
    }

    private final void httpReadNoticeMessageList(HomeMsg m2) {
        FlowExtKt.lifecycle(YddClinicRepository.INSTANCE.getReadNoticeId(m2.getId()), this, false, new Function1<Throwable, Unit>() { // from class: com.yddmi.doctor.pages.main.MsgDetailActivity.httpReadNoticeMessageList.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Throwable it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }
        }, new Function1<String, Unit>() { // from class: com.yddmi.doctor.pages.main.MsgDetailActivity.httpReadNoticeMessageList.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable String str) {
                BusUtils.post(GlobalAction.eventMsgNum);
            }
        });
    }

    private final void viewShowDetails() {
        MsgViewModel viewModel = getViewModel();
        Object obj = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_MSG_DETAILS);
        viewModel.setDetailsMsg(obj instanceof HomeMsg ? (HomeMsg) obj : null);
        if (getViewModel().getDetailsMsg() == null) {
            Toaster.show(R.string.common_no_data);
            closeActivity();
            return;
        }
        bodyBinding(new Function1<ActivityHomeMsgBinding, Unit>() { // from class: com.yddmi.doctor.pages.main.MsgDetailActivity.viewShowDetails.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ActivityHomeMsgBinding activityHomeMsgBinding) {
                invoke2(activityHomeMsgBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ActivityHomeMsgBinding bodyBinding) {
                String createTime;
                Intrinsics.checkNotNullParameter(bodyBinding, "$this$bodyBinding");
                TextView textView = bodyBinding.titleTv;
                HomeMsg detailsMsg = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                Intrinsics.checkNotNull(detailsMsg);
                textView.setText(detailsMsg.getTitle());
                HomeMsg detailsMsg2 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                Intrinsics.checkNotNull(detailsMsg2);
                String typeName = detailsMsg2.getTypeName();
                if (typeName == null || typeName.length() == 0) {
                    bodyBinding.typeTv.setText("");
                } else {
                    TextView textView2 = bodyBinding.typeTv;
                    String string = MsgDetailActivity.this.getString(R.string.notification_type);
                    HomeMsg detailsMsg3 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                    Intrinsics.checkNotNull(detailsMsg3);
                    textView2.setText(string + ":" + detailsMsg3.getTypeName());
                }
                HomeMsg detailsMsg4 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                Intrinsics.checkNotNull(detailsMsg4);
                if (detailsMsg4.getReleaseTime().length() > 0) {
                    HomeMsg detailsMsg5 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                    Intrinsics.checkNotNull(detailsMsg5);
                    createTime = detailsMsg5.getReleaseTime();
                } else {
                    HomeMsg detailsMsg6 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                    Intrinsics.checkNotNull(detailsMsg6);
                    if (detailsMsg6.getUpdateTime().length() > 0) {
                        HomeMsg detailsMsg7 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                        Intrinsics.checkNotNull(detailsMsg7);
                        createTime = detailsMsg7.getUpdateTime();
                    } else {
                        HomeMsg detailsMsg8 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                        Intrinsics.checkNotNull(detailsMsg8);
                        createTime = detailsMsg8.getCreateTime();
                    }
                }
                bodyBinding.timeTv.setText(createTime);
                TextView textView3 = bodyBinding.detailsTv;
                HomeMsg detailsMsg9 = MsgDetailActivity.this.getViewModel().getDetailsMsg();
                Intrinsics.checkNotNull(detailsMsg9);
                textView3.setText(detailsMsg9.getContent());
            }
        });
        if (this.dealRead) {
            int i2 = this.msgType;
            if (i2 == 100) {
                HomeMsg detailsMsg = getViewModel().getDetailsMsg();
                Intrinsics.checkNotNull(detailsMsg);
                httpReadNoticeMessageList(detailsMsg);
            } else {
                if (i2 != 101) {
                    return;
                }
                HomeMsg detailsMsg2 = getViewModel().getDetailsMsg();
                Intrinsics.checkNotNull(detailsMsg2);
                httpReadMessageList(detailsMsg2);
            }
        }
    }

    public final boolean getDealRead() {
        return this.dealRead;
    }

    public final int getMsgType() {
        return this.msgType;
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initFlow() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C08171(null), 3, null);
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
            this.dealRead = intent.getBooleanExtra("dealRead", false);
            this.msgType = intent.getIntExtra(com.alipay.sdk.authjs.a.f3175h, -1);
        }
    }

    @Override // com.catchpig.mvvm.base.view.BaseVMView
    public void initView() {
        viewShowDetails();
    }

    public final void setDealRead(boolean z2) {
        this.dealRead = z2;
    }

    public final void setMsgType(int i2) {
        this.msgType = i2;
    }
}
