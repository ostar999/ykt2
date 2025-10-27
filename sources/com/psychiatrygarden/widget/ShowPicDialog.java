package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ShowPicDialog extends Dialog {
    public static final int TWO_BUTTON = 2;
    private boolean isOutTouchDismiss;
    private RelativeLayout relView;
    private View viewRoot;

    public interface ProjectChoosedInterface {
        void mItemLinsenter(boolean isComputer);
    }

    public ShowPicDialog(Context context, String path) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_show_pic, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        LinearLayout linearLayout = (LinearLayout) viewFindViewById.findViewById(R.id.view_dialog_root);
        final ImageView imageView = (ImageView) this.viewRoot.findViewById(R.id.img_pic);
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.lh
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16688c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        Glide.with(context).asBitmap().placeholder(R.drawable.plugin_camera_no_pictures).error(R.drawable.plugin_camera_no_pictures).load(path).into((RequestBuilder) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.widget.ShowPicDialog.1
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                imageView.setImageBitmap(resource);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.mh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16718c.lambda$new$1(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.nh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16742c.lambda$new$2(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(13);
        this.viewRoot.setLayoutParams(layoutParams2);
        this.viewRoot.setPivotY(0.0f);
        this.viewRoot.setPivotX(((getContext().getResources().getDisplayMetrics().widthPixels * 2.0f) / 3.0f) / 2.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    public void isOutTouchDismiss(boolean isOutTouchDismiss) {
        this.isOutTouchDismiss = isOutTouchDismiss;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(1024);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        getWindow().setAttributes(attributes);
    }
}
