package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.psychiatrygarden.activity.answer.AnswerDetailActivity;
import com.psychiatrygarden.bean.QuestionListTestBean;
import com.psychiatrygarden.utils.FullyGridLayoutManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionListTestAdapter extends BaseAdapter {
    private boolean ISPractice;
    private Context context;
    private List<QuestionListTestBean> mQestionListTestBeans;
    private String title;
    ViewHoder viewHoder = null;
    private String year;

    public class ViewHoder {
        private RecyclerView gradview;
        private TextView titleTxt;

        public ViewHoder(View view) {
            this.titleTxt = (TextView) view.findViewById(R.id.titleTxt);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.gradview);
            this.gradview = recyclerView;
            recyclerView.setHasFixedSize(true);
            this.gradview.setNestedScrollingEnabled(false);
            this.gradview.setLayoutManager(new FullyGridLayoutManager(QuestionListTestAdapter.this.context, 5, 1, false));
        }
    }

    public QuestionListTestAdapter(Context context, List<QuestionListTestBean> mQestionListTestBeans, boolean ISPractice, String title, String year) {
        this.context = context;
        this.mQestionListTestBeans = mQestionListTestBeans;
        this.ISPractice = ISPractice;
        this.title = title;
        this.year = year;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(long[] jArr, QuestionListTestBean questionListTestBean, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        try {
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < this.mQestionListTestBeans.size(); i3++) {
                arrayList.addAll(this.mQestionListTestBeans.get(i3).getListQuestions());
            }
            int size = arrayList.size();
            long[] jArr2 = new long[size];
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                jArr2[i4] = Long.parseLong((String) arrayList.get(i4));
            }
            int i5 = 0;
            for (int i6 = 0; i6 < size; i6++) {
                if (jArr2[i6] == jArr[i2]) {
                    i5 = i6;
                }
            }
            Intent intent = new Intent(this.context, (Class<?>) AnswerDetailActivity.class);
            intent.putExtra("totalCount", size + "");
            intent.putExtra("modletype", "year");
            intent.putExtra("position", i5);
            intent.putExtra("year", this.year);
            intent.putExtra("list", jArr2);
            intent.putExtra("title", this.title);
            intent.putExtra("ISPractice", this.ISPractice);
            intent.putExtra("json_question_data", "");
            intent.putExtra("unit", questionListTestBean.getUnit());
            this.context.startActivity(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mQestionListTestBeans.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.mQestionListTestBeans.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_gridadapter, (ViewGroup) null);
            ViewHoder viewHoder = new ViewHoder(convertView);
            this.viewHoder = viewHoder;
            convertView.setTag(viewHoder);
        } else {
            this.viewHoder = (ViewHoder) convertView.getTag();
        }
        final QuestionListTestBean questionListTestBean = this.mQestionListTestBeans.get(position);
        this.viewHoder.titleTxt.setText(questionListTestBean.getQuestionTitle());
        List<String> listQuestions = questionListTestBean.getListQuestions();
        if (listQuestions != null && listQuestions.size() > 0) {
            final long[] jArr = new long[listQuestions.size()];
            for (int i2 = 0; i2 < listQuestions.size(); i2++) {
                jArr[i2] = Long.parseLong(listQuestions.get(i2));
            }
            QuestionListAdapter questionListAdapter = new QuestionListAdapter(this.context, jArr, "year", true, false, false);
            this.viewHoder.gradview.setAdapter(questionListAdapter);
            questionListAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.adapter.je
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i3) {
                    this.f14657c.lambda$getView$0(jArr, questionListTestBean, baseQuickAdapter, view, i3);
                }
            });
        }
        return convertView;
    }
}
