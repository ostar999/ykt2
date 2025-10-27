package com.psychiatrygarden.activity.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.mine.bean.MemberDetailBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class MemberDetailAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<MemberDetailBean.RewardItem> mList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRewardContent;
        TextView tvRewardTime;
        TextView tvRewardType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvRewardType = (TextView) itemView.findViewById(R.id.tv_reward_type);
            this.tvRewardContent = (TextView) itemView.findViewById(R.id.tv_reward_content);
            this.tvRewardTime = (TextView) itemView.findViewById(R.id.tv_reward_time);
        }
    }

    public MemberDetailAdapter(Context context, List<MemberDetailBean.RewardItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<MemberDetailBean.RewardItem> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemberDetailBean.RewardItem rewardItem = this.mList.get(position);
        holder.tvRewardType.setText(rewardItem.getReward_type_str());
        holder.tvRewardContent.setText(rewardItem.getReward_str());
        holder.tvRewardTime.setText(rewardItem.getCreated_at());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_member_detail, parent, false));
    }
}
