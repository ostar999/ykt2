package com.yddmi.doctor.pages.product;

import androidx.lifecycle.ViewModelKt;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.hjq.toast.Toaster;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.entity.result.BatchInfo;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.entity.result.SkillTicket;
import com.yddmi.doctor.exception.HttpLogout401Exception;
import com.yddmi.doctor.repository.YddClinicRepository;
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
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u00109\u001a\u00020:J\"\u0010;\u001a\u00020:2\u0006\u0010<\u001a\u00020\u00042\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\r2\u0006\u0010>\u001a\u00020?J\u0006\u0010@\u001a\u00020:R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001e\"\u0004\b!\u0010\"R \u0010#\u001a\b\u0012\u0004\u0012\u00020\r0\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001e\"\u0004\b%\u0010\"R\u001a\u0010&\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010(\"\u0004\b3\u0010*R\u0017\u00104\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u0019R\u001a\u00106\u001a\u00020,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010.\"\u0004\b8\u00100¨\u0006A"}, d2 = {"Lcom/yddmi/doctor/pages/product/ProductViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "allItem", "Lcom/yddmi/doctor/entity/result/SkillHome;", "getAllItem", "()Lcom/yddmi/doctor/entity/result/SkillHome;", "setAllItem", "(Lcom/yddmi/doctor/entity/result/SkillHome;)V", "currentSkill", "getCurrentSkill", "setCurrentSkill", "currentTicket", "Lcom/yddmi/doctor/entity/result/SkillTicket;", "getCurrentTicket", "()Lcom/yddmi/doctor/entity/result/SkillTicket;", "setCurrentTicket", "(Lcom/yddmi/doctor/entity/result/SkillTicket;)V", "data", "getData", PLVRxEncryptDataFunction.SET_DATA_METHOD, "dataChangeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getDataChangeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "introduceUrlsAllList", "", "", "getIntroduceUrlsAllList", "()Ljava/util/List;", "mSkillList", "getMSkillList", "setMSkillList", "(Ljava/util/List;)V", "mSkillTicketList", "getMSkillTicketList", "setMSkillTicketList", "name", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "skill24", "", "getSkill24", "()I", "setSkill24", "(I)V", "skillId", "getSkillId", "setSkillId", "ticketChangeMsf", "getTicketChangeMsf", "type", "getType", "setType", "dealData", "", "httpGetProductInfo", "m", "ticket", "useCoupon", "", "httpGetUserCoupon", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ProductViewModel extends BaseViewModel {
    public SkillHome allItem;

    @Nullable
    private SkillHome currentSkill;

    @Nullable
    private SkillTicket currentTicket;

    @Nullable
    private SkillHome data;
    private int type = 100;

    @NotNull
    private String name = "";

    @NotNull
    private String skillId = "-1";
    private int skill24 = 1;

    @NotNull
    private List<SkillHome> mSkillList = new ArrayList();

    @NotNull
    private List<SkillTicket> mSkillTicketList = new ArrayList();

    @NotNull
    private final List<String> introduceUrlsAllList = new ArrayList();

    @NotNull
    private final MutableStateFlow<Long> dataChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> ticketChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.product.ProductViewModel$httpGetProductInfo$1", f = "ProductViewModel.kt", i = {}, l = {84, 125, 133}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nProductViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProductViewModel.kt\ncom/yddmi/doctor/pages/product/ProductViewModel$httpGetProductInfo$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,163:1\n1855#2,2:164\n1864#2,3:166\n*S KotlinDebug\n*F\n+ 1 ProductViewModel.kt\ncom/yddmi/doctor/pages/product/ProductViewModel$httpGetProductInfo$1\n*L\n90#1:164,2\n107#1:166,3\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.product.ProductViewModel$httpGetProductInfo$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ SkillHome $m;
        final /* synthetic */ SkillTicket $ticket;
        final /* synthetic */ boolean $useCoupon;
        Object L$0;
        int label;
        final /* synthetic */ ProductViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SkillHome skillHome, ProductViewModel productViewModel, SkillTicket skillTicket, boolean z2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$m = skillHome;
            this.this$0 = productViewModel;
            this.$ticket = skillTicket;
            this.$useCoupon = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(this.$m, this.this$0, this.$ticket, this.$useCoupon, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            int i2;
            ProductViewModel productViewModel;
            Object productInfoSkill;
            String couponRecordId;
            SkillHome data;
            List<SkillHome> allItemList;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i3 = this.label;
            try {
            } catch (Throwable th) {
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                if (th instanceof HttpLogout401Exception) {
                    BusUtils.post(GlobalAction.eventLogout401);
                } else {
                    String message = th.getMessage();
                    if (!(message == null || message.length() == 0)) {
                        Toaster.show((CharSequence) th.getMessage());
                    }
                }
                MutableStateFlow<Long> dataChangeMsf = this.this$0.getDataChangeMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.L$0 = null;
                this.label = 3;
                if (dataChangeMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i3 == 0) {
                ResultKt.throwOnFailure(obj);
                if (Intrinsics.areEqual(this.$m.getSkillId(), "-1")) {
                    ProductViewModel productViewModel2 = this.this$0;
                    productViewModel2.setCurrentSkill(productViewModel2.getAllItem());
                    i2 = 1;
                } else {
                    this.this$0.setCurrentSkill(this.$m);
                    i2 = 2;
                }
                this.this$0.setCurrentTicket(this.$ticket);
                String skillId = this.$m.getSkillId();
                String skillId2 = skillId == null || skillId.length() == 0 ? "-1" : this.$m.getSkillId();
                SkillTicket skillTicket = this.$ticket;
                String str = (skillTicket == null || (couponRecordId = skillTicket.getCouponRecordId()) == null) ? "" : couponRecordId;
                productViewModel = this.this$0;
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                boolean z2 = this.$useCoupon;
                int skill24 = productViewModel.getSkill24();
                this.L$0 = productViewModel;
                this.label = 1;
                productInfoSkill = yddClinicRepository.getProductInfoSkill(skillId2, i2, str, z2, skill24, this);
                if (productInfoSkill == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i3 != 1) {
                    if (i3 != 2 && i3 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                productViewModel = (ProductViewModel) this.L$0;
                ResultKt.throwOnFailure(obj);
                productInfoSkill = obj;
            }
            productViewModel.setData((SkillHome) productInfoSkill);
            if (this.this$0.getData() != null) {
                SkillHome data2 = this.this$0.getData();
                Intrinsics.checkNotNull(data2);
                String introduceUrls = data2.getIntroduceUrls();
                if (!(introduceUrls == null || introduceUrls.length() == 0)) {
                    SkillHome data3 = this.this$0.getData();
                    Intrinsics.checkNotNull(data3);
                    String introduceUrls2 = data3.getIntroduceUrls();
                    Intrinsics.checkNotNull(introduceUrls2);
                    List listSplit$default = StringsKt__StringsKt.split$default((CharSequence) introduceUrls2, new String[]{","}, false, 0, 6, (Object) null);
                    ArrayList arrayList = new ArrayList();
                    Iterator it = listSplit$default.iterator();
                    while (it.hasNext()) {
                        arrayList.add(YddHostConfig.INSTANCE.getInstance().getFileFullPrivateUrl((String) it.next()));
                    }
                    SkillHome data4 = this.this$0.getData();
                    Intrinsics.checkNotNull(data4);
                    data4.setIntroduceUrlsList(arrayList);
                }
            }
            ProductViewModel productViewModel3 = this.this$0;
            SkillHome data5 = productViewModel3.getData();
            productViewModel3.setCurrentTicket(data5 != null ? data5.getCouponRecordDTO() : null);
            this.this$0.getMSkillList().clear();
            switch (this.this$0.getType()) {
                case 100:
                    this.this$0.getMSkillList().add(this.this$0.getAllItem());
                    break;
                case 101:
                    this.this$0.getMSkillList().add(this.this$0.getAllItem());
                    if (!Intrinsics.areEqual(this.this$0.getSkillId(), "-1") && (data = this.this$0.getData()) != null && (allItemList = data.getAllItemList()) != null) {
                        ProductViewModel productViewModel4 = this.this$0;
                        int i4 = 0;
                        for (Object obj2 : allItemList) {
                            int i5 = i4 + 1;
                            if (i4 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                            }
                            SkillHome skillHome = (SkillHome) obj2;
                            if (Intrinsics.areEqual(skillHome.getSkillId(), productViewModel4.getSkillId())) {
                                skillHome.setLabel(skillHome.getName());
                                String string = ContextManager.INSTANCE.getInstance().getContext().getString(R.string.product_current);
                                Intrinsics.checkNotNullExpressionValue(string, "ContextManager.getInstan…R.string.product_current)");
                                skillHome.setName(string);
                                productViewModel4.getMSkillList().add(skillHome);
                            }
                            i4 = i5;
                        }
                        break;
                    }
                    break;
                case 102:
                    this.this$0.getMSkillList().add(this.this$0.getAllItem());
                    if (this.this$0.getData() != null) {
                        SkillHome data6 = this.this$0.getData();
                        Intrinsics.checkNotNull(data6);
                        List<SkillHome> allItemList2 = data6.getAllItemList();
                        if (!(allItemList2 == null || allItemList2.isEmpty())) {
                            List<SkillHome> mSkillList = this.this$0.getMSkillList();
                            SkillHome data7 = this.this$0.getData();
                            Intrinsics.checkNotNull(data7);
                            List<SkillHome> allItemList3 = data7.getAllItemList();
                            Intrinsics.checkNotNull(allItemList3);
                            mSkillList.addAll(allItemList3);
                            break;
                        }
                    }
                    break;
            }
            MutableStateFlow<Long> dataChangeMsf2 = this.this$0.getDataChangeMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.L$0 = null;
            this.label = 2;
            if (dataChangeMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.product.ProductViewModel$httpGetUserCoupon$1", f = "ProductViewModel.kt", i = {}, l = {144, 149, 157}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.yddmi.doctor.pages.product.ProductViewModel$httpGetUserCoupon$1, reason: invalid class name and case insensitive filesystem */
    public static final class C08661 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public C08661(Continuation<? super C08661> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return ProductViewModel.this.new C08661(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C08661) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0091 A[RETURN] */
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
                r2 = 0
                r3 = 3
                r4 = 2
                r5 = 1
                if (r1 == 0) goto L2a
                if (r1 == r5) goto L24
                if (r1 == r4) goto L1f
                if (r1 != r3) goto L17
                kotlin.ResultKt.throwOnFailure(r9)
                goto Ld2
            L17:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L1f:
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L28
                goto Ld2
            L24:
                kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L28
                goto L5d
            L28:
                r9 = move-exception
                goto L92
            L2a:
                kotlin.ResultKt.throwOnFailure(r9)
                com.yddmi.doctor.pages.product.ProductViewModel r9 = com.yddmi.doctor.pages.product.ProductViewModel.this     // Catch: java.lang.Throwable -> L28
                com.yddmi.doctor.entity.result.SkillHome r9 = r9.getCurrentSkill()     // Catch: java.lang.Throwable -> L28
                if (r9 == 0) goto L7b
                com.yddmi.doctor.pages.product.ProductViewModel r9 = com.yddmi.doctor.pages.product.ProductViewModel.this     // Catch: java.lang.Throwable -> L28
                java.util.List r9 = r9.getMSkillTicketList()     // Catch: java.lang.Throwable -> L28
                r9.clear()     // Catch: java.lang.Throwable -> L28
                com.yddmi.doctor.repository.YddClinicRepository r9 = com.yddmi.doctor.repository.YddClinicRepository.INSTANCE     // Catch: java.lang.Throwable -> L28
                com.yddmi.doctor.pages.product.ProductViewModel r1 = com.yddmi.doctor.pages.product.ProductViewModel.this     // Catch: java.lang.Throwable -> L28
                com.yddmi.doctor.entity.result.SkillHome r1 = r1.getCurrentSkill()     // Catch: java.lang.Throwable -> L28
                kotlin.jvm.internal.Intrinsics.checkNotNull(r1)     // Catch: java.lang.Throwable -> L28
                java.lang.String r1 = r1.getSkillId()     // Catch: java.lang.Throwable -> L28
                kotlin.jvm.internal.Intrinsics.checkNotNull(r1)     // Catch: java.lang.Throwable -> L28
                long r6 = java.lang.Long.parseLong(r1)     // Catch: java.lang.Throwable -> L28
                r8.label = r5     // Catch: java.lang.Throwable -> L28
                java.lang.Object r9 = r9.getUserCoupon(r6, r8)     // Catch: java.lang.Throwable -> L28
                if (r9 != r0) goto L5d
                return r0
            L5d:
                java.util.List r9 = (java.util.List) r9     // Catch: java.lang.Throwable -> L28
                r1 = r9
                java.util.Collection r1 = (java.util.Collection) r1     // Catch: java.lang.Throwable -> L28
                if (r1 == 0) goto L6d
                boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> L28
                if (r1 == 0) goto L6b
                goto L6d
            L6b:
                r1 = r2
                goto L6e
            L6d:
                r1 = r5
            L6e:
                if (r1 != 0) goto L7b
                com.yddmi.doctor.pages.product.ProductViewModel r1 = com.yddmi.doctor.pages.product.ProductViewModel.this     // Catch: java.lang.Throwable -> L28
                java.util.List r1 = r1.getMSkillTicketList()     // Catch: java.lang.Throwable -> L28
                java.util.Collection r9 = (java.util.Collection) r9     // Catch: java.lang.Throwable -> L28
                r1.addAll(r9)     // Catch: java.lang.Throwable -> L28
            L7b:
                com.yddmi.doctor.pages.product.ProductViewModel r9 = com.yddmi.doctor.pages.product.ProductViewModel.this     // Catch: java.lang.Throwable -> L28
                kotlinx.coroutines.flow.MutableStateFlow r9 = r9.getTicketChangeMsf()     // Catch: java.lang.Throwable -> L28
                long r6 = com.catchpig.mvvm.utils.DateUtil.getTimeInMillisLong()     // Catch: java.lang.Throwable -> L28
                java.lang.Long r1 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r6)     // Catch: java.lang.Throwable -> L28
                r8.label = r4     // Catch: java.lang.Throwable -> L28
                java.lang.Object r9 = r9.emit(r1, r8)     // Catch: java.lang.Throwable -> L28
                if (r9 != r0) goto Ld2
                return r0
            L92:
                java.lang.String r1 = java.lang.String.valueOf(r9)
                java.lang.String r4 = "h0xta"
                com.catchpig.utils.ext.LogExtKt.loge(r1, r4)
                boolean r1 = r9 instanceof com.yddmi.doctor.exception.HttpLogout401Exception
                if (r1 == 0) goto La5
                java.lang.String r9 = "eventLogout401"
                com.blankj.utilcode.util.BusUtils.post(r9)
                goto Lbb
            La5:
                java.lang.String r1 = r9.getMessage()
                if (r1 == 0) goto Lb1
                int r1 = r1.length()
                if (r1 != 0) goto Lb2
            Lb1:
                r2 = r5
            Lb2:
                if (r2 != 0) goto Lbb
                java.lang.String r9 = r9.getMessage()
                com.hjq.toast.Toaster.show(r9)
            Lbb:
                com.yddmi.doctor.pages.product.ProductViewModel r9 = com.yddmi.doctor.pages.product.ProductViewModel.this
                kotlinx.coroutines.flow.MutableStateFlow r9 = r9.getTicketChangeMsf()
                long r1 = com.catchpig.mvvm.utils.DateUtil.getTimeInMillisLong()
                java.lang.Long r1 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r1)
                r8.label = r3
                java.lang.Object r9 = r9.emit(r1, r8)
                if (r9 != r0) goto Ld2
                return r0
            Ld2:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.product.ProductViewModel.C08661.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ void httpGetProductInfo$default(ProductViewModel productViewModel, SkillHome skillHome, SkillTicket skillTicket, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            skillTicket = null;
        }
        productViewModel.httpGetProductInfo(skillHome, skillTicket, z2);
    }

    public final void dealData() {
        this.introduceUrlsAllList.clear();
        if (3 != this.skill24) {
            this.introduceUrlsAllList.add("android.resource://com.yddmi.doctor/drawable/" + R.drawable.product_24all);
            ContextManager.Companion companion = ContextManager.INSTANCE;
            String string = companion.getInstance().getContext().getString(R.string.product_all);
            String string2 = companion.getInstance().getContext().getString(R.string.product_all24);
            Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.product_all)");
            setAllItem(new SkillHome((String) null, (String) null, string2, 0, 0, string, (String) null, "-1", (String) null, 0L, (String) null, (String) null, (String) null, (String) null, (String) null, (Float) null, (String) null, (String) null, (List) null, (String) null, (String) null, (String) null, (BatchInfo) null, 0, (String) null, (List) null, (List) null, 0.0f, (List) null, (String) null, (SkillTicket) null, (String) null, 0, 0, (List) null, false, 0, 0, 0, -165, 127, (DefaultConstructorMarker) null));
            return;
        }
        this.introduceUrlsAllList.add("android.resource://com.yddmi.doctor/drawable/" + R.drawable.product_body1);
        this.introduceUrlsAllList.add("android.resource://com.yddmi.doctor/drawable/" + R.drawable.product_body2);
        ContextManager.Companion companion2 = ContextManager.INSTANCE;
        String string3 = companion2.getInstance().getContext().getString(R.string.product_all);
        String string4 = companion2.getInstance().getContext().getString(R.string.product_all_body);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(R.string.product_all)");
        setAllItem(new SkillHome((String) null, (String) null, string4, 0, 0, string3, (String) null, "-1", (String) null, 0L, (String) null, (String) null, (String) null, (String) null, (String) null, (Float) null, (String) null, (String) null, (List) null, (String) null, (String) null, (String) null, (BatchInfo) null, 0, (String) null, (List) null, (List) null, 0.0f, (List) null, (String) null, (SkillTicket) null, (String) null, 0, 0, (List) null, false, 0, 0, 0, -165, 127, (DefaultConstructorMarker) null));
    }

    @NotNull
    public final SkillHome getAllItem() {
        SkillHome skillHome = this.allItem;
        if (skillHome != null) {
            return skillHome;
        }
        Intrinsics.throwUninitializedPropertyAccessException("allItem");
        return null;
    }

    @Nullable
    public final SkillHome getCurrentSkill() {
        return this.currentSkill;
    }

    @Nullable
    public final SkillTicket getCurrentTicket() {
        return this.currentTicket;
    }

    @Nullable
    public final SkillHome getData() {
        return this.data;
    }

    @NotNull
    public final MutableStateFlow<Long> getDataChangeMsf() {
        return this.dataChangeMsf;
    }

    @NotNull
    public final List<String> getIntroduceUrlsAllList() {
        return this.introduceUrlsAllList;
    }

    @NotNull
    public final List<SkillHome> getMSkillList() {
        return this.mSkillList;
    }

    @NotNull
    public final List<SkillTicket> getMSkillTicketList() {
        return this.mSkillTicketList;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final int getSkill24() {
        return this.skill24;
    }

    @NotNull
    public final String getSkillId() {
        return this.skillId;
    }

    @NotNull
    public final MutableStateFlow<Long> getTicketChangeMsf() {
        return this.ticketChangeMsf;
    }

    public final int getType() {
        return this.type;
    }

    public final void httpGetProductInfo(@NotNull SkillHome m2, @Nullable SkillTicket ticket, boolean useCoupon) {
        Intrinsics.checkNotNullParameter(m2, "m");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(m2, this, ticket, useCoupon, null), 3, null);
    }

    public final void httpGetUserCoupon() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C08661(null), 3, null);
    }

    public final void setAllItem(@NotNull SkillHome skillHome) {
        Intrinsics.checkNotNullParameter(skillHome, "<set-?>");
        this.allItem = skillHome;
    }

    public final void setCurrentSkill(@Nullable SkillHome skillHome) {
        this.currentSkill = skillHome;
    }

    public final void setCurrentTicket(@Nullable SkillTicket skillTicket) {
        this.currentTicket = skillTicket;
    }

    public final void setData(@Nullable SkillHome skillHome) {
        this.data = skillHome;
    }

    public final void setMSkillList(@NotNull List<SkillHome> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.mSkillList = list;
    }

    public final void setMSkillTicketList(@NotNull List<SkillTicket> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.mSkillTicketList = list;
    }

    public final void setName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final void setSkill24(int i2) {
        this.skill24 = i2;
    }

    public final void setSkillId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.skillId = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }
}
