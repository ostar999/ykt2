package com.psychiatrygarden.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditZuTiActivity;
import com.uuzuche.lib_zxing.DisplayUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgeQuestionRedoDialog extends Dialog {
    public static final int TWO_BUTTON = 2;
    private boolean isOutTouchDismiss;
    private TextView mBtnAllRedo;
    private TextView mBtnCancel;
    private RelativeLayout relView;
    private TextView tvRedoMoreQuestion;
    private View viewRoot;

    public KnowledgeQuestionRedoDialog(Context context, boolean isCombine, String title, String btnTitle) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_redo_question_view_knowledge, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        this.tvRedoMoreQuestion = (TextView) viewFindViewById.findViewById(R.id.btn_redo_other_chapter);
        this.mBtnCancel = (TextView) this.viewRoot.findViewById(R.id.btn_cancel);
        this.mBtnAllRedo = (TextView) this.viewRoot.findViewById(R.id.btn_all_redo);
        ((TextView) this.viewRoot.findViewById(R.id.title)).setText(title);
        this.mBtnAllRedo.setText(btnTitle);
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getContext().getResources().getDisplayMetrics().widthPixels - DisplayUtil.dip2px(context, 80.0f), -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.qa
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16820c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        this.viewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ra
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KnowledgeQuestionRedoDialog.lambda$new$1(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.sa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16898c.lambda$new$2(view);
            }
        });
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ta
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16930c.lambda$new$3(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.viewRoot, Key.ROTATION, -3.0f, 3.5f, -1.5f, 0.0f).setDuration(500L));
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(13);
        this.viewRoot.setLayoutParams(layoutParams2);
        this.viewRoot.setPivotY(0.0f);
        this.viewRoot.setPivotX(((getContext().getResources().getDisplayMetrics().widthPixels * 2.0f) / 3.0f) / 2.0f);
        animatorSet.start();
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
    public /* synthetic */ void lambda$new$3(View view) {
        dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setShowDoMore$4(String str, String str2, View view) {
        Intent intent = new Intent(this.tvRedoMoreQuestion.getContext(), (Class<?>) KnowledgeListEditZuTiActivity.class);
        intent.putExtra("type", str);
        intent.putExtra("treeId", str2);
        view.getContext().startActivity(intent);
        dismiss();
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
        attributes.width = -2;
        getWindow().setAttributes(attributes);
    }

    public void setButtonLisenter(View.OnClickListener allRedoLisenter) {
        this.mBtnAllRedo.setOnClickListener(allRedoLisenter);
    }

    public void setShowDoMore(final String treeId, final String type) {
        this.tvRedoMoreQuestion.setVisibility(0);
        this.tvRedoMoreQuestion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ua
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16954c.lambda$setShowDoMore$4(type, treeId, view);
            }
        });
    }
}
