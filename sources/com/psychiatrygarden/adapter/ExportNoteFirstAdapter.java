package com.psychiatrygarden.adapter;

import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.adapter.ExportNoteAdapter;
import com.yikaobang.yixue.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J&\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016J\u0018\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J\u0018\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\b¨\u0006\u001c"}, d2 = {"Lcom/psychiatrygarden/adapter/ExportNoteFirstAdapter;", "Lcom/chad/library/adapter/base/provider/BaseNodeProvider;", NotifyType.LIGHTS, "Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;", "(Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;)V", "itemViewType", "", "getItemViewType", "()I", "getL", "()Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;", "layoutId", "getLayoutId", "convert", "", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "Lcom/chad/library/adapter/base/entity/node/BaseNode;", "payloads", "", "", "expandOrCollapse", "pos", "updateSelectIcon", "Lcom/psychiatrygarden/activity/online/bean/QuestionBankNewBean$DataBean;", "view", "Landroid/view/View;", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nExportNoteFirstAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExportNoteFirstAdapter.kt\ncom/psychiatrygarden/adapter/ExportNoteFirstAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,95:1\n1864#2,3:96\n*S KotlinDebug\n*F\n+ 1 ExportNoteFirstAdapter.kt\ncom/psychiatrygarden/adapter/ExportNoteFirstAdapter\n*L\n58#1:96,3\n*E\n"})
/* loaded from: classes5.dex */
public final class ExportNoteFirstAdapter extends BaseNodeProvider {

    @NotNull
    private final ExportNoteAdapter.SelectListener l;

    public ExportNoteFirstAdapter(@NotNull ExportNoteAdapter.SelectListener l2) {
        Intrinsics.checkNotNullParameter(l2, "l");
        this.l = l2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(ExportNoteFirstAdapter this$0, BaseNode item, BaseViewHolder helper, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(helper, "$helper");
        this$0.expandOrCollapse(item, helper.getLayoutPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$2(BaseNode item, QuestionBankNewBean.DataBean newItem, ExportNoteFirstAdapter this$0, BaseViewHolder helper, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(newItem, "$newItem");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(helper, "$helper");
        view.setSelected(!view.isSelected());
        boolean zIsSelected = view.isSelected();
        ((QuestionBankNewBean.DataBean) item).setIsSelected(zIsSelected ? 1 : 0);
        List<QuestionBankNewBean.DataBean.ChildrenBean> children = newItem.getChildren();
        if (children == null || children.size() <= 0) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        int count = 0;
        for (Object obj : children) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            QuestionBankNewBean.DataBean.ChildrenBean childrenBean = (QuestionBankNewBean.DataBean.ChildrenBean) obj;
            childrenBean.setIsSelected(zIsSelected ? 1 : 0);
            if (zIsSelected) {
                i2++;
                count += childrenBean.getCount();
            }
            BaseProviderMultiAdapter<BaseNode> adapter2 = this$0.getAdapter2();
            if (adapter2 != null) {
                adapter2.notifyItemChanged(helper.getLayoutPosition() + i3 + (i3 == 0 ? 1 : 0), 1);
            }
            i3 = i4;
        }
        if (i2 == children.size()) {
            newItem.setIsSelected(1);
        } else if (i2 > 0) {
            newItem.setIsSelected(2);
        } else {
            newItem.setIsSelected(0);
        }
        BaseProviderMultiAdapter<BaseNode> adapter22 = this$0.getAdapter2();
        if (adapter22 != null) {
            adapter22.notifyItemChanged(helper.getLayoutPosition(), 1);
        }
        this$0.l.select(count);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$3(BaseViewHolder helper, ExportNoteFirstAdapter this$0, BaseNode item, View view) {
        Intrinsics.checkNotNullParameter(helper, "$helper");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        int bindingAdapterPosition = helper.getBindingAdapterPosition();
        BaseProviderMultiAdapter<BaseNode> adapter2 = this$0.getAdapter2();
        Integer numValueOf = adapter2 != null ? Integer.valueOf(adapter2.getHeaderLayoutCount()) : null;
        Intrinsics.checkNotNull(numValueOf);
        int iIntValue = bindingAdapterPosition - numValueOf.intValue();
        if (iIntValue < 0) {
            return;
        }
        this$0.expandOrCollapse(item, iIntValue);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    private final void expandOrCollapse(BaseNode item, int pos) {
        Intrinsics.checkNotNull(item, "null cannot be cast to non-null type com.psychiatrygarden.activity.online.bean.QuestionBankNewBean.DataBean");
        if (((QuestionBankNewBean.DataBean) item).getIsExpanded()) {
            ?? adapter2 = getAdapter2();
            if (adapter2 != 0) {
                BaseNodeAdapter.collapse$default(adapter2, pos, true, false, null, 12, null);
                return;
            }
            return;
        }
        ?? adapter22 = getAdapter2();
        if (adapter22 != 0) {
            BaseNodeAdapter.expand$default(adapter22, pos, true, false, null, 12, null);
        }
    }

    private final void updateSelectIcon(QuestionBankNewBean.DataBean item, View view) {
        int i2 = item.isSelected;
        if (i2 != 2) {
            view.setSelected(i2 == 1);
        } else if (i2 == 2) {
            view.setSelected(true);
        }
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 0;
    }

    @NotNull
    public final ExportNoteAdapter.SelectListener getL() {
        return this.l;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_export_note_parent;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, BaseNode baseNode, List list) {
        convert2(baseViewHolder, baseNode, (List<? extends Object>) list);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull BaseViewHolder helper, @NotNull BaseNode item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        super.convert(helper, (BaseViewHolder) item, payloads);
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.download_select, R.attr.download_part_select, R.attr.icon_bottom_arrow_new, R.attr.icon_top_arrow_new});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.theme.obtainStyl…attr.icon_top_arrow_new))");
        QuestionBankNewBean.DataBean dataBean = (QuestionBankNewBean.DataBean) item;
        updateSelectIcon(dataBean, helper.getView(R.id.iv_select));
        helper.setImageDrawable(R.id.iv_exp_col, typedArrayObtainStyledAttributes.getDrawable(dataBean.getIsExpanded() ? 3 : 2));
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull final BaseViewHolder helper, @NotNull final BaseNode item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        final QuestionBankNewBean.DataBean dataBean = (QuestionBankNewBean.DataBean) item;
        updateSelectIcon(dataBean, helper.getView(R.id.iv_select));
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.k6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExportNoteFirstAdapter.convert$lambda$0(this.f14672c, item, helper, view);
            }
        });
        ((ImageView) helper.getView(R.id.iv_select)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.l6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExportNoteFirstAdapter.convert$lambda$2(item, dataBean, this, helper, view);
            }
        });
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.icon_bottom_arrow_new, R.attr.icon_top_arrow_new});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…attr.icon_top_arrow_new))");
        helper.setText(R.id.tv_title, dataBean.title).setText(R.id.tv_count, dataBean.getCount() + "条笔记").setGone(R.id.iv_exp_col, dataBean.getCount() <= 0).setImageDrawable(R.id.iv_exp_col, !dataBean.getIsExpanded() ? typedArrayObtainStyledAttributes.getDrawable(0) : typedArrayObtainStyledAttributes.getDrawable(1));
        ((ImageView) helper.getView(R.id.iv_exp_col)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.m6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExportNoteFirstAdapter.convert$lambda$3(helper, this, item, view);
            }
        });
        typedArrayObtainStyledAttributes.recycle();
    }
}
