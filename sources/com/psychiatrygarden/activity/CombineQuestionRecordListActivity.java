package com.psychiatrygarden.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.ChartAnswerSheetActivity;
import com.psychiatrygarden.adapter.CombineQuestionRecordAdapter;
import com.psychiatrygarden.bean.CombineQuestionRecordItem;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.RefreshHomePaperListEvent;
import com.psychiatrygarden.event.RefreshPaperListEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.DeleteRecordConfirmPop;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CombineQuestionRecordListActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private TextView buttonTextRight;
    private Drawable disableDrawable;
    private Drawable enableDrawable;
    private boolean hasEmptyView;
    private ImageView imageLeftBack;
    private boolean isEditMode;
    private boolean isLoadMore;
    private LinearLayout llDelete;
    private final CombineQuestionRecordAdapter mAdapter = new CombineQuestionRecordAdapter();
    private int mPage = 1;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private boolean selectAll;
    private TextView tvDelete;

    private void deletePaper() {
        new XPopup.Builder(this).asCustom(new DeleteRecordConfirmPop(this, new DeleteRecordConfirmPop.ClickIml() { // from class: com.psychiatrygarden.activity.CombineQuestionRecordListActivity.1
            @Override // com.psychiatrygarden.widget.DeleteRecordConfirmPop.ClickIml
            public void mClickIml() {
                if (CombineQuestionRecordListActivity.this.isEditMode) {
                    StringBuilder sb = new StringBuilder();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    int size = CombineQuestionRecordListActivity.this.mAdapter.getData().size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        if (CombineQuestionRecordListActivity.this.mAdapter.getItem(i2).isSelect()) {
                            sb.append(CombineQuestionRecordListActivity.this.mAdapter.getItem(i2).getPaperId());
                            sb.append(i2 < size + (-1) ? "," : "");
                            arrayList.add(CombineQuestionRecordListActivity.this.mAdapter.getItem(i2).getPaperId());
                            arrayList2.add(CombineQuestionRecordListActivity.this.mAdapter.getItem(i2).getTitle());
                        }
                        i2++;
                    }
                    if (sb.length() > 0) {
                        AjaxParams ajaxParams = new AjaxParams();
                        ajaxParams.put("paper_id", sb.toString());
                        YJYHttpUtils.post(CombineQuestionRecordListActivity.this, NetworkRequestsURL.deleteCombineQuestion, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CombineQuestionRecordListActivity.1.1
                            @Override // net.tsz.afinal.http.AjaxCallBack
                            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                                super.onFailure(t2, errorNo, strMsg);
                            }

                            @Override // net.tsz.afinal.http.AjaxCallBack
                            public void onSuccess(String s2) {
                                try {
                                    JSONObject jSONObject = new JSONObject(s2);
                                    if (jSONObject.optString("code", "").equals("200")) {
                                        String strOptString = jSONObject.optString("is_empty", "");
                                        ListIterator<CombineQuestionRecordItem> listIterator = CombineQuestionRecordListActivity.this.mAdapter.getData().listIterator();
                                        while (listIterator.hasNext()) {
                                            if (listIterator.next().isSelect()) {
                                                listIterator.remove();
                                            }
                                        }
                                        List<CombineQuestionRecordItem> data = CombineQuestionRecordListActivity.this.mAdapter.getData();
                                        CombineQuestionRecordListActivity.this.mAdapter.notifyDataSetChanged();
                                        if (!data.isEmpty()) {
                                            CombineQuestionRecordListActivity.this.tvDelete.setEnabled(false);
                                        } else if (strOptString.equals("0")) {
                                            CombineQuestionRecordListActivity.this.exitEditMode();
                                            CombineQuestionRecordListActivity.this.refreshLayout.autoRefresh();
                                        } else {
                                            CombineQuestionRecordListActivity.this.exitEditMode();
                                            CombineQuestionRecordListActivity.this.finish();
                                        }
                                        EventBus.getDefault().post(new RefreshHomePaperListEvent());
                                    }
                                } catch (JSONException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        });
                        String str = "[\"" + q2.a("\",\"", arrayList) + "\"]";
                        String str2 = "[\"" + q2.a("\",\"", arrayList2) + "\"]";
                        AliyunEvent aliyunEvent = AliyunEvent.DelePaper;
                        CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", str, str2, "", "2");
                    }
                }
            }
        })).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void exitEditMode() {
        this.isEditMode = false;
        this.selectAll = false;
        if (this.mAdapter.getData().isEmpty()) {
            this.buttonTextRight.setVisibility(8);
        } else {
            for (CombineQuestionRecordItem combineQuestionRecordItem : this.mAdapter.getData()) {
                combineQuestionRecordItem.setSelect(false);
                combineQuestionRecordItem.setEdit(false);
            }
            this.mAdapter.notifyDataSetChanged();
        }
        this.tvDelete.setEnabled(false);
        this.llDelete.setVisibility(8);
        this.imageLeftBack.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.mipmap.ic_black_back : R.mipmap.ic_black_back_night);
        this.refreshLayout.setEnableRefresh(true);
        this.refreshLayout.setEnableLoadMore(true);
        this.buttonTextRight.setText("编辑");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        this.mAdapter.getItem(i2).setSelect(!view.isSelected());
        this.mAdapter.notifyItemChanged(i2);
        updateDelete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.isEditMode) {
            this.mAdapter.getItem(i2).setSelect(true ^ this.mAdapter.getItem(i2).isSelect());
            this.mAdapter.notifyItemChanged(i2);
        } else {
            String paperId = this.mAdapter.getItem(i2).getPaperId();
            String type = this.mAdapter.getItem(i2).getType();
            String question_category_id = this.mAdapter.getItem(i2).getQuestion_category_id();
            if ("1".equals(type)) {
                if (!TextUtils.isEmpty(paperId) && !TextUtils.isEmpty(paperId)) {
                    ChartAnswerSheetActivity.INSTANCE.startActivity(this, paperId, this.mAdapter.getItem(i2).getTitle(), "", true, question_category_id);
                }
            } else if (!TextUtils.isEmpty(paperId)) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromQuestionCombine", true);
                bundle.putString("paperId", paperId);
                bundle.putString("title", this.mAdapter.getItem(i2).getTitle());
                AnswerSheetActivity.gotoActivity(this, bundle);
            }
        }
        updateDelete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        if (this.isEditMode) {
            exitEditMode();
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(View view) {
        if (this.mAdapter.getData().isEmpty()) {
            return;
        }
        if (!this.isEditMode) {
            this.isEditMode = true;
        }
        this.refreshLayout.setEnableRefresh(false);
        this.refreshLayout.setEnableLoadMore(false);
        if (this.llDelete.getVisibility() != 0) {
            this.llDelete.setVisibility(0);
        }
        this.imageLeftBack.setImageResource(SkinManager.getCurrentSkinType(this) == 0 ? R.mipmap.ic_push_circle_close_day : R.mipmap.ic_push_circle_close_night);
        this.tvDelete.setEnabled(this.selectAll);
        this.buttonTextRight.setText(this.selectAll ? "取消全选" : "全选");
        for (CombineQuestionRecordItem combineQuestionRecordItem : this.mAdapter.getData()) {
            combineQuestionRecordItem.setEdit(true);
            combineQuestionRecordItem.setSelect(this.selectAll);
        }
        this.selectAll = !this.selectAll;
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4(View view) {
        deletePaper();
    }

    private void loadList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.mPage + "");
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("parent_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        YJYHttpUtils.get(this, NetworkRequestsURL.questionCombineList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.CombineQuestionRecordListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (CombineQuestionRecordListActivity.this.isLoadMore) {
                    CombineQuestionRecordListActivity.this.refreshLayout.finishLoadMore();
                } else {
                    CombineQuestionRecordListActivity.this.refreshLayout.finishRefresh();
                }
                if (CombineQuestionRecordListActivity.this.hasEmptyView) {
                    return;
                }
                CombineQuestionRecordListActivity.this.hasEmptyView = true;
                CombineQuestionRecordListActivity.this.mAdapter.setEmptyView(R.layout.layout_empty_view);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code", "").equals("200")) {
                        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
                        if (jSONArrayOptJSONArray != null) {
                            List list = (List) new Gson().fromJson(jSONArrayOptJSONArray.toString(), new TypeToken<List<CombineQuestionRecordItem>>() { // from class: com.psychiatrygarden.activity.CombineQuestionRecordListActivity.2.1
                            }.getType());
                            if (list.isEmpty()) {
                                if (CombineQuestionRecordListActivity.this.isLoadMore) {
                                    CombineQuestionRecordListActivity.this.refreshLayout.finishLoadMoreWithNoMoreData();
                                } else {
                                    CombineQuestionRecordListActivity.this.refreshLayout.finishRefreshWithNoMoreData();
                                }
                            } else if (CombineQuestionRecordListActivity.this.isLoadMore) {
                                CombineQuestionRecordListActivity.this.mAdapter.addData((Collection) list);
                                CombineQuestionRecordListActivity.this.refreshLayout.finishLoadMore();
                            } else {
                                CombineQuestionRecordListActivity.this.refreshLayout.finishRefresh();
                                CombineQuestionRecordListActivity.this.mAdapter.setList(list);
                            }
                        }
                        CombineQuestionRecordListActivity.this.buttonTextRight.setVisibility(CombineQuestionRecordListActivity.this.mAdapter.getData().isEmpty() ? 8 : 0);
                    } else {
                        String strOptString = jSONObject.optString("message", "");
                        if (!TextUtils.isEmpty(strOptString)) {
                            CombineQuestionRecordListActivity.this.AlertToast(strOptString);
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    if (CombineQuestionRecordListActivity.this.isLoadMore) {
                        CombineQuestionRecordListActivity.this.refreshLayout.finishLoadMore();
                    } else {
                        CombineQuestionRecordListActivity.this.refreshLayout.finishRefresh();
                    }
                }
                if (CombineQuestionRecordListActivity.this.hasEmptyView) {
                    return;
                }
                CombineQuestionRecordListActivity.this.hasEmptyView = true;
                CombineQuestionRecordListActivity.this.mAdapter.setEmptyView(R.layout.layout_empty_view);
            }
        });
    }

    public static void navigationToCombineQuestionRecordList(Context context) {
        context.startActivity(new Intent(context, (Class<?>) CombineQuestionRecordListActivity.class));
    }

    private void updateDelete() {
        Iterator<CombineQuestionRecordItem> it = this.mAdapter.getData().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().isSelect()) {
                i2++;
            }
        }
        this.tvDelete.setEnabled(i2 > 0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("组题记录");
        this.buttonTextRight.setText("编辑");
        this.buttonTextRight.setVisibility(8);
        this.mAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        this.mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() { // from class: com.psychiatrygarden.activity.l2
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12652c.lambda$init$0(baseQuickAdapter, view, i2);
            }
        });
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.m2
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12685c.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
        this.imageLeftBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.n2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13034c.lambda$init$2(view);
            }
        });
        this.buttonTextRight.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.o2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13069c.lambda$init$3(view);
            }
        });
        this.tvDelete.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.p2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13529c.lambda$init$4(view);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(RefreshPaperListEvent event) {
        for (int i2 = 0; i2 < this.mAdapter.getData().size(); i2++) {
            if (TextUtils.equals(this.mAdapter.getItem(i2).getPaperId(), event.getPaperId())) {
                this.mAdapter.getItem(i2).setCurrent(event.getErrorCount() + event.getRightCount());
                this.mAdapter.getItem(i2).setErrorCount(event.getErrorCount());
                this.mAdapter.getItem(i2).setRightCount(event.getRightCount());
                this.mAdapter.notifyItemChanged(i2);
                return;
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !this.isEditMode) {
            return super.onKeyDown(keyCode, event);
        }
        exitEditMode();
        return true;
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.mPage++;
        this.isLoadMore = true;
        loadList();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.mPage = 1;
        this.isLoadMore = false;
        loadList();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_artice_combine_question_list);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        this.buttonTextRight = (TextView) findViewById(R.id.iv_actionbar_right);
        this.imageLeftBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.tvDelete = (TextView) findViewById(R.id.tv_delete);
        this.llDelete = (LinearLayout) findViewById(R.id.ll_delete);
        this.refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        TypedArray typedArrayObtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.red_round_coner, R.attr.login_btn_en_disable});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            this.enableDrawable = drawable;
        }
        if (drawable2 != null) {
            this.disableDrawable = drawable2;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.refreshLayout.setOnRefreshLoadMoreListener(this);
        this.refreshLayout.autoRefresh();
    }
}
