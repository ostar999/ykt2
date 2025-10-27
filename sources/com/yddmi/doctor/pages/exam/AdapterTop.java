package com.yddmi.doctor.pages.exam;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.utils.DateUtil;
import com.yddmi.doctor.databinding.ExamAdapterTopBinding;
import com.yddmi.doctor.entity.result.ExamLeaderBoardItemResult;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/exam/AdapterTop;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/ExamLeaderBoardItemResult;", "Lcom/yddmi/doctor/databinding/ExamAdapterTopBinding;", "()V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterTop extends RecyclerAdapter<ExamLeaderBoardItemResult, ExamAdapterTopBinding> {
    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, ExamLeaderBoardItemResult examLeaderBoardItemResult, int i2) {
        bindViewHolder2((CommonViewHolder<ExamAdapterTopBinding>) commonViewHolder, examLeaderBoardItemResult, i2);
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<ExamAdapterTopBinding> holder, @NotNull final ExamLeaderBoardItemResult m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<ExamAdapterTopBinding, Unit>() { // from class: com.yddmi.doctor.pages.exam.AdapterTop.bindViewHolder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ExamAdapterTopBinding examAdapterTopBinding) {
                invoke2(examAdapterTopBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ExamAdapterTopBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                viewBanding.oneTv.setText(String.valueOf(m2.getRowNum()));
                viewBanding.twoTv.setText(String.valueOf(m2.getUser()));
                viewBanding.threeTv.setText(String.valueOf(DateUtil.second2Time6(Integer.parseInt(m2.getPracticeTime()))));
                viewBanding.fourTv.setText(String.valueOf(m2.getScore()));
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public ExamAdapterTopBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ExamAdapterTopBinding examAdapterTopBindingInflate = ExamAdapterTopBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(examAdapterTopBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return examAdapterTopBindingInflate;
    }
}
