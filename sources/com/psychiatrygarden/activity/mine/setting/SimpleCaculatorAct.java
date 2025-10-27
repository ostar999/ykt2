package com.psychiatrygarden.activity.mine.setting;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.Formulautil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import org.slf4j.Marker;

/* loaded from: classes5.dex */
public class SimpleCaculatorAct extends BaseActivity implements View.OnClickListener {
    private Button Clean;
    private Button add;
    private Button divide;
    private Button dot;
    private EditText edit;
    private Button eight;
    private Button equal;
    private Button five;
    private Button four;
    private GridLayout gridlayout;
    private String lastresult;
    private Button left;
    private Button multiply;
    private Button nine;
    private boolean now;
    private Button one;
    private Button right;
    private Button seven;
    private Button six;
    private Button substact;
    private Button three;
    private Button two;
    private TextView view;
    private Button zero;

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.gridlayout = (GridLayout) findViewById(R.id.gridlayout);
        this.edit = (EditText) findViewById(R.id.edit);
        this.view = (TextView) findViewById(R.id.view);
        this.add = (Button) findViewById(R.id.add);
        this.substact = (Button) findViewById(R.id.substact);
        this.multiply = (Button) findViewById(R.id.multiply);
        this.divide = (Button) findViewById(R.id.divide);
        this.seven = (Button) findViewById(R.id.seven);
        this.eight = (Button) findViewById(R.id.eight);
        this.nine = (Button) findViewById(R.id.nine);
        this.left = (Button) findViewById(R.id.left);
        this.four = (Button) findViewById(R.id.four);
        this.five = (Button) findViewById(R.id.five);
        this.six = (Button) findViewById(R.id.six);
        this.right = (Button) findViewById(R.id.right);
        this.three = (Button) findViewById(R.id.three);
        this.two = (Button) findViewById(R.id.two);
        this.one = (Button) findViewById(R.id.one);
        this.Clean = (Button) findViewById(R.id.Clean);
        this.dot = (Button) findViewById(R.id.dot);
        this.zero = (Button) findViewById(R.id.zero);
        this.equal = (Button) findViewById(R.id.equal);
        this.edit.setOnClickListener(this);
        this.view.setOnClickListener(this);
        this.add.setOnClickListener(this);
        this.multiply.setOnClickListener(this);
        this.divide.setOnClickListener(this);
        this.dot.setOnClickListener(this);
        this.one.setOnClickListener(this);
        this.two.setOnClickListener(this);
        this.three.setOnClickListener(this);
        this.four.setOnClickListener(this);
        this.five.setOnClickListener(this);
        this.six.setOnClickListener(this);
        this.seven.setOnClickListener(this);
        this.eight.setOnClickListener(this);
        this.nine.setOnClickListener(this);
        this.zero.setOnClickListener(this);
        this.right.setOnClickListener(this);
        this.Clean.setOnClickListener(this);
        this.left.setOnClickListener(this);
        this.equal.setOnClickListener(this);
        this.substact.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        String string = this.edit.getText().toString();
        Formulautil formulautil = new Formulautil();
        if (string.equals("0")) {
            this.edit.setText("");
        }
        switch (v2.getId()) {
            case R.id.Clean /* 2131361805 */:
                this.edit.setText("0");
                this.Clean.setText("AC");
                this.now = true;
                break;
            case R.id.add /* 2131361967 */:
                this.edit.setText(((Object) this.edit.getText()) + Marker.ANY_NON_NULL_MARKER);
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.divide /* 2131362941 */:
                this.edit.setText(((Object) this.edit.getText()) + "÷");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.dot /* 2131362944 */:
                this.edit.setText(((Object) this.edit.getText()) + StrPool.DOT);
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.eight /* 2131363034 */:
                this.edit.setText(((Object) this.edit.getText()) + Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.equal /* 2131363084 */:
                this.edit.setText(((Object) this.edit.getText()) + "=");
                this.view.setText(String.valueOf(formulautil.caculate(this.edit.getText().toString())));
                break;
            case R.id.five /* 2131363246 */:
                this.edit.setText(((Object) this.edit.getText()) + "5");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.four /* 2131363322 */:
                this.edit.setText(((Object) this.edit.getText()) + "4");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.left /* 2131364492 */:
                this.edit.setText(((Object) this.edit.getText()) + "(");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.multiply /* 2131365522 */:
                this.edit.setText(((Object) this.edit.getText()) + "×");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.nine /* 2131365599 */:
                this.edit.setText(((Object) this.edit.getText()) + Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.one /* 2131365665 */:
                this.edit.setText(((Object) this.edit.getText()) + "1");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.right /* 2131366455 */:
                this.edit.setText(((Object) this.edit.getText()) + ")");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.seven /* 2131366889 */:
                this.edit.setText(((Object) this.edit.getText()) + "7");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.six /* 2131366967 */:
                this.edit.setText(((Object) this.edit.getText()) + Constants.VIA_SHARE_TYPE_INFO);
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.substact /* 2131367096 */:
                this.edit.setText(((Object) this.edit.getText()) + "-");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.three /* 2131367264 */:
                this.edit.setText(((Object) this.edit.getText()) + "3");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.two /* 2131368823 */:
                this.edit.setText(((Object) this.edit.getText()) + "2");
                this.Clean.setText("C");
                this.now = false;
                break;
            case R.id.zero /* 2131369251 */:
                this.edit.setText(((Object) this.edit.getText()) + "0");
                this.Clean.setText("C");
                this.now = false;
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_calculator);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
