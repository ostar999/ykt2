package com.psychiatrygarden.adapter;

import android.view.View;
import android.widget.ImageView;
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

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J&\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\b¨\u0006\u0016"}, d2 = {"Lcom/psychiatrygarden/adapter/ExportNoteSecondAdapter;", "Lcom/chad/library/adapter/base/provider/BaseNodeProvider;", NotifyType.LIGHTS, "Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;", "(Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;)V", "itemViewType", "", "getItemViewType", "()I", "getL", "()Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;", "layoutId", "getLayoutId", "convert", "", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "Lcom/chad/library/adapter/base/entity/node/BaseNode;", "payloads", "", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nExportNoteSecondAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExportNoteSecondAdapter.kt\ncom/psychiatrygarden/adapter/ExportNoteSecondAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,71:1\n1864#2,2:72\n1855#2,2:74\n1866#2:76\n*S KotlinDebug\n*F\n+ 1 ExportNoteSecondAdapter.kt\ncom/psychiatrygarden/adapter/ExportNoteSecondAdapter\n*L\n36#1:72,2\n41#1:74,2\n36#1:76\n*E\n"})
/* loaded from: classes5.dex */
public final class ExportNoteSecondAdapter extends BaseNodeProvider {

    @NotNull
    private final ExportNoteAdapter.SelectListener l;

    public ExportNoteSecondAdapter(@NotNull ExportNoteAdapter.SelectListener l2) {
        Intrinsics.checkNotNullParameter(l2, "l");
        this.l = l2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r10v5, types: [com.chad.library.adapter.base.BaseNodeAdapter] */
    public static final void convert$lambda$2(BaseNode item, ExportNoteSecondAdapter this$0, BaseViewHolder helper, View view) {
        BaseProviderMultiAdapter<BaseNode> adapter2;
        List<BaseNode> data;
        int count;
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(helper, "$helper");
        QuestionBankNewBean.DataBean.ChildrenBean childrenBean = (QuestionBankNewBean.DataBean.ChildrenBean) item;
        int i2 = childrenBean.isSelected == 0 ? 1 : 0;
        childrenBean.isSelected = i2;
        view.setSelected(i2 == 1);
        BaseProviderMultiAdapter<BaseNode> adapter22 = this$0.getAdapter2();
        if (adapter22 != null) {
            adapter22.notifyItemChanged(helper.getLayoutPosition(), 1);
        }
        ?? adapter23 = this$0.getAdapter2();
        Integer numValueOf = adapter23 != 0 ? Integer.valueOf(adapter23.findParentNode(helper.getLayoutPosition())) : null;
        if ((numValueOf != null && numValueOf.intValue() == -1) || (adapter2 = this$0.getAdapter2()) == null || (data = adapter2.getData()) == null) {
            return;
        }
        int i3 = 0;
        int i4 = 0;
        for (Object obj : data) {
            int i5 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            BaseNode baseNode = (BaseNode) obj;
            if (numValueOf != null && numValueOf.intValue() == i3) {
                List<BaseNode> childNode = baseNode.getChildNode();
                int size = childNode != null ? childNode.size() : 0;
                List<BaseNode> childNode2 = baseNode.getChildNode();
                if (childNode2 != null) {
                    count = 0;
                    for (BaseNode baseNode2 : childNode2) {
                        if (baseNode2 instanceof QuestionBankNewBean.DataBean.ChildrenBean) {
                            QuestionBankNewBean.DataBean.ChildrenBean childrenBean2 = (QuestionBankNewBean.DataBean.ChildrenBean) baseNode2;
                            if (childrenBean2.isSelected == 1) {
                                count += childrenBean2.getCount();
                                i4++;
                            }
                        }
                    }
                } else {
                    count = 0;
                }
                if (i4 <= 0) {
                    Intrinsics.checkNotNull(baseNode, "null cannot be cast to non-null type com.psychiatrygarden.activity.online.bean.QuestionBankNewBean.DataBean");
                    ((QuestionBankNewBean.DataBean) baseNode).setIsSelected(0);
                } else if (i4 < size) {
                    Intrinsics.checkNotNull(baseNode, "null cannot be cast to non-null type com.psychiatrygarden.activity.online.bean.QuestionBankNewBean.DataBean");
                    ((QuestionBankNewBean.DataBean) baseNode).setIsSelected(2);
                } else {
                    Intrinsics.checkNotNull(baseNode, "null cannot be cast to non-null type com.psychiatrygarden.activity.online.bean.QuestionBankNewBean.DataBean");
                    ((QuestionBankNewBean.DataBean) baseNode).setIsSelected(1);
                }
                this$0.l.select(count);
                BaseProviderMultiAdapter<BaseNode> adapter24 = this$0.getAdapter2();
                if (adapter24 != null) {
                    adapter24.notifyItemChanged(numValueOf.intValue(), 1);
                }
            }
            i3 = i5;
        }
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 1;
    }

    @NotNull
    public final ExportNoteAdapter.SelectListener getL() {
        return this.l;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_export_note_child;
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
        ((ImageView) helper.getView(R.id.iv_select)).setSelected(((QuestionBankNewBean.DataBean.ChildrenBean) item).isSelected == 1);
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull final BaseViewHolder helper, @NotNull final BaseNode item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        QuestionBankNewBean.DataBean.ChildrenBean childrenBean = (QuestionBankNewBean.DataBean.ChildrenBean) item;
        helper.setText(R.id.tv_title, childrenBean.title).setText(R.id.tv_count, childrenBean.getCount() + "条笔记");
        ImageView imageView = (ImageView) helper.getView(R.id.iv_select);
        imageView.setSelected(childrenBean.getIsSelected() == 1);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.n6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ExportNoteSecondAdapter.convert$lambda$2(item, this, helper, view);
            }
        });
    }
}
