package com.yddmi.doctor.pages.heartlung;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.SpanExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.TextViewExtKt;
import com.yddmi.doctor.R;
import com.yddmi.doctor.databinding.GarbledHeartlungAdapterScoreBinding;
import com.yddmi.doctor.entity.result.TreeNodesItem;
import com.yddmi.doctor.pages.heartlung.AdapterScore;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/AdapterScore;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/TreeNodesItem;", "Lcom/yddmi/doctor/databinding/GarbledHeartlungAdapterScoreBinding;", "()V", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "", "viewBinding", "parent", "Landroid/view/ViewGroup;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class AdapterScore extends RecyclerAdapter<TreeNodesItem, GarbledHeartlungAdapterScoreBinding> {

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/GarbledHeartlungAdapterScoreBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.heartlung.AdapterScore$bindViewHolder$1, reason: invalid class name */
    public static final class AnonymousClass1 extends Lambda implements Function1<GarbledHeartlungAdapterScoreBinding, Unit> {
        final /* synthetic */ TreeNodesItem $m;
        final /* synthetic */ int $position;
        final /* synthetic */ AdapterScore this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(TreeNodesItem treeNodesItem, AdapterScore adapterScore, int i2) {
            super(1);
            this.$m = treeNodesItem;
            this.this$0 = adapterScore;
            this.$position = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(TreeNodesItem m2, AdapterScore this$0, int i2, View view) {
            Intrinsics.checkNotNullParameter(m2, "$m");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            m2.setMShow(!m2.getMShow());
            this$0.notifyItemChanged(i2);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(GarbledHeartlungAdapterScoreBinding garbledHeartlungAdapterScoreBinding) {
            invoke2(garbledHeartlungAdapterScoreBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull GarbledHeartlungAdapterScoreBinding viewBanding) {
            String str;
            Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
            int level = this.$m.getLevel();
            boolean z2 = true;
            if (level == 1) {
                viewBanding.f25735tv.setTextSize(16.0f);
                String seq = this.$m.getSeq();
                if (seq == null || seq.length() == 0) {
                    TextView textView = viewBanding.f25735tv;
                    String primaryCategory = this.$m.getPrimaryCategory();
                    textView.setText(primaryCategory != null ? primaryCategory : "");
                } else {
                    TextView textView2 = viewBanding.f25735tv;
                    String primaryCategory2 = this.$m.getPrimaryCategory();
                    textView2.setText(primaryCategory2 != null ? primaryCategory2 : "");
                }
                TextView tv2 = viewBanding.f25735tv;
                Intrinsics.checkNotNullExpressionValue(tv2, "tv");
                TextViewExtKt.setTextColorRes(tv2, R.color.c_2f2f46);
                viewBanding.f25735tv.setTypeface(Typeface.defaultFromStyle(1));
                return;
            }
            if (level == 2) {
                viewBanding.f25735tv.setTextSize(14.0f);
                String secondaryCategory = this.$m.getSecondaryCategory();
                if (secondaryCategory == null || secondaryCategory.length() == 0) {
                    viewBanding.f25735tv.setText("");
                } else {
                    viewBanding.f25735tv.setText(String.valueOf(this.$m.getSecondaryCategory()));
                }
                TextView tv3 = viewBanding.f25735tv;
                Intrinsics.checkNotNullExpressionValue(tv3, "tv");
                TextViewExtKt.setTextColorRes(tv3, R.color.c_2f2f46);
                viewBanding.f25735tv.setTypeface(Typeface.defaultFromStyle(1));
                return;
            }
            viewBanding.f25735tv.setTextSize(14.0f);
            viewBanding.f25735tv.setTypeface(Typeface.defaultFromStyle(0));
            String ratingItem = this.$m.getRatingItem();
            if (ratingItem != null && ratingItem.length() != 0) {
                z2 = false;
            }
            if (z2) {
                str = " ";
            } else {
                str = this.$m.getRatingItem() + " \t";
            }
            String strValueOf = String.valueOf(str);
            viewBanding.f25735tv.setText(strValueOf);
            if (this.$m.getMShow()) {
                TextView tv4 = viewBanding.f25735tv;
                Intrinsics.checkNotNullExpressionValue(tv4, "tv");
                TextViewExtKt.setTextColorRes(tv4, R.color.c_50536b);
            } else {
                int length = strValueOf.length();
                TextView tv5 = viewBanding.f25735tv;
                Intrinsics.checkNotNullExpressionValue(tv5, "tv");
                IntRange intRange = new IntRange(0, length);
                Context context = viewBanding.f25735tv.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "tv.context");
                int i2 = R.color.c_aecae9;
                SpanExtKt.backgroundColorLineHeightSpan(tv5, "", intRange, ContextExtKt.getColorM(context, i2));
                TextView tv6 = viewBanding.f25735tv;
                Intrinsics.checkNotNullExpressionValue(tv6, "tv");
                TextViewExtKt.setTextColorRes(tv6, i2);
            }
            TextView tv7 = viewBanding.f25735tv;
            Intrinsics.checkNotNullExpressionValue(tv7, "tv");
            final TreeNodesItem treeNodesItem = this.$m;
            final AdapterScore adapterScore = this.this$0;
            final int i3 = this.$position;
            ViewExtKt.setDebounceClickListener$default(tv7, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AdapterScore.AnonymousClass1.invoke$lambda$0(treeNodesItem, adapterScore, i3, view);
                }
            }, 0L, 2, null);
        }
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, TreeNodesItem treeNodesItem, int i2) {
        bindViewHolder2((CommonViewHolder<GarbledHeartlungAdapterScoreBinding>) commonViewHolder, treeNodesItem, i2);
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<GarbledHeartlungAdapterScoreBinding> holder, @NotNull TreeNodesItem m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new AnonymousClass1(m2, this, position));
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public GarbledHeartlungAdapterScoreBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        GarbledHeartlungAdapterScoreBinding garbledHeartlungAdapterScoreBindingInflate = GarbledHeartlungAdapterScoreBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(garbledHeartlungAdapterScoreBindingInflate, "inflate(\n            Lay…, parent, false\n        )");
        return garbledHeartlungAdapterScoreBindingInflate;
    }
}
