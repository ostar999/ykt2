package com.psychiatrygarden.adapter;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.AnsweredQuestionBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionListAdapter extends BaseQuickAdapter<Long, BaseViewHolder> {
    private boolean ISPractice;
    private boolean IsLisnxi;
    private String chapter_id;
    private Context context;
    private boolean isXiTong;
    private boolean isZHONGYI;
    private long[] list_questionid;
    private String type;

    public QuestionListAdapter(Context context, long[] list_questionid, String type, boolean ISPractice, boolean IsLisnxi, boolean isXiTong) {
        super(R.layout.layout_question_list_tv);
        this.type = "";
        this.ISPractice = true;
        ArrayList arrayList = new ArrayList(list_questionid.length);
        for (long j2 : list_questionid) {
            arrayList.add(Long.valueOf(j2));
        }
        setNewInstance(arrayList);
        this.context = context;
        this.list_questionid = list_questionid;
        this.type = type;
        this.ISPractice = ISPractice;
        this.IsLisnxi = IsLisnxi;
        this.isXiTong = isXiTong;
    }

    private boolean hasAdded(AnsweredQuestionBean info, List<AnsweredQuestionBean> listSubmitDataList) {
        for (AnsweredQuestionBean answeredQuestionBean : listSubmitDataList) {
            if (info.getQuestion_id().equals(answeredQuestionBean.getQuestion_id()) && info.getChapter_id().equals(answeredQuestionBean.getChapter_id()) && info.getChapter_parent_id().equals(answeredQuestionBean.getChapter_parent_id())) {
                return true;
            }
        }
        return false;
    }

    public long[] getList_questionid() {
        return this.list_questionid;
    }

    public boolean isZHONGYI() {
        return this.isZHONGYI;
    }

    public void setData(long[] list_questionid) {
        this.list_questionid = list_questionid;
    }

    public void setListData(long[] list_questionid) {
        this.list_questionid = list_questionid;
        ArrayList arrayList = new ArrayList(list_questionid.length);
        for (long j2 : list_questionid) {
            arrayList.add(Long.valueOf(j2));
        }
        setNewInstance(arrayList);
    }

    public void setZHONGYI(boolean ZHONGYI) {
        this.isZHONGYI = ZHONGYI;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, Long item) {
        TextView textView = (TextView) helper.getView(R.id.questionList_item_tv);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UIUtil.getScreenWidth(getContext()) / 7, UIUtil.getScreenWidth(getContext()) / 7);
        layoutParams.setMargins(0, UIUtil.dip2px(getContext(), 15.0d), 0, 0);
        textView.setLayoutParams(layoutParams);
        AnsweredQuestionBean answeredQuestionBeanLoadByRowId = ProjectApp.mDaoSession.getAnsweredQuestionBeanDao().loadByRowId(item.longValue());
        if (answeredQuestionBeanLoadByRowId == null) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                textView.setBackgroundResource(R.drawable.drawable_gray);
                textView.setTextColor(this.context.getResources().getColor(R.color.gray_font));
            } else {
                textView.setBackgroundResource(R.drawable.drawable_gray_night);
                textView.setTextColor(this.context.getResources().getColor(R.color.font_com_night));
            }
        } else if (SharePreferencesUtils.readIntConfig(CommonParameter.ISCESHITIKU, this.context, 0) == 1) {
            if (this.ISPractice) {
                if (answeredQuestionBeanLoadByRowId.getIs_right().equals("0")) {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        textView.setBackgroundResource(R.drawable.drawable_quetionlist_red);
                    } else {
                        textView.setBackgroundResource(R.drawable.drawable_red_night);
                    }
                    textView.setTextColor(this.context.getResources().getColor(R.color.white));
                } else {
                    if (SkinManager.getCurrentSkinType(this.context) == 0) {
                        textView.setBackgroundResource(R.drawable.drawable_quetionlist_green);
                    } else {
                        textView.setBackgroundResource(R.drawable.drawable_green_night);
                    }
                    textView.setTextColor(this.context.getResources().getColor(R.color.white));
                }
            } else if (hasAdded(answeredQuestionBeanLoadByRowId, ProjectApp.listSubmitData)) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    textView.setBackgroundResource(R.drawable.drawzble_quetionlist_black);
                } else {
                    textView.setBackgroundResource(R.drawable.drawable_an_night);
                }
                textView.setTextColor(this.context.getResources().getColor(R.color.white));
            } else if (answeredQuestionBeanLoadByRowId.getIs_right().equals("0")) {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    textView.setBackgroundResource(R.drawable.drawable_quetionlist_red);
                } else {
                    textView.setBackgroundResource(R.drawable.drawable_red_night);
                }
                textView.setTextColor(this.context.getResources().getColor(R.color.white));
            } else {
                if (SkinManager.getCurrentSkinType(this.context) == 0) {
                    textView.setBackgroundResource(R.drawable.drawable_quetionlist_green);
                } else {
                    textView.setBackgroundResource(R.drawable.drawable_green_night);
                }
                textView.setTextColor(this.context.getResources().getColor(R.color.white));
            }
        } else if (answeredQuestionBeanLoadByRowId.getIs_right().equals("0")) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                textView.setBackgroundResource(R.drawable.drawable_quetionlist_red);
            } else {
                textView.setBackgroundResource(R.drawable.drawable_red_night);
            }
            textView.setTextColor(this.context.getResources().getColor(R.color.white));
        } else {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                textView.setBackgroundResource(R.drawable.drawable_quetionlist_green);
            } else {
                textView.setBackgroundResource(R.drawable.drawable_green_night);
            }
            textView.setTextColor(this.context.getResources().getColor(R.color.white));
        }
        if (isZHONGYI()) {
            if ("".equals(CommonUtil.getSortNum(this.chapter_id, item.longValue()))) {
                textView.setText((helper.getAdapterPosition() + 1) + "");
                return;
            }
            textView.setText(CommonUtil.getSortNum(this.chapter_id, item.longValue()) + "");
            return;
        }
        if (this.type.equals("year")) {
            textView.setText(ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(item.longValue()).getNumber_number() + "");
            return;
        }
        if (this.type.equals("subject")) {
            if (SharePreferencesUtils.readStrConfig(CommonParameter.APP_TIKU_MARK, this.context, "").equals(CommonParameter.Xueshuo)) {
                if (!this.isXiTong) {
                    textView.setText(ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(item.longValue()).getS_num_xue() + "");
                    return;
                }
                if (this.IsLisnxi) {
                    textView.setText(ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(item.longValue()).getS_num_xue() + "");
                    return;
                }
                if ("".equals(CommonUtil.getSortNum(this.chapter_id, item.longValue()))) {
                    textView.setText((helper.getAdapterPosition() + 1) + "");
                    return;
                }
                textView.setText(CommonUtil.getSortNum(this.chapter_id, item.longValue()) + "");
                return;
            }
            if (!this.isXiTong) {
                textView.setText(ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(item.longValue()).getS_num() + "");
                return;
            }
            if (this.IsLisnxi) {
                textView.setText(ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().loadByRowId(item.longValue()).getS_num() + "");
                return;
            }
            if ("".equals(CommonUtil.getSortNum(this.chapter_id, item.longValue()))) {
                textView.setText((helper.getAdapterPosition() + 1) + "");
                return;
            }
            textView.setText(CommonUtil.getSortNum(this.chapter_id, item.longValue()) + "");
        }
    }

    public QuestionListAdapter(Context context, long[] list_questionid, String type, boolean ISPractice, boolean IsLisnxi, boolean isXiTong, String chapter_id) {
        super(R.layout.layout_question_list_tv);
        this.type = "";
        this.ISPractice = true;
        ArrayList arrayList = new ArrayList(list_questionid.length);
        for (long j2 : list_questionid) {
            arrayList.add(Long.valueOf(j2));
        }
        setNewInstance(arrayList);
        this.context = context;
        this.list_questionid = list_questionid;
        this.type = type;
        this.ISPractice = ISPractice;
        this.IsLisnxi = IsLisnxi;
        this.isXiTong = isXiTong;
        this.chapter_id = chapter_id;
    }
}
