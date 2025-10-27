package com.psychiatrygarden.adapter;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class CircleSearchContentAdapter extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> implements LoadMoreModule {
    private String searchContent;
    private boolean showLine;

    public CircleSearchContentAdapter(int layoutResId, @Nullable List<CirclrListBean.DataBean> data, boolean showLine) {
        super(layoutResId, data);
        this.showLine = showLine;
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    public void getImageData(String content, TextView mTextView, CirclrListBean.DataBean dataBean) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
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
            for (int i2 = 0; i2 < dataBean.getIcon_img().size(); i2++) {
                if (!dataBean.getIcon_img().get(i2).isEmpty()) {
                    spannableStringBuilder.insert(0, (CharSequence) (StrPool.BRACKET_START + dataBean.getIcon_img().get(i2) + StrPool.BRACKET_END));
                }
            }
            Matcher matcher2 = Pattern.compile("\\[[^\\]]+\\]").matcher(spannableStringBuilder);
            while (matcher2.find()) {
                String strGroup = matcher2.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new StickerSpan(new URLImageParser(mTextView, getContext(), (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1)), 1), matcher2.start(), matcher2.end(), 33);
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
    public void convert(BaseViewHolder helper, CirclrListBean.DataBean dataBean) {
        TextView textView = (TextView) helper.getView(R.id.title);
        TextView textView2 = (TextView) helper.getView(R.id.tv_desc);
        textView2.setVisibility(0);
        helper.setText(R.id.time, dataBean.getComment_time()).setText(R.id.commnum, dataBean.getComment_count() + "回复").setGone(R.id.line, !this.showLine);
        getImageData(dataBean.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", ""), textView, dataBean);
        getImageData(dataBean.getContent().replaceAll("<font.*?>", "").replaceAll("</font>", ""), textView2, dataBean);
    }
}
