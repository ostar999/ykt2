package com.psychiatrygarden.activity.planning;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.QuestionInfoBean;
import com.psychiatrygarden.bean.QuestionInfoBeanDao;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

/* loaded from: classes5.dex */
public class PlanningAnswerStatusActivity extends BaseActivity {
    private ImageView iv_planning_more;
    private final View.OnClickListener mOnclick = new View.OnClickListener() { // from class: com.psychiatrygarden.activity.planning.PlanningAnswerStatusActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            int id = v2.getId();
            if (id == R.id.iv_actionbar_right) {
                PlanningAnswerStatusActivity.this.goActivity(PlanningExecuteProgressActivity.class);
                return;
            }
            if (id == R.id.iv_planning_more) {
                if (((Boolean) PlanningAnswerStatusActivity.this.tv_kaodian.getTag()).booleanValue()) {
                    PlanningAnswerStatusActivity.this.tv_kaodian.setMaxLines(4);
                    PlanningAnswerStatusActivity.this.tv_kaodian.setTag(Boolean.FALSE);
                    PlanningAnswerStatusActivity.this.iv_planning_more.setImageResource(R.drawable.planning_more_down);
                } else {
                    PlanningAnswerStatusActivity.this.tv_kaodian.setSingleLine(false);
                    PlanningAnswerStatusActivity.this.tv_kaodian.setTag(Boolean.TRUE);
                    PlanningAnswerStatusActivity.this.iv_planning_more.setImageResource(R.drawable.planning_more_up);
                }
            }
        }
    };
    private TextView tv_kaodian;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        TextView textView = (TextView) findViewById(R.id.tv_kaodian);
        this.tv_kaodian = textView;
        textView.setTag(Boolean.FALSE);
        this.iv_planning_more = (ImageView) findViewById(R.id.iv_planning_more);
        List<QuestionInfoBean> list = ProjectApp.mTiKuDaoSession.getQuestionInfoBeanDao().queryBuilder().where(QuestionInfoBeanDao.Properties.Year.eq(2015), new WhereCondition[0]).orderAsc(QuestionInfoBeanDao.Properties.Number_number).list();
        long[] jArr = new long[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            jArr[i2] = list.get(i2).getQuestion_id().longValue();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("7月17日");
        this.iv_actionbar_right.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        this.iv_actionbar_right.setImageResource(R.drawable.planning_rili_top);
        this.iv_actionbar_right.setOnClickListener(this.mOnclick);
        setContentView(R.layout.activity_planning_answer_status);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.iv_planning_more.setOnClickListener(this.mOnclick);
    }
}
