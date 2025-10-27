package com.yddmi.doctor.pages.exam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.catchpig.mvvm.base.adapter.CommonViewHolder;
import com.catchpig.mvvm.base.adapter.RecyclerAdapter;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.databinding.ExamAdapterTopTreeBinding;
import com.yddmi.doctor.entity.result.SkillHome;
import com.yddmi.doctor.pages.exam.AdapterTopTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \"2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004:\u0002\"#B\u0005¢\u0006\u0002\u0010\u0005J&\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tH\u0016J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0002H\u0002J\u0018\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tH\u0016J\u0018\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tH\u0016J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tH\u0016J\u000e\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0002J\u0016\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tJ\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\rJ\u0010\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020!H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/yddmi/doctor/pages/exam/AdapterTopTree;", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "Lcom/yddmi/doctor/entity/result/SkillHome;", "Lcom/yddmi/doctor/databinding/ExamAdapterTopTreeBinding;", "Lcom/yddmi/doctor/pages/exam/TreeStateChangeListener;", "()V", "mCurrentSkill", "mCurrentSkillFolder", "mCurrentSkillFolderIndex", "", "mCurrentSkillFolderName", "", "mListener", "Lcom/yddmi/doctor/pages/exam/AdapterTopTree$OnItemClickListenerAbsolutePosition;", "bindViewHolder", "", "holder", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "m", "position", "closeChild", "itemTree", "onClose", "treeItem", "onOpen", "onOpenOnlyOne", "setCurrentSkill", "skill", "setCurrentSkillFolder", "setOnItemClickListenerAbsolutePosition", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewBinding", "parent", "Landroid/view/ViewGroup;", "Companion", "OnItemClickListenerAbsolutePosition", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAdapterTopTree.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AdapterTopTree.kt\ncom/yddmi/doctor/pages/exam/AdapterTopTree\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,165:1\n1855#2,2:166\n*S KotlinDebug\n*F\n+ 1 AdapterTopTree.kt\ncom/yddmi/doctor/pages/exam/AdapterTopTree\n*L\n131#1:166,2\n*E\n"})
/* loaded from: classes6.dex */
public final class AdapterTopTree extends RecyclerAdapter<SkillHome, ExamAdapterTopTreeBinding> implements TreeStateChangeListener {
    public static final int ITEM_STATE_CLOSE = 0;
    public static final int ITEM_STATE_OPEN = 1;

    @Nullable
    private SkillHome mCurrentSkill;

    @Nullable
    private SkillHome mCurrentSkillFolder;
    private int mCurrentSkillFolderIndex;

    @NotNull
    private String mCurrentSkillFolderName = "";

    @Nullable
    private OnItemClickListenerAbsolutePosition mListener;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/pages/exam/AdapterTopTree$OnItemClickListenerAbsolutePosition;", "", "onItemClickListenerAbsolutePosition", "", "data", "Lcom/yddmi/doctor/entity/result/SkillHome;", "absolutePosition", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemClickListenerAbsolutePosition {
        void onItemClickListenerAbsolutePosition(@NotNull SkillHome data, int absolutePosition);
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lcom/yddmi/doctor/databinding/ExamAdapterTopTreeBinding;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.yddmi.doctor.pages.exam.AdapterTopTree$bindViewHolder$1, reason: invalid class name */
    public static final class AnonymousClass1 extends Lambda implements Function1<ExamAdapterTopTreeBinding, Unit> {
        final /* synthetic */ CommonViewHolder<ExamAdapterTopTreeBinding> $holder;
        final /* synthetic */ SkillHome $m;
        final /* synthetic */ AdapterTopTree this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SkillHome skillHome, AdapterTopTree adapterTopTree, CommonViewHolder<ExamAdapterTopTreeBinding> commonViewHolder) {
            super(1);
            this.$m = skillHome;
            this.this$0 = adapterTopTree;
            this.$holder = commonViewHolder;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(AdapterTopTree this$0, SkillHome m2, CommonViewHolder holder, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(m2, "$m");
            Intrinsics.checkNotNullParameter(holder, "$holder");
            OnItemClickListenerAbsolutePosition onItemClickListenerAbsolutePosition = this$0.mListener;
            if (onItemClickListenerAbsolutePosition != null) {
                onItemClickListenerAbsolutePosition.onItemClickListenerAbsolutePosition(m2, holder.getAbsoluteAdapterPosition());
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(ExamAdapterTopTreeBinding examAdapterTopTreeBinding) {
            invoke2(examAdapterTopTreeBinding);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(@NotNull ExamAdapterTopTreeBinding viewBanding) {
            Intrinsics.checkNotNullParameter(viewBanding, "$this$viewBanding");
            FrameLayout fl = viewBanding.fl;
            Intrinsics.checkNotNullExpressionValue(fl, "fl");
            final AdapterTopTree adapterTopTree = this.this$0;
            final SkillHome skillHome = this.$m;
            final CommonViewHolder<ExamAdapterTopTreeBinding> commonViewHolder = this.$holder;
            ViewExtKt.setDebounceClickListener$default(fl, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.exam.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AdapterTopTree.AnonymousClass1.invoke$lambda$0(adapterTopTree, skillHome, commonViewHolder, view);
                }
            }, 0L, 2, null);
            viewBanding.f25734tv.setText(this.$m.getName());
            if (1 == this.$m.getMFolder()) {
                viewBanding.f25734tv.setTypeface(null, 1);
                if (Intrinsics.areEqual(this.this$0.mCurrentSkillFolderName, this.$m.getName())) {
                    viewBanding.aImgv.setImageResource(R.drawable.exam_top_down);
                } else {
                    viewBanding.aImgv.setImageResource(R.drawable.exam_top_right);
                }
                viewBanding.aImgv.setVisibility(0);
                viewBanding.imgv.setImageResource(R.drawable.exam_top_bg6);
                return;
            }
            viewBanding.f25734tv.setTypeface(null, 0);
            viewBanding.aImgv.setVisibility(4);
            if (this.this$0.mCurrentSkill != null) {
                SkillHome skillHome2 = this.this$0.mCurrentSkill;
                Intrinsics.checkNotNull(skillHome2);
                if (Intrinsics.areEqual(skillHome2.getName(), this.$m.getName())) {
                    viewBanding.imgv.setImageResource(R.drawable.exam_top_bg9);
                    return;
                }
            }
            viewBanding.imgv.setImageResource(R.drawable.exam_top_bg7);
        }
    }

    private final void closeChild(SkillHome itemTree) {
        List<SkillHome> children = itemTree.getChildren();
        if (children != null) {
            for (SkillHome skillHome : children) {
                if (skillHome != null) {
                    skillHome.setMItemState(0);
                    closeChild(skillHome);
                }
            }
        }
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    public /* bridge */ /* synthetic */ void bindViewHolder(CommonViewHolder commonViewHolder, SkillHome skillHome, int i2) {
        bindViewHolder2((CommonViewHolder<ExamAdapterTopTreeBinding>) commonViewHolder, skillHome, i2);
    }

    @Override // com.yddmi.doctor.pages.exam.TreeStateChangeListener
    public void onClose(@NotNull SkillHome treeItem, int position) {
        Intrinsics.checkNotNullParameter(treeItem, "treeItem");
        LogExtKt.logd("onClose 列表关闭 " + position + "  " + this.mCurrentSkillFolderIndex, YddConfig.TAG);
        if (treeItem.getMFolder() == 1) {
            if (this.mCurrentSkillFolder != null) {
                String name = treeItem.getName();
                SkillHome skillHome = this.mCurrentSkillFolder;
                Intrinsics.checkNotNull(skillHome);
                if (Intrinsics.areEqual(name, skillHome.getName())) {
                    this.mCurrentSkillFolderName = "";
                }
            }
            int size = getData().size() - 1;
            int i2 = position + 1;
            if (getData().size() > i2) {
                SkillHome skillHome2 = getData().get(position);
                int size2 = getData().size();
                int i3 = i2;
                while (true) {
                    if (i3 >= size2) {
                        break;
                    }
                    SkillHome skillHome3 = getData().get(i3);
                    if (skillHome3 != null && skillHome2 != null) {
                        skillHome3.getMLevel();
                        skillHome2.getMLevel();
                        if (skillHome3.getMLevel() <= skillHome2.getMLevel()) {
                            size = i3 - 1;
                            break;
                        }
                    }
                    i3++;
                }
                if (skillHome2 != null) {
                    closeChild(skillHome2);
                }
                if (size > position) {
                    getData().subList(i2, size + 1).clear();
                    treeItem.setMItemState(0);
                    notifyItemRangeRemoved(i2, size - position);
                    notifyItemChanged(position);
                }
            }
        }
    }

    @Override // com.yddmi.doctor.pages.exam.TreeStateChangeListener
    public void onOpen(@NotNull SkillHome treeItem, int position) {
        Intrinsics.checkNotNullParameter(treeItem, "treeItem");
        if (1 == treeItem.getMFolder()) {
            if (this.mCurrentSkillFolder != null) {
                LogExtKt.logd("关闭其它已打开目录", YddConfig.TAG);
                SkillHome skillHome = this.mCurrentSkillFolder;
                Intrinsics.checkNotNull(skillHome);
                onClose(skillHome, this.mCurrentSkillFolderIndex);
            }
            this.mCurrentSkillFolder = treeItem;
            this.mCurrentSkillFolderName = treeItem.getName();
            this.mCurrentSkillFolderIndex = position;
            LogExtKt.logd("onOpen 列表打开 " + position + "  " + position, YddConfig.TAG);
            treeItem.setMItemState(1);
            if (treeItem.getChildren() != null) {
                int i2 = position + 1;
                getData().addAll(i2, treeItem.getChildren());
                notifyItemRangeInserted(i2, treeItem.getChildren().size());
            }
            notifyItemChanged(position);
        }
    }

    @Override // com.yddmi.doctor.pages.exam.TreeStateChangeListener
    public void onOpenOnlyOne(@NotNull SkillHome treeItem, int position) {
        Intrinsics.checkNotNullParameter(treeItem, "treeItem");
    }

    public final void setCurrentSkill(@NotNull SkillHome skill) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        this.mCurrentSkill = skill;
        notifyDataSetChanged();
    }

    public final void setCurrentSkillFolder(@NotNull SkillHome skill, int position) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        this.mCurrentSkillFolder = skill;
        this.mCurrentSkillFolderName = skill.getName();
        this.mCurrentSkillFolderIndex = position;
    }

    public final void setOnItemClickListenerAbsolutePosition(@NotNull OnItemClickListenerAbsolutePosition listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    /* renamed from: bindViewHolder, reason: avoid collision after fix types in other method */
    public void bindViewHolder2(@NotNull CommonViewHolder<ExamAdapterTopTreeBinding> holder, @NotNull SkillHome m2, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(m2, "m");
        holder.viewBanding(new AnonymousClass1(m2, this, holder));
    }

    @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter
    @NotNull
    public ExamAdapterTopTreeBinding viewBinding(@NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ExamAdapterTopTreeBinding examAdapterTopTreeBindingInflate = ExamAdapterTopTreeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(examAdapterTopTreeBindingInflate, "inflate(LayoutInflater.f….context), parent, false)");
        return examAdapterTopTreeBindingInflate;
    }
}
