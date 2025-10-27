package com.yddmi.doctor.pages.heartlung;

import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.catchpig.mvvm.base.viewmodel.BaseViewModel;
import com.yddmi.doctor.entity.result.FeedBackPageList;
import com.yddmi.doctor.repository.YddClinicRepository;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u001a2\b\b\u0002\u0010\u001c\u001a\u00020\u001dR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0006\"\u0004\b\u0012\u0010\bR\u000e\u0010\u0013\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u001e"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/CorrectionViewModel;", "Lcom/catchpig/mvvm/base/viewmodel/BaseViewModel;", "()V", "categoryId", "", "getCategoryId", "()Ljava/lang/String;", "setCategoryId", "(Ljava/lang/String;)V", "currentIndex", "", "dataChangeMsf", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getDataChangeMsf", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "medicalKnowledgeId", "getMedicalKnowledgeId", "setMedicalKnowledgeId", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "type", "getType", "()I", "setType", "(I)V", "getFeedBackPage", "Lkotlinx/coroutines/flow/Flow;", "Lcom/yddmi/doctor/entity/result/FeedBackPageList;", "refresh", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CorrectionViewModel extends BaseViewModel {
    private int type = 100;

    @NotNull
    private String categoryId = "";

    @Nullable
    private String medicalKnowledgeId = "";
    private final int pageSize = 10;
    private int currentIndex = 1;

    @NotNull
    private final MutableStateFlow<Long> dataChangeMsf = StateFlowKt.MutableStateFlow(0L);

    public static /* synthetic */ Flow getFeedBackPage$default(CorrectionViewModel correctionViewModel, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        return correctionViewModel.getFeedBackPage(z2);
    }

    @NotNull
    public final String getCategoryId() {
        return this.categoryId;
    }

    @NotNull
    public final MutableStateFlow<Long> getDataChangeMsf() {
        return this.dataChangeMsf;
    }

    @NotNull
    public final Flow<FeedBackPageList> getFeedBackPage(boolean refresh) {
        int i2 = refresh ? 1 : 1 + this.currentIndex;
        this.currentIndex = i2;
        return YddClinicRepository.INSTANCE.getFeedBackPage(this.categoryId, this.medicalKnowledgeId, i2, this.pageSize);
    }

    @Nullable
    public final String getMedicalKnowledgeId() {
        return this.medicalKnowledgeId;
    }

    public final int getType() {
        return this.type;
    }

    public final void setCategoryId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.categoryId = str;
    }

    public final void setMedicalKnowledgeId(@Nullable String str) {
        this.medicalKnowledgeId = str;
    }

    public final void setType(int i2) {
        this.type = i2;
    }
}
