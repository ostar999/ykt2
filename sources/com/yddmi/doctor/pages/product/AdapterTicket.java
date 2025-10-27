package com.yddmi.doctor.pages.product;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.ext.StringExtKt;
import com.catchpig.utils.ext.TextViewExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.ProductItemTicketBinding;
import com.yddmi.doctor.entity.result.SkillTicket;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/yddmi/doctor/pages/product/AdapterTicket;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/SkillTicket;", "Lcom/yddmi/doctor/databinding/ProductItemTicketBinding;", "()V", "mCurrentTicket", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "setCurrentData", "current", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterTicket extends RecyclerAdapter<SkillTicket, ProductItemTicketBinding> {

    @Nullable
    private SkillTicket mCurrentTicket;

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, SkillTicket skillTicket, int i2) {
        bindViewHolder2((CommonViewHolder<ProductItemTicketBinding>) commonViewHolder, skillTicket, i2);
    }

    public final void setCurrentData(@Nullable SkillTicket current) {
        this.mCurrentTicket = current;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<ProductItemTicketBinding> holder, @NotNull final SkillTicket m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<ProductItemTicketBinding, Unit>() { // from class: com.yddmi.doctor.pages.product.AdapterTicket.bindViewHolder.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ProductItemTicketBinding productItemTicketBinding) {
                invoke2(productItemTicketBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ProductItemTicketBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                TextView textView = viewBanding.saleTv;
                String couponAmount = m2.getCouponAmount();
                textView.setText(StringExtKt.get2EffectiveNum(couponAmount != null ? Float.valueOf(Float.parseFloat(couponAmount)) : null) + ContextManager.INSTANCE.getInstance().getContext().getString(R.string.product_ticket4));
                TextView textView2 = viewBanding.tipTv;
                String typeName = m2.getTypeName();
                if (typeName == null) {
                    typeName = "";
                }
                textView2.setText(typeName);
                TextView textView3 = viewBanding.nameTv;
                String couponRecord = m2.getCouponRecord();
                textView3.setText(couponRecord != null ? couponRecord : "");
                viewBanding.timeTv.setText(m2.getClaimTime() + "-" + m2.getEnableTime());
                SkillTicket skillTicket = this.mCurrentTicket;
                if (Intrinsics.areEqual(skillTicket != null ? skillTicket.getCouponId() : null, m2.getCouponId())) {
                    viewBanding.selectImgv.setImageResource(R.drawable.common_select1);
                } else {
                    viewBanding.selectImgv.setImageResource(R.drawable.common_unselect);
                }
                if (m2.getCanUse() == 0) {
                    viewBanding.ticketCl.setBackgroundResource(R.drawable.product_ticket_can);
                    TextView nameTv = viewBanding.nameTv;
                    Intrinsics.checkNotNullExpressionValue(nameTv, "nameTv");
                    TextViewExtKt.setTextColorRes(nameTv, R.color.color_black);
                    return;
                }
                viewBanding.ticketCl.setBackgroundResource(R.drawable.product_ticket_cannot);
                TextView nameTv2 = viewBanding.nameTv;
                Intrinsics.checkNotNullExpressionValue(nameTv2, "nameTv");
                TextViewExtKt.setTextColorRes(nameTv2, R.color.c_7f7f7f);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public ProductItemTicketBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ProductItemTicketBinding productItemTicketBindingInflate = ProductItemTicketBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(productItemTicketBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return productItemTicketBindingInflate;
    }
}
