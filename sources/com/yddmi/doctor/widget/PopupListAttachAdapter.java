package com.yddmi.doctor.widget;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.yddmi.doctor.databinding.PopupListAttachItemBinding;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00030\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\"\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00062\b\b\u0001\u0010\u0011\u001a\u00020\u00062\b\b\u0001\u0010\u0012\u001a\u00020\u0006J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/yddmi/doctor/widget/PopupListAttachAdapter;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "", "Lcom/yddmi/doctor/databinding/PopupListAttachItemBinding;", "()V", "mIndex", "", "mNomalColorInt", "mSelectColorInt", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "setCurrentIndex", "index", "selectColor", "nomalColor", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupListAttachAdapter extends RecyclerAdapter<String, PopupListAttachItemBinding> {
    private int mIndex;
    private int mNomalColorInt;
    private int mSelectColorInt;

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, String str, int i2) {
        bindViewHolder2((CommonViewHolder<PopupListAttachItemBinding>) commonViewHolder, str, i2);
    }

    public final void setCurrentIndex(int index, @ColorInt int selectColor, @ColorInt int nomalColor) {
        this.mIndex = index;
        this.mSelectColorInt = selectColor;
        this.mNomalColorInt = nomalColor;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<PopupListAttachItemBinding> holder, @NotNull final String m2, final int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<PopupListAttachItemBinding, Unit>() { // from class: com.yddmi.doctor.widget.PopupListAttachAdapter.bindViewHolder.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PopupListAttachItemBinding popupListAttachItemBinding) {
                invoke2(popupListAttachItemBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull PopupListAttachItemBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                if (PopupListAttachAdapter.this.mIndex == position) {
                    viewBanding.f25743tv.setTextColor(PopupListAttachAdapter.this.mSelectColorInt);
                } else {
                    viewBanding.f25743tv.setTextColor(PopupListAttachAdapter.this.mNomalColorInt);
                }
                viewBanding.f25743tv.setText(m2);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public PopupListAttachItemBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        PopupListAttachItemBinding popupListAttachItemBindingInflate = PopupListAttachItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(popupListAttachItemBindingInflate, "inflate(\n            Lay…, parent, false\n        )");
        return popupListAttachItemBindingInflate;
    }
}
