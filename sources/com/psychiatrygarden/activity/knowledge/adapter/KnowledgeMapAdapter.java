package com.psychiatrygarden.activity.knowledge.adapter;

import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.KnowledgeTaskListItemBean;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0002H\u0014J\u001a\u0010\f\u001a\u00020\b2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/psychiatrygarden/activity/knowledge/adapter/KnowledgeMapAdapter;", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/KnowledgeTaskListItemBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "()V", "taskClick", "Lkotlin/Function1;", "", "", "convert", "holder", "item", "setTaskClick", "click", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nKnowledgeMapAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KnowledgeMapAdapter.kt\ncom/psychiatrygarden/activity/knowledge/adapter/KnowledgeMapAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,67:1\n1#2:68\n*E\n"})
/* loaded from: classes5.dex */
public final class KnowledgeMapAdapter extends BaseQuickAdapter<KnowledgeTaskListItemBean, BaseViewHolder> {

    @NotNull
    private Function1<? super String, Unit> taskClick;

    public KnowledgeMapAdapter() {
        super(R.layout.item_knowledge_map, null, 2, null);
        this.taskClick = new Function1<String, Unit>() { // from class: com.psychiatrygarden.activity.knowledge.adapter.KnowledgeMapAdapter$taskClick$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String i2) {
                Intrinsics.checkNotNullParameter(i2, "i");
                System.out.println((Object) ("点击了taskId " + i2));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$1(KnowledgeTaskListItemBean item, KnowledgeMapAdapter this$0, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String id = item.getId();
        if (id != null) {
            this$0.taskClick.invoke(id);
        }
    }

    public final void setTaskClick(@NotNull Function1<? super String, Unit> click) {
        Intrinsics.checkNotNullParameter(click, "click");
        this.taskClick = click;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull final KnowledgeTaskListItemBean item) {
        String str;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.knowledgeTextName);
        TextView textView2 = (TextView) holder.getView(R.id.knowledgeTextAddCount);
        TextView textView3 = (TextView) holder.getView(R.id.knowledgeTaskBtn);
        String title = item.getTitle();
        boolean z2 = true;
        textView.setText(title == null || title.length() == 0 ? "--" : item.getTitle());
        String reward_days = item.getReward_days();
        if (reward_days == null || reward_days.length() == 0) {
            str = "+0天";
        } else {
            str = '+' + item.getReward_days() + (char) 22825;
        }
        textView2.setText(str);
        if (Intrinsics.areEqual("1", item.is_receive())) {
            textView3.setText("已领取");
            textView3.setTextColor(getContext().getResources().getColor(R.color.forth_txt_color, null));
            textView3.setBackgroundResource(R.drawable.shape_new_bg_two_color_stroke_eee);
            textView3.setEnabled(false);
            return;
        }
        if (Intrinsics.areEqual("1", item.getCan_receive())) {
            textView3.setText("领取");
            textView3.setTextColor(getContext().getResources().getColor(R.color.white, null));
            textView3.setBackgroundResource(R.drawable.shape_app_theme_corners_8_only_day);
            textView3.setEnabled(true);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.knowledge.adapter.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KnowledgeMapAdapter.convert$lambda$1(item, this, view);
                }
            });
            return;
        }
        String complete_num = item.getComplete_num();
        String complete_num2 = complete_num == null || complete_num.length() == 0 ? "0" : item.getComplete_num();
        String should_num = item.getShould_num();
        if (should_num != null && should_num.length() != 0) {
            z2 = false;
        }
        textView3.setText(complete_num2 + '/' + (z2 ? "0" : item.getShould_num()));
        textView3.setTextColor(getContext().getResources().getColor(R.color.main_theme_color, null));
        textView3.setBackgroundResource(R.drawable.shape_main_theme_color_stroke05_corner8);
        textView3.setEnabled(false);
    }
}
