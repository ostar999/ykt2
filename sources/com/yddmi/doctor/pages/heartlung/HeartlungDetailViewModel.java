package com.yddmi.doctor.pages.heartlung;

import androidx.lifecycle.ViewModelKt;
import com.blankj.utilcode.util.BusUtils;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.catchpig.mvvm.utils.DateUtil;
import com.catchpig.utils.ext.LogExtKt;
import com.hjq.toast.Toaster;
import com.yddmi.doctor.config.GlobalAction;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.entity.result.HeartDetail;
import com.yddmi.doctor.entity.result.RatingCriteria;
import com.yddmi.doctor.entity.result.TreeNodesItem;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u00101\u001a\u000202J\u0006\u00103\u001a\u000202R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R\u001a\u0010\u0017\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0006\"\u0004\b\u0019\u0010\bR\u001a\u0010\u001a\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\bR\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u001f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\"\u0010$\u001a\n\u0012\u0004\u0012\u00020&\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0006\"\u0004\b-\u0010\bR\u001c\u0010.\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0011\"\u0004\b0\u0010\u0013¨\u00064"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/HeartlungDetailViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "autoGoVpIndex", "", "getAutoGoVpIndex", "()I", "setAutoGoVpIndex", "(I)V", "autoVpGoMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getAutoVpGoMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "categoryId", "", "getCategoryId", "()Ljava/lang/String;", "setCategoryId", "(Ljava/lang/String;)V", "categoryName", "getCategoryName", "setCategoryName", "code", "getCode", "setCode", "currentVpIndex", "getCurrentVpIndex", "setCurrentVpIndex", "detailsChangeMsf", "getDetailsChangeMsf", "isFavorite", "setFavorite", "lastVpIndex", "getLastVpIndex", "setLastVpIndex", "mData", "", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "getMData", "()Ljava/util/List;", "setMData", "(Ljava/util/List;)V", "mIsExchange", "getMIsExchange", "setMIsExchange", "medicalKnowledgeId", "getMedicalKnowledgeId", "setMedicalKnowledgeId", "dealVpAutoGo", "", "httpGetKnowledgeDetails", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class HeartlungDetailViewModel extends BaseViewModel {
    private int autoGoVpIndex;
    private int currentVpIndex;
    private int isFavorite;
    private int lastVpIndex;

    @Nullable
    private List<HeartDetail> mData;
    private int mIsExchange;
    private int code = 1;

    @NotNull
    private String categoryId = "";

    @NotNull
    private String categoryName = "";

    @Nullable
    private String medicalKnowledgeId = "";

    @NotNull
    private final MutableStateFlow<Long> detailsChangeMsf = StateFlowKt.MutableStateFlow(0L);

    @NotNull
    private final MutableStateFlow<Long> autoVpGoMsf = StateFlowKt.MutableStateFlow(0L);

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungDetailViewModel$dealVpAutoGo$1", f = "HeartlungDetailViewModel.kt", i = {}, l = {55}, m = "invokeSuspend", n = {}, s = {})
    @SourceDebugExtension({"SMAP\nHeartlungDetailViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeartlungDetailViewModel.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungDetailViewModel$dealVpAutoGo$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,116:1\n1864#2,3:117\n*S KotlinDebug\n*F\n+ 1 HeartlungDetailViewModel.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungDetailViewModel$dealVpAutoGo$1\n*L\n47#1:117,3\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungDetailViewModel$dealVpAutoGo$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungDetailViewModel.this.new AnonymousClass1(continuation);
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
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                String autoGoMedicalKnowledgeId = GlobalAction.INSTANCE.getAutoGoMedicalKnowledgeId();
                if (!(autoGoMedicalKnowledgeId == null || autoGoMedicalKnowledgeId.length() == 0)) {
                    Ref.IntRef intRef = new Ref.IntRef();
                    intRef.element = -1;
                    List<HeartDetail> mData = HeartlungDetailViewModel.this.getMData();
                    if (mData != null) {
                        int i3 = 0;
                        for (Object obj2 : mData) {
                            int i4 = i3 + 1;
                            if (i3 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                            }
                            HeartDetail heartDetail = (HeartDetail) obj2;
                            String medicalKnowledgeId = heartDetail.getMedicalKnowledgeId();
                            if (!(medicalKnowledgeId == null || medicalKnowledgeId.length() == 0) && Intrinsics.areEqual(heartDetail.getMedicalKnowledgeId(), GlobalAction.INSTANCE.getAutoGoMedicalKnowledgeId())) {
                                intRef.element = i3;
                            }
                            i3 = i4;
                        }
                    }
                    int i5 = intRef.element;
                    if (-1 != i5 && i5 != HeartlungDetailViewModel.this.getCurrentVpIndex()) {
                        HeartlungDetailViewModel.this.setAutoGoVpIndex(intRef.element);
                        MutableStateFlow<Long> autoVpGoMsf = HeartlungDetailViewModel.this.getAutoVpGoMsf();
                        Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                        this.label = 1;
                        if (autoVpGoMsf.emit(lBoxLong, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                }
                return Unit.INSTANCE;
            }
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            GlobalAction.INSTANCE.setAutoGoMedicalKnowledgeId("");
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.yddmi.doctor.pages.heartlung.HeartlungDetailViewModel$httpGetKnowledgeDetails$1", f = "HeartlungDetailViewModel.kt", i = {2}, l = {68, 101, 104}, m = "invokeSuspend", n = {"t"}, s = {"L$0"})
    @SourceDebugExtension({"SMAP\nHeartlungDetailViewModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeartlungDetailViewModel.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungDetailViewModel$httpGetKnowledgeDetails$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,116:1\n1864#2,2:117\n1864#2,2:119\n1864#2,2:121\n1864#2,3:123\n1866#2:126\n1866#2:127\n1855#2,2:128\n1866#2:130\n*S KotlinDebug\n*F\n+ 1 HeartlungDetailViewModel.kt\ncom/yddmi/doctor/pages/heartlung/HeartlungDetailViewModel$httpGetKnowledgeDetails$1\n*L\n73#1:117,2\n81#1:119,2\n83#1:121,2\n88#1:123,3\n83#1:126\n81#1:127\n93#1:128,2\n73#1:130\n*E\n"})
    /* renamed from: com.yddmi.doctor.pages.heartlung.HeartlungDetailViewModel$httpGetKnowledgeDetails$1, reason: invalid class name and case insensitive filesystem */
    public static final class C06781 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        public C06781(Continuation<? super C06781> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return HeartlungDetailViewModel.this.new C06781(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C06781) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object knowledgeDetails$default;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            try {
            } catch (Throwable th) {
                th = th;
                LogExtKt.loge(String.valueOf(th), YddConfig.TAG);
                MutableStateFlow<Long> detailsChangeMsf = HeartlungDetailViewModel.this.getDetailsChangeMsf();
                Long lBoxLong = Boxing.boxLong(DateUtil.getTimeInMillisLong());
                this.L$0 = th;
                this.label = 3;
                if (detailsChangeMsf.emit(lBoxLong, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                YddClinicRepository yddClinicRepository = YddClinicRepository.INSTANCE;
                String categoryId = HeartlungDetailViewModel.this.getCategoryId();
                int isFavorite = HeartlungDetailViewModel.this.getIsFavorite();
                this.label = 1;
                knowledgeDetails$default = YddClinicRepository.getKnowledgeDetails$default(yddClinicRepository, categoryId, isFavorite, 0, this, 4, null);
                if (knowledgeDetails$default == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    if (i2 == 2) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    if (i2 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    th = (Throwable) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    if (th instanceof HttpLogout401Exception) {
                        BusUtils.post(GlobalAction.eventLogout401);
                    } else {
                        String message = th.getMessage();
                        if (message != null) {
                            Toaster.show((CharSequence) message);
                        }
                    }
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
                knowledgeDetails$default = obj;
            }
            List<HeartDetail> list = (List) knowledgeDetails$default;
            if (list != null) {
                HeartlungDetailViewModel.this.setMData(list);
                List<HeartDetail> mData = HeartlungDetailViewModel.this.getMData();
                Intrinsics.checkNotNull(mData);
                HeartlungDetailViewModel heartlungDetailViewModel = HeartlungDetailViewModel.this;
                int i3 = 0;
                for (Object obj2 : mData) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    HeartDetail heartDetail = (HeartDetail) obj2;
                    if (heartlungDetailViewModel.getMIsExchange() == 0) {
                        String medicalKnowledgeId = heartDetail.getMedicalKnowledgeId();
                        if (!(medicalKnowledgeId == null || medicalKnowledgeId.length() == 0) && Intrinsics.areEqual(heartDetail.getMedicalKnowledgeId(), heartlungDetailViewModel.getMedicalKnowledgeId())) {
                            heartlungDetailViewModel.setAutoGoVpIndex(i3);
                        }
                    }
                    if (4 == heartlungDetailViewModel.getCode() || 5 == heartlungDetailViewModel.getCode()) {
                        RatingCriteria ratingCriteria = heartDetail.getRatingCriteria();
                        if ((ratingCriteria != null ? ratingCriteria.getTreeNodes() : null) != null && heartDetail.getRatingCriteria().getTreeNodes().size() > 0) {
                            ArrayList arrayList = new ArrayList();
                            int i5 = 0;
                            for (Object obj3 : heartDetail.getRatingCriteria().getTreeNodes()) {
                                int i6 = i5 + 1;
                                if (i5 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                TreeNodesItem treeNodesItem = (TreeNodesItem) obj3;
                                arrayList.add(treeNodesItem);
                                List<TreeNodesItem> children = treeNodesItem.getChildren();
                                if (children != null) {
                                    int i7 = 0;
                                    for (Object obj4 : children) {
                                        int i8 = i7 + 1;
                                        if (i7 < 0) {
                                            CollectionsKt__CollectionsKt.throwIndexOverflow();
                                        }
                                        TreeNodesItem treeNodesItem2 = (TreeNodesItem) obj4;
                                        String secondaryCategory = treeNodesItem2.getSecondaryCategory();
                                        if (secondaryCategory == null || secondaryCategory.length() == 0) {
                                            treeNodesItem2.setLevel(3);
                                        }
                                        arrayList.add(treeNodesItem2);
                                        List<TreeNodesItem> children2 = treeNodesItem2.getChildren();
                                        if (children2 != null) {
                                            int i9 = 0;
                                            for (Object obj5 : children2) {
                                                int i10 = i9 + 1;
                                                if (i9 < 0) {
                                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                                }
                                                arrayList.add((TreeNodesItem) obj5);
                                                i9 = i10;
                                            }
                                        }
                                        i7 = i8;
                                    }
                                }
                                i5 = i6;
                            }
                            List<TreeNodesItem> mTreeNodes = heartDetail.getRatingCriteria().getMTreeNodes();
                            if (mTreeNodes != null) {
                                Iterator<T> it = mTreeNodes.iterator();
                                while (it.hasNext()) {
                                    ((TreeNodesItem) it.next()).setChildren(null);
                                }
                            }
                            heartDetail.getRatingCriteria().setMTreeNodes(arrayList);
                        }
                    }
                    i3 = i4;
                }
            }
            MutableStateFlow<Long> detailsChangeMsf2 = HeartlungDetailViewModel.this.getDetailsChangeMsf();
            Long lBoxLong2 = Boxing.boxLong(DateUtil.getTimeInMillisLong());
            this.label = 2;
            if (detailsChangeMsf2.emit(lBoxLong2, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    public final void dealVpAutoGo() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
    }

    public final int getAutoGoVpIndex() {
        return this.autoGoVpIndex;
    }

    @NotNull
    public final MutableStateFlow<Long> getAutoVpGoMsf() {
        return this.autoVpGoMsf;
    }

    @NotNull
    public final String getCategoryId() {
        return this.categoryId;
    }

    @NotNull
    public final String getCategoryName() {
        return this.categoryName;
    }

    public final int getCode() {
        return this.code;
    }

    public final int getCurrentVpIndex() {
        return this.currentVpIndex;
    }

    @NotNull
    public final MutableStateFlow<Long> getDetailsChangeMsf() {
        return this.detailsChangeMsf;
    }

    public final int getLastVpIndex() {
        return this.lastVpIndex;
    }

    @Nullable
    public final List<HeartDetail> getMData() {
        return this.mData;
    }

    public final int getMIsExchange() {
        return this.mIsExchange;
    }

    @Nullable
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    public final void httpGetKnowledgeDetails() {
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C06781(null), 3, null);
    }

    /* renamed from: isFavorite, reason: from getter */
    public final int getIsFavorite() {
        return this.isFavorite;
    }

    public final void setAutoGoVpIndex(int i2) {
        this.autoGoVpIndex = i2;
    }

    public final void setCategoryId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.categoryId = str;
    }

    public final void setCategoryName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.categoryName = str;
    }

    public final void setCode(int i2) {
        this.code = i2;
    }

    public final void setCurrentVpIndex(int i2) {
        this.currentVpIndex = i2;
    }

    public final void setFavorite(int i2) {
        this.isFavorite = i2;
    }

    public final void setLastVpIndex(int i2) {
        this.lastVpIndex = i2;
    }

    public final void setMData(@Nullable List<HeartDetail> list) {
        this.mData = list;
    }

    public final void setMIsExchange(int i2) {
        this.mIsExchange = i2;
    }

    public final void setMedicalKnowledgeId(@Nullable String str) {
        this.medicalKnowledgeId = str;
    }
}
