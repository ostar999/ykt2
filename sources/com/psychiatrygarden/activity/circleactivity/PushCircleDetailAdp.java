package com.psychiatrygarden.activity.circleactivity;

import android.text.SpannableStringBuilder;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.utils.VerticalImageSpan;
import com.psychiatrygarden.widget.AutoSplitTextView;
import com.yikaobang.yixue.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class PushCircleDetailAdp extends BaseQuickAdapter<CirclrListBean.DataBean, BaseViewHolder> implements LoadMoreModule {
    public PushCircleDetailAdp() {
        super(R.layout.item_push_circle_detail);
    }

    private void getImageData(StringBuffer stringBuffer, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(stringBuffer.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new VerticalImageSpan(new URLImageParser(mTextView, getContext(), (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1))), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, CirclrListBean.DataBean item) {
        AutoSplitTextView autoSplitTextView = (AutoSplitTextView) helper.getView(R.id.tv_name);
        autoSplitTextView.setText(item.getTitle());
        StringBuffer stringBuffer = new StringBuffer();
        if (item.getIcon_img() == null || item.getIcon_img().size() <= 0) {
            autoSplitTextView.setText(item.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", ""));
        } else {
            String strReplaceAll = item.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "");
            if (!(strReplaceAll + helper.getAdapterPosition()).equals(autoSplitTextView.getTag())) {
                for (int i2 = 0; i2 < item.getIcon_img().size(); i2++) {
                    stringBuffer.append(StrPool.BRACKET_START);
                    stringBuffer.append(item.getIcon_img().get(i2));
                    stringBuffer.append(StrPool.BRACKET_END);
                }
                stringBuffer.append(strReplaceAll);
                getImageData(stringBuffer, autoSplitTextView);
            }
        }
        autoSplitTextView.setTag(item.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "") + helper.getAdapterPosition());
    }
}
