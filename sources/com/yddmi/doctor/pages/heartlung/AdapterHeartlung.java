package com.yddmi.doctor.pages.heartlung;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.yddmi.doctor.databinding.GarbledHeartlungAdapterBinding;
import com.yddmi.doctor.entity.result.HeartDetail;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/AdapterHeartlung;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungAdapterBinding;", "()V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterHeartlung extends RecyclerAdapter<HeartDetail, GarbledHeartlungAdapterBinding> {
    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, HeartDetail heartDetail, int i2) {
        bindViewHolder2((CommonViewHolder<GarbledHeartlungAdapterBinding>) commonViewHolder, heartDetail, i2);
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<GarbledHeartlungAdapterBinding> holder, @NotNull final HeartDetail m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<GarbledHeartlungAdapterBinding, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.AdapterHeartlung.bindViewHolder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungAdapterBinding garbledHeartlungAdapterBinding) {
                invoke2(garbledHeartlungAdapterBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull GarbledHeartlungAdapterBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                viewBanding.nameTv.setText(m2.getCategoryName());
                viewBanding.numTv.setText("(" + m2.getMasteredNum() + "/" + m2.getTotalNum() + ")");
                if (1 == m2.getContinueFlag()) {
                    viewBanding.goingTv.setVisibility(0);
                } else {
                    viewBanding.goingTv.setVisibility(4);
                }
                viewBanding.progressBar.setMax(m2.getTotalNum());
                viewBanding.progressBar.setProgress(m2.getMasteredNum());
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public GarbledHeartlungAdapterBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        GarbledHeartlungAdapterBinding garbledHeartlungAdapterBindingInflate = GarbledHeartlungAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(garbledHeartlungAdapterBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return garbledHeartlungAdapterBindingInflate;
    }
}
