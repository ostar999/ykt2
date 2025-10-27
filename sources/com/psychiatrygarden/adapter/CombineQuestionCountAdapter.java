package com.psychiatrygarden.adapter;

import android.text.Editable;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.util.RandomUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.QuestionCountItem;
import com.psychiatrygarden.widget.SimpleTextWatcher;
import com.yikaobang.yixue.R;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class CombineQuestionCountAdapter extends BaseMultiItemQuickAdapter<QuestionCountItem, BaseViewHolder> {
    public CombineQuestionCountAdapter(@Nullable List<QuestionCountItem> data) {
        super(data);
        addItemType(1, R.layout.layout_question_count_item);
        addItemType(2, R.layout.layout_question_count_item_input);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(View view, boolean z2) {
        if (z2) {
            for (int i2 = 0; i2 < getData().size(); i2++) {
                QuestionCountItem questionCountItem = (QuestionCountItem) getData().get(i2);
                questionCountItem.setSelect(questionCountItem.getItemType() == 2);
            }
        } else {
            Iterator it = getData().iterator();
            while (it.hasNext()) {
                ((QuestionCountItem) it.next()).setSelect(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull final BaseViewHolder holder, QuestionCountItem item) {
        int itemType = item.getItemType();
        if (itemType == 1) {
            holder.setText(R.id.labeltext, item.getTitle());
            holder.getView(R.id.labeltext).setSelected(item.isSelect());
        } else {
            if (itemType != 2) {
                return;
            }
            EditText editText = (EditText) holder.getView(R.id.et_input);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.psychiatrygarden.adapter.n1
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view, boolean z2) {
                    this.f14783c.lambda$convert$0(view, z2);
                }
            });
            editText.setKeyListener(DigitsKeyListener.getInstance(RandomUtil.BASE_NUMBER));
            editText.addTextChangedListener(new SimpleTextWatcher() { // from class: com.psychiatrygarden.adapter.CombineQuestionCountAdapter.1
                @Override // com.psychiatrygarden.widget.SimpleTextWatcher, android.text.TextWatcher
                public void afterTextChanged(Editable s2) {
                    ((QuestionCountItem) CombineQuestionCountAdapter.this.getData().get(holder.getLayoutPosition())).setTitle(s2.toString());
                }
            });
        }
    }
}
