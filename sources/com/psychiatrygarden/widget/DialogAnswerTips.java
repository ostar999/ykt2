package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DialogAnswerTips extends AlertDialog {
    private Context context;
    private Window window;

    public DialogAnswerTips(Context context) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(RelativeLayout relativeLayout, ImageView imageView, ImageView imageView2, ImageView imageView3, View view) {
        relativeLayout.setVisibility(8);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER, false, this.context);
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER1, true, this.context)) {
            imageView.setVisibility(0);
            imageView2.setVisibility(0);
            imageView3.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, View view) {
        imageView.setVisibility(8);
        imageView2.setVisibility(8);
        imageView3.setVisibility(8);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER1, false, this.context);
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER2, true, this.context)) {
            imageView4.setVisibility(0);
            imageView5.setVisibility(0);
            imageView6.setVisibility(0);
            imageView7.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, ImageView imageView8, View view) {
        imageView.setVisibility(8);
        imageView2.setVisibility(8);
        imageView3.setVisibility(8);
        imageView4.setVisibility(8);
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER2, false, this.context);
        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER3, true, this.context)) {
            imageView5.setVisibility(0);
            imageView6.setVisibility(0);
            imageView7.setVisibility(0);
            imageView8.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER3, false, this.context);
        imageView.setVisibility(8);
        imageView2.setVisibility(8);
        imageView3.setVisibility(8);
        imageView4.setVisibility(8);
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$4(ImageView imageView, ImageView imageView2, View view) {
        imageView.setVisibility(8);
        imageView2.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$5(ImageView imageView, ImageView imageView2, View view) {
        imageView.setVisibility(8);
        imageView2.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$6(ImageView imageView, ImageView imageView2, View view) {
        imageView.setVisibility(8);
        imageView2.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$7(View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER, false, this.context);
        dismiss();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        final DialogAnswerTips dialogAnswerTips;
        super.onCreate(savedInstanceState);
        if (SkinManager.getCurrentSkinType(this.context) == 0) {
            setContentView(R.layout.dialog_answer_tips);
            final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel_imgh);
            ImageView imageView = (ImageView) findViewById(R.id.tikuelo);
            final ImageView imageView2 = (ImageView) findViewById(R.id.im4);
            final ImageView imageView3 = (ImageView) findViewById(R.id.im3);
            final ImageView imageView4 = (ImageView) findViewById(R.id.imageView13);
            final ImageView imageView5 = (ImageView) findViewById(R.id.questiondetails_btn_zantong1);
            final ImageView imageView6 = (ImageView) findViewById(R.id.im1);
            final ImageView imageView7 = (ImageView) findViewById(R.id.im2);
            final ImageView imageView8 = (ImageView) findViewById(R.id.imageView12);
            final ImageView imageView9 = (ImageView) findViewById(R.id.questiondetails_btn_centerMsg1);
            final ImageView imageView10 = (ImageView) findViewById(R.id.questiondetails_btn_edit1);
            final ImageView imageView11 = (ImageView) findViewById(R.id.imageView11);
            final ImageView imageView12 = (ImageView) findViewById(R.id.im5);
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER, true, this.context)) {
                relativeLayout.setVisibility(0);
            } else {
                relativeLayout.setVisibility(8);
                if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER1, true, this.context)) {
                    imageView10.setVisibility(0);
                    imageView11.setVisibility(0);
                    imageView12.setVisibility(0);
                } else {
                    imageView10.setVisibility(8);
                    imageView11.setVisibility(8);
                    imageView12.setVisibility(8);
                    if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER2, true, this.context)) {
                        imageView6.setVisibility(0);
                        imageView7.setVisibility(0);
                        imageView8.setVisibility(0);
                        imageView9.setVisibility(0);
                    } else {
                        imageView6.setVisibility(8);
                        imageView7.setVisibility(8);
                        imageView8.setVisibility(8);
                        imageView9.setVisibility(8);
                        if (SharePreferencesUtils.readBooleanConfig(CommonParameter.IS_SHOW_FRIST_ANSWER3, true, this.context)) {
                            imageView2.setVisibility(0);
                            imageView3.setVisibility(0);
                            imageView4.setVisibility(0);
                            imageView5.setVisibility(0);
                        } else {
                            imageView2.setVisibility(8);
                            imageView3.setVisibility(8);
                            imageView4.setVisibility(8);
                            imageView5.setVisibility(8);
                        }
                    }
                }
            }
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16984c.lambda$onCreate$0(relativeLayout, imageView10, imageView11, imageView12, view);
                }
            });
            imageView12.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.w6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f17032c.lambda$onCreate$1(imageView10, imageView11, imageView12, imageView6, imageView7, imageView8, imageView9, view);
                }
            });
            imageView7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.x6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f17078c.lambda$onCreate$2(imageView6, imageView7, imageView8, imageView9, imageView2, imageView3, imageView4, imageView5, view);
                }
            });
            imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.y6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f17115c.lambda$onCreate$3(imageView2, imageView3, imageView4, imageView5, view);
                }
            });
            dialogAnswerTips = this;
        } else {
            dialogAnswerTips = this;
            dialogAnswerTips.setContentView(R.layout.ansview);
            RelativeLayout relativeLayout2 = (RelativeLayout) dialogAnswerTips.findViewById(R.id.rel_imgh);
            final ImageView imageView13 = (ImageView) dialogAnswerTips.findViewById(R.id.tikuelo);
            final ImageView imageView14 = (ImageView) dialogAnswerTips.findViewById(R.id.q11);
            final ImageView imageView15 = (ImageView) dialogAnswerTips.findViewById(R.id.q12);
            final ImageView imageView16 = (ImageView) dialogAnswerTips.findViewById(R.id.q13);
            relativeLayout2.setVisibility(0);
            imageView13.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.z6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DialogAnswerTips.lambda$onCreate$4(imageView13, imageView14, view);
                }
            });
            imageView14.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.a7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DialogAnswerTips.lambda$onCreate$5(imageView14, imageView15, view);
                }
            });
            imageView15.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.b7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DialogAnswerTips.lambda$onCreate$6(imageView15, imageView16, view);
                }
            });
            imageView16.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.c7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16363c.lambda$onCreate$7(view);
                }
            });
        }
        dialogAnswerTips.window = getWindow();
        dialogAnswerTips.setCanceledOnTouchOutside(false);
        dialogAnswerTips.window.setLayout(CommonUtil.getScreenWidth(dialogAnswerTips.context), -1);
    }
}
