package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.circleactivity.PushCircleAdp;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes6.dex */
public class PushCircleDialog extends Dialog {
    public static final int TWO_BUTTON = 2;
    private OnChoosedLisenter chooseCircleLisenter;
    private Context context;
    private List<CirclrListBean.DataBean> data;
    private boolean hasEmptyView;
    private boolean isOutTouchDismiss;
    private RelativeLayout lyBottom;
    private PushCircleAdp mAdapter;
    private List<String> mChooseCircleIds;
    private List<CirclrListBean.DataBean> mChooseCircleItems;
    private ClearEditText mEtContent;
    private List<String> mLocalSaveIds;
    private LinearLayout mLyProgress;
    private TextView mTvCount;
    private int page;
    private RelativeLayout relView;
    private View viewRoot;

    public static abstract class OnChoosedLisenter {
        public abstract void onCircleChoosed(List<String> oldCircleIds, List<String> newCircleIds, List<CirclrListBean.DataBean> mChooseCircleItems);
    }

    public PushCircleDialog(final Context context, List<CirclrListBean.DataBean> chooseList) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.hasEmptyView = false;
        this.page = 1;
        this.mChooseCircleIds = new ArrayList();
        this.mChooseCircleItems = new ArrayList();
        this.mLocalSaveIds = new ArrayList();
        this.data = new ArrayList();
        this.context = context;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_push_circle_view, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        TextView textView = (TextView) viewFindViewById.findViewById(R.id.btn_sure);
        this.mTvCount = (TextView) this.viewRoot.findViewById(R.id.tv_count);
        RecyclerView recyclerView = (RecyclerView) this.viewRoot.findViewById(R.id.recyclerview);
        this.mEtContent = (ClearEditText) this.viewRoot.findViewById(R.id.ed_search);
        ImageView imageView = (ImageView) this.viewRoot.findViewById(R.id.btn_cancel);
        this.mLyProgress = (LinearLayout) this.viewRoot.findViewById(R.id.ly_progress);
        this.lyBottom = (RelativeLayout) this.viewRoot.findViewById(R.id.ly_bottom);
        this.mTvCount.setText("已推荐0个");
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.ie
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16589c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        this.viewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.je
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PushCircleDialog.lambda$new$1(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ke
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16651c.lambda$new$2(view);
            }
        });
        PushCircleAdp pushCircleAdp = new PushCircleAdp(false, false);
        this.mAdapter = pushCircleAdp;
        pushCircleAdp.setOnItemChoosedLisenter(new PushCircleAdp.OnChooseItemLisenter() { // from class: com.psychiatrygarden.widget.PushCircleDialog.1
            @Override // com.psychiatrygarden.activity.circleactivity.PushCircleAdp.OnChooseItemLisenter
            public void onDelChoosed(int pos, CirclrListBean.DataBean item) {
            }

            @Override // com.psychiatrygarden.activity.circleactivity.PushCircleAdp.OnChooseItemLisenter
            public void onItemChoosed(int pos, CirclrListBean.DataBean item) {
                if (PushCircleDialog.this.mChooseCircleIds.size() < 100) {
                    item.setSelected(!item.isSelected());
                    if (item.isSelected()) {
                        if (!PushCircleDialog.this.mChooseCircleIds.contains(item.getOrigin_id())) {
                            PushCircleDialog.this.mChooseCircleIds.add(item.getOrigin_id());
                            PushCircleDialog.this.mChooseCircleItems.add(item);
                        }
                    } else if (PushCircleDialog.this.mChooseCircleIds.contains(item.getOrigin_id())) {
                        PushCircleDialog.this.mChooseCircleIds.remove(item.getOrigin_id());
                        for (int i2 = 0; i2 < PushCircleDialog.this.mChooseCircleItems.size(); i2++) {
                            if (((CirclrListBean.DataBean) PushCircleDialog.this.mChooseCircleItems.get(i2)).getOrigin_id().equals(item.getOrigin_id())) {
                                PushCircleDialog.this.mChooseCircleItems.remove(i2);
                            }
                        }
                    }
                    PushCircleDialog.this.mAdapter.notifyItemChanged(pos, 1);
                    PushCircleDialog pushCircleDialog = PushCircleDialog.this;
                    pushCircleDialog.setBottomTxtValue(pushCircleDialog.mChooseCircleIds.size());
                } else if (item.isSelected()) {
                    if (PushCircleDialog.this.mChooseCircleIds.contains(item.getOrigin_id())) {
                        PushCircleDialog.this.mChooseCircleIds.remove(item.getOrigin_id());
                        for (int i3 = 0; i3 < PushCircleDialog.this.mChooseCircleItems.size(); i3++) {
                            if (((CirclrListBean.DataBean) PushCircleDialog.this.mChooseCircleItems.get(i3)).getOrigin_id().equals(item.getOrigin_id())) {
                                PushCircleDialog.this.mChooseCircleItems.remove(i3);
                            }
                        }
                    }
                    item.setSelected(!item.isSelected());
                    PushCircleDialog.this.mAdapter.notifyItemChanged(pos, 1);
                    PushCircleDialog pushCircleDialog2 = PushCircleDialog.this;
                    pushCircleDialog2.setBottomTxtValue(pushCircleDialog2.mChooseCircleIds.size());
                } else {
                    ToastUtil.shortToast(context, "最多只能添加100个帖子");
                }
                PushCircleDialog.this.lyBottom.setVisibility(PushCircleDialog.this.mChooseCircleIds.size() <= 0 ? 8 : 0);
            }
        });
        if (chooseList.size() > 0) {
            this.lyBottom.setVisibility(0);
            setBottomTxtValue(chooseList.size());
            for (CirclrListBean.DataBean dataBean : chooseList) {
                this.mLocalSaveIds.add(dataBean.getOrigin_id());
                this.mChooseCircleIds.add(dataBean.getOrigin_id());
            }
            this.mChooseCircleItems.addAll(chooseList);
        } else {
            this.lyBottom.setVisibility(8);
        }
        this.mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.widget.le
            @Override // com.chad.library.adapter.base.listener.OnLoadMoreListener
            public final void onLoadMore() {
                this.f16681c.lambda$new$3(context);
            }
        });
        recyclerView.setAdapter(this.mAdapter);
        this.mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.widget.me
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                return this.f16710c.lambda$new$4(inputMethodManager, context, textView2, i2, keyEvent);
            }
        });
        this.mEtContent.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.PushCircleDialog.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() == 0) {
                    PushCircleDialog.this.mAdapter.setList(new ArrayList());
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ne
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16738c.lambda$new$5(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.oe
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16767c.lambda$new$6(view);
            }
        });
        this.mEtContent.requestFocus();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.pe
            @Override // java.lang.Runnable
            public final void run() {
                this.f16798c.lambda$new$7(inputMethodManager);
            }
        }, 200L);
    }

    public static /* synthetic */ int access$610(PushCircleDialog pushCircleDialog) {
        int i2 = pushCircleDialog.page;
        pushCircleDialog.page = i2 - 1;
        return i2;
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
    public static /* synthetic */ void lambda$new$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(Context context) {
        this.page++;
        getCircleData(context, this.mEtContent.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$4(InputMethodManager inputMethodManager, Context context, TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        String strTrim = this.mEtContent.getText().toString().trim();
        if (!strTrim.equals("")) {
            this.page = 1;
            this.mLyProgress.setVisibility(0);
            getCircleData(context, strTrim);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view) {
        OnChoosedLisenter onChoosedLisenter = this.chooseCircleLisenter;
        if (onChoosedLisenter != null) {
            onChoosedLisenter.onCircleChoosed(this.mLocalSaveIds, this.mChooseCircleIds, this.mChooseCircleItems);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(InputMethodManager inputMethodManager) {
        inputMethodManager.showSoftInput(this.mEtContent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBottomTxtValue(int count) {
        String str = "已推荐" + count + "个";
        if (this.mTvCount != null) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            if (count > 0) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(SkinManager.getCurrentSkinType(this.context) == 0 ? "#DD594A" : "#B2575C")), 3, str.length() - 1, 34);
            } else {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor(SkinManager.getCurrentSkinType(this.context) == 0 ? "#141516" : "#7380A9")), 3, str.length() - 1, 34);
            }
            this.mTvCount.setText(spannableStringBuilder);
        }
    }

    public void dismissNoAnimaltion() {
        super.dismiss();
    }

    public void getCircleData(final Context context, String content) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, content);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("limit", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        ajaxParams.put("code", "bbs");
        ajaxParams.put("search_type", "1");
        YJYHttpUtils.post(context, NetworkRequestsURL.searchArticleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PushCircleDialog.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (PushCircleDialog.this.page > 1) {
                    PushCircleDialog.access$610(PushCircleDialog.this);
                    PushCircleDialog.this.mAdapter.getLoadMoreModule().loadMoreFail();
                }
                PushCircleDialog.this.mLyProgress.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                PushCircleDialog.this.mLyProgress.setVisibility(8);
                try {
                    CirclrListBean circlrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (!circlrListBean.getCode().equals("200")) {
                        if (PushCircleDialog.this.page > 1) {
                            PushCircleDialog.access$610(PushCircleDialog.this);
                            PushCircleDialog.this.mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                        ToastUtil.shortToast(context, circlrListBean.getMessage());
                        return;
                    }
                    if (circlrListBean.getData() == null || circlrListBean.getData().size() <= 0) {
                        if (PushCircleDialog.this.page > 1) {
                            PushCircleDialog.access$610(PushCircleDialog.this);
                        } else {
                            PushCircleDialog.this.data.clear();
                            PushCircleDialog.this.mAdapter.setList(PushCircleDialog.this.data);
                            PushCircleDialog.this.mAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                        }
                        if (PushCircleDialog.this.mAdapter != null) {
                            PushCircleDialog.this.mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                        if (PushCircleDialog.this.hasEmptyView || PushCircleDialog.this.mAdapter == null) {
                            return;
                        }
                        PushCircleDialog.this.hasEmptyView = true;
                        CustomEmptyView customEmptyView = new CustomEmptyView(PushCircleDialog.this.getContext(), SkinManager.getCurrentSkinType(PushCircleDialog.this.getContext()) == 0 ? R.drawable.ic_empty_data_normal_day : R.drawable.ic_empty_data_normal_night, "暂无数据");
                        customEmptyView.showEmptyView();
                        PushCircleDialog.this.mAdapter.setEmptyView(customEmptyView);
                        return;
                    }
                    for (int i2 = 0; i2 < circlrListBean.getData().size(); i2++) {
                        if (PushCircleDialog.this.mChooseCircleIds.contains(circlrListBean.getData().get(i2).getOrigin_id())) {
                            circlrListBean.getData().get(i2).setSelected(true);
                        }
                    }
                    if (PushCircleDialog.this.page != 1) {
                        List<CirclrListBean.DataBean> data = circlrListBean.getData();
                        PushCircleDialog.this.data.addAll(data);
                        PushCircleDialog.this.mAdapter.addData((Collection) data);
                        PushCircleDialog.this.mAdapter.getLoadMoreModule().loadMoreComplete();
                        return;
                    }
                    PushCircleDialog.this.data.clear();
                    PushCircleDialog.this.data = circlrListBean.getData();
                    PushCircleDialog.this.mAdapter.setSearchContent(PushCircleDialog.this.mEtContent.getText().toString().trim());
                    PushCircleDialog.this.mAdapter.setList(PushCircleDialog.this.data);
                    if (PushCircleDialog.this.data.size() < 10) {
                        PushCircleDialog.this.mAdapter.getLoadMoreModule().loadMoreEnd();
                    } else {
                        PushCircleDialog.this.mAdapter.getLoadMoreModule().loadMoreComplete();
                    }
                    if (circlrListBean.getData().size() > 8) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) PushCircleDialog.this.viewRoot.getLayoutParams();
                        layoutParams.height = PushCircleDialog.this.getContext().getResources().getDisplayMetrics().heightPixels - ScreenUtil.getPxByDp(context, 100);
                        PushCircleDialog.this.viewRoot.setLayoutParams(layoutParams);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (PushCircleDialog.this.page > 1) {
                        PushCircleDialog.access$610(PushCircleDialog.this);
                        PushCircleDialog.this.mAdapter.getLoadMoreModule().loadMoreFail();
                    }
                }
            }
        });
    }

    public void isOutTouchDismiss(boolean isOutTouchDismiss) {
        this.isOutTouchDismiss = isOutTouchDismiss;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        getWindow().setAttributes(attributes);
    }

    public void setOnChoosedLisenter(OnChoosedLisenter lisenter) {
        this.chooseCircleLisenter = lisenter;
    }
}
