package com.psychiatrygarden.activity.ebook;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.BookStackBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class ProjectBookStoreAdp extends BaseQuickAdapter<BookStackBean.BookStackData, BaseViewHolder> {
    private String searchContent;

    public ProjectBookStoreAdp() {
        super(R.layout.item_book_store);
    }

    private void getImageData(String content, TextView mTextView) {
        try {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content.toString());
            String str = this.searchContent;
            if (str != null && !"".equals(str)) {
                for (String str2 : this.searchContent.split("\\s+")) {
                    String strReplace = str2.replace("\\s+", "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, getContext().getResources().getColorStateList(R.color.app_theme_red), null), matcher.start(0), matcher.end(0), 34);
                        }
                    }
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, BookStackBean.BookStackData item) {
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.iv_book_img);
        TextView textView = (TextView) holder.getView(R.id.tv_book_name);
        TextView textView2 = (TextView) holder.getView(R.id.tv_desc);
        TextView textView3 = (TextView) holder.getView(R.id.tv_read_people_num);
        TextView textView4 = (TextView) holder.getView(R.id.tv_comment_num);
        View view = holder.getView(R.id.line);
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getThumbnail())).into(roundedImageView);
        textView.setText(item.getBookTitle());
        textView3.setText(item.getRead_count() + " 人阅读");
        textView2.setText(item.getDescribe());
        textView4.setText(item.getBook_review_count() + " 评论");
        getImageData(item.getBookTitle(), textView);
        if (holder.getLayoutPosition() == 0) {
            view.setVisibility(8);
        } else {
            view.setVisibility(0);
        }
    }
}
