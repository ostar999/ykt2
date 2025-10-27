package com.yddmi.doctor.pages.product;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.noober.background.drawable.DrawableCreator;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.ProductItemSkillBinding;
import com.yddmi.doctor.entity.result.SkillHome;
import com.zhpan.bannerview.utils.BannerUtils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0005J&\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u000e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0002J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/yddmi/doctor/pages/product/AdapterSkill;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/SkillHome;", "Lcom/yddmi/doctor/databinding/ProductItemSkillBinding;", "skill", "(Lcom/yddmi/doctor/entity/result/SkillHome;)V", "mSkill", "noSelect", "Landroid/graphics/drawable/Drawable;", "kotlin.jvm.PlatformType", "select", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "setCurrentSkill", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterSkill extends RecyclerAdapter<SkillHome, ProductItemSkillBinding> {

    @Nullable
    private SkillHome mSkill;
    private final Drawable noSelect;
    private final Drawable select;

    public AdapterSkill(@Nullable SkillHome skillHome) {
        this.mSkill = skillHome;
        DrawableCreator.Builder cornersRadius = new DrawableCreator.Builder().setCornersRadius(BannerUtils.dp2px(6.0f));
        ContextManager.Companion companion = ContextManager.INSTANCE;
        this.select = cornersRadius.setSolidColor(ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.color_white)).setStrokeColor(ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.c_fd600d)).setStrokeWidth(BannerUtils.dp2px(1.0f)).build();
        this.noSelect = new DrawableCreator.Builder().setCornersRadius(BannerUtils.dp2px(6.0f)).setSolidColor(ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.c_f9f9f9)).setStrokeColor(ContextExtKt.getColorM(companion.getInstance().getContext(), R.color.c_f5f5f5)).setStrokeWidth(BannerUtils.dp2px(1.0f)).build();
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, SkillHome skillHome, int i2) {
        bindViewHolder2((CommonViewHolder<ProductItemSkillBinding>) commonViewHolder, skillHome, i2);
    }

    public final void setCurrentSkill(@NotNull SkillHome skill) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        this.mSkill = skill;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<ProductItemSkillBinding> holder, @NotNull final SkillHome m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<ProductItemSkillBinding, Unit>() { // from class: com.yddmi.doctor.pages.product.AdapterSkill.bindViewHolder.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ProductItemSkillBinding productItemSkillBinding) {
                invoke2(productItemSkillBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ProductItemSkillBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                viewBanding.nameTv.setText(m2.getName());
                if (this.mSkill == null) {
                    TextView nameTv = viewBanding.nameTv;
                    Intrinsics.checkNotNullExpressionValue(nameTv, "nameTv");
                    Drawable noSelect = this.noSelect;
                    Intrinsics.checkNotNullExpressionValue(noSelect, "noSelect");
                    ViewExtKt.setBackgroundJellyBean16(nameTv, noSelect);
                    return;
                }
                SkillHome skillHome = this.mSkill;
                Intrinsics.checkNotNull(skillHome);
                if (Intrinsics.areEqual(skillHome.getSkillId(), m2.getSkillId())) {
                    TextView nameTv2 = viewBanding.nameTv;
                    Intrinsics.checkNotNullExpressionValue(nameTv2, "nameTv");
                    Drawable select = this.select;
                    Intrinsics.checkNotNullExpressionValue(select, "select");
                    ViewExtKt.setBackgroundJellyBean16(nameTv2, select);
                    return;
                }
                TextView nameTv3 = viewBanding.nameTv;
                Intrinsics.checkNotNullExpressionValue(nameTv3, "nameTv");
                Drawable noSelect2 = this.noSelect;
                Intrinsics.checkNotNullExpressionValue(noSelect2, "noSelect");
                ViewExtKt.setBackgroundJellyBean16(nameTv3, noSelect2);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public ProductItemSkillBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ProductItemSkillBinding productItemSkillBindingInflate = ProductItemSkillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(productItemSkillBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return productItemSkillBindingInflate;
    }
}
