package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.bean.oneTitleDb;
import com.psychiatrygarden.bean.twoTitleDb;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectFWNExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private boolean isExport;
    private List<oneTitleDb> list;
    private List<groupsViewHoder> parentItems;
    private String type;

    public class childViewHoder {
        private ImageView iv_select;
        private TextView tv_Name;
        private TextView tv_Num;

        public childViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.mycollect_child_tv_Name);
            this.tv_Num = (TextView) view.findViewById(R.id.mycollect_child_tv_num);
            this.iv_select = (ImageView) view.findViewById(R.id.iv_select);
        }
    }

    public class groupsViewHoder {
        private ImageView iv_select;
        private TextView tv_Name;
        private TextView tv_Num;
        private TextView tv_groups_bei_share;

        public groupsViewHoder(View view) {
            this.tv_Name = (TextView) view.findViewById(R.id.mycollect_groups_tv_name);
            this.tv_groups_bei_share = (TextView) view.findViewById(R.id.tv_groups_bei_share);
            this.tv_Num = (TextView) view.findViewById(R.id.mycollect_groups_tv_num);
            this.iv_select = (ImageView) view.findViewById(R.id.iv_select);
        }
    }

    public SubjectFWNExpandableListAdapter(Context context, List<oneTitleDb> list, String type) {
        this.parentItems = new ArrayList();
        this.isExport = false;
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int groupPosition, int childPosition) {
        return this.list.get(groupPosition).getChapters().get(childPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) throws Resources.NotFoundException {
        childViewHoder childviewhoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_mycollect_child, (ViewGroup) null);
            childviewhoder = new childViewHoder(convertView);
            convertView.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
        }
        new twoTitleDb();
        twoTitleDb twotitledb = this.list.get(groupPosition).getChapters().get(childPosition);
        if (this.isExport) {
            childviewhoder.iv_select.setVisibility(0);
            childviewhoder.iv_select.setSelected(twotitledb.isIs_choice());
        } else {
            childviewhoder.iv_select.setVisibility(8);
        }
        childviewhoder.tv_Name.setText(twotitledb.getCate_name());
        childviewhoder.tv_Num.setText(twotitledb.getTotal() + "");
        if (this.type.equals("question_video")) {
            Drawable drawable = this.context.getResources().getDrawable(R.drawable.zhibo_red);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            childviewhoder.tv_Name.setCompoundDrawablePadding(10);
            childviewhoder.tv_Name.setCompoundDrawables(drawable, null, null, null);
        }
        return convertView;
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int groupPosition) {
        return this.list.get(groupPosition).getChapters().size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int groupPosition) {
        return this.list.get(groupPosition);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.list.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) throws Resources.NotFoundException {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_mycollect_groups, (ViewGroup) null);
        groupsViewHoder groupsviewhoder = new groupsViewHoder(viewInflate);
        viewInflate.setTag(groupsviewhoder);
        oneTitleDb onetitledb = this.list.get(groupPosition);
        if (this.isExport) {
            groupsviewhoder.iv_select.setVisibility(0);
            groupsviewhoder.iv_select.setSelected(onetitledb.isIs_choice());
            groupsviewhoder.iv_select.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.SubjectFWNExpandableListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    ((oneTitleDb) SubjectFWNExpandableListAdapter.this.list.get(groupPosition)).setIs_choice(!((oneTitleDb) SubjectFWNExpandableListAdapter.this.list.get(groupPosition)).isIs_choice());
                    for (int i2 = 0; i2 < ((oneTitleDb) SubjectFWNExpandableListAdapter.this.list.get(groupPosition)).getChapters().size(); i2++) {
                        ((oneTitleDb) SubjectFWNExpandableListAdapter.this.list.get(groupPosition)).getChapters().get(i2).setIs_choice(((oneTitleDb) SubjectFWNExpandableListAdapter.this.list.get(groupPosition)).isIs_choice());
                    }
                    SubjectFWNExpandableListAdapter.this.notifyDataSetChanged();
                    EventBus.getDefault().post("ExportQuestionGroup");
                }
            });
        } else {
            groupsviewhoder.iv_select.setVisibility(8);
        }
        groupsviewhoder.tv_Name.setText(onetitledb.getCate_name());
        if (isExpanded) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                Drawable drawable = this.context.getResources().getDrawable(R.drawable.icon_indicator_top);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                groupsviewhoder.tv_Num.setCompoundDrawables(null, null, drawable, null);
            } else {
                Drawable drawable2 = this.context.getResources().getDrawable(R.drawable.icon_indicator_top_night);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                groupsviewhoder.tv_Num.setCompoundDrawables(null, null, drawable2, null);
            }
        } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
            Drawable drawable3 = this.context.getResources().getDrawable(R.drawable.icon_indicator_bottom);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            groupsviewhoder.tv_Num.setCompoundDrawables(null, null, drawable3, null);
        } else {
            Drawable drawable4 = this.context.getResources().getDrawable(R.drawable.icon_indicator_bottom_night);
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            groupsviewhoder.tv_Num.setCompoundDrawables(null, null, drawable4, null);
        }
        if (this.type.equals("error")) {
            groupsviewhoder.tv_Num.setText("错" + onetitledb.getTotal() + "题");
        } else if (this.type.equals("collect")) {
            groupsviewhoder.tv_Num.setText("收藏" + onetitledb.getTotal() + "题");
        } else if (this.type.equals("note")) {
            groupsviewhoder.tv_Num.setText(onetitledb.getTotal() + "条笔记");
        } else if (this.type.equals("question_video")) {
            groupsviewhoder.tv_Num.setText(onetitledb.getTotal() + "");
            Drawable drawable5 = this.context.getResources().getDrawable(R.drawable.zhibo_gray);
            drawable5.setBounds(0, 0, drawable5.getMinimumWidth(), drawable5.getMinimumHeight());
            groupsviewhoder.tv_Name.setCompoundDrawablePadding(10);
            groupsviewhoder.tv_Name.setCompoundDrawables(drawable5, null, null, null);
        } else if (this.type.equals("kuangbei")) {
            groupsviewhoder.tv_Num.setText(onetitledb.getTotal() + "条");
            try {
                if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.context, "").equals(CommonParameter.Xueshuo)) {
                    groupsviewhoder.tv_groups_bei_share.setVisibility(8);
                    groupsviewhoder.tv_Num.setVisibility(0);
                } else {
                    groupsviewhoder.tv_groups_bei_share.setVisibility(8);
                    groupsviewhoder.tv_Num.setVisibility(0);
                    String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.SHARE_UNLOCK_CHECK_POINTS_NUM, this.context, "");
                    try {
                        if (!strConfig.equals("")) {
                            JSONArray jSONArrayOptJSONArray = new JSONObject(strConfig).optJSONArray("pm");
                            for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                                if (onetitledb.getCate_p_id().equals(jSONObjectOptJSONObject.optString("obj_id")) && jSONObjectOptJSONObject.optInt("share_count") < 1) {
                                    groupsviewhoder.tv_groups_bei_share.setVisibility(0);
                                    groupsviewhoder.tv_Num.setVisibility(8);
                                }
                            }
                        }
                    } catch (Exception unused) {
                        groupsviewhoder.tv_groups_bei_share.setVisibility(8);
                        groupsviewhoder.tv_Num.setVisibility(0);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.parentItems.add(groupsviewhoder);
        return viewInflate;
    }

    public List<oneTitleDb> getListData() {
        if (this.list == null) {
            this.list = new ArrayList();
        }
        return this.list;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setListData(List<oneTitleDb> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public SubjectFWNExpandableListAdapter(Context context, List<oneTitleDb> list, String type, boolean isExport) {
        this.parentItems = new ArrayList();
        this.list = list;
        this.context = context;
        this.type = type;
        this.isExport = isExport;
        getListData();
    }
}
