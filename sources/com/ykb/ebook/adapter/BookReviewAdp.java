package com.ykb.ebook.adapter;

import android.content.AppCtxKt;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.hutool.core.lang.RegexPool;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.weight.PinnedSectionListView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002:\u0001=B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0014\u0010'\u001a\u00020\u00132\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000e0)J\b\u0010*\u001a\u00020\u0012H\u0016J\u0016\u0010+\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fJ\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0012H\u0016J\u0010\u0010/\u001a\u0002002\u0006\u0010.\u001a\u00020\u0012H\u0016J\u0010\u00101\u001a\u00020\u00122\u0006\u0010.\u001a\u00020\u0012H\u0016J$\u00102\u001a\u0002032\u0006\u0010.\u001a\u00020\u00122\b\u00104\u001a\u0004\u0018\u0001032\b\u00105\u001a\u0004\u0018\u000106H\u0016J\b\u00107\u001a\u00020\u0012H\u0016J\u0010\u00108\u001a\u00020\u00072\u0006\u00109\u001a\u00020\u0012H\u0016J\u0014\u0010:\u001a\u00020\u00132\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000e0)J\u000e\u0010;\u001a\u00020\u00132\u0006\u0010<\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R.\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R(\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR.\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0015\"\u0004\b \u0010\u0017R.\u0010!\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017R.\u0010$\u001a\u0016\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0015\"\u0004\b&\u0010\u0017¨\u0006>"}, d2 = {"Lcom/ykb/ebook/adapter/BookReviewAdp;", "Landroid/widget/BaseAdapter;", "Lcom/ykb/ebook/weight/PinnedSectionListView$PinnedSectionListAdapter;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "isHaveHot", "", "isShowNews", "()Z", "setShowNews", "(Z)V", "mData", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/model/BookReview;", "Lkotlin/collections/ArrayList;", "onItemClickAction", "Lkotlin/Function2;", "", "", "getOnItemClickAction", "()Lkotlin/jvm/functions/Function2;", "setOnItemClickAction", "(Lkotlin/jvm/functions/Function2;)V", "onItemHotAndNewsAction", "Lkotlin/Function1;", "getOnItemHotAndNewsAction", "()Lkotlin/jvm/functions/Function1;", "setOnItemHotAndNewsAction", "(Lkotlin/jvm/functions/Function1;)V", "onItemOpposeAction", "getOnItemOpposeAction", "setOnItemOpposeAction", "onItemReplyAction", "getOnItemReplyAction", "setOnItemReplyAction", "onItemSupportAction", "getOnItemSupportAction", "setOnItemSupportAction", "addData", "data", "", "getCount", "getData", "getItem", "", "position", "getItemId", "", "getItemViewType", "getView", "Landroid/view/View;", "convertView", "parent", "Landroid/view/ViewGroup;", "getViewTypeCount", "isItemViewTypePinned", "viewType", PLVRxEncryptDataFunction.SET_DATA_METHOD, "setIsHaveHot", "isHave", "ContentViewHolder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBookReviewAdp.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BookReviewAdp.kt\ncom/ykb/ebook/adapter/BookReviewAdp\n+ 2 View.kt\nandroidx/core/view/ViewKt\n*L\n1#1,236:1\n296#2,2:237\n*S KotlinDebug\n*F\n+ 1 BookReviewAdp.kt\ncom/ykb/ebook/adapter/BookReviewAdp\n*L\n120#1:237,2\n*E\n"})
/* loaded from: classes6.dex */
public final class BookReviewAdp extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    @NotNull
    private final Context context;
    private boolean isHaveHot;
    private boolean isShowNews;

    @NotNull
    private ArrayList<BookReview> mData;

    @Nullable
    private Function2<? super Integer, ? super BookReview, Unit> onItemClickAction;

    @Nullable
    private Function1<? super Boolean, Unit> onItemHotAndNewsAction;

    @Nullable
    private Function2<? super Integer, ? super BookReview, Unit> onItemOpposeAction;

    @Nullable
    private Function2<? super Integer, ? super BookReview, Unit> onItemReplyAction;

    @Nullable
    private Function2<? super Integer, ? super BookReview, Unit> onItemSupportAction;

    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0013\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u001d\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u0011\u0010#\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b$\u0010 R\u0011\u0010%\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\bR\u0011\u0010'\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\bR\u0011\u0010)\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\bR\u0011\u0010+\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\bR\u0011\u0010-\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u0011\u0010/\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b0\u0010 ¨\u00061"}, d2 = {"Lcom/ykb/ebook/adapter/BookReviewAdp$ContentViewHolder;", "", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "btnHot", "Lcom/ruffian/library/widget/RTextView;", "getBtnHot", "()Lcom/ruffian/library/widget/RTextView;", "btnNews", "getBtnNews", "imgAvatar", "Lcom/ruffian/library/widget/RImageView;", "getImgAvatar", "()Lcom/ruffian/library/widget/RImageView;", "imgPicture", "getImgPicture", "lyContentView", "Landroid/widget/LinearLayout;", "getLyContentView", "()Landroid/widget/LinearLayout;", "lyMoreView", "getLyMoreView", "lyTabView", "Lcom/ruffian/library/widget/RLinearLayout;", "getLyTabView", "()Lcom/ruffian/library/widget/RLinearLayout;", "lyTitleView", "getLyTitleView", "tvComment", "Landroid/widget/TextView;", "getTvComment", "()Landroid/widget/TextView;", "tvHospital", "getTvHospital", "tvMoreValue", "getTvMoreValue", "tvNickName", "getTvNickName", "tvOpposition", "getTvOpposition", "tvReply", "getTvReply", "tvSupport", "getTvSupport", "tvTime", "getTvTime", "tvTitleView", "getTvTitleView", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ContentViewHolder {

        @NotNull
        private final RTextView btnHot;

        @NotNull
        private final RTextView btnNews;

        @NotNull
        private final RImageView imgAvatar;

        @NotNull
        private final RImageView imgPicture;

        @NotNull
        private final LinearLayout lyContentView;

        @NotNull
        private final LinearLayout lyMoreView;

        @NotNull
        private final RLinearLayout lyTabView;

        @NotNull
        private final LinearLayout lyTitleView;

        @NotNull
        private final TextView tvComment;

        @NotNull
        private final TextView tvHospital;

        @NotNull
        private final TextView tvMoreValue;

        @NotNull
        private final RTextView tvNickName;

        @NotNull
        private final RTextView tvOpposition;

        @NotNull
        private final RTextView tvReply;

        @NotNull
        private final RTextView tvSupport;

        @NotNull
        private final TextView tvTime;

        @NotNull
        private final TextView tvTitleView;

        public ContentViewHolder(@NotNull View itemView) {
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            View viewFindViewById = itemView.findViewById(R.id.img_avatar);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "itemView.findViewById(R.id.img_avatar)");
            this.imgAvatar = (RImageView) viewFindViewById;
            View viewFindViewById2 = itemView.findViewById(R.id.ly_title_view);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "itemView.findViewById(R.id.ly_title_view)");
            this.lyTitleView = (LinearLayout) viewFindViewById2;
            View viewFindViewById3 = itemView.findViewById(R.id.tv_newest_review);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "itemView.findViewById(R.id.tv_newest_review)");
            this.tvTitleView = (TextView) viewFindViewById3;
            View viewFindViewById4 = itemView.findViewById(R.id.ly_look_more);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "itemView.findViewById(R.id.ly_look_more)");
            this.lyMoreView = (LinearLayout) viewFindViewById4;
            View viewFindViewById5 = itemView.findViewById(R.id.tv_more_value);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "itemView.findViewById(R.id.tv_more_value)");
            this.tvMoreValue = (TextView) viewFindViewById5;
            View viewFindViewById6 = itemView.findViewById(R.id.ly_content_view);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "itemView.findViewById(R.id.ly_content_view)");
            this.lyContentView = (LinearLayout) viewFindViewById6;
            View viewFindViewById7 = itemView.findViewById(R.id.tv_support);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "itemView.findViewById(R.id.tv_support)");
            this.tvSupport = (RTextView) viewFindViewById7;
            View viewFindViewById8 = itemView.findViewById(R.id.tv_opposition);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "itemView.findViewById(R.id.tv_opposition)");
            this.tvOpposition = (RTextView) viewFindViewById8;
            View viewFindViewById9 = itemView.findViewById(R.id.img_picture);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "itemView.findViewById(R.id.img_picture)");
            this.imgPicture = (RImageView) viewFindViewById9;
            View viewFindViewById10 = itemView.findViewById(R.id.tv_nick_name);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "itemView.findViewById(R.id.tv_nick_name)");
            this.tvNickName = (RTextView) viewFindViewById10;
            View viewFindViewById11 = itemView.findViewById(R.id.tv_hospital);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "itemView.findViewById(R.id.tv_hospital)");
            this.tvHospital = (TextView) viewFindViewById11;
            View viewFindViewById12 = itemView.findViewById(R.id.tv_time);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "itemView.findViewById(R.id.tv_time)");
            this.tvTime = (TextView) viewFindViewById12;
            View viewFindViewById13 = itemView.findViewById(R.id.tv_comment);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "itemView.findViewById(R.id.tv_comment)");
            this.tvComment = (TextView) viewFindViewById13;
            View viewFindViewById14 = itemView.findViewById(R.id.tv_reply);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById14, "itemView.findViewById(R.id.tv_reply)");
            this.tvReply = (RTextView) viewFindViewById14;
            View viewFindViewById15 = itemView.findViewById(R.id.ly_tab_view);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById15, "itemView.findViewById(R.id.ly_tab_view)");
            this.lyTabView = (RLinearLayout) viewFindViewById15;
            View viewFindViewById16 = itemView.findViewById(R.id.btn_hot);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById16, "itemView.findViewById(R.id.btn_hot)");
            this.btnHot = (RTextView) viewFindViewById16;
            View viewFindViewById17 = itemView.findViewById(R.id.btn_news);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById17, "itemView.findViewById(R.id.btn_news)");
            this.btnNews = (RTextView) viewFindViewById17;
        }

        @NotNull
        public final RTextView getBtnHot() {
            return this.btnHot;
        }

        @NotNull
        public final RTextView getBtnNews() {
            return this.btnNews;
        }

        @NotNull
        public final RImageView getImgAvatar() {
            return this.imgAvatar;
        }

        @NotNull
        public final RImageView getImgPicture() {
            return this.imgPicture;
        }

        @NotNull
        public final LinearLayout getLyContentView() {
            return this.lyContentView;
        }

        @NotNull
        public final LinearLayout getLyMoreView() {
            return this.lyMoreView;
        }

        @NotNull
        public final RLinearLayout getLyTabView() {
            return this.lyTabView;
        }

        @NotNull
        public final LinearLayout getLyTitleView() {
            return this.lyTitleView;
        }

        @NotNull
        public final TextView getTvComment() {
            return this.tvComment;
        }

        @NotNull
        public final TextView getTvHospital() {
            return this.tvHospital;
        }

        @NotNull
        public final TextView getTvMoreValue() {
            return this.tvMoreValue;
        }

        @NotNull
        public final RTextView getTvNickName() {
            return this.tvNickName;
        }

        @NotNull
        public final RTextView getTvOpposition() {
            return this.tvOpposition;
        }

        @NotNull
        public final RTextView getTvReply() {
            return this.tvReply;
        }

        @NotNull
        public final RTextView getTvSupport() {
            return this.tvSupport;
        }

        @NotNull
        public final TextView getTvTime() {
            return this.tvTime;
        }

        @NotNull
        public final TextView getTvTitleView() {
            return this.tvTitleView;
        }
    }

    public BookReviewAdp(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.mData = new ArrayList<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getView$lambda$0(BookReview item, BookReviewAdp this$0, int i2, View view) {
        Function2<? super Integer, ? super BookReview, Unit> function2;
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (item.isOpposition() == 1 || (function2 = this$0.onItemSupportAction) == null) {
            return;
        }
        function2.invoke(Integer.valueOf(i2), item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getView$lambda$1(BookReview item, BookReviewAdp this$0, int i2, View view) {
        Function2<? super Integer, ? super BookReview, Unit> function2;
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (item.isSupport() == 1 || (function2 = this$0.onItemOpposeAction) == null) {
            return;
        }
        function2.invoke(Integer.valueOf(i2), item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getView$lambda$2(BookReviewAdp this$0, int i2, BookReview item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        Function2<? super Integer, ? super BookReview, Unit> function2 = this$0.onItemReplyAction;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i2), item);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void getView$lambda$3(BookReviewAdp this$0, BookReview item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        new XPopup.Builder(this$0.context).asImageViewer(null, item.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void getView$lambda$4(Ref.ObjectRef viewHolder, BookReviewAdp this$0, View view) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.INSTANCE.logE("click_hot_news", "点击了最热");
        ((ContentViewHolder) viewHolder.element).getBtnNews().setSelected(false);
        ((ContentViewHolder) viewHolder.element).getBtnHot().setSelected(true);
        Function1<? super Boolean, Unit> function1 = this$0.onItemHotAndNewsAction;
        if (function1 != null) {
            function1.invoke(Boolean.TRUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void getView$lambda$5(Ref.ObjectRef viewHolder, BookReviewAdp this$0, View view) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.INSTANCE.logE("click_hot_news", "点击了最新");
        ((ContentViewHolder) viewHolder.element).getBtnNews().setSelected(true);
        ((ContentViewHolder) viewHolder.element).getBtnHot().setSelected(false);
        Function1<? super Boolean, Unit> function1 = this$0.onItemHotAndNewsAction;
        if (function1 != null) {
            function1.invoke(Boolean.FALSE);
        }
    }

    public final void addData(@NotNull List<BookReview> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mData.size();
    }

    @NotNull
    public final ArrayList<BookReview> getData() {
        return this.mData;
    }

    @Override // android.widget.Adapter
    @NotNull
    public Object getItem(int position) {
        BookReview bookReview = this.mData.get(position);
        Intrinsics.checkNotNullExpressionValue(bookReview, "mData[position]");
        return bookReview;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        String id;
        ArrayList<BookReview> arrayList = this.mData;
        if (arrayList == null || arrayList.isEmpty() || (id = this.mData.get(position).getId()) == null || !new Regex(RegexPool.NUMBERS).matches(id)) {
            return 0L;
        }
        return Long.parseLong(id);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        Object item = getItem(position);
        Intrinsics.checkNotNull(item, "null cannot be cast to non-null type com.ykb.ebook.model.BookReview");
        return ((BookReview) item).getType();
    }

    @Nullable
    public final Function2<Integer, BookReview, Unit> getOnItemClickAction() {
        return this.onItemClickAction;
    }

    @Nullable
    public final Function1<Boolean, Unit> getOnItemHotAndNewsAction() {
        return this.onItemHotAndNewsAction;
    }

    @Nullable
    public final Function2<Integer, BookReview, Unit> getOnItemOpposeAction() {
        return this.onItemOpposeAction;
    }

    @Nullable
    public final Function2<Integer, BookReview, Unit> getOnItemReplyAction() {
        return this.onItemReplyAction;
    }

    @Nullable
    public final Function2<Integer, BookReview, Unit> getOnItemSupportAction() {
        return this.onItemSupportAction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, com.ykb.ebook.adapter.BookReviewAdp$ContentViewHolder] */
    /* JADX WARN: Type inference failed for: r2v88, types: [T, com.ykb.ebook.adapter.BookReviewAdp$ContentViewHolder, java.lang.Object] */
    @Override // android.widget.Adapter
    @NotNull
    public View getView(final int position, @Nullable View convertView, @Nullable ViewGroup parent) throws SecurityException {
        View view;
        String str;
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        BookReview bookReview = this.mData.get(position);
        Intrinsics.checkNotNullExpressionValue(bookReview, "mData[position]");
        final BookReview bookReview2 = bookReview;
        if (convertView == null) {
            View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_book_review, (ViewGroup) null);
            ?? contentViewHolder = new ContentViewHolder(viewInflate);
            objectRef.element = contentViewHolder;
            viewInflate.setTag(contentViewHolder);
            view = viewInflate;
        } else {
            Object tag = convertView.getTag();
            Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type com.ykb.ebook.adapter.BookReviewAdp.ContentViewHolder");
            objectRef.element = (ContentViewHolder) tag;
            view = convertView;
        }
        if (bookReview2.getType() == 1) {
            ViewExtensionsKt.visible(((ContentViewHolder) objectRef.element).getLyTitleView());
            ViewExtensionsKt.gone(((ContentViewHolder) objectRef.element).getLyContentView());
            ((ContentViewHolder) objectRef.element).getTvTitleView().setText(bookReview2.getShowName());
            if (StringsKt__StringsKt.contains$default((CharSequence) bookReview2.getShowName(), (CharSequence) "最热评论", false, 2, (Object) null)) {
                ViewExtensionsKt.visible(((ContentViewHolder) objectRef.element).getLyTabView());
                ((ContentViewHolder) objectRef.element).getBtnHot().setSelected(true);
                ((ContentViewHolder) objectRef.element).getBtnNews().setSelected(false);
            } else if (this.isShowNews) {
                ViewExtensionsKt.visible(((ContentViewHolder) objectRef.element).getLyTabView());
                ((ContentViewHolder) objectRef.element).getBtnHot().setSelected(false);
                ((ContentViewHolder) objectRef.element).getBtnNews().setSelected(true);
            } else {
                ViewExtensionsKt.gone(((ContentViewHolder) objectRef.element).getLyTabView());
            }
        } else {
            ViewExtensionsKt.gone(((ContentViewHolder) objectRef.element).getLyTitleView());
            ViewExtensionsKt.visible(((ContentViewHolder) objectRef.element).getLyContentView());
        }
        if (bookReview2.getOtherView() == 3) {
            ViewExtensionsKt.visible(((ContentViewHolder) objectRef.element).getLyMoreView());
            ((ContentViewHolder) objectRef.element).getTvMoreValue().setText(bookReview2.getShowName());
        } else {
            ViewExtensionsKt.gone(((ContentViewHolder) objectRef.element).getLyMoreView());
        }
        ImageLoader imageLoader = ImageLoader.INSTANCE;
        imageLoader.load(this.context, bookReview2.getAvatar()).placeholder(R.drawable.personal_headimg_icon).into(((ContentViewHolder) objectRef.element).getImgAvatar());
        ((ContentViewHolder) objectRef.element).getTvSupport().setText("赞同(" + bookReview2.getSupportCount() + ')');
        ((ContentViewHolder) objectRef.element).getTvSupport().setSelected(bookReview2.isSupport() == 1);
        ((ContentViewHolder) objectRef.element).getTvOpposition().setText("反对(" + bookReview2.getOppositionCount() + ')');
        ((ContentViewHolder) objectRef.element).getTvOpposition().setSelected(bookReview2.isOpposition() == 1);
        ((ContentViewHolder) objectRef.element).getTvNickName().setText(bookReview2.getNickname());
        ((ContentViewHolder) objectRef.element).getTvTime().setText(bookReview2.getCtime());
        ((ContentViewHolder) objectRef.element).getTvHospital().setText(bookReview2.getSchool());
        ((ContentViewHolder) objectRef.element).getTvComment().setText(bookReview2.getComment());
        ((ContentViewHolder) objectRef.element).getImgPicture().setVisibility(bookReview2.getPicture().length() == 0 ? 8 : 0);
        if (bookReview2.getPicture().length() > 0) {
            imageLoader.load(this.context, bookReview2.getPicture()).into(((ContentViewHolder) objectRef.element).getImgPicture());
        }
        ((ContentViewHolder) objectRef.element).getTvReply().getHelper().setBackgroundColorNormal(bookReview2.getReplyCount() > 0 ? AppCtxKt.getAppCtx().getColor(R.color.color_0a000000) : AppCtxKt.getAppCtx().getColor(android.R.color.transparent));
        RTextView tvReply = ((ContentViewHolder) objectRef.element).getTvReply();
        if (bookReview2.getReplyCount() > 0) {
            str = "回复（" + bookReview2.getReplyCount() + (char) 65289;
        } else {
            str = "回复";
        }
        tvReply.setText(str);
        ((ContentViewHolder) objectRef.element).getTvReply().getHelper().setSelected(bookReview2.getReplyCount() > 0);
        ((ContentViewHolder) objectRef.element).getTvReply().setSelected(bookReview2.getReplyCount() > 0);
        ((ContentViewHolder) objectRef.element).getTvSupport().setOnClickListener(null);
        ((ContentViewHolder) objectRef.element).getTvSupport().setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BookReviewAdp.getView$lambda$0(bookReview2, this, position, view2);
            }
        });
        ((ContentViewHolder) objectRef.element).getTvOpposition().setOnClickListener(null);
        ((ContentViewHolder) objectRef.element).getTvOpposition().setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BookReviewAdp.getView$lambda$1(bookReview2, this, position, view2);
            }
        });
        ((ContentViewHolder) objectRef.element).getTvReply().setOnClickListener(null);
        ((ContentViewHolder) objectRef.element).getTvReply().setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BookReviewAdp.getView$lambda$2(this.f26254c, position, bookReview2, view2);
            }
        });
        ((ContentViewHolder) objectRef.element).getImgPicture().setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BookReviewAdp.getView$lambda$3(this.f26257c, bookReview2, view2);
            }
        });
        ((ContentViewHolder) objectRef.element).getBtnHot().setOnClickListener(null);
        ((ContentViewHolder) objectRef.element).getBtnHot().setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BookReviewAdp.getView$lambda$4(objectRef, this, view2);
            }
        });
        ((ContentViewHolder) objectRef.element).getBtnNews().setOnClickListener(null);
        ((ContentViewHolder) objectRef.element).getBtnNews().setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.adapter.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BookReviewAdp.getView$lambda$5(objectRef, this, view2);
            }
        });
        Intrinsics.checkNotNull(view);
        return view;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // com.ykb.ebook.weight.PinnedSectionListView.PinnedSectionListAdapter
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    /* renamed from: isShowNews, reason: from getter */
    public final boolean getIsShowNews() {
        return this.isShowNews;
    }

    public final void setData(@NotNull List<BookReview> data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public final void setIsHaveHot(boolean isHave) {
        this.isHaveHot = isHave;
    }

    public final void setOnItemClickAction(@Nullable Function2<? super Integer, ? super BookReview, Unit> function2) {
        this.onItemClickAction = function2;
    }

    public final void setOnItemHotAndNewsAction(@Nullable Function1<? super Boolean, Unit> function1) {
        this.onItemHotAndNewsAction = function1;
    }

    public final void setOnItemOpposeAction(@Nullable Function2<? super Integer, ? super BookReview, Unit> function2) {
        this.onItemOpposeAction = function2;
    }

    public final void setOnItemReplyAction(@Nullable Function2<? super Integer, ? super BookReview, Unit> function2) {
        this.onItemReplyAction = function2;
    }

    public final void setOnItemSupportAction(@Nullable Function2<? super Integer, ? super BookReview, Unit> function2) {
        this.onItemSupportAction = function2;
    }

    public final void setShowNews(boolean z2) {
        this.isShowNews = z2;
    }
}
