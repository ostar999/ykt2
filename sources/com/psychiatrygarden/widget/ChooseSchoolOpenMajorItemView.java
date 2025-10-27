package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.activity.chooseSchool.MajorBySchoolAct;
import com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil;
import com.psychiatrygarden.bean.EnrollmentData;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChooseSchoolOpenMajorItemView extends LinearLayout {
    private LinearLayout mLyItem;
    private TextView mTvName;
    private TextView mTvType;

    public ChooseSchoolOpenMajorItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_school_detail_major, this);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
        this.mTvType = (TextView) findViewById(R.id.tv_type);
        this.mLyItem = (LinearLayout) findViewById(R.id.ly_item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setData$0(EnrollmentData enrollmentData, String str, View view) {
        AliYunLogUtil.getInstance().addLog(AliyunEvent.PreviewMajor, enrollmentData.getMajor_id(), enrollmentData.getMajor_title());
        MajorBySchoolAct.newIntent(getContext(), str, enrollmentData.getMajor_id());
    }

    public void setData(final EnrollmentData data, final String schoolId) {
        Context context;
        int i2;
        Context context2;
        int i3;
        String major_code = data.getMajor_code();
        if (!TextUtils.isEmpty(data.getMajor_code())) {
            major_code = major_code + "-";
        }
        this.mTvName.setText(major_code + data.getMajor_title());
        if (TextUtils.isEmpty(data.getMajor_type())) {
            this.mTvType.setVisibility(8);
        } else {
            this.mTvType.setVisibility(0);
            if (data.getMajor_type().equals("1")) {
                this.mTvType.setText("专硕");
                TextView textView = this.mTvType;
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    context2 = getContext();
                    i3 = R.color.new_success_color_night;
                } else {
                    context2 = getContext();
                    i3 = R.color.new_success_color;
                }
                textView.setTextColor(context2.getColor(i3));
                this.mTvType.setBackgroundResource(R.drawable.shape_major_type_zhuan_bg);
            } else {
                this.mTvType.setText("学硕");
                TextView textView2 = this.mTvType;
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    context = getContext();
                    i2 = R.color.orange_color_night;
                } else {
                    context = getContext();
                    i2 = R.color.orange_color;
                }
                textView2.setTextColor(context.getColor(i2));
                this.mTvType.setBackgroundResource(R.drawable.shape_major_type_xue_bg);
            }
        }
        this.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.i2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16573c.lambda$setData$0(data, schoolId, view);
            }
        });
    }

    public ChooseSchoolOpenMajorItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChooseSchoolOpenMajorItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
