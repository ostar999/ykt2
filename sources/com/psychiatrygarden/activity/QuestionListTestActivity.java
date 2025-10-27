package com.psychiatrygarden.activity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.psychiatrygarden.adapter.QuestionListTestAdapter;
import com.psychiatrygarden.bean.QuestionListTestBean;
import com.psychiatrygarden.bean.UserOwnerBean;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionListTestActivity extends BaseActivity {
    private boolean ISPractice;
    private ListView listView;
    private String title;
    private String year;
    private List<UserOwnerBean.DataBean> dataBean = new ArrayList();
    private final List<QuestionListTestBean> mQuestionListTestBeans = new ArrayList();
    private int groupPosition = 0;
    private int childPosition = 0;
    private final QuestionListTestBean unit1 = new QuestionListTestBean();
    private final QuestionListTestBean unit2 = new QuestionListTestBean();
    private final QuestionListTestBean unit3 = new QuestionListTestBean();
    private final QuestionListTestBean unit4 = new QuestionListTestBean();

    public void getNewData() {
        List<UserOwnerBean.DataBean> list = this.dataBean;
        if (list == null || list.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (int i2 = 0; i2 < this.dataBean.get(this.groupPosition).getChild().get(this.childPosition).getmThreadDati().size(); i2++) {
            UserOwnerBean.DataBean.ThreadDati threadDati = this.dataBean.get(this.groupPosition).getChild().get(this.childPosition).getmThreadDati().get(i2);
            if (threadDati.getUnit().equals("U1")) {
                this.unit1.setQuestionTitle("第一单元");
                this.unit1.setUnit("U1");
                arrayList.add(threadDati.getObj());
            } else if (threadDati.getUnit().equals("U2")) {
                this.unit2.setQuestionTitle("第二单元");
                this.unit2.setUnit("U2");
                arrayList2.add(threadDati.getObj());
            } else if (threadDati.getUnit().equals("U3")) {
                this.unit3.setQuestionTitle("第三单元");
                this.unit3.setUnit("U3");
                arrayList3.add(threadDati.getObj());
            } else if (threadDati.getUnit().equals("U4")) {
                this.unit4.setQuestionTitle("第四单元");
                this.unit4.setUnit("U4");
                arrayList4.add(threadDati.getObj());
            }
        }
        this.unit1.setListQuestions(arrayList);
        this.unit2.setListQuestions(arrayList2);
        this.unit3.setListQuestions(arrayList3);
        this.unit4.setListQuestions(arrayList4);
        if (this.unit1.getListQuestions().size() > 0) {
            this.mQuestionListTestBeans.add(this.unit1);
        }
        if (this.unit2.getListQuestions().size() > 0) {
            this.mQuestionListTestBeans.add(this.unit2);
        }
        if (this.unit3.getListQuestions().size() > 0) {
            this.mQuestionListTestBeans.add(this.unit3);
        }
        if (this.unit4.getListQuestions().size() > 0) {
            this.mQuestionListTestBeans.add(this.unit4);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.listView = (ListView) findViewById(R.id.listView);
        this.dataBean = (List) getIntent().getSerializableExtra("dataBean");
        this.year = getIntent().getExtras().getString("year");
        this.title = getIntent().getExtras().getString("title");
        this.groupPosition = getIntent().getExtras().getInt("groupPosition");
        this.childPosition = getIntent().getExtras().getInt("childPosition");
        this.ISPractice = getIntent().getBooleanExtra("ISPractice", false);
        setTitle(this.title);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_list_test);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        getNewData();
        this.listView.setAdapter((ListAdapter) new QuestionListTestAdapter(this.mContext, this.mQuestionListTestBeans, this.ISPractice, this.title, this.year));
    }
}
