package com.psychiatrygarden.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u0006H\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/psychiatrygarden/adapter/ExportNoteAdapter;", "Lcom/chad/library/adapter/base/BaseNodeAdapter;", NotifyType.LIGHTS, "Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;", "(Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;)V", "getItemType", "", "data", "", "Lcom/chad/library/adapter/base/entity/node/BaseNode;", "position", "SelectListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ExportNoteAdapter extends BaseNodeAdapter {

    @NotNull
    private final SelectListener l;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/psychiatrygarden/adapter/ExportNoteAdapter$SelectListener;", "", "select", "", "count", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface SelectListener {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void select$default(SelectListener selectListener, int i2, int i3, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: select");
                }
                if ((i3 & 1) != 0) {
                    i2 = 0;
                }
                selectListener.select(i2);
            }
        }

        void select(int count);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExportNoteAdapter(@NotNull SelectListener l2) {
        super(null, 1, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(l2, "l");
        this.l = l2;
        addNodeProvider(new ExportNoteFirstAdapter(l2));
        addNodeProvider(new ExportNoteSecondAdapter(l2));
    }

    @Override // com.chad.library.adapter.base.BaseProviderMultiAdapter
    public int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        Intrinsics.checkNotNullParameter(data, "data");
        BaseNode baseNode = data.get(position);
        if (baseNode instanceof QuestionBankNewBean.DataBean) {
            return 0;
        }
        return baseNode instanceof QuestionBankNewBean.DataBean.ChildrenBean ? 1 : -1;
    }
}
