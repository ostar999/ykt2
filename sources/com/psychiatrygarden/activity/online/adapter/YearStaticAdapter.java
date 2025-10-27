package com.psychiatrygarden.activity.online.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.activity.online.bean.YearStatisticsBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class YearStaticAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<YearStatisticsBean.DataDTO.TreeDTO> data;
    private double defenlvall;
    private final List<groupsViewHoder> parentItems = new ArrayList();

    public static class childViewHoder {
        private TextView fenshu;
        private TextView tv_Name;

        public childViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.tv_relive_child_tv_Name);
            this.fenshu = (TextView) view.findViewById(R.id.fenshu);
        }
    }

    public static class groupsViewHoder {
        private ImageView course_img;
        private TextView mycollect_groups_tv_num;
        private TextView tv_Name;

        public groupsViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.mycollect_groups_tv_name);
            this.course_img = (ImageView) view.findViewById(R.id.course_img);
            this.mycollect_groups_tv_num = (TextView) view.findViewById(R.id.mycollect_groups_tv_num);
        }
    }

    public YearStaticAdapter(Context context, List<YearStatisticsBean.DataDTO.TreeDTO> data, double defenlvall) {
        this.data = new ArrayList();
        this.data = data;
        this.context = context;
        this.defenlvall = defenlvall;
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childViewHoder childviewhoder;
        View view;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        if (convertView == null) {
            View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_datichild, (ViewGroup) null);
            childViewHoder childviewhoder2 = new childViewHoder(viewInflate);
            viewInflate.setTag(childviewhoder2);
            view = viewInflate;
            childviewhoder = childviewhoder2;
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
            view = convertView;
        }
        try {
            YearStatisticsBean.DataDTO.TreeDTO.ChildrenDTO childrenDTO = this.data.get(groupPosition).getChildren().get(childPosition);
            childviewhoder.tv_Name.setText(childrenDTO.getTitle());
            double d3 = Double.parseDouble(childrenDTO.getFull_mark()) > 0.0d ? 100.0d * (Double.parseDouble(childrenDTO.getScore()) / Double.parseDouble(childrenDTO.getFull_mark())) : 0.0d;
            String str = replacevalue(new DecimalFormat("#0.0").format(d3)) + "%";
            String str2 = "得分" + childrenDTO.getScore() + "，满分" + childrenDTO.getFull_mark() + "，得分率" + str;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                colorStateList = ContextCompat.getColorStateList(this.context, R.color.app_theme_red);
                colorStateList2 = ContextCompat.getColorStateList(this.context, R.color.green);
            } else {
                colorStateList = ContextCompat.getColorStateList(this.context, R.color.red_theme_night);
                colorStateList2 = ContextCompat.getColorStateList(this.context, R.color.green_theme_night);
            }
            ColorStateList colorStateList3 = colorStateList;
            ColorStateList colorStateList4 = colorStateList2;
            if (d3 > this.defenlvall) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList4, null), str2.length() - str.length(), str2.length(), 34);
            } else {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), str2.length() - str.length(), str2.length(), 34);
            }
            childviewhoder.fenshu.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return view;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.itemdatigroup, (ViewGroup) null);
        groupsViewHoder groupsviewhoder = new groupsViewHoder(viewInflate);
        viewInflate.setTag(groupsviewhoder);
        try {
            YearStatisticsBean.DataDTO.TreeDTO treeDTO = this.data.get(groupPosition);
            groupsviewhoder.tv_Name.setText(treeDTO.getTitle());
            double d3 = Double.parseDouble(treeDTO.getFull_mark()) > 0.0d ? 100.0d * (Double.parseDouble(treeDTO.getScore()) / Double.parseDouble(treeDTO.getFull_mark())) : 0.0d;
            String str = replacevalue(new DecimalFormat("#0.0").format(d3)) + "%";
            String str2 = "得分" + treeDTO.getScore() + "，满分" + treeDTO.getFull_mark() + "，得分率" + str;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                colorStateList = ContextCompat.getColorStateList(this.context, R.color.app_theme_red);
                colorStateList2 = ContextCompat.getColorStateList(this.context, R.color.green);
            } else {
                colorStateList = ContextCompat.getColorStateList(this.context, R.color.red_theme_night);
                colorStateList2 = ContextCompat.getColorStateList(this.context, R.color.green_theme_night);
            }
            ColorStateList colorStateList3 = colorStateList;
            ColorStateList colorStateList4 = colorStateList2;
            if (d3 >= this.defenlvall) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList4, null), str2.length() - str.length(), str2.length(), 34);
            } else {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), str2.length() - str.length(), str2.length(), 34);
            }
            groupsviewhoder.mycollect_groups_tv_num.setText(spannableStringBuilder);
            this.parentItems.add(groupsviewhoder);
            if (isExpanded) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_top);
                } else {
                    groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_top_night);
                }
            } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
                groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_bottom);
            } else {
                groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_bottom_night);
            }
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
