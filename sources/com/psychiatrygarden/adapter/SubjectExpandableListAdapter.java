package com.psychiatrygarden.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.bean.RealProUnlockBean;
import com.psychiatrygarden.bean.oneTitleDb;
import com.psychiatrygarden.bean.twoTitleDb;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.SpringProgressView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int index;
    private boolean isXitong;
    private List<oneTitleDb> list;
    private List<groupsViewHoder> parentItems = new ArrayList();

    public class childViewHoder {
        private ImageView iv_right;
        private SpringProgressView spro_child;
        private TextView tv_Name;
        private TextView tv_Num;

        public childViewHoder(View view) {
            this.tv_Num = (TextView) view.findViewById(R.id.main_child_tv_num);
            this.tv_Name = (TextView) view.findViewById(R.id.main_child_tv_name);
            this.spro_child = (SpringProgressView) view.findViewById(R.id.spro_child);
            this.iv_right = (ImageView) view.findViewById(R.id.iv_right);
        }
    }

    public class groupsViewHoder {
        private LinearLayout llay_progress_view;
        private RelativeLayout main_groups_layout;
        private TextView main_groups_share;
        private SpringProgressView spring_progress_view;
        private TextView tv_Name;
        private TextView tv_Num;

        private groupsViewHoder() {
        }
    }

    public SubjectExpandableListAdapter(Context context, List<oneTitleDb> list, boolean isXitong) {
        this.list = list;
        this.context = context;
        this.isXitong = isXitong;
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childViewHoder childviewhoder;
        long longSize;
        long longSize2;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_main_child, (ViewGroup) null);
            childviewhoder = new childViewHoder(convertView);
            convertView.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
        }
        if (this.list.size() != 0 && this.list.get(groupPosition).getChapters().size() != 0) {
            twoTitleDb twotitledb = this.list.get(groupPosition).getChapters().get(childPosition);
            long count = twotitledb.getCount();
            String str = SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.context, "").equals(CommonParameter.Xueshuo) ? "Isxueshuo" : "Iszhuanshuo";
            String str2 = this.isXitong ? "Part_id" : "Chapter_id";
            if ("".equals(CommonUtil.getCStr())) {
                String str3 = "select count(*) from ANSWERED_QUESTION_BEAN where " + str2 + "=" + twotitledb.getCate_id() + " and " + str + "=1 and Is_practice=0;";
                String str4 = "select count(*) from ANSWERED_QUESTION_BEAN where " + str2 + "=" + twotitledb.getCate_id() + " and " + str + "=1 and Is_practice=0 and Is_right=0;";
                longSize = CommonUtil.getLongSize(str3);
                longSize2 = CommonUtil.getLongSize(str4);
            } else {
                String str5 = "select count(*) from ANSWERED_QUESTION_BEAN where " + CommonUtil.getCStr() + " and " + str2 + "=" + twotitledb.getCate_id() + " and " + str + "=1 and Is_practice=0;";
                String str6 = "select count(*) from ANSWERED_QUESTION_BEAN where " + CommonUtil.getCStr() + " and " + str2 + "=" + twotitledb.getCate_id() + " and " + str + "=1 and Is_practice=0 and Is_right=0;";
                longSize = CommonUtil.getLongSize(str5);
                longSize2 = CommonUtil.getLongSize(str6);
            }
            childviewhoder.spro_child.setMaxErrRightCount(Float.parseFloat(count + ""), longSize2, longSize - longSize2);
            childviewhoder.spro_child.invalidate();
            childviewhoder.tv_Name.setText(twotitledb.getCate_name());
            childviewhoder.tv_Num.setText(longSize + "/" + count);
            if (count == 0) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    childviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.gray_light));
                } else {
                    childviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.font_com_night));
                }
                childviewhoder.iv_right.setVisibility(4);
                childviewhoder.spro_child.setVisibility(8);
                childviewhoder.tv_Num.setVisibility(8);
            } else {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    childviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
                } else {
                    childviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
                }
                childviewhoder.iv_right.setVisibility(0);
                childviewhoder.spro_child.setVisibility(0);
                childviewhoder.tv_Num.setVisibility(0);
            }
            childviewhoder.spro_child.invalidate();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        long longSize;
        long longSize2;
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_main_groups, (ViewGroup) null);
        groupsViewHoder groupsviewhoder = new groupsViewHoder();
        groupsviewhoder.tv_Num = (TextView) viewInflate.findViewById(R.id.main_groups_tv_num);
        groupsviewhoder.tv_Name = (TextView) viewInflate.findViewById(R.id.main_groups_tv_name);
        groupsviewhoder.main_groups_share = (TextView) viewInflate.findViewById(R.id.main_groups_share);
        groupsviewhoder.main_groups_layout = (RelativeLayout) viewInflate.findViewById(R.id.main_groups_layout);
        groupsviewhoder.llay_progress_view = (LinearLayout) viewInflate.findViewById(R.id.llay_progress_view);
        groupsviewhoder.spring_progress_view = (SpringProgressView) viewInflate.findViewById(R.id.spring_progress_view);
        viewInflate.setTag(groupsviewhoder);
        this.parentItems.add(groupsviewhoder);
        oneTitleDb onetitledb = this.list.get(groupPosition);
        groupsviewhoder.tv_Name.setText(onetitledb.getCate_name());
        String str = SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.context, "").equals(CommonParameter.Xueshuo) ? "Isxueshuo" : "Iszhuanshuo";
        String str2 = this.isXitong ? "Part_parent_id" : "Chapter_parent_id";
        if ("".equals(CommonUtil.getCStr())) {
            String str3 = "select count(*) from ANSWERED_QUESTION_BEAN where " + str2 + "=" + onetitledb.getCate_p_id() + " and " + str + "=1 and Is_practice=0;";
            String str4 = "select count(*) from ANSWERED_QUESTION_BEAN where " + str2 + "=" + onetitledb.getCate_p_id() + " and " + str + "=1 and Is_practice=0 and Is_right=0;";
            longSize = CommonUtil.getLongSize(str3);
            longSize2 = CommonUtil.getLongSize(str4);
        } else {
            String str5 = "select count(*) from ANSWERED_QUESTION_BEAN where " + CommonUtil.getCStr() + " and " + str2 + "=" + onetitledb.getCate_p_id() + " and " + str + "=1 and Is_practice=0;";
            String str6 = "select count(*) from ANSWERED_QUESTION_BEAN where " + CommonUtil.getCStr() + " and " + str2 + "=" + onetitledb.getCate_p_id() + " and " + str + "=1 and Is_practice=0 and Is_right=0;";
            longSize = CommonUtil.getLongSize(str5);
            longSize2 = CommonUtil.getLongSize(str6);
        }
        groupsviewhoder.main_groups_share.setVisibility(8);
        groupsviewhoder.llay_progress_view.setVisibility(0);
        try {
            if (Float.parseFloat(onetitledb.getTotal()) == 0.0f) {
                groupsviewhoder.tv_Num.setVisibility(8);
                groupsviewhoder.spring_progress_view.setVisibility(8);
                groupsviewhoder.tv_Name.setCompoundDrawables(null, null, null, null);
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    groupsviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.gray_font_new2));
                } else {
                    groupsviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.font_com_night));
                }
                onetitledb.setChapters(new ArrayList());
            } else {
                groupsviewhoder.tv_Num.setVisibility(0);
                groupsviewhoder.spring_progress_view.setVisibility(0);
                if (isExpanded) {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        Drawable drawable = ContextCompat.getDrawable(this.context, R.drawable.icon_indicator_top);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable, null);
                        groupsviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
                    } else {
                        Drawable drawable2 = ContextCompat.getDrawable(this.context, R.drawable.icon_indicator_top_night);
                        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable2, null);
                        groupsviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
                    }
                } else if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    Drawable drawable3 = ContextCompat.getDrawable(this.context, R.drawable.icon_indicator_bottom);
                    drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
                    groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable3, null);
                    groupsviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color));
                } else {
                    Drawable drawable4 = ContextCompat.getDrawable(this.context, R.drawable.icon_indicator_bottom_night);
                    drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
                    groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable4, null);
                    groupsviewhoder.tv_Name.setTextColor(ContextCompat.getColor(this.context, R.color.question_color_night));
                }
                groupsviewhoder.tv_Num.setText(longSize + "/" + onetitledb.getTotal());
                groupsviewhoder.tv_Name.invalidate();
                groupsviewhoder.spring_progress_view.setMaxErrRightCount(Float.parseFloat(onetitledb.getTotal()), (float) longSize2, (float) (longSize - longSize2));
                groupsviewhoder.spring_progress_view.invalidate();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.isXitong) {
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.xitongsuo, this.context, "");
            try {
                if (TextUtils.isEmpty(strConfig)) {
                    groupsviewhoder.main_groups_share.setVisibility(0);
                    groupsviewhoder.llay_progress_view.setVisibility(8);
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        Drawable drawable5 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2);
                        drawable5.setBounds(0, 0, drawable5.getMinimumWidth(), drawable5.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable5, null);
                    } else {
                        Drawable drawable6 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2_night);
                        drawable6.setBounds(0, 0, drawable6.getMinimumWidth(), drawable6.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable6, null);
                    }
                    groupsviewhoder.tv_Num.setVisibility(8);
                    groupsviewhoder.main_groups_share.setTextColor(this.context.getResources().getColor(R.color.green));
                } else {
                    try {
                        if (!TextUtils.equals(new JSONObject(strConfig).optString("pass"), "1")) {
                            groupsviewhoder.main_groups_share.setVisibility(0);
                            groupsviewhoder.llay_progress_view.setVisibility(8);
                            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                                Drawable drawable7 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2);
                                drawable7.setBounds(0, 0, drawable7.getMinimumWidth(), drawable7.getMinimumHeight());
                                groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable7, null);
                            } else {
                                Drawable drawable8 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2_night);
                                drawable8.setBounds(0, 0, drawable8.getMinimumWidth(), drawable8.getMinimumHeight());
                                groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable8, null);
                            }
                            groupsviewhoder.tv_Num.setVisibility(8);
                            groupsviewhoder.main_groups_share.setTextColor(this.context.getResources().getColor(R.color.green));
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        } else if (!SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.context).equals("20")) {
            String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.SHARE_UNLOCK_REAL_QUESTION_NUM, this.context, "");
            try {
                if (strConfig2.equals("")) {
                    groupsviewhoder.main_groups_share.setVisibility(0);
                    groupsviewhoder.llay_progress_view.setVisibility(8);
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        Drawable drawable9 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2);
                        drawable9.setBounds(0, 0, drawable9.getMinimumWidth(), drawable9.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable9, null);
                    } else {
                        Drawable drawable10 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2_night);
                        drawable10.setBounds(0, 0, drawable10.getMinimumWidth(), drawable10.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable10, null);
                    }
                    groupsviewhoder.tv_Num.setVisibility(8);
                    groupsviewhoder.main_groups_share.setTextColor(ContextCompat.getColor(this.context, R.color.green));
                } else {
                    List list = (List) new Gson().fromJson(strConfig2, new TypeToken<List<RealProUnlockBean.DataBean>>() { // from class: com.psychiatrygarden.adapter.SubjectExpandableListAdapter.1
                    }.getType());
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        if (onetitledb.getCate_p_id().equals(((RealProUnlockBean.DataBean) list.get(i2)).getChapter_id() + "")) {
                            if (((RealProUnlockBean.DataBean) list.get(i2)).getPass() == 0) {
                                groupsviewhoder.main_groups_share.setVisibility(0);
                                groupsviewhoder.llay_progress_view.setVisibility(8);
                                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                                    Drawable drawable11 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2);
                                    drawable11.setBounds(0, 0, drawable11.getMinimumWidth(), drawable11.getMinimumHeight());
                                    groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable11, null);
                                } else {
                                    Drawable drawable12 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2_night);
                                    drawable12.setBounds(0, 0, drawable12.getMinimumWidth(), drawable12.getMinimumHeight());
                                    groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable12, null);
                                }
                                groupsviewhoder.tv_Num.setVisibility(8);
                                groupsviewhoder.main_groups_share.setTextColor(ContextCompat.getColor(this.context, R.color.green));
                            }
                            return viewInflate;
                        }
                    }
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (this.index == 2) {
            String strConfig3 = SharePreferencesUtils.readStrConfig(CommonParameter.xitongsuo, this.context, "");
            try {
                if (TextUtils.isEmpty(strConfig3)) {
                    groupsviewhoder.main_groups_share.setVisibility(0);
                    groupsviewhoder.llay_progress_view.setVisibility(8);
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        Drawable drawable13 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2);
                        drawable13.setBounds(0, 0, drawable13.getMinimumWidth(), drawable13.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable13, null);
                    } else {
                        Drawable drawable14 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2_night);
                        drawable14.setBounds(0, 0, drawable14.getMinimumWidth(), drawable14.getMinimumHeight());
                        groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable14, null);
                    }
                    groupsviewhoder.tv_Num.setVisibility(8);
                    groupsviewhoder.main_groups_share.setTextColor(this.context.getResources().getColor(R.color.green));
                } else {
                    try {
                        if (!TextUtils.equals(new JSONObject(strConfig3).optString("pass"), "1")) {
                            groupsviewhoder.main_groups_share.setVisibility(0);
                            groupsviewhoder.llay_progress_view.setVisibility(8);
                            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                                Drawable drawable15 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2);
                                drawable15.setBounds(0, 0, drawable15.getMinimumWidth(), drawable15.getMinimumHeight());
                                groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable15, null);
                            } else {
                                Drawable drawable16 = ContextCompat.getDrawable(this.context, R.drawable.icon_password2_night);
                                drawable16.setBounds(0, 0, drawable16.getMinimumWidth(), drawable16.getMinimumHeight());
                                groupsviewhoder.tv_Name.setCompoundDrawables(null, null, drawable16, null);
                            }
                            groupsviewhoder.tv_Num.setVisibility(8);
                            groupsviewhoder.main_groups_share.setTextColor(ContextCompat.getColor(this.context, R.color.green));
                        }
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
            } catch (Exception e7) {
                e7.printStackTrace();
            }
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

    public void setQuestionData(List<oneTitleDb> listData) {
        if (listData == null || listData.size() <= 0) {
            return;
        }
        this.list.clear();
        this.list.addAll(listData);
        notifyDataSetChanged();
    }

    public SubjectExpandableListAdapter(Context context, List<oneTitleDb> list, boolean isXitong, int index) {
        this.list = list;
        this.context = context;
        this.isXitong = isXitong;
        this.index = index;
    }
}
