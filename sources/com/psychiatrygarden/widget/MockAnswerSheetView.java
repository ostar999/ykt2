package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.activity.online.adapter.ComputerAnswerSheetAdp;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class MockAnswerSheetView extends LinearLayout {
    private int currentPosition;
    private boolean isSpecialType;
    private ComputerAnswerSheetAdp mAdapter;
    private int mCurrentStep;
    private ImageView mImgArrow;
    private RecyclerView mRecyclerView;
    private TextView mTvPartName;
    private OnSheetItemClickLisenter onSheetItemClickLisenter;

    public static abstract class OnSheetItemClickLisenter {
        public abstract void itemSheetClick(int pos, int currentStep, ExesQuestionBean item, boolean isSpecial);
    }

    public MockAnswerSheetView(Context context) {
        this(context, null);
    }

    private void init() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.view_mock_left_answer_sheet, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        this.mRecyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_sheet);
        this.mTvPartName = (TextView) viewInflate.findViewById(R.id.tv_part_name);
        this.mImgArrow = (ImageView) viewInflate.findViewById(R.id.img_arrow);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ly_title);
        this.mRecyclerView.setLayoutManager(gridLayoutManager);
        this.mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        ComputerAnswerSheetAdp computerAnswerSheetAdp = new ComputerAnswerSheetAdp();
        this.mAdapter = computerAnswerSheetAdp;
        this.mRecyclerView.setAdapter(computerAnswerSheetAdp);
        this.mAdapter.setOnItemClickLisenter(new ComputerAnswerSheetAdp.OnItemClickLisenter() { // from class: com.psychiatrygarden.widget.MockAnswerSheetView.1
            @Override // com.psychiatrygarden.activity.online.adapter.ComputerAnswerSheetAdp.OnItemClickLisenter
            public void itemClick(int pos, ExesQuestionBean item) {
                if (MockAnswerSheetView.this.onSheetItemClickLisenter != null) {
                    MockAnswerSheetView.this.onSheetItemClickLisenter.itemSheetClick(pos, MockAnswerSheetView.this.mCurrentStep, item, MockAnswerSheetView.this.isSpecialType);
                }
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xa
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17092c.lambda$init$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (this.mRecyclerView.getVisibility() == 0) {
            this.mRecyclerView.setVisibility(8);
            this.mImgArrow.setRotation(90.0f);
        } else {
            this.mRecyclerView.setVisibility(0);
            this.mImgArrow.setRotation(270.0f);
        }
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public List<ExesQuestionBean> getDatas() {
        return this.mAdapter.getData();
    }

    public ExesQuestionBean getItemBean(int currentPosition) {
        return this.mAdapter.getItem(currentPosition);
    }

    public boolean isSpecialType() {
        return this.isSpecialType;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setData(List<ExesQuestionBean> list, int currentStep, int currentPosition, boolean isExpand) {
        this.mCurrentStep = currentStep;
        this.mAdapter.setCurrentPosition(currentPosition);
        this.mAdapter.setList(list);
        String type = list.get(0).getType();
        if (type.equals(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO) || type.equals(Constants.VIA_REPORT_TYPE_JOININ_GROUP) || type.equals(Constants.VIA_REPORT_TYPE_MAKE_FRIEND) || type.equals(Constants.VIA_REPORT_TYPE_WPA_STATE) || type.equals("16") || type.equals(Constants.VIA_ACT_TYPE_NINETEEN)) {
            this.isSpecialType = true;
        } else {
            this.isSpecialType = false;
        }
        this.mTvPartName.setText("第" + CommonUtil.convertNumbersToUpperCase(list.get(0).getPart()) + "部分：" + list.get(0).getPart_title());
        this.mRecyclerView.setVisibility(0);
        this.mImgArrow.setRotation(270.0f);
    }

    public void setOnSheetItemClickLisenter(OnSheetItemClickLisenter lisenter) {
        this.onSheetItemClickLisenter = lisenter;
    }

    public void updateAdp(int position) {
        this.mAdapter.setCurrentPosition(position);
        this.mAdapter.notifyDataSetChanged();
    }

    public void updateCurrentUi(boolean isProv) {
        if (isProv) {
            this.mAdapter.setCurrentPosition(-1);
            this.mAdapter.notifyDataSetChanged();
            return;
        }
        this.currentPosition = 0;
        this.mRecyclerView.setVisibility(0);
        this.mImgArrow.setRotation(270.0f);
        this.mAdapter.setCurrentPosition(0);
        this.mAdapter.notifyDataSetChanged();
    }

    public MockAnswerSheetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MockAnswerSheetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.currentPosition = 0;
        this.mCurrentStep = 0;
        this.isSpecialType = false;
        init();
    }
}
