package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.hutool.core.text.StrPool;
import com.google.android.material.chip.ChipGroup;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class TargetSchoolListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ItemClickSelectListener itemClickSelectListener;
    private Context mContext;
    private List<FollowSchoolBean.DataBean> mList;
    private String mListType;
    private OnItemClickListener mListener;
    private String searchContent;
    private List<String> tags;

    public interface ItemClickSelectListener {
        void onItemClickSelect(int position, FollowSchoolBean.DataBean item);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, FollowSchoolBean.DataBean item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxSelect;
        ChipGroup chipGroup;
        HorizontalScrollView chipScrollView;
        LinearLayout itemSchoolLl;
        RelativeLayout itemSchoolRl;
        CircleImageView ivSchoolIcon;
        ImageView rankChangeIv;
        LinearLayout schoolChangeLl;
        TextView schoolChangeTv;
        ImageView schoolRankIv;
        RelativeLayout schoolRankRl;
        TextView schoolRankTv;
        TextView schoolTitleTv;
        TextView tvAddressCode;
        TextView tvFollowCount;
        TextView tvFollowCount7;
        TextView tvMajorCount;
        TextView tvRecommend;
        TextView tvStudentCount;
        TextView tvViewCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chipGroup = (ChipGroup) itemView.findViewById(R.id.chipGroup);
            this.chipScrollView = (HorizontalScrollView) itemView.findViewById(R.id.chipScrollView);
            this.checkBoxSelect = (CheckBox) itemView.findViewById(R.id.checkbox_select);
            this.schoolTitleTv = (TextView) itemView.findViewById(R.id.school_title_tv);
            this.tvAddressCode = (TextView) itemView.findViewById(R.id.tv_address_code);
            this.tvFollowCount = (TextView) itemView.findViewById(R.id.tv_follow_count);
            this.tvFollowCount7 = (TextView) itemView.findViewById(R.id.tv_follow_count_7);
            this.tvViewCount = (TextView) itemView.findViewById(R.id.tv_view_count);
            this.tvStudentCount = (TextView) itemView.findViewById(R.id.tv_student_count);
            this.tvMajorCount = (TextView) itemView.findViewById(R.id.tv_major_count);
            this.ivSchoolIcon = (CircleImageView) itemView.findViewById(R.id.iv_school_icon);
            this.schoolRankRl = (RelativeLayout) itemView.findViewById(R.id.school_rank_rl);
            this.schoolRankTv = (TextView) itemView.findViewById(R.id.school_rank_tv);
            this.schoolRankIv = (ImageView) itemView.findViewById(R.id.school_rank_iv);
            this.schoolChangeLl = (LinearLayout) itemView.findViewById(R.id.school_change_ll);
            this.schoolChangeTv = (TextView) itemView.findViewById(R.id.school_rank_change_tv);
            this.itemSchoolRl = (RelativeLayout) itemView.findViewById(R.id.item_school_rl);
            this.tvRecommend = (TextView) itemView.findViewById(R.id.tv_recommend);
            this.itemSchoolLl = (LinearLayout) itemView.findViewById(R.id.item_school_ll);
            this.rankChangeIv = (ImageView) itemView.findViewById(R.id.rankChangeIv);
        }
    }

    public TargetSchoolListAdapter(Context context, List<FollowSchoolBean.DataBean> list) {
        this.mListType = "";
        this.tags = new ArrayList();
        this.mContext = context;
        this.mList = list;
    }

    private void checkAllList() {
        boolean z2;
        Iterator<FollowSchoolBean.DataBean> it = this.mList.iterator();
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

    private void getImageDataDesc(String content, TextView mTextView) {
        String str = "\\s+";
        try {
            ColorStateList colorStateList = SkinManager.getCurrentSkinType(this.mContext) == 1 ? this.mContext.getResources().getColorStateList(R.color.main_theme_color_night) : this.mContext.getResources().getColorStateList(R.color.main_theme_color);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content.toString());
            String str2 = this.searchContent;
            if (str2 != null && !"".equals(str2)) {
                String[] strArrSplit = this.searchContent.split("\\s+");
                int length = strArrSplit.length;
                int i2 = 0;
                while (i2 < length) {
                    String strReplace = strArrSplit[i2].replace(str, "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            String str3 = str;
                            Matcher matcher2 = matcher;
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList, null), matcher2.start(0), matcher2.end(0), 34);
                            matcher = matcher2;
                            str = str3;
                        }
                    }
                    i2++;
                    str = str;
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i2, FollowSchoolBean.DataBean dataBean, View view) {
        this.mListener.onItemClick(i2, dataBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i2, FollowSchoolBean.DataBean dataBean, View view) {
        this.mListener.onItemClick(i2, dataBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$2(int i2, FollowSchoolBean.DataBean dataBean, View view) {
        this.mListener.onItemClick(i2, dataBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(int i2, FollowSchoolBean.DataBean dataBean, CompoundButton compoundButton, boolean z2) {
        this.mList.get(i2).setItem_select(z2);
        checkAllList();
        ItemClickSelectListener itemClickSelectListener = this.itemClickSelectListener;
        if (itemClickSelectListener != null) {
            itemClickSelectListener.onItemClickSelect(i2, dataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$4(int i2, FollowSchoolBean.DataBean dataBean, View view) {
        this.mListener.onItemClick(i2, dataBean);
    }

    public void SetTargetSchoolList(List<FollowSchoolBean.DataBean> list) {
        this.mList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<FollowSchoolBean.DataBean> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public List<FollowSchoolBean.DataBean> getList() {
        return this.mList;
    }

    public void setItemClickSelectListener(ItemClickSelectListener itemClickSelectListener) {
        this.itemClickSelectListener = itemClickSelectListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:51:0x024a  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull com.psychiatrygarden.adapter.TargetSchoolListAdapter.ViewHolder r14, final int r15) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 854
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.adapter.TargetSchoolListAdapter.onBindViewHolder(com.psychiatrygarden.adapter.TargetSchoolListAdapter$ViewHolder, int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_targetschool, parent, false));
    }

    public TargetSchoolListAdapter(Context context, List<FollowSchoolBean.DataBean> list, String listType) {
        this.mListType = "";
        this.tags = new ArrayList();
        this.mContext = context;
        this.mList = list;
        this.mListType = listType;
    }
}
