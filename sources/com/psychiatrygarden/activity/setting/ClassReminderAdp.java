package com.psychiatrygarden.activity.setting;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.bean.ClassReminderBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ClassReminderAdp extends BaseQuickAdapter<ClassReminderBean.ClassReminderListBean, BaseViewHolder> {
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void setBtnClickAction(int pos, ClassReminderBean.ClassReminderListBean item);
    }

    public ClassReminderAdp() {
        super(R.layout.item_class_reminder_notice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, ClassReminderBean.ClassReminderListBean classReminderListBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setBtnClickAction(baseViewHolder.getLayoutPosition(), classReminderListBean);
        }
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(final BaseViewHolder holder, final ClassReminderBean.ClassReminderListBean item) {
        holder.setText(R.id.tv_time, item.getTime_text());
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_content, item.getContent());
        TextView textView = (TextView) holder.getView(R.id.tv_to_details);
        if (item.getType().equals("1") || item.getType().equals("3")) {
            textView.setBackgroundResource(R.drawable.shape_empty_stroke_red);
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                textView.setTextColor(Color.parseColor("#B2575C"));
            } else {
                textView.setTextColor(Color.parseColor("#DD594A"));
            }
            textView.setText("查看");
        } else {
            textView.setBackgroundResource(R.drawable.shape_full_red);
            textView.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
            textView.setText("去听课");
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13885c.lambda$convert$0(holder, item, view);
            }
        });
    }
}
