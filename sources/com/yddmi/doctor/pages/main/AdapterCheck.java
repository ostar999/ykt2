package com.yddmi.doctor.pages.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.utils.ext.TextViewExtKt;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.MainItemLeftBinding;
import com.yddmi.doctor.entity.result.SkillHome;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J&\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/yddmi/doctor/pages/main/AdapterCheck;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/SkillHome;", "Lcom/yddmi/doctor/databinding/MainItemLeftBinding;", "itemBgId", "", "(I)V", "mItemBgId", "getMItemBgId", "()I", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterCheck extends RecyclerAdapter<SkillHome, MainItemLeftBinding> {
    private final int mItemBgId;

    public AdapterCheck(@DrawableRes int i2) {
        this.mItemBgId = i2;
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, SkillHome skillHome, int i2) {
        bindViewHolder2((CommonViewHolder<MainItemLeftBinding>) commonViewHolder, skillHome, i2);
    }

    public final int getMItemBgId() {
        return this.mItemBgId;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<MainItemLeftBinding> holder, @NotNull final SkillHome m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<MainItemLeftBinding, Unit>() { // from class: com.yddmi.doctor.pages.main.AdapterCheck.bindViewHolder.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MainItemLeftBinding mainItemLeftBinding) {
                invoke2(mainItemLeftBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull MainItemLeftBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                BLTextView bLTextView = viewBanding.f25739tv;
                String label = m2.getLabel();
                SkillHome skillHome = m2;
                if (label.length() == 0) {
                    label = skillHome.getName();
                }
                bLTextView.setText(label);
                if (m2.getMSelected()) {
                    viewBanding.f25739tv.setBackgroundResource(this.getMItemBgId());
                    BLTextView tv2 = viewBanding.f25739tv;
                    Intrinsics.checkNotNullExpressionValue(tv2, "tv");
                    TextViewExtKt.setTextColorRes(tv2, R.color.color_white);
                    return;
                }
                viewBanding.f25739tv.setBackground(null);
                BLTextView tv3 = viewBanding.f25739tv;
                Intrinsics.checkNotNullExpressionValue(tv3, "tv");
                TextViewExtKt.setTextColorRes(tv3, R.color.c_173a8e);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public MainItemLeftBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        MainItemLeftBinding mainItemLeftBindingInflate = MainItemLeftBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(mainItemLeftBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return mainItemLeftBindingInflate;
    }
}
