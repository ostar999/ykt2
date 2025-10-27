package com.yddmi.doctor.pages.main;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.databinding.MainItemRight1Binding;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.pages.main.AdapterSkill1;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0002\u0019\u001aB\u0005¢\u0006\u0002\u0010\u0004J&\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u000e\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0002J\u000e\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0006J\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\tJ\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/yddmi/doctor/pages/main/AdapterSkill1;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/SkillHome;", "Lcom/yddmi/doctor/databinding/MainItemRight1Binding;", "()V", "mListener", "Lcom/yddmi/doctor/pages/main/AdapterSkill1$OnItemChildClickListener;", "mSelectItem", "shareUnLockListener", "Lcom/yddmi/doctor/pages/main/AdapterSkill1$ShareUnlockListener;", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "setCurrentSelectItem", "setOnItemChildClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setShareUnlockListener", NotifyType.LIGHTS, "viewBinding", "parent", "Landroid/view/ViewGroup;", "OnItemChildClickListener", "ShareUnlockListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterSkill1 extends RecyclerAdapter<SkillHome, MainItemRight1Binding> {

    @Nullable
    private OnItemChildClickListener mListener;

    @Nullable
    private SkillHome mSelectItem;

    @Nullable
    private ShareUnlockListener shareUnLockListener;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lcom/yddmi/doctor/pages/main/AdapterSkill1$OnItemChildClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "position", "", "m", "Lcom/yddmi/doctor/entity/result/SkillHome;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemChildClickListener {
        void onClick(@NotNull View view, int position, @NotNull SkillHome m2);
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/main/AdapterSkill1$ShareUnlockListener;", "", "shareClick", "", "position", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface ShareUnlockListener {
        void shareClick(int position);
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/MainItemRight1Binding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.main.AdapterSkill1$bindViewHolder$1, reason: invalid class name */
    public static final class AnonymousClass1 extends Lambda implements Function1<MainItemRight1Binding, Unit> {
        final /* synthetic */ SkillHome $m;
        final /* synthetic */ int $position;
        final /* synthetic */ AdapterSkill1 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SkillHome skillHome, AdapterSkill1 adapterSkill1, int i2) {
            super(1);
            this.$m = skillHome;
            this.this$0 = adapterSkill1;
            this.$position = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(SkillHome m2, AdapterSkill1 this$0, int i2, View it) {
            Intrinsics.checkNotNullParameter(m2, "$m");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (1 == m2.isPlay() && m2.getLockStatus() != 1) {
                ShareUnlockListener shareUnlockListener = this$0.shareUnLockListener;
                if (shareUnlockListener != null) {
                    shareUnlockListener.shareClick(i2);
                    return;
                }
                return;
            }
            OnItemChildClickListener onItemChildClickListener = this$0.mListener;
            if (onItemChildClickListener != null) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                onItemChildClickListener.onClick(it, i2, m2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(SkillHome m2, AdapterSkill1 this$0, int i2, View it) {
            Intrinsics.checkNotNullParameter(m2, "$m");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (1 == m2.isPlay() && m2.getLockStatus() != 1) {
                ShareUnlockListener shareUnlockListener = this$0.shareUnLockListener;
                if (shareUnlockListener != null) {
                    shareUnlockListener.shareClick(i2);
                    return;
                }
                return;
            }
            OnItemChildClickListener onItemChildClickListener = this$0.mListener;
            if (onItemChildClickListener != null) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                onItemChildClickListener.onClick(it, i2, m2);
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(MainItemRight1Binding mainItemRight1Binding) throws Resources.NotFoundException {
            invoke2(mainItemRight1Binding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull MainItemRight1Binding viewBanding) throws Resources.NotFoundException {
            Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
            viewBanding.f25740tv.setText(this.$m.getName());
            BLTextView bLTextView = viewBanding.detailTv;
            String introduce = this.$m.getIntroduce();
            if (introduce == null) {
                introduce = "";
            }
            bLTextView.setText(introduce);
            ImageView iconImgv = viewBanding.iconImgv;
            Intrinsics.checkNotNullExpressionValue(iconImgv, "iconImgv");
            String fileFullUrl = YddHostConfig.INSTANCE.getInstance().getFileFullUrl(this.$m.getIcon());
            int i2 = R.drawable.home_icon;
            ImageViewExtKt.load(iconImgv, fileFullUrl, (261628 & 2) != 0 ? 0 : i2, (261628 & 4) != 0 ? 0 : i2, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
            ConstraintLayout rootCl = viewBanding.rootCl;
            Intrinsics.checkNotNullExpressionValue(rootCl, "rootCl");
            final SkillHome skillHome = this.$m;
            final AdapterSkill1 adapterSkill1 = this.this$0;
            final int i3 = this.$position;
            ViewExtKt.setDebounceClickListener$default(rootCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AdapterSkill1.AnonymousClass1.invoke$lambda$0(skillHome, adapterSkill1, i3, view);
                }
            }, 0L, 2, null);
            ConstraintLayout shareCl = viewBanding.shareCl;
            Intrinsics.checkNotNullExpressionValue(shareCl, "shareCl");
            final SkillHome skillHome2 = this.$m;
            final AdapterSkill1 adapterSkill12 = this.this$0;
            final int i4 = this.$position;
            ViewExtKt.setDebounceClickListener$default(shareCl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AdapterSkill1.AnonymousClass1.invoke$lambda$1(skillHome2, adapterSkill12, i4, view);
                }
            }, 0L, 2, null);
            viewBanding.starBar.setRating(this.$m.getStar());
            String string = viewBanding.contentCl.getResources().getString(R.string.product_buy_tip7);
            Intrinsics.checkNotNullExpressionValue(string, "contentCl.resources.getS….string.product_buy_tip7)");
            if (1 == this.$m.isPlay()) {
                string = viewBanding.contentCl.getResources().getString(R.string.share_unlock);
                Intrinsics.checkNotNullExpressionValue(string, "contentCl.resources.getS…ng(R.string.share_unlock)");
            }
            viewBanding.tvUnlockTitle.setText(string);
            if (1 == this.$m.getLockStatus()) {
                viewBanding.tryImgv.setVisibility(8);
                viewBanding.lockCl.setVisibility(8);
                viewBanding.unlockView.setVisibility(8);
                if (1 == this.$m.isPlay()) {
                    viewBanding.tryImgv.setVisibility(0);
                }
            } else {
                viewBanding.lockCl.setVisibility(0);
                viewBanding.unlockView.setVisibility(0);
                if (1 == this.$m.isPlay()) {
                    viewBanding.tryImgv.setVisibility(0);
                } else {
                    viewBanding.lockImgv.setVisibility(0);
                    viewBanding.tryImgv.setVisibility(4);
                }
                viewBanding.lockImgv.setImageResource(R.drawable.home_lock1);
            }
            if (this.this$0.mSelectItem != null) {
                SkillHome skillHome3 = this.this$0.mSelectItem;
                Intrinsics.checkNotNull(skillHome3);
                if (Intrinsics.areEqual(skillHome3.getId(), this.$m.getId())) {
                    viewBanding.bgImgv.setVisibility(0);
                    return;
                }
            }
            viewBanding.bgImgv.setVisibility(4);
        }
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, SkillHome skillHome, int i2) {
        bindViewHolder2((CommonViewHolder<MainItemRight1Binding>) commonViewHolder, skillHome, i2);
    }

    public final void setCurrentSelectItem(@NotNull SkillHome m2) {
        Intrinsics.checkNotNullParameter(m2, "m");
        this.mSelectItem = m2;
    }

    public final void setOnItemChildClickListener(@NotNull OnItemChildClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    public final void setShareUnlockListener(@NotNull ShareUnlockListener l2) {
        Intrinsics.checkNotNullParameter(l2, "l");
        this.shareUnLockListener = l2;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<MainItemRight1Binding> holder, @NotNull SkillHome m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new AnonymousClass1(m2, this, position));
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public MainItemRight1Binding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        MainItemRight1Binding mainItemRight1BindingInflate = MainItemRight1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(mainItemRight1BindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return mainItemRight1BindingInflate;
    }
}
