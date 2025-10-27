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
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.aliyun.player.alivcplayerexpand.util.TimeFormater;
import com.psychiatrygarden.activity.courselist.bean.CourseCalalogueBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.YkbLottieAnimationView;
import com.yikaobang.yixue.R;
import java.math.BigDecimal;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseCalalogueListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CourseCalalogueBean.DataNewBean.DataBean> data;
    private CalaInterfaceIml mCalaInterfaceIml;
    private String watch_permission = "0";

    public interface CalaInterfaceIml {
        void getVidData(CourseCalalogueBean.DataNewBean.DataBean.CourseListBean childrenBean);

        void pauseByVid(String vid);

        void startByVid(CourseCalalogueBean.DataNewBean.DataBean.CourseListBean childrenBean);
    }

    public class childViewHoder {
        TextView detailtx;
        YkbLottieAnimationView lottieview;
        TextView num;
        TextView title;
        ImageView xiazainew;

        public childViewHoder(View view) {
            this.num = (TextView) view.findViewById(R.id.num);
            this.title = (TextView) view.findViewById(R.id.title);
            this.detailtx = (TextView) view.findViewById(R.id.detailtx);
            this.lottieview = (YkbLottieAnimationView) view.findViewById(R.id.lottieview);
            this.xiazainew = (ImageView) view.findViewById(R.id.xiazainew);
        }
    }

    public class groupsViewHoder {
        ImageView course_img;
        TextView mycollect_groups_tv_name;

        public groupsViewHoder(View convertView) {
            this.mycollect_groups_tv_name = (TextView) convertView.findViewById(R.id.mycollect_groups_tv_name);
            this.course_img = (ImageView) convertView.findViewById(R.id.course_img);
        }
    }

    public CourseCalalogueListAdapter(Context context, List<CourseCalalogueBean.DataNewBean.DataBean> data, CalaInterfaceIml mCalaInterfaceIml) {
        this.context = context;
        this.data = data;
        this.mCalaInterfaceIml = mCalaInterfaceIml;
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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final childViewHoder childviewhoder;
        View viewInflate;
        if (convertView == null) {
            viewInflate = LayoutInflater.from(this.context).inflate(R.layout.item_course_calalog_child, (ViewGroup) null);
            childviewhoder = new childViewHoder(viewInflate);
            viewInflate.setTag(childviewhoder);
        } else {
            childviewhoder = (childViewHoder) convertView.getTag();
            viewInflate = convertView;
        }
        final CourseCalalogueBean.DataNewBean.DataBean.CourseListBean courseListBean = this.data.get(groupPosition).getCourseList().get(childPosition);
        int i2 = childPosition + 1;
        if (i2 < 10) {
            childviewhoder.num.setText("0" + i2);
        } else {
            childviewhoder.num.setText(i2 + "");
        }
        if (courseListBean.getDuration() == null || "".equals(courseListBean.getDuration())) {
            childviewhoder.detailtx.setText("时长 0");
        } else {
            long jDoubleValue = (long) (Double.valueOf(Double.parseDouble(courseListBean.getDuration())).doubleValue() * 1000.0d);
            String ms = TimeFormater.formatMs(jDoubleValue);
            if (courseListBean.getDurationTemp() > 0.0d) {
                int iIntValue = new BigDecimal(100.0d * (courseListBean.getDurationTemp() / jDoubleValue)).setScale(0, 4).intValue();
                if (iIntValue >= 100) {
                    String str = "时长 " + ms + "  |  已看完";
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#5EAE06")), str.length() - 3, str.length(), 18);
                    childviewhoder.detailtx.setText(spannableStringBuilder);
                } else if (iIntValue < 1) {
                    childviewhoder.detailtx.setText("时长 " + ms + "  |  已看1%");
                } else {
                    childviewhoder.detailtx.setText("时长 " + ms + "  |  已看" + iIntValue + "%");
                }
            } else if (1 == courseListBean.getIsSelected()) {
                childviewhoder.detailtx.setText("时长 " + ms + "  |  已看1%");
            } else {
                childviewhoder.detailtx.setText("时长 " + ms);
            }
        }
        if ("1".equals(getWatch_permission())) {
            childviewhoder.xiazainew.setVisibility(0);
        } else {
            childviewhoder.xiazainew.setVisibility(8);
        }
        if (5 == courseListBean.getmStatus()) {
            childviewhoder.xiazainew.setImageResource(R.drawable.yixiazainewimg);
            childviewhoder.xiazainew.setOnClickListener(null);
            childviewhoder.xiazainew.clearAnimation();
        } else if (1 == courseListBean.getmStatus()) {
            childviewhoder.xiazainew.setImageResource(R.drawable.xiazaizimgnew);
            childviewhoder.xiazainew.setOnClickListener(null);
            CommonUtil.roteImg(childviewhoder.xiazainew, -1);
            childviewhoder.xiazainew.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    courseListBean.setmStatus(4);
                    CourseCalalogueListAdapter.this.mCalaInterfaceIml.pauseByVid(courseListBean.getVid());
                    CourseCalalogueListAdapter.this.notifyDataSetChanged();
                }
            });
        } else if (3 == courseListBean.getmStatus()) {
            childviewhoder.xiazainew.setImageResource(R.drawable.daixiazainewimg);
            childviewhoder.xiazainew.setOnClickListener(null);
            childviewhoder.xiazainew.clearAnimation();
            childviewhoder.xiazainew.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    courseListBean.setmStatus(4);
                    CourseCalalogueListAdapter.this.mCalaInterfaceIml.pauseByVid(courseListBean.getVid());
                    CourseCalalogueListAdapter.this.notifyDataSetChanged();
                }
            });
        } else if (4 == courseListBean.getmStatus()) {
            childviewhoder.xiazainew.setImageResource(R.drawable.zantingnewimg);
            childviewhoder.xiazainew.setOnClickListener(null);
            childviewhoder.xiazainew.clearAnimation();
            childviewhoder.xiazainew.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.3
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    courseListBean.setmStatus(3);
                    CourseCalalogueListAdapter.this.mCalaInterfaceIml.startByVid(courseListBean);
                    CourseCalalogueListAdapter.this.notifyDataSetChanged();
                }
            });
        } else {
            childviewhoder.xiazainew.clearAnimation();
            childviewhoder.xiazainew.setImageResource(R.drawable.xizainewimg);
            childviewhoder.xiazainew.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.4
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    CourseCalalogueListAdapter.this.mCalaInterfaceIml.getVidData(courseListBean);
                }
            });
        }
        if (1 == courseListBean.getIsSelected()) {
            childviewhoder.title.setTextColor(this.context.getResources().getColor(R.color.app_theme_red));
            childviewhoder.num.setTextColor(this.context.getResources().getColor(R.color.app_theme_red));
            try {
                childviewhoder.lottieview.setVisibility(0);
                LottieCompositionFactory.fromAsset(this.context, "courseimg.json").addListener(new LottieListener<LottieComposition>() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.6
                    @Override // com.airbnb.lottie.LottieListener
                    public void onResult(LottieComposition result) {
                        childviewhoder.lottieview.setComposition(result);
                        childviewhoder.lottieview.setRepeatCount(-1);
                        childviewhoder.lottieview.playAnimation();
                    }
                }).addFailureListener(new LottieListener<Throwable>() { // from class: com.psychiatrygarden.activity.courselist.adapter.CourseCalalogueListAdapter.5
                    @Override // com.airbnb.lottie.LottieListener
                    public void onResult(Throwable result) {
                        result.printStackTrace();
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                childviewhoder.lottieview.setVisibility(4);
                if (childviewhoder.lottieview.isAnimating()) {
                    childviewhoder.lottieview.cancelAnimation();
                    childviewhoder.lottieview.clearAnimation();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            childviewhoder.title.setTextColor(this.context.getResources().getColor(R.color.FF191919));
            childviewhoder.num.setTextColor(this.context.getResources().getColor(R.color.FF969696));
        }
        childviewhoder.title.setText(courseListBean.getTitle());
        return viewInflate;
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        groupsViewHoder groupsviewhoder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_relive_groups, (ViewGroup) null);
            groupsviewhoder = new groupsViewHoder(convertView);
            convertView.setTag(groupsviewhoder);
        } else {
            groupsviewhoder = (groupsViewHoder) convertView.getTag();
        }
        CourseCalalogueBean.DataNewBean.DataBean dataBean = this.data.get(groupPosition);
        groupsviewhoder.mycollect_groups_tv_name.setText(dataBean.getTitle());
        if (dataBean.getCourseList() == null || dataBean.getCourseList().size() <= 0) {
            groupsviewhoder.mycollect_groups_tv_name.setTextColor(this.context.getResources().getColor(R.color.gray_font_new2));
            groupsviewhoder.course_img.setVisibility(4);
        } else {
            groupsviewhoder.course_img.setVisibility(0);
            groupsviewhoder.mycollect_groups_tv_name.setTextColor(this.context.getResources().getColor(R.color.question_color));
            if (isExpanded) {
                groupsviewhoder.course_img.setImageResource(R.drawable.course_s);
            } else {
                groupsviewhoder.course_img.setImageResource(R.drawable.course_x);
            }
        }
        return convertView;
    }

    public String getWatch_permission() {
        return this.watch_permission;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setNewData(List<CourseCalalogueBean.DataNewBean.DataBean> dataNew) {
        this.data.clear();
        this.data.addAll(dataNew);
        notifyDataSetChanged();
    }

    public void setWatch_permission(String watch_permission) {
        this.watch_permission = watch_permission;
        notifyDataSetChanged();
    }
}
