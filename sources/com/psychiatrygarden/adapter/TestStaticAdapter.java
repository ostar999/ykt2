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
import android.widget.ImageView;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.bean.UserOwnerBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class TestStaticAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<UserOwnerBean.DataBean> data;
    private double defenlvall;
    private List<groupsViewHoder> parentItems = new ArrayList();

    public class childViewHoder {
        private TextView fenshu;
        private TextView tv_Name;

        public childViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.tv_relive_child_tv_Name);
            this.fenshu = (TextView) view.findViewById(R.id.fenshu);
        }
    }

    public class groupsViewHoder {
        private ImageView course_img;
        private TextView mycollect_groups_tv_num;
        private TextView tv_Name;

        public groupsViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.mycollect_groups_tv_name);
            this.course_img = (ImageView) view.findViewById(R.id.course_img);
            this.mycollect_groups_tv_num = (TextView) view.findViewById(R.id.mycollect_groups_tv_num);
        }
    }

    public TestStaticAdapter(Context context, List<UserOwnerBean.DataBean> data, double defenlvall) {
        this.data = data;
        this.context = context;
        this.defenlvall = defenlvall;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.data.get(groupPosition).getChild().get(childPosition);
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
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_datichild, (ViewGroup) null);
            childviewhoder = new childViewHoder(convertView);
            convertView.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
        }
        try {
            UserOwnerBean.DataBean.ChildBean childBean = this.data.get(groupPosition).getChild().get(childPosition);
            childviewhoder.tv_Name.setText(childBean.getTitle());
            double dDoubleValue = (childBean.getWrongscroe().doubleValue() / childBean.getScore().doubleValue()) * 100.0d;
            String str = replacevalue(new DecimalFormat("#0.0").format(dDoubleValue)) + "%";
            String str2 = "得分" + childBean.getWrongscroe() + "，满分" + childBean.getScore() + "，得分率" + str;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                colorStateList = this.context.getResources().getColorStateList(R.color.app_theme_red);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.green);
            } else {
                colorStateList = this.context.getResources().getColorStateList(R.color.red_theme_night);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.green_theme_night);
            }
            ColorStateList colorStateList3 = colorStateList;
            ColorStateList colorStateList4 = colorStateList2;
            if (dDoubleValue > this.defenlvall) {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList4, null), str2.length() - str.length(), str2.length(), 34);
            } else {
                spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, colorStateList3, null), str2.length() - str.length(), str2.length(), 34);
            }
            childviewhoder.fenshu.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.data.get(groupPosition).getChild().size();
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
            UserOwnerBean.DataBean dataBean = this.data.get(groupPosition);
            groupsviewhoder.tv_Name.setText(dataBean.getTitle());
            double d3 = (Double.parseDouble(dataBean.getWorngScore()) / Double.parseDouble(dataBean.getScroe())) * 100.0d;
            String str = replacevalue(new DecimalFormat("#0.0").format(d3)) + "%";
            String str2 = "得分" + dataBean.getWorngScore() + "，满分" + dataBean.getScroe() + "，得分率" + str;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str2);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                colorStateList = this.context.getResources().getColorStateList(R.color.app_theme_red);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.green);
            } else {
                colorStateList = this.context.getResources().getColorStateList(R.color.red_theme_night);
                colorStateList2 = this.context.getResources().getColorStateList(R.color.green_theme_night);
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
