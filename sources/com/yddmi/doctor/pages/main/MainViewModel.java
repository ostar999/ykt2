package com.yddmi.doctor.pages.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.lifecycle.ViewModelKt;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.TransformExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddCacheManager;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.result.BatchInfo;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.HomeMsgList;
import com.yddmi.doctor.entity.result.MeProfileIntegral;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.entity.result.SkillTicket;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.BitmpUtils;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u0018J\u0016\u0010E\u001a\u00020C2\u000e\u0010F\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004J\u000e\u0010G\u001a\u00020C2\u0006\u0010F\u001a\u00020\"J\u000e\u0010H\u001a\u00020C2\u0006\u0010I\u001a\u00020JJ\u0006\u0010K\u001a\u00020CJ\u0006\u0010L\u001a\u00020CJ\u0006\u0010M\u001a\u00020CJ\u0006\u0010N\u001a\u00020CJ\u0006\u0010O\u001a\u00020CR\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\"\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u000fR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010'\u001a\u0004\u0018\u00010(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010*\"\u0004\b/\u0010,R\u0014\u00100\u001a\u00020(X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b1\u0010*R\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b3\u0010\u000fR\"\u00104\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0014\"\u0004\b6\u0010\u0016R\u0017\u00107\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u000fR\u001c\u00109\u001a\u0004\u0018\u00010:X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001c\u0010?\u001a\u0004\u0018\u00010(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010*\"\u0004\bA\u0010,¨\u0006P"}, d2 = {"Lcom/yddmi/doctor/pages/main/MainViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "allDataList", "", "Lcom/yddmi/doctor/entity/result/SkillHome;", "firstSkill", "getFirstSkill", "()Lcom/yddmi/doctor/entity/result/SkillHome;", "setFirstSkill", "(Lcom/yddmi/doctor/entity/result/SkillHome;)V", "getLabelMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getGetLabelMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "getSkillErrorMsf", "getGetSkillErrorMsf", "labelList", "getLabelList", "()Ljava/util/List;", "setLabelList", "(Ljava/util/List;)V", "leftLastIndex", "", "getLeftLastIndex", "()I", "setLeftLastIndex", "(I)V", "leftList", "getLeftList", "leftListMsf", "getLeftListMsf", "mMsgData", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "getMMsgData", "()Lcom/yddmi/doctor/entity/result/HomeMsg;", "setMMsgData", "(Lcom/yddmi/doctor/entity/result/HomeMsg;)V", "mRuleData", "", "getMRuleData", "()Ljava/lang/String;", "setMRuleData", "(Ljava/lang/String;)V", "newId", "getNewId", "setNewId", "newUrl", "getNewUrl", "noticeMsf", "getNoticeMsf", "rightList", "getRightList", "setRightList", "rightListChange", "getRightListChange", "skillCall", "Lcom/yddmi/doctor/entity/result/SkillCall;", "getSkillCall", "()Lcom/yddmi/doctor/entity/result/SkillCall;", "setSkillCall", "(Lcom/yddmi/doctor/entity/result/SkillCall;)V", "skillGetError", "getSkillGetError", "setSkillGetError", "changeLeftIndex", "", "index", "dealChangeLabel", "data", "dealNoticeRead", "dealWxShare", "share", "Lcom/yddmi/doctor/entity/result/SkillShare;", "httpGetContact", "httpGetIntegralApp", "httpGetNoticeList", "httpGetSkillBasicHome", "httpPointSave", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class MainViewModel extends BaseViewModel {

    @Nullable
    private List<SkillHome> allDataList;

    @Nullable
    private SkillHome firstSkill;

    @Nullable
    private List<SkillHome> labelList;
    private int leftLastIndex;

    @Nullable
    private HomeMsg mMsgData;

    @Nullable
    private List<SkillHome> rightList;

    @Nullable
    private SkillCall skillCall;

    @NotNull
    private final List<SkillHome> leftList = new ArrayList();

    @Nullable
    private String skillGetError = "";

    @Nullable
    private String mRuleData = "";

    @NotNull
    private String newId = "-1";

    @NotNull
    private final String newUrl = "LPN";

    @NotNull
    private final MutableStateFlow<Long> noticeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> leftListMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> rightListChange = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> getSkillErrorMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> getLabelMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$changeLeftIndex$1", f = "MainViewModel.kt", i = {}, l = {94}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$changeLeftIndex$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $index;
        int label;
        final /* synthetic */ MainViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i2, MainViewModel mainViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$index = i2;
            this.this$0 = mainViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$index, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:56:0x010f  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
            /*
                Method dump skipped, instructions count: 319
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.main.MainViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$dealChangeLabel$1", f = "MainViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$dealChangeLabel$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08071 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<SkillHome> $data;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08071(List<SkillHome> list, Continuation<? super C08071> continuation) {
            super(2, continuation);
            this.$data = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainViewModel.this.new C08071(this.$data, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08071) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            MainViewModel.this.setRightList(this.$data);
            List<SkillHome> rightList = MainViewModel.this.getRightList();
            LogExtKt.logd("右侧列表 " + (rightList != null ? Boxing.boxInt(rightList.size()) : null), YddConfig.TAG);
            MainViewModel.this.getRightListChange().setValue(Boxing.boxLong(DateUtil.getTimeInMillisLong()));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$dealNoticeRead$1", f = "MainViewModel.kt", i = {}, l = {241}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$dealNoticeRead$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08081 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeMsg $data;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08081(HomeMsg homeMsg, Continuation<? super C08081> continuation) {
            super(2, continuation);
            this.$data = homeMsg;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08081(this.$data, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08081) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$dealWxShare$1", f = "MainViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$dealWxShare$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SkillShare $share;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08091(SkillShare skillShare, Continuation<? super C08091> continuation) {
            super(2, continuation);
            this.$share = skillShare;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08091(this.$share, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Bitmap urlBitmap;
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            try {
                WXWebpageObject wXWebpageObject = new WXWebpageObject();
                wXWebpageObject.webpageUrl = this.$share.getShareJumpLink() + "?inviteSign=" + this.$share.getInviteSign() + "&practiceListId=" + this.$share.getPracticeListId() + "&umChannel=" + AppInformationUtil.getUMChannel();
                WXMediaMessage wXMediaMessage = new WXMediaMessage(wXWebpageObject);
                wXMediaMessage.title = this.$share.getShareTitle();
                wXMediaMessage.description = this.$share.getShareIntroduce();
                String shareIconUrl = this.$share.getShareIconUrl();
                if (shareIconUrl == null || shareIconUrl.length() == 0) {
                    urlBitmap = BitmapFactory.decodeResource(ContextManager.INSTANCE.getInstance().getContext().getResources(), R.drawable.common_share);
                } else {
                    String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(this.$share.getShareIconUrl());
                    LogExtKt.logd("分享图片地址：" + fileFullUrl, YddConfig.TAG);
                    urlBitmap = BitmpUtils.getUrlBitmap(fileFullUrl);
                }
                if (urlBitmap != null) {
                    wXMediaMessage.thumbData = TransformExtKt.toByteArray$default(urlBitmap, (Bitmap.CompressFormat) null, 1, (Object) null);
                }
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = wXMediaMessage;
                req.scene = 0;
                YddUserManager.INSTANCE.getInstance().getmWxApi().sendReq(req);
            } catch (Throwable th) {
                LogExtKt.loge("微信分享异常" + th, YddConfig.TAG);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$httpGetContact$1", f = "MainViewModel.kt", i = {}, l = {202}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$httpGetContact$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08101 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C08101(Continuation<? super C08101> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainViewModel.this.new C08101(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08101) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            MainViewModel mainViewModel;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    MainViewModel mainViewModel2 = MainViewModel.this;
                    YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                    this.L$0 = mainViewModel2;
                    this.label = 1;
                    Object contact = yddClinicRepository.getContact(this);
                    if (contact == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    mainViewModel = mainViewModel2;
                    obj = contact;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    mainViewModel = (MainViewModel) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                mainViewModel.setSkillCall((SkillCall) obj);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$httpGetIntegralApp$1", f = "MainViewModel.kt", i = {}, l = {220}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$httpGetIntegralApp$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08111 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08111(Continuation<? super C08111> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08111(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08111) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                    obj = yddClinicRepository.getIntegralApp(this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                MeProfileIntegral meProfileIntegral = (MeProfileIntegral) obj;
                if (meProfileIntegral != null) {
                    YddUserManager.INSTANCE.getInstance().userIntegralSet(meProfileIntegral.getIntegral());
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$httpGetNoticeList$1", f = "MainViewModel.kt", i = {}, l = {257}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$httpGetNoticeList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08121 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$httpGetNoticeList$1$1", f = "MainViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$httpGetNoticeList$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04261 extends SuspendLambda implements Function3<FlowCollector<? super HomeMsgList>, Throwable, Continuation<? super Unit>, Object> {
            int label;

            public C04261(Continuation<? super C04261> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new C04261(continuation).invokeSuspend(Unit.INSTANCE);
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

        public C08121(Continuation<? super C08121> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainViewModel.this.new C08121(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08121) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flowM2338catch = FlowKt.m2338catch(YddClinicRepository.getNoticeList$default(YddClinicRepository.INSTANCE, Boxing.boxInt(1), Boxing.boxInt(1), 0, 4, null), new C04261(null));
                    final MainViewModel mainViewModel = MainViewModel.this;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.main.MainViewModel.httpGetNoticeList.1.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                            return emit((HomeMsgList) obj2, (Continuation<? super Unit>) continuation);
                        }

                        @Nullable
                        public final Object emit(@Nullable HomeMsgList homeMsgList, @NotNull Continuation<? super Unit> continuation) {
                            if ((homeMsgList != null ? homeMsgList.getRows() : null) != null && !homeMsgList.getRows().isEmpty()) {
                                boolean z2 = false;
                                HomeMsg homeMsg = homeMsgList.getRows().get(0);
                                if (homeMsg != null && homeMsg.isRead() == 0) {
                                    z2 = true;
                                }
                                if (z2 && 1 == homeMsg.getWarnStatus()) {
                                    mainViewModel.setMMsgData(homeMsg);
                                    Object objEmit = mainViewModel.getNoticeMsf().emit(Boxing.boxLong(DateUtil.getTimeInMillisLong()), continuation);
                                    return objEmit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEmit : Unit.INSTANCE;
                                }
                            }
                            return Unit.INSTANCE;
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
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$httpGetSkillBasicHome$1", f = "MainViewModel.kt", i = {}, l = {151, R2.array.ease_excel_file_suffix}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nMainViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MainViewModel.kt\ncom/yddmi/doctor/pages/main/MainViewModel$httpGetSkillBasicHome$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,291:1\n1864#2,2:292\n1855#2,2:294\n1866#2:296\n*S KotlinDebug\n*F\n+ 1 MainViewModel.kt\ncom/yddmi/doctor/pages/main/MainViewModel$httpGetSkillBasicHome$1\n*L\n155#1:292,2\n175#1:294,2\n155#1:296\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$httpGetSkillBasicHome$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08131 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08131(Continuation<? super C08131> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return MainViewModel.this.new C08131(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08131) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object skillBasicHome;
            List<SkillHome> skills;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                } else {
                    MainViewModel.this.setSkillGetError(th.getMessage());
                    MutableStateFlow<Long> getSkillErrorMsf = MainViewModel.this.getGetSkillErrorMsf();
                    Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                    this.label = 2;
                    if (getSkillErrorMsf.emit(lBoxLong, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                this.label = 1;
                skillBasicHome = yddClinicRepository.getSkillBasicHome(this);
                if (skillBasicHome == coroutine_suspended) {
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
                skillBasicHome = obj;
            }
            List list = (List) skillBasicHome;
            List list2 = list;
            if (!(list2 == null || list2.isEmpty())) {
                MainViewModel.this.allDataList = ((SkillHome) list.get(0)).getChildren();
                List list3 = MainViewModel.this.allDataList;
                if (list3 != null) {
                    MainViewModel mainViewModel = MainViewModel.this;
                    int i3 = 0;
                    for (Object obj2 : list3) {
                        int i4 = i3 + 1;
                        if (i3 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        SkillHome skillHome = (SkillHome) obj2;
                        if (i3 == 0) {
                            mainViewModel.getLeftList().clear();
                            skillHome.setMSelected(true);
                        }
                        mainViewModel.getLeftList().add(new SkillHome(skillHome.getIcon(), skillHome.getId(), skillHome.getIntroduce(), skillHome.isPlay(), skillHome.getLockStatus(), skillHome.getName(), skillHome.getPid(), (String) null, skillHome.getSkillCategoryId(), 0L, (String) null, (String) null, (String) null, (String) null, (String) null, (Float) null, (String) null, (String) null, (List) null, (String) null, (String) null, (String) null, (BatchInfo) null, 0, (String) null, (List) null, (List) null, 0.0f, (List) null, (String) null, (SkillTicket) null, skillHome.getLabel(), 0, 0, (List) null, skillHome.getMSelected(), 0, 0, 0, 2147483264, 119, (DefaultConstructorMarker) null));
                        if (Intrinsics.areEqual("-1", mainViewModel.getNewId()) && (skills = skillHome.getSkills()) != null) {
                            for (SkillHome skillHome2 : skills) {
                                if (Intrinsics.areEqual(skillHome2.getUrl(), "LP")) {
                                    String id = skillHome2.getId();
                                    if (id == null) {
                                        id = "";
                                    }
                                    mainViewModel.setNewId(id);
                                }
                            }
                        }
                        i3 = i4;
                    }
                }
            }
            MainViewModel.this.getLeftListMsf().setValue(Boxing.boxLong(DateUtil.getTimeInMillisLong()));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1", f = "MainViewModel.kt", i = {}, l = {R2.attr.adjustable, R2.attr.alertDialogCenterButtons}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08141 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$1", f = "MainViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04271 extends SuspendLambda implements Function3<FlowCollector<? super String>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            public C04271(Continuation<? super C04271> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                C04271 c04271 = new C04271(continuation);
                c04271.L$0 = th;
                return c04271.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                LogExtKt.logw(String.valueOf(((Throwable) this.L$0).getMessage()), YddConfig.TAG);
                return Unit.INSTANCE;
            }
        }

        public C08141(Continuation<? super C08141> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08141(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08141) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0057 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r5) {
            /*
                r4 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r4.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r5)
                goto L58
            L12:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r0)
                throw r5
            L1a:
                kotlin.ResultKt.throwOnFailure(r5)
                goto L41
            L1e:
                kotlin.ResultKt.throwOnFailure(r5)
                com.yddmi.doctor.config.YddPointManager$Companion r5 = com.yddmi.doctor.config.YddPointManager.INSTANCE
                com.yddmi.doctor.config.YddPointManager r5 = r5.getInstance()
                com.yddmi.doctor.entity.request.PointSaveReq r5 = r5.getPointSave()
                if (r5 != 0) goto L36
                java.lang.String r5 = "暂无埋点数据"
                java.lang.String r0 = "h0xta"
                com.catchpig.utils.ext.LogExtKt.logd(r5, r0)
                goto L58
            L36:
                com.yddmi.doctor.repository.YddClinicRepository r1 = com.yddmi.doctor.repository.YddClinicRepository.INSTANCE
                r4.label = r3
                java.lang.Object r5 = r1.postPointSave(r5, r4)
                if (r5 != r0) goto L41
                return r0
            L41:
                kotlinx.coroutines.flow.Flow r5 = (kotlinx.coroutines.flow.Flow) r5
                com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$1 r1 = new com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$1
                r3 = 0
                r1.<init>(r3)
                kotlinx.coroutines.flow.Flow r5 = kotlinx.coroutines.flow.FlowKt.m2338catch(r5, r1)
                com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$2<T> r1 = new kotlinx.coroutines.flow.FlowCollector() { // from class: com.yddmi.doctor.pages.main.MainViewModel.httpPointSave.1.2
                    static {
                        /*
                            com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$2 r0 = new com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$2
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$2<T>) com.yddmi.doctor.pages.main.MainViewModel.httpPointSave.1.2.INSTANCE com.yddmi.doctor.pages.main.MainViewModel$httpPointSave$1$2
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.main.MainViewModel.C08141.AnonymousClass2.<clinit>():void");
                    }

                    {
                        /*
                            r0 = this;
                            r0.<init>()
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.main.MainViewModel.C08141.AnonymousClass2.<init>():void");
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ java.lang.Object emit(java.lang.Object r1, kotlin.coroutines.Continuation r2) {
                        /*
                            r0 = this;
                            java.lang.String r1 = (java.lang.String) r1
                            java.lang.Object r1 = r0.emit(r1, r2)
                            return r1
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.main.MainViewModel.C08141.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }

                    @org.jetbrains.annotations.Nullable
                    public final java.lang.Object emit(@org.jetbrains.annotations.Nullable java.lang.String r1, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super kotlin.Unit> r2) {
                        /*
                            r0 = this;
                            com.yddmi.doctor.config.YddPointManager$Companion r1 = com.yddmi.doctor.config.YddPointManager.INSTANCE
                            com.yddmi.doctor.config.YddPointManager r1 = r1.getInstance()
                            r1.clearOperationList()
                            kotlin.Unit r1 = kotlin.Unit.INSTANCE
                            return r1
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.main.MainViewModel.C08141.AnonymousClass2.emit(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }
                r4.label = r2
                java.lang.Object r5 = r5.collect(r1, r4)
                if (r5 != r0) goto L58
                return r0
            L58:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.main.MainViewModel.C08141.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void changeLeftIndex(int index) {
        this.leftLastIndex = index;
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(index, this, null), 3, null);
    }

    public final void dealChangeLabel(@Nullable List<SkillHome> data) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08071(data, null), 3, null);
    }

    public final void dealNoticeRead(@NotNull HomeMsg data) {
        Intrinsics.checkNotNullParameter(data, "data");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08081(data, null), 3, null);
    }

    public final void dealWxShare(@NotNull SkillShare share) {
        Intrinsics.checkNotNullParameter(share, "share");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C08091(share, null), 2, null);
    }

    @Nullable
    public final SkillHome getFirstSkill() {
        return this.firstSkill;
    }

    @NotNull
    public final MutableStateFlow<Long> getGetLabelMsf() {
        return this.getLabelMsf;
    }

    @NotNull
    public final MutableStateFlow<Long> getGetSkillErrorMsf() {
        return this.getSkillErrorMsf;
    }

    @Nullable
    public final List<SkillHome> getLabelList() {
        return this.labelList;
    }

    public final int getLeftLastIndex() {
        return this.leftLastIndex;
    }

    @NotNull
    public final List<SkillHome> getLeftList() {
        return this.leftList;
    }

    @NotNull
    public final MutableStateFlow<Long> getLeftListMsf() {
        return this.leftListMsf;
    }

    @Nullable
    public final HomeMsg getMMsgData() {
        return this.mMsgData;
    }

    @Nullable
    public final String getMRuleData() {
        return this.mRuleData;
    }

    @NotNull
    public final String getNewId() {
        return this.newId;
    }

    @NotNull
    public final String getNewUrl() {
        return this.newUrl;
    }

    @NotNull
    public final MutableStateFlow<Long> getNoticeMsf() {
        return this.noticeMsf;
    }

    @Nullable
    public final List<SkillHome> getRightList() {
        return this.rightList;
    }

    @NotNull
    public final MutableStateFlow<Long> getRightListChange() {
        return this.rightListChange;
    }

    @Nullable
    public final SkillCall getSkillCall() {
        return this.skillCall;
    }

    @Nullable
    public final String getSkillGetError() {
        return this.skillGetError;
    }

    public final void httpGetContact() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08101(null), 3, null);
    }

    public final void httpGetIntegralApp() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08111(null), 3, null);
        }
    }

    public final void httpGetNoticeList() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08121(null), 3, null);
        }
    }

    public final void httpGetSkillBasicHome() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08131(null), 3, null);
    }

    public final void httpPointSave() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08141(null), 3, null);
    }

    public final void setFirstSkill(@Nullable SkillHome skillHome) {
        this.firstSkill = skillHome;
    }

    public final void setLabelList(@Nullable List<SkillHome> list) {
        this.labelList = list;
    }

    public final void setLeftLastIndex(int i2) {
        this.leftLastIndex = i2;
    }

    public final void setMMsgData(@Nullable HomeMsg homeMsg) {
        this.mMsgData = homeMsg;
    }

    public final void setMRuleData(@Nullable String str) {
        this.mRuleData = str;
    }

    public final void setNewId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.newId = str;
    }

    public final void setRightList(@Nullable List<SkillHome> list) {
        this.rightList = list;
    }

    public final void setSkillCall(@Nullable SkillCall skillCall) {
        this.skillCall = skillCall;
    }

    public final void setSkillGetError(@Nullable String str) {
        this.skillGetError = str;
    }
}
