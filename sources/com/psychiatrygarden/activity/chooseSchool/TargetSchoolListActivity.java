package com.psychiatrygarden.activity.chooseSchool;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.adapter.TargetSchoolListAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class TargetSchoolListActivity extends BaseActivity implements View.OnClickListener {
    private TextView addTargetSchoolBtnTv;
    private TargetSchoolListAdapter mAdapter;
    private CustomEmptyView mEmptyView;
    private LinearLayout mLlEmpty;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvMemberDetail;
    private int page = 1;
    private int pageSize = 20;
    private List<FollowSchoolBean.DataBean> mDetailList = new ArrayList();
    private boolean isEdited = false;
    String ids = "";
    private boolean isVisible = false;

    public static /* synthetic */ int access$310(TargetSchoolListActivity targetSchoolListActivity) {
        int i2 = targetSchoolListActivity.page;
        targetSchoolListActivity.page = i2 - 1;
        return i2;
    }

    private void initRefreshLayout() {
        this.mRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.chooseSchool.f7
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f11262c.lambda$initRefreshLayout$1(refreshLayout);
            }
        });
        this.mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.activity.chooseSchool.g7
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f11299c.lambda$initRefreshLayout$2(refreshLayout);
            }
        });
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.mEmptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.h7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11309c.lambda$initRefreshLayout$3(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, FollowSchoolBean.DataBean dataBean) {
        SchoolDetailsAct.newIntent(this.mContext, dataBean.getSchool_id());
        setBtnNoClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$1(RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$2(RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefreshLayout$3(View view) {
        this.page = 1;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$5(FollowSchoolBean.DataBean dataBean) {
        if (dataBean.isItem_select()) {
            this.ids += dataBean.getSchool_id() + ",";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", this.pageSize + "");
        ajaxParams.put("limit", "0");
        YJYHttpUtils.get(this, NetworkRequestsURL.chooseSchoolTargetSchool, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.TargetSchoolListActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                TargetSchoolListActivity.this.hideProgressDialog();
                TargetSchoolListActivity.this.mRefreshLayout.finishRefresh();
                NewToast.showShort(TargetSchoolListActivity.this, "网络连接失败", 0).show();
                if (TargetSchoolListActivity.this.page == 1) {
                    TargetSchoolListActivity.this.showEmptyView();
                } else {
                    TargetSchoolListActivity.access$310(TargetSchoolListActivity.this);
                    TargetSchoolListActivity.this.mRefreshLayout.finishLoadMore(false);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                if (TargetSchoolListActivity.this.isVisible) {
                    TargetSchoolListActivity.this.showProgressDialog();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                TargetSchoolListActivity.this.hideProgressDialog();
                TargetSchoolListActivity.this.mRefreshLayout.finishRefresh();
                try {
                    FollowSchoolBean followSchoolBean = (FollowSchoolBean) new Gson().fromJson(s2, FollowSchoolBean.class);
                    if ("200".equals(followSchoolBean.getCode())) {
                        TargetSchoolListActivity.this.updateUI(followSchoolBean);
                    } else {
                        NewToast.showShort(TargetSchoolListActivity.this, followSchoolBean.getMessage(), 0).show();
                        if (TargetSchoolListActivity.this.page == 1) {
                            TargetSchoolListActivity.this.showEmptyView();
                        } else {
                            TargetSchoolListActivity.access$310(TargetSchoolListActivity.this);
                            TargetSchoolListActivity.this.mRefreshLayout.finishLoadMore(false);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    NewToast.showShort(TargetSchoolListActivity.this, "数据解析出错", 0).show();
                    if (TargetSchoolListActivity.this.page == 1) {
                        TargetSchoolListActivity.this.showEmptyView();
                    } else {
                        TargetSchoolListActivity.access$310(TargetSchoolListActivity.this);
                        TargetSchoolListActivity.this.mRefreshLayout.finishLoadMore(false);
                    }
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) TargetSchoolListActivity.class);
    }

    private void setBtnClick() {
        this.addTargetSchoolBtnTv.setEnabled(true);
        this.addTargetSchoolBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_btn_red_radius_12_day_night));
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.zx_color_blue_night));
        } else {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBtnNoClick() {
        this.addTargetSchoolBtnTv.setEnabled(false);
        this.addTargetSchoolBtnTv.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_block_corners_12));
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.forth_txt_color_night));
        } else {
            this.addTargetSchoolBtnTv.setTextColor(ContextCompat.getColor(this, R.color.forth_txt_color));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyView() {
        this.mLlEmpty.setVisibility(0);
        this.mRefreshLayout.setVisibility(8);
    }

    private void toAddTarget(String ids) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("school_id", ids);
        ajaxParams.put("operate", PLVRemoveMicSiteEvent.EVENT_NAME);
        YJYHttpUtils.post(this, NetworkRequestsURL.saveDelTargetSchool, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.TargetSchoolListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                TargetSchoolListActivity.this.hideProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
                TargetSchoolListActivity.this.showProgressDialog();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                TargetSchoolListActivity.this.hideProgressDialog();
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optInt("code") == 200) {
                        ToastUtil.shortToast(TargetSchoolListActivity.this.mContext, "删除成功！");
                        EventBus.getDefault().post(EventBusConstant.EVENT_DEL_TARGET_SCHOOL_SUCCESS);
                        TargetSchoolListActivity.this.setBtnNoClick();
                        TargetSchoolListActivity.this.isEdited = true;
                        TargetSchoolListActivity.this.page = 1;
                        TargetSchoolListActivity.this.loadData();
                    } else {
                        Toast.makeText(TargetSchoolListActivity.this, jSONObject.optString("message"), 0).show();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI(FollowSchoolBean detailBean) {
        if (detailBean.getData() == null || detailBean.getData().isEmpty()) {
            if (this.page != 1) {
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
            this.mDetailList.clear();
            showEmptyView();
            this.mRefreshLayout.finishLoadMoreWithNoMoreData();
            return;
        }
        List<FollowSchoolBean.DataBean> data = detailBean.getData();
        if (this.page != 1) {
            int size = this.mDetailList.size();
            this.mDetailList.addAll(data);
            this.mAdapter.notifyItemRangeInserted(size, data.size());
            if (data.size() < this.pageSize) {
                this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                return;
            } else {
                this.mRefreshLayout.finishLoadMore();
                return;
            }
        }
        this.mDetailList.clear();
        this.mDetailList.addAll(data);
        if (this.isEdited && Build.VERSION.SDK_INT >= 24) {
            this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.e7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((FollowSchoolBean.DataBean) obj).setEditing_state(true);
                }
            });
        }
        this.mAdapter.notifyDataSetChanged();
        this.mLlEmpty.setVisibility(8);
        this.mRefreshLayout.setVisibility(0);
        if (data.size() < this.pageSize) {
            this.mRefreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            this.mRefreshLayout.finishLoadMore();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("目标院校");
        this.mTvActionbarRight.setText("编辑");
        this.mTvActionbarRight.setVisibility(0);
        this.mBtnActionbarRight.setVisibility(8);
        this.mRvMemberDetail = (RecyclerView) findViewById(R.id.rv_member_detail);
        this.mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        TextView textView = (TextView) findViewById(R.id.add_target_school_btn_tv);
        this.addTargetSchoolBtnTv = textView;
        textView.setOnClickListener(this);
        this.mTvActionbarRight.setOnClickListener(this);
        this.mBtnActionbarBack.setOnClickListener(this);
        this.mAdapter = new TargetSchoolListAdapter(this, this.mDetailList);
        this.mRvMemberDetail.setLayoutManager(new LinearLayoutManager(this));
        this.mRvMemberDetail.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(new TargetSchoolListAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.d7
            @Override // com.psychiatrygarden.adapter.TargetSchoolListAdapter.OnItemClickListener
            public final void onItemClick(int i2, FollowSchoolBean.DataBean dataBean) {
                this.f11243a.lambda$init$0(i2, dataBean);
            }
        });
        initRefreshLayout();
        loadData();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.add_target_school_btn_tv) {
            if (!this.addTargetSchoolBtnTv.getText().equals("删除")) {
                startActivity(SearchTargetSchoolActivity.newIntent(this));
                return;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                this.mAdapter.getList().forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.i7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f11323c.lambda$onClick$5((FollowSchoolBean.DataBean) obj);
                    }
                });
            }
            if (this.ids.isEmpty()) {
                ToastUtil.shortToast(this.mContext, "请选择院校！");
                return;
            } else {
                String str = this.ids;
                toAddTarget(str.substring(0, str.length() - 1));
                return;
            }
        }
        if (id == R.id.iv_actionbar_back) {
            if (this.mTvActionbarRight.getText().equals("编辑")) {
                finish();
                return;
            }
            this.isEdited = false;
            if (Build.VERSION.SDK_INT >= 24) {
                this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.n7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FollowSchoolBean.DataBean) obj).setEditing_state(false);
                    }
                });
                this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.j7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FollowSchoolBean.DataBean) obj).setItem_select(false);
                    }
                });
            }
            this.mAdapter.notifyDataSetChanged();
            this.mTvActionbarRight.setText("编辑");
            this.addTargetSchoolBtnTv.setText("添加目标院校");
            this.mBtnActionbarBack.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_black_back_night : R.mipmap.ic_black_back);
            setBtnClick();
            return;
        }
        if (id != R.id.tv_actionbar_right) {
            return;
        }
        if (this.mTvActionbarRight.getText().toString().equals("全选")) {
            if (this.mDetailList.isEmpty()) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.k7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FollowSchoolBean.DataBean) obj).setItem_select(true);
                    }
                });
            }
            setBtnClick();
            this.mTvActionbarRight.setText("取消全选");
        } else if (this.mTvActionbarRight.getText().toString().equals("取消全选")) {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.l7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FollowSchoolBean.DataBean) obj).setItem_select(false);
                    }
                });
            }
            this.mTvActionbarRight.setText("全选");
            setBtnNoClick();
        } else if (this.mTvActionbarRight.getText().toString().equals("编辑")) {
            this.isEdited = true;
            this.addTargetSchoolBtnTv.setText("删除");
            setBtnNoClick();
            this.mTvActionbarRight.setText("全选");
            this.mBtnActionbarBack.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.mipmap.ic_push_circle_close_night : R.mipmap.ic_push_circle_close_day);
            if (Build.VERSION.SDK_INT >= 24) {
                this.mDetailList.forEach(new Consumer() { // from class: com.psychiatrygarden.activity.chooseSchool.m7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((FollowSchoolBean.DataBean) obj).setEditing_state(true);
                    }
                });
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        str.hashCode();
        switch (str) {
            case "target_school_no_select":
                setBtnNoClick();
                break;
            case "add_target_school_success":
                this.page = 1;
                loadData();
                break;
            case "target_school_has_select":
                setBtnClick();
                break;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.isVisible = false;
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        this.page = 1;
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isVisible = true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_target_school);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
