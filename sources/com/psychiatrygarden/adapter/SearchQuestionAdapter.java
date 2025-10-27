package com.psychiatrygarden.adapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.QuestionSearchBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class SearchQuestionAdapter extends BaseQuickAdapter<QuestionSearchBean.DataDTO, BaseViewHolder> implements LoadMoreModule {
    private WebClickOnIm mWebClickOnIm;
    private String search_content;

    public interface WebClickOnIm {
        void onClick(int positon);
    }

    public SearchQuestionAdapter(List<QuestionSearchBean.DataDTO> list, WebClickOnIm mWebClickOnIm) {
        super(R.layout.adapter_search_question, list);
        this.mWebClickOnIm = mWebClickOnIm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$convert$0(BaseViewHolder baseViewHolder, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            this.mWebClickOnIm.onClick(baseViewHolder.getBindingAdapterPosition());
        }
        return true;
    }

    @Override // com.chad.library.adapter.base.module.LoadMoreModule
    public /* synthetic */ BaseLoadMoreModule addLoadMoreModule(BaseQuickAdapter baseQuickAdapter) {
        return t0.h.a(this, baseQuickAdapter);
    }

    public void setSearchContent(String search_content) {
        this.search_content = search_content;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(final BaseViewHolder helper, QuestionSearchBean.DataDTO item) {
        WebView webView = (WebView) helper.getView(R.id.webview);
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.llay_search_option);
        TextView textView = (TextView) helper.getView(R.id.typestr);
        TextView textView2 = (TextView) helper.getView(R.id.commentnum);
        textView.setText(item.getSubject_title() + "");
        textView2.setText(item.getComment_count() + "评论");
        if ("1".equals(item.getType()) || "2".equals(item.getType()) || Constants.VIA_ACT_TYPE_NINETEEN.equals(item.getType())) {
            linearLayout.setVisibility(0);
            linearLayout.removeAllViews();
            StringBuilder sb = new StringBuilder("");
            for (int i2 = 0; i2 < item.getOption().size(); i2++) {
                sb.append(item.getOption().get(i2).getKey());
                sb.append(StrPool.DOT);
                sb.append(item.getOption().get(i2).getTitle());
                sb.append("   ");
            }
            TextView textView3 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_search_child, (ViewGroup) null);
            textView3.setText(sb.toString());
            linearLayout.addView(textView3);
        } else {
            linearLayout.setVisibility(8);
        }
        String strReplaceAll = item.getTitle().replaceAll("<p.*?>", "").replaceAll("</p>", "");
        item.getNumber();
        if (item.getNumber().contains(this.search_content)) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                item.getNumber().replace(this.search_content, "<font  color='red'>" + this.search_content + "</font>");
            } else {
                item.getNumber().replace(this.search_content, "<font  color='#B2585C'>" + this.search_content + "</font>");
            }
        } else if (item.getNumber().contains(this.search_content.toUpperCase())) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                item.getNumber().replace(this.search_content.toUpperCase(), "<font  color='red'>" + this.search_content.toUpperCase() + "</font>");
            } else {
                item.getNumber().replace(this.search_content.toUpperCase(), "<font  color='#B2585C'>" + this.search_content.toUpperCase() + "</font>");
            }
        } else if (item.getNumber().contains(this.search_content.toLowerCase())) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                item.getNumber().replace(this.search_content.toLowerCase(), "<font  color='red'>" + this.search_content.toLowerCase() + "</font>");
            } else {
                item.getNumber().replace(this.search_content.toLowerCase(), "<font  color='#B2585C'>" + this.search_content.toLowerCase() + "</font>");
            }
        }
        webView.loadDataWithBaseURL(null, CommonUtil.getHtmlData2(getContext(), strReplaceAll), "text/html; charset=utf-8", "utf-8", null);
        webView.setFocusable(false);
        webView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.adapter.qe
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f14932c.lambda$convert$0(helper, view, motionEvent);
            }
        });
    }
}
