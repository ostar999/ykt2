package com.psychiatrygarden.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.activity.mine.errorquestion.ExportQuestionNewActivity;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditActivity;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeQuestionListFragment;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ExportDescriptionPop extends CenterPopupView implements View.OnClickListener {
    private Bundle bundle;

    public ExportDescriptionPop(@NonNull Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_export_description;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_cancel) {
            dismiss();
            return;
        }
        if (id != R.id.tv_ok) {
            return;
        }
        if ("1".equals(this.bundle.getString("isKnowledge"))) {
            String string = this.bundle.getString(KnowledgeQuestionListFragment.EXTRA_TREE_ID, "");
            String string2 = this.bundle.getString(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, "");
            if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                NewToast.showShort(getContext(), "数据异常");
            } else {
                KnowledgeListEditActivity.gotToEditKnowledge(getContext(), string, string2);
            }
        } else {
            ExportQuestionNewActivity.INSTANCE.navigationToExportQuestionActivity(getContext(), this.bundle);
        }
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.o9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16758c.onClick(view);
            }
        });
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.o9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16758c.onClick(view);
            }
        });
    }
}
