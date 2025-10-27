package com.psychiatrygarden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowMajorsBean;
import com.psychiatrygarden.utils.EventBusConstant;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class MajorsListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<FollowMajorsBean.DataBean> mList;
    private String mListType;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkboxSelect;
        TextView followCountTv;
        LinearLayout itemMajorsLl;
        RelativeLayout itemMajorsRl;
        TextView mTvType;
        LinearLayout majorChangeLl;
        TextView majorChangeTv;
        ImageView majorRankIv;
        RelativeLayout majorRankRl;
        TextView majorRankTv;
        TextView majorTitleCodeTv;
        ImageView rankChangeIv;
        TextView recentDaysFollow;
        TextView studentCountTv;
        TextView viewCountTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkboxSelect = (CheckBox) itemView.findViewById(R.id.checkbox_select);
            this.majorTitleCodeTv = (TextView) itemView.findViewById(R.id.major_title_code_tv);
            this.followCountTv = (TextView) itemView.findViewById(R.id.follow_count_tv);
            this.recentDaysFollow = (TextView) itemView.findViewById(R.id.recent_7days_follow);
            this.viewCountTv = (TextView) itemView.findViewById(R.id.view_count_tv);
            this.studentCountTv = (TextView) itemView.findViewById(R.id.student_count_tv);
            this.majorChangeLl = (LinearLayout) itemView.findViewById(R.id.major_change_ll);
            this.majorChangeTv = (TextView) itemView.findViewById(R.id.major_rank_change_tv);
            this.majorRankRl = (RelativeLayout) itemView.findViewById(R.id.major_rank_rl);
            this.majorRankIv = (ImageView) itemView.findViewById(R.id.major_rank_iv);
            this.majorRankTv = (TextView) itemView.findViewById(R.id.major_rank_tv);
            this.itemMajorsRl = (RelativeLayout) itemView.findViewById(R.id.item_majors_rl);
            this.itemMajorsLl = (LinearLayout) itemView.findViewById(R.id.item_majors_ll);
            this.rankChangeIv = (ImageView) itemView.findViewById(R.id.rankChangeIv);
            this.mTvType = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }

    public MajorsListAdapter(Context context, List<FollowMajorsBean.DataBean> list) {
        this.mListType = "";
        this.mContext = context;
        this.mList = list;
    }

    private void checkAllList() {
        boolean z2;
        Iterator<FollowMajorsBean.DataBean> it = this.mList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = false;
                break;
            } else if (it.next().isItem_select()) {
                EventBus.getDefault().post(EventBusConstant.EVENT_TARGET_SCHOOL_HAS_SELECT);
                z2 = true;
                break;
            }
        }
        if (z2) {
            return;
        }
        EventBus.getDefault().post(EventBusConstant.EVENT_TARGET_SCHOOL_NO_SELECT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i2, CompoundButton compoundButton, boolean z2) {
        this.mList.get(i2).setItem_select(z2);
        checkAllList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i2, View view) {
        this.mListener.onItemClick(i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<FollowMajorsBean.DataBean> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01cd  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull com.psychiatrygarden.adapter.MajorsListAdapter.ViewHolder r14, final int r15) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.adapter.MajorsListAdapter.onBindViewHolder(com.psychiatrygarden.adapter.MajorsListAdapter$ViewHolder, int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_majors, parent, false));
    }

    public MajorsListAdapter(Context context, List<FollowMajorsBean.DataBean> list, String listType) {
        this.mContext = context;
        this.mList = list;
        this.mListType = listType;
    }
}
