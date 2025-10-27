package com.psychiatrygarden.activity.online.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.online.bean.QuestionDetailBean;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class AnswerSheetAdapter extends BaseQuickAdapter<QuestionDetailBean, BaseViewHolder> {
    private boolean isKnowledgeChart;
    private boolean isTestMode;
    private int itemMargin;
    private List<String> judgeTypes;

    public AnswerSheetAdapter(@Nullable List<QuestionDetailBean> data) {
        super(R.layout.item_knowledge_answer_sheet, data);
        ArrayList arrayList = new ArrayList();
        this.judgeTypes = arrayList;
        arrayList.clear();
        this.judgeTypes.add("1");
        this.judgeTypes.add("2");
        this.judgeTypes.add(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        this.judgeTypes.add(Constants.VIA_ACT_TYPE_NINETEEN);
        this.judgeTypes.add(Constants.VIA_REPORT_TYPE_DATALINE);
    }

    private boolean isItemInLastRow(int position, RecyclerView recyclerView, int totalItems) {
        if (totalItems == 0 || position < 0 || position >= totalItems) {
            return false;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (!(layoutManager instanceof GridLayoutManager)) {
            return false;
        }
        int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        if (totalItems <= spanCount) {
            return true;
        }
        int i2 = totalItems % spanCount;
        return position >= (i2 == 0 ? totalItems - spanCount : totalItems - i2) && position < totalItems;
    }

    private void testModeShow(TextView tv2, boolean isCut, QuestionDetailBean item) {
        StringBuilder sb = new StringBuilder();
        if (this.judgeTypes.contains(item.getType())) {
            for (int i2 = 0; i2 < item.getOption().size(); i2++) {
                if (item.getOption().get(i2).getType().equals("1")) {
                    sb.append(item.getOption().get(i2).getKey());
                }
            }
        } else if (item.getmAnalysisBean() != null && !TextUtils.isEmpty(item.getmAnalysisBean().getAnalysis())) {
            sb = new StringBuilder(item.getmAnalysisBean().getAnalysis());
        }
        if (!TextUtils.isEmpty(sb.toString())) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                tv2.setBackgroundResource(!isCut ? R.drawable.drawzble_quetionlist_black_round : R.drawable.drawzble_quetionlist_black_cut_round);
            } else {
                tv2.setBackgroundResource(!isCut ? R.drawable.drawable_an_night_round : R.drawable.drawable_an_night_cut_round);
            }
            tv2.setTextColor(getContext().getResources().getColor(R.color.white));
            return;
        }
        if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            tv2.setBackgroundResource(!isCut ? R.drawable.drawable_gray_round : R.drawable.drawable_gray_night_cut_round);
            tv2.setTextColor(ContextCompat.getColor(getContext(), R.color.color_ff303030));
        } else {
            tv2.setBackgroundResource(R.drawable.drawable_gray_night_round);
            tv2.setTextColor(ContextCompat.getColor(getContext(), R.color.first_text_color_night));
        }
    }

    public boolean isTestMode() {
        return this.isTestMode;
    }

    public void setKnowledgeChart(boolean chart) {
        this.isKnowledgeChart = chart;
    }

    public void setTestMode(boolean testMode) {
        this.isTestMode = testMode;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, QuestionDetailBean item, @NonNull List payloads) {
        convert2(holder, item, (List<?>) payloads);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NonNull BaseViewHolder holder, QuestionDetailBean item, @NonNull List<?> payloads) {
        super.convert((AnswerSheetAdapter) holder, (BaseViewHolder) item, (List<? extends Object>) payloads);
        if (payloads.size() > 0) {
            Iterator<?> it = payloads.iterator();
            while (it.hasNext()) {
                if (TextUtils.equals("CONTINUE_DO", it.next().toString())) {
                    ((TextView) holder.getView(R.id.img_conunite)).setVisibility(item.isShowConunite() ? 0 : 8);
                    if (item.isShowConunite()) {
                        holder.getView(R.id.iv_question_new).setVisibility(8);
                    } else {
                        holder.getView(R.id.iv_question_new).setVisibility(item.getIs_new().equals("1") ? 0 : 8);
                    }
                }
            }
        }
    }

    public AnswerSheetAdapter(@Nullable List<QuestionDetailBean> data, int itemLayoutId) {
        super(itemLayoutId, data);
        ArrayList arrayList = new ArrayList();
        this.judgeTypes = arrayList;
        arrayList.clear();
        this.judgeTypes.add("1");
        this.judgeTypes.add("2");
        this.judgeTypes.add(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        this.judgeTypes.add(Constants.VIA_ACT_TYPE_NINETEEN);
        this.judgeTypes.add(Constants.VIA_REPORT_TYPE_DATALINE);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder helper, QuestionDetailBean item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_cut_slash);
        TextView textView = (TextView) helper.getView(R.id.questionList_item_tv);
        View view = helper.itemView;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = SizeUtil.dp2px(getContext(), 16);
        if (isItemInLastRow(helper.getLayoutPosition(), getRecyclerView(), getData().size())) {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = SizeUtil.dp2px(getContext(), 16);
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = 0;
        }
        view.setLayoutParams(layoutParams);
        imageView.setVisibility(TextUtils.equals("1", item.getIs_cut()) ? 0 : 8);
        textView.setText(item.getSort());
        TextView textView2 = (TextView) helper.getView(R.id.img_conunite);
        if (item.isShowConunite()) {
            helper.getView(R.id.iv_question_new).setVisibility(8);
        } else {
            helper.getView(R.id.iv_question_new).setVisibility(item.getIs_new().equals("1") ? 0 : 8);
        }
        textView2.setVisibility(item.isShowConunite() ? 0 : 8);
        boolean zEquals = TextUtils.equals("1", item.getIs_cut());
        if (item.getIs_right().equals("0")) {
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                textView.setBackgroundResource(!zEquals ? R.drawable.drawable_quetionlist_red_round : R.drawable.bg_answer_sheet_wrong_cut_day_round);
            } else {
                textView.setBackgroundResource(!zEquals ? R.drawable.drawable_red_night_oval : R.drawable.bg_answer_sheet_wrong_cut_night_round);
            }
            textView.setTextColor(getContext().getResources().getColor(R.color.white));
            return;
        }
        if (item.getIs_right().equals("1")) {
            if (zEquals) {
                textView.setBackgroundResource(R.drawable.bg_answer_sheet_right_cut_round);
            } else if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                textView.setBackgroundResource(R.drawable.drawable_quetionlist_green_round);
            } else {
                textView.setBackgroundResource(R.drawable.drawable_green_night_round);
            }
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            return;
        }
        testModeShow(textView, zEquals, item);
    }
}
