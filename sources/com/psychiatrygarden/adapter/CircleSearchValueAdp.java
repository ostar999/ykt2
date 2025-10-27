package com.psychiatrygarden.adapter;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ForumTopBean;
import com.yikaobang.yixue.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class CircleSearchValueAdp extends BaseQuickAdapter<ForumTopBean.KeyWordsData, BaseViewHolder> implements LoadMoreModule {
    private String searchContent;

    public CircleSearchValueAdp() {
        super(R.layout.item_search_value);
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

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, ForumTopBean.KeyWordsData item) {
        TextView textView = (TextView) helper.getView(R.id.tv_content);
        textView.setText(item.getKeywords());
        getImageData(item.getKeywords().replaceAll("<font.*?>", "").replaceAll("</font>", ""), textView);
    }
}
