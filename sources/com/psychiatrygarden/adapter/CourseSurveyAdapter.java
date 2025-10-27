package com.psychiatrygarden.adapter;

import android.text.Editable;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CourseSurveyBean;
import com.psychiatrygarden.widget.SimpleTextWatcher;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public class CourseSurveyAdapter extends BaseQuickAdapter<CourseSurveyBean, BaseViewHolder> {
    private final Map<String, String> answerMap;
    private final Map<Integer, View> mViewMap;

    public CourseSurveyAdapter() {
        super(R.layout.item_course_survey, new ArrayList());
        this.answerMap = new ArrayMap();
        this.mViewMap = new ArrayMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(List list, CourseSurveyBean courseSurveyBean, View view) {
        if (!view.isSelected()) {
            view.setSelected(true);
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ImageView imageView = (ImageView) it.next();
            if (imageView != view) {
                imageView.setSelected(false);
            }
        }
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            ImageView imageView2 = (ImageView) it2.next();
            if (imageView2.isSelected()) {
                this.answerMap.put(courseSurveyBean.getId(), String.valueOf(imageView2.getTag()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(List list, CourseSurveyBean courseSurveyBean, View view) {
        view.setSelected(!view.isSelected());
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            View view2 = (View) it.next();
            if (view2.isSelected()) {
                arrayList.add((Integer) view2.getTag());
            }
        }
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            sb.append(arrayList.get(i2));
            if (i2 < arrayList.size() - 1) {
                sb.append(",");
            }
        }
        this.answerMap.put(courseSurveyBean.getId(), sb.toString());
    }

    public Map<String, String> getAnswerMap() {
        return this.answerMap;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, final CourseSurveyBean item) {
        holder.setText(R.id.tv_title, (holder.getLayoutPosition() + 1) + "、" + item.getTitle());
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ll_options);
        linearLayout.removeAllViews();
        final ArrayList arrayList = new ArrayList();
        List<String> option = item.getOption();
        if ("3".equals(item.getType()) && option.isEmpty()) {
            option.add("");
        }
        for (int i2 = 0; i2 < option.size(); i2++) {
            String str = option.get(i2);
            View viewInflate = View.inflate(getContext(), R.layout.item_survey_option, null);
            linearLayout.addView(viewInflate);
            viewInflate.setTag(Integer.valueOf(i2));
            this.mViewMap.put(Integer.valueOf(i2), viewInflate);
            EditText editText = (EditText) viewInflate.findViewById(R.id.input_et_content);
            LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.ll_option);
            linearLayout2.setVisibility(!Objects.equals(item.getType(), "3") ? 0 : 8);
            editText.setVisibility(Objects.equals(item.getType(), "3") ? 0 : 8);
            if ("3".equals(item.getType())) {
                linearLayout2.setVisibility(8);
                editText.addTextChangedListener(new SimpleTextWatcher() { // from class: com.psychiatrygarden.adapter.CourseSurveyAdapter.1
                    @Override // com.psychiatrygarden.widget.SimpleTextWatcher, android.text.TextWatcher
                    public void afterTextChanged(Editable s2) {
                        super.afterTextChanged(s2);
                        item.setSelectOption(s2.toString());
                        CourseSurveyAdapter.this.answerMap.put(item.getId(), item.getSelectOption());
                    }
                });
            } else {
                linearLayout2.setVisibility(0);
                ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_single_select);
                ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.iv_multi_select);
                if ("1".equals(item.getType())) {
                    arrayList.add(imageView);
                } else {
                    arrayList.add(imageView2);
                }
                imageView.setTag(Integer.valueOf(i2));
                imageView2.setTag(Integer.valueOf(i2));
                imageView.setVisibility(Objects.equals(item.getType(), "1") ? 0 : 8);
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.a4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14275c.lambda$convert$0(arrayList, item, view);
                    }
                });
                imageView2.setVisibility(Objects.equals(item.getType(), "2") ? 0 : 8);
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.b4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f14314c.lambda$convert$1(arrayList, item, view);
                    }
                });
                ((TextView) viewInflate.findViewById(R.id.tv_title)).setText(str);
            }
        }
    }
}
