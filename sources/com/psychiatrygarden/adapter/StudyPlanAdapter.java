package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.knowledge.DailyTasksActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.StudyPlanListBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ReadMoreIcon;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class StudyPlanAdapter extends RecyclerView.Adapter<ViewHolder> {
    private OnExpandClickListener expandClickListener;
    private Context mContext;
    private setOnReceiveLisenter onReceiveLisenter;
    private List<StudyPlanListBean.DataBean.SystemPlanBean> studyPlans;

    public interface OnExpandClickListener {
        void onExpandClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llGoPractice;
        LinearLayout llVipLock;
        ProgressBar progressBar;
        RelativeLayout rlProgressContainer;
        ReadMoreIcon tvDescriptionExpand;
        TextView tvGoPractice;
        TextView tvPlanName;
        TextView tvProgressPercent;
        TextView tvReceive;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvPlanName = (TextView) itemView.findViewById(R.id.tv_plan_name);
            this.tvGoPractice = (TextView) itemView.findViewById(R.id.tv_go_practice);
            this.tvReceive = (TextView) itemView.findViewById(R.id.tv_receive);
            this.rlProgressContainer = (RelativeLayout) itemView.findViewById(R.id.rl_progress_container);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            this.tvProgressPercent = (TextView) itemView.findViewById(R.id.tv_progress_percent);
            this.tvDescriptionExpand = (ReadMoreIcon) itemView.findViewById(R.id.tv_description_expand);
            this.llGoPractice = (LinearLayout) itemView.findViewById(R.id.ll_go_practice);
            this.llVipLock = (LinearLayout) itemView.findViewById(R.id.ll_vip_lock);
        }
    }

    public static abstract class setOnReceiveLisenter {
        public abstract void setItemClickAction(String plan_id);
    }

    public StudyPlanAdapter(Context context, List<StudyPlanListBean.DataBean.SystemPlanBean> studyPlans, OnExpandClickListener listener) {
        this.studyPlans = studyPlans;
        this.expandClickListener = listener;
        this.mContext = context;
    }

    private List<Integer> getKeywordIndex(String content, String searchContent) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile(Pattern.quote(searchContent)).matcher(content);
        while (matcher.find()) {
            arrayList.add(Integer.valueOf(matcher.start()));
        }
        return arrayList;
    }

    private SpannableStringBuilder getSpannableString(SpannableStringBuilder stringBuilder, String content, String keyword) throws Resources.NotFoundException {
        if (stringBuilder == null) {
            stringBuilder = new SpannableStringBuilder(content);
        }
        if (!TextUtils.isEmpty(keyword) && !TextUtils.isEmpty(content)) {
            List<Integer> keywordIndex = getKeywordIndex(content, keyword);
            for (int i2 = 0; i2 < keywordIndex.size(); i2++) {
                int color = this.mContext.getResources().getColor(R.color.main_theme_color_night);
                int color2 = this.mContext.getResources().getColor(R.color.main_theme_color);
                if (SkinManager.getCurrentSkinType(this.mContext) != 1) {
                    color = color2;
                }
                stringBuilder.setSpan(new ForegroundColorSpan(color), keywordIndex.get(i2).intValue(), keywordIndex.get(i2).intValue() + keyword.length(), 17);
            }
        }
        return stringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(View view) {
        this.mContext.startActivity(new Intent(this.mContext, (Class<?>) MemberCenterActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBindViewHolder$1(ViewHolder viewHolder, StudyPlanListBean.DataBean.SystemPlanBean systemPlanBean) {
        int width = viewHolder.progressBar.getWidth();
        int width2 = viewHolder.tvProgressPercent.getWidth();
        int iMin = Math.min(Math.max(0, ((int) (width * (Integer.parseInt(systemPlanBean.getCompletion()) / 100.0f))) - (width2 / 2)), width - width2);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.tvProgressPercent.getLayoutParams();
        layoutParams.leftMargin = iMin;
        layoutParams.removeRule(9);
        viewHolder.tvProgressPercent.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onBindViewHolder$2(View view) {
        Log.e("wwwwwwwwww", "去做题");
        view.getContext().startActivity(new Intent(view.getContext(), (Class<?>) DailyTasksActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(StudyPlanListBean.DataBean.SystemPlanBean systemPlanBean, View view) {
        setOnReceiveLisenter setonreceivelisenter = this.onReceiveLisenter;
        if (setonreceivelisenter != null) {
            setonreceivelisenter.setItemClickAction(systemPlanBean.getId());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.studyPlans.size();
    }

    public void setListData(List<StudyPlanListBean.DataBean.SystemPlanBean> studyPlans) {
        this.studyPlans = studyPlans;
    }

    public void setOnItemClickLisenter(setOnReceiveLisenter lisenter) {
        this.onReceiveLisenter = lisenter;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) throws Resources.NotFoundException {
        final StudyPlanListBean.DataBean.SystemPlanBean systemPlanBean = this.studyPlans.get(position);
        holder.tvPlanName.setText(getSpannableString(CommonUtil.setSpanImg(this.mContext, R.drawable.study_plan_title_icon, 1.3f, 0, 1, 33, "& " + systemPlanBean.getName()), systemPlanBean.getName(), ""));
        if (systemPlanBean.getDescription().isEmpty()) {
            holder.tvDescriptionExpand.setVisibility(8);
        } else {
            holder.tvDescriptionExpand.setVisibility(0);
            holder.tvDescriptionExpand.restoreState(systemPlanBean.isExpanded());
            holder.tvDescriptionExpand.setOriginalText(systemPlanBean.getDescription());
            holder.tvDescriptionExpand.setText(systemPlanBean.getDescription());
            holder.tvDescriptionExpand.setContentUpdateListener(new ReadMoreIcon.OnStateChangeListener() { // from class: com.psychiatrygarden.adapter.StudyPlanAdapter.1
                @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
                public void onChange(boolean isExpand) {
                    holder.tvDescriptionExpand.setText(systemPlanBean.getDescription());
                    holder.tvDescriptionExpand.restoreState(true);
                    systemPlanBean.setExpanded(true);
                }

                @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
                public void updateContent(CharSequence expandText, CharSequence collapseText) {
                }

                @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
                public void updateLineCount(int count) {
                }

                @Override // com.psychiatrygarden.widget.ReadMoreIcon.OnStateChangeListener
                public void updateLineIndex(int index) {
                }
            });
        }
        StudyPlanListBean.DataBean.SystemPlanBean.ItemType item_type = systemPlanBean.getItem_type();
        StudyPlanListBean.DataBean.SystemPlanBean.ItemType itemType = StudyPlanListBean.DataBean.SystemPlanBean.ItemType.WITH_PROGRESS;
        if (item_type == itemType) {
            holder.llGoPractice.setVisibility(0);
            holder.tvReceive.setVisibility(8);
            holder.llVipLock.setVisibility(8);
        } else {
            holder.llGoPractice.setVisibility(8);
            if (UserConfig.getInstance().getUser().getIs_vip().equals("0")) {
                holder.tvReceive.setVisibility(8);
                holder.llVipLock.setVisibility(0);
            } else {
                holder.tvReceive.setVisibility(0);
                holder.llVipLock.setVisibility(8);
            }
        }
        holder.llVipLock.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.ue
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15084c.lambda$onBindViewHolder$0(view);
            }
        });
        if (systemPlanBean.getItem_type() == itemType) {
            holder.rlProgressContainer.setVisibility(0);
            holder.progressBar.setProgress(Integer.parseInt(systemPlanBean.getCompletion()));
            holder.tvProgressPercent.setText(systemPlanBean.getCompletion() + "%");
            holder.progressBar.post(new Runnable() { // from class: com.psychiatrygarden.adapter.ve
                @Override // java.lang.Runnable
                public final void run() {
                    StudyPlanAdapter.lambda$onBindViewHolder$1(holder, systemPlanBean);
                }
            });
        } else {
            holder.rlProgressContainer.setVisibility(8);
        }
        holder.tvGoPractice.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.we
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StudyPlanAdapter.lambda$onBindViewHolder$2(view);
            }
        });
        holder.tvReceive.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.xe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15180c.lambda$onBindViewHolder$3(systemPlanBean, view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_plan, parent, false));
    }
}
