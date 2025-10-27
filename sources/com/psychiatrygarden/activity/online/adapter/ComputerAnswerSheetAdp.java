package com.psychiatrygarden.activity.online.adapter;

import android.content.res.Resources;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class ComputerAnswerSheetAdp extends BaseQuickAdapter<ExesQuestionBean, BaseViewHolder> {
    private int currentPosition;
    private OnItemClickLisenter onItemClickLisenter;

    public static abstract class OnItemClickLisenter {
        public abstract void itemClick(int pos, ExesQuestionBean item);
    }

    public ComputerAnswerSheetAdp() {
        super(R.layout.item_computer_mock_question);
        this.currentPosition = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, ExesQuestionBean exesQuestionBean, View view) {
        OnItemClickLisenter onItemClickLisenter = this.onItemClickLisenter;
        if (onItemClickLisenter != null) {
            onItemClickLisenter.itemClick(baseViewHolder.getLayoutPosition(), exesQuestionBean);
        }
    }

    private void setTextUi(TextView tv2, ExesQuestionBean item) {
        Resources resources;
        int i2;
        if (item.isBiaoDoubt()) {
            tv2.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? getContext().getResources().getColor(R.color.new_bg_one_color) : getContext().getResources().getColor(R.color.new_bg_one_color_night));
            tv2.setBackgroundResource(R.drawable.shape_computer_yellow_question_bg);
            return;
        }
        int answer_mode = item.getAnswer_mode();
        if (answer_mode != 0) {
            if (answer_mode != 2) {
                return;
            }
            tv2.setTextColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? getContext().getResources().getColor(R.color.new_bg_one_color) : getContext().getResources().getColor(R.color.new_bg_one_color_night));
            tv2.setBackgroundResource(R.drawable.shape_computer_answerd_question_bg);
            return;
        }
        if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            resources = getContext().getResources();
            i2 = R.color.first_txt_color;
        } else {
            resources = getContext().getResources();
            i2 = R.color.first_txt_color_night;
        }
        tv2.setTextColor(resources.getColor(i2));
        tv2.setBackgroundResource(R.drawable.shape_computer_no_answer_question_bg);
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setOnItemClickLisenter(OnItemClickLisenter lisenter) {
        this.onItemClickLisenter = lisenter;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(@NonNull BaseViewHolder holder, ExesQuestionBean item, @NonNull List payloads) {
        convert2(holder, item, (List<?>) payloads);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NonNull BaseViewHolder holder, ExesQuestionBean item, @NonNull List<?> payloads) {
        super.convert((ComputerAnswerSheetAdp) holder, (BaseViewHolder) item, (List<? extends Object>) payloads);
        if (payloads.size() <= 0 || ((Integer) payloads.get(0)).intValue() != 0) {
            return;
        }
        setTextUi((TextView) holder.getView(R.id.tv_number), item);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull final BaseViewHolder helper, final ExesQuestionBean item) {
        TextView textView = (TextView) helper.getView(R.id.tv_number);
        View view = helper.getView(R.id.current_view);
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.ly_number);
        int iDip2px = UIUtil.dip2px(getContext(), 168.0d) / 5;
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(iDip2px, iDip2px));
        int iDip2px2 = iDip2px - UIUtil.dip2px(getContext(), 12.0d);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iDip2px2, iDip2px2);
        textView.setLayoutParams(layoutParams);
        textView.setText(item.getNumber());
        view.setLayoutParams(layoutParams);
        if (this.currentPosition == helper.getLayoutPosition()) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
        setTextUi(textView, item);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13104c.lambda$convert$0(helper, item, view2);
            }
        });
    }
}
