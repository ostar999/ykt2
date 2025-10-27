package com.psychiatrygarden.activity.mine.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.setting.CaculatorAct;
import com.psychiatrygarden.bean.Formulautil;
import com.psychiatrygarden.utils.ScreenUtil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import org.slf4j.Marker;

/* loaded from: classes5.dex */
public class CaculatorAct extends BaseActivity {
    private AlertDialog dialog;
    private boolean now;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(DialogInterface dialogInterface) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        this.dialog.dismiss();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$10(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$11(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$12(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + StrPool.DOT);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$13(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Marker.ANY_NON_NULL_MARKER);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$14(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText("0");
        textView2.setText("AC");
        this.now = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$15(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "-");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$16(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "*");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$17(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "/");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$18(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "(");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$19(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + ")");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "0");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$20(TextView textView, Formulautil formulautil, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "=" + String.valueOf(formulautil.caculate(textView.getText().toString())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "1");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "2");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$5(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "3");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$6(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "4");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$7(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "5");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$8(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Constants.VIA_SHARE_TYPE_INFO);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$9(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "7");
        textView2.setText("C");
        this.now = false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_calculator_dialog, (ViewGroup) null);
        this.dialog = new AlertDialog.Builder(this.mContext, R.style.MyDialog).create();
        TextView textView = (TextView) linearLayout.findViewById(R.id.add);
        TextView textView2 = (TextView) linearLayout.findViewById(R.id.substact);
        TextView textView3 = (TextView) linearLayout.findViewById(R.id.multiply);
        TextView textView4 = (TextView) linearLayout.findViewById(R.id.divide);
        TextView textView5 = (TextView) linearLayout.findViewById(R.id.seven);
        TextView textView6 = (TextView) linearLayout.findViewById(R.id.eight);
        TextView textView7 = (TextView) linearLayout.findViewById(R.id.nine);
        TextView textView8 = (TextView) linearLayout.findViewById(R.id.four);
        TextView textView9 = (TextView) linearLayout.findViewById(R.id.five);
        TextView textView10 = (TextView) linearLayout.findViewById(R.id.six);
        TextView textView11 = (TextView) linearLayout.findViewById(R.id.three);
        TextView textView12 = (TextView) linearLayout.findViewById(R.id.two);
        TextView textView13 = (TextView) linearLayout.findViewById(R.id.one);
        final TextView textView14 = (TextView) linearLayout.findViewById(R.id.clean);
        TextView textView15 = (TextView) linearLayout.findViewById(R.id.dot);
        TextView textView16 = (TextView) linearLayout.findViewById(R.id.zero);
        TextView textView17 = (TextView) linearLayout.findViewById(R.id.equal);
        TextView textView18 = (TextView) linearLayout.findViewById(R.id.btn_close);
        TextView textView19 = (TextView) linearLayout.findViewById(R.id.left);
        TextView textView20 = (TextView) linearLayout.findViewById(R.id.right);
        final TextView textView21 = (TextView) linearLayout.findViewById(R.id.tv_result);
        this.dialog.getWindow().setGravity(17);
        this.dialog.setCancelable(true);
        this.dialog.setCanceledOnTouchOutside(true);
        this.dialog.show();
        WindowManager.LayoutParams attributes = this.dialog.getWindow().getAttributes();
        attributes.width = ScreenUtil.getPxByDp(this.mContext, 199);
        this.dialog.getWindow().setAttributes(attributes);
        this.dialog.getWindow().setContentView(linearLayout);
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: m1.a
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f27674c.lambda$init$0(dialogInterface);
            }
        });
        textView18.setOnClickListener(new View.OnClickListener() { // from class: m1.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27679c.lambda$init$1(view);
            }
        });
        final Formulautil formulautil = new Formulautil();
        textView16.setOnClickListener(new View.OnClickListener() { // from class: m1.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27682c.lambda$init$2(textView21, textView14, view);
            }
        });
        textView13.setOnClickListener(new View.OnClickListener() { // from class: m1.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27685c.lambda$init$3(textView21, textView14, view);
            }
        });
        textView12.setOnClickListener(new View.OnClickListener() { // from class: m1.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27688c.lambda$init$4(textView21, textView14, view);
            }
        });
        textView11.setOnClickListener(new View.OnClickListener() { // from class: m1.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27691c.lambda$init$5(textView21, textView14, view);
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() { // from class: m1.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27694c.lambda$init$6(textView21, textView14, view);
            }
        });
        textView9.setOnClickListener(new View.OnClickListener() { // from class: m1.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27697c.lambda$init$7(textView21, textView14, view);
            }
        });
        textView10.setOnClickListener(new View.OnClickListener() { // from class: m1.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27700c.lambda$init$8(textView21, textView14, view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: m1.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27706c.lambda$init$9(textView21, textView14, view);
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() { // from class: m1.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27703c.lambda$init$10(textView21, textView14, view);
            }
        });
        textView7.setOnClickListener(new View.OnClickListener() { // from class: m1.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27709c.lambda$init$11(textView21, textView14, view);
            }
        });
        textView15.setOnClickListener(new View.OnClickListener() { // from class: m1.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27712c.lambda$init$12(textView21, textView14, view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: m1.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27715c.lambda$init$13(textView21, textView14, view);
            }
        });
        textView14.setOnClickListener(new View.OnClickListener() { // from class: m1.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27718c.lambda$init$14(textView21, textView14, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: m1.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27721c.lambda$init$15(textView21, textView14, view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: m1.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27724c.lambda$init$16(textView21, textView14, view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: m1.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27727c.lambda$init$17(textView21, textView14, view);
            }
        });
        textView19.setOnClickListener(new View.OnClickListener() { // from class: m1.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27730c.lambda$init$18(textView21, textView14, view);
            }
        });
        textView20.setOnClickListener(new View.OnClickListener() { // from class: m1.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27676c.lambda$init$19(textView21, textView14, view);
            }
        });
        textView17.setOnClickListener(new View.OnClickListener() { // from class: m1.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CaculatorAct.lambda$init$20(textView21, formulautil, view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == 4;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setSwipeBackEnable(false);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
