package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.adapter.QuestionYearFilterAdp;
import com.psychiatrygarden.widget.english.PopQuestionYearFilter;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class QuestionYearFilterDialog extends Dialog {
    public static final int TWO_BUTTON = 2;
    private boolean isOutTouchDismiss;
    private TextView mBtnSure;
    private QuestionListBean.DataDTO.SearchDTO.SearchDataDTO mSelectedItem;
    private OnItemClickLisenter onItemClickLisenter;
    private RelativeLayout relView;
    private String value;
    private View viewRoot;

    public static abstract class OnItemClickLisenter {
        public abstract void setOnItemClick(String value, List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearData, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO item);
    }

    public QuestionYearFilterDialog(final Context context, final List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearData) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_question_year_filter, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        this.mBtnSure = (TextView) viewFindViewById.findViewById(R.id.btn_sure);
        ImageView imageView = (ImageView) this.viewRoot.findViewById(R.id.iv_year_close);
        RecyclerView recyclerView = (RecyclerView) this.viewRoot.findViewById(R.id.recyclerView);
        final QuestionYearFilterAdp questionYearFilterAdp = new QuestionYearFilterAdp(R.layout.item_question_filter_year);
        recyclerView.setAdapter(questionYearFilterAdp);
        questionYearFilterAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.mf
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16713c.lambda$new$1(questionYearFilterAdp, context, yearData, baseQuickAdapter, view, i2);
            }
        });
        questionYearFilterAdp.setList(yearData);
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.nf
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16739c.lambda$new$2(layoutParams, dialogInterface);
            }
        });
        this.viewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.of
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuestionYearFilterDialog.lambda$new$3(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.pf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16800c.lambda$new$4(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.qf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16830c.lambda$new$5(view);
            }
        });
        this.mBtnSure.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.rf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16865c.lambda$new$6(yearData, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(List list, int i2, QuestionYearFilterAdp questionYearFilterAdp, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO) {
        for (int i3 = 0; i3 < list.size(); i3++) {
            ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setIsSelected(0);
        }
        ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i2)).setIsSelected(1);
        ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i2)).setYearTitle(searchDataDTO.getYearTitle());
        this.mSelectedItem.setIsSelected(1);
        this.mSelectedItem.setYearTitle(searchDataDTO.getYearTitle());
        questionYearFilterAdp.notifyDataSetChanged();
        if (TextUtils.isEmpty(this.mSelectedItem.getYearTitle())) {
            this.value = this.mSelectedItem.getTitle();
        } else {
            this.value = this.mSelectedItem.getYearTitle();
        }
        OnItemClickLisenter onItemClickLisenter = this.onItemClickLisenter;
        if (onItemClickLisenter != null) {
            onItemClickLisenter.setOnItemClick(this.value, list, this.mSelectedItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(final QuestionYearFilterAdp questionYearFilterAdp, Context context, final List list, BaseQuickAdapter baseQuickAdapter, View view, final int i2) {
        QuestionListBean.DataDTO.SearchDTO.SearchDataDTO item = questionYearFilterAdp.getItem(i2);
        this.mSelectedItem = item;
        if (item.getId().equals("free_year")) {
            new XPopup.Builder(context).autoDismiss(Boolean.FALSE).asCustom(new PopQuestionYearFilter(context, this.mSelectedItem, new PopQuestionYearFilter.OnClickBtnListener() { // from class: com.psychiatrygarden.widget.lf
                @Override // com.psychiatrygarden.widget.english.PopQuestionYearFilter.OnClickBtnListener
                public final void onChooseYear(QuestionListBean.DataDTO.SearchDTO.SearchDataDTO searchDataDTO) {
                    this.f16683a.lambda$new$0(list, i2, questionYearFilterAdp, searchDataDTO);
                }
            })).show();
            return;
        }
        for (int i3 = 0; i3 < list.size(); i3++) {
            ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setYearTitle("");
            if (((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).getId().equals(this.mSelectedItem.getId())) {
                ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setIsSelected(1);
            } else {
                ((QuestionListBean.DataDTO.SearchDTO.SearchDataDTO) list.get(i3)).setIsSelected(0);
            }
        }
        this.value = this.mSelectedItem.getTitle();
        questionYearFilterAdp.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(12);
        this.viewRoot.setLayoutParams(layoutParams2);
        this.viewRoot.setPivotY(0.0f);
        this.viewRoot.setPivotX(((getContext().getResources().getDisplayMetrics().widthPixels * 2.0f) / 3.0f) / 2.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$3(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view) {
        dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(List list, View view) {
        OnItemClickLisenter onItemClickLisenter = this.onItemClickLisenter;
        if (onItemClickLisenter != null) {
            onItemClickLisenter.setOnItemClick(this.value, list, this.mSelectedItem);
        }
    }

    public void dismissNoAnimaltion() {
        super.dismiss();
    }

    public void isOutTouchDismiss(boolean isOutTouchDismiss) {
        this.isOutTouchDismiss = isOutTouchDismiss;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        getWindow().setAttributes(attributes);
    }

    public void setOnItemActionLisenter(OnItemClickLisenter lisenter) {
        this.onItemClickLisenter = lisenter;
    }
}
