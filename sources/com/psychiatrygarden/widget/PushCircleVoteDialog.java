package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.bean.CircleInfoBean;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public class PushCircleVoteDialog extends Dialog {
    public static final int TWO_BUTTON = 2;
    private OnChoosedLisenter chooseCircleLisenter;
    private Context context;
    private int index;
    private String isMore;
    private boolean isOutTouchDismiss;
    private LinearLayout mLyAddOption;
    private LinearLayout mLyAddView;
    private TextView mTvAddOption;
    private RelativeLayout relView;
    private View viewRoot;

    public static abstract class OnChoosedLisenter {
        public abstract void onCircleChoosed(List<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean> votes, String isSingle);
    }

    public PushCircleVoteDialog(final Context context, List<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean> mOptionsList, String voteType) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.index = 1;
        this.isMore = "2";
        this.context = context;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_vote_circle_view, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        TextView textView = (TextView) viewFindViewById.findViewById(R.id.btn_cancel);
        TextView textView2 = (TextView) this.viewRoot.findViewById(R.id.btn_finish);
        final TextView textView3 = (TextView) this.viewRoot.findViewById(R.id.btn_single);
        final TextView textView4 = (TextView) this.viewRoot.findViewById(R.id.btn_more);
        this.mLyAddView = (LinearLayout) this.viewRoot.findViewById(R.id.linetoupiao);
        this.mLyAddOption = (LinearLayout) this.viewRoot.findViewById(R.id.ly_add_option);
        this.mTvAddOption = (TextView) this.viewRoot.findViewById(R.id.addoption);
        setContentView(this.relView);
        int i2 = 0;
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.se
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16902c.lambda$new$0(layoutParams, context, dialogInterface);
            }
        });
        if (voteType.equals("单选")) {
            this.isMore = "1";
            textView3.setTextColor(SkinManager.getCurrentSkinType(context) == 1 ? Color.parseColor("#B2575C") : Color.parseColor("#DD594A"));
            textView4.setTextColor(SkinManager.getCurrentSkinType(context) == 1 ? Color.parseColor("#606A8A") : Color.parseColor("#606060"));
            textView4.setBackgroundResource(R.drawable.shape_bg_gray_4_round);
            textView3.setBackgroundResource(R.drawable.shape_bg_light_red_4_round);
        } else {
            this.isMore = "2";
            textView4.setTextColor(SkinManager.getCurrentSkinType(context) == 1 ? Color.parseColor("#B2575C") : Color.parseColor("#DD594A"));
            textView3.setTextColor(SkinManager.getCurrentSkinType(context) == 1 ? Color.parseColor("#606A8A") : Color.parseColor("#606060"));
            textView4.setBackgroundResource(R.drawable.shape_bg_light_red_4_round);
            textView3.setBackgroundResource(R.drawable.shape_bg_gray_4_round);
        }
        if (mOptionsList == null || mOptionsList.size() <= 0) {
            this.mLyAddView.removeAllViews();
            while (i2 < 2) {
                addOptionView("");
                i2++;
            }
        } else {
            this.mLyAddView.removeAllViews();
            while (i2 < mOptionsList.size()) {
                addOptionView(mOptionsList.get(i2).getOption(), mOptionsList.get(i2));
                i2++;
            }
        }
        this.viewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.te
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PushCircleVoteDialog.lambda$new$1(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ue
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16962c.lambda$new$2(view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ve
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16999c.lambda$new$3(textView4, context, textView3, view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.we
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17049c.lambda$new$4(textView3, context, textView4, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17096c.lambda$new$5(context, view);
            }
        });
        this.mLyAddOption.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ye
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17129c.lambda$new$6(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ze
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17154c.lambda$new$7(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOptionView$8(RelativeLayout relativeLayout, View view) {
        this.mLyAddView.removeView(relativeLayout);
        mRefreshOptionView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOptionView$9(RelativeLayout relativeLayout, View view) {
        this.mLyAddView.removeView(relativeLayout);
        mRefreshOptionView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(RelativeLayout.LayoutParams layoutParams, Context context, DialogInterface dialogInterface) {
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(12);
        layoutParams2.height = getContext().getResources().getDisplayMetrics().heightPixels - ScreenUtil.getPxByDp(context, 200);
        this.viewRoot.setLayoutParams(layoutParams2);
        this.viewRoot.setPivotY(0.0f);
        this.viewRoot.setPivotX(((getContext().getResources().getDisplayMetrics().widthPixels * 2.0f) / 3.0f) / 2.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(TextView textView, Context context, TextView textView2, View view) {
        this.isMore = "2";
        textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(context) == 1 ? "#B2575C" : "#DD594A"));
        textView2.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(context) == 1 ? "#606A8A" : "#606060"));
        textView.setBackgroundResource(R.drawable.shape_bg_light_red_4_round);
        textView2.setBackgroundResource(R.drawable.shape_bg_gray_4_round);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(TextView textView, Context context, TextView textView2, View view) {
        this.isMore = "1";
        textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(context) == 1 ? "#B2575C" : "#DD594A"));
        textView2.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(context) == 1 ? "#606A8A" : "#606060"));
        textView2.setBackgroundResource(R.drawable.shape_bg_gray_4_round);
        textView.setBackgroundResource(R.drawable.shape_bg_light_red_4_round);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(Context context, View view) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mLyAddView.getChildCount(); i2++) {
            if (this.mLyAddView.getChildAt(i2) instanceof RelativeLayout) {
                RelativeLayout relativeLayout = (RelativeLayout) this.mLyAddView.getChildAt(i2);
                Object tag = relativeLayout.getTag();
                for (int i3 = 0; i3 < relativeLayout.getChildCount(); i3++) {
                    if (relativeLayout.getChildAt(i3) instanceof EditText) {
                        EditText editText = (EditText) relativeLayout.getChildAt(i3);
                        if (TextUtils.isEmpty(editText.getText())) {
                            continue;
                        } else {
                            if (editText.getText().toString().length() < 2 || editText.getText().toString().length() > 15) {
                                ToastUtil.shortToast(context, "选项" + (i2 + 1) + "长度不符合！");
                                return;
                            }
                            if (tag == null || !(tag instanceof CircleInfoBean.DataBean.OptionsBeanX.OptionsBean)) {
                                CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean = new CircleInfoBean.DataBean.OptionsBeanX.OptionsBean();
                                optionsBean.setId("0");
                                optionsBean.setOption(editText.getText().toString());
                                arrayList.add(optionsBean);
                            } else {
                                CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean2 = (CircleInfoBean.DataBean.OptionsBeanX.OptionsBean) tag;
                                optionsBean2.setOption(editText.getText().toString());
                                arrayList.add(optionsBean2);
                            }
                        }
                    }
                }
            }
        }
        OnChoosedLisenter onChoosedLisenter = this.chooseCircleLisenter;
        if (onChoosedLisenter != null) {
            onChoosedLisenter.onCircleChoosed(arrayList, this.isMore);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(View view) {
        addOptionView("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(View view) {
        dismiss();
    }

    private void mRefreshOptionView() {
        this.mLyAddView.setVisibility(0);
        int i2 = 0;
        while (i2 < this.mLyAddView.getChildCount()) {
            EditText editText = (EditText) this.mLyAddView.getChildAt(i2).findViewById(R.id.editId);
            StringBuilder sb = new StringBuilder();
            sb.append(" 选项");
            i2++;
            sb.append(i2);
            sb.append("(2-15个字)");
            editText.setHint(sb.toString());
        }
        if (this.mLyAddView.getChildCount() >= 20) {
            this.mLyAddOption.setVisibility(8);
        } else if (this.mLyAddView.getChildCount() <= 2) {
            for (int i3 = 0; i3 < this.mLyAddView.getChildCount(); i3++) {
                ((ImageView) this.mLyAddView.getChildAt(i3).findViewById(R.id.deleEdit)).setVisibility(4);
            }
        } else {
            for (int i4 = 0; i4 < this.mLyAddView.getChildCount(); i4++) {
                ((ImageView) this.mLyAddView.getChildAt(i4).findViewById(R.id.deleEdit)).setVisibility(0);
            }
        }
        this.mTvAddOption.setText(String.format(Locale.CHINA, "增加选项（剩余%d个选项）", Integer.valueOf(20 - this.mLyAddView.getChildCount())));
    }

    public void addOptionView(String content) {
        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this.context).inflate(R.layout.layout_circle_option_new, (ViewGroup) null);
        EditText editText = (EditText) relativeLayout.findViewById(R.id.editId);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.deleEdit);
        if (this.mLyAddView.getChildCount() < 2) {
            imageView.setVisibility(4);
        } else {
            imageView.setVisibility(0);
        }
        editText.setHint(" 选项" + this.index + "(2-15个字)");
        editText.setText(content);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.re
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16863c.lambda$addOptionView$8(relativeLayout, view);
            }
        });
        this.mLyAddView.addView(relativeLayout);
        this.index = this.index + 1;
        mRefreshOptionView();
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

    public void setOnChoosedLisenter(OnChoosedLisenter lisenter) {
        this.chooseCircleLisenter = lisenter;
    }

    public void addOptionView(String content, CircleInfoBean.DataBean.OptionsBeanX.OptionsBean bean) {
        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this.context).inflate(R.layout.layout_circle_option_new, (ViewGroup) null);
        relativeLayout.setTag(bean);
        EditText editText = (EditText) relativeLayout.findViewById(R.id.editId);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.deleEdit);
        if (this.mLyAddView.getChildCount() < 2) {
            imageView.setVisibility(4);
        } else {
            imageView.setVisibility(0);
        }
        editText.setHint(" 选项" + this.index + "(2-15个字)");
        editText.setText(content);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.qe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16828c.lambda$addOptionView$9(relativeLayout, view);
            }
        });
        this.mLyAddView.addView(relativeLayout);
        this.index = this.index + 1;
        mRefreshOptionView();
    }
}
