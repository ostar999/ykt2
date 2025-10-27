package com.psychiatrygarden.activity.courselist.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.beizi.fusion.widget.ScrollClickView;
import com.psychiatrygarden.activity.courselist.bean.CourseListChapterBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class CourseListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CourseListChapterBean.DataBean> data;
    private String type;

    public class childViewHoder {
        TextView tv_relive_child_tv_Name;
        TextView tv_relive_down_state;

        public childViewHoder(View view) {
            this.tv_relive_child_tv_Name = (TextView) view.findViewById(R.id.tv_relive_child_tv_Name);
            this.tv_relive_down_state = (TextView) view.findViewById(R.id.tv_relive_down_state);
        }
    }

    public class groupsViewHoder {
        ImageView course_img;
        TextView mycollect_groups_tv_name;
        TextView mycollect_groups_tv_num;

        public groupsViewHoder(View convertView) {
            this.mycollect_groups_tv_name = (TextView) convertView.findViewById(R.id.mycollect_groups_tv_name);
            this.mycollect_groups_tv_num = (TextView) convertView.findViewById(R.id.mycollect_groups_tv_num);
            this.course_img = (ImageView) convertView.findViewById(R.id.course_img);
        }
    }

    public CourseListAdapter(Context context, List<CourseListChapterBean.DataBean> data, String type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.data.get(groupPosition).getChildren().get(childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childViewHoder childviewhoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_relive_child, (ViewGroup) null);
            childviewhoder = new childViewHoder(convertView);
            convertView.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
        }
        CourseListChapterBean.DataBean.ChildrenBean childrenBean = this.data.get(groupPosition).getChildren().get(childPosition);
        childviewhoder.tv_relive_child_tv_Name.setText(childrenBean.getTitle());
        if (this.type.equals("all")) {
            childviewhoder.tv_relive_down_state.setText(String.format(Locale.CHINA, "%d/%d", Integer.valueOf(childrenBean.getWatched()), Integer.valueOf(childrenBean.getTotal())));
        } else if (this.type.equals(ScrollClickView.DIR_DOWN)) {
            childviewhoder.tv_relive_down_state.setText("");
        } else {
            childviewhoder.tv_relive_down_state.setText(String.valueOf(childrenBean.getTotal()));
        }
        if (childrenBean.getTotal() == 0) {
            childviewhoder.tv_relive_down_state.setVisibility(8);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                childviewhoder.tv_relive_child_tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.gray_light));
            } else {
                childviewhoder.tv_relive_child_tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.font_com_night));
            }
        } else {
            childviewhoder.tv_relive_down_state.setVisibility(0);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                childviewhoder.tv_relive_child_tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
            } else {
                childviewhoder.tv_relive_child_tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
            }
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
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
        return groupPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        groupsViewHoder groupsviewhoder;
        Drawable drawable;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_relive_groups, (ViewGroup) null);
            groupsviewhoder = new groupsViewHoder(convertView);
            convertView.setTag(groupsviewhoder);
        } else {
            groupsviewhoder = (groupsViewHoder) convertView.getTag();
        }
        CourseListChapterBean.DataBean dataBean = this.data.get(groupPosition);
        groupsviewhoder.mycollect_groups_tv_name.setText(dataBean.getTitle());
        if (this.type.equals("all")) {
            groupsviewhoder.mycollect_groups_tv_num.setText(dataBean.getWatched() + "/" + dataBean.getTotal());
        } else if (this.type.equals(ScrollClickView.DIR_DOWN)) {
            groupsviewhoder.mycollect_groups_tv_num.setText("");
        } else {
            groupsviewhoder.mycollect_groups_tv_num.setText(dataBean.getTotal() + "");
        }
        if (dataBean.getChildren() == null || dataBean.getChildren().size() <= 0) {
            groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_right2);
            if (dataBean.getTotal() == 0) {
                groupsviewhoder.course_img.setVisibility(8);
                groupsviewhoder.mycollect_groups_tv_num.setVisibility(8);
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    groupsviewhoder.mycollect_groups_tv_name.setTextColor(ContextCompat.getColor(this.context, R.color.gray_light));
                } else {
                    groupsviewhoder.mycollect_groups_tv_name.setTextColor(ContextCompat.getColor(this.context, R.color.font_com_night));
                }
            } else {
                groupsviewhoder.course_img.setVisibility(0);
                if (SkinManager.getCurrentSkinType(this.context) == 1 && (drawable = ContextCompat.getDrawable(this.context, R.drawable.icon_indicator_right2)) != null) {
                    drawable.setColorFilter(ContextCompat.getColor(this.context, R.color.first_text_color_night), PorterDuff.Mode.SRC_IN);
                }
                groupsviewhoder.mycollect_groups_tv_num.setVisibility(0);
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    groupsviewhoder.mycollect_groups_tv_name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
                } else {
                    groupsviewhoder.mycollect_groups_tv_name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
                }
            }
        } else if (isExpanded) {
            if (SkinManager.getCurrentSkinType(this.context) == 1) {
                groupsviewhoder.course_img.setImageResource(R.drawable.ctopimg_night);
            } else {
                groupsviewhoder.course_img.setImageResource(R.drawable.ctopimg);
            }
        } else if (SkinManager.getCurrentSkinType(this.context) == 1) {
            groupsviewhoder.course_img.setImageResource(R.drawable.cbottomimg_night);
        } else {
            groupsviewhoder.course_img.setImageResource(R.drawable.cbottomimg);
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

    public void setNewData(List<CourseListChapterBean.DataBean> dataNew) {
        this.data.clear();
        this.data.addAll(dataNew);
        notifyDataSetChanged();
    }
}
