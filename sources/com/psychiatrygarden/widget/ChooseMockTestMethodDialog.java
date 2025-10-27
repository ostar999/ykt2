package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChooseMockTestMethodDialog extends Dialog {
    public static final int TWO_BUTTON = 2;
    private boolean isOutTouchDismiss;
    private RelativeLayout relView;
    private View viewRoot;

    public interface ProjectChoosedInterface {
        void mItemLinsenter(boolean isComputer);
    }

    public ChooseMockTestMethodDialog(Context context, final ProjectChoosedInterface itemChooseLisenter) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_mock_type_view, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        LinearLayout linearLayout = (LinearLayout) viewFindViewById.findViewById(R.id.view_dialog_root);
        final RelativeLayout relativeLayout = (RelativeLayout) this.viewRoot.findViewById(R.id.ly_computer);
        final RelativeLayout relativeLayout2 = (RelativeLayout) this.viewRoot.findViewById(R.id.ly_single);
        final TextView textView = (TextView) this.viewRoot.findViewById(R.id.tv_mode_computer);
        final TextView textView2 = (TextView) this.viewRoot.findViewById(R.id.tv_mode_computer_info);
        final TextView textView3 = (TextView) this.viewRoot.findViewById(R.id.tv_mode_single);
        final TextView textView4 = (TextView) this.viewRoot.findViewById(R.id.tv_mode_single_info);
        final ImageView imageView = (ImageView) this.viewRoot.findViewById(R.id.img_computer);
        final ImageView imageView2 = (ImageView) this.viewRoot.findViewById(R.id.img_single);
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.v1
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16978c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.w1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17017c.lambda$new$1(relativeLayout, textView, textView2, relativeLayout2, textView3, textView4, imageView, imageView2, itemChooseLisenter, view);
            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.x1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17062c.lambda$new$2(relativeLayout, textView, textView2, relativeLayout2, textView3, textView4, imageView, imageView2, itemChooseLisenter, view);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.y1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17111c.lambda$new$3(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.z1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17139c.lambda$new$4(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(12);
        this.viewRoot.setLayoutParams(layoutParams2);
        this.viewRoot.setPivotY(0.0f);
        this.viewRoot.setPivotX(((getContext().getResources().getDisplayMetrics().widthPixels * 2.0f) / 3.0f) / 2.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(RelativeLayout relativeLayout, TextView textView, TextView textView2, RelativeLayout relativeLayout2, TextView textView3, TextView textView4, ImageView imageView, ImageView imageView2, ProjectChoosedInterface projectChoosedInterface, View view) {
        relativeLayout.setBackgroundResource(R.drawable.shape_mock_type_red_bg);
        textView.setSelected(true);
        textView2.setSelected(true);
        relativeLayout2.setBackgroundResource(R.drawable.shape_mock_type_gray_bg);
        textView3.setSelected(false);
        textView4.setSelected(false);
        imageView.setSelected(true);
        imageView2.setSelected(false);
        dismiss();
        projectChoosedInterface.mItemLinsenter(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(RelativeLayout relativeLayout, TextView textView, TextView textView2, RelativeLayout relativeLayout2, TextView textView3, TextView textView4, ImageView imageView, ImageView imageView2, ProjectChoosedInterface projectChoosedInterface, View view) {
        relativeLayout.setBackgroundResource(R.drawable.shape_mock_type_gray_bg);
        textView.setSelected(false);
        textView2.setSelected(false);
        relativeLayout2.setBackgroundResource(R.drawable.shape_mock_type_red_bg);
        textView3.setSelected(true);
        textView4.setSelected(true);
        imageView.setSelected(false);
        imageView2.setSelected(true);
        dismiss();
        projectChoosedInterface.mItemLinsenter(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    public void isOutTouchDismiss(boolean isOutTouchDismiss) {
        this.isOutTouchDismiss = isOutTouchDismiss;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(1024);
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        getWindow().setAttributes(attributes);
    }
}
