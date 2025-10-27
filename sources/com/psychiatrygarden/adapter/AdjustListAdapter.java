package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.psychiatrygarden.activity.AdjustRankingActivity;
import com.psychiatrygarden.adapter.AdjustListAdapter;
import com.psychiatrygarden.bean.AdjustInfoBean;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0004!\"#$B#\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000eH\u0016J\u0006\u0010\u0018\u001a\u00020\u0012J\u0018\u0010\u0019\u001a\u00020\u00122\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u0005J\u0010\u0010\u001d\u001a\u00020\u00122\b\u0010\u001e\u001a\u0004\u0018\u00010\fJ\u0014\u0010\u001f\u001a\u00020\u00122\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/psychiatrygarden/adapter/AdjustListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "items", "", "Lcom/psychiatrygarden/bean/AdjustInfoBean$DataBean$AdjustVol1Bean;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "version", "", "(Ljava/util/List;Landroid/content/Context;Ljava/lang/String;)V", "onItemActionListener", "Lcom/psychiatrygarden/adapter/AdjustListAdapter$OnItemClickListener;", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onDestroy", "setFormattedText", "textView", "Landroid/widget/TextView;", "item", "setOnItemClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "updateData", "newItems", "Companion", "EmptyViewHolder", "FilledViewHolder", "OnItemClickListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nAdjustListAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AdjustListAdapter.kt\ncom/psychiatrygarden/adapter/AdjustListAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,235:1\n766#2:236\n857#2,2:237\n*S KotlinDebug\n*F\n+ 1 AdjustListAdapter.kt\ncom/psychiatrygarden/adapter/AdjustListAdapter\n*L\n120#1:236\n120#1:237,2\n*E\n"})
/* loaded from: classes5.dex */
public final class AdjustListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_FILLED = 1;

    @NotNull
    private final Context context;

    @NotNull
    private List<? extends AdjustInfoBean.DataBean.AdjustVol1Bean> items;

    @Nullable
    private OnItemClickListener onItemActionListener;

    @NotNull
    private final String version;

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lcom/psychiatrygarden/adapter/AdjustListAdapter$EmptyViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/psychiatrygarden/adapter/AdjustListAdapter;Landroid/view/View;)V", "adjustTitleTv", "Landroid/widget/TextView;", "getAdjustTitleTv", "()Landroid/widget/TextView;", "bind", "", "item", "Lcom/psychiatrygarden/bean/AdjustInfoBean$DataBean$AdjustVol1Bean;", "position", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class EmptyViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        private final TextView adjustTitleTv;
        final /* synthetic */ AdjustListAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EmptyViewHolder(@NotNull AdjustListAdapter adjustListAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = adjustListAdapter;
            this.adjustTitleTv = (TextView) itemView.findViewById(R.id.adjust_title_tv);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void bind$lambda$0(AdjustListAdapter this$0, int i2, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            OnItemClickListener onItemClickListener = this$0.onItemActionListener;
            if (onItemClickListener != null) {
                onItemClickListener.setItemClickAction(i2);
            }
        }

        public final void bind(@NotNull AdjustInfoBean.DataBean.AdjustVol1Bean item, final int position) {
            Intrinsics.checkNotNullParameter(item, "item");
            TextView textView = this.adjustTitleTv;
            if (textView != null) {
                textView.setText(item.getTitle());
            }
            View view = this.itemView;
            final AdjustListAdapter adjustListAdapter = this.this$0;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    AdjustListAdapter.EmptyViewHolder.bind$lambda$0(adjustListAdapter, position, view2);
                }
            });
        }

        @Nullable
        public final TextView getAdjustTitleTv() {
            return this.adjustTitleTv;
        }
    }

    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\bR\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\bR\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010 \u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\bR\u0013\u0010\"\u001a\u0004\u0018\u00010\u001a¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001cR\u0013\u0010$\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\b¨\u0006,"}, d2 = {"Lcom/psychiatrygarden/adapter/AdjustListAdapter$FilledViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/psychiatrygarden/adapter/AdjustListAdapter;Landroid/view/View;)V", "adjustAllRankTv", "Landroid/widget/TextView;", "getAdjustAllRankTv", "()Landroid/widget/TextView;", "adjustMsgTv", "getAdjustMsgTv", "adjustRankCountTv", "getAdjustRankCountTv", "adjustRankTitleTv", "getAdjustRankTitleTv", "adjustRankTv", "getAdjustRankTv", "adjustTitleTv", "getAdjustTitleTv", "allRankCountTv", "getAllRankCountTv", "allRankTopTv", "getAllRankTopTv", "allRankTv", "getAllRankTv", "allRrankIv", "Landroid/widget/ImageView;", "getAllRrankIv", "()Landroid/widget/ImageView;", "bot_view", "getBot_view", "()Landroid/view/View;", "check_adjust_rank_tv", "getCheck_adjust_rank_tv", "rankIv", "getRankIv", "rankTv", "getRankTv", "bind", "", "item", "Lcom/psychiatrygarden/bean/AdjustInfoBean$DataBean$AdjustVol1Bean;", "position", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public final class FilledViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        private final TextView adjustAllRankTv;

        @Nullable
        private final TextView adjustMsgTv;

        @Nullable
        private final TextView adjustRankCountTv;

        @Nullable
        private final TextView adjustRankTitleTv;

        @Nullable
        private final TextView adjustRankTv;

        @Nullable
        private final TextView adjustTitleTv;

        @Nullable
        private final TextView allRankCountTv;

        @Nullable
        private final TextView allRankTopTv;

        @Nullable
        private final TextView allRankTv;

        @Nullable
        private final ImageView allRrankIv;

        @Nullable
        private final View bot_view;

        @Nullable
        private final TextView check_adjust_rank_tv;

        @Nullable
        private final ImageView rankIv;

        @Nullable
        private final TextView rankTv;
        final /* synthetic */ AdjustListAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FilledViewHolder(@NotNull AdjustListAdapter adjustListAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = adjustListAdapter;
            this.adjustMsgTv = (TextView) itemView.findViewById(R.id.adjust_msg_tv);
            this.adjustTitleTv = (TextView) itemView.findViewById(R.id.adjust_title_tv);
            this.adjustRankTitleTv = (TextView) itemView.findViewById(R.id.adjust_rank_title_tv);
            this.adjustAllRankTv = (TextView) itemView.findViewById(R.id.adjust_allrank_tv);
            this.adjustRankTv = (TextView) itemView.findViewById(R.id.adjust_rank_tv);
            this.adjustRankCountTv = (TextView) itemView.findViewById(R.id.adjust_rank_count_tv);
            this.allRankTv = (TextView) itemView.findViewById(R.id.all_rank_tv);
            this.allRankCountTv = (TextView) itemView.findViewById(R.id.all_rank_count_tv);
            this.rankIv = (ImageView) itemView.findViewById(R.id.rank_iv);
            this.rankTv = (TextView) itemView.findViewById(R.id.rank_tv);
            this.allRrankIv = (ImageView) itemView.findViewById(R.id.all_rank_iv);
            this.allRankTopTv = (TextView) itemView.findViewById(R.id.all_rank_top_tv);
            this.check_adjust_rank_tv = (TextView) itemView.findViewById(R.id.check_adjust_rank_tv);
            this.bot_view = itemView.findViewById(R.id.bot_view);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void bind$lambda$0(AdjustListAdapter this$0, int i2, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intent intent = new Intent(this$0.context, (Class<?>) AdjustRankingActivity.class);
            intent.putExtra("version", this$0.version);
            intent.putExtra("type", String.valueOf(i2 + 1));
            this$0.context.startActivity(intent);
        }

        public final void bind(@NotNull AdjustInfoBean.DataBean.AdjustVol1Bean item, final int position) {
            Intrinsics.checkNotNullParameter(item, "item");
            String adjust_rank = item.getVol_data().getAdjust_rank();
            String all_rank = item.getVol_data().getAll_rank();
            TextView textView = this.adjustTitleTv;
            if (textView != null) {
                textView.setText(item.getTitle());
            }
            TextView textView2 = this.adjustRankTitleTv;
            if (textView2 != null) {
                textView2.setText(item.getVol_data().getAdjust_rank_title());
            }
            TextView textView3 = this.adjustAllRankTv;
            if (textView3 != null) {
                textView3.setText(item.getVol_data().getAll_rank_title());
            }
            TextView textView4 = this.adjustRankTv;
            if (textView4 != null) {
                textView4.setText(adjust_rank);
            }
            TextView textView5 = this.adjustRankCountTv;
            if (textView5 != null) {
                textView5.setText('/' + item.getVol_data().getAdjust_rank_count());
            }
            TextView textView6 = this.allRankTv;
            if (textView6 != null) {
                textView6.setText(all_rank);
            }
            TextView textView7 = this.allRankCountTv;
            if (textView7 != null) {
                textView7.setText('/' + item.getVol_data().getAll_rank_count());
            }
            if (adjust_rank.equals("1")) {
                ImageView imageView = this.rankIv;
                if (imageView != null) {
                    imageView.setVisibility(0);
                }
                TextView textView8 = this.rankTv;
                if (textView8 != null) {
                    textView8.setVisibility(8);
                }
                ImageView imageView2 = this.rankIv;
                if (imageView2 != null) {
                    imageView2.setImageDrawable(this.this$0.context.getDrawable(R.drawable.rankone));
                }
            } else if (adjust_rank.equals("2")) {
                ImageView imageView3 = this.rankIv;
                if (imageView3 != null) {
                    imageView3.setVisibility(0);
                }
                TextView textView9 = this.rankTv;
                if (textView9 != null) {
                    textView9.setVisibility(8);
                }
                ImageView imageView4 = this.rankIv;
                if (imageView4 != null) {
                    imageView4.setImageDrawable(this.this$0.context.getDrawable(R.drawable.ranktwo));
                }
            } else if (adjust_rank.equals("3")) {
                ImageView imageView5 = this.rankIv;
                if (imageView5 != null) {
                    imageView5.setVisibility(0);
                }
                TextView textView10 = this.rankTv;
                if (textView10 != null) {
                    textView10.setVisibility(8);
                }
                ImageView imageView6 = this.rankIv;
                if (imageView6 != null) {
                    imageView6.setImageDrawable(this.this$0.context.getDrawable(R.drawable.rankthree));
                }
            } else {
                ImageView imageView7 = this.rankIv;
                if (imageView7 != null) {
                    imageView7.setVisibility(8);
                }
                TextView textView11 = this.rankTv;
                if (textView11 != null) {
                    textView11.setVisibility(0);
                }
                TextView textView12 = this.rankTv;
                if (textView12 != null) {
                    textView12.setText(adjust_rank);
                }
            }
            if (all_rank.equals("1")) {
                ImageView imageView8 = this.allRrankIv;
                if (imageView8 != null) {
                    imageView8.setVisibility(0);
                }
                TextView textView13 = this.allRankTopTv;
                if (textView13 != null) {
                    textView13.setVisibility(8);
                }
                ImageView imageView9 = this.allRrankIv;
                if (imageView9 != null) {
                    imageView9.setImageDrawable(this.this$0.context.getDrawable(R.drawable.rankone));
                }
            } else if (all_rank.equals("2")) {
                ImageView imageView10 = this.allRrankIv;
                if (imageView10 != null) {
                    imageView10.setVisibility(0);
                }
                TextView textView14 = this.allRankTopTv;
                if (textView14 != null) {
                    textView14.setVisibility(8);
                }
                ImageView imageView11 = this.allRrankIv;
                if (imageView11 != null) {
                    imageView11.setImageDrawable(this.this$0.context.getDrawable(R.drawable.ranktwo));
                }
            } else if (all_rank.equals("3")) {
                ImageView imageView12 = this.allRrankIv;
                if (imageView12 != null) {
                    imageView12.setVisibility(0);
                }
                TextView textView15 = this.allRankTopTv;
                if (textView15 != null) {
                    textView15.setVisibility(8);
                }
                ImageView imageView13 = this.allRrankIv;
                if (imageView13 != null) {
                    imageView13.setImageDrawable(this.this$0.context.getDrawable(R.drawable.rankthree));
                }
            } else {
                ImageView imageView14 = this.allRrankIv;
                if (imageView14 != null) {
                    imageView14.setVisibility(8);
                }
                TextView textView16 = this.allRankTopTv;
                if (textView16 != null) {
                    textView16.setVisibility(0);
                }
                TextView textView17 = this.allRankTopTv;
                if (textView17 != null) {
                    textView17.setText(all_rank);
                }
            }
            this.this$0.setFormattedText(this.adjustMsgTv, item);
            TextView textView18 = this.check_adjust_rank_tv;
            if (textView18 != null) {
                final AdjustListAdapter adjustListAdapter = this.this$0;
                textView18.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.b
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AdjustListAdapter.FilledViewHolder.bind$lambda$0(adjustListAdapter, position, view);
                    }
                });
            }
            if (position == 2) {
                View view = this.bot_view;
                if (view == null) {
                    return;
                }
                view.setVisibility(0);
                return;
            }
            View view2 = this.bot_view;
            if (view2 == null) {
                return;
            }
            view2.setVisibility(8);
        }

        @Nullable
        public final TextView getAdjustAllRankTv() {
            return this.adjustAllRankTv;
        }

        @Nullable
        public final TextView getAdjustMsgTv() {
            return this.adjustMsgTv;
        }

        @Nullable
        public final TextView getAdjustRankCountTv() {
            return this.adjustRankCountTv;
        }

        @Nullable
        public final TextView getAdjustRankTitleTv() {
            return this.adjustRankTitleTv;
        }

        @Nullable
        public final TextView getAdjustRankTv() {
            return this.adjustRankTv;
        }

        @Nullable
        public final TextView getAdjustTitleTv() {
            return this.adjustTitleTv;
        }

        @Nullable
        public final TextView getAllRankCountTv() {
            return this.allRankCountTv;
        }

        @Nullable
        public final TextView getAllRankTopTv() {
            return this.allRankTopTv;
        }

        @Nullable
        public final TextView getAllRankTv() {
            return this.allRankTv;
        }

        @Nullable
        public final ImageView getAllRrankIv() {
            return this.allRrankIv;
        }

        @Nullable
        public final View getBot_view() {
            return this.bot_view;
        }

        @Nullable
        public final TextView getCheck_adjust_rank_tv() {
            return this.check_adjust_rank_tv;
        }

        @Nullable
        public final ImageView getRankIv() {
            return this.rankIv;
        }

        @Nullable
        public final TextView getRankTv() {
            return this.rankTv;
        }
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/psychiatrygarden/adapter/AdjustListAdapter$OnItemClickListener;", "", "setItemClickAction", "", "pos", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemClickListener {
        void setItemClickAction(int pos);
    }

    public AdjustListAdapter(@NotNull List<? extends AdjustInfoBean.DataBean.AdjustVol1Bean> items, @NotNull Context context, @NotNull String version) {
        Intrinsics.checkNotNullParameter(items, "items");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(version, "version");
        this.items = items;
        this.context = context;
        this.version = version;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        AdjustInfoBean.DataBean.AdjustVol1Bean adjustVol1Bean = (AdjustInfoBean.DataBean.AdjustVol1Bean) CollectionsKt___CollectionsKt.getOrNull(this.items, position);
        if (adjustVol1Bean == null) {
            return 0;
        }
        return Intrinsics.areEqual(adjustVol1Bean.getIs_fill(), "1") ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        AdjustInfoBean.DataBean.AdjustVol1Bean adjustVol1Bean = (AdjustInfoBean.DataBean.AdjustVol1Bean) CollectionsKt___CollectionsKt.getOrNull(this.items, position);
        if (adjustVol1Bean == null) {
            return;
        }
        if (holder instanceof FilledViewHolder) {
            ((FilledViewHolder) holder).bind(adjustVol1Bean, position);
        } else if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).bind(adjustVol1Bean, position);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adjust_list, parent, false);
            Intrinsics.checkNotNullExpressionValue(view, "view");
            return new FilledViewHolder(this, view);
        }
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adjust_list_empty, parent, false);
        Intrinsics.checkNotNullExpressionValue(view2, "view");
        return new EmptyViewHolder(this, view2);
    }

    public final void onDestroy() {
        this.onItemActionListener = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setFormattedText(@org.jetbrains.annotations.Nullable android.widget.TextView r13, @org.jetbrains.annotations.NotNull com.psychiatrygarden.bean.AdjustInfoBean.DataBean.AdjustVol1Bean r14) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.adapter.AdjustListAdapter.setFormattedText(android.widget.TextView, com.psychiatrygarden.bean.AdjustInfoBean$DataBean$AdjustVol1Bean):void");
    }

    public final void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.onItemActionListener = listener;
    }

    public final void updateData(@NotNull List<? extends AdjustInfoBean.DataBean.AdjustVol1Bean> newItems) {
        Intrinsics.checkNotNullParameter(newItems, "newItems");
        this.items = new ArrayList(newItems);
        notifyDataSetChanged();
    }
}
