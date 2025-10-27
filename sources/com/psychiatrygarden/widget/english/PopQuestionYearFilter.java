package com.psychiatrygarden.widget.english;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.adapter.QuestionYearFilterAdapter;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.TriangleView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PopQuestionYearFilter extends BottomPopupView implements View.OnClickListener, OnItemChildClickListener {
    private String endYear;
    private QuestionListBean.DataDTO.SearchDTO.SearchDataDTO items;
    private ImageView iv_year_close;
    private LinearLayout ll_year_hint;
    private OnClickBtnListener onClickBtnListener;
    private QuestionYearFilterAdapter questionYearFilterAdapter;
    private RecyclerView recyclerView;
    private RelativeLayout rl_mian;
    private String startYear;
    private TriangleView triangle_pop;
    private TextView tv_year_ok;

    public interface OnClickBtnListener {
        void onChooseYear(QuestionListBean.DataDTO.SearchDTO.SearchDataDTO items);
    }

    public PopQuestionYearFilter(@NonNull Context context, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO items, OnClickBtnListener onClickBtnListener) {
        super(context);
        this.startYear = "";
        this.endYear = "";
        this.items = items;
        try {
            if (!TextUtils.isEmpty(items.getYearTitle())) {
                String[] strArrSplit = items.getYearTitle().split("-");
                this.startYear = strArrSplit[0];
                this.endYear = strArrSplit[1];
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.onClickBtnListener = onClickBtnListener;
    }

    private void initRv() {
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));
        QuestionYearFilterAdapter questionYearFilterAdapter = new QuestionYearFilterAdapter(R.layout.item_question_year_filter, this.items.getList());
        this.questionYearFilterAdapter = questionYearFilterAdapter;
        questionYearFilterAdapter.setSelectYear(this.startYear, this.endYear);
        this.questionYearFilterAdapter.addChildClickViewIds(R.id.tv_year);
        this.questionYearFilterAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.widget.english.k
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f16461c.onItemChildClick(baseQuickAdapter, view, i2);
            }
        });
        this.recyclerView.setAdapter(this.questionYearFilterAdapter);
    }

    public static TranslateAnimation moveToViewTop() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
        translateAnimation.setDuration(500L);
        return translateAnimation;
    }

    public static TranslateAnimation moveTopToViewLocation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        translateAnimation.setDuration(500L);
        return translateAnimation;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        super.doAfterDismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_question_year_filter_bottom;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.iv_year_close) {
            dismiss();
            return;
        }
        if (id != R.id.tv_year_ok) {
            return;
        }
        if (TextUtils.isEmpty(this.startYear) || TextUtils.isEmpty(this.endYear)) {
            ToastUtil.shortToast(getContext(), this.tv_year_ok.getText().toString());
            return;
        }
        dismiss();
        this.items.setYearTitle(this.startYear + "-" + this.endYear);
        this.onClickBtnListener.onChooseYear(this.items);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.rl_mian = (RelativeLayout) findViewById(R.id.rl_mian);
        this.iv_year_close = (ImageView) findViewById(R.id.iv_year_close);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.tv_year_ok = (TextView) findViewById(R.id.tv_year_ok);
        this.ll_year_hint = (LinearLayout) findViewById(R.id.ll_year_hint);
        this.triangle_pop = (TriangleView) findViewById(R.id.triangle_pop);
        initRv();
        this.iv_year_close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.english.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16462c.onClick(view);
            }
        });
        this.tv_year_ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.english.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16462c.onClick(view);
            }
        });
        if (TextUtils.isEmpty(this.startYear) || TextUtils.isEmpty(this.endYear)) {
            this.tv_year_ok.setBackgroundResource(SkinManager.getCurrentSkinType(getContext()) == 0 ? R.drawable.ffdedede_6 : R.drawable.ffdedede_6_night);
        } else {
            this.tv_year_ok.setText("确定");
            this.tv_year_ok.setBackgroundResource(R.drawable.ffcd6151_6);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
    }

    @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        float f2;
        float width;
        try {
            TextView textView = (TextView) view.findViewById(R.id.tv_year);
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            textView.getLocationInWindow(iArr);
            this.rl_mian.getLocationInWindow(iArr2);
            int screenWidth = ScreenUtil.getScreenWidth((Activity) getContext());
            int width2 = this.ll_year_hint.getWidth();
            int height = this.ll_year_hint.getHeight();
            int i2 = 0;
            int i3 = iArr[0];
            if (i3 + width2 > screenWidth) {
                f2 = screenWidth - width2;
                width = (((i3 + width2) - screenWidth) + (textView.getWidth() / 2)) - (this.triangle_pop.getWidth() / 2);
            } else {
                f2 = i3;
                width = (textView.getWidth() / 2) - (this.triangle_pop.getWidth() / 2);
            }
            float f3 = (iArr[1] - iArr2[1]) - height;
            boolean zIsEmpty = TextUtils.isEmpty(this.startYear);
            int i4 = R.drawable.ffdedede_6;
            if (zIsEmpty && TextUtils.isEmpty(this.endYear)) {
                this.startYear = this.items.getList().get(position);
                this.tv_year_ok.setText("选择截止年份");
                TextView textView2 = this.tv_year_ok;
                if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                    i4 = R.drawable.ffdedede_6_night;
                }
                textView2.setBackgroundResource(i4);
                this.triangle_pop.setX(width);
                this.ll_year_hint.setX(f2);
                this.ll_year_hint.setY(f3);
                this.triangle_pop.setAnimation(moveTopToViewLocation());
                this.ll_year_hint.setVisibility(0);
                this.ll_year_hint.setAnimation(moveTopToViewLocation());
            } else if (TextUtils.isEmpty(this.startYear) || !TextUtils.isEmpty(this.endYear)) {
                this.startYear = this.items.getList().get(position);
                this.endYear = "";
                this.tv_year_ok.setText("选择截止年份");
                TextView textView3 = this.tv_year_ok;
                if (SkinManager.getCurrentSkinType(getContext()) != 0) {
                    i4 = R.drawable.ffdedede_6_night;
                }
                textView3.setBackgroundResource(i4);
                this.triangle_pop.setX(width);
                this.ll_year_hint.setX(f2);
                this.ll_year_hint.setY(f3);
                this.ll_year_hint.setVisibility(0);
                this.ll_year_hint.setAnimation(moveTopToViewLocation());
            } else {
                int i5 = 0;
                while (true) {
                    if (i5 >= this.items.getList().size()) {
                        break;
                    }
                    if (this.startYear.equals(this.items.getList().get(i5))) {
                        i2 = i5;
                        break;
                    }
                    i5++;
                }
                if (i2 <= position) {
                    this.endYear = this.items.getList().get(position);
                } else {
                    this.endYear = this.startYear;
                    this.startYear = this.items.getList().get(position);
                }
                this.tv_year_ok.setText("确定");
                this.tv_year_ok.setBackgroundResource(R.drawable.ffcd6151_6);
                this.ll_year_hint.setVisibility(8);
                this.ll_year_hint.setAnimation(moveToViewTop());
            }
            this.questionYearFilterAdapter.setSelectYear(this.startYear, this.endYear);
            this.questionYearFilterAdapter.notifyDataSetChanged();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
    }
}
