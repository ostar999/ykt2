package com.psychiatrygarden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.bean.UserOwnerBean;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.cookie.ClientCookie;

/* loaded from: classes5.dex */
public class UserOwernCommAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<UserOwnerBean.DataBean> data;
    private String fucn;
    private final List<groupsViewHoder> parentItems = new ArrayList();
    private int typeValue;

    public class childViewHoder {
        private TextView tv_Name;
        private TextView tv_relive_down_state;

        public childViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.tv_relive_child_tv_Name);
            this.tv_relive_down_state = (TextView) view.findViewById(R.id.tv_relive_down_state);
        }
    }

    public static class groupsViewHoder {
        private ImageView course_img;
        private TextView share_num;
        private TextView tv_Name;
        private TextView tv_Num;

        public groupsViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.mycollect_groups_tv_name);
            this.tv_Num = (TextView) view.findViewById(R.id.mycollect_groups_tv_num);
            this.course_img = (ImageView) view.findViewById(R.id.course_img);
            this.share_num = (TextView) view.findViewById(R.id.share_num);
        }
    }

    public UserOwernCommAdapter(Context context, List<UserOwnerBean.DataBean> data, int typeValue, String fucn) {
        this.data = data;
        this.context = context;
        this.typeValue = typeValue;
        this.fucn = fucn;
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childViewHoder childviewhoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_relive_child, (ViewGroup) null);
            childviewhoder = new childViewHoder(convertView);
            convertView.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
        }
        try {
            UserOwnerBean.DataBean.ChildBean childBean = this.data.get(groupPosition).getChild().get(childPosition);
            childviewhoder.tv_Name.setText(childBean.getTitle());
            if (this.fucn.equals(ClientCookie.COMMENT_ATTR)) {
                childviewhoder.tv_relive_down_state.setText(String.format("%s条评论", childBean.getCount()));
            } else {
                childviewhoder.tv_relive_down_state.setText(String.format("%s条点赞", childBean.getCount()));
            }
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_relive_groups, (ViewGroup) null);
        groupsViewHoder groupsviewhoder = new groupsViewHoder(viewInflate);
        viewInflate.setTag(groupsviewhoder);
        try {
            UserOwnerBean.DataBean dataBean = this.data.get(groupPosition);
            groupsviewhoder.tv_Name.setText(dataBean.getTitle());
            groupsviewhoder.tv_Num.setVisibility(8);
            if (this.fucn.equals(ClientCookie.COMMENT_ATTR)) {
                groupsviewhoder.tv_Num.setText(String.format("%s条评论", dataBean.getCount()));
            } else {
                groupsviewhoder.tv_Num.setText(String.format("%s条点赞", dataBean.getCount()));
            }
            if (this.typeValue == 2) {
                try {
                    groupsviewhoder.course_img.setVisibility(0);
                    groupsviewhoder.course_img.setImageResource(SkinManager.getCurrentSkinType(this.context) == 0 ? R.drawable.icon_indicator_right : R.drawable.icon_indicator_right_night);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else if (isExpanded) {
                if (dataBean.getChild().size() > 0) {
                    try {
                        groupsviewhoder.course_img.setVisibility(0);
                        if (SkinManager.getCurrentSkinType(this.context) == 0) {
                            groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_top);
                        } else {
                            groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_top_night);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else {
                    groupsviewhoder.course_img.setVisibility(8);
                    groupsviewhoder.tv_Name.setCompoundDrawables(null, null, null, null);
                }
            } else if (dataBean.getChild().size() > 0) {
                try {
                    groupsviewhoder.course_img.setVisibility(0);
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_bottom);
                    } else {
                        groupsviewhoder.course_img.setImageResource(R.drawable.icon_indicator_bottom_night);
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } else {
                groupsviewhoder.course_img.setVisibility(8);
                groupsviewhoder.tv_Name.setCompoundDrawables(null, null, null, null);
            }
            groupsviewhoder.share_num.setVisibility(8);
            groupsviewhoder.tv_Num.setVisibility(0);
            groupsviewhoder.course_img.setVisibility(0);
            this.parentItems.add(groupsviewhoder);
        } catch (Exception e5) {
            e5.printStackTrace();
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
}
