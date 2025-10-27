package com.psychiatrygarden.activity.online.adapter;

import android.graphics.Color;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.activity.online.bean.ErrorCorrectionGettypeBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionRestoreTypeAdapter extends BaseQuickAdapter<ErrorCorrectionGettypeBean.DataDTO, BaseViewHolder> {
    private int selectPosition;

    public QuestionRestoreTypeAdapter(@Nullable List<ErrorCorrectionGettypeBean.DataDTO> data) {
        super(R.layout.item_question_restore_type, data);
        this.selectPosition = 0;
    }

    public int getSelectPosition() {
        return this.selectPosition;
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, ErrorCorrectionGettypeBean.DataDTO item) {
        TextView textView = (TextView) helper.getView(R.id.tv_restore_type);
        textView.setText(item.getTitle());
        if (helper.getBindingAdapterPosition() == this.selectPosition) {
            textView.setBackgroundResource(R.drawable.ffdd594a_16);
            textView.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
            return;
        }
        textView.setBackgroundResource(R.drawable.ffffffff_16_stroke_1);
        if (SkinManager.getCurrentSkinType(getContext()) == 0) {
            textView.setTextColor(Color.parseColor("#303030"));
        } else {
            textView.setTextColor(Color.parseColor("#7380A9"));
        }
    }
}
