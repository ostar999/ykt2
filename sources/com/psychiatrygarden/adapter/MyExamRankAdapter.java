package com.psychiatrygarden.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.RankingContentBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class MyExamRankAdapter extends BaseQuickAdapter<RankingContentBean.DataBean.RankingBean, BaseViewHolder> {
    public MyExamRankAdapter() {
        super(R.layout.adapter_ranking_exam_content, new ArrayList(0));
    }

    private String getExamTimeStr(String remain) {
        String str;
        String str2;
        int i2 = !TextUtils.isEmpty(remain) ? Integer.parseInt(remain) : 0;
        if (i2 == 0) {
            return "0秒";
        }
        int i3 = i2 / 3600;
        int i4 = i2 % 3600;
        int i5 = i4 / 60;
        int i6 = i4 % 60;
        StringBuilder sb = new StringBuilder();
        String str3 = "";
        if (i3 <= 0) {
            str = "";
        } else {
            str = i3 + "时";
        }
        sb.append(str);
        if (i5 <= 0) {
            str2 = "";
        } else {
            str2 = i5 + "分";
        }
        sb.append(str2);
        if (i6 > 0) {
            str3 = i6 + "秒";
        }
        sb.append(str3);
        return sb.toString();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, RankingContentBean.DataBean.RankingBean item) {
        BaseViewHolder text = holder.setText(R.id.tv_ranking_grade, "成绩: " + item.getScore() + "分");
        StringBuilder sb = new StringBuilder();
        sb.append("用时: ");
        sb.append(getExamTimeStr(item.getExam_time()));
        text.setText(R.id.tv_ranking_accuracy, sb.toString()).setText(R.id.tv_ranking_name, item.getNickname());
        GlideUtils.loadImage(holder.getView(R.id.iv_header_img).getContext(), item.getAvatar(), (ImageView) holder.getView(R.id.iv_header_img), R.drawable.personal_headimg_icon, R.drawable.personal_headimg_icon);
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.circleimg);
        TextView textView = (TextView) holder.getView(R.id.tv_ranking_num);
        int bindingAdapterPosition = holder.getBindingAdapterPosition();
        if (bindingAdapterPosition == 1) {
            circleImageView.setVisibility(0);
            textView.setVisibility(8);
            circleImageView.setImageResource(R.drawable.rankone);
        } else if (bindingAdapterPosition == 2) {
            circleImageView.setVisibility(0);
            textView.setVisibility(8);
            circleImageView.setImageResource(R.drawable.ranktwo);
        } else if (bindingAdapterPosition != 3) {
            circleImageView.setVisibility(8);
            textView.setVisibility(0);
            textView.setText(String.valueOf(holder.getBindingAdapterPosition()));
        } else {
            circleImageView.setVisibility(0);
            textView.setVisibility(8);
            circleImageView.setImageResource(R.drawable.rankthree);
        }
    }
}
