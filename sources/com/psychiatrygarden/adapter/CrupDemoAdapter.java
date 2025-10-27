package com.psychiatrygarden.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.psychiatrygarden.bean.CropCupBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CrupDemoAdapter extends BaseExpandableListAdapter {
    private Context context;
    private CropCupBean mCropList;

    public class childViewHoder {
        CircleImageView mCircleImageView;
        TextView username;

        private childViewHoder() {
        }
    }

    public class groupsViewHoder {
        TextView countnum;
        TextView groupname;

        private groupsViewHoder() {
        }
    }

    public CrupDemoAdapter(Context context, CropCupBean mCropList) {
        this.mCropList = mCropList;
        this.context = context;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.mCropList.getData().get(groupPosition).getUser().get(childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childViewHoder childviewhoder;
        if (convertView == null) {
            childviewhoder = new childViewHoder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_cropcup, (ViewGroup) null);
            childviewhoder.username = (TextView) convertView.findViewById(R.id.username);
            childviewhoder.mCircleImageView = (CircleImageView) convertView.findViewById(R.id.iv_cup_photo);
            convertView.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
        }
        childviewhoder.username.setText(this.mCropList.getData().get(groupPosition).getUser().get(childPosition).getNickname());
        GlideUtils.loadImage(childviewhoder.mCircleImageView.getContext(), this.mCropList.getData().get(groupPosition).getUser().get(childPosition).getAvatar(), childviewhoder.mCircleImageView);
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.mCropList.getData().get(groupPosition).getUser().size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.mCropList.getData().get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.mCropList.getData().size();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View viewInflate;
        groupsViewHoder groupsviewhoder;
        if (convertView == null) {
            groupsviewhoder = new groupsViewHoder();
            viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_crup_child, (ViewGroup) null);
            groupsviewhoder.groupname = (TextView) viewInflate.findViewById(R.id.groupname);
            groupsviewhoder.countnum = (TextView) viewInflate.findViewById(R.id.countnum);
            viewInflate.setTag(groupsviewhoder);
        } else {
            viewInflate = convertView;
            groupsviewhoder = (groupsViewHoder) convertView.getTag();
        }
        groupsviewhoder.groupname.setSelected(isExpanded);
        String str = this.mCropList.getData().get(groupPosition).getUser().size() + "";
        groupsviewhoder.groupname.setText(this.mCropList.getData().get(groupPosition).getGroup_name());
        groupsviewhoder.countnum.setText("（" + str + "）");
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
