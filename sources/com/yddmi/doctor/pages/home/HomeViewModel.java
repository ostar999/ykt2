package com.yddmi.doctor.pages.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.lifecycle.ViewModelKt;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.AppInformationUtil;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.TransformExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.hjq.toast.Toaster;
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
import com.yddmi.doctor.entity.result.AuthLoginResult1;
import com.yddmi.doctor.entity.result.HomeMsg;
import com.yddmi.doctor.entity.result.HomeMsgList;
import com.yddmi.doctor.entity.result.MeProfile;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.BitmpUtils;
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
import kotlin.jvm.internal.Intrinsics;
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

@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u000fJ\u0006\u00102\u001a\u000200J\u000e\u00103\u001a\u0002002\u0006\u00104\u001a\u000205J\u000e\u00106\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010807J\u0016\u00109\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u0018072\u0006\u0010:\u001a\u00020\u0018J\u0006\u0010;\u001a\u000200J\u0006\u0010<\u001a\u000200J\u0006\u0010=\u001a\u000200J\u0006\u0010>\u001a\u000200J\u0006\u0010?\u001a\u000200J\u0006\u0010@\u001a\u000200J\u0006\u0010A\u001a\u000200J\u0006\u0010B\u001a\u000200J\u0006\u0010C\u001a\u000200J$\u0010D\u001a\b\u0012\u0004\u0012\u00020E072\u0006\u0010:\u001a\u00020\u00182\u0006\u0010F\u001a\u00020\u00182\u0006\u0010G\u001a\u00020\u0018R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0011\"\u0004\b\u001f\u0010\u0013R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\rR\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\rR\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001c\u0010*\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u001a\"\u0004\b,\u0010\u001cR\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\r¨\u0006H"}, d2 = {"Lcom/yddmi/doctor/pages/home/HomeViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "firstSkill", "Lcom/yddmi/doctor/entity/result/SkillHome;", "getFirstSkill", "()Lcom/yddmi/doctor/entity/result/SkillHome;", "setFirstSkill", "(Lcom/yddmi/doctor/entity/result/SkillHome;)V", "integralMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getIntegralMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "mMsgData", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "getMMsgData", "()Lcom/yddmi/doctor/entity/result/HomeMsg;", "setMMsgData", "(Lcom/yddmi/doctor/entity/result/HomeMsg;)V", "mRecommendMsgData", "getMRecommendMsgData", "setMRecommendMsgData", "mRuleData", "", "getMRuleData", "()Ljava/lang/String;", "setMRuleData", "(Ljava/lang/String;)V", "mShopMsg", "getMShopMsg", "setMShopMsg", "noticeMsf", "getNoticeMsf", "recommendMsf", "getRecommendMsf", "skillCall", "Lcom/yddmi/doctor/entity/result/SkillCall;", "getSkillCall", "()Lcom/yddmi/doctor/entity/result/SkillCall;", "setSkillCall", "(Lcom/yddmi/doctor/entity/result/SkillCall;)V", "skillGetError", "getSkillGetError", "setSkillGetError", "whiteCallMsf", "getWhiteCallMsf", "dealNoticeRead", "", "data", "dealTpushUserUnBind", "dealWxShare", "share", "Lcom/yddmi/doctor/entity/result/SkillShare;", "getPersonInfo", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/MeProfile;", "getPushCodeRegister", AliyunLogCommon.TERMINAL_TYPE, "httpGetAppGeneralConfig", "httpGetConfigWhite", "httpGetContact", "httpGetIntegralApp", "httpGetNoticeList", "httpGetPopUserList", "httpGetRandomSkill", "httpGetUserSpecificCoupon", "httpPointSave", "postInviteLogin", "Lcom/yddmi/doctor/entity/result/AuthLoginResult1;", "code", "inviteSign", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class HomeViewModel extends BaseViewModel {

    @Nullable
    private SkillHome firstSkill;

    @Nullable
    private HomeMsg mMsgData;

    @Nullable
    private HomeMsg mRecommendMsgData;

    @Nullable
    private HomeMsg mShopMsg;

    @Nullable
    private SkillCall skillCall;

    @Nullable
    private String skillGetError = "";

    @Nullable
    private String mRuleData = "";

    @NotNull
    private final MutableStateFlow<Long> integralMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> noticeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> recommendMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> whiteCallMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$dealNoticeRead$1", f = "HomeViewModel.kt", i = {}, l = {234}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$dealNoticeRead$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ HomeMsg $data;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(HomeMsg homeMsg, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$data = homeMsg;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$data, continuation);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$dealWxShare$1", f = "HomeViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$dealWxShare$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07291 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SkillShare $share;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07291(SkillShare skillShare, Continuation<? super C07291> continuation) {
            super(2, continuation);
            this.$share = skillShare;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C07291(this.$share, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                wXWebpageObject.webpageUrl = this.$share.getShareJumpLink() + "?inviteSign=" + this.$share.getInviteSign() + "&practiceListId=" + this.$share.getPracticeListId();
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetAppGeneralConfig$1", f = "HomeViewModel.kt", i = {}, l = {R2.anim.welcome_loading}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetAppGeneralConfig$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07301 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07301(Continuation<? super C07301> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeViewModel.this.new C07301(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07301) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                    obj = YddClinicRepository.getAppGeneralConfig$default(yddClinicRepository, 3, 0, this, 2, null);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                HomeMsg homeMsg = (HomeMsg) obj;
                LogExtKt.logd("商店广告图地址：" + (homeMsg != null ? homeMsg.getFileUrl() : null), YddConfig.TAG);
                if (homeMsg != null) {
                    homeMsg.setCoverUrl(homeMsg.getFileUrl());
                    homeMsg.setSkipWay("1");
                    homeMsg.setSkipUrl("1");
                    homeMsg.setType(102);
                    homeMsg.setMHalfClick(true);
                    HomeViewModel.this.setMShopMsg(homeMsg);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetConfigWhite$1", f = "HomeViewModel.kt", i = {}, l = {R2.attr.arcEnabledSize, R2.attr.arcMode}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetConfigWhite$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07311 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07311(Continuation<? super C07311> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeViewModel.this.new C07311(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07311) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
            MutableStateFlow<Long> whiteCallMsf = HomeViewModel.this.getWhiteCallMsf();
            Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (whiteCallMsf.emit(lBoxLong, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetContact$1", f = "HomeViewModel.kt", i = {}, l = {117}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetContact$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07321 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C07321(Continuation<? super C07321> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeViewModel.this.new C07321(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07321) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            HomeViewModel homeViewModel;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    HomeViewModel homeViewModel2 = HomeViewModel.this;
                    YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                    this.L$0 = homeViewModel2;
                    this.label = 1;
                    Object contact = yddClinicRepository.getContact(this);
                    if (contact == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    homeViewModel = homeViewModel2;
                    obj = contact;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    homeViewModel = (HomeViewModel) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                homeViewModel.setSkillCall((SkillCall) obj);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetIntegralApp$1", f = "HomeViewModel.kt", i = {}, l = {133, 138, 144}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetIntegralApp$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07331 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07331(Continuation<? super C07331> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeViewModel.this.new C07331(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0069 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 3
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L28
                if (r1 == r4) goto L22
                if (r1 == r3) goto L1e
                if (r1 != r2) goto L16
                kotlin.ResultKt.throwOnFailure(r7)
                goto L93
            L16:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L26
                goto L93
            L22:
                kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L26
                goto L42
            L26:
                r7 = move-exception
                goto L6a
            L28:
                kotlin.ResultKt.throwOnFailure(r7)
                com.yddmi.doctor.config.YddUserManager$Companion r7 = com.yddmi.doctor.config.YddUserManager.INSTANCE     // Catch: java.lang.Throwable -> L26
                com.yddmi.doctor.config.YddUserManager r7 = r7.getInstance()     // Catch: java.lang.Throwable -> L26
                boolean r7 = r7.userIsLogin()     // Catch: java.lang.Throwable -> L26
                if (r7 == 0) goto L53
                com.yddmi.doctor.repository.YddClinicRepository r7 = com.yddmi.doctor.repository.YddClinicRepository.INSTANCE     // Catch: java.lang.Throwable -> L26
                r6.label = r4     // Catch: java.lang.Throwable -> L26
                java.lang.Object r7 = r7.getIntegralApp(r6)     // Catch: java.lang.Throwable -> L26
                if (r7 != r0) goto L42
                return r0
            L42:
                com.yddmi.doctor.entity.result.MeProfileIntegral r7 = (com.yddmi.doctor.entity.result.MeProfileIntegral) r7     // Catch: java.lang.Throwable -> L26
                if (r7 == 0) goto L53
                com.yddmi.doctor.config.YddUserManager$Companion r1 = com.yddmi.doctor.config.YddUserManager.INSTANCE     // Catch: java.lang.Throwable -> L26
                com.yddmi.doctor.config.YddUserManager r1 = r1.getInstance()     // Catch: java.lang.Throwable -> L26
                int r7 = r7.getIntegral()     // Catch: java.lang.Throwable -> L26
                r1.userIntegralSet(r7)     // Catch: java.lang.Throwable -> L26
            L53:
                com.yddmi.doctor.pages.home.HomeViewModel r7 = com.yddmi.doctor.pages.home.HomeViewModel.this     // Catch: java.lang.Throwable -> L26
                kotlinx.coroutines.flow.MutableStateFlow r7 = r7.getIntegralMsf()     // Catch: java.lang.Throwable -> L26
                long r4 = com.catchpig.mvvm.utils.DateUtil.getTimeInMillisLong()     // Catch: java.lang.Throwable -> L26
                java.lang.Long r1 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r4)     // Catch: java.lang.Throwable -> L26
                r6.label = r3     // Catch: java.lang.Throwable -> L26
                java.lang.Object r7 = r7.emit(r1, r6)     // Catch: java.lang.Throwable -> L26
                if (r7 != r0) goto L93
                return r0
            L6a:
                java.lang.String r1 = java.lang.String.valueOf(r7)
                java.lang.String r3 = "h0xta"
                com.catchpig.utils.ext.LogExtKt.loge(r1, r3)
                boolean r7 = r7 instanceof com.yddmi.doctor.exception.HttpLogout401Exception
                if (r7 == 0) goto L7c
                java.lang.String r7 = "eventLogout401"
                com.blankj.utilcode.util.BusUtils.post(r7)
            L7c:
                com.yddmi.doctor.pages.home.HomeViewModel r7 = com.yddmi.doctor.pages.home.HomeViewModel.this
                kotlinx.coroutines.flow.MutableStateFlow r7 = r7.getIntegralMsf()
                long r3 = com.catchpig.mvvm.utils.DateUtil.getTimeInMillisLong()
                java.lang.Long r1 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r3)
                r6.label = r2
                java.lang.Object r7 = r7.emit(r1, r6)
                if (r7 != r0) goto L93
                return r0
            L93:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeViewModel.C07331.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetNoticeList$1", f = "HomeViewModel.kt", i = {}, l = {250}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetNoticeList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07341 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/yddmi/doctor/entity/result/HomeMsgList;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetNoticeList$1$1", f = "HomeViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetNoticeList$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04201 extends SuspendLambda implements Function3<FlowCollector<? super HomeMsgList>, Throwable, Continuation<? super Unit>, Object> {
            int label;

            public C04201(Continuation<? super C04201> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super HomeMsgList> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                return new C04201(continuation).invokeSuspend(Unit.INSTANCE);
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

        public C07341(Continuation<? super C07341> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeViewModel.this.new C07341(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07341) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flowM2338catch = FlowKt.m2338catch(YddClinicRepository.getNoticeList$default(YddClinicRepository.INSTANCE, Boxing.boxInt(1), Boxing.boxInt(1), 0, 4, null), new C04201(null));
                    final HomeViewModel homeViewModel = HomeViewModel.this;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.yddmi.doctor.pages.home.HomeViewModel.httpGetNoticeList.1.2
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
                                    homeViewModel.setMMsgData(homeMsg);
                                    Object objEmit = homeViewModel.getNoticeMsf().emit(Boxing.boxLong(DateUtil.getTimeInMillisLong()), continuation);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetPopUserList$1", f = "HomeViewModel.kt", i = {}, l = {273, R2.attr.ad_width, R2.attr.alertDialogStyle}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetPopUserList$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07351 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C07351(Continuation<? super C07351> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeViewModel.this.new C07351(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07351) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            boolean z2 = true;
            try {
            } catch (Throwable th) {
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                }
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                MutableStateFlow<Long> recommendMsf = HomeViewModel.this.getRecommendMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.label = 3;
                if (recommendMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                this.label = 1;
                obj = yddClinicRepository.getPopUserList(this);
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
            List list = (List) obj;
            List list2 = list;
            if (list2 != null && !list2.isEmpty()) {
                z2 = false;
            }
            if (z2) {
                HomeViewModel.this.setMRecommendMsgData(null);
            } else {
                HomeViewModel.this.setMRecommendMsgData((HomeMsg) list.get(0));
            }
            MutableStateFlow<Long> recommendMsf2 = HomeViewModel.this.getRecommendMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (recommendMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetRandomSkill$1", f = "HomeViewModel.kt", i = {}, l = {156}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetRandomSkill$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07361 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C07361(Continuation<? super C07361> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HomeViewModel.this.new C07361(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07361) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            HomeViewModel homeViewModel;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            boolean z2 = true;
            try {
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    HomeViewModel homeViewModel2 = HomeViewModel.this;
                    YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                    this.L$0 = homeViewModel2;
                    this.label = 1;
                    Object randomSkill = yddClinicRepository.getRandomSkill(this);
                    if (randomSkill == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    homeViewModel = homeViewModel2;
                    obj = randomSkill;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    homeViewModel = (HomeViewModel) this.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                homeViewModel.setFirstSkill((SkillHome) obj);
                if (HomeViewModel.this.getFirstSkill() != null) {
                    z2 = false;
                }
                LogExtKt.logd("随机获取一个技能是否为null:" + z2, YddConfig.TAG);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpGetUserSpecificCoupon$1", f = "HomeViewModel.kt", i = {1}, l = {R2.array.ease_pages_file_suffix, R2.array.ease_pdf_file_suffix}, m = "invokeSuspend", n = {"ad1"}, s = {"L$0"})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpGetUserSpecificCoupon$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C07371(Continuation<? super C07371> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C07371(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x0048  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0049 A[Catch: all -> 0x0022, TRY_LEAVE, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0055 A[Catch: all -> 0x0022, TRY_ENTER, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:32:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x005b A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0063 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x0069 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0073 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0078  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0079 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x007f A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0084 A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:53:0x0091 A[Catch: all -> 0x0022, TRY_LEAVE, TryCatch #0 {all -> 0x0022, blocks: (B:7:0x0012, B:23:0x0044, B:30:0x0055, B:51:0x0084, B:53:0x0091, B:49:0x007f, B:46:0x0079, B:43:0x0073, B:40:0x0069, B:37:0x0063, B:33:0x005b, B:26:0x0049, B:11:0x001e, B:19:0x0033, B:16:0x0028), top: B:58:0x0008 }] */
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
                if (r1 == 0) goto L25
                if (r1 == r3) goto L1e
                if (r1 != r2) goto L16
                java.lang.Object r0 = r4.L$0
                com.yddmi.doctor.entity.result.HomeMsg r0 = (com.yddmi.doctor.entity.result.HomeMsg) r0
                kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L22
                goto L44
            L16:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r0)
                throw r5
            L1e:
                kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L22
                goto L33
            L22:
                r5 = move-exception
                goto L9d
            L25:
                kotlin.ResultKt.throwOnFailure(r5)
                com.yddmi.doctor.repository.YddClinicRepository r5 = com.yddmi.doctor.repository.YddClinicRepository.INSTANCE     // Catch: java.lang.Throwable -> L22
                r4.label = r3     // Catch: java.lang.Throwable -> L22
                java.lang.Object r5 = r5.getUserSpecificCoupon(r3, r4)     // Catch: java.lang.Throwable -> L22
                if (r5 != r0) goto L33
                return r0
            L33:
                com.yddmi.doctor.entity.result.HomeMsg r5 = (com.yddmi.doctor.entity.result.HomeMsg) r5     // Catch: java.lang.Throwable -> L22
                com.yddmi.doctor.repository.YddClinicRepository r1 = com.yddmi.doctor.repository.YddClinicRepository.INSTANCE     // Catch: java.lang.Throwable -> L22
                r4.L$0 = r5     // Catch: java.lang.Throwable -> L22
                r4.label = r2     // Catch: java.lang.Throwable -> L22
                java.lang.Object r1 = r1.getUserSpecificCoupon(r2, r4)     // Catch: java.lang.Throwable -> L22
                if (r1 != r0) goto L42
                return r0
            L42:
                r0 = r5
                r5 = r1
            L44:
                com.yddmi.doctor.entity.result.HomeMsg r5 = (com.yddmi.doctor.entity.result.HomeMsg) r5     // Catch: java.lang.Throwable -> L22
                if (r0 != 0) goto L49
                goto L50
            L49:
                java.lang.String r1 = r0.getShowPic()     // Catch: java.lang.Throwable -> L22
                r0.setCoverUrl(r1)     // Catch: java.lang.Throwable -> L22
            L50:
                java.lang.String r1 = "1"
                if (r0 != 0) goto L55
                goto L58
            L55:
                r0.setSkipWay(r1)     // Catch: java.lang.Throwable -> L22
            L58:
                if (r0 != 0) goto L5b
                goto L5e
            L5b:
                r0.setSkipUrl(r1)     // Catch: java.lang.Throwable -> L22
            L5e:
                r2 = 102(0x66, float:1.43E-43)
                if (r0 != 0) goto L63
                goto L66
            L63:
                r0.setType(r2)     // Catch: java.lang.Throwable -> L22
            L66:
                if (r5 != 0) goto L69
                goto L70
            L69:
                java.lang.String r3 = r5.getShowPic()     // Catch: java.lang.Throwable -> L22
                r5.setCoverUrl(r3)     // Catch: java.lang.Throwable -> L22
            L70:
                if (r5 != 0) goto L73
                goto L76
            L73:
                r5.setSkipWay(r1)     // Catch: java.lang.Throwable -> L22
            L76:
                if (r5 != 0) goto L79
                goto L7c
            L79:
                r5.setSkipUrl(r1)     // Catch: java.lang.Throwable -> L22
            L7c:
                if (r5 != 0) goto L7f
                goto L82
            L7f:
                r5.setType(r2)     // Catch: java.lang.Throwable -> L22
            L82:
                if (r0 == 0) goto L8f
                com.yddmi.doctor.config.YddConfig r1 = com.yddmi.doctor.config.YddConfig.INSTANCE     // Catch: java.lang.Throwable -> L22
                java.util.concurrent.ConcurrentHashMap r1 = r1.getKvData()     // Catch: java.lang.Throwable -> L22
                java.lang.String r2 = "kvAdFirstBuy"
                r1.put(r2, r0)     // Catch: java.lang.Throwable -> L22
            L8f:
                if (r5 == 0) goto La6
                com.yddmi.doctor.config.YddConfig r0 = com.yddmi.doctor.config.YddConfig.INSTANCE     // Catch: java.lang.Throwable -> L22
                java.util.concurrent.ConcurrentHashMap r0 = r0.getKvData()     // Catch: java.lang.Throwable -> L22
                java.lang.String r1 = "kvAdCancelBuy"
                r0.put(r1, r5)     // Catch: java.lang.Throwable -> L22
                goto La6
            L9d:
                java.lang.String r5 = java.lang.String.valueOf(r5)
                java.lang.String r0 = "h0xta"
                com.catchpig.utils.ext.LogExtKt.loge(r5, r0)
            La6:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeViewModel.C07371.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1", f = "HomeViewModel.kt", i = {}, l = {R2.attr.app_theme_red_color, R2.attr.app_update_top_img}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1, reason: invalid class name and case insensitive filesystem */
    public static final class C07381 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$1", f = "HomeViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C04211 extends SuspendLambda implements Function3<FlowCollector<? super String>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            public C04211(Continuation<? super C04211> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            @Nullable
            public final Object invoke(@NotNull FlowCollector<? super String> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
                C04211 c04211 = new C04211(continuation);
                c04211.L$0 = th;
                return c04211.invokeSuspend(Unit.INSTANCE);
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

        public C07381(Continuation<? super C07381> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C07381(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C07381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$1 r1 = new com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$1
                r3 = 0
                r1.<init>(r3)
                kotlinx.coroutines.flow.Flow r5 = kotlinx.coroutines.flow.FlowKt.m2338catch(r5, r1)
                com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$2<T> r1 = new kotlinx.coroutines.flow.FlowCollector() { // from class: com.yddmi.doctor.pages.home.HomeViewModel.httpPointSave.1.2
                    static {
                        /*
                            com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$2 r0 = new com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$2
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$2<T>) com.yddmi.doctor.pages.home.HomeViewModel.httpPointSave.1.2.INSTANCE com.yddmi.doctor.pages.home.HomeViewModel$httpPointSave$1$2
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeViewModel.C07381.AnonymousClass2.<clinit>():void");
                    }

                    {
                        /*
                            r0 = this;
                            r0.<init>()
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeViewModel.C07381.AnonymousClass2.<init>():void");
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ java.lang.Object emit(java.lang.Object r1, kotlin.coroutines.Continuation r2) {
                        /*
                            r0 = this;
                            java.lang.String r1 = (java.lang.String) r1
                            java.lang.Object r1 = r0.emit(r1, r2)
                            return r1
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeViewModel.C07381.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
                        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeViewModel.C07381.AnonymousClass2.emit(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
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
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.home.HomeViewModel.C07381.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void dealNoticeRead(@NotNull HomeMsg data) {
        Intrinsics.checkNotNullParameter(data, "data");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(data, null), 3, null);
    }

    public final void dealTpushUserUnBind() {
    }

    public final void dealWxShare(@NotNull SkillShare share) {
        Intrinsics.checkNotNullParameter(share, "share");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C07291(share, null), 2, null);
    }

    @Nullable
    public final SkillHome getFirstSkill() {
        return this.firstSkill;
    }

    @NotNull
    public final MutableStateFlow<Long> getIntegralMsf() {
        return this.integralMsf;
    }

    @Nullable
    public final HomeMsg getMMsgData() {
        return this.mMsgData;
    }

    @Nullable
    public final HomeMsg getMRecommendMsgData() {
        return this.mRecommendMsgData;
    }

    @Nullable
    public final String getMRuleData() {
        return this.mRuleData;
    }

    @Nullable
    public final HomeMsg getMShopMsg() {
        return this.mShopMsg;
    }

    @NotNull
    public final MutableStateFlow<Long> getNoticeMsf() {
        return this.noticeMsf;
    }

    @NotNull
    public final Flow<MeProfile> getPersonInfo() {
        return YddClinicRepository.INSTANCE.getPersonInfoApp();
    }

    @NotNull
    public final Flow<String> getPushCodeRegister(@NotNull String phone) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        return YddClinicRepository.INSTANCE.getPushCodeRegister(phone);
    }

    @NotNull
    public final MutableStateFlow<Long> getRecommendMsf() {
        return this.recommendMsf;
    }

    @Nullable
    public final SkillCall getSkillCall() {
        return this.skillCall;
    }

    @Nullable
    public final String getSkillGetError() {
        return this.skillGetError;
    }

    @NotNull
    public final MutableStateFlow<Long> getWhiteCallMsf() {
        return this.whiteCallMsf;
    }

    public final void httpGetAppGeneralConfig() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07301(null), 3, null);
    }

    public final void httpGetConfigWhite() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07311(null), 3, null);
    }

    public final void httpGetContact() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07321(null), 3, null);
    }

    public final void httpGetIntegralApp() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07331(null), 3, null);
    }

    public final void httpGetNoticeList() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07341(null), 3, null);
        }
    }

    public final void httpGetPopUserList() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07351(null), 3, null);
        }
    }

    public final void httpGetRandomSkill() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07361(null), 3, null);
        }
    }

    public final void httpGetUserSpecificCoupon() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07371(null), 3, null);
        }
    }

    public final void httpPointSave() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C07381(null), 3, null);
        } else {
            LogExtKt.logd("未登录，暂不上报埋点", YddConfig.TAG);
        }
    }

    @NotNull
    public final Flow<AuthLoginResult1> postInviteLogin(@NotNull String phone, @NotNull String code, @NotNull String inviteSign) {
        Intrinsics.checkNotNullParameter(phone, "phone");
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(inviteSign, "inviteSign");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(AliyunLogCommon.TERMINAL_TYPE, phone);
        linkedHashMap.put("code", code);
        linkedHashMap.put("phoneSystem", "android");
        YddConfig yddConfig = YddConfig.INSTANCE;
        linkedHashMap.put("oaId", yddConfig.getAndroidInfo(100));
        linkedHashMap.put("mobileDeviceId", yddConfig.getAndroidInfo(100));
        String BRAND = Build.BRAND;
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("mobileBrand", BRAND);
        String MODEL = Build.MODEL;
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("mobileModel", MODEL);
        String RELEASE = Build.VERSION.RELEASE;
        Intrinsics.checkNotNullExpressionValue(RELEASE, "RELEASE");
        linkedHashMap.put("mobileVersion", RELEASE);
        linkedHashMap.put("inviteSign", inviteSign);
        Intrinsics.checkNotNullExpressionValue(BRAND, "BRAND");
        linkedHashMap.put("brandName", BRAND);
        linkedHashMap.put("operatingSystem", AppInformationUtil.isHarmonyOS() ? YddConfig.harmonyOs : "android");
        linkedHashMap.put("deviceType", "2");
        Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
        linkedHashMap.put("modelName", MODEL);
        String DEVICE = Build.DEVICE;
        Intrinsics.checkNotNullExpressionValue(DEVICE, "DEVICE");
        linkedHashMap.put("modelCode", DEVICE);
        if (AppInformationUtil.isApkDebugable()) {
            Toaster.showLong((CharSequence) ("首页邀请码登录：" + inviteSign));
        }
        return YddClinicRepository.INSTANCE.postInviteLogin(linkedHashMap);
    }

    public final void setFirstSkill(@Nullable SkillHome skillHome) {
        this.firstSkill = skillHome;
    }

    public final void setMMsgData(@Nullable HomeMsg homeMsg) {
        this.mMsgData = homeMsg;
    }

    public final void setMRecommendMsgData(@Nullable HomeMsg homeMsg) {
        this.mRecommendMsgData = homeMsg;
    }

    public final void setMRuleData(@Nullable String str) {
        this.mRuleData = str;
    }

    public final void setMShopMsg(@Nullable HomeMsg homeMsg) {
        this.mShopMsg = homeMsg;
    }

    public final void setSkillCall(@Nullable SkillCall skillCall) {
        this.skillCall = skillCall;
    }

    public final void setSkillGetError(@Nullable String str) {
        this.skillGetError = str;
    }
}
