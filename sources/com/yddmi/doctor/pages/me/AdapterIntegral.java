package com.yddmi.doctor.pages.me;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.utils.ext.TextViewExtKt;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.MeItemIntegralBinding;
import com.yddmi.doctor.entity.result.IntegralRow;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Marker;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/me/AdapterIntegral;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/IntegralRow;", "Lcom/yddmi/doctor/databinding/MeItemIntegralBinding;", "()V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterIntegral extends RecyclerAdapter<IntegralRow, MeItemIntegralBinding> {
    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, IntegralRow integralRow, int i2) {
        bindViewHolder2((CommonViewHolder<MeItemIntegralBinding>) commonViewHolder, integralRow, i2);
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<MeItemIntegralBinding> holder, @NotNull final IntegralRow m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<MeItemIntegralBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.AdapterIntegral.bindViewHolder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeItemIntegralBinding meItemIntegralBinding) {
                invoke2(meItemIntegralBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull MeItemIntegralBinding viewBanding) {
                int score;
                StringBuilder sb;
                String str;
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                IntegralRow integralRow = m2;
                if (integralRow != null) {
                    TextView textView = viewBanding.timeTv;
                    String createTime = integralRow.getCreateTime();
                    String reason = integralRow.getReason();
                    if (reason == null) {
                        reason = "";
                    }
                    textView.setText(createTime + " " + reason);
                    TextView textView2 = viewBanding.typeTv;
                    String businessName = integralRow.getBusinessName();
                    textView2.setText(businessName != null ? businessName : "");
                    TextView textView3 = viewBanding.detailTv;
                    if (integralRow.getType() == 0) {
                        score = integralRow.getScore();
                        sb = new StringBuilder();
                        str = Marker.ANY_NON_NULL_MARKER;
                    } else {
                        score = integralRow.getScore();
                        sb = new StringBuilder();
                        str = "-";
                    }
                    sb.append(str);
                    sb.append(score);
                    textView3.setText(sb.toString());
                    if (integralRow.getType() == 0) {
                        TextView detailTv = viewBanding.detailTv;
                        Intrinsics.checkNotNullExpressionValue(detailTv, "detailTv");
                        TextViewExtKt.setTextColorRes(detailTv, R.color.c_173a8e);
                    } else {
                        TextView detailTv2 = viewBanding.detailTv;
                        Intrinsics.checkNotNullExpressionValue(detailTv2, "detailTv");
                        TextViewExtKt.setTextColorRes(detailTv2, R.color.c_ff4141);
                    }
                    viewBanding.integralTv.setText(integralRow.getCurrentScore() + viewBanding.integralTv.getContext().getString(R.string.me_integral1));
                }
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public MeItemIntegralBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        MeItemIntegralBinding meItemIntegralBindingInflate = MeItemIntegralBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(meItemIntegralBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return meItemIntegralBindingInflate;
    }
}
