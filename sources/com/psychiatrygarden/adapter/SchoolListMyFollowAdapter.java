package com.psychiatrygarden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.chooseSchool.SchoolDetailsAct;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class SchoolListMyFollowAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<FollowSchoolBean.DataBean> mList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView myFollowIv;
        LinearLayout myFollowLl;
        TextView myFollowTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.myFollowTv = (TextView) itemView.findViewById(R.id.my_follow_tv);
            this.myFollowIv = (CircleImageView) itemView.findViewById(R.id.my_follow_iv);
            this.myFollowLl = (LinearLayout) itemView.findViewById(R.id.my_follow_ll);
        }
    }

    public SchoolListMyFollowAdapter(Context context, List<FollowSchoolBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(FollowSchoolBean.DataBean dataBean, View view) {
        SchoolDetailsAct.newIntent(this.mContext, dataBean.getSchool_id());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<FollowSchoolBean.DataBean> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FollowSchoolBean.DataBean dataBean = this.mList.get(position);
        holder.myFollowTv.setText(dataBean.getTitle());
        GlideUtils.loadImage(this.mContext, dataBean.getCover(), holder.myFollowIv, R.mipmap.ic_order_default, R.mipmap.ic_order_default);
        holder.myFollowLl.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.pe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14890c.lambda$onBindViewHolder$0(dataBean, view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_schoollist_myfollow, parent, false));
    }
}
