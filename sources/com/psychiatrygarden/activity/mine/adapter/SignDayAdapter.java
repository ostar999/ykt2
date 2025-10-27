package com.psychiatrygarden.activity.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.mine.bean.SignInBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class SignDayAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<SignInBean.SignLogBean> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, SignInBean.SignLogBean item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStatus;
        LinearLayout signItemLl;
        TextView tvDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            this.ivStatus = (ImageView) itemView.findViewById(R.id.iv_status);
            this.signItemLl = (LinearLayout) itemView.findViewById(R.id.sign_item_ll);
        }
    }

    public SignDayAdapter(Context context, List<SignInBean.SignLogBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i2, SignInBean.SignLogBean signLogBean, View view) {
        OnItemClickListener onItemClickListener = this.mListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(i2, signLogBean);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<SignInBean.SignLogBean> list = this.mData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setData(List<SignInBean.SignLogBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) throws NumberFormatException {
        final SignInBean.SignLogBean signLogBean = this.mData.get(position);
        holder.tvDay.setText(signLogBean.getDay());
        int i2 = Integer.parseInt(signLogBean.getSign_type());
        String have_continuous_reward = signLogBean.getHave_continuous_reward();
        String have_qr_code = signLogBean.getHave_qr_code();
        if (i2 == 10) {
            holder.signItemLl.setBackgroundResource(R.drawable.ic_sign_done);
            holder.ivStatus.setImageResource(R.drawable.sign_icon_10);
            holder.tvDay.setTextColor(this.mContext.getColor(R.color.first_txt_color));
            if (have_continuous_reward.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_box);
            }
            if (have_qr_code.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_book);
            }
        } else if (i2 == 11) {
            holder.signItemLl.setBackgroundResource(R.drawable.ic_sign_done);
            holder.ivStatus.setImageResource(R.drawable.sign_icon_11);
            holder.tvDay.setTextColor(this.mContext.getColor(R.color.first_txt_color));
        } else if (i2 == 20) {
            holder.signItemLl.setBackgroundResource(R.drawable.bg_sign_btn);
            holder.ivStatus.setImageResource(R.drawable.sign_icon_20);
            holder.tvDay.setTextColor(this.mContext.getColor(R.color.white));
            if (have_continuous_reward.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_box);
            }
            if (have_qr_code.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_book);
            }
        } else if (i2 == 21 || i2 == 23) {
            holder.signItemLl.setBackgroundResource(R.drawable.bg_sign_disabled);
            holder.tvDay.setTextColor(this.mContext.getColor(R.color.third_txt_color));
            if (have_continuous_reward.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_box);
            } else {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_21);
            }
            if (have_qr_code.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_book);
            }
        } else if (i2 == 22) {
            holder.signItemLl.setBackgroundResource(R.drawable.bg_sign_disabled);
            holder.tvDay.setTextColor(this.mContext.getColor(R.color.third_txt_color));
            if (have_continuous_reward.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_11_gray);
            } else {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_22);
            }
            if (have_qr_code.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.sign_icon_full);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.adapter.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12772c.lambda$onBindViewHolder$0(position, signLogBean, view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_sign_day, parent, false));
    }
}
