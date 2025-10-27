package com.ykb.ebook.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ruffian.library.widget.REditText;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.dialog.ChapterSearchDialog;
import com.ykb.ebook.model.TextSearchResult;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\n¢\u0006\u0002\b\b"}, d2 = {"<anonymous>", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "pos", "", "item", "Lcom/ykb/ebook/model/TextSearchResult;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nChapterSearchDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChapterSearchDialog.kt\ncom/ykb/ebook/dialog/ChapterSearchDialog$Builder$adapter$2$1$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Background.kt\nsplitties/views/BackgroundKt\n*L\n1#1,348:1\n1864#2,2:349\n1866#2:354\n32#3:351\n32#3:352\n32#3:353\n*S KotlinDebug\n*F\n+ 1 ChapterSearchDialog.kt\ncom/ykb/ebook/dialog/ChapterSearchDialog$Builder$adapter$2$1$1\n*L\n81#1:349,2\n81#1:354\n89#1:351\n94#1:352\n99#1:353\n*E\n"})
/* loaded from: classes7.dex */
public final class ChapterSearchDialog$Builder$adapter$2$1$1 extends Lambda implements Function3<QuickViewHolder, Integer, TextSearchResult, Unit> {
    final /* synthetic */ Context $context;
    final /* synthetic */ ChapterSearchDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChapterSearchDialog$Builder$adapter$2$1$1(ChapterSearchDialog.Builder builder, Context context) {
        super(3);
        this.this$0 = builder;
        this.$context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$1$lambda$0(ChapterSearchDialog.Builder this$0, int i2, TextSearchResult textSearchResult, String str, String keyword, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(str, "$str");
        Intrinsics.checkNotNullParameter(keyword, "$keyword");
        this$0.dismiss();
        Function4 function4 = this$0.onItemClick;
        if (function4 != null) {
            function4.invoke(Integer.valueOf(i2), textSearchResult, str, keyword);
        }
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, TextSearchResult textSearchResult) {
        invoke(quickViewHolder, num.intValue(), textSearchResult);
        return Unit.INSTANCE;
    }

    public final void invoke(@NotNull QuickViewHolder holder, final int i2, @Nullable final TextSearchResult textSearchResult) {
        String string;
        Iterator it;
        Intrinsics.checkNotNullParameter(holder, "holder");
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ll_sub_title);
        linearLayout.removeAllViews();
        Intrinsics.checkNotNull(textSearchResult);
        List<String> paragraphList = textSearchResult.getParagraphList();
        int i3 = 2;
        int color = this.this$0.getColor(ReadConfig.INSTANCE.getColorMode() != 2 ? R.color.color_F95843 : R.color.color_B2575C);
        Context context = this.$context;
        final ChapterSearchDialog.Builder builder = this.this$0;
        Iterator it2 = paragraphList.iterator();
        boolean z2 = false;
        int i4 = 0;
        while (it2.hasNext()) {
            Object next = it2.next();
            int i5 = i4 + 1;
            if (i4 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            final String str = (String) next;
            View viewInflate = LayoutInflater.from(context).inflate(R.layout.item_chapter_search_sub, linearLayout, z2);
            View viewFindViewById = viewInflate.findViewById(R.id.tv_title);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById, "insertView.findViewById(R.id.tv_title)");
            TextView textView = (TextView) viewFindViewById;
            View viewFindViewById2 = viewInflate.findViewById(R.id.viewLine);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "insertView.findViewById(R.id.viewLine)");
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                textView.setTextColor(builder.getColor(R.color.color_303030));
                viewFindViewById2.setBackgroundColor(builder.getColor(R.color.color_eeeeee));
            } else if (colorMode == 1) {
                textView.setTextColor(builder.getColor(R.color.color_303030));
                viewFindViewById2.setBackgroundColor(builder.getColor(R.color.color_EDE2C3));
            } else if (colorMode == i3) {
                textView.setTextColor(builder.getColor(R.color.color_7380a9));
                viewFindViewById2.setBackgroundColor(builder.getColor(R.color.color_theme_blue_line_color));
            }
            REditText etInput = builder.getEtInput();
            Intrinsics.checkNotNull(etInput);
            Editable text = etInput.getText();
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            final String str2 = string;
            if (TextUtils.isEmpty(str2)) {
                it = it2;
                textView.setText(str);
            } else {
                SpannableString spannableString = new SpannableString(str);
                Matcher matcher = Pattern.compile(str2).matcher(str);
                while (matcher.find()) {
                    spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), 18);
                    it2 = it2;
                }
                it = it2;
                textView.setText(spannableString);
            }
            linearLayout.addView(viewInflate);
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.a0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChapterSearchDialog$Builder$adapter$2$1$1.invoke$lambda$1$lambda$0(builder, i2, textSearchResult, str, str2, view);
                }
            });
            i4 = i5;
            it2 = it;
            i3 = 2;
            z2 = false;
        }
        int i6 = R.id.tv_title;
        holder.setText(i6, (char) 31532 + textSearchResult.getSort() + "章 " + textSearchResult.getTitle());
        holder.setGone(R.id.img_lock, Intrinsics.areEqual(textSearchResult.getPass(), "1"));
        int colorMode2 = ReadConfig.INSTANCE.getColorMode();
        if (colorMode2 == 0) {
            holder.setTextColor(i6, this.this$0.getColor(R.color.color_909090));
        } else if (colorMode2 == 1) {
            holder.setTextColor(i6, this.this$0.getColor(R.color.color_909090));
        } else {
            if (colorMode2 != 2) {
                return;
            }
            holder.setTextColor(i6, this.this$0.getColor(R.color.color_575F79));
        }
    }
}
