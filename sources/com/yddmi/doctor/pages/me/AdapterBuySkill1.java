package com.yddmi.doctor.pages.me;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.databinding.MeItemBuy1Binding;
import com.yddmi.doctor.entity.result.SkillHome;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/me/AdapterBuySkill1;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/SkillHome;", "Lcom/yddmi/doctor/databinding/MeItemBuy1Binding;", "()V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterBuySkill1 extends RecyclerAdapter<SkillHome, MeItemBuy1Binding> {
    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, SkillHome skillHome, int i2) {
        bindViewHolder2((CommonViewHolder<MeItemBuy1Binding>) commonViewHolder, skillHome, i2);
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<MeItemBuy1Binding> holder, @NotNull final SkillHome m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new Function1<MeItemBuy1Binding, Unit>() { // from class: com.yddmi.doctor.pages.me.AdapterBuySkill1.bindViewHolder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(MeItemBuy1Binding meItemBuy1Binding) {
                invoke2(meItemBuy1Binding);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull MeItemBuy1Binding viewBanding) {
                Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
                viewBanding.f25741tv.setText(m2.getName());
                BLTextView bLTextView = viewBanding.detailTv;
                String introduce = m2.getIntroduce();
                if (introduce == null) {
                    introduce = "";
                }
                bLTextView.setText(introduce);
                ImageView iconImgv = viewBanding.iconImgv;
                Intrinsics.checkNotNullExpressionValue(iconImgv, "iconImgv");
                String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(m2.getIcon());
                int i2 = R.drawable.home_icon;
                ImageViewExtKt.load(iconImgv, fileFullUrl, (261628 & 2) != 0 ? 0 : i2, (261628 & 4) != 0 ? 0 : i2, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
                TextView textView = viewBanding.createTimeTv;
                String string = textView.getContext().getString(R.string.me_createtime);
                String createTime = m2.getCreateTime();
                if (createTime == null) {
                    createTime = "";
                }
                textView.setText(string + createTime);
                TextView textView2 = viewBanding.endTimeTv;
                String string2 = viewBanding.createTimeTv.getContext().getString(R.string.me_endtime);
                String maturityTime = m2.getMaturityTime();
                textView2.setText(string2 + (maturityTime != null ? maturityTime : ""));
            }
        });
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public MeItemBuy1Binding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        MeItemBuy1Binding meItemBuy1BindingInflate = MeItemBuy1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(meItemBuy1BindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return meItemBuy1BindingInflate;
    }
}
