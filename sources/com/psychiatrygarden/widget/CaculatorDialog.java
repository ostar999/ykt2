package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.bean.Formulautil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import org.slf4j.Marker;

/* loaded from: classes6.dex */
public class CaculatorDialog extends CenterPopupView {
    private boolean now;

    public CaculatorDialog(@NonNull Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "0");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$10(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$11(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + StrPool.DOT);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$12(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Marker.ANY_NON_NULL_MARKER);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$13(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText("0");
        textView2.setText("AC");
        this.now = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$14(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "-");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$15(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "*");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$16(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "/");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$17(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "(");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$18(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + ")");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$19(TextView textView, Formulautil formulautil, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "=" + String.valueOf(formulautil.caculate(textView.getText().toString())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "1");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "2");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "3");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$5(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "4");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$6(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "5");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$7(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Constants.VIA_SHARE_TYPE_INFO);
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$8(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + "7");
        textView2.setText("C");
        this.now = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$9(TextView textView, TextView textView2, View view) {
        if (textView.getText().toString().equals("0")) {
            textView.setText("");
        }
        textView.setText(((Object) textView.getText()) + Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        textView2.setText("C");
        this.now = false;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.view_calculator_dialog;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.add);
        TextView textView2 = (TextView) findViewById(R.id.substact);
        TextView textView3 = (TextView) findViewById(R.id.multiply);
        TextView textView4 = (TextView) findViewById(R.id.divide);
        TextView textView5 = (TextView) findViewById(R.id.seven);
        TextView textView6 = (TextView) findViewById(R.id.eight);
        TextView textView7 = (TextView) findViewById(R.id.nine);
        TextView textView8 = (TextView) findViewById(R.id.four);
        TextView textView9 = (TextView) findViewById(R.id.five);
        TextView textView10 = (TextView) findViewById(R.id.six);
        TextView textView11 = (TextView) findViewById(R.id.three);
        TextView textView12 = (TextView) findViewById(R.id.two);
        TextView textView13 = (TextView) findViewById(R.id.one);
        final TextView textView14 = (TextView) findViewById(R.id.clean);
        TextView textView15 = (TextView) findViewById(R.id.dot);
        TextView textView16 = (TextView) findViewById(R.id.zero);
        TextView textView17 = (TextView) findViewById(R.id.equal);
        TextView textView18 = (TextView) findViewById(R.id.btn_close);
        TextView textView19 = (TextView) findViewById(R.id.left);
        TextView textView20 = (TextView) findViewById(R.id.right);
        final TextView textView21 = (TextView) findViewById(R.id.tv_result);
        textView18.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16355c.lambda$onCreate$0(view);
            }
        });
        final Formulautil formulautil = new Formulautil();
        textView16.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16417c.lambda$onCreate$1(textView21, textView14, view);
            }
        });
        textView13.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16467c.lambda$onCreate$2(textView21, textView14, view);
            }
        });
        textView12.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16497c.lambda$onCreate$3(textView21, textView14, view);
            }
        });
        textView11.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16533c.lambda$onCreate$4(textView21, textView14, view);
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16569c.lambda$onCreate$5(textView21, textView14, view);
            }
        });
        textView9.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.j0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16601c.lambda$onCreate$6(textView21, textView14, view);
            }
        });
        textView10.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.k0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16631c.lambda$onCreate$7(textView21, textView14, view);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16660c.lambda$onCreate$8(textView21, textView14, view);
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.m0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16694c.lambda$onCreate$9(textView21, textView14, view);
            }
        });
        textView7.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.n0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16721c.lambda$onCreate$10(textView21, textView14, view);
            }
        });
        textView15.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.o0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16746c.lambda$onCreate$11(textView21, textView14, view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.p0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16773c.lambda$onCreate$12(textView21, textView14, view);
            }
        });
        textView14.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16806c.lambda$onCreate$13(textView21, textView14, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16839c.lambda$onCreate$14(textView21, textView14, view);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16876c.lambda$onCreate$15(textView21, textView14, view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.t0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16915c.lambda$onCreate$16(textView21, textView14, view);
            }
        });
        textView19.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.u0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16940c.lambda$onCreate$17(textView21, textView14, view);
            }
        });
        textView20.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16975c.lambda$onCreate$18(textView21, textView14, view);
            }
        });
        textView17.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CaculatorDialog.lambda$onCreate$19(textView21, formulautil, view);
            }
        });
    }
}
