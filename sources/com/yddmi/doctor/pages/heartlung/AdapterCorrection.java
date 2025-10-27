package com.yddmi.doctor.pages.heartlung;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.GarbledHeartlungAdapterCorrectionBinding;
import com.yddmi.doctor.entity.result.FeedBackPageItem;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/AdapterCorrection;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/FeedBackPageItem;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungAdapterCorrectionBinding;", "()V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterCorrection extends RecyclerAdapter<FeedBackPageItem, GarbledHeartlungAdapterCorrectionBinding> {
    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, FeedBackPageItem feedBackPageItem, int i2) {
        bindViewHolder2((CommonViewHolder<GarbledHeartlungAdapterCorrectionBinding>) commonViewHolder, feedBackPageItem, i2);
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<GarbledHeartlungAdapterCorrectionBinding> holder, @NotNull final FeedBackPageItem m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<GarbledHeartlungAdapterCorrectionBinding, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.AdapterCorrection.bindViewHolder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungAdapterCorrectionBinding garbledHeartlungAdapterCorrectionBinding) {
                invoke2(garbledHeartlungAdapterCorrectionBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull GarbledHeartlungAdapterCorrectionBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                if (m2.getFeedbackType() == 0) {
                    TextView textView = viewBanding.titleTv;
                    textView.setText(textView.getContext().getString(R.string.heartlung_question1));
                } else {
                    TextView textView2 = viewBanding.titleTv;
                    textView2.setText(textView2.getContext().getString(R.string.heartlung_answer1));
                }
                TextView textView3 = viewBanding.timeTv;
                String createTime = m2.getCreateTime();
                if (createTime == null) {
                    createTime = "";
                }
                textView3.setText(createTime);
                TextView textView4 = viewBanding.detailsTv;
                String description = m2.getDescription();
                textView4.setText(description != null ? description : "");
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public GarbledHeartlungAdapterCorrectionBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        GarbledHeartlungAdapterCorrectionBinding garbledHeartlungAdapterCorrectionBindingInflate = GarbledHeartlungAdapterCorrectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(garbledHeartlungAdapterCorrectionBindingInflate, "inflate(\n            Lay…          false\n        )");
        return garbledHeartlungAdapterCorrectionBindingInflate;
    }
}
