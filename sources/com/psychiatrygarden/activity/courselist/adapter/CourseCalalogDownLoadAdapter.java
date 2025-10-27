package com.psychiatrygarden.activity.courselist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.TipPopWindow;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseCalalogDownLoadAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CourseCalalogueBean.DataNewBean.DataBean> data;
    private DeleteDataIml mDeleteDataIml;
    private int type;

    public class ChildViewHoder {
        TextView detailtx;
        TextView num;
        ImageView selectId;
        ImageView shanchuvimg;
        TextView title;
        ImageView whyimg;

        public ChildViewHoder(View view) {
            this.num = (TextView) view.findViewById(R.id.num);
            this.title = (TextView) view.findViewById(R.id.title);
            this.detailtx = (TextView) view.findViewById(R.id.detailtx);
            this.selectId = (ImageView) view.findViewById(R.id.selectId);
            this.whyimg = (ImageView) view.findViewById(R.id.whyimg);
            this.shanchuvimg = (ImageView) view.findViewById(R.id.shanchuvimg);
        }
    }

    public interface DeleteDataIml {
        void CoseAllData();

        void deleteAllData(String vid);
    }

    public class GroupViewHoder {
        ImageView course_img;
        TextView mycollect_groups_tv_name;
        ImageView uncheckvideo;

        public GroupViewHoder(View convertView) {
            this.mycollect_groups_tv_name = (TextView) convertView.findViewById(R.id.mycollect_groups_tv_name);
            this.course_img = (ImageView) convertView.findViewById(R.id.course_img);
            this.uncheckvideo = (ImageView) convertView.findViewById(R.id.uncheckvideo);
        }
    }

    public CourseCalalogDownLoadAdapter(Context context, List<CourseCalalogueBean.DataNewBean.DataBean> data) {
        this.mDeleteDataIml = null;
        this.type = 0;
        this.context = context;
        this.data = data;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.data.get(groupPosition).getCourseList().get(childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) throws NumberFormatException {
        ChildViewHoder childViewHoder;
        String str;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.layout_coursecala_child_hoder, (ViewGroup) null);
            childViewHoder = new ChildViewHoder(convertView);
            convertView.setTag(childViewHoder);
        } else {
            childViewHoder = (ChildViewHoder) convertView.getTag();
        }
        final CourseCalalogueBean.DataNewBean.DataBean.CourseListBean courseListBean = this.data.get(groupPosition).getCourseList().get(childPosition);
        int i2 = childPosition + 1;
        String str2 = "0";
        if (i2 < 10) {
            childViewHoder.num.setText("0" + i2);
        } else {
            childViewHoder.num.setText(i2 + "");
        }
        childViewHoder.detailtx.setOnClickListener(null);
        childViewHoder.whyimg.setVisibility(8);
        String size = courseListBean.getSize();
        if (size != null && !"".equals(size)) {
            str2 = size;
        }
        int i3 = (Integer.parseInt(str2) / 1024) / 1024;
        Float.parseFloat(courseListBean.getProgress() + "");
        if (courseListBean.getmStatus() == 5) {
            String str3 = i3 + "M  |  已下载";
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str3);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#5EAE06")), str3.length() - 3, str3.length(), 18);
            childViewHoder.detailtx.setText(spannableStringBuilder);
        } else if (courseListBean.getmStatus() == 3) {
            String str4 = i3 + "M  |  等待下载";
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(str4);
            spannableStringBuilder2.setSpan(new ForegroundColorSpan(Color.parseColor("#969696")), str4.length() - 4, str4.length(), 18);
            childViewHoder.detailtx.setText(spannableStringBuilder2);
        } else if (courseListBean.getmStatus() == 1) {
            String str5 = i3 + "M  |  下载中...(" + courseListBean.getProgress() + "%)";
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder(str5);
            spannableStringBuilder3.setSpan(new ForegroundColorSpan(Color.parseColor("#969696")), str5.length() - 6, str5.length(), 18);
            childViewHoder.detailtx.setText(spannableStringBuilder3);
        } else if (courseListBean.getmStatus() == 4) {
            String str6 = i3 + "M  |  暂停中";
            SpannableStringBuilder spannableStringBuilder4 = new SpannableStringBuilder(str6);
            spannableStringBuilder4.setSpan(new ForegroundColorSpan(Color.parseColor("#969696")), str6.length() - 3, str6.length(), 18);
            childViewHoder.detailtx.setText(spannableStringBuilder4);
        } else if (courseListBean.getmStatus() == 2) {
            childViewHoder.whyimg.setVisibility(0);
            if (CommonUtil.isNetworkConnected(this.context)) {
                str = i3 + "M  |  下载出错";
            } else {
                str = i3 + "M  |  网络出错";
            }
            SpannableStringBuilder spannableStringBuilder5 = new SpannableStringBuilder(str);
            spannableStringBuilder5.setSpan(new ForegroundColorSpan(Color.parseColor("#969696")), str.length() - 4, str.length(), 18);
            childViewHoder.detailtx.setText(spannableStringBuilder5);
            childViewHoder.whyimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    new XPopup.Builder(CourseCalalogDownLoadAdapter.this.context).asCustom(new TipPopWindow(CourseCalalogDownLoadAdapter.this.context)).toggle();
                }
            });
        } else {
            String str7 = i3 + "M  |  未下载";
            SpannableStringBuilder spannableStringBuilder6 = new SpannableStringBuilder(str7);
            spannableStringBuilder6.setSpan(new ForegroundColorSpan(Color.parseColor("#B2575C")), str7.length() - 3, str7.length(), 18);
            childViewHoder.detailtx.setText(spannableStringBuilder6);
        }
        if (this.type == 1) {
            childViewHoder.selectId.setVisibility(8);
        } else {
            childViewHoder.selectId.setVisibility(0);
        }
        if (1 == courseListBean.getIsSelected()) {
            childViewHoder.selectId.setImageResource(R.drawable.selectedvideo);
        } else {
            childViewHoder.selectId.setImageResource(R.drawable.uncheckvideo);
        }
        childViewHoder.title.setText(courseListBean.getTitle());
        if (this.mDeleteDataIml == null) {
            childViewHoder.shanchuvimg.setVisibility(8);
            childViewHoder.shanchuvimg.setOnClickListener(null);
        } else {
            childViewHoder.shanchuvimg.setVisibility(0);
            childViewHoder.shanchuvimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter.3
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    CourseCalalogDownLoadAdapter.this.mDeleteDataIml.deleteAllData(courseListBean.getVid());
                }
            });
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.data.get(groupPosition).getCourseList().size();
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
        return groupPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHoder groupViewHoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.layout_coursecala_group_hoder, (ViewGroup) null);
            groupViewHoder = new GroupViewHoder(convertView);
            convertView.setTag(groupViewHoder);
        } else {
            groupViewHoder = (GroupViewHoder) convertView.getTag();
        }
        groupViewHoder.mycollect_groups_tv_name.setText(this.data.get(groupPosition).getTitle());
        if (this.data.get(groupPosition).getCourseList() == null || this.data.get(groupPosition).getCourseList().size() <= 0) {
            groupViewHoder.uncheckvideo.setVisibility(4);
            groupViewHoder.mycollect_groups_tv_name.setTextColor(this.context.getResources().getColor(R.color.gray_font_new2));
            groupViewHoder.course_img.setVisibility(4);
        } else {
            groupViewHoder.uncheckvideo.setVisibility(0);
            groupViewHoder.course_img.setVisibility(0);
            groupViewHoder.mycollect_groups_tv_name.setTextColor(this.context.getResources().getColor(R.color.question_color));
            if (isExpanded) {
                groupViewHoder.course_img.setImageResource(R.drawable.course_s);
            } else {
                groupViewHoder.course_img.setImageResource(R.drawable.course_x);
            }
            if (this.type == 1) {
                groupViewHoder.uncheckvideo.setVisibility(8);
            } else {
                groupViewHoder.uncheckvideo.setVisibility(0);
            }
            if (1 == this.data.get(groupPosition).getIsSelected()) {
                groupViewHoder.uncheckvideo.setImageResource(R.drawable.selectedvideo);
            } else {
                groupViewHoder.uncheckvideo.setImageResource(R.drawable.uncheckvideo);
            }
            groupViewHoder.uncheckvideo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogDownLoadAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (1 == ((CourseCalalogueBean.DataNewBean.DataBean) CourseCalalogDownLoadAdapter.this.data.get(groupPosition)).getIsSelected()) {
                        ((CourseCalalogueBean.DataNewBean.DataBean) CourseCalalogDownLoadAdapter.this.data.get(groupPosition)).setIsSelected(0);
                        for (int i2 = 0; i2 < ((CourseCalalogueBean.DataNewBean.DataBean) CourseCalalogDownLoadAdapter.this.data.get(groupPosition)).getCourseList().size(); i2++) {
                            ((CourseCalalogueBean.DataNewBean.DataBean) CourseCalalogDownLoadAdapter.this.data.get(groupPosition)).getCourseList().get(i2).setIsSelected(0);
                        }
                    } else {
                        ((CourseCalalogueBean.DataNewBean.DataBean) CourseCalalogDownLoadAdapter.this.data.get(groupPosition)).setIsSelected(1);
                        for (int i3 = 0; i3 < ((CourseCalalogueBean.DataNewBean.DataBean) CourseCalalogDownLoadAdapter.this.data.get(groupPosition)).getCourseList().size(); i3++) {
                            ((CourseCalalogueBean.DataNewBean.DataBean) CourseCalalogDownLoadAdapter.this.data.get(groupPosition)).getCourseList().get(i3).setIsSelected(1);
                        }
                    }
                    CourseCalalogDownLoadAdapter.this.notifyDataSetChanged();
                    if (CourseCalalogDownLoadAdapter.this.mDeleteDataIml != null) {
                        CourseCalalogDownLoadAdapter.this.mDeleteDataIml.CoseAllData();
                    }
                }
            });
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public CourseCalalogDownLoadAdapter(Context context, List<CourseCalalogueBean.DataNewBean.DataBean> data, DeleteDataIml mDeleteDataIml) {
        this.type = 0;
        this.context = context;
        this.data = data;
        this.mDeleteDataIml = mDeleteDataIml;
    }

    public CourseCalalogDownLoadAdapter(Context context, List<CourseCalalogueBean.DataNewBean.DataBean> data, int type, DeleteDataIml mDeleteDataIml) {
        this.context = context;
        this.data = data;
        this.type = type;
        this.mDeleteDataIml = mDeleteDataIml;
    }
}
