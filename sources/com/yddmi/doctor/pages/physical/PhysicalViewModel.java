package com.yddmi.doctor.pages.physical;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.lifecycle.ViewModelKt;
import cn.hutool.core.text.StrPool;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.TransformExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.result.BatchInfo;
import com.yddmi.doctor.entity.result.MeProfileIntegral;
import com.yddmi.doctor.entity.result.SkillCall;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillShare;
import com.yddmi.doctor.entity.result.SkillTicket;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
import com.yddmi.doctor.utils.BitmpUtils;
import java.util.ArrayList;
import java.util.Iterator;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u0019J\u0016\u00103\u001a\u0002012\u000e\u00104\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004J\u0018\u00105\u001a\u0002012\u000e\u00104\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004H\u0002J\u000e\u00106\u001a\u0002012\u0006\u00107\u001a\u000208J\u0006\u00109\u001a\u000201J\u0006\u0010:\u001a\u000201R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\"\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0010R\"\u0010\"\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0015\"\u0004\b$\u0010\u0017R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0010R\u001c\u0010'\u001a\u0004\u0018\u00010(X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010-\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\t\"\u0004\b/\u0010\u000b¨\u0006;"}, d2 = {"Lcom/yddmi/doctor/pages/physical/PhysicalViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "allDataList", "", "Lcom/yddmi/doctor/entity/result/SkillHome;", "code", "", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", "getLabelMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getGetLabelMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "getSkillErrorMsf", "getGetSkillErrorMsf", "labelList", "getLabelList", "()Ljava/util/List;", "setLabelList", "(Ljava/util/List;)V", "leftLastIndex", "", "getLeftLastIndex", "()I", "setLeftLastIndex", "(I)V", "leftList", "getLeftList", "leftListMsf", "getLeftListMsf", "rightList", "getRightList", "setRightList", "rightListChange", "getRightListChange", "skillCall", "Lcom/yddmi/doctor/entity/result/SkillCall;", "getSkillCall", "()Lcom/yddmi/doctor/entity/result/SkillCall;", "setSkillCall", "(Lcom/yddmi/doctor/entity/result/SkillCall;)V", "skillGetError", "getSkillGetError", "setSkillGetError", "changeLeftIndex", "", "index", "dealChangeLabel", "data", "dealUnLock", "dealWxShare", "share", "Lcom/yddmi/doctor/entity/result/SkillShare;", "httpGetIntegralApp", "httpGetSkillBodyHome", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPhysicalViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PhysicalViewModel.kt\ncom/yddmi/doctor/pages/physical/PhysicalViewModel\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,205:1\n1855#2:206\n1855#2,2:207\n1856#2:209\n*S KotlinDebug\n*F\n+ 1 PhysicalViewModel.kt\ncom/yddmi/doctor/pages/physical/PhysicalViewModel\n*L\n135#1:206\n136#1:207,2\n135#1:209\n*E\n"})
/* loaded from: classes6.dex */
public final class PhysicalViewModel extends BaseViewModel {

    @Nullable
    private List<SkillHome> allDataList;

    @Nullable
    private List<SkillHome> labelList;
    private int leftLastIndex;

    @Nullable
    private List<SkillHome> rightList;

    @Nullable
    private SkillCall skillCall;

    @NotNull
    private final List<SkillHome> leftList = new ArrayList();

    @Nullable
    private String skillGetError = "";

    @NotNull
    private String code = "TGJC";

    @NotNull
    private final MutableStateFlow<Long> leftListMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> rightListChange = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> getSkillErrorMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> getLabelMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalViewModel$changeLeftIndex$1", f = "PhysicalViewModel.kt", i = {}, l = {80}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalViewModel$changeLeftIndex$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $index;
        int label;
        final /* synthetic */ PhysicalViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i2, PhysicalViewModel physicalViewModel, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$index = i2;
            this.this$0 = physicalViewModel;
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
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.physical.PhysicalViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalViewModel$dealChangeLabel$1", f = "PhysicalViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalViewModel$dealChangeLabel$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08571 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<SkillHome> $data;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08571(List<SkillHome> list, Continuation<? super C08571> continuation) {
            super(2, continuation);
            this.$data = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return PhysicalViewModel.this.new C08571(this.$data, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08571) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            PhysicalViewModel.this.setRightList(this.$data);
            List<SkillHome> rightList = PhysicalViewModel.this.getRightList();
            LogExtKt.logd("右侧列表 " + (rightList != null ? Boxing.boxInt(rightList.size()) : null), YddConfig.TAG);
            PhysicalViewModel.this.getRightListChange().setValue(Boxing.boxLong(DateUtil.getTimeInMillisLong()));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalViewModel$dealWxShare$1", f = "PhysicalViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalViewModel$dealWxShare$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08581 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SkillShare $share;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08581(SkillShare skillShare, Continuation<? super C08581> continuation) {
            super(2, continuation);
            this.$share = skillShare;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08581(this.$share, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08581) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Bitmap bitmapCompressBitmapToSize;
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
                    bitmapCompressBitmapToSize = BitmapFactory.decodeResource(ContextManager.INSTANCE.getInstance().getContext().getResources(), R.drawable.common_share);
                } else {
                    String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(this.$share.getShareIconUrl());
                    LogExtKt.logd("分享图片地址：" + fileFullUrl, YddConfig.TAG);
                    String strSubstring = fileFullUrl.substring(StringsKt__StringsKt.lastIndexOf$default((CharSequence) fileFullUrl, StrPool.DOT, 0, false, 6, (Object) null) + 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String).substring(startIndex)");
                    bitmapCompressBitmapToSize = BitmpUtils.compressBitmapToSize(BitmpUtils.getUrlBitmap(fileFullUrl), 32, strSubstring);
                }
                if (bitmapCompressBitmapToSize != null) {
                    wXMediaMessage.thumbData = TransformExtKt.toByteArray$default(bitmapCompressBitmapToSize, (Bitmap.CompressFormat) null, 1, (Object) null);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalViewModel$httpGetIntegralApp$1", f = "PhysicalViewModel.kt", i = {}, l = {192}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalViewModel$httpGetIntegralApp$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08591 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08591(Continuation<? super C08591> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C08591(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08591) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
    @DebugMetadata(c = "com.yddmi.doctor.pages.physical.PhysicalViewModel$httpGetSkillBodyHome$1", f = "PhysicalViewModel.kt", i = {}, l = {148, 178}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nPhysicalViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PhysicalViewModel.kt\ncom/yddmi/doctor/pages/physical/PhysicalViewModel$httpGetSkillBodyHome$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,205:1\n1864#2,3:206\n*S KotlinDebug\n*F\n+ 1 PhysicalViewModel.kt\ncom/yddmi/doctor/pages/physical/PhysicalViewModel$httpGetSkillBodyHome$1\n*L\n152#1:206,3\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.physical.PhysicalViewModel$httpGetSkillBodyHome$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08601 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08601(Continuation<? super C08601> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return PhysicalViewModel.this.new C08601(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08601) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object skillBasic;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                } else {
                    PhysicalViewModel.this.setSkillGetError(th.getMessage());
                    MutableStateFlow<Long> getSkillErrorMsf = PhysicalViewModel.this.getGetSkillErrorMsf();
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
                String code = PhysicalViewModel.this.getCode();
                this.label = 1;
                skillBasic = yddClinicRepository.getSkillBasic(code, 2, this);
                if (skillBasic == coroutine_suspended) {
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
                skillBasic = obj;
            }
            List list = (List) skillBasic;
            List list2 = list;
            int i3 = 0;
            if (!(list2 == null || list2.isEmpty())) {
                PhysicalViewModel.this.allDataList = ((SkillHome) list.get(0)).getChildren();
                List list3 = PhysicalViewModel.this.allDataList;
                if (list3 != null) {
                    PhysicalViewModel physicalViewModel = PhysicalViewModel.this;
                    for (Object obj2 : list3) {
                        int i4 = i3 + 1;
                        if (i3 < 0) {
                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        SkillHome skillHome = (SkillHome) obj2;
                        if (i3 == 0) {
                            physicalViewModel.getLeftList().clear();
                            skillHome.setMSelected(true);
                        }
                        physicalViewModel.getLeftList().add(new SkillHome(skillHome.getIcon(), skillHome.getId(), skillHome.getIntroduce(), skillHome.isPlay(), skillHome.getLockStatus(), skillHome.getName(), skillHome.getPid(), (String) null, (String) null, 0L, (String) null, (String) null, (String) null, (String) null, (String) null, (Float) null, (String) null, (String) null, (List) null, (String) null, (String) null, (String) null, (BatchInfo) null, 0, (String) null, (List) null, (List) null, 0.0f, (List) null, (String) null, (SkillTicket) null, skillHome.getLabel(), 0, 0, (List) null, skillHome.getMSelected(), 0, 0, 0, 2147483520, 119, (DefaultConstructorMarker) null));
                        i3 = i4;
                    }
                }
            }
            PhysicalViewModel.this.getLeftListMsf().setValue(Boxing.boxLong(DateUtil.getTimeInMillisLong()));
            return Unit.INSTANCE;
        }
    }

    private final void dealUnLock(List<SkillHome> data) {
        if (data != null) {
            Iterator<T> it = data.iterator();
            while (it.hasNext()) {
                List<SkillHome> children = ((SkillHome) it.next()).getChildren();
                if (children != null) {
                    Iterator<T> it2 = children.iterator();
                    while (it2.hasNext()) {
                        ((SkillHome) it2.next()).setLockStatus(1);
                    }
                }
            }
        }
    }

    public final void changeLeftIndex(int index) {
        this.leftLastIndex = index;
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(index, this, null), 3, null);
    }

    public final void dealChangeLabel(@Nullable List<SkillHome> data) {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08571(data, null), 3, null);
    }

    public final void dealWxShare(@NotNull SkillShare share) {
        Intrinsics.checkNotNullParameter(share, "share");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), Dispatchers.getIO(), null, new C08581(share, null), 2, null);
    }

    @NotNull
    public final String getCode() {
        return this.code;
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

    public final void httpGetIntegralApp() {
        if (YddUserManager.INSTANCE.getInstance().userIsLogin()) {
            BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08591(null), 3, null);
        }
    }

    public final void httpGetSkillBodyHome() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08601(null), 3, null);
    }

    public final void setCode(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.code = str;
    }

    public final void setLabelList(@Nullable List<SkillHome> list) {
        this.labelList = list;
    }

    public final void setLeftLastIndex(int i2) {
        this.leftLastIndex = i2;
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
