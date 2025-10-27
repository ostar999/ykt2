package com.yddmi.doctor.pages.me;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.utils.ext.TextViewExtKt;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.MeItemHistoryDetailBinding;
import com.yddmi.doctor.entity.result.SkillHistory;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/me/AdapterPopHistory;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/SkillHistory;", "Lcom/yddmi/doctor/databinding/MeItemHistoryDetailBinding;", "()V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterPopHistory extends RecyclerAdapter<SkillHistory, MeItemHistoryDetailBinding> {
    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, SkillHistory skillHistory, int i2) {
        bindViewHolder2((CommonViewHolder<MeItemHistoryDetailBinding>) commonViewHolder, skillHistory, i2);
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<MeItemHistoryDetailBinding> holder, @NotNull final SkillHistory m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<MeItemHistoryDetailBinding, Unit>() { // from class: com.yddmi.doctor.pages.me.AdapterPopHistory.bindViewHolder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeItemHistoryDetailBinding meItemHistoryDetailBinding) {
                invoke2(meItemHistoryDetailBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull MeItemHistoryDetailBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                viewBanding.f25742tv.setText(m2.getName());
                viewBanding.scoreTv.setText(String.valueOf(m2.getScore()));
                if (m2.getScore() > 0) {
                    TextView tv2 = viewBanding.f25742tv;
                    Intrinsics.checkNotNullExpressionValue(tv2, "tv");
                    int i2 = R.color.c_173a8e;
                    TextViewExtKt.setTextColorRes(tv2, i2);
                    BLTextView scoreTv = viewBanding.scoreTv;
                    Intrinsics.checkNotNullExpressionValue(scoreTv, "scoreTv");
                    TextViewExtKt.setTextColorRes(scoreTv, i2);
                    return;
                }
                TextView tv3 = viewBanding.f25742tv;
                Intrinsics.checkNotNullExpressionValue(tv3, "tv");
                int i3 = R.color.c_ff585a;
                TextViewExtKt.setTextColorRes(tv3, i3);
                BLTextView scoreTv2 = viewBanding.scoreTv;
                Intrinsics.checkNotNullExpressionValue(scoreTv2, "scoreTv");
                TextViewExtKt.setTextColorRes(scoreTv2, i3);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public MeItemHistoryDetailBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        MeItemHistoryDetailBinding meItemHistoryDetailBindingInflate = MeItemHistoryDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(meItemHistoryDetailBindingInflate, "inflate(\n            Lay…          false\n        )");
        return meItemHistoryDetailBindingInflate;
    }
}
