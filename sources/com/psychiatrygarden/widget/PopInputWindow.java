package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.interfaceclass.OnMInputListenster;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;
import java.text.SimpleDateFormat;

/* loaded from: classes6.dex */
public class PopInputWindow extends PopupWindow implements View.OnClickListener {
    private Context context;
    private EditText inputedit;
    private TextView inputtxt;
    private OnMInputListenster mOnMInputListenster;
    private TextView queding;
    private TextView quxiao;
    private RelativeLayout rel_view;
    private int titlestr;
    private String txtstr;
    private View view;

    public PopInputWindow(Context context, String txtstr, int titlestr, OnMInputListenster mOnMInputListenster) {
        super(context);
        this.context = context;
        this.txtstr = txtstr;
        this.titlestr = titlestr;
        this.mOnMInputListenster = mOnMInputListenster;
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_inputwindow, (ViewGroup) null);
        this.view = viewInflate;
        setContentView(viewInflate);
        this.view.measure(0, 0);
        setWidth(-1);
        setHeight(-1);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        setBackgroundDrawable(context.getResources().getDrawable(R.color.transparent));
        this.rel_view = (RelativeLayout) this.view.findViewById(R.id.rel_view);
        this.quxiao = (TextView) this.view.findViewById(R.id.quxiao);
        this.queding = (TextView) this.view.findViewById(R.id.queding);
        this.inputedit = (EditText) this.view.findViewById(R.id.inputedit);
        this.inputtxt = (TextView) this.view.findViewById(R.id.inputtxt);
        try {
            if (titlestr == 1) {
                if (TextUtils.isEmpty(txtstr)) {
                    this.inputedit.setText(CommonUtil.getDate((System.currentTimeMillis() / 1000) + ""));
                } else {
                    this.inputedit.setText(CommonUtil.getDate(txtstr));
                }
                this.inputedit.setHint("格式必须为：yyyy-MM-dd");
                this.inputtxt.setText("修改时间");
            } else if (titlestr == 2) {
                this.inputedit.setText(txtstr);
                this.inputtxt.setText("修改赞同数");
            } else if (titlestr == 3) {
                this.inputedit.setText(txtstr);
                this.inputtxt.setText("修改反对数");
            }
        } catch (Exception unused) {
            NewToast.showShort(context, "数据信息有误，可能不能编辑成功,请联系工作人员", 0).show();
        }
        this.quxiao.setOnClickListener(this);
        this.queding.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NewApi"})
    public void onClick(View v2) {
        int id = v2.getId();
        if (id != R.id.queding) {
            if (id != R.id.quxiao) {
                return;
            }
            dismiss();
            return;
        }
        if (!TextUtils.isEmpty(this.inputedit.getText().toString())) {
            if (this.titlestr != 1) {
                this.mOnMInputListenster.mInputDataListter(this.inputedit.getText().toString());
            } else {
                try {
                    long time = new SimpleDateFormat("yyyy-MM-dd").parse(this.inputedit.getText().toString()).getTime() / 1000;
                    this.mOnMInputListenster.mInputDataListter(time + "");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            dismiss();
            return;
        }
        this.inputtxt.setText("修改内容不能为空");
        try {
            int i2 = this.titlestr;
            if (i2 != 1) {
                if (i2 == 2 || i2 == 3) {
                    this.inputedit.setText(this.txtstr);
                }
            } else if (TextUtils.isEmpty(this.txtstr)) {
                this.inputedit.setText(CommonUtil.getDate((System.currentTimeMillis() / 1000) + ""));
            } else {
                this.inputedit.setText(CommonUtil.getDate(this.txtstr));
            }
        } catch (Exception unused) {
            NewToast.showShort(this.context, "数据信息有误，可能不能编辑成功,请联系工作人员", 0).show();
        }
        this.inputtxt.setTextColor(this.context.getResources().getColor(R.color.app_theme_red));
    }

    public void showPopUp(View view) {
        showAtLocation(view, 17, 0, 0);
    }
}
