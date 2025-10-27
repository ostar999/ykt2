package com.yddmi.doctor.pages.heartlung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.widget.DotView;
import com.catchpig.utils.ext.TextViewExtKt;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.GarbledHeartlungAdapterMasteredBinding;
import com.yddmi.doctor.entity.result.HeartDetail;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J&\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/AdapterMastered;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungAdapterMasteredBinding;", "mastered", "", "(Z)V", "mMastered", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterMastered extends RecyclerAdapter<HeartDetail, GarbledHeartlungAdapterMasteredBinding> {
    private final boolean mMastered;

    public AdapterMastered() {
        this(false, 1, null);
    }

    public /* synthetic */ AdapterMastered(boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? true : z2);
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, HeartDetail heartDetail, int i2) {
        bindViewHolder2((CommonViewHolder<GarbledHeartlungAdapterMasteredBinding>) commonViewHolder, heartDetail, i2);
    }

    public AdapterMastered(boolean z2) {
        this.mMastered = z2;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull final CommonViewHolder<GarbledHeartlungAdapterMasteredBinding> holder, @NotNull final HeartDetail m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<GarbledHeartlungAdapterMasteredBinding, Unit>() { // from class: com.yddmi.doctor.pages.heartlung.AdapterMastered.bindViewHolder.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungAdapterMasteredBinding garbledHeartlungAdapterMasteredBinding) {
                invoke2(garbledHeartlungAdapterMasteredBinding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull GarbledHeartlungAdapterMasteredBinding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                if (AdapterMastered.this.mMastered) {
                    TextView textView = viewBanding.nameTv;
                    int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition() + 1;
                    String name = m2.getName();
                    textView.setText(absoluteAdapterPosition + StrPool.DOT + (name != null ? name : ""));
                    TextView nameTv = viewBanding.nameTv;
                    Intrinsics.checkNotNullExpressionValue(nameTv, "nameTv");
                    TextViewExtKt.setTextColorRes(nameTv, R.color.c_50536b);
                    if (m2.isMastered() == 0) {
                        DotView dotView = viewBanding.dotV;
                        Context context = viewBanding.nameTv.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "nameTv.context");
                        dotView.setDotColor(ContextExtKt.getColorM(context, R.color.c_e7e9f2));
                    } else {
                        DotView dotView2 = viewBanding.dotV;
                        Context context2 = viewBanding.nameTv.getContext();
                        Intrinsics.checkNotNullExpressionValue(context2, "nameTv.context");
                        dotView2.setDotColor(ContextExtKt.getColorM(context2, R.color.c_3776ff));
                    }
                } else {
                    TextView textView2 = viewBanding.nameTv;
                    int absoluteAdapterPosition2 = holder.getAbsoluteAdapterPosition() + 1;
                    String categoryName = m2.getCategoryName();
                    textView2.setText(absoluteAdapterPosition2 + StrPool.DOT + (categoryName != null ? categoryName : ""));
                    TextView nameTv2 = viewBanding.nameTv;
                    Intrinsics.checkNotNullExpressionValue(nameTv2, "nameTv");
                    TextViewExtKt.setTextColorRes(nameTv2, R.color.c_2f2f46);
                    viewBanding.dotV.setVisibility(8);
                }
                viewBanding.vipTag.setVisibility(8);
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public GarbledHeartlungAdapterMasteredBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        GarbledHeartlungAdapterMasteredBinding garbledHeartlungAdapterMasteredBindingInflate = GarbledHeartlungAdapterMasteredBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(garbledHeartlungAdapterMasteredBindingInflate, "inflate(\n            Lay…, parent, false\n        )");
        return garbledHeartlungAdapterMasteredBindingInflate;
    }
}
