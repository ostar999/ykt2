package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.activity.online.bean.YearStatisticsBean;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class TestEntranceAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<YearStatisticsBean.DataDTO.TreeDTO> data;
    private double defenlvall;
    private List<groupsViewHoder> parentItems = new ArrayList();

    public class childViewHoder {
        private TextView fenshu;
        private RelativeLayout tanhao1;
        private TextView tv_Name;

        public childViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.tv_relive_child_tv_Name);
            this.fenshu = (TextView) view.findViewById(R.id.fenshu);
            this.tanhao1 = (RelativeLayout) view.findViewById(R.id.tanhao1);
        }
    }

    public class groupsViewHoder {
        private TextView mycollect_groups_tv_num;
        RelativeLayout tanhao;
        private TextView tv_Name;

        public groupsViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.mycollect_groups_tv_name);
            this.mycollect_groups_tv_num = (TextView) view.findViewById(R.id.mycollect_groups_tv_num);
            this.tanhao = (RelativeLayout) view.findViewById(R.id.tanhao);
        }
    }

    public TestEntranceAdapter(Context context, List<YearStatisticsBean.DataDTO.TreeDTO> data, double defenlvall) {
        this.data = data;
        this.context = context;
        this.defenlvall = defenlvall;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChildView$0(View view) {
        NewToast.showShort(this.context, "高于平均错误率，需加强！", 0).show();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.data.get(groupPosition).getChildren().get(childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) throws Resources.NotFoundException {
        childViewHoder childviewhoder;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_teststaticitem, (ViewGroup) null);
            childviewhoder = new childViewHoder(convertView);
            convertView.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
        }
        try {
            YearStatisticsBean.DataDTO.TreeDTO.ChildrenDTO childrenDTO = this.data.get(groupPosition).getChildren().get(childPosition);
            childviewhoder.tv_Name.setText(childrenDTO.getTitle());
            DecimalFormat decimalFormat = new DecimalFormat("#0.0");
            Double dValueOf = Double.valueOf(0.0d);
            if (Double.parseDouble(childrenDTO.getFull_mark()) > 0.0d) {
                dValueOf = Double.valueOf((Double.parseDouble(childrenDTO.getScore()) / Double.parseDouble(childrenDTO.getFull_mark())) * 100.0d);
            }
            String str = replacevalue(decimalFormat.format(100.0d - dValueOf.doubleValue())) + "%";
            String str2 = "共" + replacevalue(childrenDTO.getQuestion_count()) + "，错" + childrenDTO.getWrong_count() + "，错误率" + str;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                colorStateList = this.context.getResources().getColorStateList(R.color.app_theme_red);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.gray_font_new2);
            } else {
                colorStateList = this.context.getResources().getColorStateList(R.color.red_theme_night);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.jiucuo_night);
            }
            ColorStateList colorStateList3 = colorStateList;
            ColorStateList colorStateList4 = colorStateList2;
            if (1.0d - dValueOf.doubleValue() > 1.0d - this.defenlvall) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), str2.length() - str.length(), str2.length(), 34);
                childviewhoder.tanhao1.setVisibility(0);
            } else {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList4, null), str2.length() - str.length(), str2.length(), 34);
                childviewhoder.tanhao1.setVisibility(8);
            }
            childviewhoder.tanhao1.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.tf
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f15049c.lambda$getChildView$0(view);
                }
            });
            childviewhoder.fenshu.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        if (this.data.get(groupPosition).getChildren() == null) {
            return 0;
        }
        return this.data.get(groupPosition).getChildren().size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.data.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.data.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int groupPosition) {
        return getCombinedGroupId(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) throws Resources.NotFoundException {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.itemdatigroup, (ViewGroup) null);
        groupsViewHoder groupsviewhoder = new groupsViewHoder(viewInflate);
        viewInflate.setTag(groupsviewhoder);
        try {
            YearStatisticsBean.DataDTO.TreeDTO treeDTO = this.data.get(groupPosition);
            groupsviewhoder.tv_Name.setText(treeDTO.getTitle());
            DecimalFormat decimalFormat = new DecimalFormat("#0.0");
            double d3 = Double.parseDouble(treeDTO.getFull_mark()) > 0.0d ? (Double.parseDouble(treeDTO.getScore()) / Double.parseDouble(treeDTO.getFull_mark())) * 100.0d : 0.0d;
            String str = replacevalue(decimalFormat.format(100.0d - d3)) + "%";
            String str2 = "共" + replacevalue(treeDTO.getQuestion_count()) + "题，错" + replacevalue(treeDTO.getWrong_count()) + "题，错误率" + str;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                colorStateList = this.context.getResources().getColorStateList(R.color.app_theme_red);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.gray_font_new2);
            } else {
                colorStateList = this.context.getResources().getColorStateList(R.color.red_theme_night);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.jiucuo_night);
            }
            ColorStateList colorStateList3 = colorStateList;
            ColorStateList colorStateList4 = colorStateList2;
            if (1.0d - d3 > 1.0d - this.defenlvall) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), str2.length() - str.length(), str2.length(), 34);
                groupsviewhoder.tanhao.setVisibility(0);
            } else {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList4, null), str2.length() - str.length(), str2.length(), 34);
                groupsviewhoder.tanhao.setVisibility(8);
            }
            groupsviewhoder.tanhao.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.TestEntranceAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    NewToast.showShort(TestEntranceAdapter.this.context, "高于平均错误率，需加强！", 0).show();
                }
            });
            groupsviewhoder.mycollect_groups_tv_num.setText(spannableStringBuilder);
            this.parentItems.add(groupsviewhoder);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return viewInflate;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public String replacevalue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }
}
