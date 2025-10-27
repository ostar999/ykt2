package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.ruffian.library.widget.RTextView;
import com.tencent.connect.common.Constants;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.PermissionDialog;
import com.ykb.ebook.model.Ways;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.ImageLoader;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/PermissionDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class PermissionDialog {

    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ(\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J&\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00122\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0002J\u0010\u0010!\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u001d\u0010\n\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u0004\u0018\u00010\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u000f\u001a\u0004\b\u0015\u0010\u0016¨\u0006\""}, d2 = {"Lcom/ykb/ebook/dialog/PermissionDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bookId", "", "click", "Lkotlin/Function0;", "", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "lyView", "Landroid/widget/LinearLayout;", "getLyView", "()Landroid/widget/LinearLayout;", "lyView$delegate", "Lkotlin/Lazy;", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/Ways;", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "getRecycler", "()Landroidx/recyclerview/widget/RecyclerView;", "recycler$delegate", "convert", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "", "item", "payloads", "", "", "loadData", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: lyView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy lyView;

        @NotNull
        private CommonAdapter<Ways> mAdapter;

        /* renamed from: recycler$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recycler;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull final Context context, @NotNull String bookId, @NotNull final Function0<Unit> click) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(click, "click");
            this.lyView = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.PermissionDialog$Builder$lyView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ly_view);
                }
            });
            this.recycler = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.PermissionDialog$Builder$recycler$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RecyclerView invoke() {
                    return (RecyclerView) this.this$0.findViewById(R.id.recyclerview);
                }
            });
            setContentView(R.layout.dialog_permission_window);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setBackgroundDimEnabled(false);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
            setOnClickListener(getLyView());
            CommonAdapter<Ways> commonAdapter = new CommonAdapter<>(R.layout.item_permission, null, 2, null);
            this.mAdapter = commonAdapter;
            commonAdapter.setConvert(new Function3<QuickViewHolder, Integer, Ways, Unit>() { // from class: com.ykb.ebook.dialog.PermissionDialog.Builder.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, Ways ways) {
                    invoke(quickViewHolder, num.intValue(), ways);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable Ways ways) {
                    Intrinsics.checkNotNullParameter(holder, "holder");
                    Builder builder = Builder.this;
                    Context context2 = context;
                    Intrinsics.checkNotNull(ways);
                    builder.convert(context2, holder, i2, ways);
                }
            });
            this.mAdapter.setConvertPayload(new Function4<QuickViewHolder, Integer, Ways, List<? extends Object>, Unit>() { // from class: com.ykb.ebook.dialog.PermissionDialog.Builder.2
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, Ways ways, List<? extends Object> list) {
                    invoke(quickViewHolder, num.intValue(), ways, list);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable Ways ways, @NotNull List<? extends Object> payloads) {
                    Intrinsics.checkNotNullParameter(holder, "holder");
                    Intrinsics.checkNotNullParameter(payloads, "payloads");
                    Builder builder = Builder.this;
                    Intrinsics.checkNotNull(ways);
                    builder.convert(holder, ways, payloads);
                }
            });
            RecyclerView recycler = getRecycler();
            if (recycler != null) {
                recycler.setAdapter(this.mAdapter);
            }
            LinearLayout lyView = getLyView();
            if (lyView != null) {
                lyView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.r0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        PermissionDialog.Builder._init_$lambda$0(click, view);
                    }
                });
            }
            loadData(bookId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Function0 click, View view) {
            Intrinsics.checkNotNullParameter(click, "$click");
            click.invoke();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void convert(Context context, QuickViewHolder holder, int position, Ways item) {
            ImageLoader.INSTANCE.load(context, item.getIcon()).placeholder(R.drawable.personal_headimg_icon).into((ImageView) holder.getView(R.id.leftimg));
            holder.setText(R.id.mfristTxt, item.getTitle()).setText(R.id.mDofristTxt, item.getDescription());
            ((RTextView) holder.getView(R.id.tv_to_lock)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.s0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PermissionDialog.Builder.convert$lambda$1(view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$1(View view) {
        }

        private final LinearLayout getLyView() {
            return (LinearLayout) this.lyView.getValue();
        }

        private final RecyclerView getRecycler() {
            return (RecyclerView) this.recycler.getValue();
        }

        private final void loadData(String bookId) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new PermissionDialog$Builder$loadData$1(MapsKt__MapsKt.hashMapOf(new Pair("user_id", "583383"), new Pair("book_id", bookId), new Pair("app_id", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)), null), 15, null), null, new PermissionDialog$Builder$loadData$2(this, null), 1, null), null, new PermissionDialog$Builder$loadData$3(null), 1, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void convert(QuickViewHolder holder, Ways item, List<? extends Object> payloads) {
            if (!payloads.isEmpty()) {
                Object obj = payloads.get(0);
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                ((Integer) obj).intValue();
            }
        }
    }
}
