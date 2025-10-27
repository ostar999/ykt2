package com.ykb.ebook.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.page.ReadBook;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\b\u0010\u000e\u001a\u00020\u000bH\u0016J\u0018\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u000bH\u0017J\u0018\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000bH\u0016J\u0014\u0010\u0016\u001a\u00020\f2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J \u0010\u0017\u001a\u00020\f2\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f0\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\f\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/ykb/ebook/adapter/ChapterListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/ykb/ebook/adapter/ChapterListAdapter$ChapterListHolder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "data", "", "Lcom/ykb/ebook/model/Chapter;", "onItemClick", "Lkotlin/Function2;", "", "", "getData", "getItemCount", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", PLVRxEncryptDataFunction.SET_DATA_METHOD, "setOnItemClick", "ChapterListHolder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ChapterListAdapter extends RecyclerView.Adapter<ChapterListHolder> {

    @NotNull
    private final Context context;

    @NotNull
    private List<Chapter> data;

    @Nullable
    private Function2<? super Integer, ? super Chapter, Unit> onItemClick;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u000f"}, d2 = {"Lcom/ykb/ebook/adapter/ChapterListAdapter$ChapterListHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "imgLock", "Landroid/widget/ImageView;", "getImgLock", "()Landroid/widget/ImageView;", "tvTitle", "Landroid/widget/TextView;", "getTvTitle", "()Landroid/widget/TextView;", "tvWordNum", "getTvWordNum", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ChapterListHolder extends RecyclerView.ViewHolder {

        @NotNull
        private final ImageView imgLock;

        @NotNull
        private final TextView tvTitle;

        @NotNull
        private final TextView tvWordNum;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ChapterListHolder(@NotNull View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.tv_title);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "itemView.findViewById(R.id.tv_title)");
            this.tvTitle = (TextView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.tv_word_num);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "itemView.findViewById(R.id.tv_word_num)");
            this.tvWordNum = (TextView) viewFindViewById2;
            View viewFindViewById3 = itemView.findViewById(R.id.img_lock);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "itemView.findViewById(R.id.img_lock)");
            this.imgLock = (ImageView) viewFindViewById3;
        }

        @NotNull
        public final ImageView getImgLock() {
            return this.imgLock;
        }

        @NotNull
        public final TextView getTvTitle() {
            return this.tvTitle;
        }

        @NotNull
        public final TextView getTvWordNum() {
            return this.tvWordNum;
        }
    }

    public ChapterListAdapter(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.data = CollectionsKt__CollectionsKt.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(ChapterListAdapter this$0, int i2, Chapter item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        Function2<? super Integer, ? super Chapter, Unit> function2 = this$0.onItemClick;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i2), item);
        }
    }

    @NotNull
    public final List<Chapter> getData() {
        return this.data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    public final void setData(@NotNull List<Chapter> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
        notifyItemRangeChanged(0, data.size());
    }

    public final void setOnItemClick(@NotNull Function2<? super Integer, ? super Chapter, Unit> onItemClick) {
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        this.onItemClick = onItemClick;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NotNull ChapterListHolder holder, final int position) {
        String chapterTitle;
        int color;
        Intrinsics.checkNotNullParameter(holder, "holder");
        final Chapter chapter = this.data.get(position);
        TextView tvTitle = holder.getTvTitle();
        if (Intrinsics.areEqual("-1", chapter.getSort())) {
            chapterTitle = chapter.getChapterTitle();
        } else {
            chapterTitle = (char) 31532 + chapter.getSort() + "章 " + chapter.getTitle();
        }
        tvTitle.setText(chapterTitle);
        holder.getTvWordNum().setVisibility(Intrinsics.areEqual("-1", chapter.getId()) ? 8 : 0);
        holder.getImgLock().setVisibility(Intrinsics.areEqual("-1", chapter.getId()) ? 8 : 0);
        TextView tvTitle2 = holder.getTvTitle();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 2) {
            color = this.context.getColor((chapter.isRead() && chapter.isPay() && (chapter.isRead() || !chapter.isPay())) ? R.color.color_7380a9 : R.color.color_575F79);
        } else {
            color = this.context.getColor((chapter.isRead() && chapter.isPay() && (chapter.isRead() || !chapter.isPay())) ? R.color.color_303030 : R.color.color_bfbfbf);
        }
        tvTitle2.setTextColor(color);
        if (ReadBook.INSTANCE.getDurChapterIndex() == chapter.getIndex() && !readConfig.getFirstPage() && !readConfig.getLastPage()) {
            holder.getTvTitle().setTextColor(readConfig.getColorMode() == 2 ? this.context.getColor(R.color.color_B2575C) : this.context.getColor(R.color.color_dd594a));
        }
        holder.getTvWordNum().setTextColor(readConfig.getColorMode() == 2 ? this.context.getColor(R.color.color_575F79) : this.context.getColor(R.color.color_bfbfbf));
        if (Integer.parseInt(chapter.getSort()) == -1) {
            ViewExtensionsKt.gone(holder.getImgLock());
            ViewExtensionsKt.gone(holder.getTvWordNum());
            holder.getTvWordNum().setText("");
        } else if (chapter.hasUnlock()) {
            ViewExtensionsKt.gone(holder.getImgLock());
            ViewExtensionsKt.visible(holder.getTvWordNum());
            holder.getTvWordNum().setText(chapter.getWordCount() + (char) 23383);
        } else {
            ViewExtensionsKt.visible(holder.getImgLock());
            ViewExtensionsKt.gone(holder.getTvWordNum());
            ImageView imgLock = holder.getImgLock();
            int colorMode = readConfig.getColorMode();
            imgLock.setImageResource(colorMode != 0 ? colorMode != 1 ? colorMode != 2 ? R.drawable.icon_chapter_lock : R.mipmap.icon_chapter_lock_blue : R.mipmap.icon_chapter_lock_yellow : R.drawable.icon_chapter_lock);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChapterListAdapter.onBindViewHolder$lambda$0(this.f26282c, position, chapter, view);
            }
        });
        if (Intrinsics.areEqual("-1", chapter.getSort())) {
            holder.getTvTitle().setTextColor(this.context.getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public ChapterListHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.item_chapter_list, parent, false);
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return new ChapterListHolder(itemView);
    }
}
