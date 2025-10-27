package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001cB\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0010H\u0016J&\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006H\u0016J\u0018\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0010H\u0016J\u001a\u0010\u001a\u001a\u00020\u000e2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ViewPageNoticeAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/psychiatrygarden/activity/chooseSchool/adapter/ViewPageNoticeAdapter$ViewHolder;", "mContext", "Landroid/content/Context;", "data", "", "Lcom/psychiatrygarden/bean/ForumTopBean$KeyWordsData;", "(Landroid/content/Context;Ljava/util/List;)V", "getData", "()Ljava/util/List;", "itemClick", "Lkotlin/Function1;", "", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "payloads", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setItemClick", "click", "ViewHolder", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class ViewPageNoticeAdapter extends RecyclerView.Adapter<ViewHolder> {

    @NotNull
    private final List<ForumTopBean.KeyWordsData> data;

    @NotNull
    private Function1<? super String, Unit> itemClick;

    @NotNull
    private final Context mContext;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/psychiatrygarden/activity/chooseSchool/adapter/ViewPageNoticeAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "title", "Landroid/widget/TextView;", "getTitle", "()Landroid/widget/TextView;", "setTitle", "(Landroid/widget/TextView;)V", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ViewHolder extends RecyclerView.ViewHolder {

        @NotNull
        private TextView title;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.title);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "itemView.findViewById<TextView>(R.id.title)");
            this.title = (TextView) viewFindViewById;
        }

        @NotNull
        public final TextView getTitle() {
            return this.title;
        }

        public final void setTitle(@NotNull TextView textView) {
            Intrinsics.checkNotNullParameter(textView, "<set-?>");
            this.title = textView;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ViewPageNoticeAdapter(@NotNull Context mContext, @NotNull List<? extends ForumTopBean.KeyWordsData> data) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(data, "data");
        this.mContext = mContext;
        this.data = data;
        this.itemClick = new Function1<String, Unit>() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.ViewPageNoticeAdapter$itemClick$1
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
    public static final void onBindViewHolder$lambda$0(ViewPageNoticeAdapter this$0, int i2, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.itemClick.invoke("" + i2);
    }

    @NotNull
    public final List<ForumTopBean.KeyWordsData> getData() {
        return this.data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public final void setItemClick(@NotNull Function1<? super String, Unit> click) {
        Intrinsics.checkNotNullParameter(click, "click");
        this.itemClick = click;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2, List list) {
        onBindViewHolder((ViewHolder) viewHolder, i2, (List<? extends Object>) list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_banner, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "from(parent.context).inf…xt_banner, parent, false)");
        viewInflate.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return new ViewHolder(viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (this.data.size() == 0) {
            return;
        }
        ForumTopBean.KeyWordsData keyWordsData = this.data.get(position % this.data.size());
        holder.getTitle().setTextSize(14.0f);
        holder.getTitle().setTextColor(SkinManager.getCurrentSkinType(this.mContext) == 1 ? Color.parseColor("#454C64") : Color.parseColor("#C2C6CB"));
        holder.getTitle().setText(keyWordsData.getKeywords());
        holder.getTitle().setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ViewPageNoticeAdapter.onBindViewHolder$lambda$0(this.f11210c, position, view);
            }
        });
    }

    public void onBindViewHolder(@NotNull ViewHolder holder, int position, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        super.onBindViewHolder((ViewPageNoticeAdapter) holder, position, (List<Object>) payloads);
    }
}
