package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.dialog.ChapterSearchDialog;
import com.ykb.ebook.model.TextSearchResult;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/TextSearchResult;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ChapterSearchDialog$Builder$adapter$2 extends Lambda implements Function0<CommonAdapter<TextSearchResult>> {
    final /* synthetic */ Context $context;
    final /* synthetic */ ChapterSearchDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChapterSearchDialog$Builder$adapter$2(ChapterSearchDialog.Builder builder, Context context) {
        super(0);
        this.this$0 = builder;
        this.$context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$2$lambda$1(ChapterSearchDialog.Builder this$0, BaseQuickAdapter adapter, View view, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
        if (((TextSearchResult) adapter.getItem(i2)) != null) {
            this$0.dismiss();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final CommonAdapter<TextSearchResult> invoke() {
        CommonAdapter<TextSearchResult> commonAdapter = new CommonAdapter<>(R.layout.item_chapter_search, null, 2, 0 == true ? 1 : 0);
        final ChapterSearchDialog.Builder builder = this.this$0;
        commonAdapter.setConvert(new ChapterSearchDialog$Builder$adapter$2$1$1(builder, this.$context));
        commonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ykb.ebook.dialog.z
            @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                ChapterSearchDialog$Builder$adapter$2.invoke$lambda$2$lambda$1(builder, baseQuickAdapter, view, i2);
            }
        });
        return commonAdapter;
    }
}
